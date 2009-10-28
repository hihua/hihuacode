function showShop(flag)
{
    if (flag==1)
    {
	    document.getElementById("arrowImg1").src="#";
	    document.getElementById("arrowImg2").src="#";	
	    document.getElementById("shop1").style.display="block"; 
	    document.getElementById("shop2").style.display="none"; 
    }
    else
    {
	    document.getElementById("arrowImg1").src="#";
	    document.getElementById("arrowImg2").src="#";	
	    document.getElementById("shop1").style.display="none";
 	    document.getElementById("shop2").style.display="block"; 
    }
}

function showSend()
{
	document.getElementById("cardImg").src="#";
	document.getElementById("send").style.display="block";
	document.getElementById("fill").style.display="none";
}

function showFill()
{
	document.getElementById("cardImg").src="#";
	document.getElementById("send").style.display="none";
	document.getElementById("fill").style.display="block";
}

var rollTime = 1;
function changeImg(flag)
{
    for(var i=1;i<4;i++)
    {
	    document.getElementById("rollImg"+i).style.display="none"; 	
        document.getElementById("img"+i).src="images/home_icon_"+i+".jpg";
    }
    document.getElementById("rollImg"+flag).style.display="block";
    document.getElementById("img"+flag).src="images/home_icons_"+flag+".jpg";
}

function AutoChangeRoll()
{	
	if (rollTime == 3)	  
	    rollTime = 1;
    else 
        rollTime++;
        
    changeImg(rollTime);
}

window.setInterval("AutoChangeRoll()", 2000);
