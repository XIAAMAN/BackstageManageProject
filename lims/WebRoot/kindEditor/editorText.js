//var editor;
//KindEditor.ready(function(K) {
//	
//	editor = K.create('textarea[name="content"]', {
//		resizeType : 1,
//		allowPreviewEmoticons : false,
//		allowImageUpload : false,
//		
//		items : [
//			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
//			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
//			'insertunorderedlist', '|', 'emoticons', 'image', 'link']
//	});
//	
//	
//	
//});

KindEditor.ready(function(K){
	
	K.create('textarea[name="content"]', {
		themeType: 'simple',
		resizeType: 1,
		uploadJson: 'common/KEditor/upload_json.php',
		fileManagerJson: 'common/KEditor/file_manager_json.php',
		allowFileManager: true,
		//经测试，下面这行代码可有可无，不影响获取textarea的值
		//afterCreate: function(){this.sync();}
		//下面这行代码就是关键的所在，当失去焦点时执行 this.sync();
		afterBlur: function(){this.sync();}
	});
});