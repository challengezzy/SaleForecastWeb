/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMTLa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTLa;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMTLaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMTLaBDConvertor()
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
		BForecastModelMTLa bForecastModelMTLa = null;
		ForecastModelMTLa   forecastModelMTLa = null;		
		
		if( b_obj == null )
		{
			bForecastModelMTLa = new BForecastModelMTLa();
		}
		else
		{
			bForecastModelMTLa = (BForecastModelMTLa) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMTLa = (ForecastModelMTLa) d_obj;
		}
		
		super.btod(bForecastModelMTLa, forecastModelMTLa);
		
		forecastModelMTLa.setDataPeriodNum( bForecastModelMTLa.getDataPeriodNum() );
		forecastModelMTLa.setTrendDampingIsValid( bForecastModelMTLa.getTrendDampingIsValid() );
		forecastModelMTLa.setTrendDampingFactor( bForecastModelMTLa.getTrendDampingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMTLa forecastModelMTLa = new ForecastModelMTLa();
		this.btod(b_obj, forecastModelMTLa);
		return forecastModelMTLa;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMTLa   forecastModelMTLa = null;
		BForecastModelMTLa bForecastModelMTLa = null;
		
		if( d_obj == null )
		{
			forecastModelMTLa = new ForecastModelMTLa();
		}
		else
		{
			forecastModelMTLa = (ForecastModelMTLa) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMTLa = (BForecastModelMTLa) b_obj;
		}
		
		super.dtob(forecastModelMTLa, bForecastModelMTLa);
		
		bForecastModelMTLa.setDataPeriodNum( forecastModelMTLa.getDataPeriodNum() );
		bForecastModelMTLa.setTrendDampingIsValid( forecastModelMTLa.getTrendDampingIsValid() );
		bForecastModelMTLa.setTrendDampingFactor( forecastModelMTLa.getTrendDampingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMTLa bForecastModelMTLa = new BForecastModelMTLa();
		this.dtob(d_obj, bForecastModelMTLa);
		return bForecastModelMTLa;
	}

}
