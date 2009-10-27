using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace HealthSurvey
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {
            if (DateTime.Now.Year != 2009)
                return;

            if (DateTime.Now.Month != 8 && DateTime.Now.Month != 9 && DateTime.Now.Month != 10 && DateTime.Now.Month != 11)
                return;
                        
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form_Main());            
        }
    }
}
