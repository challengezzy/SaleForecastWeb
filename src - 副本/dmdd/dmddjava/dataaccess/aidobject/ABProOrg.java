/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;

/**
 * @author liuzhen
 * 辅助作用的类，与前端对应类进行对应，不涉及数据库操作，引用的对象是B类对象
 */
public class ABProOrg implements Serializable
{
	public final static long serialVersionUID = -1010000001;
	
	private BOrganization organization;
	private BProduct product;

	/**
	 * @return the organization
	 */
	public BOrganization getOrganization()
	{
		return organization;
	}



	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(BOrganization organization)
	{
		this.organization = organization;
	}



	/**
	 * @return the product
	 */
	public BProduct getProduct()
	{
		return product;
	}



	/**
	 * @param product the product to set
	 */
	public void setProduct(BProduct product)
	{
		this.product = product;
	}



	/**
	 * @param organization
	 * @param product
	 */
	public ABProOrg(BOrganization organization, BProduct product)
	{
		this.organization = organization;
		this.product = product;
	}



	/**
	 * 
	 * @param _bProduct
	 * @param bOrganization
	 */
	public ABProOrg(BProduct _bProduct, BOrganization _bOrganization)
	{
		this.product = _bProduct;
		this.organization = _bOrganization;
	}
		

	/**
	 * 
	 */
	public ABProOrg()
	{
		// TODO Auto-generated constructor stub
	}
	


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
