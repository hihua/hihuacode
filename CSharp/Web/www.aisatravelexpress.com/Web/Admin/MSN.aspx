<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="MSN.aspx.cs" Inherits="Web.Admin.MSN" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript">
        function ActionSubmit(Action_ID, Form_ID)
        {
            switch (Action_ID)
            {
                case 1:
                    if (confirm("确实添加"))
                    {
                        $("#MSN_Name").val($("#MSN_Name_Add").val());
                        $("#MSN_Invitee").val($("#MSN_Invitee_Add").val());
                        $("#Form_Submit").attr("action", "MSN_Submit.aspx?Action_ID=1");
                        $("#Form_Submit").submit();
                    }
                    break;
                    
                case 2:
                    if (confirm("确实修改"))
                    {
                        $("#MSN_ID").val($("#MSN_ID_" + Form_ID).val());
                        $("#MSN_Name").val($("#MSN_Name_" + Form_ID).val());
                        $("#MSN_Invitee").val($("#MSN_Invitee_" + Form_ID).val());
                        $("#Form_Submit").attr("action", "MSN_Submit.aspx?Action_ID=2");
                        $("#Form_Submit").submit();
                    }
                    break;
                    
                    
                case 3:
                    if (confirm("确实删除"))
                    {
                        $("#MSN_ID").val($("#MSN_ID_" + Form_ID).val());                        
                        $("#Form_Submit").attr("action", "MSN_Submit.aspx?Action_ID=3");
                        $("#Form_Submit").submit();
                    }
                    break;            
            }        
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table align="center" border="0" width="70%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30">MSN管理</td>
	            </tr>
            </table>
            <table id="g_MainTable" width="70%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr height="30" align="center" bgcolor="#C0C0C0">
                    <td>序号</td>
	                <td>MSN_Name</td>
	                <td>MSN_Invitee</td>
	                <td>编辑</td>	                
                </tr>
            </table>
            <p></p>
            <table id="Table1" width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr>
                    <td>MSN_Name: <input id="MSN_Name_Add" name="MSN_Name_Add" type="text" maxlength="20" /></td>                    
                    <td>MSN_Invitee: <input id="MSN_Invitee_Add" name="MSN_Invitee_Add" type="text" maxlength="40" /></td>
                    <td><input id="MSN_Submit" type="button" value=" 添加 " onclick="ActionSubmit(1, 0)" /></td>                                                            
                </tr>
            </table>
        </div>
    </form>
    <form id="Form_Submit" method="post" action="">
        <input id="MSN_ID" name="MSN_ID" type="hidden" />
        <input id="MSN_Name" name="MSN_Name" type="hidden" />
        <input id="MSN_Invitee" name="MSN_Invitee" type="hidden" />
    </form>
</body>
</html>
