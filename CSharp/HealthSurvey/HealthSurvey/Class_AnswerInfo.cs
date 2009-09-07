using System;
using System.Collections.Generic;
using System.Text;

namespace HealthSurvey
{
    public class Class_AnswerInfo
    {
        private int _AnswerInfo_ID;
        private int _ClientInfo_ID;
        private int _Question_ID;
        private String _Question_Tail;
        private DateTime _AddTime;

        public int AnswerInfo_ID
        {
            get { return _AnswerInfo_ID; }
            set { _AnswerInfo_ID = value; }
        }

        public int ClientInfo_ID
        {
            get { return _ClientInfo_ID; }
            set { _ClientInfo_ID = value; }
        }

        public int Question_ID
        {
            get { return _Question_ID; }
            set { _Question_ID = value; }
        }

        public String Question_Tail
        {
            get { return _Question_Tail; }
            set { _Question_Tail = value; }
        }

        public DateTime AddTime
        {
            get { return _AddTime; }
            set { _AddTime = value; }
        }        
    }
}
