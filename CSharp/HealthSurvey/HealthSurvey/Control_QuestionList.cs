using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace HealthSurvey
{
    public partial class Control_QuestionList : UserControl
    {
        private Class_Question class_question;
        private Class_ClientInfo class_clientInfo;
        public ArrayList QuestionSelect_List = null;
        public int QuestionList_ID;
        public int QuestionList_ListID;        
        public String QuestionList_Title = "";
        public int[] QuestionSelect_ID_List = null;
        public int QuestionSelect_ID = -1;
        public int[] QuestionSelect_Score_List = null;
        public String[] QuestionSelect_Text_List = null;
        public int QuestionSelect_Score = -1;
        public String QuestionSelect_Text = "";
        public TextBox TextBox_QuestionList = null;

        public Control_QuestionList(Class_Question C_Question_Obj, Class_ClientInfo C_ClientInfo_Obj)
        {
            InitializeComponent();

            if (C_Question_Obj != null)
                class_question = C_Question_Obj;

            class_clientInfo = C_ClientInfo_Obj;
        }

        public void Show_QuestionList()
        {
            if (class_question != null)
            {
                QuestionList_ID = class_question.QuestionList_ID;
                QuestionList_ListID = class_question.QuestionList_ListID;
                QuestionList_Title = class_question.Question_Title;
                
                Label label_questionList = new Label();
                label_questionList.AutoSize = false;
                label_questionList.BackColor = Color.FromArgb(230, 230, 230);
                label_questionList.Dock = DockStyle.Top;
                label_questionList.Font = new Font("宋体", 9f, FontStyle.Regular, GraphicsUnit.Point, 0x86);
                label_questionList.ForeColor = Color.Black;
                label_questionList.Location = new Point(0, 0);
                label_questionList.Name = "label_questionList";
                label_questionList.TabIndex = 0;
                label_questionList.Text = class_question.QuestionList_ListID.ToString() + "." + " " + class_question.Question_Title;
                label_questionList.TextAlign = ContentAlignment.MiddleLeft;

                int X = 24;
                int Y = label_questionList.Size.Height + 5;
                int Z = 0;
                ArrayList list_questionSelect = class_question.Class_QuestionSelect;
                if (list_questionSelect != null && list_questionSelect.Count > 0)
                {
                    QuestionSelect_ID_List = new int[list_questionSelect.Count];
                    QuestionSelect_Score_List = new int[list_questionSelect.Count];
                    QuestionSelect_Text_List = new String[list_questionSelect.Count];
                    for (int i = 0; i < list_questionSelect.Count; i++)
                    {
                        Class_QuestionSelect class_questionSelect = (Class_QuestionSelect)list_questionSelect[i];

                        if (class_question.Question_TurnRow == 2)
                        {
                            TextBox_QuestionList = new TextBox();
                            TextBox_QuestionList.Location = new Point(X, Y);
                            TextBox_QuestionList.Name = class_questionSelect.QuestionSelect_Text;
                            TextBox_QuestionList.Width = 60;

                            if (class_clientInfo != null)
                            {
                                if (class_questionSelect.QuestionSelect_Text == "ClientInfo_Weight" && class_clientInfo.ClientInfo_Weight > 0)
                                    TextBox_QuestionList.Text = class_clientInfo.ClientInfo_Weight.ToString();

                                if (class_questionSelect.QuestionSelect_Text == "ClientInfo_Height" && class_clientInfo.ClientInfo_Height > 0)
                                    TextBox_QuestionList.Text = class_clientInfo.ClientInfo_Height.ToString();
                            }

                            Controls.Add(TextBox_QuestionList);

                            Y = label_questionList.Size.Height + 5;
                            if (TextBox_QuestionList.Size.Height > Z)
                                Z = TextBox_QuestionList.Size.Height;
                        }
                        else
                        {
                            RadioButton radioButton_questionList = new RadioButton();
                            radioButton_questionList.AutoSize = true;
                            radioButton_questionList.TextAlign = ContentAlignment.MiddleLeft;

                            if (QuestionSelect_List != null && QuestionSelect_List.Count > 0)
                            {
                                int j = Convert.ToInt32(QuestionSelect_List[0]);
                                if (class_questionSelect.QuestionSelect_ID == j)
                                {
                                    foreach (Control radio_controls in Controls)
                                    {
                                        if (radio_controls is RadioButton)
                                        {
                                            RadioButton radioButton = (RadioButton)radio_controls;
                                            radioButton.Checked = false;
                                        }
                                    }

                                    radioButton_questionList.Checked = true;
                                    QuestionSelect_ID = class_questionSelect.QuestionSelect_ID;
                                    QuestionSelect_Score = class_questionSelect.QuestionSelect_Score;
                                    QuestionSelect_Text = class_questionSelect.QuestionSelect_Text;
                                }
                            }

                            switch (i % 5)
                            {
                                case 0:
                                    radioButton_questionList.ForeColor = Color.Green;
                                    break;

                                case 1:
                                    radioButton_questionList.ForeColor = Color.Olive;
                                    break;

                                case 2:
                                    radioButton_questionList.ForeColor = Color.Chocolate;
                                    break;

                                case 3:
                                    radioButton_questionList.ForeColor = Color.DarkRed;
                                    break;

                                default:
                                    radioButton_questionList.ForeColor = Color.Green;
                                    break;
                            }

                            radioButton_questionList.Location = new Point(X, Y);
                            radioButton_questionList.Name = "RadioButton_QuestionList" + class_questionSelect.QuestionSelect_ListID;
                            radioButton_questionList.TabIndex = class_questionSelect.QuestionSelect_ListID;
                            radioButton_questionList.TabStop = true;
                            radioButton_questionList.Text = class_questionSelect.QuestionSelect_Text;
                            radioButton_questionList.UseVisualStyleBackColor = true;
                            radioButton_questionList.MouseClick += new MouseEventHandler(radioButton_MouseClick);

                            if (class_question.Question_TurnRow == 1)
                                Y += radioButton_questionList.Size.Height;
                            else
                            {
                                X = radioButton_questionList.Location.X + radioButton_questionList.Size.Width;
                                Y = label_questionList.Size.Height + 5;
                            }

                            Controls.Add(radioButton_questionList);

                            QuestionSelect_ID_List[i] = class_questionSelect.QuestionSelect_ID;
                            QuestionSelect_Score_List[i] = class_questionSelect.QuestionSelect_Score;
                            QuestionSelect_Text_List[i] = class_questionSelect.QuestionSelect_Text;

                            if (radioButton_questionList.Size.Height > Z)
                                Z = radioButton_questionList.Size.Height;                                                        
                        }                                                
                    }

                    if (QuestionSelect_Score != -1)
                        label_questionList.Text += " " + "(" + QuestionSelect_Score.ToString() + ")";
                }
                
                Controls.Add(label_questionList);

                if (class_question.Question_TurnRow == 1)
                    Size = new Size(600, Y);
                else
                    Size = new Size(600, Y + Z + 5);
            }
        }

        private void radioButton_MouseClick(object sender, MouseEventArgs e)
        {
            if ((sender as RadioButton).Checked)
            {
                int TabIndex = (sender as RadioButton).TabIndex;
                QuestionSelect_ID = QuestionSelect_ID_List[TabIndex - 1];
                QuestionSelect_Score = QuestionSelect_Score_List[TabIndex - 1];
                QuestionSelect_Text = QuestionSelect_Text_List[TabIndex - 1];

                Control[] label_controls = Controls.Find("Label_QuestionList", false);
                if (label_controls == null || label_controls.Length == 0)
                    return;

                if (label_controls[0] is Label)
                {
                    Label label_questionList = (Label)label_controls[0];
                    label_questionList.Text = QuestionList_ListID.ToString() + "." + " " + QuestionList_Title + " " + "(" + QuestionSelect_Score.ToString() + ")";
                }
            }
        }
    }
}
