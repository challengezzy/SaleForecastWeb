/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastInst;
import dmdd.dmddjava.dataaccess.bizobject.BForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BForecastInstProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelM;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelM;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.utils.UtilFactoryForecastModelM;

/**
 * @author liuzhen
 *
 */
public class ForecastInstBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastInstBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性runProductLayer、runOrganizationLayer、mappingFcModel、analyzeFcModel、finalFcBizData、nextFinalFcBizData,处理;
	 * 下附的集合属性forecastInstProOrgs、forecastInstNextProOrgs,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastInst bForecastInst = null;
		ForecastInst   forecastInst = null;
		
		if( b_obj == null )
		{
			bForecastInst = new BForecastInst();
		}
		else
		{
			bForecastInst = (BForecastInst)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastInst = (ForecastInst)d_obj;
		}
		
		forecastInst.setVersion( bForecastInst.getVersion() );
		forecastInst.setId( bForecastInst.getId() );
		forecastInst.setCode( bForecastInst.getCode() );
		forecastInst.setName( bForecastInst.getName() );
		forecastInst.setFcPeriodNum( bForecastInst.getFcPeriodNum() );		
		forecastInst.setNextFcPeriodNum( bForecastInst.getNextFcPeriodNum() );
		forecastInst.setFzPeriodNum( bForecastInst.getFzPeriodNum() );		
		forecastInst.setNextFzPeriodNum( bForecastInst.getNextFzPeriodNum() );
		forecastInst.setDistributeRefFormula( bForecastInst.getDistributeRefFormula() );
		forecastInst.setDecomposeFormula( bForecastInst.getDecomposeFormula());
		forecastInst.setDistributeRefPeriodNum( bForecastInst.getDistributeRefPeriodNum() );
		forecastInst.setIsValid( bForecastInst.getIsValid() );
		forecastInst.setNextIsValid( bForecastInst.getNextIsValid() );
		forecastInst.setErrorThreshold( bForecastInst.getErrorThreshold() );
		forecastInst.setIsRunned( bForecastInst.getIsRunned() );
		forecastInst.setMappingFcModelRunTime( bForecastInst.getMappingFcModelRunTime() );
		forecastInst.setComments( bForecastInst.getComments() );
		
		//    runProductLayer
		if( bForecastInst.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			ProductLayer productLayer = (ProductLayer) productLayerBDConvertor.btod( bForecastInst.getRunProductLayer() );	
			forecastInst.setRunProductLayer(productLayer);
		}
		else
		{
			forecastInst.setRunProductLayer(null);
		}		
		
		//    runOrganizationLayer
		if( bForecastInst.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			OrganizationLayer organizationLayer = (OrganizationLayer) organizationLayerBDConvertor.btod( bForecastInst.getRunOrganizationLayer() );	
			forecastInst.setRunOrganizationLayer(organizationLayer);
		}
		else
		{
			forecastInst.setRunOrganizationLayer(null);
		}	
		
		//    mappingFcModel
		if( bForecastInst.getMappingFcModel() != null )
		{
			BForecastModelM bForecastModelM = bForecastInst.getMappingFcModel();
			ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance( bForecastModelM.getIndicator());
			ForecastModelM forecastModelM = (ForecastModelM) forecastModelMBDConvertor.btod( bForecastModelM );	
			forecastInst.setMappingFcModel(forecastModelM);
		}
		else
		{
			forecastInst.setMappingFcModel(null);
		}					
		
		//    finalFcBizData
		if( bForecastInst.getFinalFcBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData bizData = (BizData) bizDataBDConvertor.btod( bForecastInst.getFinalFcBizData() );	
			forecastInst.setFinalFcBizData(bizData);
		}
		else
		{
			forecastInst.setFinalFcBizData(null);
		}	

		//    nextFinalFcBizData
		if( bForecastInst.getNextFinalFcBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData bizData = (BizData) bizDataBDConvertor.btod( bForecastInst.getNextFinalFcBizData() );	
			forecastInst.setNextFinalFcBizData(bizData);
		}
		else
		{
			forecastInst.setNextFinalFcBizData(null);
		}	
		
		//    distributeRefBizDataBizData
		if( bForecastInst.getDistributeRefBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData bizData = (BizData) bizDataBDConvertor.btod( bForecastInst.getDistributeRefBizData() );	
			forecastInst.setDistributeRefBizData(bizData);
		}
		else
		{
			forecastInst.setDistributeRefBizData(null);
		}		
		
	
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性runProductLayer、runOrganizationLayer、mappingFcModel、analyzeFcModel、finalFcBizData、nextFinalFcBizData,处理;
	 * 下附的集合属性forecastInstProOrgs、forecastInstNextProOrgs,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ForecastInst forecastInst = new ForecastInst();
		this.btod(b_obj, forecastInst);
		return forecastInst;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性runProductLayer、runOrganizationLayer、mappingFcModel、analyzeFcModel、finalFcBizData、nextFinalFcBizData,处理;
	 * 下附的集合属性forecastInstProOrgs、forecastInstNextProOrgs,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastInst   forecastInst = null;
		BForecastInst bForecastInst = null;
		
		if( d_obj == null )
		{
			forecastInst = new ForecastInst();
		}
		else
		{
			forecastInst = (ForecastInst)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastInst = (BForecastInst)b_obj;
		}
		
		bForecastInst.setVersion( forecastInst.getVersion() );
		bForecastInst.setId( forecastInst.getId() );
		bForecastInst.setCode( forecastInst.getCode() );
		bForecastInst.setName( forecastInst.getName() );
		bForecastInst.setFcPeriodNum( forecastInst.getFcPeriodNum() );		
		bForecastInst.setNextFcPeriodNum( forecastInst.getNextFcPeriodNum() );
		bForecastInst.setFzPeriodNum( forecastInst.getFzPeriodNum() );		
		bForecastInst.setNextFzPeriodNum( forecastInst.getNextFzPeriodNum() );	
		bForecastInst.setDistributeRefFormula( forecastInst.getDistributeRefFormula() );
		bForecastInst.setDecomposeFormula( forecastInst.getDecomposeFormula() );
		bForecastInst.setDistributeRefPeriodNum( forecastInst.getDistributeRefPeriodNum() );		
		bForecastInst.setIsValid( forecastInst.getIsValid() );
		bForecastInst.setNextIsValid( forecastInst.getNextIsValid() );
		bForecastInst.setErrorThreshold( forecastInst.getErrorThreshold() );
		bForecastInst.setIsRunned( forecastInst.getIsRunned() );
		bForecastInst.setMappingFcModelRunTime( forecastInst.getMappingFcModelRunTime() );
		bForecastInst.setComments( bForecastInst.getComments() );

		//    runProductLayer
		if( forecastInst.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			BProductLayer bProductLayer = (BProductLayer) productLayerBDConvertor.dtob( forecastInst.getRunProductLayer() );	
			bForecastInst.setRunProductLayer(bProductLayer);
		}
		else
		{
			bForecastInst.setRunProductLayer(null);
		}		
		
		//    runOrganizationLayer
		if( forecastInst.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			BOrganizationLayer bOrganizationLayer = (BOrganizationLayer) organizationLayerBDConvertor.dtob( forecastInst.getRunOrganizationLayer() );	
			bForecastInst.setRunOrganizationLayer(bOrganizationLayer);
		}
		else
		{
			bForecastInst.setRunOrganizationLayer(null);
		}	
		
		//    mappingFcModel
		if( forecastInst.getMappingFcModel() != null )
		{
			ForecastModelM forecastModelM = forecastInst.getMappingFcModel();
			ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance(forecastModelM.getIndicator());
			BForecastModelM bForecastModelM = (BForecastModelM) forecastModelMBDConvertor.dtob( forecastModelM );	
			bForecastInst.setMappingFcModel(bForecastModelM);
		}
		else
		{
			bForecastInst.setMappingFcModel(null);
		}				
		
		//    finalFcBizData
		if( forecastInst.getFinalFcBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData bBizData = (BBizData) bizDataBDConvertor.dtob( forecastInst.getFinalFcBizData() );	
			bForecastInst.setFinalFcBizData(bBizData);
		}
		else
		{
			bForecastInst.setFinalFcBizData(null);
		}	

		//    nextFinalFcBizData
		if( forecastInst.getNextFinalFcBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData bBizData = (BBizData) bizDataBDConvertor.dtob( forecastInst.getNextFinalFcBizData() );	
			bForecastInst.setNextFinalFcBizData(bBizData);
		}
		else
		{
			bForecastInst.setNextFinalFcBizData(null);
		}		
		
		//    distributeRefBizData
		if( forecastInst.getDistributeRefBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData bBizData = (BBizData) bizDataBDConvertor.dtob( forecastInst.getDistributeRefBizData() );	
			bForecastInst.setDistributeRefBizData(bBizData);
		}
		else
		{
			bForecastInst.setDistributeRefBizData(null);
		}		
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性runProductLayer、runOrganizationLayer、mappingFcModel、analyzeFcModel、finalFcBizData、nextFinalFcBizData,处理;
	 * 下附的集合属性forecastInstProOrgs、forecastInstNextProOrgs,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BForecastInst bForecastInst = new BForecastInst();
		this.dtob(d_obj, bForecastInst);
		return bForecastInst;
	}
	

	
	
	public void btod( BForecastInst _bForecastInst, ForecastInst _forecastInst, boolean _blWithProOrgs )
	{
		if( _forecastInst == null )
		{
			return;
		}
		
		this.btod(_bForecastInst, _forecastInst);
		
		if( _blWithProOrgs == true )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
			ProductBDConvertor productBDConvertor = new ProductBDConvertor();
			
			// forecastInstProOrgs
			if( _bForecastInst != null && _bForecastInst.getForecastInstProOrgs() != null && _bForecastInst.getForecastInstProOrgs().iterator() != null )
			{
				Iterator<BForecastInstProOrg> itr_bForecastInstProOrgs = _bForecastInst.getForecastInstProOrgs().iterator();
				while( itr_bForecastInstProOrgs.hasNext() )
				{
					BForecastInstProOrg bForecastInstProOrg = itr_bForecastInstProOrgs.next();

					ForecastInstProOrg forecastInstProOrg = new ForecastInstProOrg();
					
					forecastInstProOrg.setVersion(bForecastInstProOrg.getVersion());
					forecastInstProOrg.setId(bForecastInstProOrg.getId());
					forecastInstProOrg.setForecastInst(_forecastInst);
					forecastInstProOrg.setProduct( (Product)productBDConvertor.btod( bForecastInstProOrg.getProduct() ) );
					forecastInstProOrg.setOrganization( (Organization)organizationBDConvertor.btod( bForecastInstProOrg.getOrganization() ) );
					

					_forecastInst.addForecastInstProOrg( forecastInstProOrg );
				}
			}
			
			// forecastInstNextProOrgs
			if( _bForecastInst != null && _bForecastInst.getForecastInstNextProOrgs() != null && _bForecastInst.getForecastInstNextProOrgs().iterator() != null )
			{
				Iterator<BForecastInstNextProOrg> itr_bForecastInstNextProOrgs = _bForecastInst.getForecastInstNextProOrgs().iterator();
				while( itr_bForecastInstNextProOrgs.hasNext() )
				{
					BForecastInstNextProOrg bForecastInstNextProOrg = itr_bForecastInstNextProOrgs.next();
										
					ForecastInstNextProOrg forecastInstNextProOrg = new ForecastInstNextProOrg();
					
					forecastInstNextProOrg.setVersion(bForecastInstNextProOrg.getVersion());
					forecastInstNextProOrg.setId(bForecastInstNextProOrg.getId());
					forecastInstNextProOrg.setForecastInst(_forecastInst);
					forecastInstNextProOrg.setProduct( (Product)productBDConvertor.btod( bForecastInstNextProOrg.getProduct() ) );
					forecastInstNextProOrg.setOrganization( (Organization)organizationBDConvertor.btod( bForecastInstNextProOrg.getOrganization() ) );

					_forecastInst.addForecastInstNextProOrg( forecastInstNextProOrg );
				}
			}
		}		
	}
	

	public ForecastInst btod( BForecastInst _bForecastInst, boolean _blWithProOrgs )
	{
		ForecastInst forecastInst = new ForecastInst();
		this.btod(_bForecastInst, forecastInst, _blWithProOrgs);
		return forecastInst;		
	}
	

	public void dtob( ForecastInst _forecastInst, BForecastInst _bForecastInst, boolean _blWithProOrgs )
	{
		if( _bForecastInst == null )
		{
			return;
		}
		
		this.dtob(_forecastInst, _bForecastInst);
		
		if( _blWithProOrgs == true )
		{
			//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
			/*
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();		
			ProductBDConvertor productBDConvertor = new ProductBDConvertor();
			*/		
			//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end
			
			// forecastInstProOrgs
			if( _forecastInst != null && _forecastInst.getForecastInstProOrgs() != null && _forecastInst.getForecastInstProOrgs().iterator() != null )
			{
				Iterator<ForecastInstProOrg> itr_forecastInstProOrgs = _forecastInst.getForecastInstProOrgs().iterator();
				while( itr_forecastInstProOrgs.hasNext() )
				{
					ForecastInstProOrg forecastInstProOrg = itr_forecastInstProOrgs.next();

					BForecastInstProOrg bForecastInstProOrg = new BForecastInstProOrg();
					
					bForecastInstProOrg.setVersion(forecastInstProOrg.getVersion());
					bForecastInstProOrg.setId(forecastInstProOrg.getId());
					bForecastInstProOrg.setForecastInst(_bForecastInst);
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
					/*
					bForecastInstProOrg.setProduct( (BProduct)productBDConvertor.dtob( forecastInstProOrg.getProduct() ) );
					bForecastInstProOrg.setOrganization( (BOrganization)organizationBDConvertor.dtob( forecastInstProOrg.getOrganization() ) );
					*/
					bForecastInstProOrg.setProduct( ServerEnvironment.getInstance().getBProduct( forecastInstProOrg.getProduct().getId() ) );
					bForecastInstProOrg.setOrganization( ServerEnvironment.getInstance().getBOrganization( forecastInstProOrg.getOrganization().getId() ) );					
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end

					_bForecastInst.addForecastInstProOrg( bForecastInstProOrg );
				}
			}
			
			// forecastInstNextProOrgs
			if( _forecastInst != null && _forecastInst.getForecastInstNextProOrgs() != null && _forecastInst.getForecastInstNextProOrgs().iterator() != null )
			{
				Iterator<ForecastInstNextProOrg> itr_forecastInstNextProOrgs = _forecastInst.getForecastInstNextProOrgs().iterator();
				while( itr_forecastInstNextProOrgs.hasNext() )
				{
					ForecastInstNextProOrg forecastInstNextProOrg = itr_forecastInstNextProOrgs.next();

					BForecastInstNextProOrg bForecastInstNextProOrg = new BForecastInstNextProOrg();
					
					bForecastInstNextProOrg.setVersion(forecastInstNextProOrg.getVersion());
					bForecastInstNextProOrg.setId(forecastInstNextProOrg.getId());
					bForecastInstNextProOrg.setForecastInst(_bForecastInst);
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
					/*
					bForecastInstNextProOrg.setProduct( (BProduct)productBDConvertor.dtob( forecastInstNextProOrg.getProduct() ) );
					bForecastInstNextProOrg.setOrganization( (BOrganization)organizationBDConvertor.dtob( forecastInstNextProOrg.getOrganization() ) );
					*/
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end
					bForecastInstNextProOrg.setProduct( ServerEnvironment.getInstance().getBProduct( forecastInstNextProOrg.getProduct().getId() ) );
					bForecastInstNextProOrg.setOrganization( ServerEnvironment.getInstance().getBOrganization( forecastInstNextProOrg.getOrganization().getId() ) );										
					
					_bForecastInst.addForecastInstNextProOrg( bForecastInstNextProOrg );
				}
			}				
		}
			
	}
	

	public BForecastInst dtob( ForecastInst _forecastInst, boolean _blWithProOrgs )
	{
		BForecastInst bForecastInst = new BForecastInst();
		this.dtob(_forecastInst, bForecastInst, _blWithProOrgs);
		return bForecastInst;		
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
