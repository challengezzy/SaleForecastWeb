package dmdd.dmddjava.service.systemservice;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dmdd.dmddjava.common.utils.UtilSysProbation;


public class SysProbationCheckJob implements Job
{

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		UtilSysProbation.ReductionDay();	
	}
}
