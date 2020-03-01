/**
 * 
 */

package dmdd.dmddjava.service.reportservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.util.DBUtil;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.bdconvertor.BreakDownRuleBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRule;
import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleFinancialDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRule;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleFinancialDefItem;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBreakDownRule;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBreakDownRuleFinancilDefItem;


/**
 * @author liuzhen
 * 
 */
public class ReportService
{

	/**
	 * 
	 */
	public ReportService()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	public int getBreakDownRuleStat( String _sqlRestriction ) throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		int rst = 0;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBreakDownRule dao = new DaoBreakDownRule( session );
			rst = dao.getBreakDownRule( _sqlRestriction );			
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
			session.close();
		}

		return rst;
	}
	
	public List<BBreakDownRule> getBreakDownRules(String _sqlRestriction,  int _pageIndex, int _pageSize) throws Exception
	{
		List<BBreakDownRule> list = new ArrayList<BBreakDownRule>();
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBreakDownRule dao = new DaoBreakDownRule( session );
			List<BreakDownRule> list_inDB = dao.getBreakDownRules( _sqlRestriction, _pageIndex, _pageSize );

			if( list_inDB != null && !(list_inDB.isEmpty()) )
			{
				BreakDownRuleBDConvertor bDConvertor = new BreakDownRuleBDConvertor();
				for( int i=0; i<list_inDB.size(); i++ )
				{
					list.add( bDConvertor.dtob( list_inDB.get( i ), true ) );
				}
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
			session.close();
		}

		return list;
	}
	
	public BBreakDownRule newBreakDownRule(BBreakDownRule _bBreakDownRule) throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		if( _bBreakDownRule == null )
		{
			Exception ex = new Exception( "The object to new is a null object" );
			throw ex;
		} 
		
		
		BBreakDownRule bBreakDownRule_rst = null;

		BreakDownRuleBDConvertor breakDownRuleBDConvertor = new BreakDownRuleBDConvertor();

		BreakDownRule breakDownRule_new = breakDownRuleBDConvertor.btod( _bBreakDownRule, true );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBreakDownRule daoBreakDownRule = new DaoBreakDownRule( session );
			daoBreakDownRule.save( breakDownRule_new );
			
			if(breakDownRule_new.getType()==1 || breakDownRule_new.getType()==2)
			{
				DaoBreakDownRuleDefItem daoBizDataDefItem = new DaoBreakDownRuleDefItem( session );
				for(BreakDownRuleDefItem breakDownRuleDefItem:breakDownRule_new.getBreakDownRuleDefItems())
				{
					daoBizDataDefItem.save( breakDownRuleDefItem);
				}
			}else if(breakDownRule_new.getType()==3)
			{
				DaoBreakDownRuleFinancilDefItem daoBizDataDefItem = new DaoBreakDownRuleFinancilDefItem( session );
				for(BreakDownRuleFinancialDefItem breakDownRuleDefItem:breakDownRule_new.getBreakDownRuleFinancialDefItems())
				{
					daoBizDataDefItem.save( breakDownRuleDefItem);
				}
			}
			
			trsa.commit();
			
			bBreakDownRule_rst = breakDownRuleBDConvertor.dtob( breakDownRule_new, true );
			
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
			session.close();
		}

		return bBreakDownRule_rst;
	}
	
	public boolean updateBreakDownRule(BBreakDownRule _bBreakDownRule) throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bBreakDownRule == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}
	
		HashMap<Integer,BreakDownRuleDefItem> hm_inDB = new HashMap<Integer, BreakDownRuleDefItem>();
		HashMap<Integer,BreakDownRuleDefItem> hm_update = new HashMap<Integer, BreakDownRuleDefItem>();
		
		Session session_query = HibernateSessionFactory.getSession();
		Transaction trsa_query = null;
		try
		{
			trsa_query = session_query.beginTransaction();
			DaoBreakDownRule dao_breakDownRule = new DaoBreakDownRule( session_query );
			BreakDownRule breakDownRule_InDB = dao_breakDownRule.getBreakDownRuleById( _bBreakDownRule.getId() );
			if( breakDownRule_InDB == null )
			{
				Exception ex_unFound = new Exception( "The object is not in Database! Can not be update!" );
				throw ex_unFound;
			}
			
			for(BreakDownRuleDefItem bizDataDefItem :breakDownRule_InDB.getBreakDownRuleDefItems())
			{
				hm_inDB.put(bizDataDefItem.getPeriod(), bizDataDefItem);
			}	
			
			BreakDownRuleBDConvertor bizDataBDConvertor = new BreakDownRuleBDConvertor();
			BreakDownRule breakDownRule_upd = bizDataBDConvertor.btod( _bBreakDownRule, true );	
			
			
			breakDownRule_InDB.copyShallow(breakDownRule_upd);
			dao_breakDownRule.update(breakDownRule_InDB);
			
			if(breakDownRule_upd.getType()==1 || breakDownRule_upd.getType()==2)
			{
				for(BreakDownRuleDefItem bizDataDefItem :breakDownRule_upd.getBreakDownRuleDefItems())
				{
					hm_update.put(bizDataDefItem.getPeriod(), bizDataDefItem);
				}
				DaoBreakDownRuleDefItem dao_BreakDownRuleDefItem = new DaoBreakDownRuleDefItem(session_query);
				for(int period:hm_update.keySet())
				{
					if(hm_inDB.containsKey(period))
					{
						
						BreakDownRuleDefItem item_db = hm_inDB.get(period);
						BreakDownRuleDefItem item_update = hm_update.get(period);
						item_db.copyShallow(item_update);
						dao_BreakDownRuleDefItem.update(item_db);
					}
					else
					{
						BreakDownRuleDefItem item_save = new BreakDownRuleDefItem();
						BreakDownRuleDefItem item_update = hm_update.get(period);
						item_save.copyShallow(item_update);
						item_save.setBreakDownRule(breakDownRule_InDB);
						dao_BreakDownRuleDefItem.save(item_save);
					}
				}
				for(int period:hm_inDB.keySet())
				{
					if(!hm_update.containsKey(period))
					{
						dao_BreakDownRuleDefItem.delete(hm_inDB.get(period));
					}
				}
			}
			else if(breakDownRule_upd.getType()==3)
			{
				DaoBreakDownRuleDefItem dao_BreakDownRuleDefItem = new DaoBreakDownRuleDefItem(session_query);
				for(BreakDownRuleFinancialDefItem breakDownRuleDefItem:breakDownRule_InDB.getBreakDownRuleFinancialDefItems())
				{
					dao_BreakDownRuleDefItem.delete(breakDownRuleDefItem);
				}
				for(BreakDownRuleFinancialDefItem breakDownRuleDefItem:breakDownRule_upd.getBreakDownRuleFinancialDefItems())
				{
					dao_BreakDownRuleDefItem.save(breakDownRuleDefItem);
				}
			}
			trsa_query.commit();
		}
		catch( Exception ex )
		{
			if( trsa_query != null )
			{
				trsa_query.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session_query.close();
		}
		//	数据库中bizDataDefItems 情况	end	
		return true;
	}
	
	
	public boolean delBreakDownRule(BBreakDownRule _bBreakDownRule) throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBreakDownRule == null )
		{
			Exception ex = new Exception( "The object to delete is null! Do nothing!" );
			throw ex;
		}

		BreakDownRuleBDConvertor bd = new BreakDownRuleBDConvertor();
		BreakDownRule breakDownRule_del = (BreakDownRule)bd.btod( _bBreakDownRule,true );// 基本信息
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBreakDownRuleDefItem dao_item = new DaoBreakDownRuleDefItem(session);
			if(breakDownRule_del.getBreakDownRuleDefItems()!=null && !breakDownRule_del.getBreakDownRuleDefItems().isEmpty())
			{	
				for(BreakDownRuleDefItem item:breakDownRule_del.getBreakDownRuleDefItems())
				{
					dao_item.delete(item);
				}
			}
			DaoBreakDownRuleFinancilDefItem dao_1= new DaoBreakDownRuleFinancilDefItem(session);
			if(breakDownRule_del.getBreakDownRuleFinancialDefItems()!=null && !breakDownRule_del.getBreakDownRuleFinancialDefItems().isEmpty())
			{
				for(BreakDownRuleFinancialDefItem item:breakDownRule_del.getBreakDownRuleFinancialDefItems())
				{
					dao_1.delete(item);
				}
			}
			DaoBreakDownRule dao = new DaoBreakDownRule( session );
			dao.delete( breakDownRule_del );
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
			session.close();
		}
		return true;
	}
	
	/**
	 * 将分解好的数据存进数据库 for 自然月
	 * @param listdata
	 * @param listRule
	 * @return
	 */
	public boolean saveBreakDwonData4Normal(int beginPeriod,int endPeriod,List<ABUiRowData> listdata, BBreakDownRule  breakdownrule) throws Exception
	{
		//第一步，删除已有
		deleteData("IN_BREAKDOWN_NORMAL","");
		
		int length_= listdata.size();
		int diff = UtilPeriod.getPeriodDifference(beginPeriod, endPeriod);
		int period;
		int weekNum;
		BBreakDownRuleDefItem rule = null;
		ABUiRowData uiRowData;
		
		HashMap<Integer,BBreakDownRuleDefItem> hm = new HashMap<Integer, BBreakDownRuleDefItem>();
		for(BBreakDownRuleDefItem item:breakdownrule.getBreakDownRuleDefItems())
		{
			hm.put(item.getPeriod(), item);
		}
		
		List<String> all_day = new ArrayList<String>();
		if(breakdownrule.getType()==1)//工作周
		{
			all_day = UtilPeriod.buildWork(beginPeriod, diff);
		}
		else if(breakdownrule.getType()==2)//日历周
		{
			all_day = UtilPeriod.buildCalendar(beginPeriod, diff);
		}
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = null;
			int index = 0;
			for(int m= 0;m<length_;m++)
			{
				index = 0;
				uiRowData = listdata.get(m);
				for(int i=0;i<=diff;i++)
				{
					period = UtilPeriod.getPeriod(beginPeriod, i);
					rule = hm.get(period);
					weekNum = rule.getNum_week();
					int j = 1;
	
					if(rule.getIsConnected() == BizConst.GLOBAL_YESNO_YES && index>0)
					{  
						j=2;
					}
					for(;j<=weekNum  ;j++)
					{
						StringBuffer buff = new StringBuffer();
						buff.append("insert into IN_BREAKDOWN_NORMAL("+DBUtil.getInsertId()+"PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,WEEKCODE,\"DATE\" )");
						buff.append("values("+ DBUtil.getSeqValue("S_IN_BREAKDOWN_NORMAL"));
						buff.append("'"+uiRowData.getProduct().getCode()+"',");
						buff.append("'"+uiRowData.getOrganization().getCode()+"',");
						buff.append(""+rule.getPeriod()+",");
						buff.append(""+uiRowData.pubFun4getPeriodDataValue(index)+",");
						buff.append("'"+uiRowData.getBizData().getCode()+"',");
						buff.append("'"+all_day.get(index)+"',");
						buff.append(all_day.get(index).replace("-", "")+")");
						
						query = session.createSQLQuery(buff.toString());
						query.executeUpdate();			

						index++;
					}
					
					
				}
			}
			
//			
//			
//			
//			for(int i=0;i<length;i++)
//			{
//				rule = listRule.get(i);
//				
//				for(int j = 0;j<length_;j++)
//				{
//					uiRowData = listdata.get(j);
//					StringBuffer buff = new StringBuffer();
//					buff.append("insert into IN_BREAKDOWN_NORMAL(PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,WEEKCODE)");
//					buff.append("values(");
//					buff.append("'"+uiRowData.getProduct().getCode()+"',");
//					buff.append("'"+uiRowData.getOrganization().getCode()+"',");
//					buff.append(""+rule.getPeriod()+",");
//					buff.append(""+uiRowData.pubFun4getPeriodDataValue(i)+",");
//					buff.append("'"+uiRowData.getBizData().getCode()+"',");
//					buff.append("'"+uiRowData.get+"')");
//					
//					query = session.createSQLQuery(buff.toString());
//					query.executeUpdate();					
//				}
//				
//			}
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
		return true;
	}
	
	
	/**
	 * 将分解好的数据存进数据库
	 * @param listdata
	 * @param listRule
	 * @return
	 */
	public boolean saveBreakDwonData(List<ABUiRowData> listdata,List<BBreakDownRuleFinancialDefItem> listRule) throws Exception
	{
		//第一步，删除已有
		deleteData("IN_BREAKDOWN","");
		
		int length = listRule.size();
		int length_= listdata.size();
		BBreakDownRuleFinancialDefItem rule = null;
		ABUiRowData uiRowData;
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = null;
			
			for(int i=0;i<length;i++)
			{
				rule = listRule.get(i);
				
				for(int j = 0;j<length_;j++)
				{
					uiRowData = listdata.get(j);
					StringBuffer buff = new StringBuffer();
					buff.append("insert into IN_BREAKDOWN("+DBUtil.getInsertId()+"VERSION,PRODUCTCODE,ORGANIZATIONCODE,PERIOD,VALUE,BIZDATACODE,WEEKCODE,\"DATE\")");
					buff.append("values(" + DBUtil.getSeqValue("S_IN_BREAKDOWN"));
					buff.append("'1',");
					buff.append("'"+uiRowData.getProduct().getCode()+"',");
					buff.append("'"+uiRowData.getOrganization().getCode()+"',");
					buff.append(""+rule.getPeriod()+",");
					buff.append(""+uiRowData.pubFun4getPeriodDataValue(i)+",");
					buff.append("'"+uiRowData.getBizData().getCode()+"',");
					buff.append("'"+rule.getWeekCode()+"',");
					buff.append(""+rule.getBeginDate()+")");
					
					query = session.createSQLQuery(buff.toString());
					query.executeUpdate();					
				}
				
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
		return true;
	}
	
	public void deleteData(String tableName,String _sql) throws Exception
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
}
