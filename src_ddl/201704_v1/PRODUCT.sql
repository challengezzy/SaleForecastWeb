-- 套装
ALTER TABLE PRODUCT
ADD IS_SUIT NUMERIC(2, 0) default 0;

-- 保质期
ALTER TABLE PRODUCT
ADD SHELF_LIFE NUMERIC(4, 0) default 0;

-- 提前采购期
ALTER TABLE PRODUCT
ADD PURCHASE_LEAD_TIME NUMERIC(4, 0) default 0;

-- 提前下架期
ALTER TABLE PRODUCT
ADD WITHDRAW_LEAD_TIME NUMERIC(2, 0) default 0;