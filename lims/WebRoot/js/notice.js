//通知相关的js
var fileIds = new Array();
var updateId;	//全局变量修改的id
$(function() {
	
	//激发增加框中置顶按钮
	$('#seqNotice-add').bootstrapSwitch({
        onColor:"success",  
        offColor:"info",  
        size:"small",
		state:false,       
  	 });
// 增加验证栏
	$("#noticeAddCheck").Validform({
		tiptype:function(msg,o,cssctl){
            //msg：提示信息;
            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
            var objtip=o.obj.siblings("#noticeAddCheck");
            cssctl(objtip,o.type);
            objtip.text(msg);          
        },

 		showAllError:true,
 		
 		callback:function(form){		//通过验证后调用增加接口的方法
 			noticeAdd(); 
 			
 			return false;
 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
 			//这里明确return false的话表单将不会提交; 
 		}, 
 	});
	
	
	// 修改验证栏
	$("#noticeUpdateCheck").Validform({
		tiptype:function(msg,o,cssctl){
            //msg：提示信息;
            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
            var objtip=o.obj.siblings("#noticeUpdateCheck");
            cssctl(objtip,o.type);
            objtip.text(msg);          
        },
        
 		showAllError:true,        
 		callback:function(form){		//通过验证后调用修改接口的方法
 			noticeUpdate(); 			
 			return false;
 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
 			//这里明确return false的话表单将不会提交; 
 		},
 	});
	
	
  // 加载表格
  loadTable();
	
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		loading1();
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/notice/listDynaPage",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"param"	: {
				  		"title"		:	$('#title_list').val(),
				  		"author"	:	$('#author_list').val()
				  	},
				  	"pageModel" : {
				  		"pageIndex": currentPage,
				         "pageSize": pageSize
			  		}
		          }),
		        async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		        // 请求成功后的回调函数。
		        success: function(data){
		        	
		          $("#userInfo-table").bootstrapTable('load', data);
		          $('input[type="checkbox"]').bootstrapSwitch({

		      	            onColor:"success",  
		      	            offColor:"info",  
		      	            size:"small",  
		      	        });
		        },
		        error: function(){
		          alert("请求错误，请检查网络连接");
		       }
		  });
		removeLoading('test');
	})
	
});

//得到增加中是否置顶的状态
function dealState() {
	switchState=$('#seqNotice-add').bootstrapSwitch('state');
	if(switchState) {
		return 1;
	}else {
		return 0;
	}
}

//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove('textarea[name="contentUpdate"]');
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[name="content"]',"");
	$("#noticeAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#titleNotice-add").next().text("请输入标题名称");
	$("#authorNotice-add").next().text("请输入作者名");
	addFileUpload();
	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
}

function updateFileUpload() {
	//修改栏中上传文件
	$("#updatefileuploader").uploadFile({
        url:"/lims/enclosure/upload", //后台处理方法
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
        statusBarWidth:750,
        dragdropWidth:750,
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
	
}

function addFileUpload() {
	//增加栏中上传文件
		$("#fileuploader").uploadFile({
	        url:"/lims/enclosure/upload", //后台处理方法
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
	        statusBarWidth:750,
	        dragdropWidth:750,
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
	
}

//增加方法
function noticeAdd(){
	temp=dealState();
$.ajax("/lims/notice/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	//crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"param"	: {
		  		"title"		:	$('#titleNotice-add').val(),
		  		"author"	:	$('#authorNotice-add').val(),
		  		"content"	:	$('#contentNotice-add').val(),
		  		"seq"		:	temp.toString()
		  	},
		  	"fileIds" : fileIds
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			fileIds = [];
	  			alert("公告添加成功!");
                $('#addNoticeModal').modal('hide');		//隐藏添加modal框
            	
                loadTable();
                
	  		} else {
	  			alert("注册失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(e, status){
			alert("您的请求有误，请正常操作或联系管理员");	
	   }
	});
}

//修改方法
function noticeUpdate() {
	//单击提交后修改
    $.ajax("/lims/notice/update",
      {
 	   
        dataType: "json", // 预期服务器返回的数据类型。
          type: "post", //  请求方式 POST或GET
          crossDomain:true,  // 跨域请求
          contentType: "application/json", //  发送信息至服务器时的内容编码类型
          // 发送到服务器的数据
          data:JSON.stringify({
         	 "param"	: {
		  		"id"    : updateId,
                "title"		:	$('#title-modal').val(),
		  		  "author"	:	$('#author-modal').val(),
		  		  "content"	:	$('#content-modal').val()
			  	},
			  	"fileIds" : fileIds
            }),
          async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
          // 请求成功后的回调函数。
          success: function(data){
            if (data.state == "0") {
         	   fileIds = [];
              alert("修改成功!");
              $('#updateNoticeModal').modal('hide');
              $('.ajax-file-upload-container').html("");
              loadTable();
              
            } else {
              alert("状态 " + data.state + " " + "修改失败!  错误:" + data.message);
            }
          },
          error: function(){
          alert("请求错误，请检查网络连接");
         }
    });
}

//按钮开关方法
function btnOnOff(value, row, index) {	
	var state='';	
	if (row.seq == '1')
		state = 'checked';
    return '<button class="switch switchButton"><div data-animated="false"><input type="checkbox" '+ state +' /></div></button> ';
    		
}


//按钮开关操作
window.onOffactionEvents={
		
		'click .switch': function(e, value, row, index) {
			
			$.ajax("/lims/notice/updateSeq",
					{
						dataType: "json", // 预期服务器返回的数据类型。
				  			type: "post", //  请求方式 POST或GET
					  	crossDomain:true,  // 跨域请求
					  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
					  	// 发送到服务器的数据
					  	data:JSON.stringify({
					  		"id"  :	row.id
					  	}),
					  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
					  	// 请求成功后的回调函数。
					  	success: function(data){
					  		if (data.state == 0) {
					  			alert("修改成功!");
					  			
					  			loadTable();
					  		} else {
					  			alert("修改 失败!  错误:" + data.message);
					  		}
					   	},
					   	error: function(){
							alert("您的请求有误，请正常操作或联系管理员");
					   }
					});
		}
}

//加载数据中显示
function loading1() {
	$('body').loading({
		loadingWidth:240,
		title:'请稍等!',
		name:'test',
		discription:'描述描述描述描述',
		direction:'column',
		type:'origin',
		// originBg:'#71EA71',
		originDivWidth:40,
		originDivHeight:40,
		originWidth:6,
		originHeight:6,
		smallLoading:false,
		loadingMaskBg:'rgba(0,0,0,0.2)'
	});

}

//加载数据
function loadTable() {
	loading1();
	$.ajax("/lims/notice/listDynaPage",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"param"	: {
				  		"title"		:	$('#title_list').val(),
				  		"author"	:	$('#author_list').val()
				  	},
				  	"pageModel" : {
				  		"pageIndex": $('#userInfo-table').attr('data-page-number'),
				         "pageSize": $('#userInfo-table').attr('data-page-size')
			  		}
			  	}),
			  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		$("#userInfo-table").bootstrapTable('load', data);
			  		$('input[type="checkbox"]').bootstrapSwitch({

			      	            onColor:"success",  
			      	            offColor:"info",  
			      	            size:"small",  
			      	            
			      	        });
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	removeLoading('test');
}


function responseHandler(result){
	 //如果没有错误则返回数据，渲染表格
	 return {
	     total : result.total, //总页数,前面的key必须为"total"
	     data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
	 };
};


//文本编辑器
var editor;
function kedit(kedit,content){
		editor = KindEditor.create(kedit,{
			themeType:'simple',
			resizeType:2,
			uploadJson : 'kindEditor/jsp/upload_json.jsp',
			fileManagerJson : 'kindEditor/jsp/file_manager_json.jsp',
			allowFileManager:true,
			width: '100%',
			height: '400px',
			items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			//经测试，下面这行代码可有可无，不影响获取textarea的值
			afterCreate: function(){this.sync();},
			//下面这行代码就是关键的所在，当失去焦点时执行 this.sync();
			afterBlur:function(){this.sync();}	
		});
		editor.html(content);
}	

//修改栏中删除已有文件
function deleteFile(temp,i) {
	$.ajax("/lims/enclosure/delete",
		{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
	  		"enclosure" : {
	  			"id" : temp
	  		},
	  		"table" : "notice"
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("删除成功!");
	  			$("#fileLink"+i).remove();
	  			$("#fileBtn"+i).remove();
	  		} else {
	  			alert("删除失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
			
}

//返回操作中的按钮
function actionFormatter(value, row, index) {
    return  '<button id="btn_look" type="button" class="noticeLook btn btn-primary" style="margin-right:5px;padding:4px 8px" data-toggle="modal"  data-target="#lookNoticeModal">'+
	  ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
	  '</button>' +
	  '<button id="btn_edit" type="button" class="noticeMod btn btn-warning" data-toggle="modal"  data-target="#updateNoticeModal">'+
           ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
           '</button>' +
           ' <button id="btn_delete" type="button" class="noticeDelete btn btn-danger" data-toggle="modal"  data-target="#deleteNoticeModal">' +
           ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
           ' </button>'
}

//表格  - 操作 - 事件
window.actionEvents = {

		
		//查看操作
	    'click .noticeLook': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	    	$.ajax("/lims/notice/get",
	                {
	                  dataType: "json", // 预期服务器返回的数据类型。
	                    type: "post", //  请求方式 POST或GET
	                    crossDomain:true,  // 跨域请求
	                    contentType: "application/json", //  发送信息至服务器时的内容编码类型
	                    // 发送到服务器的数据
	                    data:JSON.stringify({
	                      "id"    : row.id,
	                      }),
	                    async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	                    // 请求成功后的回调函数。
	                    success: function(data){
	                      if (data.state == "0") {
	                    	// 为模态框中的输入框提前赋值
	                    	  $('#title-look').val(data.information.title);
		                        $('#author-look').val(data.information.author);
		                        $('#description-look').html(data.information.content);
		                         
	                      } else {
	                        alert("公告不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	          
	        },
	        
    //修改操作
    'click .noticeMod': function(e, value, row, index) {
    	updateFileUpload();
    	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
            // 从后台查询此用户，为修改输入框提前赋值
    	$.ajax("/lims/notice/get",
                {
                  dataType: "json", // 预期服务器返回的数据类型。
                    type: "post", //  请求方式 POST或GET
                    crossDomain:true,  // 跨域请求
                    contentType: "application/json", //  发送信息至服务器时的内容编码类型
                    // 发送到服务器的数据
                    data:JSON.stringify({
                      "id"    : row.id,
                      }),
                    async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success: function(data){
                      if (data.state == "0") {
                        // 为模态框中的输入框提前赋值
                    	  updateId = data.information.id;	//获取修改的id
                    	  $('#title-modal').val(data.information.title);
	                        $('#author-modal').val(data.information.author);
	                        
	                        KindEditor.remove('textarea[name="content"]');	//清除文本编辑框
	                        KindEditor.remove('textarea[name="contentUpdate"]');
	                        
	                        kedit('textarea[name="contentUpdate"]',data.information.content);	//重新给文本编辑框数据
                        
	                        var tempName,fileDescription,fileSize,fileId,newFileName;
	                       
	                        var fileLength=data.information.enclosures.length;	//文件的个数
	                        
	                        var filehtmls="";
	                        if(fileLength!=0) {
	                        	
	                        	for(var i=0;i<fileLength;i++) {	//循环所有文件
	                        		tempName=data.information.enclosures[i].oldName;	//获取文件的名称
	                        		newFileName = data.information.enclosures[i].fileName;
	                        		fileId=data.information.enclosures[i].id;		//获取文件id
	                        		fileSize="  ("+data.information.enclosures[i].size+" KB)";	//获取文件的大小
	                        		fileDescription=tempName+fileSize;
	                        		
		                        	
		                        	 filehtmls +="<div>" +
						                        	 "<a href='http://localhost:8080/lims/enclosure/download/notice/"+newFileName+"'" +
						                        	 " class='fileDescribleLink' "+" id='fileLink"+i+"'"+">"+fileDescription+"</a>" +
						                        	 "<button  type='button'"+" class='btn-sm fileDeleteBtnStyle btn-danger' "+
						                        	 "id='fileBtn"+i+"'"+"onClick=deleteFile("+fileId+","+i+")>"+"删除"+"</button>"+
						                        "<div>";
	                        	}
	                        }
	                        
	                    $("#noticeUpdateFile").html(filehtmls);    
                        
                      } else {
                        alert("公告不存在");
                      }
                    },
                    error: function(){
                    alert("请求错误，请检查网络连接");
                   }
              });
          
        },
        
        
        'click .noticeDelete' : function(e, value, row, index) {
            //删除操作
        	$('#noticeSubmit_delete').unbind();
            // 获取row.id获取此行id
            $('#noticeSubmit_delete').click(function() {
              $.ajax("/lims/notice/delete",
                {
                  dataType: "json", // 预期服务器返回的数据类型。
                    type: "post", //  请求方式 POST或GET
                    crossDomain:true,  // 跨域请求
                    contentType: "application/json", //  发送信息至服务器时的内容编码类型
                    // 发送到服务器的数据
                    data:JSON.stringify({
                      "id"    : row.id,
                      }),
                    async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success: function(data){
                      if (data.state == "0") {
                        alert("已删除公告: ");
                        $('#deleteNoticeModal').modal('hide');
                        loadTable();
                      } else {
                        alert("公告不存在");
                      }
                    },
                    error: function(){
                    alert("请求错误，请检查网络连接");
                   }
              });
            })
          }
    }

