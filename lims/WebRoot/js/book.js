
var bookId;		//修改传的id
$(function() {
	
  // 加载表格
  loadTable();
  var opt= new Option("无");
  $("#teacher_list").append(opt);
  $("#teacher_list").val("无");
  showTeacherId("#teacher-add");
  showTeacherId("#teacher-modal");
  showTeacherId("#teacher_list");
 
  selectActive();
	//对增加信息进行验证
  $("#bookAddCheck").Validform({
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
  
	//对修改信息进行验证
  $("#bookUpdateCheck").Validform({
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
  
 
  $("#queryBtn").click(function() {
		$('#userInfo-table').bootstrapTable('refresh');    //刷新表格
	});
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
			
		//刷新表格
		loading1();
		var temp=getTeacherId("#teacher_list");	//得到id
		temp=temp.toString();		//将id转换成字符串
		var pageSize = size.toString();
		var currentPage = number.toString();
		
		
		$.ajax("/lims/book/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"name"	:	$('#name_list').val(),
		        	"publisher"	:	$('#publisher_list').val(),
		        	"teacherId" :	temp,
			  		"pageIndex"		:	currentPage,
			  		"pageSize"		:	pageSize
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
	// 鼠标点击查询按钮后，对表格进行刷新

});

function showTeacherId(teacherId){		//搜索栏中的主持人下拉框
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
						$(teacherId).append(opt);
					}

			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

function getTeacherId(bookTeacherId){	//得到搜索栏下拉框中选中的主持人id
	var num=0;
	var index=0;
	var teacherString=$(bookTeacherId).find('option:selected').val();
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
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[id="abstracts-add"]',"");
	$("#bookAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#name-add").next().text("请输入教材名称");
	$("#teacherId-add").next().text("请输入教师ID");
	$("#publisher-add").next().text("请输入出版社名称");
	$("#ranking-add").next().text("请输入排名");
	$("#totalWords-add").next().text("请输入总字数");
	$("#referenceWords-add").next().text("请输入参编字数");
}

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
	paperTime();
	var temp=getTeacherId("#teacher_list");	//得到id
	temp=temp.toString();		//将id转换成字符串
	//单选框赋值
    var radio=document.getElementsByName('sponsor-look');
    var type=$(":radio[id='sponsor-look']:checked").val();
	$.ajax("/lims/book/listDynaPageMap",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"name"		:	$('#name_list').val(),
			  		"teacherId" :	temp,
			  		"publisher"	:	$('#publisher_list').val(),
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
	$("#publishTime-add").val(time);
	
	
}
function change(n){
	return n>9?n:"0"+n;
}


function actionFormatter(value, row, index) {
    return '<button id="btn_look" type="button" class="lookBtn btn btn-primary" data-toggle="modal"  data-target="#lookUserModal">'+
    	   ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
    	   '</button>' +
    	   '<button id="btn_edit" type="button" class="mod btn btn-warning" data-toggle="modal"  data-target="#updateUserModal">'+
           ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
           '</button>' +
          ' <button id="btn_delete" type="button" class="delete btn btn-danger" style="padding:4px 8px" data-toggle="modal"  data-target="#deleteUserModal">' +
          ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
          ' </button>'
           
}




//下拉框激活
function selectActive() {
	$('#teacher_list').selectpicker({
        width: 180
    });
	
	$('#teacher-add').selectpicker({
       width: 300
	});
	
	$('#teacher-modal').selectpicker({
	       width: 400
		});
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



//表格  - 操作 - 事件
window.actionEvents = {
		//
	    'click .lookBtn': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/book/get",
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
	                        $('#name-look').val(data.information.name);
	                        $('#teacherID-look').val(data.information.teacher.name +"(" + data.information.teacher.college + ")");
	                        $('#publisher-look').val(data.information.publisher);
	                        $('#ranking-look').val(data.information.ranking);
	                        $('#totalWords-look').val(data.information.totalWords);
	                        $('#referenceWords-look').val(data.information.referenceWords);
	                        $('#sponsor-look').val(data.information.sponsor);
	                        $('#publishTime-look').val(data.information.publishTime);
	                        $('#abstracts-look').html(data.information.abstracts);
	                      } else {
	                        alert("教材不存在");
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
	$('#teacher-modal').selectpicker({
		width:400,	
	});	
      $.ajax("/lims/book/get",
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
                	
                  $('#name-modal').val(data.information.name);
                  $('#teacher-modal').val(data.information.teacher.name +"(" + data.information.teacher.college + ")");
                  $('#publisher-modal').val(data.information.publisher);
                  $('#ranking-modal').val(data.information.ranking);
                  $('#totalWords-modal').val(data.information.totalWords);
                  $('#referenceWords-modal').val(data.information.referenceWords);
                  $("#publishTime-modal").val(data.information.publishTime); 
                  
                  $('#teacher-modal').selectpicker('refresh');
                  //单选框赋值
	                var type=data.information.sponsor;
	            	var radio=document.getElementsByName('sponsor-modal');
	            	for(var i=0;i<radio.length;i++){
	            		if(type == radio[i].value){
		                	radio[i].checked="checked";
		                }
	            	}
	            	
                  KindEditor.remove('textarea[name="content"]');
	              	kedit('textarea[id="abstracts-modal"]',data.information.abstracts);
	              	bookId = row.id;
                  
                } else {
                  alert("项目不存在");
                }
              },
              error: function(){
              alert("请求错误，请检查网络连接");
             }
        });
    
  },
  'click .delete' : function(e, value, row, index) {
      //删除操作
	
  	$('#submit_delete').unbind();
      // 获取row.id获取此行id
      $('#submit_delete').click(function() {
        $.ajax("/lims/book/delete",
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
                  alert(data.information.name+" 教材已删除");
                  $('#deleteUserModal').modal('hide');
                  loadTable();
                } else {
                  alert("该教材不存在");
                }
              },
              error: function(){
              alert("请求错误，请检查网络连接");
             }
        });
      })
    }
	        
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
				height: '300px',
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

function addFunction(){
	//单选框赋值
    var radio=document.getElementsByName('sponsor-add');
    var type=$(":radio[id='sponsor-add']:checked").val();
    temp = getTeacherId("#teacher-add");
    temp = temp.toString();
	$.ajax("/lims/book/add",
	        {
	            dataType: "json", // 预期服务器返回的数据类型。
	            type: "post", //  请求方式 POST或GET
	            crossDomain:true,  // 跨域请求
	            contentType: "application/json", //  发送信息至服务器时的内容编码类型
	            // 发送到服务器的数据
	            data:JSON.stringify({
		           
	            	"name"			:	$('#name-add').val(),
	            	"teacher"	:	{"id":temp},
	    	  		"publisher"		:	$('#publisher-add').val(),
	    	  		"ranking"		:	$('#ranking-add').val(),
	    	  		"totalWords"	:	$('#totalWords-add').val(),
	    	  		"referenceWords":	$('#referenceWords-add').val(),
	    	  		"publishTime"	: 	$('#publishTime-add').val(),
	    	  		"sponsor"		:	type,
	    	  		"abstracts"		:	$('#abstracts-add').val()
		    
	            }),
	            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	            // 请求成功后的回调函数。
	            success: function(data){
	                if (data.state == "0") {
	                	fileIds=[];
	                    alert("教材添加成功!");
	                    $('#addUserModal').modal('hide');
	                    loadTable();	                    
	                   
	                } else {
	                    alert("注册失败! 错误:" + data.message);
	                }
	            },
	            error: function(){
	                alert("请求错误，请检查网络连接");
	           }
	    });
	        return true;  
}

function updateFunction() {
	
	//单选框赋值
    var radio=document.getElementsByName('sponsor-modal');
    var type=$(":radio[id='sponsor-modal']:checked").val();
    temp = getTeacherId("#teacher-modal");
    temp = temp.toString();
	 $.ajax("/lims/book/update",
             {
        	   
               dataType: "json", // 预期服务器返回的数据类型。
                 type: "post", //  请求方式 POST或GET
                 crossDomain:true,  // 跨域请求
                 contentType: "application/json", //  发送信息至服务器时的内容编码类型
                 // 发送到服务器的数据
                 data:JSON.stringify({
          		 "id"    : bookId,
                 "name"  : $('#name-modal').val(),
                 "teacher"  : {"id": temp},
                 "publisher" : $('#publisher-modal').val(),
                 "ranking"   : $('#ranking-modal').val(),
                 "publishTime" : $("#publishTime-modal").val(),
                 "abstracts"  : $('#abstracts-modal').val(),
                  "sponsor"	:	type,  
                  "totalWords"	:	$('#totalWords-modal').val(),
                  "referenceWords": $('#referenceWords-modal').val()

                   }),
                 async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                 // 请求成功后的回调函数。
                 success: function(data){
                   if (data.state == "0") {
                     alert(data.information.name + "修改成功! ");
                     $('#updateUserModal').modal('hide');
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
//时间日期选择器图标
function addIcon(){
	$(".icon-arrow-left").html("<span class='glyphicon glyphicon-chevron-left'>");
	$(".icon-arrow-right").html("<span class='glyphicon glyphicon-chevron-right'>");
}
