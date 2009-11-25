using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

using Utility;

namespace DAL
{
    public class City : DALBase
    {
        private string g_TableName = "t_City";
        private string g_TableFields = "City_ID,City_Country,City_Name,City_Title";
        private string g_TableOrderByFields = "City_Name";

        public City()
        {

        }

        public DataTable Select_City(int p_City_Country, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "";
            if (p_City_Country > 0)
                o_Where += "City_Country=" + p_City_Country.ToString();

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_CityName(string p_City_Name, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "";
            if (VerifyUtility.IsString_NotNull(p_City_Name))
                o_Where += "City_Name Like N'%" + p_City_Name.ToString() + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }

        public DataTable Select_CityTitle(string p_City_Title, int p_PageSize, int p_PageIndex, ref int o_TotalCount, ref int o_TotalPage)
        {
            string o_Where = "";
            if (VerifyUtility.IsString_NotNull(p_City_Title))
                o_Where += "City_Title = '" + p_City_Title.ToString() + "'";

            DataTable o_DataTable = Execute_Select_DataTable(g_TableName, g_TableFields, g_TableOrderByFields, p_PageSize, p_PageIndex, 0, 0, o_Where, ref o_TotalCount, ref o_TotalPage);
            return o_DataTable;
        }
    }
}
