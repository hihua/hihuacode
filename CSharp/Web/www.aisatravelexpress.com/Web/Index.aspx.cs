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

using DAL;

namespace Web
{
    public partial class Index : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            DALBase o_DALBase = new DALBase();
            DataTable o_DataTable = o_DALBase.ExecuteDataTable("Select * From T_AdminUser");
            Response.Write(o_DataTable.Rows.Count);
        }
    }
}
