using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class LowFare
    {
        private int _LowFare_ID;

        public int LowFare_ID
        {
            get { return _LowFare_ID; }
            set { _LowFare_ID = value; }
        }
        private int _LowFare_Type;

        public int LowFare_Type
        {
            get { return _LowFare_Type; }
            set { _LowFare_Type = value; }
        }
        private List<LowFare_Detail> _LowFare_Detail_ID;

        public List<LowFare_Detail> LowFare_Detail_ID
        {
            get { return _LowFare_Detail_ID; }
            set { _LowFare_Detail_ID = value; }
        }
        private int _LowFare_Adults;

        public int LowFare_Adults
        {
            get { return _LowFare_Adults; }
            set { _LowFare_Adults = value; }
        }
        private int _LowFare_Children;

        public int LowFare_Children
        {
            get { return _LowFare_Children; }
            set { _LowFare_Children = value; }
        }
        private int _LowFare_Infants;

        public int LowFare_Infants
        {
            get { return _LowFare_Infants; }
            set { _LowFare_Infants = value; }
        }
        private string _LowFare_Passengers;

        public string LowFare_Passengers
        {
            get { return _LowFare_Passengers; }
            set { _LowFare_Passengers = value; }
        }
        
        private string _LowFare_Airline;

        public string LowFare_Airline
        {
            get { return _LowFare_Airline; }
            set { _LowFare_Airline = value; }
        }
        private string _LowFare_Class;

        public string LowFare_Class
        {
            get { return _LowFare_Class; }
            set { _LowFare_Class = value; }
        }
        private Member _LowFare_Member_ID;

        public Member LowFare_Member_ID
        {
            get { return _LowFare_Member_ID; }
            set { _LowFare_Member_ID = value; }
        }
        private AdminUser _LowFare_AdminUser_ID;

        public AdminUser LowFare_AdminUser_ID
        {
            get { return _LowFare_AdminUser_ID; }
            set { _LowFare_AdminUser_ID = value; }
        }
        private int _LowFare_Status;

        public int LowFare_Status
        {
            get { return _LowFare_Status; }
            set { _LowFare_Status = value; }
        }
        private DateTime _LowFare_AddTime;

        public DateTime LowFare_AddTime
        {
            get { return _LowFare_AddTime; }
            set { _LowFare_AddTime = value; }
        }
        private string _LowFare_SubmitTime;

        public string LowFare_SubmitTime
        {
            get { return _LowFare_SubmitTime; }
            set { _LowFare_SubmitTime = value; }
        }
    }
}
