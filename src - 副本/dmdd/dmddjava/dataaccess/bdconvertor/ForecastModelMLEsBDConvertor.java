/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLEs;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMLEsBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMLEsBDConvertor()
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
		BForecastModelMLEs bForecastModelMLEs = null;
		ForecastModelMLEs   forecastModelMLEs = null;		
		
		if( b_obj == null )
		{
			bForecastModelMLEs = new BForecastModelMLEs();
		}
		else
		{
			bForecastModelMLEs = (BForecastModelMLEs) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMLEs = (ForecastModelMLEs) d_obj;
		}
		
		super.btod(bForecastModelMLEs, forecastModelMLEs);
		
		forecastModelMLEs.setInitDataPeriodNum( bForecastModelMLEs.getInitDataPeriodNum() );
		forecastModelMLEs.setSmoothingFactor( bForecastModelMLEs.getSmoothingFactor() );		

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
		
		ForecastModelMLEs forecastModelMLEs = new ForecastModelMLEs();
		this.btod(b_obj, forecastModelMLEs);
		return forecastModelMLEs;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMLEs   forecastModelMLEs = null;
		BForecastModelMLEs bForecastModelMLEs = null;
		
		if( d_obj == null )
		{
			forecastModelMLEs = new ForecastModelMLEs();
		}
		else
		{
			forecastModelMLEs = (ForecastModelMLEs) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMLEs = (BForecastModelMLEs) b_obj;
		}
		
		super.dtob(forecastModelMLEs, bForecastModelMLEs);
		
		bForecastModelMLEs.setInitDataPeriodNum( forecastModelMLEs.getInitDataPeriodNum() );
		bForecastModelMLEs.setSmoothingFactor( forecastModelMLEs.getSmoothingFactor() );		
		
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
		
		BForecastModelMLEs bForecastModelMLEs = new BForecastModelMLEs();
		this.dtob(d_obj, bForecastModelMLEs);
		return bForecastModelMLEs;
	}

}
