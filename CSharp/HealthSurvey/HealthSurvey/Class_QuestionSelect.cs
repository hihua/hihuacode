using System;
using System.Collections.Generic;
using System.Text;

namespace HealthSurvey
{
    public class Class_QuestionSelect
    {
        private int _QuestionSelect_ID;
        private int _QuestionSelect_MainID;
        private int _QuestionSelect_ListID;
        private String _QuestionSelect_Text;
        private int _QuestionSelect_Score;

        public int QuestionSelect_ID
        {
            get { return _QuestionSelect_ID; }
            set { _QuestionSelect_ID = value; }
        }

        public int QuestionSelect_MainID
        {
            get { return _QuestionSelect_MainID; }
            set { _QuestionSelect_MainID = value; }
        }

        public int QuestionSelect_ListID
        {
            get { return _QuestionSelect_ListID; }
            set { _QuestionSelect_ListID = value; }
        }

        public String QuestionSelect_Text
        {
            get { return _QuestionSelect_Text; }
            set { _QuestionSelect_Text = value; }
        }

        public int QuestionSelect_Score
        {
            get { return _QuestionSelect_Score; }
            set { _QuestionSelect_Score = value; }
        }
    }
}
