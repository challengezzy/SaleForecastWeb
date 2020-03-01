/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;

/**
 * @author liuzhen
 *
 */
public class ABUiRowData implements Serializable
{
	public final static long serialVersionUID = -2010000001;

	private BProduct product = null;
	private BProductCharacter productCharacter = null;
	private BOrganization organization = null;
	private BOrganizationCharacter organizationCharacter = null;
	private BBizData bizData = null;
	private int periodBegin = SysConst.PERIOD_NULL;
	private int periodEnd = SysConst.PERIOD_NULL;	
	private List<String> detailProOrgIds = null;
	
	/** 是否(1/0)以%显示   */
	private int showPercent = 0;
	
	public Double periodDataValue00 = null;
	public Double periodDataValue01 = null;
	public Double periodDataValue02 = null;
	public Double periodDataValue03 = null;
	public Double periodDataValue04 = null;
	public Double periodDataValue05 = null;
	public Double periodDataValue06 = null;
	public Double periodDataValue07 = null;
	public Double periodDataValue08 = null;
	public Double periodDataValue09 = null;
	public Double periodDataValue10 = null;
	public Double periodDataValue11 = null;
	public Double periodDataValue12 = null;
	public Double periodDataValue13 = null;
	public Double periodDataValue14 = null;
	public Double periodDataValue15 = null;
	public Double periodDataValue16 = null;
	public Double periodDataValue17 = null;
	public Double periodDataValue18 = null;
	public Double periodDataValue19 = null;
	public Double periodDataValue20 = null;
	public Double periodDataValue21 = null;
	public Double periodDataValue22 = null;
	public Double periodDataValue23 = null;
	public Double periodDataValue24 = null;
	public Double periodDataValue25 = null;
	public Double periodDataValue26 = null;
	public Double periodDataValue27 = null;
	public Double periodDataValue28 = null;
	public Double periodDataValue29 = null;	
	
	public Double periodDataValue30 = null;
	public Double periodDataValue31 = null;
	public Double periodDataValue32 = null;
	public Double periodDataValue33 = null;
	public Double periodDataValue34 = null;
	public Double periodDataValue35 = null;
	public Double periodDataValue36 = null;
	public Double periodDataValue37 = null;
	public Double periodDataValue38 = null;
	public Double periodDataValue39 = null;	
	
	public Double periodDataValue40 = null;
	public Double periodDataValue41 = null;
	public Double periodDataValue42 = null;
	public Double periodDataValue43 = null;
	public Double periodDataValue44 = null;
	public Double periodDataValue45 = null;
	public Double periodDataValue46 = null;
	public Double periodDataValue47 = null;
	public Double periodDataValue48 = null;
	public Double periodDataValue49 = null;	
	
	public Double periodDataValue50 = null;
	public Double periodDataValue51 = null;
	public Double periodDataValue52 = null;
	public Double periodDataValue53 = null;
	public Double periodDataValue54 = null;
	public Double periodDataValue55 = null;
	public Double periodDataValue56 = null;
	public Double periodDataValue57 = null;
	public Double periodDataValue58 = null;
	public Double periodDataValue59 = null;				
	
	public Double periodDataValue60 = null;
	public Double periodDataValue61 = null;
	public Double periodDataValue62 = null;
	public Double periodDataValue63 = null;
	public Double periodDataValue64 = null;
	public Double periodDataValue65 = null;
	public Double periodDataValue66 = null;
	public Double periodDataValue67 = null;
	public Double periodDataValue68 = null;
	public Double periodDataValue69 = null;	
	public Double periodDataValue70 = null;
	public Double periodDataValue71 = null;
	public Double periodDataValue72 = null;
	public Double periodDataValue73 = null;
	public Double periodDataValue74 = null;
	public Double periodDataValue75 = null;
	public Double periodDataValue76 = null;
	public Double periodDataValue77 = null;	
	
	public Double periodDataValue00Bak = null;
	public Double periodDataValue01Bak = null;
	public Double periodDataValue02Bak = null;
	public Double periodDataValue03Bak = null;
	public Double periodDataValue04Bak = null;
	public Double periodDataValue05Bak = null;
	public Double periodDataValue06Bak = null;
	public Double periodDataValue07Bak = null;
	public Double periodDataValue08Bak = null;
	public Double periodDataValue09Bak = null;
	public Double periodDataValue10Bak = null;
	public Double periodDataValue11Bak = null;
	public Double periodDataValue12Bak = null;
	public Double periodDataValue13Bak = null;
	public Double periodDataValue14Bak = null;
	public Double periodDataValue15Bak = null;
	public Double periodDataValue16Bak = null;
	public Double periodDataValue17Bak = null;
	public Double periodDataValue18Bak = null;
	public Double periodDataValue19Bak = null;
	public Double periodDataValue20Bak = null;
	public Double periodDataValue21Bak = null;
	public Double periodDataValue22Bak = null;
	public Double periodDataValue23Bak = null;	
	public Double periodDataValue24Bak = null;
	public Double periodDataValue25Bak = null;
	public Double periodDataValue26Bak = null;
	public Double periodDataValue27Bak = null;
	public Double periodDataValue28Bak = null;
	public Double periodDataValue29Bak = null;	
	
	public Double periodDataValue30Bak = null;
	public Double periodDataValue31Bak = null;
	public Double periodDataValue32Bak = null;
	public Double periodDataValue33Bak = null;	
	public Double periodDataValue34Bak = null;
	public Double periodDataValue35Bak = null;
	public Double periodDataValue36Bak = null;
	public Double periodDataValue37Bak = null;
	public Double periodDataValue38Bak = null;
	public Double periodDataValue39Bak = null;
	
	public Double periodDataValue40Bak = null;
	public Double periodDataValue41Bak = null;
	public Double periodDataValue42Bak = null;
	public Double periodDataValue43Bak = null;	
	public Double periodDataValue44Bak = null;
	public Double periodDataValue45Bak = null;
	public Double periodDataValue46Bak = null;
	public Double periodDataValue47Bak = null;
	public Double periodDataValue48Bak = null;
	public Double periodDataValue49Bak = null;	
	
	public Double periodDataValue50Bak = null;
	public Double periodDataValue51Bak = null;
	public Double periodDataValue52Bak = null;
	public Double periodDataValue53Bak = null;	
	public Double periodDataValue54Bak = null;
	public Double periodDataValue55Bak = null;
	public Double periodDataValue56Bak = null;
	public Double periodDataValue57Bak = null;
	public Double periodDataValue58Bak = null;
	public Double periodDataValue59Bak = null;		
	
	public Double periodDataValue60Bak = null;
	public Double periodDataValue61Bak = null;	
	public Double periodDataValue62Bak = null;
	public Double periodDataValue63Bak = null;
	public Double periodDataValue64Bak = null;
	public Double periodDataValue65Bak = null;
	public Double periodDataValue66Bak = null;
	public Double periodDataValue67Bak = null;
	public Double periodDataValue68Bak = null;
	public Double periodDataValue69Bak = null;
	public Double periodDataValue70Bak = null;
	public Double periodDataValue71Bak = null;	
	public Double periodDataValue72Bak = null;
	public Double periodDataValue73Bak = null;
	public Double periodDataValue74Bak = null;
	public Double periodDataValue75Bak = null;
	public Double periodDataValue76Bak = null;
	public Double periodDataValue77Bak = null;
	/**
	 * 
	 */
	public ABUiRowData() {
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
	 * @return the product
	 */
	public BProduct getProduct()
	{
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct( BProduct product )
	{
		this.product = product;
	}

	/**
	 * @return the productCharacter
	 */
	public BProductCharacter getProductCharacter()
	{
		return productCharacter;
	}

	/**
	 * @param productCharacter the productCharacter to set
	 */
	public void setProductCharacter( BProductCharacter productCharacter )
	{
		this.productCharacter = productCharacter;
	}

	/**
	 * @return the organization
	 */
	public BOrganization getOrganization()
	{
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization( BOrganization organization )
	{
		this.organization = organization;
	}

	/**
	 * @return the organizationCharacter
	 */
	public BOrganizationCharacter getOrganizationCharacter()
	{
		return organizationCharacter;
	}

	/**
	 * @param organizationCharacter the organizationCharacter to set
	 */
	public void setOrganizationCharacter( BOrganizationCharacter organizationCharacter )
	{
		this.organizationCharacter = organizationCharacter;
	}

	/**
	 * @return the bizData
	 */
	public BBizData getBizData()
	{
		return bizData;
	}

	/**
	 * @param bizData the bizData to set
	 */
	public void setBizData( BBizData bizData )
	{
		this.bizData = bizData;
	}

	/**
	 * @return the periodBegin
	 */
	public int getPeriodBegin()
	{
		return periodBegin;
	}

	/**
	 * @param periodBegin the periodBegin to set
	 */
	public void setPeriodBegin( int periodBegin )
	{
		this.periodBegin = periodBegin;
	}

	/**
	 * @return the periodEnd
	 */
	public int getPeriodEnd()
	{
		return periodEnd;
	}

	/**
	 * @param periodEnd the periodEnd to set
	 */
	public void setPeriodEnd( int periodEnd )
	{
		this.periodEnd = periodEnd;
	}

	/**
	 * @return the detailProOrgIds
	 */
	public List<String> getDetailProOrgIds()
	{
		return detailProOrgIds;
	}

	/**
	 * @param detailProOrgIds the detailProOrgIds to set
	 */
	public void setDetailProOrgIds( List<String> detailProOrgIds )
	{
		this.detailProOrgIds = detailProOrgIds;
	}
	/** 是否(1/0)以%显示   */
	public int getShowPercent() {
		return showPercent;
	}

	/** 是否(1/0)以%显示   */
	public void setShowPercent(int showPercent) {
		this.showPercent = showPercent;
	}

	/**
	 * 格式化输出,保留N位小数
	 * @param _ithPeriod
	 * @return
	 */
	public Double pubFun4getPeriodDataValue( int _ithPeriod ){
		Double valueOri = getPeriodDataValue(_ithPeriod);
		
		if(valueOri == null){
			return null;
		}
		
		return valueOri;
//		BigDecimal bg = new BigDecimal(valueOri);
//		
//		int decimalLen = ServerEnvironment.getInstance().getDecimalLength();
//		double valueNew = bg.setScale(decimalLen, BigDecimal.ROUND_HALF_UP).doubleValue();
//		
//		return valueNew;
	}
	
	private Double getPeriodDataValue( int _ithPeriod )
	{
		switch( _ithPeriod )
		{
			case  0: return this.periodDataValue00;
			case  1: return this.periodDataValue01;
			case  2: return this.periodDataValue02;
			case  3: return this.periodDataValue03;
			case  4: return this.periodDataValue04;
			case  5: return this.periodDataValue05;
			case  6: return this.periodDataValue06;
			case  7: return this.periodDataValue07;
			case  8: return this.periodDataValue08;
			case  9: return this.periodDataValue09;
			case 10: return this.periodDataValue10;
			case 11: return this.periodDataValue11;
			case 12: return this.periodDataValue12;
			case 13: return this.periodDataValue13;
			case 14: return this.periodDataValue14;
			case 15: return this.periodDataValue15;
			case 16: return this.periodDataValue16;
			case 17: return this.periodDataValue17;
			case 18: return this.periodDataValue18;
			case 19: return this.periodDataValue19;
			case 20: return this.periodDataValue20;
			case 21: return this.periodDataValue21;
			case 22: return this.periodDataValue22;
			case 23: return this.periodDataValue23;
			case 24: return this.periodDataValue24;
			case 25: return this.periodDataValue25;
			case 26: return this.periodDataValue26;
			case 27: return this.periodDataValue27;
			case 28: return this.periodDataValue28;
			case 29: return this.periodDataValue29;		
			
			case 30: return this.periodDataValue30;
			case 31: return this.periodDataValue31;
			case 32: return this.periodDataValue32;
			case 33: return this.periodDataValue33;
			case 34: return this.periodDataValue34;
			case 35: return this.periodDataValue35;
			case 36: return this.periodDataValue36;
			case 37: return this.periodDataValue37;
			case 38: return this.periodDataValue38;
			case 39: return this.periodDataValue39;	
			
			case 40: return this.periodDataValue40;
			case 41: return this.periodDataValue41;
			case 42: return this.periodDataValue42;
			case 43: return this.periodDataValue43;
			case 44: return this.periodDataValue44;
			case 45: return this.periodDataValue45;
			case 46: return this.periodDataValue46;
			case 47: return this.periodDataValue47;
			case 48: return this.periodDataValue48;
			case 49: return this.periodDataValue49;				
			
			case 50: return this.periodDataValue50;
			case 51: return this.periodDataValue51;
			case 52: return this.periodDataValue52;
			case 53: return this.periodDataValue53;
			case 54: return this.periodDataValue54;
			case 55: return this.periodDataValue55;
			case 56: return this.periodDataValue56;
			case 57: return this.periodDataValue57;
			case 58: return this.periodDataValue58;
			case 59: return this.periodDataValue59;			
			
			case 60: return this.periodDataValue60;
			case 61: return this.periodDataValue61;
			case 62: return this.periodDataValue62;
			case 63: return this.periodDataValue63;
			case 64: return this.periodDataValue64;
			case 65: return this.periodDataValue65;
			case 66: return this.periodDataValue66;
			case 67: return this.periodDataValue67;	
			case 68: return this.periodDataValue68;
			case 69: return this.periodDataValue69;
			case 70: return this.periodDataValue70;
			case 71: return this.periodDataValue71;
			case 72: return this.periodDataValue72;
			case 73: return this.periodDataValue73;
			case 74: return this.periodDataValue74;
			case 75: return this.periodDataValue75;
			case 76: return this.periodDataValue76;
			case 77: return this.periodDataValue77;	
			
		}
		
		return null;
	}
	
	
	public void pubFun4setPeriodDataValue( int _ithPeriod, Double _value )
	{
		switch( _ithPeriod )
		{
			case  0: this.periodDataValue00 = _value; return;
			case  1: this.periodDataValue01 = _value; return;
			case  2: this.periodDataValue02 = _value; return;
			case  3: this.periodDataValue03 = _value; return;
			case  4: this.periodDataValue04 = _value; return;
			case  5: this.periodDataValue05 = _value; return;
			case  6: this.periodDataValue06 = _value; return;
			case  7: this.periodDataValue07 = _value; return;
			case  8: this.periodDataValue08 = _value; return;
			case  9: this.periodDataValue09 = _value; return;
			case 10: this.periodDataValue10 = _value; return;
			case 11: this.periodDataValue11 = _value; return;
			case 12: this.periodDataValue12 = _value; return;
			case 13: this.periodDataValue13 = _value; return;
			case 14: this.periodDataValue14 = _value; return;
			case 15: this.periodDataValue15 = _value; return;
			case 16: this.periodDataValue16 = _value; return;
			case 17: this.periodDataValue17 = _value; return;
			case 18: this.periodDataValue18 = _value; return;
			case 19: this.periodDataValue19 = _value; return;
			case 20: this.periodDataValue20 = _value; return;
			case 21: this.periodDataValue21 = _value; return;
			case 22: this.periodDataValue22 = _value; return;
			case 23: this.periodDataValue23 = _value; return;
			case 24: this.periodDataValue24 = _value; return;
			case 25: this.periodDataValue25 = _value; return;
			case 26: this.periodDataValue26 = _value; return;
			case 27: this.periodDataValue27 = _value; return;
			case 28: this.periodDataValue28 = _value; return;
			case 29: this.periodDataValue29 = _value; return;	
			
			case 30: this.periodDataValue30 = _value; return;
			case 31: this.periodDataValue31 = _value; return;
			case 32: this.periodDataValue32 = _value; return;
			case 33: this.periodDataValue33 = _value; return;
			case 34: this.periodDataValue34 = _value; return;
			case 35: this.periodDataValue35 = _value; return;
			case 36: this.periodDataValue36 = _value; return;
			case 37: this.periodDataValue37 = _value; return;
			case 38: this.periodDataValue38 = _value; return;
			case 39: this.periodDataValue39 = _value; return;
			
			case 40: this.periodDataValue40 = _value; return;
			case 41: this.periodDataValue41 = _value; return;
			case 42: this.periodDataValue42 = _value; return;
			case 43: this.periodDataValue43 = _value; return;
			case 44: this.periodDataValue44 = _value; return;
			case 45: this.periodDataValue45 = _value; return;
			case 46: this.periodDataValue46 = _value; return;
			case 47: this.periodDataValue47 = _value; return;
			case 48: this.periodDataValue48 = _value; return;
			case 49: this.periodDataValue49 = _value; return;			
			
			case 50: this.periodDataValue50 = _value; return;
			case 51: this.periodDataValue51 = _value; return;
			case 52: this.periodDataValue52 = _value; return;
			case 53: this.periodDataValue53 = _value; return;
			case 54: this.periodDataValue54 = _value; return;
			case 55: this.periodDataValue55 = _value; return;
			case 56: this.periodDataValue56 = _value; return;
			case 57: this.periodDataValue57 = _value; return;
			case 58: this.periodDataValue58 = _value; return;
			case 59: this.periodDataValue59 = _value; return;	
			
			case 60: this.periodDataValue60 = _value; return;
			case 61: this.periodDataValue61 = _value; return;
			case 62: this.periodDataValue62 = _value; return;
			case 63: this.periodDataValue63 = _value; return;
			case 64: this.periodDataValue64 = _value; return;
			case 65: this.periodDataValue65 = _value; return;
			case 66: this.periodDataValue66 = _value; return;
			case 67: this.periodDataValue67 = _value; return;
			case 68: this.periodDataValue68 = _value; return;
			case 69: this.periodDataValue69 = _value; return;
			case 70: this.periodDataValue70 = _value; return;
			case 71: this.periodDataValue71 = _value; return;
			case 72: this.periodDataValue72 = _value; return;
			case 73: this.periodDataValue73 = _value; return;
			case 74: this.periodDataValue74 = _value; return;
			case 75: this.periodDataValue75 = _value; return;
			case 76: this.periodDataValue76 = _value; return;
			case 77: this.periodDataValue77 = _value; return;					
		}
	} 
	
	/**
	 * 获取保留N位小数的值
	 * @param _ithPeriod
	 * @return
	 */
	public Double pubFun4getPeriodDataValueBak( int _ithPeriod ){
		Double valueOri = getPeriodDataValueBak(_ithPeriod);
		
		if(valueOri == null){
			return null;
		}
		
		return valueOri;
//		BigDecimal bg = new BigDecimal(valueOri);
//		
//		int decimalLen = ServerEnvironment.getInstance().getDecimalLength();
//		double valueNew = bg.setScale(decimalLen, BigDecimal.ROUND_HALF_UP).doubleValue();
//		
//		return valueNew;
	}
	
	private Double getPeriodDataValueBak( int _ithPeriod )
	{
		switch( _ithPeriod )
		{
			case  0: return this.periodDataValue00Bak;
			case  1: return this.periodDataValue01Bak;
			case  2: return this.periodDataValue02Bak;
			case  3: return this.periodDataValue03Bak;
			case  4: return this.periodDataValue04Bak;
			case  5: return this.periodDataValue05Bak;
			case  6: return this.periodDataValue06Bak;
			case  7: return this.periodDataValue07Bak;
			case  8: return this.periodDataValue08Bak;
			case  9: return this.periodDataValue09Bak;
			case 10: return this.periodDataValue10Bak;
			case 11: return this.periodDataValue11Bak;
			case 12: return this.periodDataValue12Bak;
			case 13: return this.periodDataValue13Bak;
			case 14: return this.periodDataValue14Bak;
			case 15: return this.periodDataValue15Bak;
			case 16: return this.periodDataValue16Bak;
			case 17: return this.periodDataValue17Bak;
			case 18: return this.periodDataValue18Bak;
			case 19: return this.periodDataValue19Bak;
			case 20: return this.periodDataValue20Bak;
			case 21: return this.periodDataValue21Bak;
			case 22: return this.periodDataValue22Bak;
			case 23: return this.periodDataValue23Bak;
			case 24: return this.periodDataValue24Bak;
			case 25: return this.periodDataValue25Bak;
			case 26: return this.periodDataValue26Bak;
			case 27: return this.periodDataValue27Bak;
			case 28: return this.periodDataValue28Bak;
			case 29: return this.periodDataValue29Bak;	
			
			case 30: return this.periodDataValue30Bak;
			case 31: return this.periodDataValue31Bak;
			case 32: return this.periodDataValue32Bak;
			case 33: return this.periodDataValue33Bak;
			case 34: return this.periodDataValue34Bak;
			case 35: return this.periodDataValue35Bak;
			case 36: return this.periodDataValue36Bak;
			case 37: return this.periodDataValue37Bak;
			case 38: return this.periodDataValue38Bak;
			case 39: return this.periodDataValue39Bak;	
			
			case 40: return this.periodDataValue40Bak;
			case 41: return this.periodDataValue41Bak;
			case 42: return this.periodDataValue42Bak;
			case 43: return this.periodDataValue43Bak;
			case 44: return this.periodDataValue44Bak;
			case 45: return this.periodDataValue45Bak;
			case 46: return this.periodDataValue46Bak;
			case 47: return this.periodDataValue47Bak;
			case 48: return this.periodDataValue48Bak;
			case 49: return this.periodDataValue49Bak;				
			
			case 50: return this.periodDataValue50Bak;
			case 51: return this.periodDataValue51Bak;
			case 52: return this.periodDataValue52Bak;
			case 53: return this.periodDataValue53Bak;
			case 54: return this.periodDataValue54Bak;
			case 55: return this.periodDataValue55Bak;
			case 56: return this.periodDataValue56Bak;
			case 57: return this.periodDataValue57Bak;
			case 58: return this.periodDataValue58Bak;
			case 59: return this.periodDataValue59Bak;	
			
			case 60: return this.periodDataValue60Bak;
			case 61: return this.periodDataValue61Bak;
			case 62: return this.periodDataValue62Bak;
			case 63: return this.periodDataValue63Bak;
			case 64: return this.periodDataValue64Bak;
			case 65: return this.periodDataValue65Bak;
			case 66: return this.periodDataValue66Bak;
			case 67: return this.periodDataValue67Bak;
			case 68: return this.periodDataValue68Bak;
			case 69: return this.periodDataValue69Bak;
			case 70: return this.periodDataValue70Bak;
			case 71: return this.periodDataValue71Bak;
			case 72: return this.periodDataValue72Bak;
			case 73: return this.periodDataValue73Bak;
			case 74: return this.periodDataValue74Bak;
			case 75: return this.periodDataValue75Bak;
			case 76: return this.periodDataValue76Bak;
			case 77: return this.periodDataValue77Bak;
		}
		
		return null;
	}
	
	
	public void pubFun4setPeriodDataValueBak( int _ithPeriod, Double _value )
	{
		switch( _ithPeriod )
		{
			case  0: this.periodDataValue00Bak = _value; return;
			case  1: this.periodDataValue01Bak = _value; return;
			case  2: this.periodDataValue02Bak = _value; return;
			case  3: this.periodDataValue03Bak = _value; return;
			case  4: this.periodDataValue04Bak = _value; return;
			case  5: this.periodDataValue05Bak = _value; return;
			case  6: this.periodDataValue06Bak = _value; return;
			case  7: this.periodDataValue07Bak = _value; return;
			case  8: this.periodDataValue08Bak = _value; return;
			case  9: this.periodDataValue09Bak = _value; return;
			case 10: this.periodDataValue10Bak = _value; return;
			case 11: this.periodDataValue11Bak = _value; return;
			case 12: this.periodDataValue12Bak = _value; return;
			case 13: this.periodDataValue13Bak = _value; return;
			case 14: this.periodDataValue14Bak = _value; return;
			case 15: this.periodDataValue15Bak = _value; return;
			case 16: this.periodDataValue16Bak = _value; return;
			case 17: this.periodDataValue17Bak = _value; return;
			case 18: this.periodDataValue18Bak = _value; return;
			case 19: this.periodDataValue19Bak = _value; return;
			case 20: this.periodDataValue20Bak = _value; return;
			case 21: this.periodDataValue21Bak = _value; return;
			case 22: this.periodDataValue22Bak = _value; return;
			case 23: this.periodDataValue23Bak = _value; return;
			case 24: this.periodDataValue24Bak = _value; return;
			case 25: this.periodDataValue25Bak = _value; return;
			case 26: this.periodDataValue26Bak = _value; return;
			case 27: this.periodDataValue27Bak = _value; return;
			case 28: this.periodDataValue28Bak = _value; return;
			case 29: this.periodDataValue29Bak = _value; return;	
			
			case 30: this.periodDataValue30Bak = _value; return;
			case 31: this.periodDataValue31Bak = _value; return;
			case 32: this.periodDataValue32Bak = _value; return;
			case 33: this.periodDataValue33Bak = _value; return;
			case 34: this.periodDataValue34Bak = _value; return;
			case 35: this.periodDataValue35Bak = _value; return;
			case 36: this.periodDataValue36Bak = _value; return;
			case 37: this.periodDataValue37Bak = _value; return;
			case 38: this.periodDataValue38Bak = _value; return;
			case 39: this.periodDataValue39Bak = _value; return;
			
			case 40: this.periodDataValue40Bak = _value; return;
			case 41: this.periodDataValue41Bak = _value; return;
			case 42: this.periodDataValue42Bak = _value; return;
			case 43: this.periodDataValue43Bak = _value; return;
			case 44: this.periodDataValue44Bak = _value; return;
			case 45: this.periodDataValue45Bak = _value; return;
			case 46: this.periodDataValue46Bak = _value; return;
			case 47: this.periodDataValue47Bak = _value; return;
			case 48: this.periodDataValue48Bak = _value; return;
			case 49: this.periodDataValue49Bak = _value; return;			
			
			case 50: this.periodDataValue50Bak = _value; return;
			case 51: this.periodDataValue51Bak = _value; return;
			case 52: this.periodDataValue52Bak = _value; return;
			case 53: this.periodDataValue53Bak = _value; return;
			case 54: this.periodDataValue54Bak = _value; return;
			case 55: this.periodDataValue55Bak = _value; return;
			case 56: this.periodDataValue56Bak = _value; return;
			case 57: this.periodDataValue57Bak = _value; return;
			case 58: this.periodDataValue58Bak = _value; return;
			case 59: this.periodDataValue59Bak = _value; return;
			case 60: this.periodDataValue60Bak = _value; return;
			case 61: this.periodDataValue61Bak = _value; return;
			case 62: this.periodDataValue62Bak = _value; return;
			case 63: this.periodDataValue63Bak = _value; return;
			case 64: this.periodDataValue64Bak = _value; return;
			case 65: this.periodDataValue65Bak = _value; return;
			case 66: this.periodDataValue66Bak = _value; return;
			case 67: this.periodDataValue67Bak = _value; return;
			case 68: this.periodDataValue68Bak = _value; return;
			case 69: this.periodDataValue69Bak = _value; return;
			case 70: this.periodDataValue70Bak = _value; return;
			case 71: this.periodDataValue71Bak = _value; return;
			case 72: this.periodDataValue72Bak = _value; return;
			case 73: this.periodDataValue73Bak = _value; return;
			case 74: this.periodDataValue74Bak = _value; return;
			case 75: this.periodDataValue75Bak = _value; return;
			case 76: this.periodDataValue76Bak = _value; return;
			case 77: this.periodDataValue77Bak = _value; return;
		}
	}

	public Double getPeriodDataValue60() {
		return periodDataValue60;
	}

	public void setPeriodDataValue60( Double periodDataValue60) {
		this.periodDataValue60 = periodDataValue60;
	}

	public Double getPeriodDataValue61() {
		return periodDataValue61;
	}

	public void setPeriodDataValue61( Double periodDataValue61) {
		this.periodDataValue61 = periodDataValue61;
	}

	public Double getPeriodDataValue62() {
		return periodDataValue62;
	}

	public void setPeriodDataValue62( Double periodDataValue62) {
		this.periodDataValue62 = periodDataValue62;
	}

	public Double getPeriodDataValue63() {
		return periodDataValue63;
	}

	public void setPeriodDataValue63( Double periodDataValue63) {
		this.periodDataValue63 = periodDataValue63;
	}

	public Double getPeriodDataValue64() {
		return periodDataValue64;
	}

	public void setPeriodDataValue64( Double periodDataValue64) {
		this.periodDataValue64 = periodDataValue64;
	}

	public Double getPeriodDataValue65() {
		return periodDataValue65;
	}

	public void setPeriodDataValue65( Double periodDataValue65) {
		this.periodDataValue65 = periodDataValue65;
	}

	public Double getPeriodDataValue66() {
		return periodDataValue66;
	}

	public void setPeriodDataValue66( Double periodDataValue66) {
		this.periodDataValue66 = periodDataValue66;
	}

	public Double getPeriodDataValue67() {
		return periodDataValue67;
	}

	public void setPeriodDataValue67( Double periodDataValue67) {
		this.periodDataValue67 = periodDataValue67;
	}

	public Double getPeriodDataValue68() {
		return periodDataValue68;
	}

	public void setPeriodDataValue68( Double periodDataValue68) {
		this.periodDataValue68 = periodDataValue68;
	}

	public Double getPeriodDataValue69() {
		return periodDataValue69;
	}

	public void setPeriodDataValue69( Double periodDataValue69) {
		this.periodDataValue69 = periodDataValue69;
	}

	public Double getPeriodDataValue70() {
		return periodDataValue70;
	}

	public void setPeriodDataValue70( Double periodDataValue70) {
		this.periodDataValue70 = periodDataValue70;
	}

	public Double getPeriodDataValue71() {
		return periodDataValue71;
	}

	public void setPeriodDataValue71( Double periodDataValue71) {
		this.periodDataValue71 = periodDataValue71;
	}

	public Double getPeriodDataValue72() {
		return periodDataValue72;
	}

	public void setPeriodDataValue72( Double periodDataValue72) {
		this.periodDataValue72 = periodDataValue72;
	}

	public Double getPeriodDataValue73() {
		return periodDataValue73;
	}

	public void setPeriodDataValue73( Double periodDataValue73) {
		this.periodDataValue73 = periodDataValue73;
	}

	public Double getPeriodDataValue74() {
		return periodDataValue74;
	}

	public void setPeriodDataValue74( Double periodDataValue74) {
		this.periodDataValue74 = periodDataValue74;
	}

	public Double getPeriodDataValue75() {
		return periodDataValue75;
	}

	public void setPeriodDataValue75( Double periodDataValue75) {
		this.periodDataValue75 = periodDataValue75;
	}

	public Double getPeriodDataValue76() {
		return periodDataValue76;
	}

	public void setPeriodDataValue76( Double periodDataValue76) {
		this.periodDataValue76 = periodDataValue76;
	}

	public Double getPeriodDataValue77() {
		return periodDataValue77;
	}

	public void setPeriodDataValue77( Double periodDataValue77) {
		this.periodDataValue77 = periodDataValue77;
	}

	public Double getPeriodDataValue60Bak() {
		return periodDataValue60Bak;
	}

	public void setPeriodDataValue60Bak( Double periodDataValue60Bak) {
		this.periodDataValue60Bak = periodDataValue60Bak;
	}

	public Double getPeriodDataValue61Bak() {
		return periodDataValue61Bak;
	}

	public void setPeriodDataValue61Bak( Double periodDataValue61Bak) {
		this.periodDataValue61Bak = periodDataValue61Bak;
	}

	public Double getPeriodDataValue62Bak() {
		return periodDataValue62Bak;
	}

	public void setPeriodDataValue62Bak( Double periodDataValue62Bak) {
		this.periodDataValue62Bak = periodDataValue62Bak;
	}

	public Double getPeriodDataValue63Bak() {
		return periodDataValue63Bak;
	}

	public void setPeriodDataValue63Bak( Double periodDataValue63Bak) {
		this.periodDataValue63Bak = periodDataValue63Bak;
	}

	public Double getPeriodDataValue64Bak() {
		return periodDataValue64Bak;
	}

	public void setPeriodDataValue64Bak( Double periodDataValue64Bak) {
		this.periodDataValue64Bak = periodDataValue64Bak;
	}

	public Double getPeriodDataValue65Bak() {
		return periodDataValue65Bak;
	}

	public void setPeriodDataValue65Bak( Double periodDataValue65Bak) {
		this.periodDataValue65Bak = periodDataValue65Bak;
	}

	public Double getPeriodDataValue66Bak() {
		return periodDataValue66Bak;
	}

	public void setPeriodDataValue66Bak( Double periodDataValue66Bak) {
		this.periodDataValue66Bak = periodDataValue66Bak;
	}

	public Double getPeriodDataValue67Bak() {
		return periodDataValue67Bak;
	}

	public void setPeriodDataValue67Bak( Double periodDataValue67Bak) {
		this.periodDataValue67Bak = periodDataValue67Bak;
	}

	public Double getPeriodDataValue68Bak() {
		return periodDataValue68Bak;
	}

	public void setPeriodDataValue68Bak( Double periodDataValue68Bak) {
		this.periodDataValue68Bak = periodDataValue68Bak;
	}

	public Double getPeriodDataValue69Bak() {
		return periodDataValue69Bak;
	}

	public void setPeriodDataValue69Bak( Double periodDataValue69Bak) {
		this.periodDataValue69Bak = periodDataValue69Bak;
	}

	public Double getPeriodDataValue70Bak() {
		return periodDataValue70Bak;
	}

	public void setPeriodDataValue70Bak( Double periodDataValue70Bak) {
		this.periodDataValue70Bak = periodDataValue70Bak;
	}

	public Double getPeriodDataValue71Bak() {
		return periodDataValue71Bak;
	}

	public void setPeriodDataValue71Bak( Double periodDataValue71Bak) {
		this.periodDataValue71Bak = periodDataValue71Bak;
	}

	public Double getPeriodDataValue72Bak() {
		return periodDataValue72Bak;
	}

	public void setPeriodDataValue72Bak( Double periodDataValue72Bak) {
		this.periodDataValue72Bak = periodDataValue72Bak;
	}

	public Double getPeriodDataValue73Bak() {
		return periodDataValue73Bak;
	}

	public void setPeriodDataValue73Bak( Double periodDataValue73Bak) {
		this.periodDataValue73Bak = periodDataValue73Bak;
	}

	public Double getPeriodDataValue74Bak() {
		return periodDataValue74Bak;
	}

	public void setPeriodDataValue74Bak( Double periodDataValue74Bak) {
		this.periodDataValue74Bak = periodDataValue74Bak;
	}

	public Double getPeriodDataValue75Bak() {
		return periodDataValue75Bak;
	}

	public void setPeriodDataValue75Bak( Double periodDataValue75Bak) {
		this.periodDataValue75Bak = periodDataValue75Bak;
	}

	public Double getPeriodDataValue76Bak() {
		return periodDataValue76Bak;
	}

	public void setPeriodDataValue76Bak( Double periodDataValue76Bak) {
		this.periodDataValue76Bak = periodDataValue76Bak;
	}

	public Double getPeriodDataValue77Bak() {
		return periodDataValue77Bak;
	}

	public void setPeriodDataValue77Bak( Double periodDataValue77Bak) {
		this.periodDataValue77Bak = periodDataValue77Bak;
	}

	/**
	 * @return the periodDataValue00
	 */
	public Double getPeriodDataValue00()
	{
		return periodDataValue00;
	}

	/**
	 * @param periodDataValue00 the periodDataValue00 to set
	 */
	public void setPeriodDataValue00( Double periodDataValue00 )
	{
		this.periodDataValue00 = periodDataValue00;
	}

	/**
	 * @return the periodDataValue01
	 */
	public Double getPeriodDataValue01()
	{
		return periodDataValue01;
	}

	/**
	 * @param periodDataValue01 the periodDataValue01 to set
	 */
	public void setPeriodDataValue01( Double periodDataValue01 )
	{
		this.periodDataValue01 = periodDataValue01;
	}

	/**
	 * @return the periodDataValue02
	 */
	public Double getPeriodDataValue02()
	{
		return periodDataValue02;
	}

	/**
	 * @param periodDataValue02 the periodDataValue02 to set
	 */
	public void setPeriodDataValue02( Double periodDataValue02 )
	{
		this.periodDataValue02 = periodDataValue02;
	}

	/**
	 * @return the periodDataValue03
	 */
	public Double getPeriodDataValue03()
	{
		return periodDataValue03;
	}

	/**
	 * @param periodDataValue03 the periodDataValue03 to set
	 */
	public void setPeriodDataValue03( Double periodDataValue03 )
	{
		this.periodDataValue03 = periodDataValue03;
	}

	/**
	 * @return the periodDataValue04
	 */
	public Double getPeriodDataValue04()
	{
		return periodDataValue04;
	}

	/**
	 * @param periodDataValue04 the periodDataValue04 to set
	 */
	public void setPeriodDataValue04( Double periodDataValue04 )
	{
		this.periodDataValue04 = periodDataValue04;
	}

	/**
	 * @return the periodDataValue05
	 */
	public Double getPeriodDataValue05()
	{
		return periodDataValue05;
	}

	/**
	 * @param periodDataValue05 the periodDataValue05 to set
	 */
	public void setPeriodDataValue05( Double periodDataValue05 )
	{
		this.periodDataValue05 = periodDataValue05;
	}

	/**
	 * @return the periodDataValue06
	 */
	public Double getPeriodDataValue06()
	{
		return periodDataValue06;
	}

	/**
	 * @param periodDataValue06 the periodDataValue06 to set
	 */
	public void setPeriodDataValue06( Double periodDataValue06 )
	{
		this.periodDataValue06 = periodDataValue06;
	}

	/**
	 * @return the periodDataValue07
	 */
	public Double getPeriodDataValue07()
	{
		return periodDataValue07;
	}

	/**
	 * @param periodDataValue07 the periodDataValue07 to set
	 */
	public void setPeriodDataValue07( Double periodDataValue07 )
	{
		this.periodDataValue07 = periodDataValue07;
	}

	/**
	 * @return the periodDataValue08
	 */
	public Double getPeriodDataValue08()
	{
		return periodDataValue08;
	}

	/**
	 * @param periodDataValue08 the periodDataValue08 to set
	 */
	public void setPeriodDataValue08( Double periodDataValue08 )
	{
		this.periodDataValue08 = periodDataValue08;
	}

	/**
	 * @return the periodDataValue09
	 */
	public Double getPeriodDataValue09()
	{
		return periodDataValue09;
	}

	/**
	 * @param periodDataValue09 the periodDataValue09 to set
	 */
	public void setPeriodDataValue09( Double periodDataValue09 )
	{
		this.periodDataValue09 = periodDataValue09;
	}

	/**
	 * @return the periodDataValue10
	 */
	public Double getPeriodDataValue10()
	{
		return periodDataValue10;
	}

	/**
	 * @param periodDataValue10 the periodDataValue10 to set
	 */
	public void setPeriodDataValue10( Double periodDataValue10 )
	{
		this.periodDataValue10 = periodDataValue10;
	}

	/**
	 * @return the periodDataValue11
	 */
	public Double getPeriodDataValue11()
	{
		return periodDataValue11;
	}

	/**
	 * @param periodDataValue11 the periodDataValue11 to set
	 */
	public void setPeriodDataValue11( Double periodDataValue11 )
	{
		this.periodDataValue11 = periodDataValue11;
	}

	/**
	 * @return the periodDataValue12
	 */
	public Double getPeriodDataValue12()
	{
		return periodDataValue12;
	}

	/**
	 * @param periodDataValue12 the periodDataValue12 to set
	 */
	public void setPeriodDataValue12( Double periodDataValue12 )
	{
		this.periodDataValue12 = periodDataValue12;
	}

	/**
	 * @return the periodDataValue13
	 */
	public Double getPeriodDataValue13()
	{
		return periodDataValue13;
	}

	/**
	 * @param periodDataValue13 the periodDataValue13 to set
	 */
	public void setPeriodDataValue13( Double periodDataValue13 )
	{
		this.periodDataValue13 = periodDataValue13;
	}

	/**
	 * @return the periodDataValue14
	 */
	public Double getPeriodDataValue14()
	{
		return periodDataValue14;
	}

	/**
	 * @param periodDataValue14 the periodDataValue14 to set
	 */
	public void setPeriodDataValue14( Double periodDataValue14 )
	{
		this.periodDataValue14 = periodDataValue14;
	}

	/**
	 * @return the periodDataValue15
	 */
	public Double getPeriodDataValue15()
	{
		return periodDataValue15;
	}

	/**
	 * @param periodDataValue15 the periodDataValue15 to set
	 */
	public void setPeriodDataValue15( Double periodDataValue15 )
	{
		this.periodDataValue15 = periodDataValue15;
	}

	/**
	 * @return the periodDataValue16
	 */
	public Double getPeriodDataValue16()
	{
		return periodDataValue16;
	}

	/**
	 * @param periodDataValue16 the periodDataValue16 to set
	 */
	public void setPeriodDataValue16( Double periodDataValue16 )
	{
		this.periodDataValue16 = periodDataValue16;
	}

	/**
	 * @return the periodDataValue17
	 */
	public Double getPeriodDataValue17()
	{
		return periodDataValue17;
	}

	/**
	 * @param periodDataValue17 the periodDataValue17 to set
	 */
	public void setPeriodDataValue17( Double periodDataValue17 )
	{
		this.periodDataValue17 = periodDataValue17;
	}

	/**
	 * @return the periodDataValue18
	 */
	public Double getPeriodDataValue18()
	{
		return periodDataValue18;
	}

	/**
	 * @param periodDataValue18 the periodDataValue18 to set
	 */
	public void setPeriodDataValue18( Double periodDataValue18 )
	{
		this.periodDataValue18 = periodDataValue18;
	}

	/**
	 * @return the periodDataValue19
	 */
	public Double getPeriodDataValue19()
	{
		return periodDataValue19;
	}

	/**
	 * @param periodDataValue19 the periodDataValue19 to set
	 */
	public void setPeriodDataValue19( Double periodDataValue19 )
	{
		this.periodDataValue19 = periodDataValue19;
	}

	/**
	 * @return the periodDataValue20
	 */
	public Double getPeriodDataValue20()
	{
		return periodDataValue20;
	}

	/**
	 * @param periodDataValue20 the periodDataValue20 to set
	 */
	public void setPeriodDataValue20( Double periodDataValue20 )
	{
		this.periodDataValue20 = periodDataValue20;
	}

	/**
	 * @return the periodDataValue21
	 */
	public Double getPeriodDataValue21()
	{
		return periodDataValue21;
	}

	/**
	 * @param periodDataValue21 the periodDataValue21 to set
	 */
	public void setPeriodDataValue21( Double periodDataValue21 )
	{
		this.periodDataValue21 = periodDataValue21;
	}

	/**
	 * @return the periodDataValue22
	 */
	public Double getPeriodDataValue22()
	{
		return periodDataValue22;
	}

	/**
	 * @param periodDataValue22 the periodDataValue22 to set
	 */
	public void setPeriodDataValue22( Double periodDataValue22 )
	{
		this.periodDataValue22 = periodDataValue22;
	}

	/**
	 * @return the periodDataValue23
	 */
	public Double getPeriodDataValue23()
	{
		return periodDataValue23;
	}

	/**
	 * @param periodDataValue23 the periodDataValue23 to set
	 */
	public void setPeriodDataValue23( Double periodDataValue23 )
	{
		this.periodDataValue23 = periodDataValue23;
	}

	/**
	 * @return the periodDataValue24
	 */
	public Double getPeriodDataValue24()
	{
		return periodDataValue24;
	}

	/**
	 * @param periodDataValue24 the periodDataValue24 to set
	 */
	public void setPeriodDataValue24( Double periodDataValue24 )
	{
		this.periodDataValue24 = periodDataValue24;
	}

	/**
	 * @return the periodDataValue25
	 */
	public Double getPeriodDataValue25()
	{
		return periodDataValue25;
	}

	/**
	 * @param periodDataValue25 the periodDataValue25 to set
	 */
	public void setPeriodDataValue25( Double periodDataValue25 )
	{
		this.periodDataValue25 = periodDataValue25;
	}

	/**
	 * @return the periodDataValue26
	 */
	public Double getPeriodDataValue26()
	{
		return periodDataValue26;
	}

	/**
	 * @param periodDataValue26 the periodDataValue26 to set
	 */
	public void setPeriodDataValue26( Double periodDataValue26 )
	{
		this.periodDataValue26 = periodDataValue26;
	}

	/**
	 * @return the periodDataValue27
	 */
	public Double getPeriodDataValue27()
	{
		return periodDataValue27;
	}

	/**
	 * @param periodDataValue27 the periodDataValue27 to set
	 */
	public void setPeriodDataValue27( Double periodDataValue27 )
	{
		this.periodDataValue27 = periodDataValue27;
	}

	/**
	 * @return the periodDataValue28
	 */
	public Double getPeriodDataValue28()
	{
		return periodDataValue28;
	}

	/**
	 * @param periodDataValue28 the periodDataValue28 to set
	 */
	public void setPeriodDataValue28( Double periodDataValue28 )
	{
		this.periodDataValue28 = periodDataValue28;
	}

	/**
	 * @return the periodDataValue29
	 */
	public Double getPeriodDataValue29()
	{
		return periodDataValue29;
	}

	/**
	 * @param periodDataValue29 the periodDataValue29 to set
	 */
	public void setPeriodDataValue29( Double periodDataValue29 )
	{
		this.periodDataValue29 = periodDataValue29;
	}

	/**
	 * @return the periodDataValue30
	 */
	public Double getPeriodDataValue30()
	{
		return periodDataValue30;
	}

	/**
	 * @param periodDataValue30 the periodDataValue30 to set
	 */
	public void setPeriodDataValue30( Double periodDataValue30 )
	{
		this.periodDataValue30 = periodDataValue30;
	}

	/**
	 * @return the periodDataValue31
	 */
	public Double getPeriodDataValue31()
	{
		return periodDataValue31;
	}

	/**
	 * @param periodDataValue31 the periodDataValue31 to set
	 */
	public void setPeriodDataValue31( Double periodDataValue31 )
	{
		this.periodDataValue31 = periodDataValue31;
	}

	/**
	 * @return the periodDataValue32
	 */
	public Double getPeriodDataValue32()
	{
		return periodDataValue32;
	}

	/**
	 * @param periodDataValue32 the periodDataValue32 to set
	 */
	public void setPeriodDataValue32( Double periodDataValue32 )
	{
		this.periodDataValue32 = periodDataValue32;
	}

	/**
	 * @return the periodDataValue33
	 */
	public Double getPeriodDataValue33()
	{
		return periodDataValue33;
	}

	/**
	 * @param periodDataValue33 the periodDataValue33 to set
	 */
	public void setPeriodDataValue33( Double periodDataValue33 )
	{
		this.periodDataValue33 = periodDataValue33;
	}

	/**
	 * @return the periodDataValue34
	 */
	public Double getPeriodDataValue34()
	{
		return periodDataValue34;
	}

	/**
	 * @param periodDataValue34 the periodDataValue34 to set
	 */
	public void setPeriodDataValue34( Double periodDataValue34 )
	{
		this.periodDataValue34 = periodDataValue34;
	}

	/**
	 * @return the periodDataValue35
	 */
	public Double getPeriodDataValue35()
	{
		return periodDataValue35;
	}

	/**
	 * @param periodDataValue35 the periodDataValue35 to set
	 */
	public void setPeriodDataValue35( Double periodDataValue35 )
	{
		this.periodDataValue35 = periodDataValue35;
	}

	/**
	 * @return the periodDataValue36
	 */
	public Double getPeriodDataValue36()
	{
		return periodDataValue36;
	}

	/**
	 * @param periodDataValue36 the periodDataValue36 to set
	 */
	public void setPeriodDataValue36( Double periodDataValue36 )
	{
		this.periodDataValue36 = periodDataValue36;
	}

	/**
	 * @return the periodDataValue37
	 */
	public Double getPeriodDataValue37()
	{
		return periodDataValue37;
	}

	/**
	 * @param periodDataValue37 the periodDataValue37 to set
	 */
	public void setPeriodDataValue37( Double periodDataValue37 )
	{
		this.periodDataValue37 = periodDataValue37;
	}

	/**
	 * @return the periodDataValue38
	 */
	public Double getPeriodDataValue38()
	{
		return periodDataValue38;
	}

	/**
	 * @param periodDataValue38 the periodDataValue38 to set
	 */
	public void setPeriodDataValue38( Double periodDataValue38 )
	{
		this.periodDataValue38 = periodDataValue38;
	}

	/**
	 * @return the periodDataValue39
	 */
	public Double getPeriodDataValue39()
	{
		return periodDataValue39;
	}

	/**
	 * @param periodDataValue39 the periodDataValue39 to set
	 */
	public void setPeriodDataValue39( Double periodDataValue39 )
	{
		this.periodDataValue39 = periodDataValue39;
	}

	/**
	 * @return the periodDataValue40
	 */
	public Double getPeriodDataValue40()
	{
		return periodDataValue40;
	}

	/**
	 * @param periodDataValue40 the periodDataValue40 to set
	 */
	public void setPeriodDataValue40( Double periodDataValue40 )
	{
		this.periodDataValue40 = periodDataValue40;
	}

	/**
	 * @return the periodDataValue41
	 */
	public Double getPeriodDataValue41()
	{
		return periodDataValue41;
	}

	/**
	 * @param periodDataValue41 the periodDataValue41 to set
	 */
	public void setPeriodDataValue41( Double periodDataValue41 )
	{
		this.periodDataValue41 = periodDataValue41;
	}

	/**
	 * @return the periodDataValue42
	 */
	public Double getPeriodDataValue42()
	{
		return periodDataValue42;
	}

	/**
	 * @param periodDataValue42 the periodDataValue42 to set
	 */
	public void setPeriodDataValue42( Double periodDataValue42 )
	{
		this.periodDataValue42 = periodDataValue42;
	}

	/**
	 * @return the periodDataValue43
	 */
	public Double getPeriodDataValue43()
	{
		return periodDataValue43;
	}

	/**
	 * @param periodDataValue43 the periodDataValue43 to set
	 */
	public void setPeriodDataValue43( Double periodDataValue43 )
	{
		this.periodDataValue43 = periodDataValue43;
	}

	/**
	 * @return the periodDataValue44
	 */
	public Double getPeriodDataValue44()
	{
		return periodDataValue44;
	}

	/**
	 * @param periodDataValue44 the periodDataValue44 to set
	 */
	public void setPeriodDataValue44( Double periodDataValue44 )
	{
		this.periodDataValue44 = periodDataValue44;
	}

	/**
	 * @return the periodDataValue45
	 */
	public Double getPeriodDataValue45()
	{
		return periodDataValue45;
	}

	/**
	 * @param periodDataValue45 the periodDataValue45 to set
	 */
	public void setPeriodDataValue45( Double periodDataValue45 )
	{
		this.periodDataValue45 = periodDataValue45;
	}

	/**
	 * @return the periodDataValue46
	 */
	public Double getPeriodDataValue46()
	{
		return periodDataValue46;
	}

	/**
	 * @param periodDataValue46 the periodDataValue46 to set
	 */
	public void setPeriodDataValue46( Double periodDataValue46 )
	{
		this.periodDataValue46 = periodDataValue46;
	}

	/**
	 * @return the periodDataValue47
	 */
	public Double getPeriodDataValue47()
	{
		return periodDataValue47;
	}

	/**
	 * @param periodDataValue47 the periodDataValue47 to set
	 */
	public void setPeriodDataValue47( Double periodDataValue47 )
	{
		this.periodDataValue47 = periodDataValue47;
	}

	/**
	 * @return the periodDataValue48
	 */
	public Double getPeriodDataValue48()
	{
		return periodDataValue48;
	}

	/**
	 * @param periodDataValue48 the periodDataValue48 to set
	 */
	public void setPeriodDataValue48( Double periodDataValue48 )
	{
		this.periodDataValue48 = periodDataValue48;
	}

	/**
	 * @return the periodDataValue49
	 */
	public Double getPeriodDataValue49()
	{
		return periodDataValue49;
	}

	/**
	 * @param periodDataValue49 the periodDataValue49 to set
	 */
	public void setPeriodDataValue49( Double periodDataValue49 )
	{
		this.periodDataValue49 = periodDataValue49;
	}

	/**
	 * @return the periodDataValue50
	 */
	public Double getPeriodDataValue50()
	{
		return periodDataValue50;
	}

	/**
	 * @param periodDataValue50 the periodDataValue50 to set
	 */
	public void setPeriodDataValue50( Double periodDataValue50 )
	{
		this.periodDataValue50 = periodDataValue50;
	}

	/**
	 * @return the periodDataValue51
	 */
	public Double getPeriodDataValue51()
	{
		return periodDataValue51;
	}

	/**
	 * @param periodDataValue51 the periodDataValue51 to set
	 */
	public void setPeriodDataValue51( Double periodDataValue51 )
	{
		this.periodDataValue51 = periodDataValue51;
	}

	/**
	 * @return the periodDataValue52
	 */
	public Double getPeriodDataValue52()
	{
		return periodDataValue52;
	}

	/**
	 * @param periodDataValue52 the periodDataValue52 to set
	 */
	public void setPeriodDataValue52( Double periodDataValue52 )
	{
		this.periodDataValue52 = periodDataValue52;
	}

	/**
	 * @return the periodDataValue53
	 */
	public Double getPeriodDataValue53()
	{
		return periodDataValue53;
	}

	/**
	 * @param periodDataValue53 the periodDataValue53 to set
	 */
	public void setPeriodDataValue53( Double periodDataValue53 )
	{
		this.periodDataValue53 = periodDataValue53;
	}

	/**
	 * @return the periodDataValue54
	 */
	public Double getPeriodDataValue54()
	{
		return periodDataValue54;
	}

	/**
	 * @param periodDataValue54 the periodDataValue54 to set
	 */
	public void setPeriodDataValue54( Double periodDataValue54 )
	{
		this.periodDataValue54 = periodDataValue54;
	}

	/**
	 * @return the periodDataValue55
	 */
	public Double getPeriodDataValue55()
	{
		return periodDataValue55;
	}

	/**
	 * @param periodDataValue55 the periodDataValue55 to set
	 */
	public void setPeriodDataValue55( Double periodDataValue55 )
	{
		this.periodDataValue55 = periodDataValue55;
	}

	/**
	 * @return the periodDataValue56
	 */
	public Double getPeriodDataValue56()
	{
		return periodDataValue56;
	}

	/**
	 * @param periodDataValue56 the periodDataValue56 to set
	 */
	public void setPeriodDataValue56( Double periodDataValue56 )
	{
		this.periodDataValue56 = periodDataValue56;
	}

	/**
	 * @return the periodDataValue57
	 */
	public Double getPeriodDataValue57()
	{
		return periodDataValue57;
	}

	/**
	 * @param periodDataValue57 the periodDataValue57 to set
	 */
	public void setPeriodDataValue57( Double periodDataValue57 )
	{
		this.periodDataValue57 = periodDataValue57;
	}

	/**
	 * @return the periodDataValue58
	 */
	public Double getPeriodDataValue58()
	{
		return periodDataValue58;
	}

	/**
	 * @param periodDataValue58 the periodDataValue58 to set
	 */
	public void setPeriodDataValue58( Double periodDataValue58 )
	{
		this.periodDataValue58 = periodDataValue58;
	}

	/**
	 * @return the periodDataValue59
	 */
	public Double getPeriodDataValue59()
	{
		return periodDataValue59;
	}

	/**
	 * @param periodDataValue59 the periodDataValue59 to set
	 */
	public void setPeriodDataValue59( Double periodDataValue59 )
	{
		this.periodDataValue59 = periodDataValue59;
	}

	/**
	 * @return the periodDataValue00Bak
	 */
	public Double getPeriodDataValue00Bak()
	{
		return periodDataValue00Bak;
	}

	/**
	 * @param periodDataValue00Bak the periodDataValue00Bak to set
	 */
	public void setPeriodDataValue00Bak( Double periodDataValue00Bak )
	{
		this.periodDataValue00Bak = periodDataValue00Bak;
	}

	/**
	 * @return the periodDataValue01Bak
	 */
	public Double getPeriodDataValue01Bak()
	{
		return periodDataValue01Bak;
	}

	/**
	 * @param periodDataValue01Bak the periodDataValue01Bak to set
	 */
	public void setPeriodDataValue01Bak( Double periodDataValue01Bak )
	{
		this.periodDataValue01Bak = periodDataValue01Bak;
	}

	/**
	 * @return the periodDataValue02Bak
	 */
	public Double getPeriodDataValue02Bak()
	{
		return periodDataValue02Bak;
	}

	/**
	 * @param periodDataValue02Bak the periodDataValue02Bak to set
	 */
	public void setPeriodDataValue02Bak( Double periodDataValue02Bak )
	{
		this.periodDataValue02Bak = periodDataValue02Bak;
	}

	/**
	 * @return the periodDataValue03Bak
	 */
	public Double getPeriodDataValue03Bak()
	{
		return periodDataValue03Bak;
	}

	/**
	 * @param periodDataValue03Bak the periodDataValue03Bak to set
	 */
	public void setPeriodDataValue03Bak( Double periodDataValue03Bak )
	{
		this.periodDataValue03Bak = periodDataValue03Bak;
	}

	/**
	 * @return the periodDataValue04Bak
	 */
	public Double getPeriodDataValue04Bak()
	{
		return periodDataValue04Bak;
	}

	/**
	 * @param periodDataValue04Bak the periodDataValue04Bak to set
	 */
	public void setPeriodDataValue04Bak( Double periodDataValue04Bak )
	{
		this.periodDataValue04Bak = periodDataValue04Bak;
	}

	/**
	 * @return the periodDataValue05Bak
	 */
	public Double getPeriodDataValue05Bak()
	{
		return periodDataValue05Bak;
	}

	/**
	 * @param periodDataValue05Bak the periodDataValue05Bak to set
	 */
	public void setPeriodDataValue05Bak( Double periodDataValue05Bak )
	{
		this.periodDataValue05Bak = periodDataValue05Bak;
	}

	/**
	 * @return the periodDataValue06Bak
	 */
	public Double getPeriodDataValue06Bak()
	{
		return periodDataValue06Bak;
	}

	/**
	 * @param periodDataValue06Bak the periodDataValue06Bak to set
	 */
	public void setPeriodDataValue06Bak( Double periodDataValue06Bak )
	{
		this.periodDataValue06Bak = periodDataValue06Bak;
	}

	/**
	 * @return the periodDataValue07Bak
	 */
	public Double getPeriodDataValue07Bak()
	{
		return periodDataValue07Bak;
	}

	/**
	 * @param periodDataValue07Bak the periodDataValue07Bak to set
	 */
	public void setPeriodDataValue07Bak( Double periodDataValue07Bak )
	{
		this.periodDataValue07Bak = periodDataValue07Bak;
	}

	/**
	 * @return the periodDataValue08Bak
	 */
	public Double getPeriodDataValue08Bak()
	{
		return periodDataValue08Bak;
	}

	/**
	 * @param periodDataValue08Bak the periodDataValue08Bak to set
	 */
	public void setPeriodDataValue08Bak( Double periodDataValue08Bak )
	{
		this.periodDataValue08Bak = periodDataValue08Bak;
	}

	/**
	 * @return the periodDataValue09Bak
	 */
	public Double getPeriodDataValue09Bak()
	{
		return periodDataValue09Bak;
	}

	/**
	 * @param periodDataValue09Bak the periodDataValue09Bak to set
	 */
	public void setPeriodDataValue09Bak( Double periodDataValue09Bak )
	{
		this.periodDataValue09Bak = periodDataValue09Bak;
	}

	/**
	 * @return the periodDataValue10Bak
	 */
	public Double getPeriodDataValue10Bak()
	{
		return periodDataValue10Bak;
	}

	/**
	 * @param periodDataValue10Bak the periodDataValue10Bak to set
	 */
	public void setPeriodDataValue10Bak( Double periodDataValue10Bak )
	{
		this.periodDataValue10Bak = periodDataValue10Bak;
	}

	/**
	 * @return the periodDataValue11Bak
	 */
	public Double getPeriodDataValue11Bak()
	{
		return periodDataValue11Bak;
	}

	/**
	 * @param periodDataValue11Bak the periodDataValue11Bak to set
	 */
	public void setPeriodDataValue11Bak( Double periodDataValue11Bak )
	{
		this.periodDataValue11Bak = periodDataValue11Bak;
	}

	/**
	 * @return the periodDataValue12Bak
	 */
	public Double getPeriodDataValue12Bak()
	{
		return periodDataValue12Bak;
	}

	/**
	 * @param periodDataValue12Bak the periodDataValue12Bak to set
	 */
	public void setPeriodDataValue12Bak( Double periodDataValue12Bak )
	{
		this.periodDataValue12Bak = periodDataValue12Bak;
	}

	/**
	 * @return the periodDataValue13Bak
	 */
	public Double getPeriodDataValue13Bak()
	{
		return periodDataValue13Bak;
	}

	/**
	 * @param periodDataValue13Bak the periodDataValue13Bak to set
	 */
	public void setPeriodDataValue13Bak( Double periodDataValue13Bak )
	{
		this.periodDataValue13Bak = periodDataValue13Bak;
	}

	/**
	 * @return the periodDataValue14Bak
	 */
	public Double getPeriodDataValue14Bak()
	{
		return periodDataValue14Bak;
	}

	/**
	 * @param periodDataValue14Bak the periodDataValue14Bak to set
	 */
	public void setPeriodDataValue14Bak( Double periodDataValue14Bak )
	{
		this.periodDataValue14Bak = periodDataValue14Bak;
	}

	/**
	 * @return the periodDataValue15Bak
	 */
	public Double getPeriodDataValue15Bak()
	{
		return periodDataValue15Bak;
	}

	/**
	 * @param periodDataValue15Bak the periodDataValue15Bak to set
	 */
	public void setPeriodDataValue15Bak( Double periodDataValue15Bak )
	{
		this.periodDataValue15Bak = periodDataValue15Bak;
	}

	/**
	 * @return the periodDataValue16Bak
	 */
	public Double getPeriodDataValue16Bak()
	{
		return periodDataValue16Bak;
	}

	/**
	 * @param periodDataValue16Bak the periodDataValue16Bak to set
	 */
	public void setPeriodDataValue16Bak( Double periodDataValue16Bak )
	{
		this.periodDataValue16Bak = periodDataValue16Bak;
	}

	/**
	 * @return the periodDataValue17Bak
	 */
	public Double getPeriodDataValue17Bak()
	{
		return periodDataValue17Bak;
	}

	/**
	 * @param periodDataValue17Bak the periodDataValue17Bak to set
	 */
	public void setPeriodDataValue17Bak( Double periodDataValue17Bak )
	{
		this.periodDataValue17Bak = periodDataValue17Bak;
	}

	/**
	 * @return the periodDataValue18Bak
	 */
	public Double getPeriodDataValue18Bak()
	{
		return periodDataValue18Bak;
	}

	/**
	 * @param periodDataValue18Bak the periodDataValue18Bak to set
	 */
	public void setPeriodDataValue18Bak( Double periodDataValue18Bak )
	{
		this.periodDataValue18Bak = periodDataValue18Bak;
	}

	/**
	 * @return the periodDataValue19Bak
	 */
	public Double getPeriodDataValue19Bak()
	{
		return periodDataValue19Bak;
	}

	/**
	 * @param periodDataValue19Bak the periodDataValue19Bak to set
	 */
	public void setPeriodDataValue19Bak( Double periodDataValue19Bak )
	{
		this.periodDataValue19Bak = periodDataValue19Bak;
	}

	/**
	 * @return the periodDataValue20Bak
	 */
	public Double getPeriodDataValue20Bak()
	{
		return periodDataValue20Bak;
	}

	/**
	 * @param periodDataValue20Bak the periodDataValue20Bak to set
	 */
	public void setPeriodDataValue20Bak( Double periodDataValue20Bak )
	{
		this.periodDataValue20Bak = periodDataValue20Bak;
	}

	/**
	 * @return the periodDataValue21Bak
	 */
	public Double getPeriodDataValue21Bak()
	{
		return periodDataValue21Bak;
	}

	/**
	 * @param periodDataValue21Bak the periodDataValue21Bak to set
	 */
	public void setPeriodDataValue21Bak( Double periodDataValue21Bak )
	{
		this.periodDataValue21Bak = periodDataValue21Bak;
	}

	/**
	 * @return the periodDataValue22Bak
	 */
	public Double getPeriodDataValue22Bak()
	{
		return periodDataValue22Bak;
	}

	/**
	 * @param periodDataValue22Bak the periodDataValue22Bak to set
	 */
	public void setPeriodDataValue22Bak( Double periodDataValue22Bak )
	{
		this.periodDataValue22Bak = periodDataValue22Bak;
	}

	/**
	 * @return the periodDataValue23Bak
	 */
	public Double getPeriodDataValue23Bak()
	{
		return periodDataValue23Bak;
	}

	/**
	 * @param periodDataValue23Bak the periodDataValue23Bak to set
	 */
	public void setPeriodDataValue23Bak( Double periodDataValue23Bak )
	{
		this.periodDataValue23Bak = periodDataValue23Bak;
	}

	/**
	 * @return the periodDataValue24Bak
	 */
	public Double getPeriodDataValue24Bak()
	{
		return periodDataValue24Bak;
	}

	/**
	 * @param periodDataValue24Bak the periodDataValue24Bak to set
	 */
	public void setPeriodDataValue24Bak( Double periodDataValue24Bak )
	{
		this.periodDataValue24Bak = periodDataValue24Bak;
	}

	/**
	 * @return the periodDataValue25Bak
	 */
	public Double getPeriodDataValue25Bak()
	{
		return periodDataValue25Bak;
	}

	/**
	 * @param periodDataValue25Bak the periodDataValue25Bak to set
	 */
	public void setPeriodDataValue25Bak( Double periodDataValue25Bak )
	{
		this.periodDataValue25Bak = periodDataValue25Bak;
	}

	/**
	 * @return the periodDataValue26Bak
	 */
	public Double getPeriodDataValue26Bak()
	{
		return periodDataValue26Bak;
	}

	/**
	 * @param periodDataValue26Bak the periodDataValue26Bak to set
	 */
	public void setPeriodDataValue26Bak( Double periodDataValue26Bak )
	{
		this.periodDataValue26Bak = periodDataValue26Bak;
	}

	/**
	 * @return the periodDataValue27Bak
	 */
	public Double getPeriodDataValue27Bak()
	{
		return periodDataValue27Bak;
	}

	/**
	 * @param periodDataValue27Bak the periodDataValue27Bak to set
	 */
	public void setPeriodDataValue27Bak( Double periodDataValue27Bak )
	{
		this.periodDataValue27Bak = periodDataValue27Bak;
	}

	/**
	 * @return the periodDataValue28Bak
	 */
	public Double getPeriodDataValue28Bak()
	{
		return periodDataValue28Bak;
	}

	/**
	 * @param periodDataValue28Bak the periodDataValue28Bak to set
	 */
	public void setPeriodDataValue28Bak( Double periodDataValue28Bak )
	{
		this.periodDataValue28Bak = periodDataValue28Bak;
	}

	/**
	 * @return the periodDataValue29Bak
	 */
	public Double getPeriodDataValue29Bak()
	{
		return periodDataValue29Bak;
	}

	/**
	 * @param periodDataValue29Bak the periodDataValue29Bak to set
	 */
	public void setPeriodDataValue29Bak( Double periodDataValue29Bak )
	{
		this.periodDataValue29Bak = periodDataValue29Bak;
	}

	/**
	 * @return the periodDataValue30Bak
	 */
	public Double getPeriodDataValue30Bak()
	{
		return periodDataValue30Bak;
	}

	/**
	 * @param periodDataValue30Bak the periodDataValue30Bak to set
	 */
	public void setPeriodDataValue30Bak( Double periodDataValue30Bak )
	{
		this.periodDataValue30Bak = periodDataValue30Bak;
	}

	/**
	 * @return the periodDataValue31Bak
	 */
	public Double getPeriodDataValue31Bak()
	{
		return periodDataValue31Bak;
	}

	/**
	 * @param periodDataValue31Bak the periodDataValue31Bak to set
	 */
	public void setPeriodDataValue31Bak( Double periodDataValue31Bak )
	{
		this.periodDataValue31Bak = periodDataValue31Bak;
	}

	/**
	 * @return the periodDataValue32Bak
	 */
	public Double getPeriodDataValue32Bak()
	{
		return periodDataValue32Bak;
	}

	/**
	 * @param periodDataValue32Bak the periodDataValue32Bak to set
	 */
	public void setPeriodDataValue32Bak( Double periodDataValue32Bak )
	{
		this.periodDataValue32Bak = periodDataValue32Bak;
	}

	/**
	 * @return the periodDataValue33Bak
	 */
	public Double getPeriodDataValue33Bak()
	{
		return periodDataValue33Bak;
	}

	/**
	 * @param periodDataValue33Bak the periodDataValue33Bak to set
	 */
	public void setPeriodDataValue33Bak( Double periodDataValue33Bak )
	{
		this.periodDataValue33Bak = periodDataValue33Bak;
	}

	/**
	 * @return the periodDataValue34Bak
	 */
	public Double getPeriodDataValue34Bak()
	{
		return periodDataValue34Bak;
	}

	/**
	 * @param periodDataValue34Bak the periodDataValue34Bak to set
	 */
	public void setPeriodDataValue34Bak( Double periodDataValue34Bak )
	{
		this.periodDataValue34Bak = periodDataValue34Bak;
	}

	/**
	 * @return the periodDataValue35Bak
	 */
	public Double getPeriodDataValue35Bak()
	{
		return periodDataValue35Bak;
	}

	/**
	 * @param periodDataValue35Bak the periodDataValue35Bak to set
	 */
	public void setPeriodDataValue35Bak( Double periodDataValue35Bak )
	{
		this.periodDataValue35Bak = periodDataValue35Bak;
	}

	/**
	 * @return the periodDataValue36Bak
	 */
	public Double getPeriodDataValue36Bak()
	{
		return periodDataValue36Bak;
	}

	/**
	 * @param periodDataValue36Bak the periodDataValue36Bak to set
	 */
	public void setPeriodDataValue36Bak( Double periodDataValue36Bak )
	{
		this.periodDataValue36Bak = periodDataValue36Bak;
	}

	/**
	 * @return the periodDataValue37Bak
	 */
	public Double getPeriodDataValue37Bak()
	{
		return periodDataValue37Bak;
	}

	/**
	 * @param periodDataValue37Bak the periodDataValue37Bak to set
	 */
	public void setPeriodDataValue37Bak( Double periodDataValue37Bak )
	{
		this.periodDataValue37Bak = periodDataValue37Bak;
	}

	/**
	 * @return the periodDataValue38Bak
	 */
	public Double getPeriodDataValue38Bak()
	{
		return periodDataValue38Bak;
	}

	/**
	 * @param periodDataValue38Bak the periodDataValue38Bak to set
	 */
	public void setPeriodDataValue38Bak( Double periodDataValue38Bak )
	{
		this.periodDataValue38Bak = periodDataValue38Bak;
	}

	/**
	 * @return the periodDataValue39Bak
	 */
	public Double getPeriodDataValue39Bak()
	{
		return periodDataValue39Bak;
	}

	/**
	 * @param periodDataValue39Bak the periodDataValue39Bak to set
	 */
	public void setPeriodDataValue39Bak( Double periodDataValue39Bak )
	{
		this.periodDataValue39Bak = periodDataValue39Bak;
	}

	/**
	 * @return the periodDataValue40Bak
	 */
	public Double getPeriodDataValue40Bak()
	{
		return periodDataValue40Bak;
	}

	/**
	 * @param periodDataValue40Bak the periodDataValue40Bak to set
	 */
	public void setPeriodDataValue40Bak( Double periodDataValue40Bak )
	{
		this.periodDataValue40Bak = periodDataValue40Bak;
	}

	/**
	 * @return the periodDataValue41Bak
	 */
	public Double getPeriodDataValue41Bak()
	{
		return periodDataValue41Bak;
	}

	/**
	 * @param periodDataValue41Bak the periodDataValue41Bak to set
	 */
	public void setPeriodDataValue41Bak( Double periodDataValue41Bak )
	{
		this.periodDataValue41Bak = periodDataValue41Bak;
	}

	/**
	 * @return the periodDataValue42Bak
	 */
	public Double getPeriodDataValue42Bak()
	{
		return periodDataValue42Bak;
	}

	/**
	 * @param periodDataValue42Bak the periodDataValue42Bak to set
	 */
	public void setPeriodDataValue42Bak( Double periodDataValue42Bak )
	{
		this.periodDataValue42Bak = periodDataValue42Bak;
	}

	/**
	 * @return the periodDataValue43Bak
	 */
	public Double getPeriodDataValue43Bak()
	{
		return periodDataValue43Bak;
	}

	/**
	 * @param periodDataValue43Bak the periodDataValue43Bak to set
	 */
	public void setPeriodDataValue43Bak( Double periodDataValue43Bak )
	{
		this.periodDataValue43Bak = periodDataValue43Bak;
	}

	/**
	 * @return the periodDataValue44Bak
	 */
	public Double getPeriodDataValue44Bak()
	{
		return periodDataValue44Bak;
	}

	/**
	 * @param periodDataValue44Bak the periodDataValue44Bak to set
	 */
	public void setPeriodDataValue44Bak( Double periodDataValue44Bak )
	{
		this.periodDataValue44Bak = periodDataValue44Bak;
	}

	/**
	 * @return the periodDataValue45Bak
	 */
	public Double getPeriodDataValue45Bak()
	{
		return periodDataValue45Bak;
	}

	/**
	 * @param periodDataValue45Bak the periodDataValue45Bak to set
	 */
	public void setPeriodDataValue45Bak( Double periodDataValue45Bak )
	{
		this.periodDataValue45Bak = periodDataValue45Bak;
	}

	/**
	 * @return the periodDataValue46Bak
	 */
	public Double getPeriodDataValue46Bak()
	{
		return periodDataValue46Bak;
	}

	/**
	 * @param periodDataValue46Bak the periodDataValue46Bak to set
	 */
	public void setPeriodDataValue46Bak( Double periodDataValue46Bak )
	{
		this.periodDataValue46Bak = periodDataValue46Bak;
	}

	/**
	 * @return the periodDataValue47Bak
	 */
	public Double getPeriodDataValue47Bak()
	{
		return periodDataValue47Bak;
	}

	/**
	 * @param periodDataValue47Bak the periodDataValue47Bak to set
	 */
	public void setPeriodDataValue47Bak( Double periodDataValue47Bak )
	{
		this.periodDataValue47Bak = periodDataValue47Bak;
	}

	/**
	 * @return the periodDataValue48Bak
	 */
	public Double getPeriodDataValue48Bak()
	{
		return periodDataValue48Bak;
	}

	/**
	 * @param periodDataValue48Bak the periodDataValue48Bak to set
	 */
	public void setPeriodDataValue48Bak( Double periodDataValue48Bak )
	{
		this.periodDataValue48Bak = periodDataValue48Bak;
	}

	/**
	 * @return the periodDataValue49Bak
	 */
	public Double getPeriodDataValue49Bak()
	{
		return periodDataValue49Bak;
	}

	/**
	 * @param periodDataValue49Bak the periodDataValue49Bak to set
	 */
	public void setPeriodDataValue49Bak( Double periodDataValue49Bak )
	{
		this.periodDataValue49Bak = periodDataValue49Bak;
	}

	/**
	 * @return the periodDataValue50Bak
	 */
	public Double getPeriodDataValue50Bak()
	{
		return periodDataValue50Bak;
	}

	/**
	 * @param periodDataValue50Bak the periodDataValue50Bak to set
	 */
	public void setPeriodDataValue50Bak( Double periodDataValue50Bak )
	{
		this.periodDataValue50Bak = periodDataValue50Bak;
	}

	/**
	 * @return the periodDataValue51Bak
	 */
	public Double getPeriodDataValue51Bak()
	{
		return periodDataValue51Bak;
	}

	/**
	 * @param periodDataValue51Bak the periodDataValue51Bak to set
	 */
	public void setPeriodDataValue51Bak( Double periodDataValue51Bak )
	{
		this.periodDataValue51Bak = periodDataValue51Bak;
	}

	/**
	 * @return the periodDataValue52Bak
	 */
	public Double getPeriodDataValue52Bak()
	{
		return periodDataValue52Bak;
	}

	/**
	 * @param periodDataValue52Bak the periodDataValue52Bak to set
	 */
	public void setPeriodDataValue52Bak( Double periodDataValue52Bak )
	{
		this.periodDataValue52Bak = periodDataValue52Bak;
	}

	/**
	 * @return the periodDataValue53Bak
	 */
	public Double getPeriodDataValue53Bak()
	{
		return periodDataValue53Bak;
	}

	/**
	 * @param periodDataValue53Bak the periodDataValue53Bak to set
	 */
	public void setPeriodDataValue53Bak( Double periodDataValue53Bak )
	{
		this.periodDataValue53Bak = periodDataValue53Bak;
	}

	/**
	 * @return the periodDataValue54Bak
	 */
	public Double getPeriodDataValue54Bak()
	{
		return periodDataValue54Bak;
	}

	/**
	 * @param periodDataValue54Bak the periodDataValue54Bak to set
	 */
	public void setPeriodDataValue54Bak( Double periodDataValue54Bak )
	{
		this.periodDataValue54Bak = periodDataValue54Bak;
	}

	/**
	 * @return the periodDataValue55Bak
	 */
	public Double getPeriodDataValue55Bak()
	{
		return periodDataValue55Bak;
	}

	/**
	 * @param periodDataValue55Bak the periodDataValue55Bak to set
	 */
	public void setPeriodDataValue55Bak( Double periodDataValue55Bak )
	{
		this.periodDataValue55Bak = periodDataValue55Bak;
	}

	/**
	 * @return the periodDataValue56Bak
	 */
	public Double getPeriodDataValue56Bak()
	{
		return periodDataValue56Bak;
	}

	/**
	 * @param periodDataValue56Bak the periodDataValue56Bak to set
	 */
	public void setPeriodDataValue56Bak( Double periodDataValue56Bak )
	{
		this.periodDataValue56Bak = periodDataValue56Bak;
	}

	/**
	 * @return the periodDataValue57Bak
	 */
	public Double getPeriodDataValue57Bak()
	{
		return periodDataValue57Bak;
	}

	/**
	 * @param periodDataValue57Bak the periodDataValue57Bak to set
	 */
	public void setPeriodDataValue57Bak( Double periodDataValue57Bak )
	{
		this.periodDataValue57Bak = periodDataValue57Bak;
	}

	/**
	 * @return the periodDataValue58Bak
	 */
	public Double getPeriodDataValue58Bak()
	{
		return periodDataValue58Bak;
	}

	/**
	 * @param periodDataValue58Bak the periodDataValue58Bak to set
	 */
	public void setPeriodDataValue58Bak( Double periodDataValue58Bak )
	{
		this.periodDataValue58Bak = periodDataValue58Bak;
	}

	/**
	 * @return the periodDataValue59Bak
	 */
	public Double getPeriodDataValue59Bak()
	{
		return periodDataValue59Bak;
	}

	/**
	 * @param periodDataValue59Bak the periodDataValue59Bak to set
	 */
	public void setPeriodDataValue59Bak( Double periodDataValue59Bak )
	{
		this.periodDataValue59Bak = periodDataValue59Bak;
	}



}
