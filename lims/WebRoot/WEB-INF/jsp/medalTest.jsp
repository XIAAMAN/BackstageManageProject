<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<link type="text/css" href="${ctx}/css/uploadfile.css" rel="stylesheet" />  
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
</head>
<body>
	<h5>增加获奖成果</h5>
	<label>name 	: </label> <input type="text" id="name_save"/><br/>
	<label>photo  	: </label> <input type="text" id="photo_save" /><br/>
	<label>teacherId: </label> <input type="text" id="teacherId_save"/><br/>
	<label>type 	: </label> <input type="text" id="type_save"/><br/>
	<label>level 	: </label> <input type="text" id="level_save"/><br/>
	<label>grade 	: </label> <input type="text" id="grade_save"/><br/>
	<label>ranking 	: </label> <input type="text" id="ranking_save"/><br/>
	<label>winningTime 	: </label> <input type="text" id="winningTime_save"/><br/>
	<label>sponsor 	: </label> <input type="text" id="sponsor_save"/><br/>
	<label>abstracts		: </label> <input type="text" id="abstracts_save"/><br/>
	<form id="newsform" method="post"  enctype="multipart/form-data" >    
        <div id="fileuploader">上传照片</div>
    </form>
	<input type="submit" value="增加" onclick="save()" />
	<br>
	<br>
	<h5>通过id删除获奖成果</h5>
	<label>id		: </label> <input type="text" id="id_delete"/><br/>
	<input type="submit" value="删除" onclick="deleteById()">
	<br>
	<br>
	<h5>更新</h5>
	<label>id 	: </label> <input type="text" id="id_update"/><br/>
	<label>name 	: </label> <input type="text" id="name_update"/><br/>
	<label>photo  	: </label> <input type="text" id="photo_update" /><br/>
	<label>type 	: </label> <input type="text" id="type_update"/><br/>
	<label>level 	: </label> <input type="text" id="level_update"/><br/>
	<label>grade 	: </label> <input type="text" id="grade_update"/><br/>
	<label>ranking 	: </label> <input type="text" id="ranking_update"/><br/>
	<label>winningTime 	: </label> <input type="text" id="winningTime_update"/><br/>
	<label>sponsor 	: </label> <input type="text" id="sponsor_update"/><br/>
	<label>abstracts		: </label> <input type="text" id="abstracts_update"/><br/>
	<input type="submit" value="更新" onclick="update()">
	<br>
	<br>
	<h5>通过id查询获奖成果</h5>
	<label>id		: </label> <input type="text" id="id_select"/><br/>
	<input type="submit" value="删除" onclick="selectById()">
	<br>
	<br>
	<h5>动态查询_x</h5>
	<label>name : </label> <input type="text" id="name_list_Dyna"/>
	<label>teacherId : </label> <input type="text" id="teacherId_list_Dyna"/>
	<label>level : </label> <input type="text" id="level_list_Dyna"/>
	<label>type : </label> <input type="text" id="type_list_Dyna"/>
	<label>grade : </label> <input type="text" id="grade_list_Dyna"/>
	<label>ranking : </label> <input type="text" id="ranking_list_Dyna"/>
	<label>sponsor : </label> <input type="text" id="sponsor_list_Dyna"/>
	<label>startDate_s : </label> <input type="text" id="startDate_s_list_Dyna"/>
	<label>startDate_e : </label> <input type="text" id="startDate_e_list_Dyna"/>
	<label>pageIndex : </label> <input type="text" id="pageIndex_list_Dyna"/>
	<label>pageSize : </label> <input type="text" id="pageSize_list_Dyna"/>
	<input type="submit" value="查询" onclick="lists_map()">
	<br>
	<br>
</body>
<script type="text/javascript">
	function save(){
		$.ajax("${ctx}/medal/add",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"name"		:	$('#name_save').val(),
			  		"photo"		:	$('#photo_save').val(),
			  		"teacher"		:	{"id":$('#teacherId_save').val()},
			  		"level"		:	$('#level_save').val(),
			  		"type"		:	$('#type_save').val(),
			  		"grade"	:	$('#grade_save').val(),
			  		"ranking"	:	$('#ranking_save').val(),
			  		"winningTime"	:	$('#winningTime_save').val(),
			  		"sponsor"	:	$('#sponsor_save').val(),
			  		"abstracts"	:	$('#abstracts_save').val()
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		if (data.state == "0") {
			  			alert("注册成功!");
			  		} else {
			  			alert("注册失败!  错误:" + data.message);
			  		}
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	}
	
	function deleteById(){
	$.ajax("${ctx}/medal/delete",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
		  		"id"		:	$('#id_delete').val(),
		  		}),
		  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		  	// 请求成功后的回调函数。
		  	success: function(data){
		  		if (data.state == "0") {
		  			alert("已删除");
		  		} else {
		  			alert(data.message);
		  		}
		   	},
		   	error: function(){
				alert("您的请求有误，请正常操作或联系管理员");
		   }
		});
	}
	
	function update(){
	$.ajax("${ctx}/medal/update",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
		  		"id"		:	$('#id_update').val(),
		  		"name"		:	$('#name_update').val(),
		  		"photo"		:	$('#photo_update').val(),
		  		"level"		:	$('#level_update').val(),
		  		"type"		:	$('#type_update').val(),
		  		"grade"		:	$('#grade_update').val(),
		  		"ranking"	:	$('#ranking_update').val(),
		  		"winningTime"	:	$('#winningTime_update').val(),
		  		"sponsor"	:	$('#sponsor_update').val(),
		  		"abstracts"	:	$('#abstracts_update').val()
		  		}),
		  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		  	// 请求成功后的回调函数。
		  	success: function(data){
		  		if (data.state == "0") {
		  			alert("状态 " + data.state + " " + "修改成功! " + data.information.id + " " + data.information.name);
		  		} else {
		  			alert("状态 " + data.state + " " + "修改失败!  错误:" + data.message);
		  		}
		   	},
		   	error: function(){
				alert("您的请求有误，请正常操作或联系管理员");
		   	}
		});
	}
	
	function selectById(){
	$.ajax("${ctx}/medal/get",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
		  		"id"		:	$('#id_select').val(),
		  		}),
		  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		  	// 请求成功后的回调函数。
		  	success: function(data){
		  		if (data.state == "0") {
		  			alert(data.information.id + " " + data.information.name);
		  		} else {
		  			alert("用户不存在");
		  		}
		   	},
		   	error: function(){
				alert("您的请求有误，请正常操作或联系管理员");
		   }
		});
	}

	function lists_map(){
	$.ajax("${ctx}/medal/listDynaPageMap",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
		  		"name"		:	$('#name_list_Dyna').val(),
		  		"teacherId"	:	$('#teacherId_list_Dyna').val(),
		  		"level"		:	$('#level_list_Dyna').val(),
		  		"type"		:	$('#type_list_Dyna').val(),
		  		"grade"	:	$('#grade_list_Dyna').val(),
		  		"ranking"		:	$('#ranking_list_Dyna').val(),
		  		"sponsor"		:	$('#sponsor_list_Dyna').val(),
		  		"startDate_s"	:	$('#startDate_s_list_Dyna').val(),
		  		"startDate_e"	:	$('#startDate_e_list_Dyna').val(),
		  		"pageIndex"	:	$('#pageIndex_list_Dyna').val(),
		  		"pageSize"	:	$('#pageSize_list_Dyna').val()
		  		}),
		  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		  	// 请求成功后的回调函数。
		  	success: function(data){
		  		alert(data.rows.length + " 个项目")
		   	},
		   	error: function(){
				alert("您的请求有误，请正常操作或联系管理员");
		   }
		});
	}

	$(document).ready(function()
	{
	    $("#fileuploader").uploadFile({
	      	url:"${ctx}/enclosure/singleSave",     //文件上传url
		    fileName:"medal",               //提交到服务器的文件名
		    maxFileCount: 1,                //上传文件个数（多个时修改此处
		    returnType: 'json',              //服务返回数据
		    allowedTypes: 'jpg,jpeg,png,gif',  //允许上传的文件式
		    showDone: false,                     //是否显示"Done"(完成)按钮
		    showDelete: true,                  //是否显示"Delete"(删除)按钮
		    deleteCallback: function(data,pd)
		    {
		        //文件删除时的回调方法。
		        //如：以下ajax方法为调用服务器端删除方法删除服务器端的文件
		        $.ajax({
		            cache: false,
		            url: "${ctx}/enclosure/singleDelete",
		            type: "DELETE",
		            contentType: "application/json",
		            dataType: "json",
		           	data:JSON.stringify({"module" : "medal", "name" : data.information}),
		            success: function(data) 
		            {
		                if(data.state===0){
		                    pd.statusbar.hide();        //删除成功后隐藏进度条等
		                    $('#photo_save').val();  // 注册时用
		                    $('#photo_update').val();  // 修改时用
		                    alert("delete success");
		                 }else{
		                   	console.log(data.message);  //打印服务器返回的错误信息
		                 }
		            }
		        }); 
		    },
		    onSuccess: function(files,data,xhr,pd)
		    {
		        //上传成功后的回调方法。本例中是将返回的文件名保到一个hidden类开的input中，以便后期数据处理
		        if(data&&data.state===0){
		        	$('#photo_save').val(data.information);
		        	$('#photo_update').val(data.information);
		        	alert("upload success");
		        }
		    }
		});
	});
</script>
</html>