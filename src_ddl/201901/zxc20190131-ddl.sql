
CREATE TABLE [dbo].[REPLENISHCOMPUTELOG](
	[ID] [numeric](18, 0) IDENTITY(1,1) NOT NULL,
	[VERSION] [numeric](18, 0) NOT NULL,
	[CURRENTPERIOD] [numeric](6, 0) NOT NULL,
	[TERMENDTYPE] [smallint] NOT NULL DEFAULT 1,
	[TERMENDNAME] [nvarchar](128) NOT NULL DEFAULT '',
	[OPERATOR] [nvarchar](128) NOT NULL,
	[BEGINTIME] [datetime] NOT NULL,
	[ENDTIME] [datetime] NOT NULL,
	[RESULT] [nvarchar](16) NOT NULL,
	[DETAILRESULT] [nvarchar](512) NOT NULL,
 CONSTRAINT [PK_REPLENISHCOMPUTELOG] PRIMARY KEY NONCLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]



ALTER TABLE [dbo].[REPLENISHCOMPUTELOG] ADD  DEFAULT ((0)) FOR [VERSION]
GO

ALTER TABLE [dbo].[REPLENISHCOMPUTELOG] ADD  DEFAULT (('')) FOR [OPERATOR]
GO

ALTER TABLE [dbo].[REPLENISHCOMPUTELOG] ADD  DEFAULT ('0') FOR [RESULT]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计算时期间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'CURRENTPERIOD'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'操作人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'OPERATOR'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'期末库存计算类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'TERMENDTYPE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'期末库存计算类型名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'TERMENDNAME'
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计算开始时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'BEGINTIME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计算结束时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'ENDTIME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计算结果' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'RESULT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'结果明细' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG', @level2type=N'COLUMN',@level2name=N'DETAILRESULT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'补货计算日志' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPLENISHCOMPUTELOG'
GO


CREATE NONCLUSTERED INDEX [INDEX_8] ON [dbo].[REPLENISHCOMPUTELOG]
(
	[ENDTIME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO


CREATE NONCLUSTERED INDEX [INDEX_7] ON [dbo].[REPLENISHCOMPUTELOG]
(
	[RESULT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO


