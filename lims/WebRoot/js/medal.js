/**
 * 
 */

//用户相关的js
var medalId;
$(function() {

  // 加载表格
  loadTable();
  
  showTeacherId();
  querySelectActive();
  showTeacherUpdateId();
   
  
//激发增加框中是否为主办方按钮
	$('#sponsorAdd').bootstrapSwitch({
		onText:'是',  
		offText:'否',  
        onColor:"success",  
        offColor:"info",  
        size:"small",
		state:false,       
  	 });
	
	
	//对增加信息进行验证
	  $("#medalAddCheck").Validform({
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
				medalAdd();
				return false;
			}
		});
	  
	//对修改信息进行验证
	  $("#medalUpdateCheck").Validform({
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
				medalUpdate();
				return false;
			}
		});
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		loading1();
		//刷新表格
		var temp=getTeacherId();	//得到id
		temp=temp.toString();		//将id转换成字符串
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/medal/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"name"		:	$('#medalName').val(),
		  			"teacherId" :	temp,
		  			"type"		:	$('#medalType').val()=="无"?"":$('#medalType').val(),
		  			"grade"		:	$("#medalGrade").val()=="无"?"":$('#medalGrade').val(),
		  			"level"		: 	$("#medalLevel").val()=="无"?"":$('#medalLevel').val(),
			  		"pageIndex": currentPage,
			        "pageSize": pageSize	
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

//触发查询中的下拉框
function querySelectActive() {
	$('#medalTeacher').selectpicker({
		width:180
  });
	$('#medalType').selectpicker({
		width: 140
	  }); 
	
	$('#medalGrade').selectpicker({
		width: 100
	  }); 
	
	$('#medalLevel').selectpicker({
		width: 100
	  }); 
	
	
	
	$('#medalTypeAdd').selectpicker({
		width:300
	  }); 
	
	$('#medalGradeAdd').selectpicker({
		width:300
	  }); 
	
	$('#medalLevelAdd').selectpicker({
		width:300
	  }); 
	
	
}

function showTeacherId(){		//搜索栏中的主持人下拉框
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		var opt = new Option("无");
					$('#medalTeacher').append(opt);
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#medalTeacher').append(opt);
					}
					$('#medalTeacher').val('');
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

//显示教师id
function showTeacheraddId(){
	
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#medalTeacherIdAdd.selectpicker').append(opt);
					}
					
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

//增加中教师的id
function addTeacherId(){
	var num=0;
	var index=0;
	var teacherString=$('#medalTeacherIdAdd option:selected').val();
	var	teacher = new Array();
	var teacherId = new Array();
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		
			  		for (var i=0; i<data.length; i++){
						teacher.push(data[i].name +"(" + data[i].college + ")");
						teacherId.push(data[i].id);
					}
			  		for (var i=0; i<data.length; i++) {
			  			if (teacher[i] == teacherString){
			  				num=1;
			  				index = i;
			  				break;
			  			}
			  		}
			  		
			   	},
			   	error: function(){
			   		alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	if (num != 0) {
		return teacherId[index];
	}
	else{
		return '';
	}
}
//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove("textarea[name='content']");	
    kedit("textarea[id='medalAbstractsAdd']","");
	showTeacheraddId();
	$('#medalTeacherIdAdd').selectpicker({
		width:300
  });
	$("#medalAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#medalNameAdd").next().text("请输入项目名称");
	$("#rankingAdd").next().text("请输入排名");
	photoUpload("#photoUploader");
	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
	
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
	loading1();
	paperTime();
	var temp=getTeacherId();	//得到id
	temp=temp.toString();		//将id转换成字符串
	$.ajax("/lims/medal/listDynaPageMap",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
		  			"name"		:	$('#medalName').val(),
		  			"teacherId" :	temp,
		  			"type"		:	$('#medalType').val()=="无"?"":$('#medalType').val(),
		  			"grade"		:	$("#medalGrade").val()=="无"?"":$('#medalGrade').val(),
		  			"level"		: 	$("#medalLevel").val()=="无"?"":$('#medalLevel').val(),
		  			"pageIndex": $('#userInfo-table').attr('data-page-number'),
			        "pageSize": $('#userInfo-table').attr('data-page-size')
			  	
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

//增加方法
function medalAdd() {
	var tempId = addTeacherId();
	tempId=tempId.toString();
	temp=dealState();
	$.ajax("/lims/medal/add",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"name"		:	$('#medalNameAdd').val(),
			  		"teacher"	:	{"id":tempId},
			  		"level"		:	$('#medalLevelAdd').val(),
			  		"type"		:	$('#medalTypeAdd').val(),
			  		"grade"		:	$('#medalGradeAdd').val(),
			  		"photo"		:	$("#photo_save").val(),
			  		"ranking"	:	$('#rankingAdd').val(),
			  		"winningTime"	:	$('#winningTimeAdd').val(),
			  		"sponsor"	:	temp.toString(),
			  		"abstracts"	:	$('#medalAbstractsAdd').val()
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		if (data.state == "0") {
			  			alert("注册成功!");
			  			$('#addMedalModal').modal('hide');
				  		
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

//得到更新下拉框中选定的主持人id
function updateTeacherId(){	
	var num=0;
	var index=0;
	var teacherString=$('#medalTeacherIdUpdate option:selected').val();
	var	teacher = new Array();
	var teacherId = new Array();
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		
			  		for (var i=0; i<data.length; i++){
						teacher.push(data[i].name +"(" + data[i].college + ")");
						teacherId.push(data[i].id);
					}
			  		for (var i=0; i<data.length; i++) {
			  			if (teacher[i] == teacherString){
			  				num=1;
			  				index = i;
			  				break;
			  			}
			  		}
			  		
			   	},
			   	error: function(){
			   		alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	if (num != 0) {
		return teacherId[index];
	}
	else{
		return '';
	}
}

function medalUpdate() {
	var temp=dealState();
	var tempId=updateTeacherId();
   	tempId=tempId.toString();   
       //单击提交后修改
       $.ajax("/lims/medal/update",
         {
    	   
           dataType: "json", // 预期服务器返回的数据类型。
             type: "post", //  请求方式 POST或GET
             crossDomain:true,  // 跨域请求
             contentType: "application/json", //  发送信息至服务器时的内容编码类型
             // 发送到服务器的数据
             data:JSON.stringify({
          	   	"id"		:	medalId,
            	"name"		:	$('#medalNameUpdate').val(),
		  		"teacher"	:	{"id":tempId},
		  		"level"		:	$('#medalLevelUpdate').val(),
		  		"type"		:	$('#medalTypeUpdate').val(),
		  		"grade"		:	$('#medalGradeUpdate').val(),
		  		"photo"		:	$("#photoUploadUpdate").val(),
		  		"ranking"	:	$('#rankingUpdate').val(),
		  		"winningTime"	:	$('#winningTimeUpdate').val(),
		  		"sponsor"	:	temp.toString(),
		  		"abstracts"	:	$('#medalAbstractsUpdate').val()
          	   
               
               }),
             async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
             // 请求成功后的回调函数。
             success: function(data){
               if (data.state == "0") {
            	   
                 alert("状态 " + data.state + " " + "修改成功! " + data.information.id + " " + data.information.name);
                 $('#updateMedalModal').modal('hide');
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

function actionFormatter(value, row, index) {
	   return '<button id="btn_look" type="button" class="medalLook btn btn-primary" style="margin-right:5px;padding:4px 8px" data-toggle="modal"  data-target="#lookMedalModal">'+
	   		  ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
	   		  '</button>' +
	   		  '<button id="btn_edit" type="button" class="medalMod btn btn-warning" data-toggle="modal" style="padding:4px 8px" data-target="#updateMedalModal">'+
	          ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
	          '</button>' +
	          ' <button id="btn_delete" type="button" class="medalDelete btn btn-danger" style="padding:4px 8px" data-toggle="modal"  data-target="#deleteMedalModal">' +
	          ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
	          ' </button>'
	}


//表格  - 操作 - 事件
window.actionEvents = {
			
		//
	    'click .medalLook': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/medal/get",
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
	                        $('#medalNameLook').val(data.information.name);
	                        $('#medalTeacherLook').val(data.information.teacher.name);
	                        $('#medalLevelLook').val(data.information.level);
	                        $('#medalTypeLook').val(data.information.type);
	                        $('#medalGradeLook').val(data.information.grade);
	                        $('#medalRankingLook').val(data.information.ranking);
	                        $('#medalWinningTimeLook').val(data.information.winningTime);
	                       
	                        $('#medalAbstractsLook').html(data.information.abstracts);
	                        
	                        //图片
	                        var ph,phpath;
	                       // phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
	                        ph=document.getElementById("medalPhotoLook");
	                       
	                        if(!data.information.photo)
	                        	{ph.innerHTML="";}
	                        else{ph.innerHTML="<img height='200px' width='150px' src='/lims/enclosure/download/medal/"+data.information.photo+"'/>";
	                        }
	                     
	                      } else {
	                        alert("项目不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	          
	        },	
	        
	      //修改操作
	        'click .medalMod': function(e, value, row, index) {
	        	showSelectedUpdate();
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/medal/get",
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
	                    	   medalId = row.id;
	                    	   //得到是否为主办方的状态
	                    	   if(data.information.sponsor=="1") {
	                    		   sta = true;
	                    		   
	                    	   }else {
	                    		   sta = false;
	                    	   }
	                    	   $('#sponsorUpdate').bootstrapSwitch('destroy');		//销毁
	                    	   $('#sponsorUpdate').bootstrapSwitch({
	                    			onText:'是',  
	                    			offText:'否',  
	                    	        onColor:"success",  
	                    	        offColor:"info",  
	                    	        size:"small",
	                    			state:sta,       
	                    	  	 });
	                    	   
	                    	    $('#photoUploadUpdate').val(data.information.photo);
	                    	 	$('#medalNameUpdate').val(data.information.name);
	        	                $('#medalTypeUpdate').val(data.information.type);
	        	                $('#medalGradeUpdate').val(data.information.grade);
	        	                $('#medalLevelUpdate').val(data.information.level);
	        	                $('#rankingUpdate').val(data.information.ranking);
	        	                $('#winningTimeUpdate').val(data.information.winningTime); 
	        	                
	        	                $('#medalTeacherIdUpdate').val(data.information.teacher.name +"(" + data.information.teacher.college + ")");
	                            $('#medalTeacherIdUpdate').selectpicker('refresh');
	        	                
	        	          	   
	        	                KindEditor.remove("textarea[name='content']");
	        					kedit('textarea[id="medalAbstractsUpdate"]',data.information.description);

	        	            	//图片
	                            var ph,phpath;
	                         //   phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
	                            ph=document.getElementById("photoUploadUpdate");
	                            if(data.information.photo) {
	                            	ph.innerHTML="<img height='200px' width='150px' " +
	                				"src='/lims/enclosure/download/medal/"+data.information.photo+"'/>" +
	                				"<button id='photochange' class='btn-danger' type='button'>更改</button>";
	                            }else{
	                            	
	                            	ph.innerHTML="";

                        	}
	                            $("#photochange").unbind();
	                            $('#photochange').click(function() {
	                            	
	                            	var p=" <form id='form' method='post'  enctype='multipart/form-data' > " +
	                            			"<input type='hidden' name='columnId' value='${columnId }'> <input type='hidden' " +
	                            			"name='state' id='state' >   <div id='photochange'>上传照片</div></form>"
	                            		
	                            		ph.innerHTML=p;

	                            	
	                            	deletePhoto(data.information.photo);
	                            	photoChangeShow();
	                            	
	                            })

	                           
	                      } else {
	                        alert("科研成果不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	        },
	        
	        'click .medalDelete' : function(e, value, row, index) {
	            //删除操作
	        	$('#medalSubmit_delete').unbind();
	            // 获取row.id获取此行id
	            $('#medalSubmit_delete').click(function() {
	              $.ajax("/lims/medal/delete",
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
	                        alert("已删除科研成果");
	                        $('#deleteMedalModal').modal('hide');
	                        loadTable();
	                      } else {
	                        alert("科研成果不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	            })
	          }
		
}

//触发修改里面的下拉框
function showSelectedUpdate() {		
		
		$('#medalTeacherIdUpdate').selectpicker({
			width:400,	
		});	
		
		$('#medalTypeUpdate').selectpicker({
			width:400,	
		});	
		
		$('#medalGradeUpdate').selectpicker({
			width:400,	
		});	
		
		$('#medalLevelUpdate').selectpicker({
			width:400,	
		});	
}

function getTeacherId(){	//得到搜索栏下拉框中选中的主持人id
	var num=0;
	var index=0;
	var teacherString=$('#medalTeacher option:selected').val();
  	var	teacher = new Array();
  	var teacherId = new Array();
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  		
			  		
			  		for (var i=0; i<data.length; i++){
						teacher.push(data[i].name +"(" + data[i].college + ")");
						teacherId.push(data[i].id);
					}
			  		for (var i=0; i<data.length; i++) {
			  			if (teacher[i] == teacherString){
			  				num=1;
			  				index = i;
			  				break;
			  			}
			  		}
			  		
			   	},
			   	error: function(){
			   		alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	if (num != 0) {
		return teacherId[index];
	}
	else{
		return '';
	}
}

//加载更新主持人下拉框中的值
function showTeacherUpdateId(){	
	$.ajax("/lims/teacher/list",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		
			  		}),
			  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  	
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#medalTeacherIdUpdate.selectpicker').append(opt);
					}
							
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

//得到增加中是否主办方的状态
function dealState() {
	switchState=$('#sponsorAdd').bootstrapSwitch('state');
	if(switchState) {
		return 1;
	}else {
		return 0;
	}
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
			height: "300px",
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

//时间日期插件
function paperTime(){
	$('.form_date').datetimepicker({
    format: 'yyyy-mm-dd',
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
});
	
//把当前时间赋值给论文时间
	var date=new Date();
	var year=change(date.getFullYear());
	var month=change(date.getMonth());
	var day=change(date.getDate());
	var time=year+"-"+month+"-"+day;
	$("#winningTimeAdd").val(time);
	
	
}
function change(n){
	return n>9?n:"0"+n;
}

//时间日期选择器图标
function addIcon(){
	$(".icon-arrow-left").html("<span class='glyphicon glyphicon-chevron-left'>");
	$(".icon-arrow-right").html("<span class='glyphicon glyphicon-chevron-right'>");
}
function photoUpload(photofile) {
		
	$(photofile).uploadFile({
      	url:"/lims/enclosure/singleSave",     //文件上传url
	    fileName:"medal",               //提交到服务器的文件名
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
	            contentType: "application/json",
	            dataType: "json",
	           	data:JSON.stringify({"module" : "medal", "name" : data.information}),
	            success: function(data) 
	            {
	                if(data.state===0){
	                    pd.statusbar.hide();        //删除成功后隐藏进度条等
	                    $('#photo_save').val("");  // 注册时用
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
	        	alert("upload success");
	        }
	    }
	});
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
      	"module":"medal", "name" : photoname
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {

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
		    fileName:"medal",               //提交到服务器的文件名
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
		            	"module":"medal", "name" : data.information
		    	  	}),
		            success: function(data) 
		            {
		                if(data.state===0){
		                    pd.statusbar.hide();        //删除成功后隐藏进度条等
		                    $('#photoUploadUpdate').val("");  // change时用

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
		        	$('#photoUploadUpdate').val(data.information);
		        	
		        	alert("upload success");
		        }
		    }
		});

}	
