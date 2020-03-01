package dmdd.dmddjava.dataaccess.aidobject;

public class ABImOperatorUserProOrg 
{
    private String username="";
    private String productcode="";
	private String organizationcode="";
    private String errorInfo="";
    
    public ABImOperatorUserProOrg()
    {
    	
    }
    
    public void setusername(String username)
    {
    	this.username = username;
    }
    
    public String getusername()
    {
    	return username;
    }
    
    public void setproductcode(String productcode)
    {
    	this.productcode = productcode;
    }
	
    public String getproductcode()
    {
    	return productcode;
    }
    
    public void setorganizationcode(String organizationcode)
    {
    	this.organizationcode = organizationcode;
    }
    
    public String getorganizationcode()
    {
    	return organizationcode;
    }
    
    public void seterrorInfo(String errorInfo)
    {
    	this.errorInfo = errorInfo;
    }
    
    public String geterrorInfo()
    {
    	return errorInfo;
    }
}
