/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLEso;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMLEsoBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMLEsoBDConvertor()
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
		BForecastModelMLEso bForecastModelMLEso = null;
		ForecastModelMLEso   forecastModelMLEso = null;		
		
		if( b_obj == null )
		{
			bForecastModelMLEso = new BForecastModelMLEso();
		}
		else
		{
			bForecastModelMLEso = (BForecastModelMLEso) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMLEso = (ForecastModelMLEso) d_obj;
		}
		
		super.btod(bForecastModelMLEso, forecastModelMLEso);
		
		forecastModelMLEso.setInitDataPeriodNum( bForecastModelMLEso.getInitDataPeriodNum() );
		forecastModelMLEso.setSmoothingFactor( bForecastModelMLEso.getSmoothingFactor() );		

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
		
		ForecastModelMLEso forecastModelMLEso = new ForecastModelMLEso();
		this.btod(b_obj, forecastModelMLEso);
		return forecastModelMLEso;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMLEso   forecastModelMLEso = null;
		BForecastModelMLEso bForecastModelMLEso = null;
		
		if( d_obj == null )
		{
			forecastModelMLEso = new ForecastModelMLEso();
		}
		else
		{
			forecastModelMLEso = (ForecastModelMLEso) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMLEso = (BForecastModelMLEso) b_obj;
		}
		
		super.dtob(forecastModelMLEso, bForecastModelMLEso);
		
		bForecastModelMLEso.setInitDataPeriodNum( forecastModelMLEso.getInitDataPeriodNum() );
		bForecastModelMLEso.setSmoothingFactor( forecastModelMLEso.getSmoothingFactor() );		
		
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
		
		BForecastModelMLEso bForecastModelMLEso = new BForecastModelMLEso();
		this.dtob(d_obj, bForecastModelMLEso);
		return bForecastModelMLEso;
	}

}
