/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BHistoryData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class HistoryDataBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public HistoryDataBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性，处理
	 * 引用的对象属性 bizData organization product，处理
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BHistoryData bHistoryData = null;
		HistoryData   historyData  = null;
		if( b_obj == null )
		{
			bHistoryData = new BHistoryData();
		}
		else
		{
			bHistoryData = (BHistoryData)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			historyData = (HistoryData)d_obj;
		}
		
		historyData.setVersion( bHistoryData.getVersion() );
		historyData.setId( bHistoryData.getId() );
		historyData.setPeriod( bHistoryData.getPeriod() );
		historyData.setValue( bHistoryData.getValue() );
		historyData.setComments( bHistoryData.getComments() );
		
		// bizData
		BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		historyData.setBizData( (BizData)bizDataBDConvertor.btod( bHistoryData.getBizData() ) );
		
		// organization
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		historyData.setOrganization( (Organization)organizationBDConvertor.btod( bHistoryData.getOrganization() ) );		

		// product
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
		historyData.setProduct( (Product)productBDConvertor.btod( bHistoryData.getProduct() ) );		
	}

	/* (non-Javadoc)
	 * 基本属性，处理
	 * 引用的对象属性 bizData organization product，处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		HistoryData historyData = new HistoryData();
		this.btod(b_obj, historyData);
		return historyData;
	}

	/* (non-Javadoc)
	 * 基本属性，处理
	 * 引用的对象属性 bizData organization product，处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		HistoryData  historyData  = null;
		BHistoryData bHistoryData = null;
		
		if( d_obj == null )
		{
			historyData = new HistoryData();
		}
		else
		{
			historyData = (HistoryData)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bHistoryData = (BHistoryData)b_obj;
		}
		
		bHistoryData.setVersion( historyData.getVersion() );
		bHistoryData.setId( historyData.getId() );
		bHistoryData.setPeriod( historyData.getPeriod() );
		bHistoryData.setValue( historyData.getValue() );
		bHistoryData.setComments( historyData.getComments() );
		
		// bizData
		BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		bHistoryData.setBizData( (BBizData)bizDataBDConvertor.dtob( historyData.getBizData() ) );
		
		// organization
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		bHistoryData.setOrganization( (BOrganization)organizationBDConvertor.dtob( historyData.getOrganization() ) );		

		// product
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
		bHistoryData.setProduct( (BProduct)productBDConvertor.dtob( historyData.getProduct() ) );

	}

	/* (non-Javadoc)
	 * 基本属性，处理
	 * 引用的对象属性 bizData organization product，处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BHistoryData bHistoryData = new BHistoryData();
		this.dtob(d_obj, bHistoryData);
		return bHistoryData;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
