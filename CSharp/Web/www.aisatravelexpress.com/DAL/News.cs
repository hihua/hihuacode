using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class News : DALBase
    {
        private string g_TableName = "t_News";
        private string g_TableFields = "News_ID,News_ClassID,News_LanguageID,News_Title,News_Intro,News_Content,News_AddTime";
        private string g_TableOrderByFields = "News_ID";

        public News()
        {

        }

        public DataTable Select_News(int p_News_ClassID, int p_News_LanguageID, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "News_ClassID=" + p_News_ClassID.ToString();
            if (p_News_LanguageID > 0)
                o_Where += " and News_LanguageID=" + p_News_LanguageID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where);
            return o_DataTable;
        }

        public DataTable Select_News(int p_News_ClassID, int p_News_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "News_ClassID=" + p_News_ClassID.ToString();
            if (p_News_LanguageID > 0)
                o_Where += " and News_LanguageID=" + p_News_LanguageID.ToString();

            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 0:
                        o_Where += " and (News_Title Like N'%" + p_Search_Content + "%' or News_Content Like N'%" + p_Search_Content + "%')";
                        break;

                    case 1:
                        o_Where += " and News_Title Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and News_Content Like N'%" + p_Search_Content + "%'";
                        break;
                }                
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_News(int p_News_ID)
        {
            string o_Where = "News_ID=" + p_News_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_News(Entity.News p_News)
        {
            if (p_News == null)
                return;

            g_TableFields = "News_ClassID,News_LanguageID,News_Title,News_Intro,News_Content,News_AddTime";

            string o_FieldsValue = "";
            o_FieldsValue += p_News.News_ClassID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_News.News_LanguageID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_News.News_Title + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_News.News_Intro + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_News.News_Content + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_News.News_AddTime.ToString() + "'";
            
            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_News(Entity.News p_News)
        {
            if (p_News == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "News_ClassID=" + p_News.News_ClassID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "News_LanguageID=" + p_News.News_LanguageID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "News_Title=N'" + p_News.News_Title + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "News_Intro=N'" + p_News.News_Intro + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "News_Content=N'" + p_News.News_Content + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "News_AddTime=N'" + p_News.News_AddTime.ToString() + "'";

            string o_Where = "News_ID=" + p_News.News_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_News(int p_News_ID)
        {
            string o_Where = "News_ID=" + p_News_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
