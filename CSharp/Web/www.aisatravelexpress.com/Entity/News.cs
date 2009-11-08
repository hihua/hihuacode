using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class News
    {
        private int _News_ID;
        private int _News_ClassID;
        private int _News_LanguageID;        
        private string _News_Title;
        private string _News_Content;
        private DateTime _News_AddTime;

        public int News_ID
        {
            get { return _News_ID; }
            set { _News_ID = value; }
        }

        public int News_ClassID
        {
            get { return _News_ClassID; }
            set { _News_ClassID = value; }
        }

        public int News_LanguageID
        {
            get { return _News_LanguageID; }
            set { _News_LanguageID = value; }
        }

        public string News_Title
        {
            get { return _News_Title; }
            set { _News_Title = value; }
        }

        public string News_Content
        {
            get { return _News_Content; }
            set { _News_Content = value; }
        }

        public DateTime News_AddTime
        {
            get { return _News_AddTime; }
            set { _News_AddTime = value; }
        }
    }
}
