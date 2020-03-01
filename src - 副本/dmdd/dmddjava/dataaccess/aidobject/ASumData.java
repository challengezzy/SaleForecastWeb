/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;


/**
 * @author liuzhen
 *
 */
public class ASumData
{

	/** persistent field */
	private Double														value;

	/** persistent field */
	private int															period;

	/** nullable persistent field */
	private dmdd.dmddjava.dataaccess.dataobject.BizData					bizData;


	/**
	 * 
	 */
	public ASumData()
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

	/**
	 * @return the value
	 */
	public Double getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue( Double value )
	{
		this.value = value;
	}



	/**
	 * @return the period
	 */
	public int getPeriod()
	{
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod( int period )
	{
		this.period = period;
	}

	/**
	 * @return the bizData
	 */
	public dmdd.dmddjava.dataaccess.dataobject.BizData getBizData()
	{
		return bizData;
	}

	/**
	 * @param bizData the bizData to set
	 */
	public void setBizData( dmdd.dmddjava.dataaccess.dataobject.BizData bizData )
	{
		this.bizData = bizData;
	}

}
