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

using Utility;

namespace Web.Admin
{
    public partial class AdminUser_Delete : PageBase
    {
        private int AdminUser_ID = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (!VerifyUtility.IsNumber_NotNull(Request["AdminUser_ID"]))                
                    ResponseError("参数错误");                

                AdminUser_ID = Convert.ToInt32(Request["AdminUser_ID"]);

                BLL.AdminUser b_AdminUser = new BLL.AdminUser();
                b_AdminUser.Delete_AdminUser(AdminUser_ID);

                if (AdminUser_ID == g_AdminUser.AdminUser_ID)                
                    ResponseSuccess("删除成功，重新登录", "Login.aspx", 1);                
                else
                    ResponseSuccess("删除成功", "AdminUser.aspx");
                
            }
        }
    }
}
