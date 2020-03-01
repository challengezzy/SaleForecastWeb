/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLEs;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSLEsBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSLEsBDConvertor()
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
		BForecastModelMSLEs bForecastModelMSLEs = null;
		ForecastModelMSLEs   forecastModelMSLEs = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSLEs = new BForecastModelMSLEs();
		}
		else
		{
			bForecastModelMSLEs = (BForecastModelMSLEs) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSLEs = (ForecastModelMSLEs) d_obj;
		}
		
		super.btod(bForecastModelMSLEs, forecastModelMSLEs);
		
		forecastModelMSLEs.setInitDataPeriodNum( bForecastModelMSLEs.getInitDataPeriodNum() );
		forecastModelMSLEs.setSmoothingFactor( bForecastModelMSLEs.getSmoothingFactor() );
		forecastModelMSLEs.setSeasonSmoothingFactor( bForecastModelMSLEs.getSeasonSmoothingFactor() );

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
		
		ForecastModelMSLEs forecastModelMSLEs = new ForecastModelMSLEs();
		this.btod(b_obj, forecastModelMSLEs);
		return forecastModelMSLEs;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSLEs   forecastModelMSLEs = null;
		BForecastModelMSLEs bForecastModelMSLEs = null;
		
		if( d_obj == null )
		{
			forecastModelMSLEs = new ForecastModelMSLEs();
		}
		else
		{
			forecastModelMSLEs = (ForecastModelMSLEs) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSLEs = (BForecastModelMSLEs) b_obj;
		}
		
		super.dtob(forecastModelMSLEs, bForecastModelMSLEs);
		
		bForecastModelMSLEs.setInitDataPeriodNum( forecastModelMSLEs.getInitDataPeriodNum() );
		bForecastModelMSLEs.setSmoothingFactor( forecastModelMSLEs.getSmoothingFactor() );
		bForecastModelMSLEs.setSeasonSmoothingFactor( forecastModelMSLEs.getSeasonSmoothingFactor() );
		
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
		
		BForecastModelMSLEs bForecastModelMSLEs = new BForecastModelMSLEs();
		this.dtob(d_obj, bForecastModelMSLEs);
		return bForecastModelMSLEs;
	}

}
