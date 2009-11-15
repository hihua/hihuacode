using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Configuration;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Entity;
using Utility;

namespace Web
{
    public class PageBase : System.Web.UI.Page
    {
        protected int g_LanguageID = 1;
        protected int g_Page = 1;
        protected Dictionary<int, string> g_Language = new Dictionary<int, string>();
        protected Dictionary<int, string> g_Title = new Dictionary<int, string>();
        protected Dictionary<int, string[]> g_Article = new Dictionary<int, string[]>();
        protected Dictionary<int, string[]> g_News = new Dictionary<int, string[]>();
        protected Dictionary<int, string[]> g_Travel = new Dictionary<int, string[]>();

        protected int g_Article_ClassID = 1;
        protected int g_News_ClassID = 1;
        protected int g_News_ID = 1;
        protected int g_Travel_ID = 1;
                        
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            g_Language.Add(1, "中文");
            g_Language.Add(2, "英文");

            g_Title.Add(1, "华捷国际旅游");
            g_Title.Add(2, "HuaJie Travel");

            string[] g_ArticleName;
            g_ArticleName = new string[2];
            g_ArticleName[0] = "关于华捷";
            g_ArticleName[1] = "About HuaJie";
            g_Article.Add(1, g_ArticleName);

            g_ArticleName = new string[2];
            g_ArticleName[0] = "关于我们";
            g_ArticleName[1] = "About us";
            g_Article.Add(2, g_ArticleName);
            
            string[] g_NewsName;
            g_NewsName = new string[2];
            g_NewsName[0] = "最新资讯";
            g_NewsName[1] = "News";
            g_News.Add(0, g_NewsName);

            g_NewsName = new string[2];
            g_NewsName[0] = "优惠资讯";
            g_NewsName[1] = "Offers";
            g_News.Add(1, g_NewsName);

            g_NewsName = new string[2];
            g_NewsName[0] = "行业资讯";
            g_NewsName[1] = "Industry";
            g_News.Add(2, g_NewsName);

            g_NewsName = new string[2];
            g_NewsName[0] = "旅游需知";
            g_NewsName[1] = "Travel Knows";
            g_News.Add(3, g_NewsName);

            g_NewsName = new string[2];
            g_NewsName[0] = "表格下载";
            g_NewsName[1] = "Form Download";
            g_News.Add(4, g_NewsName);

            string[] g_TravelTitle;
            g_TravelTitle = new string[2];
            g_TravelTitle[0] = "旅游路线";
            g_TravelTitle[1] = "Travel Routes";
            g_Travel.Add(0, g_TravelTitle);
            
            if (Session["LanguageID"] != null)
                g_LanguageID = Convert.ToInt32(Session["LanguageID"].ToString());

            if (VerifyUtility.IsNumber_NotNull(Request["Article_ClassID"]) && Request["Article_ClassID"] != "0")
                g_Article_ClassID = Convert.ToInt32(Request["Article_ClassID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["News_ClassID"]) && Request["News_ClassID"] != "0")
                g_News_ClassID = Convert.ToInt32(Request["News_ClassID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["News_ID"]) && Request["News_ID"] != "0")
                g_News_ID = Convert.ToInt32(Request["News_ID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Travel_ID"]) && Request["Travel_ID"] != "0")
                g_Travel_ID = Convert.ToInt32(Request["Travel_ID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Page"]) && Request["Page"] != "0")
                g_Page = Convert.ToInt32(Request["Page"]);
        }

        protected override void OnError(EventArgs e)
        {
            base.OnError(e);
        }

        public void ResponseError(string Message)
        {
            Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.history.back();</script>");
            Response.End();
        }

        public void ResponseError(string Message, string Url)
        {
            Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.location.href='" + Url + "';</script>");
            Response.End();
        }

        public void ResponseError(string Message, string Url, int Top)
        {
            if (Top == 0)
            {
                Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.location.href='" + Url + "';</script>");
                Response.End();
            }
            else
            {
                Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.top.location.href='" + Url + "';</script>");
                Response.End();
            }
        }

        public void ResponseSuccess(string Message)
        {
            Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.history.back();</script>");
            Response.End();
        }

        public void ResponseSuccess(string Message, string Url)
        {
            Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.location.href='" + Url + "';</script>");
            Response.End();
        }

        public void ResponseSuccess(string Message, string Url, int Top)
        {
            if (Top == 0)
            {
                Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.location.href='" + Url + "';</script>");
                Response.End();
            }
            else
            {
                Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.top.location.href='" + Url + "';</script>");
                Response.End();
            }
        }

        public void ResponseClose(string Message)
        {
            Response.Write("<script type=\"text/javascript\">alert(\"" + Message + "\");window.close();</script>");
            Response.End();
        }

        public string GetMSN_herf(string Invitee)
        {
            return WebConfigurationManager.AppSettings["MSN_herf"] + Invitee + WebConfigurationManager.AppSettings["MSN_link"];
        }

        public string GetMSN_img(string Invitee)
        {
            return WebConfigurationManager.AppSettings["MSN_img_herf"] + Invitee + WebConfigurationManager.AppSettings["MSN_img_link"];
        }

        public string GetTitle()
        {
            return g_Title[g_LanguageID];
        }

        public void SetHyperLinkTitle(HyperLink p_HyperLink)
        {
            if (p_HyperLink != null)
            {
                p_HyperLink.NavigateUrl = "Index.aspx";
                p_HyperLink.Text = GetTitle();
            }
        }

        public void SetHyperLinkArticle(HyperLink p_HyperLink)
        {
            if (p_HyperLink != null)
            {
                p_HyperLink.NavigateUrl = "Article.aspx?Article_ClassID=" + g_Article_ClassID.ToString();
                p_HyperLink.Text = g_Article[g_Article_ClassID][g_LanguageID - 1];
            }
        }

        public void SetHyperLinkNews(HyperLink p_HyperLink)
        {
            if (p_HyperLink != null)
            {
                if (g_News_ClassID == 1 || g_News_ClassID == 2)
                {
                    p_HyperLink.NavigateUrl = "News_List.aspx?News_ClassID=1";
                    p_HyperLink.Text = g_News[0][g_LanguageID - 1];
                }
                else
                {
                    p_HyperLink.NavigateUrl = "News_List.aspx?News_ClassID=" + g_News_ClassID.ToString();
                    p_HyperLink.Text = g_News[g_News_ClassID][g_LanguageID - 1];
                }
            }
        }

        public void SetHyperLinkNewsClass(HyperLink p_HyperLink1, HyperLink p_HyperLink2, Label p_Label)
        {
            if (g_News_ClassID == 1 || g_News_ClassID == 2)
            {
                if (p_HyperLink1 != null)
                {
                    p_HyperLink1.NavigateUrl = "News_List.aspx?News_ClassID=1";
                    p_HyperLink1.Text = g_News[1][g_LanguageID - 1];
                    if (g_News_ClassID != 1)
                        p_HyperLink1.CssClass = "nav10";
                }

                if (p_HyperLink2 != null)
                {
                    p_HyperLink2.NavigateUrl = "News_List.aspx?News_ClassID=2";
                    p_HyperLink2.Text = g_News[2][g_LanguageID - 1];
                    if (g_News_ClassID != 2)
                        p_HyperLink2.CssClass = "nav10";
                }

                if (p_Label != null)
                {
                    p_Label.Visible = true;
                }
            }
        }
    }
}
