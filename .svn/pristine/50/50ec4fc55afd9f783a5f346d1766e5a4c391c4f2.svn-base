package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class SysDictionaryItem implements Serializable {
	
	public final static long serialVersionUID = -1190000001;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String className;

    /** persistent field */
    private String attributeName;

    /** persistent field */
    private int value;

    /** persistent field */
    private String valueDesc;

    /** nullable persistent field */
    private Long version;

    /** full constructor */
    public SysDictionaryItem(String className, String attributeName, int value, String valueDesc, Long version) {
        this.className = className;
        this.attributeName = attributeName;
        this.value = value;
        this.valueDesc = valueDesc;
        this.version = version;
    }

    /** default constructor */
    public SysDictionaryItem() {
    }

    /** minimal constructor */
    public SysDictionaryItem(String className, String attributeName, int value, String valueDesc) {
        this.className = className;
        this.attributeName = attributeName;
        this.value = value;
        this.valueDesc = valueDesc;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValueDesc() {
        return this.valueDesc;
    }

    public void setValueDesc(String valueDesc) {
        this.valueDesc = valueDesc;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String toString() {
        return this.valueDesc;
    }

}
