<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Travel_Detail.aspx.cs" Inherits="Web.Travel_Detail" %>
<%@ Register src="Controls/Top.ascx" tagname="Top" tagprefix="Controls_Top" %>
<%@ Register src="Controls/Left.ascx" tagname="Left" tagprefix="Controls_Left" %>
<%@ Register src="Controls/Mid.ascx" tagname="Mid" tagprefix="Controls_Mid" %>
<%@ Register src="Controls/Tail.ascx" tagname="Tail" tagprefix="Controls_Tail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <script type="text/javascript">
    function MM_showHideLayers() 
    { 
        var i,p,v,obj,args=MM_showHideLayers.arguments;
        for (i=0; i<(args.length-2); i+=3) 
        {
            if ((obj=MM_findObj(args[i]))!=null) 
            { 
                v=args[i+2];
                if (obj.style) 
                { 
                    obj=obj.style; 
                    v=(v=='show')?'visible':(v='hide')?'hidden':v; 
                }
                
                obj.visibility=v; 
            }
        }
    }

    function MM_findObj(n, d) 
    {
        var p,i,x;
        if (!d) 
            d=document; 
            
        if ((p=n.indexOf("?"))>0&&parent.frames.length) 
        {
            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
        }
        
        if (!(x=d[n])&&d.all) 
            x=d.all[n];
            
        for (i=0;!x&&i<d.forms.length;i++) 
            x=d.forms[i][n];
        
        for (i=0;!x&&d.layers&&i<d.layers.length;i++) 
            x=MM_findObj(n,d.layers[i].document);
        
        if (!x && d.getElementById) 
            x=d.getElementById(n); 
        
        return x;
    }
    </script>
</head>
<body style="background:url(images/inside_12.jpg) repeat-x #e9faff top;">
    <form id="form1" runat="server">        
        <Controls_Top:Top ID="Web_Top" runat="server" />                     
        <div class="inside_content" style=" background-color:#ddf6fe">
	        <div class="inside_content_left"></div>
	        <div class="inside_content_center">
		        <table width="200" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="ddf6fe">
                    <tr>
                        <td align="left" valign="top" bgcolor="ddf6fe" height="10px">
                            <Controls_Left:Left ID="Web_Left" runat="server" />        		
		                </td>
			            <td rowspan="2" valign="top" bgcolor="#ddf6fe">
			                <div id="Travel_Lists" class="inside_content_rightanv" runat="server">
			                    <a href="#"><img src="images/inside_b.jpg" alt="" /></a>
			                    <div class="inside_content_rightlink">
				                    <span class="inside_content_rightlink2"></span>
				                    <span class="inside_content_rightlink3"><asp:HyperLink ID="HyperLink_Title" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink ID="HyperLink_Travel" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink ID="HyperLink_Travel_TypeID" runat="server" class="nav10"></asp:HyperLink></span>
				                    <span style="float:left; padding-top:10px;"><asp:HyperLink ID="HyperLink_Travel_TypeID_1" runat="server"></asp:HyperLink> <asp:Label ID="Label_Travel_TypeID" Text="|" runat="server"></asp:Label> <asp:HyperLink ID="HyperLink_Travel_TypeID_2" runat="server"></asp:HyperLink></span>
				                    <span class="inside_content_rightlink4"></span>				                    
				                </div>
				                <div class="inside2">
				                    <h1><strong id="Travel_Name" runat="server"></strong></h1>
				                    <div class="inside2_photo"><span id="Travel_PreView2" runat="server"></span></div>
				                    <p><strong>线路编号：</strong><span id="Travel_Code" runat="server"></span></p>				
                                    <p><strong>价格：</strong><span id="Travel_Price" runat="server"></span></p> 
                                    <p><strong>出发地：</strong><span id="Travel_StartAddr" runat="server"></span></p>
                                    <p><strong>结束地：</strong><span id="Travel_EndAddr" runat="server"></span></p>
                                    <p><strong>积分信息：</strong><span id="Travel_Points" runat="server"></span>个积分</p> 
                                    <p><strong>出团日期：</strong><span id="Travel_Date" runat="server"></span></p> 
                                    <p><strong>主要景点：</strong></p>
                                    <p id="Travel_Views" class="letter" runat="server"></p>
                                    <p><strong>详细行程：</strong></p> 
                                    <p id="Travel_Route" runat="server"></p>
                                    <p><strong>精彩瞬间：</strong>(点击图片放大)</p>			
			                    </div>			
			                    <div id="Travel_PreViews" class="inside2_photo_demo" style="position:relative;" runat="server">
			                        <%--<a href="#" title="景点图片" onclick="return false"><img name="small" src="images/inside2_photo_1.jpg" onclick="MM_showHideLayers('layer2','','show');MM_showHideLayers('layer','','hide');MM_showHideLayers('layer1','','hide');" alt=""/></a>
			                        <a href="#" title="景点图片" onclick="return false"><img name="small" src="images/inside2_photo_2.jpg" onclick="MM_showHideLayers('layer3','','show');MM_showHideLayers('layer','','hide');MM_showHideLayers('layer1','','hide');" alt=""/></a>
			                        <a href="#" title="景点图片" onclick="return false"><img name="small" src="images/inside2_photo_1.jpg" onclick="MM_showHideLayers('layer4','','show');MM_showHideLayers('layer','','hide');MM_showHideLayers('layer1','','hide');" alt=""/></a>
			                        <a href="#" title="景点图片" onclick="return false"><img name="small" src="images/inside2_photo_2.jpg" onclick="MM_showHideLayers('layer5','','show');MM_showHideLayers('layer','','hide');MM_showHideLayers('layer1','','hide');" alt=""/></a>			
			                        <div id="layer2" class="inside2_photo_layer"> 
                                        <a href="" onclick="return false"><img name="big" src="images/inside2_photo_1.jpg" onclick="MM_showHideLayers('layer2','','hide');MM_showHideLayers('layer','','show');MM_showHideLayers('layer1','','show');return false;" border="0" width="450" height="280" alt=""/><strong>点击图片关闭</strong></a> 
			                        </div>
			                        <div id="layer3" class="inside2_photo_layer"> 
                                        <a href="" onclick="return false"><img name="big" src="images/inside2_photo_2.jpg" onclick="MM_showHideLayers('layer3','','hide');MM_showHideLayers('layer','','show');MM_showHideLayers('layer1','','show');return false;" border="0" width="450" height="280" alt=""/><strong>点击图片关闭</strong></a> 
			                        </div>
			                        <div id="layer4" class="inside2_photo_layer"> 
                                        <a href="" onclick="return false"><img name="big" src="images/inside2_photo_1.jpg" onclick="MM_showHideLayers('layer4','','hide');MM_showHideLayers('layer','','show');MM_showHideLayers('layer1','','show');return false;" border="0" width="450" height="280" alt=""/><strong>点击图片关闭</strong></a> 
			                        </div>
			                        <div id="layer5" class="inside2_photo_layer"> 
                                        <a href="" onclick="return false"><img name="big" src="images/inside2_photo_2.jpg" onclick="MM_showHideLayers('layer5','','hide');MM_showHideLayers('layer','','show');MM_showHideLayers('layer1','','show');return false;" border="0" width="450" height="280" alt=""/><strong>点击图片关闭</strong></a> 
			                        </div>--%>			
			                    </div>			                			                	                    			                                     			
		                    </div>
		                </td>
                    </tr>
                    <tr>
                        <td align="left" valign="bottom" bgcolor="#ddf6fe"><img src="images/inside_6.jpg" width="254" alt="" /></td>
                    </tr>
                </table>        	
	        </div>
	        <div class="inside_content_right"></div>
	        <div style="clear:both;"></div>
	        <Controls_Tail:Tail ID="Web_Tail" runat="server" />
        </div>
    </form>
</body>
</html>
