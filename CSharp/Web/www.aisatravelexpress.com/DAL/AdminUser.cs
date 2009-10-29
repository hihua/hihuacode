using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace DAL
{
    public class AdminUser : DALBase
    {
        private string g_TableName = "t_AdminUser";
        private string g_TableFields = "AdminUser_ID,AdminUser_Name,AdminUser_NickName,AdminUser_PassWord,AdminUser_Status,AdminUser_AddTime";
        private string g_TableOrderByFields = "AdminUser_ID";

        public int g_TotalCount = 0;
        public int g_TotalPage = 0;

        public AdminUser()
        {

        }

        public DataTable Select_AdminUser(string p_AdminUser_Name, string p_AdminUser_PassWord)
        {
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, "AdminUser_Name='" + p_AdminUser_Name + "' and AdminUser_PassWord='" + p_AdminUser_PassWord + "'", ref g_TotalCount, ref g_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_AdminUser(int p_PageSize, int p_PageIndex, ref int p_CountTotal, ref int p_PageTotal)
        {
            g_TableOrderByFields = "AdminUser_Status";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, "", ref g_TotalCount, ref g_TotalPage);
            return o_DataTable;
        }
    }
}
