﻿using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

namespace Web.Admin
{
    public partial class RefreshSession : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Response.Write(g_AdminUser.AdminUser_NickName);
        }
    }
}
