using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Printing;
using System.Text;
using System.IO;
using System.Windows.Forms;
using System.Reflection;

namespace SoftPrint
{
    public partial class MainFrame : Form
    {
        private FontDialog FontControl = null;
        private ColorDialog ColorControl = null;

        private PrintDialog PrintDialogControl = null;
        private PrintDocument PrintDocumentControl = null;
        private PrintPreviewDialog PrintPreviewDialogControl = null;

        private ArrayList BodyList = null;
        private ArrayList FontColorList = null;

        private FontColorClass FontColorTitleObj = null;
        private FontColorClass FontColorDeadManObj = null;
        private FontColorClass FontColorTailObj = null;

        private FontColorClass FontColorFObj = null;
        private FontColorClass FontColorTObj = null;

        private FontColorClass FontColorDSObj1 = null;
        private FontColorClass FontColorDSObj2 = null;

        private FontColorClass FontColorSequenceObj = null;
        private FontColorClass FontColorDefault = null;

        private float SequenceX = 10;
        private float SequenceY = 10;

        private float Spacing1 = 5;
        private float Spacing2 = 5;

        private bool IsHead = true;
        private int CurrentPage = 0;
        private int PrintSelect = -1;
        private ArrayList PrintList = null;

        public MainFrame()
        {
            InitializeComponent();

            FontControl = new FontDialog();
            ColorControl = new ColorDialog();

            DeadSexComBoBox.SelectedIndex = 0;
            DeadManTimeText.Text = DateTime.Now.ToShortDateString();

            PrintDialogControl = new PrintDialog();
            PrintDocumentControl = new PrintDocument();
            PrintDocumentControl.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.PrintDocumentControl_PrintPage);
            PrintPreviewDialogControl = new PrintPreviewDialog();

            FontControl.Font = new Font("隶书", (float)40, FontStyle.Bold);
            ColorControl.Color = Color.Black;

            FontColorTitleObj = new FontColorClass();
            FontColorTitleObj.FontObj = new Font("隶书", (float)48, FontStyle.Bold);
            FontColorTitleObj.ColorObj = Color.Black;

            FontColorDeadManObj = new FontColorClass();
            FontColorDeadManObj.FontObj = FontControl.Font;
            FontColorDeadManObj.ColorObj = ColorControl.Color;

            BodyList = new ArrayList();
            FontColorList = new ArrayList();
            PrintList = new ArrayList();

            FontColorTailObj = new FontColorClass();
            FontColorTailObj.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
            FontColorTailObj.ColorObj = Color.Black;

            FontColorFObj = new FontColorClass();
            FontColorFObj.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
            FontColorFObj.ColorObj = Color.Black;

            FontColorTObj = new FontColorClass();
            FontColorTObj.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
            FontColorTObj.ColorObj = Color.Black;

            FontColorDSObj1 = new FontColorClass();
            FontColorDSObj1.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
            FontColorDSObj1.ColorObj = Color.Black;

            FontColorDSObj2 = new FontColorClass();
            FontColorDSObj2.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
            FontColorDSObj2.ColorObj = Color.Black;

            FontColorSequenceObj = new FontColorClass();
            FontColorSequenceObj.FontObj = new Font("宋体", (float)10, FontStyle.Regular);
            FontColorSequenceObj.ColorObj = Color.Black;

            SetFontColor(TitleButton, FontColorTitleObj.FontObj, FontColorTitleObj.ColorObj);
            SetFontColor(DeadManButton, FontColorDeadManObj.FontObj, FontColorDeadManObj.ColorObj);
            SetFontColor(TailButton, FontColorTailObj.FontObj, FontColorTailObj.ColorObj);
            SetFontColor(DeadManFButton, FontColorFObj.FontObj, FontColorFObj.ColorObj);
            SetFontColor(DeadManTButton, FontColorTObj.FontObj, FontColorTObj.ColorObj);
            SetFontColor(Button_SetDSFont1, FontColorDSObj1.FontObj, FontColorDSObj1.ColorObj);
            SetFontColor(Button_SetDSFont2, FontColorDSObj2.FontObj, FontColorDSObj2.ColorObj);
            SetFontColor(Button_SequenceFontColor, FontColorSequenceObj.FontObj, FontColorSequenceObj.ColorObj);

            TopSizeText1.Text = "100";
            TopSizeText2.Text = "100";
            Text_NameSize.Text = "10";
            Name12SizeText.Text = "20";
            Text_SequenceX.Text = SequenceX.ToString();
            Text_SequenceY.Text = SequenceY.ToString();
            Text_Spacing1.Text = Spacing1.ToString();
            Text_Spacing2.Text = Spacing2.ToString();

            if (File.Exists(Application.StartupPath + "\\" + "default.txt"))
                LoadConfigFile(Application.StartupPath + "\\" + "default.txt");
        }

        private void MainFrame_FormClosing(object sender, FormClosingEventArgs e)
        {
            SaveConfigFile(Application.StartupPath + "\\" + "default.txt");
        }

        private void SetFontColor(Button SetButton, Font FontClass, Color ColorClass)
        {
            SetButton.Text = FontControl.Font.Name;

            if (FontClass.Style == FontStyle.Bold)
            {
                SetButton.Text += " " + " | " + "加粗";
            }

            if (FontClass.Style == FontStyle.Italic)
            {
                SetButton.Text += " " + " | " + "倾斜";
            }

            if (FontClass.Style == FontStyle.Regular)
            {
                SetButton.Text += " " + " | " + "普通";
            }

            if (FontClass.Style == FontStyle.Strikeout)
            {
                SetButton.Text += " " + " | " + "有直线";
            }

            if (FontClass.Style == FontStyle.Underline)
            {
                SetButton.Text += " " + " | " + "带下划线";
            }

            SetButton.Text += " " + " | " + FontClass.Size.ToString();
            SetButton.ForeColor = ColorClass;
        }

        private string GetFont(Font FontClass)
        {
            string FontClassName = FontClass.Name;
            string FontClassStyle = "普通";

            if (FontClass.Style == FontStyle.Bold)
            {
                FontClassStyle = "加粗";
            }

            if (FontClass.Style == FontStyle.Italic)
            {
                FontClassStyle = "倾斜";
            }

            if (FontClass.Style == FontStyle.Regular)
            {
                FontClassStyle = "普通";
            }

            if (FontClass.Style == FontStyle.Strikeout)
            {
                FontClassStyle = "有直线";
            }

            if (FontClass.Style == FontStyle.Underline)
            {
                FontClassStyle = "带下划线";
            }

            string FontClassSize = FontClass.Size.ToString();
            return FontClassName + ";" + FontClassStyle + ";" + FontClassSize;
        }

        private Font SetFont(string FontClassName)
        {
            Font FontClass = null;

            try
            {
                if (FontClassName.IndexOf(";") > 0)
                {
                    string[] FontClassString = FontClassName.Split(new string[] { ";" }, StringSplitOptions.RemoveEmptyEntries);
                    if (FontClassString.Length >= 3 && (IsFloatNumber(FontClassString[2]) || IsIntegerNumber(FontClassString[2])))
                    {
                        if (FontClassString[1] == "加粗")
                        {
                            FontClass = new Font(FontClassString[0], Convert.ToSingle(FontClassString[2]), FontStyle.Bold);
                        }

                        if (FontClassString[1] == "倾斜")
                        {
                            FontClass = new Font(FontClassString[0], Convert.ToSingle(FontClassString[2]), FontStyle.Italic);
                        }

                        if (FontClassString[1] == "普通")
                        {
                            FontClass = new Font(FontClassString[0], Convert.ToSingle(FontClassString[2]), FontStyle.Regular);
                        }

                        if (FontClassString[1] == "有直线")
                        {
                            FontClass = new Font(FontClassString[0], Convert.ToSingle(FontClassString[2]), FontStyle.Strikeout);
                        }

                        if (FontClassString[1] == "带下划线")
                        {
                            FontClass = new Font(FontClassString[0], Convert.ToSingle(FontClassString[2]), FontStyle.Underline);
                        }
                    }
                }

                return FontClass;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "设置字体错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return FontClass;
            }
        }

        private string StringToRow(string Content)
        {
            string NameContent = "";
            if (String.IsNullOrEmpty(Content) == false)
            {
                //Content = Content.Replace("\r", "");
                //Content = Content.Replace("\n", "");

                for (int i = 0; i < Content.Length; i++)
                {
                    NameContent += Content.Substring(i, 1) + "\n";
                }
            }

            return NameContent;
        }

        private string GridViewCellToString(DataGridViewCell GridViewCellObj)
        {
            if (GridViewCellObj.Value == null || String.IsNullOrEmpty(GridViewCellObj.Value.ToString().Trim()))
                return "";
            else
                return GridViewCellObj.Value.ToString().Trim();
        }

        private void GridViewSetFont(int Rows, int Cells, Font FontClass)
        {
            if (GridViewControl.RowCount > 0 && GridViewControl.ColumnCount > 0)
            {
                if (GridViewControl.RowCount - 1 >= Rows && GridViewControl.ColumnCount - 1 >= Cells)
                {
                    string FontColor = "";
                    FontColor = FontClass.Name;

                    if (FontClass.Style == FontStyle.Bold)
                    {
                        FontColor += " " + " | " + "加粗";
                    }

                    if (FontClass.Style == FontStyle.Italic)
                    {
                        FontColor += " " + " | " + "倾斜";
                    }

                    if (FontClass.Style == FontStyle.Regular)
                    {
                        FontColor += " " + " | " + "普通";
                    }

                    if (FontClass.Style == FontStyle.Strikeout)
                    {
                        FontColor += " " + " | " + "有直线";
                    }

                    if (FontClass.Style == FontStyle.Underline)
                    {
                        FontColor += " " + " | " + "带下划线";
                    }

                    FontColor += " " + " | " + FontClass.Size.ToString();

                    //GridViewControl[GridViewControl.ColumnCount - 1, e.RowIndex].Value = FontColor;
                    GridViewControl.Rows[Rows].Cells[Cells].Value = FontColor;
                }
            }
        }

        private string ExcelCellToString(Excel.Range RangeObj)
        {
            if (RangeObj.Value2 == null || String.IsNullOrEmpty(RangeObj.Value2.ToString().Trim()))
                return "";
            else
                return RangeObj.Value2.ToString().Trim();
        }

        private string ExcelCellToDate(Excel.Range RangeObj)
        {
            if (RangeObj.Value2 == null || String.IsNullOrEmpty(RangeObj.Value2.ToString().Trim()))
                return "";
            else
                return RangeObj.Text.ToString().Trim();
        }

        private void StringToExcelCell(Excel.Worksheet WorksheetObj, string RowCell, string Content, int LCR)
        {
            Excel.Range ExcelRange = WorksheetObj.get_Range(RowCell, Missing.Value);
            ExcelRange.Value2 = Content;
            ExcelRange.ColumnWidth = 15;

            if (LCR == 1)
                ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignLeft;
            else
            {
                if (LCR == 2)
                    ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignCenter;
                else
                    ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignRight;
            }
        }

        private void StringToExcelCell(Excel.Worksheet WorksheetObj, int Rows, int Cells, string Content, int LCR)
        {
            Excel.Range ExcelRange = (Excel.Range)WorksheetObj.Cells[Rows, Cells];
            ExcelRange.Value2 = Content;
            ExcelRange.ColumnWidth = 15;

            if (LCR == 1)
                ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignLeft;
            else
            {
                if (LCR == 2)
                    ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignCenter;
                else
                    ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignRight;
            }
        }

        private bool IsFloatNumber(string StringNumber)
        {
            try
            {
                float FloatNumber = Convert.ToSingle(StringNumber);
                return true;
            }
            catch (FormatException ex)
            {
                return false;
            }
            catch (OverflowException ex)
            {
                return false;
            }
        }

        private bool IsIntegerNumber(string StringNumber)
        {
            try
            {
                int IntegerNumber = Convert.ToInt32(StringNumber);

                return true;
            }
            catch (FormatException ex)
            {
                return false;
            }
            catch (OverflowException ex)
            {
                return false;
            }
        }

        private float SumCellPrice(int CellNumber)
        {
            float Total = 0;
            for (int i = 0; i < GridViewControl.RowCount - 1; i++)
            {
                string PriceStr = GridViewCellToString(GridViewControl.Rows[i].Cells[CellNumber]);

                if (IsFloatNumber(PriceStr))
                    Total += Convert.ToSingle(PriceStr);
            }

            return Total;
        }

        private bool InToExcel(string ExcelFile)
        {
            Excel.Application ExcelObj = null;

            try
            {
                ExcelObj = new Excel.ApplicationClass();
                ExcelObj.Visible = false;
                Excel.Workbook ExcelBook = ExcelObj.Workbooks._Open(ExcelFile, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value);
                Excel.Worksheet ExcelSheet = (Excel.Worksheet)ExcelBook.Sheets[1];

                //死者                
                DeadManText.Text = ExcelCellToString(ExcelSheet.get_Range("B1", Type.Missing));

                //性别
                if (ExcelCellToString(ExcelSheet.get_Range("G1", Type.Missing)) == "男")
                    DeadSexComBoBox.SelectedIndex = 0;
                else
                    DeadSexComBoBox.SelectedIndex = 1;

                //出殡时间
                DeadManTimeText.Text = ExcelCellToDate(ExcelSheet.get_Range("B2", Type.Missing));

                //告别厅
                DeadManAddressText.Text = ExcelCellToString(ExcelSheet.get_Range("F2", Type.Missing));

                //列表用户   
                if (GridViewControl.RowCount > 1)
                    GridViewControl.Rows.Clear();

                int i = 4;
                int j = 0;
                while (true)
                {
                    string Cell1 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 1]);
                    if (String.IsNullOrEmpty(Cell1))
                    {
                        break;
                    }
                    else
                    {
                        GridViewControl.Rows.Add();
                        GridViewControl.Rows[j].Cells[0].Value = Cell1;

                        //称谓(前)
                        string Cell2 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 2]);
                        GridViewControl.Rows[j].Cells[1].Value = Cell2;

                        //称谓(后)
                        string Cell3 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 3]);
                        GridViewControl.Rows[j].Cells[2].Value = Cell3;

                        //姓名1
                        string Cell4 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 4]);
                        GridViewControl.Rows[j].Cells[3].Value = Cell4;

                        //姓名2
                        string Cell5 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 5]);
                        GridViewControl.Rows[j].Cells[4].Value = Cell5;

                        //项目
                        string Cell6 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 6]);
                        GridViewControl.Rows[j].Cells[5].Value = Cell6;

                        //价钱
                        string Cell7 = ExcelCellToString((Excel.Range)ExcelSheet.Cells[i, 7]);
                        GridViewControl.Rows[j].Cells[6].Value = Cell7;

                        FontColorClass FontColorObj = new FontColorClass();
                        if (FontColorDefault == null)
                        {
                            FontColorObj.FontObj = new Font("隶书", (float)40, FontStyle.Bold);
                            FontColorObj.ColorObj = Color.Black;
                        }
                        else
                        {
                            FontColorObj = FontColorDefault;
                        }

                        FontColorList.Add(FontColorObj);

                        GridViewSetFont(j, GridViewControl.ColumnCount - 1, FontColorObj.FontObj);

                        i++;
                        j++;
                    }
                }

                ExcelSheet = null;
                ExcelBook.Close(false, false, false);
                ExcelBook = null;

                ExcelObj.Quit();
                ExcelObj = null;

                //合计
                TotalPriceText.Text = SumCellPrice(6).ToString();

                return true;
            }
            catch (Exception ex)
            {
                ExcelObj.Quit();
                ExcelObj = null;

                MessageBox.Show(ex.Message, "导入错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        private bool OutToExcel(string ExcelFile)
        {
            Excel.Application ExcelObj = null;

            try
            {
                ExcelObj = new Excel.ApplicationClass();
                ExcelObj.Visible = false;

                Excel.Workbook ExcelBook = ExcelObj.Workbooks.Add(Missing.Value);
                Excel.Worksheet ExcelSheet = (Excel.Worksheet)ExcelBook.Sheets[1];

                //死者
                StringToExcelCell(ExcelSheet, "A1", "死者：", 3);
                StringToExcelCell(ExcelSheet, "B1", DeadManText.Text, 1);

                //性别
                StringToExcelCell(ExcelSheet, "F1", "性别：", 3);
                StringToExcelCell(ExcelSheet, "G1", DeadSexComBoBox.Text, 1);

                //出殡日期
                StringToExcelCell(ExcelSheet, "A2", "出殡日期：", 3);
                StringToExcelCell(ExcelSheet, "B2", DeadManTimeText.Text, 1);

                //告别厅
                StringToExcelCell(ExcelSheet, "G2", "告别厅", 1);
                StringToExcelCell(ExcelSheet, "F2", DeadManAddressText.Text, 3);

                //列表标题
                StringToExcelCell(ExcelSheet, "A3", "序号", 2);
                StringToExcelCell(ExcelSheet, "B3", "称 谓（前）", 2);
                StringToExcelCell(ExcelSheet, "C3", "称 谓（后）", 2);
                StringToExcelCell(ExcelSheet, "D3", "姓名1", 2);
                StringToExcelCell(ExcelSheet, "E3", "姓名2", 2);
                StringToExcelCell(ExcelSheet, "F3", "项目", 2);
                StringToExcelCell(ExcelSheet, "G3", "价格", 2);

                //列表数据
                int j = 4;
                for (int i = 0; i < GridViewControl.RowCount - 1; i++)
                {
                    StringToExcelCell(ExcelSheet, j, 1, Convert.ToString(i + 1), 2);
                    StringToExcelCell(ExcelSheet, j, 2, GridViewCellToString(GridViewControl.Rows[i].Cells[1]), 2);
                    StringToExcelCell(ExcelSheet, j, 3, GridViewCellToString(GridViewControl.Rows[i].Cells[2]), 2);
                    StringToExcelCell(ExcelSheet, j, 4, GridViewCellToString(GridViewControl.Rows[i].Cells[3]), 2);
                    StringToExcelCell(ExcelSheet, j, 5, GridViewCellToString(GridViewControl.Rows[i].Cells[4]), 2);
                    StringToExcelCell(ExcelSheet, j, 6, GridViewCellToString(GridViewControl.Rows[i].Cells[5]), 2);
                    StringToExcelCell(ExcelSheet, j, 7, GridViewCellToString(GridViewControl.Rows[i].Cells[6]), 2);

                    j++;
                }

                //合计
                StringToExcelCell(ExcelSheet, j, 6, "合计：", 3);
                StringToExcelCell(ExcelSheet, j, 7, TotalPriceText.Text, 2);

                //保存
                ExcelBook.SaveAs(ExcelFile, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Excel.XlSaveAsAccessMode.xlNoChange, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value);

                ExcelSheet = null;
                ExcelBook.Close(false, false, false);
                ExcelBook = null;

                ExcelObj.Quit();
                ExcelObj = null;

                return true;
            }
            catch (Exception ex)
            {
                ExcelObj.Quit();
                ExcelObj = null;

                MessageBox.Show(ex.Message, "导出错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        private void OutToHtml()
        {
            float TotalPrice = 0;

            FolderBrowserDialog Directory = new FolderBrowserDialog();
            Directory.SelectedPath = Application.StartupPath;
            Directory.ShowNewFolderButton = true;
            Directory.ShowDialog();

            StreamWriter sw = new StreamWriter(Directory.SelectedPath + "\\" + DeadManText.Text + "_" + DateTime.Now.ToShortDateString() + ".html", false, System.Text.Encoding.GetEncoding("GBK"));

            sw.WriteLine("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            sw.WriteLine("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            sw.WriteLine("<head>");
            sw.WriteLine("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\" />");
            sw.WriteLine("<title>" + DeadManText.Text + "</title>");
            sw.WriteLine("</head>");

            sw.WriteLine("<body>");
            sw.WriteLine("<table width=\"80%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            sw.WriteLine("<tr>");
            sw.WriteLine("<td width=\"9%\" align=\"right\">死者：</td>");
            sw.WriteLine("<td width=\"41%\">" + DeadManText.Text + "</td>");
            sw.WriteLine("<td width=\"43%\" align=\"right\">性别：</td>");
            sw.WriteLine("<td width=\"7%\">" + DeadSexComBoBox.Text + "</td>");
            sw.WriteLine("</tr>");
            sw.WriteLine("<tr>");
            sw.WriteLine("<td align=\"right\">出殡日期：</td>");
            sw.WriteLine("<td>" + DeadManTimeText.Text + "</td>");
            sw.WriteLine("<td align=\"right\">" + DeadManAddressText.Text + "</td>");
            sw.WriteLine("<td>&nbsp;&nbsp;告别厅</td>");
            sw.WriteLine("</tr>");
            sw.WriteLine("</table>");

            sw.WriteLine("<table width=\"80%\" border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#000000\">");
            sw.WriteLine("<tr align=\"center\" height=\"25\">");
            sw.WriteLine("<td width=\"6%\">序号</td>");
            sw.WriteLine("<td width=\"15%\">称谓(前)</td>");
            sw.WriteLine("<td width=\"15%\">称谓(后)</td>");
            sw.WriteLine("<td width=\"19%\">姓名</td>");
            sw.WriteLine("<td width=\"17%\">选项</td>");
            sw.WriteLine("<td width=\"18%\">项目</td>");
            sw.WriteLine("<td width=\"10%\">价格</td>");
            sw.WriteLine("</tr>");

            for (int i = 0; i < GridViewControl.RowCount - 1; i++)
            {
                string CellString = "";

                sw.WriteLine("<tr align=\"center\" height=\"25\">");
                sw.WriteLine("<td>" + GridViewControl.Rows[i].Cells[0].Value.ToString() + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[1]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                    sw.WriteLine("<td>" + CellString + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[2]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                    sw.WriteLine("<td>" + CellString + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[3]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                    sw.WriteLine("<td>" + CellString + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[4]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                    sw.WriteLine("<td>" + CellString + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[5]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                    sw.WriteLine("<td>" + CellString + "</td>");

                CellString = GridViewCellToString(GridViewControl.Rows[i].Cells[6]);
                if (String.IsNullOrEmpty(CellString))
                    sw.WriteLine("<td>&nbsp;</td>");
                else
                {
                    sw.WriteLine("<td>" + CellString + "</td>");
                    TotalPrice += Convert.ToSingle(CellString);
                }

                sw.WriteLine("</tr>");
            }

            sw.WriteLine("</table>");

            sw.WriteLine("<table width=\"80%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            sw.WriteLine("<tr height=\"25\">");
            sw.WriteLine("<td width=\"6%\">&nbsp;</td>");
            sw.WriteLine("<td width=\"15%\">&nbsp;</td>");
            sw.WriteLine("<td width=\"15%\">&nbsp;</td>");
            sw.WriteLine("<td width=\"19%\">&nbsp;</td>");
            sw.WriteLine("<td width=\"17%\">&nbsp;</td>");
            sw.WriteLine("<td width=\"18%\" align=\"right\">合计：</td>");
            sw.WriteLine("<td width=\"10%\" align=\"center\">" + TotalPrice.ToString() + "</td>");
            sw.WriteLine("</tr>");
            sw.WriteLine("</table>");

            sw.Close();
            ClearAll();

            MessageBox.Show("导出" + DeadManText.Text + "_" + DateTime.Now.ToShortDateString() + ".html文件成功");
        }

        private bool CheckAll()
        {
            if (String.IsNullOrEmpty(TitleText.Text))
            {
                MessageBox.Show("请输入标题", "请输入标题", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            for (int i = 0; i < GridViewControl.RowCount - 1; i++)
            {
                string[] Name = new string[4];
                Name[0] = GridViewCellToString(GridViewControl.Rows[i].Cells[1]);
                Name[1] = GridViewCellToString(GridViewControl.Rows[i].Cells[2]);
                Name[2] = GridViewCellToString(GridViewControl.Rows[i].Cells[3]);
                Name[3] = GridViewCellToString(GridViewControl.Rows[i].Cells[4]);

                FontColorClass FontColorObj = (FontColorClass)FontColorList[i];
                BodyClass BodyObj = new BodyClass();
                BodyObj.Sequence = GridViewCellToString(GridViewControl.Rows[i].Cells[0]);
                BodyObj.Remarks = GridViewCellToString(GridViewControl.Rows[i].Cells[5]);
                BodyObj.Content = Name;
                BodyObj.FontColorObj = FontColorObj;
                BodyList.Add(BodyObj);
            }

            if (String.IsNullOrEmpty(TailText.Text))
            {
                MessageBox.Show("请输入尾部标题", "请输入尾部标题", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            if (IsFloatNumber(TopSizeText1.Text) == false)
            {
                TopSizeText1.Focus();

                MessageBox.Show("错误，挽联1上边距请输入正确的数字", "错误，挽联1上边距请输入正确的数字", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            if (IsIntegerNumber(TopSizeText2.Text) == false)
            {
                TopSizeText2.Focus();

                MessageBox.Show("错误，挽联2上边距请输入正确的数字", "错误，挽联2上边距请输入正确的数字", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            if (IsIntegerNumber(Name12SizeText.Text) == false)
            {
                Name12SizeText.Focus();

                MessageBox.Show("错误，姓名1,2边距请输入正确的数字", "错误，姓名1,2边距请输入正确的数字", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            if (IsFloatNumber(Text_SequenceX.Text))
                SequenceX = Convert.ToSingle(Text_SequenceX.Text);

            if (IsFloatNumber(Text_SequenceY.Text))
                SequenceY = Convert.ToSingle(Text_SequenceY.Text);

            if (IsFloatNumber(Text_Spacing1.Text))
                Spacing1 = Convert.ToSingle(Text_Spacing1.Text);

            if (IsFloatNumber(Text_Spacing2.Text))
                Spacing2 = Convert.ToSingle(Text_Spacing2.Text);

            return true;
        }

        private void ClearAll()
        {
            BodyList.Clear();
            PrintList.Clear();
            PrintSelect = -1;
            CurrentPage = 0;
            IsHead = true;
        }

        private void TitleButton_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorTitleObj.FontObj;
            ColorControl.Color = FontColorTitleObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorTitleObj.FontObj = FontControl.Font;
            FontColorTitleObj.ColorObj = ColorControl.Color;

            SetFontColor(TitleButton, FontControl.Font, ColorControl.Color);
        }

        private void DeadManButton_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorDeadManObj.FontObj;
            ColorControl.Color = FontColorDeadManObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorDeadManObj.FontObj = FontControl.Font;
            FontColorDeadManObj.ColorObj = ColorControl.Color;

            SetFontColor(DeadManButton, FontControl.Font, ColorControl.Color);
        }

        private void TailButton_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorTailObj.FontObj;
            ColorControl.Color = FontColorTailObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorTailObj.FontObj = FontControl.Font;
            FontColorTailObj.ColorObj = ColorControl.Color;

            SetFontColor(TailButton, FontControl.Font, ColorControl.Color);
        }

        private void DeadManFButton_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorFObj.FontObj;
            ColorControl.Color = FontColorFObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorFObj.FontObj = FontControl.Font;
            FontColorFObj.ColorObj = ColorControl.Color;

            SetFontColor(DeadManFButton, FontColorFObj.FontObj, FontColorFObj.ColorObj);
        }

        private void DeadManTButton_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorTObj.FontObj;
            ColorControl.Color = FontColorTObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorTObj.FontObj = FontControl.Font;
            FontColorTObj.ColorObj = ColorControl.Color;

            SetFontColor(DeadManTButton, FontColorTObj.FontObj, FontColorTObj.ColorObj);
        }

        private void Button_SetDSFont1_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorDSObj1.FontObj;
            ColorControl.Color = FontColorDSObj1.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorDSObj1.FontObj = FontControl.Font;
            FontColorDSObj1.ColorObj = ColorControl.Color;

            SetFontColor(Button_SetDSFont1, FontColorDSObj1.FontObj, FontColorDSObj1.ColorObj);
        }

        private void Button_SetDSFont2_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorDSObj2.FontObj;
            ColorControl.Color = FontColorDSObj2.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorDSObj2.FontObj = FontControl.Font;
            FontColorDSObj2.ColorObj = ColorControl.Color;

            SetFontColor(Button_SetDSFont2, FontColorDSObj2.FontObj, FontColorDSObj2.ColorObj);
        }

        private void Button_SequenceFontColor_Click(object sender, EventArgs e)
        {
            FontControl.Font = FontColorSequenceObj.FontObj;
            ColorControl.Color = FontColorSequenceObj.ColorObj;

            FontControl.ShowDialog();
            ColorControl.ShowDialog();

            FontColorSequenceObj.FontObj = FontControl.Font;
            FontColorSequenceObj.ColorObj = ColorControl.Color;

            SetFontColor(Button_SequenceFontColor, FontColorSequenceObj.FontObj, FontColorSequenceObj.ColorObj);
        }

        private bool LoadConfigFile(string FileName)
        {
            StreamReader SRObj = new StreamReader(FileName, System.Text.Encoding.GetEncoding("GBK"));
            try
            {
                if (SRObj.EndOfStream == false)
                {
                    //悼念
                    string FontClass = SRObj.ReadLine();

                    if (String.IsNullOrEmpty(FontClass) == false && FontColorTitleObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorTitleObj.FontObj = SetFont(FontClass);
                            SetFontColor(TitleButton, FontColorTitleObj.FontObj, FontColorTitleObj.ColorObj);
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    //LIST字体
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorDefault = new FontColorClass();
                            FontColorDefault.FontObj = SetFont(FontClass);
                            FontColorDefault.ColorObj = Color.Black;
                        }

                        if (FontColorList != null && FontColorList.Count > 0)
                        {
                            for (int i = 0; i < FontColorList.Count; i++)
                            {
                                if (FontColorList[i] != null)
                                {
                                    if (((FontColorClass)FontColorList[i]) != null)
                                    {
                                        if (FontColorDefault != null)
                                        {
                                            FontColorList[i] = FontColorDefault;
                                            GridViewSetFont(i, GridViewControl.ColumnCount - 1, ((FontColorClass)FontColorList[i]).FontObj);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    //敬挽
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorTailObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorTailObj.FontObj = SetFont(FontClass);
                            SetFontColor(TailButton, FontColorTailObj.FontObj, FontColorTailObj.ColorObj);
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    //死者
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorDeadManObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorDeadManObj.FontObj = SetFont(FontClass);
                            SetFontColor(DeadManButton, FontColorDeadManObj.FontObj, FontColorDeadManObj.ColorObj);
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    //称谓前
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorFObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorFObj.FontObj = SetFont(FontClass);
                            SetFontColor(DeadManFButton, FontColorFObj.FontObj, FontColorFObj.ColorObj);
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    //称谓后
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorTObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorTObj.FontObj = SetFont(FontClass);
                            SetFontColor(DeadManTButton, FontColorTObj.FontObj, FontColorTObj.ColorObj);
                        }
                    }
                }

                //边距
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        TopSizeText1.Text = FontClass;
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        TopSizeText2.Text = FontClass;
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Name12SizeText.Text = FontClass;
                    }
                }

                //姓名之间距离
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Text_NameSize.Text = FontClass;
                    }
                }

                //单,双行字体
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorDSObj1.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorDSObj1.FontObj = SetFont(FontClass);
                            SetFontColor(Button_SetDSFont1, FontColorDSObj1.FontObj, FontColorDSObj1.ColorObj);
                        }
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorDSObj2.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorDSObj2.FontObj = SetFont(FontClass);
                            SetFontColor(Button_SetDSFont2, FontColorDSObj2.FontObj, FontColorDSObj2.ColorObj);
                        }
                    }
                }

                //序号X,Y 字体
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Text_SequenceX.Text = FontClass;
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Text_SequenceY.Text = FontClass;
                    }
                }

                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && FontColorSequenceObj.FontObj != null)
                    {
                        if (SetFont(FontClass) != null)
                        {
                            FontColorSequenceObj.FontObj = SetFont(FontClass);
                            SetFontColor(Button_SequenceFontColor, FontColorSequenceObj.FontObj, FontColorSequenceObj.ColorObj);
                        }
                    }
                }

                //行距(单)
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Text_Spacing1.Text = FontClass;
                    }
                }

                //行距(双)
                if (SRObj.EndOfStream == false)
                {
                    string FontClass = SRObj.ReadLine();
                    if (String.IsNullOrEmpty(FontClass) == false && (IsFloatNumber(FontClass) || IsIntegerNumber(FontClass)))
                    {
                        Text_Spacing2.Text = FontClass;
                    }
                }

                SRObj.Close();
                return true;
            }
            catch (Exception ex)
            {
                SRObj.Close();
                MessageBox.Show(ex.Message, "载入设置错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        private void LoadConfig_Click(object sender, EventArgs e)
        {
            OpenFileDialog OpenFileObj = new OpenFileDialog();
            OpenFileObj.InitialDirectory = Application.StartupPath;
            OpenFileObj.DefaultExt = "txt";
            OpenFileObj.Filter = "TXT文件 (*.txt)|*.txt";
            OpenFileObj.Multiselect = false;
            DialogResult Result = OpenFileObj.ShowDialog();

            if (Result == DialogResult.OK || Result == DialogResult.Yes)
            {
                if (LoadConfigFile(OpenFileObj.FileName))
                    MessageBox.Show("载入设置成功");               
            }
        }

        private bool SaveConfigFile(string FileName)
        {
            StreamWriter SWObj = new StreamWriter(FileName, false, System.Text.Encoding.GetEncoding("GBK"));

            try
            {
                string FontClass = "";

                //悼念
                if (FontColorTitleObj.FontObj != null)
                    FontClass += GetFont(FontColorTitleObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //LIST字体
                if (FontColorList != null && FontColorList.Count > 0)
                {
                    if (FontColorList[0] != null)
                    {
                        if (((FontColorClass)FontColorList[0]) != null)
                            FontClass += GetFont(((FontColorClass)FontColorList[0]).FontObj) + "\r\n";
                        else
                        {
                            if (FontColorDefault != null)
                                FontClass += GetFont(FontColorDefault.FontObj) + "\r\n";
                            else
                                FontClass += "\r\n";
                        }
                    }
                    else
                    {
                        if (FontColorDefault != null)
                            FontClass += GetFont(FontColorDefault.FontObj) + "\r\n";
                        else
                            FontClass += "\r\n";
                    }
                }
                else
                {
                    if (FontColorDefault != null)
                        FontClass += GetFont(FontColorDefault.FontObj) + "\r\n";
                    else
                        FontClass += "\r\n";
                }

                //敬挽
                if (FontColorTailObj.FontObj != null)
                    FontClass += GetFont(FontColorTailObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //死者
                if (FontColorDeadManObj.FontObj != null)
                    FontClass += GetFont(FontColorDeadManObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //称谓前
                if (FontColorFObj.FontObj != null)
                    FontClass += GetFont(FontColorFObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //称谓后
                if (FontColorTObj.FontObj != null)
                    FontClass += GetFont(FontColorTObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //边距
                if (String.IsNullOrEmpty(TopSizeText1.Text) == false && (IsFloatNumber(TopSizeText1.Text) || IsIntegerNumber(TopSizeText1.Text)))
                    FontClass += TopSizeText1.Text + "\r\n";
                else
                    FontClass += "\r\n";

                if (String.IsNullOrEmpty(TopSizeText2.Text) == false && (IsFloatNumber(TopSizeText2.Text) || IsIntegerNumber(TopSizeText2.Text)))
                    FontClass += TopSizeText2.Text + "\r\n";
                else
                    FontClass += "\r\n";

                if (String.IsNullOrEmpty(Name12SizeText.Text) == false && (IsFloatNumber(Name12SizeText.Text) || IsIntegerNumber(Name12SizeText.Text)))
                    FontClass += Name12SizeText.Text + "\r\n";
                else
                    FontClass += "\r\n";

                //姓名之间距离
                if (String.IsNullOrEmpty(Text_NameSize.Text) == false && (IsFloatNumber(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text)))
                    FontClass += Text_NameSize.Text + "\r\n";
                else
                    FontClass += "\r\n";

                //单,双行字体
                if (FontColorDSObj1.FontObj != null)
                    FontClass += GetFont(FontColorDSObj1.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                if (FontColorDSObj2.FontObj != null)
                    FontClass += GetFont(FontColorDSObj2.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //序号X,Y,字体
                if (String.IsNullOrEmpty(Text_SequenceX.Text) == false && (IsFloatNumber(Text_SequenceX.Text) || IsIntegerNumber(Text_SequenceX.Text)))
                    FontClass += Text_SequenceX.Text + "\r\n";
                else
                    FontClass += "\r\n";

                if (String.IsNullOrEmpty(Text_SequenceY.Text) == false && (IsFloatNumber(Text_SequenceY.Text) || IsIntegerNumber(Text_SequenceY.Text)))
                    FontClass += Text_SequenceY.Text + "\r\n";
                else
                    FontClass += "\r\n";

                if (FontColorSequenceObj.FontObj != null)
                    FontClass += GetFont(FontColorSequenceObj.FontObj) + "\r\n";
                else
                    FontClass += "\r\n";

                //行距(单)
                if (String.IsNullOrEmpty(Text_Spacing1.Text) == false && (IsFloatNumber(Text_Spacing1.Text) || IsIntegerNumber(Text_Spacing1.Text)))
                    FontClass += Text_Spacing1.Text + "\r\n";
                else
                    FontClass += "\r\n";

                //行距(双)
                if (String.IsNullOrEmpty(Text_Spacing2.Text) == false && (IsFloatNumber(Text_Spacing2.Text) || IsIntegerNumber(Text_Spacing2.Text)))
                    FontClass += Text_Spacing2.Text + "\r\n";
                else
                    FontClass += "\r\n";
                                
                SWObj.Write(FontClass);
                SWObj.Close();
                return true;
            }
            catch (Exception ex)
            {
                SWObj.Close();
                MessageBox.Show(ex.Message, "保存设置错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        private void SaveConfig_Click(object sender, EventArgs e)
        {            
            SaveFileDialog SaveFileObj = new SaveFileDialog();
            SaveFileObj.InitialDirectory = Application.StartupPath;
            SaveFileObj.DefaultExt = "txt";
            SaveFileObj.Filter = "TXT文件 (*.txt)|*.txt";
            SaveFileObj.FileName = DeadManText.Text;
            DialogResult Result = SaveFileObj.ShowDialog();

            if (Result == DialogResult.OK || Result == DialogResult.Yes)
            {
                if (SaveConfigFile(SaveFileObj.FileName))
                    MessageBox.Show("保存设置成功");
            }
        }

        private void GirdViewDel_Click(object sender, EventArgs e)
        {
            if (GridViewControl.RowCount > 1)
            {
                if (GridViewControl.SelectedRows.Count > 0)
                {
                    if (GridViewControl.SelectedRows[0].Index < GridViewControl.RowCount - 1)
                    {
                        if (MessageBox.Show("是否删除选定行吗？", "是否删除选定行吗？", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                        {
                            while (GridViewControl.SelectedRows.Count > 0)
                            {
                                DataGridViewRow GridViewRow = GridViewControl.SelectedRows[0];
                                GridViewControl.Rows.Remove(GridViewRow);
                            }
                        }
                    }
                }
                else
                {
                    if (MessageBox.Show("是否删除最后一行吗？", "是否删除最后一行吗？", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                    {
                        GridViewControl.Rows.RemoveAt(GridViewControl.RowCount - 2);
                    }
                }

                TotalPriceText.Text = SumCellPrice(6).ToString();
            }
        }

        private void GridViewControl_CellBeginEdit(object sender, DataGridViewCellCancelEventArgs e)
        {
            if (GridViewControl.Rows[e.RowIndex].Cells[0].Value == null || String.IsNullOrEmpty(GridViewControl.Rows[e.RowIndex].Cells[0].Value.ToString()))
                GridViewControl.Rows[e.RowIndex].Cells[0].Value = Convert.ToString(e.RowIndex + 1);

            if (e.ColumnIndex == GridViewControl.ColumnCount - 1)
            {
                if (FontColorList.Count > e.RowIndex)
                {
                    FontControl.Font = ((FontColorClass)FontColorList[e.RowIndex]).FontObj;
                    ColorControl.Color = ((FontColorClass)FontColorList[e.RowIndex]).ColorObj;

                    FontControl.ShowDialog();
                    ColorControl.ShowDialog();

                    FontColorClass FontColorObj = new FontColorClass();
                    FontColorObj.FontObj = FontControl.Font;
                    FontColorObj.ColorObj = ColorControl.Color;

                    FontColorList[e.RowIndex] = FontColorObj;

                    GridViewSetFont(e.RowIndex, GridViewControl.ColumnCount - 1, FontColorObj.FontObj);
                    e.Cancel = true;
                }
                else
                    e.Cancel = true;
            }
        }

        private void GridViewControl_RowsRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            if (FontColorList.Count > e.RowIndex)
                FontColorList.RemoveAt(e.RowIndex);
        }

        private void GridViewControl_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == GridViewControl.ColumnCount - 2)
            {
                string PriceString = GridViewCellToString(GridViewControl[e.ColumnIndex, e.RowIndex]);
                if (String.IsNullOrEmpty(PriceString) == false)
                {
                    if (IsFloatNumber(PriceString))
                        TotalPriceText.Text = SumCellPrice(6).ToString();
                    else
                    {
                        MessageBox.Show("请输入正确的金额", "请输入正确的金额", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        GridViewControl[e.ColumnIndex, e.RowIndex].Value = "";

                        return;
                    }
                }
            }

            if (e.ColumnIndex != GridViewControl.ColumnCount - 1 && e.RowIndex == FontColorList.Count)
            {
                FontColorClass FontColorObj = new FontColorClass();

                if (FontColorDefault == null)
                {
                    FontColorObj.FontObj = FontControl.Font;
                    FontColorObj.ColorObj = ColorControl.Color;
                }
                else
                {
                    FontColorObj = FontColorDefault;
                }

                FontColorList.Add(FontColorObj);

                GridViewSetFont(e.RowIndex, GridViewControl.ColumnCount - 1, FontColorObj.FontObj);
            }
        }

        private void Print_Click(object sender, EventArgs e)
        {
            ClearAll();
            if (CheckAll() == false)
            {
                ClearAll();
                return;
            }

            try
            {
                PrintDialogControl.Document = PrintDocumentControl;
                PrintDialogControl.AllowCurrentPage = true;
                PrintDialogControl.AllowSelection = true;
                PrintDialogControl.AllowSomePages = true;
                PrintDialogControl.ShowHelp = true;
                PrintDialogControl.ShowNetwork = true;

                DialogResult Result = PrintDialogControl.ShowDialog();
                if (Result == DialogResult.OK || Result == DialogResult.Yes)
                    PrintDocumentControl.Print();
            }
            catch (Exception ex)
            {
                ClearAll();

                MessageBox.Show(ex.Message, "错误，可能没有安装打印机", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }

        private void PrintSelectButton_Click(object sender, EventArgs e)
        {
            ClearAll();
            if (GridViewControl.RowCount > 1)
            {
                if (GridViewControl.SelectedRows.Count > 0)
                {
                    if (CheckAll() == false)
                    {
                        ClearAll();
                        return;
                    }

                    for (int i = GridViewControl.SelectedRows.Count - 1; i >= 0; i--)
                    {
                        if (GridViewControl.SelectedRows[i].Index < GridViewControl.RowCount - 1)
                        {
                            PrintList.Add(GridViewControl.SelectedRows[i].Index);
                        }
                    }

                    try
                    {
                        PrintSelect = 0;
                        PrintDialogControl.Document = PrintDocumentControl;
                        PrintDialogControl.AllowCurrentPage = true;
                        PrintDialogControl.AllowSelection = true;
                        PrintDialogControl.AllowSomePages = true;
                        PrintDialogControl.ShowHelp = true;
                        PrintDialogControl.ShowNetwork = true;

                        DialogResult Result = PrintDialogControl.ShowDialog();
                        if (Result == DialogResult.OK || Result == DialogResult.Yes)
                            PrintDocumentControl.Print();
                    }
                    catch (Exception ex)
                    {
                        ClearAll();

                        MessageBox.Show(ex.Message, "错误，可能没有安装打印机", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }
            }
        }

        private void PrintPreView_Click(object sender, EventArgs e)
        {
            if (CheckAll() == false)
            {
                ClearAll();
                return;
            }

            try
            {
                PrintPreviewDialogControl.Document = PrintDocumentControl;
                PrintPreviewDialogControl.ShowIcon = true;
                PrintPreviewDialogControl.ShowDialog();
            }
            catch (Exception ex)
            {
                ClearAll();

                MessageBox.Show(ex.Message, "错误，可能没有安装打印机", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }

        private void PrintSelectPreView_Click(object sender, EventArgs e)
        {
            if (GridViewControl.RowCount > 1)
            {
                if (GridViewControl.SelectedRows.Count > 0)
                {
                    if (CheckAll() == false)
                    {
                        ClearAll();
                        return;
                    }

                    for (int i = GridViewControl.SelectedRows.Count - 1; i >= 0; i--)
                    {
                        if (GridViewControl.SelectedRows[i].Index < GridViewControl.RowCount - 1)
                        {
                            PrintList.Add(GridViewControl.SelectedRows[i].Index);
                        }
                    }

                    try
                    {
                        PrintSelect = 0;
                        PrintPreviewDialogControl.Document = PrintDocumentControl;
                        PrintPreviewDialogControl.ShowIcon = true;
                        PrintPreviewDialogControl.ShowDialog();
                    }
                    catch (Exception ex)
                    {
                        ClearAll();

                        MessageBox.Show(ex.Message, "错误，可能没有安装打印机", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }
            }
        }

        private void InPutFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog OpenFileObj = new OpenFileDialog();
            OpenFileObj.InitialDirectory = Application.StartupPath;
            OpenFileObj.DefaultExt = "xls";
            OpenFileObj.Filter = "Excel文件 (*.xls)|*.xls";
            OpenFileObj.Multiselect = false;
            OpenFileObj.ShowDialog();

            if (String.IsNullOrEmpty(OpenFileObj.FileName) == false)
            {
                if (InToExcel(OpenFileObj.FileName))
                    MessageBox.Show("导入成功");
                else
                    MessageBox.Show("导入失败");
            }
        }

        private void OutPutFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog SaveFileObj = new SaveFileDialog();
            SaveFileObj.InitialDirectory = Application.StartupPath;
            SaveFileObj.DefaultExt = "xls";
            SaveFileObj.Filter = "Excel文件 (*.xls)|*.xls";
            SaveFileObj.FileName = DeadManText.Text;

            DialogResult Result = SaveFileObj.ShowDialog();
            if (Result == DialogResult.OK || Result == DialogResult.Yes)
            {
                if (String.IsNullOrEmpty(SaveFileObj.FileName) == false)
                {
                    if (OutToExcel(SaveFileObj.FileName))
                        MessageBox.Show("导出成功");
                    else
                        MessageBox.Show("导出失败");
                }
            }
        }

        private void Radio_SetDSFont1_CheckedChanged(object sender, EventArgs e)
        {
            if (Radio_SetDSFont1.Checked)
            {
                Button_SetDSFont1.Enabled = true;
                Button_SetDSFont2.Enabled = true;
            }
            else
            {
                Button_SetDSFont1.Enabled = false;
                Button_SetDSFont2.Enabled = false;
            }
        }

        private void Radio_SetDSFont2_CheckedChanged(object sender, EventArgs e)
        {
            if (Radio_SetDSFont2.Checked)
            {
                Button_SetDSFont1.Enabled = false;
                Button_SetDSFont2.Enabled = false;
            }
            else
            {
                Button_SetDSFont1.Enabled = true;
                Button_SetDSFont2.Enabled = true;
            }
        }

        //Center
        public SizeF Print_Content_Center(string PrintString, FontColorClass FontColorObj, float PrintY, int SpacingType, PrintPageEventArgs e)
        {
            SizeF PrintWH = new SizeF();
            if (PrintString.IndexOf("\n") == -1)
            {
                PrintWH = e.Graphics.MeasureString(PrintString, FontColorObj.FontObj);
                e.Graphics.DrawString(PrintString, FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 - PrintWH.Width / 2, PrintY, new StringFormat());
                PrintWH.Height += PrintY;
            }
            else
            {
                float Width = 0, Height = 0;
                string[] PrintString_Array = PrintString.Split(new string[] { "\n" }, StringSplitOptions.RemoveEmptyEntries);
                for (int i = 0; i < PrintString_Array.Length; i++)
                {
                    PrintWH = e.Graphics.MeasureString(PrintString_Array[i], FontColorObj.FontObj);
                    e.Graphics.DrawString(PrintString_Array[i], FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 - PrintWH.Width / 2, PrintY, new StringFormat());

                    Width = PrintWH.Width;
                    Height = PrintY + PrintWH.Height;

                    if (i < PrintString_Array.Length - 1)
                    {
                        if (SpacingType == 1)
                            Height += Spacing1;
                        else
                            Height += Spacing2;

                        PrintY = Height;
                    }
                }

                PrintWH.Width = Width;
                PrintWH.Height = Height;
            }

            return PrintWH;
        }

        //Left
        public SizeF Print_Content_Left(string PrintString, FontColorClass FontColorObj, float PostionLR, float PrintY, int SpacingType, PrintPageEventArgs e)
        {
            SizeF PrintWH = new SizeF();
            if (PrintString.IndexOf("\n") == -1)
            {
                PrintWH = e.Graphics.MeasureString(PrintString, FontColorObj.FontObj);
                e.Graphics.DrawString(PrintString, FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 - PostionLR - PrintWH.Width, PrintY, new StringFormat());
                PrintWH.Height += PrintY;
            }
            else
            {
                float Width = 0, Height = 0;
                string[] PrintString_Array = PrintString.Split(new string[] { "\n" }, StringSplitOptions.RemoveEmptyEntries);
                for (int i = 0; i < PrintString_Array.Length; i++)
                {
                    PrintWH = e.Graphics.MeasureString(PrintString_Array[i], FontColorObj.FontObj);
                    e.Graphics.DrawString(PrintString_Array[i], FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 - PostionLR - PrintWH.Width, PrintY, new StringFormat());

                    Width = PrintWH.Width;
                    Height = PrintY + PrintWH.Height;

                    if (i < PrintString_Array.Length - 1)
                    {
                        if (SpacingType == 1)
                            Height += Spacing1;
                        else
                            Height += Spacing2;

                        PrintY = Height;
                    }
                }

                PrintWH.Width = Width;
                PrintWH.Height = Height;
            }

            return PrintWH;
        }

        //Right
        public SizeF Print_Content_Right(string PrintString, FontColorClass FontColorObj, float PostionLR, float PrintY, int SpacingType, PrintPageEventArgs e)
        {
            SizeF PrintWH = new SizeF();
            if (PrintString.IndexOf("\n") == -1)
            {
                PrintWH = e.Graphics.MeasureString(PrintString, FontColorObj.FontObj);
                e.Graphics.DrawString(PrintString, FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 + PostionLR, PrintY, new StringFormat());
                PrintWH.Height += PrintY;
            }
            else
            {
                float Width = 0, Height = 0;
                string[] PrintString_Array = PrintString.Split(new string[] { "\n" }, StringSplitOptions.RemoveEmptyEntries);
                for (int i = 0; i < PrintString_Array.Length; i++)
                {
                    PrintWH = e.Graphics.MeasureString(PrintString_Array[i], FontColorObj.FontObj);
                    e.Graphics.DrawString(PrintString_Array[i], FontColorObj.FontObj, new SolidBrush(FontColorObj.ColorObj), e.PageBounds.Width / 2 + PostionLR, PrintY, new StringFormat());

                    Width = PrintWH.Width;
                    Height = PrintY + PrintWH.Height;

                    if (i < PrintString_Array.Length - 1)
                    {
                        if (SpacingType == 1)
                            Height += Spacing1;
                        else
                            Height += Spacing2;

                        PrintY = Height;
                    }
                }

                PrintWH.Width = Width;
                PrintWH.Height = Height;
            }

            return PrintWH;
        }

        private void PrintDocumentControl_PrintPage(object sender, System.Drawing.Printing.PrintPageEventArgs e)
        {
            float PostionTop = 20;                                     //标题到死者的距离
            float PostionTB = 10;                                      //死者到称谓距离           
            float PostionLR = Convert.ToSingle(Name12SizeText.Text);   //姓名1，2距离          
            float PostionTail = 20;                                    //姓名到敬挽距离                      

            if (BodyList == null || BodyList.Count <= 0)
            {
                if (IsHead)
                {
                    float Pos = Convert.ToSingle(TopSizeText1.Text);

                    //标题                
                    SizeF StringWH = e.Graphics.MeasureString(StringToRow(TitleText.Text), FontColorTitleObj.FontObj);
                    float StringW = StringWH.Width;
                    float StringH = StringWH.Height;
                    e.Graphics.DrawString(StringToRow(TitleText.Text), FontColorTitleObj.FontObj, new SolidBrush(FontColorTitleObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, e.MarginBounds.Top, new StringFormat());
                    Pos += StringH + PostionTop;

                    //死者                
                    StringWH = e.Graphics.MeasureString(StringToRow(DeadManText.Text), FontColorDeadManObj.FontObj);
                    StringW = StringWH.Width;
                    StringH = StringWH.Height;
                    e.Graphics.DrawString(StringToRow(DeadManText.Text), FontColorDeadManObj.FontObj, new SolidBrush(FontColorDeadManObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                    Pos += StringH + PostionTB;

                    e.HasMorePages = true;
                    IsHead = false;
                }
                else
                {
                    float Pos = Convert.ToSingle(TopSizeText2.Text);

                    //底部
                    SizeF StringWH = e.Graphics.MeasureString(StringToRow(TailText.Text), FontColorTailObj.FontObj);
                    float StringW = StringWH.Width;
                    float StringH = StringWH.Height;
                    e.Graphics.DrawString(StringToRow(TailText.Text), FontColorTailObj.FontObj, new SolidBrush(FontColorTailObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                    e.HasMorePages = false;
                }
            }
            else
            {
                if (IsHead)
                {
                    float Pos = Convert.ToSingle(TopSizeText1.Text);

                    //标题                
                    SizeF StringWH = e.Graphics.MeasureString(StringToRow(TitleText.Text), FontColorTitleObj.FontObj);
                    float StringW = StringWH.Width;
                    float StringH = StringWH.Height;
                    e.Graphics.DrawString(StringToRow(TitleText.Text), FontColorTitleObj.FontObj, new SolidBrush(FontColorTitleObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                    Pos += StringH + PostionTop;

                    //打印选定
                    if (PrintList.Count == 0)
                    {
                        //称谓(前)
                        BodyClass BodyObj = (BodyClass)BodyList[CurrentPage];
                        string[] Content = BodyObj.Content;
                        StringWH = e.Graphics.MeasureString(StringToRow(Content[0]), FontColorFObj.FontObj);
                        StringW = StringWH.Width;
                        StringH = StringWH.Height;
                        e.Graphics.DrawString(StringToRow(Content[0]), FontColorFObj.FontObj, new SolidBrush(FontColorFObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        Pos += StringH + PostionTB;

                        //序号,选项
                        e.Graphics.DrawString(BodyObj.Sequence + " " + BodyObj.Remarks, FontColorSequenceObj.FontObj, new SolidBrush(FontColorSequenceObj.ColorObj), SequenceX, SequenceY, new StringFormat());
                    }
                    else
                    {
                        //称谓(前)                                                
                        BodyClass BodyObj = (BodyClass)BodyList[Convert.ToInt32(PrintList[PrintSelect])];
                        string[] Content = BodyObj.Content;
                        StringWH = e.Graphics.MeasureString(StringToRow(Content[0]), FontColorFObj.FontObj);
                        StringW = StringWH.Width;
                        StringH = StringWH.Height;
                        e.Graphics.DrawString(StringToRow(Content[0]), FontColorFObj.FontObj, new SolidBrush(FontColorFObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        Pos += StringH + PostionTB;

                        //序号,选项
                        e.Graphics.DrawString(BodyObj.Sequence + " " + BodyObj.Remarks, FontColorSequenceObj.FontObj, new SolidBrush(FontColorSequenceObj.ColorObj), SequenceX, SequenceY, new StringFormat());
                    }

                    //死者                
                    StringWH = e.Graphics.MeasureString(StringToRow(DeadManText.Text), FontColorDeadManObj.FontObj);
                    StringW = StringWH.Width;
                    StringH = StringWH.Height;
                    e.Graphics.DrawString(StringToRow(DeadManText.Text), FontColorDeadManObj.FontObj, new SolidBrush(FontColorDeadManObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                    Pos += StringH + PostionTB;

                    //打印选定
                    if (PrintList.Count == 0)
                    {
                        //称谓(后)                 
                        BodyClass BodyObj = (BodyClass)BodyList[CurrentPage];
                        string[] Content = BodyObj.Content;
                        StringWH = e.Graphics.MeasureString(StringToRow(Content[1]), FontColorTObj.FontObj);
                        StringW = StringWH.Width;
                        StringH = StringWH.Height;
                        e.Graphics.DrawString(StringToRow(Content[1]), FontColorTObj.FontObj, new SolidBrush(FontColorTObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        Pos += StringH + PostionTB;
                    }
                    else
                    {
                        //称谓(后)                  
                        BodyClass BodyObj = (BodyClass)BodyList[Convert.ToInt32(PrintList[PrintSelect])];
                        string[] Content = BodyObj.Content;
                        StringWH = e.Graphics.MeasureString(StringToRow(Content[1]), FontColorTObj.FontObj);
                        StringW = StringWH.Width;
                        StringH = StringWH.Height;
                        e.Graphics.DrawString(StringToRow(Content[1]), FontColorTObj.FontObj, new SolidBrush(FontColorTObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        Pos += StringH + PostionTB;
                    }

                    e.HasMorePages = true;
                    IsHead = false;
                }
                else
                {
                    float Pos = Convert.ToSingle(TopSizeText2.Text);

                    //打印选定
                    if (PrintList.Count == 0)
                    {
                        //姓名
                        BodyClass BodyObj = (BodyClass)BodyList[CurrentPage];
                        string[] Content = BodyObj.Content;

                        //序号,选项
                        e.Graphics.DrawString(BodyObj.Sequence + " " + BodyObj.Remarks, FontColorSequenceObj.FontObj, new SolidBrush(FontColorSequenceObj.ColorObj), SequenceX, SequenceY, new StringFormat());

                        //单行
                        if (String.IsNullOrEmpty(Content[3]))
                        {
                            SizeF StringWH;
                            float StringW = 0;
                            float StringH = 0;

                            if (Content[2].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[2].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH = Print_Content_Center(StringToRow(Name_Array[i]), FontColorDSObj1, Pos, 1, e);
                                        StringW = StringWH.Width;
                                        StringH = StringWH.Height;
                                    }
                                    else
                                    {
                                        StringWH = Print_Content_Center(StringToRow(Name_Array[i]), BodyObj.FontColorObj, Pos, 1, e);
                                        StringW = StringWH.Width;
                                        StringH = StringWH.Height;
                                    }

                                    if (i == Name_Array.Length - 1)
                                        Pos = StringH + PostionTail;
                                    else
                                    {
                                        if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                            Pos = StringH + 10;
                                        else
                                            Pos = StringH + Convert.ToSingle(Text_NameSize.Text);
                                    }
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH = Print_Content_Center(StringToRow(Content[2]), FontColorDSObj1, Pos, 1, e);
                                    StringW = StringWH.Width;
                                    StringH = StringWH.Height;
                                }
                                else
                                {
                                    StringWH = Print_Content_Center(StringToRow(Content[2]), BodyObj.FontColorObj, Pos, 1, e);
                                    StringW = StringWH.Width;
                                    StringH = StringWH.Height;
                                }

                                Pos = StringH + PostionTail;
                            }

                            //底部
                            StringWH = e.Graphics.MeasureString(StringToRow(TailText.Text), FontColorTailObj.FontObj);
                            StringW = StringWH.Width;
                            StringH = StringWH.Height;
                            e.Graphics.DrawString(StringToRow(TailText.Text), FontColorTailObj.FontObj, new SolidBrush(FontColorTailObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        }
                        else
                        {
                            //双行
                            float Pos1 = Convert.ToSingle(TopSizeText2.Text);
                            float Pos2 = Convert.ToSingle(TopSizeText2.Text);

                            SizeF StringWH1;
                            SizeF StringWH2;
                            float StringW1 = 0;
                            float StringH1 = 0;
                            float StringW2 = 0;
                            float StringH2 = 0;

                            if (Content[2].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[2].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH1 = Print_Content_Left(StringToRow(Name_Array[i]), FontColorDSObj2, PostionLR, Pos1, 2, e);
                                        StringW1 = StringWH1.Width;
                                        StringH1 = StringWH1.Height;
                                    }
                                    else
                                    {
                                        StringWH1 = Print_Content_Left(StringToRow(Name_Array[i]), BodyObj.FontColorObj, PostionLR, Pos1, 2, e);
                                        StringW1 = StringWH1.Width;
                                        StringH1 = StringWH1.Height;
                                    }

                                    if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                        Pos1 = StringH1 + 10;
                                    else
                                        Pos1 = StringH1 + Convert.ToSingle(Text_NameSize.Text);
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH1 = Print_Content_Left(StringToRow(Content[2]), FontColorDSObj2, PostionLR, Pos1, 2, e);
                                    StringW1 = StringWH1.Width;
                                    StringH1 = StringWH1.Height;
                                }
                                else
                                {
                                    StringWH1 = Print_Content_Left(StringToRow(Content[2]), BodyObj.FontColorObj, PostionLR, Pos1, 2, e);
                                    StringW1 = StringWH1.Width;
                                    StringH1 = StringWH1.Height;
                                }

                                Pos1 = StringH1;
                            }

                            if (Content[3].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[3].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH2 = Print_Content_Right(StringToRow(Name_Array[i]), FontColorDSObj2, PostionLR, Pos2, 2, e);
                                        StringW2 = StringWH2.Width;
                                        StringH2 = StringWH2.Height;
                                    }
                                    else
                                    {
                                        StringWH2 = Print_Content_Right(StringToRow(Name_Array[i]), BodyObj.FontColorObj, PostionLR, Pos2, 2, e);
                                        StringW2 = StringWH2.Width;
                                        StringH2 = StringWH2.Height;
                                    }

                                    if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                        Pos2 = StringH2 + 10;
                                    else
                                        Pos2 = StringH2 + Convert.ToSingle(Text_NameSize.Text);
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH2 = Print_Content_Right(StringToRow(Content[3]), FontColorDSObj2, PostionLR, Pos2, 2, e);
                                    StringW2 = StringWH2.Width;
                                    StringH2 = StringWH2.Height;
                                }
                                else
                                {
                                    StringWH2 = Print_Content_Right(StringToRow(Content[3]), BodyObj.FontColorObj, PostionLR, Pos2, 2, e);
                                    StringW2 = StringWH2.Width;
                                    StringH2 = StringWH2.Height;
                                }

                                Pos2 = StringH2;
                            }

                            if (Pos1 == Pos2)
                            {
                                Pos1 += PostionTail;
                                Pos = Pos1;
                            }
                            else
                            {
                                if (Pos1 > Pos2)
                                    Pos = Pos1 + PostionTail;
                                else
                                    Pos = Pos2 + PostionTail;
                            }

                            //底部
                            StringWH1 = e.Graphics.MeasureString(StringToRow(TailText.Text), FontColorTailObj.FontObj);
                            StringW1 = StringWH1.Width;
                            StringH1 = StringWH1.Height;
                            e.Graphics.DrawString(StringToRow(TailText.Text), FontColorTailObj.FontObj, new SolidBrush(FontColorTailObj.ColorObj), e.PageBounds.Width / 2 - StringW1 / 2, Pos, new StringFormat());
                        }

                        CurrentPage++;
                        e.HasMorePages = true;
                        IsHead = true;
                    }
                    else
                    {
                        //姓名
                        BodyClass BodyObj = (BodyClass)BodyList[Convert.ToInt32(PrintList[PrintSelect])];
                        string[] Content = BodyObj.Content;

                        //序号,选项
                        e.Graphics.DrawString(BodyObj.Sequence + " " + BodyObj.Remarks, FontColorSequenceObj.FontObj, new SolidBrush(FontColorSequenceObj.ColorObj), SequenceX, SequenceY, new StringFormat());

                        //单行
                        if (String.IsNullOrEmpty(Content[3]))
                        {
                            SizeF StringWH;
                            float StringW = 0;
                            float StringH = 0;

                            if (Content[2].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[2].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH = Print_Content_Center(StringToRow(Name_Array[i]), FontColorDSObj1, Pos, 1, e);
                                        StringW = StringWH.Width;
                                        StringH = StringWH.Height;
                                    }
                                    else
                                    {
                                        StringWH = Print_Content_Center(StringToRow(Name_Array[i]), BodyObj.FontColorObj, Pos, 1, e);
                                        StringW = StringWH.Width;
                                        StringH = StringWH.Height;
                                    }

                                    if (i == Name_Array.Length - 1)
                                        Pos = StringH + PostionTail;
                                    else
                                    {
                                        if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                            Pos = StringH + 10;
                                        else
                                            Pos = StringH + Convert.ToSingle(Text_NameSize.Text);
                                    }
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH = Print_Content_Center(StringToRow(Content[2]), FontColorDSObj1, Pos, 1, e);
                                    StringW = StringWH.Width;
                                    StringH = StringWH.Height;
                                }
                                else
                                {
                                    StringWH = Print_Content_Center(StringToRow(Content[2]), BodyObj.FontColorObj, Pos, 1, e);
                                    StringW = StringWH.Width;
                                    StringH = StringWH.Height;
                                }

                                Pos = StringH + PostionTail;
                            }

                            //底部
                            StringWH = e.Graphics.MeasureString(StringToRow(TailText.Text), FontColorTailObj.FontObj);
                            StringW = StringWH.Width;
                            StringH = StringWH.Height;
                            e.Graphics.DrawString(StringToRow(TailText.Text), FontColorTailObj.FontObj, new SolidBrush(FontColorTailObj.ColorObj), e.PageBounds.Width / 2 - StringW / 2, Pos, new StringFormat());
                        }
                        else
                        {
                            //双行
                            float Pos1 = Convert.ToSingle(TopSizeText2.Text);
                            float Pos2 = Convert.ToSingle(TopSizeText2.Text);

                            SizeF StringWH1;
                            SizeF StringWH2;
                            float StringW1 = 0;
                            float StringH1 = 0;
                            float StringW2 = 0;
                            float StringH2 = 0;

                            if (Content[2].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[2].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH1 = Print_Content_Left(StringToRow(Name_Array[i]), FontColorDSObj2, PostionLR, Pos1, 2, e);
                                        StringW1 = StringWH1.Width;
                                        StringH1 = StringWH1.Height;
                                    }
                                    else
                                    {
                                        StringWH1 = Print_Content_Left(StringToRow(Name_Array[i]), BodyObj.FontColorObj, PostionLR, Pos1, 2, e);
                                        StringW1 = StringWH1.Width;
                                        StringH1 = StringWH1.Height;
                                    }

                                    if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                        Pos1 = StringH1 + 10;
                                    else
                                        Pos1 = StringH1 + Convert.ToSingle(Text_NameSize.Text);
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH1 = Print_Content_Left(StringToRow(Content[2]), FontColorDSObj2, PostionLR, Pos1, 2, e);
                                    StringW1 = StringWH1.Width;
                                    StringH1 = StringWH1.Height;
                                }
                                else
                                {
                                    StringWH1 = Print_Content_Left(StringToRow(Content[2]), BodyObj.FontColorObj, PostionLR, Pos1, 2, e);
                                    StringW1 = StringWH1.Width;
                                    StringH1 = StringWH1.Height;
                                }

                                Pos1 = StringH1;
                            }

                            if (Content[3].IndexOf(",") >= 0)
                            {
                                string[] Name_Array = Content[3].Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);

                                for (int i = 0; i < Name_Array.Length; i++)
                                {
                                    if (Radio_SetDSFont1.Checked)
                                    {
                                        StringWH2 = Print_Content_Right(StringToRow(Name_Array[i]), FontColorDSObj2, PostionLR, Pos2, 2, e);
                                        StringW2 = StringWH2.Width;
                                        StringH2 = StringWH2.Height;
                                    }
                                    else
                                    {
                                        StringWH2 = Print_Content_Right(StringToRow(Name_Array[i]), BodyObj.FontColorObj, PostionLR, Pos2, 2, e);
                                        StringW2 = StringWH2.Width;
                                        StringH2 = StringWH2.Height;
                                    }

                                    if (String.IsNullOrEmpty(Text_NameSize.Text) || IsIntegerNumber(Text_NameSize.Text) == false)
                                        Pos2 = StringH2 + 10;
                                    else
                                        Pos2 = StringH2 + Convert.ToSingle(Text_NameSize.Text);
                                }
                            }
                            else
                            {
                                if (Radio_SetDSFont1.Checked)
                                {
                                    StringWH2 = Print_Content_Right(StringToRow(Content[3]), FontColorDSObj2, PostionLR, Pos2, 2, e);
                                    StringW2 = StringWH2.Width;
                                    StringH2 = StringWH2.Height;
                                }
                                else
                                {
                                    StringWH2 = Print_Content_Right(StringToRow(Content[3]), BodyObj.FontColorObj, PostionLR, Pos2, 2, e);
                                    StringW2 = StringWH2.Width;
                                    StringH2 = StringWH2.Height;
                                }

                                Pos2 = StringH2;
                            }

                            if (Pos1 == Pos2)
                            {
                                Pos1 += PostionTail;
                                Pos = Pos1;
                            }
                            else
                            {
                                if (Pos1 > Pos2)
                                    Pos = Pos1 + PostionTail;
                                else
                                    Pos = Pos2 + PostionTail;
                            }

                            //底部
                            StringWH1 = e.Graphics.MeasureString(StringToRow(TailText.Text), FontColorTailObj.FontObj);
                            StringW1 = StringWH1.Width;
                            StringH1 = StringWH1.Height;
                            e.Graphics.DrawString(StringToRow(TailText.Text), FontColorTailObj.FontObj, new SolidBrush(FontColorTailObj.ColorObj), e.PageBounds.Width / 2 - StringW1 / 2, Pos, new StringFormat());
                        }

                        e.HasMorePages = true;
                        IsHead = true;
                        PrintSelect++;
                    }
                }

                if (PrintList.Count == 0)
                {
                    if (CurrentPage >= BodyList.Count)
                    {
                        e.HasMorePages = false;
                        ClearAll();
                    }
                }
                else
                {
                    if (IsHead == true && PrintList.Count == PrintSelect)
                    {
                        e.HasMorePages = false;
                        ClearAll();
                    }
                }
            }
        }      
    }

    public class FontColorClass
    {
        public Font FontObj;
        public Color ColorObj;
    }

    public class BodyClass
    {
        public string Sequence;
        public string Remarks;
        public string[] Content;
        public FontColorClass FontColorObj;
    }
}

