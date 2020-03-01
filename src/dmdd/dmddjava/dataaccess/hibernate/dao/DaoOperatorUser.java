/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;

/**
 * @author liuzhen
 *
 */
public class DaoOperatorUser extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOperatorUser( Session _session )
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


	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @param _operatorUserId
	 * @return
	 */
	public int getOperatorUsersStat(String _sqlRestriction, Long _operatorUserId)
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		if( _sqlRestriction == null || _sqlRestriction.trim().equals( "" ) )
		{
			//	这里这样写是因为 start with 语句不能直接跟在 where 后面
			_sqlRestriction = " (1=1) ";
		}
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			if( _operatorUserId != null )
			{
				_sqlRestriction = _sqlRestriction + " start with id = " + _operatorUserId + " connect by prior id = superioruserid ";
			}
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
			crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
	
			Integer countValue = (Integer) crit.uniqueResult();
			
			return countValue;
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			
			List<OperatorUser> list =  getOperatorUsersByRestrAndID(_sqlRestriction,_operatorUserId);
			return list.size();
			
		}
		return 0;
	}	
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _operatorUserId
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public List<OperatorUser> getOperatorUsers(String _sqlRestriction, Long _operatorUserId, int _pageIndex, int _pageSize)
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		
		if( _sqlRestriction == null || _sqlRestriction.trim().equals( "" ) )
		{
			//	这里这样写是因为 start with 语句不能直接跟在 where 后面
			_sqlRestriction = " (1=1) ";
		}
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			if( _operatorUserId != null )
			{
				_sqlRestriction = _sqlRestriction + " and start with id = " + _operatorUserId + " connect by prior id = superioruserid ";
			}
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
			
			if( _pageIndex > 0 )
			{
				// 分页查询
				crit.setFirstResult( (_pageIndex-1) * _pageSize );
				crit.setMaxResults( _pageSize );
			}
	 		
			List<OperatorUser> rstList = crit.list();
			return rstList;
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			List<OperatorUser> list =  getOperatorUsersByRestrAndID(_sqlRestriction,_operatorUserId);
			List<OperatorUser> rstlist = new ArrayList<OperatorUser>();
			if(_pageIndex!=-1)//不分页
			{
				for(int i=(_pageIndex-1) * _pageSize ;i<_pageIndex*_pageSize ;i++)
				{
					if(list.size()>i)
					{
						rstlist.add(list.get(i));
					}
					else
					{
						break;
					}
				}
			}
			else
			{
				return list;
			}
			return rstlist;
		}
		return null;
	}	
	
	/**不查询关联表
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _operatorUserId
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public List<OperatorUser> getOperatorUsers2(String _sqlRestriction, Long _operatorUserId, int _pageIndex, int _pageSize)
	{
//		if( _sqlRestriction == null || _sqlRestriction.trim().equals( "" ) )
//		{
//			//	这里这样写是因为 start with 语句不能直接跟在 where 后面
//			_sqlRestriction = " (1=1) ";
//		}
//		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
//		{
//			if( _operatorUserId != null )
//			{
//				_sqlRestriction = _sqlRestriction + " and start with id = " + _operatorUserId + " connect by prior id = superioruserid ";
//			}
//			Query query = this.getSession().createQuery( "select new OperatorUser(id,loginName,userName,userExpiryTime,pwdExpiryTime,isValid,isOnline) from OperatorUser where"+_sqlRestriction  );
//			
//			if( _pageIndex > 0 )
//			{
//				// 分页查询
//				query.setFirstResult( (_pageIndex-1) * _pageSize );
//				query.setMaxResults( _pageSize );
//			}
//	 		
//			List<OperatorUser> rstList = query.list();
//			return rstList;
//		}
//		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER))
//		{
			List<OperatorUser> list =  getOperatorUsersByRestrAndID2(_sqlRestriction,_operatorUserId);
			List<OperatorUser> rstlist = new ArrayList<OperatorUser>();
			if(_pageIndex!=-1)//不分页
			{
				for(int i=(_pageIndex-1) * _pageSize ;i<_pageIndex*_pageSize ;i++)
				{
					if(list.size()>i)
					{
						rstlist.add(list.get(i));
					}
					else
					{
						break;
					}
				}
			}
			else
			{
				return list;
			}
			return rstlist;
//		}
//		return null;
	}	
	
	public OperatorUser getOperatorUserById(Long _id)
	{
		Object obj = this.getSession().get(OperatorUser.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (OperatorUser) obj;
	}
	
	public OperatorUser getOperatorUser(Long _id)
	{
//		Object obj = this.getSession().get(OperatorUser.class, _id);
//		if( obj == null )
//		{
//			return null;
//		}
//		
//		return (OperatorUser) obj;
		
		Query query = this.getSession().createQuery("select new OperatorUser(U.id,"+
					"U.loginName,"+
					"U.password,"+
					"U.userName,"+
					"U.creatorName,"+
					"U.createdTime,"+
					"U.userExpiryTime,"+
					"U.pwdExpiryTime,"+
					"U.isValid,"+
					"U.position,"+
					"U.officeAddress,"+
					"U.homeAddress,"+
					"U.telNo,"+
					"U.mobileNo,"+
					"U.email,"+
					"U.isOnline,"+
					"U.loginTime,"+
					"U.logoutTime,"+
					"U.loginTimes,"+
					"U.comments,"+
					"U.version,U.superiorOperatorUser) from OperatorUser U where U.id="+_id);
							
		List<OperatorUser> rest  = query.list();
		return rest.get(0);
	}	
	
	public OperatorUser getOperatorUser( String _loginName, String _password )
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		crit.add( Restrictions.eq( "loginName", _loginName ) );
		crit.add( Restrictions.eq( "password", _password ) );
		return (OperatorUser) crit.uniqueResult();		
	}	
	
	public OperatorUser getOperatorUser( String _loginName )
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		crit.add( Restrictions.eq( "loginName", _loginName ) );
		return (OperatorUser) crit.uniqueResult();		
	}	
	
	public OperatorUser getOperatorUserByUserName( String _userName )
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		crit.add( Restrictions.eq( "userName", _userName ) );
		return (OperatorUser) crit.uniqueResult();		
	}	
	
	public List<OperatorUser> getOperatorUsersByRestrAndID(String _sqlRestriction, Long _operatorUserId)
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		List<OperatorUser>	rstList = new ArrayList<OperatorUser>();
		String sqlRestriction= "";
		if( _operatorUserId != null )
		{
			OperatorUser _operatorUser = getOperatorUserByRestrAndID(_sqlRestriction,_operatorUserId);
			if(_operatorUser!=null)
			{
				rstList.add(_operatorUser);
			}
			sqlRestriction=" superioruserid = "+_operatorUserId;
		}
		else
		{
			sqlRestriction=" superioruserid is  null";
		}
		
		crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
		List<OperatorUser> _SubList = crit.list();
		for(OperatorUser subOperatorUser:_SubList)
		{
			rstList.addAll(getOperatorUsersByRestrAndID(_sqlRestriction,subOperatorUser.getId()));
		}
		return rstList;
		
	}
	
	public OperatorUser getOperatorUserByRestrAndID(String _sqlRestriction, Long _operatorUserId)
	{
		Criteria crit = this.getSession().createCriteria( OperatorUser.class );
		
		if( _sqlRestriction == null || _sqlRestriction.trim().equals( "" ) )
		{
			_sqlRestriction = " (1=1) ";
		}
		if( _operatorUserId != null )
		{
			_sqlRestriction = _sqlRestriction + " and id = " + _operatorUserId ;
		}
		crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		return (OperatorUser) crit.uniqueResult();			
	}
	
	public List<OperatorUser> getOperatorUsersByRestrAndID2(String _sqlRestriction, Long _operatorUserId)
	{
		List<OperatorUser>	rstList = new ArrayList<OperatorUser>();
		String sqlRestriction= "";
		if( _operatorUserId != null )
		{
			OperatorUser _operatorUser = getOperatorUserByRestrAndID2(_sqlRestriction,_operatorUserId);
			if(_operatorUser!=null)
			{
				rstList.add(_operatorUser);
			}
			sqlRestriction=" superioruserid = "+_operatorUserId;
		}
		else
		{
			sqlRestriction=" superioruserid is  null";
		}
		
		Query query = this.getSession().createQuery( "select new OperatorUser(id,loginName,userName,userExpiryTime,pwdExpiryTime,isValid,isOnline) from OperatorUser where"+sqlRestriction  );
		List<OperatorUser> _SubList = query.list();
		for(OperatorUser subOperatorUser:_SubList)
		{
			rstList.addAll(getOperatorUsersByRestrAndID2(_sqlRestriction,subOperatorUser.getId()));
		}
		return rstList;
		
	}
	
	public OperatorUser getOperatorUserByRestrAndID2(String _sqlRestriction, Long _operatorUserId)
	{
	
		if( _sqlRestriction == null || _sqlRestriction.trim().equals( "" ) )
		{
			_sqlRestriction = " (1=1) ";
		}
		if( _operatorUserId != null )
		{
			_sqlRestriction = _sqlRestriction + " and id = " + _operatorUserId ;
		}
		Query query = this.getSession().createQuery( "select new OperatorUser(id,loginName,userName,userExpiryTime,pwdExpiryTime,isValid,isOnline) from OperatorUser where"+_sqlRestriction  );
		return (OperatorUser) query.uniqueResult();			
	}
	
}
