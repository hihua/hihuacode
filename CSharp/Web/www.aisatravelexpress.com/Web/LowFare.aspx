﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LowFare.aspx.cs" Inherits="Web.LowFare" %>

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
</head>
<body style="background: url(images/inside_12.jpg) repeat-x #e9faff top;">
    <form id="form1" runat="server">
    <Controls_Top:Top ID="Web_Top" runat="server" />
    <div class="inside_content" style="background-color: #ddf6fe">
        <div class="inside_content_left">
        </div>
        <div class="inside_content_center">
            <table width="200" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="ddf6fe">
                <tr>
                    <td align="left" valign="top" bgcolor="ddf6fe" height="10px">
                        <Controls_Left:Left ID="Web_Left" runat="server" />
                    </td>
                    <td rowspan="2" valign="top" bgcolor="#ddf6fe">
                        <div class="inside_content_rightanv" style="height: 1100px;">
                            <img src="images/inside_c.jpg" alt="" />
                            <div class="inside_content_rightlink">
                                <span class="inside_content_rightlink2"></span><span class="inside_content_rightlink3">
                                    <asp:HyperLink ID="HyperLink_Title" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink
                                        ID="HyperLink_LowFare" runat="server" class="nav10"></asp:HyperLink></span>
                                <span class="inside_content_rightlink4"></span>
                            </div>
                            <div class="inside2" id="LowFare_Tips" runat="server">
                                <div style="text-align: center">
                                    请先登录</div>
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
                                        <tr id="LowFare_Flexibility_TD" runat="server">
                                            <td align="left" style="height: 50px">
                                                <asp:CheckBox ID="LowFare_Flexibility" Text="Flexibility of +/- 1 day" runat="server" Checked="true" />
                                            </td>
                                        </tr>
                                        <tr id="LowFare_Text_Type1" runat="server">
                                            <td align="left" style="height: 50px">
                                                &nbsp;From
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type1" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type1" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type1" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type1" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Departing_Image_Type1" ImageUrl="images/calendar.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type1" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Returning_Image_Type1" ImageUrl="images/calendar.gif" runat="server" />
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type2" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type2" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type2" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type2" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Departing_Text_Type2" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="Image4" ImageUrl="images/calendar.gif" runat="server" />
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_1" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type3_1" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_1" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type3_1" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type3_1" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Returning_Image_Type3_1" ImageUrl="images/calendar.gif" runat="server" />
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_2" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type3_2" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_2" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type3_2" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type3_2" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Returning_Image_Type3_2" ImageUrl="images/calendar.gif" runat="server" />
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_3" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type3_3" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_3" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type3_3" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type3_3" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Returning_Image_Type3_3" ImageUrl="images/calendar.gif" runat="server" />
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
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_From_Text_Type3_4" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_From_List_Type3_4" ImageUrl="images/button_list.gif" runat="server" />
                                                <br />
                                                <br />                                                
                                                &nbsp;To
                                                <br />
                                                &nbsp;<asp:TextBox ID="LowFare_Detail_To_Text_Type3_4" Width="180" runat="server"></asp:TextBox><asp:Image ID="LowFare_Detail_To_List_Type3_4" ImageUrl="images/button_list.gif" runat="server" />
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
                                                            &nbsp;<asp:TextBox ID="LowFare_Detail_Returning_Text_Type3_4" Text="mm/dd/yy" Width="100px" runat="server"></asp:TextBox>&nbsp;<asp:Image ID="LowFare_Detail_Returning_Image_Type3_4" ImageUrl="images/calendar.gif" runat="server" />
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
                                                        <td>
                                                            &nbsp;Class
                                                        </td>                                                        
                                                    </tr>
                                                    <tr align="left" style="height: 30px">
                                                        <td>
                                                            &nbsp;<asp:DropDownList ID="LowFare_Airline" runat="server">
                                                                <asp:ListItem Value="" Enabled="true">Search All Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Air Canada">Air Canada</asp:ListItem>
                                                                <asp:ListItem Value="Air China">Air China</asp:ListItem>
                                                                <asp:ListItem Value="Air France">Air France</asp:ListItem>
                                                                <asp:ListItem Value="Alaska Airlines">Alaska Airlines</asp:ListItem>
                                                                <asp:ListItem Value="All Nippon Airways">All Nippon Airways</asp:ListItem>
                                                                <asp:ListItem Value="America West Airlines">America West Airlines</asp:ListItem>
                                                                <asp:ListItem Value="American Airlines">American Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Asiana">Asiana</asp:ListItem>
                                                                <asp:ListItem Value="British Airways">British Airways</asp:ListItem>
                                                                <asp:ListItem Value="Cathay Pacific">Cathay Pacific</asp:ListItem>
                                                                <asp:ListItem Value="China Airlines">China Airlines</asp:ListItem>
                                                                <asp:ListItem Value="China Eastern Airlines">China Eastern Airlines</asp:ListItem>
                                                                <asp:ListItem Value="China Southern Airlines">China Southern Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Continental">Continental</asp:ListItem>
                                                                <asp:ListItem Value="Delta Airlines">Delta Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Dragon Air">Dragon Air</asp:ListItem>
                                                                <asp:ListItem Value="Frontier Airlines">Frontier Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Hainan Airlines">Hainan Airlines</asp:ListItem>                                                                
                                                                <asp:ListItem Value="Japan Airlines">Japan Airlines</asp:ListItem>
                                                                <asp:ListItem Value="KLM">KLM</asp:ListItem>
                                                                <asp:ListItem Value="Korean Air">Korean Air</asp:ListItem>
                                                                <asp:ListItem Value="Lufthansa">Lufthansa</asp:ListItem>
                                                                <asp:ListItem Value="Malysian Airlines">Malysian Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Northwest Airlines">Northwest Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Philippine Airlines">Philippine Airlines</asp:ListItem>
                                                                <asp:ListItem Value="SAS">SAS</asp:ListItem>
                                                                <asp:ListItem Value="Shanghai Airlines">Shanghai Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Singapore Airlines">Singapore Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Spirit Airlines">Spirit Airlines</asp:ListItem>                                                                
                                                                <asp:ListItem Value="Thai Airways Intl">Thai Airways Intl</asp:ListItem>
                                                                <asp:ListItem Value="United Airlines">United Airlines</asp:ListItem>
                                                                <asp:ListItem Value="US Airways">US Airways</asp:ListItem>
                                                                <asp:ListItem Value="Vietnam Airlines">Vietnam Airlines</asp:ListItem>
                                                                <asp:ListItem Value="Virgin Atlantic">Virgin Atlantic</asp:ListItem>                                                                                                                                
                                                            </asp:DropDownList>
                                                        </td>                                                        
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
