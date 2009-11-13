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
    public partial class Top : System.Web.UI.UserControl
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Session["LanguageID"] != null)
                {
                    int i_LanguageID = Convert.ToInt32(Session["LanguageID"]);

                    switch (i_LanguageID)
                    {
                        case 1:
                            ChangeLanguage.NavigateUrl += "?Language=2";
                            ChangeLanguage.Text = "Engilsh";
                            break;

                        case 2:
                            ChangeLanguage.NavigateUrl += "?Language=1";
                            ChangeLanguage.Text = "中文";
                            break;

                        default:
                            ChangeLanguage.NavigateUrl += "?Language=2";
                            ChangeLanguage.Text = "Engilsh";
                            break;
                    }
                }
                else
                {
                    ChangeLanguage.NavigateUrl += "?Language=2";
                    ChangeLanguage.Text = "Engilsh";
                }
            }
        }
    }
}