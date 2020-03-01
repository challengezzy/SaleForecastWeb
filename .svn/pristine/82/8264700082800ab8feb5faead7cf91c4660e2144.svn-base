/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLEso;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSLEsoBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSLEsoBDConvertor()
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
		BForecastModelMSLEso bForecastModelMSLEso = null;
		ForecastModelMSLEso   forecastModelMSLEso = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSLEso = new BForecastModelMSLEso();
		}
		else
		{
			bForecastModelMSLEso = (BForecastModelMSLEso) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSLEso = (ForecastModelMSLEso) d_obj;
		}
		
		super.btod(bForecastModelMSLEso, forecastModelMSLEso);
		
		forecastModelMSLEso.setInitDataPeriodNum( bForecastModelMSLEso.getInitDataPeriodNum() );
		forecastModelMSLEso.setSmoothingFactor( bForecastModelMSLEso.getSmoothingFactor() );
		forecastModelMSLEso.setSeasonSmoothingFactor( bForecastModelMSLEso.getSeasonSmoothingFactor() );

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
		
		ForecastModelMSLEso forecastModelMSLEso = new ForecastModelMSLEso();
		this.btod(b_obj, forecastModelMSLEso);
		return forecastModelMSLEso;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSLEso   forecastModelMSLEso = null;
		BForecastModelMSLEso bForecastModelMSLEso = null;
		
		if( d_obj == null )
		{
			forecastModelMSLEso = new ForecastModelMSLEso();
		}
		else
		{
			forecastModelMSLEso = (ForecastModelMSLEso) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSLEso = (BForecastModelMSLEso) b_obj;
		}
		
		super.dtob(forecastModelMSLEso, bForecastModelMSLEso);
		
		bForecastModelMSLEso.setInitDataPeriodNum( forecastModelMSLEso.getInitDataPeriodNum() );
		bForecastModelMSLEso.setSmoothingFactor( forecastModelMSLEso.getSmoothingFactor() );
		bForecastModelMSLEso.setSeasonSmoothingFactor( forecastModelMSLEso.getSeasonSmoothingFactor() );
		
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
		
		BForecastModelMSLEso bForecastModelMSLEso = new BForecastModelMSLEso();
		this.dtob(d_obj, bForecastModelMSLEso);
		return bForecastModelMSLEso;
	}

}
