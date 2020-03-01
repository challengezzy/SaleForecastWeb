/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BUnit;
import dmdd.dmddjava.dataaccess.dataobject.Unit;

/**
 * @author liuzhen
 *
 */
public class UnitBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public UnitBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性unitGroup,不处理;   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BUnit bUnit = null;;
		Unit  unit  = null;
		
		if( b_obj == null )
		{
			bUnit = new BUnit();
		}
		else
		{
			bUnit = (BUnit)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			unit = (Unit)d_obj;
		}

		unit.setVersion( bUnit.getVersion() );
		unit.setId( bUnit.getId() );
		unit.setCode( bUnit.getCode() );
		unit.setName( bUnit.getName() );
		unit.setExchangeRate( bUnit.getExchangeRate() );
		unit.setIsBase( bUnit.getIsBase() );
		unit.setComments( bUnit.getComments() );
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性unitGroup,不处理;    
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		Unit unit = new Unit();
		this.btod( b_obj, unit );
		return unit;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性unitGroup,不处理;    
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		Unit  unit  = null;
		BUnit bUnit = null;
		
		if( d_obj == null )
		{
			unit = new Unit();
		}
		else
		{
			unit = (Unit)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bUnit = (BUnit)b_obj;
		}
	
		
		bUnit.setVersion( unit.getVersion() );
		bUnit.setId( unit.getId() );
		bUnit.setCode( unit.getCode() );
		bUnit.setName( unit.getName() );
		bUnit.setExchangeRate( unit.getExchangeRate() );
		bUnit.setIsBase( unit.getIsBase() );
		bUnit.setComments( unit.getComments() );
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性unitGroup,不处理;    
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BUnit bUnit = new BUnit();
		this.dtob( d_obj, bUnit );
		return bUnit;		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
