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
    public partial class News_Detail : PageBase
    {
        private BLL.News g_News;
        private Entity.News e_News;
        private int g_News_ID = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            g_News = new BLL.News();

            if (VerifyUtility.IsNumber_NotNull(Request["News_ID"]) && Request["News_ID"] != "0")
                g_News_ID = Convert.ToInt32(Request["News_ID"]);

            if (!IsPostBack)
            {
                GetNewsTXT(News_Name);
                SetLanguageControl(News_LanguageID);

                switch (g_Action_ID)
                {
                    case 1:
                        News_AddTime_TD.Visible = false;
                        News_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_News_ID == 0)
                            ResponseError("参数错误");

                        e_News = g_News.Select_News(g_News_ID);
                        if (e_News != null)
                        {
                            News_LanguageID.SelectedValue = e_News.News_LanguageID.ToString();
                            News_AddTime.Text = e_News.News_AddTime.ToString();
                            News_Title.Text = e_News.News_Title;
                            News_Content.Value = e_News.News_Content;
                        }

                        News_Submit.Text = " 修改 ";
                        break;

                    case 3:
                        if (g_News_ID == 0)
                            ResponseError("参数错误");

                        g_News.Delete_News(g_News_ID);
                        ResponseClose("删除成功");
                        break;
                }                
            }
        }

        protected void News_Submit_Click(object sender, EventArgs e)
        {
            switch (g_Action_ID)
            {
                case 1:
                    g_News.Insert_News(g_News_ClassID, Convert.ToInt32(News_LanguageID.SelectedValue), News_Title.Text, News_Content.Value);
                    g_TipsTable.Visible = true;
                    g_MainTable.Visible = false;
                    TipsMessage.Text = "添加成功";
                    TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&News_ID=1&News_ClassID=" + g_News_ClassID.ToString();
                    TipsLink1.Text = "继续添加";
                    break;

                case 2:
                    g_News.Update_News(g_News_ID, g_News_ClassID, Convert.ToInt32(News_LanguageID.SelectedValue), News_Title.Text, News_Content.Value);
                    g_TipsTable.Visible = true;
                    g_MainTable.Visible = false;
                    TipsMessage.Text = "修改成功";
                    TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&News_ID=" + g_News_ID.ToString() + "&News_ClassID=" + g_News_ClassID.ToString();
                    TipsLink1.Text = "继续修改";
                    break;

                default:
                    break;
            }
        }
    }
}
