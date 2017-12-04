<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<link type="text/css" href="${ctx}/css/uploadfile.css" rel="stylesheet" />  
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
</head>
<body>

<h5>增加论文</h5>
	<label>name 		: </label> <input type="text" id="name_save"/><br/>
	<label>author		: </label> <input type="text" id="author_save"/><br/>
	<label>authorComm 	: </label> <input type="text" id="authorComm_save"/><br/>
	<label>authorAll	: </label> <input type="text" id="authorAll_save"/><br/>
	<label>publishTime	: </label> <input type="text" id="publishTime_save"/><br/>
	<label>paperType	: </label> <input type="text" id="paperType_save"/><br/>
	<label>indexType	: </label> <input type="text" id="indexType_save"/><br/>
	<label>journal		: </label> <input type="text" id="journal_save"/><br/>
	<label>vol			: </label> <input type="text" id="vol_save"/><br/>
	<label>abstracts	: </label> <input type="text" id="abstracts_save"/><br/>
	<input type="submit" value="增加" onclick="add()" />
	<br>
	<br>
	<form id="newsform" method="post"  enctype="multipart/form-data" >    
        <input type="hidden" name="columnId" value="${columnId }">
        <input type="hidden" name="state" id="state" >
        <div id="fileuploader">上传附件</div>
    </form>
    <br>
	<br>
    <h5>删除论文</h5>
   	<label>id	: </label> <input type="text" id="id_delete"/><br/>
	<input type="submit" value="删除" onclick="deletes()" />
	<br>
	<br>
	<h5>修改论文</h5>
	<label>id 			: </label> <input type="text" id="id_update"/><br/>
	<label>name 		: </label> <input type="text" id="name_update"/><br/>
	<label>author		: </label> <input type="text" id="author_update"/><br/>
	<label>authorComm 	: </label> <input type="text" id="authorComm_update"/><br/>
	<label>authorAll	: </label> <input type="text" id="authorAll_update"/><br/>
	<label>publishTime	: </label> <input type="text" id="publishTime_update"/><br/>
	<label>paperType	: </label> <input type="text" id="paperType_update"/><br/>
	<label>indexType	: </label> <input type="text" id="indexType_update"/><br/>
	<label>journal		: </label> <input type="text" id="journal_update"/><br/>
	<label>vol			: </label> <input type="text" id="vol_update"/><br/>
	<label>abstracts	: </label> <input type="text" id="abstracts_update"/><br/>
	<input type="submit" value="修改" onclick="updates()" />
	<br>
	<br>
	<h5>查询公告（目前用这个）</h5>
	<label>name 		: </label> <input type="text" id="name_list_Dyna"/><br/>
	<label>author    	: </label> <input type="text" id="author_list_Dyna"/><br/>
	<label>authorComm 	: </label> <input type="text" id="authorComm_list_Dyna"/><br/>
	<label>paperType	: </label> <input type="text" id="paperType_list_Dyna"/><br/>
	<label>indexType	: </label> <input type="text" id="indexType_list_Dyna"/><br/>
	<label>journal		: </label> <input type="text" id="journal_list_Dyna"/><br/>
	<label>vol			: </label> <input type="text" id="vol_list_Dyna"/><br/>
	<label>startDate_s  : </label> <input type="text" id="startDate_s_list_Dyna"/><br/>
	<label>startDate_e  : </label> <input type="text" id="startDate_e_list_Dyna"/><br/>
	<label>pageIndex 	: </label> <input type="text" id="pageIndex_list_Dyna"/><br/>
	<label>pageSize	: </label> <input type="text" id="pageSize_list_Dyna"/><br/>
	<input type="submit" value="查询" onclick="lists_dynamap()" />
	<br>
	<br>
	<h5>查询公告id</h5>
   	<label>id	: </label> <input type="text" id="id_select"/><br/>
	<input type="submit" value="查询" onclick="selects()" />
</body>
<script type="text/javascript">
var fileIds = new Array();

function add(){
$.ajax("${pageContext.request.contextPath}/paper/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"param"	: {
		  		"name"		:	$('#name_save').val(),
		  		"author"	:	$('#author_save').val(),
		  		"authorComm"	:	$('#authorComm_save').val(),
		  		"authorAll"		:	$('#authorAll_save').val(),
		  		"publishTime" : $('#publishTime_save').val(),
		  		"paperType"		:	$('#paperType_save').val(),
		  		"indexType"		:	$('#indexType_save').val(),
		  		"journal"		:	$('#journal_save').val(),
		  		"vol"			:	$('#vol_save').val(),
		  		"abstracts"		:	$('#abstracts_save').val(),
		  	},
		  	"fileIds" : fileIds
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			fileIds = [];
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

function deletes(){
$.ajax("${pageContext.request.contextPath}/paper/delete",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"id" : $('#id_delete').val()
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("删除成功!");
	  		} else {
	  			alert("删除失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

function updates(){
$.ajax("${ctx}/paper/update",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
	  		"param"	: {
		  		"id"			:	$('#id_update').val(),
	  			"name"			:	$('#name_update').val(),
		  		"author"		:	$('#author_update').val(),
		  		"authorComm"	:	$('#authorComm_update').val(),
		  		"authorAll"		:	$('#authorAll_update').val(),
		  		"publishTime" 	:	$('#publishTime_update').val(),
		  		"paperType"		:	$('#paperType_update').val(),
		  		"indexType"		:	$('#indexType_update').val(),
		  		"journal"		:	$('#journal_update').val(),
		  		"vol"			:	$('#vol_update').val(),
		  		"abstracts"		:	$('#abstracts_update').val(),
		  	},
		  	"fileIds" : fileIds
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			fileIds = [];
	  			alert("修改成功!");
	  		} else {
	  			alert("修改失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

function selects(){
$.ajax("${pageContext.request.contextPath}/paper/get",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"id" : $('#id_select').val()
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("查询成功!");
	  		} else {
	  			alert("查询失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

function lists_dynamap(){
	$.ajax("${pageContext.request.contextPath}/paper/listDynaPageMap",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
		  		"name"			:	$('#name_list_Dyna').val(),
		  		"author"		:	$('#author_list_Dyna').val(),
		  		"authorComm"	:	$('#authorComm_list_Dyna').val(),
		  		"paperType"		:	$('#paperType_list_Dyna').val(),
		  		"indexType"		:	$('#indexType_list_Dyna').val(),
		  		"journal"		:	$('#journal_list_Dyna').val(),
		  		"vol"			:	$('#vol_list_Dyna').val(),
		  		"startDate_s"	:	$('#startDate_s_list_Dyna').val(),
		  		"startDate_e"	:	$('#startDate_e_list_Dyna').val(),
		  		"pageIndex"		:	$('#pageIndex_list_Dyna').val(),
		  		"pageSize"		:	$('#pageSize_list_Dyna').val()
		  	}),
		  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		  	// 请求成功后的回调函数。
		  	success: function(data){
		  			alert("成功!" + data.total);
		   	},
		   	error: function(){
				alert("您的请求有误，请正常操作或联系管理员");
		   }
	});
}

$(document).ready(function() {
    $("#fileuploader").uploadFile({
        url:"${ctx}/enclosure/upload", //后台处理方法
        fileName:"paper",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
        dragDrop:true,  //可以取消
        abortStr:"取消",
        sequential:true,  //按顺序上传
        sequentialCount:1,  //按顺序上传
        autoSubmit :"false",  //取消自动上传
        //acceptFiles:"application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword" , //限制上传文件格式
        extErrorStr:"上传文件格式不对",
        maxFileCount:5,       //上传文件数量
        //maxFileSize:10*1024*1024, //大小限制10M
        //sizeErrorStr:"上传文件不能大于1M", 
        dragDropStr: "<span><b>附件拖放于此</b></span>",
        showFileCounter:false,
        returnType:"json",  //返回数据格式为json
        onSuccess:function(files,data,xhr,pd) {
            //将返回的上传文件id动态加入的表单中，用于提交表单时返回给后台。
            //var newsform = $("#newsform");
           	if(data.state==0){
                   fileIds.push(data.information.id)
            }else{
                alert("上传失败");
            }
			
        },
        
        showDelete: true,//删除按钮
        statusBarWidth:600,
        dragdropWidth:600,
        //删除按钮执行的方法
        deleteCallback: function (data, pd) {
            var fileId=data.data[0].fileId;
            alert(fileId)
            $.post("${ctx}/file/notice/delete", {fileId:fileId},
                    function (resp,textStatus, jqXHR) {
                        alert("delete ok");
            })
            .success(function() { alert("second success"); })
   			.error(function() { alert("error"); });
            //删除input标签
            $("#"+fileId).remove();
            pd.statusbar.hide(); //You choice.
        }
    });
});


</script>
</html>