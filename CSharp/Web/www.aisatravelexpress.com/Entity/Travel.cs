using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Travel
    {
        private int _Travel_ID;

        public int Travel_ID
        {
            get { return _Travel_ID; }
            set { _Travel_ID = value; }
        }
        private int _Travel_LanguageID;

        public int Travel_LanguageID
        {
            get { return _Travel_LanguageID; }
            set { _Travel_LanguageID = value; }
        }
        private int _Travel_TypeID;

        public int Travel_TypeID
        {
            get { return _Travel_TypeID; }
            set { _Travel_TypeID = value; }
        }
        private string _Travel_Code;

        public string Travel_Code
        {
            get { return _Travel_Code; }
            set { _Travel_Code = value; }
        }
        private string _Travel_Name;

        public string Travel_Name
        {
            get { return _Travel_Name; }
            set { _Travel_Name = value; }
        }
        private string _Travel_Price;

        public string Travel_Price
        {
            get { return _Travel_Price; }
            set { _Travel_Price = value; }
        }
        private int _Travel_Points;

        public int Travel_Points
        {
            get { return _Travel_Points; }
            set { _Travel_Points = value; }
        }
        private DateTime _Travel_StartDate;

        public DateTime Travel_StartDate
        {
            get { return _Travel_StartDate; }
            set { _Travel_StartDate = value; }
        }
        private DateTime _Travel_EndDate;

        public DateTime Travel_EndDate
        {
            get { return _Travel_EndDate; }
            set { _Travel_EndDate = value; }
        }
        private string _Travel_Views;

        public string Travel_Views
        {
            get { return _Travel_Views; }
            set { _Travel_Views = value; }
        }
        private string _Travel_Route;

        public string Travel_Route
        {
            get { return _Travel_Route; }
            set { _Travel_Route = value; }
        }
        private string _Travel_PreView1;

        public string Travel_PreView1
        {
            get { return _Travel_PreView1; }
            set { _Travel_PreView1 = value; }
        }
        private string _Travel_PreView2;

        public string Travel_PreView2
        {
            get { return _Travel_PreView2; }
            set { _Travel_PreView2 = value; }
        }
        private List<string> _Travel_PreViews;

        public List<string> Travel_PreViews
        {
            get { return _Travel_PreViews; }
            set { _Travel_PreViews = value; }
        }
        private string _Travel_StartAddr;

        public string Travel_StartAddr
        {
            get { return _Travel_StartAddr; }
            set { _Travel_StartAddr = value; }
        }

        private string _Travel_EndAddr;

        public string Travel_EndAddr
        {
            get { return _Travel_EndAddr; }
            set { _Travel_EndAddr = value; }
        }              
        
        private DateTime _Travel_AddTime;

        public DateTime Travel_AddTime
        {
            get { return _Travel_AddTime; }
            set { _Travel_AddTime = value; }
        }        
    }
}
