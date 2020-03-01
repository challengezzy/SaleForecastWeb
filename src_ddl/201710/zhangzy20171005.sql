--   for MR#: 系统功能权限 初始化脚本    begin

--库存导入   
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Import ZLife', '库存导入', 'zlife_import', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'zlife_import'));

--POPR IMPORT
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Import POPR', 'PO/PR导入', 'popr_import', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'popr_import'));

--MTD IMPORT   
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Import MTD', '当月交货导入', 'mtd_import', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'mtd_import'));
   
--POPR IN QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'POPR IN QUERY', 'POPR导入查询', 'popr_inquery', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'popr_inquery'));
   
--ZLIFE IN QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'ZLIFE IN QUERY', '库存导入查询', 'zlife_inquery', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'zlife_inquery'));
   
--MTD IN QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'MTD IN QUERY', '当前交货导入查询', 'mtd_inquery', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'mtd_inquery'));
   
--STOCK QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'ZLIFE QUERY', '库存查询', 'stock_query', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'stock_query'));
   
--STOCK QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'OVERSTOCK RISK', 'OverStock风险报表', 'overstock_risk', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'overstock_risk'));
   
--POPR QUERY
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'POPR QUERY', 'POPR查询', 'popr_query', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'popr_query'));
   
--过期库存风险汇总报表
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'OVERSTOCK RISK SUMMARY', 'OverStock风险汇总报表', 'overstock_risksummary', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'overstock_risksummary'));

--缺货风险评估报表,未实现
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'INVENTORY COVERMONTHS', '缺货风险评估报表', 'iventory_covermonths', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'iventory_covermonths'));

--缺货风险评估汇总报表,未实现
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'INVENTORY COVERMONTHS SUMMARY', '缺货风险评估汇总报表', 'iventory_covermonths_summary', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'iventory_covermonths_summary'));

--预测库存报表,未实现
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'FORECAST INVENTORY', '预测库存报表', 'forecast_inventory', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'forecast_inventory'));



--安全库存导入
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'SAFE STOCK IMPORT', '安全库存导入', 'safestock_import', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'safestock_import'));
   
--安全库存查询
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'SAFE STOCK QUERY', '安全库存查询', 'safestock_query', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'safestock_query'));

--库存风险报表
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'INVENTORY RISK', '库存风险报表', 'inventory_risk_report', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'inventory_risk_report'));
   
--库存风险报表-汇总
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'INVENTORY RISK SUMMARY', '库存风险报表汇总', 'inventory_risk_report_summary', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'inventory_risk_report_summary'));


--产品主数据
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'PRODUCT DATA', '产品主数据', 'pro_report', '产品'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'pro_report'));

--产品套装导出   
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Product KIT', '产品套装', 'pro_productkit', '产品'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'pro_productkit'));
   
--预测策略导出 
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Forecast Strategy Query', '预测策略查询', 'pro_fcproduct_query', '主数据管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'pro_fcproduct_query'));
   
--预测-库存报表
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'FORECAST INVENTORY', '预测库存报表', 'forecast_inventory_report', '库存管理'); 
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'forecast_inventory_report'));
  