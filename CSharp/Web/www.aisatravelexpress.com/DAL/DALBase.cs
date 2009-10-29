using System;
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
        protected string g_SelectProcedure = "p_ALL_Pager ";
       
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

        protected DataTable ExecuteDataTable(string Sql)
        {
            g_SqlDataAdapter = new SqlDataAdapter(Sql, g_DBConnectionString);
           
            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);
            
            if (o_DataTable.Rows != null && o_DataTable.Rows.Count > 0)
                return o_DataTable;
            else
                return null;                   
        }

        protected DataSet ExecuteDataSet(string Sql)
        {
            g_SqlDataAdapter = new SqlDataAdapter(Sql, g_DBConnectionString);

            DataSet o_DataSet = new DataSet();
            g_SqlDataAdapter.Fill(o_DataSet);

            if (o_DataSet.Tables.Count > 0 && o_DataSet.Tables[0].Rows != null && o_DataSet.Tables[0].Rows.Count > 0)
                return o_DataSet;
            else
                return null;
        }

        protected DataTable Execute_Select_DataTable(string TableName, string TableFields, string TableOrderByFields, int PageSize, int PageIndex, int IsCount, int OrderByType, string SelectWhere, ref int TotalCount, ref int TotalPage)
        {
            g_SqlDataAdapter = new SqlDataAdapter("p_ALL_Pager", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;
            
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TableName", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TableFields", SqlDbType.VarChar, 4000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TableOrderByFields", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@PageSize", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@PageIndex", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@IsCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@OrderByType", SqlDbType.Bit);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@SelectWhere", SqlDbType.VarChar, 3000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TotalCount", SqlDbType.Int);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TotalPage", SqlDbType.Int);
            
            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = TableFields;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = TableOrderByFields;
            g_SqlDataAdapter.SelectCommand.Parameters[3].Value = PageSize;
            g_SqlDataAdapter.SelectCommand.Parameters[4].Value = PageIndex;
            g_SqlDataAdapter.SelectCommand.Parameters[5].Value = IsCount;
            g_SqlDataAdapter.SelectCommand.Parameters[6].Value = OrderByType;
            g_SqlDataAdapter.SelectCommand.Parameters[7].Value = SelectWhere;
            g_SqlDataAdapter.SelectCommand.Parameters[8].Direction = ParameterDirection.Output;
            g_SqlDataAdapter.SelectCommand.Parameters[9].Direction = ParameterDirection.Output;

            g_SqlDataAdapter.SelectCommand.Parameters[8].Value = TotalCount;
            g_SqlDataAdapter.SelectCommand.Parameters[9].Value = TotalPage;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            if (o_DataTable == null || o_DataTable.Rows.Count <= 0)
                return null;
            else
            {
                if (IsCount == -2)
                {
                    TotalCount = Convert.ToInt32(o_DataTable.Rows[0][0].ToString());
                    TotalPage = 1;
                }

                if (IsCount == -1)
                {
                    TotalCount = o_DataTable.Rows.Count;
                    TotalPage = 1;
                }

                if (IsCount > 0)
                {
                    TotalCount = o_DataTable.Rows.Count;
                    TotalPage = 1;
                }
            }

            return o_DataTable;
        }

        protected int Execute_Select_DataTable(string TableName, string SelectWhere)
        {
            int TotalCount = 0;

            g_SqlDataAdapter = new SqlDataAdapter("P_Select_Count", g_DBConnectionString);
            g_SqlDataAdapter.SelectCommand.CommandType = CommandType.StoredProcedure;

            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TableName", SqlDbType.VarChar, 255);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@SelectWhere", SqlDbType.VarChar, 3000);
            g_SqlDataAdapter.SelectCommand.Parameters.Add("@TotalCount ", SqlDbType.Int);

            g_SqlDataAdapter.SelectCommand.Parameters[0].Value = TableName;
            g_SqlDataAdapter.SelectCommand.Parameters[1].Value = SelectWhere;

            g_SqlDataAdapter.SelectCommand.Parameters[2].Direction = ParameterDirection.Output;
            g_SqlDataAdapter.SelectCommand.Parameters[2].Value = TotalCount;

            DataTable o_DataTable = new DataTable();
            g_SqlDataAdapter.Fill(o_DataTable);

            return TotalCount;
        }
    }
}
