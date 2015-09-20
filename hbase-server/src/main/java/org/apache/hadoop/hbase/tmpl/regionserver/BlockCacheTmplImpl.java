// Autogenerated Jamon implementation
// /Users/enis/projects/git-repos/hbase/hbase-server/src/main/jamon/org/apache/hadoop/hbase/tmpl/regionserver/BlockCacheTmpl.jamon

package org.apache.hadoop.hbase.tmpl.regionserver;

// 37, 1
import java.util.Map;
// 38, 1
import org.apache.hadoop.hbase.io.hfile.BlockCacheUtil.CachedBlocksByFile;
// 39, 1
import org.apache.hadoop.hbase.io.hfile.AgeSnapshot;
// 40, 1
import org.apache.hadoop.hbase.io.hfile.CachedBlock;
// 41, 1
import org.apache.hadoop.conf.Configuration;
// 42, 1
import org.apache.hadoop.hbase.io.hfile.CacheConfig;
// 43, 1
import org.apache.hadoop.hbase.io.hfile.BlockCache;
// 44, 1
import org.apache.hadoop.hbase.io.hfile.bucket.BucketCacheStats;
// 45, 1
import org.apache.hadoop.hbase.io.hfile.bucket.BucketCache;
// 46, 1
import org.apache.hadoop.hbase.io.hfile.bucket.BucketAllocator;
// 47, 1
import org.apache.hadoop.hbase.io.hfile.bucket.BucketAllocator.Bucket;
// 48, 1
import org.apache.hadoop.util.StringUtils;

public class BlockCacheTmplImpl
  extends org.jamon.AbstractTemplateImpl
  implements org.apache.hadoop.hbase.tmpl.regionserver.BlockCacheTmpl.Intf

{
  private final CacheConfig cacheConfig;
  private final Configuration config;
  protected static org.apache.hadoop.hbase.tmpl.regionserver.BlockCacheTmpl.ImplData __jamon_setOptionalArguments(org.apache.hadoop.hbase.tmpl.regionserver.BlockCacheTmpl.ImplData p_implData)
  {
    return p_implData;
  }
  public BlockCacheTmplImpl(org.jamon.TemplateManager p_templateManager, org.apache.hadoop.hbase.tmpl.regionserver.BlockCacheTmpl.ImplData p_implData)
  {
    super(p_templateManager, __jamon_setOptionalArguments(p_implData));
    cacheConfig = p_implData.getCacheConfig();
    config = p_implData.getConfig();
  }
  
  public void renderNoFlush(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter)
    throws java.io.IOException
  {
    // 24, 1
    
  BlockCache bc = cacheConfig == null? null: cacheConfig.getBlockCache();
  String bcUrl = null;
  String bcName = null;
  if (bc != null) {
    bcUrl = "http://hbase.apache.org/devapidocs/" + bc.getClass().getName().replaceAll("\\.", "/") + ".html";
    bcName = bc.getClass().getSimpleName();
  }
  BlockCache [] bcs = cacheConfig == null? null: cacheConfig.getBlockCache() == null? null: cacheConfig.getBlockCache().getBlockCaches();
  // If more than one bc, show evictions in each bc listing so can compare
  boolean evictions = bcs != null && bcs.length > 1;

    // 50, 1
    jamonWriter.write("<div class=\"tabbable\">\n    <ul class=\"nav nav-pills\">\n        <li class=\"active\"><a href=\"#tab_bc_baseInfo\" data-toggle=\"tab\">Base Info</a></li>\n        <li class=\"\"><a href=\"#tab_bc_config\" data-toggle=\"tab\">Config</a></li>\n        <li class=\"\"><a href=\"#tab_bc_stats\" data-toggle=\"tab\">Stats</a></li>\n        <li class=\"\"><a href=\"#tab_bc_l1\" data-toggle=\"tab\">L1</a></li>\n        <li class=\"\"><a href=\"#tab_bc_l2\" data-toggle=\"tab\">L2</a></li>\n    </ul>\n    <div class=\"tab-content\" style=\"padding-bottom: 9px; border-bottom: 1px solid #ddd;\">\n        <div class=\"tab-pane active\" id=\"tab_bc_baseInfo\">\n            ");
    // 60, 13
    {
      // 60, 13
      __jamon_innerUnit__bc_baseInfo(jamonWriter, cacheConfig, bcUrl, bcName);
    }
    // 60, 91
    jamonWriter.write("\n        </div>\n        <div class=\"tab-pane\" id=\"tab_bc_config\">\n            ");
    // 63, 13
    {
      // 63, 13
      __jamon_innerUnit__bc_config(jamonWriter, cacheConfig );
    }
    // 63, 55
    jamonWriter.write("\n        </div>\n        <div class=\"tab-pane\" id=\"tab_bc_stats\">\n            ");
    // 66, 13
    {
      // 66, 13
      __jamon_innerUnit__bc_stats(jamonWriter, cacheConfig );
    }
    // 66, 54
    jamonWriter.write("\n        </div>\n        <div class=\"tab-pane\" id=\"tab_bc_l1\">\n            ");
    // 69, 13
    {
      // 69, 13
      __jamon_innerUnit__bc_l(jamonWriter, bcs == null? bc: bcs[0], "L1", evictions);
    }
    // 69, 90
    jamonWriter.write("\n        </div>\n        <div class=\"tab-pane\" id=\"tab_bc_l2\">\n            ");
    // 72, 13
    {
      // 72, 13
      __jamon_innerUnit__bc_l(jamonWriter, bcs == null? null: bcs.length <= 1? null: bcs[1], "L2", evictions);
    }
    // 72, 115
    jamonWriter.write("\n        </div>\n    </div>\n</div>\n\n");
  }
  
  
  // 209, 1
  private void __jamon_innerUnit__bc_stats(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final CacheConfig cacheConfig)
    throws java.io.IOException
  {
    // 213, 1
    if (cacheConfig == null || cacheConfig.getBlockCache() == null )
    {
      // 213, 67
      jamonWriter.write("\n<p>CacheConfig is null</p>\n");
    }
    // 215, 1
    else
    {
      // 215, 8
      jamonWriter.write("\n<table class=\"table table-striped\">\n    <tr>\n        <th>Attribute</th>\n        <th>Value</th>\n        <th>Description</th>\n    </tr>\n    <tr>\n        <td>Size</td>\n        <td>");
      // 224, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(StringUtils.humanReadableInt(cacheConfig.getBlockCache().getCurrentSize())), jamonWriter);
      // 224, 93
      jamonWriter.write("</td>\n        <td>Current size of block cache in use (bytes)</td>\n    </tr>\n    <tr>\n        <td>Free</td>\n        <td>");
      // 229, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(StringUtils.humanReadableInt(cacheConfig.getBlockCache().getFreeSize())), jamonWriter);
      // 229, 90
      jamonWriter.write("</td>\n        <td>The total free memory currently available to store more cache entries (bytes)</td>\n    </tr>\n    <tr>\n        <td>Count</td>\n        <td>");
      // 234, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cacheConfig.getBlockCache().getBlockCount())), jamonWriter);
      // 234, 84
      jamonWriter.write("</td>\n        <td>Number of blocks in block cache</td>\n    </tr>\n    ");
      // 237, 5
      {
        // 237, 5
        __jamon_innerUnit__evictions_tmpl(jamonWriter, cacheConfig.getBlockCache());
      }
      // 237, 60
      jamonWriter.write("\n    <tr>\n        <td>Hits</td>\n        <td>");
      // 240, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cacheConfig.getBlockCache().getStats().getHitCount())), jamonWriter);
      // 240, 93
      jamonWriter.write("</td>\n        <td>Number requests that were cache hits</td>\n    </tr>\n    <tr>\n        <td>Hits Caching</td>\n        <td>");
      // 245, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cacheConfig.getBlockCache().getStats().getHitCachingCount())), jamonWriter);
      // 245, 100
      jamonWriter.write("</td>\n        <td>Cache hit block requests but only requests set to cache block if a miss</td>\n    </tr>\n    <tr>\n        <td>Misses</td>\n        <td>");
      // 250, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cacheConfig.getBlockCache().getStats().getMissCount())), jamonWriter);
      // 250, 94
      jamonWriter.write("</td>\n        <td>Block requests that were cache misses but set to cache missed blocks</td>\n    </tr>\n    <tr>\n        <td>Misses Caching</td>\n        <td>");
      // 255, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cacheConfig.getBlockCache().getStats().getMissCount())), jamonWriter);
      // 255, 94
      jamonWriter.write("</td>\n        <td>Block requests that were cache misses but only requests set to use block cache</td>\n    </tr>\n    <tr>\n        <td>Hit Ratio</td>\n        <td>");
      // 260, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,.2f", cacheConfig.getBlockCache().getStats().getHitRatio() * 100)), jamonWriter);
      // 260, 101
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf("%"), jamonWriter);
      // 260, 110
      jamonWriter.write("</td>\n        <td>Hit Count divided by total requests count</td>\n    </tr>\n</table>\n<p>If block cache is made up of more than one cache -- i.e. a L1 and a L2 -- then the above\nare combined counts. Request count is sum of hits and misses.</p>\n");
    }
    // 266, 7
    jamonWriter.write("\n");
  }
  
  
  // 119, 1
  private void __jamon_innerUnit__bc_config(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final CacheConfig cacheConfig)
    throws java.io.IOException
  {
    // 123, 1
    if (cacheConfig == null )
    {
      // 123, 28
      jamonWriter.write("\n<p>CacheConfig is null</p>\n");
    }
    // 125, 1
    else
    {
      // 125, 8
      jamonWriter.write("\n<table class=\"table table-striped\">\n    <tr>\n        <th>Attribute</th>\n        <th>Value</th>\n        <th>Description</th>\n    </tr>\n    <tr>\n        <td>Cache DATA on Read</td>\n        <td>");
      // 134, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldCacheDataOnRead()), jamonWriter);
      // 134, 54
      jamonWriter.write("</td>\n        <td>True if DATA blocks are cached on read\n        (INDEX & BLOOM blocks are always cached)</td>\n    </tr>\n    <tr>\n        <td>Cache DATA on Write</td>\n        <td>");
      // 140, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldCacheDataOnWrite()), jamonWriter);
      // 140, 55
      jamonWriter.write("</td>\n        <td>True if DATA blocks are cached on write.</td>\n    </tr>\n    <tr>\n        <td>Cache INDEX on Write</td>\n        <td>");
      // 145, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldCacheIndexesOnWrite()), jamonWriter);
      // 145, 58
      jamonWriter.write("</td>\n        <td>True if INDEX blocks are cached on write</td>\n    </tr>\n    <tr>\n        <td>Cache BLOOM on Write</td>\n        <td>");
      // 150, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldCacheBloomsOnWrite()), jamonWriter);
      // 150, 57
      jamonWriter.write("</td>\n        <td>True if BLOOM blocks are cached on write</td>\n    </tr>\n    <tr>\n        <td>Evict blocks on Close</td>\n        <td>");
      // 155, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldEvictOnClose()), jamonWriter);
      // 155, 51
      jamonWriter.write("</td>\n        <td>True if blocks are evicted from cache when an HFile\n        reader is closed</td>\n    </tr>\n    <tr>\n        <td>Cache DATA in compressed format</td>\n        <td>");
      // 161, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldCacheDataCompressed()), jamonWriter);
      // 161, 58
      jamonWriter.write("</td>\n        <td>True if DATA blocks are cached in their compressed form</td>\n    </tr>\n    <tr>\n        <td>Prefetch on Open</td>\n        <td>");
      // 166, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cacheConfig.shouldPrefetchOnOpen()), jamonWriter);
      // 166, 53
      jamonWriter.write("</td>\n        <td>True if blocks are prefetched into cache on open</td>\n    </tr>\n</table>\n");
    }
    // 170, 7
    jamonWriter.write("\n");
  }
  
  
  // 77, 1
  private void __jamon_innerUnit__bc_baseInfo(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final CacheConfig cacheConfig, final String bcUrl, final String bcName)
    throws java.io.IOException
  {
    // 83, 1
    
  BlockCache bc = cacheConfig == null? null: cacheConfig.getBlockCache();
  BlockCache [] bcs = bc == null? null: bc.getBlockCaches();
  String bcl1Url = null;
  String bcl1Name = null;
  String bcl2Url = null;
  String bcl2Name = null;
  if (bcs != null) {
    BlockCache bcl1 = bcs[0];
    if (bcl1 != null) {
      bcl1Url = "http://hbase.apache.org/devapidocs/" + bcl1.getClass().getName().replaceAll("\\.", "/") + ".html";
      bcl1Name = bcl1.getClass().getSimpleName();
    }
    if (bcs.length == 2) {
      BlockCache bcl2 = bcs[1];
      bcl2Url = "http://hbase.apache.org/devapidocs/" + bcl2.getClass().getName().replaceAll("\\.", "/") + ".html";
      bcl2Name = bcl2.getClass().getSimpleName();
    }
  }

    // 103, 1
    jamonWriter.write("<table class=\"table table-striped\">\n    <tr>\n        <th>Attribute</th>\n        <th>Value</th>\n        <th>Description</th>\n    </tr>\n    </tr>\n    <tr>\n        <td>Implementation</td>\n        <td><a href=\"");
    // 112, 22
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bcUrl), jamonWriter);
    // 112, 33
    jamonWriter.write("\">");
    // 112, 35
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bcName), jamonWriter);
    // 112, 47
    jamonWriter.write("</a></td>\n        <td>Block cache implementing class</td>\n    </tr>\n</table>\n<p>See <a href=\"http://hbase.apache.org/book.html#block.cache\">block cache</a> in the HBase Reference Guide for help.</p>\n");
  }
  
  
  // 282, 1
  private void __jamon_innerUnit__block_cache(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final BlockCache bc, final String name, final boolean evictions)
    throws java.io.IOException
  {
    // 288, 1
    
  final long nanosPerSecond = 1000000000;
  String bcUrl = "http://hbase.apache.org/devapidocs/" + bc.getClass().getName().replaceAll("\\.", "/") + ".html";
  String bcName = bc.getClass().getSimpleName();
  org.apache.hadoop.hbase.io.hfile.BlockCacheUtil.CachedBlocksByFile cbsbf =
    org.apache.hadoop.hbase.io.hfile.BlockCacheUtil.getLoadedCachedBlocksByFile(config, bc);
  AgeSnapshot cbsbfSnapshot = cbsbf.getAgeInCacheSnapshot();

  boolean bucketCache = bc.getClass().getSimpleName().equals("BucketCache");
  BucketCacheStats bucketCacheStats = null;
  BucketAllocator bucketAllocator = null;
  Bucket [] buckets = null;

  if (bucketCache) {
    bucketCacheStats = (BucketCacheStats)bc.getStats();
    bucketAllocator = ((BucketCache)bc).getAllocator();
    buckets = bucketAllocator.getBuckets();
  }

    // 307, 1
    if (cbsbf.isFull() )
    {
      // 307, 23
      jamonWriter.write("\n<p><b>Statistics below is based on sampling first ");
      // 308, 51
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(cbsbfSnapshot.getMax()), jamonWriter);
      // 308, 79
      jamonWriter.write(" blocks only</b> (hbase.ui.blockcache.by.file.max)</p> \n");
    }
    // 309, 7
    jamonWriter.write("\n<table id=\"blocks_summary\" class=\"table table-striped\">\n    <tr>\n        <th>Attribute</th>\n        <th>Value</th>\n        <th>Description</th>\n    </tr>\n    <tr>\n        <td>Implementation</td>\n        <td><a href=\"");
    // 318, 22
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bcUrl), jamonWriter);
    // 318, 33
    jamonWriter.write("\">");
    // 318, 35
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bc.getClass().getSimpleName()), jamonWriter);
    // 318, 70
    jamonWriter.write("</a></td>\n        <td>Class implementing this block cache Level</td>\n    </tr>\n");
    // 321, 1
    if (bucketCache )
    {
      // 321, 20
      jamonWriter.write("\n    <tr>\n        <td>Implementation</td>\n        <td>");
      // 324, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(((BucketCache)bc).getIoEngine()), jamonWriter);
      // 324, 50
      jamonWriter.write("</a></td>\n        <td>IOEngine</td>\n    </tr>\n");
    }
    // 327, 7
    jamonWriter.write("\n    <tr>\n        <td>Count</td>\n        <td>");
    // 330, 13
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cbsbf.getCount())), jamonWriter);
    // 330, 57
    jamonWriter.write("</td>\n        <td>Count of Blocks</td>\n    </tr>\n");
    // 333, 1
    if (!bucketCache )
    {
      // 333, 21
      jamonWriter.write("\n    <tr>\n        <td>Count</td>\n        <td>");
      // 336, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", cbsbf.getDataCount())), jamonWriter);
      // 336, 61
      jamonWriter.write("</td>\n        <td>Count of DATA Blocks</td>\n    </tr>\n");
    }
    // 339, 7
    jamonWriter.write("\n    <tr>\n        <td>Size</td>\n        <td>");
    // 342, 13
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(StringUtils.humanReadableInt(cbsbf.getSize())), jamonWriter);
    // 342, 64
    jamonWriter.write("</td>\n        <td>Size of Blocks</td>\n    </tr>\n");
    // 345, 1
    if (!bucketCache )
    {
      // 345, 21
      jamonWriter.write("\n    <tr>\n        <td>Size</td>\n        <td>");
      // 348, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(StringUtils.humanReadableInt(cbsbf.getDataSize())), jamonWriter);
      // 348, 68
      jamonWriter.write("</td>\n        <td>Size of DATA Blocks</td>\n    </tr>\n");
    }
    // 351, 7
    jamonWriter.write(" \n");
    // 352, 1
    if (evictions )
    {
      // 352, 18
      {
        // 352, 18
        __jamon_innerUnit__evictions_tmpl(jamonWriter, bc);
      }
    }
    // 352, 54
    jamonWriter.write(" \n");
    // 353, 1
    if (bucketCache )
    {
      // 353, 20
      jamonWriter.write("\n    <tr>\n        <td>Hits per Second</td>\n        <td>");
      // 356, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucketCacheStats.getIOHitsPerSecond()), jamonWriter);
      // 356, 56
      jamonWriter.write("</td>\n        <td>Block gets against this cache per second</td>\n    </tr>\n    <tr>\n        <td>Time per Hit</td>\n        <td>");
      // 361, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucketCacheStats.getIOTimePerHit()), jamonWriter);
      // 361, 53
      jamonWriter.write("</td>\n        <td>Time per cache hit</td>\n    </tr>\n");
    }
    // 364, 7
    jamonWriter.write("\n</table>\n<p>View block cache <a href=\"?format=json&bcn=");
    // 367, 47
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(name), jamonWriter);
    // 367, 57
    jamonWriter.write("\">as JSON</a> | Block cache <a href=\"?format=json&bcn=");
    // 367, 111
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(name), jamonWriter);
    // 367, 121
    jamonWriter.write("&bcv=file\">as JSON by file</a></p>\n");
    // 368, 1
    if (bucketCache )
    {
      // 368, 20
      jamonWriter.write("\n<p>BucketCache does not discern between DATA and META blocks so we do not show DATA counts (If deploy is using CombinedBlockCache, BucketCache is only DATA blocks</p>\n<h3>BucketCache Buckets</h3>\n<table class=\"table table-striped\">\n    <tr>\n        <th>Bucket Offset</th>\n        <th>Allocation Size</th>\n        <th>Free Count</th>\n        <th>Used Count</th>\n    </tr>\n");
      // 378, 1
      for (Bucket bucket: buckets )
      {
        // 378, 32
        jamonWriter.write("\n    <tr>\n        <td>");
        // 380, 13
        org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucket.getBaseOffset()), jamonWriter);
        // 380, 41
        jamonWriter.write("</td>\n        <td>");
        // 381, 13
        org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucket.getItemAllocationSize()), jamonWriter);
        // 381, 49
        jamonWriter.write("</td>\n        <td>");
        // 382, 13
        org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucket.getFreeBytes()), jamonWriter);
        // 382, 40
        jamonWriter.write("</td>\n        <td>");
        // 383, 13
        org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(bucket.getUsedBytes()), jamonWriter);
        // 383, 40
        jamonWriter.write("</td>\n    </tr>\n");
      }
      // 385, 8
      jamonWriter.write("\n</table>\n");
    }
    // 387, 7
    jamonWriter.write("\n");
    // 388, 1
    
cbsbf = null;

  }
  
  
  // 269, 1
  private void __jamon_innerUnit__bc_l(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final BlockCache bc, final String name, final boolean evictions)
    throws java.io.IOException
  {
    // 275, 1
    if (bc == null )
    {
      // 275, 19
      jamonWriter.write("\n<p>No ");
      // 276, 7
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(name), jamonWriter);
      // 276, 17
      jamonWriter.write(" deployed</p>\n");
    }
    // 277, 1
    else
    {
      // 277, 8
      jamonWriter.write("\n");
      // 278, 1
      {
        // 278, 1
        __jamon_innerUnit__block_cache(jamonWriter, bc, name, evictions);
      }
      // 278, 65
      jamonWriter.write("\n");
    }
    // 279, 7
    jamonWriter.write("\n");
  }
  
  
  // 173, 1
  private void __jamon_innerUnit__evictions_tmpl(@SuppressWarnings({"unused","hiding"}) final java.io.Writer jamonWriter, final BlockCache bc)
    throws java.io.IOException
  {
    // 177, 1
    
  AgeSnapshot ageAtEvictionSnapshot = bc.getStats().getAgeAtEvictionSnapshot();
  // Only show if non-zero mean and stddev as is the case in combinedblockcache
  double mean = ageAtEvictionSnapshot.getMean();
  double stddev = ageAtEvictionSnapshot.getStdDev();

    // 183, 5
    jamonWriter.write("<tr>\n        <td>Evicted</td>\n        <td>");
    // 185, 13
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", bc.getStats().getEvictedCount())), jamonWriter);
    // 185, 72
    jamonWriter.write("</td>\n        <td>The total number of blocks evicted</td>\n    </tr>\n    <tr>\n        <td>Evictions</td>\n        <td>");
    // 190, 13
    org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", bc.getStats().getEvictionCount())), jamonWriter);
    // 190, 73
    jamonWriter.write("</td>\n        <td>The total number of times an eviction has occurred</td>\n    </tr>\n");
    // 193, 1
    if (mean > 0 )
    {
      // 193, 17
      jamonWriter.write("\n    <tr>\n        <td>Mean</td>\n        <td>");
      // 196, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", (long)(ageAtEvictionSnapshot.getMean()/(1000000 * 1000)))), jamonWriter);
      // 196, 97
      jamonWriter.write("</td>\n        <td>Mean age of Blocks at eviction time (seconds)</td>\n    </tr>\n");
    }
    // 199, 7
    jamonWriter.write("\n");
    // 200, 1
    if (stddev > 0 )
    {
      // 200, 19
      jamonWriter.write("\n    <tr>\n        <td>StdDev</td>\n        <td>");
      // 203, 13
      org.jamon.escaping.Escaping.HTML.write(org.jamon.emit.StandardEmitter.valueOf(String.format("%,d", (long)(ageAtEvictionSnapshot.getStdDev()/1000000))), jamonWriter);
      // 203, 90
      jamonWriter.write("</td>\n        <td>Standard Deviation for age of Blocks at eviction time</td>\n    </tr>\n");
    }
    // 206, 7
    jamonWriter.write("\n");
  }
  
  
}
