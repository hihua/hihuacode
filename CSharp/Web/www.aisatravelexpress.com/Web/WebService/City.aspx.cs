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

using Utility;

namespace Web
{
    public partial class City : PageBase
    {
        private string g_City_Name_Title = "";
        
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (VerifyUtility.IsString_NotNull(Request["City_Name_Title"]))
                {
                    Response.ContentType = "";

                    g_City_Name_Title = Request["City_Name_Title"];
                                        
                    if (g_City_Name_Title.Length == 3)
                    {
                        BLL.City b_City = new BLL.City();
                        b_City.Select_CityTitle(g_City_Name_Title, Response);
                    }
                    else
                    {
                        BLL.City b_City = new BLL.City();
                        b_City.Select_CityName(g_City_Name_Title, Response);
                    }
                }
            }
        }
    }
}
