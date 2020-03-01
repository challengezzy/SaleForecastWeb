package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserProOrg;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUserProOrg;
import dmdd.dmddjava.dm.OperatorUserDM;


public class UtilOperatoruser 
{
	/**
	 * 导入明细用户业务范围
	 */
	public static void adddetailProOrgsToOperatorUser(OperatorUser _operatorUser,List<AProOrg> _arrProOrg)
	{
		if( _arrProOrg == null )
		{
			return;
		}
		
		if( _operatorUser == null )
		{
			return;
		}
		
		OperatorUserDM dm = new OperatorUserDM();
		List<AProOrg> arrProOrg_permission;
		try {
			arrProOrg_permission = dm.getPermissionProOrgs( _operatorUser.getId() );
		
		List<AProOrg> arrProOrg_rst = UtilProOrg.getUnion4arrProOrgByDetail( arrProOrg_permission, _arrProOrg );
		//消除掉已有的
		Set<OperatorUserProOrg> set4proorgexist = _operatorUser.getOperatorUserProOrgs();
		List<String> list4proorgexist = new ArrayList<String>();
		List<AProOrg> arrProOrg_del= new ArrayList<AProOrg>();
		for(OperatorUserProOrg operatorUserProOrg:set4proorgexist)
			list4proorgexist.add(operatorUserProOrg.getProduct().getCode()+"-"+operatorUserProOrg.getOrganization().getCode());
		for(AProOrg proOrg:arrProOrg_rst)
		{
			if(list4proorgexist.contains(proOrg.getProduct().getCode()+"-"+proOrg.getOrganization().getCode()))
				arrProOrg_del.add(proOrg);
		}
		arrProOrg_rst.removeAll(arrProOrg_del);
		//去掉arrProOrg_rst中的重复的
		HashMap<String,AProOrg>	tempHashMap= new HashMap<String, AProOrg>();
		for(AProOrg proOrg:arrProOrg_rst)
			tempHashMap.put(proOrg.getProduct().getCode()+"-"+proOrg.getOrganization().getCode(), proOrg);
		arrProOrg_rst = new ArrayList<AProOrg>(tempHashMap.values());
		if( arrProOrg_rst != null )
		{
			Transaction trsa=null;
			Session session = null;
			try
			{
				session = HibernateSessionFactory.getSession();
				trsa = session.beginTransaction();				
				for(AProOrg proOrg:arrProOrg_rst)
				{	
					
						DaoOperatorUserProOrg dao= new DaoOperatorUserProOrg(session);
						OperatorUserProOrg operatorUserProOrg = new OperatorUserProOrg();
						operatorUserProOrg.setProduct(proOrg.getProduct());
						operatorUserProOrg.setOrganization(proOrg.getOrganization());
						operatorUserProOrg.setOperatorUser( _operatorUser);
						dao.save(operatorUserProOrg);
						_operatorUser.getOperatorUserProOrgs().add( operatorUserProOrg );
						
				}	
				trsa.commit();	
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
					if( session != null && session.isOpen() )
					{
						session.close();
					}
				}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 把 _arrProOrg 加入到 _operatorUser 的权限列表
	 */ 
	public static void addProOrgsToOperatorUser( OperatorUser _operatorUser,List<AProOrg> _arrProOrg)
	{
		if( _arrProOrg == null )
		{
			return;
		}
		
		if( _operatorUser == null )
		{
			return;
		}
	
	try
	{
	
		OperatorUserDM dm = new OperatorUserDM();
		List<AProOrg> arrProOrg_permission= dm.getPermissionProOrgs( _operatorUser.getId() );
		List<AProOrg> arrProOrg_rst = UtilProOrg.getUnion4arrProOrg( arrProOrg_permission, _arrProOrg );
		//消除掉已有的
		HashMap<String,AProOrg>  list4proorgexist= new HashMap<String,AProOrg>();		
		
		for(AProOrg proOrg:arrProOrg_rst)
		{
			list4proorgexist.put(proOrg.getProduct().getCode()+"-"+proOrg.getOrganization().getCode(),proOrg);
		}
		;
		if( arrProOrg_rst != null )
		{
			dm.addProOrg4User(list4proorgexist.values(), _operatorUser.getId());
//			Transaction trsa=null;
//			Session session = null;
//			try
//			{
//				session = HibernateSessionFactory.getSession();
//				trsa = session.beginTransaction();				
//				for(AProOrg proOrg:arrProOrg_rst)
//				{	
//					
//						DaoOperatorUserProOrg dao= new DaoOperatorUserProOrg(session);
//						OperatorUserProOrg operatorUserProOrg = new OperatorUserProOrg();
//						operatorUserProOrg.setProduct(proOrg.getProduct());
//						operatorUserProOrg.setOrganization(proOrg.getOrganization());
//						operatorUserProOrg.setOperatorUser( _operatorUser);
//						dao.save(operatorUserProOrg);
//						_operatorUser.getOperatorUserProOrgs().add( operatorUserProOrg );
//						
//				}	
//				trsa.commit();	
//				}
//				catch( Exception ex )
//				{
//					if( trsa != null )
//					{
//						trsa.rollback();
//					}
//					ex.printStackTrace();
//				}
//				finally
//				{
//					if( session != null && session.isOpen() )
//					{
//						session.close();
//					}
//			
		}
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}	

}
