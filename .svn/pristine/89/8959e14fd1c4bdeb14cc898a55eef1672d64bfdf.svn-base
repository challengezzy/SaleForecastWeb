/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.FunPermission;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserBizData;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserFunPermission;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserProOrg;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class OperatorUserBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public OperatorUserBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 下附的集合属性subOperatorUsers,不处理  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BOperatorUser bOperatorUser = null;
		OperatorUser   operatorUser = null;
		
		if( b_obj == null )
		{
			bOperatorUser = new BOperatorUser();
		}
		else
		{
			bOperatorUser = (BOperatorUser)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			operatorUser = (OperatorUser)d_obj;
		}
		
		operatorUser.setVersion( bOperatorUser.getVersion() );
		operatorUser.setId( bOperatorUser.getId() );
		operatorUser.setLoginName( bOperatorUser.getLoginName() );
		operatorUser.setPassword( bOperatorUser.getPassword() );
		operatorUser.setUserName( bOperatorUser.getUserName() );
		operatorUser.setCreatorName( bOperatorUser.getCreatorName() );
		operatorUser.setCreatedTime( bOperatorUser.getCreatedTime() );
		operatorUser.setUserExpiryTime( bOperatorUser.getUserExpiryTime() );
		operatorUser.setPwdExpiryTime( bOperatorUser.getPwdExpiryTime() );
		operatorUser.setIsValid( bOperatorUser.getIsValid() );
		operatorUser.setPosition( bOperatorUser.getPosition() );
		operatorUser.setOfficeAddress( bOperatorUser.getOfficeAddress() );
		operatorUser.setHomeAddress( bOperatorUser.getHomeAddress() );
		operatorUser.setTelNo( bOperatorUser.getTelNo() );
		operatorUser.setMobileNo( bOperatorUser.getMobileNo() );
		operatorUser.setEmail( bOperatorUser.getEmail() );
		operatorUser.setIsOnline( bOperatorUser.getIsOnline() );
		operatorUser.setLoginTime( bOperatorUser.getLoginTime() );
		operatorUser.setLogoutTime( bOperatorUser.getLogoutTime() );
		operatorUser.setLoginTimes( bOperatorUser.getLoginTimes() );
		operatorUser.setComments( bOperatorUser.getComments() );	
		
		if( bOperatorUser.getSuperiorOperatorUser() != null  )
		{
			operatorUser.setSuperiorOperatorUser( this.btod( bOperatorUser.getSuperiorOperatorUser(), false ) );
		}
		else
		{
			operatorUser.setSuperiorOperatorUser( null );
		}

	}

	/* (non-Javadoc)
	 * 下附的集合属性subOperatorUsers,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		OperatorUser operatorUser = new OperatorUser();
		this.btod(b_obj, operatorUser);
		return operatorUser;
	}

	/* (non-Javadoc)
	 * 引用的对象属性superiorOperatorUser,不处理;
	 * 下附的集合属性subOperatorUsers,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		OperatorUser   operatorUser = null;
		BOperatorUser bOperatorUser = null;
		
		if( d_obj == null )
		{
			operatorUser = new OperatorUser();
		}
		else
		{
			operatorUser = (OperatorUser)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bOperatorUser = (BOperatorUser)b_obj;
		}
		
		bOperatorUser.setVersion( operatorUser.getVersion() );
		bOperatorUser.setId( operatorUser.getId() );
		bOperatorUser.setLoginName( operatorUser.getLoginName() );
		bOperatorUser.setPassword( operatorUser.getPassword() );
		bOperatorUser.setUserName( operatorUser.getUserName() );
		bOperatorUser.setCreatorName( operatorUser.getCreatorName() );
		bOperatorUser.setCreatedTime( operatorUser.getCreatedTime() );
		bOperatorUser.setUserExpiryTime( operatorUser.getUserExpiryTime() );
		bOperatorUser.setPwdExpiryTime( operatorUser.getPwdExpiryTime() );
		bOperatorUser.setIsValid( operatorUser.getIsValid() );
		bOperatorUser.setPosition( operatorUser.getPosition() );
		bOperatorUser.setOfficeAddress( operatorUser.getOfficeAddress() );
		bOperatorUser.setHomeAddress( operatorUser.getHomeAddress() );
		bOperatorUser.setTelNo( operatorUser.getTelNo() );
		bOperatorUser.setMobileNo( operatorUser.getMobileNo() );
		bOperatorUser.setEmail( operatorUser.getEmail() );
		bOperatorUser.setIsOnline( operatorUser.getIsOnline() );
		bOperatorUser.setLoginTime( operatorUser.getLoginTime() );
		bOperatorUser.setLogoutTime( operatorUser.getLogoutTime() );
		bOperatorUser.setLoginTimes( operatorUser.getLoginTimes() );
		bOperatorUser.setComments( operatorUser.getComments() );
		
		if( operatorUser.getSuperiorOperatorUser() != null  )
		{
			bOperatorUser.setSuperiorOperatorUser( this.dtob( operatorUser.getSuperiorOperatorUser(), false ) );
		}
		else
		{
			bOperatorUser.setSuperiorOperatorUser( null );
		}		

	}

	/* (non-Javadoc)
	 * 引用的对象属性superiorOperatorUser,不处理;
	 * 下附的集合属性subOperatorUsers,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BOperatorUser bOperatorUser = new BOperatorUser();
		this.dtob(d_obj, bOperatorUser);
		return bOperatorUser;
	}
	

	public void btod(BOperatorUser _bOperatorUser, OperatorUser _operatorUser, boolean _blWithPermissions )
	{
		if( _operatorUser == null )
		{
			return;
		}
		
		this.btod(_bOperatorUser, _operatorUser);
		
		if( _blWithPermissions == true )
		{
			//    operatorUserFunPermissions
			if( _bOperatorUser != null && _bOperatorUser.getOperatorUserFunPermissions() != null && _bOperatorUser.getOperatorUserFunPermissions().iterator() != null )
			{
				FunPermissionBDConvertor funPermissionBDConvertor = new FunPermissionBDConvertor();
				
				Iterator<BOperatorUserFunPermission> itr_bOperatorUserFunPermissions = _bOperatorUser.getOperatorUserFunPermissions().iterator();
				while( itr_bOperatorUserFunPermissions.hasNext() )
				{
					BOperatorUserFunPermission bOperatorUserFunPermission = itr_bOperatorUserFunPermissions.next();
										
					FunPermission funPermission = (FunPermission)funPermissionBDConvertor.btod(bOperatorUserFunPermission.getFunPermission()); 
					
					OperatorUserFunPermission operatorUserFunPermission = new OperatorUserFunPermission();
					
					operatorUserFunPermission.setVersion(bOperatorUserFunPermission.getVersion());
					operatorUserFunPermission.setId(bOperatorUserFunPermission.getId());
					operatorUserFunPermission.setOperatorUser(_operatorUser);
					operatorUserFunPermission.setFunPermission(funPermission);

					_operatorUser.addOperatorUserFunPermission( operatorUserFunPermission );
				}
			}		
			
			//    operatorUserBizDatas
			if( _bOperatorUser != null && _bOperatorUser.getOperatorUserBizDatas() != null && _bOperatorUser.getOperatorUserBizDatas().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
				
				Iterator<BOperatorUserBizData> itr_bOperatorUserBizDatas = _bOperatorUser.getOperatorUserBizDatas().iterator();
				while( itr_bOperatorUserBizDatas.hasNext() )
				{
					BOperatorUserBizData bOperatorUserBizData = itr_bOperatorUserBizDatas.next();
					
					BizData bizData = (BizData)bizDataBDConvertor.btod(bOperatorUserBizData.getBizData());
					
					OperatorUserBizData operatorUserBizData = new OperatorUserBizData();
					
					operatorUserBizData.setVersion(bOperatorUserBizData.getVersion());
					operatorUserBizData.setId(bOperatorUserBizData.getId());
					operatorUserBizData.setOperatorUser(_operatorUser);
					operatorUserBizData.setBizData(bizData);
					operatorUserBizData.setIsManaging( bOperatorUserBizData.getIsManaging() );

					_operatorUser.addOperatorUserBizData( operatorUserBizData );
				}
			}	
			
			//    operatorUserProOrgs
			if( _bOperatorUser != null && _bOperatorUser.getOperatorUserProOrgs() != null && _bOperatorUser.getOperatorUserProOrgs().iterator() != null )
			{
				ProductBDConvertor productBDConvertor = new ProductBDConvertor();
				OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
				
				Iterator<BOperatorUserProOrg> itr_bOperatorUserProOrgs = _bOperatorUser.getOperatorUserProOrgs().iterator();
				while( itr_bOperatorUserProOrgs.hasNext() )
				{
					BOperatorUserProOrg bOperatorUserProOrg = itr_bOperatorUserProOrgs.next();
					
					Product product = (Product) productBDConvertor.btod( bOperatorUserProOrg.getProduct() );
					Organization organization = (Organization) organizationBDConvertor.btod( bOperatorUserProOrg.getOrganization() );
					
					
					OperatorUserProOrg operatorUserProOrg = new OperatorUserProOrg();
					
					operatorUserProOrg.setVersion(bOperatorUserProOrg.getVersion());
					operatorUserProOrg.setId(bOperatorUserProOrg.getId());
					operatorUserProOrg.setOperatorUser(_operatorUser);
					operatorUserProOrg.setProduct(product);
					operatorUserProOrg.setOrganization(organization);
					
					_operatorUser.addOperatorUserProOrg( operatorUserProOrg );
				}
			}				
		}
		
		
	}
	
	
	public OperatorUser btod(BOperatorUser _bOperatorUser, boolean _blWithPermissions )
	{
		OperatorUser operatorUser = new OperatorUser();
		this.btod(_bOperatorUser, operatorUser, _blWithPermissions);
		return operatorUser;		
	}	
	
	public void dtob(OperatorUser _operatorUser, BOperatorUser _bOperatorUser, boolean _blWithPermissions )
	{
		if( _bOperatorUser == null )
		{
			return;
		}
		
		this.dtob(_operatorUser, _bOperatorUser);
		
		if( _blWithPermissions == true )
		{
			//    operatorUserFunPermissions
			if( _operatorUser != null && _operatorUser.getOperatorUserFunPermissions() != null && _operatorUser.getOperatorUserFunPermissions().iterator() != null )
			{
				FunPermissionBDConvertor funPermissionBDConvertor = new FunPermissionBDConvertor();
				
				Iterator<OperatorUserFunPermission> itr_operatorUserFunPermissions = _operatorUser.getOperatorUserFunPermissions().iterator();
				while( itr_operatorUserFunPermissions.hasNext() )
				{
					OperatorUserFunPermission operatorUserFunPermission = itr_operatorUserFunPermissions.next();
					
					BFunPermission bFunPermission = (BFunPermission)funPermissionBDConvertor.dtob(operatorUserFunPermission.getFunPermission());    // 认为operatorUserFunPermission.getFunPermission()!=null
					
					BOperatorUserFunPermission bOperatorUserFunPermission = new BOperatorUserFunPermission();
					
					bOperatorUserFunPermission.setVersion(operatorUserFunPermission.getVersion());
					bOperatorUserFunPermission.setId(operatorUserFunPermission.getId());
					bOperatorUserFunPermission.setOperatorUser(_bOperatorUser);
					bOperatorUserFunPermission.setFunPermission(bFunPermission);
										
					_bOperatorUser.addOperatorUserFunPermission( bOperatorUserFunPermission );
				}
			}	
			
			//    operatorUserBizDatas
			if( _operatorUser != null && _operatorUser.getOperatorUserBizDatas() != null && _operatorUser.getOperatorUserBizDatas().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
				
				Iterator<OperatorUserBizData> itr_operatorUserBizDatas = _operatorUser.getOperatorUserBizDatas().iterator();
				while( itr_operatorUserBizDatas.hasNext() )
				{
					OperatorUserBizData operatorUserBizData = itr_operatorUserBizDatas.next();

					BBizData bBizData = (BBizData)bizDataBDConvertor.dtob(operatorUserBizData.getBizData()); 
					
					BOperatorUserBizData bOperatorUserBizData = new BOperatorUserBizData();
					
					bOperatorUserBizData.setVersion(operatorUserBizData.getVersion());
					bOperatorUserBizData.setId(operatorUserBizData.getId());
					bOperatorUserBizData.setOperatorUser(_bOperatorUser);
					bOperatorUserBizData.setBizData(bBizData);
					bOperatorUserBizData.setIsManaging( operatorUserBizData.getIsManaging() );
				
					_bOperatorUser.addOperatorUserBizData( bOperatorUserBizData );
				}
			}
			
			//    operatorUserProOrgs
			if( _operatorUser != null && _operatorUser.getOperatorUserProOrgs() != null && _operatorUser.getOperatorUserProOrgs().iterator() != null )
			{
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
				/*
				ProductBDConvertor productBDConvertor = new ProductBDConvertor();
				OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
				*/
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end
				
				Iterator<OperatorUserProOrg> itr_operatorUserProOrgs = _operatorUser.getOperatorUserProOrgs().iterator();
				while( itr_operatorUserProOrgs.hasNext() )
				{
					OperatorUserProOrg operatorUserProOrg = itr_operatorUserProOrgs.next();
					
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
					/*
					BProduct bProduct = (BProduct)productBDConvertor.dtob(operatorUserProOrg.getProduct()); 
					BOrganization bOrganization = (BOrganization)organizationBDConvertor.dtob(operatorUserProOrg.getOrganization());
					*/
					BProduct bProduct = ServerEnvironment.getInstance().getBProduct( operatorUserProOrg.getProduct().getId() );
					BOrganization bOrganization = ServerEnvironment.getInstance().getBOrganization( operatorUserProOrg.getOrganization().getId() );
					//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end

					BOperatorUserProOrg bOperatorUserProOrg = new BOperatorUserProOrg();
					
					bOperatorUserProOrg.setVersion(operatorUserProOrg.getVersion());
					bOperatorUserProOrg.setId(operatorUserProOrg.getId());
					bOperatorUserProOrg.setOperatorUser(_bOperatorUser);
					bOperatorUserProOrg.setProduct(bProduct);
					bOperatorUserProOrg.setOrganization(bOrganization);
					
					_bOperatorUser.addOperatorUserProOrg( bOperatorUserProOrg );
				}
			}				
		}
			
	}
	

	public BOperatorUser dtob(OperatorUser _operatorUser, boolean _blWithPermissions )
	{
		BOperatorUser bOperatorUser = new BOperatorUser();
		this.dtob(_operatorUser, bOperatorUser, _blWithPermissions);
		return bOperatorUser;		
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
