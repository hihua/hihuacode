using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace HealthSurvey
{
    public partial class Control_QuestionCase2 : UserControl
    {
        private int Question_ID = 0;
        private Hashtable list_answerInfo_Score = null;
        private Hashtable list_answerInfo_Text = null;
        private Hashtable list_answerScore = new Hashtable();
        private Hashtable list_answerResult = new Hashtable();
        private String QuestionCase_Manual = "";
        private DataTable QuestionCase_Description = null;
        private DataTable chart_dataTable = null;
        private String[] chart_dataX = new string[] { "偏瘦", "蒜辣椒", "荔枝榴莲", "烟酒", "烧烤煎炸", "熬夜", "电视电脑", "压力" };

        public Control_QuestionCase2(int Q_ID, Hashtable A_List_Score, Hashtable A_List_Text)
        {
            Question_ID = Q_ID;
            list_answerInfo_Score = A_List_Score;
            list_answerInfo_Text = A_List_Text;

            InitializeComponent();

            label1.Text = "在上火方面，您目前的健康状况是：";
            label2.Text = "上火指数";

            float X = Convert.ToSingle(pictureBox1.Width) / Convert.ToSingle(100);
            float X0 = X * 0;
            float X10 = X * 10;
            float X20 = X * 20;
            float X40 = X * 40;
            float X50 = X * 50;
            float X60 = X * 60;
            float X100 = X * 100;

            label3.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label3.Width) / Convert.ToSingle(2)), label3.Location.Y);
            label4.Location = new Point(Convert.ToInt32(X10) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label4.Width) / Convert.ToSingle(2)), label4.Location.Y);
            label5.Location = new Point(Convert.ToInt32(X20) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label5.Width) / Convert.ToSingle(2)), label5.Location.Y);
            label6.Location = new Point(Convert.ToInt32(X50) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label6.Width) / Convert.ToSingle(2)), label6.Location.Y);
            label7.Location = new Point(Convert.ToInt32(X100) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label7.Width) / Convert.ToSingle(2)), label7.Location.Y);

            label8.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label8.Width) / Convert.ToSingle(2)), label8.Location.Y);
            label9.Location = new Point(Convert.ToInt32(X10) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label9.Width) / Convert.ToSingle(2)), label9.Location.Y);
            label10.Location = new Point(Convert.ToInt32(X20) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label10.Width) / Convert.ToSingle(2)), label10.Location.Y);
            label11.Location = new Point(Convert.ToInt32(X40) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label11.Width) / Convert.ToSingle(2)), label11.Location.Y);
            label12.Location = new Point(Convert.ToInt32(X60) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label12.Width) / Convert.ToSingle(2)), label12.Location.Y);

            pictureBox2.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - pictureBox2.Width / 2, pictureBox2.Location.Y);

            Question_Score();
            Question_Result();
        }

        private void Question_Score()
        {
            int AnswerInfo_Score = 0;
            String AnswerInfo_Name = "";

            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                //上火症状
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "上火症状";
                for (int i = 1; i <= 20; i++)
                {
                    if (list_answerInfo_Score.ContainsKey(i))
                        AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[i]);
                }

                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);

                //危险因数
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "危险因数";
                chart_dataTable = new DataTable();
                chart_dataTable.Columns.Add(new DataColumn("DataX"));
                chart_dataTable.Columns.Add(new DataColumn("DataY", typeof(int)));
                int j = 0;
                for (int i = 21; i <= 32; i++)
                {
                    if (list_answerInfo_Score.ContainsKey(i))
                    {
                        if (j < chart_dataX.Length)
                        {
                            DataRow dataRow = chart_dataTable.NewRow();
                            dataRow["DataX"] = chart_dataX[j];
                            dataRow["DataY"] = Convert.ToInt32(list_answerInfo_Score[i]);
                            chart_dataTable.Rows.Add(dataRow);
                            j++;
                        }

                        AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[i]);
                    }
                }

                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);
            }
        }

        private void Question_Result()
        {
            String AnswerInfo_Name = "";
            int AnswerInfo_ScoreTotal = 0;

            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                AnswerInfo_Name = "上火症状";
                if (list_answerScore.ContainsKey(AnswerInfo_Name))
                {
                    AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                    if (AnswerInfo_ScoreTotal == 0)
                        list_answerResult[AnswerInfo_Name] = "健康";

                    if (AnswerInfo_ScoreTotal >= 1 && AnswerInfo_ScoreTotal <= 20)
                        list_answerResult[AnswerInfo_Name] = "亚健康";

                    if (AnswerInfo_ScoreTotal >= 21 && AnswerInfo_ScoreTotal <= 40)
                        list_answerResult[AnswerInfo_Name] = "轻度";

                    if (AnswerInfo_ScoreTotal >= 41 && AnswerInfo_ScoreTotal <= 60)
                        list_answerResult[AnswerInfo_Name] = "中度";

                    if (AnswerInfo_ScoreTotal >= 61)
                        list_answerResult[AnswerInfo_Name] = "重度";
                }
                else
                    list_answerResult[AnswerInfo_Name] = "健康";
            }

            if (list_answerInfo_Text != null && list_answerInfo_Text.Count > 0)
            {
                AnswerInfo_Name = "危险因数";
                int p = 0, q = 0;
                for (int i = 21; i <= 32; i++)
                {
                    if (list_answerInfo_Text.ContainsKey(i))
                    {
                        String AnswerInfo_Text = (String)list_answerInfo_Text[i];
                        if (AnswerInfo_Text == "中度")
                            p++;

                        if (AnswerInfo_Text == "重度")
                            q++;
                    }
                }

                if (p >= 3)
                    list_answerResult[AnswerInfo_Name] = "中度";

                if (q >= 3)
                    list_answerResult[AnswerInfo_Name] = "重度";

                if (!list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    if (list_answerScore.ContainsKey(AnswerInfo_Name))
                    {
                        AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                        Function_QuestionCase function_questionCase = new Function_QuestionCase();
                        DataTable dt = function_questionCase.Query_QuestionCase(Question_ID, AnswerInfo_ScoreTotal);
                        if (dt != null && dt.Rows.Count > 0)
                        {
                            int QuestionCase_ID = Convert.ToInt32(dt.Rows[0]["QuestionCase_ID"].ToString());
                            Function_QuestionManual function_questionManual = new Function_QuestionManual();
                            dt = function_questionManual.Query_QuestionManual(QuestionCase_ID);
                            if (dt != null && dt.Rows.Count > 0)
                                QuestionCase_Manual = dt.Rows[0]["QuestionManual_Content"].ToString();

                            Function_QuestionDescription function_questionDescription = new Function_QuestionDescription();
                            dt = function_questionDescription.Query_QuestionDescription(QuestionCase_ID);
                            if (dt != null && dt.Rows.Count > 0)
                                QuestionCase_Description = dt;
                        }
                    }
                    else
                    {
                        list_answerResult[AnswerInfo_Name] = "健康";
                        Function_QuestionCase function_questionCase = new Function_QuestionCase();
                        DataTable dt = function_questionCase.Query_QuestionCase(Question_ID, (String)list_answerResult[AnswerInfo_Name]);
                        if (dt != null && dt.Rows.Count > 0)
                        {
                            int QuestionCase_ID = Convert.ToInt32(dt.Rows[0]["QuestionCase_ID"].ToString());
                            Function_QuestionManual function_questionManual = new Function_QuestionManual();
                            dt = function_questionManual.Query_QuestionManual(QuestionCase_ID);
                            if (dt != null && dt.Rows.Count > 0)
                                QuestionCase_Manual = dt.Rows[0]["QuestionManual_Content"].ToString();

                            Function_QuestionDescription function_questionDescription = new Function_QuestionDescription();
                            dt = function_questionDescription.Query_QuestionDescription(QuestionCase_ID);
                            if (dt != null && dt.Rows.Count > 0)
                                QuestionCase_Description = dt;
                        }
                    }
                }
                else
                {
                    Function_QuestionCase function_questionCase = new Function_QuestionCase();
                    DataTable dt = function_questionCase.Query_QuestionCase(Question_ID, (String)list_answerResult[AnswerInfo_Name]);
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        int QuestionCase_ID = Convert.ToInt32(dt.Rows[0]["QuestionCase_ID"].ToString());
                        Function_QuestionManual function_questionManual = new Function_QuestionManual();
                        dt = function_questionManual.Query_QuestionManual(QuestionCase_ID);
                        if (dt != null && dt.Rows.Count > 0)
                            QuestionCase_Manual = dt.Rows[0]["QuestionManual_Content"].ToString();

                        Function_QuestionDescription function_questionDescription = new Function_QuestionDescription();
                        dt = function_questionDescription.Query_QuestionDescription(QuestionCase_ID);
                        if (dt != null && dt.Rows.Count > 0)
                            QuestionCase_Description = dt;
                    }
                }
            }
        }

        public void Show_Result()
        {
            String AnswerInfo_Name = "";

            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                AnswerInfo_Name = "上火症状";
                if (list_answerScore.ContainsKey(AnswerInfo_Name) && list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                    String AnswerInfo_ResultTotal = Convert.ToString(list_answerResult[AnswerInfo_Name]);
                    label2.Text += AnswerInfo_ScoreTotal.ToString();
                    pictureBox2.Location = new Point(pictureBox1.Location.X + Convert.ToInt32(Convert.ToSingle(pictureBox1.Width) / Convert.ToSingle(100) * AnswerInfo_ScoreTotal - Convert.ToSingle(pictureBox2.Width) / Convert.ToSingle(2)), pictureBox2.Location.Y);
                }

                AnswerInfo_Name = "危险因数";
                if (!String.IsNullOrEmpty(QuestionCase_Manual))
                    Label_QuestionCase_Manual.Text = QuestionCase_Manual;

                if (QuestionCase_Description != null && QuestionCase_Description.Rows.Count > 0)
                {
                    for (int i = 0; i < QuestionCase_Description.Rows.Count; i++)
                    {
                        Label_QuestionCase_Description.Text += QuestionCase_Description.Rows[i]["QuestionDescription_ListID"].ToString() + "." + QuestionCase_Description.Rows[i]["QuestionDescription_Content"].ToString() + "\r\n";
                    }
                }                                
                
                RichTextBox_QuestionCase_Manual.Text = Label_QuestionCase_Manual.Text;                                 
                RichTextBox_QuestionCase_Description.Text = Label_QuestionCase_Description.Text;
                
                Label_QuestionCase_Manual.Visible = false;
                Label_QuestionCase_Description.Visible = false;

                if (chart_dataTable != null && chart_dataTable.Rows.Count > 0)
                {
                    Class_Chart class_Chart = new Class_Chart(chart_dataTable, "DataX", "DataY", Color.BlueViolet, pictureBox5.Width, pictureBox5.Height);
                    Bitmap bitMap = class_Chart.Show_MSChart();

                    if (bitMap != null)
                    {
                        Image image = Image.FromHbitmap(bitMap.GetHbitmap());
                        pictureBox5.Image = image;
                        pictureBox5.Show();
                        pictureBox5.Refresh();
                    }
                }
            }
        }

        private void RichTextBox_QuestionCase_Description_ContentsResized(object sender, ContentsResizedEventArgs e)
        {
            ((RichTextBox)sender).Height = e.NewRectangle.Height + 5;
        }

        private void RichTextBox_QuestionCase_Manual_ContentsResized(object sender, ContentsResizedEventArgs e)
        {
            ((RichTextBox)sender).Height = e.NewRectangle.Height + 5;
        }
    }
}
