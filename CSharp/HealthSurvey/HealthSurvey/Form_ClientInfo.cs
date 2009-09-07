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
    public partial class Form_ClientInfo : Form_Class
    {
        public event Form_ClientInfoClick_Handler ClientInfoClick;
        public event Form_ClientInfoDoubleClick_Handler ClientInfoDoubleClick;
        public event Form_ClientInfoRefresh_Handler ClientInfoRefresh;
        public event Form_ClientListRefresh_Handler ClientListRefresh;
        public event Form_ClientListSearch_Handler ClientListSearch;

        public Form_ClientInfo()
        {
            InitializeComponent();
        }

        private void Form_ClientInfo_VisibleChanged(object sender, EventArgs e)
        {
            if (base.Visible)
            {
                listView_Refresh();
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listView1.Items.Count > 0 && listView1.SelectedItems != null && listView1.SelectedItems.Count > 0 && ClientInfoClick != null)
            {
                if (CommonFunction.IsNumber(listView1.SelectedItems[0].SubItems[0].Text, 0))
                    ClientInfoClick(Convert.ToInt32(listView1.SelectedItems[0].SubItems[0].Text));
            }
        }

        private void listView1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            ListViewHitTestInfo listView = listView1.HitTest(e.X, e.Y);
            if (listView != null && listView.Item != null && ClientInfoDoubleClick != null)
            {
                if (CommonFunction.IsNumber(listView.Item.Text, 0))
                    ClientInfoDoubleClick(Convert.ToInt32(listView.Item.Text));
            }
        }

        private void listView1_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                ListViewHitTestInfo listView = listView1.HitTest(e.X, e.Y);
                if (listView != null && listView.Item != null)
                    listView.Item.Selected = true;

                ContextMenuStrip contextMenuStrip = new ContextMenuStrip();
                contextMenuStrip.Items.Add("添加");
                contextMenuStrip.Items.Add("删除");
                contextMenuStrip.Items.Add("修改");
                contextMenuStrip.Items.Add("查询");

                contextMenuStrip.Items[0].Click += new EventHandler(contextMenuStrip_Add);
                contextMenuStrip.Items[1].Click += new EventHandler(contextMenuStrip_Del);
                contextMenuStrip.Items[2].Click += new EventHandler(contextMenuStrip_Fix);
                contextMenuStrip.Items[3].Click += new EventHandler(contextMenuStrip_Query);

                if (listView1.SelectedItems == null || listView1.SelectedItems.Count <= 0 || String.IsNullOrEmpty(listView1.SelectedItems[0].Text))
                {
                    contextMenuStrip.Items[1].Enabled = false;
                    contextMenuStrip.Items[2].Enabled = false;
                }

                contextMenuStrip.Show(listView1, new Point(e.X, e.Y));
            }
        }

        public void listView_Refresh()
        {
            listView1.Items.Clear();
            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            DataTable dt = function_clientInfo.Query_ClientInfo();
            if (dt != null && dt.Rows.Count > 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    listView1.Items.Add(dt.Rows[i]["ClientInfo_ID"].ToString());
                    listView1.Items[i].SubItems.Add(dt.Rows[i]["ClientInfo_Name"].ToString());
                }

                if (listView1.Items.Count > 0)
                {
                    listView1.Items[0].Selected = true;
                }
            }
        }

        private void contextMenuStrip_Add(object sender, EventArgs e)
        {
            ClientInfoDoubleClick(0);
        }

        private void contextMenuStrip_Del(object sender, EventArgs e)
        {
            if (MessageBox.Show("确定删除当前客户吗？", "提示", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
            {
                Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                if (function_clientInfo.Delete_ClientInfo(Convert.ToInt32(listView1.SelectedItems[0].SubItems[0].Text)))
                    MessageBox.Show("删除成功", "成功", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                else
                    MessageBox.Show("删除失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);

                ClientInfoRefresh();
                ClientListRefresh();
            }
        }

        private void contextMenuStrip_Fix(object sender, EventArgs e)
        {
            if (CommonFunction.IsNumber(listView1.SelectedItems[0].SubItems[0].Text, 0))
                ClientInfoDoubleClick(Convert.ToInt32(listView1.SelectedItems[0].SubItems[0].Text));
        }

        private void contextMenuStrip_Query(object sender, EventArgs e)
        {
            ClientListSearch();
        }
    }
}
