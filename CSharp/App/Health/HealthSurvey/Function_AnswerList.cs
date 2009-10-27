using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_AnswerList
    {
        public DataTable Query_AnswerList(int AnswerInfo_ID)
        {
            String Sql = "";
            Sql += "Select * From AnswerList Where AnswerInfo_ID = " + AnswerInfo_ID.ToString() + " Order By QuestionList_ID";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public bool Insert_AnswerList(Class_AnswerList class_answerList)
        {
            if (class_answerList == null)
                return false;

            String Sql = "";
            Sql += "Insert Into AnswerList(AnswerInfo_ID,QuestionList_ID,QuestionSelect_ID) Values(" + class_answerList.AnswerInfo_ID.ToString() + "," + class_answerList.QuestionList_ID.ToString() + "," + class_answerList.QuestionSelect_ID.ToString() + ")";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return true;
        }

        public bool Update_AnswerList(Class_AnswerList class_answerList)
        {
            if (class_answerList == null)
                return false;

            String Sql = "";
            Sql += "Update AnswerList Set QuestionSelect_ID = " + class_answerList.QuestionSelect_ID.ToString() + " Where AnswerInfo_ID = " + class_answerList.AnswerInfo_ID.ToString() + " and QuestionList_ID = " + class_answerList.QuestionList_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return true;
        }
    }
}
