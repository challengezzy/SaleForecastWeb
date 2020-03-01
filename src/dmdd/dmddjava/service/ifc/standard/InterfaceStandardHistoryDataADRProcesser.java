/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataADRProcesser.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc.standard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.IThreadProcess;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryDataADR;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnit;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoUnitGroup;

/**
 * <li>Title: InterfaceStandardHistoryDataADRProcesser.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class InterfaceStandardHistoryDataADRProcesser implements IThreadProcess
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private ABImInHistoryDataADR data = null;
	private InterfaceStandardhistoryDataADRMgmt mgmt = null;
	
	public InterfaceStandardHistoryDataADRProcesser(ABImInHistoryDataADR data,InterfaceStandardhistoryDataADRMgmt mgmt)
	{
		this.data = data;
		this.mgmt = mgmt;
	}
	
	@Override
	public Object doProcess()
	{
		logger.info("正在导入一条历史修正原因数据");	
		
		HashMap<String, List<HistoryData>> hmap_ppcoocpb_list4HistoryData4Ad = new HashMap<String, List<HistoryData>>();
		HashMap<String, Double> hmap_ppcoocpb_sum4HistoryData4Ad = new HashMap<String, Double>();
		HashMap<String, Double> hmap_popb_incrementValue4HisrotyData4Ad = new HashMap<String, Double>();
		
		
		List<HistoryData> listHistoryData_AdR_new = new ArrayList<HistoryData>();
		List<HistoryData> listHistoryData_AdR_upd = new ArrayList<HistoryData>();
		
		BizData historyadr ;
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			DaoBizData daoBizData = new DaoBizData(session);
			historyadr = daoBizData.getBizDataByCode(data.getbizdatacode());
			if(historyadr==null)
			{
				data.setresult( "bizdatacode :Can not find BizData by the Code" );	
				return data;
			}
			if(historyadr.getType() != BizConst.BIZDATA_TYPE_HISTORYADR)
			{
				data.setresult( "bizdatacode :BizData type error" );	
				return data;
			}
			
			DaoHistoryData daoHistoryData = new DaoHistoryData(session);
			
			DaoProduct daoProduct = new DaoProduct( session );
			DaoOrganization daoOrganization = new DaoOrganization( session );
			DaoUnitGroup daoUnitGroup = new DaoUnitGroup(session);
			DaoUnit daoUnit = new DaoUnit(session);
			Product queryProduct = null;
			Organization queryOrganization = null;
			String importResult ="";
			int period ;
			
		
			//数据有效性验证，并把业务范围收集起来 begin
			queryProduct = daoProduct.getProductByCode( data.getproductcode());
			queryOrganization = daoOrganization.getOrganizationByCode(  data.getorganizationcode() );
			if( queryProduct == null )
			{
				importResult = "productcode :Can not find  Product by the Code";
				data.setresult( importResult );	
				return data;	
			}
			if( queryOrganization == null )
			{
				importResult = "organizationcode:Can not find  Organization by the Code";
				data.setresult( importResult );	
				return data;		
			}	
			if( queryProduct.getUnitGroup() == null )
			{
				importResult = "productcode :Detail Product has no UnitGroup";
				data.setresult( importResult );	
				return data;					
			}
			UnitGroup unitGroup = daoUnitGroup.getUnitGroupByCode(data.getunitgroupcode());
			if(unitGroup == null)
			{
				importResult = "unitgroupcode :Can not find UnitGroup by the Code";
				data.setresult( importResult );	
				return data;
			}
			
			if( queryProduct.getUnitGroup().getId().longValue() != unitGroup.getId().longValue() )
			{
				importResult = "unitgroupcode :Detail Product's UOM Group does not match with that of parameter";
				data.setresult( importResult );	
				return data;							
			}
			Unit base_unit =null;
			if(unitGroup.getUnits().size()>0)
			{
				for(Unit unit:unitGroup.getUnits())
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
			Unit unit = daoUnit.getUnitByCode(data.getunitcode(),unitGroup);
			if(unit == null)
			{
				importResult = "unitcode :Can not find Unit by the Code";
				data.setresult(importResult);
				return data;
			}
			
			if(unit.getUnitGroup().getId() != unitGroup.getId())
			{
				importResult = "unitgroupcode :Unit is not belong to UnitGroup";
				data.setresult(importResult);
				return data;
			}
			
			period = data.getperiod();
			if(UtilPeriod.checkPeriod(period) == false)
			{
				importResult = "period :period is error ";
				data.setresult( importResult );
				return data;
			}
			
			//数据有效性验证，并把业务范围收集起来 end.
			//单位转换begin
			data.exchangeUnit(unit.getExchangeRate(),base_unit.getExchangeRate());
			//单位转换begin
			Double old_value = 0d;
			
			String detailProOrgIdStr = UtilProOrg.getProOrgIds( queryProduct, queryOrganization );
			
//				查询已有的 historyData_AdR	begin
			List<HistoryData> listHistoryData_AdR = daoHistoryData.getHistoryDatas( detailProOrgIdStr, period, historyadr.getId() );
			HashMap<String, HistoryData> hmap_pop_HistoryData_AdR = new HashMap<String, HistoryData>();
			if( listHistoryData_AdR != null )
			{
				for(int i=0 ;i<listHistoryData_AdR.size(); i++)
				{
					HistoryData historyData_AdR = listHistoryData_AdR.get( i );
					String strKey4pop = UtilStrKey.getStrKey4POPB( historyData_AdR.getProduct(), historyData_AdR.getOrganization(), historyData_AdR.getPeriod(), null );
					hmap_pop_HistoryData_AdR.put( strKey4pop, historyData_AdR );
					old_value =old_value + historyData_AdR.getValue();
				}					
			}
			//	查询已有的 historyData_AdR	end
			

			//	获得相应的 historyData_Ad 以进行分解	begin
			BizData bizData_historyAd = ((BizDataDefItemHistoryAdR)(historyadr.getBizDataDefItems().iterator().next())).getHistoryAdBizData();
			String strKey4ppcoocpb = UtilStrKey.getStrKey4PPcOOcPB(queryProduct, null, queryOrganization, null,period, bizData_historyAd );
			
			List<HistoryData> listHistoryData_Ad = hmap_ppcoocpb_list4HistoryData4Ad.get( strKey4ppcoocpb );
			if( listHistoryData_Ad == null )
			{
				//	没有查询过，从数据库查询
				listHistoryData_Ad = daoHistoryData.getHistoryDatas( detailProOrgIdStr, period, bizData_historyAd.getId() );
				if( listHistoryData_Ad == null || listHistoryData_Ad.isEmpty() )
				{
					importResult = "Can not find historydata ";
					data.setresult( importResult );
					return data;	
				}
				hmap_ppcoocpb_list4HistoryData4Ad.put( strKey4ppcoocpb, listHistoryData_Ad );
				
				Double sum_Ad = 0d;
				for( int i=0; i<listHistoryData_Ad.size(); i=i+1 )
				{
					sum_Ad = sum_Ad + listHistoryData_Ad.get( i ).getValue();
				}
				hmap_ppcoocpb_sum4HistoryData4Ad.put( strKey4ppcoocpb, sum_Ad );
			}
			Double sum_Ad = hmap_ppcoocpb_sum4HistoryData4Ad.get( strKey4ppcoocpb );
			//	获得相应的 historyData_Ad 以进行分解	end
			
			//	分解: 创建和更新historyData_AdR，并收集对 historyData_Ad 的增量以便后面更新historyData_Ad	begin
			int sign = 1;	// 这个符号值,用于使 totalIncrementValue distributedIncrementValue incrementValue  为正值,分解时分的恰好
			Double totalIncrementValue = data.getvalue()-old_value;; //这里认为是直接导入value，不管以前是什么值。
			if( totalIncrementValue < 0 )
			{
				sign = -1;
				totalIncrementValue = 0-totalIncrementValue;
			}
			
			Double distributedIncrementValue = 0d;
			for( int i=0; i<listHistoryData_Ad.size(); i++ )
			{
				//	这里的分解是以 HistoryData_Ad为核心，有HistoryData_Ad才会有HistoryData_AdR的量
				HistoryData historyData_Ad = listHistoryData_Ad.get( i );
				
				Double incrementValue = 0d;
				
				if( sum_Ad.longValue() == 0 )
				{
					//	均分
					//incrementValue = Math.round( totalIncrementValue * 1.0/listHistoryData_Ad.size() );
					//modify by zhangzy 20150413 修改为支持小数的形式
					incrementValue =  totalIncrementValue * 1.0/listHistoryData_Ad.size() ;
				}
				else
				{
					//incrementValue = Math.round( totalIncrementValue * (historyData_Ad.getValue() * 1.0 / sum_Ad) );
					//modify by zhangzy 20150413 修改为支持小数的形式
					incrementValue =  totalIncrementValue * (historyData_Ad.getValue() * 1.0 / sum_Ad) ;
				}
				distributedIncrementValue = distributedIncrementValue + incrementValue;
				
				//	保证分净且不超出	begin
				if( distributedIncrementValue > totalIncrementValue )
				{
					incrementValue = incrementValue - (distributedIncrementValue-totalIncrementValue);
					distributedIncrementValue = totalIncrementValue;
				}
				
				if( i == (listHistoryData_Ad.size()-1) )
				{
					//	最后一个
					if( distributedIncrementValue < totalIncrementValue )
					{
						incrementValue = incrementValue + (totalIncrementValue - distributedIncrementValue);
						distributedIncrementValue = totalIncrementValue;
					}
				}
				//	保证分净且不超出	end
				
				String strKey4pop = UtilStrKey.getStrKey4POPB( historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(), null );
				HistoryData historyData_AdR = hmap_pop_HistoryData_AdR.get( strKey4pop );
				if( historyData_AdR == null )
				{
					//	新建
					historyData_AdR = new HistoryData();
					historyData_AdR.setProduct( historyData_Ad.getProduct() );
					historyData_AdR.setOrganization( historyData_Ad.getOrganization() );
					historyData_AdR.setPeriod( historyData_Ad.getPeriod() );
					historyData_AdR.setBizData( historyadr );
					historyData_AdR.setValue( sign*incrementValue );
					
					listHistoryData_AdR_new.add( historyData_AdR );
				}
				else
				{
					//	更新
					historyData_AdR.setValue( historyData_AdR.getValue() + sign*incrementValue );//更新也视为是覆盖
					
					listHistoryData_AdR_upd.add( historyData_AdR );
				}
				
				//	收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个 historyData_Ad起作用	begin
				String strKey4popb = UtilStrKey.getStrKey4POPB( historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(), historyData_Ad.getBizData() );
				Double incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get( strKey4popb );
				if( incremtnValue4HistoryData4Ad == null )
				{
					incremtnValue4HistoryData4Ad = sign*incrementValue;
				}
				else
				{
					incremtnValue4HistoryData4Ad = incremtnValue4HistoryData4Ad + sign*incrementValue;
				}
				hmap_popb_incrementValue4HisrotyData4Ad.put( strKey4popb, incremtnValue4HistoryData4Ad );
				//	收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个 historyData_Ad起作用	end
				
			}
			//更新历史数据和历史修正类 end.
			
			
		
			//持久化到数据库	begin
			//	更新 historyData_Ad	begin
			if( hmap_ppcoocpb_list4HistoryData4Ad.values() != null && !(hmap_ppcoocpb_list4HistoryData4Ad.values().isEmpty()) )
			{
				Iterator<List<HistoryData>> itr_hmap_ppcoocpb_list4HistoryData4Ad_values = hmap_ppcoocpb_list4HistoryData4Ad.values().iterator();
				while( itr_hmap_ppcoocpb_list4HistoryData4Ad_values.hasNext() )
				{
					List<HistoryData> listHistoryData4Ad = itr_hmap_ppcoocpb_list4HistoryData4Ad_values.next();
					if( listHistoryData4Ad != null && !(listHistoryData4Ad.isEmpty()) )
					{
						for( int i=0; i<listHistoryData4Ad.size(); i=i+1 )
						{
							HistoryData historyData_Ad_upd = listHistoryData4Ad.get( i );
							String strKey4popb = UtilStrKey.getStrKey4POPB( historyData_Ad_upd.getProduct(), historyData_Ad_upd.getOrganization(), historyData_Ad_upd.getPeriod(), historyData_Ad_upd.getBizData() );
							Double incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get( strKey4popb );
							if( incremtnValue4HistoryData4Ad != null )
							{
								//	HistoryData_AdR的增加值是 HistoryData_Ad 的减少值: HistoryData = HistoryData_Ad + HistoryData_AdR 
								historyData_Ad_upd.setValue( historyData_Ad_upd.getValue() - incremtnValue4HistoryData4Ad  );
								daoHistoryData.update( historyData_Ad_upd );
							}
						}
					}
				}
			}
			//	更新 historyData_Ad	end
			
			if( listHistoryData_AdR_new != null )
			{
				for( int i=0; i<listHistoryData_AdR_new.size(); i++ )
				{
					daoHistoryData.save( listHistoryData_AdR_new.get( i ) );
				}
			}
			
			if( listHistoryData_AdR_upd != null )
			{
				for( int i=0; i<listHistoryData_AdR_upd.size(); i++ )
				{
					daoHistoryData.update( listHistoryData_AdR_upd.get( i ) );
				}
			}
	
			trsa.commit();
			//	持久化到数据库	end
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
				
		}
		finally
		{
			session.close();
		}	
	data.setresult( "OK" );
	return data;	
	}

	@Override
	public Object doComplete()
	{
		logger.info("导入一条历史修正原因数据完成");
		mgmt.doResult(data );
		return null;
	}

	@Override
	public Object doStart()
	{
		logger.info("导入一条历史修正原因数据");
		return null;
	}



}

/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataADRProcesser.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:InterfaceStandardHistoryDataADRProcesser.java,v $
 *********************************************************************/