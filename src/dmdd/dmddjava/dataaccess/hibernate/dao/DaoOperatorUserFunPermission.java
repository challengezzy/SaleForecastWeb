/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import dmdd.dmddjava.dataaccess.bizobject.BFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserFunPermission;

/**
 * @author liuzhen
 *
 */
public class DaoOperatorUserFunPermission extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOperatorUserFunPermission( Session _session )
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
	
	public BOperatorUser getOperatorUserFunPermissions(BOperatorUser operatorUser)
	{
		SQLQuery query = this.getSession().createSQLQuery("select code,name,description,funpermission.id as id from operatoruser_funpermission ,funpermission "+
				"where  operatoruser_funpermission.funpermissionid = funpermission.id and operatoruser_funpermission.operatoruserid  ="+operatorUser.getId());
		query.addScalar("code",new org.hibernate.type.StringType());
		query.addScalar("name",new org.hibernate.type.StringType());
		query.addScalar("description",new org.hibernate.type.StringType());
		query.addScalar("id",new org.hibernate.type.LongType());		
		
		List result = query.list();
		BFunPermission fun ;
		for(Object obj:result)
		{
			Object[] objs = (Object[])obj;
			fun = new BFunPermission();
			if(objs[0]!=null)
				fun.setCode((String)objs[0]);
			if(objs[1]!=null)
				fun.setName((String)objs[1]);
			if(objs[2]!=null)
				fun.setDescription((String)objs[2]);
			if(objs[3]!=null)
				fun.setId((Long)objs[3]);
			BOperatorUserFunPermission op_fun = new BOperatorUserFunPermission();
			op_fun.setOperatorUser(operatorUser);
			op_fun.setFunPermission(fun);	
			operatorUser.addOperatorUserFunPermission(op_fun);
		}
		
		return operatorUser;
	}
}
