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
    public partial class News_Detail : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);

                if (g_News_ClassID == 1 || g_News_ClassID == 2)
                    SetHyperLinkNewsClass(HyperLink_News_ClassID_1, HyperLink_News_ClassID_2);
                else
                {
                    HyperLink_News_ClassID_1.Visible = false;
                    HyperLink_News_ClassID_2.Visible = false;
                }        

                BLL.News b_News = new BLL.News();
                Entity.News e_News = b_News.Select_News(g_News_ID);

                if (e_News != null)
                {
                    g_News_ClassID = e_News.News_ClassID;
                    SetHyperLinkNews(HyperLink_News);
                    
                    News_Content.InnerHtml += "<h1><strong>" + e_News.News_Title + "</strong></h1>";                    
                    News_Content.InnerHtml += e_News.News_Content;
                }
            }
        }
    }
}
