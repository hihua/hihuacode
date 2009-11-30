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
    public partial class Member_Reg : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Member_Submit_Click(object sender, EventArgs e)
        {
            BLL.Member b_Member = new BLL.Member();

            if (!VerifyUtility.IsString_NotNull(Member_Account.Text))
                ResponseError("请输入帐号");

            if (b_Member.Check_Account(Member_Account.Text))
                ResponseError(Member_Account.Text + "已经注册过");

            if (!VerifyUtility.IsString_NotNull(Member_PassWord1.Text))
                ResponseError("请输入密码");

            if (!VerifyUtility.IsString_NotNull(Member_PassWord2.Text))
                ResponseError("请输入确认密码");

            if (Member_PassWord1.Text != Member_PassWord2.Text)
                ResponseError("请输入确认密码输入不正确");

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

            if (b_Member.Check_Email(Member_Email.Text))
                ResponseError(Member_Email.Text + "已经注册过");

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

            Entity.Member o_Member = new Entity.Member();

            int Member_Recommended = 0;
            if (VerifyUtility.IsString_NotNull(Member_Serial.Text))
            {
                Entity.Member e_Member = b_Member.Select_Member(2, Member_Serial.Text);
                if (e_Member == null)
                    ResponseError("没有该会员号");
                else
                {
                    Member_Recommended = e_Member.Member_ID;
                    o_Member.Member_ReSerial = Member_Serial.Text;
                }
            }
            else
                o_Member.Member_ReSerial = "";
                        
            o_Member.Member_Account = Member_Account.Text;
            o_Member.Member_PassWord = Member_PassWord1.Text;
            o_Member.Member_Name_CN = Member_Name_CN.Text;
            o_Member.Member_Name_EN = Member_Name_EN.Text;

            if (Member_Male.Checked)
                o_Member.Member_Sex = true;
            else
                o_Member.Member_Sex = false;

            o_Member.Member_Work = Member_Work.Text;
            o_Member.Member_Tel = Member_Tel.Text;
            o_Member.Member_Mobile = Member_Mobile.Text;
            o_Member.Member_Email = Member_Email.Text;
            o_Member.Member_Address = Member_Address.Text;
            o_Member.Member_Company_Name = Member_Company_Name.Text;
            o_Member.Member_Company_Tel = Member_Company_Tel.Text;
            o_Member.Member_Company_Address = Member_Company_Address.Text;
            
            for (int i = 1; i <= 12; i++)
            {
                Control o_Control = form1.FindControl("Member_Months_" + i.ToString());
                if (o_Control != null)
                {
                    CheckBox o_CheckBox = (CheckBox)o_Control;
                    if (o_CheckBox.Checked)
                    {
                        if (o_Member.Member_Months == null)
                            o_Member.Member_Months = new List<int>();

                        o_Member.Member_Months.Add(i);
                    }
                }
            }

            o_Member.Member_Airlines = Member_Airlines.Text;
            o_Member.Member_Serial = b_Member.Random_Member();
            o_Member.Member_Points = 0;
            o_Member.Member_Commission = 0;
            o_Member.Member_Consumption = 0;
            o_Member.Member_Times = 0;
            o_Member.Member_Recommended = Member_Recommended;            
            o_Member.Member_Level = 1;
            o_Member.Member_AddTime = DateTime.Now;

            b_Member.Insert_Member(o_Member);

            ResponseSuccess("注册成功", "Member_Reg.aspx");
        }
    }
}
