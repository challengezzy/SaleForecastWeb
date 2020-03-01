/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTCply;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTCply;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTCplyBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTCplyBDConvertor()
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
		BForecastModelMSTCply bForecastModelMSTCply = null;
		ForecastModelMSTCply   forecastModelMSTCply = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTCply = new BForecastModelMSTCply();
		}
		else
		{
			bForecastModelMSTCply = (BForecastModelMSTCply) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTCply = (ForecastModelMSTCply) d_obj;
		}
		
		super.btod(bForecastModelMSTCply, forecastModelMSTCply);
		
		forecastModelMSTCply.setDataPeriodNum( bForecastModelMSTCply.getDataPeriodNum() );
		forecastModelMSTCply.setSeasonSmoothingFactor( bForecastModelMSTCply.getSeasonSmoothingFactor() );

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
		
		ForecastModelMSTCply forecastModelMSTCply = new ForecastModelMSTCply();
		this.btod(b_obj, forecastModelMSTCply);
		return forecastModelMSTCply;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTCply   forecastModelMSTCply = null;
		BForecastModelMSTCply bForecastModelMSTCply = null;
		
		if( d_obj == null )
		{
			forecastModelMSTCply = new ForecastModelMSTCply();
		}
		else
		{
			forecastModelMSTCply = (ForecastModelMSTCply) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTCply = (BForecastModelMSTCply) b_obj;
		}
		
		super.dtob(forecastModelMSTCply, bForecastModelMSTCply);
		
		bForecastModelMSTCply.setDataPeriodNum( forecastModelMSTCply.getDataPeriodNum() );
		bForecastModelMSTCply.setSeasonSmoothingFactor( forecastModelMSTCply.getSeasonSmoothingFactor() );
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
		
		BForecastModelMSTCply bForecastModelMSTCply = new BForecastModelMSTCply();
		this.dtob(d_obj, bForecastModelMSTCply);
		return bForecastModelMSTCply;
	}

}
