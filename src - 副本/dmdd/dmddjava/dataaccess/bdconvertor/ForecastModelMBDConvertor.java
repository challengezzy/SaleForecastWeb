/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelM;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelM;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastModelMBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastModelM bForecastModelM = null;
		ForecastModelM   forecastModelM = null;
		
		if( b_obj == null )
		{
			bForecastModelM = new BForecastModelM();
		}
		else
		{
			bForecastModelM = (BForecastModelM)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelM = (ForecastModelM)d_obj;
		}
		
		forecastModelM.setVersion( bForecastModelM.getVersion() );
		forecastModelM.setId( bForecastModelM.getId() );
		forecastModelM.setCode( bForecastModelM.getCode() );
		forecastModelM.setName( bForecastModelM.getName() );
		forecastModelM.setType( bForecastModelM.getType() );		
		forecastModelM.setIndicator( bForecastModelM.getIndicator() );
		forecastModelM.setSource( bForecastModelM.getSource() );		
		forecastModelM.setDescription( bForecastModelM.getDescription() );		
		forecastModelM.setComments( bForecastModelM.getComments() );
		forecastModelM.setOutlierFactor( bForecastModelM.getOutlierFactor() );
		forecastModelM.setOutlierAnalyzePeriodNum( bForecastModelM.getOutlierAnalyzePeriodNum() );
		forecastModelM.setOutlierDataIsAutoAdjust( bForecastModelM.getOutlierDataIsAutoAdjust() );
		forecastModelM.setOutlierDataAdjustHistoryWgt( bForecastModelM.getOutlierDataAdjustHistoryWgt() );
		forecastModelM.setOutlierDataAdjustComputeWgt( bForecastModelM.getOutlierDataAdjustComputeWgt() );
		
		//    historyBizData
		if( bForecastModelM.getHistoryBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData historyBizData = (BizData) bizDataBDConvertor.btod( bForecastModelM.getHistoryBizData() );	
			forecastModelM.setHistoryBizData(historyBizData);
		}
		else
		{
			forecastModelM.setHistoryBizData(null);
		}		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ForecastModelM forecastModelM = new ForecastModelM();
		this.btod(b_obj, forecastModelM);
		return forecastModelM;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastModelM   forecastModelM = null;
		BForecastModelM bForecastModelM = null;
		
		if( d_obj == null )
		{
			forecastModelM = new ForecastModelM();
		}
		else
		{
			forecastModelM = (ForecastModelM)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelM = (BForecastModelM)b_obj;
		}
		
		bForecastModelM.setVersion( forecastModelM.getVersion() );
		bForecastModelM.setId( forecastModelM.getId() );
		bForecastModelM.setCode( forecastModelM.getCode() );
		bForecastModelM.setName( forecastModelM.getName() );
		bForecastModelM.setType( forecastModelM.getType() );		
		bForecastModelM.setIndicator( forecastModelM.getIndicator() );
		bForecastModelM.setSource( forecastModelM.getSource() );		
		bForecastModelM.setDescription( forecastModelM.getDescription() );		
		bForecastModelM.setComments( forecastModelM.getComments() );
		bForecastModelM.setOutlierFactor( forecastModelM.getOutlierFactor() );
		bForecastModelM.setOutlierAnalyzePeriodNum( forecastModelM.getOutlierAnalyzePeriodNum() );
		bForecastModelM.setOutlierDataIsAutoAdjust( forecastModelM.getOutlierDataIsAutoAdjust() );
		bForecastModelM.setOutlierDataAdjustHistoryWgt( forecastModelM.getOutlierDataAdjustHistoryWgt() );
		bForecastModelM.setOutlierDataAdjustComputeWgt( forecastModelM.getOutlierDataAdjustComputeWgt() );
		
		//    historyBizData
		if( forecastModelM.getHistoryBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData historyBizData = (BBizData) bizDataBDConvertor.dtob( forecastModelM.getHistoryBizData() );	
			bForecastModelM.setHistoryBizData(historyBizData);
		}
		else
		{
			bForecastModelM.setHistoryBizData(null);
		}

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BForecastModelM bForecastModelM = new BForecastModelM();
		this.dtob(d_obj, bForecastModelM);
		return bForecastModelM;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
