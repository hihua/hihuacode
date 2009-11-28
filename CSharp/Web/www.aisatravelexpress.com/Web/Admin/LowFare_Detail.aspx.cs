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
    public partial class LowFare_Detail : PageBase
    {
        private BLL.LowFare b_LowFare;

        protected void Page_Load(object sender, EventArgs e)
        {
            b_LowFare = new BLL.LowFare();
                
            if (!IsPostBack)
            {                
                switch (g_Action_ID)
                {
                    case 2:
                        {
                            if (g_LowFare_ID == 0)
                                ResponseError("参数错误");

                            Entity.LowFare e_LowFare = b_LowFare.Select_LowFare(g_LowFare_ID);
                            GetLowFare(e_LowFare);
                        }
                        break;

                    case 3:
                        {
                            if (g_LowFare_ID == 0)
                                ResponseError("参数错误");

                            b_LowFare.Delete_LowFare(g_LowFare_ID);
                            ResponseClose("删除成功");
                        }
                        break;

                    case 4:
                        {
                            if (g_LowFare_ID == 0)
                                ResponseError("参数错误");

                            Entity.LowFare e_LowFare = b_LowFare.Select_LowFare(g_LowFare_ID);
                            e_LowFare.LowFare_AdminUser_ID = g_AdminUser;
                            e_LowFare.LowFare_Status = 1;
                            e_LowFare.LowFare_SubmitTime = DateTime.Now.ToString();
                            b_LowFare.Update_LowFare(e_LowFare);
                            ResponseClose("转换成功");
                        }
                        break;
                }
            }
        }

        private void GetLowFare(Entity.LowFare e_LowFare)
        {
            if (e_LowFare == null)
                return;

            switch (e_LowFare.LowFare_Type)
            {
                case 1:
                    LowFare_Type1.Checked = true;
                    LowFare_Text_Type1.Visible = true;
                    LowFare_Text_Type2.Visible = false;
                    LowFare_Text_Type3.Visible = false;

                    LowFare_Flexibility_TD.Visible = true;
                    break;

                case 2:
                    LowFare_Type2.Checked = true;
                    LowFare_Text_Type1.Visible = false;
                    LowFare_Text_Type2.Visible = true;
                    LowFare_Text_Type3.Visible = false;

                    LowFare_Flexibility_TD.Visible = true;
                    break;

                case 3:
                    LowFare_Type3.Checked = true;
                    LowFare_Text_Type1.Visible = false;
                    LowFare_Text_Type2.Visible = false;
                    LowFare_Text_Type3.Visible = true;

                    LowFare_Flexibility_TD.Visible = false;
                    break;
            }

            if (e_LowFare.LowFare_Flexibility)
                LowFare_Flexibility.Checked = true;
            else
                LowFare_Flexibility.Checked = false;

            LowFare_Adults.SelectedValue = e_LowFare.LowFare_Adults.ToString();
            LowFare_Children.SelectedValue = e_LowFare.LowFare_Children.ToString();
            LowFare_Infants.SelectedValue = e_LowFare.LowFare_Infants.ToString();
            LowFare_Airline.SelectedValue = e_LowFare.LowFare_Airline;
            LowFare_Class.SelectedValue = e_LowFare.LowFare_Class;
            LowFare_AddTime.Text = e_LowFare.LowFare_AddTime.ToString();

            if (e_LowFare.LowFare_Member_ID != null)
            {
                Member_Account.OnClientClick = "ActionMember(2, " + e_LowFare.LowFare_Member_ID.Member_ID.ToString() + ");return false;";
                Member_Account.Text = e_LowFare.LowFare_Member_ID.Member_Account;
                Member_Serial.Text = e_LowFare.LowFare_Member_ID.Member_Serial;
            }

            if (e_LowFare.LowFare_Status == 1)
                LowFare_Status.Checked = true;
            else
                LowFare_Status.Checked = false;

            if (e_LowFare.LowFare_AdminUser_ID != null)
            {
                AdminUser_Name.Text = e_LowFare.LowFare_AdminUser_ID.AdminUser_Name;
                AdminUser_NickName.Text = e_LowFare.LowFare_AdminUser_ID.AdminUser_NickName;
            }

            if (VerifyUtility.IsString_NotNull(e_LowFare.LowFare_SubmitTime))
                LowFare_SubmitTime.Text = e_LowFare.LowFare_SubmitTime;

            if (e_LowFare.LowFare_Detail_ID != null)
            {
                int i = 1;

                foreach (Entity.LowFare_Detail e_LowFare_Detail in e_LowFare.LowFare_Detail_ID)
                {
                    if (LowFare_Type1.Checked)
                    {
                        LowFare_Detail_From_Text_Type1.Text = e_LowFare_Detail.LowFare_Detail_From;
                        LowFare_Detail_To_Text_Type1.Text = e_LowFare_Detail.LowFare_Detail_To;
                        LowFare_Detail_Departing_Text_Type1.Text = e_LowFare_Detail.LowFare_Detail_Departing;
                        LowFare_Detail_Time1_Select_Type1.SelectedValue = e_LowFare_Detail.LowFare_Detail_Time1;
                        LowFare_Detail_Returning_Text_Type1.Text = e_LowFare_Detail.LowFare_Detail_Returning;
                        LowFare_Detail_Time2_Select_Type1.Text = e_LowFare_Detail.LowFare_Detail_Time2;
                    }

                    if (LowFare_Type2.Checked)
                    {
                        LowFare_Detail_From_Text_Type2.Text = e_LowFare_Detail.LowFare_Detail_From;
                        LowFare_Detail_To_Text_Type2.Text = e_LowFare_Detail.LowFare_Detail_To;
                        LowFare_Detail_Departing_Text_Type2.Text = e_LowFare_Detail.LowFare_Detail_Departing;
                        LowFare_Detail_Time1_Select_Type2.SelectedValue = e_LowFare_Detail.LowFare_Detail_Time1;                       
                    }

                    if (LowFare_Type3.Checked)
                    {
                        Control o_Control;

                        o_Control = form1.FindControl("LowFare_Detail_From_Text_Type3_" + i.ToString());
                        if (o_Control != null)
                        {
                            TextBox o_TextBox = (TextBox)o_Control;
                            o_TextBox.Text = e_LowFare_Detail.LowFare_Detail_From;
                        }

                        o_Control = form1.FindControl("LowFare_Detail_To_Text_Type3_" + i.ToString());
                        if (o_Control != null)
                        {
                            TextBox o_TextBox = (TextBox)o_Control;
                            o_TextBox.Text = e_LowFare_Detail.LowFare_Detail_To;
                        }

                        o_Control = form1.FindControl("LowFare_Detail_Departing_Text_Type3_" + i.ToString());
                        if (o_Control != null)
                        {
                            TextBox o_TextBox = (TextBox)o_Control;
                            o_TextBox.Text = e_LowFare_Detail.LowFare_Detail_Departing;
                        }

                        o_Control = form1.FindControl("LowFare_Detail_Time1_Select_Type3_" + i.ToString());
                        if (o_Control != null)
                        {
                            DropDownList o_DropDownList = (DropDownList)o_Control;
                            o_DropDownList.SelectedValue = e_LowFare_Detail.LowFare_Detail_Time1;
                        }
                    }

                    i++;
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
            if (g_LowFare_ID == 0)
                ResponseError("参数错误");

            Entity.LowFare e_LowFare = b_LowFare.Select_LowFare(g_LowFare_ID);
            e_LowFare.LowFare_AdminUser_ID = g_AdminUser;
            e_LowFare.LowFare_Status = 1;
            e_LowFare.LowFare_SubmitTime = DateTime.Now.ToString();
            b_LowFare.Update_LowFare(e_LowFare);
            ResponseSuccess("转换成功");
        }

        protected void LowFare_Delete_Click(object sender, EventArgs e)
        {
            if (g_LowFare_ID == 0)
                ResponseError("参数错误");

            b_LowFare.Delete_LowFare(g_LowFare_ID);
            ResponseClose("删除成功");
        }
    }
}
