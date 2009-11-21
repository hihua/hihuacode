using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Member
    {
        private int _Member_ID;

        public int Member_ID
        {
            get { return _Member_ID; }
            set { _Member_ID = value; }
        }
        private string _Member_Account;

        public string Member_Account
        {
            get { return _Member_Account; }
            set { _Member_Account = value; }
        }
        private string _Member_PassWord;

        public string Member_PassWord
        {
            get { return _Member_PassWord; }
            set { _Member_PassWord = value; }
        }
        private string _Member_Name_CN;

        public string Member_Name_CN
        {
            get { return _Member_Name_CN; }
            set { _Member_Name_CN = value; }
        }
        private string _Member_Name_EN;

        public string Member_Name_EN
        {
            get { return _Member_Name_EN; }
            set { _Member_Name_EN = value; }
        }
        private bool _Member_Sex;

        public bool Member_Sex
        {
            get { return _Member_Sex; }
            set { _Member_Sex = value; }
        }
        private string _Member_Work;

        public string Member_Work
        {
            get { return _Member_Work; }
            set { _Member_Work = value; }
        }
        private string _Member_Tel;

        public string Member_Tel
        {
            get { return _Member_Tel; }
            set { _Member_Tel = value; }
        }
        private string _Member_Mobile;

        public string Member_Mobile
        {
            get { return _Member_Mobile; }
            set { _Member_Mobile = value; }
        }
        private string _Member_Email;

        public string Member_Email
        {
            get { return _Member_Email; }
            set { _Member_Email = value; }
        }
        private string _Member_Address;

        public string Member_Address
        {
            get { return _Member_Address; }
            set { _Member_Address = value; }
        }
        private string _Member_Company_Name;

        public string Member_Company_Name
        {
            get { return _Member_Company_Name; }
            set { _Member_Company_Name = value; }
        }
        private string _Member_Company_Tel;

        public string Member_Company_Tel
        {
            get { return _Member_Company_Tel; }
            set { _Member_Company_Tel = value; }
        }
        private string _Member_Company_Address;

        public string Member_Company_Address
        {
            get { return _Member_Company_Address; }
            set { _Member_Company_Address = value; }
        }
        private List<int> _Member_Months;

        public List<int> Member_Months
        {
            get { return _Member_Months; }
            set { _Member_Months = value; }
        }
        private string _Member_Airlines;

        public string Member_Airlines
        {
            get { return _Member_Airlines; }
            set { _Member_Airlines = value; }
        }
        private string _Member_Serial;

        public string Member_Serial
        {
            get { return _Member_Serial; }
            set { _Member_Serial = value; }
        }
        private int _Member_Points;

        public int Member_Points
        {
            get { return _Member_Points; }
            set { _Member_Points = value; }
        }
        private int _Member_Commission;

        public int Member_Commission
        {
            get { return _Member_Commission; }
            set { _Member_Commission = value; }
        }
        private int _Member_Consumption;

        public int Member_Consumption
        {
            get { return _Member_Consumption; }
            set { _Member_Consumption = value; }
        }
        private int _Member_Times;

        public int Member_Times
        {
            get { return _Member_Times; }
            set { _Member_Times = value; }
        }
        private int _Member_Recommended;

        public int Member_Recommended
        {
            get { return _Member_Recommended; }
            set { _Member_Recommended = value; }
        }
        private int _Member_Level;

        public int Member_Level
        {
            get { return _Member_Level; }
            set { _Member_Level = value; }
        }
        private DateTime _Member_AddTime;

        public DateTime Member_AddTime
        {
            get { return _Member_AddTime; }
            set { _Member_AddTime = value; }
        }                
    }
}
