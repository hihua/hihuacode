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
    public partial class Member : PageBase
    {
        protected int g_PageSize = 20; 

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
                GetMember();
        }

        private void GetMember()
        {
            BLL.Member b_Member = new BLL.Member();
            Entity.Member[] e_Member = b_Member.Select_Member(Search_Content.Text, Convert.ToInt32(Search_Method.SelectedValue), g_PageSize, g_Page);
            if (e_Member != null)
            {
                int i = 1;

                foreach (Entity.Member o_Member in e_Member)
                {
                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                    HtmlTableCell o_HtmlTableCell;
                    HtmlAnchor o_HtmlAnchor;
                    HtmlGenericControl o_HtmlGenericControl;

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = ((g_Page - 1) * g_PageSize + i).ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Serial;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();

                    if (o_Member.Member_Sex)
                        o_HtmlTableCell.InnerText = "男";
                    else
                        o_HtmlTableCell.InnerText = "女";

                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Account;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = b_Member.Show_Member_Level(o_Member.Member_Level);
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Name_CN;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Name_EN;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                       
                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Tel;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Mobile;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_Email;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Member.Member_AddTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + o_Member.Member_ID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "详细";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                    o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                    o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                    if (o_Member.Member_Level < 3)
                    {
                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.HRef = "#";
                        o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(4, " + o_Member.Member_ID.ToString() + ");return false;");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.InnerText = "转成VIP";
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                        o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);
                    }                                        

                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + o_Member.Member_ID.ToString() + ");return false;");
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
                Total_Page.Text = b_Member.g_TotalPage.ToString();
                Total_Count.Text = b_Member.g_TotalCount.ToString();

                if (g_Page > 1)
                {
                    Previous_Page.Visible = true;
                    Previous_Page.CommandArgument = (g_Page - 1).ToString();
                }
                else
                {
                    Previous_Page.Visible = false;
                }

                if (g_Page < b_Member.g_TotalPage)
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
            GetMember();            
        }

        protected void Next_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Next_Page.CommandArgument);
            GetMember();            
        }

        protected void Search_Submit_Click(object sender, EventArgs e)
        {
            g_Page = 1;
            GetMember();            
        }

        protected void Search_Refresh_Click(object sender, EventArgs e)
        {
            GetMember();
        }
    }
}
