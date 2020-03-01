/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWmaItem;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMLWmaBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMLWmaBDConvertor()
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
		BForecastModelMLWma bForecastModelMLWma = null;
		ForecastModelMLWma   forecastModelMLWma = null;		
		
		if( b_obj == null )
		{
			bForecastModelMLWma = new BForecastModelMLWma();
		}
		else
		{
			bForecastModelMLWma = (BForecastModelMLWma) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMLWma = (ForecastModelMLWma) d_obj;
		}
		
		super.btod(bForecastModelMLWma, forecastModelMLWma);
		
		forecastModelMLWma.setDataPeriodNum( bForecastModelMLWma.getDataPeriodNum() );
		
		if( bForecastModelMLWma != null && bForecastModelMLWma.getForecastModelMLWmaItems() != null && !(bForecastModelMLWma.getForecastModelMLWmaItems().isEmpty()) )
		{
			ForecastModelMLWmaItemBDConvertor forecastModelMLWmaItemBDConvertor = new ForecastModelMLWmaItemBDConvertor();
			Iterator<BForecastModelMLWmaItem> itr_bForecastModelMLWmaItems = bForecastModelMLWma.getForecastModelMLWmaItems().iterator();
			while( itr_bForecastModelMLWmaItems.hasNext() )
			{
				ForecastModelMLWmaItem forecastModelMLWmaItem = (ForecastModelMLWmaItem)forecastModelMLWmaItemBDConvertor.btod( itr_bForecastModelMLWmaItems.next() );
				forecastModelMLWmaItem.setForecastModelMLWma( forecastModelMLWma );
				forecastModelMLWma.addForecastModelMLWmaItem( forecastModelMLWmaItem );
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
		
		ForecastModelMLWma forecastModelMLWma = new ForecastModelMLWma();
		this.btod(b_obj, forecastModelMLWma);
		return forecastModelMLWma;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMLWma   forecastModelMLWma = null;
		BForecastModelMLWma bForecastModelMLWma = null;
		
		if( d_obj == null )
		{
			forecastModelMLWma = new ForecastModelMLWma();
		}
		else
		{
			forecastModelMLWma = (ForecastModelMLWma) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMLWma = (BForecastModelMLWma) b_obj;
		}
		
		super.dtob(forecastModelMLWma, bForecastModelMLWma);
		
		bForecastModelMLWma.setDataPeriodNum( forecastModelMLWma.getDataPeriodNum() );
		
		if( forecastModelMLWma != null && forecastModelMLWma.getForecastModelMLWmaItems() != null && !(forecastModelMLWma.getForecastModelMLWmaItems().isEmpty()) )
		{
			ForecastModelMLWmaItemBDConvertor forecastModelMLWmaItemBDConvertor = new ForecastModelMLWmaItemBDConvertor();
			Iterator<ForecastModelMLWmaItem> itr_forecastModelMLWmaItems = forecastModelMLWma.getForecastModelMLWmaItems().iterator();
			while( itr_forecastModelMLWmaItems.hasNext() )
			{
				BForecastModelMLWmaItem bForecastModelMLWmaItem = (BForecastModelMLWmaItem)forecastModelMLWmaItemBDConvertor.dtob( itr_forecastModelMLWmaItems.next() );
				bForecastModelMLWmaItem.setForecastModelMLWma( bForecastModelMLWma );
				bForecastModelMLWma.addForecastModelMLWmaItem( bForecastModelMLWmaItem );
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
		
		BForecastModelMLWma bForecastModelMLWma = new BForecastModelMLWma();
		this.dtob(d_obj, bForecastModelMLWma);
		return bForecastModelMLWma;
	}

}
