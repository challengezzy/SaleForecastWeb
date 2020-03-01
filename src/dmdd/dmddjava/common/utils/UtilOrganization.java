/**
 * 
 */
package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganizationLayer;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductLayer;

/**
 * @author liuzhen
 *
 */
public class UtilOrganization
{

	/**
	 * 
	 */
	public UtilOrganization()
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

	public static List<Organization> getDetailOrganizations(Organization _organization)
	{

		if (_organization == null)
		{
			return null;
		}

		List<Organization> rstList = new ArrayList<Organization>();
		if (_organization.getIsCatalog() == BizConst.GLOBAL_YESNO_NO)
		{
			rstList.add(_organization);
			return rstList;
		}
		else
		{
			if (_organization.getSubOrganizations() != null)
			{
				Iterator<Organization> itr_subOrganizations = _organization.getSubOrganizations().iterator();
				while (itr_subOrganizations.hasNext())
				{
					Organization subOrganization = itr_subOrganizations.next();
					List<Organization> rstList4subOrganization = getDetailOrganizations(subOrganization);
					if (rstList4subOrganization != null)
					{
						rstList.addAll(rstList4subOrganization);
					}
				}

				return rstList;
			}
			else
			{
				return null;
			}
		}
	}

	public static List<BOrganization> getDetailOrganizations(BOrganization _organization)
	{

		if (_organization == null)
		{
			return null;
		}

		List<BOrganization> rstList = new ArrayList<BOrganization>();
		if (_organization.getIsCatalog() == BizConst.GLOBAL_YESNO_NO)
		{
			rstList.add(_organization);
			return rstList;
		}
		else
		{
			if (_organization.getSubOrganizations() != null)
			{
				Iterator<BOrganization> itr_subOrganizations = _organization.getSubOrganizations().iterator();
				while (itr_subOrganizations.hasNext())
				{
					BOrganization subOrganization = itr_subOrganizations.next();
					List<BOrganization> rstList4subOrganization = getDetailOrganizations(subOrganization);
					if (rstList4subOrganization != null)
					{
						rstList.addAll(rstList4subOrganization);
					}
				}

				return rstList;
			}
			else
			{
				return null;
			}
		}
	}
	
	/**
	 * 算法功能：获得 _detailOrganization 的 _organizationLayerValue 层次的祖先
	 * 算法前提：1. _detailOrganization 是明细产品
	 * 算法注意：_detailOrganization 的层次高于_organizationLayerValue对应的层次的话，返回_detailOrganization本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static Organization getProjectOrganizationByLayer(Organization _detailOrganization, int _organizationLayerValue)
	{
		if (_detailOrganization == null)
		{
			return null;
		}

		if (_detailOrganization.getOrganizationLayer().getValue() <= _organizationLayerValue)
		{
			return _detailOrganization;
		}

		Organization projectOrganization = _detailOrganization.getParentOrganization();
		while (projectOrganization != null)
		{
			if (projectOrganization.getOrganizationLayer().getValue() == _organizationLayerValue)
			{
				return projectOrganization;
			}

			projectOrganization = projectOrganization.getParentOrganization();
		}

		return null; // impossible result
	}
	
	/**
	 * 算法功能：获得 _detailOrganization 的 _organizationLayerValue 层次的祖先
	 * 算法前提：1. _detailOrganization 是明细产品
	 * 算法注意：_detailOrganization 的层次高于_organizationLayerValue对应的层次的话，返回_detailOrganization本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static BOrganization getProjectOrganizationByLayer(BOrganization _detailOrganization, int _organizationLayerValue)
	{
		if (_detailOrganization == null)
		{
			return null;
		}

		if (_detailOrganization.getOrganizationLayer().getValue() <= _organizationLayerValue)
		{
			return _detailOrganization;
		}

		BOrganization projectOrganization = _detailOrganization.getParentOrganization();
		while (projectOrganization != null)
		{
			if (projectOrganization.getOrganizationLayer().getValue() == _organizationLayerValue)
			{
				return projectOrganization;
			}

			projectOrganization = projectOrganization.getParentOrganization();
		}

		return null; // impossible result
	}
	
	public static OrganizationCharacter getDetailOrganizationCharacter( Organization _detailOrganization, String _organizationCharacterType )
	{		
		if( _detailOrganization == null )
		{
			return null;
		}

		if( _detailOrganization.getOrganizationOrgCharacters() == null || _detailOrganization.getOrganizationOrgCharacters().isEmpty() )
		{
			return null;
		}
		
		Iterator<OrganizationOrgCharacter> itr_OrganizationOrgCharacter = _detailOrganization.getOrganizationOrgCharacters().iterator();
		while( itr_OrganizationOrgCharacter.hasNext() )
		{
			OrganizationOrgCharacter organizationOrgCharacter = itr_OrganizationOrgCharacter.next();
			if( organizationOrgCharacter.getOrganizationCharacter().getType().equals( _organizationCharacterType ) )
			{
				return organizationOrgCharacter.getOrganizationCharacter();
			}
		}
		
		return null;
	}	
	
	
	/**
	 * 计算 _bOrganization_1 与  _bOrganization_2 的关系
	 * @param _bOrganization_1
	 * @param _bOrganization_2
	 * @return
	 */
	public static int relationOf( BOrganization _bOrganization_1, BOrganization _bOrganization_2 )
	{
		if( _bOrganization_1 == null || _bOrganization_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _bOrganization_1.getId().longValue() == _bOrganization_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		
		if( _bOrganization_1.getOrganizationLayer().getValue() == _bOrganization_2.getOrganizationLayer().getValue() )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		BOrganization bOrganization_1 = null;
		BOrganization bOrganization_2 = null;
		if( _bOrganization_1.getOrganizationLayer().getValue() > _bOrganization_2.getOrganizationLayer().getValue() )
		{
			bOrganization_1 = _bOrganization_2;
			bOrganization_2 = _bOrganization_1;
			blSwitched = true;
		}	
		else
		{
			bOrganization_1 = _bOrganization_1;
			bOrganization_2 = _bOrganization_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		BOrganization ancestorBOrganization = bOrganization_2.getParentOrganization();
		while( ancestorBOrganization != null  )
		{	
			if( ancestorBOrganization.getOrganizationLayer().getValue() == bOrganization_1.getOrganizationLayer().getValue() )
			{
				break;
			}			
			ancestorBOrganization = ancestorBOrganization.getParentOrganization();
		}		
		// ancestorBOrganization 不会为空
		if( ancestorBOrganization.getId().longValue() == bOrganization_1.getId().longValue() )
		{
			// bOrganization_1 是 bOrganization_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}		
	
	/**
	 * 计算 _organization_1 与  _organization_2 的关系
	 * @param _organization_1
	 * @param _organization_2
	 * @return
	 */
	public static int relationOf_2( Organization _organization_1, Organization _organization_2 )
	{
		if( _organization_1 == null || _organization_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _organization_1.getId().longValue() == _organization_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		int value1= getOrganizationLayoutValueByOrganizationCode(_organization_1.getCode());
		int value2= getOrganizationLayoutValueByOrganizationCode(_organization_2.getCode());
		if( value1 == value2 )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		Organization organization_1 = null;
		Organization organization_2 = null;
		if( value1 > value2 )
		{
			organization_1 = _organization_2;
			organization_2 = _organization_1;
			blSwitched = true;
		}	
		else
		{
			organization_1 = _organization_1;
			organization_2 = _organization_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		Organization ancestorOrganization = organization_2.getParentOrganization();
		int _value2= getOrganizationLayoutValueByOrganizationCode(ancestorOrganization.getCode());
		while( ancestorOrganization != null  )
		{	
			if( _value2 == value1 )
			{
				break;
			}			
			ancestorOrganization = ancestorOrganization.getParentOrganization();
		}		
		// ancestorOrganization 不会为空
		if( ancestorOrganization.getId().longValue() == organization_1.getId().longValue() )
		{
			// organization_1 是 organization_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}	
	/**
	 * 用pathcode计算关系
	 * @param _organization_1
	 * @param _organization_2
	 * @return
	 */
	public static int relationOf_pathCode( Organization _organization_1, Organization _organization_2 )
	{
		if( _organization_1 == null || _organization_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _organization_1.getId().longValue() == _organization_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		int value1= getOrganizationLayoutValueByOrganizationCode(_organization_1.getCode());
		int value2= getOrganizationLayoutValueByOrganizationCode(_organization_2.getCode());
		if( value1 == value2 )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		String pathCode1 = _organization_1.getPathCode();
		String pathCode2 = _organization_2.getPathCode();
		
		if(pathCode1.indexOf(pathCode2)>=0)//p2>p1
		{
			return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
		}
		else if(pathCode2.indexOf(pathCode1)>=0)//p1>p2
		{
			return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		
//		boolean blSwitched = false;
//		Organization organization_1 = null;
//		Organization organization_2 = null;
//		if( value1 > value2 )
//		{
//			organization_1 = _organization_2;
//			organization_2 = _organization_1;
//			blSwitched = true;
//		}	
//		else
//		{
//			organization_1 = _organization_1;
//			organization_2 = _organization_2;
//			blSwitched = false;				
//		}			
//		
//		// 爬到一个层次上
//		Organization ancestorOrganization = organization_2.getParentOrganization();
//		int _value2= getOrganizationLayoutValueByOrganizationCode(ancestorOrganization.getCode());
//		while( ancestorOrganization != null  )
//		{	
//			if( _value2 == value1 )
//			{
//				break;
//			}			
//			ancestorOrganization = ancestorOrganization.getParentOrganization();
//		}		
//		// ancestorOrganization 不会为空
//		if( ancestorOrganization.getId().longValue() == organization_1.getId().longValue() )
//		{
//			// organization_1 是 organization_2的祖先
//			if( blSwitched == false )
//			{
//				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
//			}
//			else
//			{
//				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
//			}
//		}
//		else
//		{
//			// 无关
//			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
//		}
	}	

	/**
	 * 计算 _organization_1 与  _organization_2 的关系
	 * @param _organization_1
	 * @param _organization_2
	 * @return
	 */
	public static int relationOf( Organization _organization_1, Organization _organization_2 )
	{
		if( _organization_1 == null || _organization_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _organization_1.getId().longValue() == _organization_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		if( _organization_1.getOrganizationLayer().getValue() == _organization_2.getOrganizationLayer().getValue() )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		Organization organization_1 = null;
		Organization organization_2 = null;
		if(  _organization_1.getOrganizationLayer().getValue() > _organization_2.getOrganizationLayer().getValue() )
		{
			organization_1 = _organization_2;
			organization_2 = _organization_1;
			blSwitched = true;
		}	
		else
		{
			organization_1 = _organization_1;
			organization_2 = _organization_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		Organization ancestorOrganization = organization_2.getParentOrganization();
		while( ancestorOrganization != null  )
		{	
			if( ancestorOrganization.getOrganizationLayer().getValue() == organization_1.getOrganizationLayer().getValue() )
			{
				break;
			}			
			ancestorOrganization = ancestorOrganization.getParentOrganization();
		}		
		// ancestorOrganization 不会为空
		if( ancestorOrganization.getId().longValue() == organization_1.getId().longValue() )
		{
			// organization_1 是 organization_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}	
	
	
	/**
	 * 在 _organizationTreeRoot 为根的树中寻找 id = _tagrgetOrganizationId 的树节点
	 * @param _tagrgetOrganizationId
	 * @param _organizationTreeRoot
	 * @return
	 */
	public static Organization getOrganizationInTreeByRecursion( Long _tagrgetOrganizationId, Organization _organizationTreeRoot )
	{
		if( _tagrgetOrganizationId == null )
		{
			return null;
		}
		
		if( _organizationTreeRoot == null )
		{
			return null;				
		}
		
		if( _organizationTreeRoot.getId().longValue() == _tagrgetOrganizationId.longValue() )
		{
			return _organizationTreeRoot;
		}
		else if( _organizationTreeRoot.getSubOrganizations() == null || (_organizationTreeRoot.getSubOrganizations().isEmpty()) )
		{
			return null;
		}

		Iterator<Organization> itr_SubOrganizations = _organizationTreeRoot.getSubOrganizations().iterator();
		while( itr_SubOrganizations.hasNext() )
		{
			Organization subOrganization = itr_SubOrganizations.next();
			Organization rstTargetOrganization = getOrganizationInTreeByRecursion(_tagrgetOrganizationId, subOrganization );
			if( rstTargetOrganization != null )
			{
				return rstTargetOrganization;
			}
		}
		return null;
	}	
	public static int getOrganizationLayoutValueByOrganizationCode(String code)
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		int value = -1;
		try
		{
			trsa = session.beginTransaction();
			DaoOrganizationLayer dao = new DaoOrganizationLayer(session);
			value = dao.getOrganizationLayourValueByOrganizationCode(code);
			return value;
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		return -1;
	}

}
