using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace DAL
{
    public class AdminUser : DALBase
    {
        private string g_Fields = "'t_AdminUser','AdminUser_ID,AdminUser_Name,AdminUser_NickName,AdminUser_PassWord,AdminUser_Status,AdminUser_AddTime',";
        private string g_OrderBy = "'AdminUser_ID',";

        public AdminUser()
        {

        }

        public DataTable Select_AdminUser(string p_AdminUser_Name, string p_AdminUser_PassWord)
        {
            string Sql = g_SelectProcedure + g_Fields + g_OrderBy + "1,1,1,1,'AdminUser_Name=''" + p_AdminUser_Name + "'' and AdminUser_PassWord=''" + p_AdminUser_PassWord + "'''";
            DataTable o_DataTable = ExecuteDataTable(Sql);
            return o_DataTable;
        }

        public DataTable Select_AdminUser(int p_PageSize, int p_PageIndex, ref int p_CountTotal, ref int p_PageTotal)
        {
            g_OrderBy = "'AdminUser_Status',";
            string Sql = g_SelectProcedure + g_Fields + g_OrderBy + p_PageSize.ToString() + "," + p_PageIndex.ToString() + ",-1,0,''";
            DataTable o_DataTable = ExecuteDataTable(Sql);
            return o_DataTable;
        }
    }
}
