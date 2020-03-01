/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemKpi;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemKpiBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemKpiBDConvertor()
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

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性aitemBizData、bitemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemKpi bBizDataDefItemKpi = null;
		BizDataDefItemKpi   bizDataDefItemKpi = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemKpi = new BBizDataDefItemKpi();
		}
		else
		{
			bBizDataDefItemKpi = (BBizDataDefItemKpi) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemKpi = (BizDataDefItemKpi) d_obj;
		}
		
		super.btod(bBizDataDefItemKpi, bizDataDefItemKpi);
		
		bizDataDefItemKpi.setKpiFormula(bBizDataDefItemKpi.getKpiFormula());
		
		if( bBizDataDefItemKpi.getAitemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData aitemBizData = bizDataBDConvertor.btod( bBizDataDefItemKpi.getAitemBizData(), true );
			bizDataDefItemKpi.setAitemBizData(aitemBizData);
		}
		if( bBizDataDefItemKpi.getBitemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData bitemBizData = bizDataBDConvertor.btod( bBizDataDefItemKpi.getBitemBizData(), true );
			bizDataDefItemKpi.setBitemBizData(bitemBizData);
		}		
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性aitemBizData、bitemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		BizDataDefItemKpi bizDataDefItemKpi = new BizDataDefItemKpi();
		this.btod(b_obj, bizDataDefItemKpi);
		return bizDataDefItemKpi;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性aitemBizData、bitemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemKpi   bizDataDefItemKpi = null;
		BBizDataDefItemKpi bBizDataDefItemKpi = null;
				
		if( d_obj == null )
		{
			bizDataDefItemKpi = new BizDataDefItemKpi();
		}
		else
		{
			bizDataDefItemKpi = (BizDataDefItemKpi) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemKpi = (BBizDataDefItemKpi) b_obj;
		}
		
		super.dtob(bizDataDefItemKpi, bBizDataDefItemKpi);
		
		bBizDataDefItemKpi.setKpiFormula( bizDataDefItemKpi.getKpiFormula() );

		if( bizDataDefItemKpi.getAitemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData aitemBizData = bizDataBDConvertor.dtob( bizDataDefItemKpi.getAitemBizData(), true );
			bBizDataDefItemKpi.setAitemBizData(aitemBizData);
		}
		if( bizDataDefItemKpi.getBitemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData bitemBizData = bizDataBDConvertor.dtob( bizDataDefItemKpi.getBitemBizData(), true );
			bBizDataDefItemKpi.setBitemBizData(bitemBizData);
		}		
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性aitemBizData、bitemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BBizDataDefItemKpi bBizDataDefItemKpi = new BBizDataDefItemKpi();
		this.dtob(d_obj, bBizDataDefItemKpi);
		return bBizDataDefItemKpi;
	}

}
