USE [champ888]
GO
/****** 对象:  Table [dbo].[T_AdminUser]    脚本日期: 04/18/2013 14:35:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_AdminUser](
	[AdminUser_ID] [int] IDENTITY(1,1) NOT NULL,
	[AdminUser_Name] [nvarchar](30) COLLATE Chinese_PRC_CS_AS_WS NOT NULL,
	[AdminUser_NickName] [nvarchar](30) COLLATE Chinese_PRC_CS_AS_WS NOT NULL,
	[AdminUser_PassWord] [nvarchar](16) COLLATE Chinese_PRC_CS_AS_WS NOT NULL,
	[AdminUser_Status] [int] NOT NULL,
	[AdminUser_AddTime] [datetime] NOT NULL,
 CONSTRAINT [PK_T_AdminUser] PRIMARY KEY CLUSTERED 
(
	[AdminUser_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Article]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Article](
	[Article_ID] [int] IDENTITY(1,1) NOT NULL,
	[Article_ClassID] [int] NOT NULL,
	[Article_LanguageID] [int] NOT NULL,
	[Article_Content] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[Article_AddTime] [datetime] NOT NULL,
 CONSTRAINT [PK_T_Article] PRIMARY KEY CLUSTERED 
(
	[Article_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Booking]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Booking](
	[Booking_ID] [int] IDENTITY(1,1) NOT NULL,
	[Booking_Seq] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Booking_Airline] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Booking_Contact] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Booking_Num] [int] NOT NULL,
	[Booking_Tel] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Booking_Email] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NULL,
	[Booking_AdminUser_ID] [int] NOT NULL,
	[Booking_Kind] [bit] NOT NULL,
	[Booking_State] [int] NOT NULL,
	[Booking_AddTime] [datetime] NOT NULL,
	[Booking_LastTime] [datetime] NOT NULL,
	[Booking_ComitTime] [nvarchar](60) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
 CONSTRAINT [PK_Booking] PRIMARY KEY CLUSTERED 
(
	[Booking_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_City]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_City](
	[City_ID] [int] IDENTITY(1,1) NOT NULL,
	[City_Country] [int] NOT NULL,
	[City_Name] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[City_Title] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
 CONSTRAINT [PK_T_City] PRIMARY KEY CLUSTERED 
(
	[City_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Consumption]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Consumption](
	[Consumption_ID] [int] IDENTITY(1,1) NOT NULL,
	[Consumption_Serial] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Consumption_Type] [int] NOT NULL,
	[Consumption_Src] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Consumption_Dest] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Consumption_Price] [int] NOT NULL,
	[Consumption_DePrice] [int] NOT NULL,
	[Consumption_Points] [int] NOT NULL,
	[Consumption_Commission] [int] NOT NULL,
	[Consumption_Date] [datetime] NOT NULL,
	[Consumption_Org_Member_ID] [int] NOT NULL,
	[Consumption_Com_Member_ID] [int] NOT NULL,
	[Consumption_Admin_ID] [int] NOT NULL,
	[Consumption_AddTime] [datetime] NOT NULL,
	[Consumption_Remark] [nvarchar](500) COLLATE Chinese_PRC_CI_AS NULL,
 CONSTRAINT [PK_T_Consumption] PRIMARY KEY CLUSTERED 
(
	[Consumption_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Knows]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Knows](
	[Knows_ID] [int] IDENTITY(1,1) NOT NULL,
	[Knows_ClassID] [int] NOT NULL,
	[Knows_LanguageID] [int] NOT NULL,
	[Knows_Summary] [nvarchar](80) COLLATE Chinese_PRC_CI_AS NULL,
	[Knows_Title] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Knows_Content] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[Knows_AddTime] [datetime] NOT NULL,
 CONSTRAINT [PK_T_Knows] PRIMARY KEY CLUSTERED 
(
	[Knows_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_LowFare]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_LowFare](
	[LowFare_ID] [int] IDENTITY(1,1) NOT NULL,
	[LowFare_Type] [int] NOT NULL,
	[LowFare_Detail_ID] [int] NOT NULL,
	[LowFare_Adults] [int] NOT NULL,
	[LowFare_Children] [int] NOT NULL,
	[LowFare_Infants] [int] NOT NULL,
	[LowFare_Passengers] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[LowFare_Airline] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[LowFare_Class] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Member_ID] [int] NOT NULL,
	[LowFare_AdminUser_ID] [int] NOT NULL,
	[LowFare_Status] [int] NOT NULL,
	[LowFare_AddTime] [datetime] NOT NULL,
	[LowFare_SubmitTime] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NULL,
 CONSTRAINT [PK_T_LowFare] PRIMARY KEY CLUSTERED 
(
	[LowFare_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_LowFare_Detail]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_LowFare_Detail](
	[LowFare_Detail_ID] [int] IDENTITY(1,1) NOT NULL,
	[LowFare_Detail_LowFare_ID] [int] NOT NULL,
	[LowFare_Detail_From] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Detail_To] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Detail_Departing] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Detail_Time1] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Flexibility1] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LowFare_Detail_Returning] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[LowFare_Detail_Time2] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[LowFare_Flexibility2] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NULL,
 CONSTRAINT [PK_T_LowFare_Detail] PRIMARY KEY CLUSTERED 
(
	[LowFare_Detail_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Member]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Member](
	[Member_ID] [int] IDENTITY(1,1) NOT NULL,
	[Member_Account] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_PassWord] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Name_CN] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Name_EN] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Sex] [bit] NOT NULL,
	[Member_Work] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Tel] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Mobile] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Email] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Address] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Company_Name] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Company_Tel] [nvarchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Company_Address] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Months] [nvarchar](100) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Airlines] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Serial] [nvarchar](40) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Member_Points] [int] NOT NULL,
	[Member_Commission] [int] NOT NULL,
	[Member_Consumption] [int] NOT NULL,
	[Member_Times] [int] NOT NULL,
	[Member_Recommended] [int] NOT NULL,
	[Member_ReSerial] [nvarchar](40) COLLATE Chinese_PRC_CI_AS NULL,
	[Member_Level] [int] NOT NULL,
	[Member_AddTime] [datetime] NOT NULL,
 CONSTRAINT [PK_T_Member] PRIMARY KEY CLUSTERED 
(
	[Member_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_MSN]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_MSN](
	[MSN_ID] [int] IDENTITY(1,1) NOT NULL,
	[MSN_Name] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[MSN_Invitee] [nvarchar](40) COLLATE Chinese_PRC_CI_AS NOT NULL,
 CONSTRAINT [PK_T_MSN] PRIMARY KEY CLUSTERED 
(
	[MSN_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_News]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_News](
	[News_ID] [int] IDENTITY(1,1) NOT NULL,
	[News_ClassID] [int] NOT NULL,
	[News_LanguageID] [int] NOT NULL,
	[News_Title] [nvarchar](200) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[News_Intro] [nvarchar](500) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[News_Content] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[News_AddTime] [datetime] NOT NULL,
 CONSTRAINT [PK_T_News] PRIMARY KEY CLUSTERED 
(
	[News_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** 对象:  Table [dbo].[T_Travel]    脚本日期: 04/18/2013 14:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_Travel](
	[Travel_ID] [int] IDENTITY(1,1) NOT NULL,
	[Travel_LanguageID] [int] NOT NULL,
	[Travel_TypeID] [int] NOT NULL,
	[Travel_Code] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_Name] [nvarchar](200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_Price] [nvarchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_Points] [int] NOT NULL,
	[Travel_StartDate] [datetime] NOT NULL,
	[Travel_EndDate] [datetime] NOT NULL,
	[Travel_Views] [nvarchar](4000) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_Route] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_PreView1] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_PreView2] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_PreViews] [nvarchar](max) COLLATE Chinese_PRC_CI_AS NULL,
	[Travel_StartAddr] [nvarchar](500) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_EndAddr] [nvarchar](500) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[Travel_AddTime] [datetime] NOT NULL CONSTRAINT [DF_T_Travel_Travel_AddTime]  DEFAULT (getdate()),
 CONSTRAINT [PK_T_Travel] PRIMARY KEY CLUSTERED 
(
	[Travel_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
