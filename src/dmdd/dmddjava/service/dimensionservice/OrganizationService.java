package dmdd.dmddjava.service.dimensionservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.aidobject.ABImOrganization;
import dmdd.dmddjava.dataaccess.aidobject.ABImOrganizationCharacter;
import dmdd.dmddjava.dataaccess.aidobject.ABImOrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationCharacterBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationCharacterLayerBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationLayerBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BDistributionCenter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganizationCharacter;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganizationLayer;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganizationOrgCharacter;

/**
 * @author liuzhen
 * 
 */
public class OrganizationService
{

	private CommDMO dmo = new CommDMO();
	
	//	OrganizationCharacterLayer	begin
	public BOrganizationCharacterLayer newOrganizationCharacterLayer( BOrganizationCharacterLayer _bOrganizationCharacterLayer4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganizationCharacterLayer bOrganizationCharacterLayer_rst = null;
		OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
		OrganizationCharacterLayer organizationCharacterLayer_new = (OrganizationCharacterLayer) organizationCharacterLayerBDConvertor.btod( _bOrganizationCharacterLayer4new );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
			daoOrganizationCharacterLayer.save( organizationCharacterLayer_new );
			trsa.commit();
			
			bOrganizationCharacterLayer_rst = (BOrganizationCharacterLayer) organizationCharacterLayerBDConvertor.dtob( organizationCharacterLayer_new );
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

		return bOrganizationCharacterLayer_rst;

	}

	public BOrganizationCharacterLayer updOrganizationCharacterLayer( BOrganizationCharacterLayer _bOrganizationCharacterLayer4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganizationCharacterLayer bOrganizationCharacterLayer_rst = null;
		OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
		OrganizationCharacterLayer organizationCharacterLayer_upd = (OrganizationCharacterLayer) organizationCharacterLayerBDConvertor.btod( _bOrganizationCharacterLayer4upd );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
			daoOrganizationCharacterLayer.update( organizationCharacterLayer_upd );
			trsa.commit();
			
			bOrganizationCharacterLayer_rst = (BOrganizationCharacterLayer) organizationCharacterLayerBDConvertor.dtob( organizationCharacterLayer_upd );
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

		BOrganizationCharacter bOrganizationCharacter_treeRoot = this.getOrganizationCharacterTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( bOrganizationCharacter_treeRoot );	
		
		return bOrganizationCharacterLayer_rst;

	}

	public boolean delOrganizationCharacterLayer( BOrganizationCharacterLayer _bOrganizationCharacterLayer4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
		OrganizationCharacterLayer organizationCharacterLayer_del = (OrganizationCharacterLayer) organizationCharacterLayerBDConvertor.btod( _bOrganizationCharacterLayer4del );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
			daoOrganizationCharacterLayer.delete( organizationCharacterLayer_del );
			
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

	public List<BOrganizationCharacterLayer> getAllOrganizationCharacterLayers() throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		//	用户登录后会与系统状态一起获取这个数据，不做检查
//		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BOrganizationCharacterLayer> rstList = new ArrayList<BOrganizationCharacterLayer>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
			List<OrganizationCharacterLayer> listOrganizationCharacterLayer_inDB = daoOrganizationCharacterLayer.getAllOrganizationCharacterLayers();
			
			if( listOrganizationCharacterLayer_inDB != null && !(listOrganizationCharacterLayer_inDB.isEmpty()) )
			{
				OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
				for( int i=0; i<listOrganizationCharacterLayer_inDB.size(); i++ )
				{
					rstList.add( (BOrganizationCharacterLayer) organizationCharacterLayerBDConvertor.dtob( listOrganizationCharacterLayer_inDB.get( i ) ) );
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
	//	OrganizationCharacterLayer	end	

	//	OrganizationLayer	begin
	public BOrganizationLayer newOrganizationLayer( BOrganizationLayer _bOrganizationLayer4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganizationLayer bOrganizationLayer_rst = null;
		OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
		OrganizationLayer organizationLayer_new = (OrganizationLayer) organizationLayerBDConvertor.btod( _bOrganizationLayer4new );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
			daoOrganizationLayer.save( organizationLayer_new );
			trsa.commit();
			
			bOrganizationLayer_rst = (BOrganizationLayer) organizationLayerBDConvertor.dtob( organizationLayer_new );
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

		return bOrganizationLayer_rst;

	}

	public BOrganizationLayer updOrganizationLayer( BOrganizationLayer _bOrganizationLayer4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganizationLayer bOrganizationLayer_rst = null;
		OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
		OrganizationLayer organizationLayer_upd = (OrganizationLayer) organizationLayerBDConvertor.btod( _bOrganizationLayer4upd );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
			daoOrganizationLayer.update( organizationLayer_upd );
			trsa.commit();
			
			bOrganizationLayer_rst = (BOrganizationLayer) organizationLayerBDConvertor.dtob( organizationLayer_upd );
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

//		主数据常驻内存，2011.01.15 by liuzhen		begin
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end	
		
		return bOrganizationLayer_rst;

	}

	public boolean delOrganizationLayer( BOrganizationLayer _bOrganizationLayer4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
		OrganizationLayer organizationLayer_del = (OrganizationLayer) organizationLayerBDConvertor.btod( _bOrganizationLayer4del );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
			daoOrganizationLayer.delete( organizationLayer_del );
			
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

	public List<BOrganizationLayer> getAllOrganizationLayers() throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		//	用户登录后会与系统状态一起获取这个数据，不做检查
//		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BOrganizationLayer> rstList = new ArrayList<BOrganizationLayer>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
			List<OrganizationLayer> listOrganizationLayer_inDB = daoOrganizationLayer.getAllOrganizationLayers();
			
			if( listOrganizationLayer_inDB != null && !(listOrganizationLayer_inDB.isEmpty()) )
			{
				OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
				for( int i=0; i<listOrganizationLayer_inDB.size(); i++ )
				{
					rstList.add( (BOrganizationLayer) organizationLayerBDConvertor.dtob( listOrganizationLayer_inDB.get( i ) ) );
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
	//	OrganizationLayer	end
		

	
	//	OrganizationCharacter	begin
	public BOrganizationCharacter getOrganizationCharacterTreeRoot4UI() throws Exception
	{
		return ServerEnvironment.getInstance().getBOrganizationCharacterTreeRoot();
	}
	
	/**
	 * 新建
	 * 返回节点本身
	 */
	public BOrganizationCharacter newOrganizationCharacter( BOrganizationCharacter _bOrganizationCharacter4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganizationCharacter bOrganizationCharacter_rst = null;

		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();
		OrganizationCharacter organizationCharacter_new = (OrganizationCharacter) organizationCharacterBDConvertor.btod( _bOrganizationCharacter4new );
		if( _bOrganizationCharacter4new.getParentOrganizationCharacter() != null )
		{
			OrganizationCharacter parentOrganizationCharacter = (OrganizationCharacter) organizationCharacterBDConvertor.btod( _bOrganizationCharacter4new.getParentOrganizationCharacter() );
			organizationCharacter_new.setParentOrganizationCharacter( parentOrganizationCharacter );
		}
		else
		{
			organizationCharacter_new.setParentOrganizationCharacter( null );
		}
		

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
			//	检查父节点来测试保证父节点最新	begin
			if( _bOrganizationCharacter4new.getParentOrganizationCharacter() != null )
			{
				OrganizationCharacter organizationCharacter_parent_inDB = daoOrganizationCharacter.getOrganizationCharacterById( _bOrganizationCharacter4new.getParentOrganizationCharacter().getId() );
				if( organizationCharacter_parent_inDB != null && 
						organizationCharacter_parent_inDB.getVersion().longValue() != _bOrganizationCharacter4new.getParentOrganizationCharacter().getVersion().longValue() )
				{
					Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_PARENTNODE_HAS_BEEN_MODIFIED );
					Exception ex = new Exception( cause );
					throw ex;
				}
			}
			//	检查父节点来测试保证父节点最新	end
			organizationCharacter_new = (OrganizationCharacter)daoOrganizationCharacter.save( organizationCharacter_new );
			if(_bOrganizationCharacter4new.getParentOrganizationCharacter()==null)
				organizationCharacter_new.setPathCode(organizationCharacter_new.getId()+"");
			else
				organizationCharacter_new.setPathCode(_bOrganizationCharacter4new.getParentOrganizationCharacter().getPathCode()+"-"+organizationCharacter_new.getId());
			daoOrganizationCharacter.save( organizationCharacter_new );
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

		//	主数据常驻内存，2011.01.15 by liuzhen		begin
		/*
		bOrganizationCharacter_rst = this.getOrganizationCharacterTree( organizationCharacter_new.getId() );
		*/
		BOrganizationCharacter bOrganizationCharacter_treeRoot = this.getOrganizationCharacterTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( bOrganizationCharacter_treeRoot );	
		bOrganizationCharacter_rst = ServerEnvironment.getInstance().getBOrganizationCharacter( organizationCharacter_new.getId() );		
		//	主数据常驻内存，2011.01.15 by liuzhen		end
		return bOrganizationCharacter_rst;

	}

	/**
	 * 返回整个树
	 * @param _bOrganizationCharacter4upd
	 * @return
	 * @throws Exception
	 */
	public BOrganizationCharacter updOrganizationCharacter( BOrganizationCharacter _bOrganizationCharacter4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOrganizationCharacter4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}

		int diffLayerChange = 0;
		List<OrganizationCharacter> toUpdateOrganizationCharacterList_descendent = new ArrayList<OrganizationCharacter>();
		HashMap<Long,Integer> hmap_layerValue_old = new HashMap<Long, Integer>();
		
		Session querySession = HibernateSessionFactory.getSession();
		Transaction queryTrsa = null;
		try
		{
			queryTrsa = querySession.beginTransaction();
			DaoOrganizationCharacter daoOrganizationCharacter_query = new DaoOrganizationCharacter( querySession );
			OrganizationCharacter organizationCharacter_inDB = daoOrganizationCharacter_query.getOrganizationCharacterById( _bOrganizationCharacter4upd.getId() );
			
			if( organizationCharacter_inDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
 
			boolean blTypeChange = false;			
			if( organizationCharacter_inDB.getType() != null )
			{
				if( !(organizationCharacter_inDB.getType().equals( _bOrganizationCharacter4upd.getType() )) )
				{
					blTypeChange = true;
				}			
			}		
			
			diffLayerChange = _bOrganizationCharacter4upd.getOrganizationCharacterLayer().getValue() - organizationCharacter_inDB.getOrganizationCharacterLayer().getValue();
			
			if( _bOrganizationCharacter4upd.getIsCatalog() == BizConst.GLOBAL_YESNO_YES && 
				(blTypeChange == true || diffLayerChange != 0) )
			{
				toUpdateOrganizationCharacterList_descendent = daoOrganizationCharacter_query.getDescendentOrganizationCharacters( organizationCharacter_inDB.getId(), false );
				if( toUpdateOrganizationCharacterList_descendent != null && !(toUpdateOrganizationCharacterList_descendent.isEmpty()) )
				{
					for( int i=0; i<toUpdateOrganizationCharacterList_descendent.size(); i=i+1 )
					{
						OrganizationCharacter organizationCharacter_descendent = toUpdateOrganizationCharacterList_descendent.get( i );
						hmap_layerValue_old.put( organizationCharacter_descendent.getId(), organizationCharacter_descendent.getOrganizationCharacterLayer().getValue() );
					}
				}
				// 注意，这里只是读出来，还不能改。要放到下面的事务中一起修改
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
		
		BOrganizationCharacter bOrganizationCharacter_rst = null;

		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();
		OrganizationCharacter organizationCharacter_upd = (OrganizationCharacter) organizationCharacterBDConvertor.btod( _bOrganizationCharacter4upd );
		if( _bOrganizationCharacter4upd.getParentOrganizationCharacter() != null )
		{
			OrganizationCharacter upddobjParentOrganizationCharacter = (OrganizationCharacter) organizationCharacterBDConvertor.btod( _bOrganizationCharacter4upd.getParentOrganizationCharacter() );
			organizationCharacter_upd.setParentOrganizationCharacter( upddobjParentOrganizationCharacter );
			organizationCharacter_upd.setPathCode(_bOrganizationCharacter4upd.getParentOrganizationCharacter().getPathCode()+"-"+organizationCharacter_upd.getId());
		}
		else
		{
			organizationCharacter_upd.setParentOrganizationCharacter( null );
			organizationCharacter_upd.setPathCode(""+organizationCharacter_upd.getId());
		}

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
			daoOrganizationCharacter.update( organizationCharacter_upd );

			// 更新下级特征(组)    begin
			if ( toUpdateOrganizationCharacterList_descendent != null && !( toUpdateOrganizationCharacterList_descendent.isEmpty() ) )
			{
				DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
				for ( int i = 0; i < toUpdateOrganizationCharacterList_descendent.size(); i++ )
				{
					OrganizationCharacter organizationCharacter_upd_descendent = toUpdateOrganizationCharacterList_descendent.get( i );

					organizationCharacter_upd_descendent.setType( _bOrganizationCharacter4upd.getType() );

					if ( diffLayerChange != 0 )
					{
						int newLayerValue = hmap_layerValue_old.get( organizationCharacter_upd_descendent.getId() )	+ diffLayerChange;
						OrganizationCharacterLayer organizationCharacterLayer = daoOrganizationCharacterLayer.getOrganizationCharacterLayerByValue( 
								newLayerValue );
						if ( organizationCharacterLayer == null )
						{
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_ORGANIZATIONCHARACTERLAYER_MAXLAYER );
							Exception ex = new Exception( cause );
							throw ex;
						}
						else
						{
							organizationCharacter_upd_descendent.setOrganizationCharacterLayer( organizationCharacterLayer );
						}
					}

					daoOrganizationCharacter.update( organizationCharacter_upd_descendent );
				}
			}

			// 更新下级特征(组) end

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

		bOrganizationCharacter_rst = this.getOrganizationCharacterTreeRoot();	//	返回整个树形
		//	主数据常驻内存，2011.01.15 by liuzhen		begin	
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( bOrganizationCharacter_rst );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end		
		
//		主数据常驻内存，2011.01.15 by liuzhen		begin
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end	
		return bOrganizationCharacter_rst;
	}

	
	public boolean delOrganizationCharacter( BOrganizationCharacter _bOrganizationCharacter4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOrganizationCharacter4del == null )
		{
			Exception ex = new Exception( "The object to delete is null! Do nothing!" );
			throw ex;
		}

		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();
		OrganizationCharacter organizationCharacter_del = (OrganizationCharacter) organizationCharacterBDConvertor.btod( _bOrganizationCharacter4del );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationOrgCharacter daoOrganizationOrgCharacter = new DaoOrganizationOrgCharacter( session );
			DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
			List<OrganizationCharacter> listOrganizatoinCharacter =  daoOrganizationCharacter.getSubOrganizationCharacters( organizationCharacter_del );
			for(OrganizationCharacter OrganizationCharacter:listOrganizatoinCharacter)
			{
				List<OrganizationOrgCharacter> list = daoOrganizationOrgCharacter.getOrganizationOrgCharactersByOrgCharacterId( OrganizationCharacter.getId() );
				if(list!=null && !list.isEmpty())
				{
					Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_ORGANIZATIONCHARACTER_DELETEERROR );
					Exception ex = new Exception( cause );
					throw ex;
				}
			}

			//	数据库设置成绩级联删除
			//daoOrganizationCharacter.delete( organizationCharacter_del );
			if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				daoOrganizationCharacter.delete( organizationCharacter_del );
			}
			else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				List<OrganizationCharacter> listOrganizationCharacters = daoOrganizationCharacter.getSubOrganizationCharacters( organizationCharacter_del );
				for(OrganizationCharacter _organizationCharacter:listOrganizationCharacters)
				{
					daoOrganizationCharacter.delete( _organizationCharacter );
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
		
		//	主数据常驻内存，2011.01.15 by liuzhen		begin
		BOrganizationCharacter bOrganizationCharacter_treeRoot = this.getOrganizationCharacterTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( bOrganizationCharacter_treeRoot );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end
		
//		主数据常驻内存，2011.01.15 by liuzhen		begin
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end	
		
		return true;
	}
	

	/**
	 * 从数据库读取 OrganizationCharacter 的唯一入口，
	 * 其他的都是用 内存常驻变量 
	 * @return
	 * @throws Exception
	 */
	public BOrganizationCharacter getOrganizationCharacterTreeRoot() throws Exception
	{			
		BOrganizationCharacter bOrganizationCharacter_treeRoot = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
			OrganizationCharacter rootOrganizationCharacter_inDB = null;
			rootOrganizationCharacter_inDB = daoOrganizationCharacter.getOrganizationCharacterTreeRoot();
			
			if( rootOrganizationCharacter_inDB != null )
			{
				bOrganizationCharacter_treeRoot = this.getBOrganizationCharacterTreeByDOrganizationCharacter( rootOrganizationCharacter_inDB );
				bOrganizationCharacter_treeRoot.setParentOrganizationCharacter( null );
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

		return bOrganizationCharacter_treeRoot;

	}
	
	/**
	 * 组织特征导入
	 */
	public List<ABImOrganizationCharacter> importOrganizationCharacter( List<ABImOrganizationCharacter> _list4ABImOrganizationCharacter ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<ABImOrganizationCharacter> rstList = new ArrayList<ABImOrganizationCharacter>();
		
		if( _list4ABImOrganizationCharacter == null || _list4ABImOrganizationCharacter.isEmpty() )
		{
			throw new Exception("Paramete is not correct");
		}
		
		String sqlRestriction = null;
		ABImOrganizationCharacter abImOrganizationCharacter = null;
		String importResult = null;		
		
		OrganizationCharacter parentOrganizationCharacter = null;
		OrganizationCharacterLayer organizationCharacterLayer = null;

		String code = null;
		String name = null;
		String type = null;
		int isCatalog;
		String description = null;
		String comments = null;
		
		for( int i=0; i<_list4ABImOrganizationCharacter.size(); i++ )
		{
			// 逐行处理
			abImOrganizationCharacter = _list4ABImOrganizationCharacter.get( i );	
		
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;			
			try
			{
				trsa = session.beginTransaction();
								
				DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
				DaoOrganizationCharacterLayer daoOrganizationCharacterLayer = new DaoOrganizationCharacterLayer( session );
				
				//	上级组织特征
				parentOrganizationCharacter = null;
				if( abImOrganizationCharacter.getParentCode() == null || abImOrganizationCharacter.getParentCode().trim().equals( "" ) )
				{
					importResult = "Fail: parentCode is null";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;
				}
				sqlRestriction = "code = '" + abImOrganizationCharacter.getParentCode().trim() + "'";
				List<OrganizationCharacter> listOrganizationCharacter_inDB = daoOrganizationCharacter.getOrganizationCharacters( sqlRestriction );
				if( listOrganizationCharacter_inDB == null || listOrganizationCharacter_inDB.isEmpty() )
				{
					importResult = "Fail: can not find parent organizationCharacter";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				if( listOrganizationCharacter_inDB.size() > 1 )
				{
					importResult = "Fail: find multiple parent organizationCharacter";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}
				parentOrganizationCharacter = listOrganizationCharacter_inDB.get( 0 );
				if( parentOrganizationCharacter.getIsCatalog() == BizConst.GLOBAL_YESNO_NO )
				{
					importResult = "Fail: parent organizationCharacter is not a catalog";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}
				
				
				
				//	层次
				organizationCharacterLayer = null;
				if( abImOrganizationCharacter.getLayerValue() == null || abImOrganizationCharacter.getLayerValue().trim().equals( "" ) )
				{
					importResult = "Fail: layerValue is null";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				
				int layerValue = 0;
				try
				{
					layerValue = Integer.parseInt( abImOrganizationCharacter.getLayerValue().trim() );
				}
				catch(Exception ex)
				{
					importResult = "Fail: layerValue is not valid integer";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				
				organizationCharacterLayer = daoOrganizationCharacterLayer.getOrganizationCharacterLayerByValue( layerValue );
				if( organizationCharacterLayer == null )
				{
					importResult = "Fail: can not find organizationCharacterLayer";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				
				if( organizationCharacterLayer.getValue() != parentOrganizationCharacter.getOrganizationCharacterLayer().getValue() + 1 )
				{
					importResult = "Fail: organizationLayer value != parent  organizationLayer value + 1";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				
				//	编码
				code = null;
				
				if( abImOrganizationCharacter.getCode() == null || abImOrganizationCharacter.getCode().trim().equals( "" ) )
				{
					importResult = "Fail: code is null ";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}
				code = abImOrganizationCharacter.getCode().trim();
				
				//	名称
				name = null;
				
				if( abImOrganizationCharacter.getName() == null || abImOrganizationCharacter.getName().trim().equals( "" ) )
				{
					importResult = "Fail: name is null ";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}	
				name = abImOrganizationCharacter.getName().trim();
				
				
				//	类型
				type = null;
				
				if( abImOrganizationCharacter.getType() == null || abImOrganizationCharacter.getType().trim().equals( "" ) )
				{
					importResult = "Fail: type is null ";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}	
				type = abImOrganizationCharacter.getType().trim();
				if( organizationCharacterLayer.getValue() == 1 )
				{
					//	检查 TYPE 是否与已有的TYPE重复
					List<String> listType_inDB = daoOrganizationCharacter.getAllOrganizationCharacterTypes();
					if( listType_inDB != null && !(listType_inDB.isEmpty()) )
					{
						int t = -1;
						for( t=0; t<listType_inDB.size(); t++ )
						{
							String tmpType = listType_inDB.get( t );
							
							if( tmpType.equals( type ) )
							{
								break;
							}
						}
						if( t<listType_inDB.size() )
						{
							importResult = "Fail: type is same with other type in layer 1 ";
							abImOrganizationCharacter.setImportResult( importResult );
							
							rstList.add( abImOrganizationCharacter );
							continue;								
						}
					}
				}
				else
				{
					//	检查与 parent的type是否一致
					if( !(parentOrganizationCharacter.getType().equals( type )) )
					{
						importResult = "Fail: type is not same with parent ";
						abImOrganizationCharacter.setImportResult( importResult );
						
						rstList.add( abImOrganizationCharacter );
						continue;												
					}
				}
				
				//	是否目录
				isCatalog = 0; 
				
				if( abImOrganizationCharacter.getIsCatalog() == null || abImOrganizationCharacter.getIsCatalog().trim().equals( "" ) )
				{
					importResult = "Fail: isCatalog is null ";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;						
				}	
				if( abImOrganizationCharacter.getIsCatalog().trim().equals( "0" ) )
				{
					isCatalog = 0;
				}
				else if( abImOrganizationCharacter.getIsCatalog().trim().equals( "1" ) )
				{
					isCatalog = 1;
				}
				else
				{
					importResult = "Fail: isCatalog is not 0 or 1 ";
					abImOrganizationCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationCharacter );
					continue;					
				}
				
				//	description
				description = null;
				if( abImOrganizationCharacter.getDescription() != null )
				{
					description = abImOrganizationCharacter.getDescription().trim();
				}
				
				//	comments
				comments = null;
				if( abImOrganizationCharacter.getComments() != null )
				{
					comments = abImOrganizationCharacter.getComments().trim();
				}				
				
				//	建立新 OrganizationCharacter	begin
				OrganizationCharacter newOrganizationCharacter = new OrganizationCharacter();
				
				newOrganizationCharacter.setParentOrganizationCharacter( parentOrganizationCharacter );
				newOrganizationCharacter.setOrganizationCharacterLayer( organizationCharacterLayer );
				newOrganizationCharacter.setCode( code );
				newOrganizationCharacter.setName( name );
				newOrganizationCharacter.setType( type );
				newOrganizationCharacter.setIsCatalog( isCatalog );
				newOrganizationCharacter.setDescription( description );
				newOrganizationCharacter.setComments( comments );
				
				daoOrganizationCharacter.save( newOrganizationCharacter );
				//	建立新 OrganizationCharacter	end
				
				trsa.commit();
				
				//	导入成功	begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImOrganizationCharacter.setImportResult( importResult );
				
				rstList.add( abImOrganizationCharacter );
				//	导入成功	end
				
			}
			catch( Exception ex )
			{
				if( trsa != null )
				{
					trsa.rollback();
				}
				ex.printStackTrace();
				
				if( ex.getCause() != null )
				{
					importResult = "Fail: " + ex.getCause().getMessage();
				}
				else
				{
					importResult = "Fail: " + ex.getMessage();
				}
				abImOrganizationCharacter.setImportResult( importResult );
				
				rstList.add( abImOrganizationCharacter );				
						
			}
			finally
			{
				session.close();
			}
		}
		
		//	主数据常驻内存，2011.02.25 by liuzhen		begin
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( this.getOrganizationCharacterTreeRoot() );	
		//	主数据常驻内存，2011.02.25 by liuzhen		end			
	
		return rstList;
	}	
	

	/**
	 * 给出一个OrganizationCharacter对象，通过递归调用获得以这个对象为Root的对象树，
	 * 需要注意的是，Root的ParentOrganizationCharacter需要在调用这个方法的地方设置（这个方法只负责向下的树）
	 * 
	 * @param _organizationCharacter_inDB
	 * @return
	 */
	private BOrganizationCharacter getBOrganizationCharacterTreeByDOrganizationCharacter( OrganizationCharacter _organizationCharacter_inDB )
	{
		BOrganizationCharacter bOrganizationCharacter_treeRoot = null;
		if( _organizationCharacter_inDB == null )
		{
			return null;
		}

		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();

		// 基本属性
		bOrganizationCharacter_treeRoot = (BOrganizationCharacter) organizationCharacterBDConvertor.dtob( _organizationCharacter_inDB );

		// ParentOrganizationCharacter不处理，在外层处理；在这里处理的话，会导致parent不一致

		// SubOrganizationCharacters    begin
		Set<BOrganizationCharacter> subOrganizationCharacters = new HashSet<BOrganizationCharacter>();
		if( _organizationCharacter_inDB.getSubOrganizationCharacters() != null && _organizationCharacter_inDB.getSubOrganizationCharacters().iterator() != null )
		{
			bOrganizationCharacter_treeRoot.setSubOrganizationCharacters( new HashSet<BOrganizationCharacter>() );
			Iterator<OrganizationCharacter> itr_subOrganizationCharacters_inDB = _organizationCharacter_inDB.getSubOrganizationCharacters().iterator();
			while( itr_subOrganizationCharacters_inDB.hasNext() )
			{
				BOrganizationCharacter bSubOrganizationCharacter = getBOrganizationCharacterTreeByDOrganizationCharacter( itr_subOrganizationCharacters_inDB.next() );
				bSubOrganizationCharacter.setParentOrganizationCharacter( bOrganizationCharacter_treeRoot );
				subOrganizationCharacters.add( bSubOrganizationCharacter );
			}
		}
		if( subOrganizationCharacters.size() > 0 )
		{
			bOrganizationCharacter_treeRoot.setSubOrganizationCharacters( subOrganizationCharacters );
		}
		else
		{
			if( bOrganizationCharacter_treeRoot.getIsCatalog() == BizConst.GLOBAL_YESNO_NO )
			{
				bOrganizationCharacter_treeRoot.setSubOrganizationCharacters( null );
			}
		}
		// SubOrganizationCharacters    end		
		return bOrganizationCharacter_treeRoot;
	}
	//	OrganizationCharacter	end
	
	

	//	Organization	begin
	public BOrganization getOrganizationTreeRoot4UI() throws Exception
	{
		return ServerEnvironment.getInstance().getBOrganizationTreeRoot();
	}	
	/**
	 * 新建
	 * 返回节点本身
	 */
	public BOrganization newOrganization( BOrganization _bOrganization4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BOrganization bOrganization_rst = null;

		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		Organization organization_new = (Organization) organizationBDConvertor.btod( _bOrganization4new, true );
		if( _bOrganization4new.getParentOrganization() != null )
		{
			Organization parentOrganization = (Organization)organizationBDConvertor.btod( _bOrganization4new.getParentOrganization() );
			organization_new.setParentOrganization( parentOrganization );
		}
		else
		{
			organization_new.setParentOrganization( null );
		}
		

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganization daoOrganization = new DaoOrganization( session );

			//	检查父节点来测试保证父节点最新	begin
			if( _bOrganization4new.getParentOrganization() != null )
			{
				Organization organization_parent_inDB = daoOrganization.getOrganizationById( _bOrganization4new.getParentOrganization().getId() );
				if( organization_parent_inDB != null && 
						organization_parent_inDB.getVersion().longValue() != _bOrganization4new.getParentOrganization().getVersion().longValue() )
				{
					Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_PARENTNODE_HAS_BEEN_MODIFIED );
					Exception ex = new Exception( cause );
					throw ex;
				}
			}
			//	检查父节点来测试保证父节点最新	end

			organization_new = (Organization)daoOrganization.save( organization_new );
			if( organization_new.getOrganizationOrgCharacters() != null && !(organization_new.getOrganizationOrgCharacters().isEmpty()) )
			{
				DaoOrganizationOrgCharacter daoOrganizationOrgCharacter = new DaoOrganizationOrgCharacter( session );
				Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacters = organization_new.getOrganizationOrgCharacters().iterator();
				while( itr_OrganizationOrgCharacters.hasNext() )
				{
					daoOrganizationOrgCharacter.save( itr_OrganizationOrgCharacters.next() );
				}
			}
			
			if(_bOrganization4new.getParentOrganization()==null)
				organization_new.setPathCode(organization_new.getId()+"");
			else
				organization_new.setPathCode(_bOrganization4new.getParentOrganization().getPathCode()+"-"+organization_new.getId());
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
		//	主数据常驻内存，2011.01.15 by liuzhen		begin
		/*
		bOrganization_rst = this.getOrganizationTree( organization_new.getId() );
		*/
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
		bOrganization_rst = ServerEnvironment.getInstance().getBOrganization( organization_new.getId() );		
		//	主数据常驻内存，2011.01.15 by liuzhen		end			
		return bOrganization_rst;

	}

	/**
	 * 返回整个树
	 * @param _bOrganization4upd
	 * @return
	 * @throws Exception
	 */
	public BOrganization updOrganization( BOrganization _bOrganization4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOrganization4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}

		String strKey4ooc = null;
		HashMap<String, OrganizationOrgCharacter> hmap_OrganizationOrgCharacter_inDB = new HashMap<String, OrganizationOrgCharacter>();		
		
		boolean blIsValidYesToNo = false;
		int diffLayerChange = 0;
		List<Organization> toUpdateOrganizationList_descendent = new ArrayList<Organization>();
		HashMap<Long,Integer> hmap_layerValue_old = new HashMap<Long, Integer>();
		
		Session querySession = HibernateSessionFactory.getSession();
		Transaction queryTrsa = null;
		try
		{
			queryTrsa = querySession.beginTransaction();
			DaoOrganization daoOrganization_query = new DaoOrganization( querySession );
			Organization organization_inDB = daoOrganization_query.getOrganizationById( _bOrganization4upd.getId() );
			
			if( organization_inDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}	
			
			//	数据库中组织特征情况	begin
			if( organization_inDB.getOrganizationOrgCharacters() != null && !(organization_inDB.getOrganizationOrgCharacters().isEmpty()) )
			{
				Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacters_inDB = organization_inDB.getOrganizationOrgCharacters().iterator();
				while( itr_OrganizationOrgCharacters_inDB.hasNext() )
				{
					OrganizationOrgCharacter organizationOrgCharacter = itr_OrganizationOrgCharacters_inDB.next();
					strKey4ooc = "" + organizationOrgCharacter.getOrganizationCharacter().getId();
					
					hmap_OrganizationOrgCharacter_inDB.put( strKey4ooc, organizationOrgCharacter );
				}
			}
			//	数据库中组织特征情况	end			

			//	层次变化情况	begin
			diffLayerChange = _bOrganization4upd.getOrganizationLayer().getValue() - organization_inDB.getOrganizationLayer().getValue();
			//	层次变化情况	end
			
			//	是否有效变化情况	begin
			blIsValidYesToNo = false;
			if( organization_inDB.getIsValid() == BizConst.GLOBAL_YESNO_YES && _bOrganization4upd.getIsValid() == BizConst.GLOBAL_YESNO_NO )
			{
				blIsValidYesToNo = true;
			}
			//	是否有效变化情况	end			
			
			if( _bOrganization4upd.getIsCatalog() == BizConst.GLOBAL_YESNO_YES )
			{
				if( diffLayerChange != 0 )
				{
					toUpdateOrganizationList_descendent = daoOrganization_query.getDescendentOrganizations( organization_inDB.getId(), false );
					if( toUpdateOrganizationList_descendent != null && !(toUpdateOrganizationList_descendent.isEmpty()) )
					{
						for( int i=0; i<toUpdateOrganizationList_descendent.size(); i=i+1 )
						{
							Organization organization_descendent = toUpdateOrganizationList_descendent.get( i );
							hmap_layerValue_old.put( organization_descendent.getId(), organization_descendent.getOrganizationLayer().getValue() );
						}
					}					
				}
				else if( blIsValidYesToNo == true )
				{
					//	有效变无效，下级组织也要有效变无效。注意无效变有效时，不必这样级联处理。
					//	逻辑上，无效组织目录下不能有有效的下级组织，但有效的组织目录下可以有无效的下级组织
					toUpdateOrganizationList_descendent = daoOrganization_query.getDescendentOrganizations( organization_inDB.getId(), false );
				}

				// 注意，这里只是读出来，还不能改。要放到下面的事务中一起修改
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
		
		

		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		Organization organization_upd = organizationBDConvertor.btod( _bOrganization4upd, true );
		if( _bOrganization4upd.getParentOrganization() != null )
		{
			Organization parentOrganization = (Organization) organizationBDConvertor.btod( _bOrganization4upd.getParentOrganization() );
			organization_upd.setParentOrganization( parentOrganization );
			organization_upd.setPathCode(_bOrganization4upd.getParentOrganization().getPathCode()+"-"+organization_upd.getId());
		}
		else
		{
			organization_upd.setParentOrganization( null );
			organization_upd.setPathCode(""+organization_upd.getId());
		}		
		
		//	参数中产品特征情况	begin
		HashMap<String, OrganizationOrgCharacter> hmap_OrganizationOrgCharacter_param = new HashMap<String, OrganizationOrgCharacter>();
		
		if( organization_upd.getOrganizationOrgCharacters() != null && !(organization_upd.getOrganizationOrgCharacters().isEmpty()) )
		{
			Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacters_param = organization_upd.getOrganizationOrgCharacters().iterator();
			while( itr_OrganizationOrgCharacters_param.hasNext() )
			{
				OrganizationOrgCharacter organizationOrgCharacter = itr_OrganizationOrgCharacters_param.next();
				strKey4ooc = "" + organizationOrgCharacter.getOrganizationCharacter().getId();
				
				hmap_OrganizationOrgCharacter_param.put( strKey4ooc, organizationOrgCharacter );
			}			
		}			
		//	参数中产品特征情况	end
		
		//	分析特征分配情况	begin
		//	organizationOrgCharacters	begin
		List<OrganizationOrgCharacter> toDelOrganizationOrgCharacterList = new ArrayList<OrganizationOrgCharacter>();
		List<OrganizationOrgCharacter> toUpdOrganizationOrgCharacterList = new ArrayList<OrganizationOrgCharacter>();
		List<OrganizationOrgCharacter> toAddOrganizationOrgCharacterList = new ArrayList<OrganizationOrgCharacter>();
		
		//	param - inDB = add	begin
		if( organization_upd.getOrganizationOrgCharacters() != null && !(organization_upd.getOrganizationOrgCharacters().isEmpty()) )
		{
			Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacters_param = organization_upd.getOrganizationOrgCharacters().iterator();
			while( itr_OrganizationOrgCharacters_param.hasNext() )
			{
				OrganizationOrgCharacter organizationOrgCharacter = itr_OrganizationOrgCharacters_param.next();
				strKey4ooc = "" + organizationOrgCharacter.getOrganizationCharacter().getId();
				
				if( hmap_OrganizationOrgCharacter_inDB.get( strKey4ooc ) == null )
				{	
					toAddOrganizationOrgCharacterList.add( organizationOrgCharacter );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_OrganizationOrgCharacter_inDB.values() != null && !(hmap_OrganizationOrgCharacter_inDB.values().isEmpty()) )
		{
			Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacters_inDB = hmap_OrganizationOrgCharacter_inDB.values().iterator();
			while( itr_OrganizationOrgCharacters_inDB.hasNext() )
			{
				OrganizationOrgCharacter organizationOrgCharacter = itr_OrganizationOrgCharacters_inDB.next();
				strKey4ooc = "" + organizationOrgCharacter.getOrganizationCharacter().getId();
				
				if( hmap_OrganizationOrgCharacter_param.get( strKey4ooc ) == null )
				{
					toDelOrganizationOrgCharacterList.add( organizationOrgCharacter );
				}
			}
		}		
		//	inDB - param = del	end			
		//	organizationOrgCharacters	end			
		//	分析特征分配情况	end		

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganization daoOrganization = new DaoOrganization( session );
			daoOrganization.update( organization_upd );
			//	更新特征分配情况	begin
			DaoOrganizationOrgCharacter daoOrganizationOrgCharacter = new DaoOrganizationOrgCharacter( session );
			for( int i = 0; i < toDelOrganizationOrgCharacterList.size(); i++ )
			{
				daoOrganizationOrgCharacter.delete( toDelOrganizationOrgCharacterList.get( i ) );
			}
			for( int i = 0; i < toUpdOrganizationOrgCharacterList.size(); i++ )
			{
				daoOrganizationOrgCharacter.update( toUpdOrganizationOrgCharacterList.get( i ) );
			}
			for( int i = 0; i < toAddOrganizationOrgCharacterList.size(); i++ )
			{
				daoOrganizationOrgCharacter.save( toAddOrganizationOrgCharacterList.get( i ) );
			}			
			//	更新特征分配情况	end			

			//	更新下级组织(组)		begin
			if ( toUpdateOrganizationList_descendent != null && !( toUpdateOrganizationList_descendent.isEmpty() ) )
			{
				DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
				for ( int i = 0; i < toUpdateOrganizationList_descendent.size(); i++ )
				{
					Organization organization_upd_descendent = toUpdateOrganizationList_descendent.get( i );

					if ( diffLayerChange != 0 )
					{
						int newLayerValue = hmap_layerValue_old.get( organization_upd_descendent.getId() ) + diffLayerChange;
						OrganizationLayer organizationLayer = daoOrganizationLayer.getOrganizationLayerByValue(	newLayerValue );
						if ( organizationLayer == null )
						{
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_ORGANIZATIONLAYER_MAXLAYER );
							Exception ex = new Exception( cause );
							throw ex;
						}
						else
						{
							organization_upd_descendent.setOrganizationLayer( organizationLayer );
						}
					}
					
					if( blIsValidYesToNo == true )
					{
						organization_upd_descendent.setIsValid( BizConst.GLOBAL_YESNO_NO );
					}					

					daoOrganization.update( organization_upd_descendent );
				}
			}

			//	更新下级组织(组)		end

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

		BOrganization bOrganization_rst = this.getOrganizationTreeRoot();	//	返回整个树形
		//	主数据常驻内存，2011.01.15 by liuzhen		begin
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_rst );			
		//	主数据常驻内存，2011.01.15 by liuzhen		end			
		return bOrganization_rst;
	}

	
	public boolean delOrganization( BOrganization _bOrganization4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOrganization4del == null )
		{
			Exception ex = new Exception( "The object to delete is null! Do nothing!" );
			throw ex;
		}

		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		Organization organization_del = (Organization) organizationBDConvertor.btod( _bOrganization4del );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganization daoOrganization = new DaoOrganization( session );
			//	数据库设置成绩级联删除
			//daoOrganization.delete( organization_del );
			if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				daoOrganization.delete( organization_del );
			}
			else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				List<Organization> listOrganizations = daoOrganization.getOrganizations( organization_del.getId() );
				for(Organization _organization:listOrganizations)
				{
					daoOrganization.delete( _organization );
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
		
		//	主数据常驻内存，2011.01.15 by liuzhen		begin
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
		//	主数据常驻内存，2011.01.15 by liuzhen		end	
		
		return true;
	}
	

	/**
	 * 从数据库读取 Organization 的唯一入口，
	 * 其他的都是用 内存常驻变量 	 * @return
	 * @throws Exception
	 */
	public BOrganization getOrganizationTreeRoot() throws Exception
	{
				
		BOrganization bOrganization_treeRoot = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganization daoOrganization = new DaoOrganization( session );
			Organization rootOrganization_inDB = null;
			rootOrganization_inDB = daoOrganization.getOrganizationTreeRoot();
			
			if( rootOrganization_inDB != null )
			{
				bOrganization_treeRoot = this.getBOrganizationTreeByDOrganization( rootOrganization_inDB );
				bOrganization_treeRoot.setParentOrganization( null );
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
		
		

		return bOrganization_treeRoot;

	}


	
	/**
	 * 组织导入
	 * @param _list4ABImOrganization
	 * @return
	 * @throws Exception
	 */
	public List<ABImOrganization> importOrganization( List<ABImOrganization> _list4ABImOrganization ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<ABImOrganization> rstList = new ArrayList<ABImOrganization>();
		
		if( _list4ABImOrganization == null || _list4ABImOrganization.isEmpty() )
		{
			throw new Exception("Paramete is not correct");
		}
		
		String sqlRestriction = null;
		ABImOrganization abImOrganization = null;
		String importResult = null;		
		
		Organization parentOrganization = null;
		OrganizationLayer organizationLayer = null;
		String code = null;
		String name = null;
		int isCatalog;
		int isValid;
		String description = null;
		String comments = null;
		
		for( int i=0; i<_list4ABImOrganization.size(); i++ )
		{
			// 逐行处理
			abImOrganization = _list4ABImOrganization.get( i );	
		
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;			
			try
			{
				trsa = session.beginTransaction();
								
				DaoOrganization daoOrganization = new DaoOrganization( session );
				DaoOrganizationLayer daoOrganizationLayer = new DaoOrganizationLayer( session );
				
				//	上级组织
				parentOrganization = null;
				if( abImOrganization.getParentCode() == null || abImOrganization.getParentCode().trim().equals( "" ) )
				{
					importResult = "Fail: parentCode is null";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;
				}
				sqlRestriction = "code = '" + abImOrganization.getParentCode().trim() + "'";
				List<Organization> listOrganization_inDB = daoOrganization.getOrganizations( sqlRestriction );
				if( listOrganization_inDB == null || listOrganization_inDB.isEmpty() )
				{
					importResult = "Fail: can not find parent organization";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				if( listOrganization_inDB.size() > 1 )
				{
					importResult = "Fail: find multiple parent organization";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}
				parentOrganization = listOrganization_inDB.get( 0 );
				if( parentOrganization.getIsCatalog() == BizConst.GLOBAL_YESNO_NO )
				{
					importResult = "Fail: parent organization is not a catalog";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}
				
				
				
				//	层次
				organizationLayer = null;
				if( abImOrganization.getLayerValue() == null || abImOrganization.getLayerValue().trim().equals( "" ) )
				{
					importResult = "Fail: layerValue is null";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				int layerValue = 0;
				try
				{
					layerValue = Integer.parseInt( abImOrganization.getLayerValue().trim() );
				}
				catch(Exception ex)
				{
					importResult = "Fail: layerValue is not valid integer";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				organizationLayer = daoOrganizationLayer.getOrganizationLayerByValue( layerValue );
				if( organizationLayer == null )
				{
					importResult = "Fail: can not find organizationLayer";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				if( organizationLayer.getValue() != parentOrganization.getOrganizationLayer().getValue() + 1 )
				{
					importResult = "Fail: organizationLayer value != parent  organizationLayer value + 1";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				
				//	编码
				code = null;
				
				if( abImOrganization.getCode() == null || abImOrganization.getCode().trim().equals( "" ) )
				{
					importResult = "Fail: code is null ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}
				code = abImOrganization.getCode().trim();
				
				//	名称
				name = null;
				
				if( abImOrganization.getName() == null || abImOrganization.getName().trim().equals( "" ) )
				{
					importResult = "Fail: name is null ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}	
				name = abImOrganization.getName().trim();
				
				//	是否目录
				isCatalog = 0; 
				
				if( abImOrganization.getIsCatalog() == null || abImOrganization.getIsCatalog().trim().equals( "" ) )
				{
					importResult = "Fail: isCatalog is null ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}	
				if( abImOrganization.getIsCatalog().trim().equals( "0" ) )
				{
					isCatalog = 0;
				}
				else if( abImOrganization.getIsCatalog().trim().equals( "1" ) )
				{
					isCatalog = 1;
				}
				else
				{
					importResult = "Fail: isCatalog is not 0 or 1 ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				//	是否有效
				isValid = 0; 
				
				if( abImOrganization.getIsValid() == null || abImOrganization.getIsValid().trim().equals( "" ) )
				{
					importResult = "Fail: IsValid is null ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;						
				}	
				if( abImOrganization.getIsValid().trim().equals( "0" ) )
				{
					isValid = 0;
				}
				else if( abImOrganization.getIsValid().trim().equals( "1" ) )
				{
					isValid = 1;
				}
				else
				{
					importResult = "Fail: IsValid is not 0 or 1 ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}				
				
				//check for dc
				BDistributionCenter dc  = null;
				if(abImOrganization.getDcCode()==null ||abImOrganization.getDcCode().trim().equals("") )//为空
				{
					
				}
				else
				{
					dc= ServerEnvironment.getInstance().getDcCodeMap().get(abImOrganization.getDcCode());
					if(dc==null)
					{
						importResult = "Fail: can not find dc by dcCode ";
						abImOrganization.setImportResult( importResult );
						
						rstList.add( abImOrganization );
						continue;	
					}
					else
					{
						
					}
				}
				
				
				if( parentOrganization.getIsValid() == BizConst.GLOBAL_YESNO_NO && isValid == BizConst.GLOBAL_YESNO_YES )
				{
					importResult = "Fail: organization is Valid  while parent organization is not valid ";
					abImOrganization.setImportResult( importResult );
					
					rstList.add( abImOrganization );
					continue;					
				}
				
				//	description
				description = null;
				if( abImOrganization.getDescription() != null )
				{
					description = abImOrganization.getDescription().trim();
				}
				
				//	comments
				comments = null;
				if( abImOrganization.getComments() != null )
				{
					comments = abImOrganization.getComments().trim();
				}				
				
				//	建立新 Organization	begin
				Organization newOrganization = new Organization();
				
				newOrganization.setParentOrganization( parentOrganization );
				newOrganization.setOrganizationLayer( organizationLayer );
				newOrganization.setCode( code );
				newOrganization.setName( name );
				newOrganization.setIsCatalog( isCatalog );
				newOrganization.setIsValid( isValid );
				if(dc!=null)
					newOrganization.setDcId(dc.getId());
				newOrganization.setDescription( description );
				newOrganization.setComments( comments );
				
				daoOrganization.save( newOrganization );
				//	建立新 Organization	end
				
				trsa.commit();
				
				//	导入成功	begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImOrganization.setImportResult( importResult );
				
				rstList.add( abImOrganization );
				//	导入成功	end
				
			}
			catch( Exception ex )
			{
				if( trsa != null )
				{
					trsa.rollback();
				}
				ex.printStackTrace();
				
				if( ex.getCause() != null )
				{
					importResult = "Fail: " + ex.getCause().getMessage();
				}
				else
				{
					importResult = "Fail: " + ex.getMessage();
				}
				
				abImOrganization.setImportResult( importResult );
				
				rstList.add( abImOrganization );				
						
			}
			finally
			{
				session.close();
			}
		}
		
		//	主数据常驻内存，2011.02.25 by liuzhen		begin
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( this.getOrganizationTreeRoot() );	
		//	主数据常驻内存，2011.02.25 by liuzhen		end					
	
		return rstList;
	}	
	

	/**
	 * 组织-组织特征导入
	 * @param _list4ABImOrganizationOrgCharacter
	 * @return
	 * @throws Exception
	 */
	public List<ABImOrganizationOrgCharacter> importOrganizationOrgCharacter( List<ABImOrganizationOrgCharacter> _list4ABImOrganizationOrgCharacter ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<ABImOrganizationOrgCharacter> rstList = new ArrayList<ABImOrganizationOrgCharacter>();
		
		if( _list4ABImOrganizationOrgCharacter == null || _list4ABImOrganizationOrgCharacter.isEmpty() )
		{
			throw new Exception("Paramete is not correct");
		}
		

		ABImOrganizationOrgCharacter abImOrganizationOrgCharacter = null;
		String importResult = null;		
		
		
		for( int i=0; i<_list4ABImOrganizationOrgCharacter.size(); i++ )
		{
			// 逐行处理
			abImOrganizationOrgCharacter = _list4ABImOrganizationOrgCharacter.get( i );	
		
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;			
			try
			{
				trsa = session.beginTransaction();
								
				DaoOrganizationOrgCharacter daoOrganizationOrgCharacter = new DaoOrganizationOrgCharacter( session );
				DaoOrganization daoOrganization = new DaoOrganization( session );
				DaoOrganizationCharacter daoOrganizationCharacter = new DaoOrganizationCharacter( session );
				
				
				//	明细组织
				Organization detailOrganization = null;
				if( abImOrganizationOrgCharacter.getDetailOrganizationCode() == null || abImOrganizationOrgCharacter.getDetailOrganizationCode().trim().equals( "" ) )
				{
					importResult = "Fail: detailOrganizationCode is null";
					abImOrganizationOrgCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationOrgCharacter );
					continue;
				}
				
				detailOrganization = daoOrganization.getDetailOrganizationByCode( abImOrganizationOrgCharacter.getDetailOrganizationCode().trim() );
				if( detailOrganization == null )
				{
					importResult = "Fail: can not find detailOrganization";
					abImOrganizationOrgCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationOrgCharacter );
					continue;					
				}
				
				
				//	明细组织特征
				OrganizationCharacter detailOrganizationCharacter = null;
				if( abImOrganizationOrgCharacter.getDetailOrganizationCharacterCode() == null || abImOrganizationOrgCharacter.getDetailOrganizationCharacterCode().trim().equals( "" ) )
				{
					importResult = "Fail: detailOrganizationCharacterCode is null";
					abImOrganizationOrgCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationOrgCharacter );
					continue;
				}
				
				detailOrganizationCharacter = daoOrganizationCharacter.getDetialOrganizationCharacterByCode( abImOrganizationOrgCharacter.getDetailOrganizationCharacterCode().trim() );
				if( detailOrganizationCharacter == null )
				{
					importResult = "Fail: can not find detailOrganizationCharacter";
					abImOrganizationOrgCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationOrgCharacter );
					continue;					
				}
				//判断同一类型特征值是否已经存在
				List<String> listOrganizationOrgCharacterTypes = new ArrayList<String>();
				for(OrganizationOrgCharacter organizationOrgCharacter:detailOrganization.getOrganizationOrgCharacters())
				{
					listOrganizationOrgCharacterTypes.add( organizationOrgCharacter.getOrganizationCharacter().getType() );
				}
				if(listOrganizationOrgCharacterTypes.contains( detailOrganizationCharacter.getType() ))
				{
					importResult = ExceptionConst.EXCEPTION_CAUSECODE_ORGANIZATIONORGCHARACTER_SAMECHARACTERTYPE;
					abImOrganizationOrgCharacter.setImportResult( importResult );
					
					rstList.add( abImOrganizationOrgCharacter );
					continue;
				}
				
				//	建立新 OrganizationOrgCharacter	begin
				OrganizationOrgCharacter newOrganizationOrgCharacter = new OrganizationOrgCharacter();
				
				newOrganizationOrgCharacter.setOrganization( detailOrganization );
				newOrganizationOrgCharacter.setOrganizationCharacter( detailOrganizationCharacter );
				
				daoOrganizationOrgCharacter.save( newOrganizationOrgCharacter );
				//	建立新 OrganizationOrgCharacter	end
				
				trsa.commit();
				
				//	导入成功	begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImOrganizationOrgCharacter.setImportResult( importResult );
				
				rstList.add( abImOrganizationOrgCharacter );
				//	导入成功	end
				
			}
			catch( Exception ex )
			{
				if( trsa != null )
				{
					trsa.rollback();
				}
				ex.printStackTrace();
				
				if( ex.getCause() != null )
				{
					importResult = "Fail: " + ex.getCause().getMessage();
				}
				else
				{
					importResult = "Fail: " + ex.getMessage();
				}
				
				abImOrganizationOrgCharacter.setImportResult( importResult );
				
				rstList.add( abImOrganizationOrgCharacter );				
						
			}
			finally
			{
				session.close();
			}
		}
	
		//	主数据常驻内存，2011.02.25 by liuzhen		begin
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( this.getOrganizationTreeRoot() );	
		//	主数据常驻内存，2011.02.25 by liuzhen		end					
		
		return rstList;
	}	
	
	
	

	/**
	 * 给出一个Organization对象，通过递归调用获得以这个对象为Root的对象树，
	 * 需要注意的是，Root的ParentOrganization需要在调用这个方法的地方设置（这个方法只负责向下的树）
	 * 
	 * @param _organization_inDB
	 * @return
	 */
	private BOrganization getBOrganizationTreeByDOrganization( Organization _organization_inDB )
	{
		BOrganization bOrganization_treeRoot = null;
		if( _organization_inDB == null )
		{
			return null;
		}

		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();

		// 基本属性
		bOrganization_treeRoot = (BOrganization) organizationBDConvertor.dtob( _organization_inDB, true );

		// ParentOrganization不处理，在外层处理；在这里处理的话，会导致parent不一致

		// SubOrganizations    begin
		Set<BOrganization> subOrganizations = new HashSet<BOrganization>();
		if( _organization_inDB.getSubOrganizations() != null && _organization_inDB.getSubOrganizations().iterator() != null )
		{
			bOrganization_treeRoot.setSubOrganizations( new HashSet<BOrganization>() );
			Iterator<Organization> itr_subOrganizations_inDB = _organization_inDB.getSubOrganizations().iterator();
			while( itr_subOrganizations_inDB.hasNext() )
			{
				BOrganization bSubOrganization = getBOrganizationTreeByDOrganization( itr_subOrganizations_inDB.next() );
				bSubOrganization.setParentOrganization( bOrganization_treeRoot );
				subOrganizations.add( bSubOrganization );
			}
		}
		if( subOrganizations.size() > 0 )
		{
			bOrganization_treeRoot.setSubOrganizations( subOrganizations );
		}
		else
		{
			if( bOrganization_treeRoot.getIsCatalog() == BizConst.GLOBAL_YESNO_NO )
			{
				bOrganization_treeRoot.setSubOrganizations( null );
			}
		}
		// SubOrganizations    end		
		return bOrganization_treeRoot;
	}
	
	/**
	 * 根据编码查询组织，返回对象只保留了基本信息,ID,CODE,NAME
	 * @param orgCode
	 * @param orgName
	 * @return
	 * @throws Exception
	 */
    public List<BOrganization> getOrgsByCodeOrName(String orgCode,String orgName) throws Exception {
        List<BOrganization> orgList = null;
    	
        String sql = "select ID,CODE,NAME,ISCATALOG,DESCRIPTION,PARENTORGANIZATIONID,ORGANIZATIONLAYERID,DISTRIBUTIONCENTERID,ISVALID " +
        		" from ORGANIZATION where iscatalog = 0 and isvalid=1 ";
        if(StringUtils.isNotEmpty(orgCode)){
        	sql += " and code like '%" + orgCode + "%'";
        }
    	if(StringUtils.isNotEmpty(orgName)){
        	sql += " and name like '%" + orgName + "%'";
        }

    	HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
    	orgList = new ArrayList<BOrganization>(vos.length);
        for(HashVO vo : vos){
        	BOrganization org = new BOrganization();
        	org.setId(vo.getLognValue("ID"));
        	org.setName(vo.getStringValue("NAME"));
        	org.setCode(vo.getStringValue("CODE"));
        	org.setIsCatalog(vo.getIntegerValue("ISCATALOG"));
        	org.setDescription(vo.getStringValue("DESCRIPTION"));
        	org.setIsValid(vo.getIntegerValue("ISVALID"));
            
        	orgList.add(org);
        }

        return orgList;
    }
	
	
	/**
	 * 刷新组织缓存
	 * @throws Exception
	 */
	public void refreshCacheOrganization() throws Exception{
		BOrganization bOrganization_treeRoot = this.getOrganizationTreeRoot();	//	返回整个树形
		ServerEnvironment.getInstance().setBOrganizationTreeRoot( bOrganization_treeRoot );	
	}
	
	/**
	 * 刷新组织特征缓存
	 * @throws Exception
	 */
	public void refreshCacheOrganizationCharacter() throws Exception{
		
		BOrganizationCharacter bOrganizationCharacter_root = this.getOrganizationCharacterTreeRoot();	//返回整个树形
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( bOrganizationCharacter_root );
	}

}

