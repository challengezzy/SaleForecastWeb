/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLMa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLMa;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSLMaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSLMaBDConvertor()
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
		BForecastModelMSLMa bForecastModelMSLMa = null;
		ForecastModelMSLMa   forecastModelMSLMa = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSLMa = new BForecastModelMSLMa();
		}
		else
		{
			bForecastModelMSLMa = (BForecastModelMSLMa) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSLMa = (ForecastModelMSLMa) d_obj;
		}
		
		super.btod(bForecastModelMSLMa, forecastModelMSLMa);
		
		forecastModelMSLMa.setDataPeriodNum( bForecastModelMSLMa.getDataPeriodNum() );
		forecastModelMSLMa.setSeasonSmoothingFactor( bForecastModelMSLMa.getSeasonSmoothingFactor() );

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
		
		ForecastModelMSLMa forecastModelMSLMa = new ForecastModelMSLMa();
		this.btod(b_obj, forecastModelMSLMa);
		return forecastModelMSLMa;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSLMa   forecastModelMSLMa = null;
		BForecastModelMSLMa bForecastModelMSLMa = null;
		
		if( d_obj == null )
		{
			forecastModelMSLMa = new ForecastModelMSLMa();
		}
		else
		{
			forecastModelMSLMa = (ForecastModelMSLMa) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSLMa = (BForecastModelMSLMa) b_obj;
		}
		
		super.dtob(forecastModelMSLMa, bForecastModelMSLMa);
		
		bForecastModelMSLMa.setDataPeriodNum( forecastModelMSLMa.getDataPeriodNum() );
		bForecastModelMSLMa.setSeasonSmoothingFactor( forecastModelMSLMa.getSeasonSmoothingFactor() );		
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
		
		BForecastModelMSLMa bForecastModelMSLMa = new BForecastModelMSLMa();
		this.dtob(d_obj, bForecastModelMSLMa);
		return bForecastModelMSLMa;
	}

}
