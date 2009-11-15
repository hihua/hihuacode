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
                        Travel_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_Travel_ID == 0)
                            ResponseError("参数错误");

                        Travel_Submit.Text = " 修改 ";
                        break;

                    case 3:
                        if (g_Travel_ID == 0)
                            ResponseError("参数错误");

                        b_Travel.Delete_Travel(g_Travel_ID, Server.MapPath("../Travel_Images/"));
                        ResponseClose("删除成功");
                        break;
                }
            }
        }

        protected void Travel_Submit_Click(object sender, EventArgs e)
        {

        }
    }
}
