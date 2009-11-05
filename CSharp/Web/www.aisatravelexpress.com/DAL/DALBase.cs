using System;
using System.Configuration;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using System.Web;
using System.Web.Configuration;

using Utility;

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
                    g_DBConnectionString = WebConfigurationManager.ConnectionStrings["DBConnectString_Location_1"].ConnectionString;
                                
                if (i_ServerName == "HUANGHAIHUA")
                    g_DBConnectionString = WebConfigurationManager.ConnectionStrings["DBConnectString_Location_2"].ConnectionString;
            }
            else
                g_DBConnectionString = WebConfigurationManager.ConnectionStrings["DBConnectString_Remote"].ConnectionString;                
        }

        protected DataTable Execute_Select_DataTable(string p_TableName, string p_TableFields, string p_TableOrderByFields, int p_PageSize, int p_PageIndex, int p_IsCount, int p_OrderByType, string p_SelectWhere, ref int r_TotalCount, ref int r_TotalPage)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_ALL_Pager", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsName", SqlDbType.NVarChar);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByFields", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageSize", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageIndex", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_IsCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByType", SqlDbType.Bit);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.NVarChar);
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
                        
            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            string s_TotalCount = g_SqlDataAdapter.SelectCommand.Parameters[8].Value.ToString();
            string s_TotalPage = g_SqlDataAdapter.SelectCommand.Parameters[9].Value.ToString();

            if (!string.IsNullOrEmpty(s_TotalCount) && VerifyUtility.Is_Number(s_TotalCount, 1))
                r_TotalCount = Convert.ToInt32(s_TotalCount);

            if (!string.IsNullOrEmpty(s_TotalPage) && VerifyUtility.Is_Number(s_TotalPage, 1))
                r_TotalPage = Convert.ToInt32(s_TotalPage);
            
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

        protected DataTable Execute_Select_DataTable(string p_TableName, string p_TableFields, string p_TableOrderByFields, int p_PageSize, int p_PageIndex, int p_IsCount, int p_OrderByType, string p_SelectWhere)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_ALL_Pager", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsName", SqlDbType.NVarChar);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByFields", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageSize", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_PageIndex", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_IsCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_OrderByType", SqlDbType.Bit);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.NVarChar);
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

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);                        

            if (o_DataTable == null || o_DataTable.Rows.Count <= 0)
                return null;
            else            
                return o_DataTable;            
        }

        protected int Execute_Select_DataTable(string p_TableName, string p_SelectWhere)
        {
            int i_TotalCount = 0;

            g_SqlDataAdapter = new SqlDataAdapter("P_Select_Count", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.NVarChar);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@o_TotalCount", SqlDbType.Int);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_SelectWhere;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Direction = ParameterDirection.Output;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = i_TotalCount;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            string s_TotalCount = g_SqlDataAdapter.SelectCommand.Parameters[2].Value.ToString();
            if (!string.IsNullOrEmpty(s_TotalCount))
                i_TotalCount = Convert.ToInt32(s_TotalCount);

            return i_TotalCount;
        }

        protected void Execute_Insert(string p_TableName, string p_FieldsName, string p_FieldsValue)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_Insert", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsName", SqlDbType.NVarChar);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsValue", SqlDbType.NVarChar);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_FieldsName;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = p_FieldsValue;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);
        }

        protected void Execute_Update(string p_TableName, string p_FieldsValue, string p_Where)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_Update", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_FieldsValue", SqlDbType.NVarChar);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.NVarChar);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_FieldsValue;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = p_Where;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);
        }

        protected void Execute_Delete(string p_TableName, string p_Where)
        {
            g_SqlDataAdapter = new SqlDataAdapter("P_Delete", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_TableName", SqlDbType.NVarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@i_Where", SqlDbType.NVarChar);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = p_TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = p_Where;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);
        }
    }
}
