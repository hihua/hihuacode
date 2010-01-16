using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Booking : DALBase
    {
        private string g_TableName = "t_Booking";
        private string g_TableFields = "Booking_ID,Booking_Seq,Booking_Airline,Booking_Contact,Booking_Num,Booking_Tel,Booking_Email,Booking_AdminUser_ID,Booking_Kind,Booking_State,Booking_AddTime,Booking_LastTime,Booking_ComitTime";
        private string g_TableOrderByFields = "Booking_ID";

        public Booking()
        {

        }

        public DataTable Select_Booking(string p_Search_Content, int p_Search_Method, int p_Search_State, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            if (VerifyUtility.IsString_NotNull(p_Search_Content))
            {
                switch (p_Search_Method)
                {
                    case 1:
                        o_Where += " and Booking_Seq Like N'%" + p_Search_Content + "%'";
                        break;

                    case 2:
                        o_Where += " and Booking_Airline Like N'%" + p_Search_Content + "%'";
                        break;

                    case 3:
                        o_Where += " and Booking_Contact Like N'%" + p_Search_Content + "%'";
                        break;

                    case 4:
                        o_Where += " and Booking_Tel Like N'%" + p_Search_Content + "%'";
                        break;

                    case 5:
                        o_Where += " and Booking_Email Like N'%" + p_Search_Content + "%'";
                        break;

                    case 6:
                        o_Where += " and Booking_AdminUser_ID = " + p_Search_Content;
                        break;

                    case 7:
                        o_Where += " and Booking_AdminUser_ID = " + p_Search_Content;
                        break;

                    default:
                        break;
                }
            }

            switch (p_Search_State)
            {               
                case 1:
                    o_Where += " and Booking_State = 0 and Datediff(day, Booking_AddTime, Booking_LastTime) > 0";
                    break;

                case 2:
                    o_Where += " and Booking_State = 0 and Datediff(day, Booking_AddTime, Booking_LastTime) = 0";
                    break;

                case 3:
                    o_Where += " and Booking_State = 1";
                    break;

                case 4:
                    o_Where += " and Booking_State = 0 and Datediff(day, Booking_AddTime, Booking_LastTime) < 0";
                    break;

                default:
                    break;

            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_Booking(int p_Booking_ID, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "Booking_ID=" + p_Booking_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public void Insert_Booking(Entity.Booking p_Booking)
        {
            if (p_Booking == null)
                return;

            g_TableFields = "Booking_Seq,Booking_Airline,Booking_Contact,Booking_Num,Booking_Tel,Booking_Email,Booking_AdminUser_ID,Booking_Kind,Booking_State,Booking_AddTime,Booking_LastTime,Booking_ComitTime";

            string o_FieldsValue = "";
            o_FieldsValue += "N'" + p_Booking.Booking_Seq + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_Airline + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_Contact + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Booking.Booking_Num.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_Email + "'";
            o_FieldsValue += ",";

            if (p_Booking.Booking_AdminUser_ID != null)
                o_FieldsValue += p_Booking.Booking_AdminUser_ID.AdminUser_ID.ToString();
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";

            if (p_Booking.Booking_Kind)
                o_FieldsValue += "1";
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += p_Booking.Booking_State.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_LastTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Booking.Booking_ComitTime + "'";

            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_Booking(Entity.Booking p_Booking)
        {
            if (p_Booking == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Booking_Seq=N'" + p_Booking.Booking_Seq + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_Airline=N'" + p_Booking.Booking_Airline + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_Contact=N'" + p_Booking.Booking_Contact + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_Num=" + p_Booking.Booking_Num.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_Tel=N'" + p_Booking.Booking_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_Email=N'" + p_Booking.Booking_Email + "'";
            o_FieldsValue += ",";
            if (p_Booking.Booking_AdminUser_ID != null)
                o_FieldsValue += "Booking_AdminUser_ID=" + p_Booking.Booking_AdminUser_ID.AdminUser_ID.ToString();
            else
                o_FieldsValue += "Booking_AdminUser_ID=0";

            o_FieldsValue += ",";

            if (p_Booking.Booking_Kind)
                o_FieldsValue += "Booking_Kind=1";
            else
                o_FieldsValue += "Booking_Kind=0";

            o_FieldsValue += ",";
            o_FieldsValue += "Booking_State=" + p_Booking.Booking_State.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_AddTime=N'" + p_Booking.Booking_AddTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_LastTime=N'" + p_Booking.Booking_LastTime.ToString() + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Booking_ComitTime=N'" + p_Booking.Booking_ComitTime + "'";

            string o_Where = "Booking_ID=" + p_Booking.Booking_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_Booking(int p_Booking_ID)
        {
            string o_Where = "Booking_ID=" + p_Booking_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }
    }
}
