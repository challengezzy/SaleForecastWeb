/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @author liuzhen
 * 
 */
public class Dao {

	private Session session = null;

	/**
	 * 
	 */
	public Dao(Session _session) {
		this.session = _session;
	}

	public Session getSession() {
		return this.session;
	}

	public Object save(Object _toSaveObject) {
		this.session.save(_toSaveObject);
		return _toSaveObject;
	}

	public Object update(Object _toUpdObject) {
		this.session.update(_toUpdObject);
		return _toUpdObject;
	}

	public void delete(Object _toDelObject) {
		this.session.delete(_toDelObject);
	}

	public List<Object[]> queryBySql(String sql) {
		List<Object[]> list = getSession().createSQLQuery(sql).list();
		return list;
	}

	public int excuteBySql(String sql) {
		int result;
		SQLQuery query = getSession().createSQLQuery(sql);
		result = query.executeUpdate();
		
		return result;
	}

}

/*******************************************************************************
 * $RCSfile: Dao.java,v $ $Revision: 1.1 $ $Date: 2010/07/04 07:26:53 $
 * 
 * $Log: Dao.java,v $ Revision 1.1 2010/07/04 07:26:53 liuzhen 2010.07.04 by
 * liuzhen Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * 
 * Revision
 * 
 * 
 ******************************************************************************/
