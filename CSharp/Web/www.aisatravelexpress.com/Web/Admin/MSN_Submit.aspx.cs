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
    public partial class MSN_Submit : PageBase
    {
        private int MSN_ID = 0;
        private string MSN_Name = "";
        private string MSN_Invitee = "";

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                switch (g_Action_ID)
                {
                    case 1:
                        if (VerifyUtility.IsString_NotNull(Request.Form["MSN_Name"]))
                            MSN_Name = Request.Form["MSN_Name"];
                        else
                            ResponseError("请输入MSN_Name");

                        if (VerifyUtility.IsString_NotNull(Request.Form["MSN_Invitee"]))
                            MSN_Invitee = Request.Form["MSN_Invitee"];
                        else
                            ResponseError("请输入MSN_Invitee");

                        MSN_Insert();
                        break;

                    case 2:
                        if (VerifyUtility.IsNumber_NotNull(Request["MSN_ID"]))
                            MSN_ID = Convert.ToInt32(Request["MSN_ID"]);
                        else
                            ResponseError("缺少MSN_ID");

                        if (VerifyUtility.IsString_NotNull(Request.Form["MSN_Name"]))
                            MSN_Name = Request.Form["MSN_Name"];
                        else
                            ResponseError("请输入MSN_Name");

                        if (VerifyUtility.IsString_NotNull(Request.Form["MSN_Invitee"]))
                            MSN_Invitee = Request.Form["MSN_Invitee"];
                        else
                            ResponseError("请输入MSN_Invitee");

                        MSN_Update();
                        break;

                    case 3:
                        if (VerifyUtility.IsNumber_NotNull(Request["MSN_ID"]))
                            MSN_ID = Convert.ToInt32(Request["MSN_ID"]);
                        else
                            ResponseError("缺少MSN_ID");

                        MSN_Delete();
                        break;

                    default:
                        ResponseError("参数错误");
                        break;
                }
            }
        }

        private void MSN_Insert()
        {
            BLL.MSN b_MSN = new BLL.MSN();
            b_MSN.Insert_MSN(MSN_Name, MSN_Invitee);

            ResponseSuccess("添加成功", "MSN.aspx");
        }

        private void MSN_Update()
        {
            BLL.MSN b_MSN = new BLL.MSN();
            b_MSN.Update_MSN(MSN_ID, MSN_Name, MSN_Invitee);

            ResponseSuccess("修改成功", "MSN.aspx");
        }

        private void MSN_Delete()
        {
            BLL.MSN b_MSN = new BLL.MSN();
            b_MSN.Delete_MSN(MSN_ID);

            ResponseSuccess("删除成功", "MSN.aspx");
        }
    }
}
