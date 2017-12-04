//触发项目增加中的编辑框
var editor;
$(function(){
	
	KindEditor.ready(function(K){
		
		editor = K.create('textarea[name="content"]',{
			themeType:'simple',
			resizeType:2,
			uploadJson : 'kindEditor/jsp/upload_json.jsp',
			fileManagerJson : 'kindEditor/jsp/file_manager_json.jsp',
			allowFileManager:true,
			width: '100%',
			height: '300px',
			items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			//经测试，下面这行代码可有可无，不影响获取textarea的值
			afterCreate: function(){this.sync();},
			//下面这行代码就是关键的所在，当失去焦点时执行 this.sync();
			afterBlur:function(){this.sync();}
		});
		
		K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		
	});

	$('#addUserModal').off('shown.bs.modal').on('shown.bs.modal', function (e) {
	    $(document).off('focusin.modal');//解决编辑器弹出层文本框不能输入的问题
	});
});
