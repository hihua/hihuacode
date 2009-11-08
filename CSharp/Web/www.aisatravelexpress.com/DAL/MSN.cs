using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class MSN : DALBase
    {
        private string g_TableName = "t_MSN";
        private string g_TableFields = "MSN_ID,MSN_Name,MSN_Invitee";
        private string g_TableOrderByFields = "MSN_ID";

        public MSN()
        {

        }

        public DataTable Select_MSN()
        {
            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, -1, 0, "");
            return o_DataTable;
        }

        public void Insert_MSN(Entity.MSN p_MSN)
        {
            if (p_MSN == null)
                return;

            g_TableFields = "MSN_Name,MSN_Invitee";
                        
            string o_FieldsValue = "";
            o_FieldsValue += "N'" + p_MSN.MSN_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_MSN.MSN_Invitee + "'";
            
            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_MSN(Entity.MSN p_MSN)
        {
            if (p_MSN == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "MSN_Name=N'" + p_MSN.MSN_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "MSN_Invitee=N'" + p_MSN.MSN_Invitee + "'";
            
            string o_Where = "MSN_ID=" + p_MSN.MSN_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_MSN(int p_MSN)
        {
            string o_Where = "MSN_ID=" + p_MSN.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
