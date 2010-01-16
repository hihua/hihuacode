using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Booking
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.Booking d_Booking;

        public Booking()
        {
            d_Booking = new DAL.Booking();
        }

        private void DateRow_Booking(DataRow p_DataRow, Entity.Booking p_Booking)
        {
            if (p_DataRow == null || p_Booking == null)
                return;

            p_Booking.Booking_ID = Convert.ToInt32(p_DataRow["Booking_ID"].ToString());
            p_Booking.Booking_Seq = p_DataRow["Booking_Seq"].ToString();
            p_Booking.Booking_Airline = p_DataRow["Booking_Airline"].ToString();
            p_Booking.Booking_Contact = p_DataRow["Booking_Contact"].ToString();
            p_Booking.Booking_Num = Convert.ToInt32(p_DataRow["Booking_Num"].ToString());
            p_Booking.Booking_Tel = p_DataRow["Booking_Tel"].ToString();
            p_Booking.Booking_Email = p_DataRow["Booking_Email"].ToString();
           
            BLL.AdminUser b_AdminUser = new AdminUser();
            p_Booking.Booking_AdminUser_ID = b_AdminUser.Select_AdminUser(Convert.ToInt32(p_DataRow["Booking_AdminUser_ID"].ToString()));

            if (p_DataRow["Booking_Kind"].ToString().ToLower() == "true")
                p_Booking.Booking_Kind = true;
            else
                p_Booking.Booking_Kind = false;

            p_Booking.Booking_State = Convert.ToInt32(p_DataRow["Booking_State"].ToString());
            p_Booking.Booking_AddTime = DateTime.Parse(p_DataRow["Booking_AddTime"].ToString());
            p_Booking.Booking_LastTime = DateTime.Parse(p_DataRow["Booking_LastTime"].ToString());
            if (p_DataRow["Booking_LastTime"] != null && VerifyUtility.IsString_NotNull(p_DataRow["Booking_LastTime"].ToString()))
                p_Booking.Booking_ComitTime = p_DataRow["Booking_ComitTime"].ToString();
        }

        public Entity.Booking[] Select_Booking(string p_Search_Content, int p_Search_Method, int p_Search_State, int p_PageSize, int p_PageIndex)
        {
            switch (p_Search_Method)
            {
                case 6:
                    {
                        BLL.AdminUser b_AdminUser = new AdminUser();
                        Entity.AdminUser o_AdminUser = b_AdminUser.Select_AdminUser(1, p_Search_Content);
                        if (o_AdminUser != null)
                        {
                            p_Search_Content = o_AdminUser.AdminUser_ID.ToString();
                            p_Search_Method = 6;
                        }
                        else
                            return null;
                    }
                    break;

                case 7:
                    {
                        BLL.AdminUser b_AdminUser = new AdminUser();
                        Entity.AdminUser o_AdminUser = b_AdminUser.Select_AdminUser(2, p_Search_Content);
                        if (o_AdminUser != null)
                        {
                            p_Search_Content = o_AdminUser.AdminUser_ID.ToString();
                            p_Search_Method = 7;
                        }
                        else
                            return null;
                    }
                    break;

                default:
                    break;
            }

            DataTable o_DataTable = d_Booking.Select_Booking(p_Search_Content, p_Search_Method, p_Search_State, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Booking[] e_Booking = new Entity.Booking[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Booking[i] = new Entity.Booking();
                    DateRow_Booking(o_DataRow, e_Booking[i]);

                    i++;
                }

                return e_Booking;
            }
        }

        public Entity.Booking Select_Booking(int p_Booking_ID)
        {
            DataTable o_DataTable = d_Booking.Select_Booking(p_Booking_ID, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];
                Entity.Booking e_Booking = new Entity.Booking();
                DateRow_Booking(o_DataRow, e_Booking);

                return e_Booking;
            }
        }

        public void Insert_Booking(Entity.Booking o_Booking)
        {
            if (o_Booking == null)
                return;

            Entity.Booking e_Booking = new Entity.Booking();
            e_Booking.Booking_ID = o_Booking.Booking_ID;
            e_Booking.Booking_Seq = FilterUtility.FilterSQL(o_Booking.Booking_Seq);
            e_Booking.Booking_Airline = FilterUtility.FilterSQL(o_Booking.Booking_Airline);
            e_Booking.Booking_Contact = FilterUtility.FilterSQL(o_Booking.Booking_Contact);
            e_Booking.Booking_Num = o_Booking.Booking_Num;
            e_Booking.Booking_Tel = FilterUtility.FilterSQL(o_Booking.Booking_Tel);
            e_Booking.Booking_Email = FilterUtility.FilterSQL(o_Booking.Booking_Email);
            e_Booking.Booking_AdminUser_ID = o_Booking.Booking_AdminUser_ID;
            e_Booking.Booking_Kind = o_Booking.Booking_Kind;
            e_Booking.Booking_State = o_Booking.Booking_State;
            e_Booking.Booking_AddTime = o_Booking.Booking_AddTime;
            e_Booking.Booking_LastTime = o_Booking.Booking_LastTime;

            d_Booking.Insert_Booking(e_Booking);
        }

        public void Update_Booking(Entity.Booking o_Booking)
        {
            if (o_Booking == null)
                return;

            Entity.Booking e_Booking = new Entity.Booking();
            e_Booking.Booking_ID = o_Booking.Booking_ID;
            e_Booking.Booking_Seq = FilterUtility.FilterSQL(o_Booking.Booking_Seq);
            e_Booking.Booking_Airline = FilterUtility.FilterSQL(o_Booking.Booking_Airline);
            e_Booking.Booking_Contact = FilterUtility.FilterSQL(o_Booking.Booking_Contact);
            e_Booking.Booking_Num = o_Booking.Booking_Num;
            e_Booking.Booking_Tel = FilterUtility.FilterSQL(o_Booking.Booking_Tel);
            e_Booking.Booking_Email = FilterUtility.FilterSQL(o_Booking.Booking_Email);
            e_Booking.Booking_AdminUser_ID = o_Booking.Booking_AdminUser_ID;
            e_Booking.Booking_Kind = o_Booking.Booking_Kind;
            e_Booking.Booking_State = o_Booking.Booking_State;
            e_Booking.Booking_AddTime = o_Booking.Booking_AddTime;
            e_Booking.Booking_LastTime = o_Booking.Booking_LastTime;
            e_Booking.Booking_ComitTime = o_Booking.Booking_ComitTime;

            d_Booking.Update_Booking(e_Booking);
        }

        public void Delete_Booking(int p_Booking_ID)
        {
            if (p_Booking_ID <= 0)
                return;

            d_Booking.Delete_Booking(p_Booking_ID);
        }
    }
}
