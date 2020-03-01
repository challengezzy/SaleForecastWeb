/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTEsa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsa;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTEsaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTEsaBDConvertor()
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
		BForecastModelMSTEsa bForecastModelMSTEsa = null;
		ForecastModelMSTEsa   forecastModelMSTEsa = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTEsa = new BForecastModelMSTEsa();
		}
		else
		{
			bForecastModelMSTEsa = (BForecastModelMSTEsa) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTEsa = (ForecastModelMSTEsa) d_obj;
		}
		
		super.btod(bForecastModelMSTEsa, forecastModelMSTEsa);
		
		forecastModelMSTEsa.setTrendSmoothingFactor( bForecastModelMSTEsa.getTrendSmoothingFactor() );
		forecastModelMSTEsa.setTrendDampingIsValid( bForecastModelMSTEsa.getTrendDampingIsValid() );
		forecastModelMSTEsa.setTrendDampingFactor( bForecastModelMSTEsa.getTrendDampingFactor() );
		forecastModelMSTEsa.setSeasonSmoothingFactor( bForecastModelMSTEsa.getSeasonSmoothingFactor() );
		forecastModelMSTEsa.setInitDataPeriodNum( bForecastModelMSTEsa.getInitDataPeriodNum() );
		forecastModelMSTEsa.setLevelSmoothingFactor( bForecastModelMSTEsa.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMSTEsa forecastModelMSTEsa = new ForecastModelMSTEsa();
		this.btod(b_obj, forecastModelMSTEsa);
		return forecastModelMSTEsa;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTEsa   forecastModelMSTEsa = null;
		BForecastModelMSTEsa bForecastModelMSTEsa = null;
		
		if( d_obj == null )
		{
			forecastModelMSTEsa = new ForecastModelMSTEsa();
		}
		else
		{
			forecastModelMSTEsa = (ForecastModelMSTEsa) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTEsa = (BForecastModelMSTEsa) b_obj;
		}
		
		super.dtob(forecastModelMSTEsa, bForecastModelMSTEsa);
		
		bForecastModelMSTEsa.setTrendSmoothingFactor( forecastModelMSTEsa.getTrendSmoothingFactor() );
		bForecastModelMSTEsa.setTrendDampingIsValid( forecastModelMSTEsa.getTrendDampingIsValid() );
		bForecastModelMSTEsa.setTrendDampingFactor( forecastModelMSTEsa.getTrendDampingFactor() );
		bForecastModelMSTEsa.setSeasonSmoothingFactor( forecastModelMSTEsa.getSeasonSmoothingFactor() );
		bForecastModelMSTEsa.setInitDataPeriodNum( forecastModelMSTEsa.getInitDataPeriodNum() );
		bForecastModelMSTEsa.setLevelSmoothingFactor( forecastModelMSTEsa.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMSTEsa bForecastModelMSTEsa = new BForecastModelMSTEsa();
		this.dtob(d_obj, bForecastModelMSTEsa);
		return bForecastModelMSTEsa;
	}

}
