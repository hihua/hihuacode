using System;
using System.Collections.Generic;
using System.Text;

namespace HealthSurvey
{
    public class Class_AnswerList
    {
        private int _AnswerList_ID;
        private int _AnswerInfo_ID;
        private int _QuestionList_ID;
        private int _QuestionSelect_ID;

        public int AnswerList_ID
        {
            get { return _AnswerList_ID; }
            set { _AnswerList_ID = value; }
        }

        public int AnswerInfo_ID
        {
            get { return _AnswerInfo_ID; }
            set { _AnswerInfo_ID = value; }
        }

        public int QuestionList_ID
        {
            get { return _QuestionList_ID; }
            set { _QuestionList_ID = value; }
        }

        public int QuestionSelect_ID
        {
            get { return _QuestionSelect_ID; }
            set { _QuestionSelect_ID = value; }
        }
    }
}
