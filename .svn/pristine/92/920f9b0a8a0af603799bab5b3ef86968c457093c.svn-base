/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;

/**
 * @author liuzhen
 *
 */
public class OrganizationLayerBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public OrganizationLayerBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BOrganizationLayer bOrganizationLayer = null;
		OrganizationLayer  organizationLayer  = null;
		if( b_obj == null )
		{
			bOrganizationLayer = new BOrganizationLayer();
		}
		else
		{
			bOrganizationLayer = (BOrganizationLayer)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			organizationLayer = (OrganizationLayer)d_obj;
		}
		
		organizationLayer.setVersion( bOrganizationLayer.getVersion() );
		organizationLayer.setId( bOrganizationLayer.getId() );
		organizationLayer.setValue( bOrganizationLayer.getValue() );
		organizationLayer.setDescription( bOrganizationLayer.getDescription() );
		organizationLayer.setComments( bOrganizationLayer.getComments() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		OrganizationLayer organizationLayer = new OrganizationLayer();
		this.btod(b_obj, organizationLayer);
		return organizationLayer;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		OrganizationLayer  organizationLayer  = null;
		BOrganizationLayer bOrganizationLayer = null;
		
		if( d_obj == null )
		{
			organizationLayer = new OrganizationLayer();
		}
		else
		{
			organizationLayer = (OrganizationLayer)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bOrganizationLayer = (BOrganizationLayer)b_obj;
		}
		
		bOrganizationLayer.setVersion( organizationLayer.getVersion() );
		bOrganizationLayer.setId( organizationLayer.getId() );
		bOrganizationLayer.setValue( organizationLayer.getValue() );
		bOrganizationLayer.setDescription( organizationLayer.getDescription() );
		bOrganizationLayer.setComments( organizationLayer.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BOrganizationLayer bOrganizationLayer = new BOrganizationLayer();
		this.dtob(d_obj, bOrganizationLayer);
		return bOrganizationLayer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
