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
    public partial class Knows : PageBase
    {
        protected int g_PageSize = 20;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                GetKnowsTXT(Knows_Name);
                SetLanguageControl(Knows_LanguageID);
                Knows_Add.OnClientClick = "ActionSubmit(1, 0);return false;";
                GetKnows();
            }
        }

        private void GetKnows()
        {
            BLL.Knows b_Knows = new BLL.Knows();
            Entity.Knows[] e_Knows = b_Knows.Select_Knows(g_Knows_ClassID, Convert.ToInt32(Knows_LanguageID.SelectedValue), Search_Content.Text, Convert.ToInt32(Search_Method.SelectedValue), g_PageSize, g_Page);
            if (e_Knows != null)
            {
                int i = 1;

                foreach (Entity.Knows o_Knows in e_Knows)
                {
                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                    HtmlTableCell o_HtmlTableCell;
                    HtmlAnchor o_HtmlAnchor;
                    HtmlGenericControl o_HtmlGenericControl;

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = ((g_Page - 1) * g_PageSize + i).ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = g_Language[o_Knows.Knows_LanguageID];
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = g_Knows[o_Knows.Knows_ClassID][0];
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Knows.Knows_Title;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_Knows.Knows_AddTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + o_Knows.Knows_ID.ToString() + ", " + g_Knows_ClassID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "修改";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                    o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                    o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + o_Knows.Knows_ID.ToString() + ", " + g_Knows_ClassID.ToString() + ");return false;");
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
                Total_Page.Text = b_Knows.g_TotalPage.ToString();
                Total_Count.Text = b_Knows.g_TotalCount.ToString();

                if (g_Page > 1)
                {
                    Previous_Page.Visible = true;
                    Previous_Page.CommandArgument = (g_Page - 1).ToString();
                }
                else
                {
                    Previous_Page.Visible = false;
                }

                if (g_Page < b_Knows.g_TotalPage)
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
            GetKnows();
        }

        protected void Next_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Next_Page.CommandArgument);
            GetKnows();
        }

        protected void Search_Submit_Click(object sender, EventArgs e)
        {
            g_Page = 1;
            GetKnows();
        }

        protected void Search_Refresh_Click(object sender, EventArgs e)
        {
            GetKnows();
        }
    }
}

