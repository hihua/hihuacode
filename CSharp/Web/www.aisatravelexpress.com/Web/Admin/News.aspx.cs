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
    public partial class News : PageBase
    {
        protected int g_PageSize = 20; 

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                GetNewsTXT(News_Name);
                SetLanguageControl(News_LanguageID);
                News_Add.OnClientClick = "ActionSubmit(1, 0, " + g_News_ClassID.ToString() + ");return false;";
                GetNews();
            }
        }

        private void GetNews()
        {
            BLL.News b_News = new BLL.News();
            Entity.News[] e_News = b_News.Select_News(g_News_ClassID, Convert.ToInt32(News_LanguageID.SelectedValue), Search_Content.Text, Convert.ToInt32(Search_Method.SelectedValue), g_PageSize, g_Page);
            if (e_News != null)
            {
                int i = 1;

                foreach (Entity.News o_News in e_News)
                {
                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                    HtmlTableCell o_HtmlTableCell;
                    HtmlAnchor o_HtmlAnchor;
                    HtmlGenericControl o_HtmlGenericControl;

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = ((g_Page - 1) * g_PageSize + i).ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = g_Language[o_News.News_LanguageID];
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_News.News_Title;
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlTableCell.InnerText = o_News.News_AddTime.ToString();
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableCell = new HtmlTableCell();
                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + o_News.News_ID.ToString() + ", " + g_News_ClassID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "修改";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                    o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                    o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                    o_HtmlAnchor = new HtmlAnchor();
                    o_HtmlAnchor.HRef = "#";
                    o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + o_News.News_ID.ToString() + ", " + g_News_ClassID.ToString() + ");return false;");
                    o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                    o_HtmlAnchor.InnerText = "删除";
                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                    o_HtmlTableRow.Align = "center";
                    o_HtmlTableRow.Height = "30";

                    g_MainTable.Rows.Add(o_HtmlTableRow);

                    i++;
                }

                Current_Page.Text = g_Page.ToString();
                Total_Page.Text = b_News.g_TotalPage.ToString();
                Total_Count.Text = b_News.g_TotalCount.ToString();

                if (g_Page > 1)
                {
                    Previous_Page.Visible = true;
                    Previous_Page.CommandArgument = (g_Page - 1).ToString();
                }
                else
                {
                    Previous_Page.Visible = false;
                }

                if (g_Page < b_News.g_TotalPage)
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
            GetNews();
        }

        protected void Next_Page_Click(object sender, EventArgs e)
        {
            g_Page = Convert.ToInt32(Next_Page.CommandArgument);
            GetNews();
        }

        protected void Search_Submit_Click(object sender, EventArgs e)
        {
            g_Page = 1;
            GetNews();
        }

        protected void Search_Refresh_Click(object sender, EventArgs e)
        {
            GetNews();
        }
    }
}
