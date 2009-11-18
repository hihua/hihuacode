using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;

using DAL;
using Entity;
using Utility;

namespace BLL
{
    public class Travel
    {
        public int g_TotalCount;
        public int g_TotalPage;
        DAL.Travel d_Travel;

        public Travel()
        {
            d_Travel = new DAL.Travel();
        }

        public Entity.Travel[] Select_Travel(int p_Travel_LanguageID, int p_Travel_TypeID, string p_Search_Content, int p_Search_Method, int p_PageSize, int p_PageIndex)
        {
            p_Search_Content = FilterUtility.FilterSQL(p_Search_Content);

            DataTable o_DataTable = d_Travel.Select_Travel(p_Travel_LanguageID, p_Travel_TypeID, p_Search_Content, p_Search_Method, p_PageSize, p_PageIndex, ref g_TotalCount, ref g_TotalPage);
            if (o_DataTable == null)
                return null;
            else
            {
                Entity.Travel[] e_Travel = new Entity.Travel[o_DataTable.Rows.Count];

                int i = 0;
                foreach (DataRow o_DataRow in o_DataTable.Rows)
                {
                    e_Travel[i] = new Entity.Travel();

                    e_Travel[i].Travel_ID = Convert.ToInt32(o_DataRow["Travel_ID"].ToString());
                    e_Travel[i].Travel_LanguageID = Convert.ToInt32(o_DataRow["Travel_LanguageID"].ToString());
                    e_Travel[i].Travel_TypeID = Convert.ToInt32(o_DataRow["Travel_TypeID"].ToString());
                    e_Travel[i].Travel_Code = o_DataRow["Travel_Code"].ToString();
                    e_Travel[i].Travel_Name = o_DataRow["Travel_Name"].ToString();
                    e_Travel[i].Travel_Price = o_DataRow["Travel_Price"].ToString();
                    e_Travel[i].Travel_Points = Convert.ToInt32(o_DataRow["Travel_Points"].ToString());
                    e_Travel[i].Travel_StartDate = DateTime.Parse(o_DataRow["Travel_StartDate"].ToString());
                    e_Travel[i].Travel_EndDate = DateTime.Parse(o_DataRow["Travel_EndDate"].ToString());
                    e_Travel[i].Travel_Views = o_DataRow["Travel_Views"].ToString();
                    e_Travel[i].Travel_Route = o_DataRow["Travel_Route"].ToString();
                    e_Travel[i].Travel_PreView1 = o_DataRow["Travel_PreView1"].ToString();
                    e_Travel[i].Travel_PreView2 = o_DataRow["Travel_PreView2"].ToString();

                    string o_Travel_PreViews = o_DataRow["Travel_PreViews"].ToString();
                    if (VerifyUtility.IsString_NotNull(o_Travel_PreViews))
                    {
                        if (o_Travel_PreViews.IndexOf(";") > -1)
                        {
                            string[] Travel_PreViews = o_Travel_PreViews.Split(new string[] { ";" }, StringSplitOptions.RemoveEmptyEntries);
                            e_Travel[i].Travel_PreViews = Travel_PreViews;
                        }
                        else
                        {
                            string[] Travel_PreViews = new string[1];
                            Travel_PreViews[0] = o_Travel_PreViews;
                            e_Travel[i].Travel_PreViews = Travel_PreViews;
                        }
                    }

                    e_Travel[i].Travel_StartAddr = o_DataRow["Travel_StartAddr"].ToString();
                    e_Travel[i].Travel_EndAddr = o_DataRow["Travel_EndAddr"].ToString();
                    e_Travel[i].Travel_AddTime = DateTime.Parse(o_DataRow["Travel_AddTime"].ToString());

                    i++;
                }

                return e_Travel;
            }

        }

        public Entity.Travel Select_Travel(int p_Travel_ID)
        {
            DataTable o_DataTable = d_Travel.Select_Travel(p_Travel_ID);
            if (o_DataTable == null)
                return null;
            else
            {
                DataRow o_DataRow = o_DataTable.Rows[0];

                Entity.Travel e_Travel = new Entity.Travel();
                e_Travel.Travel_ID = Convert.ToInt32(o_DataRow["Travel_ID"].ToString());
                e_Travel.Travel_LanguageID = Convert.ToInt32(o_DataRow["Travel_LanguageID"].ToString());
                e_Travel.Travel_TypeID = Convert.ToInt32(o_DataRow["Travel_TypeID"].ToString());
                e_Travel.Travel_Code = o_DataRow["Travel_Code"].ToString();
                e_Travel.Travel_Name = o_DataRow["Travel_Name"].ToString();
                e_Travel.Travel_Price = o_DataRow["Travel_Price"].ToString();
                e_Travel.Travel_Points = Convert.ToInt32(o_DataRow["Travel_Points"].ToString());
                e_Travel.Travel_StartDate = DateTime.Parse(o_DataRow["Travel_StartDate"].ToString());
                e_Travel.Travel_EndDate = DateTime.Parse(o_DataRow["Travel_EndDate"].ToString());
                e_Travel.Travel_Views = o_DataRow["Travel_Views"].ToString();
                e_Travel.Travel_Route = o_DataRow["Travel_Route"].ToString();
                e_Travel.Travel_PreView1 = o_DataRow["Travel_PreView1"].ToString();
                e_Travel.Travel_PreView2 = o_DataRow["Travel_PreView2"].ToString();

                string o_Travel_PreViews = o_DataRow["Travel_PreViews"].ToString();
                if (VerifyUtility.IsString_NotNull(o_Travel_PreViews))
                {
                    if (o_Travel_PreViews.IndexOf(";") > -1)
                    {
                        string[] Travel_PreViews = o_Travel_PreViews.Split(new string[] { ";" }, StringSplitOptions.RemoveEmptyEntries);
                        e_Travel.Travel_PreViews = Travel_PreViews;
                    }
                    else
                    {
                        string[] Travel_PreViews = new string[1];
                        Travel_PreViews[0] = o_Travel_PreViews;
                        e_Travel.Travel_PreViews = Travel_PreViews;
                    }
                }

                e_Travel.Travel_StartAddr = o_DataRow["Travel_StartAddr"].ToString();
                e_Travel.Travel_EndAddr = o_DataRow["Travel_EndAddr"].ToString();
                e_Travel.Travel_AddTime = DateTime.Parse(o_DataRow["Travel_AddTime"].ToString());
                
                return e_Travel;
            }
        }

        public void Insert_Travel(int p_Travel_LanguageID, int p_Travel_TypeID, string p_Travel_Code, string p_Travel_Name, string p_Travel_Price, int p_Travel_Points, DateTime p_Travel_StartDate, DateTime p_Travel_EndDate, string p_Travel_Views, string p_Travel_Route, string p_Travel_PreView1, string p_Travel_PreView2, string[] p_Travel_PreViews, string p_Travel_StartAddr, string p_Travel_EndAddr)
        {
            p_Travel_Code = FilterUtility.FilterSQL(p_Travel_Code);
            p_Travel_Name = FilterUtility.FilterSQL(p_Travel_Name);
            p_Travel_Price = FilterUtility.FilterSQL(p_Travel_Price);
            p_Travel_Views = FilterUtility.FilterSQL(p_Travel_Views);
            p_Travel_Route = FilterUtility.FilterSQL(p_Travel_Route);
            p_Travel_PreView1 = FilterUtility.FilterSQL(p_Travel_PreView1);
            p_Travel_PreView2 = FilterUtility.FilterSQL(p_Travel_PreView2);
            p_Travel_StartAddr = FilterUtility.FilterSQL(p_Travel_StartAddr);
            p_Travel_EndAddr = FilterUtility.FilterSQL(p_Travel_EndAddr);

            Entity.Travel o_Travel = new Entity.Travel();            
            o_Travel.Travel_LanguageID = p_Travel_LanguageID;
            o_Travel.Travel_TypeID = p_Travel_TypeID;
            o_Travel.Travel_Code = p_Travel_Code;
            o_Travel.Travel_Name = p_Travel_Name;
            o_Travel.Travel_Price = p_Travel_Price;
            o_Travel.Travel_Points = p_Travel_Points;
            o_Travel.Travel_StartDate = p_Travel_StartDate;
            o_Travel.Travel_EndDate = p_Travel_EndDate;
            o_Travel.Travel_Views = p_Travel_Views;
            o_Travel.Travel_Route = p_Travel_Route;
            o_Travel.Travel_PreView1 = p_Travel_PreView1;
            o_Travel.Travel_PreView2 = p_Travel_PreView2;
            o_Travel.Travel_PreViews = p_Travel_PreViews;
            o_Travel.Travel_StartAddr = p_Travel_StartAddr;
            o_Travel.Travel_EndAddr = p_Travel_EndAddr;
            o_Travel.Travel_AddTime = DateTime.Now;

            d_Travel.Insert_Travel(o_Travel);
        }

        public void Update_Travel(Entity.Travel p_Travel)
        {           
            Entity.Travel o_Travel = new Entity.Travel();
            o_Travel.Travel_ID = p_Travel.Travel_ID;
            o_Travel.Travel_LanguageID = p_Travel.Travel_LanguageID;
            o_Travel.Travel_TypeID = p_Travel.Travel_TypeID;
            o_Travel.Travel_Code = FilterUtility.FilterSQL(p_Travel.Travel_Code);
            o_Travel.Travel_Name = FilterUtility.FilterSQL(p_Travel.Travel_Name);
            o_Travel.Travel_Price = FilterUtility.FilterSQL(p_Travel.Travel_Price);
            o_Travel.Travel_Points = p_Travel.Travel_Points;
            o_Travel.Travel_StartDate = p_Travel.Travel_StartDate;
            o_Travel.Travel_EndDate = p_Travel.Travel_EndDate;
            o_Travel.Travel_Views = FilterUtility.FilterSQL(p_Travel.Travel_Views);
            o_Travel.Travel_Route = FilterUtility.FilterSQL(p_Travel.Travel_Route);
            o_Travel.Travel_PreView1 = p_Travel.Travel_PreView1;
            o_Travel.Travel_PreView2 = p_Travel.Travel_PreView2;
            o_Travel.Travel_PreViews = p_Travel.Travel_PreViews;
            o_Travel.Travel_StartAddr = FilterUtility.FilterSQL(p_Travel.Travel_StartAddr);
            o_Travel.Travel_EndAddr = FilterUtility.FilterSQL(p_Travel.Travel_EndAddr);
            o_Travel.Travel_AddTime = p_Travel.Travel_AddTime;

            d_Travel.Update_Travel(o_Travel);
        }

        public void Update_Travel(Entity.Travel p_Travel, string p_Travel_Images, string p_Travel_ImagesPath)
        {
            if (p_Travel != null)
            {
                if (!p_Travel_ImagesPath.EndsWith("\\") && !p_Travel_ImagesPath.EndsWith("/"))
                    p_Travel_ImagesPath += "\\";

                if (p_Travel.Travel_PreViews.Length == 1)                
                    p_Travel.Travel_PreViews = null;                
                else
                {
                    int i = 0;
                    string[] Travel_PreViews = new string[p_Travel.Travel_PreViews.Length - 1];

                    foreach (string Travel_Images in p_Travel.Travel_PreViews)
                    {
                        if (Travel_Images != p_Travel_Images)
                            Travel_PreViews[i] = Travel_Images;

                        i++;
                    }

                    p_Travel.Travel_PreViews = Travel_PreViews;                    
                }

                if (File.Exists(p_Travel_ImagesPath + p_Travel_Images))
                    File.Delete(p_Travel_ImagesPath + p_Travel_Images);
            }
        }

        public void Update_Travel(Entity.Travel p_Travel, int p_Pos, string p_Travel_Images, string p_Travel_ImagesPath)
        {
            if (p_Travel != null)
            {
                if (!p_Travel_ImagesPath.EndsWith("\\") && !p_Travel_ImagesPath.EndsWith("/"))
                    p_Travel_ImagesPath += "\\";

                if (p_Travel.Travel_PreViews == null || p_Travel.Travel_PreViews.Length == 0)
                {
                    string[] Travel_PreViews = new string[1];
                    Travel_PreViews[0] = p_Travel_Images;
                    p_Travel.Travel_PreViews = Travel_PreViews;
                }
                else
                {
                    int i = 0;
                    foreach (string Travel_Images in p_Travel.Travel_PreViews)
                    {
                        if (i == p_Pos)
                        {
                            if (File.Exists(p_Travel_ImagesPath + p_Travel.Travel_PreViews[i]))
                                File.Delete(p_Travel_ImagesPath + p_Travel.Travel_PreViews[i]);

                            p_Travel.Travel_PreViews[i] = p_Travel_Images;

                            break;
                        }

                        i++;
                    }
                }
            }
        }

        public void Delete_Travel(int p_Travel_ID, string p_Travel_ImagesPath)
        {
            if (!p_Travel_ImagesPath.EndsWith("\\") && !p_Travel_ImagesPath.EndsWith("/"))
                p_Travel_ImagesPath += "\\";

            Entity.Travel o_Travel = Select_Travel(p_Travel_ID);
            if (o_Travel != null && o_Travel.Travel_PreViews != null)
            {
                foreach (string Travel_PreViews in o_Travel.Travel_PreViews)
                {
                    if (File.Exists(p_Travel_ImagesPath + Travel_PreViews))
                        File.Delete(p_Travel_ImagesPath + Travel_PreViews);
                }
            }

            d_Travel.Delete_Travel(p_Travel_ID);
        }
    }
}
