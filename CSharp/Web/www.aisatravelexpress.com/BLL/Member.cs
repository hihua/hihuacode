using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Member
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.Member d_Member;

        public Member()
        {
            d_Member = new DAL.Member();
        }

        private void DateRow_Member(DataRow p_DataRow, Entity.Member p_Member)
        {
            if (p_DataRow == null || p_Member == null)
                return;

            p_Member.Member_ID = Convert.ToInt32(p_DataRow["Member_ID"].ToString());
            p_Member.Member_Account = p_DataRow["Member_Account"].ToString();
            p_Member.Member_PassWord = p_DataRow["Member_PassWord"].ToString();
            p_Member.Member_Name_CN = p_DataRow["Member_Name_CN"].ToString();
            p_Member.Member_Name_EN = p_DataRow["Member_Name_EN"].ToString();

            if (p_DataRow["Member_Sex"].ToString().ToUpper() == "TRUE")
                p_Member.Member_Sex = true;
            else
                p_Member.Member_Sex = false;

            p_Member.Member_Work = p_DataRow["Member_Work"].ToString();
            p_Member.Member_Tel = p_DataRow["Member_Tel"].ToString();
            p_Member.Member_Mobile = p_DataRow["Member_Mobile"].ToString();
            p_Member.Member_Email = p_DataRow["Member_Email"].ToString();
            p_Member.Member_Address = p_DataRow["Member_Address"].ToString();
            p_Member.Member_Company_Name = p_DataRow["Member_Company_Name"].ToString();
            p_Member.Member_Company_Tel = p_DataRow["Member_Company_Tel"].ToString();
            p_Member.Member_Company_Address = p_DataRow["Member_Company_Address"].ToString();

            if (p_DataRow["Member_Months"] != null)
            {
                if (VerifyUtility.IsString_NotNull(p_DataRow["Member_Months"].ToString()))
                {
                    if (p_DataRow["Member_Months"].ToString().IndexOf(";") == -1)
                    {
                        if (VerifyUtility.IsNumber_NotNull(p_DataRow["Member_Months"].ToString()))
                        {
                            p_Member.Member_Months = new List<int>();
                            p_Member.Member_Months.Add(Convert.ToInt32(p_DataRow["Member_Months"].ToString()));
                        }
                        else
                            p_Member.Member_Months = null;
                    }
                    else
                    {
                        p_Member.Member_Months = new List<int>();

                        string[] Member_Months = p_DataRow["Member_Months"].ToString().Split(new string[] { ";" }, StringSplitOptions.RemoveEmptyEntries);
                        foreach (string Member_Month in Member_Months)
                        {
                            if (VerifyUtility.IsNumber_NotNull(Member_Month))
                                p_Member.Member_Months.Add(Convert.ToInt32(Member_Month));
                        }
                    }
                }
                else
                {
                    p_Member.Member_Months = null;
                }
            }

            p_Member.Member_Airlines = p_DataRow["Member_Airlines"].ToString();
            p_Member.Member_Serial = p_DataRow["Member_Serial"].ToString();
            p_Member.Member_Points = Convert.ToInt32(p_DataRow["Member_Points"].ToString());
            p_Member.Member_Commission = Convert.ToInt32(p_DataRow["Member_Commission"].ToString());
            p_Member.Member_Consumption = Convert.ToInt32(p_DataRow["Member_Consumption"].ToString());
            p_Member.Member_Times = Convert.ToInt32(p_DataRow["Member_Times"].ToString());
            p_Member.Member_Recommended = Convert.ToInt32(p_DataRow["Member_Recommended"].ToString());
            p_Member.Member_Level = Convert.ToInt32(p_DataRow["Member_Level"].ToString());
            p_Member.Member_AddTime = DateTime.Parse(p_DataRow["Member_AddTime"].ToString());                    
        }

        public string Show_Member_Level(int p_Member_Level)
        {
            switch (p_Member_Level)
            {
                case 1:
                    return "普通";

                case 2:
                    return "高级";

                case 3:
                    return "VIP";

                default:
                    return "";
            }
        }

        public string Random_Member()
        {
            string Member_Serial = "";

            while (true)
            {
                Member_Serial = FilterUtility.FilterNumber(20);

                Entity.Member o_Member = Select_Member(Member_Serial);
                if (o_Member == null)
                    return Member_Serial;
            }
        }

        public bool Check_Account(string p_Member_Account)
        {
            p_Member_Account = FilterUtility.FilterSQL(p_Member_Account);

            DataTable o_DataTable = d_Member.Select_Account(p_Member_Account);
            if (o_DataTable == null)
                return false;
            else            
                return true;            
        }

        public bool Check_Email(string p_Member_Email)
        {
            p_Member_Email = FilterUtility.FilterSQL(p_Member_Email);

            DataTable o_DataTable = d_Member.Select_Email(p_Member_Email);
            if (o_DataTable == null)
                return false;
            else
                return true;
        }

        public Entity.Member[] Select_Member(string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            p_Search_Content = FilterUtility.FilterSQL(p_Search_Content);

            DataTable o_DataTable = d_Member.Select_Member(p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Member[] e_Member = new Entity.Member[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Member[i] = new Entity.Member();
                    DateRow_Member(o_DataRow, e_Member[i]);
                    
                    i++;
                }

                return e_Member;
            }
        }

        public Entity.Member Select_Member(int p_Member_ID)
        {
            DataTable o_DataTable = d_Member.Select_Member(p_Member_ID);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.Member e_Member = new Entity.Member();
                DateRow_Member(o_DataRow, e_Member);

                return e_Member;
            }
        }

        public Entity.Member Select_Member(string p_Member_Serial)
        {
            DataTable o_DataTable = d_Member.Select_Member(p_Member_Serial);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.Member e_Member = new Entity.Member();
                DateRow_Member(o_DataRow, e_Member);

                return e_Member;
            }
        }

        public Entity.Member Select_Member(string p_Member_Account, string p_Member_PassWord)
        {
            DataTable o_DataTable = d_Member.Select_Member(p_Member_Account, p_Member_PassWord);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.Member e_Member = new Entity.Member();
                DateRow_Member(o_DataRow, e_Member);

                return e_Member;
            }
        }

        public void Insert_Member(Entity.Member p_Member)
        {
            if (p_Member == null)
                return;

            Entity.Member o_Member = new Entity.Member();
            o_Member.Member_ID = p_Member.Member_ID;
            o_Member.Member_Account = FilterUtility.FilterSQL(p_Member.Member_Account);
            o_Member.Member_PassWord = FilterUtility.FilterSQL(p_Member.Member_PassWord);
            o_Member.Member_Name_CN = FilterUtility.FilterSQL(p_Member.Member_Name_CN);
            o_Member.Member_Name_EN = FilterUtility.FilterSQL(p_Member.Member_Name_EN);
            o_Member.Member_Sex = p_Member.Member_Sex;
            o_Member.Member_Work = FilterUtility.FilterSQL(p_Member.Member_Work);
            o_Member.Member_Tel = FilterUtility.FilterSQL(p_Member.Member_Tel);
            o_Member.Member_Mobile = FilterUtility.FilterSQL(p_Member.Member_Mobile);
            o_Member.Member_Email = FilterUtility.FilterSQL(p_Member.Member_Email);
            o_Member.Member_Address = FilterUtility.FilterSQL(p_Member.Member_Address);
            o_Member.Member_Company_Name = FilterUtility.FilterSQL(p_Member.Member_Company_Name);
            o_Member.Member_Company_Tel = FilterUtility.FilterSQL(p_Member.Member_Company_Tel);
            o_Member.Member_Company_Address = FilterUtility.FilterSQL(p_Member.Member_Company_Address);
            o_Member.Member_Months = p_Member.Member_Months;
            o_Member.Member_Airlines = FilterUtility.FilterSQL(p_Member.Member_Airlines);
            o_Member.Member_Serial = FilterUtility.FilterSQL(p_Member.Member_Serial);
            o_Member.Member_Points = p_Member.Member_Points;
            o_Member.Member_Commission = p_Member.Member_Commission;
            o_Member.Member_Consumption = p_Member.Member_Consumption;
            o_Member.Member_Times = p_Member.Member_Times;
            o_Member.Member_Recommended = p_Member.Member_Recommended;
            o_Member.Member_Level = p_Member.Member_Level;
            o_Member.Member_AddTime = DateTime.Now;

            d_Member.Insert_Member(o_Member);
        }

        public void Update_Member(Entity.Member p_Member)
        {
            if (p_Member == null)
                return;

            Entity.Member o_Member = new Entity.Member();
            o_Member.Member_ID = p_Member.Member_ID;
            o_Member.Member_Account = FilterUtility.FilterSQL(p_Member.Member_Account);
            o_Member.Member_PassWord = FilterUtility.FilterSQL(p_Member.Member_PassWord);
            o_Member.Member_Name_CN = FilterUtility.FilterSQL(p_Member.Member_Name_CN);
            o_Member.Member_Name_EN = FilterUtility.FilterSQL(p_Member.Member_Name_EN);
            o_Member.Member_Sex = p_Member.Member_Sex;
            o_Member.Member_Work = FilterUtility.FilterSQL(p_Member.Member_Work);
            o_Member.Member_Tel = FilterUtility.FilterSQL(p_Member.Member_Tel);
            o_Member.Member_Mobile = FilterUtility.FilterSQL(p_Member.Member_Mobile);
            o_Member.Member_Email = FilterUtility.FilterSQL(p_Member.Member_Email);
            o_Member.Member_Address = FilterUtility.FilterSQL(p_Member.Member_Address);
            o_Member.Member_Company_Name = FilterUtility.FilterSQL(p_Member.Member_Company_Name);
            o_Member.Member_Company_Tel = FilterUtility.FilterSQL(p_Member.Member_Company_Tel);
            o_Member.Member_Company_Address = FilterUtility.FilterSQL(p_Member.Member_Company_Address);
            o_Member.Member_Months = p_Member.Member_Months;
            o_Member.Member_Airlines = FilterUtility.FilterSQL(p_Member.Member_Airlines);
            o_Member.Member_Serial = FilterUtility.FilterSQL(p_Member.Member_Serial);
            o_Member.Member_Points = p_Member.Member_Points;
            o_Member.Member_Commission = p_Member.Member_Commission;
            o_Member.Member_Consumption = p_Member.Member_Consumption;
            o_Member.Member_Times = p_Member.Member_Times;
            o_Member.Member_Recommended = p_Member.Member_Recommended;
            o_Member.Member_Level = p_Member.Member_Level;
            o_Member.Member_AddTime = p_Member.Member_AddTime;

            d_Member.Update_Member(o_Member);
        }

        public void Delete_Member(int p_Member_ID)
        {
            d_Member.Delete_Member(p_Member_ID);
        }
    }
}
