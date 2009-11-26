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
        protected Entity.Member g_Member = null;
        protected Dictionary<int, string> g_Language = new Dictionary<int, string>();
        protected Dictionary<int, string> g_Title = new Dictionary<int, string>();
        protected Dictionary<int, string[]> g_Article = new Dictionary<int, string[]>();
        protected Dictionary<int, string[]> g_News = new Dictionary<int, string[]>();
        protected Dictionary<int, string[]> g_Travel = new Dictionary<int, string[]>();
        protected Dictionary<int, string[]> g_Knows = new Dictionary<int, string[]>();
        protected Dictionary<int, string> g_LowFare = new Dictionary<int, string>();
       
        protected int g_Article_ClassID = 1;
        protected int g_News_ClassID = 1;
        protected int g_News_ID = 1;
        protected int g_Knows_ID = 1;
        protected int g_Knows_ClassID = 1;
        protected int g_Travel_TypeID = 1;
        protected int g_Travel_ID = 1;
        protected int g_Member_ID = 1;

        protected string g_Travel_Images = "Travel_Images";
                        
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

            g_ArticleName = new string[2];
            g_ArticleName[0] = "成为会员能享受什么好处";
            g_ArticleName[1] = "To Be Member";
            g_Article.Add(3, g_ArticleName);
            
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
            g_NewsName[0] = "表格下载";
            g_NewsName[1] = "Form Download";
            g_News.Add(3, g_NewsName);

            string[] g_KnowsName;
            g_KnowsName = new string[2];
            g_KnowsName[0] = "旅游需知";
            g_KnowsName[1] = "Knows";
            g_Knows.Add(0, g_KnowsName);

            g_KnowsName = new string[2];
            g_KnowsName[0] = "行李规定";
            g_KnowsName[1] = "Luggage";
            g_Knows.Add(1, g_KnowsName);

            g_KnowsName = new string[2];
            g_KnowsName[0] = "机楼地图";
            g_KnowsName[1] = "Maps";
            g_Knows.Add(2, g_KnowsName);

            string[] g_TravelName;
            g_TravelName = new string[2];
            g_TravelName[0] = "旅游路线";
            g_TravelName[1] = "Travel Routes";
            g_Travel.Add(0, g_TravelName);

            g_TravelName = new string[2];
            g_TravelName[0] = "国外旅游";
            g_TravelName[1] = "Abroad";
            g_Travel.Add(1, g_TravelName);

            g_TravelName = new string[2];
            g_TravelName[0] = "中国旅游";
            g_TravelName[1] = "China";
            g_Travel.Add(2, g_TravelName);

            g_LowFare.Add(0, "机票问价");
            g_LowFare.Add(1, "Ticket Price");

            if (Session["Member"] != null)
                g_Member = Session["Member"] as Entity.Member;

            if (Session["LanguageID"] != null)
                g_LanguageID = Convert.ToInt32(Session["LanguageID"].ToString());

            if (VerifyUtility.IsNumber_NotNull(Request["Article_ClassID"]) && Request["Article_ClassID"] != "0")
                g_Article_ClassID = Convert.ToInt32(Request["Article_ClassID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["News_ClassID"]) && Request["News_ClassID"] != "0")
                g_News_ClassID = Convert.ToInt32(Request["News_ClassID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["News_ID"]) && Request["News_ID"] != "0")
                g_News_ID = Convert.ToInt32(Request["News_ID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Knows_ClassID"]) && Request["Knows_ClassID"] != "0")
                g_Knows_ClassID = Convert.ToInt32(Request["Knows_ClassID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Knows_ID"]) && Request["Knows_ID"] != "0")
                g_Knows_ID = Convert.ToInt32(Request["Knows_ID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Travel_TypeID"]) && Request["Travel_TypeID"] != "0")
                g_Travel_TypeID = Convert.ToInt32(Request["Travel_TypeID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Travel_ID"]) && Request["Travel_ID"] != "0")
                g_Travel_ID = Convert.ToInt32(Request["Travel_ID"]);

            if (VerifyUtility.IsNumber_NotNull(Request["Member_ID"]) && Request["Member_ID"] != "0")
                g_Member_ID = Convert.ToInt32(Request["Member_ID"]);

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

        public void SetHyperLinkKnows(HyperLink p_HyperLink)
        {
            if (p_HyperLink != null)
            {
                if (g_Knows_ClassID == 1 || g_Knows_ClassID == 2)
                {
                    p_HyperLink.NavigateUrl = "Knows_List.aspx?Knows_ClassID=1";
                    p_HyperLink.Text = g_Knows[0][g_LanguageID - 1];
                }
                else
                {
                    p_HyperLink.NavigateUrl = "Knows_List.aspx?Knows_ClassID=" + g_Knows_ClassID.ToString();
                    p_HyperLink.Text = g_Knows[g_Knows_ClassID][g_LanguageID - 1];
                }
            }
        }

        public void SetHyperLinkKnowsClass(HyperLink p_HyperLink1, HyperLink p_HyperLink2, Label p_Label)
        {
            if (g_Knows_ClassID == 1 || g_Knows_ClassID == 2)
            {
                if (p_HyperLink1 != null)
                {
                    p_HyperLink1.NavigateUrl = "Knows_List.aspx?Knows_ClassID=1";
                    p_HyperLink1.Text = g_Knows[1][g_LanguageID - 1];
                    if (g_Knows_ClassID != 1)
                        p_HyperLink1.CssClass = "nav10";
                }

                if (p_HyperLink2 != null)
                {
                    p_HyperLink2.NavigateUrl = "Knows_List.aspx?Knows_ClassID=2";
                    p_HyperLink2.Text = g_Knows[2][g_LanguageID - 1];
                    if (g_Knows_ClassID != 2)
                        p_HyperLink2.CssClass = "nav10";
                }

                if (p_Label != null)
                {
                    p_Label.Visible = true;
                }
            }
        }

        public void SetHyperLinkTravel(HyperLink p_HyperLink, HyperLink p_HyperLinkTypeID)
        {
            if (p_HyperLink != null)
            {
                p_HyperLink.NavigateUrl = "Travel_List.aspx?Travel_TypeID=1";
                p_HyperLink.Text = g_Travel[0][g_LanguageID - 1];                               
            }

            if (p_HyperLinkTypeID != null)
            {
                p_HyperLinkTypeID.NavigateUrl = "Travel_List.aspx?Travel_TypeID=" + g_Travel_TypeID.ToString();
                p_HyperLinkTypeID.Text = g_Travel[g_Travel_TypeID][g_LanguageID - 1];
            }
        }

        public void SetHyperLinkTravelType(HyperLink p_HyperLink1, HyperLink p_HyperLink2)
        {
            if (p_HyperLink1 != null)
            {
                p_HyperLink1.NavigateUrl = "Travel_List.aspx?Travel_TypeID=1";
                p_HyperLink1.Text = g_Travel[1][g_LanguageID - 1];
                if (g_Travel_TypeID != 1)
                    p_HyperLink1.CssClass = "nav10";

                p_HyperLink2.NavigateUrl = "Travel_List.aspx?Travel_TypeID=2";
                p_HyperLink2.Text = g_Travel[2][g_LanguageID - 1];
                if (g_Travel_TypeID != 2)
                    p_HyperLink2.CssClass = "nav10";
            }
        }

        public void SetHyperLinkLowFare(HyperLink p_HyperLink)
        {
            if (p_HyperLink != null)
            {
                p_HyperLink.NavigateUrl = "LowFare.aspx";
                p_HyperLink.Text = g_LowFare[g_LanguageID - 1];
            }
        }
    }
}
