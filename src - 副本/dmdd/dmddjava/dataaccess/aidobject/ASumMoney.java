/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

/**
 * @author liuzhen
 *
 */
public class ASumMoney
{
	/** persistent field */
	private Double														value;

	/** persistent field */
	private int															period;	

	/**
	 * 
	 */
	public ASumMoney() {
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

}
