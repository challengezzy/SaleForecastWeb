/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BSysDictionaryItem;
import dmdd.dmddjava.dataaccess.dataobject.SysDictionaryItem;


/**
 * @author liuzhen
 *
 */
public class SysDictionaryItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public SysDictionaryItemBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BSysDictionaryItem bSysDictionaryItem = null;
		SysDictionaryItem   sysDictionaryItem = null;
		if( b_obj == null )
		{
			bSysDictionaryItem = new BSysDictionaryItem();
		}
		else
		{
			bSysDictionaryItem = (BSysDictionaryItem) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			sysDictionaryItem = (SysDictionaryItem) d_obj;
		}
		
		sysDictionaryItem.setVersion( bSysDictionaryItem.getVersion() );
		sysDictionaryItem.setId( bSysDictionaryItem.getId() );
		sysDictionaryItem.setClassName( bSysDictionaryItem.getClassName() );
		sysDictionaryItem.setAttributeName( bSysDictionaryItem.getAttributeName() );
		sysDictionaryItem.setValue( bSysDictionaryItem.getValue() );
		sysDictionaryItem.setValueDesc( bSysDictionaryItem.getValueDesc() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
		this.btod(b_obj, sysDictionaryItem);
		return sysDictionaryItem;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		SysDictionaryItem   sysDictionaryItem = null;
		BSysDictionaryItem bSysDictionaryItem = null;
		
		if( d_obj == null )
		{
			sysDictionaryItem = new SysDictionaryItem();
		}
		else
		{
			sysDictionaryItem = (SysDictionaryItem) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bSysDictionaryItem = (BSysDictionaryItem) b_obj;
		}
		
		bSysDictionaryItem.setVersion( sysDictionaryItem.getVersion() );
		bSysDictionaryItem.setId( sysDictionaryItem.getId() );
		bSysDictionaryItem.setClassName( sysDictionaryItem.getClassName() );
		bSysDictionaryItem.setAttributeName( sysDictionaryItem.getAttributeName() );
		bSysDictionaryItem.setValue( sysDictionaryItem.getValue() );
		bSysDictionaryItem.setValueDesc( sysDictionaryItem.getValueDesc() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BSysDictionaryItem bSysDictionaryItem = new BSysDictionaryItem();
		this.dtob(d_obj, bSysDictionaryItem);
		return bSysDictionaryItem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
