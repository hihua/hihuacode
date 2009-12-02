using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class Member : DALBase
    {
        private string g_TableName = "t_Member";
        private string g_TableFields = "Member_ID,Member_Account,Member_PassWord,Member_Name_CN,Member_Name_EN,Member_Sex,Member_Work,Member_Tel,Member_Mobile,Member_Email,Member_Address,Member_Company_Name,Member_Company_Tel,Member_Company_Address,Member_Months,Member_Airlines,Member_Serial,Member_Points,Member_Commission,Member_Consumption,Member_Times,Member_Recommended,Member_ReSerial,Member_Level,Member_AddTime";
        private string g_TableOrderByFields = "Member_ID";

        public Member()
        {

        }

        public DataTable Select_Member(string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "1=1";
            switch (p_Search_Method)
            {
                case 1:
                    o_Where += " and Member_Account Like N'%" + p_Search_Content + "%'";                        
                    break;

                case 2:
                    o_Where += " and Member_Name_CN Like N'%" + p_Search_Content + "%'";
                    break;

                case 3:
                    o_Where += " and Member_Name_EN Like N'%" + p_Search_Content + "%'";
                    break;

                case 4:
                    o_Where += " and Member_Work Like N'%" + p_Search_Content + "%'";
                    break;

                case 5:
                    o_Where += " and Member_Tel Like N'%" + p_Search_Content + "%'";
                    break;

                case 6:
                    o_Where += " and Member_Mobile Like N'%" + p_Search_Content + "%'";
                    break;

                case 7:
                    o_Where += " and Member_Email Like N'%" + p_Search_Content + "%'";
                    break;

                case 8:
                    o_Where += " and Member_Address Like N'%" + p_Search_Content + "%'";
                    break;

                case 9:
                    o_Where += " and Member_Airlines Like N'%" + p_Search_Content + "%'";
                    break;

                case 10:
                    o_Where += " and Member_Serial Like N'%" + p_Search_Content + "%'";
                    break;

                case 11:
                    o_Where += " and Member_Level = " + p_Search_Content;
                    break;

                case 12:
                    o_Where += " and Member_ReSerial Like N'%" + p_Search_Content + "%'";
                    break;

                default:
                    break;
            }

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 1, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_Member(int p_Member_ID)
        {
            string o_Where = "Member_ID=" + p_Member_ID.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public DataTable Select_Member(int p_Member_Method, string p_Member_Content)
        {
            string o_Where = "";
            switch (p_Member_Method)
            {
                case 1:
                    o_Where += "Member_Account Like N'%" + p_Member_Content + "%'";
                    break;

                case 2:
                    o_Where += "Member_Serial=N'" + p_Member_Content + "'";
                    break;

                default:
                    return null;
            }            

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public DataTable Select_Member(string p_Member_Account, string p_Member_PassWord)
        {
            string o_Where = "Member_Account=N'" + p_Member_Account + "' and Member_PassWord=N'" + p_Member_PassWord + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public DataTable Select_Account(string p_Member_Account)
        {
            string o_Where = "Member_Account=N'" + p_Member_Account + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public DataTable Select_Email(string p_Member_Email)
        {
            string o_Where = "Member_Email=N'" + p_Member_Email + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }

        public void Insert_Member(Entity.Member p_Member)
        {
            if (p_Member == null)
                return;

            g_TableFields = "Member_Account,Member_PassWord,Member_Name_CN,Member_Name_EN,Member_Sex,Member_Work,Member_Tel,Member_Mobile,Member_Email,Member_Address,Member_Company_Name,Member_Company_Tel,Member_Company_Address,Member_Months,Member_Airlines,Member_Serial,Member_Points,Member_Commission,Member_Consumption,Member_Times,Member_Recommended,Member_ReSerial,Member_Level,Member_AddTime";

            string o_FieldsValue = "";
            o_FieldsValue += "N'" + p_Member.Member_Account + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_PassWord + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Name_CN + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Name_EN + "'";
            o_FieldsValue += ",";

            if (p_Member.Member_Sex)
                o_FieldsValue += "1";
            else
                o_FieldsValue += "0";

            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Work + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Mobile + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Email + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Address + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Company_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Company_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Company_Address + "'";
            o_FieldsValue += ",";

            string Member_Months = "";
            if (p_Member.Member_Months != null)
            {                
                foreach (int Member_Month in p_Member.Member_Months)
                {
                    Member_Months += ";" + Member_Month.ToString();
                }

                if (VerifyUtility.IsString_NotNull(Member_Months))
                {
                    if (Member_Months.StartsWith(";"))
                        Member_Months = Member_Months.Remove(0, 1);
                }
            }
            o_FieldsValue += "N'" + Member_Months + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Airlines + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_Serial + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Commission.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Consumption.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Times.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Recommended.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_ReSerial + "'";
            o_FieldsValue += ",";
            o_FieldsValue += p_Member.Member_Level.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "N'" + p_Member.Member_AddTime.ToString() + "'";
                        
            Execute_Insert(g_TableName, g_TableFields, o_FieldsValue);
        }

        public void Update_Member(Entity.Member p_Member)
        {
            if (p_Member == null)
                return;

            string o_FieldsValue = "";
            o_FieldsValue += "Member_PassWord=N'" + p_Member.Member_PassWord + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Name_CN=N'" + p_Member.Member_Name_CN + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Name_EN=N'" + p_Member.Member_Name_EN + "'";
            o_FieldsValue += ",";

            if (p_Member.Member_Sex)
                o_FieldsValue += "Member_Sex=1";
            else
                o_FieldsValue += "Member_Sex=0";

            o_FieldsValue += ",";
            o_FieldsValue += "Member_Work=N'" + p_Member.Member_Work + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Tel=N'" + p_Member.Member_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Mobile=N'" + p_Member.Member_Mobile + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Email=N'" + p_Member.Member_Email + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Address=N'" + p_Member.Member_Address + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Company_Name=N'" + p_Member.Member_Company_Name + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Company_Tel=N'" + p_Member.Member_Company_Tel + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Company_Address=N'" + p_Member.Member_Company_Address + "'";
            o_FieldsValue += ",";

            string Member_Months = "";
            if (p_Member.Member_Months != null)
            {
                foreach (int Member_Month in p_Member.Member_Months)
                {
                    Member_Months += ";" + Member_Month.ToString();
                }

                if (VerifyUtility.IsString_NotNull(Member_Months))
                {
                    if (Member_Months.StartsWith(";"))
                        Member_Months = Member_Months.Remove(0, 1);
                }
            }
            o_FieldsValue += "Member_Months=N'" + Member_Months + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Airlines=N'" + p_Member.Member_Airlines + "'";
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Points=" + p_Member.Member_Points.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Commission=" + p_Member.Member_Commission.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Consumption=" + p_Member.Member_Consumption.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Level=" + p_Member.Member_Level.ToString();
            o_FieldsValue += ",";
            o_FieldsValue += "Member_Times=" + p_Member.Member_Times.ToString();

            string o_Where = "Member_ID=" + p_Member.Member_ID.ToString();

            Execute_Update(g_TableName, o_FieldsValue, o_Where);
        }

        public void Delete_Member(int p_Member_ID)
        {
            string o_Where = "Member_ID=" + p_Member_ID.ToString();
            Execute_Delete(g_TableName, o_Where);
        }

        public DataTable Get_MemberEmail(string p_Member_Email)
        {
            string o_Where = "Member_Email=N'" + p_Member_Email + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, 1, 1, 1, 0, o_Where);
            return o_DataTable;
        }
    }
}
