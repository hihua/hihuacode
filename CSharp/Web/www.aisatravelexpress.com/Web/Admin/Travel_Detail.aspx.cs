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
    public partial class Travel_Detail : PageBase
    {
        private BLL.Travel b_Travel;
        private Entity.Travel e_Travel;
        
        protected void Page_Load(object sender, EventArgs e)
        {
            b_Travel = new BLL.Travel();

            if (!IsPostBack)
            {
                GetTravelTXT(Travel_Title);
                SetLanguageControl(Travel_LanguageID);
                SetTravelTypeControl(Travel_TypeID);

                switch (g_Action_ID)
                {
                    case 1:
                        Travel_AddTime_TD.Visible = false;
                        Travel_PreView1_Image.Visible = false;
                        Travel_PreView2_Image.Visible = false;
                        Travel_PreViews_Panel.Visible = false;                        
                        Travel_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_Travel_ID == 0)
                            ResponseError("参数错误");

                        e_Travel = b_Travel.Select_Travel(g_Travel_ID);
                        if (e_Travel != null)
                        {
                            Travel_AddTime.Text = e_Travel.Travel_AddTime.ToString();
                            Travel_LanguageID.SelectedValue = e_Travel.Travel_LanguageID.ToString();
                            Travel_TypeID.SelectedValue = e_Travel.Travel_TypeID.ToString();
                            Travel_Code.Text = e_Travel.Travel_Code;
                            Travel_Name.Text = e_Travel.Travel_Name;
                            Travel_Price.Text = e_Travel.Travel_Price;
                            Travel_Points.Text = e_Travel.Travel_Points.ToString();
                            Travel_StartDate.Text = e_Travel.Travel_StartDate.ToString();
                            Travel_EndDate.Text = e_Travel.Travel_EndDate.ToString();
                            Travel_Views.Text = e_Travel.Travel_Views;
                            Travel_Route.Value = e_Travel.Travel_Route;
                            Travel_PreView1_Image.ImageUrl = "../" + g_Travel_Images + "/" + e_Travel.Travel_PreView1;
                            Travel_PreView2_Image.ImageUrl = "../" + g_Travel_Images + "/" + e_Travel.Travel_PreView2;

                            if (e_Travel.Travel_PreViews != null)
                            {
                                Travel_PreViews_Num.Text = e_Travel.Travel_PreViews.Length.ToString();
                                for (int i = 0; i < e_Travel.Travel_PreViews.Length; i++)
                                {
                                    FileUpload o_FileUpload = new FileUpload();                                    
                                    o_FileUpload.ID = "Travel_PreViews_" + i.ToString();

                                    HtmlGenericControl o_HtmlGenericControl = new HtmlGenericControl("br");

                                    Travel_PreViews_Panel.Controls.Add(o_FileUpload);
                                    Travel_PreViews_Panel.Controls.Add(o_HtmlGenericControl);
                                }

                                Travel_PreViews_Panel.Visible = true;
                            }
                        }

                        Travel_Submit.Text = " 修改 ";
                        break;

                    case 3:
                        if (g_Travel_ID == 0)
                            ResponseError("参数错误");

                        b_Travel.Delete_Travel(g_Travel_ID, Server.MapPath("../" + g_Travel_Images + "/"));
                        ResponseClose("删除成功");
                        break;
                }
            }
        }

        protected void Travel_Submit_Click(object sender, EventArgs e)
        {
            if (!VerifyUtility.IsNumber_NotNull(Travel_LanguageID.SelectedValue))
                ResponseError("请选择语言");

            if (!VerifyUtility.IsNumber_NotNull(Travel_TypeID.SelectedValue))
                ResponseError("请选择分类");

            if (!VerifyUtility.IsString_NotNull(Travel_Code.Text))
                ResponseError("请输入线路编号");

            if (!VerifyUtility.IsString_NotNull(Travel_Name.Text))
                ResponseError("请输入标题");

            if (!VerifyUtility.IsString_NotNull(Travel_Price.Text))
                ResponseError("请输入价格");

            if (!VerifyUtility.IsNumber_NotNull(Travel_Points.Text))
                ResponseError("请输入积分信息");

            if (!VerifyUtility.IsString_NotNull(Travel_StartDate.Text))
                ResponseError("请输入出团开始日期");

            if (!VerifyUtility.IsString_NotNull(Travel_EndDate.Text))
                ResponseError("请输入出团结束日期");

            if (!VerifyUtility.IsString_NotNull(Travel_Views.Text))
                ResponseError("请输入主要景点");

            if (!VerifyUtility.IsString_NotNull(Travel_Route.Value))
                ResponseError("请输入详细行程");

            if (!VerifyUtility.IsString_NotNull(Travel_StartAddr.Text))
                ResponseError("请输入出发地");

            if (!VerifyUtility.IsString_NotNull(Travel_EndAddr.Text))
                ResponseError("请输入结束地");                       

            switch (g_Action_ID)
            {
                case 1:                    
                    if (Request.Files["Travel_PreView1"] == null)
                        ResponseError("请选择上传预览图1");

                    if (Request.Files["Travel_PreView2"] == null)
                        ResponseError("请选择上传预览图2");

                    for (int i = 1;i <= 2;i++)
                    {
                        string o_ImagesFile = DateTime.Now.ToString("PreView_yyyyMMddHHmmss") + "-" + i.ToString();

                        HttpPostedFile o_HttpPostedFile = Request.Files["Travel_PreViews_" + i.ToString()];
                        o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + o_ImagesFile + o_HttpPostedFile.FileName.Substring(o_HttpPostedFile.FileName.Length - 4, 4));
                    }                                                   

                    if (VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
                    {
                        int m = Convert.ToInt32(Travel_PreViews_Num.Text);
                        for (int i = 0; i < m; i++)
                        {
                            HttpPostedFile o_HttpPostedFile = Request.Files["Travel_PreViews_" + i.ToString()];
                            if (o_HttpPostedFile != null)
                            {
                                string o_ImagesFile = DateTime.Now.ToString("PreViews_yyyyMMddHHmmss") + "-" + i.ToString();
                                o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + o_ImagesFile + o_HttpPostedFile.FileName.Substring(o_HttpPostedFile.FileName.Length - 4, 4));
                            }
                        }
                    }
                    break;

                case 2:
                    
                    break;
            }
        }

        protected void Travel_PreViews_Buttom_Click(object sender, EventArgs e)
        {
            if (VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
            {
                int m = Convert.ToInt32(Travel_PreViews_Num.Text);
                for (int i = 0; i < m; i++)
                {
                    FileUpload o_FileUpload = new FileUpload();                    
                    o_FileUpload.ID = "Travel_PreViews_" + i.ToString();

                    HtmlGenericControl o_HtmlGenericControl = new HtmlGenericControl("br");

                    Travel_PreViews_Panel.Controls.Add(o_FileUpload);
                    Travel_PreViews_Panel.Controls.Add(o_HtmlGenericControl);
                }

                Travel_PreViews_Panel.Visible = true;
            }
            else
                ResponseError("请输入正确数字");
        }
    }
}
