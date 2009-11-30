using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Text;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Utility;

namespace Web.Admin
{
    public partial class Member_Detail : PageBase
    {
        private BLL.Member b_Member;

        protected void Page_Load(object sender, EventArgs e)
        {
            b_Member = new BLL.Member();

            if (!IsPostBack)
            {
                switch (g_Action_ID)
                {
                    case 2:
                        if (g_Member_ID == 0)
                            ResponseError("参数错误");

                        GetMember();
                        break;

                    case 3:
                        if (g_Member_ID == 0)
                            ResponseError("参数错误");

                        b_Member.Delete_Member(g_Member_ID);
                        ResponseClose("删除成功");
                        break;

                    case 4:
                        if (g_Member_ID == 0)
                            ResponseError("参数错误");

                        ToVIP();
                        ResponseClose("转换成功");
                        break;
                }
            }
        }

        private void GetMember()
        {            
            Entity.Member o_Member = b_Member.Select_Member(g_Member_ID);

            if (o_Member != null)
            {
                Member_Account.Text = o_Member.Member_Account;
                Member_Serial.Text = o_Member.Member_Serial;
                Member_Name_CN.Text = o_Member.Member_Name_CN;
                Member_Name_EN.Text = o_Member.Member_Name_EN;

                if (o_Member.Member_Sex)
                    Member_Male.Checked = true;
                else
                    Member_Female.Checked = true;

                Member_Work.Text = o_Member.Member_Work;
                Member_Tel.Text = o_Member.Member_Tel;
                Member_Mobile.Text = o_Member.Member_Mobile;
                Member_Email.Text = o_Member.Member_Email;
                Member_Address.Text = o_Member.Member_Address;
                Member_Company_Name.Text = o_Member.Member_Company_Name;
                Member_Company_Tel.Text = o_Member.Member_Company_Tel;
                Member_Company_Address.Text = o_Member.Member_Company_Address;
                Member_Airlines.Text = o_Member.Member_Airlines;

                if (o_Member.Member_Months != null)
                {
                    foreach (int Member_Months in o_Member.Member_Months)
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

                Member_Consumption_Account.Text = o_Member.Member_Account;
                Member_Consumption_Commission.Text = o_Member.Member_Commission.ToString();
                Member_Consumption_Times.Text = o_Member.Member_Times.ToString();
                Member_Consumption_Points.Text = o_Member.Member_Points.ToString();
                Member_Consumption_Consumption.Text = o_Member.Member_Consumption.ToString();

                if (o_Member.Member_Recommended > 0)
                {
                    Entity.Member c_Member = b_Member.Select_Member(o_Member.Member_Recommended);
                    if (c_Member != null)
                    {
                        Member_ReSerial.NavigateUrl = "?Action_ID=2&Member_ID=" + c_Member.Member_ID;
                        Member_ReSerial.Text = c_Member.Member_Account;
                    }
                    else
                        Member_ReSerial.Visible = false;
                }
                else
                    Member_ReSerial.Visible = false;

                BLL.Consumption b_Consumption = new BLL.Consumption();
                Entity.Consumption[] e_Consumption = b_Consumption.Select_Consumption(o_Member.Member_Account, 9, 0x7FFFFFFF, 1);

                if (e_Consumption != null)
                {
                    StringBuilder o_InnerHtml = new StringBuilder();
                    int i = 0;

                    foreach (Entity.Consumption o_Consumption in e_Consumption)
                    {
                        string o_CssClass = "";

                        if (i % 2 == 0)
                            o_CssClass = " win_pic_txtdl_none";

                        o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:84px\">" + o_Consumption.Consumption_Date.ToString("yyyy-MM-dd") + "</dt>");

                        switch (o_Consumption.Consumption_Type)
                        {
                            case 1:
                                o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:48px\">单程</dt>");
                                break;

                            case 2:
                                o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:48px\">双程</dt>");
                                break;

                            case 3:
                                o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:48px\">多程</dt>");
                                break;

                            default:
                                o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:48px\">&nbsp;</dt>");
                                break;
                        }

                        o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:84px\">" + o_Consumption.Consumption_Src + "</dt>");
                        o_InnerHtml.Append("<dt class=\"win_pic_txtdl3" + o_CssClass + "\">" + o_Consumption.Consumption_Dest + "</dt>");
                        o_InnerHtml.Append("<dt class=\"win_pic_txtdl4" + o_CssClass + "\" style=\"width:48px\">" + o_Consumption.Consumption_Price.ToString() + "</dt>");
                        o_InnerHtml.Append("<dt class=\"win_pic_txtdl3" + o_CssClass + "\" style=\"width:84px\">" + o_Consumption.Consumption_Points.ToString() + "</dt>");

                        i++;
                    }

                    if (VerifyUtility.IsString_NotNull(o_InnerHtml.ToString()))
                        Member_Consumption_List.InnerHtml += o_InnerHtml.ToString();
                }
            }
        }

        private void ToVIP()
        {
            Entity.Member o_Member = b_Member.Select_Member(g_Member_ID);

            if (o_Member != null)
            {
                o_Member.Member_Level = 3;
                b_Member.Update_Member(o_Member);
            }
        }
    }
}
