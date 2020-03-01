--   for MR#: 系统功能权限 初始化脚本    begin

insert into SYSDICTIONARYITEM ( VERSION, CLASSNAME, ATTRIBUTENAME, VALUE, VALUEDESC)
values  ( 0, 'BIZDATA', 'TYPE', 37, 'BIZDATA_TYPE_AVGHIS'); 
insert into SYSDICTIONARYITEM ( VERSION, CLASSNAME, ATTRIBUTENAME, VALUE, VALUEDESC)
values  ( 0, 'BIZDATA', 'TYPE', 38, 'BIZDATA_TYPE_ADDFC'); 
insert into SYSDICTIONARYITEM ( VERSION, CLASSNAME, ATTRIBUTENAME, VALUE, VALUEDESC)
values  ( 0, 'BIZDATA', 'TYPE', 39, 'BIZDATA_TYPE_ADDHIS'); 

insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Average History', '历史平均', 'bizdata_avgHistory', '业务数据管理'); 
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Accumulate History', '历史累积', 'bizdata_accHistory', '业务数据管理'); 
insert into funpermission ( VERSION, TYPE, CODE, NAME, DESCRIPTION, COMMENTS) values ( 0, '0', 'Accumulate Forecast', '预测累积', 'bizdata_accForecast', '业务数据管理'); 


insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'bizdata_avgHistory'));
   
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'bizdata_accHistory'));
   
insert into OPERATORUSER_FUNPERMISSION
  ( VERSION, OPERATORUSERID, FUNPERMISSIONID)
values ( 0,
   (select ID from OPERATORUSER where LOGINNAME = 'dmadmin'),
   (select ID from FUNPERMISSION where DESCRIPTION = 'bizdata_accForecast'));



