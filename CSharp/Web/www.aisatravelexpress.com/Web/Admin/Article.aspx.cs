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

namespace Web.Admin
{
    public partial class Article : PageBase
    {
        private BLL.Article b_Article;
        private Entity.Article e_Article;

        protected void Page_Load(object sender, EventArgs e)
        {
            GetArticleTXT(Article_Name);            
            b_Article = new BLL.Article();

            if (!IsPostBack)
            {
                SetLanguageControl(Article_LanguageID);
                e_Article = b_Article.Select_Article(g_Article_ClassID, Convert.ToInt32(Article_LanguageID.SelectedValue));
                Article_Content.Value = e_Article.Article_Content;
            }
        }

        protected void Article_Submit_Click(object sender, EventArgs e)
        {
            e_Article = b_Article.Select_Article(g_Article_ClassID, Convert.ToInt32(Article_LanguageID.SelectedValue));
            
            e_Article.Article_Content = Article_Content.Value;
            b_Article.Update_Article(e_Article.Article_ID, e_Article.Article_ClassID, e_Article.Article_LanguageID, e_Article.Article_Content);

            ResponseSuccess("修改成功", "Article.aspx?Article_ClassID=" + e_Article.Article_ClassID);
        }

        protected void Article_LanguageID_SelectedIndexChanged(object sender, EventArgs e)
        {
            e_Article = b_Article.Select_Article(g_Article_ClassID, Convert.ToInt32(Article_LanguageID.SelectedValue));
            Article_Content.Value = e_Article.Article_Content;
        }
    }
}
