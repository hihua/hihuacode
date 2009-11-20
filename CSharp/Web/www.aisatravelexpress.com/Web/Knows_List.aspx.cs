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
    public partial class Knows_List : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkKnows(HyperLink_Knows);

                if (g_Knows_ClassID == 1 || g_Knows_ClassID == 2)
                    SetHyperLinkKnowsClass(HyperLink_Knows_ClassID_1, HyperLink_Knows_ClassID_2, Label_Knows_ClassID);
                else
                {
                    HyperLink_Knows_ClassID_1.Visible = false;
                    HyperLink_Knows_ClassID_2.Visible = false;
                    Label_Knows_ClassID.Visible = false;
                }

                BLL.Knows b_Knows = new BLL.Knows();
                Entity.Knows[] e_Knows = b_Knows.Select_Knows(g_Knows_ClassID, g_LanguageID, 7, g_Page);

                if (e_Knows != null)
                {
                    foreach (Entity.Knows o_Knows in e_Knows)
                    {
                        HtmlGenericControl o_Div = new HtmlGenericControl("div");
                        o_Div.Attributes.Add("class", "inside3_news");

                        HtmlGenericControl o_H3 = new HtmlGenericControl("h3");
                        HtmlGenericControl o_Strong = new HtmlGenericControl("strong");
                        HtmlAnchor o_Anchor = new HtmlAnchor();
                        o_Anchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                        o_Anchor.InnerText = o_Knows.Knows_Title;
                        o_Anchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                        HtmlGenericControl o_Span = new HtmlGenericControl("span");
                        o_Span.Style.Add(HtmlTextWriterStyle.Color, "#636363");
                        o_Span.InnerText = "(" + o_Knows.Knows_AddTime.ToShortDateString() + ")";
                        o_Strong.Controls.Add(o_Anchor);
                        o_Strong.Controls.Add(o_Span);
                        o_H3.Controls.Add(o_Strong);
                        o_Div.Controls.Add(o_H3);
                                                
                        Knows_Lists.Controls.Add(o_Div);
                    }
                }
            }
        }
    }
}

