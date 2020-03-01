/**********************************************************************
 *$RCSfile:DaoSysBakLog.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.SysBakLog;

/**
 * <li>Title: DaoSysBakLog.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class DaoSysBakLog extends Dao
{

	public DaoSysBakLog( Session session )
	{
		
		super( session );
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}
	
	public List<SysBakLog> getAllSysBakLog()
	{
		Criteria crit = this.getSession().createCriteria( SysBakLog.class );
		List<SysBakLog>	rstList = new ArrayList<SysBakLog>();
				
		crit.addOrder( Order.desc( "excuteTime" ) );
		rstList = crit.list();
		
		return rstList;
	}

}

/**********************************************************************
 *$RCSfile:DaoSysBakLog.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *
 *$Log:DaoSysBakLog.java,v $
 *********************************************************************/