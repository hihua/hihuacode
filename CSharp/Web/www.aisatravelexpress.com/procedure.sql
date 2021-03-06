USE [champ888]
GO
/****** 对象:  StoredProcedure [dbo].[P_ALL_Pager]    脚本日期: 04/18/2013 14:38:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[P_ALL_Pager]
@i_TableName			nvarchar(255),					-- 表名
@i_FieldsName			nvarchar(Max) = '*',			-- 需要返回的列
@i_OrderByFields		nvarchar(255) = '',				-- 排序的字段名
@i_PageSize				int = 10,						-- 页尺寸
@i_PageIndex			int = 1,						-- 页码
@i_IsCount				int = 0,						-- 返回记录总数, -2返回总数, -1返回所有, 0返回记录, >0返回Top N总数
@i_OrderByType			int = 0,						-- 设置排序类型,0 = ASC, 1 = DESC
@i_Where				nvarchar(Max) = '',				-- 查询条件 (注意: 不要加 where)
@o_TotalCount			int = 0 output,					-- 返回总数
@o_TotalPage			int = 0 output					-- 返回总页数
AS
Declare @n_CountSQL		nvarchar(Max)					-- 主语句
Declare @v_SQL			nvarchar(Max)
Declare @v_Temp			nvarchar(Max)
Declare @v_Order		nvarchar(Max)


If @i_IsCount = -2
	Begin        
		If Len(@i_Where) = 0
			Set @v_SQL = 'Select Count(1) As Total From [' + @i_TableName + ']'
		Else
			Set @v_SQL = 'Select Count(1) As Total From [' + @i_TableName + '] Where ' + @i_Where
	End
Else  
	Begin
		If @i_IsCount = -1
			Begin
				Set @v_SQL = 'Select ' + @i_FieldsName + ' From ' + @i_TableName

				If Len(@i_Where) > 0
					Set @v_SQL = @v_SQL + ' Where ' + @i_Where

				If @i_OrderByType = 0
					Set @v_SQL = @v_SQL + ' Order By [' + @i_OrderByFields + '] Asc'
				Else
					Set @v_SQL = @v_SQL + ' Order By [' + @i_OrderByFields + '] Desc'
			End	
		Else
			Begin
				If @i_IsCount > 0
					Begin
						Set @v_SQL = 'Select Top ' + ltrim(str(@i_IsCount)) + ' ' + @i_FieldsName + ' From ' + @i_TableName

						If Len(@i_Where) > 0
							Set @v_SQL = @v_SQL + ' Where ' + @i_Where

						If @i_OrderByType = 0
							Set @v_SQL = @v_SQL + ' Order By [' + @i_OrderByFields + '] Asc'
						Else
							Set @v_SQL = @v_SQL + ' Order By [' + @i_OrderByFields + '] Desc'
					End
				Else
					Begin 
						If Len(@i_Where) > 0 
							Set @n_CountSQL = 'Select @TotalCount = Count(1) From [' + @i_TableName + '] Where ' + @i_Where							
						Else
							Set @n_CountSQL = 'Select @TotalCount = Count(1) From [' + @i_TableName + ']'
						
						Execute sp_ExecuteSql @n_CountSQL, N'@TotalCount int out', @o_TotalCount out
						Set @o_TotalPage = Ceiling(@o_TotalCount / @i_PageSize) + 1

						If @i_OrderByType = 0
							Begin
								Set @v_Temp  = ' > (Select Max'
								Set @v_Order = ' Order By [' + @i_OrderByFields + '] Asc'
							End
						Else
							Begin
								Set @v_Temp  = ' < (Select Min'
								Set @v_Order = ' Order By [' + @i_OrderByFields + '] Desc'
							End

						If @i_PageIndex = 1
							Begin
								If Len(@i_Where) = 0
									Set @v_SQL = 'Select Top ' + Str(@i_PageSize) + ' ' + @i_FieldsName + ' From [' + @i_TableName + '] ' + @v_Order        
								Else
									Set @v_SQL = 'Select Top ' + Str(@i_PageSize) + ' ' + @i_FieldsName + ' From [' + @i_TableName + '] Where ' + @i_Where + ' ' + @v_Order
							End
						Else
							Begin
								Set @v_SQL = 'Select Top ' + Str(@i_PageSize) + ' ' + @i_FieldsName + ' From [' + @i_TableName + '] Where [' + @i_OrderByFields + ']' + @v_Temp + ' ([' + @i_OrderByFields + ']) From (Select Top ' + Str((@i_PageIndex - 1) * @i_PageSize)+ '  ' + @i_FieldsName + '  From [' + @i_TableName + ']' + @v_Order + ') As TableTemp)' + @v_Order

								If Len(@i_Where) > 0
									Set @v_SQL = 'Select Top ' + Str(@i_PageSize) + ' ' + @i_FieldsName + ' From [' + @i_TableName + '] Where [' + @i_OrderByFields + ']' + @v_Temp + ' ([' + @i_OrderByFields + ']) From (Select Top ' + Str((@i_PageIndex - 1) * @i_PageSize) + '  ' + @i_FieldsName + '  From [' + @i_TableName + '] Where ' + @i_Where + ' ' + @v_Order + ') As TableTemp) and ' + @i_Where + ' ' + @v_Order
							End
					End
			End
	End			
execute (@v_SQL)



GO
/****** 对象:  StoredProcedure [dbo].[P_Delete]    脚本日期: 04/18/2013 14:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[P_Delete] 
@i_TableName			nvarchar(255),
@i_Where				nvarchar(Max)
AS
Declare @v_SQL			nvarchar(Max)
BEGIN
	Set @v_SQL = 'Delete From ' + @i_TableName + ' Where ' + @i_Where 
	execute (@v_SQL)
END

GO
/****** 对象:  StoredProcedure [dbo].[P_Insert]    脚本日期: 04/18/2013 14:38:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[P_Insert] 
@i_TableName			nvarchar(255),
@i_FieldsName			nvarchar(Max),
@i_FieldsValue			nvarchar(Max)
AS
Declare @v_SQL			nvarchar(Max)
BEGIN
	Set @v_SQL = 'Insert Into ' + @i_TableName + '(' + @i_FieldsName + ') Values(' + @i_FieldsValue + ')' 
	execute (@v_SQL)
END

GO
/****** 对象:  StoredProcedure [dbo].[P_Select_Count]    脚本日期: 04/18/2013 14:38:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[P_Select_Count] 
@i_TableName		nvarchar(255),				-- 表名
@i_Where			nvarchar(Max) = '',			-- 查询条件 (注意: 不要加 where)
@o_TotalCount		int = 0 output				-- 返回总数
AS
Declare @n_CountSQL   nvarchar(Max)			-- 主语句
BEGIN
	Set @n_CountSQL = 'Select @TotalCount = Count(*) From [' + @i_TableName + ']'
	If Len(@i_Where) > 0
		Set @n_CountSQL = @n_CountSQL + ' Where ' + @i_Where 

	Execute sp_ExecuteSql @n_CountSQL, N'@TotalCount int out', @o_TotalCount out 
END

GO
/****** 对象:  StoredProcedure [dbo].[P_Update]    脚本日期: 04/18/2013 14:38:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[P_Update] 
@i_TableName			nvarchar(255),
@i_FieldsValue			nvarchar(Max),
@i_Where				nvarchar(Max)
AS
Declare @v_SQL			nvarchar(Max)
BEGIN
	Set @v_SQL = 'Update ' + @i_TableName + ' Set ' + @i_FieldsValue + ' Where ' + @i_Where
	execute (@v_SQL)
END
