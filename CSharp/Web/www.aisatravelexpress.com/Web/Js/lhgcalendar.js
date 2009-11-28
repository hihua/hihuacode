
var ie = $.browser.msie, co = null, io = '';

var config = { dir : '', skin : 'gray' }

String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '');
}

function getet()
{
    if(ie) return window.event;
	var func = getet.caller;
	while( func != null )
	{
	    var arg = func.arguments[0];
		if( arg && (arg + '').indexOf('Event') >= 0 ) return arg;
		func = func.caller;
	}
	return null;
}

function getpos( o )
{
    var l, t;
	
	if( o.getBoundingClientRect )
	{
	    var el = o.getBoundingClientRect();
		var st = Math.max( document.documentElement.scrollTop, document.body.scrollTop );
		var sl = Math.max( document.documentElement.scrollLeft, document.body.scrollLeft );
		l = sl + el.left; t = st + el.top;
	}
	else
	{
	    l = o.offsetLeft; t = o.offsetTop;
		while( o = o.offsetParent )
		{
		    l += o.offsetLeft; t += o.offsetTop;
		}
	}
	
	return { x:l, y:t };
}

var calendar = function( s )
{
    var d = new Date();
	
	if( s && s.indexOf('-') >= 0 )
	{
	    var a = s.trim().split('-');
		d = new Date( a[0], a[1]-1, a[2] );
	}
	
	this.y = d.getFullYear();
	this.m = d.getMonth() + 1;
	this.d = d.getDate();
}

calendar.prototype =
{
    draw : function()
	{
	    var a = [], h = '', w = ['日','一','二','三','四','五','六'];
		var fd = new Date(this.y, this.m - 1, 1).getDay();
		var md = new Date(this.y, this.m, 0).getDate();
		
		for( var i = 1; i <= fd; i++ ) a.push(0);
		for( var i = 1; i <= md; i++ ) a.push(i);
			
		h += '<table cellspacing="0" cellpadding="0" id="calendar">' +
		     '<caption><div id="py" onclick="co.py();"><img height="1"/></div><div id="pm" onclick="co.pm();"><img height="1"/></div><div align="center">' + this.y + ' 年 ' + this.m + ' 月</div><div id="nm" onclick="co.nm();"><img height="1"/></div><div id="ny" onclick="co.ny();"><img height="1"/></div></caption>' +
		     '<thead><tr>';
		
		for( var i = 0; i < 7; i++ )
			h += '<td>' + w[i] + '</td>';
		
		h += '</tr></thead><tbody>';
		
		while( a.length )
		{
		    h += '<tr>';
			for( var i = 1; i <= 7; i++ )
			{
				if( a.length )
				{
				    var d = a.shift();
					if(d)
					{
					    h += '<td onmouseover="lhgcalendar_over(this)" onmouseout="lhgcalendar_out(this)"';
						if( d == this.d ) h += ' class="tday" '; else h += ' class="tdday" '; 
						h += 'onclick="co.setdate(this);">' + d + '</td>';
					}else h += '<td>&nbsp;</td>';
				}
			}
			h += '</tr>';
		}
		
		h += '</tbody></table>';
		return h;
	},
	
	crte : function()
	{
	    this.hide();
		var contain = document.createElement('div');
		var s = contain.style;
		s.visibility = 'hidden';
		s.position = 'absolute';
		s.top = '0px'; s.zIndex = 65530;
		contain.innerHTML = this.draw();
		contain.id = 'container';
		document.body.appendChild( contain );
		
		if(ie)
		{
			var ifm = document.createElement('iframe');
			var s = ifm.style;
			ifm.frameBorder = 0;
			ifm.height = (contain.clientHeight - 3) + 'px';
			s.visibility = 'inherit'; s.zIndex = -1;
			s.filter = 'alpha(opacity=0)';
			s.position = 'absolute'; s.top = '0px';
			s.width = $('#container').attr("offsetWidth");
			contain.insertAdjacentElement( 'afterBegin', ifm );
		}
	},
	
	hide : function()
	{
		//if($('container')){ document.body.removeChild($('container')); }
		$('#container').remove();
		var co = null;
	},
	
	py : function()
	{
	    this.predraw( new Date(this.y - 1, this.m - 1, 1) );
	},
	
	ny : function()
	{
	    this.predraw( new Date(this.y + 1, this.m - 1, 1) );
	},
	
	pm : function()
	{
	    this.predraw( new Date(this.y, this.m - 2, 1) );
	},
	
	nm : function()
	{
	    this.predraw( new Date(this.y, this.m, 1) );
	},
	
	predraw : function( d )
	{
	    this.y = d.getFullYear(); this.m = d.getMonth() + 1;
		//$('container').innerHTML = this.draw();
		$('#container').html(this.draw());
	},
	
	setdate : function( o )
	{
	    //if( $(io) ) $(io).value = this.y + '-' + this.m + '-' + o.innerHTML;
	    $(io).val(this.y + '-' + this.m + '-' + o.innerHTML);	    	    	    
		this.hide();
	}
};

function lhgcalendar( id,e)
{
	var evt = getet();
	//var e = evt.srcElement || evt.target;
	var d = id ? $(id).value : e.value;
	//io = id ? id : e.id;
	io = '#' + id;
		
	var c = new calendar( d ); co = c; c.crte();

	var pos = getpos(e);
	var l, t, w = e.offsetWidth, h = e.offsetHeight;
	if( config.dir == 'right' )
	{
	    l = pos.x + w; t = pos.y;
		l = ie ? l : l+2; t = ie ? t-2 : t;
	}
	else
	{
	    l = pos.x; t = pos.y + h;
		l = ie ? l-2 : l; t = ie ? t-1 : t+1;
	}
		
	//var o = $('container');
	//o.style.top = t + 'px';
	//o.style.left = l + 'px';
	//o.style.visibility = 'visible';
	
	$('#container').css('top', t + 'px');
	$('#container').css('left', l + 'px');
	$('#container').css('visibility', 'visible');
	
	if(ie)
	{
	    document.attachEvent( 'onclick', c.hide );
		//o.attachEvent( 'onclick', function(e){e.cancelBubble = true;} );
		$('#container').click( function(e){e.cancelBubble = true;} );
	}
	else
	{
	    document.addEventListener( 'click', c.hide, false );
		//o.addEventListener( 'click', function(e){e.cancelBubble = true;}, false );
		$('#container').click( function(e){e.cancelBubble = true;} );
	}
	
	evt.cancelBubble = true;
}

function lhgcalendar_over( o )
{
    if( o.className != 'tday' ) o.className = 'over';
}

function lhgcalendar_out( o )
{
    if( o.className != 'tday' ) o.className = 'tdday';
}