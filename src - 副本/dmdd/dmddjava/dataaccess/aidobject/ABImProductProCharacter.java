/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

/**
 * @author liuzhen
 *
 */
public class ABImProductProCharacter implements Serializable
{
	
	public final static long serialVersionUID = -1010000023;
	
	private String detailProductCode = null;
	private String detailProductCharacterCode = null;
	
	private String importResult = null;
	

	/**
	 * 
	 */
	public ABImProductProCharacter() {
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
	 * @return the detailProductCode
	 */
	public String getDetailProductCode()
	{
		return detailProductCode;
	}

	/**
	 * @param detailProductCode the detailProductCode to set
	 */
	public void setDetailProductCode( String detailProductCode )
	{
		this.detailProductCode = detailProductCode;
	}

	/**
	 * @return the detailProductCharacterCode
	 */
	public String getDetailProductCharacterCode()
	{
		return detailProductCharacterCode;
	}

	/**
	 * @param detailProductCharacterCode the detailProductCharacterCode to set
	 */
	public void setDetailProductCharacterCode( String detailProductCharacterCode )
	{
		this.detailProductCharacterCode = detailProductCharacterCode;
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

	
	

	
}
