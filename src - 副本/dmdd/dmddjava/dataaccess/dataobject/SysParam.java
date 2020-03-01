package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class SysParam implements Serializable {
	
	public final static long serialVersionUID = -1190000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String value;

    /** persistent field */
    private int valueType;

    /** persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** full constructor */
    public SysParam(String code, String value, int valueType, String description, String comments, Long version) {
        this.code = code;
        this.value = value;
        this.valueType = valueType;
        this.description = description;
        this.comments = comments;
        this.version = version;
    }

    /** default constructor */
    public SysParam() {
    }

    /** minimal constructor */
    public SysParam(String code, String value, int valueType, String description) {
        this.code = code;
        this.value = value;
        this.valueType = valueType;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
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

    public String toString() {
        return this.code;
    }

}
