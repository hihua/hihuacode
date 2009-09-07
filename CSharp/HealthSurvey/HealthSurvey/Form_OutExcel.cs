using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ChoiControls;

namespace HealthSurvey
{
    public partial class Form_OutExcel : ChoiForm
    {
        private Hashtable QuestionTable = null;
        private Hashtable AnswerInfo_Table = null;

        public Form_OutExcel()
        {
            InitializeComponent();

            listView_Refresh();
        }
        
        private void Set_QuestionTable()
        {
            QuestionTable = new Hashtable();
                
            Function_Question function_Question = new Function_Question();
            DataTable dt = function_Question.Query_AnswerInfo();
            if (dt != null && dt.Rows.Count > 0)
            {
                AnswerInfo_Table = new Hashtable();
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    String Title = "";                        
                    if (dt.Rows[i]["Question_ID"] != null && CommonFunction.IsNumber(dt.Rows[i]["Question_ID"].ToString(), 1))
                    {                            
                        int QuestionID = Convert.ToInt32(dt.Rows[i]["Question_ID"].ToString());
                        AnswerInfo_Table.Add(QuestionID, dt.Rows[i]["Question_Title"].ToString());

                        Function_QuestionList function_QuestionList = new Function_QuestionList();
                        DataTable QuestionList = function_QuestionList.Query_QuestionList(QuestionID);

                        if (QuestionList != null && QuestionList.Rows.Count > 0)
                        {
                            ArrayList QuestionArray = new ArrayList();
                            for (int j = 0; j < QuestionList.Rows.Count; j++)
                            {
                                if (Title == QuestionList.Rows[j]["QuestionList_Title"].ToString() && j > 0)
                                {
                                    Class_AnswerTable class_answerTable = (Class_AnswerTable)QuestionArray[QuestionArray.Count - 1];
                                    SortedList QuestionSelect = class_answerTable.AnswerTable_Select;
                                    if (QuestionSelect != null && QuestionList.Rows[j]["QuestionSelect_ID"] != null && CommonFunction.IsNumber(QuestionList.Rows[j]["QuestionSelect_ID"].ToString(), 1))
                                    {
                                        int QuestionSelect_ID = Convert.ToInt32(QuestionList.Rows[j]["QuestionSelect_ID"].ToString());
                                        QuestionSelect.Add(QuestionSelect_ID, QuestionList.Rows[j]["QuestionSelect_Text"].ToString());
                                        class_answerTable.AnswerTable_Select = QuestionSelect;
                                    }

                                    QuestionArray[QuestionArray.Count - 1] = class_answerTable;
                                }
                                else
                                {
                                    Class_AnswerTable class_answerTable = new Class_AnswerTable();
                                    if (QuestionList.Rows[j]["QuestionList_ListID"] != null && CommonFunction.IsNumber(QuestionList.Rows[j]["QuestionList_ListID"].ToString(), 1))
                                    {
                                        class_answerTable.AnswerTable_ID = Convert.ToInt32(QuestionList.Rows[j]["QuestionList_ListID"].ToString());
                                        class_answerTable.AnswerTable_Title = QuestionList.Rows[j]["QuestionList_Title"].ToString();

                                        if (QuestionList.Rows[j]["QuestionSelect_ID"] != null && CommonFunction.IsNumber(QuestionList.Rows[j]["QuestionSelect_ID"].ToString(), 1))
                                        {
                                            SortedList QuestionSelect = new SortedList();
                                            int QuestionSelect_ID = Convert.ToInt32(QuestionList.Rows[j]["QuestionSelect_ID"].ToString());
                                            QuestionSelect.Add(QuestionSelect_ID, QuestionList.Rows[j]["QuestionSelect_Text"].ToString());
                                            class_answerTable.AnswerTable_Select = QuestionSelect;
                                        }
                                        else
                                        {
                                            class_answerTable.AnswerTable_Select = null;
                                        }

                                        class_answerTable.AnswerTable_SelectID = 0;

                                        if (QuestionList.Rows[j]["QuestionList_TurnRow"] != null && CommonFunction.IsNumber(QuestionList.Rows[j]["QuestionList_TurnRow"].ToString(), 1))
                                            class_answerTable.AnswerTable_TurnRow = Convert.ToInt32(QuestionList.Rows[j]["QuestionList_TurnRow"].ToString());
                                        else
                                            class_answerTable.AnswerTable_TurnRow = 0;
                                    }

                                    Title = QuestionList.Rows[j]["QuestionList_Title"].ToString();
                                    QuestionArray.Add(class_answerTable);
                                }
                            }

                            QuestionTable.Add(QuestionID, QuestionArray);
                        }
                    }
                }
            }
        }
                
        private void listView_Refresh()
        {
            listView1.Items.Clear();
            Function_ClientInfo function_clientInfo = new Function_ClientInfo();
            Function_AnswerInfo function_answerInfo = new Function_AnswerInfo();
            
            DataTable dt = function_clientInfo.Query_ClientInfo();
            if (dt != null && dt.Rows.Count > 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    int ClientID = Convert.ToInt32(dt.Rows[i]["ClientInfo_ID"].ToString());
                    int AnswerInfo = function_answerInfo.Query_AnswerInfo_Client(ClientID);

                    listView1.Items.Add(dt.Rows[i]["ClientInfo_ID"].ToString());
                    listView1.Items[i].SubItems.Add(dt.Rows[i]["ClientInfo_Name"].ToString());
                    if (dt.Rows[i]["ClientInfo_Age"] != null && !String.IsNullOrEmpty(dt.Rows[i]["ClientInfo_Age"].ToString()))                    
                        listView1.Items[i].SubItems.Add(dt.Rows[i]["ClientInfo_Age"].ToString());                    
                    else
                        listView1.Items[i].SubItems.Add(""); 

                    if (dt.Rows[i]["ClientInfo_Sex"] != null && !String.IsNullOrEmpty(dt.Rows[i]["ClientInfo_Sex"].ToString()))
                    {
                        if (dt.Rows[i]["ClientInfo_Sex"].ToString() == "1")
                            listView1.Items[i].SubItems.Add("男");
                        else
                            listView1.Items[i].SubItems.Add("女");
                    }
                    else                    
                        listView1.Items[i].SubItems.Add("");

                    listView1.Items[i].SubItems.Add(AnswerInfo.ToString()); 
                }

                if (listView1.Items.Count > 0)
                {
                    listView1.Items[0].Selected = true;
                }
            }
        }

        private void choiButton1_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog folderBrowser = new FolderBrowserDialog();
            folderBrowser.SelectedPath = Application.StartupPath;
            DialogResult Result = folderBrowser.ShowDialog();
            if (Result == DialogResult.OK || Result == DialogResult.Yes)
            {
                if (QuestionTable == null || QuestionTable.Count <= 0)
                    Set_QuestionTable();

                if (listView1.SelectedItems.Count > 0 && QuestionTable != null && QuestionTable.Count > 0) 
                {
                    Class_Excel class_Excel = new Class_Excel();
                    Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                    for (int i = 0; i < listView1.SelectedItems.Count;i++)
                    {
                        if (CommonFunction.IsNumber(listView1.SelectedItems[i].Text, 1))
                        {
                            int ClientID = Convert.ToInt32(listView1.SelectedItems[i].Text);
                            DataTable ClientInfo = function_clientInfo.Query_ClientInfo(ClientID);
                            if (ClientInfo == null || ClientInfo.Rows.Count <= 0)
                                continue;

                            String ExcelFile = ClientInfo.Rows[0]["ClientInfo_Name"].ToString() + "-" + ClientInfo.Rows[0]["ClientInfo_ID"].ToString() + ".xls";
                            class_Excel.ExcelFile = folderBrowser.SelectedPath + "\\" + ExcelFile;

                            Function_AnswerInfo function_answerInfo = new Function_AnswerInfo();
                            DataTable AnswerInfo = function_answerInfo.Query_AnswerInfo_List(ClientID);
                            if (AnswerInfo != null && AnswerInfo.Rows.Count > 0)
                            {
                                int[] AnswerInfo_Array = new int[AnswerInfo.Rows.Count];
                                String Question_Tail = "";
                                for (int j = 0; j < AnswerInfo.Rows.Count; j++)
                                {
                                    if (AnswerInfo.Rows[j]["Question_ID"] != null && CommonFunction.IsNumber(AnswerInfo.Rows[j]["Question_ID"].ToString(), 1))
                                    {
                                        int QuestionID = Convert.ToInt32(AnswerInfo.Rows[j]["Question_ID"].ToString());
                                        int AnswerInfo_ID = Convert.ToInt32(AnswerInfo.Rows[j]["AnswerInfo_ID"].ToString());

                                        if (QuestionID == 1)
                                            Question_Tail = AnswerInfo.Rows[j]["Question_Tail"].ToString();

                                        if (QuestionTable.ContainsKey(QuestionID))
                                        {
                                            ArrayList QuestionArray = (ArrayList)QuestionTable[QuestionID];
                                            Function_AnswerList function_answerList = new Function_AnswerList();
                                            DataTable AnswerList = function_answerList.Query_AnswerList(AnswerInfo_ID);

                                            for (int k = 0; k < QuestionArray.Count; k++)
                                            {
                                                Class_AnswerTable class_answerTable = (Class_AnswerTable)QuestionArray[k];
                                                if (AnswerList.Rows[k]["QuestionSelect_ID"] != null && CommonFunction.IsNumber(AnswerList.Rows[k]["QuestionSelect_ID"].ToString(), 1))
                                                {
                                                    int QuestionSelect_ID = Convert.ToInt32(AnswerList.Rows[k]["QuestionSelect_ID"].ToString());
                                                    class_answerTable.AnswerTable_SelectID = QuestionSelect_ID;
                                                }

                                                QuestionArray[k] = class_answerTable;
                                            }

                                            AnswerInfo_Array[j] = QuestionID;
                                            QuestionTable[QuestionID] = QuestionArray;
                                        }
                                    }
                                }

                                class_Excel.OutToFile(ClientInfo, QuestionTable, AnswerInfo_Array, AnswerInfo_Table, Question_Tail);
                            }
                            else
                            {
                                class_Excel.OutToFile(ClientInfo, null, null, null, "");
                            }
                        }
                    }

                    class_Excel.ExcelClose();
                    class_Excel = null;

                    MessageBox.Show("导出成功！");
                }
            }
        }

        private void choiButton2_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog folderBrowser = new FolderBrowserDialog();
            folderBrowser.SelectedPath = Application.StartupPath;
            DialogResult Result = folderBrowser.ShowDialog();
            if (Result == DialogResult.OK || Result == DialogResult.Yes)
            {
                if (QuestionTable == null || QuestionTable.Count <= 0)
                    Set_QuestionTable();

                if (listView1.Items.Count > 0 && QuestionTable != null && QuestionTable.Count > 0)
                {
                    Class_Excel class_Excel = new Class_Excel();
                    Function_ClientInfo function_clientInfo = new Function_ClientInfo();
                    for (int i = 0; i < listView1.Items.Count; i++)
                    {
                        if (CommonFunction.IsNumber(listView1.Items[i].Text, 1))
                        {
                            int ClientID = Convert.ToInt32(listView1.Items[i].Text);
                            DataTable ClientInfo = function_clientInfo.Query_ClientInfo(ClientID);
                            if (ClientInfo == null || ClientInfo.Rows.Count <= 0)
                                continue;

                            String ExcelFile = ClientInfo.Rows[0]["ClientInfo_Name"].ToString() + "-" + ClientInfo.Rows[0]["ClientInfo_ID"].ToString() + ".xls";
                            class_Excel.ExcelFile = folderBrowser.SelectedPath + "\\" + ExcelFile;

                            Function_AnswerInfo function_answerInfo = new Function_AnswerInfo();
                            DataTable AnswerInfo = function_answerInfo.Query_AnswerInfo_List(ClientID);
                            if (AnswerInfo != null && AnswerInfo.Rows.Count > 0)
                            {
                                int[] AnswerInfo_Array = new int[AnswerInfo.Rows.Count];
                                String Question_Tail = "";
                                for (int j = 0; j < AnswerInfo.Rows.Count; j++)
                                {
                                    if (AnswerInfo.Rows[j]["Question_ID"] != null && CommonFunction.IsNumber(AnswerInfo.Rows[j]["Question_ID"].ToString(), 1))
                                    {
                                        int QuestionID = Convert.ToInt32(AnswerInfo.Rows[j]["Question_ID"].ToString());
                                        int AnswerInfo_ID = Convert.ToInt32(AnswerInfo.Rows[j]["AnswerInfo_ID"].ToString());

                                        if (QuestionID == 1)
                                            Question_Tail = AnswerInfo.Rows[j]["Question_Tail"].ToString();

                                        if (QuestionTable.ContainsKey(QuestionID))
                                        {
                                            ArrayList QuestionArray = (ArrayList)QuestionTable[QuestionID];
                                            Function_AnswerList function_answerList = new Function_AnswerList();
                                            DataTable AnswerList = function_answerList.Query_AnswerList(AnswerInfo_ID);

                                            for (int k = 0; k < QuestionArray.Count; k++)
                                            {
                                                Class_AnswerTable class_answerTable = (Class_AnswerTable)QuestionArray[k];
                                                if (AnswerList.Rows[k]["QuestionSelect_ID"] != null && CommonFunction.IsNumber(AnswerList.Rows[k]["QuestionSelect_ID"].ToString(), 1))
                                                {
                                                    int QuestionSelect_ID = Convert.ToInt32(AnswerList.Rows[k]["QuestionSelect_ID"].ToString());
                                                    class_answerTable.AnswerTable_SelectID = QuestionSelect_ID;
                                                }

                                                QuestionArray[k] = class_answerTable;
                                            }

                                            AnswerInfo_Array[j] = QuestionID;
                                            QuestionTable[QuestionID] = QuestionArray;
                                        }
                                    }
                                }

                                class_Excel.OutToFile(ClientInfo, QuestionTable, AnswerInfo_Array, AnswerInfo_Table, Question_Tail);
                            }
                            else
                            {
                                class_Excel.OutToFile(ClientInfo, null, null, null, "");
                            }
                        }
                    }

                    class_Excel.ExcelClose();
                    class_Excel = null;

                    MessageBox.Show("导出成功！");
                }
            }
        }
    }
}
