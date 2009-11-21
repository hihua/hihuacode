using System;
using System.Collections.Generic;
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
    public partial class Knows_List : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkKnows(HyperLink_Knows);

                if (g_Knows_ClassID == 1 || g_Knows_ClassID == 2)
                    SetHyperLinkKnowsClass(HyperLink_Knows_ClassID_1, HyperLink_Knows_ClassID_2, Label_Knows_ClassID);
                else
                {
                    HyperLink_Knows_ClassID_1.Visible = false;
                    HyperLink_Knows_ClassID_2.Visible = false;
                    Label_Knows_ClassID.Visible = false;
                }

                if (g_Knows_ClassID == 1)
                {
                    AirportCode.Visible = false;
                    AirportName.Visible = false;

                    BLL.Knows b_Knows = new BLL.Knows();
                    Entity.Knows[] e_Knows = b_Knows.Select_Knows(g_Knows_ClassID, g_LanguageID, 7, g_Page);

                    if (e_Knows != null)
                    {
                        foreach (Entity.Knows o_Knows in e_Knows)
                        {
                            HtmlGenericControl o_Div = new HtmlGenericControl("div");
                            o_Div.Attributes.Add("class", "inside3_news");

                            HtmlGenericControl o_H3 = new HtmlGenericControl("h3");
                            HtmlGenericControl o_Strong = new HtmlGenericControl("strong");
                            HtmlAnchor o_Anchor = new HtmlAnchor();
                            o_Anchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                            o_Anchor.InnerText = o_Knows.Knows_Title;
                            o_Anchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                            HtmlGenericControl o_Span = new HtmlGenericControl("span");
                            o_Span.Style.Add(HtmlTextWriterStyle.Color, "#636363");
                            o_Span.InnerText = "(" + o_Knows.Knows_AddTime.ToShortDateString() + ")";
                            o_Strong.Controls.Add(o_Anchor);
                            o_Strong.Controls.Add(o_Span);
                            o_H3.Controls.Add(o_Strong);
                            o_Div.Controls.Add(o_H3);

                            Knows_Lists.Controls.Add(o_Div);
                        }
                    }
                }
                else
                {
                    AirportCode.Visible = true;
                    AirportName.Visible = true;

                    BLL.Knows b_Knows = new BLL.Knows();
                    Entity.Knows[] e_Knows = b_Knows.Select_Knows(g_Knows_ClassID, 1, g_LanguageID, 0x7FFFFFFF, g_Page);

                    if (e_Knows != null)
                    {                        
                        foreach (Entity.Knows o_Knows in e_Knows)
                        {                            
                            if (o_Knows.Knows_TypeID == 1)
                            {
                                if (AirportCode_Table.Rows.Count == 0)
                                {
                                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                                    HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                    HtmlAnchor o_HtmlAnchor = new HtmlAnchor();
                                                        
                                    o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                    o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                    o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                                    o_HtmlTableRow.Height = "30px";

                                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                    AirportCode_Table.Rows.Add(o_HtmlTableRow);
                                }
                                else
                                {
                                    HtmlTableRow o_HtmlTableRow = AirportCode_Table.Rows[AirportCode_Table.Rows.Count - 1];

                                    if (o_HtmlTableRow.Cells.Count == 4)
                                    {
                                        o_HtmlTableRow = new HtmlTableRow();
                                        o_HtmlTableRow.Height = "30px";

                                        HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                        HtmlAnchor o_HtmlAnchor = new HtmlAnchor();

                                        o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                        o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");

                                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                        AirportCode_Table.Rows.Add(o_HtmlTableRow);
                                    }
                                    else
                                    {
                                        HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                        HtmlAnchor o_HtmlAnchor = new HtmlAnchor();

                                        o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                        o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");

                                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);                                        
                                    }
                                }
                            }
                            else
                            {
                                if (AirportName_Table.Rows.Count == 0)
                                {
                                    HtmlTableRow o_HtmlTableRow = new HtmlTableRow();
                                    HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                    HtmlAnchor o_HtmlAnchor = new HtmlAnchor();

                                    o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                    o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                    o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                                    o_HtmlTableRow.Height = "30px";

                                    o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                    o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                    AirportName_Table.Rows.Add(o_HtmlTableRow);
                                }
                                else
                                {
                                    HtmlTableRow o_HtmlTableRow = AirportName_Table.Rows[AirportName_Table.Rows.Count - 1];

                                    if (o_HtmlTableRow.Cells.Count == 3)
                                    {
                                        o_HtmlTableRow = new HtmlTableRow();
                                        HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                        HtmlAnchor o_HtmlAnchor = new HtmlAnchor();

                                        o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                        o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");
                                        o_HtmlTableRow.Height = "30px";

                                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                        AirportName_Table.Rows.Add(o_HtmlTableRow);
                                    }
                                    else
                                    {
                                        HtmlTableCell o_HtmlTableCell = new HtmlTableCell();
                                        HtmlAnchor o_HtmlAnchor = new HtmlAnchor();

                                        o_HtmlAnchor.HRef = "Knows_Detail.aspx?Knows_ID=" + o_Knows.Knows_ID.ToString();
                                        o_HtmlAnchor.InnerText = o_Knows.Knows_Title;
                                        o_HtmlAnchor.Style.Add(HtmlTextWriterStyle.Color, "#0355a3");

                                        o_HtmlTableCell.Controls.Add(o_HtmlAnchor);
                                        o_HtmlTableRow.Controls.Add(o_HtmlTableCell);
                                    }
                                }
                            }                     
                        }
                    }
                }
            }
        }
    }
}

