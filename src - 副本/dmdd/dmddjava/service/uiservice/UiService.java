/**
 * 
 */
package dmdd.dmddjava.service.uiservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.UiPopbScopeBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScope;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScope;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScopeProOrg;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUiPopbScope;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUiPopbScopeProOrg;
import dmdd.dmddjava.dm.UiPopbScopeDM;

/**
 * @author liuzhen
 *
 */
public class UiService
{
	private Logger logger = CoolLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	/**
	 * 
	 */
	public UiService()
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
	
	
	
	// UiPopbScope    begin
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getUiPopbScopesStat( String _sqlRestriction ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		int rst = 0;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUiPopbScope daoUiPopbScope = new DaoUiPopbScope( session );
			rst = daoUiPopbScope.getUiPopbScopesStat( _sqlRestriction );			
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

	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public List<BUiPopbScope> getUiPopbScopes( String _sqlRestriction, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BUiPopbScope> rstList = new ArrayList<BUiPopbScope>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUiPopbScope daoUiPopbScope = new DaoUiPopbScope( session );
			List<UiPopbScope> listUiPopbScope_inDB = daoUiPopbScope.getUiPopbScopes( _sqlRestriction, _pageIndex, _pageSize  );

			if( listUiPopbScope_inDB != null && listUiPopbScope_inDB.iterator() != null )
			{
				UiPopbScopeBDConvertor uiPopbScopeBDConvertor = new UiPopbScopeBDConvertor();
				for( int i=0; i<listUiPopbScope_inDB.size(); i++ )
				{
					BUiPopbScope bUiPopbScope = (BUiPopbScope) uiPopbScopeBDConvertor.dtob( listUiPopbScope_inDB.get( i ) );
					rstList.add( bUiPopbScope );
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

		return rstList;
	}
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public BUiPopbScope getUiPopbScopeBySql( String _sqlRestriction) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BUiPopbScope bUiPopbScope = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUiPopbScope daoUiPopbScope = new DaoUiPopbScope( session );
			UiPopbScope uiPopbScope_inDB = daoUiPopbScope.getUiPopbScope( _sqlRestriction);	
			if(uiPopbScope_inDB==null)
				return null;
			UiPopbScopeBDConvertor uiPopbScopeBDConvertor = new UiPopbScopeBDConvertor();
			bUiPopbScope = (BUiPopbScope) uiPopbScopeBDConvertor.dtob(uiPopbScope_inDB );

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

		return bUiPopbScope;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BUiPopbScope getUiPopbScope( Long id ,Long operatoruserId) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		UiPopbScopeDM dm = new UiPopbScopeDM();
		BUiPopbScope bUiPopbScope = dm.getUiPopbScope(id, operatoruserId);
//		Session session = HibernateSessionFactory.getSession();
//		Transaction trsa = null;
//		try
//		{
//			trsa = session.beginTransaction();
//			DaoUiPopbScope daoUiPopbScope = new DaoUiPopbScope( session );
//			UiPopbScope UiPopbScope_inDB = daoUiPopbScope.getUiPopbScopeById(id);
//			UiPopbScopeBDConvertor uiPopbScopeBDConvertor = new UiPopbScopeBDConvertor();
//			bUiPopbScope = (BUiPopbScope) uiPopbScopeBDConvertor.dtob( UiPopbScope_inDB);
//
//			trsa.commit();
//		}
//		catch( Exception ex )
//		{
//			if( trsa != null )
//			{
//				trsa.rollback();
//			}
//			ex.printStackTrace();
//			throw ex;
//		}
//		finally
//		{
//			session.close();
//		}

		return bUiPopbScope;
	}
	
	/**
	 * 
	 * @param _bUiPopbScope4new
	 * @return
	 * @throws Exception
	 */
	public BUiPopbScope newUiPopbScope( BUiPopbScope _bUiPopbScope4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务
		ServerEnvironment.getInstance().checkSystemStatus();
		
		if( _bUiPopbScope4new == null )
		{
			return null;
		}
		
		BUiPopbScope rstUiPopbScope = null;

		UiPopbScopeBDConvertor uiPopbScopeBDConvertor = new UiPopbScopeBDConvertor();
		UiPopbScope uiPopbScope_new = (UiPopbScope)uiPopbScopeBDConvertor.btod( _bUiPopbScope4new );	

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
				
			DaoUiPopbScope daoUiPopbScope = new DaoUiPopbScope(session);
			daoUiPopbScope.save( uiPopbScope_new );
			
			if( uiPopbScope_new.getUiPopbScopeProOrgs() != null && !(uiPopbScope_new.getUiPopbScopeProOrgs().isEmpty()) )
			{
				DaoUiPopbScopeProOrg daoUiPopbScopeProOrg = new DaoUiPopbScopeProOrg( session );
				Iterator<UiPopbScopeProOrg> itr_UiPopbScopeProOrgs = uiPopbScope_new.getUiPopbScopeProOrgs().iterator();
				while( itr_UiPopbScopeProOrgs.hasNext() )
				{
					daoUiPopbScopeProOrg.save( itr_UiPopbScopeProOrgs.next() );
				}
			}

			if( uiPopbScope_new.getUiPopbScopeBizDatas() != null && !(uiPopbScope_new.getUiPopbScopeBizDatas().isEmpty()) )
			{
				DaoUiPopbScopeBizData daoUiPopbScopeBizData = new DaoUiPopbScopeBizData( session );
				Iterator<UiPopbScopeBizData> itr_UiPopbScopeBizDatas = uiPopbScope_new.getUiPopbScopeBizDatas().iterator();
				while( itr_UiPopbScopeBizDatas.hasNext() )
				{
					daoUiPopbScopeBizData.save( itr_UiPopbScopeBizDatas.next() );
				}
			}
			
			trsa.commit();
			
			rstUiPopbScope = (BUiPopbScope)uiPopbScopeBDConvertor.dtob( uiPopbScope_new );

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

		return rstUiPopbScope;
	}	
	
	/**
	 * 
	 * @param _bUiPopbScope4del
	 * @return
	 * @throws Exception
	 */
	public boolean delUiPopbScope( BUiPopbScope _bUiPopbScope4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bUiPopbScope4del == null )
		{
			return false;
		}

		logger.info("开始删除常用条件");
		
		//从hibernate和从dmo中查出的数据格式不一样,有对象的从对象中查外键ID
		String oprUserId = _bUiPopbScope4del.getOperatorUserId()+"";
		if(_bUiPopbScope4del.getOperatorUser() != null){
			oprUserId = _bUiPopbScope4del.getOperatorUser().getId() +"";
		}
		
		String selIdSql = "( select id from uipopbscope where uicode='"+_bUiPopbScope4del.getUiCode()
					+"' and code='"+_bUiPopbScope4del.getCode()+"' and operatoruserid="+ oprUserId + ")";
		
		List<String> list_sqls = new ArrayList<String>();
		list_sqls.add("delete from uipopbscope_bizdata where uipopbscopeid= "+selIdSql);
		list_sqls.add("delete from uipopbscope_proorg where uipopbscopeid= "+selIdSql);
		list_sqls.add("delete from uipopbscope where id= "+selIdSql);
		try
		{
			dmo.executeBatchByDS(DMOConst.DS_DEFAULT, list_sqls);
			dmo.commit(DMOConst.DS_DEFAULT);
		}
		catch(Exception ex)
		{
			logger.error("删除常用条件失败:"+ex);
			dmo.rollback(DMOConst.DS_DEFAULT);
		}
		finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		logger.error("删除常用条件完成");
		return true;
	}	
	
	
	public BUiPopbScope upUiPopbScope( BUiPopbScope _bUiPopbScope4del , BUiPopbScope _bUiPopbScope4new )throws Exception
	{
		if(!delUiPopbScope(_bUiPopbScope4del))
		{
			return null;
		}
		
		BUiPopbScope bUiPopbScope = newUiPopbScope(_bUiPopbScope4new);
		return bUiPopbScope;
	}

}
