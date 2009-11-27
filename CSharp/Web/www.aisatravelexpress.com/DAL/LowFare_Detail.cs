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
        private string g_TableFields = "LowFare_Detail_ID,LowFare_Detail_From,LowFare_Detail_To,LowFare_Detail_Departing,LowFare_Detail_Time1,LowFare_Detail_Returning,LowFare_Detail_Time2";
        private string g_TableOrderByFields = "LowFare_Detail_ID";

        public LowFare_Detail()
        {

        }

        public DataTable Select_LowFare_Detail(int LowFare_Detail_ID)
        {
            if (LowFare_Detail_ID <= 0)
                return null;

            string o_Where = "LowFare_Detail_ID=" + LowFare_Detail_ID.ToString();
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, -1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_LowFare_Detail(List<Entity.LowFare_Detail> o_LowFare_Detail)
        {
            if (o_LowFare_Detail == null)
                return;

            foreach (Entity.LowFare_Detail e_LowFare_Detail in o_LowFare_Detail)
            {
                g_TableFields = "LowFare_Detail_ID,LowFare_Detail_From,LowFare_Detail_To,LowFare_Detail_Departing,LowFare_Detail_Time1,LowFare_Detail_Returning,LowFare_Detail_Time2";

                string o_FieldsValue = "";
                o_FieldsValue += e_LowFare_Detail.LowFare_Detail_ID.ToString();
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_From + "'";
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_To + "'";
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Departing.ToString() + "'";
                o_FieldsValue += ",";
                o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Time1 + "'";

                if (e_LowFare_Detail.LowFare_Detail_Returning != null)
                {
                    o_FieldsValue += ",";
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Returning.ToString() + "'";
                }
                else
                {
                    o_FieldsValue += ",";
                    o_FieldsValue += "N''";
                }

                if (VerifyUtility.IsString_NotNull(e_LowFare_Detail.LowFare_Detail_Time2))
                {
                    o_FieldsValue += ",";
                    o_FieldsValue += "N'" + e_LowFare_Detail.LowFare_Detail_Time2 + "'";
                }
                else
                {
                    o_FieldsValue += ",";
                    o_FieldsValue += "N''";
                }

                Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
            }
        }
    }
}
