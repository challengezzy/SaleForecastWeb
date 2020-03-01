/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTLa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTLa;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTLaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTLaBDConvertor()
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
		BForecastModelMSTLa bForecastModelMSTLa = null;
		ForecastModelMSTLa   forecastModelMSTLa = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTLa = new BForecastModelMSTLa();
		}
		else
		{
			bForecastModelMSTLa = (BForecastModelMSTLa) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTLa = (ForecastModelMSTLa) d_obj;
		}
		
		super.btod(bForecastModelMSTLa, forecastModelMSTLa);
		
		forecastModelMSTLa.setDataPeriodNum( bForecastModelMSTLa.getDataPeriodNum() );
		forecastModelMSTLa.setTrendDampingIsValid( bForecastModelMSTLa.getTrendDampingIsValid() );
		forecastModelMSTLa.setTrendDampingFactor( bForecastModelMSTLa.getTrendDampingFactor() );
		forecastModelMSTLa.setSeasonSmoothingFactor( bForecastModelMSTLa.getSeasonSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMSTLa forecastModelMSTLa = new ForecastModelMSTLa();
		this.btod(b_obj, forecastModelMSTLa);
		return forecastModelMSTLa;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTLa   forecastModelMSTLa = null;
		BForecastModelMSTLa bForecastModelMSTLa = null;
		
		if( d_obj == null )
		{
			forecastModelMSTLa = new ForecastModelMSTLa();
		}
		else
		{
			forecastModelMSTLa = (ForecastModelMSTLa) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTLa = (BForecastModelMSTLa) b_obj;
		}
		
		super.dtob(forecastModelMSTLa, bForecastModelMSTLa);
		
		bForecastModelMSTLa.setDataPeriodNum( forecastModelMSTLa.getDataPeriodNum() );
		bForecastModelMSTLa.setTrendDampingIsValid( forecastModelMSTLa.getTrendDampingIsValid() );
		bForecastModelMSTLa.setTrendDampingFactor( forecastModelMSTLa.getTrendDampingFactor() );
		bForecastModelMSTLa.setSeasonSmoothingFactor( forecastModelMSTLa.getSeasonSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMSTLa bForecastModelMSTLa = new BForecastModelMSTLa();
		this.dtob(d_obj, bForecastModelMSTLa);
		return bForecastModelMSTLa;
	}

}
