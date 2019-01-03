var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
        	var self = $(this), txt = self.attr('placeholder'); 
            var color = self.css("color");
            self.val(txt).css({"color":"#999"});
            //获取焦点
            self.focusin(function(e) {
            	if(self.val() == txt){
            		self.val("").css({"color":color});
                }//失去焦点
            }).focusout(function(e) {
                if(self.val() == ""){
                	self.val(txt).css({"color":"#999"});
                }
            });
            
        });
    }
};
