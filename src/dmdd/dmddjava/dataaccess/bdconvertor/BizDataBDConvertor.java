/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;
import dmdd.dmddjava.dataaccess.utils.UtilFactoryBizDataDefItem;

/**
 * @author liuzhen
 * 
 */
public class BizDataBDConvertor implements BDConvertorInterface {

	/**
	 * 
	 */
	public BizDataBDConvertor() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc) 基本属性,处理; 下附的集合属性bizDataDefItems,不处理;
	 * 
	 * @see
	 * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang
	 * .Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj) {
		BBizData bBizData = null;
		BizData bizData = null;

		if (b_obj == null) {
			bBizData = new BBizData();
		} else {
			bBizData = (BBizData) b_obj;
		}

		if (d_obj == null) {
			return;
		} else {
			bizData = (BizData) d_obj;
		}

		bizData.setVersion(bBizData.getVersion());
		bizData.setId(bBizData.getId());
		bizData.setCode(bBizData.getCode());
		bizData.setName(bBizData.getName());
		bizData.setType(bBizData.getType());
		bizData.setSource(bBizData.getSource());
		bizData.setIsValid(bBizData.getIsValid());
		bizData.setDescription(bBizData.getDescription());
		bizData.setComments(bBizData.getComments());
		//是否支持折和计算,兼容老数据为空的情况
		bizData.setIsSuitSupport(bBizData.getIsSuitSupport());
	}

	/*
	 * (non-Javadoc) 基本属性,处理; 下附的集合属性bizDataDefItems,不处理;
	 * 
	 * @see
	 * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang
	 * .Object)
	 */
	public Object btod(Object b_obj) {
		if (b_obj == null) {
			return null;
		}
		BizData bizData = new BizData();
		this.btod(b_obj, bizData);
		return bizData;
	}

	/*
	 * (non-Javadoc) 基本属性,处理; 下附的集合属性bizDataDefItems,不处理;
	 * 
	 * @see
	 * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang
	 * .Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj) {
		BizData bizData = null;
		BBizData bBizData = null;

		if (d_obj == null) {
			bizData = new BizData();
		} else {
			bizData = (BizData) d_obj;
		}

		if (b_obj == null) {
			return;
		} else {
			bBizData = (BBizData) b_obj;
		}

		bBizData.setVersion(bizData.getVersion());
		bBizData.setId(bizData.getId());
		bBizData.setCode(bizData.getCode());
		bBizData.setName(bizData.getName());
		bBizData.setType(bizData.getType());
		bBizData.setSource(bizData.getSource());
		bBizData.setIsValid(bizData.getIsValid());
		bBizData.setDescription(bizData.getDescription());
		bBizData.setComments(bizData.getComments());
		/**折和转换 */
		bBizData.setIsSuitSupport(bizData.getIsSuitSupport());
	}

	/*
	 * (non-Javadoc) 基本属性,处理; 下附的集合属性bizDataDefItems,不处理;
	 * 
	 * @see
	 * dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang
	 * .Object)
	 */
	public Object dtob(Object d_obj) {
		if (d_obj == null) {
			return null;
		}
		BBizData bBizData = new BBizData();
		this.dtob(d_obj, bBizData);
		return bBizData;
	}

	public void btod(BBizData _bBizData, BizData _bizData,
			boolean _blWithBizDataDefItems) {
		if (_bizData == null) {
			return;
		}

		this.btod(_bBizData, _bizData);

		if (_blWithBizDataDefItems == true) {
			if (_bBizData != null && _bBizData.getBizDataDefItems() != null
					&& _bBizData.getBizDataDefItems().iterator() != null) {
				Iterator<BBizDataDefItem> itr_bBizDataDefItems = _bBizData
						.getBizDataDefItems().iterator();
				while (itr_bBizDataDefItems.hasNext()) {
					BBizDataDefItem bBizDataDefItem = itr_bBizDataDefItems
							.next();
					BizDataDefItemBDConvertor bizDataDefItemBDConvertor = UtilFactoryBizDataDefItem
							.getBizDataDefItemBDConvertorInstance(bBizDataDefItem);

					BizDataDefItem bizDataDefItem = (BizDataDefItem) bizDataDefItemBDConvertor
							.btod(bBizDataDefItem);

					bizDataDefItem.setBizData(_bizData);
					_bizData.addBizDataDefItems(bizDataDefItem);
				}
			}
		}
	}

	public BizData btod(BBizData _bBizData, boolean _blWithBizDataDefItems) {
		if (_bBizData == null) {
			return null;
		}
		BizData bizData = new BizData();
		this.btod(_bBizData, bizData, _blWithBizDataDefItems);
		return bizData;
	}

	public void dtob(BizData _bizData, BBizData _bBizData,
			boolean _blWithBizDataDefItems) {
		if (_bBizData == null) {
			return;
		}

		this.dtob(_bizData, _bBizData);

		if (_blWithBizDataDefItems == true) {
			if (_bizData != null && _bizData.getBizDataDefItems() != null
					&& _bizData.getBizDataDefItems().iterator() != null) {
				Iterator<BizDataDefItem> itr_bizDataDefItems = _bizData
						.getBizDataDefItems().iterator();
				while (itr_bizDataDefItems.hasNext()) {
					BizDataDefItem bizDataDefItem = itr_bizDataDefItems.next();
					BizDataDefItemBDConvertor bizDataDefItemBDConvertor = UtilFactoryBizDataDefItem
							.getBizDataDefItemBDConvertorInstance(bizDataDefItem);

					BBizDataDefItem bBizDataDefItem = (BBizDataDefItem) bizDataDefItemBDConvertor
							.dtob(bizDataDefItem);

					bBizDataDefItem.setBizData(_bBizData);
					_bBizData.addBizDataDefItems(bBizDataDefItem);
				}
			}

		}

	}

	public BBizData dtob(BizData _bizData, boolean _blWithBizDataDefItems) {
		if (_bizData == null) {
			return null;
		}
		BBizData bBizData = new BBizData();
		this.dtob(_bizData, bBizData, _blWithBizDataDefItems);
		return bBizData;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
