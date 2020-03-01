package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrgPathCode;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScope;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.dm.ProOrgDM;

/**
 * @author liuzhen
 * 
 */
public class UtilProOrg
{

	public final static int RELATION_FIRST2SECOND_COVERING		= 0;	//FIRST => SECOND
	public final static int RELATION_FIRST2SECOND_COVERED		= 1;	//FIRST <= SECOND
	public final static int RELATION_FIRST2SECOND_EQUAL			= 2;	//FIRST == SECOND
	public final static int RELATION_FIRST2SECOND_IRRELATED		= 3;	//FIRST and SECOND == null
	public final static int RELATION_FIRST2SECOND_INTERSECT_1	= 4;	//FIRST and SECOND != null P1>P2 O1<O2 
	public final static int RELATION_FIRST2SECOND_INTERSECT_2	= 5;	//FIRST and SECOND != null P1<P2 O1>O2
	
	/**
	 * 获取字符串中的产品ID
	 * @param proorgId 形式为 (1890,38)
	 */
	public static String getPidByIdStr(String proorgId){
		String pid = proorgId.substring( proorgId.indexOf("(")+1, proorgId.indexOf(",")).trim();
		return pid;
	}
	
	/**
	 * 获取字符串中的组织ID
	 * @param proorgId 形式为 (1890,38)
	 */
	public static String getOidByIdStr(String proorgId){
		String oid = proorgId.substring( proorgId.indexOf(",")+1, proorgId.indexOf(")")).trim();
		return oid;
	}

	public static HashMap<String, List<AProOrg>> getGroupedDetailProOrgs(
			HashMap<String, AProOrg> hmap4DetailProOrgs, int _organizationLayerValue, int _productLayerValue)
	{
		HashMap<String, List<AProOrg>> hmap4List4GroupedDetailProOrgs = new HashMap<String, List<AProOrg>>();

		if (hmap4DetailProOrgs.values() != null && !(hmap4DetailProOrgs.values().isEmpty()))
		{
			Iterator<AProOrg> itr_hmap4DetailProOrgs_values = hmap4DetailProOrgs.values().iterator();
			while (itr_hmap4DetailProOrgs_values.hasNext())
			{
				AProOrg detailProOrg = itr_hmap4DetailProOrgs_values.next();
				Product projectProduct = UtilProduct.getProjectProductByLayer(detailProOrg.getProduct(),
						_productLayerValue);				
				Organization projectOrganization = UtilOrganization.getProjectOrganizationByLayer(detailProOrg
						.getOrganization(), _organizationLayerValue);

				String key4ProjectAProOrg = "";
				if (projectProduct == null)
				{
					key4ProjectAProOrg = key4ProjectAProOrg + "-1" + "_";
					
				}
				else
				{
					key4ProjectAProOrg = key4ProjectAProOrg + projectProduct.getId().longValue() + "_";
				}				
				if (projectOrganization == null)
				{
					key4ProjectAProOrg = key4ProjectAProOrg + "-1";
				}
				else
				{
					key4ProjectAProOrg = key4ProjectAProOrg + projectOrganization.getId().longValue();
				}


				List<AProOrg> list4GroupedDetailProOrgs = hmap4List4GroupedDetailProOrgs
						.get(key4ProjectAProOrg);
				if (list4GroupedDetailProOrgs == null)
				{
					list4GroupedDetailProOrgs = new ArrayList<AProOrg>();
				}
				list4GroupedDetailProOrgs.add(detailProOrg);
				hmap4List4GroupedDetailProOrgs.put(key4ProjectAProOrg, list4GroupedDetailProOrgs);
			}
		}

		return hmap4List4GroupedDetailProOrgs;
	}

	public static HashMap<String, AProOrg> getDetailProOrgs(List<ABProOrg> _list4ProOrgScope,
			Session _session, Boolean _blOnly4ValidProOrg)
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();

		DaoOrganization daoOrganization = new DaoOrganization(_session);
		DaoProduct daoProduct = new DaoProduct(_session);

		Iterator<ABProOrg> itr_aBProOrgs4ForecastScope = _list4ProOrgScope.iterator();
		while (itr_aBProOrgs4ForecastScope.hasNext())
		{
			ABProOrg aBProOrg = itr_aBProOrgs4ForecastScope.next();

			Product product = daoProduct.getProductById(aBProOrg.getProduct().getId());
			Organization organization = daoOrganization.getOrganizationById(aBProOrg.getOrganization().getId());
			
			if ( product != null && organization != null )
			{
				List<Product> list4DetailProducts = UtilProduct.getDetailProducts(product);
				List<Organization> list4DetailOrganizations = UtilOrganization.getDetailOrganizations(organization);
				HashMap<String, AProOrg> tempHashMap4DetailProOrgs = getDetailProOrgs(
						list4DetailProducts, list4DetailOrganizations, _blOnly4ValidProOrg);

				hmap4DetailProOrgs.putAll(tempHashMap4DetailProOrgs);
			}
		}

		return hmap4DetailProOrgs;
	}

	public static HashMap<String, AProOrg> getDetailProOrgs4ForecastInst(
			Set<ForecastInstProOrg> _set4ForecastInstProOrg, Boolean _blOnly4ValidProOrg)
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();
		if (_set4ForecastInstProOrg == null || _set4ForecastInstProOrg.isEmpty())
		{
			return hmap4DetailProOrgs;
		}

		Iterator<ForecastInstProOrg> itr_set4ForecastInstProOrg = _set4ForecastInstProOrg.iterator();
		while (itr_set4ForecastInstProOrg.hasNext())
		{
			ForecastInstProOrg forecastInstProOrg = itr_set4ForecastInstProOrg.next();

			Product product = forecastInstProOrg.getProduct();
			Organization organization = forecastInstProOrg.getOrganization();
			
			if (product != null && organization != null)
			{
				List<Product> list4DetailProducts = UtilProduct.getDetailProducts(product);
				List<Organization> list4DetailOrganizations = UtilOrganization.getDetailOrganizations(organization);
				HashMap<String, AProOrg> tempHashMap4DetailProOrgs = getDetailProOrgs(
						list4DetailProducts, list4DetailOrganizations, _blOnly4ValidProOrg);

				hmap4DetailProOrgs.putAll(tempHashMap4DetailProOrgs);
			}
		}

		return hmap4DetailProOrgs;
	}
	
	public static HashMap<String, AProOrg> getDetailProOrgs( ForecastModelMAAnalogItem _forecastModelMAAnalogItem, Boolean _blOnly4ValidProOrg )
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();
		
		if( _forecastModelMAAnalogItem == null )
		{
			return hmap4DetailProOrgs;
		}

		Product product = _forecastModelMAAnalogItem.getProduct();
		Organization organization = _forecastModelMAAnalogItem.getOrganization();

		if ( product != null && organization != null )
		{
			List<Product> list4DetailProducts = UtilProduct.getDetailProducts( product );
			List<Organization> list4DetailOrganizations = UtilOrganization.getDetailOrganizations( organization );
			hmap4DetailProOrgs = getDetailProOrgs( list4DetailProducts, list4DetailOrganizations, _blOnly4ValidProOrg );
		}

		return hmap4DetailProOrgs;
	}
	
	public static HashMap<String, AProOrg> getDetailProOrgs( BForecastModelMAAnalogItem _bForecastModelMAAnalogItem, Session _session, Boolean _blOnly4ValidProOrg )
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();

		DaoOrganization daoOrganization = new DaoOrganization( _session );
		DaoProduct daoProduct = new DaoProduct( _session );

		Product product = daoProduct.getProductById( _bForecastModelMAAnalogItem.getProduct().getId() );
		Organization organization = daoOrganization.getOrganizationById( _bForecastModelMAAnalogItem.getOrganization().getId() );

		if ( product != null && organization != null )
		{
			List<Product> list4DetailProducts = UtilProduct.getDetailProducts( product );
			List<Organization> list4DetailOrganizations = UtilOrganization.getDetailOrganizations( organization );
			hmap4DetailProOrgs = getDetailProOrgs( list4DetailProducts, list4DetailOrganizations, _blOnly4ValidProOrg );
		}

		return hmap4DetailProOrgs;
	}	
	
	
	public static HashMap<String, AProOrg> getDetailProOrgs(List<Product> _list4DetailProducts, List<Organization> _list4DetailOrganizations,
			Boolean _blOnly4ValidProOrg)
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();

		if (_list4DetailOrganizations != null && !(_list4DetailOrganizations.isEmpty()) && _list4DetailProducts != null
			&& !(_list4DetailProducts.isEmpty()))
		{
			Iterator<Product> itr_list4DetailProducts = _list4DetailProducts.iterator();
			
			while (itr_list4DetailProducts.hasNext())
			{
				Product detailProduct = itr_list4DetailProducts.next();
				if (_blOnly4ValidProOrg == true)
				{
					if (detailProduct.getIsValid() == BizConst.GLOBAL_YESNO_NO)
					{
						continue;
					}
				}

				Iterator<Organization> itr_list4DetailOrganizations = _list4DetailOrganizations.iterator();
				while (itr_list4DetailOrganizations.hasNext())
				{
					Organization detailOrganization = itr_list4DetailOrganizations.next();
					if (_blOnly4ValidProOrg == true)
					{
						if (detailOrganization.getIsValid() == BizConst.GLOBAL_YESNO_NO)
						{
							continue;
						}
					}
					

					String key4DetailProOrg = "" + detailProduct.getId().longValue() + "_"
						+ detailOrganization.getId().longValue();
					AProOrg value4DetailProOrg = new AProOrg(detailProduct, detailOrganization);

					hmap4DetailProOrgs.put(key4DetailProOrg, value4DetailProOrg);
				}
			}
		}
		return hmap4DetailProOrgs;
	}
	
	public static HashMap<String, ABProOrg> getDetailProOrgs2(List<BProduct> _list4DetailProducts, List<BOrganization> _list4DetailOrganizations,
			Boolean _blOnly4ValidProOrg)
	{
		HashMap<String, ABProOrg> hmap4DetailProOrgs = new HashMap<String, ABProOrg>();

		if (_list4DetailOrganizations != null && !(_list4DetailOrganizations.isEmpty()) && _list4DetailProducts != null
			&& !(_list4DetailProducts.isEmpty()))
		{
			Iterator<BProduct> itr_list4DetailProducts = _list4DetailProducts.iterator();
			
			while (itr_list4DetailProducts.hasNext())
			{
				BProduct detailProduct = itr_list4DetailProducts.next();
				if (_blOnly4ValidProOrg == true)
				{
					if (detailProduct.getIsValid() == BizConst.GLOBAL_YESNO_NO)
					{
						continue;
					}
				}

				Iterator<BOrganization> itr_list4DetailOrganizations = _list4DetailOrganizations.iterator();
				while (itr_list4DetailOrganizations.hasNext())
				{
					BOrganization detailOrganization = itr_list4DetailOrganizations.next();
					if (_blOnly4ValidProOrg == true)
					{
						if (detailOrganization.getIsValid() == BizConst.GLOBAL_YESNO_NO)
						{
							continue;
						}
					}
					

					String key4DetailProOrg = "" + detailProduct.getId().longValue() + "_"
						+ detailOrganization.getId().longValue();
					ABProOrg value4DetailProOrg = new ABProOrg(detailProduct, detailOrganization);

					hmap4DetailProOrgs.put(key4DetailProOrg, value4DetailProOrg);
				}
			}
		}
		return hmap4DetailProOrgs;
	}

	public static HashMap<String, AProOrg> getDetailProOrgs(
			Set<BForecastMakeLogProOrg> _set4BForecastMakeLogProOrg, Session _session, Boolean _blOnly4ValidProOrg)
	{
		HashMap<String, AProOrg> hmap4DetailProOrgs = new HashMap<String, AProOrg>();
		if (_set4BForecastMakeLogProOrg == null || _set4BForecastMakeLogProOrg.isEmpty())
		{
			return hmap4DetailProOrgs;
		}
		
		DaoOrganization daoOrganization = new DaoOrganization(_session);
		DaoProduct daoProduct = new DaoProduct(_session);		

		Iterator<BForecastMakeLogProOrg> itr_set4BForecastMakeLogProOrg = _set4BForecastMakeLogProOrg.iterator();
		while (itr_set4BForecastMakeLogProOrg.hasNext())
		{
			BForecastMakeLogProOrg bForecastMakeLogProOrg = itr_set4BForecastMakeLogProOrg.next();

			Product product = daoProduct.getProductById(bForecastMakeLogProOrg.getProduct().getId());
			Organization organization = daoOrganization.getOrganizationById(bForecastMakeLogProOrg.getOrganization().getId());
			
			if (product != null && organization != null)
			{
				List<Organization> list4DetailOrganizations = UtilOrganization.getDetailOrganizations(organization);
				List<Product> list4DetailProducts = UtilProduct.getDetailProducts(product);
				HashMap<String, AProOrg> tempHashMap4DetailProOrgs = getDetailProOrgs(
						list4DetailProducts, list4DetailOrganizations, _blOnly4ValidProOrg);

				hmap4DetailProOrgs.putAll(tempHashMap4DetailProOrgs);
			}
		}

		return hmap4DetailProOrgs;
	}

	public static String getIdsScopeStr4ProOrgs(List<AProOrg> _list4ProOrgs)
	{
		String rstStr = "((-1,-1)";
		if (_list4ProOrgs != null && !(_list4ProOrgs.isEmpty()))
		{
			Iterator<AProOrg> itr_list4ProOrgs = _list4ProOrgs.iterator();
			while (itr_list4ProOrgs.hasNext())
			{
				AProOrg aProOrg = itr_list4ProOrgs.next();
				rstStr = rstStr + " , (" + aProOrg.getProduct().getId().longValue() + ","
					+ aProOrg.getOrganization().getId().longValue() + ")";
			}
		}
		rstStr = rstStr + " )";
		return rstStr;
	}
	
	public static String getIdsScopeStr4ProOrgs1(List<ABProOrg> _list4ProOrgs)
	{
		String rstStr = "((-1,-1)";
		if (_list4ProOrgs != null && !(_list4ProOrgs.isEmpty()))
		{
			Iterator<ABProOrg> itr_list4ProOrgs = _list4ProOrgs.iterator();
			while (itr_list4ProOrgs.hasNext())
			{
				ABProOrg aProOrg = itr_list4ProOrgs.next();
				rstStr = rstStr + " , (" + aProOrg.getProduct().getId().longValue() + ","
					+ aProOrg.getOrganization().getId().longValue() + ")";
			}
		}
		rstStr = rstStr + " )";
		return rstStr;
	}
	
	public static String getIdsScopeStr4ProOrgs(HashMap<String,AProOrg> _hmap4ProOrgs)
	{
		String rstStr = "((-1,-1)";
		if (_hmap4ProOrgs != null && !(_hmap4ProOrgs.isEmpty()))
		{
			Iterator<AProOrg> itr_hmap4ProOrgs_values = _hmap4ProOrgs.values().iterator();
			while (itr_hmap4ProOrgs_values.hasNext())
			{
				AProOrg aProOrg = itr_hmap4ProOrgs_values.next();
				rstStr = rstStr + " , (" + aProOrg.getProduct().getId().longValue() + ","
					+ aProOrg.getOrganization().getId().longValue() + ")";
			}
		}
		rstStr = rstStr + " )";
		return rstStr;
	}	
	
	public static String getIds4msslqStr4ProOrgs(HashMap<String,AProOrg> _hmap4ProOrgs)
	{
		String rstStr = "((productid = -1 and organizationid = -1)";
		for(AProOrg aProOrg :_hmap4ProOrgs.values())
		{		
			rstStr = rstStr + " or (productid =  " + aProOrg.getProduct().getId().longValue() + " and organizationid ="
				+ aProOrg.getOrganization().getId().longValue() + ")";
			
		}
		rstStr = rstStr + " )";
		return rstStr;
	}

	public static String format4mssqlstr4ids(String[] idsArr)
	{
		String productid="",organizationid="";
		//只有一对proid, orgid
		if(idsArr.length == 1){
			String idArr = idsArr[0];
			idArr = idArr.replaceAll("\\(", "").replaceAll("\\)", "");
			productid = idArr.substring(0,idArr.indexOf(","));
			organizationid = idArr.substring(idArr.indexOf(",")+1);
			return " productid = "+productid +" and organizationid="+organizationid+" ";
		}else{
			StringBuffer rstStr = new StringBuffer();
			rstStr.append("(");
			boolean isFirst = true;
			for(int i=0;i<idsArr.length;i++)
			{
				String idArr = idsArr[i];
				idArr = idArr.replaceAll("\\(", "").replaceAll("\\)", "");
				productid = idArr.substring(0,idArr.indexOf(","));
				organizationid = idArr.substring(idArr.indexOf(",")+1);
				
				if(isFirst){
					isFirst = false;
					rstStr.append(" (productid="+productid +" and organizationid="+organizationid+")");
				}else{
					rstStr.append(" or (productid="+productid +" and organizationid="+organizationid+")");
				}
			}
			rstStr.append(" )");
			return rstStr.toString();
		}
	}
	
	/**
	 * 转换为sql server的查询条件格式.
	 * @param ids
	 * @return
	 */
	public static String format4mssqlstr4ids(String idsStr)
	{
		idsStr = idsStr.replaceAll(" ", "");
		String[] idsArr = idsStr.split("\\),\\(");
		
		return format4mssqlstr4ids(idsArr);
	}
	
	/**
	 * 当量实在太大时，只能一个一个处理
	 * @param ids
	 * @return
	 */
	public static String[] format4sqlstr4ids(String ids)
	{		
		ids = ids.replaceAll(" ", "");
		String[] idsArr = ids.split("\\),\\(");
		
		String[] result = new String[idsArr.length];
		for(int i=0;i<idsArr.length;i++)
		{
			String idArr = idsArr[i];
			idArr = idArr.replaceAll("\\(", "");
			idArr = idArr.replaceAll("\\)", "");
			System.out.println(idArr);
			result[i] =idArr;
			
		}
		
		return result;
	}
	
	
	/**
	 * 拼装sqlserver语法，not in,一定要去掉-1，-1的组合
	 * @param ids
	 * @return
	 */
	public static String format4mssqlstr4idsNotIn(String ids,String tableName)
	{
		ids= format4mssqlstr4ids(ids);
		StringBuffer rstStr = new StringBuffer();
		rstStr.append(" id not in(");
		rstStr.append(" select id from "+tableName+" where "+ids+")");

		return rstStr.toString();
	}
	
	/**
	 * 将ids拼装字段分解，以免太大直接报错 
	 * @param ids
	 * @return
	 */
	public static String[] format4oracle2big(String ids)
	{
		ids = ids.replaceAll(" ", "");
		String[] idsArr = ids.split("\\),\\(");
		for(int i=0;i<idsArr.length;i++)
		{
			String idArr = idsArr[i];
			idArr = idArr.replaceAll("\\(", "");
			idArr = idArr.replaceAll("\\)", "");
			idsArr[i]=idArr;
			
		}
		return idsArr;
	}
	
	public static String format4mssqlstr4ids(String ids,String aStrkey,String bStrKey)
	{
		StringBuffer rstStr = new StringBuffer();
		rstStr.append("(("+aStrkey+" = -1 and "+bStrKey+" = -1)");
		ids = ids.replaceAll(" ", "");
		String[] idsArr = ids.split("\\),\\(");
		String productid="",organizationid="";
		
		for(int i=0;i<idsArr.length;i++)
		{
			String idArr = idsArr[i];
			idArr = idArr.replaceAll("\\(", "");
			idArr = idArr.replaceAll("\\)", "");
			productid = idArr.substring(0,idArr.indexOf(","));
			organizationid = idArr.substring(idArr.indexOf(",")+1);
			rstStr.append(" or ("+aStrkey+" = "+productid +" and "+bStrKey+"="+organizationid+" )");
		}
		rstStr.append(" )");
		return rstStr.toString();
	}
	
	public static String getIdsScopeStr4BProOrgs(List<ABProOrg> _list4BProOrgs)
	{
		String rstStr = "((-1,-1)";
		if (_list4BProOrgs != null && !(_list4BProOrgs.isEmpty()))
		{
			Iterator<ABProOrg> itr_list4ProOrgs = _list4BProOrgs.iterator();
			while (itr_list4ProOrgs.hasNext())
			{
				ABProOrg aBProOrg = itr_list4ProOrgs.next();
				rstStr = rstStr + " , (" + aBProOrg.getProduct().getId().longValue() + ","
					+ aBProOrg.getOrganization().getId().longValue() + ")";
			}
		}
		rstStr = rstStr + " )";
		return rstStr;
	}
	
	/**
	 * 获取ABUiRowDataProOrg中的所有明细业务范围
	 * @param _abUiRowDataProOrg
	 * @return
	 */
	public static String getIdsScopeStr4BProOrgs(ABUiRowDataProOrg _abUiRowDataProOrg)
	{
		StringBuilder sb = new StringBuilder("(");
		//boolean isFirst = true;
		if (_abUiRowDataProOrg != null && _abUiRowDataProOrg.getDetailProOrgIds() != null )
		{
			sb.append( StringUtils.join(_abUiRowDataProOrg.getDetailProOrgIds(), ",") );
//			for(String proOrgId : _abUiRowDataProOrg.getDetailProOrgIds() )
//			{
//				if(isFirst){
//					isFirst = false;
//					sb.append(proOrgId);
//				}
//				sb.append("," + proOrgId);
//			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 获取List<ABUiRowDataProOrg>中的所有明细业务范围ID
	 * @param _list4ABUiRowDataProOrg
	 * @return
	 */
	public static String getIdsScopeStr4ListBProOrgs(List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg)
	{
		String rstStr = "((-1,-1)";
		for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 )
		{
			ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
			if (abUiRowDataProOrg != null && abUiRowDataProOrg.getDetailProOrgIds() != null )
			{
				for(int j=0; j<abUiRowDataProOrg.getDetailProOrgIds().size(); j=j+1 )
				{
					rstStr =  rstStr + ", " + abUiRowDataProOrg.getDetailProOrgIds().get( j );
				}
			}
		}
		rstStr = rstStr + ")";
		return rstStr;
	} 
	
	
	public static String getIdsScopeStr4BProOrgs(ABUiRowData _abUiRowData)
	{
		String rstStr = "((-1,-1)";
		if (_abUiRowData != null && _abUiRowData.getDetailProOrgIds() != null )
		{
			for(int j=0; j<_abUiRowData.getDetailProOrgIds().size(); j=j+1 )
			{
				rstStr =  rstStr + ", " + _abUiRowData.getDetailProOrgIds().get( j );
			}
		}
		rstStr = rstStr + " )";
		return rstStr;
	}
	
	/**
	 * _idsScopeStr 形如 "((-1,-1), (1001,2001), (1002,2002) )"
	 * 返回结果是去掉头尾的"(" ")"，得到 (-1,-1), (1001,2001), (1002,2002)
	 * @param _idsScopeStr
	 * @return
	 */
	public static String getContentOfIdsScopeStr(String _idsScopeStr)
	{
		String rstStr = _idsScopeStr.substring( _idsScopeStr.indexOf( "(" )+1, _idsScopeStr.lastIndexOf( ")" ) );
		return rstStr;
	}	
	
	
	/**
	 * 计算 _aBProOrg_1 与  _aBProOrg_2 的关系 
	 * @param _aBProOrg_1
	 * @param _aBProOrg_2
	 * @return
	 */	
	public static int relationOf( ABProOrg _aBProOrg_1, ABProOrg _aBProOrg_2 )
	{			
		if( _aBProOrg_1 == null || _aBProOrg_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _aBProOrg_1.getProduct() == null || _aBProOrg_1.getOrganization() == null || 
				_aBProOrg_2.getProduct() == null || _aBProOrg_2.getOrganization() == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}			
		
		BProduct bProduct_1 = _aBProOrg_1.getProduct();
		BProduct bProduct_2 = _aBProOrg_2.getProduct();
		BOrganization bOrganization_1 = _aBProOrg_1.getOrganization();
		BOrganization bOrganization_2 = _aBProOrg_2.getOrganization();
		
		int relation_p1_p2 = UtilProduct.relationOf( bProduct_1, bProduct_2 );
		int relation_o1_o2 = UtilOrganization.relationOf( bOrganization_1, bOrganization_2 );

		if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1>P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1>P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1>P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_1;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1>P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1=P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1=P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1=P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1=P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}													
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1<P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_2;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1<P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1<P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1<P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
		{
			// P1nP2
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;				
		}	
		
		return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;					
	}		
	
	/**
	 * 计算 _aProOrg_1 与  _aProOrg_2 的关系 
	 * @param _aProOrg_1
	 * @param _aProOrg_2
	 * @return
	 */
	public static int relationOf_2( AProOrg _aProOrg_1, AProOrg _aProOrg_2 )
	{			
		if( _aProOrg_1 == null || _aProOrg_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _aProOrg_1.getProduct() == null || _aProOrg_1.getOrganization() == null || 
				_aProOrg_2.getProduct() == null || _aProOrg_2.getOrganization() == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}			
		
		Product product_1 = _aProOrg_1.getProduct();
		Product product_2 = _aProOrg_2.getProduct();
		Organization organization_1 = _aProOrg_1.getOrganization();
		Organization organization_2 = _aProOrg_2.getOrganization();
		
		int relation_p1_p2 = UtilProduct.relationOf_2( product_1, product_2 );
		int relation_o1_o2 = UtilOrganization.relationOf_2( organization_1, organization_2 );

		if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1>P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1>P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1>P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_1;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1>P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1=P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1=P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1=P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1=P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}													
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1<P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_2;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1<P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1<P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1<P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
		{
			// P1nP2
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;				
		}	
		
		return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;					
	}		
	
	/**
	 * 计算 _aProOrg_1 与  _aProOrg_2 的关系 
	 * @param _aProOrg_1
	 * @param _aProOrg_2
	 * @return
	 */
	public static int relationOf( AProOrg _aProOrg_1, AProOrg _aProOrg_2 )
	{			
		if( _aProOrg_1 == null || _aProOrg_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _aProOrg_1.getProduct() == null || _aProOrg_1.getOrganization() == null || 
				_aProOrg_2.getProduct() == null || _aProOrg_2.getOrganization() == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}			
		
		Product product_1 = _aProOrg_1.getProduct();
		Product product_2 = _aProOrg_2.getProduct();
		Organization organization_1 = _aProOrg_1.getOrganization();
		Organization organization_2 = _aProOrg_2.getOrganization();
		
		int relation_p1_p2 = UtilProduct.relationOf( product_1, product_2 );
		int relation_o1_o2 = UtilOrganization.relationOf( organization_1, organization_2 );

		if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1>P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1>P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1>P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_1;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1>P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1=P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1=P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1=P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1=P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}													
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1<P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_2;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1<P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1<P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1<P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
		{
			// P1nP2
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;				
		}	
		
		return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;					
	}		
	//从UtilProOrg.as迁移过来
	// 求两个ProOrg集合的并集
	// _arr4ProOrg_1 内部的 ProOrg 两两之间都是不相交的 ，且是合理合法的
	// _arr4ProOrg_2 内部的 ProOrg 两两之间都是不相交的		
	public static List<AProOrg> getUnion4arrProOrg(List<AProOrg> _arr4ProOrg_1,List<AProOrg> _arr4ProOrg_2 )
	{
		List<AProOrg> arr4AProOrg = new ArrayList<AProOrg>();
		List<AProOrg> arr4Res = new ArrayList<AProOrg>();
		
		if( _arr4ProOrg_1 == null || _arr4ProOrg_1.size() <= 0 )
		{
			if( _arr4ProOrg_2 == null || _arr4ProOrg_2.size() <=0 )
			{
				return arr4Res;	
			}
			else
			{
				return _arr4ProOrg_2;
			}
		}
		else if( _arr4ProOrg_2 == null || _arr4ProOrg_2.size() <= 0 )
		{
			return _arr4ProOrg_1;
		}
		
		// 二者都不为空
		HashMap<String,AProOrg> hmap4ProOrg = new HashMap<String,AProOrg>();
		String strKey4ProOrg = null;
		int index = 0;
		//由于当用户的现有业务范围太大时，下面的算法耗时太长，因此这里基于信任的原则重新计算。我们信任原有的业务范围都是不相交的，且是不包含关系的。
		
		// 先放到一起，过滤掉完全一样的    begin
		for( AProOrg proOrg :_arr4ProOrg_1 )
		{			
			strKey4ProOrg = getStrKey4ProOrg(proOrg);	
			hmap4ProOrg.put( strKey4ProOrg, proOrg );				
		}
		index = hmap4ProOrg.size();
		for( AProOrg proOrg:_arr4ProOrg_2)
		{			
			strKey4ProOrg = getStrKey4ProOrg(proOrg);	
			hmap4ProOrg.put( strKey4ProOrg, proOrg );				
		}			
		// 先放到一起，过滤掉完全一样的    end
		
		AProOrg proOrg_i = null;
		AProOrg proOrg_k = null;
		int relation ;
		
		List<AProOrg> arr4ProOrg4Union = null;
		int i=0,k=0;
		boolean blRemove_i = false;
		boolean blRemove_k = false;
		boolean blReInit = false;
		boolean blGoon = true;
		for(AProOrg aProOrg:hmap4ProOrg.values())
			arr4AProOrg.add(aProOrg);
		
		while( blGoon == true )
		{
			blGoon = false;
			blReInit = false;
			blRemove_i = false;
			blRemove_k = false;
			
			for(i=index; i<arr4AProOrg.size(); i=i+1 )
			{
				proOrg_i = arr4AProOrg.get( i ) ;	
				
					
				for( k=0; k<i; k=k+1 )
				{
					System.out.println("----------"+k);
					proOrg_k = arr4AProOrg.get( k );
					
					relation = UtilProOrg.relationOf_pathCode( proOrg_i, proOrg_k );
					if( relation == RELATION_FIRST2SECOND_COVERING )
					{
						blRemove_k = true;
						blGoon = true;
						break;
					}
					else if( relation == RELATION_FIRST2SECOND_EQUAL )
					{
						blRemove_k = true;
						blGoon = true;
						break;							
					}						
					else if( relation == RELATION_FIRST2SECOND_COVERED )
					{
						blRemove_i = true; 
						blGoon = true;
						break;							
					}
					else if( relation == RELATION_FIRST2SECOND_INTERSECT_1 )
					{
						arr4ProOrg4Union = getUnion4ProOrg4IntersectedProOrgs(proOrg_i,proOrg_k);
						blReInit = true;
						blGoon = true;
						break;								
					}
					else if( relation == RELATION_FIRST2SECOND_INTERSECT_2 )
					{
						arr4ProOrg4Union = getUnion4ProOrg4IntersectedProOrgs(proOrg_k,proOrg_i);
						blReInit = true;
						blGoon = true;
						break;								
					}
					else if( relation == RELATION_FIRST2SECOND_IRRELATED )
					{
						// good, do nothing
					}																		
				}
				
				if( blRemove_i == true )
				{
					arr4AProOrg.remove(i );
				}
				else if( blRemove_k == true )
				{
					arr4AProOrg.remove( k );
				}
				else if( blReInit == true )
				{
					hmap4ProOrg = new HashMap<String,AProOrg>();
					if( arr4ProOrg4Union != null )
					{
						for(int t=0; t<arr4ProOrg4Union.size(); t=t+1 )
						{
							AProOrg proOrg = arr4ProOrg4Union.get( t ) ;
							strKey4ProOrg = getStrKey4ProOrg(proOrg);	
							hmap4ProOrg.put( strKey4ProOrg, proOrg );
						}
					}
					for(int t=0; t<arr4AProOrg.size(); t=t+1 )
					{
						if((t != i) && (t != k) )
						{
							AProOrg proOrg = arr4AProOrg.get( t ) ;
							strKey4ProOrg = getStrKey4ProOrg(proOrg);	
							hmap4ProOrg.put( strKey4ProOrg, proOrg );								
						}
					}
					arr4AProOrg=new ArrayList<AProOrg>();
					for(AProOrg aProOrg:hmap4ProOrg.values())
						arr4AProOrg.add(aProOrg);	
				}
				
			}					
		}
		
		return arr4AProOrg;
	}
	
	public static int relationOf_pathCode( AProOrg _aProOrg_1, AProOrg _aProOrg_2 )
	{			
		if( _aProOrg_1 == null || _aProOrg_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _aProOrg_1.getProduct() == null || _aProOrg_1.getOrganization() == null || 
				_aProOrg_2.getProduct() == null || _aProOrg_2.getOrganization() == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}	
		
		Product product_1 = _aProOrg_1.getProduct();
		Product product_2 = _aProOrg_2.getProduct();
		Organization organization_1 = _aProOrg_1.getOrganization();
		Organization organization_2 = _aProOrg_2.getOrganization();
		
		int relation_p1_p2 = UtilProduct.relationOf_pathCode( product_1, product_2 );
		int relation_o1_o2 = UtilOrganization.relationOf_pathCode( organization_1, organization_2 );

		if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1>P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1>P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1>P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_1;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1>P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1=P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1=P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1=P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1=P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}													
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
		{
			if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERING )
			{
				// P1<P2 O1>O2
				return UtilProOrg.RELATION_FIRST2SECOND_INTERSECT_2;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_EQUAL )
			{
				// P1<P2 O1=O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_o1_o2 == UtilProOrg.RELATION_FIRST2SECOND_COVERED )
			{
				// P1<P2 O1<O2
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
			else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
			{
				// P1<P2 O1nO2
				return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
			}									
		}
		else if( relation_p1_p2 == UtilProOrg.RELATION_FIRST2SECOND_IRRELATED )
		{
			// P1nP2
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;				
		}	
		
		return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		
		
		
	}
	
	private static String getStrKey4ProOrg( AProOrg _proOrg)
	{
		if( _proOrg == null )
		{
			return "_-1_-1";
		}
		String strKey4ProOrg = "";
		if( _proOrg.getProduct() != null )
		{
			strKey4ProOrg = strKey4ProOrg + "_" +  _proOrg.getProduct().getId();	
		}
		else
		{
			strKey4ProOrg = strKey4ProOrg + "_" +  "-1";
		}
		if( _proOrg.getOrganization() != null )
		{
			strKey4ProOrg = strKey4ProOrg + "_" +  _proOrg.getOrganization().getId();	
		}
		else
		{
			strKey4ProOrg = strKey4ProOrg + "_" +  "-1";
		}	
		return strKey4ProOrg;	
		
	}
	// P1>P2 O1<O2 
	// 求出来的是一组互不相交的ProOrg
	private static List<AProOrg> getUnion4ProOrg4IntersectedProOrgs( AProOrg _proOrg_1,AProOrg _proOrg_2 )
	{
		List<AProOrg> arr4Rst = new ArrayList<AProOrg>();
		
		Product product_1= _proOrg_1.getProduct();
		Product product_2= _proOrg_2.getProduct();
		Organization organization_1 = _proOrg_1.getOrganization();
		Organization organization_2 = _proOrg_2.getOrganization();
		
		AProOrg rstProOrg = null;
		
		// (P2,O1)
		rstProOrg = new AProOrg();
		rstProOrg.setProduct( product_2);
		rstProOrg.setOrganization( organization_1);
		arr4Rst.add( rstProOrg );

		ProOrgDM dm = new ProOrgDM();
		arr4Rst.addAll(dm.buildProductIntersectedProOrgs(product_2.getId(), product_1.getId(), organization_1));
		arr4Rst.addAll(dm.buildOrgIntersectedProOrgs(organization_1.getId(), organization_2.getId(), product_2));
		// (P?,O1)
//		Product parentProduct = null;
//		Product pathProduct = null;
//		Set<Product> arr4SiblingProduct = null;	

//		// product_2不会为空
//		pathProduct = product_2;
//		while( pathProduct.getId() != product_1.getId())
//		{
//			parentProduct = pathProduct.getParentProduct();
//				arr4SiblingProduct = parentProduct.getSubProducts();
//				for(Product siblingProduct:arr4SiblingProduct )
//				{
//					if( siblingProduct.getId() != pathProduct.getId() )
//					{
//					rstProOrg = new AProOrg();
//					rstProOrg.setProduct(siblingProduct);
//					rstProOrg.setOrganization(organization_1);
//					arr4Rst.add( rstProOrg ); 
//					}	
//				}
//
//			pathProduct = pathProduct.getParentProduct();
//		}
//		
//		// (P2,O?)
//		Organization parentOrganization = null;
//		Organization pathOrganization = null;
//		Set<Organization> arr4SiblingOrganization = null;
//		
//		// organization_1 不会为空
//		pathOrganization = organization_1;
//		while( pathOrganization.getId() != organization_2.getId() )
//		{
//			parentOrganization = pathOrganization.getParentOrganization();
//			arr4SiblingOrganization = parentOrganization.getSubOrganizations();
//			for( Organization siblingOrganization:arr4SiblingOrganization)
//			{
//
//				if( siblingOrganization.getId() != pathOrganization.getId() )
//				{
//					rstProOrg = new AProOrg();
//					rstProOrg.setProduct(product_2);
//					rstProOrg.setOrganization(siblingOrganization);
//					arr4Rst.add( rstProOrg ); 						
//				}
//			}
//			
//			pathOrganization = pathOrganization.getParentOrganization();
//		} 
		
		return arr4Rst;
	}	
	
	
	
	public static String getProOrgIds(Product _product,Organization _organization)
	{
		List<Product> detailProducts = UtilProduct.getDetailProducts(  _product);
		List<Organization> detailOrganization = UtilOrganization.getDetailOrganizations( _organization );
		
		StringBuffer proorgids = new StringBuffer();
		proorgids.append( "((-1,-1)");
		for(Product product:detailProducts)
		{
			for(Organization organization:detailOrganization)
			{
				proorgids.append( " , (" + product.getId().longValue() + ","
				+ organization.getId().longValue() + ")");			
			}	
		}	
		proorgids.append( ")" );
		return proorgids.toString();
	}
	/**
	 * 以 (101,202) 形式返回ProOrg的联合ID
	 */ 
	public static String getProOrgId( BProduct _product, BOrganization _organization )
	{
		String rstStr = "( ";
		if( _product != null )
		{
			rstStr = rstStr + _product.getId();	
		}
		else
		{
			rstStr = rstStr + "-1";
		}
		rstStr = rstStr + ",";
		if( _organization != null )
		{
			rstStr = rstStr + _organization.getId();	
		}
		else
		{
			rstStr = rstStr + "-1";
		}			
		rstStr = rstStr + " )";
		
		return rstStr;
	}
	
	/**
	 * 比较_abUiRowDataProOrg 和_abProOrg的范围大小
	 * @param _abUiRowDataProOrg
	 * @param _abProOrg
	 * @return
	 */
	public static boolean relationOf(ABUiRowDataProOrg _abUiRowDataProOrg,ABProOrg _abProOrg)
	{
		List<String> detailProOrgIds = _abUiRowDataProOrg.getDetailProOrgIds();
		String proorgIds = "( "+_abProOrg.getProduct().getId()+","+_abProOrg.getOrganization().getId()+" )";
		if(detailProOrgIds.contains(proorgIds))
		{
			return true;
		}
		return false;
		
//		int rel_pro = -1;
//		int rel_proChar = -1;
//		int rel_org = -1;
//		int rel_orgChar = -1;
//		
//		boolean rel_pro_ok = false;
//		boolean rel_proChar_ok = false;
//		boolean rel_org_ok = false;
//		boolean rel_orgChar_ok = false;
//		
//		BProduct bProduct_source =_abUiRowDataProOrg.getProduct();
//		BProductCharacter bProductCharacter_source = _abUiRowDataProOrg.getProductCharacter();
//		BOrganization bOrganization_source = _abUiRowDataProOrg.getOrganization();
//		BOrganizationCharacter bOrganizationCharacter_source = _abUiRowDataProOrg.getOrganizationCharacter();
//		
//		//判断特列，当产品，产品特征，组织，组织特征都为null时，需要判断
////		if(bProduct_source==null && bProductCharacter_source==null && bOrganization_source==null &&bOrganizationCharacter_source)
////		{
////			
////		}
//		
//		//判断关系,主要判断步骤为
//		//1、先判断产品，产品特征，组织，组织特征各自之间的关系。
//
//		rel_pro = UtilProduct.relationOf(bProduct_source, _abProOrg.getProduct());
//		if(_abProOrg.getProduct()!=null && _abProOrg.getProduct().getProductProCharacters()!=null)
//		{
//			for(BProductProCharacter bProductProChar:_abProOrg.getProduct().getProductProCharacters())
//			{			
//				if(ProductCharacterUtil.isAncestorOf(bProductCharacter_source, bProductProChar.getProductCharacter()))
//				{
//					rel_proChar = RELATION_FIRST2SECOND_COVERING;
//				}
//			}
//		}
//		rel_org = UtilOrganization.relationOf(bOrganization_source, _abProOrg.getOrganization());
//		if(_abProOrg.getOrganization()!=null && _abProOrg.getOrganization().getOrganizationOrgCharacters()!=null)
//		{
//			for(BOrganizationOrgCharacter bOrgChar:_abProOrg.getOrganization().getOrganizationOrgCharacters())
//			{
//				if(OrganizationCharacterUtil.isAncestorOf(bOrganizationCharacter_source, bOrgChar.getOrganizationCharacter()))
//				{
//					rel_orgChar = RELATION_FIRST2SECOND_COVERING;
//				}
//			}
//		}
//		
//		//2、根据用户界面的选择，来判断各自的包含逻辑
//		if(bProduct_source != null)//勾选了显示产品
//		{
//			if(rel_pro == RELATION_FIRST2SECOND_COVERING || rel_pro == RELATION_FIRST2SECOND_EQUAL)//包含产品
//				rel_pro_ok = true;
//		}
//		else//不选,我们也认为产品是ok的
//		{
//			rel_pro_ok = true;
//		}
//		if(bProductCharacter_source !=null)//选择了产品特征
//		{
//			if(rel_proChar == RELATION_FIRST2SECOND_COVERING)
//				rel_proChar_ok = true;
//		}
//		else//同理，不选择产品特征，我们也认为是ok的
//		{
//			rel_proChar_ok = true;
//		}
//		if(bOrganization_source != null)//选择了组织
//		{
//			if(rel_org== RELATION_FIRST2SECOND_COVERING || rel_org == RELATION_FIRST2SECOND_EQUAL)//包含组织
//				rel_org_ok = true;
//		}
//		else//同理,没有选组织,我们认为是ok的
//		{
//			rel_org_ok = true;
//		}
//		if(bOrganizationCharacter_source != null)//选择了组织特征
//		{
//			if(rel_orgChar == RELATION_FIRST2SECOND_COVERING)
//				rel_orgChar_ok = true;
//		}
//		else//同理
//		{
//			rel_orgChar_ok = true;
//		}
//		
//		
//		if(rel_pro_ok && rel_proChar_ok && rel_org_ok && rel_orgChar_ok)
//			return true;
//		else
//			return false;
	}
	
	/**
	 * 比较_abUiRowDataProOrg 和_abProOrg的范围大小
	 * @param _abUiRowDataProOrg
	 * @param _abProOrg
	 * @return
	 */
	public static boolean relationOf(ABUiRowDataProOrg _abUiRowDataProOrgA,ABUiRowDataProOrg _abUiRowDataProOrgB)
	{
		List<String> detailProOrgIdsA = _abUiRowDataProOrgA.getDetailProOrgIds();
		List<String> detailProOrgIdsB = _abUiRowDataProOrgB.getDetailProOrgIds();
		for(String proorgIds :detailProOrgIdsB)
		{
			if(!detailProOrgIdsA.contains(proorgIds))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 比较_abUiRowDataProOrg 和_abProOrg的范围大小
	 * @param _abUiRowDataProOrg
	 * @param _abProOrg
	 * @return
	 */
	public static boolean relationOf(ABUiRowDataProOrg _abUiRowDataProOrgA,ABUiRowData _abUiRowDataProOrgB)
	{
		List<String> detailProOrgIdsA = _abUiRowDataProOrgA.getDetailProOrgIds();
		List<String> detailProOrgIdsB = _abUiRowDataProOrgB.getDetailProOrgIds();
		for(String proorgIds :detailProOrgIdsB)
		{
			if(!detailProOrgIdsA.contains(proorgIds))
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static List<AProOrg> getUnion4arrProOrgByDetail(List<AProOrg> _arr4ProOrg_1,List<AProOrg> _arr4ProOrg_2 )
	{
		System.out.println("--------------开始合并");
		List<AProOrg> list_res = new ArrayList<AProOrg>();
		HashMap<String,AProOrg> hm_1 = new HashMap<String, AProOrg>();
		HashMap<String,AProOrg> hm_2 = new HashMap<String, AProOrg>();
		System.out.println("--------------将已有的放进hm");
		for(AProOrg aproorg:_arr4ProOrg_1)
		{
			hm_1.put( aproorg.getProduct().getId()+"-"+aproorg.getOrganization().getId(), aproorg );
		}
		System.out.println("--------------将新来的放进hm");
		for(AProOrg aproorg:_arr4ProOrg_2)
		{
			hm_2.put( aproorg.getProduct().getId()+"-"+aproorg.getOrganization().getId(), aproorg );
		
		}
		System.out.println("--------------判断关系，不是覆盖就是新增");
		
		for(String key:hm_2.keySet())
		{
			if(!hm_1.containsKey( key ))
			{
				list_res.add( hm_2.get( key ) );
			}
		}
		list_res.addAll( hm_1.values() );
		return list_res;
	}
	
	/**
	 * 判断proPathCode，orgPathCode是否包含在hm里面
	 * @param hm
	 * @param proPathCode
	 * @param orgPathCode
	 * @return
	 */
	public static boolean checkRelation(HashMap<String,String> hm,String proPathCode,String orgPathCode)
	{
		if(hm.size()<1|| proPathCode == null || orgPathCode==null || proPathCode.equals("") || orgPathCode.equals(""))
		{
			return false;
		}
		for(String key:hm.keySet())
		{
			if(proPathCode.indexOf(key)>=0)//说明proPathCode是子节点
			{
				if(orgPathCode.indexOf(hm.get(key))>=0)//说明orgPathCode是子节点
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 判断proPathCode，orgPathCode是否包含在list里面
	 * @param list
	 * @param proPathCode
	 * @param orgPathCode
	 * @return
	 */
	public static boolean checkRelation(List<ABProOrgPathCode> list,String proPathCode,String orgPathCode)
	{
		if(list.size()<1|| proPathCode.equals("") || orgPathCode.equals(""))
		{
			return false;
		}
		for(ABProOrgPathCode pathcode:list)
		{
			if(proPathCode.indexOf(pathcode.getProPathCode())>=0)//说明proPathCode是子节点
			{
				if(orgPathCode.indexOf(pathcode.getOrgPathCode())>=0)//说明orgPathCode是子节点
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean checkRelation2(List<AProOrg> list,String proPathCode,String orgPathCode)
	{
		if(list.size()<1|| proPathCode.equals("") || orgPathCode.equals(""))
		{
			return false;
		}
		for(AProOrg proorg:list)
		{
			if(proPathCode.indexOf(proorg.getProduct().getPathCode())>=0)//说明proPathCode是子节点
			{
				if(orgPathCode.indexOf(proorg.getOrganization().getPathCode())>=0)//说明orgPathCode是子节点
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Collection<ABUiRowDataProOrg> buildExecuteData(BUiPopbScope uiPopScope)
	{		
		//获取明细proorg
		HashMap<String, ABProOrg> hm_proorg = new HashMap<String, ABProOrg>();
		HashMap<String, ABProOrg> temp_proorg = null;

		List<BProduct> list_pro = null;
		List<BOrganization> list_org = null;
		for(BUiPopbScopeProOrg buiproorg:uiPopScope.getUiPopbScopeProOrgs())
		{
			list_org = UtilOrganization.getDetailOrganizations(buiproorg.getOrganization());
			list_pro=UtilProduct.getDetailProducts(buiproorg.getProduct());
			temp_proorg = getDetailProOrgs2(list_pro,list_org,true);
			for(String key:temp_proorg.keySet())
			{
				if(!hm_proorg.containsKey(key))
				{
					hm_proorg.put(key, temp_proorg.get(key));
				}
			}
		}
		
		//得到明细的proorg
		String strKey4Product = "";
		String strKey4Organization = "";
		
		String strKey4uiRowDataProOrg="";
		
		BProduct projectProduct = null;
		BProductCharacter projectProductCharacter = null;			
		BOrganization projectOrganization = null;
		BOrganizationCharacter projectOrganizationCharacter = null;
		
		BProductCharacter detailProductCharacter = null;
		BOrganizationCharacter detailOrganizationCharacter=null;
		
		ABUiRowDataProOrg uiRowDataProOrg = null;
		HashMap<String,ABUiRowDataProOrg> hmap_uiRowDataProOrgs = new HashMap<String,ABUiRowDataProOrg> ();
		
		HashMap<String,BProduct> hmap_detailProduct_projectProduct = new HashMap<String,BProduct>(); // 明细产品到投影产品的映射 
		HashMap<String,BProductCharacter> hmap_detailProduct_projectProductCharacter = new HashMap<String,BProductCharacter>(); // 明细产品到投影产品的映射				
		HashMap<String,BOrganization> hmap_detailOrganization_projectOrganization = new HashMap<String,BOrganization>(); // 明细组织到投影组织的映射 
		HashMap<String,BOrganizationCharacter> hmap_detailOrganization_projectOrganizationCharacter = new HashMap<String,BOrganizationCharacter>(); // 明细组织到投影组织的映射
		
//		计算投影		begin
		for(ABProOrg detailProOrg:hm_proorg.values())
		{
			strKey4Product = "" + detailProOrg.getProduct().getId();
			strKey4Organization = "" + detailProOrg.getOrganization().getId();
			
			if(uiPopScope.getIsShowProduct() == BizConst.GLOBAL_YESNO_YES  )
			{
				projectProduct =  hmap_detailProduct_projectProduct.get(strKey4Product) ;
				if( projectProduct == null )
				{
					projectProduct = UtilProduct.getProjectProductByLayer( detailProOrg.getProduct(),uiPopScope.getProductLayer().getValue() );
					if( projectProduct == null )
					{
						// should not happen
						continue;
					}							
					hmap_detailProduct_projectProduct.put( strKey4Product, projectProduct ); 
				}
			}
			else
			{
				// 不需要显示product
				projectProduct = null;
			}
			
			if(uiPopScope.getIsShowProductCharacter() == BizConst.GLOBAL_YESNO_YES  )
			{
				projectProductCharacter =  hmap_detailProduct_projectProductCharacter.get( strKey4Product ) ;
				if( projectProductCharacter == null )
				{
					detailProductCharacter = UtilProductCharacter.getDetailProductCharacter( detailProOrg.getProduct(), uiPopScope.getProductCharacterType() );
					if( detailProductCharacter != null )
					{
						projectProductCharacter = UtilProductCharacter.getProjectProductCharacterByLayer( detailProductCharacter,uiPopScope.getProductCharacterLayer().getValue());
						if( projectProductCharacter == null )
						{
							// should not happen
							continue;	
						}
						hmap_detailProduct_projectProductCharacter.put( strKey4Product, projectProductCharacter );
					}
					else
					{
						// 明细产品没有被指定该类型的特征，投影到一个特殊的产品特征上
						projectProductCharacter = null;
					}
				}
			}
			else
			{
				// 不需要显示productCharacter
				projectProductCharacter = null;
			}					
			
			if( uiPopScope.getIsShowOrganization() ==  BizConst.GLOBAL_YESNO_YES )
			{
				projectOrganization =  hmap_detailOrganization_projectOrganization.get(strKey4Organization) ;
				if( projectOrganization == null )
				{
					projectOrganization = UtilOrganization.getProjectOrganizationByLayer( detailProOrg.getOrganization(),uiPopScope.getOrganizationLayer().getValue() );
					if( projectOrganization == null )
					{
						// should not happen
						continue;
					}							
					hmap_detailOrganization_projectOrganization.put( strKey4Organization, projectOrganization ); 
				}
			}
			else
			{
				// 不需要显示organization
				projectOrganization = null;
			}
			
			if( uiPopScope.getIsShowOrganizationCharacter() ==  BizConst.GLOBAL_YESNO_YES )
			{
				projectOrganizationCharacter =  hmap_detailOrganization_projectOrganizationCharacter.get( strKey4Organization ) ;
				if( projectOrganizationCharacter == null )
				{
					detailOrganizationCharacter = UtilOrganizationCharacter.getDetailOrganizationCharacter( detailProOrg.getOrganization(),uiPopScope.getOrganizationCharacterType() );
					if( detailOrganizationCharacter != null )
					{
						projectOrganizationCharacter = UtilOrganizationCharacter.getProjectOrganizationCharacterByLayer( detailOrganizationCharacter,uiPopScope.getOrganizationCharacterLayer().getValue());
						if( projectOrganizationCharacter == null )
						{
							// should not happen
							continue;	
						}
						hmap_detailOrganization_projectOrganizationCharacter.put( strKey4Organization, projectOrganizationCharacter );
					}
					else
					{
						// 明细组织没有被指定该类型的特征，投影到一个特殊的组织特征上
						projectOrganizationCharacter = null;
					}
				}
			}
			else
			{
				// 不需要显示organizationCharacter
				projectOrganizationCharacter = null;
			}
			//	计算投影		end							
			
			strKey4uiRowDataProOrg = UtilStrKey.getStrKey4PPcOOcB( projectProduct, projectProductCharacter, projectOrganization, projectOrganizationCharacter, null );
			uiRowDataProOrg =  hmap_uiRowDataProOrgs.get( strKey4uiRowDataProOrg ) ;
			if( uiRowDataProOrg == null )
			{
				uiRowDataProOrg = new ABUiRowDataProOrg();
				uiRowDataProOrg.setProduct( projectProduct);
				uiRowDataProOrg.setProductCharacter(projectProductCharacter);
				uiRowDataProOrg.setOrganization(projectOrganization);
				uiRowDataProOrg.setOrganizationCharacter( projectOrganizationCharacter);
				
			}
			
			uiRowDataProOrg.addDetailProOrgIds( UtilProOrg.getProOrgId( detailProOrg.getProduct(), detailProOrg.getOrganization() ) );
			
			hmap_uiRowDataProOrgs.put( strKey4uiRowDataProOrg , uiRowDataProOrg );	
		}
		
		return hmap_uiRowDataProOrgs.values();
	}
	
}

/************************************************************************
 * $RCSfile: UtilProOrg.java,v $  $Revision: 1.5 $  $Date: 2010/07/20 02:50:07 $
 * $Log: UtilProOrg.java,v $
 * Revision 1.5  2010/07/20 02:50:07  liuzhen
 * 2010.07.20 by liuzhen
 *
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.4  2010/07/19 14:45:21  liuzhen
 * 2010.07.19 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.3  2010/07/14 11:37:29  liuzhen
 * 2010.07.14 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2010/07/12 13:22:27  liuzhen
 * 2010.07.12 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/09 07:32:10  liuzhen
 * 2010.07.09 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 
 *
 ************************************************************************/