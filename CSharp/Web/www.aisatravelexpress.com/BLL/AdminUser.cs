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
        DAL.AdminUser g_AdminUser;

        public AdminUser()
        {
            g_AdminUser = new DAL.AdminUser();
        }

        public Entity.AdminUser Select_AdminUser(string p_AdminUser_Name, string p_AdminUser_PassWord)
        {
            p_AdminUser_Name = FilterUtility.FilterSQL(p_AdminUser_Name);
            p_AdminUser_PassWord = FilterUtility.FilterSQL(p_AdminUser_PassWord);

            DataTable o_DataTable = g_AdminUser.Select_AdminUser(p_AdminUser_Name, p_AdminUser_PassWord);
            if (o_DataTable == null)
                return null;

            Entity.AdminUser e_AdminUser = new Entity.AdminUser();
            foreach (DataRow o_DataRow in o_DataTable.Rows)
            {
                e_AdminUser.AdminUser_ID = Convert.ToInt32(o_DataRow["AdminUser_ID"].ToString());
                e_AdminUser.AdminUser_Name = o_DataRow["AdminUser_Name"].ToString();
                e_AdminUser.AdminUser_NickName = o_DataRow["AdminUser_NickName"].ToString();
                e_AdminUser.AdminUser_PassWord = o_DataRow["AdminUser_PassWord"].ToString();
                e_AdminUser.AdminUser_Status = Convert.ToInt32(o_DataRow["AdminUser_Status"].ToString());
                e_AdminUser.AdminUser_AddTime = DateTime.Parse(o_DataRow["AdminUser_AddTime"].ToString());
            }

            return e_AdminUser;
        }

        public Entity.AdminUser Select_AdminUser(int p_AdminUser_ID)
        {
            DataTable o_DataTable = g_AdminUser.Select_AdminUser(p_AdminUser_ID);
            if (o_DataTable == null)
                return null;

            Entity.AdminUser e_AdminUser = new Entity.AdminUser();
            e_AdminUser.AdminUser_ID = p_AdminUser_ID;
            e_AdminUser.AdminUser_Name = o_DataTable.Rows[0]["AdminUser_Name"].ToString();
            e_AdminUser.AdminUser_NickName = o_DataTable.Rows[0]["AdminUser_NickName"].ToString();
            e_AdminUser.AdminUser_PassWord = o_DataTable.Rows[0]["AdminUser_PassWord"].ToString();
            e_AdminUser.AdminUser_Status = Convert.ToInt32(o_DataTable.Rows[0]["AdminUser_Status"].ToString());
            e_AdminUser.AdminUser_AddTime = DateTime.Parse(o_DataTable.Rows[0]["AdminUser_AddTime"].ToString());

            return e_AdminUser;
        }

        public Entity.AdminUser[] Select_AdminUser(Entity.AdminUser p_AdminUser, int p_PageSize, int p_PageIndex, ref int p_TotalCount, ref int p_TotalPage)
        {
            DataTable o_DataTable = g_AdminUser.Select_AdminUser(p_AdminUser, p_PageSize, p_PageIndex, ref p_TotalCount, ref p_TotalPage);
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
                e_AdminUser[i].AdminUser_Status = Convert.ToInt32(o_DataRow["AdminUser_Status"].ToString());
                e_AdminUser[i].AdminUser_AddTime = DateTime.Parse(o_DataRow["AdminUser_AddTime"].ToString());

                i++;
            }

            return e_AdminUser;
        }

        public void Insert_AdminUser(string p_AdminUser_Name, string p_AdminUser_NickName, string p_AdminUser_PassWord, int p_AdminUser_Status)
        {
            p_AdminUser_Name = FilterUtility.FilterSQL(p_AdminUser_Name);
            p_AdminUser_NickName = FilterUtility.FilterSQL(p_AdminUser_NickName);
            p_AdminUser_PassWord = FilterUtility.FilterSQL(p_AdminUser_PassWord);

            Entity.AdminUser o_AdminUser = new Entity.AdminUser();
            o_AdminUser.AdminUser_ID = 0;
            o_AdminUser.AdminUser_Name = p_AdminUser_Name;
            o_AdminUser.AdminUser_NickName = p_AdminUser_NickName;
            o_AdminUser.AdminUser_PassWord = p_AdminUser_PassWord;
            o_AdminUser.AdminUser_Status = p_AdminUser_Status;
            o_AdminUser.AdminUser_AddTime = DateTime.Now;

            g_AdminUser.Insert_AdminUser(o_AdminUser);
        }

        public void Update_AdminUser(int p_AdminUser_ID, string p_AdminUser_Name, string p_AdminUser_NickName, string p_AdminUser_PassWord, int p_AdminUser_Status)
        {
            p_AdminUser_Name = FilterUtility.FilterSQL(p_AdminUser_Name);
            p_AdminUser_NickName = FilterUtility.FilterSQL(p_AdminUser_NickName);
            p_AdminUser_PassWord = FilterUtility.FilterSQL(p_AdminUser_PassWord);

            Entity.AdminUser o_AdminUser = new Entity.AdminUser();
            o_AdminUser.AdminUser_ID = p_AdminUser_ID;
            o_AdminUser.AdminUser_Name = p_AdminUser_Name;
            o_AdminUser.AdminUser_NickName = p_AdminUser_NickName;
            o_AdminUser.AdminUser_PassWord = p_AdminUser_PassWord;
            o_AdminUser.AdminUser_Status = p_AdminUser_Status;
            o_AdminUser.AdminUser_AddTime = DateTime.Now;

            g_AdminUser.Update_AdminUser(o_AdminUser);
        }

        public void Delete_AdminUser(int p_AdminUser_ID)
        {
            g_AdminUser.Delete_AdminUser(p_AdminUser_ID);
        }
    }
}
