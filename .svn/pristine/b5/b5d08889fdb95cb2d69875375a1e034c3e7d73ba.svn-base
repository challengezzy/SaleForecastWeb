/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserBizData;

/**
 * @author liuzhen
 *
 */
public class DaoOperatorUserBizData extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOperatorUserBizData( Session _session )
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

	public BOperatorUser getOperatorUserBizDatas(BOperatorUser operatorUser)
	{
		SQLQuery query = this.getSession().createSQLQuery("select ismanaging,code,name "+
					  " from operatoruser_bizdata, bizdata "+
					  " where operatoruser_bizdata.bizdataid = bizdata.id"+
					  "  and operatoruser_bizdata.operatoruserid = "+operatorUser.getId());
		query.addScalar("ismanaging",new org.hibernate.type.IntegerType());
		query.addScalar("code",new org.hibernate.type.StringType());
		query.addScalar("name",new org.hibernate.type.StringType());
		List result = query.list();
		for(Object obj:result)
		{
			Object[] objs = (Object[])obj;
			BBizData biz = new BBizData();
			BOperatorUserBizData op_biz = new BOperatorUserBizData();
			op_biz.setOperatorUser(operatorUser);
		
			if(objs[0]!=null)
				op_biz.setIsManaging((Integer)objs[0]);
			if(objs[1]!=null)
				biz.setCode((String)objs[1]);
			if(objs[2]!=null)
				biz.setName((String)objs[2]);
			op_biz.setBizData(biz);		
			operatorUser.addOperatorUserBizData(op_biz);
		}
		return operatorUser;
	}
}
