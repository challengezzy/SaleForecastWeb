/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTaskItem;

/**
 * @author liuzhen
 *
 */
public class ForecastRunTaskBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastRunTaskBDConvertor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod( Object bObj, Object dObj )
	{
		// TODO Auto-generated method stub
		BForecastRunTask bForecastRunTask = null;;
		ForecastRunTask  forecastRunTask  = null;
		
		if( bObj == null )
		{
			bForecastRunTask = new BForecastRunTask();
		}
		else
		{
			bForecastRunTask = (BForecastRunTask)bObj;
		}
		
		if( dObj == null )
		{
			return;
		}
		else
		{
			forecastRunTask = (ForecastRunTask)dObj;
		}
		

		forecastRunTask.setVersion( bForecastRunTask.getVersion() );
		forecastRunTask.setId( bForecastRunTask.getId() );
		forecastRunTask.setCompilePeriod( bForecastRunTask.getCompilePeriod() );
		forecastRunTask.setDescription( bForecastRunTask.getDescription() );
		forecastRunTask.setCreator( bForecastRunTask.getCreator() );
		forecastRunTask.setCreatedTime( bForecastRunTask.getCreatedTime() );
		forecastRunTask.setStatus( bForecastRunTask.getStatus() );
		forecastRunTask.setFinishTime( bForecastRunTask.getFinishTime() );
		
		forecastRunTask.setComments( bForecastRunTask.getComments() );		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	@Override
	public Object btod( Object bObj )
	{
		// TODO Auto-generated method stub
		ForecastRunTask forecastRunTask = new ForecastRunTask();
		this.btod( bObj, forecastRunTask );
		return forecastRunTask;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob( Object dObj, Object bObj )
	{
		// TODO Auto-generated method stub
		ForecastRunTask  forecastRunTask  = null;
		BForecastRunTask bForecastRunTask = null;
		
		if( dObj == null )
		{
			forecastRunTask = new ForecastRunTask();
		}
		else
		{
			forecastRunTask = (ForecastRunTask)dObj;
		}
		
		if( bObj == null )
		{
			return;
		}	
		else
		{
			bForecastRunTask = (BForecastRunTask)bObj;
		}
		
		bForecastRunTask.setVersion( forecastRunTask.getVersion() );
		bForecastRunTask.setId( forecastRunTask.getId() );
		bForecastRunTask.setCompilePeriod( forecastRunTask.getCompilePeriod() );
		bForecastRunTask.setDescription( forecastRunTask.getDescription() );
		bForecastRunTask.setCreator( forecastRunTask.getCreator() );
		bForecastRunTask.setCreatedTime( forecastRunTask.getCreatedTime() );
		bForecastRunTask.setStatus( forecastRunTask.getStatus() );
		bForecastRunTask.setFinishTime( forecastRunTask.getFinishTime() );
		
		bForecastRunTask.setComments( forecastRunTask.getComments() );	
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob( Object dObj )
	{
		// TODO Auto-generated method stub
		BForecastRunTask bForecastRunTask = new BForecastRunTask();
		this.dtob( dObj, bForecastRunTask );
		return bForecastRunTask;
	}
	
	
	public void btod(BForecastRunTask _bForecastRunTask, ForecastRunTask _forecastRunTask, boolean _blWithForecastRunTaskItems )
	{
		if( _forecastRunTask == null )
		{
			return;
		}
		
		this.btod(_bForecastRunTask, _forecastRunTask);
		
		if( _blWithForecastRunTaskItems == true )
		{
			if( _bForecastRunTask != null && _bForecastRunTask.getForecastRunTaskItems() != null && _bForecastRunTask.getForecastRunTaskItems().iterator() != null )
			{
				ForecastRunTaskItemBDConvertor forecastRunTaskItemBDConvertor = new ForecastRunTaskItemBDConvertor();
				Iterator<BForecastRunTaskItem> itr_bForecastRunTaskItems = _bForecastRunTask.getForecastRunTaskItems().iterator();
				while( itr_bForecastRunTaskItems.hasNext() )
				{
					ForecastRunTaskItem forecastRunTaskItem = (ForecastRunTaskItem)forecastRunTaskItemBDConvertor.btod(itr_bForecastRunTaskItems.next());
					forecastRunTaskItem.setForecastRunTask(_forecastRunTask);
					_forecastRunTask.addForecastRunTaskItem( forecastRunTaskItem );
				}
			}					
		}

	}
	

	public ForecastRunTask btod(BForecastRunTask _bForecastRunTask, boolean _blWithForecastRunTaskItems)
	{
		ForecastRunTask forecastRunTask = new ForecastRunTask();
		this.btod(_bForecastRunTask, forecastRunTask,_blWithForecastRunTaskItems);
		return forecastRunTask;
	}
	

	public void dtob(ForecastRunTask _forecastRunTask, BForecastRunTask _bForecastRunTask, boolean _blWithForecastRunTaskItems)
	{
		if( _bForecastRunTask == null )
		{
			return;
		}
		
		this.dtob(_forecastRunTask, _bForecastRunTask);
		
		if( _blWithForecastRunTaskItems == true )
		{
			if( _forecastRunTask != null && _forecastRunTask.getForecastRunTaskItems() != null && _forecastRunTask.getForecastRunTaskItems().iterator() != null )
			{
				ForecastRunTaskItemBDConvertor forecastRunTaskItemBDConvertor = new ForecastRunTaskItemBDConvertor();
				Iterator<ForecastRunTaskItem> forecastRunTaskItemsItr = _forecastRunTask.getForecastRunTaskItems().iterator();
				while( forecastRunTaskItemsItr.hasNext() )
				{
					BForecastRunTaskItem bForecastRunTaskItem = (BForecastRunTaskItem)forecastRunTaskItemBDConvertor.dtob(forecastRunTaskItemsItr.next());
					bForecastRunTaskItem.setForecastRunTask(_bForecastRunTask);
					_bForecastRunTask.addForecastRunTaskItem( bForecastRunTaskItem );
				}
			}				
		}
			
	}
	
	public BForecastRunTask dtob(ForecastRunTask _forecastRunTask, boolean _blWithForecastRunTaskItems)
	{
		BForecastRunTask bForecastRunTask = new BForecastRunTask();
		this.dtob(_forecastRunTask, bForecastRunTask, _blWithForecastRunTaskItems);
		return bForecastRunTask;
	}		

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
