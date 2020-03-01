package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BBulletinOperatorUser implements Serializable {
	
	public final static long serialVersionUID = -1020000011;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBulletin bulletin;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** full constructor */
    public BBulletinOperatorUser(Long version, dmdd.dmddjava.dataaccess.bizobject.BBulletin bulletin, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.version = version;
        this.bulletin = bulletin;
        this.operatorUser = operatorUser;
    }

    /** default constructor */
    public BBulletinOperatorUser() {
    }

    /** minimal constructor */
    public BBulletinOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BBulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBulletin getBulletin() {
        return this.bulletin;
    }

    public void setBulletin(dmdd.dmddjava.dataaccess.bizobject.BBulletin bulletin) {
        this.bulletin = bulletin;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public String toString() {
        return "" + this.id;
    }

}
