using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Consumption
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.Consumption d_Consumption;

        public Consumption()
        {
            d_Consumption = new DAL.Consumption();
        }

        private void DateRow_Consumption(DataRow p_DataRow, Entity.Consumption p_Consumption)
        {
            if (p_DataRow == null || p_Consumption == null)
                return;

            p_Consumption.Consumption_ID = Convert.ToInt32(p_DataRow["Consumption_ID"].ToString());
            p_Consumption.Consumption_Serial = p_DataRow["Consumption_Serial"].ToString();
            p_Consumption.Consumption_Type = Convert.ToInt32(p_DataRow["Consumption_Type"].ToString());
            p_Consumption.Consumption_Src = p_DataRow["Consumption_Src"].ToString();
            p_Consumption.Consumption_Dest = p_DataRow["Consumption_Dest"].ToString();
            p_Consumption.Consumption_Price = Convert.ToInt32(p_DataRow["Consumption_Price"].ToString());
            p_Consumption.Consumption_DePrice = Convert.ToInt32(p_DataRow["Consumption_DePrice"].ToString());
            p_Consumption.Consumption_Points = Convert.ToInt32(p_DataRow["Consumption_Points"].ToString());
            p_Consumption.Consumption_Commission = Convert.ToInt32(p_DataRow["Consumption_Commission"].ToString());
            p_Consumption.Consumption_Date = DateTime.Parse(p_DataRow["Consumption_Date"].ToString());

            BLL.Member b_Member = new Member();
            p_Consumption.Consumption_Org_Member_ID = b_Member.Select_Member(Convert.ToInt32(p_DataRow["Consumption_Org_Member_ID"].ToString()));
            p_Consumption.Consumption_Com_Member_ID = b_Member.Select_Member(Convert.ToInt32(p_DataRow["Consumption_Com_Member_ID"].ToString()));

            BLL.AdminUser b_AdminUser = new AdminUser();
            p_Consumption.Consumption_Admin_ID = b_AdminUser.Select_AdminUser(Convert.ToInt32(p_DataRow["Consumption_Admin_ID"].ToString()));

            p_Consumption.Consumption_AddTime = DateTime.Parse(p_DataRow["Consumption_AddTime"].ToString());
            p_Consumption.Consumption_Remark = p_DataRow["Consumption_Remark"].ToString();            
        }

        public Entity.Consumption[] Select_Consumption(string p_Search_Content, int p_Search_Method, int p_Search_Year, int p_Search_Month, int p_PageSize, int p_PageIndex)
        {
            switch (p_Search_Method)
            {
                case 9:
                    {
                        BLL.Member b_Member = new Member();
                        Entity.Member o_Member = b_Member.Select_Member(1, p_Search_Content);
                        if (o_Member != null)
                        {
                            p_Search_Content = o_Member.Member_ID.ToString();
                            p_Search_Method = 9;
                        }
                        else
                            return null;
                    }
                    break;

                case 10:
                    {
                        BLL.Member b_Member = new Member();
                        Entity.Member o_Member = b_Member.Select_Member(2, p_Search_Content);
                        if (o_Member != null)
                        {
                            p_Search_Content = o_Member.Member_ID.ToString();
                            p_Search_Method = 9;
                        }
                        else
                            return null;
                    }
                    break;

                case 11:
                    {
                        BLL.Member b_Member = new Member();
                        Entity.Member o_Member = b_Member.Select_Member(1, p_Search_Content);
                        if (o_Member != null)
                        {
                            p_Search_Content = o_Member.Member_ID.ToString();
                            p_Search_Method = 10;
                        }
                        else
                            return null;
                    }
                    break;

                case 12:
                    {
                        BLL.Member b_Member = new Member();
                        Entity.Member o_Member = b_Member.Select_Member(2, p_Search_Content);
                        if (o_Member != null)
                        {
                            p_Search_Content = o_Member.Member_ID.ToString();
                            p_Search_Method = 10;
                        }
                        else
                            return null;
                    }
                    break;

                case 13:
                    {
                        BLL.AdminUser b_AdminUser = new AdminUser();
                        Entity.AdminUser o_AdminUser = b_AdminUser.Select_AdminUser(1, p_Search_Content);
                        if (o_AdminUser != null)
                        {
                            p_Search_Content = o_AdminUser.AdminUser_ID.ToString();
                            p_Search_Method = 11;
                        }
                        else
                            return null;
                    }
                    break;

                case 14:
                    {
                        BLL.AdminUser b_AdminUser = new AdminUser();
                        Entity.AdminUser o_AdminUser = b_AdminUser.Select_AdminUser(2, p_Search_Content);
                        if (o_AdminUser != null)
                        {
                            p_Search_Content = o_AdminUser.AdminUser_ID.ToString();
                            p_Search_Method = 11;
                        }
                        else
                            return null;
                    }
                    break;

                default:
                    break;
            }

            DataTable o_DataTable = d_Consumption.Select_Consumption(p_Search_Content, p_Search_Method, p_Search_Year, p_Search_Month, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Consumption[] e_Consumption = new Entity.Consumption[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Consumption[i] = new Entity.Consumption();
                    DateRow_Consumption(o_DataRow, e_Consumption[i]);

                    i++;
                }

                return e_Consumption;
            }
        }

        public Entity.Consumption Select_Consumption(int p_Consumption_ID)
        {
            DataTable o_DataTable = d_Consumption.Select_Consumption(p_Consumption_ID, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];
                Entity.Consumption e_Consumption = new Entity.Consumption();
                DateRow_Consumption(o_DataRow, e_Consumption);

                return e_Consumption;
            }
        }

        public void Insert_Consumption(Entity.Consumption o_Consumption)
        {
            if (o_Consumption == null)
                return;

            Entity.Consumption e_Consumption = new Entity.Consumption();
            e_Consumption.Consumption_ID = o_Consumption.Consumption_ID;
            e_Consumption.Consumption_Serial = FilterUtility.FilterSQL(o_Consumption.Consumption_Serial);
            e_Consumption.Consumption_Type = o_Consumption.Consumption_Type;
            e_Consumption.Consumption_Src = FilterUtility.FilterSQL(o_Consumption.Consumption_Src);
            e_Consumption.Consumption_Dest = FilterUtility.FilterSQL(o_Consumption.Consumption_Dest);
            e_Consumption.Consumption_Price = o_Consumption.Consumption_Price;
            e_Consumption.Consumption_DePrice = o_Consumption.Consumption_DePrice;
            e_Consumption.Consumption_Points = o_Consumption.Consumption_Points;
            e_Consumption.Consumption_Commission = o_Consumption.Consumption_Commission;
            e_Consumption.Consumption_Date = o_Consumption.Consumption_Date;
            e_Consumption.Consumption_Org_Member_ID = o_Consumption.Consumption_Org_Member_ID;
            e_Consumption.Consumption_Com_Member_ID = o_Consumption.Consumption_Com_Member_ID;
            e_Consumption.Consumption_Admin_ID = o_Consumption.Consumption_Admin_ID;
            e_Consumption.Consumption_AddTime = o_Consumption.Consumption_AddTime;
            e_Consumption.Consumption_Remark = FilterUtility.FilterSQL(o_Consumption.Consumption_Remark);

            d_Consumption.Insert_Consumption(e_Consumption);
        }

        public void Update_Consumption(Entity.Consumption o_Consumption)
        {
            if (o_Consumption == null)
                return;

            Entity.Consumption e_Consumption = new Entity.Consumption();
            e_Consumption.Consumption_ID = o_Consumption.Consumption_ID;
            e_Consumption.Consumption_Serial = FilterUtility.FilterSQL(o_Consumption.Consumption_Serial);
            e_Consumption.Consumption_Type = o_Consumption.Consumption_Type;
            e_Consumption.Consumption_Src = FilterUtility.FilterSQL(o_Consumption.Consumption_Src);
            e_Consumption.Consumption_Dest = FilterUtility.FilterSQL(o_Consumption.Consumption_Dest);
            e_Consumption.Consumption_Price = o_Consumption.Consumption_Price;
            e_Consumption.Consumption_DePrice = o_Consumption.Consumption_DePrice;
            e_Consumption.Consumption_Points = o_Consumption.Consumption_Points;
            e_Consumption.Consumption_Commission = o_Consumption.Consumption_Commission;
            e_Consumption.Consumption_Date = o_Consumption.Consumption_Date;
            e_Consumption.Consumption_Org_Member_ID = o_Consumption.Consumption_Org_Member_ID;
            e_Consumption.Consumption_Com_Member_ID = o_Consumption.Consumption_Com_Member_ID;
            e_Consumption.Consumption_Admin_ID = o_Consumption.Consumption_Admin_ID;
            e_Consumption.Consumption_AddTime = o_Consumption.Consumption_AddTime;
            e_Consumption.Consumption_Remark = FilterUtility.FilterSQL(o_Consumption.Consumption_Remark);

            d_Consumption.Update_Consumption(e_Consumption);
        }

        public void Delete_Consumption(int p_Consumption_ID)
        {
            if (p_Consumption_ID <= 0)
                return;

            d_Consumption.Delete_Consumption(p_Consumption_ID);
        }
    }
}
