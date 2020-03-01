/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastInst;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BPeriodRollTask;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.PeriodRollTask;

/**
 * @author liuzhen
 *
 */
public class PeriodRollTaskBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public PeriodRollTaskBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BPeriodRollTask bPeriodRollTask = null;
		PeriodRollTask   periodRollTask = null;
		
		if( b_obj == null )
		{
			bPeriodRollTask = new BPeriodRollTask();
		}
		else
		{
			bPeriodRollTask = (BPeriodRollTask)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			periodRollTask = (PeriodRollTask)d_obj;
		}
		
		periodRollTask.setVersion( bPeriodRollTask.getVersion() );
		periodRollTask.setId( bPeriodRollTask.getId() );
		periodRollTask.setCompilePeriod( bPeriodRollTask.getCompilePeriod() );
		periodRollTask.setSeqNo( bPeriodRollTask.getSeqNo() );
		periodRollTask.setCategory( bPeriodRollTask.getCategory() );
		periodRollTask.setStatus( bPeriodRollTask.getStatus() );		
		periodRollTask.setCreateTime( bPeriodRollTask.getCreateTime() );
		periodRollTask.setBeginTime( bPeriodRollTask.getBeginTime() );
		periodRollTask.setEndTime( bPeriodRollTask.getEndTime() );
		periodRollTask.setComments( bPeriodRollTask.getComments() );
		
		//    organization
		if( bPeriodRollTask.getOrganization() != null )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
			Organization organization = (Organization) organizationBDConvertor.btod( bPeriodRollTask.getOrganization() );	
			periodRollTask.setOrganization( organization );
		}
		else
		{
			periodRollTask.setOrganization(null);
		}
		
		//    forecastInst
		if( bPeriodRollTask.getForecastInst() != null )
		{
			ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
			ForecastInst forecastInst = (ForecastInst) forecastInstBDConvertor.btod( bPeriodRollTask.getForecastInst() );	
			periodRollTask.setForecastInst( forecastInst );
		}
		else
		{
			periodRollTask.setForecastInst(null);
		}	

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		if( b_obj == null )
		{
			return null;
		}
		PeriodRollTask periodRollTask = new PeriodRollTask();
		this.btod(b_obj, periodRollTask);
		return periodRollTask;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		PeriodRollTask   periodRollTask = null;
		BPeriodRollTask bPeriodRollTask = null;
		
		if( d_obj == null )
		{
			periodRollTask = new PeriodRollTask();
		}
		else
		{
			periodRollTask = (PeriodRollTask)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bPeriodRollTask = (BPeriodRollTask)b_obj;
		}
		
		bPeriodRollTask.setVersion( periodRollTask.getVersion() );
		bPeriodRollTask.setId( periodRollTask.getId() );
		bPeriodRollTask.setCompilePeriod( periodRollTask.getCompilePeriod() );
		bPeriodRollTask.setSeqNo( periodRollTask.getSeqNo() );
		bPeriodRollTask.setCategory( periodRollTask.getCategory() );
		bPeriodRollTask.setStatus( periodRollTask.getStatus() );		
		bPeriodRollTask.setCreateTime( periodRollTask.getCreateTime() );
		bPeriodRollTask.setBeginTime( periodRollTask.getBeginTime() );
		bPeriodRollTask.setEndTime( periodRollTask.getEndTime() );
		bPeriodRollTask.setComments( periodRollTask.getComments() );
		
		//    organization
		if( periodRollTask.getOrganization() != null )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
			BOrganization bOrganization = (BOrganization) organizationBDConvertor.dtob( periodRollTask.getOrganization() );	
			bPeriodRollTask.setOrganization( bOrganization );
		}
		else
		{
			bPeriodRollTask.setOrganization(null);
		}
		
		//    forecastInst
		if( periodRollTask.getForecastInst() != null )
		{
			ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
			BForecastInst bForecastInst = (BForecastInst) forecastInstBDConvertor.dtob( periodRollTask.getForecastInst() );	
			bPeriodRollTask.setForecastInst( bForecastInst );
		}
		else
		{
			bPeriodRollTask.setForecastInst(null);
		}	


	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		if( d_obj == null )
		{
			return null;
		}		
		BPeriodRollTask bPeriodRollTask = new BPeriodRollTask();
		this.dtob(d_obj, bPeriodRollTask);
		return bPeriodRollTask;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
