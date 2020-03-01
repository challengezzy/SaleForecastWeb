package dmdd.dmddjava.service.mobile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilMD5;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastSetting;
import dmdd.dmddjava.dataaccess.aidobject.ABLoginInfo;
import dmdd.dmddjava.dataaccess.aidobject.ABMobileData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUipopbScopeInfo;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLog;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogHfcItem;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScope;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeBizData;
import dmdd.dmddjava.dm.BasicDM;
import dmdd.dmddjava.service.forecastservice.ForecastHandleService;
import dmdd.dmddjava.service.forecastservice.ForecastService;
import dmdd.dmddjava.service.securityservice.SecurityService;
import dmdd.dmddjava.service.uiservice.UiService;

public class MobileService  extends BasicDM
{

	public List<ABMobileData> queryMobileData(String code,String uiCode,Long operatorUserId,int beginPeriod,int endPeriod)throws Exception 
	{
		logger.info("开始查询单期间报表数据...");
		List<ABMobileData> list = new ArrayList<ABMobileData>();
		//第一步，根据常用条件的名称，编码，用户id查询业务范围
		String sql = "operatorUserId = "+operatorUserId + " and uiCode = '"+uiCode+"' and code='"+code+"'";
		UiService uiService = new UiService();
		BUiPopbScope uiPopScope = uiService.getUiPopbScopeBySql(sql);
		if(uiPopScope==null)
			return null;
		//第二步，得到所有的业务范围和业务数据
		List<BBizData> list_bizdata = new ArrayList<BBizData>();

		for(BUiPopbScopeBizData buibizdata:uiPopScope.getUiPopbScopeBizDatas())
		{
			list_bizdata.add(buibizdata.getBizData());
		}
		
		//第三步，投影
		List<ABUiRowDataProOrg> list_uiRowDataProOrg = new ArrayList<ABUiRowDataProOrg>();
		list_uiRowDataProOrg.addAll(UtilProOrg.buildExecuteData(uiPopScope));
		
		//第四步，查询
		ForecastHandleService queryService = new ForecastHandleService();
		List<Object> list_temp  = queryService.getUiRowDatas(list_uiRowDataProOrg, beginPeriod, endPeriod, list_bizdata,ServerEnvironment.getInstance().getSysPeriod());
		List<ABUiRowData> list_result = (List<ABUiRowData>)list_temp.get(1);
		ABMobileData data = null;
		if(list_result!=null && list_result.size()>0)
		{	
			for(ABUiRowData uirowdata:list_result)
			{
				data = new ABMobileData();
				data.copyABUiRowData(uirowdata);
				list.add(data);
			}
		}
		logger.info("查询单期间报表数据结束...");
		return list;
	}
	
	public List<ABUipopbScopeInfo> queryUipopbScopeInfo(String uiCode,Long operatorUserId)throws Exception 
	{
		logger.info("开始查询手机后台设置的常用条件...");
		 List<ABUipopbScopeInfo> list = new ArrayList<ABUipopbScopeInfo>();
		String sql = "select code,name,comments from uipopbscope where uicode=? and operatoruserid=?";
		try
		{
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,uiCode,operatorUserId); 
			if(vos!=null&& vos.length>0)
			{
				ABUipopbScopeInfo info = null;
				for(HashVO vo:vos)
				{
					info = new ABUipopbScopeInfo();
					info.setName(vo.getStringValue("name"));
					info.setCode(vo.getStringValue("code"));
					info.setComments(vo.getStringValue("comments"));
					list.add(info);
				}
			}
			
		}
		catch(Exception ex)
		{
			logger.error("查询手机后台设置的常用条件出错："+ex.getMessage());
			throw ex;
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		logger.info("查询手机后台设置的常用条件结束...");
		return list;
	}
	
	public ABLoginInfo login(String userName,String passWord)throws Exception 
	{
		logger.info("用户从手机端登陆，开始验证...");
		ABLoginInfo info = null;
		try
		{
			String sql="select id, username from operatoruser where loginname=? and password=?";
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,userName,UtilMD5.getMD5Str(passWord)); 
			if(vos!=null && vos.length==1)
			{
				HashVO vo = vos[0];
				
				info = new ABLoginInfo();
				info.setUserName(vo.getStringValue("username"));
				info.setOparatorUserId(vo.getLognValue("id"));
				info.setPeriod(ServerEnvironment.getInstance().getSysPeriod().getCompilePeriod());
				logger.info("用户从手机端登陆，验证通过...");
			}
			else
			{
				logger.info("用户从手机端登陆，用户名或者密码错误...");
			}
		}
		catch(Exception ex)
		{
			logger.error("用户从手机端登陆，验证出错："+ex.getMessage());
			throw ex;
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		logger.info("用户从手机端登陆，验证结束...");
		
		return info;
	}
	
	public List<Object> queryMobileForecastData(String code,String uiCode,Long operatorUserId,int beginPeriod,int endPeriod)throws Exception 
	{
		logger.info("开始查询预测数据...");
		//第一步，根据常用条件的名称，编码，用户id查询业务范围
		String sql = "operatorUserId = "+operatorUserId + " and uiCode = '"+uiCode+"' and code='"+code+"'";
		UiService uiService = new UiService();
		BUiPopbScope uiPopScope = uiService.getUiPopbScopeBySql(sql);
		if(uiPopScope==null)
			return null;
		//第二步，得到所有的业务范围和业务数据
		List<BBizData> list_bizdata = new ArrayList<BBizData>();

		for(BUiPopbScopeBizData buibizdata:uiPopScope.getUiPopbScopeBizDatas())
		{
			list_bizdata.add(buibizdata.getBizData());
		}
		
		//第三步，投影
		List<ABUiRowDataProOrg> list_uiRowDataProOrg = new ArrayList<ABUiRowDataProOrg>();
		list_uiRowDataProOrg.addAll(UtilProOrg.buildExecuteData(uiPopScope));
		
		//第四步，查询
		ForecastHandleService queryService = new ForecastHandleService();
		List<Object> list_result  = queryService.getUiRowDatas(list_uiRowDataProOrg, beginPeriod, endPeriod, list_bizdata,ServerEnvironment.getInstance().getSysPeriod());
		
		//查询该期间是否能进行编制
		if(list_result!=null && list_result.size()>1)
		{
			Collection<ABForecastSetting> list_settings = (Collection<ABForecastSetting>)(list_result.get(0));
			if(list_settings !=null && list_settings.size()==1)
			{
				for(ABForecastSetting setting :list_settings)
				{	
					int editPeriodBegin = UtilPeriod.getPeriod( ServerEnvironment.getInstance().getSysPeriod().getForecastRunPeriod(),setting.getFzPeriodNum() );
				    int editPeriodEnd = UtilPeriod.getPeriod(  ServerEnvironment.getInstance().getSysPeriod().getForecastRunPeriod(), setting.getFcPeriodNum() - 1 );
				    if(beginPeriod>=editPeriodBegin && endPeriod<=editPeriodEnd)
				    {
				    	list_result.add(1);//可以编辑
				    }
				    else
				    {
				    	list_result.add(0);
				    }
				}
			}
		}
		logger.info("查询预测数据结束...");
		return list_result;
	}
	
	public boolean saveForecastDatas4Adjust4Mobile(ABUiRowData aBUiRowData4save,Long operatorUserId,String operatorUserName,String comments,  ABForecastSetting abForecastSetting ) throws Exception
	{
		logger.info("用户保存预测编制...");
		try
		{
			List<ABUiRowData> _listABUiRowData4save = new ArrayList<ABUiRowData>();		
			_listABUiRowData4save.add(aBUiRowData4save);
			
			//创建BForecastMakeLog
			BForecastMakeLog forecastlog = new BForecastMakeLog();
			forecastlog.setCompilePeriod(ServerEnvironment.getInstance().getSysPeriod().getCompilePeriod());
			forecastlog.setSubmitter(operatorUserName);
			forecastlog.setSubmitTime(new Date());
			
			SecurityService op_service = new SecurityService();
			forecastlog.setOperatorUser(op_service.getOperatorUser(operatorUserId));
			
			forecastlog.setDescription("手机终端预测编制");
			forecastlog.setActionType(BizConst.FORECASTMAKELOG_ACTIONTYPE_HFC);
			
			BForecastMakeLogProOrg proorg = new BForecastMakeLogProOrg();
			proorg.setOrganization(aBUiRowData4save.getOrganization());
			proorg.setProduct(aBUiRowData4save.getProduct());
			proorg.setForecastMakeLog(forecastlog);
			
			Set<BForecastMakeLogProOrg> proorgs = new HashSet<BForecastMakeLogProOrg>();
			proorgs.add(proorg);
			forecastlog.setForecastMakeLogProOrgs(proorgs);
			
			BForecastMakeLogHfcItem item = new BForecastMakeLogHfcItem();
			item.setBizData(aBUiRowData4save.getBizData());
			item.setComments(comments);
			item.setDescription("手机终端预测编制");
			item.setForecastMakeLog(forecastlog);
			item.setNewValue( Math.round(aBUiRowData4save.getPeriodDataValue00()) );
			item.setOldValue( Math.round(aBUiRowData4save.getPeriodDataValue00Bak()) );
			item.setOrganization(aBUiRowData4save.getOrganization());
			item.setOrganizationCharacter(aBUiRowData4save.getOrganizationCharacter());
			item.setPeriod(aBUiRowData4save.getPeriodBegin());
			item.setProduct(aBUiRowData4save.getProduct());
			item.setProductCharacter(aBUiRowData4save.getProductCharacter());
			
			Set<BForecastMakeLogHfcItem> items = new HashSet<BForecastMakeLogHfcItem>();
			items.add(item);
			
			forecastlog.setForecastMakeLogHfcItems(items);
			
			ForecastService service = new ForecastService();
			service.saveForecastDatas4AdjustUI(_listABUiRowData4save, forecastlog, abForecastSetting, ServerEnvironment.getInstance().getSysPeriod());
		}
		catch(Exception ex)
		{
			logger.error("用户保存预测编制出错："+ex.getMessage());
			return false;	
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		logger.info("用户保存预测编制结束...");
		return true;
		
	}
}
