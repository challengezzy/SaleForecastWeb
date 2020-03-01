/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;

/**
 * @author liuzhen
 *
 */
public class ABUiRowDataProOrg implements Serializable
{
	public final static long serialVersionUID = -2010000002;

	private BProduct product = null;
	private BProductCharacter productCharacter = null;
	private BOrganization organization = null;
	private BOrganizationCharacter organizationCharacter = null;
	private List<String> detailProOrgIds = null;
	
	
	/**
	 * 
	 */
	public ABUiRowDataProOrg() {
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
	 * @return the product
	 */
	public BProduct getProduct()
	{
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct( BProduct product )
	{
		this.product = product;
	}

	/**
	 * @return the productCharacter
	 */
	public BProductCharacter getProductCharacter()
	{
		return productCharacter;
	}

	/**
	 * @param productCharacter the productCharacter to set
	 */
	public void setProductCharacter( BProductCharacter productCharacter )
	{
		this.productCharacter = productCharacter;
	}

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
	public void setOrganization( BOrganization organization )
	{
		this.organization = organization;
	}

	/**
	 * @return the organizationCharacter
	 */
	public BOrganizationCharacter getOrganizationCharacter()
	{
		return organizationCharacter;
	}

	/**
	 * @param organizationCharacter the organizationCharacter to set
	 */
	public void setOrganizationCharacter( BOrganizationCharacter organizationCharacter )
	{
		this.organizationCharacter = organizationCharacter;
	}

	/**
	 * @return the detailProOrgIds
	 */
	public List<String> getDetailProOrgIds()
	{
		return detailProOrgIds;
	}

	/**
	 * @param detailProOrgIds the detailProOrgIds to set
	 */
	public void setDetailProOrgIds( List<String> detailProOrgIds )
	{
		this.detailProOrgIds = detailProOrgIds;
	}
	public void addDetailProOrgIds(String ids)
	{
		if(detailProOrgIds==null )
			detailProOrgIds = new ArrayList<String>();
		detailProOrgIds.add(ids);
	}
}
