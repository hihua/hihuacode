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
    public partial class Form_ClientList : Form_Class
    {
        public Form_ClientList()
        {
            InitializeComponent();            
        }        
       
        private void Form_ClientList_VisibleChanged(object sender, EventArgs e)
        {
            if (base.Visible)
            {
                dataGridView_Refresh();
            }
        }

        private void dataGridView1_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (e.ColumnIndex == 3)
            {
                if (e.Value != null)
                {
                    if (e.Value.ToString() == "1")
                        e.Value = "男";
                    else
                        e.Value = "女";                    
                }                
            }
        }

        private void dataGridView_SetColumns()
        {
            dataGridView1.Columns[0].HeaderText = "编号";
            dataGridView1.Columns[1].HeaderText = "姓名";
            dataGridView1.Columns[2].HeaderText = "年龄";
            dataGridView1.Columns[3].HeaderText = "性别";
            dataGridView1.Columns[4].HeaderText = "体重";
            dataGridView1.Columns[5].HeaderText = "身高";
            dataGridView1.Columns[6].HeaderText = "省份";
            dataGridView1.Columns[7].HeaderText = "城市";
            dataGridView1.Columns[8].HeaderText = "通讯地址";
            dataGridView1.Columns[9].HeaderText = "联系电话";
            dataGridView1.Columns[10].HeaderText = "电子邮件";
            dataGridView1.Columns[11].HeaderText = "邮政编码";
            dataGridView1.Columns[12].Visible = false;
        }

        public void dataGridView_Refresh()
        {
            if (dataGridView1.Rows.Count > 0)
            {
                for (int i = 0; i < dataGridView1.Rows.Count; i++)
                {
                    dataGridView1.Rows.RemoveAt(i);
                }
            }

            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            DataTable dt = function_clientInfo.Query_ClientInfo();
            if (dt != null)
            {
                dataGridView1.DataSource = dt;
                dataGridView_SetColumns();
            }
        }

        public void dataGridView_Refresh(DataTable ClientList_Table)
        {
            if (dataGridView1.Rows.Count > 0)
            {
                for (int i = 0; i < dataGridView1.Rows.Count; i++)
                {
                    dataGridView1.Rows.RemoveAt(i);
                }
            }

            dataGridView1.DataSource = ClientList_Table;
            dataGridView_SetColumns();
            dataGridView1.Refresh();
        }

        public void dataGridView_Refresh(int ClientInfo_ID)
        {
            for (int i = 0; i < dataGridView1.Rows.Count; i++)
            {
                if (dataGridView1.Rows[i].Cells[0].Value != null && !String.IsNullOrEmpty(dataGridView1.Rows[i].Cells[0].Value.ToString()))
                {
                    if (dataGridView1.Rows[i].Cells[0].Value.ToString() == ClientInfo_ID.ToString())
                    {
                        dataGridView1.Rows[i].Selected = true;
                        break;
                    }
                }
            }
        }
    }
}
