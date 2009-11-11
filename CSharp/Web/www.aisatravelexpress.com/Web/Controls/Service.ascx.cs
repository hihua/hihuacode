using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Configuration;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

namespace Web.Controls
{
    public partial class Service : System.Web.UI.UserControl
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                BLL.MSN b_MSN = new BLL.MSN();
                Entity.MSN[] e_MSN = b_MSN.Select_MSN();

                string Href = WebConfigurationManager.AppSettings["MSN_herf"]; 
                string Link = WebConfigurationManager.AppSettings["MSN_link"];
                string Img_Herf = WebConfigurationManager.AppSettings["MSN_img_herf"]; 
                string Img_Link = WebConfigurationManager.AppSettings["MSN_img_link"];

                if (e_MSN != null)
                {
                    foreach (Entity.MSN o_MSN in e_MSN)
                    {
                        HyperLink o_HyperLink;
                        HtmlGenericControl o_HtmlGenericControl;
                        
                        o_HyperLink = new HyperLink();
                        o_HyperLink.NavigateUrl = Href + o_MSN.MSN_Invitee + Link;
                        o_HyperLink.Text = "客服人员：" + o_MSN.MSN_Name;                                                
                        o_HyperLink.CssClass = "nav8";
                        o_HyperLink.Target = "_blank";
                        o_HyperLink.Style.Add(HtmlTextWriterStyle.MarginLeft, "20px");
                        MSN_Controls.Controls.Add(o_HyperLink);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                        MSN_Controls.Controls.Add(o_HtmlGenericControl);

                        o_HyperLink = new HyperLink();
                        o_HyperLink.NavigateUrl = Href + o_MSN.MSN_Invitee + Link;
                        o_HyperLink.ImageUrl = Img_Herf + o_MSN.MSN_Invitee + Img_Link;
                        o_HyperLink.Target = "_blank";
                        MSN_Controls.Controls.Add(o_HyperLink);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "<br/><br/>";
                        MSN_Controls.Controls.Add(o_HtmlGenericControl);
                    }
                }
            }
        }
    }
}