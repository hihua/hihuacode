using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class MSN
    {
        private int _MSN_ID;
        private string _MSN_Name;
        private string _MSN_Invitee;

        public int MSN_ID
        {
            get { return _MSN_ID; }
            set { _MSN_ID = value; }
        }

        public string MSN_Name
        {
            get { return _MSN_Name; }
            set { _MSN_Name = value; }
        }

        public string MSN_Invitee
        {
            get { return _MSN_Invitee; }
            set { _MSN_Invitee = value; }
        }
    }
}
