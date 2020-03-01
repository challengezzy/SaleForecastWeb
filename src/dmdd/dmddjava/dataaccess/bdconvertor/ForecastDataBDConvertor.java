/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class ForecastDataBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastDataBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastData bForecastData = null;
		ForecastData   forecastData = null;
		
		if( b_obj == null )
		{
			bForecastData = new BForecastData();
		}
		else
		{
			bForecastData = (BForecastData)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastData = (ForecastData)d_obj;
		}
		
		forecastData.setVersion( bForecastData.getVersion() );
		forecastData.setId( bForecastData.getId() );
		forecastData.setPeriod( bForecastData.getPeriod() );
		forecastData.setValue( bForecastData.getValue() );			
		forecastData.setStatus( bForecastData.getStatus() );		
		forecastData.setInitTime( bForecastData.getInitTime() );		
		forecastData.setUpdateTime( bForecastData.getUpdateTime() );
		forecastData.setComments( bForecastData.getComments() );
		
		//    bizData
		if( bForecastData.getBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData bizData = (BizData) bizDataBDConvertor.btod( bForecastData.getBizData() );	
			forecastData.setBizData(bizData);
		}
		else
		{
			forecastData.setBizData(null);
		}		
		
		//    organization
		if( bForecastData.getOrganization() != null )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
			Organization organization = (Organization) organizationBDConvertor.btod( bForecastData.getOrganization() );	
			forecastData.setOrganization(organization);
		}
		else
		{
			forecastData.setOrganization(null);
		}	
		
		//    product
		if( bForecastData.getProduct() != null )
		{
			ProductBDConvertor productBDConvertor = new ProductBDConvertor();
			Product product = (Product) productBDConvertor.btod( bForecastData.getProduct() );	
			forecastData.setProduct(product);
		}
		else
		{
			forecastData.setProduct(null);
		}		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ForecastData forecastData = new ForecastData();
		this.btod(b_obj, forecastData);
		return forecastData;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastData   forecastData = null;
		BForecastData bForecastData = null;
		
		if( d_obj == null )
		{
			forecastData = new ForecastData();
		}
		else
		{
			forecastData = (ForecastData)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastData = (BForecastData)b_obj;
		}
		
		bForecastData.setVersion( forecastData.getVersion() );
		bForecastData.setId( forecastData.getId() );
		bForecastData.setPeriod( forecastData.getPeriod() );
		bForecastData.setValue( forecastData.getValue() );				
		bForecastData.setStatus( forecastData.getStatus() );		
		bForecastData.setInitTime( forecastData.getInitTime() );		
		bForecastData.setUpdateTime( forecastData.getUpdateTime() );
		bForecastData.setComments( forecastData.getComments() );
		
		//    bizData
		if( forecastData.getBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			// 界面上有时需要根据业务数据定义来计算预测数据，这里把业务数据的定义项传过去
			BBizData bBizData = bizDataBDConvertor.dtob( forecastData.getBizData(), true );	
			bForecastData.setBizData(bBizData);
		}
		else
		{
			bForecastData.setBizData(null);
		}			

		//    organization
		if( forecastData.getOrganization() != null )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
			BOrganization bOrganization = (BOrganization) organizationBDConvertor.dtob( forecastData.getOrganization() );	
			bForecastData.setOrganization(bOrganization);
		}
		else
		{
			bForecastData.setOrganization(null);
		}
		
		//    product
		if( forecastData.getProduct() != null )
		{
			ProductBDConvertor productBDConvertor = new ProductBDConvertor();
			BProduct bProduct = (BProduct) productBDConvertor.dtob( forecastData.getProduct() );	
			bForecastData.setProduct(bProduct);
		}
		else
		{
			bForecastData.setProduct(null);
		}		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BForecastData bForecastData = new BForecastData();
		this.dtob(d_obj, bForecastData);
		return bForecastData;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
