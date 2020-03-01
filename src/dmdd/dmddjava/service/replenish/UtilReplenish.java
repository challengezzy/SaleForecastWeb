package dmdd.dmddjava.service.replenish;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

public class UtilReplenish {
	
	private static CommDMO dmo = new CommDMO();
	
	private static Logger logger = CoolLogger.getLogger(UtilReplenish.class);
	
	/**
	 * 判断补货记录是否存在
	 * @param proId
	 * @param dcId
	 * @param period
	 * @return
	 */
	public static boolean checkExists4ReplenishData(Long proId,Long dcId,int period) throws Exception
	{
		String sql= "select id from replenishdata where period=? and productid=? and dcid=?";
		try
		{
			HashVO[] vos= dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql,period,proId,dcId);
			if(vos==null || vos.length<1|| vos.length>1)
				return false;
			else if(vos.length==1)
				return true;
		}
		catch (Exception e) {
			logger.error("检查补货表数据异常:" + e.toString(),e);
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return false;
	}
	
	public static int getMaxDcLayer() throws Exception
	{
		String sql= "SELECT MAX(DCLAYER) MAXLAYER FROM DISTRIBUTIONCENTER";
		int maxDclayer = 3;
		try
		{
			HashVO[] vos= dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql);
			if(vos.length > 0){
				maxDclayer = vos[0].getIntegerValue("MAXLAYER");
			}
		}
		catch (Exception e) {
			logger.error("查询明细层DCLAYER异常," + e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return maxDclayer;
	}
	
	/**
	 * 判断历史补货记录是否存在
	 * @param proId
	 * @param dcId
	 * @param period
	 * @return
	 */
	public static boolean checkExists4HistoryReplenish(Long proId,Long dcId,int period)
	{
		String sql= "select id from replenishhisdata where period=? and productid=? and  dcid=?";
		try
		{
			HashVO[] vos= dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql,period,proId,dcId);
			if(vos==null || vos.length<1|| vos.length>1)
				return false;
			else if(vos.length==1)
				return true;
		}
		catch (Exception e) {
			logger.error("检查历史补货表数据异常:" + e.toString());
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return false;
	}
	
	public static boolean checkExistsEndInvData(int period,Long dcId,Long proId,String code) throws Exception
	{
		String sql= "select id from ENDING_INVENTORY_DATA where PERIOD=? and DCID=? and PRODUCTID=? and ENDINV_CODE=?";
		try
		{
			HashVO[] vos= dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql,period,dcId,proId,code);
			if(vos==null || vos.length<1|| vos.length>1)
				return false;
			else if(vos.length==1)
				return true;
		}
		catch (Exception e) {
			logger.error("检查补货表数据异常:" + e.toString(),e);
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return false;
	}

}
