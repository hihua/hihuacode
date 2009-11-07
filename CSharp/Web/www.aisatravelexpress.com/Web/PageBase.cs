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
        protected Dictionary<int, string> g_Language = new Dictionary<int, string>();
        protected Dictionary<int, string> g_Title = new Dictionary<int, string>();

        protected int g_Article_ClassID = 1;
                        
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            g_Language.Add(1, "中文");
            g_Language.Add(2, "英文");

            g_Title.Add(1, "华捷国际旅游");
            g_Title.Add(2, "HuaJie Travel");

            if (Session["LanguageID"] != null)
                g_LanguageID = Convert.ToInt32(Session["LanguageID"].ToString());

            if (VerifyUtility.IsNumber_NotNull(Request["Article_ClassID"]) && Request["Article_ClassID"] != "0")
                g_Article_ClassID = Convert.ToInt32(Request["Article_ClassID"]);
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
    }
}
