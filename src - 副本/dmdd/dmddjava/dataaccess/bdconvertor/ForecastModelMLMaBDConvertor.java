/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLMa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLMa;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMLMaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMLMaBDConvertor()
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
		BForecastModelMLMa bForecastModelMLMa = null;
		ForecastModelMLMa   forecastModelMLMa = null;		
		
		if( b_obj == null )
		{
			bForecastModelMLMa = new BForecastModelMLMa();
		}
		else
		{
			bForecastModelMLMa = (BForecastModelMLMa) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMLMa = (ForecastModelMLMa) d_obj;
		}
		
		super.btod(bForecastModelMLMa, forecastModelMLMa);
		
		forecastModelMLMa.setDataPeriodNum( bForecastModelMLMa.getDataPeriodNum() );

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
		
		ForecastModelMLMa forecastModelMLMa = new ForecastModelMLMa();
		this.btod(b_obj, forecastModelMLMa);
		return forecastModelMLMa;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMLMa   forecastModelMLMa = null;
		BForecastModelMLMa bForecastModelMLMa = null;
		
		if( d_obj == null )
		{
			forecastModelMLMa = new ForecastModelMLMa();
		}
		else
		{
			forecastModelMLMa = (ForecastModelMLMa) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMLMa = (BForecastModelMLMa) b_obj;
		}
		
		super.dtob(forecastModelMLMa, bForecastModelMLMa);
		
		bForecastModelMLMa.setDataPeriodNum( forecastModelMLMa.getDataPeriodNum() );
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
		
		BForecastModelMLMa bForecastModelMLMa = new BForecastModelMLMa();
		this.dtob(d_obj, bForecastModelMLMa);
		return bForecastModelMLMa;
	}

}
