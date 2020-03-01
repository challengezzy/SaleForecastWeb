/**********************************************************************
 *$RCSfile:ABImHistoryADRData.java,v $  $Revision: 1.0 $  $Date:2011-12-14 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.aidobject;

import dmdd.dmddjava.common.constant.SysConst;

/**
 * <li>Title: ABImHistoryADRData.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class ABImHistoryADRData
{
	private String productCode = null;
	private String organizationCode = null;
	private String importResult = null;
	private int period = SysConst.PERIOD_NULL;;
	private Long value  =null;
	private String comments =null;
  	
	public void setproductCode(String productCode)
	{
		this.productCode = productCode;
	}
	
	public String getproductCode()
	{
		return this.productCode;
	}
	
	public void setorganizationCode(String organizationCode)
	{
		this.organizationCode = organizationCode;
	}
	
	public String getorganizationCode()
	{
		return this.organizationCode;
	}

	
	public void setimportResult(String importResult)
	{
		this.importResult = importResult;
	}
	
	public String getimportResult()
	{
		return this.importResult;
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
	
	public void setcomments(String comments)
	{
		this.comments= comments;
	}
	
	public String getcomments()
	{
		return this.comments;
	}
}

/**********************************************************************
 *$RCSfile:ABImHistoryADRData.java,v $  $Revision: 1.0 $  $Date:2011-12-14 $
 *
 *$Log:ABImHistoryADRData.java,v $
 *********************************************************************/