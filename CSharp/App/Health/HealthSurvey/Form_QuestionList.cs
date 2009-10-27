using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;
using System.Globalization;
using System.Reflection;
using System.Windows.Forms;
using System.Resources;
using HealthSurvey.Properties;
using WinFormsUI.Docking;
using ChoiControls;

namespace HealthSurvey
{
    public partial class Form_QuestionList : Form_Class
    {
        public int Question_ID;
        public int ClientInfo_ID;

        private Class_ClientInfo class_clientInfo = null;
        private int AnswerInfo_ID = 0;
        private int Controls_Height = 0;
        private int Panel_Height = 0;
        private int QuestionList_Total = 0;
        private int QuestionList_TotalPage = 0;
        private int QuestionList_CurrentPage = 1;

        private bool IsClose = false;
        
        public Form_QuestionList()
        {
            InitializeComponent();
        }

        public Form_QuestionList(int Q_ID, int C_ID)
        {
            Question_ID = Q_ID;
            ClientInfo_ID = C_ID;
                        
            InitializeComponent();
        }

        private void choiButton1_Click(object sender, EventArgs e)
        {
            QuestionList_CurrentPage--;
            Control_Visible();
        }

        private void choiButton2_Click(object sender, EventArgs e)
        {
            if (QuestionList_CurrentPage == QuestionList_TotalPage)
            {
                if (IsClose)
                    Print_Test();
                else
                    Save_Test(0);
            }
            else
            {
                QuestionList_CurrentPage++;
                Control_Visible();
            }
        }

        private void choiButton3_Click(object sender, EventArgs e)
        {
            if (IsClose)
                this.Close();
            else
                Save_Test(1);
        }

        private void Panel_Control_Added(object sender, ControlEventArgs e)
        {
            e.Control.Left = 2;
            e.Control.Top = Controls_Height;
            Controls_Height += e.Control.Height;
        }

        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.DrawString(label2.Text, label2.Font, new SolidBrush(label2.ForeColor), label2.Left - pictureBox2.Left, label2.Top - pictureBox2.Top);
        }

        private void Form_QuestionList_Scroll(object sender, ScrollEventArgs e)
        {
            this.Refresh();
        }

        private void Form_QuestionList_MouseWheel(object sender, MouseEventArgs e)
        {
            this.Refresh();
        }

        public void Show_QuestionList()
        {
            //显示客户姓名和编号
            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            DataTable dt = function_clientInfo.Query_ClientInfo(ClientInfo_ID);
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

            if (Question_ID > 0)
            {
                //显示题目名称和题目头
                Function_Question function_question = new Function_Question();
                dt = function_question.Query_AnswerInfo(Question_ID);
                if (dt != null && dt.Rows.Count > 0)
                {
                    label2.Text = dt.Rows[0]["Question_Title"].ToString();
                    label4.Text = dt.Rows[0]["Question_Top"].ToString();

                    //flowLayoutPanel1.BorderStyle = BorderStyle.Fixed3D;                    
                    flowLayoutPanel1.Controls.Add(label3);
                    flowLayoutPanel1.Controls.Add(label4);
                    flowLayoutPanel1.Height = label3.Height + label4.Height + 5;

                    label5.Location = new Point(label5.Location.X, flowLayoutPanel1.Location.Y + flowLayoutPanel1.Height + 10);
                    label6.Location = new Point(label6.Location.X, flowLayoutPanel1.Location.Y + flowLayoutPanel1.Height + 10);

                    Panel_Height = label5.Location.Y + label5.Height + 10;
                }

                Function_AnswerInfo function_anwerInfo = new Function_AnswerInfo();
                dt = function_anwerInfo.Query_AnswerInfo(ClientInfo_ID, Question_ID);
                if (dt != null && dt.Rows.Count > 0)
                {
                    if (CommonFunction.IsNumber(dt.Rows[0]["AnswerInfo_ID"].ToString(), 0))
                    {
                        AnswerInfo_ID = Convert.ToInt32(dt.Rows[0]["AnswerInfo_ID"].ToString());
                        Text_Question_Tail.Text = dt.Rows[0]["Question_Tail"].ToString();
                    }
                }

                Hashtable hash_answerList = new Hashtable();
                if (AnswerInfo_ID > 0)
                {
                    //读出每条答过的题目
                    Function_AnswerList function_answerList = new Function_AnswerList();
                    dt = function_answerList.Query_AnswerList(AnswerInfo_ID);
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        int QuestionList_ID = 0;
                        for (int i = 0; i < dt.Rows.Count; i++)
                        {
                            if (QuestionList_ID != Convert.ToInt32(dt.Rows[i]["QuestionList_ID"].ToString()))
                            {
                                QuestionList_ID = Convert.ToInt32(dt.Rows[i]["QuestionList_ID"].ToString());

                                ArrayList list_questionSelect = new ArrayList();
                                list_questionSelect.Add(Convert.ToInt32(dt.Rows[i]["QuestionSelect_ID"].ToString()));

                                hash_answerList.Add(QuestionList_ID, list_questionSelect);
                            }
                            else
                            {
                                ArrayList list_questionSelect = (ArrayList)hash_answerList[QuestionList_ID];
                                list_questionSelect.Add(Convert.ToInt32(dt.Rows[i]["QuestionSelect_ID"].ToString()));

                                hash_answerList[QuestionList_ID] = list_questionSelect;
                            }
                        }
                    }
                }

                //读出每条题目
                Function_QuestionList function_questionList = new Function_QuestionList();
                dt = function_questionList.Query_QuestionList(Question_ID);
                if (dt != null && dt.Rows.Count > 0)
                {
                    ArrayList list_QuestionList = new ArrayList();
                    int j = -1;
                    int QuestionList_Sequence = 0;
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        int QuestionList_ID = Convert.ToInt32(dt.Rows[i]["QuestionList_ID"].ToString());
                        if (QuestionList_ID != QuestionList_Sequence)
                        {
                            Class_Question class_question = new Class_Question();
                            class_question.QuestionList_ListID = Convert.ToInt32(dt.Rows[i]["QuestionList_ListID"].ToString()); ;
                            class_question.QuestionList_ID = QuestionList_ID;
                            class_question.Question_Title = dt.Rows[i]["QuestionList_Title"].ToString();
                            class_question.Question_Option = Convert.ToInt32(dt.Rows[i]["QuestionList_Option"].ToString());
                            class_question.Question_TurnRow = Convert.ToInt32(dt.Rows[i]["QuestionList_TurnRow"].ToString());

                            Class_QuestionSelect class_questionSelect = new Class_QuestionSelect();
                            class_questionSelect.QuestionSelect_ID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_ID"].ToString());
                            class_questionSelect.QuestionSelect_MainID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_MainID"].ToString());
                            class_questionSelect.QuestionSelect_ListID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_ListID"].ToString());
                            class_questionSelect.QuestionSelect_Text = dt.Rows[i]["QuestionSelect_Text"].ToString();
                            class_questionSelect.QuestionSelect_Score = Convert.ToInt32(dt.Rows[i]["QuestionSelect_Score"].ToString());

                            ArrayList list_questionSelect = new ArrayList();
                            list_questionSelect.Add(class_questionSelect);
                            list_QuestionList.Add(class_question);

                            class_question.Class_QuestionSelect = list_questionSelect;
                            QuestionList_Sequence = QuestionList_ID;
                            j++;
                        }
                        else
                        {
                            Class_Question class_question = (Class_Question)list_QuestionList[j];
                            ArrayList list_question = class_question.Class_QuestionSelect;

                            Class_QuestionSelect class_questionSelect = new Class_QuestionSelect();
                            class_questionSelect.QuestionSelect_ID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_ID"].ToString());
                            class_questionSelect.QuestionSelect_MainID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_MainID"].ToString());
                            class_questionSelect.QuestionSelect_ListID = Convert.ToInt32(dt.Rows[i]["QuestionSelect_ListID"].ToString());
                            class_questionSelect.QuestionSelect_Text = dt.Rows[i]["QuestionSelect_Text"].ToString();
                            class_questionSelect.QuestionSelect_Score = Convert.ToInt32(dt.Rows[i]["QuestionSelect_Score"].ToString());

                            list_question.Add(class_questionSelect);
                        }
                    }

                    //显示每条题目
                    if (list_QuestionList.Count > 0)
                    {
                        QuestionList_Total = list_QuestionList.Count;
                        QuestionList_TotalPage = (list_QuestionList.Count - 1) / 10 + 1;

                        Panel panel = null;
                        int p = QuestionList_CurrentPage;

                        int i = 0;
                        for (i = 0; i < list_QuestionList.Count; i++)
                        {
                            if (i % 10 == 0)
                            {
                                panel = new Panel();
                                panel.AutoSize = true;                                
                                //panel.BorderStyle = BorderStyle.Fixed3D;                                                                
                                panel.Location = new System.Drawing.Point(pictureBox1.Location.X, Panel_Height);
                                panel.Name = "Panel_QuestionList" + p.ToString();
                                panel.Visible = false;
                                panel.ControlAdded += new ControlEventHandler(Panel_Control_Added);
                                Controls.Add(panel);

                                if (p == QuestionList_CurrentPage)
                                    panel.Visible = true;

                                p++;
                                Controls_Height = 0;
                            }

                            Class_Question class_question = (Class_Question)list_QuestionList[i];
                            Control_QuestionList control_questionList = new Control_QuestionList(class_question, class_clientInfo);

                            if (hash_answerList != null && hash_answerList.Count > 0)
                            {
                                ArrayList list_questionSelect = (ArrayList)hash_answerList[class_question.QuestionList_ID];
                                control_questionList.QuestionSelect_List = list_questionSelect;
                            }

                            control_questionList.Show_QuestionList();
                            panel.Controls.Add(control_questionList);
                        }

                        String Question_Tail = function_question.Query_Question_Tail(Question_ID);
                        if (!String.IsNullOrEmpty(Question_Tail))
                            label7.Text = Question_Tail;
                        else
                        {
                            label7.Visible = false;
                            Text_Question_Tail.Visible = false;
                        }

                        if (panel != null)
                        {
                            panel.Controls.Add(label7);
                            panel.Controls.Add(Text_Question_Tail);
                        }

                        Control[] panel_controls = Controls.Find("Panel_QuestionList" + QuestionList_CurrentPage, false);
                        if (panel_controls == null || panel_controls.Length == 0)
                            return;

                        if (panel_controls[0] is Panel)
                        {
                            panel = (Panel)panel_controls[0];

                            choiButton1.Location = new Point(choiButton1.Location.X, panel.Location.Y + panel.Size.Height + 10);
                            choiButton2.Location = new Point(choiButton2.Location.X, panel.Location.Y + panel.Size.Height + 10);
                            choiButton3.Location = new Point(choiButton3.Location.X, panel.Location.Y + panel.Size.Height + 10);
                        }
                    }
                }
            }
        }

        private void Control_Visible()
        {
            for (int i = 1; i <= QuestionList_TotalPage; i++)
            {
                Control[] panel_controls = Controls.Find("Panel_QuestionList" + i, false);
                if (panel_controls == null || panel_controls.Length == 0)
                    return;

                if (panel_controls[0] is Panel)
                {
                    Panel panel = (Panel)panel_controls[0];

                    if (i == QuestionList_CurrentPage)
                    {
                        panel.Visible = true;

                        choiButton1.Location = new Point(choiButton1.Location.X, panel.Location.Y + panel.Size.Height + 10);
                        choiButton2.Location = new Point(choiButton2.Location.X, panel.Location.Y + panel.Size.Height + 10);
                        choiButton3.Location = new Point(choiButton3.Location.X, panel.Location.Y + panel.Size.Height + 10);
                    }
                    else
                        panel.Visible = false;
                }
            }

            if (QuestionList_CurrentPage > 1)
                choiButton1.Enabled = true;
            else
                choiButton1.Enabled = false;

            if (QuestionList_CurrentPage < QuestionList_TotalPage)
                choiButton2.Text = "下一页";
            else
                choiButton2.Text = "保存";

            if (QuestionList_CurrentPage == QuestionList_TotalPage)
            {
                choiButton3.Visible = true;
                choiButton3.Enabled = true;
            }
            else
            {
                choiButton3.Visible = false;
                choiButton3.Enabled = false;
            }
        }

        private void Save_Test(int SaveTest)
        {
            if (QuestionList_TotalPage > 0)
            {
                Hashtable list_answerInfo_Score = new Hashtable();
                Hashtable list_answerInfo_Text = new Hashtable();
                Panel panel;
                Control[] panel_controls;

                //保存
                if (SaveTest == 0)
                {
                    bool Is_Save = false;
                    if (AnswerInfo_ID > 0)
                        Is_Save = true;

                    String Question_Tail = "";
                    Function_AnswerInfo function_answerInfo = new Function_AnswerInfo();

                    if (!Is_Save)
                        AnswerInfo_ID = function_answerInfo.Query_AnswerInfo_ID();

                    for (int i = 1; i <= QuestionList_TotalPage; i++)
                    {
                        panel_controls = Controls.Find("Panel_QuestionList" + i.ToString(), false);
                        if (panel_controls == null || panel_controls.Length == 0)
                            return;

                        if (panel_controls[0] is Panel)
                            panel = (Panel)panel_controls[0];
                        else
                            return;

                        foreach (Control control_questionList in panel.Controls)
                        {
                            if (control_questionList is Control_QuestionList)
                            {
                                int QuestionList_ListID = (control_questionList as Control_QuestionList).QuestionList_ListID;
                                int QuestionSelect_Score = (control_questionList as Control_QuestionList).QuestionSelect_Score;
                                String QuestionSelect_Text = (control_questionList as Control_QuestionList).QuestionSelect_Text;
                                TextBox TextBox_QuestionList = (control_questionList as Control_QuestionList).TextBox_QuestionList;

                                if (QuestionSelect_Score != -1)
                                {
                                    list_answerInfo_Score.Add(QuestionList_ListID, QuestionSelect_Score);
                                    list_answerInfo_Text.Add(QuestionList_ListID, QuestionSelect_Text);
                                }

                                if (TextBox_QuestionList != null)
                                {
                                    if (TextBox_QuestionList.Name == "ClientInfo_Weight")
                                    {
                                        if (!String.IsNullOrEmpty(TextBox_QuestionList.Text))
                                        {
                                            if (class_clientInfo != null && CommonFunction.IsNumber(TextBox_QuestionList.Text, 2))
                                                class_clientInfo.ClientInfo_Weight = Convert.ToSingle(TextBox_QuestionList.Text);

                                            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                                            function_clientInfo.Update_ClientInfo(class_clientInfo);
                                        }
                                    }

                                    if (TextBox_QuestionList.Name == "ClientInfo_Height")
                                    {
                                        if (!String.IsNullOrEmpty(TextBox_QuestionList.Text))
                                        {
                                            if (class_clientInfo != null && CommonFunction.IsNumber(TextBox_QuestionList.Text, 2))
                                                class_clientInfo.ClientInfo_Height = Convert.ToSingle(TextBox_QuestionList.Text);

                                            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                                            function_clientInfo.Update_ClientInfo(class_clientInfo);
                                        }
                                    }
                                }

                                Class_AnswerList class_answerList = new Class_AnswerList();
                                class_answerList.AnswerInfo_ID = AnswerInfo_ID;
                                class_answerList.QuestionList_ID = (control_questionList as Control_QuestionList).QuestionList_ID;
                                class_answerList.QuestionSelect_ID = (control_questionList as Control_QuestionList).QuestionSelect_ID;

                                Function_AnswerList function_answerList = new Function_AnswerList();
                                if (Is_Save)
                                {
                                    if (!function_answerList.Update_AnswerList(class_answerList))
                                        MessageBox.Show("Update_AnswerList更新数据失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                                }
                                else
                                {
                                    if (!function_answerList.Insert_AnswerList(class_answerList))
                                        MessageBox.Show("Insert_AnswerList插入数据失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                                }
                            }

                            if (control_questionList is TextBox)
                            {
                                TextBox textBox_controls = (TextBox)control_questionList;
                                if (textBox_controls.Name == "Text_Question_Tail")
                                    Question_Tail = textBox_controls.Text;
                            }
                        }
                    }

                    Class_AnswerInfo class_answerInfo = new Class_AnswerInfo();
                    class_answerInfo.AnswerInfo_ID = AnswerInfo_ID;
                    class_answerInfo.ClientInfo_ID = ClientInfo_ID;
                    class_answerInfo.Question_ID = Question_ID;
                    class_answerInfo.Question_Tail = Question_Tail;

                    if (Is_Save)
                    {
                        if (!function_answerInfo.Update_AnswerInfo(class_answerInfo))
                            MessageBox.Show("Update_AnswerInfo更新数据失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                    else
                    {
                        if (!function_answerInfo.Insert_AnswerInfo(class_answerInfo))
                            MessageBox.Show("Insert_AnswerInfo插入数据失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }

                    MessageBox.Show("已将答卷记录保存到数据库。", "成功", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                }

                //测试
                if (SaveTest == 1)
                {
                    int QuestionList_NoSelect = 0;
                    for (int i = 1; i <= QuestionList_TotalPage; i++)
                    {
                        panel_controls = Controls.Find("Panel_QuestionList" + i.ToString(), false);
                        if (panel_controls == null || panel_controls.Length == 0)
                            return;

                        if (panel_controls[0] is Panel)
                        {
                            panel = (Panel)panel_controls[0];
                            //panel.Visible = false;
                        }
                        else
                            return;

                        foreach (Control control_questionList in panel.Controls)
                        {
                            if (control_questionList is Control_QuestionList)
                            {
                                int QuestionList_ListID = (control_questionList as Control_QuestionList).QuestionList_ListID;
                                int QuestionSelect_Score = (control_questionList as Control_QuestionList).QuestionSelect_Score;
                                String QuestionSelect_Text = (control_questionList as Control_QuestionList).QuestionSelect_Text;
                                TextBox TextBox_QuestionList = (control_questionList as Control_QuestionList).TextBox_QuestionList;

                                if (TextBox_QuestionList == null)
                                {
                                    if (QuestionSelect_Score != -1 && !String.IsNullOrEmpty(QuestionSelect_Text))
                                    {
                                        list_answerInfo_Score.Add(QuestionList_ListID, QuestionSelect_Score);
                                        list_answerInfo_Text.Add(QuestionList_ListID, QuestionSelect_Text);
                                    }
                                    else
                                    {
                                        QuestionList_NoSelect = i;
                                        MessageBox.Show("第" + QuestionList_ListID.ToString() + "题没选择", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                                        break;
                                    }
                                }
                                else
                                {
                                    if (!String.IsNullOrEmpty(TextBox_QuestionList.Text) && CommonFunction.IsNumber(TextBox_QuestionList.Text, 2))
                                    {
                                        list_answerInfo_Score.Add(QuestionList_ListID, Convert.ToSingle(TextBox_QuestionList.Text));
                                        list_answerInfo_Text.Add(QuestionList_ListID, QuestionSelect_Text);
                                    }
                                    else
                                    {
                                        list_answerInfo_Score.Add(QuestionList_ListID, 0);
                                        list_answerInfo_Text.Add(QuestionList_ListID, QuestionSelect_Text);
                                    }
                                }
                            }
                        }

                        if (QuestionList_NoSelect > 0)
                            break;
                    }

                    if (QuestionList_NoSelect > 0)
                    {
                        QuestionList_CurrentPage = QuestionList_NoSelect;
                        for (int i = 1; i <= QuestionList_TotalPage; i++)
                        {
                            panel_controls = Controls.Find("Panel_QuestionList" + i.ToString(), false);
                            if (panel_controls == null || panel_controls.Length == 0)
                                return;

                            if (panel_controls[0] is Panel)
                                panel = (Panel)panel_controls[0];
                            else
                                return;

                            if (i == QuestionList_NoSelect)
                            {
                                Control_Visible();
                                panel.Visible = true;
                            }
                            else
                                panel.Visible = false;
                        }

                        return;
                    }

                    //Control[] flow_controls = Controls.Find("Panel_Top", false);
                    //if (flow_controls == null || flow_controls.Length == 0)
                    //    return;

                    //if (flow_controls[0] is FlowLayoutPanel)
                    //{
                    //    FlowLayoutPanel flow = (FlowLayoutPanel)flow_controls[0];
                    //    flow.Visible = false;
                    //}

                    //label5.Location = new Point(label5.Location.X, pictureBox2.Location.Y + pictureBox2.Height + 10);
                    //label6.Location = new Point(label6.Location.X, pictureBox2.Location.Y + pictureBox2.Height + 10);

                    //panel = new Panel();
                    //panel.AutoSize = true;
                    ////panel.BorderStyle = BorderStyle.Fixed3D;
                    //panel.Location = new System.Drawing.Point(50, label5.Location.Y + 15);
                    //panel.Name = "Panel_QuestionCase";
                    //panel.Visible = true;
                    //Controls.Add(panel);

                    //if (Question_ID == 1)
                    //{
                    //    Control_QuestionCase1 control_questionCase = new Control_QuestionCase1(Convert.ToInt32(Question_ID), list_answerInfo_Score);
                    //    control_questionCase.Show_Result();

                    //    panel.Controls.Add(control_questionCase);
                    //}

                    //if (Question_ID == 2)
                    //{
                    //    Control_QuestionCase2 control_questionCase = new Control_QuestionCase2(Convert.ToInt32(Question_ID), list_answerInfo_Score, list_answerInfo_Text);
                    //    control_questionCase.Show_Result();

                    //    panel.Controls.Add(control_questionCase);
                    //}

                    //choiButton1.Enabled = false;
                    //choiButton1.Visible = false;

                    //choiButton1.Location = new Point(choiButton1.Location.X, panel.Location.Y + panel.Size.Height + 10);
                    //choiButton2.Location = new Point(choiButton2.Location.X, panel.Location.Y + panel.Size.Height + 10);
                    //choiButton3.Location = new Point(choiButton3.Location.X, panel.Location.Y + panel.Size.Height + 10);

                    //IsClose = true;
                    //choiButton2.Text = "打印";
                    //choiButton3.Text = "关闭";
                                        
                    //this.Refresh();          
                                        
                    Form_Result form_Result = new Form_Result(Question_ID, ClientInfo_ID, list_answerInfo_Score, list_answerInfo_Text);
                    form_Result.Show_Result();
                    form_Result.FormBorderStyle = FormBorderStyle.None;
                    form_Result.StartPosition = FormStartPosition.Manual;
                    form_Result.WindowState = FormWindowState.Maximized;        
                    form_Result.Show();                                        
                }
            }
        }

        private void Print_Test()
        {

        }                
    }
}
