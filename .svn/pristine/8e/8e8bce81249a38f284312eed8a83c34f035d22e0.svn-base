/**
 * 
 */
package dmdd.dmddjava.service.price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABImPriceData;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowPriceData;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.PriceDataBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BPriceData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.PriceData;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoPriceData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;

/**
 * @author liuzhen
 *
 */
public class PriceService
{

	/**
	 * 
	 */
	public PriceService() {
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
	 * 价格导入保存
	 * 前端传递来的价格数据的数据值应该是已经换算成基准单位的数据值
	 * @param _bUnitGroup
	 * @param _list4ABImPriceData
	 * @return
	 * @throws Exception
	 */
	public List<ABImPriceData> savePriceDatas4ImportUI( BUnitGroup _bUnitGroup, List<ABImPriceData> _list4ABImPriceData ,int _selectedPrice) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		List<ABImPriceData> rstList = new ArrayList<ABImPriceData>();

		if( _list4ABImPriceData == null || _list4ABImPriceData.isEmpty() )
		{
			throw new Exception("Paramete is not correct");
		}
		if( _bUnitGroup == null )
		{
			throw new Exception("Paramete is not correct");
		}
		// 检查选择导入何种价格
		if(_selectedPrice == BizConst.GLOBAL_NULL_NULL)
		{
			throw new Exception("Select a type of price");
		}
											
		ABImPriceData abImPriceData = null;
		String importResult = null;		
		
		Iterator<ABImPriceData> itr_list4ABImPriceData = _list4ABImPriceData.iterator();
		while( itr_list4ABImPriceData.hasNext() )
		{
			// 逐行处理，要检查是否是明细层的产品、组织编码
			abImPriceData = itr_list4ABImPriceData.next();		
		
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;			
			try
			{
				trsa = session.beginTransaction();
				
				DaoPriceData daoPriceData = new DaoPriceData( session );
				
				DaoProduct daoProduct = new DaoProduct( session );
				DaoOrganization daoOrganization = new DaoOrganization( session );
	
				// 产品检查
				Product detailProduct = daoProduct.getDetailProductByCode( abImPriceData.getProductCode() );
				if( detailProduct == null )
				{
					importResult = "Can not find Detail Product by the Code";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;
				}
				if( detailProduct.getUnitGroup() == null )
				{
					importResult = "Detail Product has no UnitGroup";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;					
				}
				if( detailProduct.getUnitGroup().getId().longValue() != _bUnitGroup.getId().longValue() )
				{
					importResult = "Detail Product's UOM Group does not match with that of parameter";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;							
				}
					
				// 组织检查
				Organization detailOrganization = daoOrganization.getDetailOrganizationByCode( abImPriceData.getOrganizationCode() );
				if( detailOrganization == null )
				{
					importResult = "Can not find Detail Organization by the Code";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;
				}	

				// 起始期间检查
				int periodBegin = abImPriceData.getPeriodBegin();
				if ( periodBegin == SysConst.PERIOD_NULL )
				{
					importResult = "There is no Begin Period";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;
				}
					
				// 终止期间检查
				int periodEnd = abImPriceData.getPeriodEnd();
				if ( periodEnd == SysConst.PERIOD_NULL )
				{
					importResult = "There is no End Period";
					abImPriceData.setImportResult( importResult );
					rstList.add( abImPriceData );
					continue;
				}				

				// 处理每月数据 begin
				List<PriceData> listPriceData_new = new ArrayList<PriceData>();
				List<PriceData> listPriceData_upd = new ArrayList<PriceData>();
				
				boolean priceError = false;
				
				int periodDiff = UtilPeriod.getPeriodDifference( periodBegin, periodEnd );
				for ( int periodLoc = 0; periodLoc <= periodDiff; periodLoc++ )
				{
					Double price = abImPriceData.pubFun4getPeriodDataValue( periodLoc );
					
					if(price<0)
					{
						importResult = "Price can not be less than 0";
						abImPriceData.setImportResult( importResult );
						rstList.add( abImPriceData );
						priceError = true;
						break;
					}
					
					int period = UtilPeriod.getPeriod( periodBegin, periodLoc );
					
					PriceData priceData_inDB = daoPriceData.getPriceData( detailProduct.getId(), detailOrganization.getId(), period );
					if( priceData_inDB == null )
					{
						//	新建价格数据		begin
						PriceData priceData_new = new PriceData();
						priceData_new.setProduct( detailProduct );
						priceData_new.setOrganization( detailOrganization );
						priceData_new.setPeriod( period );
						if(_selectedPrice==BizConst.PRICE_IMPORT_STANDARDPRICE)
						{
							priceData_new.setStandardPrice( price );
							priceData_new.setRealPrice( 0.0 );
						}
						else if(_selectedPrice==BizConst.PRICE_IMPORT_REALPRICE)
						{
							priceData_new.setStandardPrice( 0.0 );
							priceData_new.setRealPrice( price );
						}
						else if(_selectedPrice==BizConst.PRICE_IMPORT_BOTHPRICE)
						{
							priceData_new.setStandardPrice( price );
							priceData_new.setRealPrice( price );
						}
							
						
						listPriceData_new.add( priceData_new );
						//	新建价格数据		end
					}
					else
					{
						//	更新价格数据		begin
						PriceData priceData_upd = priceData_inDB;
						if(_selectedPrice==BizConst.PRICE_IMPORT_STANDARDPRICE)
						{
							priceData_upd.setStandardPrice( price );
							//priceData_upd.setRealPrice( 0.0 );
						}
						else if(_selectedPrice==BizConst.PRICE_IMPORT_REALPRICE)
						{
							priceData_upd.setRealPrice( price );
							//priceData_upd.setStandardPrice( 0.0 );
						}
						else if(_selectedPrice==BizConst.PRICE_IMPORT_BOTHPRICE)
						{
							priceData_upd.setStandardPrice( price );
							priceData_upd.setRealPrice( price );
						}
						
						listPriceData_upd.add( priceData_upd );
						//	更新价格数据		end				
					}
				}
				if(priceError)
				{
					continue;
				}
				// 处理每月数据 end
				
				// 调用持久化方法 begin
				if( !(listPriceData_new.isEmpty()) )
				{
					for( int j=0; j<listPriceData_new.size(); j++ )
					{
						daoPriceData.save( listPriceData_new.get( j ) );
					}					
				}
				
				if( !(listPriceData_upd.isEmpty()) )
				{
					for( int j=0; j<listPriceData_upd.size(); j++ )
					{
						daoPriceData.update( listPriceData_upd.get( j ) );
					}					
				}	
				trsa.commit();
				// 调用持久化方法 end
				
				//	导入成功	begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImPriceData.setImportResult( importResult );
				
				rstList.add( abImPriceData ); 
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
				abImPriceData.setImportResult( importResult );
				rstList.add( abImPriceData );				
			}
			finally
			{
				session.close();
			}
		}
	
		return rstList;
	}	
	
	
	/**
	 * 价格数据调整，是对 BPriceData 对象直接调整
	 * realPrice和comments会被修改
	 * 前端传递来的 BPriceData 的 realPrice应该是被换算成基准单位的数据
	 * 前端传递来的 BPriceData 的 standardPrice应该是被恢复成查询时的基准单位的数据。这是为了避免界面上换算的误差被持久化到数据库
	 * @param _listBPriceData4save
	 * @throws Exception
	 */
	public void savePriceDatas4AdjustUI( List<BPriceData> _listBPriceData4save ) throws Exception
	{	
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _listBPriceData4save == null || _listBPriceData4save.isEmpty() )
		{
			return;
		}
		
		PriceDataBDConvertor priceDataBDConvertor = new PriceDataBDConvertor();
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoPriceData daoPriceData = new DaoPriceData( session );
			
			//	采用BD转换，然后持久化的方法以进行严格的版本控制
			for( int i=0; i<_listBPriceData4save.size(); i++ )
			{
				daoPriceData.update( priceDataBDConvertor.btod( _listBPriceData4save.get( i ) ) );
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
	}
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 * @throws Exception
	 */
	public int getPriceDatasStat( List<ABProOrg> _list4ABProOrg_detail, int _periodBegin, int _periodEnd) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
//		业务范围下明细产品组织范围	begin
		String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4BProOrgs( _list4ABProOrg_detail );
//		业务范围下明细产品组织范围	end		
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and ( (productid, organizationid) in " + idsScopeStr4DetailProOrgs + " ) ";
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr ="(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and "+ UtilProOrg.format4mssqlstr4ids(idsScopeStr4DetailProOrgs);
		}
		
		int rst = 0;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			DaoPriceData daoPriceData = new DaoPriceData( session );
			rst = daoPriceData.getPriceDatasStat( sqlStr );			
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
	public List<BPriceData> getPriceDatas( List<ABProOrg> _list4ABProOrg_detail, int _periodBegin, int _periodEnd, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BPriceData> rstList = new ArrayList<BPriceData>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		
//		业务范围下明细产品组织范围	begin
		String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4BProOrgs( _list4ABProOrg_detail );
//		业务范围下明细产品组织范围	end		
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and ( (productid, organizationid) in " + idsScopeStr4DetailProOrgs + " ) ";
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr ="(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and "+ UtilProOrg.format4mssqlstr4ids(idsScopeStr4DetailProOrgs);
		}
		
		try
		{
			trsa = session.beginTransaction();
			
			DaoPriceData daoPriceData = new DaoPriceData( session );
			List<PriceData> listPriceData_inDB = daoPriceData.getPriceDatas( sqlStr, _pageIndex, _pageSize  );

			if( listPriceData_inDB != null && listPriceData_inDB.iterator() != null )
			{
				PriceDataBDConvertor priceDataBDConvertor = new PriceDataBDConvertor();
				for( int i=0; i<listPriceData_inDB.size(); i++ )
				{
					rstList.add( (BPriceData) priceDataBDConvertor.dtob( listPriceData_inDB.get( i ) ) );
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
	 * 查询期间范围内 UiRowPriceData
	 * _list4ABProOrg_detail 是明细产品组织
	 * 由于是期间范围内的查询，后台进行分页比较复杂，由前端负责对明细组织产品分组后传递给后台，后台只负责查询 	
	 * @param _list4ABProOrg_detail
	 * @param _periodBegin
	 * @param _periodEnd
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowPriceData> getUiRowPriceDatas( List<ABProOrg> _list4ABProOrg_detail, int _periodBegin, int _periodEnd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<ABUiRowPriceData> rstList = new ArrayList<ABUiRowPriceData>();
		HashMap<String, ABUiRowPriceData> hmap_UiRowPriceData = new HashMap<String, ABUiRowPriceData>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			//	业务范围下明细产品组织范围	begin
			String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4BProOrgs( _list4ABProOrg_detail );
			//	业务范围下明细产品组织范围	end			
			//String sqlRestriction = "(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and ( (productid, organizationid) in " + idsScopeStr4DetailProOrgs + " ) ";

			String sqlStr ="";
			if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				sqlStr= "(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and ( (productid, organizationid) in " + idsScopeStr4DetailProOrgs + " ) ";
			}
			else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				sqlStr ="(period >= " + _periodBegin + ") and (period <= " + _periodEnd + ") and "+ UtilProOrg.format4mssqlstr4ids(idsScopeStr4DetailProOrgs);
			}
			
			DaoPriceData daoPriceData = new DaoPriceData( session );
			List<PriceData> listPriceData_inDB = daoPriceData.getPriceDatas( sqlStr, -1, -1  );

			if( listPriceData_inDB != null && listPriceData_inDB.iterator() != null )
			{
				ProductBDConvertor productBDConvertor = new ProductBDConvertor();
				OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
				
				//	拼出 ABUiRowPriceData		begin
				for( int i=0; i<listPriceData_inDB.size(); i++ )
				{
					PriceData priceData_inDB = listPriceData_inDB.get( i );
					
					String strKey_po = UtilStrKey.getStrKey4PO( priceData_inDB.getProduct(), priceData_inDB.getOrganization() );
					ABUiRowPriceData abUiRowPriceData = hmap_UiRowPriceData.get( strKey_po );
					if( abUiRowPriceData == null )
					{
						abUiRowPriceData = new ABUiRowPriceData();
						abUiRowPriceData.setProduct( (BProduct) productBDConvertor.dtob( priceData_inDB.getProduct() ) );
						abUiRowPriceData.setOrganization( (BOrganization) organizationBDConvertor.dtob( priceData_inDB.getOrganization() ) );
						abUiRowPriceData.setPeriodBegin( _periodBegin );
						abUiRowPriceData.setPeriodEnd( _periodEnd );
					}
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, priceData_inDB.getPeriod() );
					abUiRowPriceData.pubFun4setPeriodDataValueStandardPrice( periodLoc, priceData_inDB.getStandardPrice() );
					abUiRowPriceData.pubFun4setPeriodDataValueRealPrice( periodLoc, priceData_inDB.getRealPrice() );
					
					hmap_UiRowPriceData.put( strKey_po, abUiRowPriceData );
				}
				//	拼出 ABUiRowPriceData		end
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
		
		
		if( hmap_UiRowPriceData.values() != null && !(hmap_UiRowPriceData.values().isEmpty()) )
		{
			Iterator<ABUiRowPriceData> itr_hmap_UiRowPriceData_values = hmap_UiRowPriceData.values().iterator();
			while( itr_hmap_UiRowPriceData_values.hasNext() )
			{
				rstList.add( itr_hmap_UiRowPriceData_values.next() );
			}
		}
		return rstList;

	}		
	
	

}


/**************************************************************************
 * 
 * $RCSfile: PriceService,v $ $Revision:  $ $Date: 
 * $
 * 
 * $Log: PriceService,v $
 * Revision 
 * 
 * 
 ***************************************************************************/