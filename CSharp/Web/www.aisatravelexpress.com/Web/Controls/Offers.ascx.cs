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
    public partial class Offers : System.Web.UI.UserControl
    {
        protected int g_LanguageID = 1;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["LanguageID"] != null)
                g_LanguageID = Convert.ToInt32(Session["LanguageID"].ToString());

            if (!IsPostBack)
            {
                BLL.News b_News = new BLL.News();
                Entity.News[] e_News;

                e_News = b_News.Select_News(1, g_LanguageID, 6, 1);
                if (e_News != null)
                {
                    foreach (Entity.News o_News in e_News)
                    {
                        HyperLink o_HyperLink = new HyperLink();
                        o_HyperLink.NavigateUrl = "News_Detail.aspx?News_ID=" + o_News.News_ID.ToString();

                        if (o_News.News_Title.Length > 24)
                            o_HyperLink.Text = o_News.News_Title.Substring(0, 24);
                        else
                            o_HyperLink.Text = o_News.News_Title;

                        o_HyperLink.CssClass = "nav8";                                                
                        Offers_Controls.Controls.Add(o_HyperLink);

                        HtmlGenericControl o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "<br/>";
                        Offers_Controls.Controls.Add(o_HtmlGenericControl);
                    }

                    HyperLink m_HyperLink = new HyperLink();
                    m_HyperLink.NavigateUrl = "News_List.aspx?News_ClassID=1";
                    m_HyperLink.Text = "更多>>>";
                    m_HyperLink.CssClass = "nav8";
                    m_HyperLink.Style.Add(HtmlTextWriterStyle.BackgroundImage, "none");
                    m_HyperLink.Style.Add(HtmlTextWriterStyle.BorderWidth, "0");
                    m_HyperLink.Style.Add(HtmlTextWriterStyle.TextAlign, "right");
                    Offers_Controls.Controls.Add(m_HyperLink);
                }
            }
        }
    }
}