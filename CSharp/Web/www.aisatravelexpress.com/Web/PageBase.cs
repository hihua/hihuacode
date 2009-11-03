using System;
using System.Data;
using System.Configuration;
using System.Web;
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
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);            
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
    }
}
