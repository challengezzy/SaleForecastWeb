/**
 * 
 */
package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;

/**
 * @author liuzhen
 *
 */
public class UtilPeriod
{

	/**
	 * 
	 */
	public UtilPeriod()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}
	
	public static boolean checkPeriod(int _period)
	{
		if( _period < 1 )
		{
			return false;
		}
		
		String strPeriod = "" + _period;
		if( strPeriod.length() != 6 )
		{
			return false;	
		}
		
		int year = _period/100;
		int pno = _period-year*100;
		
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		if( pno > periodNumPerYear || pno < 1 )
		{
			return false;
		}		
		return true;
	}
	
	/**
	 * 当前期间平移N个期间
	 * @param _originPeriod
	 * @param _addPeriodNum
	 * @return
	 */
	public static int getPeriod(int _originPeriod, int _addPeriodNum)
	{
		if( checkPeriod(_originPeriod) == false )
		{
			return SysConst.PERIOD_NULL;
		}
		
		int year = _originPeriod/100;
		int pno = _originPeriod-year*100;
		
		int year_rst = year;
		int pno_rst = pno + _addPeriodNum;
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		while( pno_rst > periodNumPerYear || pno_rst < 1 )
		{
			if( pno_rst > periodNumPerYear )
			{
				pno_rst = pno_rst - periodNumPerYear;
				year_rst = year_rst + 1;
			}
			else if( pno_rst < 1 )
			{
				pno_rst = pno_rst + periodNumPerYear;
				year_rst = year_rst - 1;				
			}
		}
		
		int period_rst = year_rst * 100 + pno_rst;

		return period_rst;
	}	
	
	/**
	 * 获取期间所有的年
	 * @param _period
	 * @return
	 */
	public static int getPeriodYear(int _period )
	{
		if( checkPeriod(_period) == false )
		{
			return SysConst.PERIOD_YEAR_NULL;
		}
		
		int year = _period/100;

		return year;
	}
	
	/**
	 * 得到期间所有年的第一个期间
	 * @param _period
	 * @return
	 */
	public static int getPeriodYearBegin(int _period )
	{
		if( checkPeriod(_period) == false )
		{
			return SysConst.PERIOD_YEAR_NULL;
		}
		
		int yearBegin = Integer.parseInt((_period+"").substring(0,4) + "01");

		return yearBegin;
	}
	
	public static int getPeriodNo(int _period)
	{
		if( checkPeriod(_period) == false )
		{
			return SysConst.PERIOD_NO_NULL;
		}
		
		int year = _period/100;
		int pno = _period-year*100;

		return pno;
	}		
	
	public static int createPeriod(int _periodYear, int _periodNo)
	{
		if( _periodYear < 1 )
		{
			return SysConst.PERIOD_NULL;
		}
		
		String strPeriodYear = "" + _periodYear;
		if( strPeriodYear.length() != 4 )
		{
			return SysConst.PERIOD_NULL;	
		}
		
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		if( _periodNo > periodNumPerYear || _periodNo < 1 )
		{
			return SysConst.PERIOD_NULL;	
		}
	
		
		int period_rst = _periodYear * 100 + _periodNo;

		return period_rst;
	}		
	
	
	public static int getPeriodDifference(int _periodBegin, int _periodEnd)
	{
		if( checkPeriod(_periodBegin) == false )
		{
			return SysConst.PERIOD_DIFF_NULL;
		}
		if( checkPeriod(_periodEnd) == false )
		{
			return SysConst.PERIOD_DIFF_NULL;
		}	

		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		int year_periodBegin = _periodBegin/100;
		int pno_periodBegin = _periodBegin-year_periodBegin*100;

		int year_periodEnd = _periodEnd/100;
		int pno_periodEnd = _periodEnd-year_periodEnd*100;

		
		int periodDifference = (year_periodEnd - year_periodBegin) * periodNumPerYear + (pno_periodEnd - pno_periodBegin);

		return periodDifference;
	}	

	/**
	 * 计算整个工作周的第一天集合
	 * @param beginPeriod
	 * @param diff
	 * @return
	 */
	public static List<String> buildWork(int beginPeriod,int diff)
	{
		int period;
		List<String> all  = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<=diff;i++)
		{
			period = UtilPeriod.getPeriod(beginPeriod, i);
			if(i==0)
				temp = cal4Work(period,true);
			else
				temp = cal4Work(period,false);
			all.addAll(temp);
		}
		return all;
	}
	
	/**
	 * 计算整个日历周第一天集合
	 * @param beginPeriod
	 * @param diff
	 * @return
	 */
	public static List<String> buildCalendar(int beginPeriod,int diff)
	{
		int period;
		List<String> all  = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<=diff;i++)
		{
			period = UtilPeriod.getPeriod(beginPeriod, i);
			if(i==0)
				temp = cal4Calendar(period,true);
			else
				temp = cal4Calendar(period,false);
			all.addAll(temp);
		}
		return all;
	}
	
	/**
	 * 计算period下面工作周的每周第一天
	 * @param period
	 * @param isFirst
	 * @return
	 */
	public static List<String> cal4Work(int period,boolean isFirst)
	{
		List<String> rel=  new ArrayList<String>();
		String period_ = period+"";
		String year = period_.substring(0,4);
		String month = period_.substring(4,6);
		int date_num = calDay(year,month);
		Calendar c = Calendar.getInstance();  
		c.set(Calendar.YEAR, Integer.parseInt(year));  
	    c.set(Calendar.MONTH, Integer.parseInt(month)-1); 
		
		for(int date =1 ;date<date_num;date++)
		{
			c.set(Calendar.DATE, date); 
			int day=c.get(Calendar.DAY_OF_WEEK);
			if(date==1)
			{
				if(isFirst)
				{
					rel.add(year+"-"+month+"-"+"01");
				}
				else if(day==Calendar.MONDAY)
				{
					if(date>9)
						rel.add(year+"-"+month+"-"+date);
					else
						rel.add(year+"-"+month+"-0"+date);
				}
			}
			else if(day==Calendar.MONDAY)
			{
				if(date>9)
					rel.add(year+"-"+month+"-"+date);
				else
					rel.add(year+"-"+month+"-0"+date);
			}
		}
		return rel;
	}
	/**
	 * 计算当前期间日历周第一天
	 * @param period
	 * @param isFirst
	 * @return
	 */
	public static List<String> cal4Calendar(int period,boolean isFirst)
	{
		List<String> rel=  new ArrayList<String>();
		String period_ = period+"";
		String year = period_.substring(0,4);
		String month = period_.substring(4,6);
		int date_num = calDay(year,month);
		Calendar c = Calendar.getInstance();  
		c.set(Calendar.YEAR, Integer.parseInt(year)); // 
	    c.set(Calendar.MONTH, Integer.parseInt(month)-1); // 
		
		for(int date =1 ;date<date_num;date++)
		{
			c.set(Calendar.DATE, date); // 
			int day=c.get(Calendar.DAY_OF_WEEK);
			if(date==1)
			{
				if(isFirst)
				{
					rel.add(year+"-"+month+"-"+"01");
				}
				else if(day==Calendar.SUNDAY)
				{
					if(date>9)
						rel.add(year+"-"+month+"-"+date);
					else
						rel.add(year+"-"+month+"-0"+date);
				}
			}
			else if(day==Calendar.SUNDAY)
			{
				if(date>9)
					rel.add(year+"-"+month+"-"+date);
				else
					rel.add(year+"-"+month+"-0"+date);
			}
		}
		return rel;
	}
	/**
	 * 计算一个月有多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int calDay(String year,String month)
	{
		Calendar c = Calendar.getInstance();  
		c.set(Calendar.YEAR, Integer.parseInt(year)); 
	    c.set(Calendar.MONTH, Integer.parseInt(month)-1); 
		
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
