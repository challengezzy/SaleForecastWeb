/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.SysConst;

public class ABImReplenishStockDays implements Serializable
{
	
	public final static long serialVersionUID = -1010000021;
	
	private String productCode = null;
	private String dcCode = null;
	private int   periodBegin = SysConst.PERIOD_NULL;
	private int   periodEnd = SysConst.PERIOD_NULL;
	
	private String importResult = null;
	
	private Long periodDataValue00 = null;
	private Long periodDataValue01 = null;
	private Long periodDataValue02 = null;
	private Long periodDataValue03 = null;
	private Long periodDataValue04 = null;
	private Long periodDataValue05 = null;
	private Long periodDataValue06 = null;
	private Long periodDataValue07 = null;
	private Long periodDataValue08 = null;
	private Long periodDataValue09 = null;
	private Long periodDataValue10 = null;
	private Long periodDataValue11 = null;
	private Long periodDataValue12 = null;
	private Long periodDataValue13 = null;
	private Long periodDataValue14 = null;
	private Long periodDataValue15 = null;
	private Long periodDataValue16 = null;
	private Long periodDataValue17 = null;
	private Long periodDataValue18 = null;
	private Long periodDataValue19 = null;
	private Long periodDataValue20 = null;
	private Long periodDataValue21 = null;
	private Long periodDataValue22 = null;
	private Long periodDataValue23 = null;		
	private Long periodDataValue24 = null;
	private Long periodDataValue25 = null;
	private Long periodDataValue26 = null;
	private Long periodDataValue27 = null;
	private Long periodDataValue28 = null;
	private Long periodDataValue29 = null;	
	
	private Long periodDataValue30 = null;
	private Long periodDataValue31 = null;
	private Long periodDataValue32 = null;
	private Long periodDataValue33 = null;		
	private Long periodDataValue34 = null;
	private Long periodDataValue35 = null;
	private Long periodDataValue36 = null;
	private Long periodDataValue37 = null;
	private Long periodDataValue38 = null;
	private Long periodDataValue39 = null;	
	private Long periodDataValue40 = null;
	private Long periodDataValue41 = null;
	private Long periodDataValue42 = null;
	private Long periodDataValue43 = null;		
	private Long periodDataValue44 = null;
	private Long periodDataValue45 = null;
	private Long periodDataValue46 = null;
	private Long periodDataValue47 = null;
	private Long periodDataValue48 = null;
	private Long periodDataValue49 = null;	
	private Long periodDataValue50 = null;
	private Long periodDataValue51 = null;
	private Long periodDataValue52 = null;
	private Long periodDataValue53 = null;		
	private Long periodDataValue54 = null;
	private Long periodDataValue55 = null;
	private Long periodDataValue56 = null;
	private Long periodDataValue57 = null;
	private Long periodDataValue58 = null;
	private Long periodDataValue59 = null;
	/**
	 * 
	 */
	public ABImReplenishStockDays() {
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
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode( String productCode )
	{
		this.productCode = productCode;
	}

	

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
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


	public Long pubFun4getPeriodDataValue( int ith )
	{
		switch( ith )
		{
			case   0 : return this.periodDataValue00;
			case   1 : return this.periodDataValue01;
			case   2 : return this.periodDataValue02;
			case   3 : return this.periodDataValue03;
			case   4 : return this.periodDataValue04;
			case   5 : return this.periodDataValue05;
			case   6 : return this.periodDataValue06;
			case   7 : return this.periodDataValue07;
			case   8 : return this.periodDataValue08;
			case   9 : return this.periodDataValue09;
			case  10 : return this.periodDataValue10;
			case  11 : return this.periodDataValue11;
			case  12 : return this.periodDataValue12;
			case  13 : return this.periodDataValue13;
			case  14 : return this.periodDataValue14;
			case  15 : return this.periodDataValue15;
			case  16 : return this.periodDataValue16;
			case  17 : return this.periodDataValue17;
			case  18 : return this.periodDataValue18;
			case  19 : return this.periodDataValue19;
			case  20 : return this.periodDataValue20;
			case  21 : return this.periodDataValue21;
			case  22 : return this.periodDataValue22;
			case  23 : return this.periodDataValue23;
			case  24 : return this.periodDataValue24;
			case  25 : return this.periodDataValue25;
			case  26 : return this.periodDataValue26;
			case  27 : return this.periodDataValue27;
			case  28 : return this.periodDataValue28;
			case  29 : return this.periodDataValue29;	
			
			case  30 : return this.periodDataValue30;
			case  31 : return this.periodDataValue31;
			case  32 : return this.periodDataValue32;
			case  33 : return this.periodDataValue33;
			case  34 : return this.periodDataValue34;
			case  35 : return this.periodDataValue35;
			case  36 : return this.periodDataValue36;
			case  37 : return this.periodDataValue37;
			case  38 : return this.periodDataValue38;
			case  39 : return this.periodDataValue39;
			
			case  40 : return this.periodDataValue40;
			case  41 : return this.periodDataValue41;
			case  42 : return this.periodDataValue42;
			case  43 : return this.periodDataValue43;
			case  44 : return this.periodDataValue44;
			case  45 : return this.periodDataValue45;
			case  46 : return this.periodDataValue46;
			case  47 : return this.periodDataValue47;
			case  48 : return this.periodDataValue48;
			case  49 : return this.periodDataValue49;
			
			case  50 : return this.periodDataValue50;
			case  51 : return this.periodDataValue51;
			case  52 : return this.periodDataValue52;
			case  53 : return this.periodDataValue53;
			case  54 : return this.periodDataValue54;
			case  55 : return this.periodDataValue55;
			case  56 : return this.periodDataValue56;
			case  57 : return this.periodDataValue57;
			case  58 : return this.periodDataValue58;
			case  59 : return this.periodDataValue59;									
		}
		return 0L;
	}


	/**
	 * @return the periodDataValue00
	 */
	public Long getPeriodDataValue00()
	{
		return periodDataValue00;
	}

	/**
	 * @param periodDataValue00 the periodDataValue00 to set
	 */
	public void setPeriodDataValue00( Long periodDataValue00 )
	{
		this.periodDataValue00 = periodDataValue00;
	}

	/**
	 * @return the periodDataValue01
	 */
	public Long getPeriodDataValue01()
	{
		return periodDataValue01;
	}

	/**
	 * @param periodDataValue01 the periodDataValue01 to set
	 */
	public void setPeriodDataValue01( Long periodDataValue01 )
	{
		this.periodDataValue01 = periodDataValue01;
	}

	/**
	 * @return the periodDataValue02
	 */
	public Long getPeriodDataValue02()
	{
		return periodDataValue02;
	}

	/**
	 * @param periodDataValue02 the periodDataValue02 to set
	 */
	public void setPeriodDataValue02( Long periodDataValue02 )
	{
		this.periodDataValue02 = periodDataValue02;
	}

	/**
	 * @return the periodDataValue03
	 */
	public Long getPeriodDataValue03()
	{
		return periodDataValue03;
	}

	/**
	 * @param periodDataValue03 the periodDataValue03 to set
	 */
	public void setPeriodDataValue03( Long periodDataValue03 )
	{
		this.periodDataValue03 = periodDataValue03;
	}

	/**
	 * @return the periodDataValue04
	 */
	public Long getPeriodDataValue04()
	{
		return periodDataValue04;
	}

	/**
	 * @param periodDataValue04 the periodDataValue04 to set
	 */
	public void setPeriodDataValue04( Long periodDataValue04 )
	{
		this.periodDataValue04 = periodDataValue04;
	}

	/**
	 * @return the periodDataValue05
	 */
	public Long getPeriodDataValue05()
	{
		return periodDataValue05;
	}

	/**
	 * @param periodDataValue05 the periodDataValue05 to set
	 */
	public void setPeriodDataValue05( Long periodDataValue05 )
	{
		this.periodDataValue05 = periodDataValue05;
	}

	/**
	 * @return the periodDataValue06
	 */
	public Long getPeriodDataValue06()
	{
		return periodDataValue06;
	}

	/**
	 * @param periodDataValue06 the periodDataValue06 to set
	 */
	public void setPeriodDataValue06( Long periodDataValue06 )
	{
		this.periodDataValue06 = periodDataValue06;
	}

	/**
	 * @return the periodDataValue07
	 */
	public Long getPeriodDataValue07()
	{
		return periodDataValue07;
	}

	/**
	 * @param periodDataValue07 the periodDataValue07 to set
	 */
	public void setPeriodDataValue07( Long periodDataValue07 )
	{
		this.periodDataValue07 = periodDataValue07;
	}

	/**
	 * @return the periodDataValue08
	 */
	public Long getPeriodDataValue08()
	{
		return periodDataValue08;
	}

	/**
	 * @param periodDataValue08 the periodDataValue08 to set
	 */
	public void setPeriodDataValue08( Long periodDataValue08 )
	{
		this.periodDataValue08 = periodDataValue08;
	}

	/**
	 * @return the periodDataValue09
	 */
	public Long getPeriodDataValue09()
	{
		return periodDataValue09;
	}

	/**
	 * @param periodDataValue09 the periodDataValue09 to set
	 */
	public void setPeriodDataValue09( Long periodDataValue09 )
	{
		this.periodDataValue09 = periodDataValue09;
	}

	/**
	 * @return the periodDataValue10
	 */
	public Long getPeriodDataValue10()
	{
		return periodDataValue10;
	}

	/**
	 * @param periodDataValue10 the periodDataValue10 to set
	 */
	public void setPeriodDataValue10( Long periodDataValue10 )
	{
		this.periodDataValue10 = periodDataValue10;
	}

	/**
	 * @return the periodDataValue11
	 */
	public Long getPeriodDataValue11()
	{
		return periodDataValue11;
	}

	/**
	 * @param periodDataValue11 the periodDataValue11 to set
	 */
	public void setPeriodDataValue11( Long periodDataValue11 )
	{
		this.periodDataValue11 = periodDataValue11;
	}

	/**
	 * @return the periodDataValue12
	 */
	public Long getPeriodDataValue12()
	{
		return periodDataValue12;
	}

	/**
	 * @param periodDataValue12 the periodDataValue12 to set
	 */
	public void setPeriodDataValue12( Long periodDataValue12 )
	{
		this.periodDataValue12 = periodDataValue12;
	}

	/**
	 * @return the periodDataValue13
	 */
	public Long getPeriodDataValue13()
	{
		return periodDataValue13;
	}

	/**
	 * @param periodDataValue13 the periodDataValue13 to set
	 */
	public void setPeriodDataValue13( Long periodDataValue13 )
	{
		this.periodDataValue13 = periodDataValue13;
	}

	/**
	 * @return the periodDataValue14
	 */
	public Long getPeriodDataValue14()
	{
		return periodDataValue14;
	}

	/**
	 * @param periodDataValue14 the periodDataValue14 to set
	 */
	public void setPeriodDataValue14( Long periodDataValue14 )
	{
		this.periodDataValue14 = periodDataValue14;
	}

	/**
	 * @return the periodDataValue15
	 */
	public Long getPeriodDataValue15()
	{
		return periodDataValue15;
	}

	/**
	 * @param periodDataValue15 the periodDataValue15 to set
	 */
	public void setPeriodDataValue15( Long periodDataValue15 )
	{
		this.periodDataValue15 = periodDataValue15;
	}

	/**
	 * @return the periodDataValue16
	 */
	public Long getPeriodDataValue16()
	{
		return periodDataValue16;
	}

	/**
	 * @param periodDataValue16 the periodDataValue16 to set
	 */
	public void setPeriodDataValue16( Long periodDataValue16 )
	{
		this.periodDataValue16 = periodDataValue16;
	}

	/**
	 * @return the periodDataValue17
	 */
	public Long getPeriodDataValue17()
	{
		return periodDataValue17;
	}

	/**
	 * @param periodDataValue17 the periodDataValue17 to set
	 */
	public void setPeriodDataValue17( Long periodDataValue17 )
	{
		this.periodDataValue17 = periodDataValue17;
	}

	/**
	 * @return the periodDataValue18
	 */
	public Long getPeriodDataValue18()
	{
		return periodDataValue18;
	}

	/**
	 * @param periodDataValue18 the periodDataValue18 to set
	 */
	public void setPeriodDataValue18( Long periodDataValue18 )
	{
		this.periodDataValue18 = periodDataValue18;
	}

	/**
	 * @return the periodDataValue19
	 */
	public Long getPeriodDataValue19()
	{
		return periodDataValue19;
	}

	/**
	 * @param periodDataValue19 the periodDataValue19 to set
	 */
	public void setPeriodDataValue19( Long periodDataValue19 )
	{
		this.periodDataValue19 = periodDataValue19;
	}

	/**
	 * @return the periodDataValue20
	 */
	public Long getPeriodDataValue20()
	{
		return periodDataValue20;
	}

	/**
	 * @param periodDataValue20 the periodDataValue20 to set
	 */
	public void setPeriodDataValue20( Long periodDataValue20 )
	{
		this.periodDataValue20 = periodDataValue20;
	}

	/**
	 * @return the periodDataValue21
	 */
	public Long getPeriodDataValue21()
	{
		return periodDataValue21;
	}

	/**
	 * @param periodDataValue21 the periodDataValue21 to set
	 */
	public void setPeriodDataValue21( Long periodDataValue21 )
	{
		this.periodDataValue21 = periodDataValue21;
	}

	/**
	 * @return the periodDataValue22
	 */
	public Long getPeriodDataValue22()
	{
		return periodDataValue22;
	}

	/**
	 * @param periodDataValue22 the periodDataValue22 to set
	 */
	public void setPeriodDataValue22( Long periodDataValue22 )
	{
		this.periodDataValue22 = periodDataValue22;
	}

	/**
	 * @return the periodDataValue23
	 */
	public Long getPeriodDataValue23()
	{
		return periodDataValue23;
	}

	/**
	 * @param periodDataValue23 the periodDataValue23 to set
	 */
	public void setPeriodDataValue23( Long periodDataValue23 )
	{
		this.periodDataValue23 = periodDataValue23;
	}

	/**
	 * @return the periodDataValue24
	 */
	public Long getPeriodDataValue24()
	{
		return periodDataValue24;
	}

	/**
	 * @param periodDataValue24 the periodDataValue24 to set
	 */
	public void setPeriodDataValue24( Long periodDataValue24 )
	{
		this.periodDataValue24 = periodDataValue24;
	}

	/**
	 * @return the periodDataValue25
	 */
	public Long getPeriodDataValue25()
	{
		return periodDataValue25;
	}

	/**
	 * @param periodDataValue25 the periodDataValue25 to set
	 */
	public void setPeriodDataValue25( Long periodDataValue25 )
	{
		this.periodDataValue25 = periodDataValue25;
	}

	/**
	 * @return the periodDataValue26
	 */
	public Long getPeriodDataValue26()
	{
		return periodDataValue26;
	}

	/**
	 * @param periodDataValue26 the periodDataValue26 to set
	 */
	public void setPeriodDataValue26( Long periodDataValue26 )
	{
		this.periodDataValue26 = periodDataValue26;
	}

	/**
	 * @return the periodDataValue27
	 */
	public Long getPeriodDataValue27()
	{
		return periodDataValue27;
	}

	/**
	 * @param periodDataValue27 the periodDataValue27 to set
	 */
	public void setPeriodDataValue27( Long periodDataValue27 )
	{
		this.periodDataValue27 = periodDataValue27;
	}

	/**
	 * @return the periodDataValue28
	 */
	public Long getPeriodDataValue28()
	{
		return periodDataValue28;
	}

	/**
	 * @param periodDataValue28 the periodDataValue28 to set
	 */
	public void setPeriodDataValue28( Long periodDataValue28 )
	{
		this.periodDataValue28 = periodDataValue28;
	}

	/**
	 * @return the periodDataValue29
	 */
	public Long getPeriodDataValue29()
	{
		return periodDataValue29;
	}

	/**
	 * @param periodDataValue29 the periodDataValue29 to set
	 */
	public void setPeriodDataValue29( Long periodDataValue29 )
	{
		this.periodDataValue29 = periodDataValue29;
	}

	/**
	 * @return the periodDataValue30
	 */
	public Long getPeriodDataValue30()
	{
		return periodDataValue30;
	}

	/**
	 * @param periodDataValue30 the periodDataValue30 to set
	 */
	public void setPeriodDataValue30( Long periodDataValue30 )
	{
		this.periodDataValue30 = periodDataValue30;
	}

	/**
	 * @return the periodDataValue31
	 */
	public Long getPeriodDataValue31()
	{
		return periodDataValue31;
	}

	/**
	 * @param periodDataValue31 the periodDataValue31 to set
	 */
	public void setPeriodDataValue31( Long periodDataValue31 )
	{
		this.periodDataValue31 = periodDataValue31;
	}

	/**
	 * @return the periodDataValue32
	 */
	public Long getPeriodDataValue32()
	{
		return periodDataValue32;
	}

	/**
	 * @param periodDataValue32 the periodDataValue32 to set
	 */
	public void setPeriodDataValue32( Long periodDataValue32 )
	{
		this.periodDataValue32 = periodDataValue32;
	}

	/**
	 * @return the periodDataValue33
	 */
	public Long getPeriodDataValue33()
	{
		return periodDataValue33;
	}

	/**
	 * @param periodDataValue33 the periodDataValue33 to set
	 */
	public void setPeriodDataValue33( Long periodDataValue33 )
	{
		this.periodDataValue33 = periodDataValue33;
	}

	/**
	 * @return the periodDataValue34
	 */
	public Long getPeriodDataValue34()
	{
		return periodDataValue34;
	}

	/**
	 * @param periodDataValue34 the periodDataValue34 to set
	 */
	public void setPeriodDataValue34( Long periodDataValue34 )
	{
		this.periodDataValue34 = periodDataValue34;
	}

	/**
	 * @return the periodDataValue35
	 */
	public Long getPeriodDataValue35()
	{
		return periodDataValue35;
	}

	/**
	 * @param periodDataValue35 the periodDataValue35 to set
	 */
	public void setPeriodDataValue35( Long periodDataValue35 )
	{
		this.periodDataValue35 = periodDataValue35;
	}

	/**
	 * @return the periodDataValue36
	 */
	public Long getPeriodDataValue36()
	{
		return periodDataValue36;
	}

	/**
	 * @param periodDataValue36 the periodDataValue36 to set
	 */
	public void setPeriodDataValue36( Long periodDataValue36 )
	{
		this.periodDataValue36 = periodDataValue36;
	}

	/**
	 * @return the periodDataValue37
	 */
	public Long getPeriodDataValue37()
	{
		return periodDataValue37;
	}

	/**
	 * @param periodDataValue37 the periodDataValue37 to set
	 */
	public void setPeriodDataValue37( Long periodDataValue37 )
	{
		this.periodDataValue37 = periodDataValue37;
	}

	/**
	 * @return the periodDataValue38
	 */
	public Long getPeriodDataValue38()
	{
		return periodDataValue38;
	}

	/**
	 * @param periodDataValue38 the periodDataValue38 to set
	 */
	public void setPeriodDataValue38( Long periodDataValue38 )
	{
		this.periodDataValue38 = periodDataValue38;
	}

	/**
	 * @return the periodDataValue39
	 */
	public Long getPeriodDataValue39()
	{
		return periodDataValue39;
	}

	/**
	 * @param periodDataValue39 the periodDataValue39 to set
	 */
	public void setPeriodDataValue39( Long periodDataValue39 )
	{
		this.periodDataValue39 = periodDataValue39;
	}

	/**
	 * @return the periodDataValue40
	 */
	public Long getPeriodDataValue40()
	{
		return periodDataValue40;
	}

	/**
	 * @param periodDataValue40 the periodDataValue40 to set
	 */
	public void setPeriodDataValue40( Long periodDataValue40 )
	{
		this.periodDataValue40 = periodDataValue40;
	}

	/**
	 * @return the periodDataValue41
	 */
	public Long getPeriodDataValue41()
	{
		return periodDataValue41;
	}

	/**
	 * @param periodDataValue41 the periodDataValue41 to set
	 */
	public void setPeriodDataValue41( Long periodDataValue41 )
	{
		this.periodDataValue41 = periodDataValue41;
	}

	/**
	 * @return the periodDataValue42
	 */
	public Long getPeriodDataValue42()
	{
		return periodDataValue42;
	}

	/**
	 * @param periodDataValue42 the periodDataValue42 to set
	 */
	public void setPeriodDataValue42( Long periodDataValue42 )
	{
		this.periodDataValue42 = periodDataValue42;
	}

	/**
	 * @return the periodDataValue43
	 */
	public Long getPeriodDataValue43()
	{
		return periodDataValue43;
	}

	/**
	 * @param periodDataValue43 the periodDataValue43 to set
	 */
	public void setPeriodDataValue43( Long periodDataValue43 )
	{
		this.periodDataValue43 = periodDataValue43;
	}

	/**
	 * @return the periodDataValue44
	 */
	public Long getPeriodDataValue44()
	{
		return periodDataValue44;
	}

	/**
	 * @param periodDataValue44 the periodDataValue44 to set
	 */
	public void setPeriodDataValue44( Long periodDataValue44 )
	{
		this.periodDataValue44 = periodDataValue44;
	}

	/**
	 * @return the periodDataValue45
	 */
	public Long getPeriodDataValue45()
	{
		return periodDataValue45;
	}

	/**
	 * @param periodDataValue45 the periodDataValue45 to set
	 */
	public void setPeriodDataValue45( Long periodDataValue45 )
	{
		this.periodDataValue45 = periodDataValue45;
	}

	/**
	 * @return the periodDataValue46
	 */
	public Long getPeriodDataValue46()
	{
		return periodDataValue46;
	}

	/**
	 * @param periodDataValue46 the periodDataValue46 to set
	 */
	public void setPeriodDataValue46( Long periodDataValue46 )
	{
		this.periodDataValue46 = periodDataValue46;
	}

	/**
	 * @return the periodDataValue47
	 */
	public Long getPeriodDataValue47()
	{
		return periodDataValue47;
	}

	/**
	 * @param periodDataValue47 the periodDataValue47 to set
	 */
	public void setPeriodDataValue47( Long periodDataValue47 )
	{
		this.periodDataValue47 = periodDataValue47;
	}

	/**
	 * @return the periodDataValue48
	 */
	public Long getPeriodDataValue48()
	{
		return periodDataValue48;
	}

	/**
	 * @param periodDataValue48 the periodDataValue48 to set
	 */
	public void setPeriodDataValue48( Long periodDataValue48 )
	{
		this.periodDataValue48 = periodDataValue48;
	}

	/**
	 * @return the periodDataValue49
	 */
	public Long getPeriodDataValue49()
	{
		return periodDataValue49;
	}

	/**
	 * @param periodDataValue49 the periodDataValue49 to set
	 */
	public void setPeriodDataValue49( Long periodDataValue49 )
	{
		this.periodDataValue49 = periodDataValue49;
	}

	/**
	 * @return the periodDataValue50
	 */
	public Long getPeriodDataValue50()
	{
		return periodDataValue50;
	}

	/**
	 * @param periodDataValue50 the periodDataValue50 to set
	 */
	public void setPeriodDataValue50( Long periodDataValue50 )
	{
		this.periodDataValue50 = periodDataValue50;
	}

	/**
	 * @return the periodDataValue51
	 */
	public Long getPeriodDataValue51()
	{
		return periodDataValue51;
	}

	/**
	 * @param periodDataValue51 the periodDataValue51 to set
	 */
	public void setPeriodDataValue51( Long periodDataValue51 )
	{
		this.periodDataValue51 = periodDataValue51;
	}

	/**
	 * @return the periodDataValue52
	 */
	public Long getPeriodDataValue52()
	{
		return periodDataValue52;
	}

	/**
	 * @param periodDataValue52 the periodDataValue52 to set
	 */
	public void setPeriodDataValue52( Long periodDataValue52 )
	{
		this.periodDataValue52 = periodDataValue52;
	}

	/**
	 * @return the periodDataValue53
	 */
	public Long getPeriodDataValue53()
	{
		return periodDataValue53;
	}

	/**
	 * @param periodDataValue53 the periodDataValue53 to set
	 */
	public void setPeriodDataValue53( Long periodDataValue53 )
	{
		this.periodDataValue53 = periodDataValue53;
	}

	/**
	 * @return the periodDataValue54
	 */
	public Long getPeriodDataValue54()
	{
		return periodDataValue54;
	}

	/**
	 * @param periodDataValue54 the periodDataValue54 to set
	 */
	public void setPeriodDataValue54( Long periodDataValue54 )
	{
		this.periodDataValue54 = periodDataValue54;
	}

	/**
	 * @return the periodDataValue55
	 */
	public Long getPeriodDataValue55()
	{
		return periodDataValue55;
	}

	/**
	 * @param periodDataValue55 the periodDataValue55 to set
	 */
	public void setPeriodDataValue55( Long periodDataValue55 )
	{
		this.periodDataValue55 = periodDataValue55;
	}

	/**
	 * @return the periodDataValue56
	 */
	public Long getPeriodDataValue56()
	{
		return periodDataValue56;
	}

	/**
	 * @param periodDataValue56 the periodDataValue56 to set
	 */
	public void setPeriodDataValue56( Long periodDataValue56 )
	{
		this.periodDataValue56 = periodDataValue56;
	}

	/**
	 * @return the periodDataValue57
	 */
	public Long getPeriodDataValue57()
	{
		return periodDataValue57;
	}

	/**
	 * @param periodDataValue57 the periodDataValue57 to set
	 */
	public void setPeriodDataValue57( Long periodDataValue57 )
	{
		this.periodDataValue57 = periodDataValue57;
	}

	/**
	 * @return the periodDataValue58
	 */
	public Long getPeriodDataValue58()
	{
		return periodDataValue58;
	}

	/**
	 * @param periodDataValue58 the periodDataValue58 to set
	 */
	public void setPeriodDataValue58( Long periodDataValue58 )
	{
		this.periodDataValue58 = periodDataValue58;
	}

	/**
	 * @return the periodDataValue59
	 */
	public Long getPeriodDataValue59()
	{
		return periodDataValue59;
	}

	/**
	 * @param periodDataValue59 the periodDataValue59 to set
	 */
	public void setPeriodDataValue59( Long periodDataValue59 )
	{
		this.periodDataValue59 = periodDataValue59;
	}
	


}
