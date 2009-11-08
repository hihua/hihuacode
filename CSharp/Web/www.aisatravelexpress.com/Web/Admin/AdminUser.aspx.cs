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

using Entity;
using Utility;

namespace Web.Admin
{
    public partial class AdminUser : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {                
                BLL.AdminUser b_AdminUser = new BLL.AdminUser();
                Entity.AdminUser[] e_AdminUser = b_AdminUser.Select_AdminUser(g_AdminUser);

                if (e_AdminUser != null)
                {
                    int i = 1;
                    foreach (Entity.AdminUser o_AdminUser in e_AdminUser)
                    {
                        HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                        HtmlTableCell o_HtmlTableCell;
                        HtmlAnchor o_HtmlAnchor;

                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.HRef = "#";
                        o_HtmlAnchor.Attributes.Add("onclick", "window.open('AdminUser_Detail.aspx?AdminUser_ID=" + o_AdminUser.AdminUser_ID.ToString() + "','AdminUser','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=680px,height=510px');return false;");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.InnerText = i.ToString();

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.InnerText = o_AdminUser.AdminUser_Name;
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.InnerText = o_AdminUser.AdminUser_NickName;
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        if (o_AdminUser.AdminUser_Status == 0)
                            o_HtmlTableCell.InnerText = "管理员";
                        else
                            o_HtmlTableCell.InnerText = "普通用户";

                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.InnerText = o_AdminUser.AdminUser_AddTime.ToString();
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.HRef = "AdminUser_Delete.aspx?AdminUser_ID=" + o_AdminUser.AdminUser_ID.ToString();
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.InnerText = "删除";

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableRow.Align = "center";
                        o_HtmlTableRow.Height = "30";

                        g_MainTable.Rows.Add(o_HtmlTableRow);

                        i++;
                    }
                }
            }
        }
    }
}
