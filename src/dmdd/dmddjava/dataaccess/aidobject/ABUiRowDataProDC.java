package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.SysConst;

/**
 * 多期间补货数据对象
 * @author jerry
 * @date Aug 16, 2013
 */
public class ABUiRowDataProDC extends ABUiRowDataProDCAbstract implements Serializable{
	
	private static final long serialVersionUID = -1011772252529563229L;

	private int periodBegin = SysConst.PERIOD_NULL;
	private int periodEnd = SysConst.PERIOD_NULL;
	
	private String periodDataValue00 ;
	private String periodDataValue01 ;
	private String periodDataValue02 ;
	private String periodDataValue03 ;
	private String periodDataValue04 ;
	private String periodDataValue05 ;
	private String periodDataValue06 ;
	private String periodDataValue07 ;
	private String periodDataValue08 ;
	private String periodDataValue09 ;
	private String periodDataValue10 ;
	private String periodDataValue11 ;
	private String periodDataValue12 ;
	private String periodDataValue13 ;
	private String periodDataValue14 ;
	private String periodDataValue15 ;
	private String periodDataValue16 ;
	private String periodDataValue17 ;
	private String periodDataValue18 ;
	private String periodDataValue19 ;
	private String periodDataValue20 ;
	private String periodDataValue21 ;
	private String periodDataValue22 ;
	private String periodDataValue23 ;
	private String periodDataValue24 ;
	private String periodDataValue25 ;
	private String periodDataValue26 ;
	private String periodDataValue27 ;
	private String periodDataValue28 ;
	private String periodDataValue29 ;	
	
	private String periodDataValue30 ;
	private String periodDataValue31 ;
	private String periodDataValue32 ;
	private String periodDataValue33 ;
	private String periodDataValue34 ;
	private String periodDataValue35 ;
	private String periodDataValue36 ;
	private String periodDataValue37 ;
	private String periodDataValue38 ;
	private String periodDataValue39 ;	
	
	private String periodDataValue40 ;
	private String periodDataValue41 ;
	private String periodDataValue42 ;
	private String periodDataValue43 ;
	private String periodDataValue44 ;
	private String periodDataValue45 ;
	private String periodDataValue46 ;
	private String periodDataValue47 ;
	private String periodDataValue48 ;
	private String periodDataValue49 ;			
	
	private String periodDataValue50 ;
	private String periodDataValue51 ;
	private String periodDataValue52 ;
	private String periodDataValue53 ;
	private String periodDataValue54 ;
	private String periodDataValue55 ;
	private String periodDataValue56 ;
	private String periodDataValue57 ;
	private String periodDataValue58 ;
	private String periodDataValue59 ;					
	
	private String periodDataValue60 ;
	private String periodDataValue61 ;
	private String periodDataValue62 ;
	private String periodDataValue63 ;
	private String periodDataValue64 ;
	private String periodDataValue65 ;
	private String periodDataValue66 ;
	private String periodDataValue67 ;
	private String periodDataValue68 ;
	private String periodDataValue69 ;
	private String periodDataValue70 ;
	private String periodDataValue71 ;
	private String periodDataValue72 ;
	private String periodDataValue73 ;
	private String periodDataValue74 ;
	private String periodDataValue75 ;
	private String periodDataValue76 ;
	private String periodDataValue77 ;
	

	public String pubFun4getPeriodDataValue( int _ithPeriod )
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
	
	
	public void pubFun4setPeriodDataValue( int _ithPeriod, String _value )
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
	
	public int getPeriodBegin() {
		return periodBegin;
	}


	public void setPeriodBegin(int periodBegin) {
		this.periodBegin = periodBegin;
	}


	public int getPeriodEnd() {
		return periodEnd;
	}


	public void setPeriodEnd(int periodEnd) {
		this.periodEnd = periodEnd;
	}
	
	public String getPeriodDataValue00() {
		return periodDataValue00;
	}


	public void setPeriodDataValue00(String periodDataValue00) {
		this.periodDataValue00 = periodDataValue00;
	}


	public String getPeriodDataValue01() {
		return periodDataValue01;
	}


	public void setPeriodDataValue01(String periodDataValue01) {
		this.periodDataValue01 = periodDataValue01;
	}


	public String getPeriodDataValue02() {
		return periodDataValue02;
	}


	public void setPeriodDataValue02(String periodDataValue02) {
		this.periodDataValue02 = periodDataValue02;
	}


	public String getPeriodDataValue03() {
		return periodDataValue03;
	}


	public void setPeriodDataValue03(String periodDataValue03) {
		this.periodDataValue03 = periodDataValue03;
	}


	public String getPeriodDataValue04() {
		return periodDataValue04;
	}


	public void setPeriodDataValue04(String periodDataValue04) {
		this.periodDataValue04 = periodDataValue04;
	}


	public String getPeriodDataValue05() {
		return periodDataValue05;
	}


	public void setPeriodDataValue05(String periodDataValue05) {
		this.periodDataValue05 = periodDataValue05;
	}


	public String getPeriodDataValue06() {
		return periodDataValue06;
	}


	public void setPeriodDataValue06(String periodDataValue06) {
		this.periodDataValue06 = periodDataValue06;
	}


	public String getPeriodDataValue07() {
		return periodDataValue07;
	}


	public void setPeriodDataValue07(String periodDataValue07) {
		this.periodDataValue07 = periodDataValue07;
	}


	public String getPeriodDataValue08() {
		return periodDataValue08;
	}


	public void setPeriodDataValue08(String periodDataValue08) {
		this.periodDataValue08 = periodDataValue08;
	}


	public String getPeriodDataValue09() {
		return periodDataValue09;
	}


	public void setPeriodDataValue09(String periodDataValue09) {
		this.periodDataValue09 = periodDataValue09;
	}


	public String getPeriodDataValue10() {
		return periodDataValue10;
	}


	public void setPeriodDataValue10(String periodDataValue10) {
		this.periodDataValue10 = periodDataValue10;
	}


	public String getPeriodDataValue11() {
		return periodDataValue11;
	}


	public void setPeriodDataValue11(String periodDataValue11) {
		this.periodDataValue11 = periodDataValue11;
	}


	public String getPeriodDataValue12() {
		return periodDataValue12;
	}


	public void setPeriodDataValue12(String periodDataValue12) {
		this.periodDataValue12 = periodDataValue12;
	}


	public String getPeriodDataValue13() {
		return periodDataValue13;
	}


	public void setPeriodDataValue13(String periodDataValue13) {
		this.periodDataValue13 = periodDataValue13;
	}


	public String getPeriodDataValue14() {
		return periodDataValue14;
	}


	public void setPeriodDataValue14(String periodDataValue14) {
		this.periodDataValue14 = periodDataValue14;
	}


	public String getPeriodDataValue15() {
		return periodDataValue15;
	}


	public void setPeriodDataValue15(String periodDataValue15) {
		this.periodDataValue15 = periodDataValue15;
	}


	public String getPeriodDataValue16() {
		return periodDataValue16;
	}


	public void setPeriodDataValue16(String periodDataValue16) {
		this.periodDataValue16 = periodDataValue16;
	}


	public String getPeriodDataValue17() {
		return periodDataValue17;
	}


	public void setPeriodDataValue17(String periodDataValue17) {
		this.periodDataValue17 = periodDataValue17;
	}


	public String getPeriodDataValue18() {
		return periodDataValue18;
	}


	public void setPeriodDataValue18(String periodDataValue18) {
		this.periodDataValue18 = periodDataValue18;
	}


	public String getPeriodDataValue19() {
		return periodDataValue19;
	}


	public void setPeriodDataValue19(String periodDataValue19) {
		this.periodDataValue19 = periodDataValue19;
	}


	public String getPeriodDataValue20() {
		return periodDataValue20;
	}


	public void setPeriodDataValue20(String periodDataValue20) {
		this.periodDataValue20 = periodDataValue20;
	}


	public String getPeriodDataValue21() {
		return periodDataValue21;
	}


	public void setPeriodDataValue21(String periodDataValue21) {
		this.periodDataValue21 = periodDataValue21;
	}


	public String getPeriodDataValue22() {
		return periodDataValue22;
	}


	public void setPeriodDataValue22(String periodDataValue22) {
		this.periodDataValue22 = periodDataValue22;
	}


	public String getPeriodDataValue23() {
		return periodDataValue23;
	}


	public void setPeriodDataValue23(String periodDataValue23) {
		this.periodDataValue23 = periodDataValue23;
	}


	public String getPeriodDataValue24() {
		return periodDataValue24;
	}


	public void setPeriodDataValue24(String periodDataValue24) {
		this.periodDataValue24 = periodDataValue24;
	}


	public String getPeriodDataValue25() {
		return periodDataValue25;
	}


	public void setPeriodDataValue25(String periodDataValue25) {
		this.periodDataValue25 = periodDataValue25;
	}


	public String getPeriodDataValue26() {
		return periodDataValue26;
	}


	public void setPeriodDataValue26(String periodDataValue26) {
		this.periodDataValue26 = periodDataValue26;
	}


	public String getPeriodDataValue27() {
		return periodDataValue27;
	}


	public void setPeriodDataValue27(String periodDataValue27) {
		this.periodDataValue27 = periodDataValue27;
	}


	public String getPeriodDataValue28() {
		return periodDataValue28;
	}


	public void setPeriodDataValue28(String periodDataValue28) {
		this.periodDataValue28 = periodDataValue28;
	}


	public String getPeriodDataValue29() {
		return periodDataValue29;
	}


	public void setPeriodDataValue29(String periodDataValue29) {
		this.periodDataValue29 = periodDataValue29;
	}


	public String getPeriodDataValue30() {
		return periodDataValue30;
	}


	public void setPeriodDataValue30(String periodDataValue30) {
		this.periodDataValue30 = periodDataValue30;
	}


	public String getPeriodDataValue31() {
		return periodDataValue31;
	}


	public void setPeriodDataValue31(String periodDataValue31) {
		this.periodDataValue31 = periodDataValue31;
	}


	public String getPeriodDataValue32() {
		return periodDataValue32;
	}


	public void setPeriodDataValue32(String periodDataValue32) {
		this.periodDataValue32 = periodDataValue32;
	}


	public String getPeriodDataValue33() {
		return periodDataValue33;
	}


	public void setPeriodDataValue33(String periodDataValue33) {
		this.periodDataValue33 = periodDataValue33;
	}


	public String getPeriodDataValue34() {
		return periodDataValue34;
	}


	public void setPeriodDataValue34(String periodDataValue34) {
		this.periodDataValue34 = periodDataValue34;
	}


	public String getPeriodDataValue35() {
		return periodDataValue35;
	}


	public void setPeriodDataValue35(String periodDataValue35) {
		this.periodDataValue35 = periodDataValue35;
	}


	public String getPeriodDataValue36() {
		return periodDataValue36;
	}


	public void setPeriodDataValue36(String periodDataValue36) {
		this.periodDataValue36 = periodDataValue36;
	}


	public String getPeriodDataValue37() {
		return periodDataValue37;
	}


	public void setPeriodDataValue37(String periodDataValue37) {
		this.periodDataValue37 = periodDataValue37;
	}


	public String getPeriodDataValue38() {
		return periodDataValue38;
	}


	public void setPeriodDataValue38(String periodDataValue38) {
		this.periodDataValue38 = periodDataValue38;
	}


	public String getPeriodDataValue39() {
		return periodDataValue39;
	}


	public void setPeriodDataValue39(String periodDataValue39) {
		this.periodDataValue39 = periodDataValue39;
	}


	public String getPeriodDataValue40() {
		return periodDataValue40;
	}


	public void setPeriodDataValue40(String periodDataValue40) {
		this.periodDataValue40 = periodDataValue40;
	}


	public String getPeriodDataValue41() {
		return periodDataValue41;
	}


	public void setPeriodDataValue41(String periodDataValue41) {
		this.periodDataValue41 = periodDataValue41;
	}


	public String getPeriodDataValue42() {
		return periodDataValue42;
	}


	public void setPeriodDataValue42(String periodDataValue42) {
		this.periodDataValue42 = periodDataValue42;
	}


	public String getPeriodDataValue43() {
		return periodDataValue43;
	}


	public void setPeriodDataValue43(String periodDataValue43) {
		this.periodDataValue43 = periodDataValue43;
	}


	public String getPeriodDataValue44() {
		return periodDataValue44;
	}


	public void setPeriodDataValue44(String periodDataValue44) {
		this.periodDataValue44 = periodDataValue44;
	}


	public String getPeriodDataValue45() {
		return periodDataValue45;
	}


	public void setPeriodDataValue45(String periodDataValue45) {
		this.periodDataValue45 = periodDataValue45;
	}


	public String getPeriodDataValue46() {
		return periodDataValue46;
	}


	public void setPeriodDataValue46(String periodDataValue46) {
		this.periodDataValue46 = periodDataValue46;
	}


	public String getPeriodDataValue47() {
		return periodDataValue47;
	}


	public void setPeriodDataValue47(String periodDataValue47) {
		this.periodDataValue47 = periodDataValue47;
	}


	public String getPeriodDataValue48() {
		return periodDataValue48;
	}


	public void setPeriodDataValue48(String periodDataValue48) {
		this.periodDataValue48 = periodDataValue48;
	}


	public String getPeriodDataValue49() {
		return periodDataValue49;
	}


	public void setPeriodDataValue49(String periodDataValue49) {
		this.periodDataValue49 = periodDataValue49;
	}


	public String getPeriodDataValue50() {
		return periodDataValue50;
	}


	public void setPeriodDataValue50(String periodDataValue50) {
		this.periodDataValue50 = periodDataValue50;
	}


	public String getPeriodDataValue51() {
		return periodDataValue51;
	}


	public void setPeriodDataValue51(String periodDataValue51) {
		this.periodDataValue51 = periodDataValue51;
	}


	public String getPeriodDataValue52() {
		return periodDataValue52;
	}


	public void setPeriodDataValue52(String periodDataValue52) {
		this.periodDataValue52 = periodDataValue52;
	}


	public String getPeriodDataValue53() {
		return periodDataValue53;
	}


	public void setPeriodDataValue53(String periodDataValue53) {
		this.periodDataValue53 = periodDataValue53;
	}


	public String getPeriodDataValue54() {
		return periodDataValue54;
	}


	public void setPeriodDataValue54(String periodDataValue54) {
		this.periodDataValue54 = periodDataValue54;
	}


	public String getPeriodDataValue55() {
		return periodDataValue55;
	}


	public void setPeriodDataValue55(String periodDataValue55) {
		this.periodDataValue55 = periodDataValue55;
	}


	public String getPeriodDataValue56() {
		return periodDataValue56;
	}


	public void setPeriodDataValue56(String periodDataValue56) {
		this.periodDataValue56 = periodDataValue56;
	}


	public String getPeriodDataValue57() {
		return periodDataValue57;
	}


	public void setPeriodDataValue57(String periodDataValue57) {
		this.periodDataValue57 = periodDataValue57;
	}


	public String getPeriodDataValue58() {
		return periodDataValue58;
	}


	public void setPeriodDataValue58(String periodDataValue58) {
		this.periodDataValue58 = periodDataValue58;
	}


	public String getPeriodDataValue59() {
		return periodDataValue59;
	}


	public void setPeriodDataValue59(String periodDataValue59) {
		this.periodDataValue59 = periodDataValue59;
	}


	public String getPeriodDataValue60() {
		return periodDataValue60;
	}


	public void setPeriodDataValue60(String periodDataValue60) {
		this.periodDataValue60 = periodDataValue60;
	}


	public String getPeriodDataValue61() {
		return periodDataValue61;
	}


	public void setPeriodDataValue61(String periodDataValue61) {
		this.periodDataValue61 = periodDataValue61;
	}


	public String getPeriodDataValue62() {
		return periodDataValue62;
	}


	public void setPeriodDataValue62(String periodDataValue62) {
		this.periodDataValue62 = periodDataValue62;
	}


	public String getPeriodDataValue63() {
		return periodDataValue63;
	}


	public void setPeriodDataValue63(String periodDataValue63) {
		this.periodDataValue63 = periodDataValue63;
	}


	public String getPeriodDataValue64() {
		return periodDataValue64;
	}


	public void setPeriodDataValue64(String periodDataValue64) {
		this.periodDataValue64 = periodDataValue64;
	}


	public String getPeriodDataValue65() {
		return periodDataValue65;
	}


	public void setPeriodDataValue65(String periodDataValue65) {
		this.periodDataValue65 = periodDataValue65;
	}


	public String getPeriodDataValue66() {
		return periodDataValue66;
	}


	public void setPeriodDataValue66(String periodDataValue66) {
		this.periodDataValue66 = periodDataValue66;
	}


	public String getPeriodDataValue67() {
		return periodDataValue67;
	}


	public void setPeriodDataValue67(String periodDataValue67) {
		this.periodDataValue67 = periodDataValue67;
	}


	public String getPeriodDataValue68() {
		return periodDataValue68;
	}


	public void setPeriodDataValue68(String periodDataValue68) {
		this.periodDataValue68 = periodDataValue68;
	}


	public String getPeriodDataValue69() {
		return periodDataValue69;
	}


	public void setPeriodDataValue69(String periodDataValue69) {
		this.periodDataValue69 = periodDataValue69;
	}


	public String getPeriodDataValue70() {
		return periodDataValue70;
	}


	public void setPeriodDataValue70(String periodDataValue70) {
		this.periodDataValue70 = periodDataValue70;
	}


	public String getPeriodDataValue71() {
		return periodDataValue71;
	}


	public void setPeriodDataValue71(String periodDataValue71) {
		this.periodDataValue71 = periodDataValue71;
	}


	public String getPeriodDataValue72() {
		return periodDataValue72;
	}


	public void setPeriodDataValue72(String periodDataValue72) {
		this.periodDataValue72 = periodDataValue72;
	}


	public String getPeriodDataValue73() {
		return periodDataValue73;
	}


	public void setPeriodDataValue73(String periodDataValue73) {
		this.periodDataValue73 = periodDataValue73;
	}


	public String getPeriodDataValue74() {
		return periodDataValue74;
	}


	public void setPeriodDataValue74(String periodDataValue74) {
		this.periodDataValue74 = periodDataValue74;
	}


	public String getPeriodDataValue75() {
		return periodDataValue75;
	}


	public void setPeriodDataValue75(String periodDataValue75) {
		this.periodDataValue75 = periodDataValue75;
	}


	public String getPeriodDataValue76() {
		return periodDataValue76;
	}


	public void setPeriodDataValue76(String periodDataValue76) {
		this.periodDataValue76 = periodDataValue76;
	}


	public String getPeriodDataValue77() {
		return periodDataValue77;
	}


	public void setPeriodDataValue77(String periodDataValue77) {
		this.periodDataValue77 = periodDataValue77;
	}
	
}
