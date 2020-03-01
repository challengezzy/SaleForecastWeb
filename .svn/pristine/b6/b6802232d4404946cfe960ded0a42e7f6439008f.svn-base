/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTEsmo;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsmo;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTEsmoBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTEsmoBDConvertor()
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
		BForecastModelMSTEsmo bForecastModelMSTEsmo = null;
		ForecastModelMSTEsmo   forecastModelMSTEsmo = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTEsmo = new BForecastModelMSTEsmo();
		}
		else
		{
			bForecastModelMSTEsmo = (BForecastModelMSTEsmo) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTEsmo = (ForecastModelMSTEsmo) d_obj;
		}
		
		super.btod(bForecastModelMSTEsmo, forecastModelMSTEsmo);
		
		forecastModelMSTEsmo.setTrendSmoothingFactor( bForecastModelMSTEsmo.getTrendSmoothingFactor() );
		forecastModelMSTEsmo.setTrendDampingIsValid( bForecastModelMSTEsmo.getTrendDampingIsValid() );
		forecastModelMSTEsmo.setTrendDampingFactor( bForecastModelMSTEsmo.getTrendDampingFactor() );
		forecastModelMSTEsmo.setSeasonSmoothingFactor( bForecastModelMSTEsmo.getSeasonSmoothingFactor() );
		forecastModelMSTEsmo.setInitDataPeriodNum( bForecastModelMSTEsmo.getInitDataPeriodNum() );
		forecastModelMSTEsmo.setLevelSmoothingFactor( bForecastModelMSTEsmo.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMSTEsmo forecastModelMSTEsmo = new ForecastModelMSTEsmo();
		this.btod(b_obj, forecastModelMSTEsmo);
		return forecastModelMSTEsmo;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTEsmo   forecastModelMSTEsmo = null;
		BForecastModelMSTEsmo bForecastModelMSTEsmo = null;
		
		if( d_obj == null )
		{
			forecastModelMSTEsmo = new ForecastModelMSTEsmo();
		}
		else
		{
			forecastModelMSTEsmo = (ForecastModelMSTEsmo) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTEsmo = (BForecastModelMSTEsmo) b_obj;
		}
		
		super.dtob(forecastModelMSTEsmo, bForecastModelMSTEsmo);
		
		bForecastModelMSTEsmo.setTrendSmoothingFactor( forecastModelMSTEsmo.getTrendSmoothingFactor() );
		bForecastModelMSTEsmo.setTrendDampingIsValid( forecastModelMSTEsmo.getTrendDampingIsValid() );
		bForecastModelMSTEsmo.setTrendDampingFactor( forecastModelMSTEsmo.getTrendDampingFactor() );
		bForecastModelMSTEsmo.setSeasonSmoothingFactor( forecastModelMSTEsmo.getSeasonSmoothingFactor() );
		bForecastModelMSTEsmo.setInitDataPeriodNum( forecastModelMSTEsmo.getInitDataPeriodNum() );
		bForecastModelMSTEsmo.setLevelSmoothingFactor( forecastModelMSTEsmo.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMSTEsmo bForecastModelMSTEsmo = new BForecastModelMSTEsmo();
		this.dtob(d_obj, bForecastModelMSTEsmo);
		return bForecastModelMSTEsmo;
	}

}
