using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_QuestionCase
    {
        public DataTable Query_QuestionCase(int Question_ID, int AnswerInfo_Score)
        {
            String Sql = "";
            Sql += "Select * From QuestionCase Where QuestionCase_MainID = " + Question_ID.ToString() + " and QuestionCase_MarkMin <= " + AnswerInfo_Score.ToString() + " and QuestionCase_MarkMax >= " + AnswerInfo_Score.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_QuestionCase(int Question_ID, String QuestionCase_Result)
        {
            QuestionCase_Result = CommonFunction.FilterString(QuestionCase_Result);

            String Sql = "";
            Sql += "Select * From QuestionCase Where QuestionCase_MainID = " + Question_ID.ToString() + " and QuestionCase_Result = '" + QuestionCase_Result + "'";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }
    }
}
