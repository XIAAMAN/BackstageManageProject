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
	<h5>增加新闻</h5>
	<label>title 	: </label> <input type="text" id="title_save"/><br/>
	<label>author: </label> <input type="text" id="author_save"/><br/>
	<form id="form" method="post"  enctype="multipart/form-data" >    
        <div id="photouploader">上传照片</div>
    </form>
	<label>photo  	: </label> <input type="text" id="photo_save" /><br/>
	<label>content 	: </label> <input type="text" id="content_save"/><br/>
	<label>keyword	: </label> <input type="text" id="keyword_save"/><br/>
	<label>seq	: </label> <input type="text" id="seq_save"/><br/>
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
	<h5>删除新闻</h5>
   	<label>id	: </label> <input type="text" id="id_delete"/><br/>
	<input type="submit" value="删除" onclick="deletes()" />
	<br>
	<br>
	<h5>修改新闻</h5>
	<label>id 	: </label> <input type="text" id="id_update"/><br/>
	<label>title 	: </label> <input type="text" id="title_update"/><br/>
	<label>author: </label> <input type="text" id="author_update"/><br/>
	<label>photo: </label> <input type="text" id="photo_update"/><br/>
	<label>content 	: </label> <input type="text" id="content_update"/><br/>
	<label>keyword 	: </label> <input type="text" id="keyword_update"/><br/>
	<input type="submit" value="修改" onclick="updates()" />
	<br>
	<br>
	<h5>修改置顶状态seq</h5>
   	<label>id	: </label> <input type="text" id="id_updateSeq"/><br/>
	<input type="submit" value="修改" onclick="updateSeq()" />
	<br>
	<br>
	<h5>查询新闻（目前用这个）</h5>
	<label>title 	: </label> <input type="text" id="title_list_Dyna"/><br/>
	<label>author    	: </label> <input type="text" id="author_list_Dyna"/><br/>
	<label>keyword    	: </label> <input type="text" id="keyword_list_Dyna"/><br/>
	<label>seq    	: </label> <input type="text" id="seq_list_Dyna"/><br/>
	<label>startDate_s    	: </label> <input type="text" id="startDate_s_list_Dyna"/><br/>
	<label>startDate_e    	: </label> <input type="text" id="startDate_e_list_Dyna"/><br/>
	<label>pageIndex 	: </label> <input type="text" id="pageIndex_list_Dyna"/><br/>
	<label>pageSize	: </label> <input type="text" id="pageSize_list_Dyna"/><br/>
	<input type="submit" value="查询" onclick="lists_dynamap()" />
	<br>
	<br>
	<h5>查询新闻id</h5>
   	<label>id	: </label> <input type="text" id="id_select"/><br/>
	<input type="submit" value="查询" onclick="selects()" />
	<br>
	<br>
</body>
<script type="text/javascript">
var fileIds = new Array();

function add(){
$.ajax("${ctx}/news/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"param"	: {
		  		"title"		:	$('#title_save').val(),
		  		"author"	:	$('#author_save').val(),
		  		"photo"		:	$('#photo_save').val(),
		  		"content"	:	$('#content_save').val(),
		  		"seq"		:	$('#seq_save').val(),
		  		"keyword"	:	$('#keyword_save').val()
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
$.ajax("${ctx}/news/delete",
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
$.ajax("${ctx}/news/update",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
	  		"param"	: {
		  		"id"		:	$('#id_update').val(),
	  			"title"		:	$('#title_update').val(),
	  			"author"	:	$('#author_update').val(),
	  			"photo"		:	$('#photo_update').val(),
	  			"content"	:	$('#content_update').val(),
	  			"keyword"	:	$('#keyword_update').val()
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
	  			alert("修改 失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

function updateSeq(){
$.ajax("${ctx}/news/updateSeq",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
	  		"id"		:	$('#id_updateSeq').val()
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("修改成功!");
	  		} else {
	  			alert("修改 失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

function selects(){
$.ajax("${pageContext.request.contextPath}/news/get",
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
	$.ajax("${pageContext.request.contextPath}/news/listDynaPageMap",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
	  			"id"		:	$('#id_list_Dyna').val(),
		  		"title"		:	$('#title_list_Dyna').val(),
		  		"author"	:	$('#author_list_Dyna').val(),
		  		"keyword"	:	$('#keyword_list_Dyna').val(),
		  		"seq"		:	$('#seq_list_Dyna').val(),
		  		"startDate_s"	:	$('#startDate_s_list_Dyna').val(),
		  		"startDate_e"		:	$('#startDate_e_list_Dyna').val(),
		  		"pageIndex"	:	$('#pageIndex_list_Dyna').val(),
		  		"pageSize"	:	$('#pageSize_list_Dyna').val()
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


// 多文件上传
$(document).ready(function() {
    $("#fileuploader").uploadFile({
        url:"${ctx}/enclosure/upload", //后台处理方法
        fileName:"news",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
        dragDrop:true,  //可以取消
        abortStr:"取消",
        sequential:true,  //按顺序上传
        sequentialCount:1,  //按顺序上传
        autoSubmit :"false",  //取消自动上传
        extErrorStr:"上传文件格式不对",
        maxFileCount:5,       //上传文件数量
        dragDropStr: "<span><b>附件拖放于此</b></span>",
        showFileCounter:false,
        returnType:"json",  //返回数据格式为json
        onSuccess:function(files,data,xhr,pd) {
           	if(data.state==0){
                for(var i=0;i<data.information.length;i++){
                    fileIds.push(data.information[i].id)
                }
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


// 新闻简介照片上传
$(document).ready(function()
	{
	    $("#photouploader").uploadFile({
	      	url:"${ctx}/enclosure/singleSave",     //文件上传url
		    fileName:"news",               //提交到服务器的文件名
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
		            data:JSON.stringify({"module" : "news", "name" : data.information}),
		            success: function(data) 
		            {
		                if(data.state===0){
		                    pd.statusbar.hide();        //删除成功后隐藏进度条等
		                    $('#photo_save').val("");  // 注册时用
		                    $('#photo_update').val("");  // 修改时用
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