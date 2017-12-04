//用户相关的js
var adminId;
$(function() {
	$("#adminAddCheck").Validform({
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
	
	$("#adminUpdateCheck").Validform({
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
	
	//初始化表格
	//initTable();
  // 加载表格
  loadTable();
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		loading1();
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/admin/listDynaPage",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"param":{
			  			"userName"		:	$('#userNameList').val(),
				  		"nickName"		:	$('#nickNameList').val(),
				  		"phone"		:	$('#phoneList').val()
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
		     
		        },
		        error: function(){
		          alert("请求错误，请检查网络连接");
		       }
		  });
		removeLoading('test');
	})
  
 
});

//清除错误提示信息
function clearWrongMsg() {
	$("#adminAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#userName-add").next().text("请输入您的账户");
	$("#nickName-add").next().text("请输入您的姓名");
	$("#instituteId-add").next().text("请输入研究所编号");
	$("#password-add").next().text("请输入您的密码");
	$("#phone-add").next().text("请输入您的手机号码");
	$("#email-add").next().text("请输入您的邮箱");
}

function addFunction(){
	$.ajax("/lims/admin/add",
	        {
	            dataType: "json", // 预期服务器返回的数据类型。
	            type: "post", //  请求方式 POST或GET
	            crossDomain:true,  // 跨域请求
	            contentType: "application/json", //  发送信息至服务器时的内容编码类型
	            // 发送到服务器的数据
	            data:JSON.stringify({
	            	"userName"		:	$('#userName-add').val(),
			  		"nickName"		:	$('#nickName-add').val(),
			  		"instituteId"	:	$('#instituteName-add').val(),
			  		"password"		:	$('#password-add').val(),
			  		"phone"			:	$('#phone-add').val(),
			  		"email"			:	$('#email-add').val()
	                }),
	            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	            // 请求成功后的回调函数。
	            success: function(data){
	                if (data.state == "0") {
	                    alert("用户添加成功!");
	                    $('#addUserModal').modal('hide');
	                    loadTable();
	                   
	                } else {
	                    alert("注册失败!  错误:" + data.message);
	                }
	                
	            },
	            error: function(){
	                alert("请求错误，请检查网络连接");
	           }
	    });
	        return true;  
	    }

function updateFunction() {
	$.ajax("/lims/admin/update",
            {
              dataType: "json", // 预期服务器返回的数据类型。
                type: "post", //  请求方式 POST或GET
                crossDomain:true,  // 跨域请求
                contentType: "application/json", //  发送信息至服务器时的内容编码类型
                // 发送到服务器的数据
                data:JSON.stringify({
                  "id"    : adminId,
                  "userName"		:	$('#userName-modal').val(),
  		  		"nickName"		:	$('#nickName-modal').val(),
  		  		"instituteName"	:	$('#instituteName-modal').val(),
  		  		"phone"			:	$('#phone-modal').val(),
  		  		"email"			:	$('#email-modal').val()
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
                },
                error: function(){
                alert("请求错误，请检查网络连接");
               }
          });
	
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

function loadTable() {
	loading1()
	$.ajax("/lims/admin/listDynaPage",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"param":{
			  			"userName"		:	$('#userNameList').val(),
				  		"nickName"		:	$('#nickNameList').val(),
				  		"phone"		:	$('#phoneList').val()
			  		},
			  		"pageModel":{
			  			"pageIndex": $('#userInfo-table').attr('data-page-number'),
				         "pageSize": $('#userInfo-table').attr('data-page-size')
			  		}
			  		}),
			  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		$("#userInfo-table").bootstrapTable('load', data);
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	removeLoading('test');
}



function actionFormatter(value, row, index) {
    return '<button id="btn_resetPassword" type="button" class="resetPasswordBtn btn btn-primary"  >'+
    		' <span class="glyphicon glyphicon-repeat" ></span> 重置密码' +
    		'</button>' +
    		'<button id="btn_edit" type="button" class="mod btn btn-warning" data-toggle="modal"  data-target="#updateUserModal">'+
           ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
           '</button>' +
           ' <button id="btn_delete" type="button" class="delete btn btn-danger" data-toggle="modal"  data-target="#deleteUserModal">' +
           ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
           ' </button>'
}

//表格  - 操作 - 事件
window.actionEvents = {
		
    //修改操作
    'click .mod': function(e, value, row, index) {
    	
            // 从后台查询此用户，为修改输入框提前赋值
            $.ajax("/lims/admin/get",
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
                    	  $('#userName-modal').val(data.information.userName);
	                        $('#nickName-modal').val(data.information.nickName);
	                        $('#instituteName-modal').val(data.information.instituteId);
	                        $('#phone-modal').val(data.information.phone);
	                        $('#email-modal').val(data.information.email);
	                        adminId = row.id;
                      } else {
                        alert("用户不存在");
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
              $.ajax("/lims/admin/delete",
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
                        alert("已删除用户: ");
                        $('#deleteUserModal').modal('hide');
                        loadTable();
                      } else {
                        alert("用户不存在");
                      }
                    },
                    error: function(){
                    alert("请求错误，请检查网络连接");
                   }
              });
            })
          },
        
          //重置密码操作
        'click .resetPasswordBtn' : function(e, value, row, index) {
       
            // 获取row.id获取此行id
        	        
              $.ajax("/lims/admin/resetPassword",
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
          		  			alert("修改成功!");
          		  		} else {
          		  			alert("修改失败!  错误:");
          		  		}
          		  		
          		   	},
                    error: function(){
                    alert("请求错误，请检查网络连接");
                   }
              });
           
          }
    }

