package dmdd.dmddjava.service.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumCommonData;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dm.HistoryDataDM;

/**
 * 查询拼装后的基础UIRowData
 * @author zzy
 *
 */
public class QueryRowDataService {
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的历史类(History HistoryAd HitoryAdR)历史数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4History
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowData> getUiRowDatas4History( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4History ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();

		if (_abUiRowDataProOrg == null) {
			return rstList;
		}
		if (_list4BBizData4History == null || _list4BBizData4History.isEmpty()) {
			return rstList;
		}

		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return rstList;
		}
		// 拼出明细范围字符串 begin
		String detailProOrgIdStr = null;
		if (_detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals("")) {
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(_abUiRowDataProOrg);
		} else {
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		// 拼出明细范围字符串 end

		// 拼出业务数据范围和结果数据集 begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();

		for (int j = 0; j < _list4BBizData4History.size(); j = j + 1) {
			BBizData bBizData4History = _list4BBizData4History.get(j);

			list4BizDataId.add(bBizData4History.getId());

			// 每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct(_abUiRowDataProOrg.getProduct());
			newABUiRowData.setProductCharacter(_abUiRowDataProOrg.getProductCharacter());
			newABUiRowData.setOrganization(_abUiRowDataProOrg.getOrganization());
			newABUiRowData.setOrganizationCharacter(_abUiRowDataProOrg.getOrganizationCharacter());
			newABUiRowData.setDetailProOrgIds(_abUiRowDataProOrg.getDetailProOrgIds());
			newABUiRowData.setBizData(bBizData4History);
			newABUiRowData.setPeriodBegin(_periodBegin);
			newABUiRowData.setPeriodEnd(_periodEnd);
			// 每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData end

			hmap_bizDataId_ABUiRowData.put(bBizData4History.getId(), newABUiRowData);
		}
		// 拼出业务数据范围和结果数据集 end

		// 查询历史类数据并填充结果数据集 begin
		List<ASumCommonData> listASumData = null;
		HistoryDataDM dm = new HistoryDataDM();

		listASumData = dm.getSumHistoryDatas(detailProOrgIdStr, _abUiRowDataProOrg, _periodBegin, _periodEnd, list4BizDataId);

		if (listASumData != null && !(listASumData.isEmpty())) {
			for (int j = 0; j < listASumData.size(); j = j + 1) {
				ASumCommonData aSumData = listASumData.get(j);
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get(aSumData.getBizDataId());
				if (abUiRowData != null) {
					int periodLoc = UtilPeriod.getPeriodDifference(_periodBegin, aSumData.getPeriod());
					abUiRowData.pubFun4setPeriodDataValue(periodLoc, (double) aSumData.getValue());
					abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, (double) aSumData.getValue());
					hmap_bizDataId_ABUiRowData.put(aSumData.getBizDataId(), abUiRowData);
				}
			}
		}

		if (hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty())) {
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while (itr_hmap_bizDataId_ABUiRowData_values.hasNext()) {
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
				rstList.add(abUiRowData);
//				if ( isExistHistoryDatas(abUiRowData, detailProOrgIdStr) == true) 
//				{
//					rstList.add(abUiRowData);
//				}
			}
		}

		// 查询历史类数据并填充结果数据集 end

		return rstList;
	}

}
