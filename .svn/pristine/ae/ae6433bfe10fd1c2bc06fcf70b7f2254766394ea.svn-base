/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLWma;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMSLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWmaItem;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMSLWmaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMSLWmaBDConvertor()
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
		BForecastModelMSLWma bForecastModelMSLWma = null;
		ForecastModelMSLWma   forecastModelMSLWma = null;		
		
		if( b_obj == null )
		{
			bForecastModelMSLWma = new BForecastModelMSLWma();
		}
		else
		{
			bForecastModelMSLWma = (BForecastModelMSLWma) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMSLWma = (ForecastModelMSLWma) d_obj;
		}
		
		super.btod(bForecastModelMSLWma, forecastModelMSLWma);
		
		forecastModelMSLWma.setDataPeriodNum( bForecastModelMSLWma.getDataPeriodNum() );
		forecastModelMSLWma.setSeasonSmoothingFactor( bForecastModelMSLWma.getSeasonSmoothingFactor() );		
		
		if( bForecastModelMSLWma != null && bForecastModelMSLWma.getForecastModelMSLWmaItems() != null && !(bForecastModelMSLWma.getForecastModelMSLWmaItems().isEmpty()) )
		{
			ForecastModelMSLWmaItemBDConvertor forecastModelMSLWmaItemBDConvertor = new ForecastModelMSLWmaItemBDConvertor();
			Iterator<BForecastModelMSLWmaItem> itr_bForecastModelMSLWmaItems = bForecastModelMSLWma.getForecastModelMSLWmaItems().iterator();
			while( itr_bForecastModelMSLWmaItems.hasNext() )
			{
				ForecastModelMSLWmaItem forecastModelMSLWmaItem = (ForecastModelMSLWmaItem)forecastModelMSLWmaItemBDConvertor.btod( itr_bForecastModelMSLWmaItems.next() );
				forecastModelMSLWmaItem.setForecastModelMSLWma( forecastModelMSLWma );
				forecastModelMSLWma.addForecastModelMSLWmaItem( forecastModelMSLWmaItem );
			}
		}		

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
		
		ForecastModelMSLWma forecastModelMSLWma = new ForecastModelMSLWma();
		this.btod(b_obj, forecastModelMSLWma);
		return forecastModelMSLWma;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMSLWma   forecastModelMSLWma = null;
		BForecastModelMSLWma bForecastModelMSLWma = null;
		
		if( d_obj == null )
		{
			forecastModelMSLWma = new ForecastModelMSLWma();
		}
		else
		{
			forecastModelMSLWma = (ForecastModelMSLWma) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMSLWma = (BForecastModelMSLWma) b_obj;
		}
		
		super.dtob(forecastModelMSLWma, bForecastModelMSLWma);
		
		bForecastModelMSLWma.setDataPeriodNum( forecastModelMSLWma.getDataPeriodNum() );
		bForecastModelMSLWma.setSeasonSmoothingFactor( forecastModelMSLWma.getSeasonSmoothingFactor() );		
		
		if( forecastModelMSLWma != null && forecastModelMSLWma.getForecastModelMSLWmaItems() != null && !(forecastModelMSLWma.getForecastModelMSLWmaItems().isEmpty()) )
		{
			ForecastModelMSLWmaItemBDConvertor forecastModelMSLWmaItemBDConvertor = new ForecastModelMSLWmaItemBDConvertor();
			Iterator<ForecastModelMSLWmaItem> itr_forecastModelMSLWmaItems = forecastModelMSLWma.getForecastModelMSLWmaItems().iterator();
			while( itr_forecastModelMSLWmaItems.hasNext() )
			{
				BForecastModelMSLWmaItem bForecastModelMSLWmaItem = (BForecastModelMSLWmaItem)forecastModelMSLWmaItemBDConvertor.dtob( itr_forecastModelMSLWmaItems.next() );
				bForecastModelMSLWmaItem.setForecastModelMSLWma( bForecastModelMSLWma );
				bForecastModelMSLWma.addForecastModelMSLWmaItem( bForecastModelMSLWmaItem );
			}
		}		
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
		
		BForecastModelMSLWma bForecastModelMSLWma = new BForecastModelMSLWma();
		this.dtob(d_obj, bForecastModelMSLWma);
		return bForecastModelMSLWma;
	}

}
