package dmdd.dmddjava.service.historyservice.imp;

import java.util.List;

import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.service.historyservice.AmountHistoryCalContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 17, 2017 10:18:44 PM
 */
public class ImHistoryDataContext {

	private BizData bizDataHistory;
	
	private BizData bizDataHistoryAd;
	
	private BUnitGroup unitGroup;
	
	private List<Long> listBizDataId_HistoryAdR;
	
	private int dealNum; //处理的总数据量
	
	private BizData amountBizDataHistory;
	
	private BizData amountBizDataHistoryAd;
	
	/** 历史调整原因类-折合业务数据 */
	private List<Long> listAmountBizDataId_HistoryAdR;
	
	private List<AmountHistoryCalContext> calContextList;
	
	public BizData getBizDataHistory() {
		return bizDataHistory;
	}

	public void setBizDataHistory(BizData bizDataHistory) {
		this.bizDataHistory = bizDataHistory;
	}
	
	public BizData getBizDataHistoryAd() {
		return bizDataHistoryAd;
	}

	public void setBizDataHistoryAd(BizData bizDataHistoryAd) {
		this.bizDataHistoryAd = bizDataHistoryAd;
	}

	public BUnitGroup getUnitGroup() {
		return unitGroup;
	}

	public void setUnitGroup(BUnitGroup unitGroup) {
		this.unitGroup = unitGroup;
	}

	public BizData getAmountBizDataHistory() {
		return amountBizDataHistory;
	}

	public void setAmountBizDataHistory(BizData amountBizDataHistory) {
		this.amountBizDataHistory = amountBizDataHistory;
	}
	
	public List<Long> getListBizDataId_HistoryAdR() {
		return listBizDataId_HistoryAdR;
	}

	public void setListBizDataId_HistoryAdR(List<Long> listBizDataId_HistoryAdR) {
		this.listBizDataId_HistoryAdR = listBizDataId_HistoryAdR;
	}

	public int getDealNum() {
		return dealNum;
	}

	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}

	public List<AmountHistoryCalContext> getCalContextList() {
		return calContextList;
	}

	public void setCalContextList(List<AmountHistoryCalContext> calContextList) {
		this.calContextList = calContextList;
	}
	
	public void addCalContext(AmountHistoryCalContext calContext){
		calContextList.add(calContext);
	}

	public BizData getAmountBizDataHistoryAd() {
		return amountBizDataHistoryAd;
	}

	public void setAmountBizDataHistoryAd(BizData amountBizDataHistoryAd) {
		this.amountBizDataHistoryAd = amountBizDataHistoryAd;
	}

	public List<Long> getListAmountBizDataId_HistoryAdR() {
		return listAmountBizDataId_HistoryAdR;
	}

	public void setListAmountBizDataId_HistoryAdR(List<Long> listAmountBizDataId_HistoryAdR) {
		this.listAmountBizDataId_HistoryAdR = listAmountBizDataId_HistoryAdR;
	}
	
}
