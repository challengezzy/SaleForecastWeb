/**********************************************************************
 *$RCSfile:ConversionSetBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BConversionSet;
import dmdd.dmddjava.dataaccess.bizobject.BConversionType;
import dmdd.dmddjava.dataaccess.dataobject.ConversionSet;
import dmdd.dmddjava.dataaccess.dataobject.ConversionType;

/**
 * <li>Title: ConversionSetBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ConversionSetBDConvertor implements BDConvertorInterface
{

	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BConversionSet bConversionSet = null;
		ConversionSet   conversionSet = null;
		
		if( b_obj == null )
		{
			bConversionSet = new BConversionSet();
		}
		else
		{
			bConversionSet = (BConversionSet) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			conversionSet = (ConversionSet) d_obj;
		}
		conversionSet.setId( bConversionSet.getId() );
		if( bConversionSet.getProduct()!=null)
		{
			ProductBDConvertor bd_product = new ProductBDConvertor();
			conversionSet.setProduct( bd_product.btod(bConversionSet.getProduct() ,false));
		}
		if(bConversionSet.getConversionType()!=null)
		{
			ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
			conversionSet.setConversionType((ConversionType)(bd.btod(bConversionSet.getConversionType())));
		}
	}

	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ConversionSet   conversionSet = null;
		BConversionSet bConversionSet = null;
		
		if( d_obj == null )
		{
			conversionSet = new ConversionSet();
		}
		else
		{
			conversionSet = (ConversionSet) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bConversionSet = (BConversionSet) b_obj;
		}

		bConversionSet.setId( conversionSet.getId() );
		if( conversionSet.getProduct()!=null)
		{
			ProductBDConvertor bd_product = new ProductBDConvertor();
			bConversionSet.setProduct( bd_product.dtob(conversionSet.getProduct() ,false));
		}
		if(conversionSet.getConversionType()!=null)
		{
			ConversionTypeBDConvertor bd = new ConversionTypeBDConvertor();
			bConversionSet.setConversionType((BConversionType)(bd.dtob(conversionSet.getConversionType())));
		}
		
	}

	@Override
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		ConversionSet ConversionSet = new ConversionSet();
		this.btod(b_obj, ConversionSet);
		return ConversionSet;
	}

	@Override
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}
		BConversionSet bConversionSet = new BConversionSet();
		this.dtob(d_obj, bConversionSet);
		return bConversionSet;
	}

}

/**********************************************************************
 *$RCSfile:ConversionSetBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:ConversionSetBDConvertor.java,v $
 *********************************************************************/