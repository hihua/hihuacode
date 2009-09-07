using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WinFormsUI.Docking;
using ChoiControls;

namespace HealthSurvey
{
    public partial class Form_Question : Form_Class
    {
        public int ClientInfo_ID;
        public event Form_QuestionDoubleClick_Handler QuestionDoubleClick;
        public event Form_QuestionClick_Handler QuestionClick;

        public Form_Question()
        {
            InitializeComponent();            
        }                

        private void Form_Question_VisibleChanged(object sender, EventArgs e)
        {
            if (base.Visible)
            {
                listView1.Items.Clear();
                Function_Question function_question = new Function_Question();
                DataTable dt = function_question.Query_AnswerInfo();
                if (dt != null && dt.Rows.Count > 0)
                {
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        listView1.Items.Add(dt.Rows[i]["Question_ID"].ToString());
                        listView1.Items[i].SubItems.Add(dt.Rows[i]["Question_Title"].ToString());
                    }                                                           
                }
            }
        }                                                      

        private void listView1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            ListViewHitTestInfo listView = listView1.HitTest(e.X, e.Y);
            if (listView != null && listView.Item != null && QuestionDoubleClick != null)
            {
                if (CommonFunction.IsNumber(listView.Item.Text, 0))
                    QuestionDoubleClick(Convert.ToInt32(listView.Item.Text), ClientInfo_ID);
            }
        }                

        private void listView1_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
            if (listView1.Items.Count > 0 && listView1.SelectedItems != null && listView1.SelectedItems.Count > 0 && QuestionClick != null)
            {
                if (CommonFunction.IsNumber(listView1.SelectedItems[0].SubItems[0].Text, 0))
                    QuestionClick(Convert.ToInt32(listView1.SelectedItems[0].SubItems[0].Text));
                else
                    QuestionClick(0);
            }
            else
            {
                if (listView1.SelectedItems.Count == 0)
                    QuestionClick(0);
            }
        }                
    }
}
