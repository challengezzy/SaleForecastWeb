package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleFinancialDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleFinancialDefItem;

public class BreakDownRuleFinancialDefItemBDConvertor  implements BDConvertorInterface
{


	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBreakDownRuleFinancialDefItem bBreakDownRuleDefItem = null;
		BreakDownRuleFinancialDefItem   beakDownRuleDefItem = null;
		
		if( b_obj == null )
		{
			bBreakDownRuleDefItem = new BBreakDownRuleFinancialDefItem();
		}
		else
		{
			bBreakDownRuleDefItem = (BBreakDownRuleFinancialDefItem) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			beakDownRuleDefItem = (BreakDownRuleFinancialDefItem) d_obj;
		}
		
		beakDownRuleDefItem.setVersion( bBreakDownRuleDefItem.getVersion() );
		beakDownRuleDefItem.setId( bBreakDownRuleDefItem.getId() );
		beakDownRuleDefItem.setWeekCode(bBreakDownRuleDefItem.getWeekCode());
		beakDownRuleDefItem.setPeriod(bBreakDownRuleDefItem.getPeriod());
		beakDownRuleDefItem.setBeginDate(bBreakDownRuleDefItem.getBeginDate());
		beakDownRuleDefItem.setEndDate(bBreakDownRuleDefItem.getEndDate());
		beakDownRuleDefItem.setProportion(bBreakDownRuleDefItem.getProportion());
//		
//		if(bBreakDownRuleDefItem.getBreakDownRule() !=null)
//		{
//			BreakDownRuleBDConvertor bd = new BreakDownRuleBDConvertor();
//			BreakDownRule breakDownRule = (BreakDownRule)bd.btod(bBreakDownRuleDefItem);
//			beakDownRuleDefItem.setBreakDownRule(breakDownRule);
//		}
	}

	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BreakDownRuleFinancialDefItem  breakDownRuleDefItem = null;
		BBreakDownRuleFinancialDefItem bBreakDownRuleDefItem = null;
				
		if( d_obj == null )
		{
			breakDownRuleDefItem = new BreakDownRuleFinancialDefItem();
		}
		else
		{
			breakDownRuleDefItem = (BreakDownRuleFinancialDefItem) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBreakDownRuleDefItem = (BBreakDownRuleFinancialDefItem) b_obj;
		}
		
		bBreakDownRuleDefItem.setVersion( breakDownRuleDefItem.getVersion() );
		bBreakDownRuleDefItem.setId( breakDownRuleDefItem.getId() );
		bBreakDownRuleDefItem.setWeekCode(breakDownRuleDefItem.getWeekCode());
		bBreakDownRuleDefItem.setPeriod(breakDownRuleDefItem.getPeriod());
		bBreakDownRuleDefItem.setBeginDate(breakDownRuleDefItem.getBeginDate());
		bBreakDownRuleDefItem.setEndDate(breakDownRuleDefItem.getEndDate());
		bBreakDownRuleDefItem.setProportion(breakDownRuleDefItem.getProportion());
	}

	@Override
	public Object btod(Object b_obj)
	{
		BreakDownRuleFinancialDefItem breakDownRuleDefItem = new BreakDownRuleFinancialDefItem();
		this.btod(b_obj, breakDownRuleDefItem);
		return breakDownRuleDefItem;
	}

	@Override
	public Object dtob(Object d_obj)
	{
		BBreakDownRuleFinancialDefItem bBreakDownRuleDefItem = new BBreakDownRuleFinancialDefItem();
		this.dtob(d_obj, bBreakDownRuleDefItem);
		return bBreakDownRuleDefItem;
	}
}
