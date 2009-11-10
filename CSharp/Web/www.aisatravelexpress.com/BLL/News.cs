using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class News
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.News d_News;

        public News()
        {
            d_News = new DAL.News();
        }

        public Entity.News[] Select_News(int p_News_ClassID, int p_News_LanguageID, int p_PageSize, int p_PageIndex)
        {
            DataTable o_DataTable = d_News.Select_News(p_News_ClassID, p_News_LanguageID, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.News[] e_News = new Entity.News[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_News[i] = new Entity.News();

                    e_News[i].News_ID = Convert.ToInt32(o_DataRow["News_ID"].ToString());
                    e_News[i].News_ClassID = Convert.ToInt32(o_DataRow["News_ClassID"].ToString());
                    e_News[i].News_LanguageID = Convert.ToInt32(o_DataRow["News_LanguageID"].ToString());
                    e_News[i].News_Title = o_DataRow["News_Title"].ToString();
                    e_News[i].News_Content = o_DataRow["News_Content"].ToString();
                    e_News[i].News_AddTime = DateTime.Parse(o_DataRow["News_AddTime"].ToString());

                    i++;
                }

                return e_News;
            }
        }

        public Entity.News[] Select_News(int p_News_ClassID, int p_News_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            p_Search_Content = FilterUtility.FilterSQL(p_Search_Content);

            DataTable o_DataTable = d_News.Select_News(p_News_ClassID, p_News_LanguageID, p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.News[] e_News = new Entity.News[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_News[i] = new Entity.News();

                    e_News[i].News_ID = Convert.ToInt32(o_DataRow["News_ID"].ToString());
                    e_News[i].News_ClassID = Convert.ToInt32(o_DataRow["News_ClassID"].ToString());
                    e_News[i].News_LanguageID = Convert.ToInt32(o_DataRow["News_LanguageID"].ToString());
                    e_News[i].News_Title = o_DataRow["News_Title"].ToString();
                    e_News[i].News_Content = o_DataRow["News_Content"].ToString();
                    e_News[i].News_AddTime = DateTime.Parse(o_DataRow["News_AddTime"].ToString());

                    i++;
                }

                return e_News;
            }
        }

        public Entity.News Select_News(int p_News_ID)
        {
            DataTable o_DataTable = d_News.Select_News(p_News_ID);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.News e_News = new Entity.News();
                DataRow o_DataRow = o_DataTable.Rows[0];
                                
                e_News.News_ID = Convert.ToInt32(o_DataRow["News_ID"].ToString());
                e_News.News_ClassID = Convert.ToInt32(o_DataRow["News_ClassID"].ToString());
                e_News.News_LanguageID = Convert.ToInt32(o_DataRow["News_LanguageID"].ToString());
                e_News.News_Title = o_DataRow["News_Title"].ToString();
                e_News.News_Content = o_DataRow["News_Content"].ToString();
                e_News.News_AddTime = DateTime.Parse(o_DataRow["News_AddTime"].ToString());

                return e_News;
            }
        }

        public void Insert_News(int p_News_ClassID, int p_News_LanguageID, string p_News_Title, string p_News_Content)
        {
            p_News_Title = FilterUtility.FilterSQL(p_News_Title);
            p_News_Content = FilterUtility.FilterSQL(p_News_Content);

            Entity.News o_News = new Entity.News();
            o_News.News_ClassID = p_News_ClassID;
            o_News.News_LanguageID = p_News_LanguageID;
            o_News.News_Title = p_News_Title;
            o_News.News_Content = p_News_Content;
            o_News.News_AddTime = DateTime.Now;

            d_News.Insert_News(o_News);
        }

        public void Update_News(int p_News_ID, int p_News_ClassID, int p_News_LanguageID, string p_News_Title, string p_News_Content)
        {
            p_News_Title = FilterUtility.FilterSQL(p_News_Title);
            p_News_Content = FilterUtility.FilterSQL(p_News_Content);

            Entity.News o_News = new Entity.News();
            o_News.News_ID = p_News_ID;
            o_News.News_ClassID = p_News_ClassID;
            o_News.News_LanguageID = p_News_LanguageID;
            o_News.News_Title = p_News_Title;
            o_News.News_Content = p_News_Content;
            o_News.News_AddTime = DateTime.Now;

            d_News.Update_News(o_News);
        }

        public void Delete_News(int p_News_ID)
        {
            d_News.Delete_News(p_News_ID);
        }
    }
}
