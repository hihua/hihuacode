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
    public partial class Form_AnswerInfo : Form_Class
    {
        public Form_AnswerInfo()
        {
            InitializeComponent();
        }                

        private void dataGridView1_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {

        }

        private void dataGridView1_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            MessageBox.Show(dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString());
        }

        public void dataGridView1_Refresh(int Question_ID)
        {
            Function_AnswerInfo function_answerInfo = new Function_AnswerInfo();

            DataTable dt;
            if (Question_ID == 0)
                dt = function_answerInfo.Query_AnswerInfo();
            else
                dt = function_answerInfo.Query_AnswerInfo(Question_ID);

            if (dt != null)
            {
                dataGridView1.DataSource = dt;
                dataGridView1.Columns[0].HeaderText = "答题编号";
                dataGridView1.Columns[1].HeaderText = "客户编号";
                dataGridView1.Columns[2].HeaderText = "姓名";
                dataGridView1.Columns[3].HeaderText = "问卷";
                dataGridView1.Columns[4].HeaderText = "答题时间";
            }            
        }                
    }
}
