CREATE FUNCTION [gettermend](@perid numeric,@stocknum int,@monthnum int,
                                @productId numeric,@dcid numeric) 
--CREATE FUNCTION 函数名称（@参数名 参数的数据类型）
RETURNS numeric   --返回返回值的数据类型
--[WITH ENCRYPTION]  --如果指定了 encryption 则函数被加密
as
BEGIN
  
    if (@perid < 1 or @perid < 200000 or @perid > 210000) 
	RETURN 0;

	 if (@productId <= 0 or @dcid <= 0 ) 
	RETURN 0;

    declare @perid_v nvarchar(6)
	declare @perid_d nvarchar(9)
	declare @peridend nvarchar(9)
	declare @addnum numeric
	declare @modnum numeric
	declare @termend numeric

	set @perid_v=cast(@perid as nvarchar)
	set @perid_d=@perid_v+'01'
	
	--divide monthnum		
	set @addnum = isnull(@stocknum/@monthnum,0)
	set @peridend = CONVERT(varchar(100),dateadd(MONTH,@addnum,@perid_d),112)
	set @peridend = LEFT(@peridend,6)
	set @termend =(select isnull(sum(r.SELLOUT),0) from REPLENISHDATA r
     where r.PRODUCTID=@productId and r.DCID = @dcid
     and r.PERIOD > @perid and r.PERIOD <= @peridend)

	--mod monthnum
	set @modnum = isnull(@stocknum%@monthnum,0)
	if ( @modnum > 0 ) 
	 begin
	   set @addnum = @addnum+1	
	   set @peridend = CONVERT(varchar(100),dateadd(MONTH,@addnum,@perid_d),112)
	   set @peridend = LEFT(@peridend,6)
	   set @termend = @termend + (select isnull(sum(r.SELLOUT/@monthnum*@modnum),0) from REPLENISHDATA r
       where  r.PRODUCTID=@productId and r.DCID =  @dcid and r.PERIOD =@peridend)
	 end

  RETURN  @termend
END



