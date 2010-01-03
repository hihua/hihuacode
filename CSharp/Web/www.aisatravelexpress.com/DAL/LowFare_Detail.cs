using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class LowFare_Detail : DALBase
    {
        private string g_TableName = "t_LowFare_Detail";
        private string g_TableFields = "LowFare_Detail_ID,LowFare_Detail_LowFare_ID,LowFare_Detail_From,LowFare_Detail_To,LowFare_Detail_Departing,LowFare_Detail_Time1,LowFare_Flexibility1,LowFare_Detail_Returning,LowFare_Detail_Time2,LowFare_Flexibility2";
        private string g_TableOrderByFields = "LowFare_Detail_ID";

        public LowFare_Detail()
        {

        }

        public DataTable Select_LowFare_Detail(int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 1, 1, "");
            return o_DataTable;
        }

        public DataTable Select_LowFare_Detail(int p_LowFare_Detail_ID, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            if (p_LowFare_Detail_ID <= 0)
                return null;

            string o_Where = "LowFare_Detail_ID=" + p_LowFare_Detail_ID.ToString();
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, -1, 0, o_Where);
            return o_DataTable;
        }

        public DataTable Select_LowFare_Detail_LowFare_ID(int p_LowFare_Detail_LowFare_ID, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            if (p_LowFare_Detail_LowFare_ID <= 0)
                return null;

            string o_Where = "LowFare_Detail_LowFare_ID=" + p_LowFare_Detail_LowFare_ID.ToString();
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, -1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_LowFare_Detail(List<Entity.LowFare_Detail> o_LowFare_Detail)
        {
            if (o_LowFare_Detail == null)
                return;

            g_TableFields = "LowFare_Detail_LowFare_ID,LowFare_Detail_From,LowFare_Detail_To,LowFare_Detail_Departing,LowFare_Detail_Time1,LowFare_Flexibility1,LowFare_Detail_Returning,LowFare_Detail_Time2,LowFare_Flexibility2";

            foreach (Entity.LowFare_Detail e_LowFare_Detail in o_LowFare_Detail)
            {                
                string o_FieldsValue = "";                
                o_FieldsValue += e_LowFare_Detail.LowFare_Detail_LowFare_ID.ToString();
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_From + "'";
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_To + "'";
                o_FieldsValue += ",";

                if (e_LowFare_Detail.LowFare_Detail_Departing != null)
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Departing.ToString() + "'";
                else
                    o_FieldsValue += "N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Detail_Time1))                
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Time1 + "'";
                else
                    o_FieldsValue += "N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Flexibility1))
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Flexibility1 + "'";
                else
                    o_FieldsValue += "N''";

                o_FieldsValue += ",";

                if (e_LowFare_Detail.LowFare_Detail_Returning != null)                 
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Returning.ToString() + "'";
                else
                    o_FieldsValue += "N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Detail_Time2))
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Time2 + "'";                
                else
                    o_FieldsValue += "N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Flexibility2))
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Flexibility2 + "'";
                else
                    o_FieldsValue += "N''";

                Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
            }
        }

        public void Update_LowFare_Detail(List<Entity.LowFare_Detail> o_LowFare_Detail)
        {
            if (o_LowFare_Detail == null)
                return;

            foreach (Entity.LowFare_Detail e_LowFare_Detail in o_LowFare_Detail)
            {
                string o_FieldsValue = "";
                o_FieldsValue += "LowFare_Detail_LowFare_ID=" + e_LowFare_Detail.LowFare_Detail_LowFare_ID.ToString();                
                o_FieldsValue += ",";
                o_FieldsValue += "LowFare_Detail_From=N'" + e_LowFare_Detail.LowFare_Detail_From + "'";
                o_FieldsValue += ",";
                o_FieldsValue += "LowFare_Detail_To=N'" + e_LowFare_Detail.LowFare_Detail_To + "'";
                o_FieldsValue += ",";

                if (e_LowFare_Detail.LowFare_Detail_Departing != null)
                    o_FieldsValue += "LowFare_Detail_Departing=N'" + e_LowFare_Detail.LowFare_Detail_Departing.ToString() + "'";
                else
                    o_FieldsValue += "LowFare_Detail_Departing=N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Detail_Time1))
                    o_FieldsValue += "LowFare_Detail_Time1=N'" + e_LowFare_Detail.LowFare_Detail_Time1 + "'";
                else
                    o_FieldsValue += "LowFare_Detail_Time1=N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Flexibility1))
                    o_FieldsValue += "LowFare_Flexibility1=N'" + e_LowFare_Detail.LowFare_Flexibility1 + "'";
                else
                    o_FieldsValue += "LowFare_Flexibility1=N''";

                o_FieldsValue += ",";

                if (e_LowFare_Detail.LowFare_Detail_Returning != null)
                    o_FieldsValue += "LowFare_Detail_Returning=N'" + e_LowFare_Detail.LowFare_Detail_Returning.ToString() + "'";
                else
                    o_FieldsValue += "LowFare_Detail_Returning=N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Detail_Time2))
                    o_FieldsValue += "LowFare_Detail_Time2=N'" + e_LowFare_Detail.LowFare_Detail_Time2 + "'";
                else
                    o_FieldsValue += "LowFare_Detail_Time2=N''";

                o_FieldsValue += ",";

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Flexibility2))
                    o_FieldsValue += "LowFare_Flexibility2=N'" + e_LowFare_Detail.LowFare_Flexibility2 + "'";
                else
                    o_FieldsValue += "LowFare_Flexibility2=N''";

                string o_Where = "LowFare_Detail_ID=" + e_LowFare_Detail.LowFare_Detail_ID.ToString();
                Execute_Update(g_TableName, o_FieldsValue, o_Where);
            }
        }

        public void Delete_LowFare_Detail(int p_LowFare_Detail_ID)
        {           
            string o_Where = "LowFare_Detail_ID=" + p_LowFare_Detail_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }

        public void Delete_LowFare_Detail_LowFare_ID(int p_LowFare_Detail_LowFare_ID)
        {
            string o_Where = "LowFare_Detail_LowFare_ID=" + p_LowFare_Detail_LowFare_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
