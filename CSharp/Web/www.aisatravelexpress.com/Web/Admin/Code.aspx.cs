using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;
using System.IO;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

using Utility;

namespace Web.Admin
{
    public partial class Code : System.Web.UI.Page
    {
        private Bitmap g_BitMap;
        private Graphics g_Graphics;

        protected void Page_Load(object sender, EventArgs e)
        {
            Response.BufferOutput = true;
            Response.Cache.SetExpires(DateTime.Now.AddMilliseconds(-1));
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.AppendHeader("Pragma", "No-Cache");

            string s_RandomCode = FilterUtility.FilterNumber(4);
            Session["Code"] = s_RandomCode;
            ResponseValidateCode(s_RandomCode);
        }

        private void ResponseValidateCode(string p_Code)
        {
            g_BitMap = new Bitmap(60, 20, PixelFormat.Format32bppArgb);
            g_Graphics = Graphics.FromImage(g_BitMap);
            g_Graphics.FillRectangle(new LinearGradientBrush(new Point(0, 0), new Point(110, 20), Color.FromArgb(240, 255, 255, 255), Color.FromArgb(240, 255, 255, 255)), 0, 0, 200, 200);
            g_Graphics.DrawString(p_Code, new Font("arial", 11), new SolidBrush(Color.Red), new PointF(6, 0));
            g_Graphics.Save();

            MemoryStream o_MemoryStream = new MemoryStream();
            g_BitMap.Save(o_MemoryStream, System.Drawing.Imaging.ImageFormat.Jpeg);
            Response.ClearContent();
            Response.Clear();
            Response.BinaryWrite(o_MemoryStream.ToArray());
            Response.End();
        }
    }
}
