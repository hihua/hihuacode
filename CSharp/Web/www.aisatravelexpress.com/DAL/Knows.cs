using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Knows : DALBase
    {
        private string g_TableName = "t_Knows";
        private string g_TableFields = "Knows_ID,Knows_ClassID,Knows_LanguageID,Knows_Summary,Knows_Title,Knows_Content,Knows_AddTime";
        private string g_TableOrderByFields = "Knows_ID";

        public Knows()
        {

        }
                                
        public DataTable Select_Knows(int p_Knows_ClassID, int p_Knows_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            o_Where += " and Knows_ClassID=" + p_Knows_ClassID.ToString();

            if (p_Knows_LanguageID > 0)
                o_Where += " and Knows_LanguageID=" + p_Knows_LanguageID.ToString();
                       
            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and Knows_Title Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and Knows_Content Like N'%" + p_Search_Content + "%'";
                        break;

                    default:
                        break;
                }
            }

            if (p_Knows_ClassID == 2)
            {
                g_TableOrderByFields = "Knows_Summary";
                DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
                return o_DataTable;
            }
            else
            {
                DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
                return o_DataTable;
            }            
        }

        public DataTable Select_Knows(int p_Knows_LanguageID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            
            if (p_Knows_LanguageID > 0)
                o_Where += " and Knows_LanguageID=" + p_Knows_LanguageID.ToString();

            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and Knows_Title Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and Knows_Content Like N'%" + p_Search_Content + "%'";
                        break;

                    default:
                        break;
                }
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;            
        }

        public DataTable Select_Knows(int p_Knows_ID)
        {
            string o_Where = "Knows_ID=" + p_Knows_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_Knows(Entity.Knows p_Knows)
        {
            if (p_Knows == null)
                return;

            g_TableFields = "Knows_ClassID,Knows_LanguageID,Knows_Summary,Knows_Title,Knows_Content,Knows_AddTime";

            string o_FieldsValue = "";
            o_FieldsValue += p_Knows.Knows_ClassID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Knows.Knows_LanguageID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Knows.Knows_Summary + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Knows.Knows_Title + "'";
            o_FieldsValue += ",";            
            o_FieldsValue += "N'" + p_Knows.Knows_Content + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Knows.Knows_AddTime.ToString() + "'";

            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_Knows(Entity.Knows p_Knows)
        {
            if (p_Knows == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Knows_ClassID=" + p_Knows.Knows_ClassID.ToString();
            o_FieldsValue += ",";            
            o_FieldsValue += "Knows_LanguageID=" + p_Knows.Knows_LanguageID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Knows_Summary=N'" + p_Knows.Knows_Summary + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Knows_Title=N'" + p_Knows.Knows_Title + "'";
            o_FieldsValue += ",";            
            o_FieldsValue += "Knows_Content=N'" + p_Knows.Knows_Content + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Knows_AddTime=N'" + p_Knows.Knows_AddTime.ToString() + "'";

            string o_Where = "Knows_ID=" + p_Knows.Knows_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_Knows(int p_Knows_ID)
        {
            string o_Where = "Knows_ID=" + p_Knows_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}

