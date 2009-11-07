using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class MSN
    {
        DAL.MSN d_MSN;

        public MSN()
        {
            d_MSN = new DAL.MSN();
        }

        public Entity.MSN[] Select_MSN()
        {
            DataTable o_DataTable = d_MSN.Select_MSN();
            if (o_DataTable == null)
                return null;

            Entity.MSN[] e_MSN = new Entity.MSN[o_DataTable.Rows.Count];
            int i = 0;

            foreach (DataRow o_DataRow in o_DataTable.Rows)
            {
                e_MSN[i] = new Entity.MSN();

                e_MSN[i].MSN_ID = Convert.ToInt32(o_DataRow["MSN_ID"].ToString());
                e_MSN[i].MSN_Name = o_DataRow["MSN_Name"].ToString();
                e_MSN[i].MSN_Invitee = o_DataRow["MSN_Invitee"].ToString();

                i++;                
            }

            return e_MSN;
        }

        public void Insert_MSN(string p_MSN_Name, string p_MSN_Invitee)
        {
            p_MSN_Name = FilterUtility.FilterSQL(p_MSN_Name);
            p_MSN_Invitee = FilterUtility.FilterSQL(p_MSN_Invitee);

            Entity.MSN o_MSN = new Entity.MSN();
            o_MSN.MSN_ID = 0;
            o_MSN.MSN_Name = p_MSN_Name;
            o_MSN.MSN_Invitee = p_MSN_Invitee;

            d_MSN.Insert_MSN(o_MSN);
        }

        public void Update_MSN(int p_MSN_ID, string p_MSN_Name, string p_MSN_Invitee)
        {
            p_MSN_Name = FilterUtility.FilterSQL(p_MSN_Name);
            p_MSN_Invitee = FilterUtility.FilterSQL(p_MSN_Invitee);

            Entity.MSN o_MSN = new Entity.MSN();
            o_MSN.MSN_ID = p_MSN_ID;
            o_MSN.MSN_Name = p_MSN_Name;
            o_MSN.MSN_Invitee = p_MSN_Invitee;

            d_MSN.Update_MSN(o_MSN);
        }

        public void Delete_MSN(int p_MSN_ID)
        {
            d_MSN.Delete_MSN(p_MSN_ID);
        }
    }
}
