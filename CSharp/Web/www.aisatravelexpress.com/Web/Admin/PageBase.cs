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

namespace Web.Admin
{
    public class PageBase : System.Web.UI.Page
    {
        protected AdminUser g_AdminUser;

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);
            if (Session["AdminUser"] == null)
            {
                Response.Write("<script type=\"text/javascript\">alert(\"超时请重新登录\");window.top.location.href=\"Login.aspx\";</script>");
                Response.End();
            }
            else
                g_AdminUser = Session["AdminUser"] as AdminUser;
        }

        protected override void OnError(EventArgs e)
        {
            base.OnError(e);            
        }
    }
}
