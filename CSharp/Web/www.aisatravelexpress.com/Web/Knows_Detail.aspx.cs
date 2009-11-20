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
    public partial class Knows_Detail : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);

                BLL.Knows b_Knows = new BLL.Knows();
                Entity.Knows e_Knows = b_Knows.Select_Knows(g_Knows_ID);

                if (e_Knows != null)
                {
                    g_Knows_ClassID = e_Knows.Knows_ClassID;
                    SetHyperLinkKnows(HyperLink_Knows);
                    SetHyperLinkKnowsClass(HyperLink_Knows_Class, null, null);

                    Knows_Content.InnerHtml += "<h1><strong>" + e_Knows.Knows_Title + "</strong></h1>";
                    Knows_Content.InnerHtml += e_Knows.Knows_Content;
                }
            }
        }
    }
}

