package dmdd.dmddjava.service.invreport;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description:为预测-库存报表的每一行数据内容
 * Company: dmdd
 * 
 * @author zzy
 * @date Nov 15, 2017 8:51:49 PM
 */
public class FcInvRowDataVo implements Serializable {
	public final static long serialVersionUID = -2010000001;

	private String proCode;
	private String proName;

	private Double hisAvg12;
	private Double hisAvg9;
	private Double hisAvg6;
	private Double hisAvg3;
	private Double curStockValid;
	private Double curStockDaysCycle;
	private Double periodPoTotal; //当期时，所有的PO到货汇总
	private Double curStockAndPo;
	private Double curStockPoDaysCycle;
	private Integer lastOffPeriod; //去当月库存查询那里抓取最远的下架期 
	private Integer curStockValidTo;
	private Integer curStockPoValidTo; //含期初+PO有效期至,(当期期间+E覆盖天数)
	private Integer custLeftMaxPeriod;
	private Double overStockDaysCycle;
	
	private Double overStockTotal;//过期库存汇总
	private Double overStockTotalMoney;//过期库存汇总金额
	
	private int maxHisIndex = 35;
	
	public Double getPeriodFcValue(int period )
	{
		switch( period )
		{
			case  0: return fcValue00;
			case  1: return fcValue01;
			case  2: return fcValue02;
			case  3: return fcValue03;
			case  4: return fcValue04;
			case  5: return fcValue05;
			case  6: return fcValue06;
			case  7: return fcValue07;
			case  8: return fcValue08;
			case  9: return fcValue09;
			case 10: return fcValue10;
			case 11: return fcValue11;
			case 12: return fcValue12;
			case 13: return fcValue13;
			case 14: return fcValue14;
			case 15: return fcValue15;
			case 16: return fcValue16;
			case 17: return fcValue17;
			case 18: return fcValue18;
			case 19: return fcValue19;
			case 20: return fcValue20;
			case 21: return fcValue21;
			case 22: return fcValue22;
			case 23: return fcValue23;
			case 24: return fcValue24;
			case 25: return fcValue25;
			case 26: return fcValue26;
			case 27: return fcValue27;
			case 28: return fcValue28;
			case 29: return fcValue29;		
			case 30: return fcValue30;
			case 31: return fcValue31;
			case 32: return fcValue32;
			case 33: return fcValue33;
			case 34: return fcValue34;
			case 35: return fcValue35;
		}
		
		return null;
	}
	
	public Double getPeriodHisValue(int period )
	{
		switch( period )
		{
			case  0: return hisValue00;
			case  1: return hisValue01;
			case  2: return hisValue02;
			case  3: return hisValue03;
			case  4: return hisValue04;
			case  5: return hisValue05;
			case  6: return hisValue06;
			case  7: return hisValue07;
			case  8: return hisValue08;
			case  9: return hisValue09;
			case 10: return hisValue10;
			case 11: return hisValue11;
			case 12: return hisValue12;
			case 13: return hisValue13;
			case 14: return hisValue14;
			case 15: return hisValue15;
			case 16: return hisValue16;
			case 17: return hisValue17;
			case 18: return hisValue18;
			case 19: return hisValue19;
			case 20: return hisValue20;
			case 21: return hisValue21;
			case 22: return hisValue22;
			case 23: return hisValue23;
			case 24: return hisValue24;
			case 25: return hisValue25;
			case 26: return hisValue26;
			case 27: return hisValue27;
			case 28: return hisValue28;
			case 29: return hisValue29;		
			case 30: return hisValue30;
			case 31: return hisValue31;
			case 32: return hisValue32;
			case 33: return hisValue33;
			case 34: return hisValue34;
			case 35: return hisValue35;
		}
		
		return null;
	}
	
	public void setPeriodHisValue( int period, Double value )
	{
		switch( period )
		{
			case  0: hisValue00 = value; return;
			case  1: hisValue01 = value; return;
			case  2: hisValue02 = value; return;
			case  3: hisValue03 = value; return;
			case  4: hisValue04 = value; return;
			case  5: hisValue05 = value; return;
			case  6: hisValue06 = value; return;
			case  7: hisValue07 = value; return;
			case  8: hisValue08 = value; return;
			case  9: hisValue09 = value; return;
			case 10: hisValue10 = value; return;
			case 11: hisValue11 = value; return;
			case 12: hisValue12 = value; return;
			case 13: hisValue13 = value; return;
			case 14: hisValue14 = value; return;
			case 15: hisValue15 = value; return;
			case 16: hisValue16 = value; return;
			case 17: hisValue17 = value; return;
			case 18: hisValue18 = value; return;
			case 19: hisValue19 = value; return;
			case 20: hisValue20 = value; return;
			case 21: hisValue21 = value; return;
			case 22: hisValue22 = value; return;
			case 23: hisValue23 = value; return;
			case 24: hisValue24 = value; return;
			case 25: hisValue25 = value; return;
			case 26: hisValue26 = value; return;
			case 27: hisValue27 = value; return;
			case 28: hisValue28 = value; return;
			case 29: hisValue29 = value; return;	
			
			case 30: hisValue30 = value; return;
			case 31: hisValue31 = value; return;
			case 32: hisValue32 = value; return;
			case 33: hisValue33 = value; return;
			case 34: hisValue34 = value; return;
			case 35: hisValue35 = value; return;
		}
	}
	
	public void setPeriodFcValue( int period, Double value )
	{
		switch( period )
		{
			case  0: fcValue00 = value; return;
			case  1: fcValue01 = value; return;
			case  2: fcValue02 = value; return;
			case  3: fcValue03 = value; return;
			case  4: fcValue04 = value; return;
			case  5: fcValue05 = value; return;
			case  6: fcValue06 = value; return;
			case  7: fcValue07 = value; return;
			case  8: fcValue08 = value; return;
			case  9: fcValue09 = value; return;
			case 10: fcValue10 = value; return;
			case 11: fcValue11 = value; return;
			case 12: fcValue12 = value; return;
			case 13: fcValue13 = value; return;
			case 14: fcValue14 = value; return;
			case 15: fcValue15 = value; return;
			case 16: fcValue16 = value; return;
			case 17: fcValue17 = value; return;
			case 18: fcValue18 = value; return;
			case 19: fcValue19 = value; return;
			case 20: fcValue20 = value; return;
			case 21: fcValue21 = value; return;
			case 22: fcValue22 = value; return;
			case 23: fcValue23 = value; return;
			case 24: fcValue24 = value; return;
			case 25: fcValue25 = value; return;
			case 26: fcValue26 = value; return;
			case 27: fcValue27 = value; return;
			case 28: fcValue28 = value; return;
			case 29: fcValue29 = value; return;	
			
			case 30: fcValue30 = value; return;
			case 31: fcValue31 = value; return;
			case 32: fcValue32 = value; return;
			case 33: fcValue33 = value; return;
			case 34: fcValue34 = value; return;
			case 35: fcValue35 = value; return;
		}
	}

	public Double fcValue00 = null;
	public Double fcValue01 = null;
	public Double fcValue02 = null;
	public Double fcValue03 = null;
	public Double fcValue04 = null;
	public Double fcValue05 = null;
	public Double fcValue06 = null;
	public Double fcValue07 = null;
	public Double fcValue08 = null;
	public Double fcValue09 = null;
	public Double fcValue10 = null;
	public Double fcValue11 = null;
	public Double fcValue12 = null;
	public Double fcValue13 = null;
	public Double fcValue14 = null;
	public Double fcValue15 = null;
	public Double fcValue16 = null;
	public Double fcValue17 = null;
	public Double fcValue18 = null;
	public Double fcValue19 = null;
	public Double fcValue20 = null;
	public Double fcValue21 = null;
	public Double fcValue22 = null;
	public Double fcValue23 = null;
	public Double fcValue24 = null;
	public Double fcValue25 = null;
	public Double fcValue26 = null;
	public Double fcValue27 = null;
	public Double fcValue28 = null;
	public Double fcValue29 = null;
	public Double fcValue30 = null;
	public Double fcValue31 = null;
	public Double fcValue32 = null;
	public Double fcValue33 = null;
	public Double fcValue34 = null;
	public Double fcValue35 = null;

	public Double hisValue00 = null;
	public Double hisValue01 = null;
	public Double hisValue02 = null;
	public Double hisValue03 = null;
	public Double hisValue04 = null;
	public Double hisValue05 = null;
	public Double hisValue06 = null;
	public Double hisValue07 = null;
	public Double hisValue08 = null;
	public Double hisValue09 = null;
	public Double hisValue10 = null;
	public Double hisValue11 = null;
	public Double hisValue12 = null;
	public Double hisValue13 = null;
	public Double hisValue14 = null;
	public Double hisValue15 = null;
	public Double hisValue16 = null;
	public Double hisValue17 = null;
	public Double hisValue18 = null;
	public Double hisValue19 = null;
	public Double hisValue20 = null;
	public Double hisValue21 = null;
	public Double hisValue22 = null;
	public Double hisValue23 = null;
	public Double hisValue24 = null;
	public Double hisValue25 = null;
	public Double hisValue26 = null;
	public Double hisValue27 = null;
	public Double hisValue28 = null;
	public Double hisValue29 = null;
	public Double hisValue30 = null;
	public Double hisValue31 = null;
	public Double hisValue32 = null;
	public Double hisValue33 = null;
	public Double hisValue34 = null;
	public Double hisValue35 = null;
	
	

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Double getHisAvg12() {
		return hisAvg12;
	}

	public void setHisAvg12(Double hisAvg12) {
		this.hisAvg12 = hisAvg12;
	}

	public Double getHisAvg9() {
		return hisAvg9;
	}

	public void setHisAvg9(Double hisAvg9) {
		this.hisAvg9 = hisAvg9;
	}

	public Double getHisAvg6() {
		return hisAvg6;
	}

	public void setHisAvg6(Double hisAvg6) {
		this.hisAvg6 = hisAvg6;
	}

	public Double getHisAvg3() {
		return hisAvg3;
	}

	public void setHisAvg3(Double hisAvg3) {
		this.hisAvg3 = hisAvg3;
	}

	public Double getCurStockValid() {
		return curStockValid;
	}

	public void setCurStockValid(Double curStockValid) {
		this.curStockValid = curStockValid;
	}

	public Double getCurStockDaysCycle() {
		return curStockDaysCycle;
	}

	public void setCurStockDaysCycle(Double curStockDaysCycle) {
		this.curStockDaysCycle = curStockDaysCycle;
	}

	public Double getCurStockAndPo() {
		return curStockAndPo;
	}

	public void setCurStockAndPo(Double curStockAndPo) {
		this.curStockAndPo = curStockAndPo;
	}

	public Double getCurStockPoDaysCycle() {
		return curStockPoDaysCycle;
	}

	public void setCurStockPoDaysCycle(Double curStockPoDaysCycle) {
		this.curStockPoDaysCycle = curStockPoDaysCycle;
	}

	public Integer getLastOffPeriod() {
		return lastOffPeriod;
	}

	public void setLastOffPeriod(Integer lastOffPeriod) {
		this.lastOffPeriod = lastOffPeriod;
	}

	public Integer getCurStockPoValidTo() {
		return curStockPoValidTo;
	}

	public void setCurStockPoValidTo(Integer curStockPoValidTo) {
		this.curStockPoValidTo = curStockPoValidTo;
	}

	public Integer getCustLeftMaxPeriod() {
		return custLeftMaxPeriod;
	}

	public void setCustLeftMaxPeriod(Integer custLeftMaxPeriod) {
		this.custLeftMaxPeriod = custLeftMaxPeriod;
	}
	

	public Double getOverStockDaysCycle() {
		return overStockDaysCycle;
	}

	public void setOverStockDaysCycle(Double overStockDaysCycle) {
		this.overStockDaysCycle = overStockDaysCycle;
	}
	
	public int getMaxHisIndex() {
		return maxHisIndex;
	}

	public Integer getCurStockValidTo() {
		return curStockValidTo;
	}

	public void setCurStockValidTo(Integer curStockValidTo) {
		this.curStockValidTo = curStockValidTo;
	}

	public Double getOverStockTotal() {
		return overStockTotal;
	}

	public void setOverStockTotal(Double overStockTotal) {
		this.overStockTotal = overStockTotal;
	}

	public Double getOverStockTotalMoney() {
		return overStockTotalMoney;
	}

	public void setOverStockTotalMoney(Double overStockTotalMoney) {
		this.overStockTotalMoney = overStockTotalMoney;
	}
	public Double getPeriodPoTotal() {
		return periodPoTotal;
	}

	public void setPeriodPoTotal(Double periodPoTotal) {
		this.periodPoTotal = periodPoTotal;
	}
	

}
