package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dmdd.dmddjava.common.constant.SysConst;

public class ABUiRowDataProDCAbstract implements Serializable {

	private static final long serialVersionUID = 8702168785882287662L;
	
	protected String id = ""; //对象ID
	protected String parentId  =""; //父对象ID，与ID一起构成层次数据对象
	protected String dcId = "";
	protected String dcCode = "";
	protected String dcName = "";
	protected String proId = "";
	protected String proCode = "";
	protected String proName = "";
	protected String unitGroupName = "";
	protected String unitGroupCode = "";
	protected String unitName = "";
	protected String unitCode = "";
	
	protected List<ABUiRowDataProDCAbstract> children = null;
	
	public String getDcId() {
		return dcId;
	}


	public void setDcId(String dcId) {
		this.dcId = dcId;
	}


	public String getProId() {
		return proId;
	}


	public void setProId(String proId) {
		this.proId = proId;
	}


	public void addChildVo(ABUiRowDataProDCAbstract _child){
		if(children==null)
			children = new ArrayList<ABUiRowDataProDCAbstract>();
		children.add(_child);
	}

	public List<ABUiRowDataProDCAbstract> getChildren() {
		return children;
	}


	public void setChildren(List<ABUiRowDataProDCAbstract> children) {
		this.children = children;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getDcCode() {
		return dcCode;
	}


	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}


	public String getDcName() {
		return dcName;
	}


	public void setDcName(String dcName) {
		this.dcName = dcName;
	}


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


	public String getUnitGroupName() {
		return unitGroupName;
	}


	public void setUnitGroupName(String unitGroupName) {
		this.unitGroupName = unitGroupName;
	}


	public String getUnitGroupCode() {
		return unitGroupCode;
	}


	public void setUnitGroupCode(String unitGroupCode) {
		this.unitGroupCode = unitGroupCode;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public String getUnitCode() {
		return unitCode;
	}


	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
}
