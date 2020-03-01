/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWmaItem;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMLWmaItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastModelMLWmaItemBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BForecastModelMLWmaItem bForecastModelMLWmaItem = null;;
		ForecastModelMLWmaItem  forecastModelMLWmaItem  = null;
		
		if( b_obj == null )
		{
			bForecastModelMLWmaItem = new BForecastModelMLWmaItem();
		}
		else
		{
			bForecastModelMLWmaItem = (BForecastModelMLWmaItem)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMLWmaItem = (ForecastModelMLWmaItem)d_obj;
		}

		forecastModelMLWmaItem.setVersion( bForecastModelMLWmaItem.getVersion() );
		forecastModelMLWmaItem.setId( bForecastModelMLWmaItem.getId() );
		forecastModelMLWmaItem.setPeriodSeqNo( bForecastModelMLWmaItem.getPeriodSeqNo() );
		forecastModelMLWmaItem.setCoefficient( bForecastModelMLWmaItem.getCoefficient() );
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
		
		ForecastModelMLWmaItem forecastModelMLWmaItem = new ForecastModelMLWmaItem();
		this.btod( b_obj, forecastModelMLWmaItem );
		return forecastModelMLWmaItem;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		ForecastModelMLWmaItem  forecastModelMLWmaItem  = null;
		BForecastModelMLWmaItem bForecastModelMLWmaItem = null;
		
		if( d_obj == null )
		{
			forecastModelMLWmaItem = new ForecastModelMLWmaItem();
		}
		else
		{
			forecastModelMLWmaItem = (ForecastModelMLWmaItem)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMLWmaItem = (BForecastModelMLWmaItem)b_obj;
		}
	
		
		bForecastModelMLWmaItem.setVersion( forecastModelMLWmaItem.getVersion() );
		bForecastModelMLWmaItem.setId( forecastModelMLWmaItem.getId() );
		bForecastModelMLWmaItem.setPeriodSeqNo( forecastModelMLWmaItem.getPeriodSeqNo() );
		bForecastModelMLWmaItem.setCoefficient( forecastModelMLWmaItem.getCoefficient() );
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
		
		BForecastModelMLWmaItem bForecastModelMLWmaItem = new BForecastModelMLWmaItem();
		this.dtob( d_obj, bForecastModelMLWmaItem );
		return bForecastModelMLWmaItem;	
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
