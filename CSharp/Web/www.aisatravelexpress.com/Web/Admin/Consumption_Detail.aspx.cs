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
    public partial class Consumption_Detail : PageBase
    {
        private BLL.Consumption b_Consumption;

        protected void Page_Load(object sender, EventArgs e)
        {
            b_Consumption = new BLL.Consumption();

            if (!IsPostBack)
            {
                switch (g_Action_ID)
                {
                    case 1:
                        GetMember();
                        Consumption_Com_Member_TD.Visible = false;
                        Consumption_Admin_Name_TD.Visible = false;
                        Consumption_Admin_NickName_TD.Visible = false;
                        Consumption_AddTime_TD.Visible = false;
                        Consumption_Submit.Text = " 添加 ";
                        break;

                    case 2:
                        if (g_Consumption_ID == 0)
                            ResponseError("参数错误");

                        GetMember();
                        Entity.Consumption e_Consumption = b_Consumption.Select_Consumption(g_Consumption_ID);
                        if (e_Consumption != null)
                        {
                            Consumption_Serial.Text = e_Consumption.Consumption_Serial;
                            if (e_Consumption.Consumption_Serial.Substring(e_Consumption.Consumption_Serial.Length - 2, 2).ToUpper() == "SB")
                                Consumption_Serial_RadioButton1.Checked = true;
                            else
                                Consumption_Serial_RadioButton2.Checked = true;

                            switch (e_Consumption.Consumption_Type)
                            {
                                case 1:
                                    Consumption_Type1.Checked = true;
                                    break;

                                case 2:
                                    Consumption_Type2.Checked = true;
                                    break;

                                case 3:
                                    Consumption_Type3.Checked = true;
                                    break;

                                default:
                                    break;
                            }

                            Consumption_Src.Text = e_Consumption.Consumption_Src;
                            Consumption_Dest.Text = e_Consumption.Consumption_Dest;
                            Consumption_Price.Text = e_Consumption.Consumption_Price.ToString();
                            Consumption_DePrice.Text = e_Consumption.Consumption_DePrice.ToString();
                            Consumption_Profit.Text = (e_Consumption.Consumption_Price - e_Consumption.Consumption_DePrice).ToString();
                            Consumption_Points.Text = e_Consumption.Consumption_Points.ToString();
                            Consumption_Commission.Text = e_Consumption.Consumption_Commission.ToString();
                            Consumption_Date.Text = e_Consumption.Consumption_Date.ToString("yyyy-MM-dd");

                            if (e_Consumption.Consumption_Org_Member_ID != null)
                            {
                                Consumption_Org_Member_ID.SelectedValue = e_Consumption.Consumption_Org_Member_ID.Member_ID.ToString();
                                Consumption_Org_Member_Account.Text = e_Consumption.Consumption_Org_Member_ID.Member_Account;
                                Consumption_Org_Member_Account.OnClientClick = "ActionSubmit(2, " + e_Consumption.Consumption_Org_Member_ID.Member_ID.ToString() + ");return false;";
                                Consumption_Org_Member_Serial.Text = e_Consumption.Consumption_Org_Member_ID.Member_Serial;
                            }
                            else
                            {
                                Consumption_Org_Member_Account.Visible = false;
                                Consumption_Org_Member_Serial.Visible = false;
                            }

                            if (e_Consumption.Consumption_Com_Member_ID != null)
                            {
                                Consumption_Com_Member_Account.Text = e_Consumption.Consumption_Com_Member_ID.Member_Account;
                                Consumption_Com_Member_Account.OnClientClick = "ActionSubmit(2, " + e_Consumption.Consumption_Com_Member_ID.Member_ID.ToString() + ");return false;";
                                Consumption_Com_Member_Serial.Text = e_Consumption.Consumption_Com_Member_ID.Member_Serial;
                            }
                            else
                            {
                                Consumption_Com_Member_Account.Visible = false;
                                Consumption_Com_Member_Serial.Visible = false;
                            }

                            if (e_Consumption.Consumption_Admin_ID != null)
                            {
                                Consumption_Admin_Name.Text = e_Consumption.Consumption_Admin_ID.AdminUser_Name;
                                Consumption_Admin_NickName.Text = e_Consumption.Consumption_Admin_ID.AdminUser_NickName;
                            }

                            Consumption_Remark.Text = e_Consumption.Consumption_Remark;
                            Consumption_AddTime.Text = e_Consumption.Consumption_AddTime.ToString();
                        }

                        Consumption_Submit.Text = " 修改 ";
                        Consumption_Submit.Visible = false;
                        break;

                    case 3:
                        if (g_Consumption_ID == 0)
                            ResponseError("参数错误");

                        b_Consumption.Delete_Consumption(g_Consumption_ID);
                        ResponseClose("删除成功");
                        break;
                }
            }
        }

        private void GetMember()
        {
            Consumption_Org_Member_ID.Items.Clear();

            BLL.Member b_Member = new BLL.Member();
            Entity.Member[] e_Member = b_Member.Select_Member("", 0, 0x7FFFFFFF, 1);

            if (e_Member != null)
            {
                foreach (Entity.Member o_Member in e_Member)
                {
                    ListItem o_ListItem = new ListItem(o_Member.Member_Serial + " " + o_Member.Member_Account, o_Member.Member_ID.ToString());
                    Consumption_Org_Member_ID.Items.Add(o_ListItem);
                }
            }
        }

        protected void Consumption_Submit_Click(object sender, EventArgs e)
        {
            if (!Consumption_Serial_RadioButton1.Checked && !Consumption_Serial_RadioButton2.Checked)
                ResponseError("请选择定位代号");

            if (!Consumption_Type1.Checked && !Consumption_Type2.Checked && !Consumption_Type3.Checked)
                ResponseError("请选择类型");

            if (!VerifyUtility.IsString_NotNull(Consumption_Src.Text))
                ResponseError("请输入出发地");

            if (!VerifyUtility.IsString_NotNull(Consumption_Dest.Text))
                ResponseError("请输入目的地");

            if (!VerifyUtility.IsNumber_NotNull(Consumption_Price.Text))
                ResponseError("请输入票价");

            if (!VerifyUtility.IsNumber_NotNull(Consumption_DePrice.Text))
                ResponseError("请输入低价");

            if (!VerifyUtility.IsNumber_NotNull(Consumption_Points.Text))
                ResponseError("请输入积分");

            if (!VerifyUtility.IsNumber_NotNull(Consumption_Commission.Text))
                ResponseError("请输入佣金");

            if (!VerifyUtility.IsString_NotNull(Consumption_Date.Text))
                ResponseError("请输入出发日期");

            if (!VerifyUtility.Check_Date(Consumption_Date.Text))
                ResponseError("出发日期, 请输入正确的日期格式如: " + DateTime.Now.ToString("yyyy-MM-dd"));

            if (!VerifyUtility.IsString_NotNull(Consumption_Org_Member_ID.SelectedValue))
                ResponseError("请选择会员");

            Entity.Consumption e_Consumption = new Entity.Consumption();
            string Consumption_Serial_RadioButton = "";
            if (Consumption_Serial_RadioButton1.Checked)
                Consumption_Serial_RadioButton += Consumption_Serial_RadioButton1.Text;
            else
                Consumption_Serial_RadioButton += Consumption_Serial_RadioButton2.Text;

            e_Consumption.Consumption_Serial = Consumption_Serial.Text + Consumption_Serial_RadioButton;

            if (Consumption_Type1.Checked)
                e_Consumption.Consumption_Type = 1;

            if (Consumption_Type2.Checked)
                e_Consumption.Consumption_Type = 2;

            if (Consumption_Type3.Checked)
                e_Consumption.Consumption_Type = 3;

            e_Consumption.Consumption_Src = Consumption_Src.Text;
            e_Consumption.Consumption_Dest = Consumption_Dest.Text;
            e_Consumption.Consumption_Price = Convert.ToInt32(Consumption_Price.Text);
            e_Consumption.Consumption_DePrice = Convert.ToInt32(Consumption_DePrice.Text);
            e_Consumption.Consumption_Points = Convert.ToInt32(Consumption_Points.Text);
            e_Consumption.Consumption_Commission = Convert.ToInt32(Consumption_Commission.Text);
            e_Consumption.Consumption_Date = DateTime.Parse(Consumption_Date.Text);

            BLL.Member b_Member = new BLL.Member();
            e_Consumption.Consumption_Org_Member_ID = b_Member.Select_Member(Convert.ToInt32(Consumption_Org_Member_ID.SelectedValue));
            if (e_Consumption.Consumption_Org_Member_ID != null)
            {
                e_Consumption.Consumption_Org_Member_ID.Member_Points += e_Consumption.Consumption_Points;
                e_Consumption.Consumption_Org_Member_ID.Member_Consumption += e_Consumption.Consumption_Price;

                if (e_Consumption.Consumption_Org_Member_ID.Member_Level == 1)
                    e_Consumption.Consumption_Org_Member_ID.Member_Level = 2;
            }
            else
                ResponseError("没有该会员");

            if (e_Consumption.Consumption_Org_Member_ID.Member_Recommended > 0)
            {
                e_Consumption.Consumption_Com_Member_ID = b_Member.Select_Member(e_Consumption.Consumption_Org_Member_ID.Member_Recommended);
                if (e_Consumption.Consumption_Com_Member_ID != null)
                {
                    e_Consumption.Consumption_Com_Member_ID.Member_Commission += e_Consumption.Consumption_Commission;
                    e_Consumption.Consumption_Com_Member_ID.Member_Times++;
                }
            }

            e_Consumption.Consumption_Admin_ID = g_AdminUser;
            e_Consumption.Consumption_AddTime = DateTime.Now;
            e_Consumption.Consumption_Remark = Consumption_Remark.Text;

            b_Consumption.Insert_Consumption(e_Consumption);

            b_Member.Update_Member(e_Consumption.Consumption_Org_Member_ID);

            if (e_Consumption.Consumption_Com_Member_ID != null)
                b_Member.Update_Member(e_Consumption.Consumption_Com_Member_ID);

            ResponseSuccess("提交成功", "Consumption_Detail.aspx?Action_ID=1");
        }
    }
}
