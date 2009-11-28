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

namespace Web.Admin
{
    public partial class LowFare : PageBase
    {
        protected int g_PageSize = 20; 

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                GetLowFare();
            }
        }

        private void GetLowFare()
        {
            BLL.LowFare b_LowFare = new BLL.LowFare();
            Entity.LowFare[] e_LowFare = b_LowFare.Select_LowFare(Search_Content.Text, Convert.ToInt32(Search_Method.SelectedValue), Convert.ToInt32(LowFare_Status.SelectedValue), g_PageSize, g_Page);
            if (e_LowFare != null)
            {
                int i = 1;

                foreach (Entity.LowFare o_LowFare in e_LowFare)
                {
                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                    HtmlTableCell o_HtmlTableCell;
                    HtmlAnchor o_HtmlAnchor;
                    HtmlGenericControl o_HtmlGenericControl;

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = ((g_Page - 1) * g_PageSize + i).ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    switch (o_LowFare.LowFare_Status)
                    {
                        case 0:
                            o_HtmlTableCell.InnerText = "未处理";
                            break;

                        case 1:
                            o_HtmlTableCell.InnerText = "已处理";
                            break;

                        default:
                            o_HtmlTableCell.InnerText = "";
                            break;
                    }

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    switch (o_LowFare.LowFare_Type)
                    {
                        case 1:
                            o_HtmlTableCell.InnerText = "Round-trip";
                            break;

                        case 2:
                            o_HtmlTableCell.InnerText = "One-way";
                            break;

                        case 3:
                            o_HtmlTableCell.InnerText = "Multi-city";
                            break;

                        default:
                            o_HtmlTableCell.InnerText = "";
                            break;
                    }
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_LowFare.LowFare_Flexibility)
                        o_HtmlTableCell.InnerText = "TRUE";
                    else
                        o_HtmlTableCell.InnerText = "FALSE";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_Adults.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_Children.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_Infants.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_Airline;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_Class;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_LowFare.LowFare_Member_ID != null)
                    {
                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.HRef = "#";
                        o_HtmlAnchor.Attributes.Add("onclick", "ActionMember(2, " + o_LowFare.LowFare_ID.ToString() + ");return false;");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.InnerText = o_LowFare.LowFare_Member_ID.Member_Account;
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                    }
                    else
                        o_HtmlTableCell.InnerText = "";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_LowFare.LowFare_Member_ID != null)
                        o_HtmlTableCell.InnerText = o_LowFare.LowFare_Member_ID.Member_Serial;
                    else
                        o_HtmlTableCell.InnerText = "";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_LowFare.LowFare_AdminUser_ID != null)
                        o_HtmlTableCell.InnerText = o_LowFare.LowFare_AdminUser_ID.AdminUser_Name;
                    else
                        o_HtmlTableCell.InnerText = "";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    if (o_LowFare.LowFare_AdminUser_ID != null)
                        o_HtmlTableCell.InnerText = o_LowFare.LowFare_AdminUser_ID.AdminUser_NickName;
                    else
                        o_HtmlTableCell.InnerText = "";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_AddTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_LowFare.LowFare_SubmitTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + o_LowFare.LowFare_ID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "详细";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                    o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                    o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                    if (o_LowFare.LowFare_Status == 0)
                    {
                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.HRef = "#";
                        o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(4, " + o_LowFare.LowFare_ID.ToString() + ");return false;");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.InnerText = "转已处理";
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                        o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);
                    }

                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + o_LowFare.LowFare_ID.ToString() + ");return false;");
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
                Total_Page.Text = b_LowFare.g_TotalPage.ToString();
                Total_Count.Text = b_LowFare.g_TotalCount.ToString();

                if (g_Page > 1)
                {
                    Previous_Page.Visible = true;
                    Previous_Page.CommandArgument = (g_Page - 1).ToString();
                }
                else
                {
                    Previous_Page.Visible = false;
                }

                if (g_Page < b_LowFare.g_TotalPage)
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

        protected void Search_Submit_Click(object sender, EventArgs e)
        {
            g_Page = 1;
            GetLowFare();
        }

        protected void Search_Refresh_Click(object sender, EventArgs e)
        {
            GetLowFare();
        }

        protected void Previous_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Previous_Page.CommandArgument);
            GetLowFare();
        }

        protected void Next_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Next_Page.CommandArgument);
            GetLowFare();
        }
    }
}
