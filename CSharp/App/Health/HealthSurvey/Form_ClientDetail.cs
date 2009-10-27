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
    public partial class Form_ClientDetail : Form_Class
    {
        public int ClientInfo_ID;
        private Form_Main form_main;

        public Form_ClientDetail(Form_Main Form_Parent)
        {
            form_main = Form_Parent;
            InitializeComponent();

            choiRadioButton1.Checked = true;
            choiRadioButton2.Checked = false;
        }

        public Form_ClientDetail(Form_Main Form_Parent, int C_ID)
        {
            form_main = Form_Parent;
            ClientInfo_ID = C_ID;

            InitializeComponent();

            choiRadioButton1.Checked = true;
            choiRadioButton2.Checked = false;
        }

        public void Show_ClientDetail()
        {
            if (ClientInfo_ID > 0)
            {
                Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                DataTable dt = function_clientInfo.Query_ClientInfo(ClientInfo_ID);
                if (dt != null && dt.Rows.Count > 0)
                {
                    label1.Visible = true;
                    label2.Visible = true;

                    label1.Text = dt.Rows[0]["ClientInfo_ID"].ToString();
                    choiTextBox1.Text = dt.Rows[0]["ClientInfo_Name"].ToString();
                    choiTextBox2.Text = dt.Rows[0]["ClientInfo_Age"].ToString();

                    if (dt.Rows[0]["ClientInfo_Sex"].ToString() == "1")
                    {
                        choiRadioButton1.Checked = true;
                        choiRadioButton2.Checked = false;
                    }
                    else
                    {
                        choiRadioButton1.Checked = false;
                        choiRadioButton2.Checked = true;
                    }

                    choiTextBox3.Text = dt.Rows[0]["ClientInfo_Weight"].ToString();
                    choiTextBox4.Text = dt.Rows[0]["ClientInfo_Height"].ToString();
                    choiTextBox5.Text = dt.Rows[0]["ClientInfo_Province"].ToString();
                    choiTextBox6.Text = dt.Rows[0]["ClientInfo_City"].ToString();
                    choiTextBox7.Text = dt.Rows[0]["ClientInfo_Address"].ToString();
                    choiTextBox8.Text = dt.Rows[0]["ClientInfo_Tel"].ToString();
                    choiTextBox9.Text = dt.Rows[0]["ClientInfo_Email"].ToString();
                    choiTextBox10.Text = dt.Rows[0]["ClientInfo_Zip"].ToString();
                }
            }
            else
            {
                label1.Visible = false;
                label2.Visible = false;
            }
        }

        private void choiButton1_Click(object sender, EventArgs e)
        {
            Class_ClientInfo class_clientInfo = CheckInput();
            if (class_clientInfo == null)
                return;

            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            if (function_clientInfo.Insert_ClientInfo(class_clientInfo))
                MessageBox.Show("添加客户" + class_clientInfo.ClientInfo_Name + "成功", "成功", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
            else
                MessageBox.Show("添加客户失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);

            ClearInput();
            form_main.ClientInfo_Refresh();
            form_main.ClientList_Refresh();
        }

        private void choiButton2_Click(object sender, EventArgs e)
        {
            if (ClientInfo_ID <= 0)
                return;

            Class_ClientInfo class_clientInfo = CheckInput();
            if (class_clientInfo == null)
                return;

            class_clientInfo.ClientInfo_ID = Convert.ToInt32(ClientInfo_ID);

            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            if (function_clientInfo.Update_ClientInfo(class_clientInfo))
                MessageBox.Show("更新客户" + class_clientInfo.ClientInfo_Name + "成功", "成功", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
            else
                MessageBox.Show("更新客户失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);

            ClearInput();
            form_main.ClientInfo_Refresh();
            form_main.ClientList_Refresh();
        }

        private void choiButton3_Click(object sender, EventArgs e)
        {
            if (ClientInfo_ID <= 0)
                return;

            if (MessageBox.Show("确定删除当前客户吗？", "提示", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
            {
                Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                if (function_clientInfo.Delete_ClientInfo(Convert.ToInt32(ClientInfo_ID)))
                    MessageBox.Show("删除成功", "成功", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                else
                    MessageBox.Show("删除失败", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);

                ClearInput();
                form_main.ClientInfo_Refresh();
                form_main.ClientList_Refresh();
            }
        }

        private void choiButton4_Click(object sender, EventArgs e)
        {
            form_main.ClientList_Search();
        }

        private Class_ClientInfo CheckInput()
        {
            Class_ClientInfo class_clientInfo = new Class_ClientInfo();

            //姓名
            if (String.IsNullOrEmpty(choiTextBox1.Text))
            {
                choiTextBox1.Focus();
                MessageBox.Show("请输入姓名", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
            class_clientInfo.ClientInfo_Name = choiTextBox1.Text;

            //年龄
            if (String.IsNullOrEmpty(choiTextBox2.Text))
            {
                choiTextBox2.Focus();
                MessageBox.Show("请输入年龄", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }

            if (!CommonFunction.IsNumber(choiTextBox2.Text, 1))
            {
                choiTextBox2.Focus();
                MessageBox.Show("请输入正确的年龄格式", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }
            class_clientInfo.ClientInfo_Age = Convert.ToUInt32(choiTextBox2.Text);

            //性别
            if (!choiRadioButton1.Checked && !choiRadioButton2.Checked)
            {
                MessageBox.Show("请选择性别", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;
            }

            if (choiRadioButton1.Checked)
                class_clientInfo.ClientInfo_Sex = "1";

            if (choiRadioButton2.Checked)
                class_clientInfo.ClientInfo_Sex = "0";

            //体重
            if (!String.IsNullOrEmpty(choiTextBox3.Text))
            {
                if (CommonFunction.IsNumber(choiTextBox3.Text, 2))
                    class_clientInfo.ClientInfo_Weight = Convert.ToSingle(choiTextBox3.Text);
                else
                {
                    MessageBox.Show("请输入正确的体重格式", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return null;
                }
            }

            //身高
            if (!String.IsNullOrEmpty(choiTextBox4.Text))
            {
                if (CommonFunction.IsNumber(choiTextBox4.Text, 2))
                    class_clientInfo.ClientInfo_Height = Convert.ToSingle(choiTextBox4.Text);
                else
                {
                    MessageBox.Show("请输入正确的身高格式", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return null;
                }
            }

            //省份
            if (!String.IsNullOrEmpty(choiTextBox5.Text))
                class_clientInfo.ClientInfo_Province = choiTextBox5.Text;

            //城市
            if (!String.IsNullOrEmpty(choiTextBox6.Text))
                class_clientInfo.ClientInfo_City = choiTextBox6.Text;

            //地址
            if (!String.IsNullOrEmpty(choiTextBox7.Text))
                class_clientInfo.ClientInfo_Address = choiTextBox7.Text;

            //电话
            if (!String.IsNullOrEmpty(choiTextBox8.Text))
                class_clientInfo.ClientInfo_Tel = choiTextBox8.Text;

            //电子邮件
            if (!String.IsNullOrEmpty(choiTextBox9.Text))
            {
                if (!CommonFunction.IsEmail(choiTextBox9.Text))
                {
                    MessageBox.Show("请输入正确的电子邮件格式", "出错", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return null;
                }

                class_clientInfo.ClientInfo_Email = choiTextBox9.Text;
            }

            //邮政编码
            if (!String.IsNullOrEmpty(choiTextBox10.Text))
                class_clientInfo.ClientInfo_Tel = choiTextBox10.Text;

            return class_clientInfo;
        }

        private void ClearInput()
        {
            label1.Visible = false;
            label2.Visible = false;
            label2.Text = "";
            choiTextBox1.Text = "";
            choiTextBox2.Text = "";
            choiTextBox3.Text = "";
            choiTextBox4.Text = "";
            choiTextBox5.Text = "";
            choiTextBox6.Text = "";
            choiTextBox7.Text = "";
            choiTextBox8.Text = "";
            choiTextBox9.Text = "";
            choiTextBox10.Text = "";
        }
    }
}
