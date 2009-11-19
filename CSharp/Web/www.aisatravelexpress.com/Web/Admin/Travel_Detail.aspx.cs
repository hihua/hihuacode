using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.IO;
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
                        Travel_PreViews_TD.Visible = false;
                        Travel_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_Travel_ID == 0)
                            ResponseError("参数错误");

                        GetTravel();

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
            else
            {
                string ControlID = Request.Params["__EVENTTARGET"];
                if (VerifyUtility.IsString_NotNull(ControlID) && ControlID.IndexOf("Del_Travel_PreViews_") == 0)
                {
                    string[] Travel_PreViews = ControlID.Split(new string[] { "_" }, StringSplitOptions.RemoveEmptyEntries);
                    if (Travel_PreViews.Length == 4 && VerifyUtility.IsNumber_NotNull(Travel_PreViews[3]))
                    {
                        int Travel_Images = Convert.ToInt32(Travel_PreViews[3]);
                        b_Travel.Delete_Travel_PreViews(g_Travel_ID, Travel_Images, Server.MapPath("../" + g_Travel_Images + "/"));
                        ResponseSuccess("删除成功", Request.RawUrl);
                    }
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

            if (!VerifyUtility.Check_Date(Travel_StartDate.Text))
                ResponseError("出团开始日期, 请输入正确的日期格式如: " + DateTime.Now.ToString("yyyy-MM-dd"));

            if (!VerifyUtility.IsString_NotNull(Travel_EndDate.Text))
                ResponseError("请输入出团结束日期");

            if (!VerifyUtility.Check_Date(Travel_EndDate.Text))
                ResponseError("出团结束日期, 请输入正确的日期格式如: " + DateTime.Now.ToString("yyyy-MM-dd"));

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
                    {
                        if (Request.Files["Travel_PreView1"] == null)
                            ResponseError("请选择上传预览图1");

                        if (Request.Files["Travel_PreView2"] == null)
                            ResponseError("请选择上传预览图2");

                        string o_DateTime = DateTime.Now.ToString("yyyyMMddHHmmss");
                        string Travel_PreView_1 = "PreView_" + o_DateTime + "-1";
                        string Travel_PreView_2 = "PreView_" + o_DateTime + "-2";

                        string UploadFileExt = "";
                        string UploadFile = "";
                        HttpPostedFile o_HttpPostedFile = null;

                        o_HttpPostedFile = Request.Files["Travel_PreView1"];
                        UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                        if (VerifyUtility.IsString_NotNull(UploadFile))
                            ResponseError(UploadFile);

                        o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + Travel_PreView_1 + "." + UploadFileExt);
                        Travel_PreView_1 += "." + UploadFileExt;

                        o_HttpPostedFile = Request.Files["Travel_PreView2"];
                        UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                        if (VerifyUtility.IsString_NotNull(UploadFile))
                            ResponseError(UploadFile);

                        o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + Travel_PreView_2 + "." + UploadFileExt);
                        Travel_PreView_2 += "." + UploadFileExt;

                        List<string> o_Travel_PreViews = null;
                        if (VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
                        {
                            int m = Convert.ToInt32(Travel_PreViews_Num.Text);
                            o_Travel_PreViews = new List<string>();

                            for (int i = 0; i < m; i++)
                            {
                                o_HttpPostedFile = Request.Files["Travel_PreViews_" + i.ToString()];
                                if (o_HttpPostedFile != null)
                                {
                                    UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                                    if (VerifyUtility.IsString_NotNull(UploadFile))
                                        ResponseError(UploadFile);

                                    o_DateTime = "PreViews_" + DateTime.Now.ToString("yyyyMMddHHmmss") + "-" + i.ToString();
                                    o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + o_DateTime + "." + UploadFileExt);                                    
                                    o_Travel_PreViews.Add(o_DateTime + "." + UploadFileExt);
                                }
                            }
                        }

                        b_Travel.Insert_Travel(Convert.ToInt32(Travel_LanguageID.SelectedValue), Convert.ToInt32(Travel_TypeID.SelectedValue), Travel_Code.Text, Travel_Name.Text, Travel_Price.Text, Convert.ToInt32(Travel_Points.Text), DateTime.Parse(Travel_StartDate.Text), DateTime.Parse(Travel_EndDate.Text), Travel_Views.Text, Travel_Route.Value, Travel_PreView_1, Travel_PreView_2, o_Travel_PreViews, Travel_StartAddr.Text, Travel_EndAddr.Text);
                        g_TipsTable.Visible = true;
                        g_MainTable.Visible = false;
                        TipsMessage.Text = "添加成功";
                        TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Travel_ID=0";
                        TipsLink1.Text = "继续添加";
                    }
                    break;

                case 2:
                    {
                        e_Travel = b_Travel.Select_Travel(g_Travel_ID);
                                                
                        if (!VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
                            ResponseError("请输入正确数字");
                                                
                        string o_DateTime = DateTime.Now.ToString("yyyyMMddHHmmss");
                        string Travel_PreView_1 = "";
                        string Travel_PreView_2 = "";

                        string UploadFileExt = "";
                        string UploadFile = "";
                        HttpPostedFile o_HttpPostedFile = null;

                        o_HttpPostedFile = Request.Files["Travel_PreView1"];
                        if (o_HttpPostedFile != null && VerifyUtility.IsString_NotNull(o_HttpPostedFile.FileName))
                        {
                            UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                            if (VerifyUtility.IsString_NotNull(UploadFile))
                                ResponseError(UploadFile);

                            Travel_PreView_1 = "PreView_" + o_DateTime + "-1" + "." + UploadFileExt;
                            o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + Travel_PreView_1);                            
                        }

                        o_HttpPostedFile = Request.Files["Travel_PreView2"];
                        if (o_HttpPostedFile != null && VerifyUtility.IsString_NotNull(o_HttpPostedFile.FileName))
                        {
                            UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                            if (VerifyUtility.IsString_NotNull(UploadFile))
                                ResponseError(UploadFile);

                            Travel_PreView_2 = "PreView_" + o_DateTime + "-2" + "." + UploadFileExt;
                            o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + Travel_PreView_2);                            
                        }

                        e_Travel.Travel_ID = g_Travel_ID;
                        e_Travel.Travel_LanguageID = Convert.ToInt32(Travel_LanguageID.SelectedValue);
                        e_Travel.Travel_TypeID = Convert.ToInt32(Travel_TypeID.SelectedValue);
                        e_Travel.Travel_Code = Travel_Code.Text;
                        e_Travel.Travel_Name = Travel_Name.Text;
                        e_Travel.Travel_Price = Travel_Price.Text;
                        e_Travel.Travel_Points = Convert.ToInt32(Travel_Points.Text);
                        e_Travel.Travel_StartDate = DateTime.Parse(Travel_StartDate.Text);
                        e_Travel.Travel_EndDate = DateTime.Parse(Travel_EndDate.Text);
                        e_Travel.Travel_Views = Travel_Views.Text;
                        e_Travel.Travel_Route = Travel_Route.Value;                                                
                        e_Travel.Travel_StartAddr = Travel_StartAddr.Text;
                        e_Travel.Travel_EndAddr = Travel_EndAddr.Text;

                        if (VerifyUtility.IsString_NotNull(Travel_PreView_1))
                        {
                            if (File.Exists(Server.MapPath("../" + g_Travel_Images + "/" + e_Travel.Travel_PreView1)))
                                File.Delete(Server.MapPath("../" + g_Travel_Images + "/" + e_Travel.Travel_PreView1));

                            e_Travel.Travel_PreView1 = Travel_PreView_1;
                        }

                        if (VerifyUtility.IsString_NotNull(Travel_PreView_2))
                        {
                            if (File.Exists(Server.MapPath("../" + g_Travel_Images + "/" + e_Travel.Travel_PreView2)))
                                File.Delete(Server.MapPath("../" + g_Travel_Images + "/" + e_Travel.Travel_PreView2));

                            e_Travel.Travel_PreView2 = Travel_PreView_2;
                        }

                        if (VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
                        {
                            int m = Convert.ToInt32(Travel_PreViews_Num.Text);
                            
                            for (int i = 0; i < m; i++)
                            {
                                o_HttpPostedFile = Request.Files["Travel_PreViews_" + i.ToString()];
                                if (o_HttpPostedFile != null && VerifyUtility.IsString_NotNull(o_HttpPostedFile.FileName))
                                {
                                    UploadFile = VerifyUtility.Check_UploadFile(o_HttpPostedFile.FileName, ref UploadFileExt);
                                    if (VerifyUtility.IsString_NotNull(UploadFile))
                                        ResponseError(UploadFile);

                                    o_DateTime = "PreViews_" + DateTime.Now.ToString("yyyyMMddHHmmss") + "-" + i.ToString();
                                    o_HttpPostedFile.SaveAs(Server.MapPath("../" + g_Travel_Images + "/") + o_DateTime + "." + UploadFileExt);

                                    b_Travel.Update_Travel_PreViews(e_Travel, i, o_DateTime + "." + UploadFileExt, Server.MapPath("../" + g_Travel_Images + "/"));
                                }
                            }
                        }

                        b_Travel.Update_Travel(e_Travel);
                        g_TipsTable.Visible = true;
                        g_MainTable.Visible = false;
                        TipsMessage.Text = "修改成功";
                        TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Travel_ID=" + g_Travel_ID.ToString();
                        TipsLink1.Text = "继续修改";
                    }
                    break;
            }
        }

        private void GetTravel()
        {
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
                Travel_StartDate.Text = e_Travel.Travel_StartDate.ToString("yyyy-MM-dd");
                Travel_EndDate.Text = e_Travel.Travel_EndDate.ToString("yyyy-MM-dd");
                Travel_Views.Text = e_Travel.Travel_Views;
                Travel_Route.Value = e_Travel.Travel_Route;
                Travel_PreView1_Image.ImageUrl = "../" + g_Travel_Images + "/" + e_Travel.Travel_PreView1;
                Travel_PreView2_Image.ImageUrl = "../" + g_Travel_Images + "/" + e_Travel.Travel_PreView2;

                if (e_Travel.Travel_PreViews != null && e_Travel.Travel_PreViews.Count > 0)
                {
                    int i = 0;

                    if (!VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
                        Travel_PreViews_Num.Text = e_Travel.Travel_PreViews.Count.ToString();

                    foreach (string Travel_PreViews in e_Travel.Travel_PreViews)
                    {
                        Image o_Image = new Image();
                        o_Image.ImageUrl = "../" + g_Travel_Images + "/" + Travel_PreViews;
                        Travel_PreViews_Panel.Controls.Add(o_Image);

                        LinkButton o_LinkButton = new LinkButton();
                        o_LinkButton.CssClass = "AdminToolsLink2";
                        o_LinkButton.ID = "Del_Travel_PreViews_" + i.ToString();
                        o_LinkButton.OnClientClick = "return IsDel()";
                        o_LinkButton.CommandArgument = Travel_PreViews;
                        o_LinkButton.Text = "删除";
                        Travel_PreViews_Panel.Controls.Add(o_LinkButton);

                        HtmlGenericControl o_HtmlGenericControl;
                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "<br/>";
                        Travel_PreViews_Panel.Controls.Add(o_HtmlGenericControl);

                        FileUpload o_FileUpload = new FileUpload();
                        o_FileUpload.ID = "Travel_PreViews_" + i.ToString();
                        Travel_PreViews_Panel.Controls.Add(o_FileUpload);

                        o_HtmlGenericControl = new HtmlGenericControl();
                        o_HtmlGenericControl.InnerHtml = "<br/>";
                        Travel_PreViews_Panel.Controls.Add(o_HtmlGenericControl);

                        i++;
                    }

                    Travel_PreViews_TD.Visible = true;
                }
                else
                {
                    Travel_PreViews_Num.Text = "0";
                    Travel_PreViews_TD.Visible = false;
                }

                Travel_StartAddr.Text = e_Travel.Travel_StartAddr;
                Travel_EndAddr.Text = e_Travel.Travel_EndAddr;
            }
        }

        protected void Travel_PreViews_Buttom_Click(object sender, EventArgs e)
        {
            if (VerifyUtility.IsNumber_NotNull(Travel_PreViews_Num.Text))
            {
                GetTravel();

                int m = Convert.ToInt32(Travel_PreViews_Num.Text);
                int n = 0;

                if (g_Action_ID == 2)
                {
                    if (m > 0)
                    {
                        if (m > e_Travel.Travel_PreViews.Count)
                            n = e_Travel.Travel_PreViews.Count;                        
                        else
                            return;
                    }
                    else
                        return;
                }

                for (int i = n; i < m; i++)
                {
                    FileUpload o_FileUpload = new FileUpload();                    
                    o_FileUpload.ID = "Travel_PreViews_" + i.ToString();

                    HtmlGenericControl o_HtmlGenericControl = new HtmlGenericControl();
                    o_HtmlGenericControl.InnerHtml = "<br/>";

                    Travel_PreViews_Panel.Controls.Add(o_FileUpload);
                    Travel_PreViews_Panel.Controls.Add(o_HtmlGenericControl);
                }

                Travel_PreViews_TD.Visible = true;
            }
            else
                ResponseError("请输入正确数字");
        }                
    }
}
