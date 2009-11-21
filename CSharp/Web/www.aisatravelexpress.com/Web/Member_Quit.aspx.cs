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

namespace Web
{
    public partial class Member_Quit : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                Session.Remove("Member");

                if (Request.UrlReferrer == null)
                    Response.Redirect("Index.aspx");
                else
                    Response.Redirect(Request.UrlReferrer.ToString());
            }
        }
    }
}
