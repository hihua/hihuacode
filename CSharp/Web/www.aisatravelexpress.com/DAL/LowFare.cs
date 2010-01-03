using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class LowFare : DALBase
    {
        private string g_TableName = "t_LowFare";
        private string g_TableFields = "LowFare_ID,LowFare_Type,LowFare_Detail_ID,LowFare_Adults,LowFare_Children,LowFare_Infants,LowFare_Passengers,LowFare_Airline,LowFare_Class,LowFare_Member_ID,LowFare_AdminUser_ID,LowFare_Status,LowFare_AddTime,LowFare_SubmitTime";
        private string g_TableOrderByFields = "LowFare_ID";

        public LowFare()
        {

        }

        public DataTable Select_LowFare(string p_Search_Content, int p_Search_Method, int p_LowFare_Status, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and LowFare_Type=" + p_Search_Content;
                        break;

                    case 2:
                        o_Where += " and LowFare_Adults=" + p_Search_Content;
                        break;

                    case 3:
                        o_Where += " and LowFare_Children=" + p_Search_Content;
                        break;

                    case 4:
                        o_Where += " and LowFare_Infants=" + p_Search_Content;
                        break;

                    case 5:
                        o_Where += " and LowFare_Passengers Like N'%" + p_Search_Content + "%'";
                        break;

                    case 6:
                        o_Where += " and LowFare_Airline Like N'%" + p_Search_Content + "%'";
                        break;

                    case 7:
                        o_Where += " and LowFare_Class Like N'%" + p_Search_Content + "%'";
                        break;

                    case 8:
                        o_Where += " and LowFare_Member_ID=" + p_Search_Content;
                        break;

                    case 9:
                        o_Where += " and LowFare_AdminUser_ID=" + p_Search_Content;
                        break;
                    
                    default:
                        break;
                }
            }

            switch (p_LowFare_Status)
            {
                case 1:
                    o_Where += " and LowFare_Status=0";
                    break;

                case 2:
                    o_Where += " and LowFare_Status=1";
                    break;

                default:
                    break;
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_LowFare(int p_LowFare_ID, ref int o_TotalCount, ref int o_TotalPage)
        {
            if (p_LowFare_ID <= 0)
                return null;

            string o_Where = "LowFare_ID=" + p_LowFare_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public void Insert_LowFare(Entity.LowFare o_LowFare)
        {
            if (o_LowFare == null)
                return;

            g_TableFields = "LowFare_Type,LowFare_Detail_ID,LowFare_Adults,LowFare_Children,LowFare_Infants,LowFare_Passengers,LowFare_Airline,LowFare_Class,LowFare_Member_ID,LowFare_AdminUser_ID,LowFare_Status,LowFare_AddTime,LowFare_SubmitTime";

            string o_FieldsValue = "";
            o_FieldsValue += o_LowFare.LowFare_Type.ToString();
            o_FieldsValue += ",";

            if (o_LowFare.LowFare_Detail_ID != null && o_LowFare.LowFare_Detail_ID.Count > 0)             
                o_FieldsValue += o_LowFare.LowFare_Detail_ID[0].LowFare_Detail_LowFare_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += o_LowFare.LowFare_Adults.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += o_LowFare.LowFare_Children.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += o_LowFare.LowFare_Infants.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + o_LowFare.LowFare_Passengers + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + o_LowFare.LowFare_Airline + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + o_LowFare.LowFare_Class + "'";
            o_FieldsValue += ",";

            if (o_LowFare.LowFare_Member_ID != null)                            
                o_FieldsValue += o_LowFare.LowFare_Member_ID.Member_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (o_LowFare.LowFare_AdminUser_ID != null)            
                o_FieldsValue += o_LowFare.LowFare_AdminUser_ID.AdminUser_ID.ToString();
            else
                o_FieldsValue += "0";
                        
            o_FieldsValue += ",";
            o_FieldsValue += o_LowFare.LowFare_Status.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "'" + o_LowFare.LowFare_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + o_LowFare.LowFare_SubmitTime + "'";
                        
            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_LowFare(Entity.LowFare o_LowFare)
        {
            if (o_LowFare == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "LowFare_Type=" + o_LowFare.LowFare_Type.ToString();
            o_FieldsValue += ",";

            if (o_LowFare.LowFare_Detail_ID != null && o_LowFare.LowFare_Detail_ID.Count > 0)
                o_FieldsValue += "LowFare_Detail_ID=" + o_LowFare.LowFare_Detail_ID[0].LowFare_Detail_LowFare_ID.ToString();
            else
                o_FieldsValue += "LowFare_Detail_ID=0";

            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Adults=" + o_LowFare.LowFare_Adults.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Children=" + o_LowFare.LowFare_Children.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Infants=" + o_LowFare.LowFare_Infants.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Passengers=N'" + o_LowFare.LowFare_Passengers + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Airline=N'" + o_LowFare.LowFare_Airline + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Class=N'" + o_LowFare.LowFare_Class + "'";
            o_FieldsValue += ",";

            if (o_LowFare.LowFare_Member_ID != null)
                o_FieldsValue += "LowFare_Member_ID=" + o_LowFare.LowFare_Member_ID.Member_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (o_LowFare.LowFare_AdminUser_ID != null)            
                o_FieldsValue += "LowFare_AdminUser_ID=" + o_LowFare.LowFare_AdminUser_ID.AdminUser_ID.ToString();            
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_Status=" + o_LowFare.LowFare_Status.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_AddTime='" + o_LowFare.LowFare_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "LowFare_SubmitTime=N'" + o_LowFare.LowFare_SubmitTime + "'"; 

            string o_Where = "LowFare_ID=" + o_LowFare.LowFare_ID.ToString();
            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_LowFare(Entity.LowFare o_LowFare)
        {
            if (o_LowFare == null)
                return;

            if (o_LowFare.LowFare_Detail_ID != null && o_LowFare.LowFare_Detail_ID.Count > 0)
            {
                LowFare_Detail d_LowFare_Detail = new LowFare_Detail();
                d_LowFare_Detail.Delete_LowFare_Detail_LowFare_ID(o_LowFare.LowFare_Detail_ID[0].LowFare_Detail_LowFare_ID);                
            }

            string o_Where = "LowFare_ID=" + o_LowFare.LowFare_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
