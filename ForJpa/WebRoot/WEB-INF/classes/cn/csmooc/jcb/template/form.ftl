<#escape x as (x)!?html>
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        
        <div class="ibox-content">
            <form id="postForm" method="post" class="form-horizontal" action="${module}/${entity}/${action}">
                    	<input type="hidden" name="oid" value="${entityId}" >
                
                <div class="form-group">
                	<label class="col-sm-2 control-label" for="name">${manager}名称</label>
                    <div class="col-sm-10"><input type="text" class="form-control" placeholder="${manager}" maxlength="20" id="name" name="name" value="${entityName}"></div>
                </div>
                <div class="hr-line-dashed"></div>
                
            </form>
        </div>
    </div>
</div>


<script type="text/javascript">

    $(document).ready(function () {
    	
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        
        
        
        //表单验证
        $("#postForm").validate({
            rules: {
            	name: {
                    required: true,
                    minlength: 2
                }
            }
        });
        
    });
        
    
    function submitForm(index){
    	if($("#postForm").valid()==false){
    		return;
    	}
    	var postData = $("#postForm").serializeArray();
		$.post($("#postForm").attr("action"), postData, function (json) {
            //var data = $.parseJSON(json);
            if (json.resultStatus) {
                //成功
                reloadGrid();
              //closeAllPopWin();
				layer.close(index); //如果设定了yes回调，需进行手工关闭
            } else {
            	//
            	layer.alert("失败了："+ json.resultMsg, {icon: 2});
            }
        }, "json");
    	
    }
</script>
</#escape>