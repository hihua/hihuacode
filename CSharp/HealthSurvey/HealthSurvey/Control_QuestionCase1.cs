using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Windows.Forms;

namespace HealthSurvey
{
    public partial class Control_QuestionCase1 : UserControl
    {
        public int QuestionCase_ID = 0;

        private int Question_ID = 0;        
        private Hashtable list_answerInfo_Score = null;
        private Hashtable list_answerScore = new Hashtable();
        private Hashtable list_answerResult = new Hashtable();
        private String QuestionCase_Manual = "";
        private DataTable QuestionCase_Description = null;
        private DataTable chart_dataTable = null;
        private String[] chart_dataX = new string[] { "血压", "血脂", "血糖", "血液粘度", "抽烟", "饮酒", "口味重", "油腻食物", "家族遗传", "工作时间", "压力大", "电视电脑" };

        public Control_QuestionCase1(int Q_ID, Hashtable A_List_Score)
        {
            Question_ID = Q_ID;
            list_answerInfo_Score = A_List_Score;

            InitializeComponent();

            label1.Text = "在心脑血管方面，您目前的健康状况是：";
                        
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
            //label9.Location = new Point(Convert.ToInt32(X10) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label9.Width) / Convert.ToSingle(2)), label9.Location.Y);
            //label10.Location = new Point(Convert.ToInt32(X20) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label10.Width) / Convert.ToSingle(2)), label10.Location.Y);
            //label11.Location = new Point(Convert.ToInt32(X40) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label11.Width) / Convert.ToSingle(2)), label11.Location.Y);
            //label12.Location = new Point(Convert.ToInt32(X60) + pictureBox1.Location.X - Convert.ToInt32(Convert.ToSingle(label12.Width) / Convert.ToSingle(2)), label12.Location.Y);

            pictureBox2.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - pictureBox2.Width / 2, pictureBox2.Location.Y);
            pictureBox3.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - pictureBox3.Width / 2, pictureBox3.Location.Y);
            pictureBox4.Location = new Point(Convert.ToInt32(X0) + pictureBox1.Location.X - pictureBox4.Width / 2, pictureBox4.Location.Y);

            Question_Score();
            Question_Result();
        }

        private void Question_Score()
        {
            int AnswerInfo_Score = 0;
            String AnswerInfo_Name = "";

            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                //心血管
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "心血管";
                for (int i = 1; i <= 9; i++)
                {
                    if (i != 3)
                    {
                        if (list_answerInfo_Score.ContainsKey(i))
                            AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[i]);
                    }
                }
                                
                //if (list_answerInfo_Score.ContainsKey(23))
                //    AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[23]);
                                
                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);

                //脑血管
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "脑血管";
                for (int i = 9; i <= 22; i++)
                {
                    if (list_answerInfo_Score.ContainsKey(i))
                        AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[i]);
                }

                //if (list_answerInfo_Score.ContainsKey(24))
                //    AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[24]);

                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);

                //老年痴呆
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "老年痴呆";
                if (list_answerInfo_Score.ContainsKey(13))
                    AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[13]);

                if (list_answerInfo_Score.ContainsKey(14))
                    AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[14]);

                //if (list_answerInfo_Score.ContainsKey(25))
                //    AnswerInfo_Score += Convert.ToInt32(list_answerInfo_Score[25]);

                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);

                //保健方案
                AnswerInfo_Score = 0;
                AnswerInfo_Name = "保健方案";
                chart_dataTable = new DataTable();
                chart_dataTable.Columns.Add(new DataColumn("DataX"));
                chart_dataTable.Columns.Add(new DataColumn("DataY", typeof(int)));
                int j = 0;
                float ClientInfo_Weight = 0;
                float ClientInfo_Height = 0;
                float ClientInfo_Weight_Height = 0;
                for (int i = 27; i <= 42; i++)
                {
                    if (list_answerInfo_Score.ContainsKey(i))
                    {
                        if (i == 40 && CommonFunction.IsNumber(list_answerInfo_Score[i].ToString(), 2))
                        {
                            ClientInfo_Weight = Convert.ToSingle(list_answerInfo_Score[i]);
                        }

                        if (i == 41 && CommonFunction.IsNumber(list_answerInfo_Score[i].ToString(), 2))
                        {
                            ClientInfo_Height = Convert.ToSingle(list_answerInfo_Score[i]);
                        }

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

                if (ClientInfo_Weight > 0 && ClientInfo_Height > 0)
                    ClientInfo_Weight_Height = ClientInfo_Weight / ClientInfo_Height;

                if (ClientInfo_Weight_Height < 18)
                    AnswerInfo_Score += 0;

                if (ClientInfo_Weight_Height >= 18 && ClientInfo_Weight_Height <= 25)
                    AnswerInfo_Score += 20;

                if (ClientInfo_Weight_Height > 25)
                    AnswerInfo_Score += 0;

                list_answerScore.Add(AnswerInfo_Name, AnswerInfo_Score);
            }
        }

        private void Question_Result()
        {
            String AnswerInfo_Name = "";            
            int QuestionSelect_SpScore = 0;
            int AnswerInfo_Max = 0;
            Hashtable AnswerInfo_Result_Table = new Hashtable();
            AnswerInfo_Result_Table.Add("健康", 0);
            AnswerInfo_Result_Table.Add("亚健康", 1);
            AnswerInfo_Result_Table.Add("轻度", 2);
            AnswerInfo_Result_Table.Add("中度", 3);
            AnswerInfo_Result_Table.Add("重度", 4);

            String[] AnswerInfo_Result_Array = new String[5];
            AnswerInfo_Result_Array[0] = "健康";
            AnswerInfo_Result_Array[1] = "亚健康";
            AnswerInfo_Result_Array[2] = "轻度";
            AnswerInfo_Result_Array[3] = "中度";
            AnswerInfo_Result_Array[4] = "重度";
            
            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                AnswerInfo_Name = "心血管";
                if (list_answerInfo_Score.ContainsKey(3))
                {
                    int QuestionSelect_Score = Convert.ToInt32(list_answerInfo_Score[3]);
                    if (QuestionSelect_Score >= 60)
                        list_answerResult[AnswerInfo_Name] = "重度";

                    if (QuestionSelect_Score > QuestionSelect_SpScore)
                        QuestionSelect_SpScore = QuestionSelect_Score; 
                }

                if (list_answerInfo_Score.ContainsKey(23))
                {
                    int QuestionSelect_Score = Convert.ToInt32(list_answerInfo_Score[23]);
                    if (QuestionSelect_Score >= 60)
                        list_answerResult[AnswerInfo_Name] = "重度";

                    if (QuestionSelect_Score > QuestionSelect_SpScore)
                        QuestionSelect_SpScore = QuestionSelect_Score;
                }

                if (!list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    if (list_answerScore.ContainsKey(AnswerInfo_Name))
                    {
                        int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                        if (AnswerInfo_ScoreTotal >= 0 && AnswerInfo_ScoreTotal <= 10)
                            list_answerResult[AnswerInfo_Name] = "健康";

                        if (AnswerInfo_ScoreTotal >= 11 && AnswerInfo_ScoreTotal <= 19)
                            list_answerResult[AnswerInfo_Name] = "亚健康";

                        if (AnswerInfo_ScoreTotal >= 20 && AnswerInfo_ScoreTotal <= 39)
                            list_answerResult[AnswerInfo_Name] = "轻度";

                        if (AnswerInfo_ScoreTotal >= 40 && AnswerInfo_ScoreTotal <= 79)
                            list_answerResult[AnswerInfo_Name] = "中度";

                        if (AnswerInfo_ScoreTotal >= 80)
                            list_answerResult[AnswerInfo_Name] = "重度";
                    }
                    else
                        list_answerResult[AnswerInfo_Name] = "健康";

                    if (list_answerResult[AnswerInfo_Name].ToString() != "重度")
                    {
                        if (QuestionSelect_SpScore >= 40)
                            list_answerResult[AnswerInfo_Name] = "中度";

                        if (QuestionSelect_SpScore >= 20 && QuestionSelect_SpScore <= 39 && (list_answerResult[AnswerInfo_Name].ToString() == "健康" || list_answerResult[AnswerInfo_Name].ToString() == "亚健康"))
                            list_answerResult[AnswerInfo_Name] = "轻度";
                    }                    
                }

                if (AnswerInfo_Max < Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]))
                    AnswerInfo_Max = Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]);

                QuestionSelect_SpScore = 0;
                AnswerInfo_Name = "脑血管";
                if (list_answerInfo_Score.ContainsKey(24))
                {
                    int QuestionSelect_Score = Convert.ToInt32(list_answerInfo_Score[24]);
                    if (QuestionSelect_Score >= 60)
                        list_answerResult[AnswerInfo_Name] = "重度";

                    QuestionSelect_SpScore = QuestionSelect_Score;
                }

                if (!list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    if (list_answerScore.ContainsKey(AnswerInfo_Name))
                    {
                        int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                        if (AnswerInfo_ScoreTotal >= 0 && AnswerInfo_ScoreTotal <= 10)
                            list_answerResult[AnswerInfo_Name] = "健康";

                        if (AnswerInfo_ScoreTotal >= 11 && AnswerInfo_ScoreTotal <= 39)
                            list_answerResult[AnswerInfo_Name] = "亚健康";

                        if (AnswerInfo_ScoreTotal >= 40 && AnswerInfo_ScoreTotal <= 79)
                            list_answerResult[AnswerInfo_Name] = "轻度";

                        if (AnswerInfo_ScoreTotal >= 80 && AnswerInfo_ScoreTotal <= 119)
                            list_answerResult[AnswerInfo_Name] = "中度";

                        if (AnswerInfo_ScoreTotal >= 120)
                            list_answerResult[AnswerInfo_Name] = "重度";
                    }
                    else
                        list_answerResult[AnswerInfo_Name] = "健康";

                    if (list_answerResult[AnswerInfo_Name].ToString() != "重度")
                    {
                        if (QuestionSelect_SpScore >= 40)
                            list_answerResult[AnswerInfo_Name] = "中度";

                        if (QuestionSelect_SpScore >= 20 && QuestionSelect_SpScore <= 39 && (list_answerResult[AnswerInfo_Name].ToString() == "健康" || list_answerResult[AnswerInfo_Name].ToString() == "亚健康"))
                            list_answerResult[AnswerInfo_Name] = "轻度";
                    }                    
                }

                if (AnswerInfo_Max < Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]))
                    AnswerInfo_Max = Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]);

                QuestionSelect_SpScore = 0;
                AnswerInfo_Name = "老年痴呆";
                if (list_answerInfo_Score.ContainsKey(25))
                {
                    int QuestionSelect_Score = Convert.ToInt32(list_answerInfo_Score[25]);
                    if (QuestionSelect_Score >= 60)
                        list_answerResult[AnswerInfo_Name] = "重度";

                    QuestionSelect_SpScore = QuestionSelect_Score;
                }

                if (!list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    if (list_answerInfo_Score.ContainsKey(39))
                    {
                        int QuestionSelect_Score = Convert.ToInt32(list_answerInfo_Score[39]);
                        if (QuestionSelect_Score >= 8)
                        {
                            if (list_answerScore.ContainsKey(AnswerInfo_Name))
                            {
                                int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                                if (AnswerInfo_ScoreTotal >= 0 && AnswerInfo_ScoreTotal <= 4)
                                    list_answerResult[AnswerInfo_Name] = "健康";

                                if (AnswerInfo_ScoreTotal >= 5 && AnswerInfo_ScoreTotal <= 7)
                                    list_answerResult[AnswerInfo_Name] = "亚健康";

                                if (AnswerInfo_ScoreTotal >= 8 && AnswerInfo_ScoreTotal <= 12)
                                    list_answerResult[AnswerInfo_Name] = "轻度";

                                if (AnswerInfo_ScoreTotal >= 13 && AnswerInfo_ScoreTotal <= 24)
                                    list_answerResult[AnswerInfo_Name] = "中度";

                                if (AnswerInfo_ScoreTotal >= 25)
                                    list_answerResult[AnswerInfo_Name] = "重度";
                            }
                            else
                                list_answerResult[AnswerInfo_Name] = "健康";
                        }
                        else
                            list_answerResult[AnswerInfo_Name] = "健康";
                    }

                    if (list_answerResult[AnswerInfo_Name].ToString() != "重度")
                    {
                        if (QuestionSelect_SpScore >= 40)
                            list_answerResult[AnswerInfo_Name] = "中度";

                        if (QuestionSelect_SpScore >= 20 && QuestionSelect_SpScore <= 39 && (list_answerResult[AnswerInfo_Name].ToString() == "健康" || list_answerResult[AnswerInfo_Name].ToString() == "亚健康"))
                            list_answerResult[AnswerInfo_Name] = "轻度";
                    }                    
                }

                if (AnswerInfo_Max < Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]))
                    AnswerInfo_Max = Convert.ToInt32(AnswerInfo_Result_Table[list_answerResult[AnswerInfo_Name].ToString()]);

                AnswerInfo_Name = "保健方案";
                if (list_answerScore.ContainsKey(AnswerInfo_Name))
                {                    
                    String AnswerInfo_Result = AnswerInfo_Result_Array[AnswerInfo_Max];                    
                    Function_QuestionCase function_questionCase = new Function_QuestionCase();
                    DataTable dt = function_questionCase.Query_QuestionCase(Question_ID, AnswerInfo_Result);
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

            AnswerInfo_Result_Table.Clear();
        }

        public void Show_Result()
        {
            //String Result = "";
            String AnswerInfo_Name = "";
            
            if (list_answerInfo_Score != null && list_answerInfo_Score.Count > 0)
            {
                AnswerInfo_Name = "心血管";
                if (list_answerScore.ContainsKey(AnswerInfo_Name) && list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                    String AnswerInfo_ResultTotal = Convert.ToString(list_answerResult[AnswerInfo_Name]);

                    //Result += AnswerInfo_Name + ": " + AnswerInfo_ScoreTotal.ToString() + "分" + "  " + "属于: " + AnswerInfo_ResultTotal;
                    //Result += "\n";
                    label2.Text += AnswerInfo_Name + ": " + AnswerInfo_ResultTotal;
                    label2.Text += "  ";

                    //pictureBox2.Location = new Point(pictureBox1.Location.X + Convert.ToInt32(Convert.ToSingle(pictureBox1.Width) / Convert.ToSingle(300) * AnswerInfo_ScoreTotal - Convert.ToSingle(pictureBox2.Width) / Convert.ToSingle(2)), pictureBox2.Location.Y);
                    pictureBox2.Location = new Point(AnswerInfo_ResultLocation(AnswerInfo_ResultTotal), pictureBox2.Location.Y);
                }

                AnswerInfo_Name = "脑血管";
                if (list_answerScore.ContainsKey(AnswerInfo_Name) && list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                    String AnswerInfo_ResultTotal = Convert.ToString(list_answerResult[AnswerInfo_Name]);

                    //Result += AnswerInfo_Name + ": " + AnswerInfo_ScoreTotal.ToString() + "分" + "  " + "属于: " + AnswerInfo_ResultTotal;
                    //Result += "\n";

                    label2.Text += AnswerInfo_Name + ": " + AnswerInfo_ResultTotal;
                    label2.Text += "  ";

                    //pictureBox3.Location = new Point(pictureBox1.Location.X + Convert.ToInt32(Convert.ToSingle(pictureBox1.Width) / Convert.ToSingle(300) * AnswerInfo_ScoreTotal - Convert.ToSingle(pictureBox3.Width) / Convert.ToSingle(2)), pictureBox3.Location.Y);
                    pictureBox3.Location = new Point(AnswerInfo_ResultLocation(AnswerInfo_ResultTotal), pictureBox3.Location.Y);
                }

                AnswerInfo_Name = "老年痴呆";
                if (list_answerScore.ContainsKey(AnswerInfo_Name) && list_answerResult.ContainsKey(AnswerInfo_Name))
                {
                    int AnswerInfo_ScoreTotal = Convert.ToInt32(list_answerScore[AnswerInfo_Name]);
                    String AnswerInfo_ResultTotal = Convert.ToString(list_answerResult[AnswerInfo_Name]);

                    //if (AnswerInfo_ResultTotal == "健康")
                    //    Result += AnswerInfo_Name + "属于: " + AnswerInfo_ResultTotal;
                    //else
                    //    Result += AnswerInfo_Name + ": " + AnswerInfo_ScoreTotal.ToString() + "分" + "  " + "属于: " + AnswerInfo_ResultTotal;
                    //
                    //Result += "\n";

                    label2.Text += AnswerInfo_Name + ": " + AnswerInfo_ResultTotal;
                    label2.Text += "  ";

                    //pictureBox4.Location = new Point(pictureBox1.Location.X + Convert.ToInt32(Convert.ToSingle(pictureBox1.Width) / Convert.ToSingle(300) * AnswerInfo_ScoreTotal - Convert.ToSingle(pictureBox4.Width) / Convert.ToSingle(2)), pictureBox4.Location.Y);
                    pictureBox4.Location = new Point(AnswerInfo_ResultLocation(AnswerInfo_ResultTotal), pictureBox4.Location.Y);
                }

                //Label label_Result = new Label();
                //label_Result.Font = new System.Drawing.Font("宋体", 10.5F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
                //label_Result.AutoSize = true;
                //label_Result.Location = label3.Location;
                //label_Result.Text = Result;
                //Controls.Add(label_Result);

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
                    Class_Chart class_Chart = new Class_Chart(chart_dataTable, "DataX", "DataY", Color.BlueViolet, pictureBox10.Width, pictureBox10.Height);
                    Bitmap bitMap = class_Chart.Show_MSChart();

                    if (bitMap != null)
                    {
                        Image image = Image.FromHbitmap(bitMap.GetHbitmap());
                        pictureBox10.Image = image;
                        pictureBox10.Show();
                        pictureBox10.Refresh();
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
                
        private int AnswerInfo_ResultLocation(String AnswerInfo_ResultTotal)
        {
            if (AnswerInfo_ResultTotal == "健康")
                return 123;

            if (AnswerInfo_ResultTotal == "亚健康")
                return 228;

            if (AnswerInfo_ResultTotal == "轻度")
                return 317;

            if (AnswerInfo_ResultTotal == "中度")
                return 467;

            if (AnswerInfo_ResultTotal == "重度")
                return 578;

            return pictureBox1.Location.X;
        }
    }
}
