using System;
using System.Collections;
using System.Text;

namespace HealthSurvey
{
    public class Class_Question
    {
        private int _QuestionList_ID;
        private int _QuestionList_ListID;
        private String _Question_Title;
        private int _Question_Option;
        private int _Question_SelectCount;
        private int _Question_TurnRow;
        private ArrayList _Class_QuestionSelect;

        public int QuestionList_ID
        {
            get { return _QuestionList_ID; }
            set { _QuestionList_ID = value; }
        }

        public int QuestionList_ListID
        {
            get { return _QuestionList_ListID; }
            set { _QuestionList_ListID = value; }
        }

        public String Question_Title
        {
            get { return _Question_Title; }
            set { _Question_Title = value; }
        }

        public int Question_Option
        {
            get { return _Question_Option; }
            set { _Question_Option = value; }
        }

        public int Question_SelectCount
        {
            get { return _Question_SelectCount; }
            set { _Question_SelectCount = value; }
        }

        public int Question_TurnRow
        {
            get { return _Question_TurnRow; }
            set { _Question_TurnRow = value; }
        }

        public ArrayList Class_QuestionSelect
        {
            get { return _Class_QuestionSelect; }
            set { _Class_QuestionSelect = value; }
        }        
    }
}
