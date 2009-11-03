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
using Utility;

namespace Web.Admin
{
    public partial class Login : Web.PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Session.Remove("AdminUser");
        }

        protected void Login_Submit_Click(object sender, EventArgs e)
        {
            if (!VerifyUtility.IsString_NotNull(AdminUser_Name.Text))                            
                ResponseError("请输入用户名");            

            if (!VerifyUtility.IsString_NotNull(AdminUser_PassWord.Text))                           
                ResponseError("请输入密码");            

            if (!VerifyUtility.IsString_NotNull(Code.Text))
                ResponseError("请输入验证码");  
            

            if (Session["Code"] == null || Code.Text != Session["Code"].ToString())
            {
                Session.Remove("Code");
                ResponseError("输入验证码错误");  
            }

            BLL.AdminUser b_AdminUser = new BLL.AdminUser();
            Entity.AdminUser e_AdminUser = b_AdminUser.Select_AdminUser(AdminUser_Name.Text, AdminUser_PassWord.Text);
            if (e_AdminUser == null)
            {
                Session.Remove("Code");               
                ResponseError("用户名，密码错误");  
            }
            else
            {
                Session["AdminUser"] = e_AdminUser;
                Response.Redirect("Main.aspx");
            }
        }
    }
}
