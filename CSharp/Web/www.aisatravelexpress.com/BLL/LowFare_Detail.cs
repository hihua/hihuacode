using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class LowFare_Detail
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.LowFare_Detail d_LowFare_Detail;

        public LowFare_Detail()
        {
            d_LowFare_Detail = new DAL.LowFare_Detail();
        }

        private void DateRow_LowFare_Detail(DataRow p_DataRow, Entity.LowFare_Detail p_LowFare_Detail)
        {
            if (p_DataRow == null || p_LowFare_Detail == null)
                return;

            p_LowFare_Detail.LowFare_Detail_ID = Convert.ToInt32(p_DataRow["LowFare_Detail_ID"].ToString());
            p_LowFare_Detail.LowFare_Detail_LowFare_ID = Convert.ToInt32(p_DataRow["LowFare_Detail_LowFare_ID"].ToString());
            p_LowFare_Detail.LowFare_Detail_From = p_DataRow["LowFare_Detail_From"].ToString();
            p_LowFare_Detail.LowFare_Detail_To = p_DataRow["LowFare_Detail_To"].ToString();

            if (p_DataRow["LowFare_Detail_Departing"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Detail_Departing"].ToString()))
                p_LowFare_Detail.LowFare_Detail_Departing = p_DataRow["LowFare_Detail_Departing"].ToString();
            
            if (p_DataRow["LowFare_Detail_Time1"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Detail_Time1"].ToString()))
                p_LowFare_Detail.LowFare_Detail_Time1 = p_DataRow["LowFare_Detail_Time1"].ToString();
            else
                p_LowFare_Detail.LowFare_Detail_Time1 = "";

            if (p_DataRow["LowFare_Flexibility1"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Flexibility1"].ToString()))
                p_LowFare_Detail.LowFare_Flexibility1 = p_DataRow["LowFare_Flexibility1"].ToString();
            else
                p_LowFare_Detail.LowFare_Flexibility1 = "";

            if (p_DataRow["LowFare_Detail_Returning"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Detail_Returning"].ToString()))
                p_LowFare_Detail.LowFare_Detail_Returning = p_DataRow["LowFare_Detail_Returning"].ToString();
            
            if (p_DataRow["LowFare_Detail_Time2"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Detail_Time2"].ToString()))
                p_LowFare_Detail.LowFare_Detail_Time2 = p_DataRow["LowFare_Detail_Time2"].ToString();
            else
                p_LowFare_Detail.LowFare_Detail_Time2 = "";

            if (p_DataRow["LowFare_Flexibility2"] != null && VerifyUtility.IsString_NotNull(p_DataRow["LowFare_Flexibility2"].ToString()))
                p_LowFare_Detail.LowFare_Flexibility2 = p_DataRow["LowFare_Flexibility2"].ToString();
            else
                p_LowFare_Detail.LowFare_Flexibility2 = "";
        }
               
        public int LowFare_Detail_Next_LowFare_ID()
        {
            DataTable o_DataTable = d_LowFare_Detail.Select_LowFare_Detail(1, 1, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return 1;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                DateRow_LowFare_Detail(o_DataRow, e_LowFare_Detail);

                return e_LowFare_Detail.LowFare_Detail_LowFare_ID + 1;
            }
        }

        public Entity.LowFare_Detail Select_LowFare_Detail(int p_LowFare_Detail_ID, int p_PageSize, int p_PageIndex)
        {
            DataTable o_DataTable = d_LowFare_Detail.Select_LowFare_Detail(p_LowFare_Detail_ID, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.LowFare_Detail e_LowFare_Detail = new Entity.LowFare_Detail();
                DateRow_LowFare_Detail(o_DataRow, e_LowFare_Detail);

                return e_LowFare_Detail;
            }
        }

        public Entity.LowFare_Detail[] Select_LowFare_Detail_LowFare_ID(int p_LowFare_Detail_LowFare_ID, int p_PageSize, int p_PageIndex)
        {
            DataTable o_DataTable = d_LowFare_Detail.Select_LowFare_Detail_LowFare_ID(p_LowFare_Detail_LowFare_ID, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.LowFare_Detail[] e_LowFare_Detail = new Entity.LowFare_Detail[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_LowFare_Detail[i] = new Entity.LowFare_Detail();
                    DateRow_LowFare_Detail(o_DataRow, e_LowFare_Detail[i]);

                    i++;
                }

                return e_LowFare_Detail;
            }
        }

        public void Insert_LowFare_Detail(List<Entity.LowFare_Detail> p_LowFare_Detail)
        {
            if (p_LowFare_Detail == null)
                return;

            List<Entity.LowFare_Detail> o_LowFare_Detail = new List<Entity.LowFare_Detail>();
            foreach (Entity.LowFare_Detail e_LowFare_Detail in p_LowFare_Detail)
            {
                Entity.LowFare_Detail c_LowFare_Detail = new Entity.LowFare_Detail();
                c_LowFare_Detail.LowFare_Detail_ID = e_LowFare_Detail.LowFare_Detail_ID;
                c_LowFare_Detail.LowFare_Detail_LowFare_ID = e_LowFare_Detail.LowFare_Detail_LowFare_ID;
                c_LowFare_Detail.LowFare_Detail_From = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_From);
                c_LowFare_Detail.LowFare_Detail_To = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_To);
                c_LowFare_Detail.LowFare_Detail_Departing = e_LowFare_Detail.LowFare_Detail_Departing;
                c_LowFare_Detail.LowFare_Detail_Time1 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_Time1);
                c_LowFare_Detail.LowFare_Flexibility1 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Flexibility1);
                c_LowFare_Detail.LowFare_Detail_Returning = e_LowFare_Detail.LowFare_Detail_Returning;
                c_LowFare_Detail.LowFare_Detail_Time2 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_Time2);
                c_LowFare_Detail.LowFare_Flexibility2 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Flexibility2);                

                o_LowFare_Detail.Add(c_LowFare_Detail);
            }

            d_LowFare_Detail.Insert_LowFare_Detail(o_LowFare_Detail);            
        }

        public void Update_LowFare_Detail(List<Entity.LowFare_Detail> p_LowFare_Detail)
        {
            if (p_LowFare_Detail == null)
                return;

            List<Entity.LowFare_Detail> o_LowFare_Detail = new List<Entity.LowFare_Detail>();
            foreach (Entity.LowFare_Detail e_LowFare_Detail in p_LowFare_Detail)
            {
                Entity.LowFare_Detail c_LowFare_Detail = new Entity.LowFare_Detail();
                c_LowFare_Detail.LowFare_Detail_ID = e_LowFare_Detail.LowFare_Detail_ID;
                c_LowFare_Detail.LowFare_Detail_LowFare_ID = e_LowFare_Detail.LowFare_Detail_LowFare_ID;
                c_LowFare_Detail.LowFare_Detail_From = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_From);
                c_LowFare_Detail.LowFare_Detail_To = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_To);
                c_LowFare_Detail.LowFare_Detail_Departing = e_LowFare_Detail.LowFare_Detail_Departing;
                c_LowFare_Detail.LowFare_Detail_Time1 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_Time1);
                c_LowFare_Detail.LowFare_Flexibility1 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Flexibility1);
                c_LowFare_Detail.LowFare_Detail_Returning = e_LowFare_Detail.LowFare_Detail_Returning;
                c_LowFare_Detail.LowFare_Detail_Time2 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Detail_Time2);
                c_LowFare_Detail.LowFare_Flexibility2 = FilterUtility.FilterSQL(e_LowFare_Detail.LowFare_Flexibility2);
                
                o_LowFare_Detail.Add(c_LowFare_Detail);
            }

            d_LowFare_Detail.Update_LowFare_Detail(o_LowFare_Detail); 
        }

        public void Delete_LowFare_Detail(int p_LowFare_Detail_ID)
        {
            d_LowFare_Detail.Delete_LowFare_Detail(p_LowFare_Detail_ID);
        }

        public void Delete_LowFare_Detail_LowFare_ID(int p_LowFare_Detail_LowFare_ID)
        {
            d_LowFare_Detail.Delete_LowFare_Detail_LowFare_ID(p_LowFare_Detail_LowFare_ID);
        }
    }
}
