var teacherId;

$(function() {
	//初始化表格
	//initTable();
	// 加载表格
	loadTable();
//	$('input').eq(0).focus();
	
	addSelecteActive();
	
	$("#queryBtn").click(function() {
		$('#userInfo-table').bootstrapTable('refresh'); //刷新表格
	});
	
	//修改验证
	$("#teacherUpdateCheck").Validform({
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
 			teacherModify();
 			return false;
 		}
 	});
		   
	//增加验证
    $("#teacherAddCheck").Validform({
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
 			teacherAdd();
 			return false;
 		}
 	});
	
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e, number, size) {
		//刷新表格
		loading1();
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/teacher/listDynaPage", //**************为何此处写teacher/list 也可以，运行时会自动加上前面的一段？？？？？？？？？？
			{
				dataType : "json", // 预期服务器返回的数据类型。
				type : "post", //  请求方式 POST或GET
				crossDomain : true, // 跨域请求
				contentType : "application/json", //  发送信息至服务器时的内容编码类型
				// 发送到服务器的数据
				data : JSON.stringify({
					"param":{
						"name" : $('#Name').val(),
						"title" : $('#Title').val()=="无"? "":$('#Title').val(),
						"major" : $('#Major').val(),
						"researchInterest" : $('#ResearchInterest').val(),
						"director" : $('#Director').val()=="无"? "":$('#Director').val(),
					},
					"pageModel":{
						"pageIndex" : currentPage, 
						"pageSize" : pageSize
					}
					
				}),
				async : false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
				// 请求成功后的回调函数。
				success : function(data) {
					$("#userInfo-table").bootstrapTable('load', data);
				},
				error : function() {
					alert("请求错误，请检查网络连接");
				}
			});
		removeLoading('test');
	})
	
	//图片文件上传
	$("#fileuploader").uploadFile({
		      	url:"/lims/enclosure/singleSave",     //文件上传url
			    fileName:"teacher",               //提交到服务器的文件名
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
			            dataType: "json",
			            contentType : "application/json",
			            data:JSON.stringify({
			            	"module":"teacher", "name" : data.information
			    	  	}),
			            success: function(data) 
			            {
			                if(data.state===0){
			                    pd.statusbar.hide();        //删除成功后隐藏进度条等
			                    $('#addPhoto').val("");
			                    $('#photo_updaie').val("");
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
			        	$('#addPhoto').val(data.information);
//			        	$('#photo_update').val(data.information);
			        	alert("upload success");
			        }
			    }
			});

});

//增加方法	
	function teacherAdd() {
		$.ajax("http://localhost:8080/lims/teacher/add",
			{
				dataType : "json", // 预期服务器返回的数据类型。
				type : "post", //  请求方式 POST或GET
				crossDomain : true, // 跨域请求
				contentType : "application/json", //  发送信息至服务器时的内容编码类型
				// 发送到服务器的数据
				data : JSON.stringify({
					"name" : $('#addName').val(),
					"major" : $('#addMajor').val(),
					
					"college" : $('#addCollege').val(),
					"graduateCollege" : $('#addGraduateCollege').val(),
					"researchInterest" : $('#addResearchInterest').val(), /*点击关闭按钮为何会清空表单数据？？？？？？？？？？*/
					"title" : $('#addTitle').val(),
					"position" : $('#addPosition').val(),
					"director" : $('#addDirector').val(),
					"email" : $('#addEmail').val(),
					"photo" : $('#addPhoto').val(),
					"description" : $('#addDescription').val()
				}),
				async : true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
				// 请求成功后的回调函数。
				success : function(data) {
					if (data.state == "0") {
						fileIds = [];
						alert("用户添加成功!");
						$('#addUserModal').modal('hide');
						$('.ajax-file-upload-container').html("");
						loadTable();
						
					} else {
						alert("添加失败!  错误:" + data.message);
					}

				},
				error : function() {
					alert("请求错误，请检查网络连接");
				}
			});

	}
//激活修改里面的下拉框
function updateSelectActive() {
	$('#modifyTitle').selectpicker({
		width:400,
	});
	$('#modifyDirector').selectpicker({
		width:400,
	});
}
//激活增加和首页里面的下拉框
function addSelecteActive() {
	$('#Title').selectpicker({
		width:200,
	});
	$('#Director').selectpicker({
		width:200,
	});
	
	$('#addTitle').selectpicker({
		width:300,
	});
	$('#addDirector').selectpicker({
		width:300,
	});
}
//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[name="content"]',"");
	$("#teacherAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#addName").next().text("请输入教师姓名");
	$("#addMajor").next().text("请输入专业名称");
	$("#addCollege").next().text("请输入所属学院");
	$("#addGraduateCollege").next().text("请输入毕业院校");
	$("#addResearchInterest").next().text("请输入研究方向");
	$("#addPosition").next().text("请输入您的职务");
	$("#addEmail").next().text("请输入您的电子邮箱");
	
}

//修改方法
function teacherModify() {  
	/*  submit_update*/
	//单击提交后修改
	$.ajax("http://localhost:8080/lims/teacher/update",
		{
			dataType : "json", // 预期服务器返回的数据类型。
			type : "post", //  请求方式 POST或GET
			crossDomain : true, // 跨域请求
			contentType : "application/json", //  发送信息至服务器时的内容编码类型
			// 发送到服务器的数据
			data : JSON.stringify({
				"id" : teacherId,
				"name" : $('#modifyName').val(),
				"major" : $('#modifyMajor').val(),
				"college" : $('#modifyCollege').val(),
				"graduateCollege" : $('#modifyGraduateCollege').val(),
				"researchInterest" : $('#modifyResearchInterest').val(),
				"title" : $('#modifyTitle').val(),
				"position" : $('#modifyPosition').val(),
				"director" : $('#modifyDirector').val(),
				"email" : $('#modifyEmail').val(),
				"photo":$('#photo-modal').val(),
				"description" : $('#modifyDescription').val(),                           //提交成功后要清空样式吗？？？？？？？？
			}),
			async : true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			// 请求成功后的回调函数。
			success : function(data) {
				if (data.state == "0") {
					alert("修改成功! ");
					$('#updateUserModal').modal('hide');
					loadTable();
				} else {
					alert("状态 " + data.state + " " + "修改失败!  错误:" + data.message);
				}
			},
			error : function() {
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

//加载表格，将服务器返回的json数据加载到表格当中
function loadTable() {
	loading1();
	$.ajax("/lims/teacher/listDynaPage",       //**************为何此处写teacher/list 也可以，运行时会自动加上前面的一段？？？？？？？？？？
		{
			dataType : "json", // 预期服务器返回的数据类型。
			type : "post", //  请求方式 POST或GET
			crossDomain : true, // 跨域请求
			contentType : "application/json", //  发送信息至服务器时的内容编码类型
			// 发送到服务器的数据
			data : JSON.stringify({
				"param":{
					"name" : $('#Name').val(),
					"title" : $('#Title').val()=="无"? "":$('#Title').val(),
					"major" : $('#Major').val(),
					"researchInterest" : $('#ResearchInterest').val(),
					"director" : $('#Director').val()=="无"? "":$('#Director').val(),
				},
				"pageModel":{
					"pageIndex" : $('#userInfo-table').attr('data-page-number'), 
					"pageSize" : $('#userInfo-table').attr('data-page-size')
				}
				
			}),
			async : false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			// 请求成功后的回调函数。
			success : function(data) {
				$("#userInfo-table").bootstrapTable('load', data);
			},
			error : function() {
				alert("请求错误，请检查网络连接");
			}
		});
	removeLoading('test');
}
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
	        	"module":"teacher", "name" : photoname
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
			    fileName:"teacher",               //提交到服务器的文件名
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
			            	"module":"teacher", "name" : data.information
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
	
	
	
	
//value: 所在collumn的当前显示值，
//row:整个行的数据 ，对象化，可通过.获取
//表格-操作 - 格式化 
function actionFormatter(value, row, index) {
	return '<button id="btn_look" type="button" class="teacherLook btn btn-primary" style="margin-right:5px;padding:4px 8px" data-toggle="modal"  data-target="#lookUserModal">'+
		  ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
		  '</button>' +
		  '<button id="btn_edit" type="button" class="teacherMod btn btn-warning" data-toggle="modal"  data-target="#updateUserModal">' +
		' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
		'</button>' +
		' <button id="btn_delete" type="button" class="delete btn btn-danger" data-toggle="modal"  data-target="#deleteUserModal">' +
		' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
		' </button>'
}
//表格  - 操作 - 事件
window.actionEvents = {
	
		//查看操作
	    'click .teacherLook': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	    	$.ajax("/lims/teacher/get",
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
								$('#lookName').val(data.information.name);
								$('#lookMajor').val(data.information.major);
								$('#lookCollege').val(data.information.college);
								$('#lookGraduateCollege').val(data.information.graduateCollege);
								$('#lookResearchInterest').val(data.information.researchInterest);

								$('#lookTitle').val(data.information.title);
								$('#lookPosition').val(data.information.position);
								$('#lookDirector').val(data.information.director);
								$('#lookEmail').val(data.information.email);
								$('#lookDescription').html(data.information.description);
								  
		                        //图片
		                        var ph,phpath;
		                       // phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
		                        ph=document.getElementById("photo_look");
		                       
		                        if(!data.information.photo)
		                        	{ph.innerHTML="";}
		                        else{ph.innerHTML="<img height='200px' width='150px' src='/lims/enclosure/download/teacher/"+data.information.photo+"'/>";
		                        }	
		                         
	                      } else {
	                        alert("用户不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	          
	        },
		
	//修改操作
	'click .teacherMod' : function(e, value, row, index) {
		updateSelectActive();
		// 从后台查询此用户，为修改输入框提前赋值                                                                          
		$.ajax("http://localhost:8080/lims/teacher/get",
			{
				dataType : "json", // 预期服务器返回的数据类型。
				type : "post", //  请求方式 POST或GET
				crossDomain : true, // 跨域请求
				contentType : "application/json", //  发送信息至服务器时的内容编码类型
				// 发送到服务器的数据
				data : JSON.stringify({
					"id" : row.id,
				}),
				async : false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
				// 请求成功后的回调函数。
				success : function(data) {
					if (data.state == "0") {
							// 为模态框中的输入框提前赋值
							$('#modifyName').val(data.information.name);
							$('#modifyMajor').val(data.information.major);
							$('#modifyCollege').val(data.information.college);
							$('#modifyGraduateCollege').val(data.information.graduateCollege);
							$('#modifyResearchInterest').val(data.information.researchInterest);

							$('#modifyTitle').val(data.information.title);
							$('#modifyPosition').val(data.information.position);
							$('#modifyDirector').val(data.information.director);
							$('#modifyEmail').val(data.information.email);
							$('#photo-modal').val("");
							   
	                        //图片
	                        var ph,phpath;
	                     //   phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
	                        ph=document.getElementById("photo-modal");
	                        if(data.information.photo) {
	                        	ph.innerHTML="<img height='200px' width='150px' src='/lims/enclosure/download/teacher/"+data.information.photo+"'/>" +
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
							
							
							
							
							
							
							
							
							$("#modifyTitle").selectpicker("refresh");
							$("#modifyDirector").selectpicker("refresh");
							teacherId = row.id;
							
							KindEditor.remove("textarea[name='content']");
							kedit('textarea[id="modifyDescription"]',data.information.description);
					} else {
						alert("用户不存在");
					}
				},
				error : function() {
					alert("请求错误，请检查网络连接");
				}
			});

	},
	'click #btn_delete' : function(e, value, row, index) {
		//删除操作
		// 获取row.id获取此行id
		$('#submit_delete').unbind();
		$('#submit_delete').click(function() {
			$.ajax("http://localhost:8080/lims/teacher/delete",
				{
					dataType : "json", // 预期服务器返回的数据类型。
					type : "post", //  请求方式 POST或GET
					crossDomain : true, // 跨域请求
					contentType : "application/json", //  发送信息至服务器时的内容编码类型
					// 发送到服务器的数据
					data : JSON.stringify({
						"id" : row.id,
					}),
					async : false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
					// 请求成功后的回调函数。
					success : function(data) {
						if (data.state == "0") {
							alert("已删除用户!");
							$('#deleteUserModal').modal('hide');
							loadTable();
						} else {
							alert("用户不存在");
						}
					},
					error : function() {
						alert("请求错误，请检查网络连接");
					}
				});
		})
	}
}

// function:responseHandler请求数据成功后，渲染表格前的方法
//请求成功方法
function responseHandler(result) {
	//如果没有错误则返回数据，渲染表格
	return {
		total : result.total, //总页数,前面的key必须为"total"
		data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
	};
};


