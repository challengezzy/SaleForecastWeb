package dmdd.dmddjava.service.ifc.standard;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryData;
import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryDataADR;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;

public class InterfaceStandardService 
{
 
	public InterfaceStandardService()
	{
		
	}
	
	public List<ABImInHistoryData> getInHistoryDatas(String sql) throws Exception
	{
		List<ABImInHistoryData> rst = new ArrayList<ABImInHistoryData>();
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,UNITGROUPCODE,UNITCODE,ID FROM IN_HISTORYDATA ");
		buff.append(" WHERE RESULT IS "+sql+" NULL ");
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(buff.toString());
			query.addScalar("PRODUCTCODE",new org.hibernate.type.StringType());
			query.addScalar("ORGANIZATIONCODE",new org.hibernate.type.StringType());
			query.addScalar("PERIOD",new org.hibernate.type.IntegerType());
			query.addScalar("VALUE",new org.hibernate.type.LongType());
			query.addScalar("BIZDATACODE",new org.hibernate.type.StringType());
			query.addScalar("UNITGROUPCODE",new org.hibernate.type.StringType());
			query.addScalar("UNITCODE",new org.hibernate.type.StringType());
			query.addScalar("ID",new org.hibernate.type.LongType());
			List result = query.list();
			for(Object obj:result)
			{
				Object[] objs = (Object[])obj;
				ABImInHistoryData data = new ABImInHistoryData();
				if(objs[0]!=null)
					data.setproductcode((String)objs[0]);
				if(objs[1]!=null)
					data.setorganizationcode((String)objs[1]);
				if(objs[2]!=null)
					data.setperiod((Integer)objs[2]);
				if(objs[3]!=null)
					data.setvalue((Long)objs[3]);
				if(objs[4]!=null)
				data.setbizdatacode((String)objs[4]);
				if(objs[5]!=null)
				data.setunitgroupcode((String)objs[5]);
				if(objs[6]!=null)
				data.setunitcode((String)objs[6]);
				if(objs[7]!=null)
					data.setid((Long)objs[7]);
				rst.add(data);
			}
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		
		return rst;
	}
	
	public void deleteHistoryDatas() throws Exception
	{
		deleteInterfaceStandardHistoryData("IN_HISTORYDATA","");
	}
	
	public List<ABImInHistoryData> saveHistoryData(List<ABImInHistoryData> listdatas) throws Exception
	{
		List<ABImInHistoryData> rst = new ArrayList<ABImInHistoryData>();
				
		InterfaceStandardHistoryDataMgmt mgmt = new InterfaceStandardHistoryDataMgmt(listdatas);
		mgmt.excute();
		rst = mgmt.getResult();

		//写入出错数据		
		insertInterfaceStandardHistoryData(rst);
		deleteInterfaceStandardHistoryData("IN_HISTORYDATA"," WHERE RESULT = 'OK' OR RESULT IS NULL ");
		return rst;
	}

	public void deleteInterfaceStandardHistoryData(String tableName,String _sql) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(" delete from "+tableName+_sql);
			query.executeUpdate();
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
	}
	
	public void insertInterfaceStandardHistoryData(List<ABImInHistoryData> list) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = null;
			for(ABImInHistoryData data :list)
			{
				if(data==null ||data.getresult()==null || data.getresult().equals(""))
				{
					continue;
				}
				StringBuffer buff = new StringBuffer();
//				buff.append("insert into IN_HISTORYDATA(PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,UNITGROUPCODE,UNITCODE,RESULT)");
//				buff.append("values(");
//				buff.append("'"+data.getproductcode()+"',");
//				buff.append("'"+data.getorganizationcode()+"',");
//				buff.append(""+data.getperiod()+",");
//				buff.append(""+data.getvalue()+",");
//				buff.append("'"+data.getbizdatacode()+"',");
//				buff.append("'"+data.getunitgroupcode()+"',");
//				buff.append("'"+data.getunitcode()+"',");
//				buff.append("'"+data.getresult()+"') ");
			
				buff.append(" update IN_HISTORYDATA set ");
				buff.append(" RESULT ='"+data.getresult()+"' ");
				buff.append(" where id="+data.getid());
				
				query = session.createSQLQuery(buff.toString());
				query.executeUpdate();
			}

			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
	}
	
	public List<ABImInHistoryDataADR> getInHistoryDataADRs(String sql) throws Exception
	{
		List<ABImInHistoryDataADR> rst = new ArrayList<ABImInHistoryDataADR>();
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,UNITGROUPCODE,UNITCODE,DESCRIPTION,ID FROM IN_HISTORYDATAADR ");
		buff.append(" WHERE RESULT IS "+sql+" NULL ");
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(buff.toString());
			query.addScalar("PRODUCTCODE",new org.hibernate.type.StringType());
			query.addScalar("ORGANIZATIONCODE",new org.hibernate.type.StringType());
			query.addScalar("PERIOD",new org.hibernate.type.IntegerType());
			query.addScalar("VALUE",new org.hibernate.type.LongType());
			query.addScalar("BIZDATACODE",new org.hibernate.type.StringType());
			query.addScalar("UNITGROUPCODE",new org.hibernate.type.StringType());
			query.addScalar("UNITCODE",new org.hibernate.type.StringType());
			query.addScalar("DESCRIPTION",new org.hibernate.type.StringType());
			query.addScalar("ID",new org.hibernate.type.LongType());
			List result = query.list();
			for(Object obj:result)
			{
				Object[] objs = (Object[])obj;
				ABImInHistoryDataADR data = new ABImInHistoryDataADR();
				if(objs[0]!=null)
					data.setproductcode((String)objs[0]);
				if(objs[1]!=null)
					data.setorganizationcode((String)objs[1]);
				if(objs[2]!=null)
					data.setperiod((Integer)objs[2]);
				if(objs[3]!=null)
					data.setvalue((Long)objs[3]);
				if(objs[4]!=null)
				data.setbizdatacode((String)objs[4]);
				if(objs[5]!=null)
				data.setunitgroupcode((String)objs[5]);
				if(objs[6]!=null)
				data.setunitcode((String)objs[6]);
				if(objs[7]!=null)
					data.setdescription((String)objs[7]);
				if(objs[8]!=null)
					data.setid((Long)objs[8]);
				rst.add(data);
			}
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		
		return rst;
	}

	
	public void deleteHistoryDataADR() throws Exception
	{
		deleteInterfaceStandardHistoryData("IN_HISTORYDATAADR","");
	}
	
	public List<ABImInHistoryDataADR> saveHistoryDataADR(List<ABImInHistoryDataADR> listdatas) throws Exception
	{
		List<ABImInHistoryDataADR> rst = new ArrayList<ABImInHistoryDataADR>();
				
		InterfaceStandardhistoryDataADRMgmt mgmt = new InterfaceStandardhistoryDataADRMgmt(listdatas);
		mgmt.excute();
		rst = mgmt.getResult();

		//写入出错数据		
		insertInterfaceStandardHistoryDataADR(rst);
		deleteInterfaceStandardHistoryData("IN_HISTORYDATAADR"," WHERE RESULT = 'OK' OR RESULT IS NULL ");
		return rst;
	}
	
	public void insertInterfaceStandardHistoryDataADR(List<ABImInHistoryDataADR> list) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = null;
			for(ABImInHistoryDataADR data :list)
			{
				if(data==null ||data.getresult()==null || data.getresult().equals(""))
				{
					continue;
				}
				StringBuffer buff = new StringBuffer();
//				buff.append("insert into IN_HISTORYDATAADR(PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,UNITGROUPCODE,UNITCODE,DESCRIPTION,RESULT)");
//				buff.append("values(");
//				buff.append("'"+data.getproductcode()+"',");
//				buff.append("'"+data.getorganizationcode()+"',");
//				buff.append(""+data.getperiod()+",");
//				buff.append(""+data.getvalue()+",");
//				buff.append("'"+data.getbizdatacode()+"',");
//				buff.append("'"+data.getunitgroupcode()+"',");
//				buff.append("'"+data.getunitcode()+"',");
//				buff.append("'"+data.getdescription()+"',");
//				buff.append("'"+data.getresult()+"') ");
			
				buff.append(" update IN_HISTORYDATAADR set ");
				buff.append(" RESULT ='"+data.getresult()+"' ");
				buff.append(" where id="+data.getid());
				
				query = session.createSQLQuery(buff.toString());
				query.executeUpdate();
			}

			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
	}
}