using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Booking
    {
        private int _Booking_ID;

        public int Booking_ID
        {
            get { return _Booking_ID; }
            set { _Booking_ID = value; }
        }
        private string _Booking_Seq;

        public string Booking_Seq
        {
            get { return _Booking_Seq; }
            set { _Booking_Seq = value; }
        }
        private string _Booking_Airline;

        public string Booking_Airline
        {
            get { return _Booking_Airline; }
            set { _Booking_Airline = value; }
        }
        private string _Booking_Contact;

        public string Booking_Contact
        {
            get { return _Booking_Contact; }
            set { _Booking_Contact = value; }
        }
        private int _Booking_Num;

        public int Booking_Num
        {
            get { return _Booking_Num; }
            set { _Booking_Num = value; }
        }
        private string _Booking_Tel;

        public string Booking_Tel
        {
            get { return _Booking_Tel; }
            set { _Booking_Tel = value; }
        }
        private string _Booking_Email;

        public string Booking_Email
        {
            get { return _Booking_Email; }
            set { _Booking_Email = value; }
        }
        private AdminUser _Booking_AdminUser_ID;

        public AdminUser Booking_AdminUser_ID
        {
            get { return _Booking_AdminUser_ID; }
            set { _Booking_AdminUser_ID = value; }
        }
        private bool _Booking_Kind;

        public bool Booking_Kind
        {
            get { return _Booking_Kind; }
            set { _Booking_Kind = value; }
        }
        private int _Booking_State;

        public int Booking_State
        {
            get { return _Booking_State; }
            set { _Booking_State = value; }
        }
        private DateTime _Booking_AddTime;

        public DateTime Booking_AddTime
        {
            get { return _Booking_AddTime; }
            set { _Booking_AddTime = value; }
        }
        private DateTime _Booking_LastTime;

        public DateTime Booking_LastTime
        {
            get { return _Booking_LastTime; }
            set { _Booking_LastTime = value; }
        }
        private string _Booking_ComitTime;

        public string Booking_ComitTime
        {
            get { return _Booking_ComitTime; }
            set { _Booking_ComitTime = value; }
        }
    }
}
