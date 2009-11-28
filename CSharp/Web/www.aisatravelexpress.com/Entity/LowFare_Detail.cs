using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class LowFare_Detail
    {
        private int _LowFare_Detail_ID;

        public int LowFare_Detail_ID
        {
            get { return _LowFare_Detail_ID; }
            set { _LowFare_Detail_ID = value; }
        }
        private int _LowFare_Detail_LowFare_ID;

        public int LowFare_Detail_LowFare_ID
        {
            get { return _LowFare_Detail_LowFare_ID; }
            set { _LowFare_Detail_LowFare_ID = value; }
        }
        private string _LowFare_Detail_From;

        public string LowFare_Detail_From
        {
            get { return _LowFare_Detail_From; }
            set { _LowFare_Detail_From = value; }
        }
        private string _LowFare_Detail_To;

        public string LowFare_Detail_To
        {
            get { return _LowFare_Detail_To; }
            set { _LowFare_Detail_To = value; }
        }
        private DateTime _LowFare_Detail_Departing;

        public DateTime LowFare_Detail_Departing
        {
            get { return _LowFare_Detail_Departing; }
            set { _LowFare_Detail_Departing = value; }
        }
        private string _LowFare_Detail_Time1;

        public string LowFare_Detail_Time1
        {
            get { return _LowFare_Detail_Time1; }
            set { _LowFare_Detail_Time1 = value; }
        }
        private DateTime _LowFare_Detail_Returning;

        public DateTime LowFare_Detail_Returning
        {
            get { return _LowFare_Detail_Returning; }
            set { _LowFare_Detail_Returning = value; }
        }
        private string _LowFare_Detail_Time2;

        public string LowFare_Detail_Time2
        {
            get { return _LowFare_Detail_Time2; }
            set { _LowFare_Detail_Time2 = value; }
        }
    }
}
