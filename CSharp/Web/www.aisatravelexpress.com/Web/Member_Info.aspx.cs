using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Utility;

namespace Web
{
    public partial class Member_Info : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (g_Member == null)
                    ResponseClose("超时，重新登录");

                int g_Member_Info_ID = 1;
                if (VerifyUtility.IsNumber_NotNull(Request["Member_Info_ID"]))
                    g_Member_Info_ID = Convert.ToInt32(Request["Member_Info_ID"]);

                for (int i = 1; i <= 3; i++)
                {
                    Control o_Control = null;

                    o_Control = form1.FindControl("Li" + i.ToString());
                    if (o_Control != null)
                    {
                        HtmlControl o_HtmlControl = (HtmlControl)o_Control;
                        if (o_HtmlControl != null)
                        {
                            if (i == g_Member_Info_ID)
                                o_HtmlControl.Attributes["class"] = "win_pic_li1";
                            else
                                o_HtmlControl.Attributes["class"] = "win_pic_li2";
                        }
                    }

                    o_Control = form1.FindControl("Div" + i.ToString());
                    if (o_Control != null)
                    {
                        HtmlControl o_HtmlControl = (HtmlControl)o_Control;
                        if (o_HtmlControl != null)
                        {
                            if (i == g_Member_Info_ID)
                                o_HtmlControl.Attributes["class"] = "win_pic_txt";
                            else
                                o_HtmlControl.Attributes["class"] = "win_pic_txt2";
                        }
                    }
                }
                
                Member_Account.Text = g_Member.Member_Account;
                Member_Serial.Text = g_Member.Member_Serial;
                Member_Name_CN.Text = g_Member.Member_Name_CN;
                Member_Name_EN.Text = g_Member.Member_Name_EN;

                if (g_Member.Member_Sex)
                    Member_Male.Checked = true;
                else
                    Member_Female.Checked = true;

                Member_Work.Text = g_Member.Member_Work;
                Member_Tel.Text = g_Member.Member_Tel;
                Member_Mobile.Text = g_Member.Member_Mobile;
                Member_Email.Text = g_Member.Member_Email;
                Member_Address.Text = g_Member.Member_Address;
                Member_Company_Name.Text = g_Member.Member_Company_Name;
                Member_Company_Tel.Text = g_Member.Member_Company_Tel;
                Member_Company_Address.Text = g_Member.Member_Company_Address;
                Member_Airlines.Text = g_Member.Member_Airlines;

                if (g_Member.Member_Months != null)
                {
                    foreach (int Member_Months in g_Member.Member_Months)
                    {
                        Control o_Control = form1.FindControl("Member_Months_" + Member_Months.ToString());
                        if (o_Control != null)
                        {
                            CheckBox o_CheckBox = (CheckBox)o_Control;
                            if (o_CheckBox != null)
                                o_CheckBox.Checked = true;
                        }
                    }                    
                }

                Member_Account.ReadOnly = true;
                Member_Serial.ReadOnly = true;

                Member_Consumption_Account.Text = g_Member.Member_Account;
                Member_Consumption_Commission.Text = g_Member.Member_Commission.ToString();
                Member_Consumption_Times.Text = g_Member.Member_Times.ToString();
                Member_Consumption_Points.Text = g_Member.Member_Points.ToString();
                Member_Consumption_Consumption.Text = g_Member.Member_Consumption.ToString();
            }
        }

        protected void Member_Submit_Click(object sender, EventArgs e)
        {
            BLL.Member b_Member = new BLL.Member();

            if (!VerifyUtility.IsString_NotNull(Member_Name_CN.Text))
                ResponseError("请输入中文名");

            if (!VerifyUtility.IsString_NotNull(Member_Name_EN.Text))
                ResponseError("请输入英文名");

            if (!Member_Male.Checked && !Member_Female.Checked)
                ResponseError("请选择性别");

            if (!VerifyUtility.IsString_NotNull(Member_Work.Text))
                ResponseError("请输入工作类型");

            if (!VerifyUtility.IsString_NotNull(Member_Tel.Text))
                ResponseError("请输入电话");

            if (!VerifyUtility.IsString_NotNull(Member_Mobile.Text))
                ResponseError("请输入手机号码");

            if (!VerifyUtility.IsString_NotNull(Member_Email.Text))
                ResponseError("请输入电子邮件");

            if (g_Member.Member_Email != Member_Email.Text)
            {
                if (b_Member.Check_Email(Member_Email.Text))
                    ResponseError(Member_Email.Text + "已经注册过");
            }

            if (!VerifyUtility.IsString_NotNull(Member_Address.Text))
                ResponseError("请输入居住地址");

            if (!VerifyUtility.IsString_NotNull(Member_Company_Name.Text))
                ResponseError("请输入公司名称");

            if (!VerifyUtility.IsString_NotNull(Member_Company_Tel.Text))
                ResponseError("请输入公司电话");

            if (!VerifyUtility.IsString_NotNull(Member_Company_Address.Text))
                ResponseError("请输入公司地址");

            if (!VerifyUtility.IsString_NotNull(Member_Airlines.Text))
                ResponseError("请输入常用航空公司");

            g_Member.Member_Name_CN = Member_Name_CN.Text;
            g_Member.Member_Name_EN = Member_Name_EN.Text;

            if (Member_Male.Checked)
                g_Member.Member_Sex = true;
            else
                g_Member.Member_Sex = false;

            g_Member.Member_Work = Member_Work.Text;
            g_Member.Member_Tel = Member_Tel.Text;
            g_Member.Member_Mobile = Member_Mobile.Text;
            g_Member.Member_Email = Member_Email.Text;
            g_Member.Member_Address = Member_Address.Text;
            g_Member.Member_Company_Name = Member_Company_Name.Text;
            g_Member.Member_Company_Tel = Member_Company_Tel.Text;
            g_Member.Member_Company_Address = Member_Company_Address.Text;

            g_Member.Member_Months = null;
            for (int i = 1; i <= 12; i++)
            {
                Control o_Control = form1.FindControl("Member_Months_" + i.ToString());
                if (o_Control != null)
                {
                    CheckBox o_CheckBox = (CheckBox)o_Control;
                    if (o_CheckBox.Checked)
                    {
                        if (g_Member.Member_Months == null)
                            g_Member.Member_Months = new List<int>();

                        g_Member.Member_Months.Add(i);
                    }
                }
            }

            g_Member.Member_Airlines = Member_Airlines.Text;

            b_Member.Update_Member(g_Member);
            ResponseSuccess("更新资料成功");
        }

        protected void Member_PassWord_Submit_Click(object sender, EventArgs e)
        {
            BLL.Member b_Member = new BLL.Member();

            if (!VerifyUtility.IsString_NotNull(Member_PassWord.Text))
                ResponseError("请输入旧密码");

            if (!VerifyUtility.IsString_NotNull(Member_PassWord1.Text))
                ResponseError("请输入新密码");

            if (!VerifyUtility.IsString_NotNull(Member_PassWord2.Text))
                ResponseError("请确认新密码");

            if (Member_PassWord.Text != g_Member.Member_PassWord)
                ResponseError("输入旧密码不正确");

            if (Member_PassWord1.Text != Member_PassWord2.Text)
                ResponseError("确认密码输入不正确");

            g_Member.Member_PassWord = Member_PassWord1.Text;

            b_Member.Update_Member(g_Member);
            ResponseSuccess("更新密码成功，请重新登录");
        }
    }
}
