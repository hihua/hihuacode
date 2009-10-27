using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_Question
    {
        public DataTable Query_AnswerInfo()
        {
            String Sql = "";
            Sql += "Select * From Question";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_AnswerInfo(int Question_ID)
        {
            String Sql = "";
            Sql += "Select * From Question Where Question_ID = " + Question_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public String Query_Question_Tail(int Question_ID)
        {
            String Sql = "";
            Sql += "Select * From Question Where Question_ID = " + Question_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            if (dt != null && dt.Rows.Count > 0)
                return dt.Rows[0]["Question_Tail"].ToString();
            else
                return "";
        }
    }
}
