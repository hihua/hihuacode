﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using System.Web;
using System.Web.Configuration;

namespace DAL
{
    public class DALBase
    {
        protected SqlDataAdapter g_SqlDataAdapter = null;
        private string g_DBConnectionString = "";
        
        public DALBase()
        {
            string i_ServerIP = HttpContext.Current.Request.ServerVariables["Local_Addr"];
            
            if (i_ServerIP == "127.0.0.1")
            {
                string i_ServerName = HttpContext.Current.Server.MachineName.ToUpper();

                if (i_ServerName == "HIHUA-X61")
                    g_DBConnectionString = WebConfigurationManager.AppSettings["DBConnectString_Location_1"];

                if (i_ServerName == "HUANGHAIHUA")
                    g_DBConnectionString = WebConfigurationManager.AppSettings["DBConnectString_Location_2"];
            }
            else
                g_DBConnectionString = WebConfigurationManager.AppSettings["DBConnectString_Remote"];                                    
        }

        protected DataTable Execute_Select_DataTable(string p_TableName, string p_TableFields, string p_TableOrderByFields, int p_PageSize, int p_PageIndex, int p_IsCount, int p_OrderByType, string p_SelectWhere, ref int r_TotalCount, ref int r_TotalPage)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_ALL_Pager", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsName", SqlDbType.VarChar, 4000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByFields", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageSize", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageIndex", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_IsCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByType", SqlDbType.Bit);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.VarChar, 3000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@o_TotalCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@o_TotalPage", SqlDbType.Int);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_TableFields;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = p_TableOrderByFields;
            g_SqlDataAdapter.SelectCommand.Parameters[3].Value = p_PageSize;
            g_SqlDataAdapter.SelectCommand.Parameters[4].Value = p_PageIndex;
            g_SqlDataAdapter.SelectCommand.Parameters[5].Value = p_IsCount;
            g_SqlDataAdapter.SelectCommand.Parameters[6].Value = p_OrderByType;
            g_SqlDataAdapter.SelectCommand.Parameters[7].Value = p_SelectWhere;
            g_SqlDataAdapter.SelectCommand.Parameters[8].Direction = ParameterDirection.Output;
            g_SqlDataAdapter.SelectCommand.Parameters[9].Direction = ParameterDirection.Output;

            g_SqlDataAdapter.SelectCommand.Parameters[8].Value = r_TotalCount;
            g_SqlDataAdapter.SelectCommand.Parameters[9].Value = r_TotalPage;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            if (o_DataTable == null || o_DataTable.Rows.Count <= 0)
                return null;
            else
            {
                if (p_IsCount == -2)
                {
                    r_TotalCount = Convert.ToInt32(o_DataTable.Rows[0][0].ToString());
                    r_TotalPage = 1;
                }

                if (p_IsCount == -1)
                {
                    r_TotalCount = o_DataTable.Rows.Count;
                    r_TotalPage = 1;
                }

                if (p_IsCount > 0)
                {
                    r_TotalCount = o_DataTable.Rows.Count;
                    r_TotalPage = 1;
                }
            }

            return o_DataTable;
        }

        protected int Execute_Select_DataTable(string p_TableName, string p_SelectWhere)
        {
            int i_TotalCount = 0;

            g_SqlDataAdapter = new SqlDataAdapter("P_Select_Count", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.VarChar, 3000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@o_TotalCount", SqlDbType.Int);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_SelectWhere;

            g_SqlDataAdapter.SelectCommand.Parameters[2].Direction = ParameterDirection.Output;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = i_TotalCount;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            return i_TotalCount;
        }
    }
}
