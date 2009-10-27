using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_QuestionList
    {
        public DataTable Query_QuestionList(int Question_ID)
        {
            String Sql = "";
            Sql += "Select t1.QuestionList_ID,t1.QuestionList_ListID,t1.QuestionList_Title,t1.QuestionList_Option,t1.QuestionList_TurnRow,t2.QuestionSelect_ID,t2.QuestionSelect_MainID,t2.QuestionSelect_ListID,t2.QuestionSelect_Text,t2.QuestionSelect_Score From QuestionList t1,QuestionSelect t2 Where t1.QuestionList_MainID = " + Question_ID.ToString() + " and t1.QuestionList_ID = t2.QuestionSelect_MainID Order By t1.QuestionList_ListID,t2.QuestionSelect_ListID";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }
    }
}
