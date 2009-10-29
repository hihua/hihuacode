using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class AdminUser
    {
        public Entity.AdminUser Select_AdminUser(string p_AdminUser_Name, string p_AdminUser_PassWord)
        {
            p_AdminUser_Name = FilterUtility.FilterSQL(p_AdminUser_Name);
            p_AdminUser_PassWord = FilterUtility.FilterSQL(p_AdminUser_PassWord);

            DAL.AdminUser d_AdminUser = new DAL.AdminUser();
            DataTable o_DataTable = d_AdminUser.Select_AdminUser(p_AdminUser_Name, p_AdminUser_PassWord);
            if (o_DataTable == null)
                return null;

            Entity.AdminUser e_AdminUser = new Entity.AdminUser();
            foreach (DataRow o_DataRow in o_DataTable.Rows)
            {
                e_AdminUser.AdminUser_ID = Convert.ToInt32(o_DataRow["AdminUser_ID"].ToString());
                e_AdminUser.AdminUser_Name = o_DataRow["AdminUser_Name"].ToString();
                e_AdminUser.AdminUser_NickName = o_DataRow["AdminUser_NickName"].ToString();
                e_AdminUser.AdminUser_PassWord = o_DataRow["AdminUser_PassWord"].ToString();

                if (o_DataRow["AdminUser_PassWord"].ToString() == "0")
                    e_AdminUser.AdminUser_Status = AdminUser_Status.AdminUser_Admin;

                if (o_DataRow["AdminUser_PassWord"].ToString() == "1")
                    e_AdminUser.AdminUser_Status = AdminUser_Status.AdminUser_User;

                e_AdminUser.AdminUser_AddTime = DateTime.Parse(o_DataRow["AdminUser_AddTime"].ToString());
            }

            return e_AdminUser;
        }
    }
}
