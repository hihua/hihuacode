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

namespace Web.Admin
{
    public partial class Booking : PageBase
    {
        protected int g_PageSize = 20; 

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                Booking_Add.OnClientClick = "ActionSubmit(1, 0);return false;";
                GetBooking();
            }
        }

        private void GetBooking()
        {
            BLL.Booking b_Booking = new BLL.Booking();

            Entity.Booking[] e_Booking = b_Booking.Select_Booking(Search_Content.Text, Convert.ToInt32(Search_Method.Text), Convert.ToInt32(Search_State.SelectedValue), g_PageSize, g_Page);
            if (e_Booking != null)
            {
                int i = 1;

                foreach (Entity.Booking o_Booking in e_Booking)
                {
                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                    HtmlTableCell o_HtmlTableCell;
                    HtmlAnchor o_HtmlAnchor;
                    HtmlGenericControl o_HtmlGenericControl;
                                        
                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Seq;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Airline;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Contact;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Num.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Tel;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_Email;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_Booking.Booking_AdminUser_ID != null)
                        o_HtmlTableCell.InnerText = o_Booking.Booking_AdminUser_ID.AdminUser_Name;
                    else
                        o_HtmlTableCell.InnerHtml = "&nbsp;";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_Booking.Booking_AdminUser_ID != null)
                        o_HtmlTableCell.InnerText = o_Booking.Booking_AdminUser_ID.AdminUser_NickName;
                    else
                        o_HtmlTableCell.InnerHtml = "&nbsp;";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_Booking.Booking_Kind)
                        o_HtmlTableCell.InnerText = "正常";
                    else
                        o_HtmlTableCell.InnerText = "假位";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    switch (o_Booking.Booking_State)
                    {
                        case 0:
                            o_HtmlTableCell.InnerHtml = "<span style=\"color:#FF0000\">未确认</span>";
                            break;

                        case 1:
                            o_HtmlTableCell.InnerHtml = "<span style=\"color:#0000FF\">已确认</span>";
                            break;

                        default:
                            o_HtmlTableCell.InnerHtml = "&nbsp;";
                            break;

                    }
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_AddTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Booking.Booking_LastTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();                    
                    o_HtmlTableCell.InnerText = o_Booking.Booking_ComitTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                        
                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + o_Booking.Booking_ID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "详细";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                    o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                    o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + o_Booking.Booking_ID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "删除";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableRow.Align = "center";
                    o_HtmlTableRow.Style.Add(HtmlTextWriterStyle.Height, "30px");

                    g_MainTable.Rows.Add(o_HtmlTableRow);

                    i++;
                }

                Current_Page.Text = g_Page.ToString();
                Total_Page.Text = b_Booking.g_TotalPage.ToString();
                Total_Count.Text = b_Booking.g_TotalCount.ToString();

                if (g_Page > 1)
                {
                    Previous_Page.Visible = true;
                    Previous_Page.CommandArgument = (g_Page - 1).ToString();
                }
                else
                {
                    Previous_Page.Visible = false;
                }

                if (g_Page < b_Booking.g_TotalPage)
                {
                    Next_Page.Visible = true;
                    Next_Page.CommandArgument = (g_Page + 1).ToString();
                }
                else
                {
                    Next_Page.Visible = false;
                }

                Current_Page.Visible = true;
                Splite_Page.Visible = true;
                Total_Page.Visible = true;
            }
            else
            {
                Current_Page.Visible = false;
                Splite_Page.Visible = false;
                Total_Page.Visible = false;
                Total_Count.Text = "0";
            }
        }

        protected void Previous_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Previous_Page.CommandArgument);
            GetBooking();
        }

        protected void Next_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Next_Page.CommandArgument);
            GetBooking();
        }

        protected void Search_Submit_Click(object sender, EventArgs e)
        {
            g_Page = 1;
            GetBooking();
        }

        protected void Search_Refresh_Click(object sender, EventArgs e)
        {
            GetBooking();
        }
    }
}
