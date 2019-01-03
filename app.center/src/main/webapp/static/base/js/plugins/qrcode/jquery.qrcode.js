/**
 * 修改过加了title
 */
(function( $ ){
	$.fn.qrcode = function(options) {
		// if options is string, 
		if( typeof options === 'string' ){
			options	= { text: options };
		}

		//标题超出28个字符则自动截取
		if(options.title.length > 28){
			options.title =  options.title.substring(0,25).concat("...");
		}
		var titleH = document.createElement('canvas').getContext('2d').measureText(options.title).width > options.width -20 ? 50 : 30;
		// set default values
		// typeNumber < 1 for automatic calculation
		options	= $.extend( {}, {
			render		: "canvas",
			width		: 256,
			height		: 256,
			titleH		: titleH,
			typeNumber	: -1,
			correctLevel	: QRErrorCorrectLevel.H,
                        background      : "#ffffff",
                        foreground      : "#000000"
		}, options);

		var createCanvas	= function(){
			// create the qrcode itself
			var qrcode	= new QRCode(options.typeNumber, options.correctLevel);
			qrcode.addData(options.text);
			qrcode.make();

			// create canvas element
			var canvas	= document.createElement('canvas');
			var titleH = options.titleH;
			canvas.width	= options.width;
			canvas.height	= options.height + titleH;
			var ctx		= canvas.getContext('2d');
			// compute tileW/tileH based on options.width/options.height
			var tileW	= options.width  / qrcode.getModuleCount();
			var tileH	= options.height / qrcode.getModuleCount();
			
			//draw title
			var title = options.title;
			ctx.textAlign = "center";
			var textWidth = ctx.measureText(title).width;
			ctx.font="17px microsoft yahei bold";
			if(title.length  <= 15){
				ctx.fillText(title, 100, 18, options.width - 20);
			}else{
				//标题分2行自动判哪个断截取位置，绘制的两行长度最相近
				var map = new HashMap();
				for(var i = 0; i < title.length - 1; i++){
					var width1 = ctx.measureText(title.substring(0,i)).width ;
					var width2 = ctx.measureText(title.substring(i)).width ;
					var dist = Math.abs(width1 - width2);
					map.put(dist, i);
				}
				var key_min = Math.min.apply(null, map.keys());
				var len = map.get(key_min);
				
				ctx.fillText(title.substring(0,len), 100, 18, options.width - 20);
				ctx.fillText(title.substring(len), 100, 36, options.width - 20);
			}
			
			//ctx.fillText(options.title, 100, 18, options.width - 20);
			// draw in the canvas
			for( var row = 0; row < qrcode.getModuleCount(); row++ ){
				for( var col = 0; col < qrcode.getModuleCount(); col++ ){
					ctx.fillStyle = qrcode.isDark(row, col) ? options.foreground : options.background;
					var w = (Math.ceil((col+1)*tileW) - Math.floor(col*tileW));
					var h = (Math.ceil((row+1)*tileW) - Math.floor(row*tileW));
					ctx.fillRect(Math.round(col*tileW),Math.round(row*tileH) + titleH, w, h);  
				}	
			}
			// return just built canvas
			return canvas;
		}

		// from Jon-Carlos Rivera (https://github.com/imbcmdth)
		var createTable	= function(){
			// create the qrcode itself
			var qrcode	= new QRCode(options.typeNumber, options.correctLevel);
			qrcode.addData(options.text);
			qrcode.make();
			
			// create table element
			var $table	= $('<table></table>')
				.css("width", options.width+"px")
				.css("height", options.height+"px")
				.css("border", "0px")
				.css("border-collapse", "collapse")
				.css('background-color', options.background);
		  
			// compute tileS percentage
			var tileW	= options.width / qrcode.getModuleCount();
			var tileH	= options.height / qrcode.getModuleCount();

			// draw in the table
			for(var row = 0; row < qrcode.getModuleCount(); row++ ){
				var $row = $('<tr></tr>').css('height', tileH+"px").appendTo($table);
				
				for(var col = 0; col < qrcode.getModuleCount(); col++ ){
					$('<td></td>')
						.css('width', tileW+"px")
						.css('background-color', qrcode.isDark(row, col) ? options.foreground : options.background)
						.appendTo($row);
				}	
			}
			// return just built canvas
			return $table;
		}
  

		return this.each(function(){
			var element	= options.render == "canvas" ? createCanvas() : createTable();
			//$(element).appendTo(this); 改成下面防止出现多张图片
			$(this).html($(element));
			
		});
	};
})( jQuery );
