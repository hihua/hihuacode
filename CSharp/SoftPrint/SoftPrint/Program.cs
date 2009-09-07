using System;
using System.Collections.Generic;
using System.Windows.Forms;
using Microsoft.Win32;

namespace SoftPrint
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {
            bool IsInstall = false;

            RegistryKey SoftwareKey = Registry.LocalMachine.OpenSubKey("Software");
            if (SoftwareKey != null)
            {
                RegistryKey SoftPrintKey = SoftwareKey.OpenSubKey("SoftPrint");
                if (SoftPrintKey != null)
                {
                    foreach (string SoftPrintName in SoftPrintKey.GetValueNames())
                    {
                        if (SoftPrintName == "IsInstall")
                        {
                            if (SoftPrintKey.GetValue(SoftPrintName) != null)
                            {
                                if (SoftPrintKey.GetValue(SoftPrintName).ToString() == "1")
                                    IsInstall = true;
                            }
                        }
                    }

                    SoftPrintKey.Close();
                }

                SoftwareKey.Close();
            }

            if (IsInstall == false)
            {
                MessageBox.Show("请先安装本软件", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                Application.ExitThread();
                return;
            }

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainFrame());
        }
    }
}
