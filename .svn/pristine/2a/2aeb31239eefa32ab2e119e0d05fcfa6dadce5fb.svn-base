/**
 * 
 */
package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;

/**
 * @author liuzhen
 *
 */
public class UtilOrganizationCharacter
{

	/**
	 * 
	 */
	public UtilOrganizationCharacter()
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

	/**
	 * 算法功能：获得 _detailOrganizationCharacter 的 _productChatacterLayerValue 层次的祖先
	 * 算法前提：1. _detailOrganizationCharacter 是明细产品
	 * 算法注意：_detailOrganizationCharacter 的层次高于_productChatacterLayerValue对应的层次的话，返回_detailOrganizationCharacter本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static OrganizationCharacter getProjectOrganizationCharacterByLayer(OrganizationCharacter _detailOrganizationCharacter, int _productChatacterLayerValue)
	{
		if (_detailOrganizationCharacter == null)
		{
			return null;
		}

		if (_detailOrganizationCharacter.getOrganizationCharacterLayer().getValue() <= _productChatacterLayerValue)
		{
			return _detailOrganizationCharacter;
		}

		OrganizationCharacter projectOrganizationCharacter = _detailOrganizationCharacter.getParentOrganizationCharacter();
		while (projectOrganizationCharacter != null)
		{
			if (projectOrganizationCharacter.getOrganizationCharacterLayer().getValue() == _productChatacterLayerValue)
			{
				return projectOrganizationCharacter;
			}

			projectOrganizationCharacter = projectOrganizationCharacter.getParentOrganizationCharacter();
		}

		return null; // impossible result
	}

	/**
	 * 算法功能：获得 _detailOrganizationCharacter 的 _productChatacterLayerValue 层次的祖先
	 * 算法前提：1. _detailOrganizationCharacter 是明细产品
	 * 算法注意：_detailOrganizationCharacter 的层次高于_productChatacterLayerValue对应的层次的话，返回_detailOrganizationCharacter本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static BOrganizationCharacter getProjectOrganizationCharacterByLayer(BOrganizationCharacter _detailOrganizationCharacter, int _productChatacterLayerValue)
	{
		if (_detailOrganizationCharacter == null)
		{
			return null;
		}

		if (_detailOrganizationCharacter.getOrganizationCharacterLayer().getValue() <= _productChatacterLayerValue)
		{
			return _detailOrganizationCharacter;
		}

		BOrganizationCharacter projectOrganizationCharacter = _detailOrganizationCharacter.getParentOrganizationCharacter();
		while (projectOrganizationCharacter != null)
		{
			if (projectOrganizationCharacter.getOrganizationCharacterLayer().getValue() == _productChatacterLayerValue)
			{
				return projectOrganizationCharacter;
			}

			projectOrganizationCharacter = projectOrganizationCharacter.getParentOrganizationCharacter();
		}

		return null; // impossible result
	}
	/**
	 * 判断 _organizationCharacter_1 是否包含 _organizationCharacter_2 
	 * 
	 */ 
	public static boolean isAncestorOf(BOrganizationCharacter _organizationCharacter_1,BOrganizationCharacter _organizationCharacter_2 )
	{

		if( _organizationCharacter_1 == null )
		{
			return false;
		}

		
		if( _organizationCharacter_2 == null )
		{
			return false;
		}	
		long sourid=_organizationCharacter_1.getId();
		long targetid =  _organizationCharacter_2.getId();
		if(sourid == targetid)
		{
			return true;
		}
		BOrganizationCharacter ancestorOrganizationCharacter = _organizationCharacter_2.getParentOrganizationCharacter();
		while( ancestorOrganizationCharacter != null )
		{
			targetid =  ancestorOrganizationCharacter.getId();
			if( sourid == targetid)
			{
				return true;
			}
			
			ancestorOrganizationCharacter = ancestorOrganizationCharacter.getParentOrganizationCharacter();				
		}
		
		return false;
	}	
	
	public static List<OrganizationCharacter> getOrganizationCharacters(OrganizationCharacter _organizationCharacter )
	{
		List<OrganizationCharacter> list = new ArrayList<OrganizationCharacter>();
		if(_organizationCharacter!=null)
		{
			list.add( _organizationCharacter )	;
		}
		
		if(_organizationCharacter.getSubOrganizationCharacters()!=null && !_organizationCharacter.getSubOrganizationCharacters().isEmpty())
		{
			for(OrganizationCharacter organizationCharacter:_organizationCharacter.getSubOrganizationCharacters())
			{
				list.addAll( getOrganizationCharacters(organizationCharacter) );
			}
		}
		
		return list;
	}
	
	/**
	 * 获取 _detailOrganization 的 _organizationCharacterType 类型的组织特征
	 * 
	 */ 
	public static BOrganizationCharacter getDetailOrganizationCharacter( BOrganization _detailOrganization,String _organizationCharacterType )
	{		
		if( _detailOrganization == null )
		{
			return null;
		}
		
		Set<BOrganizationOrgCharacter> organizationOrgCharacters = _detailOrganization.getOrganizationOrgCharacters();
		if( organizationOrgCharacters == null )
		{
			return null;
		}			
		
		for( BOrganizationOrgCharacter organizationOrgCharacter:organizationOrgCharacters)
		{
			if( organizationOrgCharacter.getOrganizationCharacter().getType().equals( _organizationCharacterType ))
			{
				return organizationOrgCharacter.getOrganizationCharacter();
			}
		}
		
		return null;
	}	
}
