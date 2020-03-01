/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;

/**
 * @author liuzhen
 *
 */
public class ABForecastSetting implements Serializable
{

	public final static long serialVersionUID = -2060000001;
	private long forecastInstId ;
	private BBizData finalFcBizData = null;
	private int fcPeriodNum;
	private int fzPeriodNum;		
	
	private int distributeRefFormula;
	private BBizData distributeRefBizData = null;
	private int distributeRefPeriodNum;
	
	private int decomposeFormula;
	
	/**
	 * 
	 */
	public ABForecastSetting() {
		// TODO Auto-generated constructor stub
	}

	public long getForecastInstId() {
		return forecastInstId;
	}

	public void setForecastInstId(long forecastInstId) {
		this.forecastInstId = forecastInstId;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @return the finalFcBizData
	 */
	public BBizData getFinalFcBizData()
	{
		return finalFcBizData;
	}

	/**
	 * @param finalFcBizData the finalFcBizData to set
	 */
	public void setFinalFcBizData( BBizData finalFcBizData )
	{
		this.finalFcBizData = finalFcBizData;
	}

	/**
	 * @return the fcPeriodNum
	 */
	public int getFcPeriodNum()
	{
		return fcPeriodNum;
	}

	/**
	 * @param fcPeriodNum the fcPeriodNum to set
	 */
	public void setFcPeriodNum( int fcPeriodNum )
	{
		this.fcPeriodNum = fcPeriodNum;
	}

	/**
	 * @return the fzPeriodNum
	 */
	public int getFzPeriodNum()
	{
		return fzPeriodNum;
	}

	/**
	 * @param fzPeriodNum the fzPeriodNum to set
	 */
	public void setFzPeriodNum( int fzPeriodNum )
	{
		this.fzPeriodNum = fzPeriodNum;
	}

	/**
	 * @return the distributeRefFormula
	 */
	public int getDistributeRefFormula()
	{
		return distributeRefFormula;
	}

	/**
	 * @param distributeRefFormula the distributeRefFormula to set
	 */
	public void setDistributeRefFormula( int distributeRefFormula )
	{
		this.distributeRefFormula = distributeRefFormula;
	}
	
	

	public int getDecomposeFormula() {
		return decomposeFormula;
	}

	public void setDecomposeFormula(int decomposeFormula) {
		this.decomposeFormula = decomposeFormula;
	}

	/**
	 * @return the distributeRefBizData
	 */
	public BBizData getDistributeRefBizData()
	{
		return distributeRefBizData;
	}

	/**
	 * @param distributeRefBizData the distributeRefBizData to set
	 */
	public void setDistributeRefBizData( BBizData distributeRefBizData )
	{
		this.distributeRefBizData = distributeRefBizData;
	}

	/**
	 * @return the distributeRefPeriodNum
	 */
	public int getDistributeRefPeriodNum()
	{
		return distributeRefPeriodNum;
	}

	/**
	 * @param distributeRefPeriodNum the distributeRefPeriodNum to set
	 */
	public void setDistributeRefPeriodNum( int distributeRefPeriodNum )
	{
		this.distributeRefPeriodNum = distributeRefPeriodNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((distributeRefBizData == null) ? 0 : distributeRefBizData
						.hashCode());
		result = prime * result + distributeRefFormula;
		result = prime * result + decomposeFormula;
		result = prime * result + distributeRefPeriodNum;
		result = prime * result + fcPeriodNum;
		result = prime * result
				+ ((finalFcBizData == null) ? 0 : finalFcBizData.hashCode());
		result = prime * result + fzPeriodNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ABForecastSetting other = (ABForecastSetting) obj;
		if (distributeRefBizData == null) {
			if (other.distributeRefBizData != null)
				return false;
		} else if (!distributeRefBizData.equals(other.distributeRefBizData))
			return false;
		if (distributeRefFormula != other.distributeRefFormula)
			return false;
		
		if (decomposeFormula != other.decomposeFormula)
			return false;
		
		if (distributeRefPeriodNum != other.distributeRefPeriodNum)
			return false;
		if (fcPeriodNum != other.fcPeriodNum)
			return false;
		if (finalFcBizData == null) {
			if (other.finalFcBizData != null)
				return false;
		} else if (!finalFcBizData.equals(other.finalFcBizData))
			return false;
		if (fzPeriodNum != other.fzPeriodNum)
			return false;
		return true;
	}

	

	
}
