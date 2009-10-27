using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ChoiControls;

namespace HealthSurvey
{
    public partial class Form_Search : ChoiForm
    {
        public DataTable ClientInfo_Table = null;     

        public Form_Search()
        {
            InitializeComponent();
        }

        private void choiButton1_Click(object sender, EventArgs e)
        {
            Class_ClientInfo class_clientInfo = new Class_ClientInfo();

            if (!String.IsNullOrEmpty(choiTextBox1.Text) && CommonFunction.IsNumber(choiTextBox1.Text, 1))
                class_clientInfo.ClientInfo_ID = Convert.ToInt32(choiTextBox1.Text);

            if (!String.IsNullOrEmpty(choiTextBox2.Text))
                class_clientInfo.ClientInfo_Name = choiTextBox2.Text;

            if (!String.IsNullOrEmpty(choiTextBox3.Text) && CommonFunction.IsNumber(choiTextBox3.Text, 1))
                class_clientInfo.ClientInfo_Age = Convert.ToUInt32(choiTextBox3.Text);

            if (choiRadioButton1.Checked)
                class_clientInfo.ClientInfo_Sex = "1";

            if (choiRadioButton2.Checked)
                class_clientInfo.ClientInfo_Sex = "0";

            if (!String.IsNullOrEmpty(choiTextBox4.Text) && CommonFunction.IsNumber(choiTextBox4.Text, 2))
                class_clientInfo.ClientInfo_Weight = Convert.ToSingle(choiTextBox4.Text);

            if (!String.IsNullOrEmpty(choiTextBox5.Text) && CommonFunction.IsNumber(choiTextBox5.Text, 2))
                class_clientInfo.ClientInfo_Height = Convert.ToSingle(choiTextBox5.Text);

            if (!String.IsNullOrEmpty(choiTextBox6.Text))
                class_clientInfo.ClientInfo_Province = choiTextBox6.Text;

            if (!String.IsNullOrEmpty(choiTextBox7.Text))
                class_clientInfo.ClientInfo_City = choiTextBox7.Text;

            if (!String.IsNullOrEmpty(choiTextBox8.Text))
                class_clientInfo.ClientInfo_Address = choiTextBox8.Text;

            if (!String.IsNullOrEmpty(choiTextBox9.Text))
                class_clientInfo.ClientInfo_Tel = choiTextBox9.Text;

            if (!String.IsNullOrEmpty(choiTextBox10.Text))
                class_clientInfo.ClientInfo_Email = choiTextBox10.Text;

            if (!String.IsNullOrEmpty(choiTextBox11.Text))
                class_clientInfo.ClientInfo_Zip = choiTextBox11.Text;

            Function_ClientInfo fc = new Function_ClientInfo();
            ClientInfo_Table = fc.Query_ClientInfo(class_clientInfo);

            Close();
        }

        private void choiButton2_Click(object sender, EventArgs e)
        {
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
            choiTextBox11.Text = "";
        }
    }
}
