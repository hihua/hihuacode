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
    public partial class LowFare : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkLowFare(HyperLink_LowFare);

                if (g_Member == null)
                {
                    LowFare_Tips.Visible = true;
                    LowFare_Content.Visible = false;
                }
                else
                {
                    LowFare_Tips.Visible = false;
                    LowFare_Content.Visible = true;
                }
            }
        }
    }
}
