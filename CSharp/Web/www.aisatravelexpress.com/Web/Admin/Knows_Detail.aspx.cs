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
    public partial class Knows_Detail : PageBase
    {
        private BLL.Knows b_Knows;
        private Entity.Knows e_Knows;

        protected void Page_Load(object sender, EventArgs e)
        {
            b_Knows = new BLL.Knows();

            if (!IsPostBack)
            {
                GetKnowsTXT(Knows_Name);
                SetLanguageControl(Knows_LanguageID);
                SetKnowsClassControl(Knows_ClassID);

                switch (g_Action_ID)
                {
                    case 1:
                        Knows_AddTime_TD.Visible = false;
                        Knows_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_Knows_ID == 0)
                            ResponseError("参数错误");

                        e_Knows = b_Knows.Select_Knows(g_Knows_ID);
                        if (e_Knows != null)
                        {
                            Knows_LanguageID.SelectedValue = e_Knows.Knows_LanguageID.ToString();
                            Knows_ClassID.SelectedValue = e_Knows.Knows_ClassID.ToString();
                            Knows_Summary.Text = e_Knows.Knows_Summary;
                            Knows_Title.Text = e_Knows.Knows_Title;
                            Knows_Content.Value = e_Knows.Knows_Content;
                            Knows_AddTime.Text = e_Knows.Knows_AddTime.ToString();                                                       
                        }

                        Knows_Submit.Text = " 修改 ";
                        break;

                    case 3:
                        if (g_Knows_ID == 0)
                            ResponseError("参数错误");

                        b_Knows.Delete_Knows(g_Knows_ID);
                        ResponseClose("删除成功");
                        break;
                }
            }
        }

        protected void Knows_Submit_Click(object sender, EventArgs e)
        {
            switch (g_Action_ID)
            {
                case 1:
                    b_Knows.Insert_Knows(Convert.ToInt32(Knows_ClassID.SelectedValue), Convert.ToInt32(Knows_LanguageID.SelectedValue), Knows_Summary.Text, Knows_Title.Text, Knows_Content.Value);
                    g_TipsTable.Visible = true;
                    g_MainTable.Visible = false;
                    TipsMessage.Text = "添加成功";
                    TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Knows_ID=1";
                    TipsLink1.Text = "继续添加";
                    break;

                case 2:
                    b_Knows.Update_Knows(g_Knows_ID, Convert.ToInt32(Knows_ClassID.SelectedValue), Convert.ToInt32(Knows_LanguageID.SelectedValue), Knows_Summary.Text, Knows_Title.Text, Knows_Content.Value);
                    g_TipsTable.Visible = true;
                    g_MainTable.Visible = false;
                    TipsMessage.Text = "修改成功";
                    TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Knows_ID=" + g_Knows_ID.ToString();
                    TipsLink1.Text = "继续修改";
                    break;

                default:
                    break;
            }
        }
    }
}

