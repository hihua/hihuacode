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

namespace Web
{
    public partial class ChangeLanguage : PageBase
    {
        private int g_Language = 1;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (VerifyUtility.IsNumber_NotNull(Request["Language"]) && Request["Language"] != "0")
                    g_Language = Convert.ToInt32(Request["Language"]);

                switch (g_Language)
                {
                    case 1:
                        Session["LanguageID"] = g_Language;
                        break;

                    case 2:
                        Session["LanguageID"] = g_Language;
                        break;

                    default:
                        Session["LanguageID"] = 1;
                        break;
                }
                

                if (Request.UrlReferrer == null)
                    Response.Redirect("Index.aspx");
                else
                    Response.Redirect(Request.UrlReferrer.ToString());
            }
        }
    }
}
