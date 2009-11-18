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
    public partial class Travel : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);                
                SetHyperLinkTravel(HyperLink_Travel, HyperLink_Travel_TypeID);
                SetHyperLinkTravelType(HyperLink_Travel_TypeID_1, HyperLink_Travel_TypeID_2);

                BLL.Travel b_Travel = new BLL.Travel();
                Entity.Travel[] e_Travel = b_Travel.Select_Travel(g_LanguageID, g_Travel_TypeID, "", 0, 7, g_Page);

                if (e_Travel != null)
                {
                    int i = 1;

                    foreach (Entity.Travel o_Travel in e_Travel)
                    {
                        HtmlGenericControl o_Div = new HtmlGenericControl("div");

                        if (i % 2 == 1)
                            o_Div.Attributes.Add("class", "inside_content_news");
                        else
                            o_Div.Attributes.Add("class", "inside_content_news inside_content_newsb");

                        HyperLink o_HyperLink = new HyperLink();
                        o_HyperLink.CssClass = "inside_content_newsa";
                        o_HyperLink.ImageUrl = g_Travel_Images + "/" + o_Travel.Travel_PreView1;
                        o_HyperLink.NavigateUrl = "Travel_Detail.aspx?Travel_ID=" + o_Travel.Travel_ID.ToString();
                        o_Div.Controls.Add(o_HyperLink);

                        HtmlGenericControl o_Span = new HtmlGenericControl("span");
                        o_Span.Attributes.Add("class", "inside_content_span");

                        HtmlGenericControl o_Strong;
                        HtmlAnchor o_Anchor;
                        HtmlGenericControl o_H3 = new HtmlGenericControl("h3");
                        o_Strong = new HtmlGenericControl("strong");
                        o_Strong.InnerText = o_Travel.Travel_Name;
                        o_H3.Controls.Add(o_Strong);
                        o_Span.Controls.Add(o_H3);

                        HtmlGenericControl o_Span_Date = new HtmlGenericControl("span");
                        o_Span_Date.Attributes.Add("class", "inside_content_span1");
                        o_Span_Date.InnerHtml = "<strong>出团日期：</strong>" + o_Travel.Travel_StartDate.ToString("yyyy-MM-dd") + "至" + o_Travel.Travel_EndDate.ToString("yyyy-MM-dd");
                        o_Span.Controls.Add(o_Span_Date);

                        HtmlGenericControl o_Br = new HtmlGenericControl();
                        o_Br.InnerHtml = "<br/>";
                        o_Span.Controls.Add(o_Br);

                        HtmlGenericControl o_Span_Views = new HtmlGenericControl("span");
                        o_Span_Views.Attributes.Add("class", "inside_content_span1");
                        o_Strong = new HtmlGenericControl("strong");
                        o_Strong.InnerText = "主要景点：";
                        o_Anchor = new HtmlAnchor();
                        o_Anchor.HRef = "Travel_Detail.aspx?Travel_ID=" + o_Travel.Travel_ID.ToString();

                        if (o_Travel.Travel_Views.Length > 102)
                            o_Anchor.InnerText = o_Travel.Travel_Views.Substring(0, 102);
                        else
                            o_Anchor.InnerText = o_Travel.Travel_Views;

                        o_Anchor.Style.Add(HtmlTextWriterStyle.Color, "#f7860f");
                        o_Span_Views.Controls.Add(o_Strong);
                        o_Span_Views.Controls.Add(o_Anchor);
                        o_Span.Controls.Add(o_Span_Views);

                        o_Div.Controls.Add(o_Span);
                        Travel_Lists.Controls.Add(o_Div);

                        i++;
                    }
                }
            }
        }
    }
}
