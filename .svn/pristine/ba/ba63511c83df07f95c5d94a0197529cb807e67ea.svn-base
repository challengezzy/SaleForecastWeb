/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWmaItem;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSLWmaItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastModelMSLWmaItemBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BForecastModelMSLWmaItem bForecastModelMSLWmaItem = null;;
		ForecastModelMSLWmaItem  forecastModelMSLWmaItem  = null;
		
		if( b_obj == null )
		{
			bForecastModelMSLWmaItem = new BForecastModelMSLWmaItem();
		}
		else
		{
			bForecastModelMSLWmaItem = (BForecastModelMSLWmaItem)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSLWmaItem = (ForecastModelMSLWmaItem)d_obj;
		}

		forecastModelMSLWmaItem.setVersion( bForecastModelMSLWmaItem.getVersion() );
		forecastModelMSLWmaItem.setId( bForecastModelMSLWmaItem.getId() );
		forecastModelMSLWmaItem.setPeriodSeqNo( bForecastModelMSLWmaItem.getPeriodSeqNo() );
		forecastModelMSLWmaItem.setCoefficient( bForecastModelMSLWmaItem.getCoefficient() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		if( b_obj == null )
		{
			return null;
		}
		
		ForecastModelMSLWmaItem forecastModelMSLWmaItem = new ForecastModelMSLWmaItem();
		this.btod( b_obj, forecastModelMSLWmaItem );
		return forecastModelMSLWmaItem;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		ForecastModelMSLWmaItem  forecastModelMSLWmaItem  = null;
		BForecastModelMSLWmaItem bForecastModelMSLWmaItem = null;
		
		if( d_obj == null )
		{
			forecastModelMSLWmaItem = new ForecastModelMSLWmaItem();
		}
		else
		{
			forecastModelMSLWmaItem = (ForecastModelMSLWmaItem)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSLWmaItem = (BForecastModelMSLWmaItem)b_obj;
		}
	
		
		bForecastModelMSLWmaItem.setVersion( forecastModelMSLWmaItem.getVersion() );
		bForecastModelMSLWmaItem.setId( forecastModelMSLWmaItem.getId() );
		bForecastModelMSLWmaItem.setPeriodSeqNo( forecastModelMSLWmaItem.getPeriodSeqNo() );
		bForecastModelMSLWmaItem.setCoefficient( forecastModelMSLWmaItem.getCoefficient() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		if( d_obj == null )
		{
			return null;
		}
		
		BForecastModelMSLWmaItem bForecastModelMSLWmaItem = new BForecastModelMSLWmaItem();
		this.dtob( d_obj, bForecastModelMSLWmaItem );
		return bForecastModelMSLWmaItem;	
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
