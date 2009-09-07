using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_QuestionDescription
    {
        public DataTable Query_QuestionDescription(int QuestionCase_ID)
        {
            String Sql = "";
            Sql += "Select * From QuestionDescription Where QuestionCase_ID = " + QuestionCase_ID.ToString() + " Order By QuestionDescription_ListID";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }
    }
}
