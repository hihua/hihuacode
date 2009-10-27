using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_AnswerInfo
    {
        public DataTable Query_AnswerInfo()
        {
            String Sql = "";
            Sql += "Select t1.AnswerInfo_ID,t2.ClientInfo_ID,t2.ClientInfo_Name,t3.Question_Title,t1.AddTime ";
            Sql += "From AnswerInfo t1,ClientInfo t2,Question t3 ";
            Sql += "Where t1.ClientInfo_ID = t2.ClientInfo_ID and t1.Question_ID = t3.Question_ID Order By t1.AddTime Desc";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_AnswerInfo(int Question_ID)
        {
            String Sql = "";
            Sql += "Select t1.AnswerInfo_ID,t2.ClientInfo_ID,t2.ClientInfo_Name,t3.Question_Title,t1.AddTime ";
            Sql += "From AnswerInfo t1,ClientInfo t2,Question t3 ";
            Sql += "Where t1.ClientInfo_ID = t2.ClientInfo_ID and t1.Question_ID = t3.Question_ID and t1.Question_ID = " + Question_ID.ToString() + " Order By t1.AddTime Desc";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_AnswerInfo(int ClientInfo_ID, int Question_ID)
        {
            String Sql = "";
            Sql += "Select * From AnswerInfo Where ClientInfo_ID = " + ClientInfo_ID.ToString() + " and Question_ID = " + Question_ID.ToString();
            
            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_AnswerInfo_List(int ClientInfo_ID)
        {
            String Sql = "";
            Sql += "Select * From AnswerInfo Where ClientInfo_ID = " + ClientInfo_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public int Query_AnswerInfo_Client(int ClientInfo_ID)
        {
            String Sql = "";
            Sql += "Select * From AnswerInfo Where ClientInfo_ID = " + ClientInfo_ID.ToString();

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            if (dt != null)
                return dt.Rows.Count;
            else
                return 0;
        }
                
        public int Query_AnswerInfo_ID()
        {
            int AnswerInfo_ID = 1;

            String Sql = "";
            Sql += "Select AnswerInfo_ID From AnswerInfo Order By AnswerInfo_ID Desc";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);
            if (dt.Rows.Count > 0)            
                AnswerInfo_ID = Convert.ToInt32(dt.Rows[0]["AnswerInfo_ID"].ToString()) + 1;            
            else
                AnswerInfo_ID = 1;

            return AnswerInfo_ID;
        }

        public bool Insert_AnswerInfo(Class_AnswerInfo class_answerInfo)
        {
            if (class_answerInfo == null)
                return false;

            class_answerInfo.Question_Tail = CommonFunction.FilterString(class_answerInfo.Question_Tail);

            String Sql = "";
            Sql += "Insert Into AnswerInfo(AnswerInfo_ID,ClientInfo_ID,Question_ID,Question_Tail,AddTime) ";
            Sql += "Values(" + class_answerInfo.AnswerInfo_ID + "," + class_answerInfo.ClientInfo_ID.ToString() + "," + class_answerInfo.Question_ID + ",'" + class_answerInfo.Question_Tail + "','" + DateTime.Now.ToString() + "')";

            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return true;
        }

        public bool Update_AnswerInfo(Class_AnswerInfo class_answerInfo)
        {
            if (class_answerInfo == null)
                return false;

            class_answerInfo.Question_Tail = CommonFunction.FilterString(class_answerInfo.Question_Tail);

            String Sql = "";
            Sql += "Update AnswerInfo Set Question_Tail = '" + class_answerInfo.Question_Tail + "' Where AnswerInfo_ID = " + class_answerInfo.AnswerInfo_ID.ToString();
            
            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return true;
        }
    }
}
