using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Net;
using System.Net.Mail;
using System.Web;
using System.Web.Configuration;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Utility;

namespace Web
{
    public partial class Member_Forget : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                Banner.ImageUrl = "images/inside_f.jpg";

                SetHyperLinkTitle(HyperLink_Title);
                SetHyperLinkMemberForget(HyperLink_Member_Forget);
            }
        }
       
        protected void SendEmail(Entity.Member p_Member)
        {
            if (p_Member == null)
                return;

            string Email_Address = WebConfigurationManager.AppSettings["Web_Email_Address"];
            string Email_Smtp = WebConfigurationManager.AppSettings["Web_Email_Smtp"];
            string Email_UserName = WebConfigurationManager.AppSettings["Web_Email_UserName"];
            string Email_UserPass = WebConfigurationManager.AppSettings["Web_Email_UserPass"];

            string Subject = "华捷国际旅游会员帐号";
            string Body = "";
            Body += "您在<a href=\"http://www.aisatravelexpress.com\" target=\"_blank\">华捷国际旅游</a>会员帐号为<br/><br/>";
            Body += "用户名：" + p_Member.Member_Account + "<br/>";
            Body += "密码：" + p_Member.Member_PassWord + "<br/>";
            Body += "会员号：" + p_Member.Member_Serial + "<br/>";

            SmtpClient o_SmtpClient = new SmtpClient();
            o_SmtpClient.Host = Email_Smtp;
            o_SmtpClient.UseDefaultCredentials = false;
            o_SmtpClient.Credentials = new NetworkCredential(Email_UserName, Email_UserPass);
            o_SmtpClient.DeliveryMethod = SmtpDeliveryMethod.Network;

            MailMessage o_MailMessage = new MailMessage(Email_Address, p_Member.Member_Email);
            o_MailMessage.Subject = Subject;
            o_MailMessage.Body = Body;
            o_MailMessage.BodyEncoding = System.Text.Encoding.UTF8;
            o_MailMessage.IsBodyHtml = true;

            o_SmtpClient.Timeout = 30;
            o_SmtpClient.Send(o_MailMessage);

            ResponseSuccess("发送成功");
        }

        protected void Member_Submit_Click1(object sender, EventArgs e)
        {
            if (VerifyUtility.IsString_NotNull(Member_Email.Text))
            {
                BLL.Member b_Member = new BLL.Member();
                Entity.Member e_Member = b_Member.Get_MemberEmail(Member_Email.Text);
                if (e_Member == null)
                    ResponseError("没有该E-mail的会员, 如注册时填写的邮箱不正确,请联系在线客服人员");
                else
                    SendEmail(e_Member);
            }
            else
                ResponseError("请输入E-Mail");
        }
    }
}
