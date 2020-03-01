/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSTEsao;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsao;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSTEsaoBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSTEsaoBDConvertor()
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
		BForecastModelMSTEsao bForecastModelMSTEsao = null;
		ForecastModelMSTEsao   forecastModelMSTEsao = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSTEsao = new BForecastModelMSTEsao();
		}
		else
		{
			bForecastModelMSTEsao = (BForecastModelMSTEsao) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSTEsao = (ForecastModelMSTEsao) d_obj;
		}
		
		super.btod(bForecastModelMSTEsao, forecastModelMSTEsao);
		
		forecastModelMSTEsao.setTrendSmoothingFactor( bForecastModelMSTEsao.getTrendSmoothingFactor() );
		forecastModelMSTEsao.setTrendDampingIsValid( bForecastModelMSTEsao.getTrendDampingIsValid() );
		forecastModelMSTEsao.setTrendDampingFactor( bForecastModelMSTEsao.getTrendDampingFactor() );
		forecastModelMSTEsao.setSeasonSmoothingFactor( bForecastModelMSTEsao.getSeasonSmoothingFactor() );
		forecastModelMSTEsao.setInitDataPeriodNum( bForecastModelMSTEsao.getInitDataPeriodNum() );
		forecastModelMSTEsao.setLevelSmoothingFactor( bForecastModelMSTEsao.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		ForecastModelMSTEsao forecastModelMSTEsao = new ForecastModelMSTEsao();
		this.btod(b_obj, forecastModelMSTEsao);
		return forecastModelMSTEsao;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSTEsao   forecastModelMSTEsao = null;
		BForecastModelMSTEsao bForecastModelMSTEsao = null;
		
		if( d_obj == null )
		{
			forecastModelMSTEsao = new ForecastModelMSTEsao();
		}
		else
		{
			forecastModelMSTEsao = (ForecastModelMSTEsao) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSTEsao = (BForecastModelMSTEsao) b_obj;
		}
		
		super.dtob(forecastModelMSTEsao, bForecastModelMSTEsao);
		
		bForecastModelMSTEsao.setTrendSmoothingFactor( forecastModelMSTEsao.getTrendSmoothingFactor() );
		bForecastModelMSTEsao.setTrendDampingIsValid( forecastModelMSTEsao.getTrendDampingIsValid() );
		bForecastModelMSTEsao.setTrendDampingFactor( forecastModelMSTEsao.getTrendDampingFactor() );
		bForecastModelMSTEsao.setSeasonSmoothingFactor( forecastModelMSTEsao.getSeasonSmoothingFactor() );
		bForecastModelMSTEsao.setInitDataPeriodNum( forecastModelMSTEsao.getInitDataPeriodNum() );
		bForecastModelMSTEsao.setLevelSmoothingFactor( forecastModelMSTEsao.getLevelSmoothingFactor() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BForecastModelMSTEsao bForecastModelMSTEsao = new BForecastModelMSTEsao();
		this.dtob(d_obj, bForecastModelMSTEsao);
		return bForecastModelMSTEsao;
	}

}
