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

        protected void LowFare_Type_CheckedChanged(object sender, EventArgs e)
        {
            if (LowFare_Type1.Checked)
            {
                LowFare_Text_Type1.Visible = true;
                LowFare_Text_Type2.Visible = false;
                LowFare_Text_Type3.Visible = false;

                LowFare_Flexibility_TD.Visible = true;
            }

            if (LowFare_Type2.Checked)
            {
                LowFare_Text_Type1.Visible = false;
                LowFare_Text_Type2.Visible = true;
                LowFare_Text_Type3.Visible = false;

                LowFare_Flexibility_TD.Visible = true;
            }

            if (LowFare_Type3.Checked)
            {
                LowFare_Text_Type1.Visible = false;
                LowFare_Text_Type2.Visible = false;
                LowFare_Text_Type3.Visible = true;

                LowFare_Flexibility_TD.Visible = false;
            }
        }

        protected void LowFare_Submit_Click(object sender, EventArgs e)
        {

        }
    }
}
