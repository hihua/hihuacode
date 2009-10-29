using System;
using System.Collections.Generic;
using System.Text;

namespace Utility
{
    public class FilterUtility
    {
        public static string FilterSQL(string Str)
        {
            if (Str == null)
                return "";
            else
                return Str.Replace("'", "''");
        }
    }
}
