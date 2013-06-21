<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LowFare_Detail.aspx.cs" Inherits="Web.Admin.LowFare_Detail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>    
    <link type="text/css" href="css.css" rel="stylesheet" />
    <link type="text/css" href="../css/aisa.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/Ajax.js"></script>
    <script type="text/javascript" src="../Js/lhgcalendar.js"></script>
    <script type="text/javascript">                
        function CityList(LowFare_TextBox)
        {
            if (LowFare_TextBox != "")
            {
                window.open('../AirportList.aspx?City_Country=0&City_TextBox=' + LowFare_TextBox,'AirportList','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');            
            }
        }
       
        function GetCity(Obj)
        {
            if (Obj == null)
                return;
            
            if (Obj.value != "")
            {
                var URL = "../WebService/City.aspx";
                var PostContent = "City_Name_Title=" + Obj.value;
                
                Ajax_CallBack(URL, PostContent, "xml", true, function(ret)
	            {
	                $(ret).find("City").each(function()
		            {
		                var City_Name = $(this).find("City_Name");             		            
		                if (City_Name != null)
		                    Obj.value = City_Name.text();
		            });	            
	            });                            
            }                    
        }
        
        function ActionMember(Action_ID, Member_ID)
        {
            var URL = "Member_Detail.aspx?Action_ID=" + Action_ID + "&Member_ID=" + Member_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");   
                    break;
                    
                case 2:
                    window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");                    
                        
                case 4:
                    if (confirm("确实转换"))
                        window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");                    
                
                    break;            
            }            
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div class="inside2" id="LowFare_Content" runat="server">
            <div style="text-align: center">
                <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	                <tr>
		                <td align="center" class="DivTitle1" height="30"><asp:Label ID="LowFare_Name" Text="机票问价" runat="server"></asp:Label></td>
	                </tr>
                </table>
                <table width="50%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td align="left" style="height: 50px">
                            <asp:RadioButton ID="LowFare_Type1" GroupName="LowFare_Type" Text="Round-trip" runat="server" AutoPostBack="true" Checked="true" oncheckedchanged="LowFare_Type_CheckedChanged" />&nbsp;
                            <asp:RadioButton ID="LowFare_Type2" GroupName="LowFare_Type" Text="One-way" runat="server" AutoPostBack="true" oncheckedchanged="LowFare_Type_CheckedChanged" />&nbsp;
                            <asp:RadioButton ID="LowFare_Type3" GroupName="LowFare_Type" Text="Multi-city" runat="server" AutoPostBack="true" oncheckedchanged="LowFare_Type_CheckedChanged" />&nbsp;
                        </td>
                    </tr>                   
                    <tr id="LowFare_Text_Type1" runat="server">
                        <td align="left" style="height: 50px">
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type1');" style="cursor:pointer" />
                            <br />
                            <br />
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type1');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type1" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>                                                                
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                                <tr style="height: 10px">
                                    <td>
                                        &nbsp;
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        &nbsp;Returning
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time2_Select_Type1" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr id="LowFare_Text_Type2" runat="server" visible="false">
                        <td align="left" style="height: 50px">
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type2');" style="cursor:pointer" />
                            <br />
                            <br />
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type2');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type2" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type2" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>
                        </td>
                    </tr>
                    <tr id="LowFare_Text_Type3" runat="server" visible="false">
                        <td align="left" style="height: 50px">
                            &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 1</span>
                            <br />
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_1');" style="cursor:pointer" />
                            <br />
                            <br />                                                
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_1');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type3_1" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>
                            <br /><br /><br /><br />
                            &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 2</span>
                            <br />
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_2');" style="cursor:pointer" />
                            <br />
                            <br />                                                
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_2');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_2" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type3_2" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>
                            <br /><br /><br /><br />
                            &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 3</span>
                            <br />
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_3" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_3');" style="cursor:pointer" />
                            <br />
                            <br />                                                
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_3" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_3');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_3" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type3_3" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>
                            <br /><br /><br /><br />
                            &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 4</span>
                            <br />
                            &nbsp;From
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_4" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_4');" style="cursor:pointer" />
                            <br />
                            <br />                                                
                            &nbsp;To
                            <br />
                            &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_4" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="../images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_4');" style="cursor:pointer" />
                            <br />
                            <br />
                            <table align="left" width="95" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        &nbsp;Departing
                                    </td>
                                    <td>
                                        &nbsp;Time
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_4" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="../images/calendar.gif" alt="" style="cursor:pointer" />
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Detail_Time1_Select_Type3_4" runat="server">
                                            <asp:ListItem Value="Any" Enabled="true">Any</asp:ListItem>
                                            <asp:ListItem Value="Morning">Morning</asp:ListItem>
                                            <asp:ListItem Value="Afternoon">Afternoon</asp:ListItem>
                                            <asp:ListItem Value="Evening">Evening</asp:ListItem>
                                            <asp:ListItem Value="1 am">1 am</asp:ListItem>
                                            <asp:ListItem Value="2 am">2 am</asp:ListItem>
                                            <asp:ListItem Value="3 am">3 am</asp:ListItem>
                                            <asp:ListItem Value="4 am">4 am</asp:ListItem>
                                            <asp:ListItem Value="5 am">5 am</asp:ListItem>
                                            <asp:ListItem Value="6 am">6 am</asp:ListItem>
                                            <asp:ListItem Value="7 am">7 am</asp:ListItem>
                                            <asp:ListItem Value="8 am">8 am</asp:ListItem>
                                            <asp:ListItem Value="9 am">9 am</asp:ListItem>
                                            <asp:ListItem Value="10 am">10 am</asp:ListItem>
                                            <asp:ListItem Value="11 am">11 am</asp:ListItem>
                                            <asp:ListItem Value="Noon">Noon</asp:ListItem>
                                            <asp:ListItem Value="1 pm">1 pm</asp:ListItem>
                                            <asp:ListItem Value="2 pm">2 pm</asp:ListItem>
                                            <asp:ListItem Value="3 pm">3 pm</asp:ListItem>
                                            <asp:ListItem Value="4 pm">4 pm</asp:ListItem>
                                            <asp:ListItem Value="5 pm">5 pm</asp:ListItem>
                                            <asp:ListItem Value="6 pm">6 pm</asp:ListItem>
                                            <asp:ListItem Value="7 pm">7 pm</asp:ListItem>
                                            <asp:ListItem Value="8 pm">8 pm</asp:ListItem>
                                            <asp:ListItem Value="9 pm">9 pm</asp:ListItem>
                                            <asp:ListItem Value="10 pm">10 pm</asp:ListItem>
                                            <asp:ListItem Value="11 pm">11 pm</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="height:20px">&nbsp;</td>
                    </tr>                                            
                    <tr>
                        <td>
                            <table align="left" width="300" cellpadding="0" cellspacing="0" border="0">
                                <tr align="left" style="height: 25px">
                                    <td>
                                        &nbsp;Adults 12+
                                    </td>
                                    <td>
                                        &nbsp;Children (2-11)
                                    </td>
                                    <td>
                                        &nbsp;Infants in laps
                                    </td>
                                </tr>
                                <tr align="left" style="height: 30px">
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Adults" runat="server">
                                            <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                            <asp:ListItem Value="2">2</asp:ListItem>
                                            <asp:ListItem Value="3">3</asp:ListItem>
                                            <asp:ListItem Value="4">4</asp:ListItem>
                                            <asp:ListItem Value="5">5</asp:ListItem>
                                            <asp:ListItem Value="6">6</asp:ListItem>
                                            <asp:ListItem Value="7">7</asp:ListItem>
                                            <asp:ListItem Value="8">8</asp:ListItem>
                                            <asp:ListItem Value="9">9</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>                                                        
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Children" runat="server">
                                            <asp:ListItem Value="0" Enabled="true">0</asp:ListItem>
                                            <asp:ListItem Value="1">1</asp:ListItem>
                                            <asp:ListItem Value="2">2</asp:ListItem>
                                            <asp:ListItem Value="3">3</asp:ListItem>
                                            <asp:ListItem Value="4">4</asp:ListItem>
                                            <asp:ListItem Value="5">5</asp:ListItem>
                                            <asp:ListItem Value="6">6</asp:ListItem>
                                            <asp:ListItem Value="7">7</asp:ListItem>
                                            <asp:ListItem Value="8">8</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Infants" runat="server">
                                            <asp:ListItem Value="0" Enabled="true">0</asp:ListItem>
                                            <asp:ListItem Value="1">1</asp:ListItem>
                                            <asp:ListItem Value="2">2</asp:ListItem>
                                            <asp:ListItem Value="3">3</asp:ListItem>
                                            <asp:ListItem Value="4">4</asp:ListItem>
                                        </asp:DropDownList>
                                    </td>
                                </tr>                                                    
                            </table>                                                
                        </td>
                    </tr>
                    <tr>
                        <td style="height:20px">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table align="left" width="300" cellpadding="0" cellspacing="0" border="0">
                                <tr align="left" style="height: 25px">
                                    <td>
                                        &nbsp;Airline
                                    </td>                                                                                           
                                </tr>
                                <tr align="left" style="height: 30px">
                                    <td>
                                        &nbsp;<asp:TextBox ID="LowFare_Airline_Text" runat="server" Width="500"></asp:TextBox>
                                    </td>                                    
                                </tr>                                
                                <tr align="left" style="height: 25px">
                                    <td>
                                        &nbsp;Class
                                    </td> 
                                </tr>                                
                                <tr align="left" style="height: 30px">                                                       
                                    <td>
                                        &nbsp;<asp:DropDownList ID="LowFare_Class" runat="server">
                                            <asp:ListItem Value="Economy/Coach" Enabled="true">Economy/Coach</asp:ListItem>
                                            <asp:ListItem Value="Premium Economy">Premium Economy</asp:ListItem>
                                            <asp:ListItem Value="Business Class">Business Class</asp:ListItem>
                                            <asp:ListItem Value="First Class">First Class</asp:ListItem>                                                                
                                        </asp:DropDownList>
                                    </td>                                                        
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="height:20px">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px">&nbsp;添加时间：<asp:Label ID="LowFare_AddTime" runat="server"></asp:Label></td>
                    </tr>                    
                    <tr>
                        <td align="left" style="height:25px">&nbsp;会员用户名：<asp:LinkButton ID="Member_Account" runat="server" CssClass="AdminToolsLink2"></asp:LinkButton></td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px">&nbsp;会员号：<asp:Label ID="Member_Serial" runat="server"></asp:Label></td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px"><asp:CheckBox ID="LowFare_Status" Text="是否已处理" runat="server" /></td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px">&nbsp;跟进客服用户名：<asp:Label ID="AdminUser_Name" runat="server"></asp:Label></td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px">&nbsp;跟进客服呢称：<asp:Label ID="AdminUser_NickName" runat="server"></asp:Label></td>
                    </tr>
                    <tr>
                        <td align="left" style="height:25px">&nbsp;已处理时间：<asp:Label ID="LowFare_SubmitTime" runat="server"></asp:Label></td>
                    </tr>
                    <tr>
                        <td align="left">
                            &nbsp;<asp:Button ID="LowFare_Submit" runat="server" Text=" 转已处理 " onclick="LowFare_Submit_Click" />
                            &nbsp;<asp:Button ID="LowFare_Delete" runat="server" Text=" 删除 " onclick="LowFare_Delete_Click" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form>
</body>
</html>
