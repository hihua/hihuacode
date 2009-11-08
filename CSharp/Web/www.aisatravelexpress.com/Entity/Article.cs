using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class Article
    {
        private int _Article_ID;
        private int _Article_ClassID;
        private int _Article_LanguageID;        
        private string _Article_Content;
        private DateTime _Article_AddTime;

        public int Article_ID
        {
            get { return _Article_ID; }
            set { _Article_ID = value; }
        }

        public int Article_ClassID
        {
            get { return _Article_ClassID; }
            set { _Article_ClassID = value; }
        }

        public int Article_LanguageID
        {
            get { return _Article_LanguageID; }
            set { _Article_LanguageID = value; }
        }

        public string Article_Content
        {
            get { return _Article_Content; }
            set { _Article_Content = value; }
        }

        public DateTime Article_AddTime
        {
            get { return _Article_AddTime; }
            set { _Article_AddTime = value; }
        }
    }
}
