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
    public partial class ChangeLanguage : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Session["LanguageID"] == null)
                    Session["LanguageID"] = 2;
                else
                {
                    int i_LanguageID = Convert.ToInt32(Session["LanguageID"]);

                    switch (i_LanguageID)
                    {
                        case 1:
                            Session["LanguageID"] = 2;
                            break;

                        case 2:
                            Session["LanguageID"] = 1;
                            break;

                        default:
                            Session["LanguageID"] = 1;
                            break;
                    }
                }

                if (Request.UrlReferrer == null)
                    Response.Redirect("Index.aspx");
                else
                    Response.Redirect(Request.UrlReferrer.ToString());
            }
        }
    }
}
