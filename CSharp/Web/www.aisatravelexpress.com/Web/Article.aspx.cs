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

using BLL;
using Utility;

namespace Web
{
    public partial class Article : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);

                BLL.Article b_Article = new BLL.Article();
                Entity.Article e_Article = b_Article.Select_Article(g_Article_ClassID, g_LanguageID);
                if (e_Article != null && VerifyUtility.IsString_NotNull(e_Article.Article_Content))
                {
                    HyperLink_Article_Name.NavigateUrl = Request.RawUrl;
                    HyperLink_Article_Name.Text = e_Article.Article_Name;
                    Article_Content.InnerHtml = e_Article.Article_Content;
                }
            }
        }
    }
}
