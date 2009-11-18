using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Travel : DALBase
    {
        private string g_TableName = "t_Travel";
        private string g_TableFields = "Travel_ID,Travel_LanguageID,Travel_TypeID,Travel_Code,Travel_Name,Travel_Price,Travel_Points,Travel_StartDate,Travel_EndDate,Travel_Views,Travel_Route,Travel_PreView1,Travel_PreView2,Travel_PreViews,Travel_StartAddr,Travel_EndAddr,Travel_AddTime";
        private string g_TableOrderByFields = "Travel_ID";

        public Travel()
        {

        }

        public DataTable Select_Travel(int p_Travel_LanguageID, int p_Travel_TypeID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            if (p_Travel_LanguageID > 0)
                o_Where += " and Travel_LanguageID=" + p_Travel_LanguageID.ToString();

            if (p_Travel_TypeID > 0)
                o_Where += " and Travel_TypeID=" + p_Travel_TypeID.ToString();
            
            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and Travel_Code Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and Travel_Name Like N'%" + p_Search_Content + "%'";
                        break;

                    case 3:
                        o_Where += " and Travel_Price Like N'%" + p_Search_Content + "%'";
                        break;

                    case 4:
                        o_Where += " and Travel_Points=" + p_Search_Content;
                        break;

                    case 5:
                        o_Where += " and Travel_StartDate > '" + p_Search_Content + "'";
                        break;

                    case 6:
                        o_Where += " and Travel_EndDate < '" + p_Search_Content + "'";
                        break;

                    case 7:
                        o_Where += " and Travel_StartAddr = '" + p_Search_Content + "'";
                        break;

                    case 8:
                        o_Where += " and Travel_EndAddr = '" + p_Search_Content + "'";
                        break;

                    default:
                        break;
                }
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_Travel(int p_Travel_ID)
        {
            string o_Where = "Travel_ID=" + p_Travel_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_Travel(Entity.Travel p_Travel)
        {
            if (p_Travel == null)
                return;

            g_TableFields = "Travel_LanguageID,Travel_TypeID,Travel_Code,Travel_Name,Travel_Price,Travel_Points,Travel_StartDate,Travel_EndDate,Travel_Views,Travel_Route,Travel_PreView1,Travel_PreView2,Travel_PreViews,Travel_StartAddr,Travel_EndAddr,Travel_AddTime";

            string o_FieldsValue = "";
            o_FieldsValue += p_Travel.Travel_LanguageID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Travel.Travel_TypeID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_Code + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_Price + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Travel.Travel_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_StartDate + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_EndDate + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_Views + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_Route + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_PreView1 + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_PreView2 + "'";
            o_FieldsValue += ",";

            string o_Travel_PreViews = "";
            if (p_Travel.Travel_PreViews != null)
            {
                foreach (string Travel_PreViews in p_Travel.Travel_PreViews)
                    o_Travel_PreViews += ";" + Travel_PreViews;
            }

            if (VerifyUtility.IsString_NotNull(o_Travel_PreViews))
            {
                if (o_Travel_PreViews.StartsWith(";"))
                    o_Travel_PreViews = o_Travel_PreViews.Remove(0, 1);
            }

            o_FieldsValue += "N'" + o_Travel_PreViews + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_StartAddr + "'";
            o_FieldsValue += ",";            
            o_FieldsValue += "N'" + p_Travel.Travel_EndAddr + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Travel.Travel_AddTime + "'";
            
            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_Travel(Entity.Travel p_Travel)
        {
            if (p_Travel == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Travel_LanguageID=" + p_Travel.Travel_LanguageID;
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_TypeID=" + p_Travel.Travel_TypeID.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_Code=N'" + p_Travel.Travel_Code + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_Name=N'" + p_Travel.Travel_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_Points=" + p_Travel.Travel_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_StartDate=N'" + p_Travel.Travel_StartDate + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_EndDate=N'" + p_Travel.Travel_EndDate + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_Views=N'" + p_Travel.Travel_Views + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_Route=N'" + p_Travel.Travel_Route + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_PreView1=N'" + p_Travel.Travel_PreView1 + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_PreView2=N'" + p_Travel.Travel_PreView2 + "'";
            o_FieldsValue += ",";

            string o_Travel_PreViews = "";
            if (p_Travel.Travel_PreViews != null)
            {
                foreach (string Travel_PreViews in p_Travel.Travel_PreViews)
                    o_Travel_PreViews += ";" + Travel_PreViews;
            }

            if (VerifyUtility.IsString_NotNull(o_Travel_PreViews))
            {
                if (o_Travel_PreViews.StartsWith(";"))
                    o_Travel_PreViews = o_Travel_PreViews.Remove(0, 1);
            }

            o_FieldsValue += "Travel_PreViews=N'" + o_Travel_PreViews + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_StartAddr=N'" + p_Travel.Travel_StartAddr + "'";            
            o_FieldsValue += ",";
            o_FieldsValue += "Travel_EndAddr=N'" + p_Travel.Travel_EndAddr + "'";

            string o_Where = "Travel_ID=" + p_Travel.Travel_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_Travel(int p_Travel_ID)
        {
            string o_Where = "Travel_ID=" + p_Travel_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
