/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTPly;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTPly;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTPlyBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTPlyBDConvertor()
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
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastModelMSTPly bForecastModelMSTPly = null;
		ForecastModelMSTPly   forecastModelMSTPly = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTPly = new BForecastModelMSTPly();
		}
		else
		{
			bForecastModelMSTPly = (BForecastModelMSTPly) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTPly = (ForecastModelMSTPly) d_obj;
		}
		
		super.btod(bForecastModelMSTPly, forecastModelMSTPly);
		
		forecastModelMSTPly.setPercentValue( bForecastModelMSTPly.getPercentValue() );
		forecastModelMSTPly.setSeasonSmoothingFactor( bForecastModelMSTPly.getSeasonSmoothingFactor() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		
		ForecastModelMSTPly forecastModelMSTPly = new ForecastModelMSTPly();
		this.btod(b_obj, forecastModelMSTPly);
		return forecastModelMSTPly;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTPly   forecastModelMSTPly = null;
		BForecastModelMSTPly bForecastModelMSTPly = null;
		
		if( d_obj == null )
		{
			forecastModelMSTPly = new ForecastModelMSTPly();
		}
		else
		{
			forecastModelMSTPly = (ForecastModelMSTPly) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTPly = (BForecastModelMSTPly) b_obj;
		}
		
		super.dtob(forecastModelMSTPly, bForecastModelMSTPly);
		
		bForecastModelMSTPly.setPercentValue( forecastModelMSTPly.getPercentValue() );
		bForecastModelMSTPly.setSeasonSmoothingFactor( forecastModelMSTPly.getSeasonSmoothingFactor() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}
		
		BForecastModelMSTPly bForecastModelMSTPly = new BForecastModelMSTPly();
		this.dtob(d_obj, bForecastModelMSTPly);
		return bForecastModelMSTPly;
	}

}
