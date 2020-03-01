package dmdd.dmddjava.service.forecastservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilOrganization;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilProduct;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastInstView;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastSetting;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastErrorMappingModelBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastInstBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastMakeLogBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastRunTaskBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.bizobject.BForecastInst;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLog;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogAuditItem;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelM;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogAuditItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelM;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInstProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLog;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLogAuditItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastModelM;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastModelMLWmaItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastModelMSLWmaItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.dataaccess.utils.UtilFactoryForecastModelM;
import dmdd.dmddjava.dm.ForecastDataDM;
import dmdd.dmddjava.service.model.ForecastModelService;

/**
 * @author liuzhen
 * 
 */
public class ForecastService
{
	private Logger logger = CoolLogger.getLogger(this.getClass());	
	
    private ForecastModelService forecastModelService = new ForecastModelService();
    
	//	ForecastInst	begin
	public BForecastInst newForecastInst( BForecastInst _bForecastInst4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BForecastInst bForecastInst_rst = null;
		
		ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
		ForecastInst forecastInst_new = forecastInstBDConvertor.btod( _bForecastInst4new, true );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			// mappingFcModel 保存    begin
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			
			ForecastModelM forecastModelM_new = forecastInst_new.getMappingFcModel();
			if( forecastModelM_new != null )
			{
				daoForecastModelM.save( forecastModelM_new );
				
				// 如果是 ForecastModelMLWma，要保存 ForecastModelMLWmaItem
				if( forecastModelM_new instanceof ForecastModelMLWma )
				{
					DaoForecastModelMLWmaItem daoForecastModelMLWmaItem = new DaoForecastModelMLWmaItem(session);
					Set<ForecastModelMLWmaItem> set4ForecastModelMLWmaItems = ((ForecastModelMLWma) forecastModelM_new).getForecastModelMLWmaItems();
					if( set4ForecastModelMLWmaItems != null && !(set4ForecastModelMLWmaItems.isEmpty()) )
					{
						Iterator<ForecastModelMLWmaItem> itr_set4ForecastModelMLWmaItems = set4ForecastModelMLWmaItems.iterator();
						while( itr_set4ForecastModelMLWmaItems.hasNext() )
						{
							daoForecastModelMLWmaItem.save( itr_set4ForecastModelMLWmaItems.next() );
						}
					}
				}	
				
				// 如果是 ForecastModelMSLWma，要保存 ForecastModelMSLWmaItem
				if( forecastModelM_new instanceof ForecastModelMSLWma )
				{
					DaoForecastModelMSLWmaItem daoForecastModelMSLWmaItem = new DaoForecastModelMSLWmaItem(session);
					Set<ForecastModelMSLWmaItem> set4ForecastModelMSLWmaItems = ((ForecastModelMSLWma) forecastModelM_new).getForecastModelMSLWmaItems();
					if( set4ForecastModelMSLWmaItems != null && !(set4ForecastModelMSLWmaItems.isEmpty()) )
					{
						Iterator<ForecastModelMSLWmaItem> itr_set4ForecastModelMSLWmaItems = set4ForecastModelMSLWmaItems.iterator();
						while( itr_set4ForecastModelMSLWmaItems.hasNext() )
						{
							daoForecastModelMSLWmaItem.save( itr_set4ForecastModelMSLWmaItems.next() );
						}
					}
				}	
				
				// 如果是 ForecastModelMAAnalog，要保存 ForecastModelMAAnalogItem
				if( forecastModelM_new instanceof ForecastModelMAAnalog )
				{
					DaoForecastModelMAAnalogItem daoForecastModelMAAnalogItem = new DaoForecastModelMAAnalogItem(session);
					Set<ForecastModelMAAnalogItem> set4ForecastModelMAAnalogItems = ((ForecastModelMAAnalog) forecastModelM_new).getForecastModelMAAnalogItems();
					if( set4ForecastModelMAAnalogItems != null && !(set4ForecastModelMAAnalogItems.isEmpty()) )
					{
						Iterator<ForecastModelMAAnalogItem> itr_set4ForecastModelMAAnalogItems = set4ForecastModelMAAnalogItems.iterator();
						while( itr_set4ForecastModelMAAnalogItems.hasNext() )
						{
							daoForecastModelMAAnalogItem.save( itr_set4ForecastModelMAAnalogItems.next() );
						}
					}
				}				
			}
			// mappingFcModel 保存    end			

			//	ForecatsInst 保存	begin
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			
			//	收集已有的ForecastInst 的ProOrg 和 NextProOrg以进行范围冲突检查	begin		
			List<ForecastInstProOrg> listForecastInstProOrg_others = new ArrayList<ForecastInstProOrg>();
			List<ForecastInstNextProOrg> listForecastInstNextProOrg_others = new ArrayList<ForecastInstNextProOrg>();
			
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES, BizConst.GLOBAL_YESNO_NO };
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );	
			if( listForecastInst_inDB != null )
			{
				for( int i=0; i<listForecastInst_inDB.size(); i++ )
				{
					ForecastInst forecastInst = listForecastInst_inDB.get( i );
					
					if( forecastInst.getIsValid() == BizConst.GLOBAL_YESNO_YES )
					{
						//	本期
						if( forecastInst.getForecastInstProOrgs() == null || forecastInst.getForecastInstProOrgs().isEmpty() )
						{
							continue;
						}
						
						Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs = forecastInst.getForecastInstProOrgs().iterator();
						while( itr_ForecastInstProOrgs.hasNext() )
						{
							listForecastInstProOrg_others.add( itr_ForecastInstProOrgs.next() );
						}						
					}
					
					if( forecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES )
					{
						//	下期
						if( forecastInst.getForecastInstNextProOrgs() == null || forecastInst.getForecastInstNextProOrgs().isEmpty() )
						{
							continue;
						}
						
						Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs = forecastInst.getForecastInstNextProOrgs().iterator();
						while( itr_ForecastInstNextProOrgs.hasNext() )
						{
							listForecastInstNextProOrg_others.add( itr_ForecastInstNextProOrgs.next() );
						}						
					}
					
				}				
			}
			
			//	收集已有的ForecastInst 的ProOrg 和 NextProOrg以进行范围冲突检查	end			

			//	基本信息	保存
			daoForecastInst.save( forecastInst_new );
			
			// forecastInstProOrgs 保存
			if( forecastInst_new.getForecastInstProOrgs() != null && !(forecastInst_new.getForecastInstProOrgs().isEmpty()) )
			{
				ForecastInstProOrg forecastInstProOrg_new = null;
				ForecastInstProOrg forecastInstProOrg_other = null;
				BProduct bProduct = null;
				BOrganization bOrganization = null;
				
				DaoForecastInstProOrg daoForecastInstProOrg = new DaoForecastInstProOrg( session );
				Iterator<ForecastInstProOrg> itr_newForecastInstProOrg = forecastInst_new.getForecastInstProOrgs().iterator();
				while( itr_newForecastInstProOrg.hasNext() )
				{
					forecastInstProOrg_new = itr_newForecastInstProOrg.next();
					
					if( forecastInst_new.getIsValid() == BizConst.GLOBAL_YESNO_YES )
					{			
						//	只有 有效 的预测类别才需要检查
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_new.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_new.getOrganization().getId() );
						ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
						
						for( int i=0; i<listForecastInstProOrg_others.size(); i++ )
						{
							forecastInstProOrg_other = listForecastInstProOrg_others.get( i );
							bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_other.getProduct().getId() );
							bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_other.getOrganization().getId() );
							ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
							
							if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
							{
								//	与其他ForecastInst 范围重叠
								Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTPROORG_OVERLAP );
								Exception ex = new Exception( cause );
								throw ex;							
							}
						}
					}
					
					daoForecastInstProOrg.save( forecastInstProOrg_new );
				}
			}

			// forecastInstNextProOrgs 保存
			if( forecastInst_new.getForecastInstNextProOrgs() != null && !(forecastInst_new.getForecastInstNextProOrgs().isEmpty()) )
			{
				ForecastInstNextProOrg forecastInstNextProOrg_new = null;
				ForecastInstNextProOrg forecastInstNextProOrg_other = null;
				BProduct bProduct = null;
				BOrganization bOrganization = null;
				
				DaoForecastInstNextProOrg daoForecastInstNextProOrg = new DaoForecastInstNextProOrg( session );
				Iterator<ForecastInstNextProOrg> itr_newForecastInstNextProOrg = forecastInst_new.getForecastInstNextProOrgs().iterator();
				while( itr_newForecastInstNextProOrg.hasNext() )
				{
					forecastInstNextProOrg_new = itr_newForecastInstNextProOrg.next();
					
					if( forecastInst_new.getNextIsValid() == BizConst.GLOBAL_YESNO_YES )
					{
						//	只有 有效的 预测类别才需要检查
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_new.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_new.getOrganization().getId() );						
						ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
						
						for( int i=0; i<listForecastInstNextProOrg_others.size(); i++ )
						{
							forecastInstNextProOrg_other = listForecastInstNextProOrg_others.get( i );
							bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_other.getProduct().getId() );
							bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_other.getOrganization().getId() );													
							ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
							
							if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
							{
								//	与其他ForecastInst 下期范围重叠
								Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTNEXTPROORG_OVERLAP );
								Exception ex = new Exception( cause );
								throw ex;							
							}
						}
					}
					
					daoForecastInstNextProOrg.save( forecastInstNextProOrg_new );
				}
			}
			
			//	ForecatsInst 保存	end

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

		bForecastInst_rst = this.getForecastInstById( forecastInst_new.getId(), true );
		return bForecastInst_rst;

	}

	
	public BForecastInst updForecastInst( BForecastInst _bForecastInst4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bForecastInst4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}
		
		//	mappingFcModel	begin
		ForecastModelM toDelForecastModelM = null;				
		//	mappingFcModel	end		

		String strKey4ProIdOrgId = null;

		//	数据库中 ForecastInstProOrgs ForecastInstNextProOrgs 情况	begin
		HashMap<String, ForecastInstProOrg> hmap_ForecastInstProOrg_inDB = new HashMap<String, ForecastInstProOrg>();
		HashMap<String, ForecastInstNextProOrg> hmap_ForecastInstNextProOrg_inDB = new HashMap<String, ForecastInstNextProOrg>();
		
		Session session_query = HibernateSessionFactory.getSession();
		Transaction trsa_query = null;
		try
		{
			trsa_query = session_query.beginTransaction();
			DaoForecastInst daoForecastInst_query = new DaoForecastInst( session_query );
			ForecastInst forecastInst_InDB = daoForecastInst_query.getForecastInstById( _bForecastInst4upd.getId() );
			if( forecastInst_InDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
			
			//	模型采取删除旧的建立新的的办法	begin
			if( forecastInst_InDB.getMappingFcModel() != null )
			{
				toDelForecastModelM = forecastInst_InDB.getMappingFcModel();
			}
			//	模型采取删除旧的建立新的的办法	end
			
			if( forecastInst_InDB.getForecastInstProOrgs() != null && !(forecastInst_InDB.getForecastInstProOrgs().isEmpty()) )
			{
				Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs_inDB = forecastInst_InDB.getForecastInstProOrgs().iterator();
				while( itr_ForecastInstProOrgs_inDB.hasNext() )
				{
					ForecastInstProOrg forecastInstProOrg = itr_ForecastInstProOrgs_inDB.next();
					strKey4ProIdOrgId = "" + forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
					
					hmap_ForecastInstProOrg_inDB.put( strKey4ProIdOrgId, forecastInstProOrg );
				}
			}
			
			if( forecastInst_InDB.getForecastInstNextProOrgs() != null && !(forecastInst_InDB.getForecastInstNextProOrgs().isEmpty()) )
			{
				Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs_inDB = forecastInst_InDB.getForecastInstNextProOrgs().iterator();
				while( itr_ForecastInstNextProOrgs_inDB.hasNext() )
				{
					ForecastInstNextProOrg forecastInstNextProOrg = itr_ForecastInstNextProOrgs_inDB.next();
					strKey4ProIdOrgId = "" + forecastInstNextProOrg.getProduct().getId() + "_" + forecastInstNextProOrg.getOrganization().getId();
					
					hmap_ForecastInstNextProOrg_inDB.put( strKey4ProIdOrgId, forecastInstNextProOrg );
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
		//	数据库中 ForecastInstProOrgs ForecastInstNextProOrgs 情况	end
		
		ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
		ForecastInst forecastInst_upd = forecastInstBDConvertor.btod( _bForecastInst4upd, true );	
		
		//	参数中 ForecastInstProOrgs ForecastInstNextProOrgs 情况	begin
		HashMap<String, ForecastInstProOrg> hmap_ForecastInstProOrg_param = new HashMap<String, ForecastInstProOrg>();
		HashMap<String, ForecastInstNextProOrg> hmap_ForecastInstNextProOrg_param = new HashMap<String, ForecastInstNextProOrg>();
		
		if( forecastInst_upd.getForecastInstProOrgs() != null && !(forecastInst_upd.getForecastInstProOrgs().isEmpty()) )
		{
			Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs_param = forecastInst_upd.getForecastInstProOrgs().iterator();
			while( itr_ForecastInstProOrgs_param.hasNext() )
			{
				ForecastInstProOrg forecastInstProOrg = itr_ForecastInstProOrgs_param.next();
				strKey4ProIdOrgId = "" + forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
				
				hmap_ForecastInstProOrg_param.put( strKey4ProIdOrgId, forecastInstProOrg );
			}			
		}	
		
		if( forecastInst_upd.getForecastInstNextProOrgs() != null && !(forecastInst_upd.getForecastInstNextProOrgs().isEmpty()) )
		{
			Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs_param = forecastInst_upd.getForecastInstNextProOrgs().iterator();
			while( itr_ForecastInstNextProOrgs_param.hasNext() )
			{
				ForecastInstNextProOrg forecastInstNextProOrg = itr_ForecastInstNextProOrgs_param.next();
				strKey4ProIdOrgId = "" + forecastInstNextProOrg.getProduct().getId() + "_" + forecastInstNextProOrg.getOrganization().getId();
				
				hmap_ForecastInstNextProOrg_param.put( strKey4ProIdOrgId, forecastInstNextProOrg );
			}			
		}		
		//	参数中 ForecastInstProOrgs ForecastInstNextProOrgs 情况	end
		
		//	比较param和inDB获得 ForecastInstProOrg 增删改情况 	begin
		List<ForecastInstProOrg> toDelForecastInstProOrgList = new ArrayList<ForecastInstProOrg>();
		List<ForecastInstProOrg> toAddForecastInstProOrgList = new ArrayList<ForecastInstProOrg>();
		List<ForecastInstProOrg> toUpdForecastInstProOrgList = new ArrayList<ForecastInstProOrg>();
		
		//	param - inDB = add	begin
		if( forecastInst_upd.getForecastInstProOrgs() != null && !(forecastInst_upd.getForecastInstProOrgs().isEmpty()) )
		{
			Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs_param = forecastInst_upd.getForecastInstProOrgs().iterator();
			while( itr_ForecastInstProOrgs_param.hasNext() )
			{
				ForecastInstProOrg forecastInstProOrg = itr_ForecastInstProOrgs_param.next();
				strKey4ProIdOrgId = "" + forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
				
				if( hmap_ForecastInstProOrg_inDB.get( strKey4ProIdOrgId ) == null )
				{	
					toAddForecastInstProOrgList.add( forecastInstProOrg );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_ForecastInstProOrg_inDB.values() != null && !(hmap_ForecastInstProOrg_inDB.values().isEmpty()) )
		{
			Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs_inDB = hmap_ForecastInstProOrg_inDB.values().iterator();
			while( itr_ForecastInstProOrgs_inDB.hasNext() )
			{
				ForecastInstProOrg forecastInstProOrg = itr_ForecastInstProOrgs_inDB.next();
				strKey4ProIdOrgId = "" + forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
				
				if( hmap_ForecastInstProOrg_param.get( strKey4ProIdOrgId ) == null )
				{
					toDelForecastInstProOrgList.add( forecastInstProOrg );
				}
				else
				{
					toUpdForecastInstProOrgList.add( forecastInstProOrg );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 ForecastInstProOrg 增删改情况 	end		
		
		//	比较param和inDB获得 ForecastInstNextProOrg 增删改情况 	begin
		List<ForecastInstNextProOrg> toDelForecastInstNextProOrgList = new ArrayList<ForecastInstNextProOrg>();
		List<ForecastInstNextProOrg> toAddForecastInstNextProOrgList = new ArrayList<ForecastInstNextProOrg>();
		List<ForecastInstNextProOrg> toUpdForecastInstNextProOrgList = new ArrayList<ForecastInstNextProOrg>();
		
		//	param - inDB = add	begin
		if( forecastInst_upd.getForecastInstNextProOrgs() != null && !(forecastInst_upd.getForecastInstNextProOrgs().isEmpty()) )
		{
			Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs_param = forecastInst_upd.getForecastInstNextProOrgs().iterator();
			while( itr_ForecastInstNextProOrgs_param.hasNext() )
			{
				ForecastInstNextProOrg forecastInstNextProOrg = itr_ForecastInstNextProOrgs_param.next();
				strKey4ProIdOrgId = "" + forecastInstNextProOrg.getProduct().getId() + "_" + forecastInstNextProOrg.getOrganization().getId();
				
				if( hmap_ForecastInstNextProOrg_inDB.get( strKey4ProIdOrgId ) == null )
				{	
					toAddForecastInstNextProOrgList.add( forecastInstNextProOrg );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_ForecastInstNextProOrg_inDB.values() != null && !(hmap_ForecastInstNextProOrg_inDB.values().isEmpty()) )
		{
			Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs_inDB = hmap_ForecastInstNextProOrg_inDB.values().iterator();
			while( itr_ForecastInstNextProOrgs_inDB.hasNext() )
			{
				ForecastInstNextProOrg forecastInstNextProOrg = itr_ForecastInstNextProOrgs_inDB.next();
				strKey4ProIdOrgId = "" + forecastInstNextProOrg.getProduct().getId() + "_" + forecastInstNextProOrg.getOrganization().getId();
				
				if( hmap_ForecastInstNextProOrg_param.get( strKey4ProIdOrgId ) == null )
				{
					toDelForecastInstNextProOrgList.add( forecastInstNextProOrg );
				}
				else
				{
					toUpdForecastInstNextProOrgList.add( forecastInstNextProOrg );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 ForecastInstNextProOrg 增删改情况 	end				
		
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			//	mappingFcModel	begin
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			if( toDelForecastModelM != null )
			{
				daoForecastModelM.delete( toDelForecastModelM );
				//	如果是 ForecastModelMLWma ForecastModelMSLWma，数据库级联删除会处理相应子项
				//	执行删除时，会因 forecastInst 引用而不能删除，通过数据库设置成置空
			}
			
			ForecastModelM forecastModelM_new = forecastInst_upd.getMappingFcModel();
			if( forecastModelM_new != null )
			{
				//	通过设置id version 来告诉hibernate是新对象
				forecastModelM_new.setId( SysConst.NULL_NUMERICAL_FIELD_Long );
				forecastModelM_new.setVersion( SysConst.NULL_NUMERICAL_FIELD_Long );
				daoForecastModelM.save( forecastModelM_new );
				
				// 如果是 ForecastModelMLWma，要保存 ForecastModelMLWmaItem
				if( forecastModelM_new instanceof ForecastModelMLWma )
				{
					DaoForecastModelMLWmaItem daoForecastModelMLWmaItem = new DaoForecastModelMLWmaItem(session);
					Set<ForecastModelMLWmaItem> set4ForecastModelMLWmaItems = ((ForecastModelMLWma) forecastModelM_new).getForecastModelMLWmaItems();
					if( set4ForecastModelMLWmaItems != null && !(set4ForecastModelMLWmaItems.isEmpty()) )
					{
						Iterator<ForecastModelMLWmaItem> itr_set4ForecastModelMLWmaItems = set4ForecastModelMLWmaItems.iterator();
						while( itr_set4ForecastModelMLWmaItems.hasNext() )
						{
							ForecastModelMLWmaItem forecastModelMLWmaItem_new = itr_set4ForecastModelMLWmaItems.next();
							//	通过设置id version 来告诉hibernate是新对象
							forecastModelMLWmaItem_new.setId( SysConst.NULL_NUMERICAL_FIELD_Long );
							forecastModelMLWmaItem_new.setVersion( SysConst.NULL_NUMERICAL_FIELD_Long );
							
							daoForecastModelMLWmaItem.save( forecastModelMLWmaItem_new );
						}
					}
				}	
				
				// 如果是 ForecastModelMSLWma，要保存 ForecastModelMSLWmaItem
				if( forecastModelM_new instanceof ForecastModelMSLWma )
				{
					DaoForecastModelMSLWmaItem daoForecastModelMSLWmaItem = new DaoForecastModelMSLWmaItem(session);
					Set<ForecastModelMSLWmaItem> set4ForecastModelMSLWmaItems = ((ForecastModelMSLWma) forecastModelM_new).getForecastModelMSLWmaItems();
					if( set4ForecastModelMSLWmaItems != null && !(set4ForecastModelMSLWmaItems.isEmpty()) )
					{
						Iterator<ForecastModelMSLWmaItem> itr_set4ForecastModelMSLWmaItems = set4ForecastModelMSLWmaItems.iterator();
						while( itr_set4ForecastModelMSLWmaItems.hasNext() )
						{
							ForecastModelMSLWmaItem forecastModelMSLWmaItem = itr_set4ForecastModelMSLWmaItems.next();
							//	通过设置id version 来告诉hibernate是新对象
							forecastModelMSLWmaItem.setId( SysConst.NULL_NUMERICAL_FIELD_Long );
							forecastModelMSLWmaItem.setVersion( SysConst.NULL_NUMERICAL_FIELD_Long );	
							daoForecastModelMSLWmaItem.save(forecastModelMSLWmaItem);
							//daoForecastModelMSLWmaItem.save( itr_set4ForecastModelMSLWmaItems.next() );
						}
					}
				}
				
				// 如果是 ForecastModelMAAnalog，要保存 ForecastModelMAAnalogItem
				if( forecastModelM_new instanceof ForecastModelMAAnalog )
				{
					DaoForecastModelMAAnalogItem daoForecastModelMAAnalogItem = new DaoForecastModelMAAnalogItem(session);
					Set<ForecastModelMAAnalogItem> set4ForecastModelMAAnalogItems = ((ForecastModelMAAnalog) forecastModelM_new).getForecastModelMAAnalogItems();
					if( set4ForecastModelMAAnalogItems != null && !(set4ForecastModelMAAnalogItems.isEmpty()) )
					{
						Iterator<ForecastModelMAAnalogItem> itr_set4ForecastModelMAAnalogItems = set4ForecastModelMAAnalogItems.iterator();
						while( itr_set4ForecastModelMAAnalogItems.hasNext() )
						{
							ForecastModelMAAnalogItem forecastModelMAAnalogItem_new = itr_set4ForecastModelMAAnalogItems.next();
							//	通过设置id version 来告诉hibernate是新对象
							forecastModelMAAnalogItem_new.setId( SysConst.NULL_NUMERICAL_FIELD_Long );
							forecastModelMAAnalogItem_new.setVersion( SysConst.NULL_NUMERICAL_FIELD_Long );
							
							daoForecastModelMAAnalogItem.save( forecastModelMAAnalogItem_new );
						}
					}
				}				
			}
			//	mappingFcModel	end
			
			//	forecastInst	begin
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			daoForecastInst.update( forecastInst_upd );
					
			//	收集已有的ForecastInst 的ProOrg 和 NextProOrg以进行范围冲突检查	begin		
			List<ForecastInstProOrg> listForecastInstProOrg_others = new ArrayList<ForecastInstProOrg>();
			List<ForecastInstNextProOrg> listForecastInstNextProOrg_others = new ArrayList<ForecastInstNextProOrg>();
			
			String sqlRestriction = " id != " + forecastInst_upd.getId();	//	把当前更新的对象排除在外
			List<ForecastInst> listForecastInst_inDB_others = daoForecastInst.getForecastInsts2( sqlRestriction, -1, 1 );	//	不分页查询
			if( listForecastInst_inDB_others != null )
			{
				for( int i=0; i<listForecastInst_inDB_others.size(); i++ )
				{
					ForecastInst forecastInst = listForecastInst_inDB_others.get( i );
					
					if( forecastInst.getIsValid() == BizConst.GLOBAL_YESNO_YES )
					{
						//	本期
						if( forecastInst.getForecastInstProOrgs() == null || forecastInst.getForecastInstProOrgs().isEmpty() )
						{
							continue;
						}
						
						Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs = forecastInst.getForecastInstProOrgs().iterator();
						while( itr_ForecastInstProOrgs.hasNext() )
						{
							listForecastInstProOrg_others.add( itr_ForecastInstProOrgs.next() );
						}						
					}
					
					if( forecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES )
					{
						//	下期
						if( forecastInst.getForecastInstNextProOrgs() == null || forecastInst.getForecastInstNextProOrgs().isEmpty() )
						{
							continue;
						}
						
						Iterator<ForecastInstNextProOrg> itr_ForecastInstNextProOrgs = forecastInst.getForecastInstNextProOrgs().iterator();
						while( itr_ForecastInstNextProOrgs.hasNext() )
						{
							listForecastInstNextProOrg_others.add( itr_ForecastInstNextProOrgs.next() );
						}						
					}
					
				}				
			}
			
			BProduct bProduct = null;
			BOrganization bOrganization = null;	
			
			//	收集已有的ForecastInst 的ProOrg 和 NextProOrg以进行范围冲突检查	end				
			
			// forecastInstProOrgs
			DaoForecastInstProOrg daoForecastInstProOrg = new DaoForecastInstProOrg( session );
			for( int i = 0; i < toDelForecastInstProOrgList.size(); i++ )
			{
				daoForecastInstProOrg.delete( toDelForecastInstProOrgList.get( i ) );
			}		
			for( int i = 0; i < toAddForecastInstProOrgList.size(); i++ )
			{
				ForecastInstProOrg forecastInstProOrg_new = toAddForecastInstProOrgList.get( i );
				
				if( forecastInst_upd.getIsValid() == BizConst.GLOBAL_YESNO_YES )
				{
					//	只有 有效的 预测类别才需要检查
					bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_new.getProduct().getId() );
					bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_new.getOrganization().getId() );
					ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
					
					for( int j=0; j<listForecastInstProOrg_others.size(); j++ )
					{
						ForecastInstProOrg forecastInstProOrg_other = listForecastInstProOrg_others.get( j );
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_other.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_other.getOrganization().getId() );
						ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
						
						if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
						{
							//	与其他ForecastInst 范围重叠
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTPROORG_OVERLAP );
							Exception ex = new Exception( cause );
							throw ex;							
						}
					}					
				}
				
				daoForecastInstProOrg.save( forecastInstProOrg_new );
			}
			for( int i = 0; i < toUpdForecastInstProOrgList.size(); i++ )
			{
				//	保留的范围项也要检查，因为有单纯的修改是否有效的情况 
				ForecastInstProOrg forecastInstProOrg_upd = toUpdForecastInstProOrgList.get( i );
				
				if( forecastInst_upd.getIsValid() == BizConst.GLOBAL_YESNO_YES )
				{
					//	只有 有效的 预测类别才需要检查
					bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_upd.getProduct().getId() );
					bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_upd.getOrganization().getId() );
					ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
					
					for( int j=0; j<listForecastInstProOrg_others.size(); j++ )
					{
						ForecastInstProOrg forecastInstProOrg_other = listForecastInstProOrg_others.get( j );
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstProOrg_other.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg_other.getOrganization().getId() );
						ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
						
						if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
						{
							//	与其他ForecastInst 范围重叠
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTPROORG_OVERLAP );
							Exception ex = new Exception( cause );
							throw ex;							
						}
					}					
				}
			}			
			

			// forecastInstNextProOrgs
			DaoForecastInstNextProOrg daoForecastInstNextProOrg = new DaoForecastInstNextProOrg( session );
			for( int i = 0; i < toDelForecastInstNextProOrgList.size(); i++ )
			{
				daoForecastInstNextProOrg.delete( toDelForecastInstNextProOrgList.get( i ) );
			}
			for( int i = 0; i < toAddForecastInstNextProOrgList.size(); i++ )
			{
				ForecastInstNextProOrg forecastInstNextProOrg_new = toAddForecastInstNextProOrgList.get( i );
				
				if( forecastInst_upd.getNextIsValid() == BizConst.GLOBAL_YESNO_YES )
				{
					//	只有 有效的 预测类别才需要检查
					bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_new.getProduct().getId() );
					bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_new.getOrganization().getId() );
					ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
					
					for( int j=0; j<listForecastInstNextProOrg_others.size(); j++ )
					{
						ForecastInstNextProOrg forecastInstNextProOrg_other = listForecastInstNextProOrg_others.get( j );
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_other.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_other.getOrganization().getId() );
						ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
						
						if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
						{
							//	与其他ForecastInst 下期范围重叠
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTNEXTPROORG_OVERLAP );
							Exception ex = new Exception( cause );
							throw ex;							
						}
					}					
				}
				
				daoForecastInstNextProOrg.save( forecastInstNextProOrg_new );
			}			
			
			for( int i = 0; i < toUpdForecastInstNextProOrgList.size(); i++ )
			{
				//	保留的范围项也要检查，因为有单纯的修改是否有效的情况 
				ForecastInstNextProOrg forecastInstNextProOrg_upd = toUpdForecastInstNextProOrgList.get( i );
				
				if( forecastInst_upd.getNextIsValid() == BizConst.GLOBAL_YESNO_YES )
				{
					//	只有 有效的 预测类别才需要检查
					bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_upd.getProduct().getId() );
					bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_upd.getOrganization().getId() );
					ABProOrg aBProOrg_new = new ABProOrg( bProduct,  bOrganization );						
					
					for( int j=0; j<listForecastInstNextProOrg_others.size(); j++ )
					{
						ForecastInstNextProOrg forecastInstNextProOrg_other = listForecastInstNextProOrg_others.get( j );
						
						bProduct = ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg_other.getProduct().getId() );
						bOrganization = ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg_other.getOrganization().getId() );
						ABProOrg aBProOrg_other = new ABProOrg( bProduct,  bOrganization );	
						
						if( UtilProOrg.relationOf( aBProOrg_new, aBProOrg_other )	!= UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
						{
							//	与其他ForecastInst 下期范围重叠
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_FORECASTINSTNEXTPROORG_OVERLAP );
							Exception ex = new Exception( cause );
							throw ex;							
						}
					}					
				}
			}				
			//	forecastInst	end

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

		BForecastInst bForecastInst_rst = this.getForecastInstById( _bForecastInst4upd.getId(), true );
		return bForecastInst_rst;
	}

	
	public boolean delForecastInst( BForecastInst _bForecastInst4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bForecastInst4del == null )
		{
			Exception ex = new Exception("要删除的预测策略是空对象，不能删除");
			throw ex;
		}
		
		ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
		ForecastInst forecastInst_del = forecastInstBDConvertor.btod( _bForecastInst4del, false ); // 数据库被设置了级联删出

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			//	mappingFcModel	begin
			if(forecastInst_del.getMappingFcModel() != null )
			{
				DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
				daoForecastModelM.delete( forecastInst_del.getMappingFcModel() );
			}
			//	mappingFcModel	end
			
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			
			daoForecastInst.delete( forecastInst_del );
			
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
	

	public int getForecastInstsStat( String _sqlRestriction ) throws Exception
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
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			rst = daoForecastInst.getForecastInstsStat( _sqlRestriction );			
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
	public List<BForecastInst> getForecastInsts( String _sqlRestriction, boolean _blWithProOrgs, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastInst> rstList = new ArrayList<BForecastInst>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getForecastInsts( _sqlRestriction, _pageIndex, _pageSize );
			
			if( listForecastInst_inDB != null && !(listForecastInst_inDB.isEmpty()) )
			{
				ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
				for( int i=0; i<listForecastInst_inDB.size(); i++ )
				{
					rstList.add( forecastInstBDConvertor.dtob( listForecastInst_inDB.get( i ), _blWithProOrgs ) );
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
	public List<BForecastInst> getForecastInstsByHibernate( String _sqlRestriction, boolean _blWithProOrgs, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastInst> rstList = new ArrayList<BForecastInst>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getForecastInsts2( _sqlRestriction, _pageIndex, _pageSize );
			
			if( listForecastInst_inDB != null && !(listForecastInst_inDB.isEmpty()) )
			{
				ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
				for( int i=0; i<listForecastInst_inDB.size(); i++ )
				{
					rstList.add( forecastInstBDConvertor.dtob( listForecastInst_inDB.get( i ), _blWithProOrgs ) );
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BForecastInst getForecastInst(Long id ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BForecastInst forecastinst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			ForecastInst ForecastInst_inDB = daoForecastInst.getForecastInstById( id);
			ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
			forecastinst = 	forecastInstBDConvertor.dtob( ForecastInst_inDB, true );			
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

		return forecastinst;
	}

	public BForecastInst getForecastInstById( Long _id, boolean _blWithProOrgs ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BForecastInst bForecastInst_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			ForecastInst forecastInst_inDB = daoForecastInst.getForecastInstById( _id );
			

			if( forecastInst_inDB != null )
			{
				ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
				bForecastInst_rst = forecastInstBDConvertor.dtob( forecastInst_inDB, _blWithProOrgs );
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

		return bForecastInst_rst;

	}
	//	ForecastInst	end

	//	ForecastModelM begin

	/**
	 * ForecastModelM 不提供save方法，因为不允许界面单独新建模型
	 * ForecastModelM 不提供delete方法，因为不允许界面删除模型
	 */
	public BForecastModelM updForecastModelM( BForecastModelM _bForecastModelM4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bForecastModelM4upd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}
		

		BForecastModelM bForecastModelM_rst = null;
		ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance( _bForecastModelM4upd.getIndicator() );
		ForecastModelM forecastModelM_upd = (ForecastModelM) forecastModelMBDConvertor.btod( _bForecastModelM4upd );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			//	ForecastModelM
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			daoForecastModelM.update( forecastModelM_upd );			
			
			//	如果是ForecastModelMLWma	begin
			if( forecastModelM_upd instanceof ForecastModelMLWma )
			{
				// 前端传来的
				HashMap<Integer, ForecastModelMLWmaItem> hmap_item_param = new HashMap<Integer, ForecastModelMLWmaItem>();
				
				Set<ForecastModelMLWmaItem> set_item_param = ((ForecastModelMLWma) forecastModelM_upd).getForecastModelMLWmaItems();
				if( set_item_param != null && !(set_item_param.isEmpty()) )
				{
					Iterator<ForecastModelMLWmaItem> itr_set_bItem_param = set_item_param.iterator();
					while( itr_set_bItem_param.hasNext() )
					{
						ForecastModelMLWmaItem forecastModelMLWmaItem_param = itr_set_bItem_param.next();
						hmap_item_param.put( forecastModelMLWmaItem_param.getPeriodSeqNo(), forecastModelMLWmaItem_param );
					}
				}
				
				// 数据库原有的
				HashMap<Integer, ForecastModelMLWmaItem> hmap_item_inDB = new HashMap<Integer, ForecastModelMLWmaItem>();	
				
				DaoForecastModelMLWmaItem daoForecastModelMLWmaItem = new DaoForecastModelMLWmaItem(session);
				List<ForecastModelMLWmaItem> list_item_inDB = daoForecastModelMLWmaItem.getForecastModelMLWmaItemsByForecastModelMLWmaId( forecastModelM_upd.getId() );
				
				if( list_item_inDB != null && !(list_item_inDB.isEmpty()) )
				{
					for(int i=0; i<list_item_inDB.size(); i++ )
					{
						ForecastModelMLWmaItem forecastModelMLWmaItem_inDB = list_item_inDB.get( i );
						hmap_item_inDB.put( forecastModelMLWmaItem_inDB.getPeriodSeqNo(), forecastModelMLWmaItem_inDB );
					}
				}
				 
				List<ForecastModelMLWmaItem> toAddForecastModelMLWmaItemList = new ArrayList<ForecastModelMLWmaItem>();
				List<ForecastModelMLWmaItem> toUpdForecastModelMLWmaItemList = new ArrayList<ForecastModelMLWmaItem>();
				List<ForecastModelMLWmaItem> toDelForecastModelMLWmaItemList = new ArrayList<ForecastModelMLWmaItem>();
				
				
				if( hmap_item_param.values() != null && !(hmap_item_param.values().isEmpty()) )
				{
					Iterator<ForecastModelMLWmaItem> itr_hmap_item_param = hmap_item_param.values().iterator();
					while( itr_hmap_item_param.hasNext() )
					{
						ForecastModelMLWmaItem forecastModelMLWmaItem_param = itr_hmap_item_param.next();
						ForecastModelMLWmaItem forecastModelMLWmaItem_inDB = hmap_item_inDB.get( forecastModelMLWmaItem_param.getPeriodSeqNo() );
						if( forecastModelMLWmaItem_inDB != null )
						{
							forecastModelMLWmaItem_inDB.setCoefficient( forecastModelMLWmaItem_param.getCoefficient() );
							toUpdForecastModelMLWmaItemList.add( forecastModelMLWmaItem_inDB );
						}
						else
						{
							toAddForecastModelMLWmaItemList.add( forecastModelMLWmaItem_param );
						}
					}
				}
				
				if( hmap_item_inDB.values() != null && !(hmap_item_inDB.values().isEmpty()) )
				{
					Iterator<ForecastModelMLWmaItem> itr_hmap_item_inDB = hmap_item_inDB.values().iterator();
					while( itr_hmap_item_inDB.hasNext() )
					{
						ForecastModelMLWmaItem forecastModelMLWmaItem_inDB = itr_hmap_item_inDB.next();
						ForecastModelMLWmaItem forecastModelMLWmaItem_param = hmap_item_param.get( forecastModelMLWmaItem_inDB.getPeriodSeqNo() );
						if( forecastModelMLWmaItem_param == null )
						{
							toDelForecastModelMLWmaItemList.add( forecastModelMLWmaItem_inDB );
						}
					}
				}				
				
				
				if( !(toDelForecastModelMLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toDelForecastModelMLWmaItemList.size(); i++ )
					{
						daoForecastModelMLWmaItem.delete( toDelForecastModelMLWmaItemList.get( i ) );
					}					
				}
				if( !(toUpdForecastModelMLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toUpdForecastModelMLWmaItemList.size(); i++ )
					{
						daoForecastModelMLWmaItem.update( toUpdForecastModelMLWmaItemList.get( i ) );
					}					
				}
				if( !(toAddForecastModelMLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toAddForecastModelMLWmaItemList.size(); i++ )
					{
						daoForecastModelMLWmaItem.save( toAddForecastModelMLWmaItemList.get( i ) );
					}					
				}				
			}
			//	如果是ForecastModelMLWma	end			

			//	如果是ForecastModelMSLWma	begin
			if( forecastModelM_upd instanceof ForecastModelMSLWma )
			{
				// 前端传来的
				HashMap<Integer, ForecastModelMSLWmaItem> hmap_item_param = new HashMap<Integer, ForecastModelMSLWmaItem>();
				
				Set<ForecastModelMSLWmaItem> set_item_param = ((ForecastModelMSLWma) forecastModelM_upd).getForecastModelMSLWmaItems();
				if( set_item_param != null && !(set_item_param.isEmpty()) )
				{
					Iterator<ForecastModelMSLWmaItem> itr_set_bItem_param = set_item_param.iterator();
					while( itr_set_bItem_param.hasNext() )
					{
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_param = itr_set_bItem_param.next();
						hmap_item_param.put( forecastModelMSLWmaItem_param.getPeriodSeqNo(), forecastModelMSLWmaItem_param );
					}
				}
				
				// 数据库原有的
				HashMap<Integer, ForecastModelMSLWmaItem> hmap_item_inDB = new HashMap<Integer, ForecastModelMSLWmaItem>();	
				
				DaoForecastModelMSLWmaItem daoForecastModelMSLWmaItem = new DaoForecastModelMSLWmaItem(session);
				List<ForecastModelMSLWmaItem> list_item_inDB = daoForecastModelMSLWmaItem.getForecastModelMSLWmaItemsByForecastModelMSLWmaId( forecastModelM_upd.getId() );
				
				if( list_item_inDB != null && !(list_item_inDB.isEmpty()) )
				{
					for(int i=0; i<list_item_inDB.size(); i++ )
					{
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_inDB = list_item_inDB.get( i );
						hmap_item_inDB.put( forecastModelMSLWmaItem_inDB.getPeriodSeqNo(), forecastModelMSLWmaItem_inDB );
					}
				}
				 
				List<ForecastModelMSLWmaItem> toAddForecastModelMSLWmaItemList = new ArrayList<ForecastModelMSLWmaItem>();
				List<ForecastModelMSLWmaItem> toUpdForecastModelMSLWmaItemList = new ArrayList<ForecastModelMSLWmaItem>();
				List<ForecastModelMSLWmaItem> toDelForecastModelMSLWmaItemList = new ArrayList<ForecastModelMSLWmaItem>();
				
				
				if( hmap_item_param.values() != null && !(hmap_item_param.values().isEmpty()) )
				{
					Iterator<ForecastModelMSLWmaItem> itr_hmap_item_param = hmap_item_param.values().iterator();
					while( itr_hmap_item_param.hasNext() )
					{
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_param = itr_hmap_item_param.next();
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_inDB = hmap_item_inDB.get( forecastModelMSLWmaItem_param.getPeriodSeqNo() );
						if( forecastModelMSLWmaItem_inDB != null )
						{
							forecastModelMSLWmaItem_inDB.setCoefficient( forecastModelMSLWmaItem_param.getCoefficient() );
							toUpdForecastModelMSLWmaItemList.add( forecastModelMSLWmaItem_inDB );
						}
						else
						{
							toAddForecastModelMSLWmaItemList.add( forecastModelMSLWmaItem_param );
						}
					}
				}
				
				if( hmap_item_inDB.values() != null && !(hmap_item_inDB.values().isEmpty()) )
				{
					Iterator<ForecastModelMSLWmaItem> itr_hmap_item_inDB = hmap_item_inDB.values().iterator();
					while( itr_hmap_item_inDB.hasNext() )
					{
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_inDB = itr_hmap_item_inDB.next();
						ForecastModelMSLWmaItem forecastModelMSLWmaItem_param = hmap_item_param.get( forecastModelMSLWmaItem_inDB.getPeriodSeqNo() );
						if( forecastModelMSLWmaItem_param == null )
						{
							toDelForecastModelMSLWmaItemList.add( forecastModelMSLWmaItem_inDB );
						}
					}
				}				
				
				
				if( !(toDelForecastModelMSLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toDelForecastModelMSLWmaItemList.size(); i++ )
					{
						daoForecastModelMSLWmaItem.delete( toDelForecastModelMSLWmaItemList.get( i ) );
					}					
				}
				if( !(toUpdForecastModelMSLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toUpdForecastModelMSLWmaItemList.size(); i++ )
					{
						daoForecastModelMSLWmaItem.update( toUpdForecastModelMSLWmaItemList.get( i ) );
					}					
				}
				if( !(toAddForecastModelMSLWmaItemList.isEmpty()) )
				{
					for(int i=0; i<toAddForecastModelMSLWmaItemList.size(); i++ )
					{
						daoForecastModelMSLWmaItem.save( toAddForecastModelMSLWmaItemList.get( i ) );
					}					
				}				
			}
			//	如果是ForecastModelMSLWma	end			

			//	如果是ForecastModelMAAnalog		begin
			if( forecastModelM_upd instanceof ForecastModelMAAnalog )
			{
				String strKey_proorg = null;
				// 前端传来的
				HashMap<String, ForecastModelMAAnalogItem> hmap_item_param = new HashMap<String, ForecastModelMAAnalogItem>();
				
				Set<ForecastModelMAAnalogItem> set_item_param = ((ForecastModelMAAnalog) forecastModelM_upd).getForecastModelMAAnalogItems();
				if( set_item_param != null && !(set_item_param.isEmpty()) )
				{
					Iterator<ForecastModelMAAnalogItem> itr_set_bItem_param = set_item_param.iterator();
					while( itr_set_bItem_param.hasNext() )
					{
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_param = itr_set_bItem_param.next();
						strKey_proorg = UtilStrKey.getStrKey4PO( forecastModelMAAnalogItem_param.getProduct(), forecastModelMAAnalogItem_param.getOrganization() );
						hmap_item_param.put( strKey_proorg, forecastModelMAAnalogItem_param );
					}
				}
				
				// 数据库原有的
				HashMap<String, ForecastModelMAAnalogItem> hmap_item_inDB = new HashMap<String, ForecastModelMAAnalogItem>();	
				
				DaoForecastModelMAAnalogItem daoForecastModelMAAnalogItem = new DaoForecastModelMAAnalogItem(session);
				List<ForecastModelMAAnalogItem> list_item_inDB = daoForecastModelMAAnalogItem.getForecastModelMAAnalogItemsByForecastModelMAAnalogId( forecastModelM_upd.getId() );
				
				if( list_item_inDB != null && !(list_item_inDB.isEmpty()) )
				{
					for(int i=0; i<list_item_inDB.size(); i++ )
					{
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_inDB = list_item_inDB.get( i );
						strKey_proorg = UtilStrKey.getStrKey4PO( forecastModelMAAnalogItem_inDB.getProduct(), forecastModelMAAnalogItem_inDB.getOrganization() );
						hmap_item_inDB.put( strKey_proorg, forecastModelMAAnalogItem_inDB );
					}
				}
				 
				List<ForecastModelMAAnalogItem> toAddForecastModelMAAnalogItemList = new ArrayList<ForecastModelMAAnalogItem>();
				List<ForecastModelMAAnalogItem> toUpdForecastModelMAAnalogItemList = new ArrayList<ForecastModelMAAnalogItem>();
				List<ForecastModelMAAnalogItem> toDelForecastModelMAAnalogItemList = new ArrayList<ForecastModelMAAnalogItem>();
				
				
				if( hmap_item_param.values() != null && !(hmap_item_param.values().isEmpty()) )
				{
					Iterator<ForecastModelMAAnalogItem> itr_hmap_item_param = hmap_item_param.values().iterator();
					while( itr_hmap_item_param.hasNext() )
					{
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_param = itr_hmap_item_param.next();
						strKey_proorg = UtilStrKey.getStrKey4PO( forecastModelMAAnalogItem_param.getProduct(), forecastModelMAAnalogItem_param.getOrganization() );
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_inDB = hmap_item_inDB.get( strKey_proorg );
						if( forecastModelMAAnalogItem_inDB != null )
						{
							forecastModelMAAnalogItem_inDB.setCoefficient( forecastModelMAAnalogItem_param.getCoefficient() );
							toUpdForecastModelMAAnalogItemList.add( forecastModelMAAnalogItem_inDB );
						}
						else
						{
							toAddForecastModelMAAnalogItemList.add( forecastModelMAAnalogItem_param );
						}
					}
				}
				
				if( hmap_item_inDB.values() != null && !(hmap_item_inDB.values().isEmpty()) )
				{
					Iterator<ForecastModelMAAnalogItem> itr_hmap_item_inDB = hmap_item_inDB.values().iterator();
					while( itr_hmap_item_inDB.hasNext() )
					{
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_inDB = itr_hmap_item_inDB.next();
						strKey_proorg = UtilStrKey.getStrKey4PO( forecastModelMAAnalogItem_inDB.getProduct(), forecastModelMAAnalogItem_inDB.getOrganization() );
						ForecastModelMAAnalogItem forecastModelMAAnalogItem_param = hmap_item_param.get( strKey_proorg );
						if( forecastModelMAAnalogItem_param == null )
						{
							toDelForecastModelMAAnalogItemList.add( forecastModelMAAnalogItem_inDB );
						}
					}
				}				
				
				
				if( !(toDelForecastModelMAAnalogItemList.isEmpty()) )
				{
					for(int i=0; i<toDelForecastModelMAAnalogItemList.size(); i++ )
					{
						daoForecastModelMAAnalogItem.delete( toDelForecastModelMAAnalogItemList.get( i ) );
					}					
				}
				if( !(toUpdForecastModelMAAnalogItemList.isEmpty()) )
				{
					for(int i=0; i<toUpdForecastModelMAAnalogItemList.size(); i++ )
					{
						daoForecastModelMAAnalogItem.update( toUpdForecastModelMAAnalogItemList.get( i ) );
					}					
				}
				if( !(toAddForecastModelMAAnalogItemList.isEmpty()) )
				{
					for(int i=0; i<toAddForecastModelMAAnalogItemList.size(); i++ )
					{
						daoForecastModelMAAnalogItem.save( toAddForecastModelMAAnalogItemList.get( i ) );
					}					
				}					
			}			
			//	如果是ForecastModelMAAnalog		end
			
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

		bForecastModelM_rst = this.getForecastModelMById( _bForecastModelM4upd.getId() );
		return bForecastModelM_rst;

	}
	
	public BForecastModelM getForecastModelMById( Long _id ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		BForecastModelM  bForecastModelM_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			ForecastModelM forecastModelM_inDB = daoForecastModelM.getForecastModelMById( _id );

			if( forecastModelM_inDB != null )
			{
				ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance( forecastModelM_inDB.getIndicator() );

				bForecastModelM_rst = (BForecastModelM)forecastModelMBDConvertor.dtob( forecastModelM_inDB );
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

		return bForecastModelM_rst;

	}	
	
	
	/**
	 * 获取查询统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getForecastModelMsStat( String _sqlRestriction ) throws Exception
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
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			rst = daoForecastModelM.getForecastModelMsStat( _sqlRestriction );			
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
	public List<BForecastModelM> getForecastModelMs( String _sqlRestriction, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastModelM> rstList = new ArrayList<BForecastModelM>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastModelM daoForecastModelM = new DaoForecastModelM( session );
			List<ForecastModelM> listForecastModelM_inDB = daoForecastModelM.getForecastModelMs( _sqlRestriction, _pageIndex, _pageSize );
			if( listForecastModelM_inDB != null && listForecastModelM_inDB.iterator() != null )
			{
				for( int i=0; i<listForecastModelM_inDB.size(); i++ )
				{
					ForecastModelM forecastModelM_inDB = listForecastModelM_inDB.get( i );

					ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance( forecastModelM_inDB
							.getIndicator() );
					rstList.add( (BForecastModelM) forecastModelMBDConvertor.dtob( forecastModelM_inDB ) );
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
	//	ForecastModelM end

	// forecast simulate begin

	
	public List<Object> forecastModelMSimulate( BSysPeriod _bSysPeriod, BForecastModelM _bForecastModelM, int _fcPeriodNum, 
			List<ABProOrg> _list4ProOrgScope4ForecastScope,
			BProductLayer _runProductLayer, BOrganizationLayer _runOrganizationLayer ) throws Exception
	{
		//实现提炼到单独的类中
		return forecastModelService.forecastModelMSimulate(_bSysPeriod, _bForecastModelM, _fcPeriodNum, _list4ProOrgScope4ForecastScope, _runProductLayer, _runOrganizationLayer);
	}
	// forecast simulate end


	// forecastData begin
	
	/**
	 * 检查 _list4ABProOrg _list4BBizDataFcHand 范围内 是否有预测数据处于 审核冻结状态 
	 */
	public boolean checkForecastDataStatusIsInactive(List<ABProOrg> _list4ABProOrg, List<BBizData> _list4BBizDataFcHand, BSysPeriod _bSysPeriod ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		boolean rstBlIsInactive = false;
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
		
		if( _list4ABProOrg == null || _list4ABProOrg.isEmpty() )
		{
			return rstBlIsInactive;
		}
		
		if( _list4BBizDataFcHand == null || _list4BBizDataFcHand.isEmpty() )
		{
			return rstBlIsInactive;
		}		
		
		List<Long> list4BizDataId = new ArrayList<Long>();
		for( int i=0; i<_list4BBizDataFcHand.size(); i++ )
		{
			list4BizDataId.add( _list4BBizDataFcHand.get( i ).getId() );
		}
		
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			DaoForecastData daoForecastData = new DaoForecastData( session );
		
			HashMap<String, AProOrg> hmap_AProOrg_detail = UtilProOrg.getDetailProOrgs( _list4ABProOrg, session, true );
			String sqlStr ="";
			if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				sqlStr= "(productid,organizationid) in " + UtilProOrg.getIdsScopeStr4ProOrgs( hmap_AProOrg_detail );	
			}
			else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				sqlStr = UtilProOrg.getIds4msslqStr4ProOrgs(hmap_AProOrg_detail);
			}
						
			rstBlIsInactive = daoForecastData.checkForecastDataStatusIsInactive( sqlStr, _bSysPeriod.getForecastRunPeriod(), SysConst.PERIOD_NULL, list4BizDataId );
			
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
		
		return rstBlIsInactive;
	}	
	
	/**
	 * 获取  _list4ProOrgScope 内的 预测设置 
	 */
	public List<ABForecastSetting> getForecastSettings(List<ABProOrg> _list4ABProOrg, BSysPeriod _bSysPeriod ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<ABForecastSetting> rstList = new ArrayList<ABForecastSetting>();
		
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
		
		if( _list4ABProOrg == null || _list4ABProOrg.isEmpty() )
		{
			return null;
		}
		logger.info("开始查询所有的策略");
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );
			logger.info("策略查询结束");
			if( listForecastInst_inDB != null )
			{
				//	查询Product Organization 树跟节点	begin
				DaoProduct daoProduct = new DaoProduct( session );
				Product productTreeRoot = daoProduct.getProductTreeRoot();
				
				DaoOrganization daoOrganization = new DaoOrganization( session );
				Organization organizationTreeRoot = daoOrganization.getOrganizationTreeRoot();
				//	查询Product Organization 树跟节点	end
				
				//	前端出来的 ABProOrg 转换成 树形机构上的  AProOrg		begin
				List<AProOrg> listAProOrg_param = new ArrayList<AProOrg>();
				for( int i=0; i<_list4ABProOrg.size(); i++ )
				{
					ABProOrg abProOrg = _list4ABProOrg.get( i );
					Product product = UtilProduct.getProductInTreeByRecursion( abProOrg.getProduct().getId(), productTreeRoot );
					Organization organization = UtilOrganization.getOrganizationInTreeByRecursion( abProOrg.getOrganization().getId(), organizationTreeRoot );					
					AProOrg aProOrg = new AProOrg( product, organization ); 
					listAProOrg_param.add( aProOrg );
				}
				//	前端出来的 ABProOrg 转换成 树形机构上的  AProOrg		end
				
				
				//	收集 ABForecastSetting	begin
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
				
				HashMap<String, ABForecastSetting> hmap_ABForecastSetting = new HashMap<String, ABForecastSetting>();
				
				boolean blIn = false;
				for( int i=0; i<listForecastInst_inDB.size(); i++ )
				{
					ForecastInst forecastInst = listForecastInst_inDB.get( i );
					
					if( forecastInst.getForecastInstProOrgs() == null || forecastInst.getForecastInstProOrgs().isEmpty() )
					{
						continue;
					}
					
					blIn = false;
					Iterator<ForecastInstProOrg> itr_ForecastInstProOrgs = forecastInst.getForecastInstProOrgs().iterator();
					while( itr_ForecastInstProOrgs.hasNext() )
					{
						ForecastInstProOrg forecastInstProOrg = itr_ForecastInstProOrgs.next();
						Product product = UtilProduct.getProductInTreeByRecursion( forecastInstProOrg.getProduct().getId(), productTreeRoot );
						Organization organization = UtilOrganization.getOrganizationInTreeByRecursion( forecastInstProOrg.getOrganization().getId(), organizationTreeRoot );
						AProOrg aProOrg_1 = new AProOrg( product,  organization );
						
						for( int j=0; j<listAProOrg_param.size(); j++ )
						{
							AProOrg aProOrg_2 = listAProOrg_param.get( j );
							if( UtilProOrg.relationOf( aProOrg_1, aProOrg_2 ) != UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
							{
								String strKey = "" + forecastInst.getFinalFcBizData().getId() 
													+ "_" + forecastInst.getFcPeriodNum() 
													+ "_" + forecastInst.getFzPeriodNum()
													+ "_" + forecastInst.getDistributeRefFormula()
													+ "_" + forecastInst.getDistributeRefBizData().getId()
													+ "_" + forecastInst.getDistributeRefPeriodNum();
								
								if( hmap_ABForecastSetting.get( strKey ) == null )
								{
									BBizData bBizDataFcComb4FcFinal = bizDataBDConvertor.dtob( forecastInst.getFinalFcBizData(), true );
									BBizData bBizData4DistributeRef = bizDataBDConvertor.dtob( forecastInst.getDistributeRefBizData(), true );
									
									ABForecastSetting abForecastSetting = new ABForecastSetting();
									abForecastSetting.setFinalFcBizData( bBizDataFcComb4FcFinal );
									abForecastSetting.setFcPeriodNum( forecastInst.getFcPeriodNum() );
									abForecastSetting.setFzPeriodNum( forecastInst.getFzPeriodNum() );
									abForecastSetting.setDistributeRefFormula( forecastInst.getDistributeRefFormula() );
									abForecastSetting.setDistributeRefBizData( bBizData4DistributeRef );
									abForecastSetting.setDistributeRefPeriodNum( forecastInst.getDistributeRefPeriodNum() );
									
									hmap_ABForecastSetting.put( strKey, abForecastSetting );
									
								}
								
								blIn = true;
								break;
							}
						}
						
						if( blIn == true )
						{
							break;
						}
					}
				}
				
				if( hmap_ABForecastSetting.values() != null && !(hmap_ABForecastSetting.values().isEmpty()) )
				{
					Iterator<ABForecastSetting> itr_hmap_ABForecastSetting_values = hmap_ABForecastSetting.values().iterator();
					while( itr_hmap_ABForecastSetting_values.hasNext() )
					{
						rstList.add( itr_hmap_ABForecastSetting_values.next() );
					}
				}
				//	收集 ABForecastSetting	end
			}
			
			trsa.commit();
			logger.info("获取策略完毕:大小："+rstList.size());
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
	 * _bForecastAdjsutLog4save 中的 ForecastMakeLogAuditItems 起数据更新作用，_listABUiRowData4save 的作用是提供分解用的明细ProOrgId
	 * 实际上也可以用 _listABUiRowData4save 中的数据来起更新作用，但 _bForecastAdjsutLog4save 中的 ForecastMakeLogAuditItems 是个半成品，可以直接用 
	 * @param _listABUiRowData4save
	 * @param _bForecastAdjsutLog4save
	 * @param _bSysPeriod
	 * @throws Exception
	 */
	public String saveForecastDatas4AdjustUI( List<ABUiRowData> _listABUiRowData4save, BForecastMakeLog _bForecastAdjsutLog4save, ABForecastSetting _abForecastSetting, BSysPeriod _bSysPeriod ) throws Exception
	{
		ForecastSaveService saveService = new ForecastSaveService();
		return saveService.saveForecastDatas4AdjustUI(_listABUiRowData4save, _bForecastAdjsutLog4save, _abForecastSetting, _bSysPeriod);
	}

	public void auditForecastDatas( BForecastMakeLog _bForecastMakeLog4new, BSysPeriod _bSysPeriod ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
		
		if( _bForecastMakeLog4new == null || _bForecastMakeLog4new.getForecastMakeLogProOrgs() == null || _bForecastMakeLog4new.getForecastMakeLogProOrgs().isEmpty()
				|| _bForecastMakeLog4new.getForecastMakeLogAuditItems() == null || _bForecastMakeLog4new.getForecastMakeLogAuditItems().isEmpty() )
		{
			return;
		}
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			DaoSystem daoSystem = new DaoSystem( session );
			DaoForecastData daoForecastData = new DaoForecastData( session );
			
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();

			ForecastMakeLogBDConvertor forecastMakeLogBDConvertor = new ForecastMakeLogBDConvertor();

			ForecastMakeLog forecastMakeLog_new = forecastMakeLogBDConvertor.btod( _bForecastMakeLog4new, true, false, true );
			forecastMakeLog_new.setSubmitTime( daoSystem.getSysTimeAsTimeStamp() );

			//	DetailProOrgId	begin
			HashMap<String, AProOrg> hmap4DetailProOrgs = UtilProOrg.getDetailProOrgs( _bForecastMakeLog4new.getForecastMakeLogProOrgs(), session, true );
			//String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( hmap4DetailProOrgs );
			String sqlStr ="";
			if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				sqlStr= "(productid,organizationid) in " + UtilProOrg.getIdsScopeStr4ProOrgs( hmap4DetailProOrgs );	
			}
			else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER) ||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				sqlStr = UtilProOrg.getIds4msslqStr4ProOrgs(hmap4DetailProOrgs);
			}
			//	DetailProOrgId	end

			//	BizDataId	begin
			List<Long> list4BizDataIds = new ArrayList<Long>();
			Iterator<BForecastMakeLogAuditItem> itr_BForecastMakeLogAuditItems = _bForecastMakeLog4new.getForecastMakeLogAuditItems().iterator();
			while( itr_BForecastMakeLogAuditItems.hasNext() )
			{
				BForecastMakeLogAuditItem bForecastMakeLogAuditItem = itr_BForecastMakeLogAuditItems.next();
				if( bForecastMakeLogAuditItem.getBizData().getType() == BizConst.BIZDATA_TYPE_FCHAND )
				{
					list4BizDataIds.add( bForecastMakeLogAuditItem.getBizData().getId() );
				}
			}
			//	BizDataId	end

			//	预测数据		begin
			List<ForecastData> list4forecastData_audit = daoForecastData.getForecastDatas( sqlStr, _bSysPeriod.getForecastRunPeriod(), SysConst.PERIOD_NULL, list4BizDataIds );
			if( list4forecastData_audit != null )
			{
				for( int i=0; i<list4forecastData_audit.size(); i++ )
				{
					ForecastData forecastData_upd = list4forecastData_audit.get( i );
					
					forecastData_upd.setUpdateTime( currentTime );
					
					if( forecastMakeLog_new.getActionType() == BizConst.FORECASTMAKELOG_ACTIONTYPE_INACTIVATE )
					{
						forecastData_upd.setStatus( BizConst.FORECASTDATA_STATUS_INACTIVE );
					}
					else if( forecastMakeLog_new.getActionType() == BizConst.FORECASTMAKELOG_ACTIONTYPE_ACTIVATE )
					{
						forecastData_upd.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
					}
					else
					{
						Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OUT_OF_DESIGN );
						Exception ex = new Exception( cause );
						throw ex;
					}
				}
			}
			//	预测数据		end

			//	持久化到数据库	begin
			//	ForecastData是查询出来的，会自动更新，不必显式调用
			
			//	ForecastMakeLog	begin
			DaoForecastMakeLog daoForecastMakeLog = new DaoForecastMakeLog( session );
			DaoForecastMakeLogProOrg daoForecastMakeLogProOrg = new DaoForecastMakeLogProOrg( session );
			DaoForecastMakeLogAuditItem daoForecastMakeLogAuditItem = new DaoForecastMakeLogAuditItem( session );
			
			daoForecastMakeLog.save( forecastMakeLog_new );
			
			if( forecastMakeLog_new.getForecastMakeLogProOrgs() != null && !(forecastMakeLog_new.getForecastMakeLogProOrgs().isEmpty()) )
			{
				Iterator<ForecastMakeLogProOrg> itr_ForecastMakeLogProOrgs = forecastMakeLog_new.getForecastMakeLogProOrgs().iterator();
				while( itr_ForecastMakeLogProOrgs.hasNext() )
				{
					daoForecastMakeLogProOrg.save( itr_ForecastMakeLogProOrgs.next() );
				}
			}
			
			if( forecastMakeLog_new.getForecastMakeLogAuditItems() != null && !(forecastMakeLog_new.getForecastMakeLogAuditItems().isEmpty()) )
			{
				Iterator<ForecastMakeLogAuditItem> itr_ForecastMakeLogAuditItems = forecastMakeLog_new.getForecastMakeLogAuditItems().iterator();
				while( itr_ForecastMakeLogAuditItems.hasNext() )
				{
					daoForecastMakeLogAuditItem.save( itr_ForecastMakeLogAuditItems.next() );
				}
			}			
			//	ForecastMakeLog	end
			
			
			//	持久化到数据库	end

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
	// forecastData end

	// forecastMakeLog begin
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getForecastMakeLogsStat( String _sqlRestriction ) throws Exception
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
			DaoForecastMakeLog daoForecastMakeLog = new DaoForecastMakeLog( session );
			rst = daoForecastMakeLog.getForecastMakeLogsStat( _sqlRestriction );			
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
	 * @param _blWithProOrgs
	 * @param _blWithHfcItems
	 * @param _blWithAuditItems
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public List<BForecastMakeLog> getForecastMakeLogs( String _sqlRestriction, boolean _blWithProOrgs, boolean _blWithHfcItems, boolean _blWithAuditItems, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastMakeLog> rstList = new ArrayList<BForecastMakeLog>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastMakeLog daoForecastMakeLog = new DaoForecastMakeLog( session );
			List<ForecastMakeLog> listForecastMakeLog_inDB = daoForecastMakeLog.getForecastMakeLogs( _sqlRestriction, _pageIndex, _pageSize  );

			if( listForecastMakeLog_inDB != null && listForecastMakeLog_inDB.iterator() != null )
			{
				ForecastMakeLogBDConvertor forecastMakeLogBDConvertor = new ForecastMakeLogBDConvertor();
				for( int i=0; i<listForecastMakeLog_inDB.size(); i++ )
				{
					rstList.add( forecastMakeLogBDConvertor.dtob( listForecastMakeLog_inDB.get( i ), _blWithProOrgs, _blWithHfcItems, _blWithAuditItems ) );
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BForecastMakeLog getForecastMakeLog(Long id ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end

		BForecastMakeLog log = null;
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastMakeLog daoForecastMakeLog = new DaoForecastMakeLog( session );
			ForecastMakeLog forecastMakeLog_inDB = daoForecastMakeLog.getForecastMakeLog( id);
			ForecastMakeLogBDConvertor forecastMakeLogBDConvertor = new ForecastMakeLogBDConvertor();				
			log =  forecastMakeLogBDConvertor.dtob( forecastMakeLog_inDB, true, true, true ) ;		
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

		return log;

	}
	
	// forecastMakeLog end

	// forecastErrorMappingModel begin
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getForecastErrorMappingModelsStat( String _sqlRestriction ) throws Exception
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
			DaoForecastErrorMappingModel daoForecastErrorMappingModel = new DaoForecastErrorMappingModel( session );
			rst = daoForecastErrorMappingModel.getForecastErrorMappingModelsStat( _sqlRestriction );			
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
	public List<BForecastErrorMappingModel> getForecastErrorMappingModels( String _sqlRestriction, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastErrorMappingModel> rstList = new ArrayList<BForecastErrorMappingModel>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastErrorMappingModel daoForecastErrorMappingModel = new DaoForecastErrorMappingModel( session );
			List<ForecastErrorMappingModel> listForecastErrorMappingModel_inDB = daoForecastErrorMappingModel.getForecastErrorMappingModels( _sqlRestriction, _pageIndex, _pageSize  );

			if( listForecastErrorMappingModel_inDB != null && listForecastErrorMappingModel_inDB.iterator() != null )
			{
				ForecastErrorMappingModelBDConvertor forecastErrorMappingModelBDConvertor = new ForecastErrorMappingModelBDConvertor();
				for( int i=0; i<listForecastErrorMappingModel_inDB.size(); i++ )
				{
					rstList.add( (BForecastErrorMappingModel) forecastErrorMappingModelBDConvertor.dtob( listForecastErrorMappingModel_inDB.get( i ) ) );
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
	// forecastErrorMappingModel end
	
	//	ForecastRunTask	begin
	public BForecastRunTask newForecastRunTask( BForecastRunTask _bForecastRunTask4new ) throws Exception
	{		
		//	检查服务器状态是否可以提供服务	begin
		//	期间滚动、模型运行时，不可以创建预测运行任务
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
	
		if( _bForecastRunTask4new == null )
		{
			return null;
		}
		
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bForecastRunTask4new.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
				
		BForecastRunTask bForecastRunTask_rst = null;
		
		ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
		ForecastRunTask forecastRunTask_new = forecastRunTaskBDConvertor.btod( _bForecastRunTask4new, true );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask( session );
			
			daoForecastRunTask.save( forecastRunTask_new );
			
			if( forecastRunTask_new.getForecastRunTaskItems() != null && !(forecastRunTask_new.getForecastRunTaskItems().isEmpty()) )
			{
				DaoForecastRunTaskItem daoForecastRunTaskItem = new DaoForecastRunTaskItem( session );
				Iterator<ForecastRunTaskItem> itr_ForecastRunTaskItem = forecastRunTask_new.getForecastRunTaskItems().iterator();
				while( itr_ForecastRunTaskItem.hasNext() )
				{
					daoForecastRunTaskItem.save( itr_ForecastRunTaskItem.next() );
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

		bForecastRunTask_rst = forecastRunTaskBDConvertor.dtob( forecastRunTask_new, true );
		
		//	设置系统状态		begin
		//	ForecastRunTaskManagerThread 下次醒来时会处理运行任务
		ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_RUNNINGFORECAST );
		//	设置系统状态		end		
		
		return bForecastRunTask_rst;
	}
	
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getForecastRunTasksStat( String _sqlRestriction ) throws Exception
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
			DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask( session );
			rst = daoForecastRunTask.getForecastRunTasksStat( _sqlRestriction );			
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
	public List<BForecastRunTask> getForecastRunTasks( String _sqlRestriction, boolean _blForecastRunTaskItems, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BForecastRunTask> rstList = new ArrayList<BForecastRunTask>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask( session );
			List<ForecastRunTask> listForecastRunTask_inDB = daoForecastRunTask.getForecastRunTasks( _sqlRestriction, _pageIndex, _pageSize );
			
			if( listForecastRunTask_inDB != null && !(listForecastRunTask_inDB.isEmpty()) )
			{
				ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
				for( int i=0; i<listForecastRunTask_inDB.size(); i++ )
				{
					rstList.add( forecastRunTaskBDConvertor.dtob( listForecastRunTask_inDB.get( i ), _blForecastRunTaskItems ) );
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
	
	
	public BForecastRunTask getLatestForecastRunTask( BSysPeriod _bSysPeriod ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		//	不必检查服务器状态，检查的话就可能查不了了
		//	ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end		
				
		BForecastRunTask rstBForecastRunTask = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask( session );
			ForecastRunTask latestForecastRunTask_inDB = daoForecastRunTask.getLatestForecastRunTask( _bSysPeriod.getCompilePeriod() );
			
			if( latestForecastRunTask_inDB != null )
			{
				ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
				rstBForecastRunTask = forecastRunTaskBDConvertor.dtob( latestForecastRunTask_inDB, true );
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

		return rstBForecastRunTask;
	}		
	//	ForecastRunTask	end

	public boolean dealForecastInst()throws Exception
	{
		logger.info("开始处理预测策略与维度关联关系");
		ForecastDataDM dm = new ForecastDataDM();
		dm.deleteForecastInstView();
		HashMap<String,ForecastInst> hm_setting = new HashMap<String, ForecastInst>();
		List<ForecastInst> listForecastInst_inDB = new ArrayList<ForecastInst>();
		Long instid=-1L;
		List<ABForecastInstView> list_view = new ArrayList<ABForecastInstView>();
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			logger.info("开始获取所有的预测策略");
			listForecastInst_inDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );
			logger.info("预测策略获取完毕");
			logger.info("开始获取所有策略范围下的明细维度");
			for(ForecastInst inst :listForecastInst_inDB)
			{
				String strKey = "" + inst.getFinalFcBizData().getId() 
				+ "_" + inst.getFcPeriodNum() 
				+ "_" + inst.getFzPeriodNum()
				+ "_" + inst.getDecomposeFormula()  //modofy by zhangzy 使用判断预测数据分解规则区分
				+ "_" + inst.getDistributeRefBizData().getId()
				+ "_" + inst.getDistributeRefPeriodNum();
				if(hm_setting.containsKey(strKey))
				{
					instid = hm_setting.get(strKey).getId();
				}
				else
				{
					hm_setting.put(strKey, inst);
					instid=inst.getId();
				}
				for(ForecastInstProOrg proorg:inst.getForecastInstProOrgs())
				{
					list_view.addAll(dm.buildDetailProOrg(proorg.getProduct().getId(),proorg.getOrganization().getId(),instid));
				}
			}
			logger.info("获取所有策略范围下的明细维度完毕");
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
		
		dm.buildForecastInstView(list_view);
		logger.info("预测策略与维度关联关系处理完毕");
		return true;
	}
	
	
}

/**************************************************************************
 * 
 * $RCSfile: ForecastService.java,v $ $Revision: 1.13 $ $Date: 2010/08/05 08:43:45 $
 * 
 * Revision 1.12  2010/08/03 11:15:38  liuzhen
 * 2010.08.03 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/04 07:26:56  liuzhen
 * 2010.07.04 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * 
 ***************************************************************************/
