using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Text;

namespace HealthSurvey
{
    public class Function_ClientInfo
    {
        public DataTable Query_ClientInfo()
        {
            OleDbDataAdapter oleDb = new OleDbDataAdapter("Select * From ClientInfo Order By ClientInfo_ID", DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }
                
        public DataTable Query_ClientInfo(int clientInfo_ID)
        {
            OleDbDataAdapter oleDb = new OleDbDataAdapter("Select * From ClientInfo Where ClientInfo_ID = " + clientInfo_ID.ToString(), DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public DataTable Query_ClientInfo(Class_ClientInfo class_clientInfo)
        {
            if (class_clientInfo == null)
                return null;

            String SqlWhere = "Where 1 and 1";
            if (class_clientInfo.ClientInfo_ID > 0)
            {
                SqlWhere += " and ClientInfo_ID = " + class_clientInfo.ClientInfo_ID.ToString();
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Name))
            {
                SqlWhere += " and ClientInfo_Name Like '%" + class_clientInfo.ClientInfo_Name + "%'";
            }

            if (class_clientInfo.ClientInfo_Age > 0)
            {
                SqlWhere += " and ClientInfo_Age = " + class_clientInfo.ClientInfo_Age.ToString();
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Sex))
            {
                SqlWhere += " and ClientInfo_Sex = '" + class_clientInfo.ClientInfo_Sex.ToString() + "'";
            }

            if (class_clientInfo.ClientInfo_Weight > 0)
            {
                SqlWhere += " and ClientInfo_Weight = " + class_clientInfo.ClientInfo_Weight.ToString();
            }

            if (class_clientInfo.ClientInfo_Height > 0)
            {
                SqlWhere += " and ClientInfo_Height = " + class_clientInfo.ClientInfo_Height.ToString();
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Province))
            {
                SqlWhere += " and ClientInfo_Province Like '%" + class_clientInfo.ClientInfo_Province + "%'";
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_City))
            {
                SqlWhere += " and ClientInfo_City Like '%" + class_clientInfo.ClientInfo_City + "%'";
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Address))
            {
                SqlWhere += " and ClientInfo_Address Like '%" + class_clientInfo.ClientInfo_Address + "%'";
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Tel))
            {
                SqlWhere += " and ClientInfo_Tel Like '%" + class_clientInfo.ClientInfo_Tel + "%'";
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Email))
            {
                SqlWhere += " and ClientInfo_Email Like '%" + class_clientInfo.ClientInfo_Email + "%'";
            }

            if (!String.IsNullOrEmpty(class_clientInfo.ClientInfo_Zip))
            {
                SqlWhere += " and ClientInfo_Zip Like '%" + class_clientInfo.ClientInfo_Zip + "%'";
            }

            String Sql = "Select * From ClientInfo " + SqlWhere + " Order By ClientInfo_ID";
            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return dt;
        }

        public bool Insert_ClientInfo(Class_ClientInfo class_clientInfo)
        {
            if (class_clientInfo == null)
                return false;
            else
            {
                class_clientInfo.ClientInfo_Name = CommonFunction.FilterString(class_clientInfo.ClientInfo_Name);
                class_clientInfo.ClientInfo_Province = CommonFunction.FilterString(class_clientInfo.ClientInfo_Province);
                class_clientInfo.ClientInfo_City = CommonFunction.FilterString(class_clientInfo.ClientInfo_City);
                class_clientInfo.ClientInfo_Address = CommonFunction.FilterString(class_clientInfo.ClientInfo_Address);
                class_clientInfo.ClientInfo_Tel = CommonFunction.FilterString(class_clientInfo.ClientInfo_Tel);
                class_clientInfo.ClientInfo_Email = CommonFunction.FilterString(class_clientInfo.ClientInfo_Email);
                class_clientInfo.ClientInfo_Zip = CommonFunction.FilterString(class_clientInfo.ClientInfo_Zip);

                String Sql = "";
                Sql += "Insert Into ClientInfo(ClientInfo_Name,ClientInfo_Age,ClientInfo_Sex,ClientInfo_Weight,ClientInfo_Height,ClientInfo_Province,ClientInfo_City,ClientInfo_Address,ClientInfo_Tel,ClientInfo_Email,ClientInfo_Zip,AddTime) ";
                Sql += "Values('" + class_clientInfo.ClientInfo_Name + "'," + class_clientInfo.ClientInfo_Age.ToString() + ",'" + class_clientInfo.ClientInfo_Sex + "'," + class_clientInfo.ClientInfo_Weight.ToString() + "," + class_clientInfo.ClientInfo_Height.ToString();
                Sql += ",'" + class_clientInfo.ClientInfo_Province + "','" + class_clientInfo.ClientInfo_City + "','" + class_clientInfo.ClientInfo_Address + "','" + class_clientInfo.ClientInfo_Tel + "','" + class_clientInfo.ClientInfo_Email + "','" + class_clientInfo.ClientInfo_Zip + "','" + DateTime.Now.ToString() + "')";

                OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
                DataTable dt = new DataTable();
                oleDb.Fill(dt);

                return true;                
            }
        }

        public bool Update_ClientInfo(Class_ClientInfo class_clientInfo)
        {
            if (class_clientInfo == null)
                return false;
            else
            {
                class_clientInfo.ClientInfo_Name = CommonFunction.FilterString(class_clientInfo.ClientInfo_Name);
                class_clientInfo.ClientInfo_Province = CommonFunction.FilterString(class_clientInfo.ClientInfo_Province);
                class_clientInfo.ClientInfo_City = CommonFunction.FilterString(class_clientInfo.ClientInfo_City);
                class_clientInfo.ClientInfo_Address = CommonFunction.FilterString(class_clientInfo.ClientInfo_Address);
                class_clientInfo.ClientInfo_Tel = CommonFunction.FilterString(class_clientInfo.ClientInfo_Tel);
                class_clientInfo.ClientInfo_Email = CommonFunction.FilterString(class_clientInfo.ClientInfo_Email);
                class_clientInfo.ClientInfo_Zip = CommonFunction.FilterString(class_clientInfo.ClientInfo_Zip);

                String Sql = "";
                Sql += "Update ClientInfo Set ClientInfo_Name = '" + class_clientInfo.ClientInfo_Name + "',";
                Sql += "ClientInfo_Age = " + class_clientInfo.ClientInfo_Age.ToString() + ",";
                Sql += "ClientInfo_Sex = '" + class_clientInfo.ClientInfo_Sex + "',";
                Sql += "ClientInfo_Weight = " + class_clientInfo.ClientInfo_Weight + ",";
                Sql += "ClientInfo_Height = " + class_clientInfo.ClientInfo_Height + ",";
                Sql += "ClientInfo_Province = '" + class_clientInfo.ClientInfo_Province + "',";
                Sql += "ClientInfo_City = '" + class_clientInfo.ClientInfo_City + "',";
                Sql += "ClientInfo_Address = '" + class_clientInfo.ClientInfo_Address + "',";
                Sql += "ClientInfo_Tel = '" + class_clientInfo.ClientInfo_Tel + "',";
                Sql += "ClientInfo_Email = '" + class_clientInfo.ClientInfo_Email + "',";
                Sql += "ClientInfo_Zip = '" + class_clientInfo.ClientInfo_Zip + "' ";
                Sql += "Where ClientInfo_ID = " + class_clientInfo.ClientInfo_ID;

                OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
                DataTable dt = new DataTable();
                oleDb.Fill(dt);

                return true;
            }
        }

        public bool Delete_ClientInfo(int clientInfo_ID)
        {
            String Sql = "";
            Sql += "Delete From ClientInfo Where ClientInfo_ID = " + clientInfo_ID.ToString();
                        
            OleDbDataAdapter oleDb = new OleDbDataAdapter(Sql, DBConnection.AccessConnection);
            DataTable dt = new DataTable();
            oleDb.Fill(dt);

            return true;            
        }
    }
}
