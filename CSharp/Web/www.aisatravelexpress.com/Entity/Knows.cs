using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Knows
    {
        private int _Knows_ID;
        private int _Knows_ClassID;
        private int _Knows_LanguageID;
        private string _Knows_Title;        
        private string _Knows_Content;
        private DateTime _Knows_AddTime;

        public int Knows_ID
        {
            get { return _Knows_ID; }
            set { _Knows_ID = value; }
        }

        public int Knows_ClassID
        {
            get { return _Knows_ClassID; }
            set { _Knows_ClassID = value; }
        }

        public int Knows_LanguageID
        {
            get { return _Knows_LanguageID; }
            set { _Knows_LanguageID = value; }
        }

        public string Knows_Title
        {
            get { return _Knows_Title; }
            set { _Knows_Title = value; }
        }               

        public string Knows_Content
        {
            get { return _Knows_Content; }
            set { _Knows_Content = value; }
        }

        public DateTime Knows_AddTime
        {
            get { return _Knows_AddTime; }
            set { _Knows_AddTime = value; }
        }
    }
}
