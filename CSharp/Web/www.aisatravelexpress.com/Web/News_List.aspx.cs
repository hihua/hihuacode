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
    public partial class News_List : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkNews(HyperLink_News);

                if (g_News_ClassID == 1 || g_News_ClassID == 2)
                    SetHyperLinkNewsClass(HyperLink_News_ClassID_1, HyperLink_News_ClassID_2, Label_News_ClassID);
                else
                {
                    HyperLink_News_ClassID_1.Visible = false;
                    HyperLink_News_ClassID_2.Visible = false;
                    Label_News_ClassID.Visible = false;
                }
                
                BLL.News b_News = new BLL.News();
                Entity.News[] e_News = b_News.Select_News(g_News_ClassID, g_LanguageID, 7, g_Page);

                if (e_News != null)
                {
                    foreach (Entity.News o_News in e_News)
                    {
                        HtmlGenericControl o_Div = new HtmlGenericControl("div");
                        o_Div.Attributes.Add("class", "inside3_news");

                        HtmlGenericControl o_H3 = new HtmlGenericControl("h3");
                        HtmlGenericControl o_Strong = new HtmlGenericControl("strong");
                        HtmlAnchor o_Anchor = new HtmlAnchor();
                        o_Anchor.HRef = "News_Detail.aspx?News_ID=" + o_News.News_ID.ToString();
                        o_Anchor.InnerText = o_News.News_Title;
                        o_Anchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                        HtmlGenericControl o_Span = new HtmlGenericControl("span");
                        o_Span.Style.Add(HtmlTextWriterStyle.Color, "#636363");
                        o_Span.InnerText = "(" + o_News.News_AddTime.ToShortDateString() + ")";
                        o_Strong.Controls.Add(o_Anchor);
                        o_Strong.Controls.Add(o_Span);
                        o_H3.Controls.Add(o_Strong);
                        o_Div.Controls.Add(o_H3);

                        o_Span = new HtmlGenericControl("span");
                        o_Span.Attributes.Add("style", "line-height:20px; color:#636363; text-indent:2em; float:left; width:620px; margin-top:10px;");

                        if (o_News.News_Intro.Length > 182)
                            o_Span.InnerText = o_News.News_Intro.Substring(0, 182);
                        else
                            o_Span.InnerText = o_News.News_Intro;

                        o_Div.Controls.Add(o_Span);
                        News_Lists.Controls.Add(o_Div);
                    }
                }
            }
        }
    }
}
