var fileIds = new Array();
var mdStudentId;

$(function() {
	// 加载表格
	loadTable();
	
	//增加验证
		$("#mdStudentAddCheck").Validform({
			tiptype:function(msg,o,cssctl){
	            //msg：提示信息;
	            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
	            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
	            var objtip=o.obj.siblings(".Validform_checktip");
	            cssctl(objtip,o.type);
	            objtip.text(msg);          
	        },
	 		showAllError:true,
	 		callback:function(form){		//通过验证后调用增加接口的方法			
	 			addFunction(); 		
	 			return false;
	 		}
	 	});
	//修改验证
			$("#mdStudentUpdateCheck").Validform({
				tiptype:function(msg,o,cssctl){
		            //msg：提示信息;
		            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
		            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
		            var objtip=o.obj.siblings(".Validform_checktip");
		            cssctl(objtip,o.type);
		            objtip.text(msg);          
		        },
		 		showAllError:true,
		 		callback:function(form){		//通过验证后调用增加接口的方法			
		 			updateFunction(); 
		 			return false;
		 		}
		 	});
		
//查询面板
  $("#queryBtn").click(function() {
		$('#userInfo-table').bootstrapTable('refresh');    //刷新表格
	});
  
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/mdstudent/listDynaPage",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"param" : {
				  		"name"		:	$('#mdStudent_list').val(),
				  		"major"		:	$('#major_list').val(),
				  		"researchInterest"	:	$('#researchInterest_list').val()
			  		},
			  		"pageModel" : {
				  		"pageIndex"	:	currentPage,
				  		"pageSize"	:	pageSize
			  		}
		          }),
		        async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		        // 请求成功后的回调函数。
		        success: function(data){
		          $("#userInfo-table").bootstrapTable('load', data);
		        },
		        error: function(){
		          alert("请求错误，请检查网络连接");
		       }
		  });
	})

	
});

//加载表格，将服务器返回的json数据加载到表格当中

function loadTable() {

	 //单选框赋值
    var radio=document.getElementsByName('mdStudentType-look');
    var type=$(":radio[id='mdStudentType-look']:checked").val();
    
	$.ajax("/lims/mdstudent/listDynaPage",
			{
		
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		"param" : {
			  			"name"		:	$('#mdStudent_list').val(),
				  		"major"		:	$('#major_list').val(),
				  		"college"	:	$('#college_list').val(),
				  		"researchInterest"	:	$('#researchInterest_list').val(),
				  		//"type"	:	$('#mdStudentType-look').val(),
				  		"type"	:	type,
				  		"description"	:	$('#description-look').val(),
			  		},
			  		"pageModel" : {
				  		"pageIndex"	:	$('#userInfo-table').attr('data-page-number'),
				  		"pageSize"	:	$('#userInfo-table').attr('data-page-size')
			  		}
			  	}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  	
			  		$("#userInfo-table").bootstrapTable('load', data);
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

function actionFormatter(value, row, index) {
    return '<button id="btn_look" type="button" class="lookBtn btn btn-primary" data-toggle="modal"  data-target="#lookUserModal">'+
    	   ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
    	   '</button>' +
    	   '<button id="btn_edit" type="button" class="mod btn btn-warning" data-toggle="modal"  data-target="#updateUserModal">'+
           ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
           '</button>' +
           ' <button id="btn_delete" type="button" class="delete btn btn-danger" data-toggle="modal"  data-target="#deleteUserModal">' +
           ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
           ' </button>'
}

//增加方法
function addFunction(){
		 
	 //单选框赋值
    var radio=document.getElementsByName('mdStudentType-add');
    var type=$(":radio[id='mdStudentType-add']:checked").val();

	$.ajax("/lims/mdstudent/add",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"photo"	:	$('#photo-add').val(),
	            	"name"		:	$('#mdStudentName-add').val(),
	                "major"		:	$('#major-add').val(),
	               // "type"		:	$('#mdStudentType-add').val(),
	                "type"	:	type,
	                "college"	:	$('#college-add').val(),
	                "researchInterest"		:	$('#researchInterest-add').val(),
	                "email"		:	$('#email-add').val(),
	               	"description"		:	$('#description-add').val(),
			  		}),
			  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
	            	if (data.state == "0") {
	                    alert("学生添加成功!");
	                    $('#addUserModal').modal('hide');
	                    loadTable();
	                  
	                } else {
	                    alert("注册失败!  错误:" + data.message);
	                }
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[id="description-add"]',"");
	$("#mdStudentAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#mdStudentName-add").next().text("请输入学生姓名");
	$("#major-add").next().text("请输入专业名称");
	$("#college-add").next().text("请输入所属学院");
	$("#researchInterest-add").next().text("请输入研究方向");
	$("#email-add").next().text("请输入邮箱地址");
	photoUpload("#photouploader");
	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
}

function photoUpload(photofile) {
	$(photofile).uploadFile({
      	url:"/lims/enclosure/singleSave",     //文件上传url
	    fileName:"news",               //提交到服务器的文件名
	    maxFileCount: 1,                //上传文件个数（多个时修改此处
	    returnType: 'json',              //服务返回数据
	    allowedTypes: 'jpg,jpeg,png,gif',  //允许上传的文件式
	    showDone: false,                     //是否显示"Done"(完成)按钮
	    showDelete: true,                  //是否显示"Delete"(删除)按钮
	    statusBarWidth:550,
        dragdropWidth:550,
	    deleteCallback: function(data,pd)
	    {
	        //文件删除时的回调方法。
	        //如：以下ajax方法为调用服务器端删除方法删除服务器端的文件
	        $.ajax({
	            cache: false,
	            url: "/lims/enclosure/singleDelete",
	            type: "DELETE",
	            contentType : "application/json",
	            dataType: "json",
	            data:JSON.stringify({
	            	"module":"mdstudent", "name" : data.information
	    	  	}),
	            success: function(data) 
	            {
	                if(data.state===0){
	                    pd.statusbar.hide();        //删除成功后隐藏进度条等
	                    $('#photo-add').val("");  // 注册时用
//	                    $('#photo-modal').val();  // 修改时用
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
	        	$('#photo-add').val(data.information);
//	        	$('#photo-modal').val(data.information);
	        	alert("upload success");
	        }
	    }
	});
}

function updateFunction(){
	
	 //单选框赋值
    var radio=document.getElementsByName('mdStudentType-modal');
    var type=$(":radio[id='mdStudentType-modal']:checked").val();
    
   //单击提交后修改
	$.ajax("/lims/mdstudent/update", 
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
	          "id"    : mdStudentId,
		      "name"  : $('#mdStudentname-modal').val(),
		      "photo"  : $('#photo-modal').val(),
		      "major"  : $('#major-modal').val(),
		      "college" : $('#college-modal').val(),
		      "researchInterest"   : $('#researchInterest-modal').val(),
		      "email"   : $('#email-modal').val(),
		      "photo":$('#photo-modal').val(),
		      
		      //"type"  : $('#mdStudentType-modal-modal').val(),
		      "type"  :	type,
		      "description"  : $('#description-modal').val(),
 			  //"fileIds" : fileIds
   		  	}),
   		  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
   		  	// 请求成功后的回调函数。
   		  	success: function(data){
   		  		if (data.state == "0") {
		             alert("状态 " + data.state + " " + "修改成功! " + data.information.id + " " + data.information.name);
		             $('#updateUserModal').modal('hide');
		             loadTable();
	           } else {
	             alert("状态 " + data.state + " " + "修改失败!  错误:" + data.message);
	           }
   		  	},error: function(){
   		  		alert("请求错误，请检查网络连接");
   		  	}
   });   
}

//表格  - 操作 - 事件
window.actionEvents = {
		
	    'click .lookBtn': function(e, value, row, index) {
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/mdstudent/get",
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
	                   	    // 为模态框中的输入框提前赋值
	                        $('#mdStudentname-look').val(data.information.name);
	                        $('#major-look').val(data.information.major);
	                        $('#mdStudentType-look').val(data.information.type);
	                        $('#college-look').val(data.information.college);
	                        $('#researchInterest-look').val(data.information.researchInterest);
	                        $('#email-look').val(data.information.email);
	                        $('#description-look').html(data.information.description);

	                        //图片
	                        var ph,phpath;
	                       // phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
	                        ph=document.getElementById("photo_look");
	                       
	                        if(!data.information.photo)
	                        	{ph.innerHTML="";}
	                        else{ph.innerHTML="<img height='200px' width='150px' src='/lims/enclosure/download/mdstudent/"+data.information.photo+"'/>";
	                        }
	                                            	                        
	                      } else {
	                        alert("学生不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	          
	        },	 
//修改操作
'click .mod': function(e, value, row, index) {
    // 从后台查询此用户，为修改输入框提前赋值
    $.ajax("/lims/mdstudent/get",
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
                // 为模态框中的输入框提前赋值
            	   mdStudentId = row.id;
            	    $('#photo-modal').val(data.information.photo);
            	 	$('#mdStudentname-modal').val(data.information.name);
	                $('#major-modal').val(data.information.major);
	                //$('#mdStudentType-look').val(data.information.type);
	                $('#college-modal').val(data.information.college);
	                $('#researchInterest-modal').val(data.information.researchInterest);
	                $('#email-modal').val(data.information.email);
	                
	          	    //单选框赋值
	                var type=data.information.type;
	            	var radio=document.getElementsByName('mdStudentType-modal');
	            	for(var i=0;i<radio.length;i++){
	            		if(type == radio[i].value){
		                	radio[i].checked="checked";
		                }
	            	}
	                	
	                KindEditor.remove("textarea[name='content']");
					kedit('textarea[id="description-modal"]',data.information.description);
	           
//					单选框赋值
//	                if(radio="硕士"){
//                	$("input[type='radio][value='1']").attr("checked",true);
//                }else{
//                	$("input[type='radio][value='2']").attr("checked",true);
//                }
//	                $("input[name='mdStudentType-modal']").attr("checked",radio);
					
	               
	               
	                 //个人简介
	                KindEditor.remove('textarea[name="content"]');
	            	kedit('textarea[id="description-modal"]',data.information.description);
	            	//图片
                    var ph,phpath;
                 //   phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
                    ph=document.getElementById("photo-modal");
                    if(data.information.photo) {
                    	ph.innerHTML="<img height='200px' width='150px' " +
        				"src='/lims/enclosure/download/mdstudent/"+data.information.photo+"'/>" +
        				"<button id='photochange' class='btn-danger' type='button'>更改</button>";
                    }else{ph.innerHTML="";}//ph.innerHTML="<button id='photochange' class='btn-danger' type='button'>更改</button>";}
                    $("#photochange").unbind();
                    $('#photochange').click(function() {
                    	
                    	var p=" <form id='form' method='post'  enctype='multipart/form-data' > " +
                    			"<input type='hidden' name='columnId' value='${columnId }'> <input type='hidden' " +
                    			"name='state' id='state' >   <div id='photochange'>上传照片</div></form>"
                    		
                    		ph.innerHTML=p;

                    	
                    	deletePhoto(data.information.photo);
                    	photoChangeShow();
                    	
                    })

                    //附件
                    var inp,inv,enpath;
                    inp=document.getElementById("enclosures_modal");
                    inv="";
                   // enpath="D:\Tomcat\tomcat\webapps\lims\WEB-INF\\uploads\news"+data.information.enclosures[i].fileName+".*";
                     for(var i=0; i<data.information.enclosures.length; i++){
                        inv+="<a href=''>"+data.information.enclosures[i].oldName+"</a>"+ "<br/>";		                        	
	                 }
                     inp.innerHTML=inv;
              } else {
                alert("学生不存在");
              }
            },
            error: function(){
            alert("请求错误，请检查网络连接");
           }
      });
},
'click .delete' : function(e, value, row, index) {
    //删除操作
    // 获取row.id获取此行id
	  $('#submit_delete').unbind();
    $('#submit_delete').click(function() {
      $.ajax("/lims/mdstudent/delete",
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
                alert("已删除该学生: ");
                $('#deleteUserModal').modal('hide');
                loadTable();
              } else {
                alert("该学生不存在");
              }
            },
            error: function(){
            alert("请求错误，请检查网络连接");
           }
      });
    })
  }
}


//删除图片
function deletePhoto(photoname){
$.ajax("/lims/enclosure/singleDelete",
	{
		dataType: "json", // 预期服务器返回的数据类型。
			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	    data:JSON.stringify({
        	"module":"mdstudent", "name" : photoname
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("请上传新图片!");
	  			$("#a"+i).remove();
	  			$("#b"+i).remove();
	  		} else {
	  			alert("删除失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
	
}	

function photoChangeShow(){
	 $("#photochange").uploadFile({
	      	url:"/lims/enclosure/singleSave",     //文件上传url
		    fileName:"mdstudent",               //提交到服务器的文件名
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
		            url: "/lims/enclosure/singleDelete",
		            type: "DELETE",
		            contentType : "application/json",
		            dataType: "json",
		            data:JSON.stringify({
		            	"module":"mdstudent", "name" : data.information
		    	  	}),
		            success: function(data) 
		            {
		                if(data.state===0){
		                    pd.statusbar.hide();        //删除成功后隐藏进度条等
		                    $('#photo-modal').val("");  // change时用
		                   
		                    alert("请上传新后图片");
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
		        	$('#photo-modal').val(data.information);
		        	
		        	alert("upload success");
		        }
		    }
		});
	
	
	
	
}	



//文本编辑器
var editor;
function kedit(kedit,content){
	//	alert("KindEditor");
		editor = KindEditor.create(kedit,{
			themeType:'simple',
			resizeType:2,
			uploadJson : 'kindEditor/jsp/upload_json.jsp',
			fileManagerJson : 'kindEditor/jsp/file_manager_json.jsp',
			allowFileManager:true,
			width: '100%',
			height:'200px',
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


//显示照片
function photoFormatter(value, row, index){
//获取文件
	var photo;
	var file;
	file = row.photo;
	if(!file){
		photo =" ";
	}else{
		photo="<img height='50px' width='40px' " +
				"src='/lims/enclosure/download/mdstudent/" +
				 file + "'/>";
	}
	return photo
}


//function:responseHandler请求数据成功后，渲染表格前的方法
//请求成功方法
function responseHandler(result){
 //如果没有错误则返回数据，渲染表格
 return {
     total : result.total, //总页数,前面的key必须为"total"
     data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
 };
};
