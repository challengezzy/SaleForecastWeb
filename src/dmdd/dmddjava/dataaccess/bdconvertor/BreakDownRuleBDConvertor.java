/**********************************************************************
 *$RCSfile:BreakDownRuleBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import java.util.Iterator;

import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRule;
import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleFinancialDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRule;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleFinancialDefItem;
import dmdd.dmddjava.dataaccess.utils.UtilFactoryBizDataDefItem;

/**
 * <li>Title: BreakDownRuleBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BreakDownRuleBDConvertor implements BDConvertorInterface
{

	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBreakDownRule bBreakDownRule = null;
		BreakDownRule   breakDownRule = null;
		
		if( b_obj == null )
		{
			bBreakDownRule = new BBreakDownRule();
		}
		else
		{
			bBreakDownRule = (BBreakDownRule) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			breakDownRule = (BreakDownRule) d_obj;
		}
		
		breakDownRule.setVersion( bBreakDownRule.getVersion() );
		breakDownRule.setId( bBreakDownRule.getId() );
		breakDownRule.setCode( bBreakDownRule.getCode() );
		breakDownRule.setName( bBreakDownRule.getName() );
		breakDownRule.setType( bBreakDownRule.getType() );
		breakDownRule.setBeginPeriod(bBreakDownRule.getBeginPeriod());
		breakDownRule.setEndPeriod(bBreakDownRule.getEndPeriod());
		breakDownRule.setDescription( bBreakDownRule.getDescription() );
		breakDownRule.setComments( bBreakDownRule.getComments() );
	}

	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BreakDownRule   breakDownRule = null;
		BBreakDownRule bBreakDownRule = null;
		
		if( d_obj == null )
		{
			breakDownRule = new BreakDownRule();
		}
		else
		{
			breakDownRule = (BreakDownRule) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBreakDownRule = (BBreakDownRule) b_obj;
		}
		
		bBreakDownRule.setVersion( breakDownRule.getVersion() );
		bBreakDownRule.setId( breakDownRule.getId() );
		bBreakDownRule.setCode( breakDownRule.getCode() );
		bBreakDownRule.setName( breakDownRule.getName() );
		bBreakDownRule.setType( breakDownRule.getType() );
		bBreakDownRule.setBeginPeriod(breakDownRule.getBeginPeriod());
		bBreakDownRule.setEndPeriod(breakDownRule.getEndPeriod());
		bBreakDownRule.setDescription( breakDownRule.getDescription() );
		bBreakDownRule.setComments( breakDownRule.getComments() );
	}

	@Override
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}
		BreakDownRule breakDownRule = new BreakDownRule();
		this.btod(b_obj, breakDownRule);
		return breakDownRule;
	}

	@Override
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}
		BBreakDownRule bBreakDownRule = new BBreakDownRule();
		this.dtob(d_obj, bBreakDownRule);
		return bBreakDownRule;
	}

	public void btod(BBreakDownRule _bBreakDownRule, BreakDownRule _breakDownRule, boolean _blWithBreakDownRuleDefItems )
	{
		if( _breakDownRule == null )
		{
			return;
		}
		
		this.btod(_bBreakDownRule, _breakDownRule);
		
		if( _blWithBreakDownRuleDefItems == true )
		{
			if( _bBreakDownRule != null && _bBreakDownRule.getBreakDownRuleDefItems() != null && _bBreakDownRule.getBreakDownRuleDefItems().iterator() != null )
			{
				Iterator<BBreakDownRuleDefItem> itr_bBreakDownRuleDefItem = _bBreakDownRule.getBreakDownRuleDefItems().iterator();
				while( itr_bBreakDownRuleDefItem.hasNext() )
				{
					BBreakDownRuleDefItem bBreakDownRuleDefItem = itr_bBreakDownRuleDefItem.next();
					BreakDownRuleDefItemBDConvertor breakDownRuleDefItemBDConvertor = new BreakDownRuleDefItemBDConvertor();
					BreakDownRuleDefItem bizDataDefItem = (BreakDownRuleDefItem) breakDownRuleDefItemBDConvertor.btod(bBreakDownRuleDefItem);
					
					bizDataDefItem.setBreakDownRule( _breakDownRule );
					_breakDownRule.addBreakDownRuleDefItems( bizDataDefItem );
				}
			}
			if( _bBreakDownRule != null && _bBreakDownRule.getBreakDownRuleFinancialDefItems() != null && _bBreakDownRule.getBreakDownRuleFinancialDefItems().iterator() != null )
			{
				Iterator<BBreakDownRuleFinancialDefItem> itr_bBreakDownRuleDefItem = _bBreakDownRule.getBreakDownRuleFinancialDefItems().iterator();
				while( itr_bBreakDownRuleDefItem.hasNext() )
				{
					BBreakDownRuleFinancialDefItem bBreakDownRuleDefItem = itr_bBreakDownRuleDefItem.next();
					BreakDownRuleFinancialDefItemBDConvertor breakDownRuleDefItemBDConvertor = new BreakDownRuleFinancialDefItemBDConvertor();
					BreakDownRuleFinancialDefItem bizDataDefItem = (BreakDownRuleFinancialDefItem) breakDownRuleDefItemBDConvertor.btod(bBreakDownRuleDefItem);
					
					bizDataDefItem.setBreakDownRule( _breakDownRule );
					_breakDownRule.addBreakDownRuleFinancialDefItems( bizDataDefItem );
				}
			}	
		}
	}
	
	public BreakDownRule btod(BBreakDownRule _bbreakDownRule, boolean _blWithBizDataDefItems)
	{
		if( _bbreakDownRule == null )
		{
			return null;
		}
		BreakDownRule breakDownRule = new BreakDownRule();
		this.btod(_bbreakDownRule, breakDownRule, _blWithBizDataDefItems);
		return breakDownRule;
	}
	
	
	public void dtob(BreakDownRule _breakDownRule, BBreakDownRule _bBreakDownRule, boolean _blWithBreakDownRuleDefItems)
	{
		if( _bBreakDownRule == null )
		{
			return;
		}
		
		this.dtob(_breakDownRule, _bBreakDownRule);
		
		if( _blWithBreakDownRuleDefItems == true )
		{
			if( _breakDownRule != null && _breakDownRule.getBreakDownRuleDefItems() != null && _breakDownRule.getBreakDownRuleDefItems().iterator() != null )
			{
				Iterator<BreakDownRuleDefItem> itr_bizDataDefItems = _breakDownRule.getBreakDownRuleDefItems().iterator();
				while( itr_bizDataDefItems.hasNext() )
				{
					BreakDownRuleDefItem breakDownRuleDefItem = itr_bizDataDefItems.next();
					BreakDownRuleDefItemBDConvertor breakDownRuleDefItemBDConvertor = new BreakDownRuleDefItemBDConvertor();
					BBreakDownRuleDefItem bBBreakDownRuleDefItem = (BBreakDownRuleDefItem) breakDownRuleDefItemBDConvertor.dtob(breakDownRuleDefItem);
					
					bBBreakDownRuleDefItem.setBreakDownRule(_bBreakDownRule);
					_bBreakDownRule.addBreakDownRuleDefItems( bBBreakDownRuleDefItem );
				}
			}
			if( _breakDownRule != null && _breakDownRule.getBreakDownRuleFinancialDefItems() != null && _breakDownRule.getBreakDownRuleFinancialDefItems().iterator() != null )
			{
				Iterator<BreakDownRuleFinancialDefItem> itr_bizDataDefItems = _breakDownRule.getBreakDownRuleFinancialDefItems().iterator();
				while( itr_bizDataDefItems.hasNext() )
				{
					BreakDownRuleFinancialDefItem breakDownRuleDefItem = itr_bizDataDefItems.next();
					BreakDownRuleFinancialDefItemBDConvertor breakDownRuleDefItemBDConvertor = new BreakDownRuleFinancialDefItemBDConvertor();
					BBreakDownRuleFinancialDefItem bBBreakDownRuleDefItem = (BBreakDownRuleFinancialDefItem) breakDownRuleDefItemBDConvertor.dtob(breakDownRuleDefItem);
					
					bBBreakDownRuleDefItem.setBreakDownRule(_bBreakDownRule);
					_bBreakDownRule.addBreakDownRuleFinancialDefItems( bBBreakDownRuleDefItem );
				}
			}				
		}
			
	}
	
	public BBreakDownRule dtob(BreakDownRule _breakDownRule, boolean _blWithBreakDownRuleDefItems)
	{
		if( _breakDownRule == null )
		{
			return null;
		}
		BBreakDownRule bBreakDownRule = new BBreakDownRule();
		this.dtob(_breakDownRule, bBreakDownRule, _blWithBreakDownRuleDefItems);
		return bBreakDownRule;		
	}
}

/**********************************************************************
 *$RCSfile:BreakDownRuleBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *
 *$Log:BreakDownRuleBDConvertor.java,v $
 *********************************************************************/