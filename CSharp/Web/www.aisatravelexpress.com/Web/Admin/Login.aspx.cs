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

using BLL;
using Entity;

namespace Web.Admin
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Session.Remove("AdminUser");
        }

        protected void Login_Submit_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(AdminUser_Name.Text))
            {
                Response.Write("<script type=\"text/javascript\">alert(\"请输入用户名\");window.history.back();</script>");
                Response.End();
            }

            if (string.IsNullOrEmpty(AdminUser_PassWord.Text))
            {
                Response.Write("<script type=\"text/javascript\">alert(\"请输入密码\");window.history.back();</script>");
                Response.End();
            }

            if (string.IsNullOrEmpty(Code.Text))
            {
                Response.Write("<script type=\"text/javascript\">alert(\"请输入验证码\");window.history.back();</script>");
                Response.End();
            }

            if (Session["Code"] == null || Code.Text != Session["Code"].ToString())
            {
                Session.Remove("Code");
                Response.Write("<script type=\"text/javascript\">alert(\"输入验证码错误\");window.history.back();</script>");
                Response.End();
            }

            BLL.AdminUser b_AdminUser = new BLL.AdminUser();
            Entity.AdminUser e_AdminUser = b_AdminUser.Select_AdminUser(AdminUser_Name.Text, AdminUser_PassWord.Text);
            if (e_AdminUser == null)
            {
                Session.Remove("Code");
                Response.Write("<script type=\"text/javascript\">alert(\"用户名，密码错误\");window.history.back();</script>");
                Response.End();
            }
            else
            {
                Session["AdminUser"] = e_AdminUser;
                Response.Redirect("Main.aspx");
            }
        }
    }
}
