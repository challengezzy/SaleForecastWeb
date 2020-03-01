package dmdd.dmddjava.dataaccess.entity;

import java.io.Serializable;
import java.util.Set;

import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeProOrg;

public class BUiPopbScope implements Serializable {

	public final static long serialVersionUID = -1210000001;
	private Long id;
	private String uiCode;
	private String code;
	private String name;
	private int isDefault;
	private int isPeriodControl;
	private int periodOffsetBegin;
	private int periodOffsetEnd;
	private int isDisplayControl;
	private int isShowProduct;
	private int isShowProductCharacter;
	private String productCharacterType;
	private int isShowOrganization;
	private int isShowOrganizationCharacter;
	private String organizationCharacterType;
	private String description;
	private String comments;
	private Long version;
	private Long productLayerId;
	private Long productCharacterLayerId;
	private Long organizationCharacterLayerId;
	private Long operatorUserId;
	private Long organizationLayerId;
	private Set<BUiPopbScopeBizData> uiPopbScopeBizDatas;
	private Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUiCode() {
		return uiCode;
	}
	public void setUiCode(String uiCode) {
		this.uiCode = uiCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public int getIsPeriodControl() {
		return isPeriodControl;
	}
	public void setIsPeriodControl(int isPeriodControl) {
		this.isPeriodControl = isPeriodControl;
	}
	public int getPeriodOffsetBegin() {
		return periodOffsetBegin;
	}
	public void setPeriodOffsetBegin(int periodOffsetBegin) {
		this.periodOffsetBegin = periodOffsetBegin;
	}
	public int getPeriodOffsetEnd() {
		return periodOffsetEnd;
	}
	public void setPeriodOffsetEnd(int periodOffsetEnd) {
		this.periodOffsetEnd = periodOffsetEnd;
	}
	public int getIsDisplayControl() {
		return isDisplayControl;
	}
	public void setIsDisplayControl(int isDisplayControl) {
		this.isDisplayControl = isDisplayControl;
	}
	public int getIsShowProduct() {
		return isShowProduct;
	}
	public void setIsShowProduct(int isShowProduct) {
		this.isShowProduct = isShowProduct;
	}
	public int getIsShowProductCharacter() {
		return isShowProductCharacter;
	}
	public void setIsShowProductCharacter(int isShowProductCharacter) {
		this.isShowProductCharacter = isShowProductCharacter;
	}
	public String getProductCharacterType() {
		return productCharacterType;
	}
	public void setProductCharacterType(String productCharacterType) {
		this.productCharacterType = productCharacterType;
	}
	public int getIsShowOrganization() {
		return isShowOrganization;
	}
	public void setIsShowOrganization(int isShowOrganization) {
		this.isShowOrganization = isShowOrganization;
	}
	public int getIsShowOrganizationCharacter() {
		return isShowOrganizationCharacter;
	}
	public void setIsShowOrganizationCharacter(int isShowOrganizationCharacter) {
		this.isShowOrganizationCharacter = isShowOrganizationCharacter;
	}
	public String getOrganizationCharacterType() {
		return organizationCharacterType;
	}
	public void setOrganizationCharacterType(String organizationCharacterType) {
		this.organizationCharacterType = organizationCharacterType;
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
	public Long getProductLayerId() {
		return productLayerId;
	}
	public void setProductLayerId(Long productLayerId) {
		this.productLayerId = productLayerId;
	}
	public Long getProductCharacterLayerId() {
		return productCharacterLayerId;
	}
	public void setProductCharacterLayerId(Long productCharacterLayerId) {
		this.productCharacterLayerId = productCharacterLayerId;
	}
	public Long getOrganizationCharacterLayerId() {
		return organizationCharacterLayerId;
	}
	public void setOrganizationCharacterLayerId(Long organizationCharacterLayerId) {
		this.organizationCharacterLayerId = organizationCharacterLayerId;
	}
	public Long getOperatorUserId() {
		return operatorUserId;
	}
	public void setOperatorUserId(Long operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	public Long getOrganizationLayerId() {
		return organizationLayerId;
	}
	public void setOrganizationLayerId(Long organizationLayerId) {
		this.organizationLayerId = organizationLayerId;
	}
	public Set<BUiPopbScopeBizData> getUiPopbScopeBizDatas() {
		return uiPopbScopeBizDatas;
	}
	public void setUiPopbScopeBizDatas(Set<BUiPopbScopeBizData> uiPopbScopeBizDatas) {
		this.uiPopbScopeBizDatas = uiPopbScopeBizDatas;
	}
	public Set<BUiPopbScopeProOrg> getUiPopbScopeProOrgs() {
		return uiPopbScopeProOrgs;
	}
	public void setUiPopbScopeProOrgs(Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs) {
		this.uiPopbScopeProOrgs = uiPopbScopeProOrgs;
	}
}