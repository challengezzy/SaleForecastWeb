IF EXISTS (SELECT 1
            FROM  SYSINDEXES
           WHERE  ID    = OBJECT_ID('META_ENDING_INVENTORY')
            AND   NAME  = 'INDEX_2'
            AND   INDID > 0
            AND   INDID < 255)
   DROP INDEX META_ENDING_INVENTORY.INDEX_2
go

IF EXISTS (SELECT 1
            FROM  SYSINDEXES
           WHERE  ID    = OBJECT_ID('META_ENDING_INVENTORY')
            AND   NAME  = 'INDEX_1'
            AND   INDID > 0
            AND   INDID < 255)
   DROP INDEX META_ENDING_INVENTORY.INDEX_1
go

IF EXISTS (SELECT 1
            FROM  SYSOBJECTS
           WHERE  ID = OBJECT_ID('META_ENDING_INVENTORY')
            AND   TYPE = 'U')
   DROP TABLE META_ENDING_INVENTORY
go

/*==============================================================*/
/* Table: META_ENDING_INVENTORY                                 */
/*==============================================================*/
CREATE TABLE META_ENDING_INVENTORY (
   ID                   NUMERIC(18)          IDENTITY,
   CODE                 VARCHAR(255)         NOT NULL,
   NAME                 VARCHAR(255)         NOT NULL,
   WEIGHT_COEFFICIENT   DECIMAL              NULL,
   COMMENTS             VARCHAR(255)         NULL,
   VERSION              NUMERIC(10)          NULL DEFAULT 0,
   UPDATETIME           DATETIME             NULL,
   CONSTRAINT PK_META_ENDING_INVENTORY PRIMARY KEY NONCLUSTERED (ID)
)
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '期末库存数据定义',
   'user', @CURRENTUSER, 'table', 'META_ENDING_INVENTORY'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '编码',
   'user', @CURRENTUSER, 'table', 'META_ENDING_INVENTORY', 'column', 'CODE'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '名称',
   'user', @CURRENTUSER, 'table', 'META_ENDING_INVENTORY', 'column', 'NAME'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '权重系统，+1或-1',
   'user', @CURRENTUSER, 'table', 'META_ENDING_INVENTORY', 'column', 'WEIGHT_COEFFICIENT'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '备注',
   'user', @CURRENTUSER, 'table', 'META_ENDING_INVENTORY', 'column', 'COMMENTS'
go

/*==============================================================*/
/* Index: INDEX_1                                               */
/*==============================================================*/
CREATE UNIQUE INDEX INDEX_1 ON META_ENDING_INVENTORY (
CODE ASC
)
go

/*==============================================================*/
/* Index: INDEX_2                                               */
/*==============================================================*/
CREATE INDEX INDEX_2 ON META_ENDING_INVENTORY (
NAME ASC
)
go


IF EXISTS (SELECT 1
            FROM  SYSINDEXES
           WHERE  ID    = OBJECT_ID('ENDING_INVENTORY_DATA')
            AND   NAME  = 'INDEX_6'
            AND   INDID > 0
            AND   INDID < 255)
   DROP INDEX ENDING_INVENTORY_DATA.INDEX_6
go

IF EXISTS (SELECT 1
            FROM  SYSINDEXES
           WHERE  ID    = OBJECT_ID('ENDING_INVENTORY_DATA')
            AND   NAME  = 'INDEX_5'
            AND   INDID > 0
            AND   INDID < 255)
   DROP INDEX ENDING_INVENTORY_DATA.INDEX_5
go

IF EXISTS (SELECT 1
            FROM  SYSOBJECTS
           WHERE  ID = OBJECT_ID('ENDING_INVENTORY_DATA')
            AND   TYPE = 'U')
   DROP TABLE ENDING_INVENTORY_DATA
go

/*==============================================================*/
/* Table: ENDING_INVENTORY_DATA                                 */
/*==============================================================*/
CREATE TABLE ENDING_INVENTORY_DATA (
   ID                   NUMERIC(18)          IDENTITY,
   VERSION              NUMERIC(10)          NOT NULL DEFAULT 0,
   PRODUCTID            NUMERIC(18)          NOT NULL,
   DCID                 NUMERIC(18)          NOT NULL,
   PERIOD               NUMERIC(6)           NOT NULL,
   ENDINV_CODE          VARCHAR(255)         NOT NULL DEFAULT '',
   VALUE                NUMERIC(15)          NOT NULL DEFAULT 0,
   COMMENTS             VARCHAR(255)         NULL,
   INITTIME             DATETIME             NOT NULL,
   UPDATETIME           DATETIME             NULL,
   CONSTRAINT PK_ENDING_INVENTORY_DATA PRIMARY KEY NONCLUSTERED (ID)
)
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '期末库存数据',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '明细产品ID',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'PRODUCTID'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '分仓ID',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'DCID'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '期间',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'PERIOD'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '期末库存编码',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'ENDINV_CODE'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '数据值',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'VALUE'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '备注',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'COMMENTS'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '产生时间，记录数据产生时间',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'INITTIME'
go

DECLARE @CURRENTUSER SYSNAME
SELECT @CURRENTUSER = USER_NAME()
EXECUTE SP_ADDEXTENDEDPROPERTY 'MS_Description', 
   '更新时间',
   'user', @CURRENTUSER, 'table', 'ENDING_INVENTORY_DATA', 'column', 'UPDATETIME'
go

/*==============================================================*/
/* Index: INDEX_5                                               */
/*==============================================================*/
CREATE UNIQUE INDEX INDEX_5 ON ENDING_INVENTORY_DATA (
PERIOD ASC,
DCID ASC,
PRODUCTID ASC,
ENDINV_CODE
)
go

/*==============================================================*/
/* Index: INDEX_6                                               */
/*==============================================================*/
CREATE INDEX INDEX_6 ON ENDING_INVENTORY_DATA (
PERIOD ASC
)
go
