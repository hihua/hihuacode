using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using System.Windows.Forms;

namespace HealthSurvey
{
    public class DBConnection
    {
        public static String AccessConnection = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + Application.StartupPath + "\\Health.mdb;Persist Security Info=True;Jet OLEDB:Database Password=";
    }
}
