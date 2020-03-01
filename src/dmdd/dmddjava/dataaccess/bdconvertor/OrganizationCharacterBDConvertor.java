/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class OrganizationCharacterBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public OrganizationCharacterBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用对象organizationCharacterLayer , 处理
	 * 引用的对象属性parentOrganizationCharacter,不处理;
	 * 下附的集合属性subOrganizationCharacters organizationOrgCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BOrganizationCharacter bOrganizationCharacter = null;
		OrganizationCharacter   organizationCharacter = null;
		if( b_obj == null )
		{
			bOrganizationCharacter = new BOrganizationCharacter();
		}
		else
		{
			bOrganizationCharacter = (BOrganizationCharacter) b_obj;
		}

		if( d_obj == null )
		{
			return;
		}
		else
		{
			organizationCharacter = (OrganizationCharacter) d_obj;
		}
		
		organizationCharacter.setVersion( bOrganizationCharacter.getVersion() );
		organizationCharacter.setId( bOrganizationCharacter.getId() );
		organizationCharacter.setCode( bOrganizationCharacter.getCode() );
		organizationCharacter.setName( bOrganizationCharacter.getName() );
		organizationCharacter.setType( bOrganizationCharacter.getType() );
		organizationCharacter.setIsCatalog( bOrganizationCharacter.getIsCatalog() );
		organizationCharacter.setDescription( bOrganizationCharacter.getDescription() );
		organizationCharacter.setComments( bOrganizationCharacter.getComments() );
		organizationCharacter.setPathCode(bOrganizationCharacter.getPathCode());
		//    organizationCharacterLayer
		if( bOrganizationCharacter.getOrganizationCharacterLayer() != null )
		{
			OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
			OrganizationCharacterLayer organizationCharacterLayer = (OrganizationCharacterLayer) organizationCharacterLayerBDConvertor.btod(bOrganizationCharacter.getOrganizationCharacterLayer());	
			organizationCharacter.setOrganizationCharacterLayer( organizationCharacterLayer );
		}
		else
		{
			organizationCharacter.setOrganizationCharacterLayer(null);
		}		
		
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用对象organizationCharacterLayer , 处理
	 * 引用的对象属性parentOrganizationCharacter,不处理;
	 * 下附的集合属性subOrganizationCharacters organizationOrgCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}			
		OrganizationCharacter organizationCharacter = new OrganizationCharacter();
		this.btod(b_obj, organizationCharacter);
		return organizationCharacter;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用对象organizationCharacterLayer , 处理
	 * 引用的对象属性parentOrganizationCharacter,不处理;
	 * 下附的集合属性subOrganizationCharacters organizationOrgCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		OrganizationCharacter   organizationCharacter = null;
		BOrganizationCharacter bOrganizationCharacter = null;
		
		if( d_obj == null )
		{
			organizationCharacter = new OrganizationCharacter();
		}
		else
		{
			organizationCharacter = (OrganizationCharacter) d_obj;
		}

		if( b_obj == null )
		{
			return;
		}
		else
		{
			bOrganizationCharacter = (BOrganizationCharacter) b_obj;
		}
		
		bOrganizationCharacter.setVersion( organizationCharacter.getVersion() );
		bOrganizationCharacter.setId( organizationCharacter.getId() );
		bOrganizationCharacter.setCode( organizationCharacter.getCode() );
		bOrganizationCharacter.setName( organizationCharacter.getName() );
		bOrganizationCharacter.setType( organizationCharacter.getType() );
		bOrganizationCharacter.setIsCatalog( organizationCharacter.getIsCatalog() );
		bOrganizationCharacter.setDescription( organizationCharacter.getDescription() );
		bOrganizationCharacter.setComments( organizationCharacter.getComments() );
		bOrganizationCharacter.setPathCode(organizationCharacter.getPathCode());
		//    organizationCharacterLayer
		if( organizationCharacter.getOrganizationCharacterLayer() != null )
		{
			OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
			BOrganizationCharacterLayer bOrganizationCharacterLayer = (BOrganizationCharacterLayer) organizationCharacterLayerBDConvertor.dtob(organizationCharacter.getOrganizationCharacterLayer());	
			bOrganizationCharacter.setOrganizationCharacterLayer( bOrganizationCharacterLayer );
		}
		else
		{
			bOrganizationCharacter.setOrganizationCharacterLayer(null);
		}			

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用对象organizationCharacterLayer , 处理
	 * 引用的对象属性parentOrganizationCharacter,不处理;
	 * 下附的集合属性subOrganizationCharacters organizationOrgCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}			
		BOrganizationCharacter bOrganizationCharacter = new BOrganizationCharacter();
		this.dtob(d_obj, bOrganizationCharacter);
		return bOrganizationCharacter;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
