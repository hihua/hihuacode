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
                
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            g_Language.Add(1, "中文");
            g_Language.Add(2, "英文");

            if (Session["LanguageID"] != null)
                g_LanguageID = Convert.ToInt32(Session["LanguageID"].ToString());
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
    }
}
