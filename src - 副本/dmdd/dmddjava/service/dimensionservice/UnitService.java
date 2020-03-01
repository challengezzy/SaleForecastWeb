
/**
 * 
 */

package dmdd.dmddjava.service.dimensionservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.UnitGroupBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnit;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnitGroup;

/**
 * @author liuzhen
 * 
 */
public class UnitService
{

	/**
	 * 
	 */
	public UnitService()
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

	public BUnitGroup newUnitGroup( BUnitGroup _bUnitGroup4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bUnitGroup4new == null )
		{
			Exception ex = new Exception( "The object to new is a null object" );
			throw ex;
		}
		
		BUnitGroup bUnitGroup_rst = null; 

		UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();

		UnitGroup unitGroup_new = unitGroupBDConvertor.btod( _bUnitGroup4new, true );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup( session );
			daoUnitGroup.save( unitGroup_new );
			
			if( unitGroup_new.getUnits() != null && !(unitGroup_new.getUnits().isEmpty()) )
			{
				DaoUnit daoUnit = new DaoUnit( session );
				Iterator<Unit> itr_units = unitGroup_new.getUnits().iterator();
				while( itr_units.hasNext() )
				{
					daoUnit.save( itr_units.next() );
				}
			}
			trsa.commit();
			
			bUnitGroup_rst = unitGroupBDConvertor.dtob( unitGroup_new, true );
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

		return bUnitGroup_rst;

	}

	/**
	 * 
	 * @param _bUnitGroup4upd
	 * @return
	 * @throws Exception
	 */
	public BUnitGroup updUnitGroup( BUnitGroup _bUnitGroup4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bUnitGroup4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}
		
		String strKey4unit = null;
		//	数据库中units 情况	begin
		HashMap<String, Unit> hmap_Unit_inDB = new HashMap<String, Unit>();
		
		Session session_query = HibernateSessionFactory.getSession();
		Transaction trsa_query = null;
		try
		{
			trsa_query = session_query.beginTransaction();
			DaoUnitGroup daoUnitGroup_query = new DaoUnitGroup( session_query );
			UnitGroup unitGroup_InDB = daoUnitGroup_query.getUnitGroupById( _bUnitGroup4upd.getId() );
			if( unitGroup_InDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
			
			if( unitGroup_InDB.getUnits() != null && !(unitGroup_InDB.getUnits().isEmpty()) )
			{
				Iterator<Unit> itr_Units_inDB = unitGroup_InDB.getUnits().iterator();
				while( itr_Units_inDB.hasNext() )
				{
					Unit unit = itr_Units_inDB.next();
					strKey4unit = "" + unit.getId();
					
					hmap_Unit_inDB.put( strKey4unit, unit );
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
		//	数据库中units 情况	end		
		
		
		UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
		UnitGroup unitGroup_upd = unitGroupBDConvertor.btod( _bUnitGroup4upd, true );
		
		//	参数中units 情况	begin
		HashMap<String, Unit> hmap_Unit_param = new HashMap<String, Unit>();

		if( unitGroup_upd.getUnits() != null && !(unitGroup_upd.getUnits().isEmpty()) )
		{
			Iterator<Unit> itr_Units_param = unitGroup_upd.getUnits().iterator();
			while( itr_Units_param.hasNext() )
			{
				Unit unit = itr_Units_param.next();
				strKey4unit = "" + unit.getId();
				
				hmap_Unit_param.put( strKey4unit, unit );
			}			
		}		
		//	参数中units 情况	end
		
		//	比较param和inDB获得 Unit 增删改情况 	begin
		List<Unit> toDelUnitList = new ArrayList<Unit>();
		List<Unit> toAddUnitList = new ArrayList<Unit>();
		List<Unit> toUpdUnitList = new ArrayList<Unit>();
		
		//	param - inDB = add	begin
		if( unitGroup_upd.getUnits() != null && !(unitGroup_upd.getUnits().isEmpty()) )
		{
			Iterator<Unit> itr_Units_param = unitGroup_upd.getUnits().iterator();
			while( itr_Units_param.hasNext() )
			{
				Unit unit = itr_Units_param.next();
				strKey4unit = "" + unit.getId();
				
				if( hmap_Unit_inDB.get( strKey4unit ) == null )
				{	
					toAddUnitList.add( unit );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_Unit_inDB.values() != null && !(hmap_Unit_inDB.values().isEmpty()) )
		{
			Iterator<Unit> itr_Units_inDB = hmap_Unit_inDB.values().iterator();
			while( itr_Units_inDB.hasNext() )
			{
				Unit unit = itr_Units_inDB.next();
				strKey4unit = "" + unit.getId();
				
				if( hmap_Unit_param.get( strKey4unit ) == null )
				{
					toDelUnitList.add( unit );
				}
				else
				{
					Unit unit_param = hmap_Unit_param.get( strKey4unit );
					toUpdUnitList.add( unit_param );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 Unit 增删改情况 	end
		
		//	持久化到数据库 	begin					
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;

		try
		{
			trsa = session.beginTransaction();
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup( session );
			daoUnitGroup.update( unitGroup_upd );

			DaoUnit daoUnit = new DaoUnit( session );
			for( int i = 0; i < toDelUnitList.size(); i++ )
			{
				daoUnit.delete( toDelUnitList.get( i ) );
			}
			for( int i = 0; i < toUpdUnitList.size(); i++ )
			{
				daoUnit.update( toUpdUnitList.get( i ) );
			}
			for( int i = 0; i < toAddUnitList.size(); i++ )
			{
				daoUnit.save( toAddUnitList.get( i ) );
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
		//	持久化到数据库 	end

		BUnitGroup bUnitGroup_rst = this.getUnitGroupById( _bUnitGroup4upd.getId(), true );
		return bUnitGroup_rst;
	}


	/**
	 * 奇怪，debug sql中 为什么要update一下呢
	 * @param _bUnitGroup4del
	 * @return
	 * @throws Exception
	 */
	public boolean delUnitGroup( BUnitGroup _bUnitGroup4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bUnitGroup4del == null )
		{
			Exception ex = new Exception( "The object to delete is null! Do nothing!" );
			throw ex;
		}
		
		UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
		UnitGroup unitGroup_del = (UnitGroup)unitGroupBDConvertor.btod( _bUnitGroup4del );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup( session );
			
			// unit 对 unitGroup 是级联删除的，这里只删除unitGroup就可以了
			daoUnitGroup.delete( unitGroup_del );
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
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getUnitGroupsStat( String _sqlRestriction ) throws Exception
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
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup( session );
			rst = daoUnitGroup.getUnitGroupsStat( _sqlRestriction );			
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
	 * @param _blWithUnits
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public List<BUnitGroup> getUnitGroups( String _sqlRestriction, boolean _blWithUnits, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BUnitGroup> rstList = new ArrayList<BUnitGroup>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup( session );
			List<UnitGroup> listUnitGroup_inDB = daoUnitGroup.getUnitGroups( _sqlRestriction, _pageIndex, _pageSize  );

			if( listUnitGroup_inDB != null && listUnitGroup_inDB.iterator() != null )
			{
				UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
				for( int i=0; i<listUnitGroup_inDB.size(); i++ )
				{
					rstList.add( unitGroupBDConvertor.dtob( listUnitGroup_inDB.get( i ), _blWithUnits ) );
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

	private BUnitGroup getUnitGroupById( Long _id, boolean _blWithUnits ) throws Exception
	{
		BUnitGroup bUnitGroup = null;
		Session querySession = HibernateSessionFactory.getSession();
		Transaction queryTrsa = null;
		try
		{
			queryTrsa = querySession.beginTransaction();
			
			DaoUnitGroup queryDaoUnitGroup = new DaoUnitGroup( querySession );
			UnitGroup unitGroup_inDB = queryDaoUnitGroup.getUnitGroupById( _id );

			if( unitGroup_inDB != null )
			{
				UnitGroupBDConvertor unitGroupBDConvertor = new UnitGroupBDConvertor();
				bUnitGroup = unitGroupBDConvertor.dtob( unitGroup_inDB, _blWithUnits );				
			}

			queryTrsa.commit();			
		}
		catch( Exception ex )
		{
			if( queryTrsa != null )
			{
				queryTrsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			querySession.close();
		}

		return bUnitGroup;

	}

}

/**************************************************************************
 * 
 * $RCSfile: UnitService.java,v $ $Revision: 1.3 $ $Date: 2010/07/15 15:01:36 $
 * 
 * $Log: UnitService.java,v $
 * Revision 1.3  2010/07/15 15:01:36  liuzhen
 * 2010.07.15 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2010/07/15 13:27:38  liuzhen
 * 2010.07.15 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/04 07:26:53  liuzhen
 * 2010.07.04 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 
 * 
 * 
 ***************************************************************************/
