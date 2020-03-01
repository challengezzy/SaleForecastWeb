create table PRODUCT_SUIT (
	ID numeric(18, 0) identity,
	VERSION numeric(10, 0) not null,
	SUIT_PRODUCT_ID numeric(18, 0) not null,
	PRODUCT_ID numeric(18, 0) not null,
	PRODUCT_NUMBER numeric(4, 0) not null
);

-- 添加主键
alter table PRODUCT_SUIT add constraint PRODUCT_SUIT_PK_ID primary key(ID);

-- 添加套装ID 外键
-- alter table PRODUCT_SUIT add constraint PRODUCT_SUIT_FK_SUIT_PRODUCT_ID foreign key(SUIT_PRODUCT_ID) references PRODUCT(ID);

-- 添加产品ID 外键
-- alter table PRODUCT_SUIT add constraint PRODUCT_SUIT_FK_PRODUCT_ID foreign key(PRODUCT_ID) references PRODUCT(ID);

--添加索引

CREATE INDEX IDXPRODUCT_SUIT_SUITID ON PRODUCT_SUIT ( SUIT_PRODUCT_ID ASC )
go

CREATE INDEX IDXPRODUCT_SUIT_PRODUCTID ON PRODUCT_SUIT ( PRODUCT_ID ASC )
go