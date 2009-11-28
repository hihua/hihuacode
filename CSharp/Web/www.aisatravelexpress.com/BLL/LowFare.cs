using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class LowFare
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.LowFare d_LowFare;

        public LowFare()
        {
            d_LowFare = new DAL.LowFare();
        }

        private void DateRow_LowFare(DataRow p_DataRow, Entity.LowFare p_LowFare)
        {
            if (p_DataRow == null || p_LowFare == null)
                return;

            p_LowFare.LowFare_ID = Convert.ToInt32(p_DataRow["LowFare_ID"].ToString());
            p_LowFare.LowFare_Type = Convert.ToInt32(p_DataRow["LowFare_Type"].ToString());

            if (p_DataRow["LowFare_Type"].ToString().ToUpper() == "TRUE")
                p_LowFare.LowFare_Flexibility = true;
            else
                p_LowFare.LowFare_Flexibility = false;

            BLL.LowFare_Detail b_LowFare_Detail = new LowFare_Detail();
            Entity.LowFare_Detail[] e_LowFare_Detail = b_LowFare_Detail.Select_LowFare_Detail_LowFare_ID(Convert.ToInt32(p_DataRow["LowFare_Detail_ID"].ToString()), 1, 1);
            if (e_LowFare_Detail != null)
            {
                p_LowFare.LowFare_Detail_ID = new List<Entity.LowFare_Detail>();
                foreach (Entity.LowFare_Detail o_LowFare_Detail in e_LowFare_Detail)                
                    p_LowFare.LowFare_Detail_ID.Add(o_LowFare_Detail);                
            }

            p_LowFare.LowFare_Adults = Convert.ToInt32(p_DataRow["LowFare_Adults"].ToString());
            p_LowFare.LowFare_Children = Convert.ToInt32(p_DataRow["LowFare_Children"].ToString());
            p_LowFare.LowFare_Infants = Convert.ToInt32(p_DataRow["LowFare_Infants"].ToString());
            p_LowFare.LowFare_Airline = p_DataRow["LowFare_Airline"].ToString();
            p_LowFare.LowFare_Class = p_DataRow["LowFare_Class"].ToString();

            BLL.Member b_Member = new Member();
            p_LowFare.LowFare_Member_ID = b_Member.Select_Member(Convert.ToInt32(p_DataRow["LowFare_Member_ID"].ToString()));
                        
            BLL.AdminUser b_AdminUser = new AdminUser();
            p_LowFare.LowFare_AdminUser_ID = b_AdminUser.Select_AdminUser(Convert.ToInt32(p_DataRow["LowFare_AdminUser_ID"].ToString()));
            
            p_LowFare.LowFare_Status = Convert.ToInt32(p_DataRow["LowFare_Status"].ToString());
            p_LowFare.LowFare_AddTime = DateTime.Parse(p_DataRow["LowFare_AddTime"].ToString());
        }

        public Entity.LowFare[] Select_LowFare(string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            DataTable o_DataTable = d_LowFare.Select_LowFare(p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.LowFare[] e_LowFare = new Entity.LowFare[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_LowFare[i] = new Entity.LowFare();
                    DateRow_LowFare(o_DataRow, e_LowFare[i]);

                    i++;
                }

                return e_LowFare;
            }
        }

        public Entity.LowFare Select_LowFare(int p_LowFare_ID)
        {
            DataTable o_DataTable = d_LowFare.Select_LowFare(p_LowFare_ID, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];
                Entity.LowFare e_LowFare = new Entity.LowFare();
                DateRow_LowFare(o_DataRow, e_LowFare);

                return e_LowFare;
            }
        }

        public void Insert_LowFare(Entity.LowFare o_LowFare)
        {
            if (o_LowFare == null)
                return;

            Entity.LowFare e_LowFare = new Entity.LowFare();
            e_LowFare.LowFare_ID = o_LowFare.LowFare_ID;
            e_LowFare.LowFare_Type = o_LowFare.LowFare_Type;
            e_LowFare.LowFare_Flexibility = o_LowFare.LowFare_Flexibility;
            e_LowFare.LowFare_Detail_ID = o_LowFare.LowFare_Detail_ID;
            e_LowFare.LowFare_Adults = o_LowFare.LowFare_Adults;
            e_LowFare.LowFare_Children = o_LowFare.LowFare_Children;
            e_LowFare.LowFare_Infants = o_LowFare.LowFare_Infants;
            e_LowFare.LowFare_Airline = FilterUtility.FilterSQL(o_LowFare.LowFare_Airline);
            e_LowFare.LowFare_Class = FilterUtility.FilterSQL(o_LowFare.LowFare_Class);
            e_LowFare.LowFare_Member_ID = o_LowFare.LowFare_Member_ID;
            e_LowFare.LowFare_AdminUser_ID = o_LowFare.LowFare_AdminUser_ID;
            e_LowFare.LowFare_Status = o_LowFare.LowFare_Status;
            e_LowFare.LowFare_AddTime = o_LowFare.LowFare_AddTime;

            BLL.LowFare_Detail b_LowFare_Detail = new LowFare_Detail();
            b_LowFare_Detail.Insert_LowFare_Detail(e_LowFare.LowFare_Detail_ID);

            d_LowFare.Insert_LowFare(e_LowFare);
        }

        public void Update_LowFare(Entity.LowFare o_LowFare)
        {
            if (o_LowFare == null)
                return;

            Entity.LowFare e_LowFare = new Entity.LowFare();
            e_LowFare.LowFare_ID = o_LowFare.LowFare_ID;
            e_LowFare.LowFare_Type = o_LowFare.LowFare_Type;
            e_LowFare.LowFare_Flexibility = o_LowFare.LowFare_Flexibility;
            e_LowFare.LowFare_Detail_ID = o_LowFare.LowFare_Detail_ID;
            e_LowFare.LowFare_Adults = o_LowFare.LowFare_Adults;
            e_LowFare.LowFare_Children = o_LowFare.LowFare_Children;
            e_LowFare.LowFare_Infants = o_LowFare.LowFare_Infants;
            e_LowFare.LowFare_Airline = FilterUtility.FilterSQL(o_LowFare.LowFare_Airline);
            e_LowFare.LowFare_Class = FilterUtility.FilterSQL(o_LowFare.LowFare_Class);
            e_LowFare.LowFare_Member_ID = o_LowFare.LowFare_Member_ID;
            e_LowFare.LowFare_AdminUser_ID = o_LowFare.LowFare_AdminUser_ID;
            e_LowFare.LowFare_Status = o_LowFare.LowFare_Status;
            e_LowFare.LowFare_AddTime = o_LowFare.LowFare_AddTime;

            BLL.LowFare_Detail b_LowFare_Detail = new LowFare_Detail();
            b_LowFare_Detail.Update_LowFare_Detail(e_LowFare.LowFare_Detail_ID);

            d_LowFare.Update_LowFare(e_LowFare);
        }

        public void Delete_LowFare(int LowFare_ID)
        {
            if (LowFare_ID <= 0)
                return;

            Entity.LowFare o_LowFare = Select_LowFare(LowFare_ID);
            if (o_LowFare == null)
                return;

            d_LowFare.Delete_LowFare(o_LowFare);
        }
    }
}
