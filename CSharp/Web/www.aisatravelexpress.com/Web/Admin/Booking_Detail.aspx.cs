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
                        break;

                    case 2:
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
                            e_Booking.Booking_LastTime = DateTime.Now;
                            b_Booking.Update_Booking(e_Booking);
                            ResponseClose("转换成功");
                        }
                        break;
                }
            }
        }
    }
}
