<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LowFare.aspx.cs" Inherits="Web.LowFare" %>
<%@ Register Src="Controls/Top.ascx" TagName="Top" TagPrefix="Controls_Top" %>
<%@ Register Src="Controls/Left.ascx" TagName="Left" TagPrefix="Controls_Left" %>
<%@ Register Src="Controls/Mid.ascx" TagName="Mid" TagPrefix="Controls_Mid" %>
<%@ Register Src="Controls/Tail.ascx" TagName="Tail" TagPrefix="Controls_Tail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/gray.css" rel="stylesheet" />
    <script type="text/javascript" src="Js/jquery.js"></script>
    <script type="text/javascript" src="Js/Ajax.js"></script>
    <script type="text/javascript" src="Js/lhgcalendar.js"></script>
    <script type="text/javascript">                
        function CityList(LowFare_TextBox)
        {
            if (LowFare_TextBox != "")
            {
                window.open('AirportList.aspx?City_Country=0&City_TextBox=' + LowFare_TextBox,'AirportList','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');            
            }
        }

        function AirlineList() 
        {
            window.open('AirlineList.aspx', 'AirlineList', 'toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=300px,height=360px');
        }
       
        function GetCity(Obj)
        {
            if (Obj == null)
                return;
            
            if (Obj.value != "")
            {
                var URL = "WebService/City.aspx";
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
    </script>
</head>
<body style="background: url(images/inside_12.jpg) repeat-x #e9faff top;">
    <form id="form1" runat="server">
    <Controls_Top:Top ID="Web_Top" runat="server" />
    <div class="inside_content" style="background-color: #ddf6fe">
        <div class="inside_content_left"></div>
        <div class="inside_content_center">
            <table width="200" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="ddf6fe">
                <tr>
                    <td align="left" valign="top" bgcolor="ddf6fe" height="10px">
                        <Controls_Left:Left ID="Web_Left" runat="server" />
                    </td>
                    <td rowspan="2" valign="top" bgcolor="#ddf6fe">
                        <div id="Content_Height" class="inside_content_rightanv" style="height:auto" runat="server">
                            <img src="images/inside_c.jpg" alt="" />
                            <div class="inside_content_rightlink">
                                <span class="inside_content_rightlink2"></span><span class="inside_content_rightlink3">
                                    <asp:HyperLink ID="HyperLink_Title" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink ID="HyperLink_LowFare" runat="server" class="nav10"></asp:HyperLink></span>
                                <span class="inside_content_rightlink4"></span>
                            </div>
                            <div class="inside2" id="LowFare_Tips" runat="server">
                                <div style="text-align: center">请先登录</div>
                            </div>
                            <div class="inside2" id="LowFare_Content" runat="server">
                                <div style="text-align: center">
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type1');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type1');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type1',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type1" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
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
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Returning_Text_Type1',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility2_Type1" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type2');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type2');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type2" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type2',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type2" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_1');" style="cursor:pointer" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_1" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_1');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_1" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type3_1',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type3_1" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
                                                            </asp:DropDownList>
                                                        </td>
                                                    </tr>                                                    
                                                </table>
                                                <br /><br /><br /><br />
                                                &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 2</span>
                                                <br />
                                                &nbsp;From
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_2');" style="cursor:pointer" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_2" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_2');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_2" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type3_2',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type3_2" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
                                                            </asp:DropDownList>
                                                        </td>
                                                    </tr>                                                    
                                                </table>
                                                <br /><br /><br /><br />
                                                &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 3</span>
                                                <br />
                                                &nbsp;From
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_3" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_3');" style="cursor:pointer" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_3" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_3');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_3" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type3_3',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type3_3" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
                                                            </asp:DropDownList>
                                                        </td>
                                                    </tr>                                                    
                                                </table>
                                                <br /><br /><br /><br />
                                                &nbsp;<span style="color: #3E4D74;font-weight: bold;">Flight 4</span>
                                                <br />
                                                &nbsp;From
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_4" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_From_Text_Type3_4');" style="cursor:pointer" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_4" Width="180" runat="server" onblur="GetCity(this);" MaxLength="200"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="CityList('LowFare_Detail_To_Text_Type3_4');" style="cursor:pointer" />
                                                <br />
                                                <br />
                                                <table align="left" width="350" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                        <td>
                                                            &nbsp;Departing
                                                        </td>
                                                        <td>
                                                            &nbsp;Time
                                                        </td>
                                                        <td>
                                                            &nbsp;Flexibility of day
                                                        </td>
                                                    </tr>
                                                    <tr style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type3_4" Text="mm/dd/yy" Width="100px" runat="server" MaxLength="30"></asp:TextBox>&nbsp;<img src="images/calendar.gif" alt="" onclick="lhgcalendar('LowFare_Detail_Departing_Text_Type3_4',this);" />
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
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Detail_Flexibility1_Type3_4" runat="server">
                                                                <asp:ListItem Value="1" Enabled="true">1</asp:ListItem>
                                                                <asp:ListItem Value="2">2</asp:ListItem>
                                                                <asp:ListItem Value="3">3</asp:ListItem>
                                                                <asp:ListItem Value="4">4</asp:ListItem>
                                                                <asp:ListItem Value="5">5</asp:ListItem>
                                                                <asp:ListItem Value="6">6</asp:ListItem>
                                                                <asp:ListItem Value="7">7</asp:ListItem>
                                                                <asp:ListItem Value="8">8</asp:ListItem>
                                                                <asp:ListItem Value="9">9</asp:ListItem>
                                                                <asp:ListItem Value="10">10</asp:ListItem>
                                                                <asp:ListItem Value="10+">10+</asp:ListItem>                                                                                                                       
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
                                                <table align="left" width="450" cellpadding="0" cellspacing="0" border="0">
                                                    <tr align="left" style="height: 25px">
                                                        <td>
                                                            &nbsp;Passengers
                                                        </td>                                                                                                               
                                                    </tr>
                                                    <tr align="left" style="height: 25px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Passengers" runat="server" Width="300" MaxLength="1024"></asp:TextBox>(as: Jack;Tom;Marry)
                                                        </td>                                                                                                               
                                                    </tr>
                                                    <tr align="left" style="height: 10px">
                                                        <td>
                                                            &nbsp;
                                                        </td>                                                                                                               
                                                    </tr>
                                                    <tr align="left" style="height: 25px">
                                                        <td>
                                                            &nbsp;Airline
                                                        </td>                                                                                                               
                                                    </tr>
                                                    <tr align="left" style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:TextBox ID="LowFare_Airline" Width="180" runat="server" MaxLength="1024" ReadOnly="true" Text="Search All Airlines"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="AirlineList();" style="cursor:pointer" />                                                      
                                                            <input type="checkbox" name="checkbox1" id="checkbox1" value="Air Canada" style="display:none" />
                                                            <input type="checkbox" name="checkbox2" id="checkbox2" value="Air China" style="display:none" />
                                                            <input type="checkbox" name="checkbox3" id="checkbox3" value="Air France" style="display:none" />
                                                            <input type="checkbox" name="checkbox4" id="checkbox4" value="Alaska Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox5" id="checkbox5" value="All Nippon Airways" style="display:none" />
                                                            <input type="checkbox" name="checkbox6" id="checkbox6" value="America West Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox7" id="checkbox7" value="American Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox8" id="checkbox8" value="Asiana" style="display:none" />
                                                            <input type="checkbox" name="checkbox9" id="checkbox9" value="British Airways" style="display:none" />
                                                            <input type="checkbox" name="checkbox10" id="checkbox10" value="Cathay Pacific" style="display:none" />
                                                            <input type="checkbox" name="checkbox11" id="checkbox11" value="China Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox12" id="checkbox12" value="China Eastern Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox13" id="checkbox13" value="China Southern Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox14" id="checkbox14" value="Continental" style="display:none" />
                                                            <input type="checkbox" name="checkbox15" id="checkbox15" value="Delta Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox16" id="checkbox16" value="Dragon Air" style="display:none" />
                                                            <input type="checkbox" name="checkbox17" id="checkbox17" value="Frontier Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox18" id="checkbox18" value="Hainan Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox19" id="checkbox19" value="Japan Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox20" id="checkbox20" value="KLM" style="display:none" />
                                                            <input type="checkbox" name="checkbox21" id="checkbox21" value="Korean Air" style="display:none" />
                                                            <input type="checkbox" name="checkbox22" id="checkbox22" value="Lufthansa" style="display:none" />
                                                            <input type="checkbox" name="checkbox23" id="checkbox23" value="Malysian Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox24" id="checkbox24" value="Northwest Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox25" id="checkbox25" value="Philippine Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox26" id="checkbox26" value="SAS" style="display:none" />
                                                            <input type="checkbox" name="checkbox27" id="checkbox27" value="Shanghai Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox28" id="checkbox28" value="Singapore Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox29" id="checkbox29" value="Spirit Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox30" id="checkbox30" value="Thai Airways Intl" style="display:none" />
                                                            <input type="checkbox" name="checkbox31" id="checkbox31" value="United Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox32" id="checkbox32" value="US Airways" style="display:none" />
                                                            <input type="checkbox" name="checkbox33" id="checkbox33" value="Vietnam Airlines" style="display:none" />
                                                            <input type="checkbox" name="checkbox34" id="checkbox34" value="Virgin Atlantic" style="display:none" />                                                            
                                                        </td>
                                                    </tr>
                                                    <tr align="left" style="height: 10px">
                                                        <td>
                                                            &nbsp;
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
                                            <td align="left">
                                                &nbsp;<asp:Button ID="LowFare_Submit" runat="server" Text="Find Low Fares" Width="200" Height="40" OnClick="LowFare_Submit_Click" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="height:160px">&nbsp;</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="bottom" bgcolor="#ddf6fe">
                        <img src="images/inside_6.jpg" width="254" alt="" />
                    </td>
                </tr>
            </table>
        </div>
        <div class="inside_content_right">
        </div>
        <div style="clear: both;">
        </div>
        <Controls_Tail:Tail ID="Web_Tail" runat="server" />
    </div>
    </form>
</body>
</html>
