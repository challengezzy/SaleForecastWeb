/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataProcesser.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc.standard;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.IThreadProcess;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryData;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizDataDefItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnit;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnitGroup;


/**
 * <li>Title: InterfaceStandardHistoryDataProcesser.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class InterfaceStandardHistoryDataProcesser implements IThreadProcess
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private ABImInHistoryData data = null;
	private InterfaceStandardHistoryDataMgmt mgmt = null;
	
	public InterfaceStandardHistoryDataProcesser(ABImInHistoryData data,InterfaceStandardHistoryDataMgmt mgmt)
	{
		this.data = data;
		this.mgmt = mgmt;
	}
	
	@Override
	public Object doProcess()
	{
		logger.info("正在导入一条历史数据");
				
		BizData bizData_History = null;
		BizData bizData_HistoryAd = null;
		List<Long> listBizDataId_HistoryAdR = new ArrayList<Long>();

		String importResult = "";		

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;			
		try
		{
			trsa = session.beginTransaction();
			
			DaoBizData daobizdata = new DaoBizData(session);
			bizData_History = daobizdata.getBizDataByCode(data.getbizdatacode());
			if(bizData_History ==null)
			{
				importResult = "bizdatacode :Can not find BizData by the Code";
				data.setresult( importResult );	
				return data;
			}
			if(bizData_History.getType()!=BizConst.BIZDATA_TYPE_HISTORY)
			{
				importResult = "bizdatacode :BizData type error";
				data.setresult( importResult );	
				return data;
			}
			
			DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem( session );
			//	查询历史类业务数据对应的历史调整类业务数据和调整原因类业务数据	begin
			bizData_HistoryAd = daoBizDataDefItem.getBizDataHistoryAdByBizDataHistoryId( bizData_History.getId() );
			listBizDataId_HistoryAdR = new ArrayList<Long>();
			if( bizData_HistoryAd != null )
			{
				List<BizData> listBizData_HistoryAdR = daoBizDataDefItem.getBizDataHistoryAdRByBizDataHistoryAdId( bizData_HistoryAd.getId() );
				if( listBizData_HistoryAdR != null )
				{
					for(int i=0; i<listBizData_HistoryAdR.size(); i++)
					{
						listBizDataId_HistoryAdR.add( listBizData_HistoryAdR.get( i ).getId() );
					}
				}
			}
			//	查询历史类业务数据对应的历史调整类业务数据和调整原因类业务数据	end	
			
			
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );
			
			DaoProduct daoProduct = new DaoProduct( session );
			DaoOrganization daoOrganization = new DaoOrganization( session );

			boolean productOk = true;
			boolean orgOk = true;
			
			Product detailProduct = null;
			Organization detailOrganization = null;

			// 产品检查
			detailProduct = daoProduct.getDetailProductByCode( data.getproductcode() );
			if( detailProduct == null )
			{
				importResult = importResult +" " + "productcode :Can not find Detail Product by the Code";
				data.setresult( importResult );					
				productOk= false;					
			}
			
			// 组织检查
			detailOrganization = daoOrganization.getDetailOrganizationByCode( data.getorganizationcode() );
			if( detailOrganization == null )
			{
				importResult = importResult +" "+ "organizationcode :Can not find Detail Organization by the Code";
				data.setresult( importResult );					
				orgOk = false;
			}	
			if(productOk == false && orgOk==false)
			{
				data.setwarninfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_3);
				return data;
			}
			else if(productOk == false && orgOk==true)
			{
				data.setwarninfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_1);
				return data;
			}
			else if(productOk ==  true && orgOk == false)
			{
				data.setwarninfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_2);
				return data;
			}
			else if(productOk == true && orgOk == true)//判断产品和组织都有,但是在下期预测有效范围之外的
			{		
				BSysParam param = ServerEnvironment.getInstance().getSysParam(BizConst.SYSPARAM_CODE_HISTORYDATAIMPORTWARN);
				if(param!=null && Integer.parseInt(param.getValue()) == BizConst.GLOBAL_YESNO_YES)
				{
					AProOrg aProOrg = new AProOrg();
					aProOrg.setProduct(detailProduct);
					aProOrg.setOrganization(detailOrganization);	
					boolean isContains = false;
					for(AProOrg aProOrg_: mgmt.getlistProOrg4FrorecastIns())
					{
						int relation= UtilProOrg.relationOf_2(aProOrg_, aProOrg);
						if(relation ==UtilProOrg.RELATION_FIRST2SECOND_COVERING || relation ==UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
						{
							isContains=true;
							break;
						}
					}
					if(!isContains)
					{
						data.setwarninfo( BizConst.HISTORYDATA_IMPORT_WARNINFO_4 );							
					}
				}				
			}		
			
			//期间检查
			int period = data.getperiod();
			if(UtilPeriod.checkPeriod(period) == false)
			{
				importResult = "period :period is error ";
				data.setresult( importResult );
				return data;
			}
			
			//单位组检查
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup(session);
			UnitGroup group = daoUnitGroup.getUnitGroupByCode(data.getunitgroupcode());
			if(group == null)
			{
				importResult = "unitgroupcode :Can not find UnitGroup by the Code";
				data.setresult(importResult);
				return data;
			}
			//单位检查
			Unit base_unit =null;
			if(group.getUnits().size()>0)
			{
				for(Unit unit:group.getUnits())
				{
					if(unit.getIsBase()==BizConst.GLOBAL_YESNO_YES)
					{
						base_unit = unit;
					}
				}
			}
			if(base_unit==null)
			{
				importResult = "unitgroupcode :Can not find Base Unit";
				data.setresult(importResult);
				return data;
			}
			DaoUnit daoUnit = new DaoUnit(session);
			Unit unit = daoUnit.getUnitByCode(data.getunitcode(),group);
			if(unit == null)
			{
				importResult = "unitcode :Can not find Unit by the Code";
				data.setresult(importResult);
				return data;
			}
			
			//单位和单位组联合检查
			if(unit.getUnitGroup().getId() != group.getId())
			{
				importResult = "unitgroupcode :Unit is not belong to UnitGroup";
				data.setresult(importResult);
				return data;
			}
			//产品和单位组
			if( detailProduct.getUnitGroup().getId().longValue() != group.getId().longValue() )
			{
				importResult = "unitgroupcode :Detail Product's UOM Group does not match with that of parameter";
				data.setresult( importResult );	
				return data;							
			}
				
			
	
			//单位转换begin
			data.exchangeUnit(unit.getExchangeRate(),base_unit.getExchangeRate());
			//单位转换begin
			// 处理数据 begin
			List<HistoryData> listHistoryData_new = new ArrayList<HistoryData>();
			List<HistoryData> listHistoryData_upd = new ArrayList<HistoryData>();
			List<HistoryData> listHistoryData_del = new ArrayList<HistoryData>();
	
			HistoryData historyData_inDB = daoHistoryData.getHistoryData( detailProduct.getId(), detailOrganization.getId(), period, bizData_History.getId() );
			if( historyData_inDB == null )
			{
				//	新建历史数据		begin
				HistoryData historyData_new = new HistoryData();
				historyData_new.setProduct( detailProduct );
				historyData_new.setOrganization( detailOrganization );
				historyData_new.setPeriod( period );
				historyData_new.setBizData( bizData_History );
				historyData_new.setValue( data.getvalue() );
				
				listHistoryData_new.add( historyData_new );
				//	新建历史数据		end
				
				//	新建历史调整数据		begin
				HistoryData historyData_new_Ad = new HistoryData();
				historyData_new_Ad.setProduct( detailProduct );
				historyData_new_Ad.setOrganization( detailOrganization );
				historyData_new_Ad.setPeriod( period );
				historyData_new_Ad.setBizData( bizData_HistoryAd );
				historyData_new_Ad.setValue( data.getvalue() );
				
				listHistoryData_new.add( historyData_new_Ad );						
				//	新建历史调整数据		end							
			}
			else
			{
				//	更新历史数据		begin
				HistoryData historyData_upd = historyData_inDB;
				historyData_upd.setValue( data.getvalue() );
				listHistoryData_upd.add( historyData_upd );
				//	更新历史数据		end
				
				//	更新历史调整数据		begin
				HistoryData historyData_upd_Ad = daoHistoryData.getHistoryData( detailProduct.getId(), detailOrganization.getId(), period, bizData_HistoryAd.getId() );
				if( historyData_upd_Ad == null )
				{
					//	新建历史调整数据		begin	
					HistoryData historyData_new_Ad = new HistoryData();
					historyData_new_Ad.setProduct( detailProduct );
					historyData_new_Ad.setOrganization( detailOrganization );
					historyData_new_Ad.setPeriod( period );
					historyData_new_Ad.setBizData( bizData_HistoryAd );
					historyData_new_Ad.setValue( data.getvalue() );
					
					listHistoryData_new.add( historyData_new_Ad );	
					//	新建历史调整数据		begin	
				}
				else
				{
					//	更新历史调整数据	begin
					historyData_upd_Ad.setValue( data.getvalue() );
					listHistoryData_upd.add( historyData_upd_Ad );
					//	更新历史调整数据	end
					
					//	删除历史调整原因类数据	begin
					if( !(listBizDataId_HistoryAdR.isEmpty()) )
					{
						List<HistoryData> listHistoryData_inDB_AdR = daoHistoryData.getHistoryDatas( detailProduct.getId(), detailOrganization.getId(), period, listBizDataId_HistoryAdR );
						if( listHistoryData_inDB_AdR != null )
						{
							listHistoryData_del.addAll( listHistoryData_inDB_AdR );
						}
					}
					//	删除历史调整原因类数据	end
				}
				//	更新历史调整数据		end						
			}
			
			// 处理数据 end
			
			// 调用持久化方法 begin
			if( !(listHistoryData_new.isEmpty()) )
			{
				for( int j=0; j<listHistoryData_new.size(); j++ )
				{
					daoHistoryData.save( listHistoryData_new.get( j ) );
				}					
			}
			
			if( !(listHistoryData_upd.isEmpty()) )
			{
				for( int j=0; j<listHistoryData_upd.size(); j++ )
				{
					daoHistoryData.update( listHistoryData_upd.get( j ) );
				}					
			}	
			
			if( !(listHistoryData_del.isEmpty()) )
			{
				for( int j=0; j<listHistoryData_del.size(); j++ )
				{
					daoHistoryData.delete( listHistoryData_del.get( j ) );
				}					
			}
			
			trsa.commit();
			// 调用持久化方法 end

			//	导入成功	begin
			importResult = BizConst.IMPORT_RESULT_SUCCESS;
			data.setresult( importResult );
			
			return data;
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
				importResult = ex.getCause().getMessage();
			}
			else
			{
				importResult = ex.getMessage();
			}
			data.setresult( importResult );
			return data;
		}
		finally
		{
			session.close();
		}
	}

	@Override
	public Object doComplete()
	{
		logger.info("导入一条历史数据完成");
		mgmt.doResult(data );
		return null;
	}

	@Override
	public Object doStart()
	{
		logger.info("导入一条历史数据");
		return null;
	}

}

/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataProcesser.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:InterfaceStandardHistoryDataProcesser.java,v $
 *********************************************************************/