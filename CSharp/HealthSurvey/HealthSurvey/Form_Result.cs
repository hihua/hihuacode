using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Drawing.Printing;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;

namespace HealthSurvey
{
    public partial class Form_Result : Form
    {
        private int Question_ID;
        private int ClientInfo_ID;
        private Bitmap memoryImage;
        private Hashtable list_answerInfo_Score = null;
        private Hashtable list_answerInfo_Text = null;
        private Class_ClientInfo class_clientInfo = null;

        public Form_Result(int Q_ID, int C_ID, Hashtable A_List_Score, Hashtable A_List_Text)
        {
            InitializeComponent();

            Question_ID = Q_ID;
            ClientInfo_ID = C_ID;
            list_answerInfo_Score = A_List_Score;
            list_answerInfo_Text = A_List_Text;
            DataTable dt = null;

            Function_Question function_question = new Function_Question();
            dt = function_question.Query_AnswerInfo(Question_ID);
            if (dt != null && dt.Rows.Count > 0)
            {
                label2.Text = dt.Rows[0]["Question_Title"].ToString();                
            }
                        
            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            dt = function_clientInfo.Query_ClientInfo(ClientInfo_ID);
            if (dt != null && dt.Rows.Count > 0)
            {
                class_clientInfo = new Class_ClientInfo();
                class_clientInfo.ClientInfo_ID = Convert.ToInt32(dt.Rows[0]["ClientInfo_ID"].ToString());
                class_clientInfo.ClientInfo_Name = dt.Rows[0]["ClientInfo_Name"].ToString();
                class_clientInfo.ClientInfo_Age = Convert.ToUInt32(dt.Rows[0]["ClientInfo_Age"].ToString());
                class_clientInfo.ClientInfo_Sex = dt.Rows[0]["ClientInfo_Sex"].ToString();

                if (dt.Rows[0]["ClientInfo_Weight"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Weight"].ToString()) && CommonFunction.IsNumber(dt.Rows[0]["ClientInfo_Weight"].ToString(), 2))
                    class_clientInfo.ClientInfo_Weight = Convert.ToSingle(dt.Rows[0]["ClientInfo_Weight"].ToString());

                if (dt.Rows[0]["ClientInfo_Height"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Height"].ToString()) && CommonFunction.IsNumber(dt.Rows[0]["ClientInfo_Height"].ToString(), 2))
                    class_clientInfo.ClientInfo_Height = Convert.ToSingle(dt.Rows[0]["ClientInfo_Height"].ToString());

                if (dt.Rows[0]["ClientInfo_Province"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Province"].ToString()))
                    class_clientInfo.ClientInfo_Province = dt.Rows[0]["ClientInfo_Province"].ToString();

                if (dt.Rows[0]["ClientInfo_City"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_City"].ToString()))
                    class_clientInfo.ClientInfo_City = dt.Rows[0]["ClientInfo_City"].ToString();

                if (dt.Rows[0]["ClientInfo_Address"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Address"].ToString()))
                    class_clientInfo.ClientInfo_City = dt.Rows[0]["ClientInfo_Address"].ToString();

                if (dt.Rows[0]["ClientInfo_Tel"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Tel"].ToString()))
                    class_clientInfo.ClientInfo_City = dt.Rows[0]["ClientInfo_Tel"].ToString();

                if (dt.Rows[0]["ClientInfo_Email"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Email"].ToString()))
                    class_clientInfo.ClientInfo_Email = dt.Rows[0]["ClientInfo_Email"].ToString();

                if (dt.Rows[0]["ClientInfo_Zip"] != null && !String.IsNullOrEmpty(dt.Rows[0]["ClientInfo_Zip"].ToString()))
                    class_clientInfo.ClientInfo_Zip = dt.Rows[0]["ClientInfo_Zip"].ToString();

                if (dt.Rows[0]["AddTime"] != null && !String.IsNullOrEmpty(dt.Rows[0]["AddTime"].ToString()))
                    class_clientInfo.AddTime = Convert.ToDateTime(dt.Rows[0]["AddTime"].ToString());

                label5.Text += class_clientInfo.ClientInfo_ID.ToString();
                label6.Text += class_clientInfo.ClientInfo_Name.ToString();
            }
        }

        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {            
            e.Graphics.DrawString(label2.Text, label2.Font, new SolidBrush(label2.ForeColor), label2.Left - pictureBox2.Left, label2.Top - pictureBox2.Top);            
        }

        private void choiButton1_Click(object sender, EventArgs e)
        {
            try
            {
                this.CaptureScreen();
                PrintPreviewDialog printPreviewDialog1 = new PrintPreviewDialog();
                PrintDocument printDocumnet1 = new PrintDocument();
                printDocumnet1.PrintPage += new PrintPageEventHandler(this.printDocument1_PrintPage);
                printPreviewDialog1.Document = printDocumnet1;
                printPreviewDialog1.Show(this);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void choiButton2_Click(object sender, EventArgs e)
        {            
            this.Close();
        }

        public void Show_Result()
        {
            if (list_answerInfo_Score == null)
                return;

            if (Question_ID == 1)
            {
                Control_QuestionCase1 control_questionCase = new Control_QuestionCase1(Question_ID, list_answerInfo_Score);
                control_questionCase.Show_Result();

                panel1.Controls.Add(control_questionCase);
            }

            if (Question_ID == 2)
            {
                if (list_answerInfo_Text == null)
                    return;

                Control_QuestionCase2 control_questionCase = new Control_QuestionCase2(Question_ID, list_answerInfo_Score, list_answerInfo_Text);
                control_questionCase.Show_Result();

                panel1.Controls.Add(control_questionCase);
            }                        
        }

        [DllImport("gdi32.dll")]
        public static extern long BitBlt(IntPtr hdcDest, int nXDest, int nYDest, int nWidth, int nHeight, IntPtr hdcSrc, int nXSrc, int nYSrc, int dwRop);
        private void CaptureScreen()
        {
            Graphics mygraphics = CreateGraphics();
            Size s = Size;
            memoryImage = new Bitmap(s.Width, s.Height, mygraphics);
            Graphics memoryGraphics = Graphics.FromImage(memoryImage);
            IntPtr dc1 = mygraphics.GetHdc();
            IntPtr dc2 = memoryGraphics.GetHdc();
            BitBlt(dc2, 0, 0, ClientRectangle.Width, ClientRectangle.Height, dc1, 0, 0, 0xcc0020);
            mygraphics.ReleaseHdc(dc1);
            memoryGraphics.ReleaseHdc(dc2);
        }
        
        private void printDocument1_PrintPage(object sender, PrintPageEventArgs e)
        {
            e.Graphics.DrawImage(memoryImage, 0, 0);
        }
    }
}
