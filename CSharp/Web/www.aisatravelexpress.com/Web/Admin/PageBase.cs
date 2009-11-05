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

namespace Web.Admin
{
    public class PageBase : Web.PageBase
    {
        protected Entity.AdminUser g_AdminUser;
        protected int g_Article_ClassID = 0;
        
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);
            if (Session["AdminUser"] == null)                            
                ResponseError("超时请重新登录", "Login.aspx", 1);            
            else
                g_AdminUser = Session["AdminUser"] as Entity.AdminUser;

            if (VerifyUtility.IsNumber_NotNull(Request["Article_ClassID"]))            
                g_Article_ClassID = Convert.ToInt32(Request["Article_ClassID"]);              
        }

        protected override void OnError(EventArgs e)
        {
            base.OnError(e);            
        }

        public void GetArticleTXT()
        {            
            switch (g_Article_ClassID)
            {
                case 1:
                    Response.Write("关于华捷");
                    break;

                case 2:
                    Response.Write("联系我们");
                    break;

                default:
                    Response.Write("");
                    break;
            }
        }

        public void SetLanguageControl(DropDownList p_DropDownList)
        {
            if (p_DropDownList != null)
            {
                foreach (int i_Key in g_Language.Keys)
                {                    
                    ListItem o_ListItem = new ListItem(g_Language[i_Key], i_Key.ToString());
                    if (i_Key == 1)
                        o_ListItem.Selected = true;
                    else
                        o_ListItem.Selected = false;

                    p_DropDownList.Items.Add(o_ListItem);
                }                                
            }
        }
    }
}
