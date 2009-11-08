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
    public partial class MSN : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                BLL.MSN b_MSN = new BLL.MSN();
                Entity.MSN[] e_MSN = b_MSN.Select_MSN();

                if (e_MSN != null)
                {
                    int i = 1;
                    foreach (Entity.MSN o_MSN in e_MSN)
                    {
                        HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                        HtmlTableCell o_HtmlTableCell;
                        HtmlInputHidden o_HtmlInputHidden;
                        HtmlInputText o_HtmlInputText;
                        HtmlAnchor o_HtmlAnchor;
                        HtmlGenericControl o_HtmlGenericControl;

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlTableCell.InnerText = i.ToString();
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlInputHidden = new HtmlInputHidden();
                        o_HtmlInputHidden.ID = "MSN_ID_" + i.ToString();
                        o_HtmlInputHidden.Name = "MSN_ID_" + i.ToString();
                        o_HtmlInputHidden.Value = o_MSN.MSN_ID.ToString();
                        o_HtmlTableCell.Controls.Add(o_HtmlInputHidden);
                        o_HtmlInputText = new HtmlInputText();
                        o_HtmlInputText.ID = "MSN_Name_" + i.ToString();
                        o_HtmlInputText.Name = "MSN_Name_" + i.ToString();
                        o_HtmlInputText.Value = o_MSN.MSN_Name;
                        o_HtmlInputText.MaxLength = 20;
                        o_HtmlTableCell.Controls.Add(o_HtmlInputText);
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlInputText = new HtmlInputText();
                        o_HtmlInputText.ID = "MSN_Invitee_" + i.ToString();
                        o_HtmlInputText.Name = "MSN_Invitee_" + i.ToString();
                        o_HtmlInputText.Value = o_MSN.MSN_Invitee;
                        o_HtmlInputText.MaxLength = 40;
                        o_HtmlTableCell.Controls.Add(o_HtmlInputText);
                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);

                        o_HtmlTableCell = new HtmlTableCell();
                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.InnerText = "修改";
                        o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(2, " + i.ToString() + ")");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Cursor, "pointer");
                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "&nbsp;&nbsp;";
                        o_HtmlTableCell.Controls.Add(o_HtmlGenericControl);

                        o_HtmlAnchor = new HtmlAnchor();
                        o_HtmlAnchor.InnerText = "删除";
                        o_HtmlAnchor.Attributes.Add("onclick", "ActionSubmit(3, " + i.ToString() + ")");
                        o_HtmlAnchor.Attributes.Add("class", "AdminToolsLink2");
                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Cursor, "pointer");
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
