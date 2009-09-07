using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WinFormsUI.Docking;

namespace HealthSurvey
{
    public partial class Form_Class : DockContent
    {
        public Form_Class()
        {
            InitializeComponent();
            base.SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.ResizeRedraw | ControlStyles.UserPaint, true);
        }

        protected override void OnFormClosing(FormClosingEventArgs e)
        {
            if (e.CloseReason != CloseReason.MdiFormClosing)
            {
                e.Cancel = true;
                base.Hide();
            }
            else
            {
                base.OnFormClosing(e);
            }
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);
            e.Graphics.DrawRectangle(new Pen(Color.FromArgb(0x7e, 0xa4, 0xd4)), base.ClientRectangle);
        }
    }
}
