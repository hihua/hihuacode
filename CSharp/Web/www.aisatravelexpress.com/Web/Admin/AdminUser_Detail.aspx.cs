using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Entity;
using Utility;

namespace Web.Admin
{
    public partial class AdminUser_Detail : PageBase
    {
        private int AdminUser_ID = 0;
        private Entity.AdminUser e_AdminUser;
        private BLL.AdminUser b_AdminUser;
       
        protected void Page_Load(object sender, EventArgs e)
        {
            if (VerifyUtility.IsNumber_NotNull(Request["AdminUser_ID"]))
                AdminUser_ID = Convert.ToInt32(Request["AdminUser_ID"]);

            b_AdminUser = new BLL.AdminUser();
            e_AdminUser = b_AdminUser.Select_AdminUser(AdminUser_ID);

            if (!Page.IsPostBack)
            {
                if (AdminUser_ID == 0)
                {
                    AdminUser_PassWord_TD.Visible = false;
                    AdminUser_Status_TD.ColSpan = 2;
                    AdminUser_AddTime.Visible = false;
                }
                else
                {
                    if (e_AdminUser != null)
                    {
                        AdminUser_Name.Text = e_AdminUser.AdminUser_Name;
                        AdminUser_NickName.Text = e_AdminUser.AdminUser_NickName;

                        if (e_AdminUser.AdminUser_Status == 0)
                        {
                            AdminUser_Status1.Checked = true;
                            AdminUser_Status2.Checked = false;
                        }

                        AdminUser_AddTime.InnerHtml += e_AdminUser.AdminUser_AddTime.ToString();
                        AdminUser_Submit.Text = " 修改 ";
                    }
                }
            }
        }

        protected void AdminUser_Submit_Click(object sender, EventArgs e)
        {
            if (!VerifyUtility.IsString_NotNull(AdminUser_Name.Text))                            
                ResponseError("请输入用户名");            

            if (!VerifyUtility.IsString_NotNull(AdminUser_NickName.Text))            
                ResponseError("请输入呢称");
            
            if (!AdminUser_Status1.Checked && !AdminUser_Status2.Checked)            
                ResponseError("请选择权限");            

            if (AdminUser_ID == 0)
            {
                if (!VerifyUtility.IsString_NotNull(AdminUser_PassWord.Text))                
                    ResponseError("请输入密码");                

                if (!VerifyUtility.IsString_NotNull(AdminUser_PassWord1.Text))                
                    ResponseError("请输入确认密码"); 

                if (AdminUser_PassWord.Text != AdminUser_PassWord1.Text)                                    
                    ResponseError("确认密码输入不正确");                
                                
                if (AdminUser_Status1.Checked)
                    b_AdminUser.Insert_AdminUser(AdminUser_Name.Text, AdminUser_NickName.Text, AdminUser_PassWord.Text, 0);
                else
                {
                    if (AdminUser_Status2.Checked)
                        b_AdminUser.Insert_AdminUser(AdminUser_Name.Text, AdminUser_NickName.Text, AdminUser_PassWord.Text, 1);
                }

                ResponseSuccess("添加成功", "AdminUser_Detail.aspx");
            }
            else
            {
                if (VerifyUtility.IsString_NotNull(AdminUser_PassWord.Text))
                {
                    if (!VerifyUtility.IsString_NotNull(AdminUser_PassWord1.Text))                    
                        ResponseError("请输入密码");                    

                    if (!VerifyUtility.IsString_NotNull(AdminUser_PassWord2.Text))                    
                        ResponseError("请输入确认密码");                     

                    if (AdminUser_PassWord.Text != AdminUser_PassWord1.Text)                    
                        ResponseError("确认密码输入不正确");                     

                    if (AdminUser_PassWord2.Text != e_AdminUser.AdminUser_PassWord)                    
                        ResponseError("原密码输入不正确"); 
                                                            
                }
                if (AdminUser_Status1.Checked)
                    b_AdminUser.Update_AdminUser(e_AdminUser.AdminUser_ID, AdminUser_Name.Text, AdminUser_NickName.Text, AdminUser_PassWord.Text, 0);
                else
                {
                    if (AdminUser_Status2.Checked)
                        b_AdminUser.Update_AdminUser(e_AdminUser.AdminUser_ID, AdminUser_Name.Text, AdminUser_NickName.Text, AdminUser_PassWord.Text, 1);
                }               
               
                ResponseSuccess("修改成功", "AdminUser_Detail.aspx?AdminUser_ID=" + e_AdminUser.AdminUser_ID.ToString());
            }                      
        }
    }
}
