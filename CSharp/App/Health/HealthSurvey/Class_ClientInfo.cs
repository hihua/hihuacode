using System;
using System.Collections.Generic;
using System.Text;

namespace HealthSurvey
{
    public class Class_ClientInfo
    {
        private int _ClientInfo_ID;                
        private String _ClientInfo_Name;                
        private uint _ClientInfo_Age;                
        private String _ClientInfo_Sex;                
        private float _ClientInfo_Weight;                
        private float _ClientInfo_Height;                
        private String _ClientInfo_Province;                
        private String _ClientInfo_City;                
        private String _ClientInfo_Address;                
        private String _ClientInfo_Tel;                
        private String _ClientInfo_Email;
        private String _ClientInfo_Zip;
        private DateTime _AddTime;

        public int ClientInfo_ID
        {
            get { return _ClientInfo_ID; }
            set { _ClientInfo_ID = value; }
        }

        public String ClientInfo_Name
        {
            get { return _ClientInfo_Name; }
            set { _ClientInfo_Name = value; }
        }

        public uint ClientInfo_Age
        {
            get { return _ClientInfo_Age; }
            set { _ClientInfo_Age = value; }
        }

        public String ClientInfo_Sex
        {
            get { return _ClientInfo_Sex; }
            set { _ClientInfo_Sex = value; }
        }

        public float ClientInfo_Weight
        {
            get { return _ClientInfo_Weight; }
            set { _ClientInfo_Weight = value; }
        }

        public float ClientInfo_Height
        {
            get { return _ClientInfo_Height; }
            set { _ClientInfo_Height = value; }
        }

        public String ClientInfo_Province
        {
            get { return _ClientInfo_Province; }
            set { _ClientInfo_Province = value; }
        }

        public String ClientInfo_City
        {
            get { return _ClientInfo_City; }
            set { _ClientInfo_City = value; }
        }

        public String ClientInfo_Address
        {
            get { return _ClientInfo_Address; }
            set { _ClientInfo_Address = value; }
        }

        public String ClientInfo_Tel
        {
            get { return _ClientInfo_Tel; }
            set { _ClientInfo_Tel = value; }
        }

        public String ClientInfo_Email
        {
            get { return _ClientInfo_Email; }
            set { _ClientInfo_Email = value; }
        }

        public String ClientInfo_Zip
        {
            get { return _ClientInfo_Zip; }
            set { _ClientInfo_Zip = value; }
        }

        public DateTime AddTime
        {
            get { return _AddTime; }
            set { _AddTime = value; }
        }
    }
}
