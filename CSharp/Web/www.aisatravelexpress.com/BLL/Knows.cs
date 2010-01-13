using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Knows
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.Knows d_Knows;

        public Knows()
        {
            d_Knows = new DAL.Knows();
        }
                       
        public Entity.Knows[] Select_Knows(int p_Knows_ClassID, int p_Knows_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            p_Search_Content = FilterUtility.FilterSQL(p_Search_Content);

            DataTable o_DataTable = d_Knows.Select_Knows(p_Knows_ClassID, p_Knows_LanguageID, p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Knows[] e_Knows = new Entity.Knows[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Knows[i] = new Entity.Knows();

                    e_Knows[i].Knows_ID = Convert.ToInt32(o_DataRow["Knows_ID"].ToString());
                    e_Knows[i].Knows_ClassID = Convert.ToInt32(o_DataRow["Knows_ClassID"].ToString());
                    e_Knows[i].Knows_LanguageID = Convert.ToInt32(o_DataRow["Knows_LanguageID"].ToString());
                    e_Knows[i].Knows_Summary = o_DataRow["Knows_Summary"].ToString();
                    e_Knows[i].Knows_Title = o_DataRow["Knows_Title"].ToString();
                    e_Knows[i].Knows_Content = o_DataRow["Knows_Content"].ToString();
                    e_Knows[i].Knows_AddTime = DateTime.Parse(o_DataRow["Knows_AddTime"].ToString());

                    i++;
                }

                return e_Knows;
            }
        }

        public Entity.Knows[] Select_Knows(int p_Knows_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            p_Search_Content = FilterUtility.FilterSQL(p_Search_Content);

            DataTable o_DataTable = d_Knows.Select_Knows(p_Knows_LanguageID, p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Knows[] e_Knows = new Entity.Knows[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Knows[i] = new Entity.Knows();

                    e_Knows[i].Knows_ID = Convert.ToInt32(o_DataRow["Knows_ID"].ToString());
                    e_Knows[i].Knows_ClassID = Convert.ToInt32(o_DataRow["Knows_ClassID"].ToString());
                    e_Knows[i].Knows_LanguageID = Convert.ToInt32(o_DataRow["Knows_LanguageID"].ToString());
                    e_Knows[i].Knows_Summary = o_DataRow["Knows_Summary"].ToString();
                    e_Knows[i].Knows_Title = o_DataRow["Knows_Title"].ToString();
                    e_Knows[i].Knows_Content = o_DataRow["Knows_Content"].ToString();
                    e_Knows[i].Knows_AddTime = DateTime.Parse(o_DataRow["Knows_AddTime"].ToString());

                    i++;
                }

                return e_Knows;
            }
        }

        public Entity.Knows Select_Knows(int p_Knows_ID)
        {
            DataTable o_DataTable = d_Knows.Select_Knows(p_Knows_ID);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Knows e_Knows = new Entity.Knows();
                DataRow o_DataRow = o_DataTable.Rows[0];

                e_Knows.Knows_ID = Convert.ToInt32(o_DataRow["Knows_ID"].ToString());
                e_Knows.Knows_ClassID = Convert.ToInt32(o_DataRow["Knows_ClassID"].ToString());
                e_Knows.Knows_LanguageID = Convert.ToInt32(o_DataRow["Knows_LanguageID"].ToString());
                e_Knows.Knows_Summary = o_DataRow["Knows_Summary"].ToString();                
                e_Knows.Knows_Title = o_DataRow["Knows_Title"].ToString();
                e_Knows.Knows_Content = o_DataRow["Knows_Content"].ToString();
                e_Knows.Knows_AddTime = DateTime.Parse(o_DataRow["Knows_AddTime"].ToString());

                return e_Knows;
            }
        }

        public void Insert_Knows(int p_Knows_ClassID, int p_Knows_LanguageID, string p_Knows_Summary, string p_Knows_Title, string p_Knows_Content)
        {
            p_Knows_Summary = FilterUtility.FilterSQL(p_Knows_Summary);
            p_Knows_Title = FilterUtility.FilterSQL(p_Knows_Title);
            p_Knows_Content = FilterUtility.FilterSQL(p_Knows_Content);

            Entity.Knows o_Knows = new Entity.Knows();
            o_Knows.Knows_ClassID = p_Knows_ClassID;
            o_Knows.Knows_LanguageID = p_Knows_LanguageID;
            o_Knows.Knows_Summary = p_Knows_Summary;
            o_Knows.Knows_Title = p_Knows_Title;
            o_Knows.Knows_Content = p_Knows_Content;
            o_Knows.Knows_AddTime = DateTime.Now;

            d_Knows.Insert_Knows(o_Knows);
        }

        public void Update_Knows(int p_Knows_ID, int p_Knows_ClassID, int p_Knows_LanguageID, string p_Knows_Summary, string p_Knows_Title, string p_Knows_Content)
        {
            p_Knows_Summary = FilterUtility.FilterSQL(p_Knows_Summary);
            p_Knows_Title = FilterUtility.FilterSQL(p_Knows_Title);
            p_Knows_Content = FilterUtility.FilterSQL(p_Knows_Content);

            Entity.Knows o_Knows = new Entity.Knows();
            o_Knows.Knows_ID = p_Knows_ID;
            o_Knows.Knows_ClassID = p_Knows_ClassID;
            o_Knows.Knows_LanguageID = p_Knows_LanguageID;
            o_Knows.Knows_Summary = p_Knows_Summary;
            o_Knows.Knows_Title = p_Knows_Title;
            o_Knows.Knows_Content = p_Knows_Content;
            o_Knows.Knows_AddTime = DateTime.Now;

            d_Knows.Update_Knows(o_Knows);
        }

        public void Delete_Knows(int p_Knows_ID)
        {
            d_Knows.Delete_Knows(p_Knows_ID);
        }
    }
}
