using System;
using System.Collections;
using System.Text;

namespace HealthSurvey
{
    public class Class_AnswerTable
    {
        private int _AnswerTable_ID;
        private String _AnswerTable_Title;
        private SortedList _AnswerTable_Select;
        private int _AnswerTable_SelectID;
        private int _AnswerTable_TurnRow;

        public int AnswerTable_ID
        {
            get { return _AnswerTable_ID; }
            set { _AnswerTable_ID = value; }
        }

        public String AnswerTable_Title
        {
            get { return _AnswerTable_Title; }
            set { _AnswerTable_Title = value; }
        }

        public SortedList AnswerTable_Select
        {
            get { return _AnswerTable_Select; }
            set { _AnswerTable_Select = value; }
        }

        public int AnswerTable_SelectID
        {
            get { return _AnswerTable_SelectID; }
            set { _AnswerTable_SelectID = value; }
        }

        public int AnswerTable_TurnRow
        {
            get { return _AnswerTable_TurnRow; }
            set { _AnswerTable_TurnRow = value; }
        }
    }
}
