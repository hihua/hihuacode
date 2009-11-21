using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

namespace Web.Controls
{
    public partial class Login : System.Web.UI.UserControl
    {
        protected Entity.Member g_Member = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Member"] != null)
            {
                BLL.Member b_Member = new BLL.Member();

                g_Member = Session["Member"] as Entity.Member;

                Member_Reg.Visible = false;
                Member_Record.Visible = true;

                Member_Name_CN.InnerText = g_Member.Member_Name_CN;
                if (g_Member.Member_Sex)
                    Member_Name_CN.InnerText += "先生";
                else
                    Member_Name_CN.InnerText += "女士";

                Member_Account.InnerText = g_Member.Member_Account;
                Member_Points.InnerText = g_Member.Member_Points.ToString();
                Member_Level.InnerText = b_Member.Show_Member_Level(g_Member.Member_Level);
            }
            else
            {
                Member_Reg.Visible = true;
                Member_Record.Visible = false;
            }
        }

        protected void Member_Account_Submit_Click(object sender, EventArgs e)
        {
            BLL.Member b_Member = new BLL.Member();
            Entity.Member o_Member = b_Member.Select_Member(Member_Account_Name.Text, Member_Account_PassWord.Text);
            if (o_Member == null)
            {
                Response.Write("<script type=\"text/javascript\">alert(\"帐号或密码错误\");window.history.back();</script>");
                Response.End();
            }
            else
            {
                Session["Member"] = o_Member;

                if (Request.UrlReferrer == null)
                    Response.Redirect("Index.aspx");
                else
                    Response.Redirect(Request.UrlReferrer.ToString());
            }
        }
    }
}