/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BHistoryAdjustLog;
import dmdd.dmddjava.dataaccess.bizobject.BHistoryAdjustLogAdjustItem;
import dmdd.dmddjava.dataaccess.bizobject.BHistoryAdjustLogProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLogAdjustItem;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLogProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;

/**
 * @author liuzhen
 *
 */
public class HistoryAdjustLogBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public HistoryAdjustLogBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyAdjustCategory、bizData、operatorUser,处理;
	 * 下附的集合属性historyAdjustLogProOrgs、historyAdjustLogAdjustItems,不处理;
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BHistoryAdjustLog bHistoryAdjustLog = null;
		HistoryAdjustLog   historyAdjustLog = null;
		
		if( b_obj == null )
		{
			bHistoryAdjustLog = new BHistoryAdjustLog();
		}
		else
		{
			bHistoryAdjustLog = (BHistoryAdjustLog)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			historyAdjustLog = (HistoryAdjustLog)d_obj;
		}
		
		historyAdjustLog.setVersion( bHistoryAdjustLog.getVersion() );
		historyAdjustLog.setId( bHistoryAdjustLog.getId() );
		historyAdjustLog.setCompilePeriod( bHistoryAdjustLog.getCompilePeriod() );
		historyAdjustLog.setSubmitTime( bHistoryAdjustLog.getSubmitTime() );
		historyAdjustLog.setSubmitter( bHistoryAdjustLog.getSubmitter() );		
		historyAdjustLog.setDescription( bHistoryAdjustLog.getDescription() );
		historyAdjustLog.setComments( bHistoryAdjustLog.getComments() );
		
		//    operatorUser
		if( bHistoryAdjustLog.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			OperatorUser operatorUser = (OperatorUser) operatorUserBDConvertor.btod( bHistoryAdjustLog.getOperatorUser() );	
			historyAdjustLog.setOperatorUser(operatorUser);
		}
		else
		{
			historyAdjustLog.setOperatorUser(null);
		}		

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyAdjustCategory、bizData、operatorUser,处理;
	 * 下附的集合属性historyAdjustLogProOrgs、historyAdjustLogAdjustItems,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		HistoryAdjustLog historyAdjustLog = new HistoryAdjustLog();
		this.btod(b_obj, historyAdjustLog);
		return historyAdjustLog;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyAdjustCategory、bizData、operatorUser,处理;
	 * 下附的集合属性historyAdjustLogProOrgs、historyAdjustLogAdjustItems,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		HistoryAdjustLog   historyAdjustLog = null;
		BHistoryAdjustLog bHistoryAdjustLog = null;
		
		if( d_obj == null )
		{
			historyAdjustLog = new HistoryAdjustLog();
		}
		else
		{
			historyAdjustLog = (HistoryAdjustLog)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bHistoryAdjustLog = (BHistoryAdjustLog)b_obj;
		}
		
		bHistoryAdjustLog.setVersion( historyAdjustLog.getVersion() );
		bHistoryAdjustLog.setId( historyAdjustLog.getId() );
		bHistoryAdjustLog.setCompilePeriod( historyAdjustLog.getCompilePeriod() );
		bHistoryAdjustLog.setSubmitTime( historyAdjustLog.getSubmitTime() );
		bHistoryAdjustLog.setSubmitter( historyAdjustLog.getSubmitter() );		
		bHistoryAdjustLog.setDescription( historyAdjustLog.getDescription() );
		bHistoryAdjustLog.setComments( historyAdjustLog.getComments() );
		
		//    operatorUser
		if( historyAdjustLog.getOperatorUser() != null )
		{
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			BOperatorUser bOperatorUser = (BOperatorUser) operatorUserBDConvertor.dtob( historyAdjustLog.getOperatorUser() );	
			bHistoryAdjustLog.setOperatorUser(bOperatorUser);
		}
		else
		{
			bHistoryAdjustLog.setOperatorUser(null);
		}		

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyAdjustCategory、bizData、operatorUser,处理;
	 * 下附的集合属性historyAdjustLogProOrgs、historyAdjustLogAdjustItems,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}		
		BHistoryAdjustLog bHistoryAdjustLog = new BHistoryAdjustLog();
		this.dtob(d_obj, bHistoryAdjustLog);
		return bHistoryAdjustLog;
	}


	public void btod( BHistoryAdjustLog _bHistoryAdjustLog, HistoryAdjustLog _historyAdjustLog, boolean _blWithProOrgs, boolean _blWithAdjustItems  )
	{
		if( _historyAdjustLog == null )
		{
			return;
		}
		
		this.btod(_bHistoryAdjustLog, _historyAdjustLog);
		
		BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();			
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();			
		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();						
		
		if( _blWithProOrgs == true )
		{
			// historyAdjustLogProOrgs
			if( _bHistoryAdjustLog != null && _bHistoryAdjustLog.getHistoryAdjustLogProOrgs() != null && _bHistoryAdjustLog.getHistoryAdjustLogProOrgs().iterator() != null )
			{
				Iterator<BHistoryAdjustLogProOrg> itr_bHistoryAdjustLogProOrgs = _bHistoryAdjustLog.getHistoryAdjustLogProOrgs().iterator();
				while( itr_bHistoryAdjustLogProOrgs.hasNext() )
				{
					BHistoryAdjustLogProOrg bHistoryAdjustLogProOrg = itr_bHistoryAdjustLogProOrgs.next();
					
					HistoryAdjustLogProOrg historyAdjustLogProOrg = new HistoryAdjustLogProOrg();
					
					historyAdjustLogProOrg.setVersion(bHistoryAdjustLogProOrg.getVersion());
					historyAdjustLogProOrg.setId(bHistoryAdjustLogProOrg.getId());
					historyAdjustLogProOrg.setHistoryAdjustLog(_historyAdjustLog);
					historyAdjustLogProOrg.setOrganization( (Organization)organizationBDConvertor.btod( bHistoryAdjustLogProOrg.getOrganization() ) );
					historyAdjustLogProOrg.setProduct( (Product)productBDConvertor.btod( bHistoryAdjustLogProOrg.getProduct() ) );

					_historyAdjustLog.addHistoryAdjustLogProOrg( historyAdjustLogProOrg );
				}
			}					
		}

		
		if( _blWithAdjustItems == true )
		{
			// historyAdjustLogAdjustItems
			if( _bHistoryAdjustLog != null && _bHistoryAdjustLog.getHistoryAdjustLogAdjustItems() != null && _bHistoryAdjustLog.getHistoryAdjustLogAdjustItems().iterator() != null )
			{
				Iterator<BHistoryAdjustLogAdjustItem> itr_bHistoryAdjustLogAdjustItems = _bHistoryAdjustLog.getHistoryAdjustLogAdjustItems().iterator();
				while( itr_bHistoryAdjustLogAdjustItems.hasNext() )
				{
					BHistoryAdjustLogAdjustItem bHistoryAdjustLogAdjustItem = itr_bHistoryAdjustLogAdjustItems.next();						
					
					HistoryAdjustLogAdjustItem historyAdjustLogAdjustItem = new HistoryAdjustLogAdjustItem();
					
					historyAdjustLogAdjustItem.setVersion(bHistoryAdjustLogAdjustItem.getVersion());
					historyAdjustLogAdjustItem.setId(bHistoryAdjustLogAdjustItem.getId());
					historyAdjustLogAdjustItem.setHistoryAdjustLog(_historyAdjustLog);
					historyAdjustLogAdjustItem.setPeriod( bHistoryAdjustLogAdjustItem.getPeriod() );
					historyAdjustLogAdjustItem.setBizData( bizDataBDConvertor.btod( bHistoryAdjustLogAdjustItem.getBizData(), true ) );
					historyAdjustLogAdjustItem.setProduct( (Product)productBDConvertor.btod( bHistoryAdjustLogAdjustItem.getProduct() ) );
					historyAdjustLogAdjustItem.setProductCharacter( (ProductCharacter)productCharacterBDConvertor.btod( bHistoryAdjustLogAdjustItem.getProductCharacter() ) );
					historyAdjustLogAdjustItem.setOrganization( (Organization)organizationBDConvertor.btod( bHistoryAdjustLogAdjustItem.getOrganization() ) );
					historyAdjustLogAdjustItem.setOrganizationCharacter( (OrganizationCharacter)organizationCharacterBDConvertor.btod( bHistoryAdjustLogAdjustItem.getOrganizationCharacter() ) );								
					historyAdjustLogAdjustItem.setOldValue( bHistoryAdjustLogAdjustItem.getOldValue() );
					historyAdjustLogAdjustItem.setNewValue( bHistoryAdjustLogAdjustItem.getNewValue() );
					historyAdjustLogAdjustItem.setDescription( bHistoryAdjustLogAdjustItem.getDescription() );
					historyAdjustLogAdjustItem.setComments( bHistoryAdjustLogAdjustItem.getComments() );				
					
					_historyAdjustLog.addHistoryAdjustLogAdjustItem( historyAdjustLogAdjustItem );
				}
			}					
		}
		
	}
	

	public HistoryAdjustLog btod( BHistoryAdjustLog _bHistoryAdjustLog, boolean _blWithProOrgs, boolean _blWithAdjustItems )
	{
		if( _bHistoryAdjustLog == null )
		{
			return null;
		}		
		HistoryAdjustLog historyAdjustLog = new HistoryAdjustLog();
		this.btod( _bHistoryAdjustLog, historyAdjustLog, _blWithProOrgs, _blWithAdjustItems );
		return historyAdjustLog;		
	}
	

	public void dtob( HistoryAdjustLog _historyAdjustLog, BHistoryAdjustLog _bHistoryAdjustLog, boolean _blWithProOrgs, boolean _blWithAdjustItems )
	{
		if( _bHistoryAdjustLog == null )
		{
			return;
		}
		
		this.dtob(_historyAdjustLog, _bHistoryAdjustLog);
		
		BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();			
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		ProductCharacterBDConvertor productCharacterBDConvertor = new ProductCharacterBDConvertor();			
		OrganizationCharacterBDConvertor organizationCharacterBDConvertor = new OrganizationCharacterBDConvertor();								
		
		if( _blWithProOrgs == true )
		{
			// historyAdjustLogProOrgs
			if( _historyAdjustLog != null && _historyAdjustLog.getHistoryAdjustLogProOrgs() != null && _historyAdjustLog.getHistoryAdjustLogProOrgs().iterator() != null )
			{
				Iterator<HistoryAdjustLogProOrg> itr_historyAdjustLogProOrgs = _historyAdjustLog.getHistoryAdjustLogProOrgs().iterator();
				while( itr_historyAdjustLogProOrgs.hasNext() )
				{
					HistoryAdjustLogProOrg historyAdjustLogProOrg = itr_historyAdjustLogProOrgs.next();
					
					BHistoryAdjustLogProOrg bHistoryAdjustLogProOrg = new BHistoryAdjustLogProOrg();
					
					bHistoryAdjustLogProOrg.setVersion(historyAdjustLogProOrg.getVersion());
					bHistoryAdjustLogProOrg.setId(historyAdjustLogProOrg.getId());
					bHistoryAdjustLogProOrg.setHistoryAdjustLog(_bHistoryAdjustLog);
					bHistoryAdjustLogProOrg.setOrganization( (BOrganization)organizationBDConvertor.dtob( historyAdjustLogProOrg.getOrganization() ) );
					bHistoryAdjustLogProOrg.setProduct( (BProduct)productBDConvertor.dtob( historyAdjustLogProOrg.getProduct() ) );

					_bHistoryAdjustLog.addHistoryAdjustLogProOrg( bHistoryAdjustLogProOrg );
				}
			}					
		}

		if( _blWithAdjustItems == true )
		{
			// historyAdjustLogAdjustItems
			if( _historyAdjustLog != null && _historyAdjustLog.getHistoryAdjustLogAdjustItems() != null && _historyAdjustLog.getHistoryAdjustLogAdjustItems().iterator() != null )
			{
				Iterator<HistoryAdjustLogAdjustItem> itr_historyAdjustLogAdjustItems = _historyAdjustLog.getHistoryAdjustLogAdjustItems().iterator();
				while( itr_historyAdjustLogAdjustItems.hasNext() )
				{
					HistoryAdjustLogAdjustItem historyAdjustLogAdjustItem = itr_historyAdjustLogAdjustItems.next();
									
					
					BHistoryAdjustLogAdjustItem bHistoryAdjustLogAdjustItem = new BHistoryAdjustLogAdjustItem();
					
					bHistoryAdjustLogAdjustItem.setVersion(historyAdjustLogAdjustItem.getVersion());
					bHistoryAdjustLogAdjustItem.setId(historyAdjustLogAdjustItem.getId());
					bHistoryAdjustLogAdjustItem.setHistoryAdjustLog(_bHistoryAdjustLog);
					bHistoryAdjustLogAdjustItem.setPeriod( historyAdjustLogAdjustItem.getPeriod() );
					bHistoryAdjustLogAdjustItem.setBizData( bizDataBDConvertor.dtob( historyAdjustLogAdjustItem.getBizData(), true ) );
					bHistoryAdjustLogAdjustItem.setProduct( (BProduct)productBDConvertor.dtob( historyAdjustLogAdjustItem.getProduct() ) );
					bHistoryAdjustLogAdjustItem.setProductCharacter( (BProductCharacter)productCharacterBDConvertor.dtob( historyAdjustLogAdjustItem.getProductCharacter() ) );
					bHistoryAdjustLogAdjustItem.setOrganization( (BOrganization)organizationBDConvertor.dtob( historyAdjustLogAdjustItem.getOrganization() ) );
					bHistoryAdjustLogAdjustItem.setOrganizationCharacter( (BOrganizationCharacter)organizationCharacterBDConvertor.dtob( historyAdjustLogAdjustItem.getOrganizationCharacter() ) );																
					bHistoryAdjustLogAdjustItem.setOldValue( historyAdjustLogAdjustItem.getOldValue() );
					bHistoryAdjustLogAdjustItem.setNewValue( historyAdjustLogAdjustItem.getNewValue() );
					bHistoryAdjustLogAdjustItem.setDescription( historyAdjustLogAdjustItem.getDescription() );
					bHistoryAdjustLogAdjustItem.setComments( historyAdjustLogAdjustItem.getComments() );				
					
					_bHistoryAdjustLog.addHistoryAdjustLogAdjustItem( bHistoryAdjustLogAdjustItem );
				}
			}							
		}

	}
	

	public BHistoryAdjustLog dtob( HistoryAdjustLog _historyAdjustLog, boolean _blWithProOrgs, boolean _blWithAdjustItems )
	{
		if( _historyAdjustLog == null )
		{
			return null;
		}		
		BHistoryAdjustLog bHistoryAdjustLog = new BHistoryAdjustLog();
		this.dtob( _historyAdjustLog, bHistoryAdjustLog, _blWithProOrgs, _blWithAdjustItems );
		return bHistoryAdjustLog;		
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
