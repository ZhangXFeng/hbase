// Autogenerated Jamon proxy
// /Users/enis/projects/git-repos/hbase/hbase-server/src/main/jamon/org/apache/hadoop/hbase/tmpl/master/BackupMasterStatusTmpl.jamon

package org.apache.hadoop.hbase.tmpl.master;

// 23, 1
import java.util.*;
// 24, 1
import org.apache.hadoop.hbase.ServerName;
// 25, 1
import org.apache.hadoop.hbase.ClusterStatus;
// 26, 1
import org.apache.hadoop.hbase.master.HMaster;
// 27, 1
import org.apache.hadoop.hbase.zookeeper.MasterAddressTracker;

@org.jamon.annotations.Template(
  signature = "090F5CDDAD95701B0A531F343D263FA8",
  requiredArguments = {
    @org.jamon.annotations.Argument(name = "master", type = "HMaster")})
public class BackupMasterStatusTmpl
  extends org.jamon.AbstractTemplateProxy
{
  
  public BackupMasterStatusTmpl(org.jamon.TemplateManager p_manager)
  {
     super(p_manager);
  }
  
  public BackupMasterStatusTmpl()
  {
     super("/org/apache/hadoop/hbase/tmpl/master/BackupMasterStatusTmpl");
  }
  
  protected interface Intf
    extends org.jamon.AbstractTemplateProxy.Intf{
    
    void renderNoFlush(final java.io.Writer jamonWriter) throws java.io.IOException;
    
  }
  public static class ImplData
    extends org.jamon.AbstractTemplateProxy.ImplData
  {
    // 20, 1
    public void setMaster(HMaster master)
    {
      // 20, 1
      m_master = master;
    }
    public HMaster getMaster()
    {
      return m_master;
    }
    private HMaster m_master;
  }
  @Override
  protected ImplData makeImplData()
  {
    return new ImplData();
  }
  @Override @SuppressWarnings("unchecked") public ImplData getImplData()
  {
    return (ImplData) super.getImplData();
  }
  
  
  @Override
  public org.jamon.AbstractTemplateImpl constructImpl(Class<? extends org.jamon.AbstractTemplateImpl> p_class){
    try
    {
      return p_class
        .getConstructor(new Class [] { org.jamon.TemplateManager.class, ImplData.class })
        .newInstance(new Object [] { getTemplateManager(), getImplData()});
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  protected org.jamon.AbstractTemplateImpl constructImpl(){
    return new BackupMasterStatusTmplImpl(getTemplateManager(), getImplData());
  }
  public org.jamon.Renderer makeRenderer(final HMaster master)
  {
    return new org.jamon.AbstractRenderer() {
      @Override
      public void renderTo(final java.io.Writer jamonWriter)
        throws java.io.IOException
      {
        render(jamonWriter, master);
      }
    };
  }
  
  public void render(final java.io.Writer jamonWriter, final HMaster master)
    throws java.io.IOException
  {
    renderNoFlush(jamonWriter, master);
    jamonWriter.flush();
  }
  public void renderNoFlush(final java.io.Writer jamonWriter, final HMaster master)
    throws java.io.IOException
  {
    ImplData implData = getImplData();
    implData.setMaster(master);
    Intf instance = (Intf) getTemplateManager().constructImpl(this);
    instance.renderNoFlush(jamonWriter);
    reset();
  }
  
  
}