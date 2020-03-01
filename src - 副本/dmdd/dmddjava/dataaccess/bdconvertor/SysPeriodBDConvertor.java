/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;

/**
 * @author liuzhen
 *
 */
public class SysPeriodBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public SysPeriodBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BSysPeriod bSysPeriod = null;
		SysPeriod   sysPeriod = null;
		if( b_obj == null )
		{
			bSysPeriod = new BSysPeriod();
		}
		else
		{
			bSysPeriod = (BSysPeriod) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			sysPeriod = (SysPeriod) d_obj;
		}
		
		sysPeriod.setVersion( bSysPeriod.getVersion() );
		sysPeriod.setId( bSysPeriod.getId() );
		sysPeriod.setCompilePeriod( bSysPeriod.getCompilePeriod() );
		sysPeriod.setHistoryValidPeriod( bSysPeriod.getHistoryValidPeriod() );
		sysPeriod.setForecastRunPeriod( bSysPeriod.getForecastRunPeriod() );
		sysPeriod.setHistoryOpenPeriod( bSysPeriod.getHistoryOpenPeriod() );
		sysPeriod.setForecastDispPeriod( bSysPeriod.getForecastDispPeriod() );
		sysPeriod.setPeriodNumPerYear( bSysPeriod.getPeriodNumPerYear() );
		sysPeriod.setComments( bSysPeriod.getComments() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		SysPeriod sysPeriod = new SysPeriod();
		this.btod(b_obj, sysPeriod);
		return sysPeriod;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		SysPeriod   sysPeriod = null;
		BSysPeriod bSysPeriod = null;
		
		if( d_obj == null )
		{
			sysPeriod = new SysPeriod();
		}
		else
		{
			sysPeriod = (SysPeriod) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bSysPeriod = (BSysPeriod) b_obj;
		}
		
		bSysPeriod.setVersion( sysPeriod.getVersion() );
		bSysPeriod.setId( sysPeriod.getId() );
		bSysPeriod.setCompilePeriod( sysPeriod.getCompilePeriod() );
		bSysPeriod.setHistoryValidPeriod( sysPeriod.getHistoryValidPeriod() );
		bSysPeriod.setForecastRunPeriod( sysPeriod.getForecastRunPeriod() );
		bSysPeriod.setHistoryOpenPeriod( sysPeriod.getHistoryOpenPeriod() );
		bSysPeriod.setForecastDispPeriod( sysPeriod.getForecastDispPeriod() );
		bSysPeriod.setPeriodNumPerYear( sysPeriod.getPeriodNumPerYear() );
		bSysPeriod.setComments( sysPeriod.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		BSysPeriod bSysPeriod = new BSysPeriod();
		this.dtob(d_obj, bSysPeriod);
		return bSysPeriod;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
