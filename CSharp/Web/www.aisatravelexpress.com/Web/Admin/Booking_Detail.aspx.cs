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
    public partial class Booking_Detail : PageBase
    {
        private BLL.Booking b_Booking;

        protected void Page_Load(object sender, EventArgs e)
        {
            b_Booking = new BLL.Booking();
            if (!IsPostBack)
            {
                switch (g_Action_ID)
                {
                    case 1:
                        TD_AdminUser_ID.Visible = false;
                        TD_Booking_State.Visible = false;
                        TD_AddTime.Visible = false;
                        TD_ComitTime.Visible = false;                        
                        break;

                    case 2:
                        {
                            if (g_Booking_ID == 0)
                                ResponseError("参数错误");

                            TD_AdminUser_ID.Visible = true;
                            TD_Booking_State.Visible = true;
                            TD_AddTime.Visible = true;
                            TD_ComitTime.Visible = true;

                            Entity.Booking e_Booking = b_Booking.Select_Booking(g_Booking_ID);
                            Booking_Seq.Text = e_Booking.Booking_Seq;
                            Booking_Airline.Text = e_Booking.Booking_Airline;
                            Booking_Contact.Text = e_Booking.Booking_Contact;
                            Booking_Num.Text = e_Booking.Booking_Num.ToString();
                            Booking_Tel.Text = e_Booking.Booking_Tel;
                            Booking_Email.Text = e_Booking.Booking_Email;
                            GetAdminUser(e_Booking.Booking_AdminUser_ID);
                            if (e_Booking.Booking_Kind)
                                Booking_Kind1.Checked = true;
                            else
                                Booking_Kind2.Checked = true;

                            switch (e_Booking.Booking_State)
                            {
                                case 0:
                                    Booking_State.SelectedIndex = 0;
                                    break;

                                case 1:
                                    Booking_State.SelectedIndex = 1;
                                    break;

                                default:
                                    break;
                            }

                            Booking_AddTime.Text = e_Booking.Booking_AddTime.ToString();
                            Booking_LastTime.Text = e_Booking.Booking_LastTime.ToString();
                            Booking_ComitTime.Text = e_Booking.Booking_ComitTime.ToString();
                            Booking_Submit.Text = " 修改 ";
                        }
                        break;

                    case 3:
                        if (g_Booking_ID == 0)
                            ResponseError("参数错误");

                        b_Booking.Delete_Booking(g_Booking_ID);
                        ResponseClose("删除成功");
                        break;

                    case 4:
                        {
                            if (g_Booking_ID == 0)
                                ResponseError("参数错误");

                            Entity.Booking e_Booking = b_Booking.Select_Booking(g_Booking_ID);
                            e_Booking.Booking_State = 1;
                            e_Booking.Booking_ComitTime = DateTime.Now.ToString();
                            b_Booking.Update_Booking(e_Booking);
                            ResponseClose("转换成功");
                        }
                        break;
                }
            }
        }

        private void GetAdminUser(Entity.AdminUser p_AdminUser)
        {
            Booking_AdminUser_ID.Items.Clear();
            if (p_AdminUser == null)
                return;

            BLL.AdminUser b_AdminUser = new BLL.AdminUser();
            Entity.AdminUser[] e_AdminUser = b_AdminUser.Select_AdminUser();

            if (e_AdminUser != null)
            {
                foreach (Entity.AdminUser o_AdminUser in e_AdminUser)
                {
                    ListItem o_ListItem = new ListItem(o_AdminUser.AdminUser_Name + " " + o_AdminUser.AdminUser_NickName, o_AdminUser.AdminUser_ID.ToString());
                    if (o_AdminUser.AdminUser_ID == p_AdminUser.AdminUser_ID)
                        o_ListItem.Selected = true;

                    Booking_AdminUser_ID.Items.Add(o_ListItem);
                }
            }
        }

        private Entity.AdminUser GetAdminUser(int p_AdminUser_ID)
        {
            if (p_AdminUser_ID <= 0)
                return null;

            BLL.AdminUser b_AdminUser = new BLL.AdminUser();
            Entity.AdminUser e_AdminUser = b_AdminUser.Select_AdminUser(p_AdminUser_ID);
            return e_AdminUser;
        }

        protected void Booking_Submit_Click(object sender, EventArgs e)
        {
            if (!VerifyUtility.IsString_NotNull(Booking_Seq.Text))
                ResponseError("请输入订位代号");

            if (!VerifyUtility.IsString_NotNull(Booking_Airline.Text))
                ResponseError("请输入航空公司");

            if (!VerifyUtility.IsString_NotNull(Booking_Airline.Text))
                ResponseError("请输入联系人");

            if (!VerifyUtility.IsNumber_NotNull(Booking_Num.Text))
                ResponseError("请输入人数");

            if (!VerifyUtility.IsString_NotNull(Booking_Tel.Text))
                ResponseError("请输入电话号码");

            if (!VerifyUtility.Check_Date(Booking_LastTime.Text))
                ResponseError("结束时间, 请输入正确的日期格式如: " + DateTime.Now.ToString("yyyy-MM-dd"));

            Entity.Booking e_Booking = new Entity.Booking();
            if (g_Action_ID == 2)
                e_Booking.Booking_ID = g_Booking_ID;

            e_Booking.Booking_Seq = Booking_Seq.Text;
            e_Booking.Booking_Airline = Booking_Airline.Text;
            e_Booking.Booking_Contact = Booking_Contact.Text;
            e_Booking.Booking_Num = Convert.ToInt32(Booking_Num.Text);
            e_Booking.Booking_Tel = Booking_Tel.Text;
            e_Booking.Booking_Email = Booking_Email.Text;

            if (g_Action_ID == 1)
                e_Booking.Booking_AdminUser_ID = g_AdminUser;

            if (g_Action_ID == 2)
            {
                int AdminUser_ID = Convert.ToInt32(Booking_AdminUser_ID.SelectedValue);
                e_Booking.Booking_AdminUser_ID = GetAdminUser(AdminUser_ID);
            }

            if (Booking_Kind1.Checked)
                e_Booking.Booking_Kind = true;
            else
                e_Booking.Booking_Kind = false;

            if (g_Action_ID == 1)
                e_Booking.Booking_State = 0;

            if (g_Action_ID == 2)
                e_Booking.Booking_State = Convert.ToInt32(Booking_State.SelectedValue);

            if (g_Action_ID == 1)
                e_Booking.Booking_AddTime = DateTime.Now;

            if (g_Action_ID == 2)
                e_Booking.Booking_AddTime = DateTime.Parse(Booking_AddTime.Text);

            e_Booking.Booking_LastTime = DateTime.Parse(Booking_LastTime.Text);

            if (g_Action_ID == 2 && Booking_State.SelectedValue == "1")
                e_Booking.Booking_ComitTime = DateTime.Now.ToString();

            if (g_Action_ID == 1)
            {
                b_Booking.Insert_Booking(e_Booking);
                g_TipsTable.Visible = true;
                g_MainTable.Visible = false;
                TipsMessage.Text = "添加成功";
                TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Booking_ID=0";
                TipsLink1.Text = "继续添加";
            }

            if (g_Action_ID == 2)
            {
                b_Booking.Update_Booking(e_Booking);
                g_TipsTable.Visible = true;
                g_MainTable.Visible = false;
                TipsMessage.Text = "修改成功";
                TipsLink1.NavigateUrl = "?Action_ID=" + g_Action_ID.ToString() + "&Booking_ID=" + g_Booking_ID.ToString();
                TipsLink1.Text = "继续修改";
            }
        }
    }
}
