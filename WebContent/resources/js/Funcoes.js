
function abrePopUp(parquivo, ptitle, pheight, pwidth, pmodal) {

	var div = $("<div></div>");
	
   	var dialogOptions = {
			"title" : ptitle,
			"width" : pwidth,
			"height" : pheight,
			"modal" : pmodal,
			"resizable" : true,
			"close" : function() {
				$(this).remove();
			}
	};
	
	var dialogExtendOptions = {
		      "closable" : true,
		      "maximizable" : true,
		      "minimizable" : true,
		      "collapsable" : false,
		      "dblclick" : "collapse",
		      "icons" : {
		          "close" : "ui-icon-circle-close",
		          "maximize" : "ui-icon-circle-plus",
		          "minimize" : "ui-icon-circle-minus",
		          "collapse" : "ui-icon-triangle-1-s",
		          "restore" : "ui-icon-bullet"
		        },
			  "load" : function(evt, dlg) {
					div.html('<iframe id="iframe" name="iframe" style="border: 0px;" src="'
							+ parquivo
							+ '" width="100%" height="100%"></iframe>')
				},
			      "beforeCollapse" : function(evt, dlg){  },
			      "beforeMaximize" : function(evt, dlg){  },
			      "beforeMinimize" : function(evt, dlg){  },
			      "beforeRestore" : function(evt, dlg){  },
			      "collapse" : function(evt, dlg){  },
			      "maximize" : function(evt, dlg){  },
			      "minimize" : function(evt, dlg){  },
			      "restore" : function(evt, dlg){  }
	};
	
	
	div = div.dialog(dialogOptions).dialogExtend(dialogExtendOptions);
	

}
