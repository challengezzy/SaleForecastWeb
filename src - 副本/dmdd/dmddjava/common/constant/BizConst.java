/**
 * 
 */
package dmdd.dmddjava.common.constant;

/**
 * @author liuzhen
 *
 */
public class BizConst
{

	/**
	 * 
	 */
	public BizConst()
	{
		// TODO Auto-generated constructor stub
	}
	
	//	系统参数		begin
	public final static String SYSPARAM_CODE_PAGESIZE = "SYSPARAM_CODE_PAGESIZE";
	public final static String SYSPARAM_CODE_DATABASE = "SYSPARAM_CODE_DATABASE";
	public final static String SYSPARAM_CODE_USERLICENSENUM = "SYSPARAM_CODE_XXYYZZ";
	public final static String SYSPARAM_CODE_PROBATION="SYSPARAM_CODE_ZZYYXX";//单位为天
	public final static String SYSPARAM_CODE_GENUINE="SYSPARAM_CODE_WWWYYY";//是否正版
	public final static String SYSPARAM_CODE_HISTORYDATAIMPORTWARN = "SYSPARAM_CODE_HISTORYDATAIMPORTWARN";//历史导入是否要判断新预测项
	public final static String SYSPARAM_VALUE_DATABASE_ORACLE ="oracle";
	public final static String SYSPARAM_VALUE_DATABASE_SQLSERVER="sqlserver";
	public final static String SYSPARAM_VALUE_DATABASE_DB2="db2";
	//	系统参数		end
	
	/**是否允许用户多点登录 multiple points of presence*/
	public final static String MPOP="MPOP";
	
	//	系统状态		begin
	public final static int SYSTEM_STATUS_NORMAL = 0;						//	正常
	public final static int SYSTEM_STATUS_EXCEPTION_START = 1;				//	启动异常
	/**执行期间滚动时异常 */
	public final static int SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD = 2;		//	执行期间滚动时异常
	public final static int SYSTEM_STATUS_ROLLINGPERIOD = 3;				//	正在执行期间滚动
	public final static int SYSTEM_STATUS_RUNNINGFORECAST = 4;				//	正在运行预测
	public final static int SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST = 5;	//	执行预测运行时异常
	
	//	系统状态		end	
	

	// 数据字典    begin
	// GLOBAL NULL    begin
	public final static int GLOBAL_NULL_NULL = -1;
	// GLOBAL NULL    end
	
	// GLOBAL YESNO    begin
	public final static int GLOBAL_YESNO_NO = 0;
	public final static int GLOBAL_YESNO_YES = 1;
	// GLOBAL YESNO    end
	
	// GLOBAL DATASOURCE    begin
	public final static int GLOBAL_DATASOURCE_SYSTEMDEFINE	= 0;
	public final static int GLOBAL_DATASOURCE_USERDEFINE	= 1;
	// GLOBAL DATASOURCE    end
	
	// BIZDATA TYPE    begin
	/** 历史类*/
	public final static int BIZDATA_TYPE_HISTORY	= 0;
	/** 历史调整类 */
	public final static int BIZDATA_TYPE_HISTORYAD	= 1;
	/** 历史调整原因类*/
	public final static int BIZDATA_TYPE_HISTORYADR	= 2;
	
	/** 统计预测 */
	public final static int BIZDATA_TYPE_FCMODEL	= 20;
	/** 判断预测 */
	public final static int BIZDATA_TYPE_FCHAND		= 21;
	
	/** 组合预测 */
	public final static int BIZDATA_TYPE_FCCOMB		= 22;
	/** 最终预测 */
	public final static int BIZDATA_TYPE_FCFINAL	= 23;
	/** 版本预测类 */
	public final static int BIZDATA_TYPE_FORECASTASSESSMENT	= 24;
	
	/** 同期历史*/
	public final static int BIZDATA_TYPE_TIMEHIS	= 30;
	/** 同期预测*/
	public final static int BIZDATA_TYPE_TIMEFC		= 31;
	/** KPI*/
	public final static int BIZDATA_TYPE_KPI		= 32;
	/** 金额类*/
	public final static int BIZDATA_TYPE_MONEY		= 33;
	/** 组合金额类*/
	public final static int BIZDATA_TYPE_MONEYCOMB	= 34;
	
	/**历史平均类*/
	public final static int BIZDATA_TYPE_AVGHIS	= 37;
	/** 期间累积类 */
	public final static int BIZDATA_TYPE_ADDFC	= 38;
	/** 历史累积类 */
	public final static int BIZDATA_TYPE_ADDHIS	= 39;
	
	/** M-N版本预测 */
	public final static int BIZDATA_TYPE_PERIODVERSION	= 44;
	
	public final static int  BIZDATA_TYPE_DC_STOCKDAY = 40;//库存天数
	public final static int  BIZDATA_TYPE_DC_TERMEND = 41;//期末库存
	public final static int  BIZDATA_TYPE_DC_LASTTERMEND = 42;//上期期末库存
	public final static int  BIZDATA_TYPE_DC_RESTOCK = 43;//补货
	public final static int  BIZDATA_TYPE_DC_SENDOUT = 44;//发货
	// BIZDATA TYPE    end
	
	// BIZDATADEFITEM TIMEFORMULA    begin
	public final static int BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST1YEAR	= 0;
	public final static int BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST2YEAR	= 1;
	public final static int BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST3YEAR	= 2;
	public final static int BIZDATADEFITEM_TIMEFORMULA_LAST1PERIOD	= 3; //上一期
	// BIZDATADEFITEM TIMEFORMULA    end
	
	//BREAKDOWNRULE_TYPE begin
	public final static int BREAKDOWNRULE_TYPE_WORK = 1;
	public final static int BREAKDOWNRULE_TYPE_CALENDAR =2 ;
	//BREAKDOWNRULE_TYPE end
	
	// BIZDATADEFITEM KPIFORMULA    begin
	public final static int BIZDATADEFITEM_KPIFORMULA_RATIO	= 0;				//	A/B
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO	= 1;	//	|A-B|/B
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_I	= 2;	//	1 - |A-B|/B
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_II	= 3;	//	|A-B|/((A+B)/2)
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_III	= 4;	//	A-B
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_IV	= 5;	//	|A-B|
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_V	= 6;	//	(A-B)/B*100
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI	= 7;	//	A/B or B/A
	public final static int BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VII	= 8;	//	∑|A(t)-B(t)|/∑A(t)
	// BIZDATADEFITEM KPIFORMULA    end		
			
	
	// FORECAST FCMONTHNUM    begin
	public final static int FORECAST_FCPERIODNUM_1				= 1;
	public final static int FORECAST_FCPERIODNUM_2				= 2;
	public final static int FORECAST_FCPERIODNUM_3				= 3;
	public final static int FORECAST_FCPERIODNUM_4				= 4;
	public final static int FORECAST_FCPERIODNUM_5				= 5;
	public final static int FORECAST_FCPERIODNUM_6				= 6;	
	public final static int FORECAST_FCPERIODNUM_7				= 7;
	public final static int FORECAST_FCPERIODNUM_8				= 8;
	public final static int FORECAST_FCPERIODNUM_9				= 9;
	public final static int FORECAST_FCPERIODNUM_10				= 10;
	public final static int FORECAST_FCPERIODNUM_11				= 11;
	public final static int FORECAST_FCPERIODNUM_12				= 12;
	public final static int FORECAST_FCPERIODNUM_13				= 13;
	public final static int FORECAST_FCPERIODNUM_14				= 14;
	public final static int FORECAST_FCPERIODNUM_15				= 15;
	public final static int FORECAST_FCPERIODNUM_16				= 16;
	public final static int FORECAST_FCPERIODNUM_17				= 17;
	public final static int FORECAST_FCPERIODNUM_18				= 18;
	public final static int FORECAST_FCPERIODNUM_19				= 19;
	public final static int FORECAST_FCPERIODNUM_20				= 20;
	public final static int FORECAST_FCPERIODNUM_21				= 21;
	public final static int FORECAST_FCPERIODNUM_22				= 22;
	public final static int FORECAST_FCPERIODNUM_23				= 23;
	public final static int FORECAST_FCPERIODNUM_24				= 24;	
	public final static int FORECAST_FCPERIODNUM_25 = 25;
	public final static int FORECAST_FCPERIODNUM_26 = 26;
	public final static int FORECAST_FCPERIODNUM_27 = 27;
	public final static int FORECAST_FCPERIODNUM_28 = 28;
	public final static int FORECAST_FCPERIODNUM_29 = 29;
	public final static int FORECAST_FCPERIODNUM_30 = 30;
	public final static int FORECAST_FCPERIODNUM_31 = 31;
	public final static int FORECAST_FCPERIODNUM_32 = 32;
	public final static int FORECAST_FCPERIODNUM_33 = 33;
	public final static int FORECAST_FCPERIODNUM_34 = 34;
	public final static int FORECAST_FCPERIODNUM_35 = 35;
	public final static int FORECAST_FCPERIODNUM_36 = 36;
	public final static int FORECAST_FCPERIODNUM_37 = 37;
	public final static int FORECAST_FCPERIODNUM_38 = 38;
	public final static int FORECAST_FCPERIODNUM_39 = 39;
	public final static int FORECAST_FCPERIODNUM_40 = 40;
	public final static int FORECAST_FCPERIODNUM_41 = 41;
	public final static int FORECAST_FCPERIODNUM_42 = 42;
	public final static int FORECAST_FCPERIODNUM_43 = 43;
	public final static int FORECAST_FCPERIODNUM_44 = 44;
	public final static int FORECAST_FCPERIODNUM_45 = 45;
	public final static int FORECAST_FCPERIODNUM_46 = 46;
	public final static int FORECAST_FCPERIODNUM_47 = 47;
	public final static int FORECAST_FCPERIODNUM_48 = 48;
	public final static int FORECAST_FCPERIODNUM_49 = 49;
	public final static int FORECAST_FCPERIODNUM_50 = 50;
	public final static int FORECAST_FCPERIODNUM_51 = 51;
	public final static int FORECAST_FCPERIODNUM_52 = 52;
	public final static int FORECAST_FCPERIODNUM_53 = 53;
	public final static int FORECAST_FCPERIODNUM_54 = 54;
	public final static int FORECAST_FCPERIODNUM_55 = 55;
	public final static int FORECAST_FCPERIODNUM_56 = 56;
	public final static int FORECAST_FCPERIODNUM_57 = 57;
	public final static int FORECAST_FCPERIODNUM_58 = 58;
	public final static int FORECAST_FCPERIODNUM_59 = 59;
	public final static int FORECAST_FCPERIODNUM_60 = 60;
	public final static int FORECAST_FCPERIODNUM_61 = 61;
	public final static int FORECAST_FCPERIODNUM_62 = 62;
	public final static int FORECAST_FCPERIODNUM_63 = 63;
	public final static int FORECAST_FCPERIODNUM_64 = 64;
	public final static int FORECAST_FCPERIODNUM_65 = 65;
	public final static int FORECAST_FCPERIODNUM_66 = 66;
	public final static int FORECAST_FCPERIODNUM_67 = 67;
	public final static int FORECAST_FCPERIODNUM_68 = 68;
	public final static int FORECAST_FCPERIODNUM_69 = 69;
	public final static int FORECAST_FCPERIODNUM_70 = 70;
	public final static int FORECAST_FCPERIODNUM_71 = 71;
	public final static int FORECAST_FCPERIODNUM_72 = 72;
	// FORECAST FCMONTHNUM    end
	
	// FORECAST FZMONTHNUM    begin
	public final static int FORECAST_FZPERIODNUM_0				= 0;
	public final static int FORECAST_FZPERIODNUM_1				= 1;
	public final static int FORECAST_FZPERIODNUM_2				= 2;
	public final static int FORECAST_FZPERIODNUM_3				= 3;
	public final static int FORECAST_FZPERIODNUM_4				= 4;
	public final static int FORECAST_FZPERIODNUM_5				= 5;
	public final static int FORECAST_FZPERIODNUM_6				= 6;	
	public final static int FORECAST_FZPERIODNUM_7				= 7;
	public final static int FORECAST_FZPERIODNUM_8				= 8;
	public final static int FORECAST_FZPERIODNUM_9				= 9;
	public final static int FORECAST_FZPERIODNUM_10				= 10;
	public final static int FORECAST_FZPERIODNUM_11				= 11;
	public final static int FORECAST_FZPERIODNUM_12				= 12;
	public final static int FORECAST_FZPERIODNUM_13				= 13;
	public final static int FORECAST_FZPERIODNUM_14				= 14;
	public final static int FORECAST_FZPERIODNUM_15				= 15;
	public final static int FORECAST_FZPERIODNUM_16				= 16;
	public final static int FORECAST_FZPERIODNUM_17				= 17;
	public final static int FORECAST_FZPERIODNUM_18				= 18;
	public final static int FORECAST_FZPERIODNUM_19				= 19;
	public final static int FORECAST_FZPERIODNUM_20				= 20;
	public final static int FORECAST_FZPERIODNUM_21				= 21;
	public final static int FORECAST_FZPERIODNUM_22				= 22;
	public final static int FORECAST_FZPERIODNUM_23				= 23;
	public final static int FORECAST_FZPERIODNUM_24				= 24;	
	public final static int FORECAST_FZPERIODNUM_25 = 25;
	public final static int FORECAST_FZPERIODNUM_26 = 26;
	public final static int FORECAST_FZPERIODNUM_27 = 27;
	public final static int FORECAST_FZPERIODNUM_28 = 28;
	public final static int FORECAST_FZPERIODNUM_29 = 29;
	public final static int FORECAST_FZPERIODNUM_30 = 30;
	public final static int FORECAST_FZPERIODNUM_31 = 31;
	public final static int FORECAST_FZPERIODNUM_32 = 32;
	public final static int FORECAST_FZPERIODNUM_33 = 33;
	public final static int FORECAST_FZPERIODNUM_34 = 34;
	public final static int FORECAST_FZPERIODNUM_35 = 35;
	public final static int FORECAST_FZPERIODNUM_36 = 36;
	public final static int FORECAST_FZPERIODNUM_37 = 37;
	public final static int FORECAST_FZPERIODNUM_38 = 38;
	public final static int FORECAST_FZPERIODNUM_39 = 39;
	public final static int FORECAST_FZPERIODNUM_40 = 40;
	public final static int FORECAST_FZPERIODNUM_41 = 41;
	public final static int FORECAST_FZPERIODNUM_42 = 42;
	public final static int FORECAST_FZPERIODNUM_43 = 43;
	public final static int FORECAST_FZPERIODNUM_44 = 44;
	public final static int FORECAST_FZPERIODNUM_45 = 45;
	public final static int FORECAST_FZPERIODNUM_46 = 46;
	public final static int FORECAST_FZPERIODNUM_47 = 47;
	public final static int FORECAST_FZPERIODNUM_48 = 48;
	public final static int FORECAST_FZPERIODNUM_49 = 49;
	public final static int FORECAST_FZPERIODNUM_50 = 50;
	public final static int FORECAST_FZPERIODNUM_51 = 51;
	public final static int FORECAST_FZPERIODNUM_52 = 52;
	public final static int FORECAST_FZPERIODNUM_53 = 53;
	public final static int FORECAST_FZPERIODNUM_54 = 54;
	public final static int FORECAST_FZPERIODNUM_55 = 55;
	public final static int FORECAST_FZPERIODNUM_56 = 56;
	public final static int FORECAST_FZPERIODNUM_57 = 57;
	public final static int FORECAST_FZPERIODNUM_58 = 58;
	public final static int FORECAST_FZPERIODNUM_59 = 59;
	public final static int FORECAST_FZPERIODNUM_60 = 60;
	public final static int FORECAST_FZPERIODNUM_61 = 61;
	public final static int FORECAST_FZPERIODNUM_62 = 62;
	public final static int FORECAST_FZPERIODNUM_63 = 63;
	public final static int FORECAST_FZPERIODNUM_64 = 64;
	public final static int FORECAST_FZPERIODNUM_65 = 65;
	public final static int FORECAST_FZPERIODNUM_66 = 66;
	public final static int FORECAST_FZPERIODNUM_67 = 67;
	public final static int FORECAST_FZPERIODNUM_68 = 68;
	public final static int FORECAST_FZPERIODNUM_69 = 69;
	public final static int FORECAST_FZPERIODNUM_70 = 70;
	public final static int FORECAST_FZPERIODNUM_71 = 71;
	public final static int FORECAST_FZPERIODNUM_72 = 72;
	// FORECAST FZMONTHNUM    end	
	
	//	FORECAST DISTRIBUTEREFFORMULA	begin
	public final static int FORECAST_DISTRIBUTEREFFORMULA_AVERAGE	= 0;	//	N期平均
	public final static int FORECAST_DISTRIBUTEREFFORMULA_COUNTERPARTOFLAST1YEAR	= 1;	//	去年同期
	//	FORECAST DISTRIBUTEREFFORMULA	end	
	
	// FORECASTDATA STATUS    begin
	public final static int FORECASTDATA_STATUS_ACTIVE					= 0;
	public final static int FORECASTDATA_STATUS_INACTIVE				= 1;
	// FORECASTDATA STATUS    end	
	
	// FORECASTMAKELOG ACTIONTYPE    begin
	public final static int FORECASTMAKELOG_ACTIONTYPE_ACTIVATE			= 0;
	public final static int FORECASTMAKELOG_ACTIONTYPE_INACTIVATE		= 1;
	public final static int FORECASTMAKELOG_ACTIONTYPE_HFC				= 2;	
	// FORECASTMAKELOG ACTIONTYPE    end	
	
	// FORECASTMODELM TYPE    begin
	public final static int FORECASTMODELM_TYPE_LEVEL			= 0;	// 水平模型
	public final static int FORECASTMODELM_TYPE_TREND			= 1;	// 趋势模型
	public final static int FORECASTMODELM_TYPE_SEASONLEVEL		= 2;	// 季节模型
	public final static int FORECASTMODELM_TYPE_SEASONTREND		= 3;	// 季节趋势模型
	public final static int FORECASTMODELM_TYPE_ANALOG			= 4;	// 类比模型
	// FORECASTMODELM TYPE    end	
	
	//	FORECASTRUNTASK STATUS	begin
	public final static int FORECASTRUNTASK_STATUS_RUNNING		= 0;	// 运行中
	public final static int FORECASTRUNTASK_STATUS_RUNNED			= 1;	// 已完成
	//	FORECASTRUNTASK STATUS	end
	
	//	FORECASTRUNTASKITEM STATUS	begin
	public final static int FORECASTRUNTASKITEM_STATUS_TORUN			= 0;	// 待运行		
	public final static int FORECASTRUNTASKITEM_STATUS_RUNNING		= 1;	// 运行中
	public final static int FORECASTRUNTASKITEM_STATUS_RUNNED			= 2;	// 已完成
	//	FORECASTRUNTASKITEM STATUS	end		
	
	//	FORECASTRUNTASKITEM RESULT	begin
	public final static int FORECASTRUNTASKITEM_RESULT_TORUN			= 0;	// 待运行
	public final static int FORECASTRUNTASKITEM_RESULT_SUCCEED			= 1;	// 成功		
	public final static int FORECASTRUNTASKITEM_RESULT_ERRORTHRESHOLD		= 2;	// 误差阈值
	public final static int FORECASTRUNTASKITEM_RESULT_EXCEPTION			= 3;	// 异常
	//	FORECASTRUNTASKITEM RESULT	end			
	
	//	PRICE TYPE	begin
	public final static int PRICE_TYPE_STANDARD		= 0;	// 标准价格
	public final static int PRICE_TYPE_REAL			= 1;	// 实际价格		
	//	PRICE TYPE	end
	
	// PERIODROLLTASK CATEGORY    begin
	public final static int PERIODROLLTASK_CATEGORY_PERIOD			= 0;	// 编制期间
	public final static int PERIODROLLTASK_CATEGORY_HISTORYDATA		= 1;	// 历史数据
	public final static int PERIODROLLTASK_CATEGORY_FORECASTINST	= 2;	// 预测类别
	// PERIODROLLTASK CATEGORY    end		
	
	// PERIODROLLTASK STATUS    begin
	public final static int PERIODROLLTASK_STATUS_TORUN			= 0;	// 待运行
	public final static int PERIODROLLTASK_STATUS_RUNNING		= 1;	// 运行中
	public final static int PERIODROLLTASK_STATUS_RUNNED		= 2;	// 已运行
	// PERIODROLLTASK STATUS    end		
	
	
	// 数据字典    end
	
	
	// BIZDATADEFITEM INDICATOR    begin 
	public final static String BIZDATADEFITEM_INDICATOR_HISTORYAD	= "BIZDATADEFITEM_INDICATOR_HISTORYAD";
	public final static String BIZDATADEFITEM_INDICATOR_HISTORYADR	= "BIZDATADEFITEM_INDICATOR_HISTORYADR";
		
	public final static String BIZDATADEFITEM_INDICATOR_FCHAND		= "BIZDATADEFITEM_INDICATOR_FCHAND";
	public final static String BIZDATADEFITEM_INDICATOR_FCCOMB		= "BIZDATADEFITEM_INDICATOR_FCCOMB";
	public final static String BIZDATADEFITEM_INDICATOR_FORECASTASSESSMENT		= "BIZDATADEFITEM_INDICATOR_FORECASTASSESSMENT";
	public final static String BIZDATADEFITEM_INDICATOR_TIMEHIS		= "BIZDATADEFITEM_INDICATOR_TIMEHIS";
	public final static String BIZDATADEFITEM_INDICATOR_TIMEFC		= "BIZDATADEFITEM_INDICATOR_TIMEFC";
	public final static String BIZDATADEFITEM_INDICATOR_KPI			= "BIZDATADEFITEM_INDICATOR_KPI";	
	public final static String BIZDATADEFITEM_INDICATOR_MONEY		= "BIZDATADEFITEM_INDICATOR_MONEY";
	public final static String BIZDATADEFITEM_INDICATOR_MONEYCOMB	= "BIZDATADEFITEM_INDICATOR_MONEYCOMB";
	
	/** 历史期间平均 */
	public final static String BIZDATADEFITEM_INDICATOR_AVGHIS		= "BIZDATADEFITEM_INDICATOR_AVGHIS";
	/** 历史期间累积 */
	public final static String BIZDATADEFITEM_INDICATOR_ADDHIS		= "BIZDATADEFITEM_INDICATOR_ADDHIS";
	/** 预测期间累积 */
	public final static String BIZDATADEFITEM_INDICATOR_ADDFC		= "BIZDATADEFITEM_INDICATOR_ADDFC";
	
	/** M-N版本预测数据 */
	public final static String BIZDATADEFITEM_INDICATOR_PERIODVERSION		= "BIZDATADEFITEM_INDICATOR_PERIODVERSION";
	// BIZDATADEFITEM INDICATOR    end
	
	// FORECASTMODELM INDICATOR    begin
	// 水平模型
	public final static String FORECASTMODELM_INDICATOR_LMA				= "FORECASTMODELM_INDICATOR_LMA"; 
	public final static String FORECASTMODELM_INDICATOR_LWMA			= "FORECASTMODELM_INDICATOR_LWMA"; 
	public final static String FORECASTMODELM_INDICATOR_LES				= "FORECASTMODELM_INDICATOR_LES";
	public final static String FORECASTMODELM_INDICATOR_LESO			= "FORECASTMODELM_INDICATOR_LESO";
	
	// 趋势模型	
	public final static String FORECASTMODELM_INDICATOR_TPLY			= "FORECASTMODELM_INDICATOR_TPLY";
	public final static String FORECASTMODELM_INDICATOR_TCPLY			= "FORECASTMODELM_INDICATOR_TCPLY";
	public final static String FORECASTMODELM_INDICATOR_TES				= "FORECASTMODELM_INDICATOR_TES";
	public final static String FORECASTMODELM_INDICATOR_TESO			= "FORECASTMODELM_INDICATOR_TESO";
	public final static String FORECASTMODELM_INDICATOR_TLA				= "FORECASTMODELM_INDICATOR_TLA";
	public final static String FORECASTMODELM_INDICATOR_TLR				= "FORECASTMODELM_INDICATOR_TLR";
	public final static String FORECASTMODELM_INDICATOR_TSDA			= "FORECASTMODELM_INDICATOR_TSDA";
	
	// 季节模型
	public final static String FORECASTMODELM_INDICATOR_SLMA			= "FORECASTMODELM_INDICATOR_SLMA"; 
	public final static String FORECASTMODELM_INDICATOR_SLWMA			= "FORECASTMODELM_INDICATOR_SLWMA"; 
	public final static String FORECASTMODELM_INDICATOR_SLES			= "FORECASTMODELM_INDICATOR_SLES";
	public final static String FORECASTMODELM_INDICATOR_SLESO			= "FORECASTMODELM_INDICATOR_SLESO";
	
	// 季节趋势模型
	public final static String FORECASTMODELM_INDICATOR_STPLY			= "FORECASTMODELM_INDICATOR_STPLY";
	public final static String FORECASTMODELM_INDICATOR_STCPLY			= "FORECASTMODELM_INDICATOR_STCPLY";
	public final static String FORECASTMODELM_INDICATOR_STESA			= "FORECASTMODELM_INDICATOR_STESA";
	public final static String FORECASTMODELM_INDICATOR_STESAO			= "FORECASTMODELM_INDICATOR_STESAO";
	public final static String FORECASTMODELM_INDICATOR_STESM			= "FORECASTMODELM_INDICATOR_STESM";
	public final static String FORECASTMODELM_INDICATOR_STESMO			= "FORECASTMODELM_INDICATOR_STESMO";	
	public final static String FORECASTMODELM_INDICATOR_STLA			= "FORECASTMODELM_INDICATOR_STLA";
	public final static String FORECASTMODELM_INDICATOR_STLR			= "FORECASTMODELM_INDICATOR_STLR";
	
	//	类比模型
	public final static String FORECASTMODELM_INDICATOR_AANALOG			= "FORECASTMODELM_INDICATOR_AANALOG";
	// FORECASTMODELM INDICATOR    end
		
	
	
	
	// 系统预定义业务数据编码    begin
	// 历史数据    begin
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SO			= "SD_HISTORY_SO";		// 销售定单
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_GI			= "SD_HISTORY_GI";		// 销售发货
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SI			= "SD_HISTORY_SI";		// 销售发票

	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_SO		= "SD_HISTORYAD_SO";	// 销售定单调整
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_GI		= "SD_HISTORYAD_GI";	// 销售发货调整
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_SI		= "SD_HISTORYAD_SI";	// 销售发票调整
	
	//新增的六种历史数据
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H1 = "SD_HISTORY_H1"; // ----AOP 数量
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H2 = "SD_HISTORY_H2"; // ----AOP 金额
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H3 = "SD_HISTORY_H3"; //----AOP 折扣金额
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H4 = "SD_HISTORY_H4"; // ----经销商库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H5 = "SD_HISTORY_H5"; // ----分仓库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H6 = "SD_HISTORY_H6"; // ----工厂库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_H7 = "SD_HISTORY_H7"; // ----7

	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H1 = "SD_HISTORYAD_H1"; // ----AOP 数量
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H2 = "SD_HISTORYAD_H2"; // ----AOP 金额
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H3 = "SD_HISTORYAD_H3"; //----AOP 折扣金额
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H4 = "SD_HISTORYAD_H4"; // ----经销商库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H5 = "SD_HISTORYAD_H5"; // ----分仓库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H6 = "SD_HISTORYAD_H6"; // ----工厂库存
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_H7 = "SD_HISTORYAD_H7"; // ----7
	
	// 历史数据    end

	// 统计预测    begin
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_MAPPING	= "SD_FORECAST_MAPPING";	// 映射模型预测
	// 统计预测    end	
	
	/** 最终预测   */
	public final static String SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL		= "SD_FORECAST_FINAL";	// 最后预测
	// 最终预测    end		
	// 系统预定义业务数据编码    end
	
	// 系统预定义映射模型编码    begin
	// 水平模型
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_L_MA				= "SD_FM_L_MA"; 		// 移动平均法         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_L_WMA				= "SD_FM_L_WMA";        // 加权移动平均法     
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_L_ES				= "SD_FM_L_ES";         // 指数平滑法         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_L_ES_OP			= "SD_FM_L_ES_OP";		// 自适应指数平滑法   
	
	// 趋势模型	
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_POLY				= "SD_FM_T_POLY";		// 去年百分比法           
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_CPOLY			= "SD_FM_T_CPOLY";      // 已计去年百分比法       
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_ES				= "SD_FM_T_ES";         // 趋势指数平滑法         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_ES_OP			= "SD_FM_T_ES_OP";      // 自适应趋势指数平滑法   
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_LA				= "SD_FM_T_LA";         // 线性逼近法             
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_LR				= "SD_FM_T_LR";         // 一元线性回归法         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_T_SDA				= "SD_FM_T_SDA";		// 二次线性逼近法         
	
	// 季节模型
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_SL_MA				= "SD_FM_SL_MA";		// 季节移动平均法          
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_SL_WMA				= "SD_FM_SL_WMA";       // 季节加权移动平均法     
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_SL_ES				= "SD_FM_SL_ES";        // 季节指数平滑法         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_SL_ES_OP			= "SD_FM_SL_ES_OP";		// 自适应季节指数平滑法   
	
	// 季节趋势模型
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_POLY			= "SD_FM_ST_POLY";		// 季节去年百分比法                     
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_CPOLY			= "SD_FM_ST_CPOLY";     // 季节已计去年百分比法                 
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_ES_ADD			= "SD_FM_ST_ES_ADD";    // 季节趋势指数平滑法 （叠加法）        
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_ES_ADD_OP		= "SD_FM_ST_ES_ADD_OP"; // 自适应季节趋势指数平滑法 （叠加法）  
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_ES_MUL			= "SD_FM_ST_ES_MUL";    // 季节趋势指数平滑法（相乘法）         
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_ES_MUL_OP		= "SD_FM_ST_ES_MUL_OP";	// 自适应季节趋势指数平滑法（相乘法）   
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_LA				= "SD_FM_ST_LA";        // 季节线性逼近法                       
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_ST_LR				= "SD_FM_ST_LR";		// 季节一元线性回归法
	
	//	类比模型
	public final static String SYSTEMDEFINE_FORECASTMOELM_CODE_SD_FM_A_ANALOG			= "SD_FM_A_ANALOG";		// 类比法
	
	// 系统预定义映射模型编码    end
	
	
	public final static int PRODUCT_UNITGROUP_CHANGECASE_NN2NNUNCHANGED = 0;	// 没变化(有到有，但没变)
	public final static int PRODUCT_UNITGROUP_CHANGECASE_NN2NNCHANGED = 1;		// 有变化(有到有，但变化)
	public final static int PRODUCT_UNITGROUP_CHANGECASE_N2N  = 2;				// 没变化(无到无)
	public final static int PRODUCT_UNITGROUP_CHANGECASE_N2NN = 3;				// 变化了(无到有)
	public final static int PRODUCT_UNITGROUP_CHANGECASE_NN2N = 4;				// 变化了(有到无)
	
	
	//	导入数据结果	begin
	public final static String IMPORT_RESULT_SUCCESS	= "OK";		// 导入成功
	
	public final static String HISTORYDATA_IMPORT_WARNINFO_1 = "HISTORYDATA_IMPORT_WARNINFO_1";
	public final static String HISTORYDATA_IMPORT_WARNINFO_2 = "HISTORYDATA_IMPORT_WARNINFO_2";
	public final static String HISTORYDATA_IMPORT_WARNINFO_3 = "HISTORYDATA_IMPORT_WARNINFO_3";
	public final static String HISTORYDATA_IMPORT_WARNINFO_4 = "HISTORYDATA_IMPORT_WARNINFO_4";
	//	导入数据结果	end
	
	
	//add by luowang 20101203 增加了AD验证部分参数 start.
	public final static String CONFIG_AD_ISOPEN="isOpenAD";//是否验证AD，如果为false则直接以正常状态打开客户端
	public final static String CONFIG_AD_ADADDRESS="ADAddress"; //ad服务器地址
	public final static String CONFIG_AD_ADPORT="ADPort";//AD链接端口，默认是389，一般不需要改动
	public final static String CONFIG_AD_DOMAIN="ADDomain";//AD用户名后缀名，一般为@XX.com，也有特殊的，这个主要取决于AD服务器配置,根据实际情况而不同.
	public final static String CONFIG_AD_BEFOREHEAD="beforeHead";
	//add by luowang 20101203 end.
	
	//add by luowang 20110308 增加了服务端和数据库版本参数 start.
	public final static String CONFIG_VERSION_SERVER="version_server";
	public final static String CONFIG_VERSION_DATABASE="version_database";
	//add by luowang 20110308 增加了服务端和数据库版本参数 end.
	
	//add by luowang 20110415 增加了权重基准数据选择 begin.
	public final static int REPORT_WEIGHT_BASEDATA_A = 0;
	public final static int REPORT_WEIGHT_BASEDATA_B = 1;
	public final static int REPORT_WEIGHT_BASEDATA_A_add_B = 2;
	public final static int REPORT_WEIGHT_BASEDATA_NULL = -1;
	//add by luowang 20110415 增加了权重基准数据选择 end.

	//add by luowang 20110609 增加了接口配置列表说明 begin.
	//------------sap配置，目前支持系统r3(一般来说，SAP系统接口都是以R3系统为核心)------------
	public final static String INTERFACE_CONFIG_SAP="SAP";
	public final static String INTERFACE_CONFIG_SAP_R3="R3";
	//add by luowang 20110609 增加了接口配置列表说明 end.
	
	//add by luowang 20111109 导入价格时可以自选导入何种价格  begin
	public final static int PRICE_IMPORT_STANDARDPRICE = 0;
	public final static int PRICE_IMPORT_REALPRICE = 1;
	public final static int PRICE_IMPORT_BOTHPRICE = 2;
	//add by luowang 20111109 导入价格时可以自选导入何种价格  end
	
	//add by luowang 20120624 增加了判断用户业务范围是否导入明细判断 begin
	public final static String ISIMPORTDETAIL="ISIMPORTDETAIL"; 
	//add by luowang 20120624 增加了判断用户业务范围是否导入明细判断end
	
	//add by leslie
	public final static String CONFIG_IS_SUIT_SUPPORT = "isSuitSupport";
	public final static String DECIMAL_LENGTH = "decimalLength";
	public final static String CONFIG_INIT_SUIT = "initSuit";
    public final static String AMOUNT_BIZ_DATA_SUFFIX = "_SYS_AMOUNT";
    public final static String AMOUNT_BIZ_DATA_NAME_SUFFIX = "－折合";
    public final static String AMOUNT_BIZ_DATA_DESC_SUFFIX = "(折合)";
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String str = "abc";
		str = str.substring( 0, 2 );
		System.out.print( str );
	}

}
