/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMTCply;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTCply;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMTCplyBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMTCplyBDConvertor()
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
		BForecastModelMTCply bForecastModelMTCply = null;
		ForecastModelMTCply   forecastModelMTCply = null;		
		
		if( b_obj == null )
		{
			bForecastModelMTCply = new BForecastModelMTCply();
		}
		else
		{
			bForecastModelMTCply = (BForecastModelMTCply) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMTCply = (ForecastModelMTCply) d_obj;
		}
		
		super.btod(bForecastModelMTCply, forecastModelMTCply);
		
		forecastModelMTCply.setDataPeriodNum( bForecastModelMTCply.getDataPeriodNum() );

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
		
		ForecastModelMTCply forecastModelMTCply = new ForecastModelMTCply();
		this.btod(b_obj, forecastModelMTCply);
		return forecastModelMTCply;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMTCply   forecastModelMTCply = null;
		BForecastModelMTCply bForecastModelMTCply = null;
		
		if( d_obj == null )
		{
			forecastModelMTCply = new ForecastModelMTCply();
		}
		else
		{
			forecastModelMTCply = (ForecastModelMTCply) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMTCply = (BForecastModelMTCply) b_obj;
		}
		
		super.dtob(forecastModelMTCply, bForecastModelMTCply);
		
		bForecastModelMTCply.setDataPeriodNum( forecastModelMTCply.getDataPeriodNum() );
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
		
		BForecastModelMTCply bForecastModelMTCply = new BForecastModelMTCply();
		this.dtob(d_obj, bForecastModelMTCply);
		return bForecastModelMTCply;
	}

}
