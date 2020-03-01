/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLog;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogAuditItem;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogHfcItem;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogAuditItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogHfcItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;

/**
 * @author liuzhen
 *
 */
public class ForecastMakeLogBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastMakeLogBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性operatorUser,处理;
	 * 下附的集合属性forecastMakeLogProOrgs、forecastMakeLogHfcItems、forecastMakeLogAuditItems,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastMakeLog bForecastMakeLog = null;
		ForecastMakeLog   forecastMakeLog = null;
		
		if( b_obj == null )
		{
			bForecastMakeLog = new BForecastMakeLog();
		}
		else
		{
			bForecastMakeLog = (BForecastMakeLog)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastMakeLog = (ForecastMakeLog)d_obj;
		}
		
		forecastMakeLog.setVersion( bForecastMakeLog.getVersion() );
		forecastMakeLog.setId( bForecastMakeLog.getId() );
		forecastMakeLog.setCompilePeriod( bForecastMakeLog.getCompilePeriod() );
		forecastMakeLog.setActionType( bForecastMakeLog.getActionType() );
		forecastMakeLog.setSubmitTime( bForecastMakeLog.getSubmitTime() );
		forecastMakeLog.setSubmitter( bForecastMakeLog.getSubmitter() );		
		forecastMakeLog.setDescription( bForecastMakeLog.getDescription() );
		forecastMakeLog.setComments( bForecastMakeLog.getComments() );
		
		
		//    operatorUser
		if( bForecastMakeLog.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			OperatorUser operatorUser = (OperatorUser) operatorUserBDConvertor.btod( bForecastMakeLog.getOperatorUser() );	
			forecastMakeLog.setOperatorUser(operatorUser);
		}
		else
		{
			forecastMakeLog.setOperatorUser(null);
		}	
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性operatorUser,处理;
	 * 下附的集合属性forecastMakeLogProOrgs、forecastMakeLogHfcItems、forecastMakeLogAuditItems,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		ForecastMakeLog forecastMakeLog = new ForecastMakeLog();
		this.btod(b_obj, forecastMakeLog);
		return forecastMakeLog;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性operatorUser,处理;
	 * 下附的集合属性forecastMakeLogProOrgs、forecastMakeLogHfcItems、forecastMakeLogAuditItems,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastMakeLog   forecastMakeLog = null;
		BForecastMakeLog bForecastMakeLog = null;
		
		if( d_obj == null )
		{
			forecastMakeLog = new ForecastMakeLog();
		}
		else
		{
			forecastMakeLog = (ForecastMakeLog)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastMakeLog = (BForecastMakeLog)b_obj;
		}
		
		bForecastMakeLog.setVersion( forecastMakeLog.getVersion() );
		bForecastMakeLog.setId( forecastMakeLog.getId() );
		bForecastMakeLog.setCompilePeriod( forecastMakeLog.getCompilePeriod() );
		bForecastMakeLog.setActionType( forecastMakeLog.getActionType() );
		bForecastMakeLog.setSubmitTime( forecastMakeLog.getSubmitTime() );
		bForecastMakeLog.setSubmitter( forecastMakeLog.getSubmitter() );		
		bForecastMakeLog.setDescription( forecastMakeLog.getDescription() );
		bForecastMakeLog.setComments( forecastMakeLog.getComments() );
		

		//    operatorUser
		if( forecastMakeLog.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			BOperatorUser bOperatorUser = (BOperatorUser) operatorUserBDConvertor.dtob( forecastMakeLog.getOperatorUser() );	
			bForecastMakeLog.setOperatorUser(bOperatorUser);
		}
		else
		{
			bForecastMakeLog.setOperatorUser(null);
		}	

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性operatorUser,处理;
	 * 下附的集合属性forecastMakeLogProOrgs、forecastMakeLogHfcItems、forecastMakeLogAuditItems,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}		
		BForecastMakeLog bForecastMakeLog = new BForecastMakeLog();
		this.dtob(d_obj, bForecastMakeLog);
		return bForecastMakeLog;
	}
	

	public void btod(BForecastMakeLog _bForecastMakeLog, ForecastMakeLog _forecastMakeLog, boolean _blWithProOrgs, boolean _blWithHfcItems, boolean _blWithAuditItems )
	{
		if( _forecastMakeLog == null )
		{
			return;
		}
		
		this.btod(_bForecastMakeLog, _forecastMakeLog);
		
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();			
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();			
		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();				
		
		// forecastMakeLogProOrgs
		if( _blWithProOrgs == true )
		{
			if( _bForecastMakeLog != null && _bForecastMakeLog.getForecastMakeLogProOrgs() != null && _bForecastMakeLog.getForecastMakeLogProOrgs().iterator() != null )
			{			
				Iterator<BForecastMakeLogProOrg> itr_bForecastMakeLogProOrgs = _bForecastMakeLog.getForecastMakeLogProOrgs().iterator();
				while( itr_bForecastMakeLogProOrgs.hasNext() )
				{
					BForecastMakeLogProOrg bForecastMakeLogProOrg = itr_bForecastMakeLogProOrgs.next();
						
					ForecastMakeLogProOrg forecastMakeLogProOrg = new ForecastMakeLogProOrg();
					
					forecastMakeLogProOrg.setVersion(bForecastMakeLogProOrg.getVersion());
					forecastMakeLogProOrg.setId(bForecastMakeLogProOrg.getId());
					forecastMakeLogProOrg.setForecastMakeLog(_forecastMakeLog);
					forecastMakeLogProOrg.setOrganization( (Organization)organizationBDConvertor.btod( bForecastMakeLogProOrg.getOrganization() ) );
					forecastMakeLogProOrg.setProduct( (Product)productBDConvertor.btod( bForecastMakeLogProOrg.getProduct() ) );

					_forecastMakeLog.addForecastMakeLogProOrg( forecastMakeLogProOrg );
				}
			}				
		}
	
		if( _blWithHfcItems == true )
		{
			// forecastMakeLogHfcItems
			if( _bForecastMakeLog != null && _bForecastMakeLog.getForecastMakeLogHfcItems() != null && _bForecastMakeLog.getForecastMakeLogHfcItems().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();	
				
				Iterator<BForecastMakeLogHfcItem> itr_bForecastMakeLogHfcItems = _bForecastMakeLog.getForecastMakeLogHfcItems().iterator();
				while( itr_bForecastMakeLogHfcItems.hasNext() )
				{
					BForecastMakeLogHfcItem bForecastMakeLogHfcItem = itr_bForecastMakeLogHfcItems.next();
					
					ForecastMakeLogHfcItem forecastMakeLogHfcItem = new ForecastMakeLogHfcItem();
					
					forecastMakeLogHfcItem.setVersion(bForecastMakeLogHfcItem.getVersion());
					forecastMakeLogHfcItem.setId(bForecastMakeLogHfcItem.getId());
					forecastMakeLogHfcItem.setForecastMakeLog(_forecastMakeLog);
					forecastMakeLogHfcItem.setPeriod( bForecastMakeLogHfcItem.getPeriod() );
					forecastMakeLogHfcItem.setProduct( (Product)productBDConvertor.btod( bForecastMakeLogHfcItem.getProduct() ) );
					forecastMakeLogHfcItem.setProductCharacter( (ProductCharacter)productCharacterBDConvertor.btod( bForecastMakeLogHfcItem.getProductCharacter() ) );
					forecastMakeLogHfcItem.setOrganization( (Organization)organizationBDConvertor.btod( bForecastMakeLogHfcItem.getOrganization() ) );
					forecastMakeLogHfcItem.setOrganizationCharacter( (OrganizationCharacter)organizationCharacterBDConvertor.btod( bForecastMakeLogHfcItem.getOrganizationCharacter() ) );								
					forecastMakeLogHfcItem.setBizData( (BizData)bizDataBDConvertor.btod( bForecastMakeLogHfcItem.getBizData() ) );
					forecastMakeLogHfcItem.setOldValue( bForecastMakeLogHfcItem.getOldValue() );
					forecastMakeLogHfcItem.setNewValue( bForecastMakeLogHfcItem.getNewValue() );
					forecastMakeLogHfcItem.setDescription( bForecastMakeLogHfcItem.getDescription() );
					forecastMakeLogHfcItem.setComments( bForecastMakeLogHfcItem.getComments() );
					
					if( bForecastMakeLogHfcItem.getOrganization() != null )
					{
						forecastMakeLogHfcItem.setOrganization( (Organization)organizationBDConvertor.btod( bForecastMakeLogHfcItem.getOrganization() ) );
					}
					else
					{
						forecastMakeLogHfcItem.setOrganization( null );
					}
					if( bForecastMakeLogHfcItem.getProduct() != null )
					{
						forecastMakeLogHfcItem.setProduct( (Product)productBDConvertor.btod( bForecastMakeLogHfcItem.getProduct() ) );
					}
					else
					{
						forecastMakeLogHfcItem.setProduct( null );
					}			
					
					_forecastMakeLog.addForecastMakeLogHfcItem( forecastMakeLogHfcItem );
				}
			}			
		}
	
		if( _blWithAuditItems == true )
		{
			// forecastMakeLogAuditItems
			if( _bForecastMakeLog != null && _bForecastMakeLog.getForecastMakeLogAuditItems() != null && _bForecastMakeLog.getForecastMakeLogAuditItems().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();				
				Iterator<BForecastMakeLogAuditItem> itr_bForecastMakeLogAuditItems = _bForecastMakeLog.getForecastMakeLogAuditItems().iterator();
				while( itr_bForecastMakeLogAuditItems.hasNext() )
				{
					BForecastMakeLogAuditItem bForecastMakeLogAuditItem = itr_bForecastMakeLogAuditItems.next();
								
					ForecastMakeLogAuditItem forecastMakeLogAuditItem = new ForecastMakeLogAuditItem();
					
					forecastMakeLogAuditItem.setVersion(bForecastMakeLogAuditItem.getVersion());
					forecastMakeLogAuditItem.setId(bForecastMakeLogAuditItem.getId());
					forecastMakeLogAuditItem.setForecastMakeLog(_forecastMakeLog);
					forecastMakeLogAuditItem.setBizData( (BizData)bizDataBDConvertor.btod( bForecastMakeLogAuditItem.getBizData() ) );
					forecastMakeLogAuditItem.setComments( bForecastMakeLogAuditItem.getComments() );				
					
					_forecastMakeLog.addForecastMakeLogAuditItem( forecastMakeLogAuditItem );
				}
			}					
		}
	
	}
	

	public ForecastMakeLog btod(BForecastMakeLog _bForecastMakeLog, boolean _blWithProOrgs, boolean _blWithHfcItems, boolean _blWithAuditItems )
	{
		if( _bForecastMakeLog == null )
		{
			return null;
		}		
		ForecastMakeLog forecastMakeLog = new ForecastMakeLog();
		this.btod( _bForecastMakeLog, forecastMakeLog, _blWithProOrgs, _blWithHfcItems, _blWithAuditItems );
		return forecastMakeLog;	
	}	
	

	public void dtob(ForecastMakeLog _forecastMakeLog, BForecastMakeLog _bForecastMakeLog, boolean _blWithProOrgs, boolean _blWithHfcItems, boolean _blWithAuditItems )
	{
		if( _bForecastMakeLog == null )
		{
			return;
		}
		
		this.dtob(_forecastMakeLog, _bForecastMakeLog);
		
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();			
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();			
		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();						
		
		if( _blWithProOrgs == true )
		{
			// forecastMakeLogProOrgs
			if( _forecastMakeLog != null && _forecastMakeLog.getForecastMakeLogProOrgs() != null && _forecastMakeLog.getForecastMakeLogProOrgs().iterator() != null )
			{		
				Iterator<ForecastMakeLogProOrg> itr_forecastMakeLogProOrgs = _forecastMakeLog.getForecastMakeLogProOrgs().iterator();
				while( itr_forecastMakeLogProOrgs.hasNext() )
				{
					ForecastMakeLogProOrg forecastMakeLogProOrg = itr_forecastMakeLogProOrgs.next();
							
					BForecastMakeLogProOrg bForecastMakeLogProOrg = new BForecastMakeLogProOrg();
					
					bForecastMakeLogProOrg.setVersion(forecastMakeLogProOrg.getVersion());
					bForecastMakeLogProOrg.setId(forecastMakeLogProOrg.getId());
					bForecastMakeLogProOrg.setForecastMakeLog(_bForecastMakeLog);
					bForecastMakeLogProOrg.setOrganization( (BOrganization)organizationBDConvertor.dtob( forecastMakeLogProOrg.getOrganization() ) );
					bForecastMakeLogProOrg.setProduct( (BProduct)productBDConvertor.dtob( forecastMakeLogProOrg.getProduct() ) );

					_bForecastMakeLog.addForecastMakeLogProOrg( bForecastMakeLogProOrg );
				}
			}		
			
		}

		if( _blWithHfcItems == true )
		{
			// forecastMakeLogHfcItems
			if( _forecastMakeLog != null && _forecastMakeLog.getForecastMakeLogHfcItems() != null && _forecastMakeLog.getForecastMakeLogHfcItems().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
				
				Iterator<ForecastMakeLogHfcItem> itr_forecastMakeLogHfcItems = _forecastMakeLog.getForecastMakeLogHfcItems().iterator();
				while( itr_forecastMakeLogHfcItems.hasNext() )
				{
					ForecastMakeLogHfcItem forecastMakeLogHfcItem = itr_forecastMakeLogHfcItems.next();
								
					BForecastMakeLogHfcItem bForecastMakeLogHfcItem = new BForecastMakeLogHfcItem();
					
					bForecastMakeLogHfcItem.setVersion(forecastMakeLogHfcItem.getVersion());
					bForecastMakeLogHfcItem.setId(forecastMakeLogHfcItem.getId());
					bForecastMakeLogHfcItem.setForecastMakeLog(_bForecastMakeLog);
					bForecastMakeLogHfcItem.setPeriod( forecastMakeLogHfcItem.getPeriod() );				
					bForecastMakeLogHfcItem.setProduct( (BProduct)productBDConvertor.dtob( forecastMakeLogHfcItem.getProduct() ) );
					bForecastMakeLogHfcItem.setProductCharacter( (BProductCharacter)productCharacterBDConvertor.dtob( forecastMakeLogHfcItem.getProductCharacter() ) );
					bForecastMakeLogHfcItem.setOrganization( (BOrganization)organizationBDConvertor.dtob( forecastMakeLogHfcItem.getOrganization() ) );
					bForecastMakeLogHfcItem.setOrganizationCharacter( (BOrganizationCharacter)organizationCharacterBDConvertor.dtob( forecastMakeLogHfcItem.getOrganizationCharacter() ) );												
					bForecastMakeLogHfcItem.setBizData( (BBizData)bizDataBDConvertor.dtob( forecastMakeLogHfcItem.getBizData() ) );
					bForecastMakeLogHfcItem.setOldValue( forecastMakeLogHfcItem.getOldValue() );
					bForecastMakeLogHfcItem.setNewValue( forecastMakeLogHfcItem.getNewValue() );
					bForecastMakeLogHfcItem.setDescription( forecastMakeLogHfcItem.getDescription() );
					bForecastMakeLogHfcItem.setComments( forecastMakeLogHfcItem.getComments() );		
					
					if( forecastMakeLogHfcItem.getOrganization() != null )
					{
						bForecastMakeLogHfcItem.setOrganization( (BOrganization)organizationBDConvertor.dtob( forecastMakeLogHfcItem.getOrganization() ) );
					}
					else
					{
						bForecastMakeLogHfcItem.setOrganization( null );
					}
					if( forecastMakeLogHfcItem.getProduct() != null )
					{
						bForecastMakeLogHfcItem.setProduct( (BProduct)productBDConvertor.dtob( forecastMakeLogHfcItem.getProduct() ) );
					}
					else
					{
						bForecastMakeLogHfcItem.setProduct( null );
					}				
					
					_bForecastMakeLog.addForecastMakeLogHfcItem( bForecastMakeLogHfcItem );
				}
			}				
		}
	
		if( _blWithAuditItems == true )
		{
			// forecastMakeLogAuditItems
			if( _forecastMakeLog != null && _forecastMakeLog.getForecastMakeLogAuditItems() != null && _forecastMakeLog.getForecastMakeLogAuditItems().iterator() != null )
			{
				BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
				Iterator<ForecastMakeLogAuditItem> itr_forecastMakeLogAuditItems = _forecastMakeLog.getForecastMakeLogAuditItems().iterator();
				while( itr_forecastMakeLogAuditItems.hasNext() )
				{
					ForecastMakeLogAuditItem forecastMakeLogAuditItem = itr_forecastMakeLogAuditItems.next();
			
					BForecastMakeLogAuditItem bForecastMakeLogAuditItem = new BForecastMakeLogAuditItem();
					
					bForecastMakeLogAuditItem.setVersion(forecastMakeLogAuditItem.getVersion());
					bForecastMakeLogAuditItem.setId(forecastMakeLogAuditItem.getId());
					bForecastMakeLogAuditItem.setForecastMakeLog(_bForecastMakeLog);
					bForecastMakeLogAuditItem.setBizData( (BBizData)bizDataBDConvertor.dtob( forecastMakeLogAuditItem.getBizData() ) );
					bForecastMakeLogAuditItem.setComments( forecastMakeLogAuditItem.getComments() );				
					
					_bForecastMakeLog.addForecastMakeLogAuditItem( bForecastMakeLogAuditItem );
				}
			}				
		}
			
	}	
	

	public BForecastMakeLog dtob(ForecastMakeLog _forecastMakeLog, boolean _blWithProOrgs, boolean _blWithHfcItems, boolean _blWithAuditItems )
	{
		if( _forecastMakeLog == null )
		{
			return null;
		}			
		BForecastMakeLog bForecastMakeLog = new BForecastMakeLog();
		this.dtob( _forecastMakeLog, bForecastMakeLog, _blWithProOrgs, _blWithHfcItems, _blWithAuditItems );
		return bForecastMakeLog;	
	}		

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
