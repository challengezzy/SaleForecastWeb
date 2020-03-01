/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserProOrg;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;

/**
 * @author liuzhen
 *
 */
public class DaoOperatorUserProOrg extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOperatorUserProOrg( Session _session )
	{
		super( _session );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	public BOperatorUser getOperatorUserProOrgs(BOperatorUser operatorUser)
	{		
		SQLQuery query = this.getSession().createSQLQuery("select productid, organizationid " +
				"FROM operatoruser_proorg  where operatoruserid ="+operatorUser.getId());
		query.addScalar("productid",new org.hibernate.type.LongType());
		query.addScalar("organizationid",new org.hibernate.type.LongType());
		List result = query.list();
		Long productid;
		Long organizationid;
		for(Object obj:result)
		{
			Object[] objs = (Object[])obj;
			productid = (Long)objs[0];
			organizationid = (Long)objs[1];
			BOperatorUserProOrg op = new BOperatorUserProOrg();
			op.setOperatorUser(operatorUser);
			BProduct pro = ServerEnvironment.getInstance().getBProduct(productid);
			BOrganization org = ServerEnvironment.getInstance().getBOrganization(organizationid);
			op.setProduct(pro);
			op.setOrganization(org);
			operatorUser.addOperatorUserProOrg(op);
		}
		return operatorUser;
	}
	
}
