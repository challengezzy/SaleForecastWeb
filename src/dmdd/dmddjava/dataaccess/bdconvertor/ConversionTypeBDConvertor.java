/**********************************************************************
 *$RCSfile:ConversionTypeBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BConversionType;
import dmdd.dmddjava.dataaccess.dataobject.ConversionType;

/**
 * <li>Title: ConversionTypeBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ConversionTypeBDConvertor implements BDConvertorInterface
{

	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BConversionType bConversionType = null;
		ConversionType   conversionType = null;
		
		if( b_obj == null )
		{
			bConversionType = new BConversionType();
		}
		else
		{
			bConversionType = (BConversionType) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			conversionType = (ConversionType) d_obj;
		}
		
		conversionType.setVersion( bConversionType.getVersion() );
		conversionType.setId( bConversionType.getId() );
		conversionType.setCode( bConversionType.getCode() );
		conversionType.setName( bConversionType.getName() );
		conversionType.setProportion(bConversionType.getProportion());
		if(bConversionType.getUnitGroup()!=null)
		{
			UnitGroupBDConvertor bd = new UnitGroupBDConvertor();
			conversionType.setUnitGroup(bd.btod(bConversionType.getUnitGroup(), true));
		}
	}

	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ConversionType   conversionType = null;
		BConversionType bConversionType = null;
		
		if( d_obj == null )
		{
			conversionType = new ConversionType();
		}
		else
		{
			conversionType = (ConversionType) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bConversionType = (BConversionType) b_obj;
		}
		
		bConversionType.setVersion( conversionType.getVersion() );
		bConversionType.setId( conversionType.getId() );
		bConversionType.setCode( conversionType.getCode() );
		bConversionType.setName( conversionType.getName() );
		bConversionType.setProportion(conversionType.getProportion());
		if(conversionType.getUnitGroup()!=null)
		{
			UnitGroupBDConvertor bd = new UnitGroupBDConvertor();
			bConversionType.setUnitGroup(bd.dtob(conversionType.getUnitGroup(), true));
		}
	}

	@Override
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		ConversionType ConversionType = new ConversionType();
		this.btod(b_obj, ConversionType);
		return ConversionType;
	}

	@Override
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}
		BConversionType bConversionType = new BConversionType();
		this.dtob(d_obj, bConversionType);
		return bConversionType;
	}

}

/**********************************************************************
 *$RCSfile:ConversionTypeBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:ConversionTypeBDConvertor.java,v $
 *********************************************************************/