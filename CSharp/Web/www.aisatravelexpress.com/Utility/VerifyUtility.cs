using System;
using System.Collections.Generic;
using System.Text;

namespace Utility
{
    public class VerifyUtility
    {
        public static bool Is_Number(string Str)
        {
            return Is_Number(Str, 0);
        }

        public static bool Is_Number(string Str, int NumberType)
        {
            try
            {
                if (string.IsNullOrEmpty(Str))
                    return false;

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
                    double Number = Convert.ToDouble(Str);
                    return true;
                }

                if (NumberType == 3)
                {
                    double Number = Convert.ToDouble(Str);
                    if (Number >= 0)
                        return true;
                    else
                        return false;
                }

                return false;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public static bool IsNumber_NotNull(string Str)
        {
            if (string.IsNullOrEmpty(Str))
                return false;

            if (Str.Trim().Length > 0 && Is_Number(Str, 1))
                return true;
            else
                return false;
        }

        public static bool IsString_NotNull(string Str)
        {
            if (string.IsNullOrEmpty(Str))
                return false;

            if (Str.Trim().Length > 0)
                return true;
            else
                return false;
        }
    }
}
