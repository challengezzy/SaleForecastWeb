/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMTEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTEso;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMTEsoBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMTEsoBDConvertor()
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
		BForecastModelMTEso bForecastModelMTEso = null;
		ForecastModelMTEso   forecastModelMTEso = null;		
		
		if( b_obj == null )
		{
			bForecastModelMTEso = new BForecastModelMTEso();
		}
		else
		{
			bForecastModelMTEso = (BForecastModelMTEso) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMTEso = (ForecastModelMTEso) d_obj;
		}
		
		super.btod(bForecastModelMTEso, forecastModelMTEso);
		
		forecastModelMTEso.setLevelSmoothingFactor( bForecastModelMTEso.getLevelSmoothingFactor() );
		forecastModelMTEso.setTrendSmoothingFactor( bForecastModelMTEso.getTrendSmoothingFactor() );
		forecastModelMTEso.setTrendDampingIsValid( bForecastModelMTEso.getTrendDampingIsValid() );
		forecastModelMTEso.setTrendDampingFactor( bForecastModelMTEso.getTrendDampingFactor() );
		forecastModelMTEso.setInitDataPeriodNum( bForecastModelMTEso.getInitDataPeriodNum() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMTEso forecastModelMTEso = new ForecastModelMTEso();
		this.btod(b_obj, forecastModelMTEso);
		return forecastModelMTEso;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMTEso   forecastModelMTEso = null;
		BForecastModelMTEso bForecastModelMTEso = null;
		
		if( d_obj == null )
		{
			forecastModelMTEso = new ForecastModelMTEso();
		}
		else
		{
			forecastModelMTEso = (ForecastModelMTEso) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMTEso = (BForecastModelMTEso) b_obj;
		}
		
		super.dtob(forecastModelMTEso, bForecastModelMTEso);
		
		bForecastModelMTEso.setLevelSmoothingFactor( forecastModelMTEso.getLevelSmoothingFactor() );
		bForecastModelMTEso.setTrendSmoothingFactor( forecastModelMTEso.getTrendSmoothingFactor() );
		bForecastModelMTEso.setTrendDampingIsValid( forecastModelMTEso.getTrendDampingIsValid() );
		bForecastModelMTEso.setTrendDampingFactor( forecastModelMTEso.getTrendDampingFactor() );
		bForecastModelMTEso.setInitDataPeriodNum( forecastModelMTEso.getInitDataPeriodNum() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMTEso bForecastModelMTEso = new BForecastModelMTEso();
		this.dtob(d_obj, bForecastModelMTEso);
		return bForecastModelMTEso;
	}

}
