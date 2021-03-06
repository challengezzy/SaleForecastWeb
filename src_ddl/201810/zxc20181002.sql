
alter table dbo.bizdata add IS_SUIT_SUPPORT numeric(2,0) not null default 1;

EXECUTE sp_addextendedproperty 
N'MS_Description','默认1表示不需要折和计算  2表示需要折和计算，',
N'user',N'dbo',
N'table',N'bizdata',
N'column',N'IS_SUIT_SUPPORT';

/**老数据订正为支持折和计算*/
update dbo.bizdata set IS_SUIT_SUPPORT = '1';

-- 是否支持折合计算
delete from SYSPARAM where code='SYSPARAM_CODE_ISSUPPORT_SUIT';
insert into SYSPARAM
  ( VERSION, CODE, VALUE, VALUETYPE, DESCRIPTION, COMMENTS)
values
  (
   0,
   'SYSPARAM_CODE_ISSUPPORT_SUIT',
   1,--1表示不需要折和计算  2表示需要折和计算
   0,
   '1表示不需要折和计算  2表示需要折和计算',
   null);

