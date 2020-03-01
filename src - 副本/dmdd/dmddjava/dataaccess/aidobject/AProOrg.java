/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 * 辅助作用的类，与前段对应类进行对应，不涉及数据库操作，引用的对象是B类对象
 */
public class AProOrg implements Serializable
{
	
	public final static long serialVersionUID = -1010000001;
	
	private Product product;
	private Organization organization;
	
	
	
	/**
	 * @return the product
	 */
	public Product getProduct()
	{
		return product;
	}




	/**
	 * @param product the product to set
	 */
	public void setProduct( Product product )
	{
		this.product = product;
	}




	/**
	 * @return the organization
	 */
	public Organization getOrganization()
	{
		return organization;
	}




	/**
	 * @param organization the organization to set
	 */
	public void setOrganization( Organization organization )
	{
		this.organization = organization;
	}

	



	/**
	 * @param organization
	 * @param product
	 */
	public AProOrg( Product product, Organization organization )
	{
		this.product = product;		
		this.organization = organization;
	}




	/**
	 * 
	 */
	public AProOrg()
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
