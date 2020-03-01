package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BUiPopbScope implements Serializable {
	
	public final static long serialVersionUID = -1210000001;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String uiCode;

    /** persistent field */
    private String code;

    /** nullable persistent field */
    private String name;

    /** persistent field */
    private int isDefault;

    /** persistent field */
    private int isPeriodControl;

    /** persistent field */
    private int periodOffsetBegin;

    /** persistent field */
    private int periodOffsetEnd;

    /** persistent field */
    private int isDisplayControl;

    /** persistent field */
    private int isShowProduct;

    /** persistent field */
    private int isShowProductCharacter;

    /** nullable persistent field */
    private String productCharacterType;

    /** persistent field */
    private int isShowOrganization;

    /** persistent field */
    private int isShowOrganizationCharacter;

    /** nullable persistent field */
    private String organizationCharacterType;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer productCharacterLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer organizationLayer;

    /** persistent field */
    private Set<BUiPopbScopeBizData> uiPopbScopeBizDatas;

    /** persistent field */
    private Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs;
    private Long productLayerId;
	private Long productCharacterLayerId;
	private Long organizationCharacterLayerId;
	private Long operatorUserId;
	private Long organizationLayerId;
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

	/** full constructor */
    public BUiPopbScope(String uiCode, String code, String name, int isDefault, int isPeriodControl, int periodOffsetBegin, int periodOffsetEnd, int isDisplayControl, int isShowProduct, int isShowProductCharacter, String productCharacterType, int isShowOrganization, int isShowOrganizationCharacter, String organizationCharacterType, String description, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer, dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer productCharacterLayer, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser, dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer organizationLayer, Set<BUiPopbScopeBizData> uiPopbScopeBizDatas, Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs) {
        this.uiCode = uiCode;
        this.code = code;
        this.name = name;
        this.isDefault = isDefault;
        this.isPeriodControl = isPeriodControl;
        this.periodOffsetBegin = periodOffsetBegin;
        this.periodOffsetEnd = periodOffsetEnd;
        this.isDisplayControl = isDisplayControl;
        this.isShowProduct = isShowProduct;
        this.isShowProductCharacter = isShowProductCharacter;
        this.productCharacterType = productCharacterType;
        this.isShowOrganization = isShowOrganization;
        this.isShowOrganizationCharacter = isShowOrganizationCharacter;
        this.organizationCharacterType = organizationCharacterType;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.productLayer = productLayer;
        this.productCharacterLayer = productCharacterLayer;
        this.organizationCharacterLayer = organizationCharacterLayer;
        this.operatorUser = operatorUser;
        this.organizationLayer = organizationLayer;
        this.uiPopbScopeBizDatas = uiPopbScopeBizDatas;
        this.uiPopbScopeProOrgs = uiPopbScopeProOrgs;
    }

    /** default constructor */
    public BUiPopbScope() {
    }

    /** minimal constructor */
    public BUiPopbScope(String uiCode, String code, int isDefault, int isPeriodControl, int periodOffsetBegin, int periodOffsetEnd, int isDisplayControl, int isShowProduct, int isShowProductCharacter, int isShowOrganization, int isShowOrganizationCharacter, Set<BUiPopbScopeBizData> uiPopbScopeBizDatas, Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs) {
        this.uiCode = uiCode;
        this.code = code;
        this.isDefault = isDefault;
        this.isPeriodControl = isPeriodControl;
        this.periodOffsetBegin = periodOffsetBegin;
        this.periodOffsetEnd = periodOffsetEnd;
        this.isDisplayControl = isDisplayControl;
        this.isShowProduct = isShowProduct;
        this.isShowProductCharacter = isShowProductCharacter;
        this.isShowOrganization = isShowOrganization;
        this.isShowOrganizationCharacter = isShowOrganizationCharacter;
        this.uiPopbScopeBizDatas = uiPopbScopeBizDatas;
        this.uiPopbScopeProOrgs = uiPopbScopeProOrgs;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUiCode() {
        return this.uiCode;
    }

    public void setUiCode(String uiCode) {
        this.uiCode = uiCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsPeriodControl() {
        return this.isPeriodControl;
    }

    public void setIsPeriodControl(int isPeriodControl) {
        this.isPeriodControl = isPeriodControl;
    }

    public int getPeriodOffsetBegin() {
        return this.periodOffsetBegin;
    }

    public void setPeriodOffsetBegin(int periodOffsetBegin) {
        this.periodOffsetBegin = periodOffsetBegin;
    }

    public int getPeriodOffsetEnd() {
        return this.periodOffsetEnd;
    }

    public void setPeriodOffsetEnd(int periodOffsetEnd) {
        this.periodOffsetEnd = periodOffsetEnd;
    }

    public int getIsDisplayControl() {
        return this.isDisplayControl;
    }

    public void setIsDisplayControl(int isDisplayControl) {
        this.isDisplayControl = isDisplayControl;
    }

    public int getIsShowProduct() {
        return this.isShowProduct;
    }

    public void setIsShowProduct(int isShowProduct) {
        this.isShowProduct = isShowProduct;
    }

    public int getIsShowProductCharacter() {
        return this.isShowProductCharacter;
    }

    public void setIsShowProductCharacter(int isShowProductCharacter) {
        this.isShowProductCharacter = isShowProductCharacter;
    }

    public String getProductCharacterType() {
        return this.productCharacterType;
    }

    public void setProductCharacterType(String productCharacterType) {
        this.productCharacterType = productCharacterType;
    }

    public int getIsShowOrganization() {
        return this.isShowOrganization;
    }

    public void setIsShowOrganization(int isShowOrganization) {
        this.isShowOrganization = isShowOrganization;
    }

    public int getIsShowOrganizationCharacter() {
        return this.isShowOrganizationCharacter;
    }

    public void setIsShowOrganizationCharacter(int isShowOrganizationCharacter) {
        this.isShowOrganizationCharacter = isShowOrganizationCharacter;
    }

    public String getOrganizationCharacterType() {
        return this.organizationCharacterType;
    }

    public void setOrganizationCharacterType(String organizationCharacterType) {
        this.organizationCharacterType = organizationCharacterType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProductLayer getProductLayer() {
        return this.productLayer;
    }

    public void setProductLayer(dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer) {
        this.productLayer = productLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer getProductCharacterLayer() {
        return this.productCharacterLayer;
    }

    public void setProductCharacterLayer(dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer productCharacterLayer) {
        this.productCharacterLayer = productCharacterLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer getOrganizationCharacterLayer() {
        return this.organizationCharacterLayer;
    }

    public void setOrganizationCharacterLayer(dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer) {
        this.organizationCharacterLayer = organizationCharacterLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer getOrganizationLayer() {
        return this.organizationLayer;
    }

    public void setOrganizationLayer(dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer organizationLayer) {
        this.organizationLayer = organizationLayer;
    }

    public Set<BUiPopbScopeBizData> getUiPopbScopeBizDatas() {
        return this.uiPopbScopeBizDatas;
    }

    public void setUiPopbScopeBizDatas(Set<BUiPopbScopeBizData> uiPopbScopeBizDatas) {
        this.uiPopbScopeBizDatas = uiPopbScopeBizDatas;
    }
    
    public void addUiPopbScopeBizData(BUiPopbScopeBizData _uiPopbScopeBizData) {
    	if( _uiPopbScopeBizData == null )
    	{
    		return;
    	}
    	if( this.uiPopbScopeBizDatas == null )
    	{
    		this.uiPopbScopeBizDatas = new HashSet<BUiPopbScopeBizData>();
    	}
    	_uiPopbScopeBizData.setUiPopbScope( this );
        this.uiPopbScopeBizDatas.add( _uiPopbScopeBizData );
    }     

    public Set<BUiPopbScopeProOrg> getUiPopbScopeProOrgs() {
        return this.uiPopbScopeProOrgs;
    }

    public void setUiPopbScopeProOrgs(Set<BUiPopbScopeProOrg> uiPopbScopeProOrgs) {
        this.uiPopbScopeProOrgs = uiPopbScopeProOrgs;
    }

    public void addUiPopbScopeProOrg(BUiPopbScopeProOrg _uiPopbScopeProOrg) {
    	if( _uiPopbScopeProOrg == null )
    	{
    		return;
    	}
    	if( this.uiPopbScopeProOrgs == null )
    	{
    		this.uiPopbScopeProOrgs = new HashSet<BUiPopbScopeProOrg>();
    	}
    	_uiPopbScopeProOrg.setUiPopbScope( this );
        this.uiPopbScopeProOrgs.add( _uiPopbScopeProOrg );
    } 
    
    public String toString() {
        return "" + this.id;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		BUiPopbScope other = (BUiPopbScope) obj;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		return true;
	}

}
