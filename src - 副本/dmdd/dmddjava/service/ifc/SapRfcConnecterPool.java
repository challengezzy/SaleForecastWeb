/**********************************************************************
 *$RCSfile:SapRfcConnecterPool.java,v $  $Revision: 1.0 $  $Date:2011-6-27 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc;

import java.util.concurrent.ConcurrentHashMap;


/**
 * <li>Title: SapRfcConnecterPool.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class SapRfcConnecterPool
{
	private static SapRfcConnecterPool theInstance = null;	
	private ConcurrentHashMap<String,SapRfcConnecter> pool = new ConcurrentHashMap<String, SapRfcConnecter>();

	public synchronized static SapRfcConnecterPool getInstance() 
	{
		if (theInstance == null) 
		{
			theInstance = new SapRfcConnecterPool();
		}
		return theInstance;
	}
	
	public SapRfcConnecterPool()
	{
		
	}
	
	public SapRfcConnecter getSapRfcConnecter(String source)
	{
		if(pool.containsKey(  source ))
		{
			return pool.get( source );
		}
		return null;
	}
	
	public void addSapRfcConnecter(String source,SapRfcConnecter sapRfcConnecter)
	{
		this.pool.put( source, sapRfcConnecter );
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}

/**********************************************************************
 *$RCSfile:SapRfcConnecterPool.java,v $  $Revision: 1.0 $  $Date:2011-6-27 $
 *
 *$Log:SapRfcConnecterPool.java,v $
 *********************************************************************/