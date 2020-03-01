package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BBulletin implements Serializable {
	
	public final static long serialVersionUID = -1020000010;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String title;

    /** nullable persistent field */
    private String summary;

    /** nullable persistent field */
    private String content;

    /** persistent field */
    private Date createTime;

    /** persistent field */
    private Date modifyTime;

    /** nullable persistent field */
    private Date publishTime;

    /** persistent field */
    private int isPublish;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** persistent field */
    private Set<BBulletinOperatorUser> bulletinOperatorUsers;

    /** full constructor */
    public BBulletin(String title, String summary, String content, Date createTime, Date modifyTime, Date publishTime, int isPublish, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser, Set<BBulletinOperatorUser> bulletinOperatorUsers) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.publishTime = publishTime;
        this.isPublish = isPublish;
        this.comments = comments;
        this.version = version;
        this.operatorUser = operatorUser;
        this.bulletinOperatorUsers = bulletinOperatorUsers;
    }

    /** default constructor */
    public BBulletin() {
    }

    /** minimal constructor */
    public BBulletin(String title, Date createTime, Date modifyTime, int isPublish, Set<BBulletinOperatorUser> bulletinOperatorUsers) {
        this.title = title;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.isPublish = isPublish;
        this.bulletinOperatorUsers = bulletinOperatorUsers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getIsPublish() {
        return this.isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
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

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public Set<BBulletinOperatorUser> getBulletinOperatorUsers() {
        return this.bulletinOperatorUsers;
    }

    public void setBulletinOperatorUsers(Set<BBulletinOperatorUser> bulletinOperatorUsers) {
        this.bulletinOperatorUsers = bulletinOperatorUsers;
    }
    
    public void addBulletinOperatorUser(BBulletinOperatorUser _bulletinOperatorUser) {
    	if( _bulletinOperatorUser == null )
    	{
    		return;
    	}
    	if( this.bulletinOperatorUsers == null )
    	{
    		this.bulletinOperatorUsers = new HashSet<BBulletinOperatorUser>();
    	}
    	_bulletinOperatorUser.setBulletin( this );
        this.bulletinOperatorUsers.add( _bulletinOperatorUser );
    }    
    

    public String toString() {
        return "" + this.id;
    }

}
