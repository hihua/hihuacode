using System;
using System.Data;
using System.Configuration;
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
    public class PageBase : Web.PageBase
    {
        protected Entity.AdminUser g_AdminUser;
        protected int g_Page = 1;
        protected int g_TotalCount;
        protected int g_TotalPage;

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);
            if (Session["AdminUser"] == null)                            
                ResponseError("超时请重新登录", "Login.aspx", 1);            
            else
                g_AdminUser = Session["AdminUser"] as Entity.AdminUser;

            if (VerifyUtility.IsNumber_NotNull(Request["Page"]))
                g_Page = Convert.ToInt32(Request["Page"]);            
        }

        protected override void OnError(EventArgs e)
        {
            base.OnError(e);            
        }                
    }
}
