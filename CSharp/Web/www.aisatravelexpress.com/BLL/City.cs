using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.Web;
using System.Xml;

using DAL;
using Entity;
using Utility;


namespace BLL
{
    public class City
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.City d_City;

        public City()
        {
            d_City = new DAL.City();
        }

        public Entity.City[] Select_City(int p_City_Country)
        {
            DataTable o_DataTable = d_City.Select_City(p_City_Country, 0x7FFFFFFF, 1, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.City[] e_City = new Entity.City[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_City[i] = new Entity.City();

                    e_City[i].City_ID = Convert.ToInt32(o_DataRow["City_ID"].ToString());
                    e_City[i].City_Country = Convert.ToInt32(o_DataRow["City_Country"].ToString());
                    e_City[i].City_Name = o_DataRow["City_Name"].ToString();
                    e_City[i].City_Title = o_DataRow["City_Title"].ToString();
                    
                    i++;
                }

                return e_City;
            }
        }

        public void Select_CityName(string p_City_Name, HttpResponse p_HttpResponse)
        {
            DataTable o_DataTable = d_City.Select_CityName(p_City_Name, 0x7FFFFFFF, 1, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return;
            else            
                o_DataTable.WriteXml(p_HttpResponse.OutputStream);            
        }

        public void Select_CityTitle(string p_City_Title, HttpResponse p_HttpResponse)
        {
            DataTable o_DataTable = d_City.Select_CityTitle(p_City_Title, 0x7FFFFFFF, 1, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return;
            else
                o_DataTable.WriteXml(p_HttpResponse.OutputStream);
        }
    }
}
