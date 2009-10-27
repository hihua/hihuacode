using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace HealthSurvey
{
    public class CommonFunction
    {
        public static bool IsNumber(String Str, int NumberType)
        {
            try
            {
                if (NumberType == 0)
                {
                    int Number = Convert.ToInt32(Str);
                    return true;
                }

                if (NumberType == 1)
                {
                    uint Number = Convert.ToUInt32(Str);
                    return true;
                }

                if (NumberType == 2)
                {
                    float Number = Convert.ToSingle(Str);
                    return true;
                }

                return false;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public static String FilterString(String Str)
        {
            if (Str == null)
                return "";
            else
                return Str.Replace("'", "''");
        }

        public static bool IsEmail(String Str)
        {
            return Regex.IsMatch(Str, @"^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$");
        }
    }
}
