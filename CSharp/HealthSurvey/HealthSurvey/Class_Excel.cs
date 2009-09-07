using System;
using System.Collections;
using System.Reflection;
using System.Data;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Text;

namespace HealthSurvey
{
    public class Class_Excel
    {
        public String ErrorMessage = "";
        public String ErrorSource = "";
      
        public String ExcelFile = "";
        private Excel.Application excel_application = null;

        public Class_Excel()
        {
            excel_application = new Excel.ApplicationClass();
            excel_application.Visible = false;
        }

        public void ExcelClose()
        {
            excel_application.Quit();
            Marshal.ReleaseComObject(excel_application);
            GC.Collect();
        }

        public bool OutToFile(DataTable ClientInfo_Table, Hashtable QuestionTable, int[] AnswerInfo_Array, Hashtable AnswerInfo_Table, String Question_Tail)
        {
            if (String.IsNullOrEmpty(ExcelFile))
                return false;

            if (ClientInfo_Table == null || ClientInfo_Table.Rows.Count <= 0)
                return false;
                        
            try
            {
                String Weight = "0";
                String Height = "0";

                Excel.Workbook excel_workBook = excel_application.Workbooks.Add(Missing.Value);
                Excel.Worksheet excel_sheet = (Excel.Worksheet)excel_workBook.Sheets[1];
                excel_sheet.Name = "客户信息";

                for (int i = 0; i < ClientInfo_Table.Columns.Count; i++)
                {
                    switch (i)
                    {
                        case 0:
                            excel_sheet.Cells[1, i + 1] = "编号";
                            break;

                        case 1:
                            excel_sheet.Cells[1, i + 1] = "姓名";
                            break;

                        case 2:
                            excel_sheet.Cells[1, i + 1] = "年龄";
                            break;

                        case 3:
                            excel_sheet.Cells[1, i + 1] = "性别";
                            break;

                        case 4:
                            excel_sheet.Cells[1, i + 1] = "体重";
                            break;

                        case 5:
                            excel_sheet.Cells[1, i + 1] = "身高";
                            break;

                        case 6:
                            excel_sheet.Cells[1, i + 1] = "省份";
                            break;

                        case 7:
                            excel_sheet.Cells[1, i + 1] = "城市";
                            break;

                        case 8:
                            excel_sheet.Cells[1, i + 1] = "地址";
                            break;

                        case 9:
                            excel_sheet.Cells[1, i + 1] = "联系电话";
                            break;

                        case 10:
                            excel_sheet.Cells[1, i + 1] = "电子邮件";
                            break;

                        case 11:
                            excel_sheet.Cells[1, i + 1] = "邮政编码";
                            break;

                        case 12:
                            excel_sheet.Cells[1, i + 1] = "注册时间";
                            break;                        
                    }                                        
                }

                if (ClientInfo_Table.Rows.Count > 0)
                {
                    for (int i = 0; i < ClientInfo_Table.Rows.Count; i++)
                    {
                        for (int j = 0; j < ClientInfo_Table.Columns.Count; j++)
                        {
                            if (j == 3)
                            {
                                if (ClientInfo_Table.Rows[i][j].ToString() == "1")
                                    excel_sheet.Cells[i + 2, j + 1] = "男";
                                else
                                    excel_sheet.Cells[i + 2, j + 1] = "女";
                            }
                            else
                                excel_sheet.Cells[i + 2, j + 1] = ClientInfo_Table.Rows[i][j].ToString();

                            if (j == 4)
                                Weight = ClientInfo_Table.Rows[i][j].ToString();

                            if (j == 5)
                                Height = ClientInfo_Table.Rows[i][j].ToString();

                            Excel.Range ExcelRange = (Excel.Range)excel_sheet.Cells[i + 2, j + 1];
                            ExcelRange.EntireColumn.AutoFit();
                        }
                    }
                }

                if (QuestionTable != null && AnswerInfo_Array != null && AnswerInfo_Array.Length > 0 && AnswerInfo_Table != null && AnswerInfo_Table.Count > 0)
                {
                    for (int i = 0; i < AnswerInfo_Array.Length; i++)
                    {
                        int QuestionID = AnswerInfo_Array[i];
                        excel_sheet = (Excel.Worksheet)excel_workBook.Sheets[2 + i];

                        if (AnswerInfo_Table.ContainsKey(QuestionID))
                            excel_sheet.Name = AnswerInfo_Table[QuestionID].ToString();
                        else
                            excel_sheet.Name = "";

                        if (QuestionTable.ContainsKey(QuestionID))
                        {
                            ArrayList QuestionArray = (ArrayList)QuestionTable[QuestionID];
                            if (QuestionArray != null && QuestionArray.Count > 0)
                            {
                                int p = 1;                                
                                for (int j = 0; j < QuestionArray.Count; j++)
                                {
                                    int k = 1;
                                    Class_AnswerTable class_answerTable = (Class_AnswerTable)QuestionArray[j];

                                    excel_sheet.Cells[p, k] = class_answerTable.AnswerTable_ID.ToString();
                                    Excel.Range ExcelRange = (Excel.Range)excel_sheet.Cells[p, k];
                                    ExcelRange.Columns.AutoFit();

                                    k++;
                                    excel_sheet.Cells[p, k] = class_answerTable.AnswerTable_Title;
                                    ExcelRange = (Excel.Range)excel_sheet.Cells[p, k];                                                                                                         
                                    
                                    if (class_answerTable.AnswerTable_Select != null)
                                    {
                                        if (class_answerTable.AnswerTable_TurnRow == 0)
                                        {
                                            p++;
                                            k = 2;

                                            SortedList AnswerTable_Select = class_answerTable.AnswerTable_Select;
                                            AnswerTable_Select.GetEnumerator();

                                            IDictionaryEnumerator Enumerator = AnswerTable_Select.GetEnumerator();
                                            while (Enumerator.MoveNext())
                                            {
                                                String QuestionSelectID = Enumerator.Key.ToString();

                                                String AnswerSelect = "";
                                                AnswerSelect += Enumerator.Value.ToString();

                                                excel_sheet.Cells[p, k] = AnswerSelect;
                                                ExcelRange = (Excel.Range)excel_sheet.Cells[p, k];
                                                ExcelRange.ColumnWidth = 20;

                                                if (QuestionSelectID == class_answerTable.AnswerTable_SelectID.ToString())
                                                    ExcelRange.Font.ColorIndex = 3;

                                                k++;                                                                                                                                                
                                            }                                           
                                        }
                                        else
                                        {
                                            if (class_answerTable.AnswerTable_TurnRow == 1)
                                            {
                                                k = 2;
                                                SortedList AnswerTable_Select = class_answerTable.AnswerTable_Select;
                                                AnswerTable_Select.GetEnumerator();

                                                IDictionaryEnumerator Enumerator = AnswerTable_Select.GetEnumerator();
                                                while (Enumerator.MoveNext())
                                                {
                                                    p++;
                                                    String QuestionSelectID = Enumerator.Key.ToString();

                                                    String AnswerSelect = "";
                                                    AnswerSelect += Enumerator.Value.ToString();

                                                    excel_sheet.Cells[p, k] = AnswerSelect;
                                                    ExcelRange = (Excel.Range)excel_sheet.Cells[p, k];
                                                    
                                                    if (QuestionSelectID == class_answerTable.AnswerTable_SelectID.ToString())
                                                        ExcelRange.Font.ColorIndex = 3;
                                                }
                                            }

                                            if (class_answerTable.AnswerTable_TurnRow == 2)
                                            {
                                                p++;
                                                String AnswerSelect = "";

                                                if (class_answerTable.AnswerTable_ID == 40)
                                                    AnswerSelect = Weight;

                                                if (class_answerTable.AnswerTable_ID == 41)
                                                    AnswerSelect = Height;

                                                excel_sheet.Cells[p, k] = AnswerSelect;                                                
                                                ExcelRange = (Excel.Range)excel_sheet.Cells[p, k];                                                
                                                ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignLeft;
                                                ExcelRange.Font.ColorIndex = 3;
                                            }
                                        }
                                    }

                                    p++;                                   
                                }
                                                                
                                if (QuestionID == 1)
                                {
                                    p++;                                    
                                    excel_sheet.Cells[p, 1] = "还有那些症状";

                                    if (!String.IsNullOrEmpty(Question_Tail))
                                    {
                                        p++;
                                        excel_sheet.Cells[p, 1] = Question_Tail;
                                        Excel.Range ExcelRange = (Excel.Range)excel_sheet.Cells[p, 1];
                                        ExcelRange.HorizontalAlignment = Excel.XlHAlign.xlHAlignLeft;
                                        ExcelRange.Font.ColorIndex = 3;
                                    }
                                }
                            }
                        }
                    }
                }
                                
                excel_workBook.SaveAs(ExcelFile, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Excel.XlSaveAsAccessMode.xlNoChange, Missing.Value, Missing.Value, Missing.Value, Missing.Value, Missing.Value);                
                excel_workBook.Close(false, false, false);
                excel_workBook = null;

                return true;              
            }
            catch (Exception ex)
            {                
                ErrorMessage = ex.Message;
                ErrorSource = ex.Source;

                return false;
            }
        }             
    }
}
