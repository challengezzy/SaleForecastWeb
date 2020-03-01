package dmdd.dmddjava.service.validator;

import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;

/**
 * 期间检查
 * @author zzy
 *
 */
public class PeriodValidator {

	/**
	 * 检查系统期间和编译期间是否和系统一致
	 * @param _bSysPeriod
	 * @throws Exception
	 */
	public void sysPeriodValidate(BSysPeriod _bSysPeriod) throws Exception{
	    //检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
	}
}
