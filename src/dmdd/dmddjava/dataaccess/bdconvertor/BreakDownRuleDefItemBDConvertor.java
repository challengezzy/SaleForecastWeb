/**********************************************************************
 *$RCSfile:BreakDownRuleDefItemBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBreakDownRuleDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BreakDownRuleDefItem;

/**
 * <li>Title: BreakDownRuleDefItemBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BreakDownRuleDefItemBDConvertor implements BDConvertorInterface
{

	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBreakDownRuleDefItem bBreakDownRuleDefItem = null;
		BreakDownRuleDefItem   beakDownRuleDefItem = null;
		
		if( b_obj == null )
		{
			bBreakDownRuleDefItem = new BBreakDownRuleDefItem();
		}
		else
		{
			bBreakDownRuleDefItem = (BBreakDownRuleDefItem) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			beakDownRuleDefItem = (BreakDownRuleDefItem) d_obj;
		}
		
		beakDownRuleDefItem.setVersion( bBreakDownRuleDefItem.getVersion() );
		beakDownRuleDefItem.setId( bBreakDownRuleDefItem.getId() );
		beakDownRuleDefItem.setIsConnected(bBreakDownRuleDefItem.getIsConnected());
		beakDownRuleDefItem.setPeriod(bBreakDownRuleDefItem.getPeriod());
		beakDownRuleDefItem.setWeek1(bBreakDownRuleDefItem.getWeek1());
		beakDownRuleDefItem.setWeek2(bBreakDownRuleDefItem.getWeek2());
		beakDownRuleDefItem.setWeek3(bBreakDownRuleDefItem.getWeek3());
		beakDownRuleDefItem.setWeek4(bBreakDownRuleDefItem.getWeek4());
		beakDownRuleDefItem.setWeek5(bBreakDownRuleDefItem.getWeek5());
		beakDownRuleDefItem.setWeek6(bBreakDownRuleDefItem.getWeek6());
		beakDownRuleDefItem.setFirstDay(bBreakDownRuleDefItem.getFirstDay());
		beakDownRuleDefItem.setNum_week(bBreakDownRuleDefItem.getNum_week());
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
		BreakDownRuleDefItem   breakDownRuleDefItem = null;
		BBreakDownRuleDefItem bBreakDownRuleDefItem = null;
				
		if( d_obj == null )
		{
			breakDownRuleDefItem = new BreakDownRuleDefItem();
		}
		else
		{
			breakDownRuleDefItem = (BreakDownRuleDefItem) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBreakDownRuleDefItem = (BBreakDownRuleDefItem) b_obj;
		}
		
		bBreakDownRuleDefItem.setVersion( breakDownRuleDefItem.getVersion() );
		bBreakDownRuleDefItem.setId( breakDownRuleDefItem.getId() );
		bBreakDownRuleDefItem.setIsConnected(breakDownRuleDefItem.getIsConnected());
		bBreakDownRuleDefItem.setPeriod(breakDownRuleDefItem.getPeriod());
		bBreakDownRuleDefItem.setWeek1(breakDownRuleDefItem.getWeek1());
		bBreakDownRuleDefItem.setWeek2(breakDownRuleDefItem.getWeek2());
		bBreakDownRuleDefItem.setWeek3(breakDownRuleDefItem.getWeek3());
		bBreakDownRuleDefItem.setWeek4(breakDownRuleDefItem.getWeek4());
		bBreakDownRuleDefItem.setWeek5(breakDownRuleDefItem.getWeek5());
		bBreakDownRuleDefItem.setWeek6(breakDownRuleDefItem.getWeek6());
		bBreakDownRuleDefItem.setFirstDay(breakDownRuleDefItem.getFirstDay());
		bBreakDownRuleDefItem.setNum_week(breakDownRuleDefItem.getNum_week());
//		if( breakDownRuleDefItem.getBreakDownRule() != null )
//		{
//			BreakDownRuleBDConvertor bd = new BreakDownRuleBDConvertor();
//			BBreakDownRule bBreakDownRule = (BBreakDownRule)bd.dtob( breakDownRuleDefItem.getBreakDownRule(), true );
//			bBreakDownRuleDefItem.setBreakDownRule(bBreakDownRule);
//		}
	}

	@Override
	public Object btod(Object b_obj)
	{
		BreakDownRuleDefItem breakDownRuleDefItem = new BreakDownRuleDefItem();
		this.btod(b_obj, breakDownRuleDefItem);
		return breakDownRuleDefItem;
	}

	@Override
	public Object dtob(Object d_obj)
	{
		BBreakDownRuleDefItem bBreakDownRuleDefItem = new BBreakDownRuleDefItem();
		this.dtob(d_obj, bBreakDownRuleDefItem);
		return bBreakDownRuleDefItem;
	}

}

/**********************************************************************
 *$RCSfile:BreakDownRuleDefItemBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *
 *$Log:BreakDownRuleDefItemBDConvertor.java,v $
 *********************************************************************/