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

        public Entity.AdminUser[] Select_AdminUser(int p_PageSize, int p_PageIndex, ref int p_CountTotal, ref int p_PageTotal)
        {
            DAL.AdminUser d_AdminUser = new DAL.AdminUser();
            DataTable o_DataTable = d_AdminUser.Select_AdminUser(p_PageSize, p_PageIndex, ref p_CountTotal, ref p_PageTotal);
            if (o_DataTable == null)
                return null;

            Entity.AdminUser[] e_AdminUser = new Entity.AdminUser[o_DataTable.Rows.Count];

            int i = 0;
            foreach (DataRow o_DataRow in o_DataTable.Rows)
            {
                e_AdminUser[i] = new Entity.AdminUser();

                e_AdminUser[i].AdminUser_ID = Convert.ToInt32(o_DataRow["AdminUser_ID"].ToString());
                e_AdminUser[i].AdminUser_Name = o_DataRow["AdminUser_Name"].ToString();
                e_AdminUser[i].AdminUser_NickName = o_DataRow["AdminUser_NickName"].ToString();
                e_AdminUser[i].AdminUser_PassWord = o_DataRow["AdminUser_PassWord"].ToString();

                if (o_DataRow["AdminUser_PassWord"].ToString() == "0")
                    e_AdminUser[i].AdminUser_Status = AdminUser_Status.AdminUser_Admin;

                if (o_DataRow["AdminUser_PassWord"].ToString() == "1")
                    e_AdminUser[i].AdminUser_Status = AdminUser_Status.AdminUser_User;

                e_AdminUser[i].AdminUser_AddTime = DateTime.Parse(o_DataRow["AdminUser_AddTime"].ToString());

                i++;
            }

            return e_AdminUser;
        }
    }
}
