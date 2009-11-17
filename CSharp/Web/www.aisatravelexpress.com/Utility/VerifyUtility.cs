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

        public static string Check_UploadFile(string UploadFile, ref string UploadFileExt)
        {
            UploadFileExt = "";
            if (IsString_NotNull(UploadFile) && UploadFile.Length > 3)
            {
                UploadFileExt = UploadFile.Substring(UploadFile.Length - 3, 3);
                UploadFileExt = UploadFileExt.ToLower();

                if (UploadFileExt == "jpg" || UploadFileExt == "gif" || UploadFileExt == "png")
                    return "";
                else
                {
                    UploadFileExt = "";
                    return "只能上传jpg,gif,png图片";
                }
            }
            else
                return "请上传正确的文件";
        }

        public static bool Check_Date(string Str)
        {
            DateTime o_DateTime;
            return DateTime.TryParse(Str, out o_DateTime);            
        }
    }
}
