package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;

public class ABImInHistoryData implements Serializable
{
	private static final long serialVersionUID = 3543938412054973610L;

	private String productcode = null;
	private String organizationcode = null;
	private int period = BizConst.GLOBAL_NULL_NULL;
	private Long value = null;
	private String bizdatacode = null;
	private String unitgroupcode = null;
	private String unitcode = null;
	private String result = null;
	private String warninfo =null;
	private Long id ;
	
	public void setid(Long id)
	{
		this.id = id;
	}
	
	public Long getid()
	{
		return this.id;
	}
	
	public void setproductcode(String  productcode)
	{
		this.productcode = productcode;
	}
	
	public String getproductcode()
	{
		return this.productcode;
	}
	
	public void setorganizationcode(String organizationcode)
	{
		this.organizationcode = organizationcode;
	}
	
	public String getorganizationcode()
	{
		return organizationcode;
	}
	
	public void setperiod(int period)
	{
		this.period = period;
	}
	
	public int getperiod()
	{
		return this.period;
	}
	
	public void setvalue(Long value)
	{
		this.value = value;
	}
	
	public Long getvalue()
	{
		return this.value;
	}
	
	public void setbizdatacode(String bizdatacode)
	{
		this.bizdatacode = bizdatacode;
	}
	
	public String getbizdatacode()
	{
		return bizdatacode;
	}
	
	public void setunitgroupcode(String unitgroupcode)
	{
		this.unitgroupcode = unitgroupcode;
	}
	
	public String getunitgroupcode()
	{
		return this.unitgroupcode;
	}
	
	public void setunitcode(String unitcode)
	{
		this.unitcode = unitcode;
	}
	
	public String getunitcode()
	{
		return this.unitcode ;
	}
	
	public void setresult(String result)
	{
		this.result = result;
	}
	
	public String getresult()
	{
		return this.result;
	}
	
	public void setwarninfo(String warninfo)
	{
		this.warninfo = warninfo;
	}
	
	public String getwarninfo()
	{
		return this.warninfo;
	}
	
	public void exchangeUnit(Long _exchangeRate_old,Long _exchangeRate_new )
	{
		if( _exchangeRate_old<0 || _exchangeRate_new <0 )
		{
			return;	
		}

		Long value_byBase =  this.value * _exchangeRate_old ;	//	先换算成整数基准值				
		this.value =  value_byBase / _exchangeRate_new ;
		
	}
}
