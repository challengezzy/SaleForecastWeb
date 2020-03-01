/**
 * 
 */
package dmdd.dmddjava.dataaccess.utils;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMAAnalogBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMLEsBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMLEsoBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMLMaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMLWmaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSLEsBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSLEsoBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSLMaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSLWmaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTCplyBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTEsaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTEsaoBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTEsmBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTEsmoBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTLaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMSTPlyBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMTCplyBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMTEsBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMTEsoBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMTLaBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMTPlyBDConvertor;

/**
 * @author liuzhen
 *
 */
public class UtilFactoryForecastModelM
{

	/**
	 * 
	 */
	public UtilFactoryForecastModelM()
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
	
	/**
	 * 
	 * @param _indicator4ForecastModelM
	 * @return
	 */
	public static ForecastModelMBDConvertor getForecastModelMBDConvertorInstance(String _indicator4ForecastModelM)
	{		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_LMA ) )
		{
			return new ForecastModelMLMaBDConvertor();
		}
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_LWMA ) )
		{
			return new ForecastModelMLWmaBDConvertor();
		}	
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_LES ) )
		{
			return new ForecastModelMLEsBDConvertor();
		}			
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_LESO ) )
		{
			return new ForecastModelMLEsoBDConvertor();
		}					

		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_TPLY ) )
		{
			return new ForecastModelMTPlyBDConvertor();
		}
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_TCPLY ) )
		{
			return new ForecastModelMTCplyBDConvertor();
		}		
		if(  _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_TES )  )
		{
			return new ForecastModelMTEsBDConvertor();
		}	
		if(  _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_TESO )  )
		{
			return new ForecastModelMTEsoBDConvertor();
		}		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_TLA ) )
		{
			return new ForecastModelMTLaBDConvertor();
		}
		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_SLMA ) )
		{
			return new ForecastModelMSLMaBDConvertor();
		}	
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_SLWMA ) )
		{
			return new ForecastModelMSLWmaBDConvertor();
		}
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_SLES ) )
		{
			return new ForecastModelMSLEsBDConvertor();
		}
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_SLESO ) )
		{
			return new ForecastModelMSLEsoBDConvertor();
		}		
		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STPLY ) )
		{
			return new ForecastModelMSTPlyBDConvertor();
		}	
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STCPLY ) )
		{
			return new ForecastModelMSTCplyBDConvertor();
		}		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STESA ) )
		{
			return new ForecastModelMSTEsaBDConvertor();
		}
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STESAO ) )
		{
			return new ForecastModelMSTEsaoBDConvertor();
		}		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STESM ) )
		{
			return new ForecastModelMSTEsmBDConvertor();
		}	
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STESMO ) )
		{
			return new ForecastModelMSTEsmoBDConvertor();
		}			
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_STLA ) )
		{
			return new ForecastModelMSTLaBDConvertor();
		}		
		
		if( _indicator4ForecastModelM.equals( BizConst.FORECASTMODELM_INDICATOR_AANALOG ) )
		{
			return new ForecastModelMAAnalogBDConvertor();
		}			
		
		return new ForecastModelMBDConvertor();
		
	}	

}
