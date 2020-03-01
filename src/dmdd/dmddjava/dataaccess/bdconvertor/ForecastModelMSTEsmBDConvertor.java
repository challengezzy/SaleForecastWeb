/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTEsm;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsm;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTEsmBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTEsmBDConvertor()
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
		BForecastModelMSTEsm bForecastModelMSTEsm = null;
		ForecastModelMSTEsm   forecastModelMSTEsm = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTEsm = new BForecastModelMSTEsm();
		}
		else
		{
			bForecastModelMSTEsm = (BForecastModelMSTEsm) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTEsm = (ForecastModelMSTEsm) d_obj;
		}
		
		super.btod(bForecastModelMSTEsm, forecastModelMSTEsm);
		
		forecastModelMSTEsm.setTrendSmoothingFactor( bForecastModelMSTEsm.getTrendSmoothingFactor() );
		forecastModelMSTEsm.setTrendDampingIsValid( bForecastModelMSTEsm.getTrendDampingIsValid() );
		forecastModelMSTEsm.setTrendDampingFactor( bForecastModelMSTEsm.getTrendDampingFactor() );
		forecastModelMSTEsm.setSeasonSmoothingFactor( bForecastModelMSTEsm.getSeasonSmoothingFactor() );
		forecastModelMSTEsm.setInitDataPeriodNum( bForecastModelMSTEsm.getInitDataPeriodNum() );
		forecastModelMSTEsm.setLevelSmoothingFactor( bForecastModelMSTEsm.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMSTEsm forecastModelMSTEsm = new ForecastModelMSTEsm();
		this.btod(b_obj, forecastModelMSTEsm);
		return forecastModelMSTEsm;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTEsm   forecastModelMSTEsm = null;
		BForecastModelMSTEsm bForecastModelMSTEsm = null;
		
		if( d_obj == null )
		{
			forecastModelMSTEsm = new ForecastModelMSTEsm();
		}
		else
		{
			forecastModelMSTEsm = (ForecastModelMSTEsm) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTEsm = (BForecastModelMSTEsm) b_obj;
		}
		
		super.dtob(forecastModelMSTEsm, bForecastModelMSTEsm);
		
		bForecastModelMSTEsm.setTrendSmoothingFactor( forecastModelMSTEsm.getTrendSmoothingFactor() );
		bForecastModelMSTEsm.setTrendDampingIsValid( forecastModelMSTEsm.getTrendDampingIsValid() );
		bForecastModelMSTEsm.setTrendDampingFactor( forecastModelMSTEsm.getTrendDampingFactor() );
		bForecastModelMSTEsm.setSeasonSmoothingFactor( forecastModelMSTEsm.getSeasonSmoothingFactor() );
		bForecastModelMSTEsm.setInitDataPeriodNum( forecastModelMSTEsm.getInitDataPeriodNum() );
		bForecastModelMSTEsm.setLevelSmoothingFactor( forecastModelMSTEsm.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMSTEsm bForecastModelMSTEsm = new BForecastModelMSTEsm();
		this.dtob(d_obj, bForecastModelMSTEsm);
		return bForecastModelMSTEsm;
	}

}
