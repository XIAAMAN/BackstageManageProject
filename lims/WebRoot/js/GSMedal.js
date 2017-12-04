//通知相关的js
var fileIds = new Array();
var addcontent;
var newsModId;
var newsModState;
$(function() {
	
	  loadTable();
	  showTeacherId();
	  querySelectActive();
	
	// 增加验证栏
				$("#GSMedalAddCheck").Validform({
					btnSubmit:".btn_sub",
					tiptype:function(msg,o,cssctl){
			            //msg：提示信息;
			            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			            var objtip=o.obj.siblings(".Validform_checktip");
			            cssctl(objtip,o.type);
			            objtip.text(msg);          
			        },
			 		showAllError:true,
			 		beforeSubmit:function(curform){
			 			
			 			GSMedalAdd();
			 			
			 			return false;
			 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			 			//这里明确return false的话表单将不会提交; 
			 		}

			 	});
				
				
				$("#GSMedalModCheck").Validform({
					//btnSubmit:".btn_sub",
					tiptype:function(msg,o,cssctl){
			            //msg：提示信息;
			            //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			            //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			            var objtip=o.obj.siblings(".Validform_checktip");
			            cssctl(objtip,o.type);
			            objtip.text(msg);          
			        },
			 		showAllError:true,
			 		beforeSubmit:function(curform){		
			 			GSMedalModSubmit();
			 			return false;
			 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			 			//这里明确return false的话表单将不会提交; 
			 		}

			 	});	
  // 加载表格
				

 
	// 当页面currentPage和pageSize发生改变
//当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		loading1();
		var temp=getTeacherId();	//得到id
		temp=temp.toString();		//将id转换成字符串
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/gsmedal/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        
		        	"name"		:	$('#name_list').val(),
			  		"teacherId"	:	temp,
			  		"level"	:	$('#level_list').val()=="无"?"":$('#level_list').val(),	  		
			  		"grade": $('#grade_list').val()=="无"?"":$('#grade_list').val(),
			  			
			  		"pageIndex": currentPage,
			         "pageSize": pageSize
		        	
		          }),
		        async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		        // 请求成功后的回调函数。
		        success: function(data){
		        	
		          $("#userInfo-table").bootstrapTable('load', data);
		          
			  		
		          $('table').find('tr').each(function(){
			  			var str=$(this).children('td').eq(0).text();
			  			var nstr="<label class='titleLabelMax' title="+str+" data-toggle='tooltip' >"+str+"</label>";
			  			$(this).children('td').eq(0).text("");
			  			$(this).children('td').eq(0).append(nstr);
			  		})
		          
		       
		       
		        },
		        error: function(){
		          alert("请求错误，请检查网络连接");
		       }
		  });
		alert("asd2");
		removeLoading('test');
		
	})
	
	
	
});


//触发查询中的下拉框
function querySelectActive() {

	$('#grade_list').selectpicker({
		width: 150
	  }); 
	  $('#teacher_list').selectpicker({
			width:180
	  }); 
	
	
	$('#level_list').selectpicker({
		width: 150
	  }); 
	$('#grade_save').selectpicker({
		width: 300
	  }); 
	  $('#teacher_save').selectpicker({
			width:300
	  }); 
	
	
	$('#level_save').selectpicker({
		width: 300
	  }); 
	$('#grade_modal').selectpicker({
		width: 400
	  }); 
	  $('#teacher_modal').selectpicker({
			width:400
	  }); 
	
	
	$('#level_modal').selectpicker({
		width: 400
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
					$('#teacher_list').append(opt);
					
					
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#teacher_list').append(opt);
						var opt = new Option(teacher);
						var tid='teacher_save'
						$('#'+tid).append(opt);
						var opt = new Option(teacher);
						$('#teacher_modal').append(opt);
					}
					$('#teacher_list').val('');
					$('#teacher_save').val('');
					$('#teacher_modal').val('');
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}



function GSMedalModSubmit(){
	var temp=getTeacherId('teacher_modal');	//得到id
	temp=temp.toString();		//将id转换成字符串
    //单击提交后修改
    $.ajax("/lims/gsmedal/update",
      {
        dataType: "json", // 预期服务器返回的数据类型。
          type: "post", //  请求方式 POST或GET
          crossDomain:true,  // 跨域请求
          contentType: "application/json", //  发送信息至服务器时的内容编码类型
          // 发送到服务器的数据
          data:JSON.stringify({
        	  
        	  "param"	: {
            "id"    : newsModId,
            "name"		:	$('#name_modal').val(),
	  		"teacher"	:	{"id": temp},
	  		"level"		:	$('#level_modal').val(),
	  		"grade"	:	$('#grade_modal').val(),
	  		"studentAll"		:	$('#studentAll_modal').val(),
	  		"winningTime"	:	$('#winningTime_modal').val(),
	  		"abstracts"		:    $('#abstracts_modal').val()
	  		
	  		
        		},
    		  	"fileIds" : fileIds

            }),
          async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
          // 请求成功后的回调函数。
          success: function(data){
        	  
            if (data.state == "0") {
              alert(data.information.name +" " + "修改成功! ");
              $('#updateNoticeModal').modal('hide');
              loadTable();

            } else {
              alert("修改失败!  错误:" + data.message);
            }
          },
          error: function(){
          alert("请求错误，请检查网络连接");
         }
    });

}






function responseHandler(result){
	 //如果没有错误则返回数据，渲染表格
	 return {
	     total : result.total, //总页数,前面的key必须为"total"
	     data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
	 };
};

function GSMedalAdd(){
	
	var temp=getTeacherId('teacher_save');	//得到id
	temp=temp.toString();		//将id转换成字符串
	
$.ajax("/lims/gsmedal/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	
	  	data:JSON.stringify({
		  	"param"	: {
		  		"name"		:	$('#name_save').val(),
		  		"teacher"	:	{"id": temp},
		  		"level"		:	$('#level_save').val(),
		  		"grade"	:	$('#grade_save').val(),
		  		"studentAll"		:	$('#studentAll_save').val(),
		  		"winningTime"	:	$('#winningTime_save').val(),
		  		"abstracts"		:    $('#abstracts_save').val()
		  	},
		  	"fileIds" : fileIds
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		
	  		if (data.state == 0) {
	  			alert("增加成功!");
	  		  $('#addNoticeModal').modal('hide');
	  		$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
              loadTable();
	  		} else {
	  			alert("增加失败!  错误:" + data.message);
	  		}
	  		
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
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

function getTeacherId(s){	//得到搜索栏下拉框中选中的主持人id
	var num=0;
	var index=0;
	
	var teacherString=$('#'+s+' option:selected').val();
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


function loadTable() {
	
	loading1();
	paperTime();
	var temp=getTeacherId('teacher_list');	//得到id
	temp=temp.toString();		//将id转换成字符串

	$.ajax("/lims/gsmedal/listDynaPageMap",
			{
		dataType: "json", // 预期服务器返回的数据类型。
			type: "post", //  请求方式 POST或GET
  	crossDomain:true,  // 跨域请求
  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
  	
  	// 发送到服务器的数据
	data:JSON.stringify({
		
	  		"name"		:	$('#name_list').val(),
	  		"teacherId"	:	temp,
	  		"level"	:	$('#level_list').val()=="无"?"":$('#level_list').val(),	  		
	  		"grade": $('#grade_list').val()=="无"?"":$('#grade_list').val(),
	  				
	  				
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
function actionFormatter(value, row, index) {
	   return '<button id="btn_look" type="button" class="newsLook btn btn-primary" style="margin-right:5px;padding:4px 8px" data-toggle="modal"  data-target="#lookNoticeModal">'+
	   		  ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
	   		  '</button>' +
	   		  '<button id="btn_edit" type="button" class="newsMod btn btn-warning" data-toggle="modal" style="padding:4px 8px" data-target="#updateNoticeModal">'+
	          ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
	          '</button>' +
	          ' <button id="btn_delete" type="button" class="newsDelete btn btn-danger" style="padding:4px 8px" data-toggle="modal"  data-target="#deleteNoticeModal">' +
	          ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
	          ' </button>'
	}

	window.actionEvents = {
			
			 //详细操作
		    'click .newsLook': function(e, value, row, index) {
		    	
		            // 从后台查询此用户，为修改输入框提前赋值
		    	$.ajax("/lims/gsmedal/get",
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
		                    	    $('#name_look').val(data.information.name);
		                    	    $('#level_look').val(data.information.level);
		                    	    $('#grade_look').val(data.information.grade);
		                    	    $('#teacherName_look').val(data.information.teacher.name);
		                    	    $('#studentAll_look').val(data.information.studentAll);
			                        $('#winningTime_look').val(data.information.winningTime);
		
			                        
			                        $('#abstracts_look').html(data.information.abstracts);	
			                       // KindEditor.remove('textarea[name="content"]');
			                      //  KindEditor.remove('textarea[name="addcontent"]');
			                      //  KindEditor.remove('textarea[name="modalcontent"]');
			                        
			                      //  kedit('textarea[name="content"]',data.information.content);
			                        
			                        
			                        
			                      
			                        //编辑框赋值
			                        

			          
			                        
			                        //附件
			                        var inp,inv,enpath;
			                        inp=document.getElementById("enclosures_look");
			                        inv="";
			                       
			                       // enpath="D:\Tomcat\tomcat\webapps\lims\WEB-INF\\uploads\news"+data.information.enclosures[i].fileName+".*";
			                         for(var i=0; i<data.information.enclosures.length; i++){
			                            inv+="<a id="+i+" href='/lims/enclosure/download/gsmedal/"+data.information.enclosures[i].fileName+"'>"+data.information.enclosures[i].oldName+"</a>"+ "<br/>";
			                           
					                    }
			                       //  if(inv!="")
			                         inp.innerHTML=inv;
			                        // else inp.innerHTML="<br>"+"<a href=''>null</a>";
			                        
			                        
			                        
			                        
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
		    'click .newsMod': function(e, value, row, index) {
		    	fileUpload("#fileuploadermod");
		    	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
		            // 从后台查询此用户，为修改输入框提前赋值
		    	$.ajax("/lims/gsmedal/get",
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
		                    	  // 为模态框中的输入框提前赋值
		                    	    $('#name_modal').val(data.information.name);
		                    	    newsModId=data.information.id;
		                    	    
		                    	    //要改变选项
		                    	    $('#level_modal').val(data.information.level);
		                    	    $('#level_modal').selectpicker('refresh');
		                    	    
		                    	    $('#grade_modal').val(data.information.grade);
		                    	    $('#grade_modal').selectpicker('refresh');
		                    	    
		                    	    $('#teacher_modal').val(data.information.teacher.name +"(" + data.information.teacher.college + ")");
		                    	    $('#teacher_modal').selectpicker('refresh');
		                    	   
			                        $('#winningTime_modal').val(data.information.winningTime);
			                        $('#studentAll_modal').val(data.information.studentAll);
			                        
			                       
			                        
			                        //$('#content-modal').val(data.information.content);
			                        
			                        KindEditor.remove('textarea[name="content"]');
			                     
			                        kedit('textarea[id="abstracts_modal"]',data.information.abstracts);
			                        
	  
			                       
			                        
			                     

			                        
			                        //附件
			                        var inp,inv,enpath;
			                        inp=document.getElementById("enclosures_modal");
			                        inv="";
			                        invb="";
			                       
			                       // enpath="D:\Tomcat\tomcat\webapps\lims\WEB-INF\\uploads\news"+data.information.enclosures[i].fileName+".*";
			                         for(var i=0; i<data.information.enclosures.length; i++){
			                            inv+="<a id='a"+i+"' href='/lims/enclosure/download/gsmedal/"+data.information.enclosures[i].fileName+"'>"+data.information.enclosures[i].oldName+"</a>";	
			                            invb="<button type='button' id='b"+i+"' class='btn btn-danger' " + "onclick='deletes("+data.information.enclosures[i].id+","+i+")'>"+"删除"+"</button><br/>";
			                            inv+="  "+invb+"<br>";
					                    }
			                         inp.innerHTML=inv;
			                        
	           
			                        
			                     //放置于ValidForm回调函数   
			                  //  $('#noticeSubmit_update').unbind();

		                      } else {
		                        alert("不存在");
		                      }
		                    },
		                    error: function(){
		                    alert("请求错误，请检查网络连接");
		                   }
		              });
		          
		        },
		        
		        
		        'click .newsDelete' : function(e, value, row, index) {
		            //删除操作
		        	$('#noticeSubmit_delete').unbind();
		            // 获取row.id获取此行id
		            $('#noticeSubmit_delete').click(function() {
		              $.ajax("/lims/gsmedal/delete",
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
		                        alert("已删除");
		                        $('#deleteNoticeModal').modal('hide');
		                        loadTable();
		                      } else {
		                        alert("不存在");
		                      }
		                    },
		                    error: function(){
		                    alert("请求错误，请检查网络连接");
		                   }
		              });
		            })
		          }
		    }
	
	

	//删除附件
	function deletes(id,i){
		$.ajax("/lims/enclosure/delete",
			{
				dataType: "json", // 预期服务器返回的数据类型。
					type: "post", //  请求方式 POST或GET
				crossDomain:true,  // 跨域请求
				contentType: "application/json", //  发送信息至服务器时的内容编码类型
				// 发送到服务器的数据
				data:JSON.stringify({
					"enclosure" : {
						"id" : id
					},
					"table" : "gsmedal"
				}),
				async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
				// 请求成功后的回调函数。
				success: function(data){
					if (data.state == 0) {
						alert("删除成功!");
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
		
	//赋值给当前时间
		var date=new Date();
		var year=change(date.getFullYear());
		var month=change(date.getMonth())+1;
		var day=change(date.getDate());
		var time=year+"-"+month+"-"+day;
		$("#winningTime_save").val(time);
		
		
	}
	function change(n){
		return n>9?n:"0"+n;
	}

	//时间日期选择器图标
	function addIcon(){
		$(".icon-arrow-left").html("<span class='glyphicon glyphicon-chevron-left'>");
		$(".icon-arrow-right").html("<span class='glyphicon glyphicon-chevron-right'>");
	}
	
	//清除错误提示信息
	function clearWrongMsg() {
		
		KindEditor.remove('textarea[name="content"]');
		kedit('textarea[id="abstracts_save"]',"");
		$("#GSMedalAddCheck").Validform().resetForm();	//重置表单
		//修改提示内容
		$("#name_save").next().text("请输入项目名称");
		$("#studentAll_save").next().text("请输入学生姓名");
		fileUpload("#fileuploader");
		$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
	}
	
	
	function fileUpload(fileuploader) {
	    $(fileuploader).uploadFile({
	        url:"/lims/enclosure/upload", //后台处理方法
	        fileName:"gsmedal",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
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
	                //for(var i=0;i<data.information.length;i++){
	                	//var inputNode='<input type="hidden" id="'+data.information[i]+'" name="fileIds" value="'+data.information[i]+'" >';
	                    //newsform.append(inputNode);
	                    fileIds.push(data.information.id)
	                    
	               // }
	            }else{
	                alert("上传失败");
	            }
				
	        },
	        
	        showDelete: true,//删除按钮
	        statusBarWidth:550,
	        dragdropWidth:550,
	        //删除按钮执行的方法
	        deleteCallback: function (data, pd) {
	            var fileId=data.data[0].fileId;
	            alert(fileId)
	            $.post("/lims/file/notice/delete", {fileId:fileId},
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

	
	//文本编辑器

	var editor;
	function kedit(kedit,content){
		
		
		//	alert("KindEditor");
			editor = KindEditor.create(kedit,{
				themeType:'simple',
				resizeType:2,
				height : "450px",
				uploadJson : 'kindEditor/jsp/upload_json.jsp',
				fileManagerJson : 'kindEditor/jsp/file_manager_json.jsp',
				allowFileManager:true,
				width: '100%',
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

	

