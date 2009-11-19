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

namespace Web
{
    public partial class Travel_Detail : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                BLL.Travel b_Travel = new BLL.Travel();
                Entity.Travel e_Travel = b_Travel.Select_Travel(g_Travel_ID);
                if (e_Travel != null)
                {
                    g_Travel_TypeID = e_Travel.Travel_TypeID;

                    SetHyperLinkTitle(HyperLink_Title);
                    SetHyperLinkTravel(HyperLink_Travel, HyperLink_Travel_TypeID);
                    SetHyperLinkTravelType(HyperLink_Travel_TypeID_1, HyperLink_Travel_TypeID_2);

                    Travel_Name.InnerText = e_Travel.Travel_Name;

                    HyperLink o_HyperLink = new HyperLink();
                    o_HyperLink.ImageUrl = g_Travel_Images + "/" + e_Travel.Travel_PreView2;
                    o_HyperLink.NavigateUrl = g_Travel_Images + "/" + e_Travel.Travel_PreView2;
                    o_HyperLink.ToolTip = "景点图片";
                    o_HyperLink.Target = "_blank";
                    Travel_PreView2.Controls.Add(o_HyperLink);

                    Travel_Code.InnerText = e_Travel.Travel_Code;
                    Travel_Price.InnerText = e_Travel.Travel_Price;
                    Travel_StartAddr.InnerText = e_Travel.Travel_StartAddr;
                    Travel_EndAddr.InnerText = e_Travel.Travel_EndAddr;
                    Travel_Points.InnerText = e_Travel.Travel_Points.ToString();

                    if (g_LanguageID == 1)                    
                        Travel_Date.InnerText = e_Travel.Travel_StartDate.ToString("yyyy年MM月dd日") + " - " + e_Travel.Travel_EndDate.ToString("yyyy年MM月dd日");
                    else
                        Travel_Date.InnerText = e_Travel.Travel_StartDate.ToString("yyyy-MM-dd") + " - " + e_Travel.Travel_EndDate.ToString("yyyy-MM-dd");

                    Travel_Views.InnerText = e_Travel.Travel_Views;
                    Travel_Route.InnerHtml = e_Travel.Travel_Route;

                    int i = 0;
                    foreach (string Travel_Images in e_Travel.Travel_PreViews)
                    {
                        o_HyperLink = new HyperLink();
                        o_HyperLink.ImageUrl = g_Travel_Images + "/" + Travel_Images;
                        o_HyperLink.NavigateUrl = g_Travel_Images + "/" + Travel_Images;
                        o_HyperLink.ToolTip = "景点图片";
                        o_HyperLink.Target = "_blank";
                        Travel_PreViews.Controls.Add(o_HyperLink);

                        i++;
                        if (i % 4 == 0)
                        {
                            HtmlGenericControl o_HtmlGenericControl = new HtmlGenericControl("div");
                            o_HtmlGenericControl.Attributes.Add("height", "15px");
                            o_HtmlGenericControl.InnerHtml = "&nbsp;";
                            Travel_PreViews.Controls.Add(o_HtmlGenericControl);
                        }
                    }
                }
            }
        }
    }
}
