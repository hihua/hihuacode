using System;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    public class AdminUser
    {
        private int _AdminUser_ID;
        private string _AdminUser_Name;
        private string _AdminUser_NickName;
        private string _AdminUser_PassWord;
        private AdminUser_Status _AdminUser_Status;
        private DateTime _AdminUser_AddTime;

        public int AdminUser_ID
        {
            get { return _AdminUser_ID; }
            set { _AdminUser_ID = value; }                       
        }

        public string AdminUser_Name
        {
            get { return _AdminUser_Name; }
            set { _AdminUser_Name = value; }
        }

        public string AdminUser_NickName
        {
            get { return _AdminUser_NickName; }
            set { _AdminUser_NickName = value; }
        }

        public string AdminUser_PassWord
        {
            get { return _AdminUser_PassWord; }
            set { _AdminUser_PassWord = value; }
        }

        public AdminUser_Status AdminUser_Status
        {
            get { return _AdminUser_Status; }
            set { _AdminUser_Status = value; }
        }

        public DateTime AdminUser_AddTime
        {
            get { return _AdminUser_AddTime; }
            set { _AdminUser_AddTime = value; }
        }
    }
}
