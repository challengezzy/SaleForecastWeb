/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class OrganizationCharacterLayerBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public OrganizationCharacterLayerBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BOrganizationCharacterLayer bOrganizationCharacterLayer = null;
		OrganizationCharacterLayer  organizationCharacterLayer  = null;
		if( b_obj == null )
		{
			bOrganizationCharacterLayer = new BOrganizationCharacterLayer();
		}
		else
		{
			bOrganizationCharacterLayer = (BOrganizationCharacterLayer)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			organizationCharacterLayer = (OrganizationCharacterLayer)d_obj;
		}
		
		organizationCharacterLayer.setVersion( bOrganizationCharacterLayer.getVersion() );
		organizationCharacterLayer.setId( bOrganizationCharacterLayer.getId() );
		organizationCharacterLayer.setValue( bOrganizationCharacterLayer.getValue() );
		organizationCharacterLayer.setDescription( bOrganizationCharacterLayer.getDescription() );
		organizationCharacterLayer.setComments( bOrganizationCharacterLayer.getComments() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		OrganizationCharacterLayer organizationCharacterLayer = new OrganizationCharacterLayer();
		this.btod(b_obj, organizationCharacterLayer);
		return organizationCharacterLayer;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		OrganizationCharacterLayer  organizationCharacterLayer  = null;
		BOrganizationCharacterLayer bOrganizationCharacterLayer = null;
		
		if( d_obj == null )
		{
			organizationCharacterLayer = new OrganizationCharacterLayer();
		}
		else
		{
			organizationCharacterLayer = (OrganizationCharacterLayer)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bOrganizationCharacterLayer = (BOrganizationCharacterLayer)b_obj;
		}
		
		bOrganizationCharacterLayer.setVersion( organizationCharacterLayer.getVersion() );
		bOrganizationCharacterLayer.setId( organizationCharacterLayer.getId() );
		bOrganizationCharacterLayer.setValue( organizationCharacterLayer.getValue() );
		bOrganizationCharacterLayer.setDescription( organizationCharacterLayer.getDescription() );
		bOrganizationCharacterLayer.setComments( organizationCharacterLayer.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BOrganizationCharacterLayer bOrganizationCharacterLayer = new BOrganizationCharacterLayer();
		this.dtob(d_obj, bOrganizationCharacterLayer);
		return bOrganizationCharacterLayer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
