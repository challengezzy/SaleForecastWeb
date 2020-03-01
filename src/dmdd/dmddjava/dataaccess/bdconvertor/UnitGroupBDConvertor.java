/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BUnit;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;

/**
 * @author liuzhen
 *
 */
public class UnitGroupBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public UnitGroupBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 下附的集合属性units,不处理;    
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BUnitGroup bUnitGroup = null;;
		UnitGroup  unitGroup  = null;
		
		if( b_obj == null )
		{
			bUnitGroup = new BUnitGroup();
		}
		else
		{
			bUnitGroup = (BUnitGroup)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			unitGroup = (UnitGroup)d_obj;
		}
		

		unitGroup.setVersion( bUnitGroup.getVersion() );
		unitGroup.setId( bUnitGroup.getId() );
		unitGroup.setCode( bUnitGroup.getCode() );
		unitGroup.setName( bUnitGroup.getName() );
		unitGroup.setDescription( bUnitGroup.getDescription() );
		unitGroup.setComments( bUnitGroup.getComments() );
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 下附的集合属性units,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		UnitGroup unitGroup = new UnitGroup();
		this.btod( b_obj, unitGroup );
		return unitGroup;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 下附的集合属性units,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		UnitGroup  unitGroup  = null;
		BUnitGroup bUnitGroup = null;
		
		if( d_obj == null )
		{
			unitGroup = new UnitGroup();
		}
		else
		{
			unitGroup = (UnitGroup)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}	
		else
		{
			bUnitGroup = (BUnitGroup)b_obj;
		}
		
		bUnitGroup.setVersion( unitGroup.getVersion() );
		bUnitGroup.setId( unitGroup.getId() );
		bUnitGroup.setCode( unitGroup.getCode() );
		bUnitGroup.setName( unitGroup.getName() );
		bUnitGroup.setDescription( unitGroup.getDescription() );
		bUnitGroup.setComments( unitGroup.getComments() );
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 下附的集合属性units,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BUnitGroup bUnitGroup = new BUnitGroup();
		this.dtob( d_obj, bUnitGroup );
		return bUnitGroup;
	}
	

	public void btod(BUnitGroup _bUnitGroup, UnitGroup _unitGroup, boolean _blWithUnits )
	{
		if( _unitGroup == null )
		{
			return;
		}
		
		this.btod(_bUnitGroup, _unitGroup);
		
		if( _blWithUnits == true )
		{
			if( _bUnitGroup != null && _bUnitGroup.getUnits() != null && _bUnitGroup.getUnits().iterator() != null )
			{
				UnitBDConvertor unitBDConvertor = new UnitBDConvertor();
				Iterator<BUnit> bUnitsItr = _bUnitGroup.getUnits().iterator();
				while( bUnitsItr.hasNext() )
				{
					Unit unit = (Unit)unitBDConvertor.btod(bUnitsItr.next());
					unit.setUnitGroup(_unitGroup);
					_unitGroup.addUnit( unit );
				}
			}					
		}

	}
	

	public UnitGroup btod(BUnitGroup _bUnitGroup, boolean _blWithUnits)
	{
		UnitGroup unitGroup = new UnitGroup();
		this.btod(_bUnitGroup, unitGroup,_blWithUnits);
		return unitGroup;
	}
	

	public void dtob(UnitGroup _unitGroup, BUnitGroup _bUnitGroup, boolean _blWithUnits)
	{
		if( _bUnitGroup == null )
		{
			return;
		}
		
		this.dtob(_unitGroup, _bUnitGroup);
		
		if( _blWithUnits == true )
		{
			if( _unitGroup != null && _unitGroup.getUnits() != null && _unitGroup.getUnits().iterator() != null )
			{
				UnitBDConvertor unitBDConvertor = new UnitBDConvertor();
				Iterator<Unit> unitsItr = _unitGroup.getUnits().iterator();
				while( unitsItr.hasNext() )
				{
					BUnit bUnit = (BUnit)unitBDConvertor.dtob(unitsItr.next());
					bUnit.setUnitGroup(_bUnitGroup);
					_bUnitGroup.addUnit( bUnit );
				}
			}				
		}
			
	}
	
	public BUnitGroup dtob(UnitGroup _unitGroup, boolean _blWithUnits)
	{
		BUnitGroup bUnitGroup = new BUnitGroup();
		this.dtob(_unitGroup, bUnitGroup, _blWithUnits);
		return bUnitGroup;
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		/*
		BUnitGroup bUnitGroup = null;
		UnitGroup unitGroup = new UnitGroup();
		UnitGroupBDConvertor ctr = new UnitGroupBDConvertor();
		ctr.btod(bUnitGroup, unitGroup);
		System.out.print("this is happen!");
		*/
		Long a = new Long(100);
		Long b = null;
		if( a.equals(b))
		{
			System.out.print("this is happen!");
		}
		else
		{
			System.out.print("fuck!");
		}
		

	}

}
