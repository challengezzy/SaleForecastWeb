/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationOrgCharacter;

/**
 * @author liuzhen
 *
 */
public class OrganizationBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public OrganizationBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性organizationLayer,处理;
	 * 引用的对象属性parentOrganization,不处理;
	 * 下附的集合属性organizationOrgCharacters,不处理;
	 * 下附的集合属性subOrganizations,不处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BOrganization bOrganization = null;
		Organization   organization = null;
		
		if( b_obj == null )
		{
			bOrganization = new BOrganization();
		}
		else
		{
			bOrganization = (BOrganization)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			organization = (Organization)d_obj;
		}
		
		organization.setVersion( bOrganization.getVersion() );
		organization.setId( bOrganization.getId() );
		organization.setCode( bOrganization.getCode() );
		organization.setName( bOrganization.getName() );
		organization.setIsCatalog( bOrganization.getIsCatalog() );
		organization.setIsValid( bOrganization.getIsValid() );
		organization.setDescription( bOrganization.getDescription() );
		organization.setComments( bOrganization.getComments() );
		
		organization.setPathCode(bOrganization.getPathCode());
		organization.setDcId(bOrganization.getDcId()); 
		
		//    organizationLayer
		if( bOrganization.getOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			OrganizationLayer organizationLayer = (OrganizationLayer) organizationLayerBDConvertor.btod(bOrganization.getOrganizationLayer());	
			organization.setOrganizationLayer(organizationLayer);
		}
		else
		{
			organization.setOrganizationLayer(null);
		}
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性organizationLayer,处理;
	 * 引用的对象属性parentOrganization,不处理;
	 * 下附的集合属性organizationOrgCharacters,不处理;
	 * 下附的集合属性subOrganizations,不处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		Organization organization = new Organization();
		this.btod(b_obj, organization);
		return organization;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性organizationLayer,处理;
	 * 引用的对象属性parentOrganization,不处理;
	 * 下附的集合属性organizationOrgCharacters,不处理;
	 * 下附的集合属性subOrganizations,不处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		Organization   organization = null;
		BOrganization bOrganization = null;
		
		if( d_obj == null )
		{
			organization = new Organization();
		}
		else
		{
			organization = (Organization)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bOrganization = (BOrganization)b_obj;
		}
		
		bOrganization.setVersion( organization.getVersion() );
		bOrganization.setId( organization.getId() );
		bOrganization.setCode( organization.getCode() );
		bOrganization.setName( organization.getName() );
		bOrganization.setIsCatalog( organization.getIsCatalog() );
		bOrganization.setIsValid( organization.getIsValid() );
		bOrganization.setDescription( organization.getDescription() );
		bOrganization.setComments( organization.getComments() );
		bOrganization.setPathCode(organization.getPathCode());
		bOrganization.setDcId(organization.getDcId());
		
		//    organizationLayer
		if( organization.getOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			BOrganizationLayer bOrganizationLayer = (BOrganizationLayer) organizationLayerBDConvertor.dtob(organization.getOrganizationLayer());	
			bOrganization.setOrganizationLayer(bOrganizationLayer);
		}
		else
		{
			bOrganization.setOrganizationLayer(null);
		}

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性organizationLayer,处理;
	 * 引用的对象属性parentOrganization,不处理;
	 * 下附的集合属性organizationOrgCharacters,不处理;
	 * 下附的集合属性subOrganizations,不处理 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}		
		BOrganization bOrganization = new BOrganization();
		this.dtob(d_obj, bOrganization);
		return bOrganization;
	}
	

	public void btod(BOrganization _bOrganization, Organization _organization, boolean _blWithOrganizationOrgCharacters )
	{
		if( _organization == null )
		{
			return;
		}
		
		this.btod(_bOrganization, _organization);
		
		if( _blWithOrganizationOrgCharacters == true )
		{
			//    organizationOrgCharacters
			if( _bOrganization != null && _bOrganization.getOrganizationOrgCharacters() != null && _bOrganization.getOrganizationOrgCharacters().iterator() != null )
			{
				OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();
				
				Iterator<BOrganizationOrgCharacter> itr_bOrganizationOrgCharacters = _bOrganization.getOrganizationOrgCharacters().iterator();
				while( itr_bOrganizationOrgCharacters.hasNext() )
				{
					BOrganizationOrgCharacter bOrganizationOrgCharacter = itr_bOrganizationOrgCharacters.next();
					
					OrganizationCharacter organizationCharacter = (OrganizationCharacter)organizationCharacterBDConvertor.btod(bOrganizationOrgCharacter.getOrganizationCharacter());    // 认为bOrganizationOrgCharacter.getOrganizationCharacter()!=null
					
					OrganizationOrgCharacter organizationOrgCharacter = new OrganizationOrgCharacter();
					
					organizationOrgCharacter.setVersion(bOrganizationOrgCharacter.getVersion());
					organizationOrgCharacter.setId(bOrganizationOrgCharacter.getId());
					organizationOrgCharacter.setOrganization(_organization);
					organizationOrgCharacter.setOrganizationCharacter(organizationCharacter);
					
					_organization.addOrganizationOrgCharacter( organizationOrgCharacter );
				}
			}				
		}
			
	}
	
	public Organization btod(BOrganization _bOrganization, boolean _blWithOrganizationOrgCharacters )
	{
		if( _bOrganization == null )
		{
			return null;
		}		
		Organization organization = new Organization();
		this.btod(_bOrganization, organization, _blWithOrganizationOrgCharacters);
		return organization;
	}
	

	public void dtob(Organization _organization, BOrganization _bOrganization, boolean _blWithOrganizationOrgCharacters )
	{
		if( _bOrganization == null )
		{
			return;
		}
		
		this.dtob(_organization, _bOrganization);
		
		if( _blWithOrganizationOrgCharacters == true )
		{
			//    organizationOrgCharacters
			if( _organization != null && _organization.getOrganizationOrgCharacters() != null && _organization.getOrganizationOrgCharacters().iterator() != null )
			{
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen	begin
				/*
				OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();
				*/
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen	end
				
				Iterator<OrganizationOrgCharacter> itr_organizationOrgCharacters = _organization.getOrganizationOrgCharacters().iterator();
				while( itr_organizationOrgCharacters.hasNext() )
				{
					OrganizationOrgCharacter organizationOrgCharacter = itr_organizationOrgCharacters.next();
				
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen	begin
					/*
					BOrganizationCharacter bOrganizationCharacter = (BOrganizationCharacter)organizationCharacterBDConvertor.dtob(organizationOrgCharacter.getOrganizationCharacter());    // 认为organizationOrgCharacter.getOrganizationCharacter()!=null
					*/
					BOrganizationCharacter bOrganizationCharacter = ServerEnvironment.getInstance().getBOrganizationCharacter( organizationOrgCharacter.getOrganizationCharacter().getId() );
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.21 by liuzhen	end
					
					BOrganizationOrgCharacter bOrganizationOrgCharacter = new BOrganizationOrgCharacter();
					
					bOrganizationOrgCharacter.setVersion(organizationOrgCharacter.getVersion());
					bOrganizationOrgCharacter.setId(organizationOrgCharacter.getId());
					bOrganizationOrgCharacter.setOrganization(_bOrganization);
					bOrganizationOrgCharacter.setOrganizationCharacter(bOrganizationCharacter);
					
					_bOrganization.addOrganizationOrgCharacters( bOrganizationOrgCharacter );
				}
			}					
		}	
	}

	public BOrganization dtob(Organization _organization, boolean _blWithOrganizationOrgCharacters )
	{
		if( _organization == null )
		{
			return null;
		}			
		BOrganization bOrganization = new BOrganization();
		this.dtob(_organization, bOrganization, _blWithOrganizationOrgCharacters );
		return bOrganization;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
