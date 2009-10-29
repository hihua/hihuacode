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
    }
}
