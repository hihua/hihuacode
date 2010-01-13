using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Consumption : DALBase
    {
        private string g_TableName = "t_Consumption";
        private string g_TableFields = "Consumption_ID,Consumption_Serial,Consumption_Type,Consumption_Src,Consumption_Dest,Consumption_Price,Consumption_DePrice,Consumption_Points,Consumption_Commission,Consumption_Date,Consumption_Org_Member_ID,Consumption_Com_Member_ID,Consumption_Admin_ID,Consumption_AddTime,Consumption_Remark";
        private string g_TableOrderByFields = "Consumption_ID";

        public Consumption()
        {

        }

        public DataTable Select_Consumption(string p_Search_Content, int p_Search_Method, int p_Search_Year, int p_Search_Month, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            if (p_Search_Year > 0)
                o_Where += " and Year(Consumption_AddTime) = " + p_Search_Year.ToString();

            if (p_Search_Month > 0)
                o_Where += " and Month(Consumption_AddTime) = " + p_Search_Month.ToString();

            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and Consumption_Serial Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and Consumption_Type = " + p_Search_Content;
                        break;

                    case 3:
                        o_Where += " and Consumption_Src = N'%" + p_Search_Content + "%'";
                        break;

                    case 4:
                        o_Where += " and Consumption_Dest = N'%" + p_Search_Content + "%'";
                        break;

                    case 5:
                        o_Where += " and Consumption_Price = " + p_Search_Content;
                        break;

                    case 6:
                        o_Where += " and Consumption_DePrice = " + p_Search_Content;
                        break;

                    case 7:
                        o_Where += " and Consumption_Points = " + p_Search_Content;
                        break;

                    case 8:
                        o_Where += " and Consumption_Points = " + p_Search_Content;
                        break;

                    case 9:
                        o_Where += " and Consumption_Org_Member_ID = " + p_Search_Content;
                        break;

                    case 10:
                        o_Where += " and Consumption_Com_Member_ID = " + p_Search_Content;
                        break;

                    case 11:
                        o_Where += " and Consumption_Admin_ID = " + p_Search_Content;
                        break;

                    default:
                        break;
                }
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_Consumption(int p_Consumption_ID, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "Consumption_ID=" + p_Consumption_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public void Insert_Consumption(Entity.Consumption p_Consumption)
        {
            if (p_Consumption == null)
                return;

            g_TableFields = "Consumption_Serial,Consumption_Type,Consumption_Src,Consumption_Dest,Consumption_Price,Consumption_DePrice,Consumption_Points,Consumption_Commission,Consumption_Date,Consumption_Org_Member_ID,Consumption_Com_Member_ID,Consumption_Admin_ID,Consumption_AddTime,Consumption_Remark";

            string o_FieldsValue = "";
            o_FieldsValue += "N'" + p_Consumption.Consumption_Serial + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Consumption.Consumption_Type.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Consumption.Consumption_Src + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Consumption.Consumption_Dest + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Consumption.Consumption_Price.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Consumption.Consumption_DePrice.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Consumption.Consumption_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Consumption.Consumption_Commission.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Consumption.Consumption_Date.ToString() + "'";
            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Org_Member_ID != null)
                o_FieldsValue += p_Consumption.Consumption_Org_Member_ID.Member_ID;
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Com_Member_ID != null)
                o_FieldsValue += p_Consumption.Consumption_Com_Member_ID.Member_ID;
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Admin_ID != null)
                o_FieldsValue += p_Consumption.Consumption_Admin_ID.AdminUser_ID;
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Consumption.Consumption_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Consumption.Consumption_Remark + "'";

            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_Consumption(Entity.Consumption p_Consumption)
        {
            if (p_Consumption == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Consumption_Serial=N'" + p_Consumption.Consumption_Serial.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Type=" + p_Consumption.Consumption_Type.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Src=N'" + p_Consumption.Consumption_Src + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Dest=N'" + p_Consumption.Consumption_Dest + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Price=" + p_Consumption.Consumption_Price.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_DePrice=" + p_Consumption.Consumption_DePrice.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Points=" + p_Consumption.Consumption_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Commission=" + p_Consumption.Consumption_Commission.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Date=N'" + p_Consumption.Consumption_Date.ToString() + "'";
            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Org_Member_ID != null)
                o_FieldsValue += "Consumption_Org_Member_ID=" + p_Consumption.Consumption_Org_Member_ID.Member_ID.ToString();
            else
                o_FieldsValue += "0";
            
            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Com_Member_ID != null)
                o_FieldsValue += "Consumption_Com_Member_ID=" + p_Consumption.Consumption_Com_Member_ID.Member_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (p_Consumption.Consumption_Admin_ID != null)
                o_FieldsValue += "Consumption_Admin_ID=" + p_Consumption.Consumption_Admin_ID.AdminUser_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_AddTime=N'" + p_Consumption.Consumption_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Consumption_Remark=N'" + p_Consumption.Consumption_Remark + "'";

            Execute_Update(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Delete_Consumption(int p_Consumption_ID)
        {
            string o_Where = "Consumption_ID=" + p_Consumption_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
