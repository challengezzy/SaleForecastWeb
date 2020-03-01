/**********************************************************************
 *$RCSfile:DmddThreadPool.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.common.system;


import org.apache.log4j.Logger;

/**
 * <li>Title: DmddThreadPool.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class DmddThreadPool
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private static DmddThreadPool theinstance = null;
	private ThreadProcessMgmt processMgmt=new ThreadProcessMgmt();
	/**
	 * 实例化-单例类
	 * @return
	 */
	public synchronized static DmddThreadPool getinstance() 
	{
		if (theinstance == null) 
		{
			theinstance = new DmddThreadPool();
			theinstance.start();
		}
		return theinstance;
	}
	
	public void start()
	{
		startThreadProcess();
	}
	
	private DmddThreadPool()
	{
		logger.info("实例化DmddPool...");
	}
	
	public void startThreadProcess()
	{
		processMgmt.start();
	}
	
	public void stopThreadProcess()
	{
		processMgmt.stopRequest();
	}
	
	public void addThreadProcessMgmt(IThreadProcess process) throws InterruptedException
	{
		processMgmt.add(process);
	}
}

/**********************************************************************
 *$RCSfile:DmddThreadPool.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:DmddThreadPool.java,v $
 *********************************************************************/