;
$(document).ready(function(){
	$("#roleAssign,#roleAssignBtn").click(function(){
		top.$.jBox.open("iframe:a/sys/role/usertorole?id=1", "分配角色",810,$(top.document).height()-240,{
			buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配角色。",submit:function(v, h, f){
				var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
				var ids = h.find("iframe")[0].contentWindow.ids;
				//nodes = selectedTree.getSelectedNodes();
				if (v=="ok"){
					// 删除''的元素
					if(ids[0]==''){
						ids.shift();
						pre_ids.shift();
					}
					if(pre_ids.sort().toString() == ids.sort().toString()){
						top.$.jBox.tip("未给角色分配新成员！", 'info');
						return false;
					};
			    	// 执行保存
			    	//loading('正在提交，请稍等...');
			    	var idsArr = "";
			    	for (var i = 0; i<ids.length; i++) {
			    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
			    	}
			    	
			    	// console.log("users:" + idsArr);
			    	
			    	$('#roleAssign').val(idsArr);
			    	//$('#assignRoleForm').submit();
			    	return true;
				} else if (v=="clear"){
					h.find("iframe")[0].contentWindow.clearAssign();
					return false;
                }
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
});