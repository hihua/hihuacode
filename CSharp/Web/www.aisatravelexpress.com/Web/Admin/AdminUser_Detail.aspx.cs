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

using Entity;
using Utility;

namespace Web.Admin
{
    public partial class AdminUser_Detail : PageBase
    {
        protected int AdminUser_ID = 0;
        private Entity.AdminUser e_AdminUser;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (VerifyUtility.IsNumber_NotNull(Request["AdminUser_ID"]))
                    AdminUser_ID = Convert.ToInt32(Request["AdminUser_ID"]);

                if (AdminUser_ID == 0)
                {
                    AdminUser_AddTime.Visible = false;
                }
                else
                {
                    BLL.AdminUser b_AdminUser = new BLL.AdminUser();
                    e_AdminUser = b_AdminUser.Select_AdminUser(AdminUser_ID);

                    if (e_AdminUser != null)
                    {
                        AdminUser_Name.Text = e_AdminUser.AdminUser_Name;
                        AdminUser_NickName.Text = e_AdminUser.AdminUser_NickName;

                        if (e_AdminUser.AdminUser_Status == 0)
                        {
                            AdminUser_Status1.Checked = true;
                            AdminUser_Status2.Checked = false;
                        }

                        AdminUser_AddTime.InnerHtml += e_AdminUser.AdminUser_AddTime.ToString();
                        AdminUser_Submit.Text = " 修改 ";
                    }
                }
            }
        }

        protected void AdminUser_Submit_Click(object sender, EventArgs e)
        {

        }
    }
}
