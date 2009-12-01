using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.IO;
using System.Text;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

namespace Web
{
    public partial class Index : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {                
                BLL.News b_News = new BLL.News();
                Entity.News[] e_News;

                e_News = b_News.Select_News(1, g_LanguageID, 5, 1);
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

                        o_HyperLink.CssClass = "nav9";

                        StringBuilder o_StringBuilder = new StringBuilder();
                        StringWriter o_StringWriter = new StringWriter(o_StringBuilder);
                        HtmlTextWriter o_HtmlTextWriter = new HtmlTextWriter(o_StringWriter);
                        o_HyperLink.RenderControl(o_HtmlTextWriter);

                        News_ClassID_1_Controls.InnerHtml += "<li>" + o_StringBuilder.ToString() + "&nbsp;&nbsp;" + o_News.News_AddTime.ToShortDateString() + "</li>";
                    }
                }
                else
                    News_ClassID_1_More.Visible = false;

                e_News = b_News.Select_News(2, g_LanguageID, 5, 1);
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

                        o_HyperLink.CssClass = "nav9";

                        StringBuilder o_StringBuilder = new StringBuilder();
                        StringWriter o_StringWriter = new StringWriter(o_StringBuilder);
                        HtmlTextWriter o_HtmlTextWriter = new HtmlTextWriter(o_StringWriter);
                        o_HyperLink.RenderControl(o_HtmlTextWriter);

                        News_ClassID_2_Controls.InnerHtml += "<li>" + o_StringBuilder.ToString() + "&nbsp;&nbsp;" + o_News.News_AddTime.ToShortDateString() + "</li>";
                    }
                }
                else
                    News_ClassID_2_More.Visible = false;
            }
        }

        protected void Member_Account_Submit_Click(object sender, EventArgs e)
        {
            BLL.Member b_Member = new BLL.Member();
            Entity.Member o_Member = b_Member.Select_Member(Member_Account_Name.Text, Member_Account_PassWord.Text);
            if (o_Member == null)            
                ResponseError("帐号或密码错误");            
            else
            {
                Session["Member"] = o_Member;

                Response.Redirect("Article.aspx?Article_ClassID=2");
            }
        }
    }
}
