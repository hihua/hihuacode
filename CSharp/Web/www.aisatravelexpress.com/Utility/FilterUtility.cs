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

        public static string FilterNumber(int p_Long)
        {
            char[] c_ValidateCode = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            string s_RandomCode = "";
            Random o_Random = new Random();

            for (int i = 0; i < p_Long; i++)            
                s_RandomCode += c_ValidateCode[o_Random.Next(0, c_ValidateCode.Length)].ToString();
            
            return s_RandomCode;
        }
    }
}
