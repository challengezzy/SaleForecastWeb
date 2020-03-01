package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cool.common.vo.HashVO;

public class BDistributionCenter implements Serializable {
	private static final long serialVersionUID = 373780487305197581L;
	private Long id;
	private String code;
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int iscatalog;
	private int isvalid;
	private Long dclayer;
	private Long parentdcid;
	private String pathcode;
	private String detailaddress;
	private String description;
	private String comments;
	private Long version;
	private List<BDistributionCenter> subDCs = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getIscatalog() {
		return iscatalog;
	}

	public void setIscatalog(int iscatalog) {
		this.iscatalog = iscatalog;
	}

	public int getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}

	public Long getDclayer() {
		return dclayer;
	}

	public void setDclayer(Long dclayer) {
		this.dclayer = dclayer;
	}

	public Long getParentdcid() {
		return parentdcid;
	}

	public void setParentdcid(Long parentdcid) {
		this.parentdcid = parentdcid;
	}

	public String getPathcode() {
		return pathcode;
	}

	public void setPathcode(String pathcode) {
		this.pathcode = pathcode;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	//将HashVO转换成B类
	public void convert(HashVO vo)
	{
		this.id=vo.getLongValue("id");
		this.code=vo.getStringValue("code");
		this.name=vo.getStringValue("name");
		this.iscatalog=vo.getIntegerValue("iscatalog");
		this.isvalid=vo.getIntegerValue("isvalid");
		this.dclayer=vo.getLongValue("dclayer");
		this.parentdcid=vo.getLongValue("parentdcid");
		this.pathcode=vo.getStringValue("pathcode");
		this.detailaddress=vo.getStringValue("detailaddress");
		this.description=vo.getStringValue("description");
		this.comments=vo.getStringValue("comments");
		this.version=vo.getLongValue("version");
		
	}

	public List<BDistributionCenter> getSubDCs() {
		return subDCs;
	}

	public void setSubDCs(List<BDistributionCenter> subDCs) {
		this.subDCs = subDCs;
	}
	
	public void addDC(BDistributionCenter dc)
	{
		if(subDCs==null)
			new ArrayList<BDistributionCenter>();
		subDCs.add(dc);
	}
}

