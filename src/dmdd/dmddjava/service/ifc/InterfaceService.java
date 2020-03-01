/**********************************************************************
 *$RCSfile:InterFaceService.java,v $  $Revision: 1.0 $  $Date:2011-6-9 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationOrgCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductProCharacter;



/**
 * <li>Title: InterFaceService.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class InterfaceService
{
	Document   document = null;
	
	public InterfaceService()
	{
		try
		{
			SAXReader reader = new SAXReader(); 
			//reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			document = reader.read(this.getClass().getClassLoader().getResourceAsStream("interface.xml"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Object getInterFaceValues(String functionName)
	{
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			return getInterFaceValues4SAP4R3(functionName,null,"");
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
//	
//	public Object getInterFaceValues4Product(String functionName,String date)
//	{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put( "a", "11" );
//		map.put( "b", "22" );
//		map.put( "c", "33" );
//		map.put( "d", "44" );
//		map.put( "e", "55" );
//		map.put( "f", "66" );
//		map.put( "g", "77" );
//		
//		return map;
//	}
	/**
	 * 获取产品接口数据
	 * @param functionName
	 * @param date
	 * @return
	 */
	public Object getInterFaceValues4Product(String functionName,String date,String region)
	{
		System.out.println("start to load interface data for product...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "IM_ERSDA", date );
			impValues.put( "IM_BUKRS", region );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for product...");
			ConcurrentHashMap<Long, BProduct> hmproduct = ServerEnvironment.getInstance().getchProduct();
			ConcurrentHashMap<Long, BProductCharacter> hmproductcharacter= ServerEnvironment.getInstance().gethmproductcharacter();
			//过滤掉中间重复的
			
			//第一步,取出所有特征值
			HashMap<String,BProductCharacter> listProductCharacter = new HashMap<String,BProductCharacter>();
			List<String> ListStr = new ArrayList<String>();
			HashMap<String,BProductCharacter> hmproductcharacter4con = new HashMap<String,BProductCharacter>();
			for(BProductCharacter productcharacter:hmproductcharacter.values())
			{
				ListStr.add( productcharacter.getName()+productcharacter.getType() );
				hmproductcharacter4con.put(productcharacter.getName(),productcharacter);
			}
			
			List<HashMap<String, String>> lishm =result.get( "IT_LIST" );
			for(HashMap<String,String> hm:lishm)
			{
				String ZCUST = hm.get( "ZCUST" );//特征类型
				String ZCUSC = hm.get( "ZCUSC" );//特征名称
				String conStr_ = ZCUSC+ZCUST;
				if(ZCUST!=null && ZCUSC!=null && !ListStr.contains( conStr_) && !ZCUST.equals( "" ) && !ZCUSC.equals( "" ))//说明是新的
				{
					BProductCharacter bProductCharacter = new BProductCharacter();
					bProductCharacter.setParentProductCharacter( hmproductcharacter4con.get( ZCUST ) );
					bProductCharacter.setName( ZCUSC );
					bProductCharacter.setType( ZCUST );
					bProductCharacter.setIsCatalog( 0 );
					bProductCharacter.setComments( "从接口读取" );
					listProductCharacter.put( ZCUSC,bProductCharacter );
				}
			}

			//读取产品,并判断产品和特征关系是否更新
			HashMap<String,BProduct> listProduct = new HashMap<String,BProduct>();
			List<HashMap<String,String>> listProductProCharacter = new ArrayList<HashMap<String,String>>();
			HashMap<String,BProduct> hmproduct4con = new HashMap<String, BProduct>();
			HashMap<String,BProduct> hmproduct4getproductByname = new HashMap<String, BProduct>();
			HashMap<String,String> hmProductNoCharacter = new HashMap<String,String>();//标记缺失了某些产品特征的产品编码
			List<HashMap<String,String>> listhmProductNoCharacter = new ArrayList<HashMap<String,String>>();
			List<String> listexitsPro = new ArrayList<String>();
			
			for(BProduct product:hmproduct.values())
			{
				String conStr = product.getCode();
				hmproduct4con.put(conStr , product );
				hmproduct4getproductByname.put( product.getName(), product );
			}
			lishm =result.get( "IT_MARA" );
			for(HashMap<String,String> hm:lishm)
			{
				String MATNR = hm.get( "MATNR" );//编码
				String MAKTX = hm.get( "MAKTX" );//名称
				String ZATWTB1 = hm.get( "ZATWTB1" );//客户用途
				String ZATWTB2 = hm.get( "ZATWTB2" );//工艺分类
				String ZATWTB3 = hm.get( "ZATWTB3" );//产品体系
				String LVORM = hm.get( "LVORM" );//客户标记了要删除的，我们不处理
				//String BUKRS = hm.get("BUKRS");//公司编号
				
				String constr_ = MATNR;
				
				if(listexitsPro.contains( MATNR ))
				{
					continue;
				}
				else
				{
					listexitsPro.add(MATNR);
				}
				if(LVORM!=null && LVORM.equals( "X" ))//客户标记了要删除的，我们不处理
					continue;
//				if(!hmproductcharacter4con.containsKey( ZATWTB1 ))//属于其他半成品的不需要处理
//					continue;
//				if(!BUKRS.equals( region )) //按照工厂过滤
//				  continue;
				if(hmproduct4con.containsKey( constr_ ))//说明存在,则需要判断关联关系
				{
					List<String> listproductcharctersName = new ArrayList<String>();
					HashMap<String, String> hmProductProCharacter =null;
					if( hmproduct4con.get( constr_ ).getProductProCharacters() !=null)
					{ 
						for(BProductProCharacter productprocharacter: hmproduct4con.get( constr_ ).getProductProCharacters())
						{
							BProductCharacter productcharacter = productprocharacter.getProductCharacter();
							listproductcharctersName.add(  productcharacter.getName());
						}
					}
					if(!listproductcharctersName.contains( ZATWTB1 ))
					{
						if(hmproductcharacter4con.get( ZATWTB1 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB1 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
						}
					}
					if(!listproductcharctersName.contains( ZATWTB2 ))
					{
						if(hmproductcharacter4con.get( ZATWTB2 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB2 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
							
						}
					}
					if(!listproductcharctersName.contains( ZATWTB3 ))
					{
						if(hmproductcharacter4con.get( ZATWTB3 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB3 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
							
						}
					}
					String proname = hmproduct4con.get( constr_ ).getName();
					if( !proname.equals( MAKTX ))
					{
						hmProductNoCharacter = new HashMap<String,String>();
						hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
						hmProductNoCharacter.put( "REASON", "该产品名称和已有的产品名称不同。读取过来的名称为："+ MAKTX);
						listhmProductNoCharacter.add( hmProductNoCharacter);
					}
					
				}
				else//不存在，则需要更新产品，产品特征关系
				{
					BProduct product = new BProduct();
					product.setCode( MATNR );
					product.setName( MAKTX );
					product.setParentProduct( hmproduct4getproductByname.get( ZATWTB1 ) );
					product.setIsCatalog( 0 );
					product.setIsValid( 1 );
					product.setComments( "接口读取" );
					
					listProduct.put(MATNR, product);	
				}
				
				if(ZATWTB1==null ||ZATWTB1.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失客户用途特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
				if(ZATWTB2==null ||ZATWTB2.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失工艺分类特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
				if(ZATWTB3==null ||ZATWTB3.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失产品体系特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
			}
			HashMap<String,Object> rst = new HashMap<String, Object>();
			rst.put( "PRODUCT", listProduct.values() );
			rst.put( "PRODUCTCHARACTER", listProductCharacter.values() );
			rst.put( "PRODUCTPROCHARACTER", listProductProCharacter );
			rst.put( "NOCHARACTER", listhmProductNoCharacter );
			return rst;
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	/**
	 * 获取半成品接口数据
	 * @param functionName
	 * @param date
	 * @param power 权限组
	 * @param list4allowproducts 允许的半成品类型,目前先不做这个过滤，仅保留了这个开口
	 * @return
	 */
	public Object getInterFaceValues4SemiFinishedProduct(String functionName,String date,String power)
	{
		System.out.println("start to load interface data for semi finished product...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "IM_ERSDA", date );
			impValues.put( "IM_BEGRU", power );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for semi finished product...");
			ConcurrentHashMap<Long, BProduct> hmproduct = ServerEnvironment.getInstance().getchProduct();
			ConcurrentHashMap<Long, BProductCharacter> hmproductcharacter= ServerEnvironment.getInstance().gethmproductcharacter();
			//过滤掉中间重复的
			
			//第一步,取出所有特征值
			HashMap<String,BProductCharacter> listProductCharacter = new HashMap<String,BProductCharacter>();
			List<String> ListStr = new ArrayList<String>();
			HashMap<String,BProductCharacter> hmproductcharacter4con = new HashMap<String,BProductCharacter>();
			for(BProductCharacter productcharacter:hmproductcharacter.values())
			{
				ListStr.add( productcharacter.getName()+productcharacter.getType() );
				hmproductcharacter4con.put(productcharacter.getName(),productcharacter);
			}
			
			List<HashMap<String, String>> lishm =result.get( "IT_LIST" );
			for(HashMap<String,String> hm:lishm)
			{
				String ZCUST = hm.get( "ZCUST" );//特征类型
				String ZCUSC = hm.get( "ZCUSC" );//特征名称
				String conStr_ = ZCUSC+ZCUST;
				if(ZCUST!=null && ZCUSC!=null && !ListStr.contains( conStr_) && !ZCUST.equals( "" ) && !ZCUSC.equals( "" ))//说明是新的
				{
					BProductCharacter bProductCharacter = new BProductCharacter();
					bProductCharacter.setParentProductCharacter( hmproductcharacter4con.get( ZCUST ) );
					bProductCharacter.setName( ZCUSC );
					bProductCharacter.setType( ZCUST );
					bProductCharacter.setIsCatalog( 0 );
					bProductCharacter.setComments( "从接口读取" );
					listProductCharacter.put( ZCUSC,bProductCharacter );
				}
			}

			//读取产品,并判断产品和特征关系是否更新
			HashMap<String,BProduct> listProduct = new HashMap<String,BProduct>();
			List<HashMap<String,String>> listProductProCharacter = new ArrayList<HashMap<String,String>>();
			HashMap<String,BProduct> hmproduct4con = new HashMap<String, BProduct>();
			HashMap<String,BProduct> hmproduct4getproductByname = new HashMap<String, BProduct>();
			HashMap<String,String> hmProductNoCharacter = new HashMap<String,String>();//标记缺失了某些产品特征的产品编码
			List<HashMap<String,String>> listhmProductNoCharacter = new ArrayList<HashMap<String,String>>();
			List<String> listexitsPro = new ArrayList<String>();
			
			for(BProduct product:hmproduct.values())
			{
				String conStr = product.getCode();
				hmproduct4con.put(conStr , product );
				hmproduct4getproductByname.put( product.getName(), product );
			}
			lishm =result.get( "IT_MARA" );
			for(HashMap<String,String> hm:lishm)
			{
				String MATNR = hm.get( "MATNR" );//编码
				String MAKTX = hm.get( "MAKTX" );//名称
				String ZATWTB1 = hm.get( "ZATWTB1" );//客户用途
				String ZATWTB2 = hm.get( "ZATWTB2" );//工艺分类
				String ZATWTB3 = hm.get( "ZATWTB3" );//产品体系
				String LVORM = hm.get( "LVORM" );//客户标记了要删除的，我们不处理
//				String BUKRS = hm.get("BUKRS");//公司编号
				
				String constr_ = MATNR;
				if(listexitsPro.contains( MATNR ))
				{
					continue;
				}
				else
				{
					listexitsPro.add(MATNR);
				}
				
				if(LVORM!=null && LVORM.equals( "X" ))//客户标记了要删除的，我们不处理
					continue;
//				if(!list4allowproducts.contains( ZATWTB1 ))
//					continue;
				if(hmproduct4con.containsKey( constr_ ))//说明存在,则需要判断关联关系
				{
					List<String> listproductcharctersName = new ArrayList<String>();
					HashMap<String, String> hmProductProCharacter =null;
					if( hmproduct4con.get( constr_ ).getProductProCharacters() !=null)
					{ 
						for(BProductProCharacter productprocharacter: hmproduct4con.get( constr_ ).getProductProCharacters())
						{
							BProductCharacter productcharacter = productprocharacter.getProductCharacter();
							listproductcharctersName.add(  productcharacter.getName());
						}
					}
					if(!listproductcharctersName.contains( ZATWTB1 ))
					{
						if(hmproductcharacter4con.get( ZATWTB1 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB1 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
						}
					}
					if(!listproductcharctersName.contains( ZATWTB2 ))
					{
						if(hmproductcharacter4con.get( ZATWTB2 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB2 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
							
						}
					}
					if(!listproductcharctersName.contains( ZATWTB3 ))
					{
						if(hmproductcharacter4con.get( ZATWTB3 )!=null)
						{
							hmProductProCharacter = new HashMap<String, String>();							
							hmProductProCharacter.put("PRODUCTCODE", MATNR);
							hmProductProCharacter.put( "PROCDUCTCHARACTERCODE", hmproductcharacter4con.get( ZATWTB3 ).getCode()  );
							listProductProCharacter.add(hmProductProCharacter);
							
						}
					}	
					String proname = hmproduct4con.get( constr_ ).getName();
					if( !proname.equals( MAKTX ))
					{
						hmProductNoCharacter = new HashMap<String,String>();
						hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
						hmProductNoCharacter.put( "REASON", "该产品名称和已有的产品名称不同。读取过来的名称为："+ MAKTX);
						listhmProductNoCharacter.add( hmProductNoCharacter);
					}
				}
				else//不存在，则需要更新产品，产品特征关系
				{
					BProduct product = new BProduct();
					product.setCode( MATNR );
					product.setName( MAKTX );
					product.setParentProduct( hmproduct4getproductByname.get( ZATWTB1 ) );
					product.setIsCatalog( 0 );
					product.setIsValid( 1 );
					product.setComments( "接口读取" );
					
					listProduct.put(MATNR, product);	
				}
				if(ZATWTB1==null ||ZATWTB1.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失客户用途特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
				if(ZATWTB2==null ||ZATWTB2.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失工艺分类特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
				if(ZATWTB3==null ||ZATWTB3.equals( "" ))
				{
					hmProductNoCharacter = new HashMap<String,String>();
					hmProductNoCharacter.put( "PRODUCTCODE", MATNR );
					hmProductNoCharacter.put( "REASON", "缺失产品体系特征" );
					listhmProductNoCharacter.add( hmProductNoCharacter);
				}
				
			}
			HashMap<String,Object> rst = new HashMap<String, Object>();
			rst.put( "PRODUCT", listProduct.values() );
			rst.put( "PRODUCTCHARACTER", listProductCharacter.values() );
			rst.put( "PRODUCTPROCHARACTER", listProductProCharacter );
			rst.put( "NOCHARACTER", listhmProductNoCharacter );
			return rst;
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	/**
	 * 从接口读取组织数据
	 * @param functionName
	 * @param date
	 * @return
	 */
	public Object getInterFaceValues4Organization(String functionName,String date,String region)
	{

		System.out.println("start to load interface data for organization...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "IM_ERDAT", date );
			impValues.put( "IM_BUKRS", region );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for organization...");
			ConcurrentHashMap<Long, BOrganization> hmorganization = ServerEnvironment.getInstance().getChOrganization();
			ConcurrentHashMap<Long, BOrganizationCharacter> hmorganizationcharacter= ServerEnvironment.getInstance().getCHOrganizationCharacter();
			//过滤掉中间重复的
			
			//第一步,取出所有特征值
			HashMap<String,BOrganizationCharacter> listOrganizationCharacter = new HashMap<String,BOrganizationCharacter>();
			List<String> ListStr = new ArrayList<String>();
			HashMap<String,BOrganizationCharacter> hmrganizationCharacter = new HashMap<String,BOrganizationCharacter>();
			for(BOrganizationCharacter organizationCharacter:hmorganizationcharacter.values())
			{
				ListStr.add( organizationCharacter.getName()+organizationCharacter.getType() );
				hmrganizationCharacter.put(organizationCharacter.getName(),organizationCharacter);
			}
			
			List<HashMap<String, String>> lishm =result.get( "IT_LIST" );
			for(HashMap<String,String> hm:lishm)
			{
				String ZCUST = hm.get( "ZCUST" );//特征类型
				String ZCUSC = hm.get( "ZCUSC" );//特征名称
				String conStr_ = ZCUSC+ZCUST;
				if(ZCUST!=null && ZCUSC!=null && !ListStr.contains( conStr_) && !ZCUST.equals( "" ) && !ZCUSC.equals( "" ))//说明是新的
				{
					BOrganizationCharacter bOrganizationCharacter = new BOrganizationCharacter();
					bOrganizationCharacter.setParentOrganizationCharacter(  hmrganizationCharacter.get( ZCUST ) );
					bOrganizationCharacter.setName( ZCUSC );
					bOrganizationCharacter.setType( ZCUST );
					bOrganizationCharacter.setIsCatalog( 0 );
					bOrganizationCharacter.setComments( "从接口读取" );
					listOrganizationCharacter.put( ZCUSC,bOrganizationCharacter );
				}
			}
			System.out.println("get all new  character complite...");
			//读取产品,并判断产品和特征关系是否更新
			HashMap<String,BOrganization> listOrganization = new HashMap<String,BOrganization>();
			List<HashMap<String,String>> listOrganizationProCharacter = new ArrayList<HashMap<String,String>>();
			HashMap<String,BOrganization> hmorganization4con = new HashMap<String, BOrganization>();
			HashMap<String,BOrganization> hmorganization4organizationByname = new HashMap<String, BOrganization>();
			HashMap<String,String> hmOrgNoCharacter = new HashMap<String,String>();//标记缺失了某些组织特征的产品编码
			List<HashMap<String,String>> listhmOrgNoCharacter = new ArrayList<HashMap<String,String>>();
			List<String> listexitsorg = new ArrayList<String>();//用来标记已经读取过的组织数据，过滤掉重复的
			
			for(BOrganization organization:hmorganization.values())
			{
				String conStr = organization.getCode();
				hmorganization4con.put(conStr , organization );
				hmorganization4organizationByname.put( organization.getName(), organization );
			}
			lishm =result.get( "IT_KNA1" );
			for(HashMap<String,String> hm:lishm)
			{
				String KUNNR = hm.get( "KUNNR" );//编码
				String NAME1 = hm.get( "NAME1" );//名称
				String ZATWTB1 = hm.get( "ZCUSC1" );//行业分类
				String ZATWTB2 = hm.get( "ZCUSC2" );//销售人员
				String ZATWTB3 = hm.get( "ZCUSC3" );//办事处
				String constr_ = KUNNR;

				if(listexitsorg.contains( KUNNR ))
				{
					continue;
				}
				else
				{
					listexitsorg.add(KUNNR);
				}
				
				if(hmorganization4con.containsKey( constr_ ))//说明存在,则需要判断关联关系
				{
					List<String> listorganzationcharctersName = new ArrayList<String>();
					HashMap<String, String> hmOrganiztionjProCharacter =null;
					if(hmorganization4con.get( constr_ ).getOrganizationOrgCharacters()!=null)
					{
						for(BOrganizationOrgCharacter organizationOrgCharacter: hmorganization4con.get( constr_ ).getOrganizationOrgCharacters())
						{
							BOrganizationCharacter organizationCharacter = organizationOrgCharacter.getOrganizationCharacter();
							listorganzationcharctersName.add(  organizationCharacter.getName());
						}
					}
					if(!listorganzationcharctersName.contains( ZATWTB1 ))
					{
						if(hmrganizationCharacter.get( ZATWTB1 )!=null)
						{
							hmOrganiztionjProCharacter = new HashMap<String, String>();							
							hmOrganiztionjProCharacter.put("ORGANIATIONCODE", KUNNR);
							hmOrganiztionjProCharacter.put( "ORGANIATIONCODECHARACTERCODE", hmrganizationCharacter.get( ZATWTB1 ).getCode()  );
							listOrganizationProCharacter.add(hmOrganiztionjProCharacter);
						}
					}
					if(!listorganzationcharctersName.contains( ZATWTB2 ))
					{
						if(hmrganizationCharacter.get( ZATWTB2 )!=null)
						{
							hmOrganiztionjProCharacter = new HashMap<String, String>();							
							hmOrganiztionjProCharacter.put("ORGANIATIONCODE", KUNNR);
							hmOrganiztionjProCharacter.put( "ORGANIATIONCODECHARACTERCODE", hmrganizationCharacter.get( ZATWTB2 ).getCode()  );
							listOrganizationProCharacter.add(hmOrganiztionjProCharacter);
							
						}
					}
					if(!listorganzationcharctersName.contains( ZATWTB3 ))
					{		
						if(hmrganizationCharacter.get( ZATWTB3 )!=null)
						{
							//第一步 分析下如果办事处的值是个高层的特征值，例如“IU上海总部”，
							//则需要分析和组织名称相同的办事处的特征值是否已经建立了关联关系,且和组织名称相同的办事处特征值是否是“IU上海总部”的子特征
							BOrganizationCharacter bOrganizationCharacter = hmrganizationCharacter.get( ZATWTB3 );
							if(bOrganizationCharacter.getIsCatalog() == BizConst.GLOBAL_YESNO_YES)//一般来说只有办事处才可能取到高层的值
							{
								//找到和这个组织同名的特征值,对于办事处来说理论上是有这个值的。
								BOrganizationCharacter subbOrganizationCharacter = hmrganizationCharacter.get( NAME1 );
								if(subbOrganizationCharacter==null)//如果不存在，则创建
								{
									BOrganizationCharacter newOrganizationCharacter = new BOrganizationCharacter();
									newOrganizationCharacter.setParentOrganizationCharacter(  hmrganizationCharacter.get( ZATWTB3 ) );
									newOrganizationCharacter.setName( NAME1 );
									newOrganizationCharacter.setCode( KUNNR );
									newOrganizationCharacter.setType( bOrganizationCharacter.getType() );
									newOrganizationCharacter.setIsCatalog( 0 );
									newOrganizationCharacter.setComments( "从接口读取" );
									listOrganizationCharacter.put( NAME1,newOrganizationCharacter );
									
								}
								else//存在，则更新关系
								{
									if(subbOrganizationCharacter.getParentOrganizationCharacter().getName().equals( ZATWTB3 ))
									{
										if(!listorganzationcharctersName.contains( NAME1 ))
										{
											hmOrganiztionjProCharacter = new HashMap<String, String>();							
											hmOrganiztionjProCharacter.put("ORGANIATIONCODE", KUNNR);
											hmOrganiztionjProCharacter.put( "ORGANIATIONCODECHARACTERCODE", hmrganizationCharacter.get( NAME1 ).getCode()  );
											listOrganizationProCharacter.add(hmOrganiztionjProCharacter);
										}			
									}	
								}						
							}
							else
							{
								hmOrganiztionjProCharacter = new HashMap<String, String>();							
								hmOrganiztionjProCharacter.put("ORGANIATIONCODE", KUNNR);
								hmOrganiztionjProCharacter.put( "ORGANIATIONCODECHARACTERCODE", hmrganizationCharacter.get( ZATWTB3 ).getCode()  );
								listOrganizationProCharacter.add(hmOrganiztionjProCharacter);
							}
						}
					}		
					
					String proname = hmorganization4con.get( constr_ ).getName();
					if( !proname.equals( NAME1 ))
					{
						hmOrgNoCharacter = new HashMap<String,String>();
						hmOrgNoCharacter.put( "ORGCODE", KUNNR );
						hmOrgNoCharacter.put( "REASON", "该组织名称和已有的组织的名称不同，从接口读取过来的名称为："+ NAME1);
						listhmOrgNoCharacter.add( hmOrgNoCharacter);
					}
				}
				else//不存在，则需要更新产品，产品特征关系
				{
					BOrganization organization = new BOrganization();
					organization.setCode( KUNNR );
					organization.setName( NAME1 );
					organization.setParentOrganization(   hmorganization4organizationByname.get( ZATWTB3 ) );
					organization.setIsCatalog( 0 );
					organization.setIsValid( 1 );
					organization.setComments( "接口读取" );
					
					listOrganization.put( KUNNR,organization);	
					
					
					//要新建同名的组织特征
					if(hmrganizationCharacter.get( ZATWTB3 )!=null)
					{
						BOrganizationCharacter bOrganizationCharacter = hmrganizationCharacter.get( NAME1 );
						if(bOrganizationCharacter==null)//如果不存在，则创建
						{
							BOrganizationCharacter newOrganizationCharacter = new BOrganizationCharacter();
							newOrganizationCharacter.setParentOrganizationCharacter(  hmrganizationCharacter.get( ZATWTB3 ) );
							newOrganizationCharacter.setName( NAME1 );
							newOrganizationCharacter.setCode( KUNNR );
							newOrganizationCharacter.setType( hmrganizationCharacter.get( ZATWTB3 ).getType() );
							newOrganizationCharacter.setIsCatalog( 0 );
							newOrganizationCharacter.setComments( "从接口读取" );
							listOrganizationCharacter.put( NAME1,newOrganizationCharacter );
							
						}
						else//存在，则更新关系
						{
							if(bOrganizationCharacter.getParentOrganizationCharacter().getName().equals( ZATWTB3 ))
							{							
								HashMap<String, String>  hmOrganiztionjProCharacter = new HashMap<String, String>();							
								hmOrganiztionjProCharacter.put("ORGANIATIONCODE", KUNNR);
								hmOrganiztionjProCharacter.put( "ORGANIATIONCODECHARACTERCODE", hmrganizationCharacter.get( NAME1 ).getCode()  );
								listOrganizationProCharacter.add(hmOrganiztionjProCharacter);									
							}	
						}	
					}
				}
				//判断缺失情况
				if(ZATWTB1==null ||ZATWTB1.equals( "" ))
				{
					hmOrgNoCharacter = new HashMap<String,String>();
					hmOrgNoCharacter.put( "ORGCODE", KUNNR );
					hmOrgNoCharacter.put( "REASON", "缺失行业分类特征" );
					listhmOrgNoCharacter.add( hmOrgNoCharacter);
				}
				if(ZATWTB2==null ||ZATWTB2.equals( "" ))
				{
					hmOrgNoCharacter = new HashMap<String,String>();
					hmOrgNoCharacter.put( "ORGCODE", KUNNR );
					hmOrgNoCharacter.put( "REASON", "缺失销售人员特征" );
					listhmOrgNoCharacter.add( hmOrgNoCharacter);
				}
				if(ZATWTB3==null ||ZATWTB3.equals( "" ))
				{
					hmOrgNoCharacter = new HashMap<String,String>();
					hmOrgNoCharacter.put( "ORGCODE", KUNNR );
					hmOrgNoCharacter.put( "REASON", "缺失办事处特征" );
					listhmOrgNoCharacter.add( hmOrgNoCharacter);
				}
				
			}
			HashMap<String,Object> rst = new HashMap<String, Object>();
			rst.put( "ORGANIATION", listOrganization.values() );
			rst.put( "ORGANIATIONCHARACTER", listOrganizationCharacter.values() );
			rst.put( "ORGANIATIONORGCHARACTER", listOrganizationProCharacter );
			rst.put( "ORGANIATIONWARNING", listhmOrgNoCharacter );
			return rst;
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	public Object getInterFaceValues4PriceData(String functionName,String date)
	{
		System.out.println("start to load interface data for pricedata...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "IM_MONTH", date );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for pricedata...");
			
			List<HashMap<String,String>> listhm = result.get( "IT_LIST" );
			HashMap<String,HashMap<String, String>> hmpricedatas = new HashMap<String, HashMap<String,String>>();
			for(HashMap<String,String> hm:listhm)
			{
				String MATNR = hm.get( "MATNR" );//物料号
				String KUNNR = hm.get( "KUNNR" );//客户编号1
				String MONTH = hm.get( "MONTH" );//期间
				String PRICE = hm.get( "PRICE" );//价格
//				String KMEIN = hm.get( "KMEIN" );//条件单位,这里不用处理了。
				String conStr = MATNR+"||"+KUNNR;
				if(hmpricedatas.containsKey( conStr ))//已经记录了产品和组织，更新其价格数据
				{
					HashMap<String, String> pricedatas = hmpricedatas.get( conStr );
					if(pricedatas!=null)
					{
						pricedatas.put( MONTH, PRICE );//不论存不存在，直接赋值。	
					}
				}
				else
				{
					HashMap<String, String> pricedatas =new HashMap<String, String>();
					pricedatas.put( MONTH, PRICE );
					pricedatas.put("MATNR",MATNR);
					pricedatas.put("KUNNR",KUNNR);
					hmpricedatas.put( conStr, pricedatas );
				}

			}
			return hmpricedatas.values();
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	/**
	 * 获取销售重量接口数据。
	 * @param functionName
	 * @param date
	 * @return
	 */
	public Object getInterFaceValues4HistoryDataWeight(String functionName,String date_begin,String data_end,String region)
	{
		System.out.println("start to load interface data for historydata weight...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "I_DATEB", date_begin );
			impValues.put( "I_DATEE", data_end );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for historydata weight...");
			
			List<HashMap<String,String>> listhm = result.get( "T_LIST" );
			HashMap<String,HashMap<String, String>> hmpricedatas = new HashMap<String, HashMap<String,String>>();
			for(HashMap<String,String> hm:listhm)
			{
				String VKORG = hm.get( "VKORG" ); //公司代码
				String KUNNR = hm.get( "KUNNR" );//客户编号1
				String MATNR = hm.get( "MATNR" );//物料号
				String CALMONTH = hm.get( "CALMONTH" );//日期
				String NT_WT_KG = hm.get( "NT_WT_KG" );//重量
				if(!VKORG.equals( region ))
					continue;
				
				String conStr = MATNR+"||"+KUNNR;
				if(hmpricedatas.containsKey( conStr ))//已经记录了产品和组织，更新其价格数据
				{
					HashMap<String, String> pricedatas = hmpricedatas.get( conStr );
					if(pricedatas!=null)
					{
						if(pricedatas.containsKey( CALMONTH ))
						{
							String _NT_WT_KG = pricedatas.get( CALMONTH );
							BigDecimal newNum = new BigDecimal( NT_WT_KG );
							BigDecimal oldNum = new BigDecimal( _NT_WT_KG );
							BigDecimal total = newNum.add(oldNum);
							
							pricedatas.put( CALMONTH, total.toString() );
						}
						else
						{
							pricedatas.put( CALMONTH, NT_WT_KG );
						}
					}
				}
				else
				{
					HashMap<String, String> pricedatas =new HashMap<String, String>();
					pricedatas.put( CALMONTH, NT_WT_KG );
					pricedatas.put("MATNR",MATNR);
					pricedatas.put("KUNNR",KUNNR);
					hmpricedatas.put( conStr, pricedatas );
				}

			}
			return hmpricedatas.values();
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	/**
	 * 获取销售收入接口数据。
	 * @param functionName
	 * @param date
	 * @return
	 */
	public Object getInterFaceValues4HistoryDataPrice(String functionName,String date_begin,String data_end,String region)
	{
		System.out.println("start to load interface data for historydata price...");
		Element rootElm = document.getRootElement(); 
		Element in_config = rootElm.element( "interface" );
		if(in_config.element( "interface-name" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP )&&
				in_config.element( "interface-system" ).getText().equals( BizConst.INTERFACE_CONFIG_SAP_R3 )	)
		{
			HashMap<String,String> impValues = new HashMap<String, String>();
			impValues.put( "I_DATEB", date_begin );
			impValues.put( "I_DATEE", data_end );
			HashMap<String,List<HashMap<String,String>>> result =  getInterFaceValues4SAP4R3(functionName,impValues,"");
			System.out.println("start to deal interface data for historydata weight...");
			
			List<HashMap<String,String>> listhm = result.get( "T_LIST" );
			HashMap<String,HashMap<String, String>> hmpricedatas = new HashMap<String, HashMap<String,String>>();
			for(HashMap<String,String> hm:listhm)
			{
				String VKORG = hm.get( "VKORG" ); //公司代码
				String KUNNR = hm.get( "KUNNR" );//客户编号1
				String MATNR = hm.get( "MATNR" );//物料号
				String CALMONTH = hm.get( "CALMONTH" );//日期
				String SALES = hm.get( "SALES" );//金额
				if(!VKORG.equals( region ))
					continue;
				String conStr = MATNR+"||"+KUNNR;
				if(hmpricedatas.containsKey( conStr ))//已经记录了产品和组织，更新其价格数据
				{
					HashMap<String, String> pricedatas = hmpricedatas.get( conStr );
					if(pricedatas!=null)
					{
						if(pricedatas.containsKey( CALMONTH ))
						{
							String _SALES = pricedatas.get( CALMONTH );
							BigDecimal newNum = new BigDecimal( SALES );
							BigDecimal oldNum = new BigDecimal( _SALES );
							BigDecimal total = newNum.add( oldNum);
							pricedatas.put( CALMONTH, total.toString() );	
						}
						else
						{
							pricedatas.put( CALMONTH, SALES );	
						}
					}
				}
				else
				{
					HashMap<String, String> pricedatas =new HashMap<String, String>();
					pricedatas.put( CALMONTH, SALES );
					pricedatas.put("MATNR",MATNR);
					pricedatas.put("KUNNR",KUNNR);
					hmpricedatas.put( conStr, pricedatas );
				}

			}
			return hmpricedatas.values();
		}
//		else if() //others system
//		{
//			
//		}
		else
			return null;
	}
	
	/**
	 * 获取R3系统接口数据
	 * @param functionName 函数名称
	 * @param impValues 导入数据列表
	 * @param tableName 需要获取的表格名称，如果为空则取所有表格值
	 * @return
	 */
	public HashMap<String,List<HashMap<String,String>>> getInterFaceValues4SAP4R3(String functionName,HashMap<String,String> impValues,String tableName)
	{
		HashMap<String,List<HashMap<String,String>>> rst = new HashMap<String,List<HashMap<String,String>>>();
		HashMap<String,String> impHm = new HashMap<String, String>();
		String userid = "";
		String password = "";
		String sapClient = "";
		String language = "";
		String serverHost = "";
		String sysnumber = "";
		String _source="";
		
		HashMap<String,List<String>> hmtables = new HashMap<String,List<String>>();
		if(impValues==null)
		{
			impValues = new HashMap<String, String>();
		}

		try
		{		       
			Element rootElm = document.getRootElement();   
			//第一步,根据函数名获取函数配置
			List<Element> functions = rootElm.elements( "function" );
			for(Element el:functions)
			{
				if(el.attribute( "name" ).getText().equals(functionName ))
				{
					String source = el.attribute( "source" ).getText();
					_source=source;
					List<Element> confs = rootElm.elements( "config" );
					for(Element conf :confs)
					{
						if(conf.attribute( "name" ).getText().equals( source ))
						{
							userid = conf.element( "userid" ).getText();
							sapClient = conf.element( "sapClient" ).getText();
							password = conf.element( "password" ).getText();
							language = conf.element( "language" ).getText();
							serverHost = conf.element( "serverHost" ).getText();
							sysnumber = conf.element( "sysnumber" ).getText();
							
						}
					}
					
					List<Element> importParams = el.elements( "importParam" );
					for(Element el_importParam:importParams)
					{
						List<Element> params = el_importParam.elements( "param" );
						for(Element el_param:params)
						{
							if(impValues.containsKey( el_param.attribute( "name" ).getText() ))
							{
								impHm.put( el_param.attribute( "name" ).getText(), impValues.get( el_param.attribute( "name" ).getText() ) );
							}
							else
								impHm.put( el_param.attribute( "name" ).getText(), el_param.attribute( "value" ).getText() );
						}						
					}
					
					
					List<Element> tablesList = el.elements("table");
					for(Element el_table:tablesList)
					{
						List<String> fieldList =new ArrayList<String>();
						String tableName_ = el_table.attributeValue( "name" );
						if(tableName==null||tableName.equals( "" ))
						{
							List<Element> fields = el_table.elements("field");
							for(Element field:fields)
							{
								fieldList.add( field.attribute( "name" ).getText() );
							}
							hmtables.put( tableName_, fieldList );
						}
						else//没有指定tablename，则取所有table
						{
							if(tableName_.equals( tableName ))
							{
								List<Element> fields = el_table.elements("field");
								for(Element field:fields)
								{
									fieldList.add( field.attribute( "name" ).getText() );
								}
								hmtables.put( tableName_, fieldList );
							}
						}
					}
				}
			}
			
			//第二步，获取实际数据
			SapRfcConnecter connecter =null;
			if(SapRfcConnecterPool.getInstance().getSapRfcConnecter( _source )!=null)
			{
				connecter = SapRfcConnecterPool.getInstance().getSapRfcConnecter( _source );
			}
			else
			{
				connecter = new SapRfcConnecter(sapClient,userid,password,language,serverHost,sysnumber,_source);
				SapRfcConnecterPool.getInstance().addSapRfcConnecter( _source, connecter );
			}
			rst = connecter.getObjectsByFunction( functionName, impHm,hmtables );
//			int i = 0;
//			for(HashMap<String,String> hm:rst)
//			{
//				i++;
//				System.out.println("-------------"+i+"--------------");
//				for(String key:hm.keySet())
//					System.out.println("key:"+key+" value:"+hm.get(key));
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return rst;
	}
	
	public static void main(String[] args)
	{
		//new InterfaceService().getInterFaceValues4Product( "ZMM0003" ,"20110601","IT_LIST");
	}
	
}

/**********************************************************************
 *$RCSfile:InterFaceService.java,v $  $Revision: 1.0 $  $Date:2011-6-9 $
 *
 *$Log:InterFaceService.java,v $
 *********************************************************************/