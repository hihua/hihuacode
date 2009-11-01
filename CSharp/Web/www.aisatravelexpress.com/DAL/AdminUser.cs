using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

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

        public DataTable Select_AdminUser(int p_AdminUser_ID)
        {
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, "AdminUser_ID='" + p_AdminUser_ID.ToString() + "'", ref g_TotalCount, ref g_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_AdminUser(Entity.AdminUser p_AdminUser, int p_PageSize, int p_PageIndex, ref int p_CountTotal, ref int p_PageTotal)
        {
            g_TableOrderByFields = "AdminUser_Status";

            string o_Where = "";
            if (p_AdminUser.AdminUser_Status == 0)
                o_Where = "AdminUser_ID=" + p_AdminUser.AdminUser_ID.ToString() + " or AdminUser_Status=1";
            else
                o_Where = "AdminUser_ID=" + p_AdminUser.AdminUser_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, o_Where, ref g_TotalCount, ref g_TotalPage);
            return o_DataTable;
        }

        public void Insert_AdminUser(Entity.AdminUser p_AdminUser)
        {
            if (p_AdminUser == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "'" + p_AdminUser.AdminUser_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "'" + p_AdminUser.AdminUser_NickName + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "'" + p_AdminUser.AdminUser_PassWord + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_AdminUser.AdminUser_Status.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_AdminUser.AdminUser_AddTime.ToString();

            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_AdminUser(Entity.AdminUser p_AdminUser)
        {
            if (p_AdminUser == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "AdminUser_Name='" + p_AdminUser.AdminUser_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "AdminUser_NickName='" + p_AdminUser.AdminUser_NickName + "'";
            o_FieldsValue += ",";

            if (VerifyUtility.IsString_NotNull(p_AdminUser.AdminUser_PassWord))
            {
                o_FieldsValue += "AdminUser_PassWord='" + p_AdminUser.AdminUser_PassWord + "'";
                o_FieldsValue += ",";
            }

            o_FieldsValue += "AdminUser_Status=" + p_AdminUser.AdminUser_Status.ToString();
            string o_Where = "AdminUser_ID=" + p_AdminUser.AdminUser_ID;

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_AdminUser(int p_AdminUser_ID)
        {
            string o_Where = "AdminUser_ID=" + p_AdminUser_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
