using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_QuestionManual
    {
        public DataTable Query_QuestionManual(int QuestionCase_ID)
        {
            String Sql = "";
            Sql += "Select * From QuestionManual Where QuestionCase_ID = " + QuestionCase_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }
    }
}
