/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalog;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMAAnalogBDConvertor extends ForecastModelMBDConvertor
{

	/**
	 * 
	 */
	public ForecastModelMAAnalogBDConvertor()
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
		BForecastModelMAAnalog bForecastModelMAAnalog = null;
		ForecastModelMAAnalog   forecastModelMAAnalog = null;		
		
		if( b_obj == null )
		{
			bForecastModelMAAnalog = new BForecastModelMAAnalog();
		}
		else
		{
			bForecastModelMAAnalog = (BForecastModelMAAnalog) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMAAnalog = (ForecastModelMAAnalog) d_obj;
		}
		
		super.btod(bForecastModelMAAnalog, forecastModelMAAnalog);
		
		forecastModelMAAnalog.setOffsetPeriodNum( bForecastModelMAAnalog.getOffsetPeriodNum() );
		
		if( bForecastModelMAAnalog != null && bForecastModelMAAnalog.getForecastModelMAAnalogItems() != null && !(bForecastModelMAAnalog.getForecastModelMAAnalogItems().isEmpty()) )
		{
			ForecastModelMAAnalogItemBDConvertor forecastModelMAAnalogItemBDConvertor = new ForecastModelMAAnalogItemBDConvertor();
			Iterator<BForecastModelMAAnalogItem> itr_bForecastModelMAAnalogItems = bForecastModelMAAnalog.getForecastModelMAAnalogItems().iterator();
			while( itr_bForecastModelMAAnalogItems.hasNext() )
			{
				ForecastModelMAAnalogItem forecastModelMAAnalogItem = (ForecastModelMAAnalogItem)forecastModelMAAnalogItemBDConvertor.btod( itr_bForecastModelMAAnalogItems.next() );
				forecastModelMAAnalogItem.setForecastModelMAAnalog( forecastModelMAAnalog );
				forecastModelMAAnalog.addForecastModelMAAnalogItem( forecastModelMAAnalogItem );
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
		
		ForecastModelMAAnalog forecastModelMAAnalog = new ForecastModelMAAnalog();
		this.btod(b_obj, forecastModelMAAnalog);
		return forecastModelMAAnalog;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelMAAnalog   forecastModelMAAnalog = null;
		BForecastModelMAAnalog bForecastModelMAAnalog = null;
		
		if( d_obj == null )
		{
			forecastModelMAAnalog = new ForecastModelMAAnalog();
		}
		else
		{
			forecastModelMAAnalog = (ForecastModelMAAnalog) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMAAnalog = (BForecastModelMAAnalog) b_obj;
		}
		
		super.dtob(forecastModelMAAnalog, bForecastModelMAAnalog);
		
		bForecastModelMAAnalog.setOffsetPeriodNum( forecastModelMAAnalog.getOffsetPeriodNum() );
		
		if( forecastModelMAAnalog != null && forecastModelMAAnalog.getForecastModelMAAnalogItems() != null && !(forecastModelMAAnalog.getForecastModelMAAnalogItems().isEmpty()) )
		{
			ForecastModelMAAnalogItemBDConvertor forecastModelMAAnalogItemBDConvertor = new ForecastModelMAAnalogItemBDConvertor();
			Iterator<ForecastModelMAAnalogItem> itr_forecastModelMAAnalogItems = forecastModelMAAnalog.getForecastModelMAAnalogItems().iterator();
			while( itr_forecastModelMAAnalogItems.hasNext() )
			{
				BForecastModelMAAnalogItem bForecastModelMAAnalogItem = (BForecastModelMAAnalogItem)forecastModelMAAnalogItemBDConvertor.dtob( itr_forecastModelMAAnalogItems.next() );
				bForecastModelMAAnalogItem.setForecastModelMAAnalog( bForecastModelMAAnalog );
				bForecastModelMAAnalog.addForecastModelMAAnalogItem( bForecastModelMAAnalogItem );
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
		
		BForecastModelMAAnalog bForecastModelMAAnalog = new BForecastModelMAAnalog();
		this.dtob(d_obj, bForecastModelMAAnalog);
		return bForecastModelMAAnalog;
	}

}
