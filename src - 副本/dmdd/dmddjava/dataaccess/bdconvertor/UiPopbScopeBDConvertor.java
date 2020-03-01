/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScope;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScope;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScopeProOrg;

/**
 * @author liuzhen
 *
 */
public class UiPopbScopeBDConvertor implements BDConvertorInterface
{

	// UiPopbScope 只提供新建和删除能力，不提供更新能力
	// 所以，bd转换都是带着 uiPopbScopeProOrgs uiPopbScopeBizDatas 的
	/**
	 * 
	 */
	public UiPopbScopeBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BUiPopbScope bUiPopbScope = null;
		UiPopbScope   uiPopbScope = null;
		
		if( b_obj == null )
		{
			bUiPopbScope = new BUiPopbScope();
		}
		else
		{
			bUiPopbScope = (BUiPopbScope)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			uiPopbScope = (UiPopbScope)d_obj;
		}
		
		uiPopbScope.setVersion( bUiPopbScope.getVersion() );
		uiPopbScope.setId( bUiPopbScope.getId() );
		uiPopbScope.setUiCode( bUiPopbScope.getUiCode() );
		uiPopbScope.setCode( bUiPopbScope.getCode() );
		uiPopbScope.setName( bUiPopbScope.getName() );
		uiPopbScope.setIsDefault( bUiPopbScope.getIsDefault() );
		
		uiPopbScope.setIsPeriodControl( bUiPopbScope.getIsPeriodControl() );
		uiPopbScope.setPeriodOffsetBegin( bUiPopbScope.getPeriodOffsetBegin() );
		uiPopbScope.setPeriodOffsetEnd( bUiPopbScope.getPeriodOffsetEnd() );
		
		uiPopbScope.setIsDisplayControl( bUiPopbScope.getIsDisplayControl() );
		uiPopbScope.setIsShowProduct( bUiPopbScope.getIsShowProduct() );
		uiPopbScope.setIsShowProductCharacter( bUiPopbScope.getIsShowProductCharacter() );
		uiPopbScope.setProductCharacterType( bUiPopbScope.getProductCharacterType() );
		uiPopbScope.setIsShowOrganization( bUiPopbScope.getIsShowOrganization() );
		uiPopbScope.setIsShowOrganizationCharacter( bUiPopbScope.getIsShowOrganizationCharacter() );	
		uiPopbScope.setOrganizationCharacterType( bUiPopbScope.getOrganizationCharacterType() );
		
		uiPopbScope.setDescription( bUiPopbScope.getDescription() );
		uiPopbScope.setComments( bUiPopbScope.getComments() );
		
		//	productLayer
		if( bUiPopbScope.getProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			uiPopbScope.setProductLayer( (ProductLayer)productLayerBDConvertor.btod( bUiPopbScope.getProductLayer() ) );
		}
		else
		{
			uiPopbScope.setProductLayer( null );
		}
		//	productCharacterLayer
		if( bUiPopbScope.getProductCharacterLayer() != null )
		{
			ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
			uiPopbScope.setProductCharacterLayer( (ProductCharacterLayer)productCharacterLayerBDConvertor.btod( bUiPopbScope.getProductCharacterLayer() ) );
		}
		else
		{
			uiPopbScope.setProductCharacterLayer( null );
		}	
		//	organizationLayer
		if( bUiPopbScope.getOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			uiPopbScope.setOrganizationLayer( (OrganizationLayer)organizationLayerBDConvertor.btod( bUiPopbScope.getOrganizationLayer() ) );
		}
		else
		{
			uiPopbScope.setOrganizationLayer( null );
		}
		//	organizationCharacterLayer
		if( bUiPopbScope.getOrganizationCharacterLayer() != null )
		{
			OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
			uiPopbScope.setOrganizationCharacterLayer( (OrganizationCharacterLayer)organizationCharacterLayerBDConvertor.btod( bUiPopbScope.getOrganizationCharacterLayer() ) );
		}
		else
		{
			uiPopbScope.setOrganizationCharacterLayer( null );
		}		
				
		//    operatorUser
		if( bUiPopbScope.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			OperatorUser operatorUser = (OperatorUser) operatorUserBDConvertor.btod( bUiPopbScope.getOperatorUser() );	
			uiPopbScope.setOperatorUser(operatorUser);
		}
		else
		{
			uiPopbScope.setOperatorUser(null);
		}	
		
		// uiPopbScopeProOrgs
		// uiPopbScopeProOrgs 传到前段做业务范围时，会与整个产品组织树计算，这里不必带整个树过去
		if( bUiPopbScope.getUiPopbScopeProOrgs() != null && bUiPopbScope.getUiPopbScopeProOrgs().iterator() != null )
		{
			OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();		
			ProductBDConvertor productBDConvertor = new ProductBDConvertor();
			
			Iterator<BUiPopbScopeProOrg> itr_bUiPopbScopeProOrgs = bUiPopbScope.getUiPopbScopeProOrgs().iterator();
			while( itr_bUiPopbScopeProOrgs.hasNext() )
			{
				BUiPopbScopeProOrg bUiPopbScopeProOrg = itr_bUiPopbScopeProOrgs.next();
					
				UiPopbScopeProOrg uiPopbScopeProOrg = new UiPopbScopeProOrg();
				
				uiPopbScopeProOrg.setVersion( bUiPopbScopeProOrg.getVersion() );
				uiPopbScopeProOrg.setId( bUiPopbScopeProOrg.getId() );
				uiPopbScopeProOrg.setUiPopbScope( uiPopbScope );
				uiPopbScopeProOrg.setProduct( (Product)productBDConvertor.btod( bUiPopbScopeProOrg.getProduct() ) );
				uiPopbScopeProOrg.setOrganization( (Organization)organizationBDConvertor.btod( bUiPopbScopeProOrg.getOrganization() ) );
				
				uiPopbScope.addUiPopbScopeProOrg( uiPopbScopeProOrg );
			}
		}
		
		// uiPopbScopeBizDatas
		if( bUiPopbScope.getUiPopbScopeBizDatas() != null && bUiPopbScope.getUiPopbScopeBizDatas().iterator() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();		
			
			Iterator<BUiPopbScopeBizData> itr_bUiPopbScopeBizDatas = bUiPopbScope.getUiPopbScopeBizDatas().iterator();
			while( itr_bUiPopbScopeBizDatas.hasNext() )
			{
				BUiPopbScopeBizData bUiPopbScopeBizData = itr_bUiPopbScopeBizDatas.next();
					
				UiPopbScopeBizData uiPopbScopeBizData = new UiPopbScopeBizData();
				
				uiPopbScopeBizData.setVersion( bUiPopbScopeBizData.getVersion() );
				uiPopbScopeBizData.setId( bUiPopbScopeBizData.getId() );
				uiPopbScopeBizData.setUiPopbScope( uiPopbScope );
				uiPopbScopeBizData.setBizData( bizDataBDConvertor.btod( bUiPopbScopeBizData.getBizData(), true ) );
				
				uiPopbScope.addUiPopbScopeBizData( uiPopbScopeBizData );
			}
		}		
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		UiPopbScope uiPopbScope = new UiPopbScope();
		this.btod(b_obj, uiPopbScope);
		return uiPopbScope;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		UiPopbScope   uiPopbScope = null;
		BUiPopbScope bUiPopbScope = null;
		
		if( d_obj == null )
		{
			uiPopbScope = new UiPopbScope();
		}
		else
		{
			uiPopbScope = (UiPopbScope)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bUiPopbScope = (BUiPopbScope)b_obj;
		}
		
		bUiPopbScope.setVersion( uiPopbScope.getVersion() );
		bUiPopbScope.setId( uiPopbScope.getId() );
		bUiPopbScope.setUiCode( uiPopbScope.getUiCode() );
		bUiPopbScope.setCode( uiPopbScope.getCode() );
		bUiPopbScope.setName( uiPopbScope.getName() );
		bUiPopbScope.setIsDefault( uiPopbScope.getIsDefault() );
		
		bUiPopbScope.setIsPeriodControl( uiPopbScope.getIsPeriodControl() );
		bUiPopbScope.setPeriodOffsetBegin( uiPopbScope.getPeriodOffsetBegin() );
		bUiPopbScope.setPeriodOffsetEnd( uiPopbScope.getPeriodOffsetEnd() );
		
		bUiPopbScope.setIsDisplayControl( uiPopbScope.getIsDisplayControl() );
		bUiPopbScope.setIsShowProduct( uiPopbScope.getIsShowProduct() );
		bUiPopbScope.setIsShowProductCharacter( uiPopbScope.getIsShowProductCharacter() );
		bUiPopbScope.setProductCharacterType( uiPopbScope.getProductCharacterType() );
		bUiPopbScope.setIsShowOrganization( uiPopbScope.getIsShowOrganization() );
		bUiPopbScope.setIsShowOrganizationCharacter( uiPopbScope.getIsShowOrganizationCharacter() );	
		bUiPopbScope.setOrganizationCharacterType( uiPopbScope.getOrganizationCharacterType() );
		
		bUiPopbScope.setDescription( uiPopbScope.getDescription() );
		bUiPopbScope.setComments( uiPopbScope.getComments() );
		

		//	productLayer
		if( uiPopbScope.getProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			bUiPopbScope.setProductLayer( (BProductLayer)productLayerBDConvertor.dtob( uiPopbScope.getProductLayer() ) );
		}
		else
		{
			bUiPopbScope.setProductLayer( null );
		}
		//	productCharacterLayer
		if( uiPopbScope.getProductCharacterLayer() != null )
		{
			ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
			bUiPopbScope.setProductCharacterLayer( (BProductCharacterLayer)productCharacterLayerBDConvertor.dtob( uiPopbScope.getProductCharacterLayer() ) );
		}
		else
		{
			bUiPopbScope.setProductCharacterLayer( null );
		}	
		//	organizationLayer
		if( uiPopbScope.getOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			bUiPopbScope.setOrganizationLayer( (BOrganizationLayer)organizationLayerBDConvertor.dtob( uiPopbScope.getOrganizationLayer() ) );
		}
		else
		{
			bUiPopbScope.setOrganizationLayer( null );
		}
		//	organizationCharacterLayer
		if( uiPopbScope.getOrganizationCharacterLayer() != null )
		{
			OrganizationCharacterLayerBDConvertor organizationCharacterLayerBDConvertor = new OrganizationCharacterLayerBDConvertor();
			bUiPopbScope.setOrganizationCharacterLayer( (BOrganizationCharacterLayer)organizationCharacterLayerBDConvertor.dtob( uiPopbScope.getOrganizationCharacterLayer() ) );
		}
		else
		{
			bUiPopbScope.setOrganizationCharacterLayer( null );
		}	
		
		
		//    operatorUser
		if( uiPopbScope.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			BOperatorUser bOperatorUser = (BOperatorUser) operatorUserBDConvertor.dtob( uiPopbScope.getOperatorUser() );	
			bUiPopbScope.setOperatorUser(bOperatorUser);
		}
		else
		{
			bUiPopbScope.setOperatorUser(null);
		}	

		// uiPopbScopeProOrgs
		if( uiPopbScope.getUiPopbScopeProOrgs() != null && uiPopbScope.getUiPopbScopeProOrgs().iterator() != null )
		{			
			Iterator<UiPopbScopeProOrg> itr_uiPopbScopeProOrgs = uiPopbScope.getUiPopbScopeProOrgs().iterator();
			while( itr_uiPopbScopeProOrgs.hasNext() )
			{
				UiPopbScopeProOrg uiPopbScopeProOrg = itr_uiPopbScopeProOrgs.next();
					
				BUiPopbScopeProOrg bUiPopbScopeProOrg = new BUiPopbScopeProOrg();
				
				bUiPopbScopeProOrg.setVersion( uiPopbScopeProOrg.getVersion() );
				bUiPopbScopeProOrg.setId( uiPopbScopeProOrg.getId() );
				bUiPopbScopeProOrg.setUiPopbScope( bUiPopbScope );
				
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	begin
				bUiPopbScopeProOrg.setProduct( ServerEnvironment.getInstance().getBProduct( uiPopbScopeProOrg.getProduct().getId() ) );
				bUiPopbScopeProOrg.setOrganization( ServerEnvironment.getInstance().getBOrganization( uiPopbScopeProOrg.getOrganization().getId() ) );
				//	直接从内存常驻变量中获取，获取完整树上的节点，2011.01.15 by liuzhen	end
				
				bUiPopbScope.addUiPopbScopeProOrg( bUiPopbScopeProOrg );
			}
		}		

		// uiPopbScopeBizDatas
		if( uiPopbScope.getUiPopbScopeBizDatas() != null && uiPopbScope.getUiPopbScopeBizDatas().iterator() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();	
			
			Iterator<UiPopbScopeBizData> itr_uiPopbScopeBizDatas = uiPopbScope.getUiPopbScopeBizDatas().iterator();
			while( itr_uiPopbScopeBizDatas.hasNext() )
			{
				UiPopbScopeBizData uiPopbScopeBizData = itr_uiPopbScopeBizDatas.next();
					
				BUiPopbScopeBizData bUiPopbScopeBizData = new BUiPopbScopeBizData();
				
				bUiPopbScopeBizData.setVersion( uiPopbScopeBizData.getVersion() );
				bUiPopbScopeBizData.setId( uiPopbScopeBizData.getId() );
				bUiPopbScopeBizData.setUiPopbScope( bUiPopbScope );
				bUiPopbScopeBizData.setBizData( bizDataBDConvertor.dtob( uiPopbScopeBizData.getBizData(), true ) );
				
				bUiPopbScope.addUiPopbScopeBizData( bUiPopbScopeBizData );
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		BUiPopbScope bUiPopbScope = new BUiPopbScope();
		this.dtob(d_obj, bUiPopbScope);
		return bUiPopbScope;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
