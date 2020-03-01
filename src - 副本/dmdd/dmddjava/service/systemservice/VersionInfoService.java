package dmdd.dmddjava.service.systemservice;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoVersionInfo;

public class VersionInfoService {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}
	
	
	public String getBuildNo()
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		String versioninfo = "";
		try
		{
			trsa = session.beginTransaction();
			DaoVersionInfo dao = new DaoVersionInfo(session);
			versioninfo = dao.getBuildNo();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		
		return versioninfo;
	}

}
