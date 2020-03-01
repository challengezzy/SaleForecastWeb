package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.math.BigDecimal;

import org.hibernate.Session;

public class DaoVersionInfo  extends Dao
{

	public DaoVersionInfo(Session session) 
	{
		super(session);
		
	}

	public String getBuildNo()
	{
		BigDecimal versionfinfo = (BigDecimal) this.getSession().createQuery("select a.buildNo from VersionInfo a where a.id = (select max(id) from VersionInfo)").uniqueResult();
		return versionfinfo.toString();
	}
	
}
