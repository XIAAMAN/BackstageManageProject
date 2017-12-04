<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="${ctx}/css/uploadfile.css" rel="stylesheet" />  
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script> 
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
</head>
<body>
	<form id="newsform" method="post"  enctype="multipart/form-data" >    
        <input type="hidden" name="columnId" value="${columnId }">
        <input type="hidden" name="state" id="state" >
        <div id="fileuploader">上传附件</div>
    </form>
	<br>
	<br>
    <h5>删除文件</h5>
   	<label>id	: </label> <input type="text" id="id_delete"/><br/>
	<input type="submit" value="删除" onclick="deletes()" />
	<br>
	<br>
	<img src="${ctx}/enclosure/download?table=project&id=9"/>
	<a href="${ctx}/enclosure/download/news/1508954131.jpg">download </a><br/>
	<img src="${ctx}/enclosure/download/news/1508656954131.jpg"/>
	<img src="${ctx}/enclosure/download/mdstudent/1508678655789.jpg"/>
	<img src="${ctx}/enclosure/download/teacher/1508678655789.jpg"/>
	<img src="${ctx}/enclosure/download/medal/1508654404940.jpg"/>
</body>
<script type="text/javascript">
var fileIds = new Array();

function deletes(){
$.ajax("${ctx}/enclosure/delete",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
	  		"enclosure" : {
	  			"id" : "10"
	  		},
	  		"table" : "project"
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

$(document).ready(function() {
    $("#fileuploader").uploadFile({
        url:"${ctx}/enclosure/upload", //后台处理方法
        fileName:"notice",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
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