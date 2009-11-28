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

using Utility;

namespace Web
{
    public partial class LowFare : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkLowFare(HyperLink_LowFare);

                if (g_Member == null)
                {
                    LowFare_Tips.Visible = true;
                    LowFare_Content.Visible = false;
                }
                else
                {
                    LowFare_Tips.Visible = false;
                    LowFare_Content.Visible = true;
                }
            }
        }

        protected void LowFare_Type_CheckedChanged(object sender, EventArgs e)
        {
            if (LowFare_Type1.Checked)
            {
                LowFare_Text_Type1.Visible = true;
                LowFare_Text_Type2.Visible = false;
                LowFare_Text_Type3.Visible = false;

                LowFare_Flexibility_TD.Visible = true;
            }

            if (LowFare_Type2.Checked)
            {
                LowFare_Text_Type1.Visible = false;
                LowFare_Text_Type2.Visible = true;
                LowFare_Text_Type3.Visible = false;

                LowFare_Flexibility_TD.Visible = true;
            }

            if (LowFare_Type3.Checked)
            {
                LowFare_Text_Type1.Visible = false;
                LowFare_Text_Type2.Visible = false;
                LowFare_Text_Type3.Visible = true;

                LowFare_Flexibility_TD.Visible = false;
            }
        }

        protected void LowFare_Submit_Click(object sender, EventArgs e)
        {
            if (g_Member == null)
                ResponseError("超时请重新登录");

            if (LowFare_Type1.Checked)
            {
                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type1.Text))
                    ResponseError("请输入From");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type1.Text))
                    ResponseError("请输入To");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type1.Text))
                    ResponseError("请输入Departing日期");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type1.SelectedValue))
                    ResponseError("请选择Departing时间段");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Returning_Text_Type1.Text))
                    ResponseError("请输入Returning日期");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time2_Select_Type1.SelectedValue))
                    ResponseError("请选择Returning时间段");

                Application.Lock();
                BLL.LowFare_Detail b_LowFare_Detail = new BLL.LowFare_Detail();

                Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                e_LowFare_Detail.LowFare_Detail_ID = 0;
                e_LowFare_Detail.LowFare_Detail_LowFare_ID = b_LowFare_Detail.LowFare_Detail_Next_LowFare_ID();
                e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type1.Text;
                e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type1.Text;
                e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type1.Text;
                e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type1.Text;
                e_LowFare_Detail.LowFare_Detail_Returning = LowFare_Detail_Returning_Text_Type1.Text;
                e_LowFare_Detail.LowFare_Detail_Time2 = LowFare_Detail_Time2_Select_Type1.Text;

                Entity.LowFare e_LowFare = new Entity.LowFare();
                e_LowFare.LowFare_Type = 1;

                if (LowFare_Flexibility.Checked)
                    e_LowFare.LowFare_Flexibility = true;
                else
                    e_LowFare.LowFare_Flexibility = false;

                e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();
                e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail);

                e_LowFare.LowFare_Adults = Convert.ToInt32(LowFare_Adults.SelectedValue);
                e_LowFare.LowFare_Children = Convert.ToInt32(LowFare_Children.SelectedValue);
                e_LowFare.LowFare_Infants = Convert.ToInt32(LowFare_Infants.SelectedValue);
                e_LowFare.LowFare_Airline = LowFare_Airline.SelectedValue;
                e_LowFare.LowFare_Class = LowFare_Class.SelectedValue;
                e_LowFare.LowFare_Member_ID = g_Member;
                e_LowFare.LowFare_AdminUser_ID = null;
                e_LowFare.LowFare_Status = 0;
                e_LowFare.LowFare_AddTime = DateTime.Now;

                BLL.LowFare b_LowFare = new BLL.LowFare();
                b_LowFare.Insert_LowFare(e_LowFare);

                Application.UnLock();

                ResponseSuccess("提交成功");
            }

            if (LowFare_Type2.Checked)
            {
                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type2.Text))
                    ResponseError("请输入From");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type2.Text))
                    ResponseError("请输入To");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type2.Text))
                    ResponseError("请输入Departing日期");

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type2.SelectedValue))
                    ResponseError("请选择Departing时间段");
                
                Application.Lock();
                BLL.LowFare_Detail b_LowFare_Detail = new BLL.LowFare_Detail();

                Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                e_LowFare_Detail.LowFare_Detail_ID = 0;
                e_LowFare_Detail.LowFare_Detail_LowFare_ID = b_LowFare_Detail.LowFare_Detail_Next_LowFare_ID();
                e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type2.Text;
                e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type2.Text;
                e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type2.Text;
                e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type2.Text;
               
                Entity.LowFare e_LowFare = new Entity.LowFare();
                e_LowFare.LowFare_Type = 2;

                if (LowFare_Flexibility.Checked)
                    e_LowFare.LowFare_Flexibility = true;
                else
                    e_LowFare.LowFare_Flexibility = false;

                e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();
                e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail);

                e_LowFare.LowFare_Adults = Convert.ToInt32(LowFare_Adults.SelectedValue);
                e_LowFare.LowFare_Children = Convert.ToInt32(LowFare_Children.SelectedValue);
                e_LowFare.LowFare_Infants = Convert.ToInt32(LowFare_Infants.SelectedValue);
                e_LowFare.LowFare_Airline = LowFare_Airline.SelectedValue;
                e_LowFare.LowFare_Class = LowFare_Class.SelectedValue;
                e_LowFare.LowFare_Member_ID = g_Member;
                e_LowFare.LowFare_AdminUser_ID = null;
                e_LowFare.LowFare_Status = 0;
                e_LowFare.LowFare_AddTime = DateTime.Now;

                BLL.LowFare b_LowFare = new BLL.LowFare();
                b_LowFare.Insert_LowFare(e_LowFare);

                Application.UnLock();

                ResponseSuccess("提交成功");
            }

            if (LowFare_Type3.Checked)
            {
                BLL.LowFare_Detail b_LowFare_Detail = new BLL.LowFare_Detail();
                Entity.LowFare e_LowFare = new Entity.LowFare();

                Application.Lock();
                int LowFare_Detail_LowFare_ID = b_LowFare_Detail.LowFare_Detail_Next_LowFare_ID();
                Application.UnLock();

                if (!VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_1.Text) && !VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_2.Text) && !VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_3.Text) && !VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_4.Text))
                    ResponseError("请输入From");

                if (VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_1.Text))
                {
                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type3_1.Text))
                        ResponseError("请输入Flight 1 To");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type3_1.Text))
                        ResponseError("请输入请输入Flight 1 Departing日期");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type3_1.SelectedValue))
                        ResponseError("请选择请输入Flight 1 Departing时间段");

                    Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                    e_LowFare_Detail.LowFare_Detail_ID = 0;
                    e_LowFare_Detail.LowFare_Detail_LowFare_ID = LowFare_Detail_LowFare_ID;
                    e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type3_1.Text;
                    e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type3_1.Text;
                    e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type3_1.Text;
                    e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type3_1.Text;

                    if (e_LowFare.LowFare_Detail_ID == null)
                        e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();

                    e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail);                    
                }

                if (VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_2.Text))
                {
                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type3_2.Text))
                        ResponseError("请输入Flight 2 To");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type3_2.Text))
                        ResponseError("请输入Flight 2 Departing日期");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type3_2.SelectedValue))
                        ResponseError("请选择Flight 2 Departing时间段");
                                        
                    Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                    e_LowFare_Detail.LowFare_Detail_ID = 0;
                    e_LowFare_Detail.LowFare_Detail_LowFare_ID = LowFare_Detail_LowFare_ID;
                    e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type3_2.Text;
                    e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type3_2.Text;
                    e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type3_2.Text;
                    e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type3_2.Text;

                    if (e_LowFare.LowFare_Detail_ID == null)
                        e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();

                    e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail);                    
                }

                if (VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_3.Text))
                {
                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type3_3.Text))
                        ResponseError("请输入Flight 3 To");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type3_3.Text))
                        ResponseError("请输入Flight 3 Departing日期");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type3_3.SelectedValue))
                        ResponseError("请选择Flight 3 Departing时间段");

                    Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                    e_LowFare_Detail.LowFare_Detail_ID = 0;
                    e_LowFare_Detail.LowFare_Detail_LowFare_ID = LowFare_Detail_LowFare_ID;
                    e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type3_3.Text;
                    e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type3_3.Text;
                    e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type3_3.Text;
                    e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type3_3.Text;

                    if (e_LowFare.LowFare_Detail_ID == null)
                        e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();

                    e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail); 
                }

                if (VerifyUtility.IsString_NotNull(LowFare_Detail_From_Text_Type3_4.Text))
                {
                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_To_Text_Type3_4.Text))
                        ResponseError("请输入Flight 4 To");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Departing_Text_Type3_4.Text))
                        ResponseError("请输入Flight 4 Departing日期");

                    if (!VerifyUtility.IsString_NotNull(LowFare_Detail_Time1_Select_Type3_4.SelectedValue))
                        ResponseError("请选择Flight 4 Departing时间段");

                    Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                    e_LowFare_Detail.LowFare_Detail_ID = 0;
                    e_LowFare_Detail.LowFare_Detail_LowFare_ID = LowFare_Detail_LowFare_ID;
                    e_LowFare_Detail.LowFare_Detail_From = LowFare_Detail_From_Text_Type3_4.Text;
                    e_LowFare_Detail.LowFare_Detail_To = LowFare_Detail_To_Text_Type3_4.Text;
                    e_LowFare_Detail.LowFare_Detail_Departing = LowFare_Detail_Departing_Text_Type3_4.Text;
                    e_LowFare_Detail.LowFare_Detail_Time1 = LowFare_Detail_Time1_Select_Type3_4.Text;

                    if (e_LowFare.LowFare_Detail_ID == null)
                        e_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();

                    e_LowFare.LowFare_Detail_ID.Add(e_LowFare_Detail); 
                }

                Application.Lock();
                e_LowFare.LowFare_Type = 3;

                if (LowFare_Flexibility.Checked)
                    e_LowFare.LowFare_Flexibility = true;
                else
                    e_LowFare.LowFare_Flexibility = false;
                                
                e_LowFare.LowFare_Adults = Convert.ToInt32(LowFare_Adults.SelectedValue);
                e_LowFare.LowFare_Children = Convert.ToInt32(LowFare_Children.SelectedValue);
                e_LowFare.LowFare_Infants = Convert.ToInt32(LowFare_Infants.SelectedValue);
                e_LowFare.LowFare_Airline = LowFare_Airline.SelectedValue;
                e_LowFare.LowFare_Class = LowFare_Class.SelectedValue;
                e_LowFare.LowFare_Member_ID = g_Member;
                e_LowFare.LowFare_AdminUser_ID = null;
                e_LowFare.LowFare_Status = 0;
                e_LowFare.LowFare_AddTime = DateTime.Now;

                BLL.LowFare b_LowFare = new BLL.LowFare();
                b_LowFare.Insert_LowFare(e_LowFare);

                Application.UnLock();
                ResponseSuccess("提交成功");
            }
        }
    }
}
