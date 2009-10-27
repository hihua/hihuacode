using System;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.IO;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using WinFormsUI.Docking;
using ChoiControls;

namespace HealthSurvey
{   
    public partial class Form_Main : Form
    {
        private String ConfigFile = "DockPanel.config";
        private DeserializeDockContent DockContent;

        private Form_AnswerInfo form_answerInfo = null;        
        private Form_ClientInfo form_clientInfo = null;        
        private Form_ClientList form_clientList = null;
        private Form_Question form_question = null;
        
        public Form_Main()
        {            
            InitializeComponent();

            form_answerInfo = new Form_AnswerInfo();
            form_clientInfo = new Form_ClientInfo();
            form_clientList = new Form_ClientList();
            form_question = new Form_Question();

            form_clientInfo.ClientInfoClick += new Form_ClientInfoClick_Handler(ClientInfoClick);
            form_clientInfo.ClientInfoDoubleClick += new Form_ClientInfoDoubleClick_Handler(ClientInfoDoubleClick);
            form_question.QuestionDoubleClick += new Form_QuestionDoubleClick_Handler(QuestionDoubleClick);
            form_question.QuestionClick += new Form_QuestionClick_Handler(QuestionClick);

            form_clientInfo.ClientInfoRefresh += new Form_ClientInfoRefresh_Handler(ClientInfo_Refresh);
            form_clientInfo.ClientListRefresh += new Form_ClientListRefresh_Handler(ClientList_Refresh);
            form_clientInfo.ClientListSearch += new Form_ClientListSearch_Handler(ClientList_Search);

            form_answerInfo.dataGridView1_Refresh(0);            
        }

        private void Form_Main_FormClosing(object sender, FormClosingEventArgs e)
        {
            dockPanel1.SaveAsXml(Application.StartupPath + "\\" + ConfigFile);
        }

        private void Form_Main_Load(object sender, EventArgs e)
        {
            if (File.Exists(Application.StartupPath + "\\" + ConfigFile))
            {
                DockContent = new DeserializeDockContent(GetContentFromPersistString);
                dockPanel1.LoadFromXml(Application.StartupPath + "\\" + ConfigFile, DockContent);
            }

            ClientInfoDoubleClick(0);
        }

        private IDockContent GetContentFromPersistString(String persistString)
        {
            if (persistString == typeof(Form_AnswerInfo).ToString())
            {
                return form_answerInfo;
            }

            if (persistString == typeof(Form_ClientInfo).ToString())
            {
                return form_clientInfo;
            }

            if (persistString == typeof(Form_ClientList).ToString())
            {
                return form_clientList;
            }

            if (persistString == typeof(Form_Question).ToString())
            {
                return form_question;
            }
            
            return null;
        }

        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            Application.ExitThread();
        }

        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            form_clientInfo.Show(dockPanel1);
        }

        private void toolStripMenuItem3_Click(object sender, EventArgs e)
        {
            form_question.Show(dockPanel1);
        }

        private void toolStripMenuItem4_Click(object sender, EventArgs e)
        {
            Form_ClientDetail fc = new Form_ClientDetail(this);
            fc.Show_ClientDetail();
            fc.Show(dockPanel1);
        }  
        
        private void toolStripMenuItem5_Click(object sender, EventArgs e)
        {
            form_clientList.Show(dockPanel1);
        }

        private void toolStripMenuItem6_Click(object sender, EventArgs e)
        {
            form_answerInfo.Show(dockPanel1);
        }

        private void toolStripMenuItem8_Click(object sender, EventArgs e)
        {
            Form_OutExcel form_OutExcel = new Form_OutExcel();
            form_OutExcel.ShowDialog();                                    
        }

        private void ClientInfoClick(int ClientInfo_ID)
        {
            form_question.ClientInfo_ID = ClientInfo_ID;
            form_clientList.dataGridView_Refresh(ClientInfo_ID);
        }

        private void ClientInfoDoubleClick(int ClientInfo_ID)
        {
            Form_ClientDetail form_ClientDetail = new Form_ClientDetail(this, ClientInfo_ID);
            form_ClientDetail.Show_ClientDetail();
            form_ClientDetail.Show(dockPanel1);
        }

        private void QuestionDoubleClick(int Question_ID, int ClientInfo_ID)
        {
            Form_QuestionList form_QuestionList = new Form_QuestionList(Question_ID, ClientInfo_ID);                        
            form_QuestionList.Show(dockPanel1);
            form_QuestionList.Show_QuestionList();
        }

        private void QuestionClick(int Question_ID)
        {
            form_answerInfo.dataGridView1_Refresh(Question_ID);
        }

        public void ClientInfo_Refresh()
        {
            form_clientInfo.listView_Refresh();
        }

        public void ClientList_Refresh()
        {
            form_clientList.dataGridView_Refresh();
        }

        public void ClientList_Search()
        {
            Form_Search form_Search = new Form_Search();
            form_Search.ShowDialog();
            if (form_Search.ClientInfo_Table != null)
            {
                form_clientList.dataGridView_Refresh(form_Search.ClientInfo_Table);
            }

            form_clientList.Show(dockPanel1);
        }
    }
}
