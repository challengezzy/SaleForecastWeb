/**********************************************************************
 *$RCSfile:ThreadProcessMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.common.system;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;


/**
 * <li>Title: ThreadProcessMgmt.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ThreadProcessMgmt implements Runnable 
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private int poolSize = 5;
    protected Thread internalThread;
    protected volatile boolean noStopRequested;
    protected Executor m_executor = null;
    protected LinkedBlockingQueue<IThreadProcess> queue = null;
    protected int addedCount = 0;	    
    
    public ThreadProcessMgmt() 
    {
        this(20);
    }

    public ThreadProcessMgmt(int poolSize)
    {
    	logger.info("初始化ThreadProcessMgmt 线程数量:"+poolSize+".....");
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue<IThreadProcess>();
        m_executor = this.createExecutor();
        noStopRequested = true;
    }

    public void start()
    {
        internalThread = new Thread(this);
        internalThread.start();
    	logger.info("启动ThreadProcessMgmt 成功.....");
    }

    protected Executor createExecutor() 
    {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(poolSize, poolSize, 1*10, //10 seconds
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        return threadPool;
    }

    public synchronized void run()
    {
        while (noStopRequested) 
        {
            try 
            {
                IThreadProcess object = this.take();
                consume(object);
            } 
            catch (InterruptedException x)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void consume(final IThreadProcess threadProcess) 
    {
        if (threadProcess == null) 
        {
            return;
        }
        try 
        {
            m_executor.execute(new Runnable() 
            {
                public void run()
                {
                    try 
                    {
                    	threadProcess.doStart();
                        threadProcess.doProcess(); 
                        threadProcess.doComplete();
                    } 
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void add(IThreadProcess threadProcess) throws InterruptedException 
    {
        queue.put(threadProcess);
        addedCount++;
    }

    public IThreadProcess take() throws InterruptedException 
    {
        return (IThreadProcess) queue.take();
    }

    public int getAddedCount() 
    {
        return addedCount;
    }

    public void stopRequest()
    {
        noStopRequested = false;
        internalThread.interrupt();
        ((ThreadPoolExecutor) m_executor).shutdownNow();
    }

}

/**********************************************************************
 *$RCSfile:ThreadProcessMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:ThreadProcessMgmt.java,v $
 *********************************************************************/