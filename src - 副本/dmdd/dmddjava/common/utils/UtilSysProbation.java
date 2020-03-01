package dmdd.dmddjava.common.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.SysParamBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.dataobject.SysParam;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysParam;

public class UtilSysProbation 
{
	
	/**
	 * 减少一天试用
	 */
	public static void ReductionDay()
	{
		//先得到参数
		Transaction trsa = null;
		Session session = HibernateSessionFactory.getSession();
		BSysParam sysParam4Probation=null;
		try
		{
			trsa = session.beginTransaction();
			DaoSysParam daoSysParam = new DaoSysParam( session );
			List<SysParam> sysPamramList_inDB = daoSysParam.getAllSysParams();
			if( sysPamramList_inDB != null )
			{
				SysParamBDConvertor sysParamBDConvertor = new SysParamBDConvertor();
				for( int i=0; i<sysPamramList_inDB.size(); i++ )
				{
					if(sysPamramList_inDB.get( i ).getCode().equals(BizConst.SYSPARAM_CODE_PROBATION))
					{
						sysParam4Probation=(BSysParam)sysParamBDConvertor.dtob(sysPamramList_inDB.get( i ));
						break;
					}
				}
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
			session.close();
		}	
		if(sysParam4Probation==null)
			return ;
		String value=sysParam4Probation.getValue();
		session = HibernateSessionFactory.getSession();
		try	
		{
			trsa = session.beginTransaction();
			int num = Integer.parseInt(value) -1 ;
			if(num<0)//试用到期
			{
				System.out.println("Trial due!");
				System.exit(0);
			}
			
			String up_des = UtilMD5.getMD5Str(Integer.toString(num)+"dmdd");
			sysParam4Probation.setValue(Integer.toString(num));
			sysParam4Probation.setDescription(up_des);
			SysParamBDConvertor convertor = new SysParamBDConvertor();			
			SysParam sysparam= (SysParam)convertor.btod(sysParam4Probation);
			DaoSysParam dao = new DaoSysParam(session);
			dao.update(sysparam);
			trsa.commit();
			
			//设置环境变量保持一致
			ServerEnvironment.getInstance().putSysParam(sysParam4Probation);
			System.out.println("UtilSysProbation:Reduce the time of day Trial");
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
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

}
