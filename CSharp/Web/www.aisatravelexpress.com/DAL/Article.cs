using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Article : DALBase
    {
        private string g_TableName = "t_Article";
        private string g_TableFields = "Article_ID,Article_ClassID,Article_LanguageID,Article_Name,Article_Content,Article_AddTime";
        private string g_TableOrderByFields = "Article_ID";
                
        public Article()
        {

        }

        public DataTable Select_Article(int p_Article_ClassID, int p_Article_LanguageID)
        {
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, "Article_ClassID=" + p_Article_ClassID.ToString() + " and Article_LanguageID=" + p_Article_LanguageID.ToString());
            return o_DataTable;
        }

        public void Update_Article(Entity.Article p_Article)
        {
            if (p_Article == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Article_Name=N'" + p_Article.Article_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Article_Content=N'" + p_Article.Article_Content + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Article_AddTime=N'" + p_Article.Article_AddTime.ToString() + "'";

            string o_Where = "Article_ID=" + p_Article.Article_ID;

            Execute_Update(g_TableName, o_FieldsValue, o_Where);                       
        }
    }
}
