/**********************************************************************
 *$RCSfile:ConversionService.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.service.conversion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilProduct;
import dmdd.dmddjava.dataaccess.aidobject.ABImConversionSet;
import dmdd.dmddjava.dataaccess.bdconvertor.ConversionSetBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ConversionTypeBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BConversionSet;
import dmdd.dmddjava.dataaccess.bizobject.BConversionType;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.ConversionSet;
import dmdd.dmddjava.dataaccess.dataobject.ConversionType;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoConversionSet;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoConversionType;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;

/**
 * <li>Title: ConversionService.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ConversionService
{
	public int getConversionTypeStat( String _sqlRestriction ) throws Exception
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
			DaoConversionType dao = new DaoConversionType( session );
			rst = dao.getConversionTypeStat( _sqlRestriction );			
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
	
	public List<BConversionType> getConversionTypes( String _sqlRestriction, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		List<BConversionType> rstList = new ArrayList<BConversionType>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoConversionType dao = new DaoConversionType( session );
			List<ConversionType> listBulletin_inDB = dao.getConversionTypes( _sqlRestriction, _pageIndex, _pageSize  );

			if( listBulletin_inDB != null && listBulletin_inDB.iterator() != null )
			{
				ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
				for( int i=0; i<listBulletin_inDB.size(); i++ )
				{
					rstList.add( (BConversionType)bd.dtob( listBulletin_inDB.get( i ) ) );
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
	
	public BConversionType newConversionType(BConversionType bConversionType)throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( bConversionType == null )
		{
			throw new Exception( "The object to save is null" );
		}

		ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
		ConversionType conversionType_new = (ConversionType)bd.btod( bConversionType );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			// 基本信息 保存
			DaoConversionType dao = new DaoConversionType( session );
			dao.save( conversionType_new );
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

		BConversionType result = (BConversionType)bd.dtob(conversionType_new);
		return result;
	}
	
	
	public BConversionType updateConversionType(BConversionType bConversionType)throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( bConversionType == null )
		{
			throw new Exception( "The object to save is null" );
		}

		ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
		ConversionType conversionType_new = (ConversionType)bd.btod( bConversionType );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			// 基本信息 保存
			DaoConversionType dao = new DaoConversionType( session );
			dao.update( conversionType_new );
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

		BConversionType result = (BConversionType)bd.dtob(conversionType_new);
		return result;
	}
	
	public void deleteConversionType(BConversionType bConversionType)throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( bConversionType == null )
		{
			throw new Exception( "The object to save is null" );
		}

		ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
		ConversionType conversionType_new = (ConversionType)bd.btod( bConversionType );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			// 基本信息 保存
			DaoConversionType dao = new DaoConversionType( session );
			dao.delete( conversionType_new );
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
	}
	
	public int getConversionSetStat( List<BProduct> list) throws Exception
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
			DaoConversionSet dao = new DaoConversionSet( session );
			rst = dao.getConversionSetStat( UtilProduct.getIds(list) );			
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
	
	
	public List<BConversionSet> getConversoinSets(List<BProduct> list, int _pageIndex, int _pageSize)throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		List<BConversionSet> rstList = new ArrayList<BConversionSet>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoConversionSet dao = new DaoConversionSet( session );
			List<ConversionSet> listBulletin_inDB = dao.getConversionSets( UtilProduct.getIds(list), _pageIndex, _pageSize  );

			if( listBulletin_inDB != null && listBulletin_inDB.iterator() != null )
			{
				ConversionSetBDConvertor bd = new ConversionSetBDConvertor();
				for( int i=0; i<listBulletin_inDB.size(); i++ )
				{
					rstList.add( (BConversionSet)bd.dtob( listBulletin_inDB.get( i ) ) );
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
	
	public void deleteConversionSet(List<BConversionSet> list_ConversionSet)throws Exception
	{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( list_ConversionSet == null || list_ConversionSet.isEmpty())
		{
			throw new Exception( "The object to save is null" );
		}

		ConversionSetBDConvertor bd = new ConversionSetBDConvertor();
				
		for(BConversionSet bConversionSet:list_ConversionSet)
		{
			ConversionSet conversionSet_new = (ConversionSet)bd.btod( bConversionSet );
			deleteConversionSet(conversionSet_new.getId());
		}
			
	}
	
	public void deleteConversionSet(Long id) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(" delete from CONVERSIONSET where id = "+id);
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
	
	public List<ABImConversionSet> ImConvsersionSet(List<ABImConversionSet> list)throws Exception
	{
		List<ABImConversionSet> result = new ArrayList<ABImConversionSet>();
		//检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		if( list == null || list.isEmpty())
		{
			throw new Exception( "The object to save is null" );
		}
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoConversionSet dao = new DaoConversionSet( session );
			DaoProduct daoProduct = new DaoProduct( session );
			DaoConversionType daoConversionType = new DaoConversionType(session);
			Product detailProduct = null;
			ConversionType conversionType = null;
			ConversionSet set =null;
			String importResult = null;		
			for(ABImConversionSet aBImConversionSet:list)
			{
				detailProduct = daoProduct.getDetailProductByCode( aBImConversionSet.getproductCode() );				
				if( detailProduct == null )
				{
					importResult = "Can not find Detail Product by the Code";
					aBImConversionSet.setimportResult( importResult );					
					result.add(aBImConversionSet);
					continue;
				}
				
				conversionType = daoConversionType.getConversionTypeByCode(aBImConversionSet.getconversionTypeCode());
				if( conversionType == null )
				{
					importResult = "Can not find ConversionType by the Code";
					aBImConversionSet.setimportResult( importResult );					
					result.add(aBImConversionSet);
					continue;
				}
				set = dao.getConversionSetByProductId(detailProduct.getId());
				if(set==null)
				{
					set = new ConversionSet();
					set.setConversionType(conversionType);
					set.setProduct(detailProduct);
					dao.save(set);
				}
				else
				{
					set.setConversionType(conversionType);
					dao.update(set);
				}
				importResult="OK";
				aBImConversionSet.setimportResult( importResult );
				result.add(aBImConversionSet);
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
		
		return result;
	}
	
}

/**********************************************************************
 *$RCSfile:ConversionService.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:ConversionService.java,v $
 *********************************************************************/