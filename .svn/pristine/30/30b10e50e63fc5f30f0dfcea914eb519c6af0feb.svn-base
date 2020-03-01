/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;

/**
 * @author liuzhen
 *
 */
public class DaoProductCharacter extends Dao
{

	/**
	 * @param _session
	 */
	public DaoProductCharacter( Session _session )
	{
		super( _session );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}
	
	public ProductCharacter getProductCharacterTreeRoot()
	{
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		crit.add( Restrictions.isNull( "parentProductCharacter.id") );
		ProductCharacter treeRootProductCharacter = (ProductCharacter) crit.uniqueResult();
		return treeRootProductCharacter;
	}	
	
	public List<String> getAllProductCharacterTypes()
	{
		List<String> rstList = new ArrayList<String>();
		
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		crit.add( Restrictions.isNull( "parentProductCharacter.id") );
		ProductCharacter treeRootProductCharacter = (ProductCharacter) crit.uniqueResult();
		
		if( treeRootProductCharacter != null && treeRootProductCharacter.getSubProductCharacters() != null && !(treeRootProductCharacter.getSubProductCharacters().isEmpty()) )
		{
			Iterator<ProductCharacter> itr_subProductCharacter = treeRootProductCharacter.getSubProductCharacters().iterator();
			
			// 保证了第一层之间的特征类型是互不相同的
			while( itr_subProductCharacter.hasNext() )
			{
				rstList.add( itr_subProductCharacter.next().getType() );
			}			
		}
		
		return rstList;
	}		
	
	public ProductCharacter getProductCharacterById(Long _id)
	{
		Object obj = this.getSession().get(ProductCharacter.class, _id);
		if( obj == null )
		{
			return null;
		}
 
		return (ProductCharacter) obj;
	}	
	
	/**
	 * 查询 _id 的所有下级
	 * @param _id
	 * @param _blIncludeSelf	是否包含 _id 本身
	 * @return
	 */
	public List<ProductCharacter> getDescendentProductCharacters( Long _id, boolean _blIncludeSelf )
	{
		if( _id == null )
		{
			return null;
		}
		List<ProductCharacter> rstList = new ArrayList<ProductCharacter>();
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
			
			String sqlRestriction = null;
			if( _blIncludeSelf == true )
			{
				sqlRestriction = " (1=1) start with id = " + _id + " connect by prior id = parentproductcharacterid ";
			}
			else
			{
				sqlRestriction = " (1=1) start with parentproductcharacterid = " + _id + " connect by prior id = parentproductcharacterid ";
			}
	
			crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
			
			rstList = crit.list();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			if( _blIncludeSelf == true )
			{
				rstList = getProductCharacters(_id);
			}
			else
			{
				Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
				String sqlRestriction = "PARENTPRODUCTCHARACTERID ="+_id;
				crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
				List<ProductCharacter> _SubList = crit.list();
				for(ProductCharacter _productCharacter:_SubList)
				{
					rstList.addAll(getProductCharacters(_productCharacter.getId()));
				}
			}
		}
		return rstList;
	}		
	
	
	public List<ProductCharacter> getProductCharacters( String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		if( _sqlRestriction != null && !(_sqlRestriction.equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		}
		List<ProductCharacter> rstList = crit.list();
		return rstList;
	}	
	
	public ProductCharacter getDetialProductCharacterByCode( String _code )
	{
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		crit.add(Restrictions.eq( "isCatalog", BizConst.GLOBAL_YESNO_NO) );
		crit.add(Restrictions.eq( "code", _code ) );
		return (ProductCharacter) crit.uniqueResult();
	}		

	public List<ProductCharacter> getProductCharacters(Long _id)
	{
		List<ProductCharacter> rstList= new ArrayList<ProductCharacter>();
		if(_id!=null)
		{
			ProductCharacter productCharacter = getProductCharacterById(_id);
			if(productCharacter!=null)
			{
				rstList.add(productCharacter);
			}
		}
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		String _sqlRestriction = " PARENTPRODUCTCHARACTERID = "+_id;
		crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		List<ProductCharacter> _subList = crit.list();
		for(ProductCharacter _productCharacter:_subList)
		{
			rstList.addAll(getProductCharacters(_productCharacter.getId()));
		}
		return rstList;
	}
	
	public List<ProductCharacter> getSubProductCharacters(ProductCharacter _productCharacter)
	{
		List<ProductCharacter> list = new ArrayList<ProductCharacter>();
		
		if(_productCharacter!=null)
		{
			list.add( _productCharacter );
		}
		
		Criteria crit = this.getSession().createCriteria( ProductCharacter.class );
		String _sqlRestriction = " PARENTPRODUCTCHARACTERID = "+_productCharacter.getId();
		crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		List<ProductCharacter> _subList = crit.list();
		for(ProductCharacter productCharacter:_subList)
		{
			list.addAll(getSubProductCharacters(productCharacter));
		}
		
		return list;
	}
}
