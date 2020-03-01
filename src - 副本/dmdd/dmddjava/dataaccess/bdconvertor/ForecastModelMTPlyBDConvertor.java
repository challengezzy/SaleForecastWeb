/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMTPly;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTPly;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMTPlyBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMTPlyBDConvertor()
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
		BForecastModelMTPly bForecastModelMTPly = null;
		ForecastModelMTPly   forecastModelMTPly = null;		
		
		if( b_obj == null )
		{
			bForecastModelMTPly = new BForecastModelMTPly();
		}
		else
		{
			bForecastModelMTPly = (BForecastModelMTPly) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMTPly = (ForecastModelMTPly) d_obj;
		}
		
		super.btod(bForecastModelMTPly, forecastModelMTPly);
		
		forecastModelMTPly.setPercentValue( bForecastModelMTPly.getPercentValue() );
		
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
		
		ForecastModelMTPly forecastModelMTPly = new ForecastModelMTPly();
		this.btod(b_obj, forecastModelMTPly);
		return forecastModelMTPly;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMTPly   forecastModelMTPly = null;
		BForecastModelMTPly bForecastModelMTPly = null;
		
		if( d_obj == null )
		{
			forecastModelMTPly = new ForecastModelMTPly();
		}
		else
		{
			forecastModelMTPly = (ForecastModelMTPly) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMTPly = (BForecastModelMTPly) b_obj;
		}
		
		super.dtob(forecastModelMTPly, bForecastModelMTPly);
		
		bForecastModelMTPly.setPercentValue( forecastModelMTPly.getPercentValue() );

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
		
		BForecastModelMTPly bForecastModelMTPly = new BForecastModelMTPly();
		this.dtob(d_obj, bForecastModelMTPly);
		return bForecastModelMTPly;
	}

}
