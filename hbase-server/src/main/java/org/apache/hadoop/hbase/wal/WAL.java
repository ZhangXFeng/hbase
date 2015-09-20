/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.wal;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.hbase.classification.InterfaceStability;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.HTableDescriptor;

// imports we use from yet-to-be-moved regionsever.wal
import org.apache.hadoop.hbase.regionserver.wal.CompressionContext;
import org.apache.hadoop.hbase.regionserver.wal.FailedLogCloseException;
import org.apache.hadoop.hbase.regionserver.wal.HLogKey;
import org.apache.hadoop.hbase.regionserver.wal.WALActionsListener;
import org.apache.hadoop.hbase.regionserver.wal.WALCoprocessorHost;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;

/**
 * A Write Ahead Log (WAL) provides service for reading, writing waledits. This interface provides
 * APIs for WAL users (such as RegionServer) to use the WAL (do append, sync, etc).
 *
 * Note that some internals, such as log rolling and performance evaluation tools, will use
 * WAL.equals to determine if they have already seen a given WAL.
 */
@InterfaceAudience.Private
@InterfaceStability.Evolving
public interface WAL {

  /**
   * Registers WALActionsListener
   */
  void registerWALActionsListener(final WALActionsListener listener);

  /**
   * Unregisters WALActionsListener
   */
  boolean unregisterWALActionsListener(final WALActionsListener listener);

  /**
   * Roll the log writer. That is, start writing log messages to a new file.
   *
   * <p>
   * The implementation is synchronized in order to make sure there's one rollWriter
   * running at any given time.
   *
   * @return If lots of logs, flush the returned regions so next time through we
   *         can clean logs. Returns null if nothing to flush. Names are actual
   *         region names as returned by {@link HRegionInfo#getEncodedName()}
   */
  byte[][] rollWriter() throws FailedLogCloseException, IOException;

  /**
   * Roll the log writer. That is, start writing log messages to a new file.
   *
   * <p>
   * The implementation is synchronized in order to make sure there's one rollWriter
   * running at any given time.
   *
   * @param force
   *          If true, force creation of a new writer even if no entries have
   *          been written to the current writer
   * @return If lots of logs, flush the returned regions so next time through we
   *         can clean logs. Returns null if nothing to flush. Names are actual
   *         region names as returned by {@link HRegionInfo#getEncodedName()}
   */
  byte[][] rollWriter(boolean force) throws FailedLogCloseException, IOException;

  /**
   * Stop accepting new writes. If we have unsynced writes still in buffer, sync them.
   * Extant edits are left in place in backing storage to be replayed later.
   */
  void shutdown() throws IOException;

  /**
   * Caller no longer needs any edits from this WAL. Implementers are free to reclaim
   * underlying resources after this call; i.e. filesystem based WALs can archive or
   * delete files.
   */
  void close() throws IOException;

  /**
   * Append a set of edits to the WAL. The WAL is not flushed/sync'd after this transaction
   * completes BUT on return this edit must have its region edit/sequence id assigned
   * else it messes up our unification of mvcc and sequenceid.  On return <code>key</code> will
   * have the region edit/sequence id filled in.
   * @param info
   * @param key Modified by this call; we add to it this edits region edit/sequence id.
   * @param edits Edits to append. MAY CONTAIN NO EDITS for case where we want to get an edit
   * sequence id that is after all currently appended edits.
   * @param htd used to give scope for replication TODO refactor out in favor of table name and info
   * @param sequenceId A reference to the atomic long the <code>info</code> region is using as
   * source of its incrementing edits sequence id.  Inside in this call we will increment it and
   * attach the sequence to the edit we apply the WAL.
   * @param inMemstore Always true except for case where we are writing a compaction completion
   * record into the WAL; in this case the entry is just so we can finish an unfinished compaction
   * -- it is not an edit for memstore.
   * @param memstoreKVs list of KVs added into memstore
   * @return Returns a 'transaction id' and <code>key</code> will have the region edit/sequence id
   * in it.
   */
  long append(HTableDescriptor htd, HRegionInfo info, WALKey key, WALEdit edits,
      AtomicLong sequenceId, boolean inMemstore, List<Cell> memstoreKVs)
  throws IOException;

  /**
   * Sync what we have in the WAL.
   * @throws IOException
   */
  void sync() throws IOException;

  /**
   * Sync the WAL if the txId was not already sync'd.
   * @param txid Transaction id to sync to.
   * @throws IOException
   */
  void sync(long txid) throws IOException;

  /**
   * WAL keeps track of the sequence numbers that were not yet flushed from memstores
   * in order to be able to do cleanup. This method tells WAL that some region is about
   * to flush memstore.
   *
   * <p>We stash the oldest seqNum for the region, and let the the next edit inserted in this
   * region be recorded in {@link #append(HTableDescriptor, HRegionInfo, WALKey, WALEdit,
   * AtomicLong, boolean, List)} as new oldest seqnum.
   * In case of flush being aborted, we put the stashed value back; in case of flush succeeding,
   * the seqNum of that first edit after start becomes the valid oldest seqNum for this region.
   *
   * @return true if the flush can proceed, false in case wal is closing (ususally, when server is
   * closing) and flush couldn't be started.
   */
  boolean startCacheFlush(final byte[] encodedRegionName);

  /**
   * Complete the cache flush.
   * @param encodedRegionName Encoded region name.
   */
  void completeCacheFlush(final byte[] encodedRegionName);

  /**
   * Abort a cache flush. Call if the flush fails. Note that the only recovery
   * for an aborted flush currently is a restart of the regionserver so the
   * snapshot content dropped by the failure gets restored to the memstore.v
   * @param encodedRegionName Encoded region name.
   */
  void abortCacheFlush(byte[] encodedRegionName);

  /**
   * @return Coprocessor host.
   */
  WALCoprocessorHost getCoprocessorHost();


  /** Gets the earliest sequence number in the memstore for this particular region.
   * This can serve as best-effort "recent" WAL number for this region.
   * @param encodedRegionName The region to get the number for.
   * @return The number if present, HConstants.NO_SEQNUM if absent.
   */
  long getEarliestMemstoreSeqNum(byte[] encodedRegionName);

  /**
   * Human readable identifying information about the state of this WAL.
   * Implementors are encouraged to include information appropriate for debugging.
   * Consumers are advised not to rely on the details of the returned String; it does
   * not have a defined structure.
   */
  @Override
  String toString();

  /**
   * When outside clients need to consume persisted WALs, they rely on a provided
   * Reader.
   */
  interface Reader extends Closeable {
    Entry next() throws IOException;
    Entry next(Entry reuse) throws IOException;
    void seek(long pos) throws IOException;
    long getPosition() throws IOException;
    void reset() throws IOException;
  }

  /**
   * Utility class that lets us keep track of the edit with it's key.
   */
  class Entry {
    private WALEdit edit;
    private WALKey key;

    public Entry() {
      edit = new WALEdit();
      // we use HLogKey here instead of WALKey directly to support legacy coprocessors.
      key = new HLogKey();
    }

    /**
     * Constructor for both params
     *
     * @param edit log's edit
     * @param key log's key
     */
    public Entry(WALKey key, WALEdit edit) {
      super();
      this.key = key;
      this.edit = edit;
    }

    /**
     * Gets the edit
     *
     * @return edit
     */
    public WALEdit getEdit() {
      return edit;
    }

    /**
     * Gets the key
     *
     * @return key
     */
    public WALKey getKey() {
      return key;
    }

    /**
     * Set compression context for this entry.
     *
     * @param compressionContext
     *          Compression context
     */
    public void setCompressionContext(CompressionContext compressionContext) {
      edit.setCompressionContext(compressionContext);
      key.setCompressionContext(compressionContext);
    }

    @Override
    public String toString() {
      return this.key + "=" + this.edit;
    }

  }

}
