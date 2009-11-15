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
        protected int g_Action_ID = 0;
        protected int g_Page = 1;
        
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);
            if (Session["AdminUser"] == null)                            
                ResponseError("超时请重新登录", "Login.aspx", 1);            
            else
                g_AdminUser = Session["AdminUser"] as Entity.AdminUser;
                        
            if (VerifyUtility.IsNumber_NotNull(Request["Action_ID"]) && Request["Action_ID"] != "0")
                g_Action_ID = Convert.ToInt32(Request["Action_ID"]);
        }

        protected override void OnError(EventArgs e)
        {
            base.OnError(e);            
        }

        public void GetArticleTXT(Label p_Label)
        {
            if (p_Label != null)
            {
                switch (g_Article_ClassID)
                {
                    case 1:
                        p_Label.Text = g_Article[g_Article_ClassID][0];
                        break;

                    case 2:
                        p_Label.Text = g_Article[g_Article_ClassID][0];
                        break;

                    default:
                        p_Label.Text = "";
                        break;
                }
            }
        }

        public void GetNewsTXT(Label p_Label)
        {
            if (p_Label != null)
            {                
                p_Label.Text = g_News[g_News_ClassID][0];                 
            }
        }

        public void GetTravelTXT(Label p_Label)
        {
            if (p_Label != null)
            {
                p_Label.Text = g_Travel[0][0];
            }
        }

        public void SetLanguageControl(DropDownList p_DropDownList)
        {
            if (p_DropDownList != null)
            {               
                foreach (int i_Key in g_Language.Keys)
                {                    
                    ListItem o_ListItem = new ListItem(g_Language[i_Key], i_Key.ToString());                   
                    p_DropDownList.Items.Add(o_ListItem);
                }                                
            }
        }
    }
}
