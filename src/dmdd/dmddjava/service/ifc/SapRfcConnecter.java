/**********************************************************************
 *$RCSfile:SapRfcService.java,v $  $Revision: 1.0 $  $Date:2011-6-1 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
/**
 * <li>Title: SapRfcService.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class SapRfcConnecter
{

	private String ABAP_AS_POOLED = ""; 
	private JCoDestination jCoDestination; 
	
	public SapRfcConnecter(String sapClient,String userid,String password,String language,String ServerHost,String sysnumber,String source)
	{
		Properties connectProperties = new Properties();      
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, ServerHost);      
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  sysnumber);        //系统编号      
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, sapClient);       //SAP集团         
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   userid);  //SAP用户名      
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, password);     //密码         
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "zh");        //登录语言      
		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数       
		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,"10");     //最大连接线程        
		createDataFile(source, "jcoDestination", connectProperties); 
		ABAP_AS_POOLED= source;
	}
	
	private void createDataFile( String name, String suffix, Properties properties )
	{
		File cfg = new File(name + "." + suffix );
//		if( !cfg.exists() )
//		{
			try
			{
				FileOutputStream fos = new FileOutputStream( cfg, false );
				properties.store( fos, "for tests only !" );
				fos.close();
			}
			catch( Exception e )
			{
				throw new RuntimeException( "Unable to create the destination file " + cfg.getName(), e );
			}
		//}
	}

	public  JCoDestination Connect()
	{
		JCoDestination destination = null;
		try
		{
			destination = JCoDestinationManager.getDestination( ABAP_AS_POOLED );
		}
		catch( JCoException e )
		{
			e.getCause();
		}
		return destination;
	}
		
	
	public HashMap<String,List<HashMap<String,String>>> getObjectsByFunction(String functionStr,HashMap<String,String> importhm,HashMap<String,List<String>> hmtables)
	{
		HashMap<String, List<HashMap<String, String>>> result = new HashMap<String, List<HashMap<String, String>>>();

		try
		{
			jCoDestination = Connect();  

			JCoFunction function = jCoDestination.getRepository().getFunction( functionStr);
			if( function == null )
				throw new RuntimeException( functionStr+" not found in SAP." ); 
			// 传入的参数
			for(String key:importhm.keySet())
			{
				function.getImportParameterList().setValue(key, importhm.get( key ));
			}
			System.out.println("start to load data...");
			function.execute( jCoDestination );
			System.out.println("loadinbg data commplite...");
			JCoTable table = null; 
			List<HashMap<String,String>> rst =null; 
			for(String tableName:hmtables.keySet())
			{
				table = function.getTableParameterList().getTable(tableName);
				rst=new ArrayList<HashMap<String,String>>();
			  	HashMap<String,String> units = null;
			    for (int i = 0; i < table.getNumRows();i++)
			    {
			    	units = new HashMap<String, String>();
			    	
			    	table.setRow(i); 
			    	for(String key:hmtables.get( tableName ))
			    	{
			    		units.put(key, table.getString(key));
			    	}
			    	rst.add(units);		    			    	
			    }	
			    result.put( tableName, rst );
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}

/**********************************************************************
 *$RCSfile:SapRfcService.java,v $  $Revision: 1.0 $  $Date:2011-6-1 $
 *
 *$Log:SapRfcService.java,v $
 *********************************************************************/