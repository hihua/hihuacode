using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Consumption
    {
        private int _Consumption_ID;

        public int Consumption_ID
        {
            get { return _Consumption_ID; }
            set { _Consumption_ID = value; }
        }
        private string _Consumption_Serial;

        public string Consumption_Serial
        {
            get { return _Consumption_Serial; }
            set { _Consumption_Serial = value; }
        }
        private int _Consumption_Type;

        public int Consumption_Type
        {
            get { return _Consumption_Type; }
            set { _Consumption_Type = value; }
        }
        private string _Consumption_Src;

        public string Consumption_Src
        {
            get { return _Consumption_Src; }
            set { _Consumption_Src = value; }
        }
        private string _Consumption_Dest;

        public string Consumption_Dest
        {
            get { return _Consumption_Dest; }
            set { _Consumption_Dest = value; }
        }
        private int _Consumption_Price;

        public int Consumption_Price
        {
            get { return _Consumption_Price; }
            set { _Consumption_Price = value; }
        }
        private int _Consumption_DePrice;

        public int Consumption_DePrice
        {
            get { return _Consumption_DePrice; }
            set { _Consumption_DePrice = value; }
        }
        private int _Consumption_Points;

        public int Consumption_Points
        {
            get { return _Consumption_Points; }
            set { _Consumption_Points = value; }
        }
        private int _Consumption_Commission;

        public int Consumption_Commission
        {
            get { return _Consumption_Commission; }
            set { _Consumption_Commission = value; }
        }
        private DateTime _Consumption_Date;

        public DateTime Consumption_Date
        {
            get { return _Consumption_Date; }
            set { _Consumption_Date = value; }
        }
        private Member _Consumption_Org_Member_ID;

        public Member Consumption_Org_Member_ID
        {
            get { return _Consumption_Org_Member_ID; }
            set { _Consumption_Org_Member_ID = value; }
        }
        private Member _Consumption_Com_Member_ID;

        public Member Consumption_Com_Member_ID
        {
            get { return _Consumption_Com_Member_ID; }
            set { _Consumption_Com_Member_ID = value; }
        }
        private AdminUser _Consumption_Admin_ID;

        public AdminUser Consumption_Admin_ID
        {
            get { return _Consumption_Admin_ID; }
            set { _Consumption_Admin_ID = value; }
        }
        private DateTime _Consumption_AddTime;

        public DateTime Consumption_AddTime
        {
            get { return _Consumption_AddTime; }
            set { _Consumption_AddTime = value; }
        }
        private string _Consumption_Remark;

        public string Consumption_Remark
        {
            get { return _Consumption_Remark; }
            set { _Consumption_Remark = value; }
        }
    }
}
