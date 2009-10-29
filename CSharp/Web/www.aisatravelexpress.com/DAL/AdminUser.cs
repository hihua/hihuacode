using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace DAL
{
    public class AdminUser : DALBase
    {
        public AdminUser()
        {

        }

        public DataTable Select_AdminUser(string p_AdminUser_Name, string p_AdminUser_PassWord)
        {
            string Sql = "p_ALL_Pager 't_AdminUser','AdminUser_ID,AdminUser_Name,AdminUser_NickName,AdminUser_PassWord,AdminUser_Status,AdminUser_AddTime','AdminUser_ID',1,1,0,1,'AdminUser_Name=''" + p_AdminUser_Name + "'' and AdminUser_PassWord=''" + p_AdminUser_PassWord + "'''";
            DataTable o_DataTable = ExecuteDataTable(Sql);
            return o_DataTable;
        }
    }
}
