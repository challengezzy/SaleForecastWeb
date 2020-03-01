/**
 * 
 */

package dmdd.dmddjava.service.bulletinservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.BulletinBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBulletin;
import dmdd.dmddjava.dataaccess.dataobject.Bulletin;
import dmdd.dmddjava.dataaccess.dataobject.BulletinOperatorUser;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBulletin;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBulletinOperatorUser;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;

/**
 * @author liuzhen
 * 
 */
public class BulletinService
{

	/**
	 * 
	 */
	public BulletinService()
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
	

	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getBulletinsStat( String _sqlRestriction ) throws Exception
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
			DaoBulletin daoBulletin = new DaoBulletin( session );
			rst = daoBulletin.getBulletinsStat( _sqlRestriction );			
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
	 * @param _blWithBulletinOperatorUsers
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public List<BBulletin> getBulletins( String _sqlRestriction, boolean _blWithBulletinOperatorUsers, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		List<BBulletin> rstList = new ArrayList<BBulletin>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBulletin daoBulletin = new DaoBulletin( session );
			List<Bulletin> listBulletin_inDB = daoBulletin.getBulletins( _sqlRestriction, _pageIndex, _pageSize  );

			if( listBulletin_inDB != null && listBulletin_inDB.iterator() != null )
			{
				BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
				for( int i=0; i<listBulletin_inDB.size(); i++ )
				{
					rstList.add( bulletinBDConvertor.dtob( listBulletin_inDB.get( i ), _blWithBulletinOperatorUsers ) );
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
	 * 
	 * @param _bulletinId
	 * @param _blWithBulletinOperatorUsers
	 * @return
	 * @throws Exception
	 */
	public BBulletin getBulletinById( Long _bulletinId, boolean _blWithBulletinOperatorUsers ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		BBulletin bBulletinRst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBulletin daoBulletin = new DaoBulletin( session );
			Bulletin bulletinInDB = daoBulletin.getBulletinById( _bulletinId );
			trsa.commit();

			if( bulletinInDB != null )
			{
				BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
				bBulletinRst = bulletinBDConvertor.dtob( bulletinInDB, _blWithBulletinOperatorUsers );
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
			session.close();
		}

		return bBulletinRst;
	}


	/**
	 * 
	 * @param _bBulletin4save
	 * @return
	 * @throws Exception
	 */
	public BBulletin newBulletin( BBulletin _bBulletin4save ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBulletin4save == null )
		{
			throw new Exception( "The object to save is null" );
		}

		BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
		Bulletin bulletin_new = bulletinBDConvertor.btod( _bBulletin4save, true );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			bulletin_new.setCreateTime( currentTime );
			bulletin_new.setModifyTime( currentTime );

			// 基本信息 保存
			DaoBulletin daoBulletin = new DaoBulletin( session );
			daoBulletin.save( bulletin_new );

			// bulletinOperatorUsers 保存
			if( bulletin_new.getBulletinOperatorUsers() != null && !(bulletin_new.getBulletinOperatorUsers().isEmpty()) )
			{
				DaoBulletinOperatorUser daoBulletinOperatorUser = new DaoBulletinOperatorUser( session );
				Iterator<BulletinOperatorUser> itr_bulletinOperatorUsers = bulletin_new.getBulletinOperatorUsers().iterator();
				while( itr_bulletinOperatorUsers.hasNext() )
				{
					daoBulletinOperatorUser.save( itr_bulletinOperatorUsers.next() );
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

		BBulletin bBulletin_Rst = this.getBulletinById( bulletin_new.getId(), true );
		return bBulletin_Rst;

	}

	
	/**
	 * 
	 * @param _bBulletin4save
	 * @return
	 * @throws Exception
	 */
	public BBulletin updBulletin( BBulletin _bBulletin4save ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBulletin4save == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}

		BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
		Bulletin bulletin_upd = bulletinBDConvertor.btod( _bBulletin4save, false );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			bulletin_upd.setModifyTime( currentTime );

			// 基本信息 保存
			DaoBulletin daoBulletin = new DaoBulletin( session );
			daoBulletin.update( bulletin_upd );

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

		BBulletin bBulletin_Rst = this.getBulletinById( _bBulletin4save.getId(), true );
		return bBulletin_Rst;

	}


	/**
	 * 
	 * @param _bBulletin4del
	 * @return
	 * @throws Exception
	 */
	public boolean delBulletin( BBulletin _bBulletin4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBulletin4del == null )
		{
			Exception ex = new Exception( "The object to delete is null! Do nothing!" );
			throw ex;
		}

		BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
		Bulletin bulletin_del = bulletinBDConvertor.btod( _bBulletin4del, false );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBulletin daoOperatorUser = new DaoBulletin( session );
			//	数据库级联删除 BulletinOperatorUsers
			daoOperatorUser.delete( bulletin_del );
			
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
	 * 
	 * @param _bBulletin4upd
	 * @return
	 * @throws Exception
	 */
	public BBulletin updBulletinIsPublish( BBulletin _bBulletin4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBulletin4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}

		BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
		Bulletin bulletin_upd = bulletinBDConvertor.btod( _bBulletin4upd, false );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoSystem daoSystem = new DaoSystem( session );

			// 基本信息 保存
			DaoBulletin daoBulletin = new DaoBulletin( session );
			if( bulletin_upd.getIsPublish() == BizConst.GLOBAL_YESNO_YES )
			{
				bulletin_upd.setPublishTime( daoSystem.getSysTimeAsTimeStamp() );
			}
			daoBulletin.update( bulletin_upd );

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

		BBulletin bBulletin_Rst = this.getBulletinById( _bBulletin4upd.getId(), true );
		return bBulletin_Rst;
	}


	/**
	 * 
	 * @param _bBulletin4upd
	 * @return
	 * @throws Exception
	 */
	public BBulletin updBulletinPublishList( BBulletin _bBulletin4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( _bBulletin4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}

		String strKey4operatorUserId = null;
		//	数据库中BulletinOperatorUser情况	begin
		HashMap<String, BulletinOperatorUser> hmap_BulletinOperatorUser_inDB = new HashMap<String, BulletinOperatorUser>();
		Session session_query = HibernateSessionFactory.getSession();
		Transaction trsa_query = null;
		try
		{
			trsa_query = session_query.beginTransaction();
			DaoBulletin daoBulletin_query = new DaoBulletin( session_query );
			Bulletin bulletin_InDB = daoBulletin_query.getBulletinById( _bBulletin4upd.getId() );
			if( bulletin_InDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
			
			if( bulletin_InDB.getBulletinOperatorUsers() != null && !(bulletin_InDB.getBulletinOperatorUsers().isEmpty()) )
			{
				Iterator<BulletinOperatorUser> itr_BulletinOperatorUsers_inDB = bulletin_InDB.getBulletinOperatorUsers().iterator();
				while( itr_BulletinOperatorUsers_inDB.hasNext() )
				{
					BulletinOperatorUser bulletinOperatorUser= itr_BulletinOperatorUsers_inDB.next();
					strKey4operatorUserId = "" + bulletinOperatorUser.getOperatorUser().getId();
					
					hmap_BulletinOperatorUser_inDB.put( strKey4operatorUserId, bulletinOperatorUser );
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
		//	数据库中BulletinOperatorUser情况	end
		
		
		BulletinBDConvertor bulletinBDConvertor = new BulletinBDConvertor();
		Bulletin bulletin_upd = bulletinBDConvertor.btod( _bBulletin4upd, true );
		
		//	参数中BulletinOperatorUser情况	begin
		HashMap<String, BulletinOperatorUser> hmap_BulletinOperatorUser_param = new HashMap<String, BulletinOperatorUser>();
		if( bulletin_upd.getBulletinOperatorUsers() != null && !(bulletin_upd.getBulletinOperatorUsers().isEmpty()) )
		{
			Iterator<BulletinOperatorUser> itr_BulletinOperatorUsers_param = bulletin_upd.getBulletinOperatorUsers().iterator();
			while( itr_BulletinOperatorUsers_param.hasNext() )
			{
				BulletinOperatorUser bulletinOperatorUser = itr_BulletinOperatorUsers_param.next();
				strKey4operatorUserId = "" + bulletinOperatorUser.getOperatorUser().getId();
				
				hmap_BulletinOperatorUser_param.put( strKey4operatorUserId, bulletinOperatorUser );
			}			
		}
		//	参数中BulletinOperatorUser情况	end
		
		//	比较param和inDB获得 BulletinOperatorUser 增删改情况 	begin
		List<BulletinOperatorUser> toDelBulletinOperatorUserList = new ArrayList<BulletinOperatorUser>();
		List<BulletinOperatorUser> toAddBulletinOperatorUserList = new ArrayList<BulletinOperatorUser>();
		
		//	param - inDB = add	begin
		if( bulletin_upd.getBulletinOperatorUsers() != null && !(bulletin_upd.getBulletinOperatorUsers().isEmpty()) )
		{
			Iterator<BulletinOperatorUser> itr_BulletinOperatorUsers_param = bulletin_upd.getBulletinOperatorUsers().iterator();
			while( itr_BulletinOperatorUsers_param.hasNext() )
			{
				BulletinOperatorUser bulletinOperatorUser = itr_BulletinOperatorUsers_param.next();
				strKey4operatorUserId = "" + bulletinOperatorUser.getOperatorUser().getId();
				
				if( hmap_BulletinOperatorUser_inDB.get( strKey4operatorUserId ) == null )
				{	
					toAddBulletinOperatorUserList.add( bulletinOperatorUser );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_BulletinOperatorUser_inDB.values() != null && !(hmap_BulletinOperatorUser_inDB.values().isEmpty()) )
		{
			Iterator<BulletinOperatorUser> itr_BulletinOperatorUsers_inDB = hmap_BulletinOperatorUser_inDB.values().iterator();
			while( itr_BulletinOperatorUsers_inDB.hasNext() )
			{
				BulletinOperatorUser bulletinOperatorUser = itr_BulletinOperatorUsers_inDB.next();
				strKey4operatorUserId = "" + bulletinOperatorUser.getOperatorUser().getId();
				
				if( hmap_BulletinOperatorUser_param.get( strKey4operatorUserId ) == null )
				{
					toDelBulletinOperatorUserList.add( bulletinOperatorUser );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 BulletinOperatorUser 增删改情况 	end
		

		//	持久化到数据库	begin
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			// 基本信息 保存
			DaoBulletin daoBulletin = new DaoBulletin( session );
			daoBulletin.update( bulletin_upd );

			// bulletinOperatorUsers 保存
			DaoBulletinOperatorUser daoBulletinOperatorUser = new DaoBulletinOperatorUser( session );

			if( toAddBulletinOperatorUserList != null && !(toAddBulletinOperatorUserList.isEmpty()) )
			{
				for( int i = 0; i < toAddBulletinOperatorUserList.size(); i++ )
				{
					daoBulletinOperatorUser.save( toAddBulletinOperatorUserList.get( i ) );
				}
			}

			if( toDelBulletinOperatorUserList != null && !(toDelBulletinOperatorUserList.isEmpty()) )
			{
				for( int i = 0; i < toDelBulletinOperatorUserList.size(); i++ )
				{
					daoBulletinOperatorUser.delete( toDelBulletinOperatorUserList.get( i ) );
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
		//	持久化到数据库	end

		BBulletin bBulletin_Rst = this.getBulletinById( _bBulletin4upd.getId(), true );
		return bBulletin_Rst;

	}

}
