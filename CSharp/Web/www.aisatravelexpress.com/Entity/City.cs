using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class City
    {
        private int _City_ID;

        public int City_ID
        {
            get { return _City_ID; }
            set { _City_ID = value; }
        }
        private int _City_Country;

        public int City_Country
        {
            get { return _City_Country; }
            set { _City_Country = value; }
        }
        private string _City_Name;

        public string City_Name
        {
            get { return _City_Name; }
            set { _City_Name = value; }
        }
        private string _City_Title;

        public string City_Title
        {
            get { return _City_Title; }
            set { _City_Title = value; }
        }                
    }
}
