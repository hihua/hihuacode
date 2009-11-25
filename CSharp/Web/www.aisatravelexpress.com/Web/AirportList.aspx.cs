using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Text;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Utility;

namespace Web
{
    public partial class AirportList : PageBase
    {
        private int City_Country = 0;
        protected string CityTextBox = "";
        protected StringBuilder o_CityList = new StringBuilder();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                GetCity();
            }
        }

        protected void GetCity()
        {
            if (VerifyUtility.IsNumber_NotNull(Request["City_Country"]))
                City_Country = Convert.ToInt32(Request["City_Country"]);

            if (VerifyUtility.IsString_NotNull(Request["CityTextBox"]))
                CityTextBox = Request["CityTextBox"];

            BLL.City b_City = new BLL.City();
            Entity.City[] e_City = b_City.Select_City(City_Country);

            string Charter = "";

            if (e_City != null)
            {
                foreach (Entity.City o_City in e_City)
                {
                    if (o_City.City_Name.Substring(0, 1) != Charter)
                    {
                        Charter = o_City.City_Name.Substring(0, 1);

                        o_CityList.Append("<a name=\"" + Charter + "\"></a><br/>");
                        o_CityList.Append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
                        o_CityList.Append("<tr><td><b>" + Charter + "</b></td>");
                        o_CityList.Append("<td align=\"right\"><a href=\"#TOP\">Top</a> | <a href=\"#\" onclick=\"window.close();return false;\">Close</a></td>");
                        o_CityList.Append("</tr>");
                        o_CityList.Append("</table><br/>");
                    }

                    o_CityList.Append("<a href=\"#\" onclick=\"SetCity('" + o_City.City_Name + "');return false;\" class=\"AdminToolsLink1\">" + o_City.City_Name + "</a><br/>");
                }                                
            }
        }
    }
}
