/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

/**
 * @author liuzhen
 *
 */
public class ABImOrganization implements Serializable
{
	
	public final static long serialVersionUID = -1010000024;
	
	private String parentCode = null;
	private String layerValue = null;
	
	private String code = null;
	private String name = null;
	private String isCatalog = null;
	private String isValid = null;
	
	private String description = null;
	private String comments = null;
	
	private String importResult = null;
	
	

	/**
	 * 
	 */
	public ABImOrganization() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @return the parentCode
	 */
	public String getParentCode()
	{
		return parentCode;
	}

	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode( String parentCode )
	{
		this.parentCode = parentCode;
	}

	/**
	 * @return the layerValue
	 */
	public String getLayerValue()
	{
		return layerValue;
	}

	/**
	 * @param layerValue the layerValue to set
	 */
	public void setLayerValue( String layerValue )
	{
		this.layerValue = layerValue;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode( String code )
	{
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return the isCatalog
	 */
	public String getIsCatalog()
	{
		return isCatalog;
	}

	/**
	 * @param isCatalog the isCatalog to set
	 */
	public void setIsCatalog( String isCatalog )
	{
		this.isCatalog = isCatalog;
	}

	/**
	 * @return the isValid
	 */
	public String getIsValid()
	{
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid( String isValid )
	{
		this.isValid = isValid;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}

	/**
	 * @return the comments
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments( String comments )
	{
		this.comments = comments;
	}

	/**
	 * @return the importResult
	 */
	public String getImportResult()
	{
		return importResult;
	}

	/**
	 * @param importResult the importResult to set
	 */
	public void setImportResult( String importResult )
	{
		this.importResult = importResult;
	}

	private String dcCode = null;
	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

}
