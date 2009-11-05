using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Article
    {
        DAL.Article d_Article;

        public Article()
        {
            d_Article = new DAL.Article();
        }

        public Entity.Article Select_Article(int p_Article_ClassID, int p_Article_LanguageID)
        {
            DataTable o_DataTable = d_Article.Select_Article(p_Article_ClassID, p_Article_LanguageID);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Article e_Article = new Entity.Article();

                e_Article.Article_ID = Convert.ToInt32(o_DataTable.Rows[0]["Article_ID"].ToString());
                e_Article.Article_ClassID = Convert.ToInt32(o_DataTable.Rows[0]["Article_ClassID"].ToString());
                e_Article.Article_LanguageID = Convert.ToInt32(o_DataTable.Rows[0]["Article_LanguageID"].ToString());
                e_Article.Article_Content = o_DataTable.Rows[0]["Article_Content"].ToString();
                e_Article.Article_AddTime = DateTime.Parse(o_DataTable.Rows[0]["Article_AddTime"].ToString());

                return e_Article;
            }
        }

        public void Update_Article(int p_Article_ID, int p_Article_ClassID, int p_Article_LanguageID, string p_Article_Content)
        {
            Entity.Article e_Article = new Entity.Article();

            e_Article.Article_ID = p_Article_ID;
            e_Article.Article_ClassID = p_Article_ClassID;
            e_Article.Article_LanguageID = p_Article_LanguageID;
            e_Article.Article_Content = FilterUtility.FilterSQL(p_Article_Content);
            e_Article.Article_AddTime = DateTime.Now;

            d_Article.Update_Article(e_Article);
        }
    }
}
