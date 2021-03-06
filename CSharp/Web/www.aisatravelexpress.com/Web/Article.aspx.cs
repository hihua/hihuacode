﻿using System;
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
                SetHyperLinkArticle(HyperLink_Article);

                switch (g_Article_ClassID)
                {
                    case 1:
                        Banner.ImageUrl = "images/inside_a.jpg";
                        break;

                    case 2:
                        Banner.ImageUrl = "images/inside_f.jpg";
                        break;

                    case 3:
                        Banner.ImageUrl = "images/inside_f.jpg";
                        break;

                    default:
                        Banner.ImageUrl = "images/inside_a.jpg";
                        break;
                }

                BLL.Article b_Article = new BLL.Article();
                Entity.Article e_Article = b_Article.Select_Article(g_Article_ClassID, g_LanguageID);
                if (e_Article != null)
                {                                        
                    Article_Content.InnerHtml = e_Article.Article_Content;
                }
            }
        }
    }
}
