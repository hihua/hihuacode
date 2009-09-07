using System;
using System.Collections.Generic;
using System.Text;

namespace HealthSurvey
{
    public class Class_QuestionCase
    {
        private int _QuestionCase_ID;
        private int _QuestionCase_MainID;
        private int _QuestionCase_MarkMin;
        private int _QuestionCase_MarkMax;
        private String _QuestionCase_Result;
        private String _QuestionCase_Images;
        private String _QuestionCase_Manual;
        private String _QuestionCase_Description;

        public int QuestionCase_ID
        {
            get { return _QuestionCase_ID; }
            set { _QuestionCase_ID = value; }
        }

        public int QuestionCase_MainID
        {
            get { return _QuestionCase_MainID; }
            set { _QuestionCase_MainID = value; }
        }

        public int QuestionCase_MarkMin
        {
            get { return _QuestionCase_MarkMin; }
            set { _QuestionCase_MarkMin = value; }
        }

        public int QuestionCase_MarkMax
        {
            get { return _QuestionCase_MarkMax; }
            set { _QuestionCase_MarkMax = value; }
        }

        public String QuestionCase_Result
        {
            get { return _QuestionCase_Result; }
            set { _QuestionCase_Result = value; }
        }

        public String QuestionCase_Images
        {
            get { return _QuestionCase_Images; }
            set { _QuestionCase_Images = value; }
        }

        public String QuestionCase_Manual
        {
            get { return _QuestionCase_Manual; }
            set { _QuestionCase_Manual = value; }
        }

        public String QuestionCase_Description
        {
            get { return _QuestionCase_Description; }
            set { _QuestionCase_Description = value; }
        }      
    }
}
