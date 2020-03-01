/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMTEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTEs;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMTEsBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMTEsBDConvertor()
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
		BForecastModelMTEs bForecastModelMTEs = null;
		ForecastModelMTEs   forecastModelMTEs = null;		
		
		if( b_obj == null )
		{
			bForecastModelMTEs = new BForecastModelMTEs();
		}
		else
		{
			bForecastModelMTEs = (BForecastModelMTEs) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMTEs = (ForecastModelMTEs) d_obj;
		}
		
		super.btod(bForecastModelMTEs, forecastModelMTEs);
		
		forecastModelMTEs.setLevelSmoothingFactor( bForecastModelMTEs.getLevelSmoothingFactor() );
		forecastModelMTEs.setTrendSmoothingFactor( bForecastModelMTEs.getTrendSmoothingFactor() );
		forecastModelMTEs.setTrendDampingIsValid( bForecastModelMTEs.getTrendDampingIsValid() );
		forecastModelMTEs.setTrendDampingFactor( bForecastModelMTEs.getTrendDampingFactor() );
		forecastModelMTEs.setInitDataPeriodNum( bForecastModelMTEs.getInitDataPeriodNum() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMTEs forecastModelMTEs = new ForecastModelMTEs();
		this.btod(b_obj, forecastModelMTEs);
		return forecastModelMTEs;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMTEs   forecastModelMTEs = null;
		BForecastModelMTEs bForecastModelMTEs = null;
		
		if( d_obj == null )
		{
			forecastModelMTEs = new ForecastModelMTEs();
		}
		else
		{
			forecastModelMTEs = (ForecastModelMTEs) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMTEs = (BForecastModelMTEs) b_obj;
		}
		
		super.dtob(forecastModelMTEs, bForecastModelMTEs);
		
		bForecastModelMTEs.setLevelSmoothingFactor( forecastModelMTEs.getLevelSmoothingFactor() );
		bForecastModelMTEs.setTrendSmoothingFactor( forecastModelMTEs.getTrendSmoothingFactor() );
		bForecastModelMTEs.setTrendDampingIsValid( forecastModelMTEs.getTrendDampingIsValid() );
		bForecastModelMTEs.setTrendDampingFactor( forecastModelMTEs.getTrendDampingFactor() );
		bForecastModelMTEs.setInitDataPeriodNum( forecastModelMTEs.getInitDataPeriodNum() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMTEs bForecastModelMTEs = new BForecastModelMTEs();
		this.dtob(d_obj, bForecastModelMTEs);
		return bForecastModelMTEs;
	}

}
