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
    public partial class FCKeditor : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            LblPostedData.Text = HttpUtility.HtmlEncode(FCKeditor1.Value);

            PostedDataBlock.Visible = true;
            PostedAlertBlock.Visible = true;
        }
    }
}