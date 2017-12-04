var fileIds = new Array();
var patentId;
$(function() {
	//初始化表格
	//initTable();
  // 加载表格
  loadTable();
  addIcon();
  selectDateYear();
  addSelectActive();
  $('#selectStartYear').selectpicker({
      width:100
  });
	
	$('#selectEndYear').selectpicker({
     width:100
	});
	$('#selectPatentType').selectpicker({
     width:120
  });
	
	$('#selectStatus').selectpicker({
     width:120
	});
	//对增加信息进行验证
  $("#patentAddCheck").Validform({
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
  
	//对增加信息进行验证
  $("#patentUpdateCheck").Validform({
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
  //起始年份监听事件,当起始年份发生改变时，终止时间也发生改变
  $('#selectStartYear').on('change', function() {
	  	
		 var selectText = $(this).find('option:selected').text();
		 if (selectText != "无"){
			 $('#selectEndYear').empty();
			 selectText = parseInt(selectText);
			 var opt = new Option("无");
			 $('#selectEndYear').append(opt);
			 for(i=selectText;i<=2017;i++) {
				 var opt = new Option(i);
				 $('#selectEndYear').append(opt);
			 }
			 $('#selectEndYear').val('2017');	 
			 surfaceYear();
		 }
	});
  $("#queryBtn").click(function() {
		$('#userInfo-table').bootstrapTable('refresh');    //刷新表格
	});
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		//刷新表格
		loading1();
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/patent/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
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
		removeLoading('test');
	})
	// 鼠标点击查询按钮后，对表格进行刷新

});

//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[id="abstracts-add"]',"");
	$("#patentAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#patentName-add").next().text("请输入专利标题");
	$("#patentAuthor-add").next().text("请输入作者");
	$("#patentAuthorAll-add").next().text("请输入其他作者");	

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

//作者(全)
function authorFormatter(value, row, index){
	var author = row.author;
	var authorAll = row.authorAll;
	var tolAuthor;
	if(authorAll == 0 || authorAll == null){
		tolAuthor = author + " " + authorAll;
	}else{
		tolAuthor = author + "," + authorAll;
	}
	return tolAuthor;
}

function loadTable() {
	loading1()
	paperTime();
	
	$.ajax("/lims/patent/listDynaPageMap",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"name"		:	$('#name_list').val(),
			  		"author"	:	$('#author_list').val(),
//			  		"authorAll"	:	$('#authorAll_list').val(),
			  		"patentType":	$('#selectPatentType').val() == "无" ? "" : $('#selectPatentType').val(),
			  		"status"	:	$('#selectStatus').val() == "无" ? "" : $('#selectStatus').val(),
			  		"publishTime" : $('#publishTime-look').val(),
			  		"startDate_s" : getSelectStartYear(),
			  		"startDate_e" : getSelectEndYear(),			  	
			  		"pageIndex": $('#userInfo-table').attr('data-page-number'),
			         "pageSize": $('#userInfo-table').attr('data-page-size')
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

function getSelectStartYear() {
	var year = $('#selectStartYear').val();
	if (year == '无'){
		return "";
	}
	else {
		return year;
	}
}

function getSelectEndYear() {
	var year = $('#selectEndYear').val();
	if (year == '无'){
		return "";
	}
	else {
		return year;
	}
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


//填写搜寻框中年份查询的option
function selectDateYear() {
	var opt = new Option("无");
	$('#selectStartYear').append(opt); 
	for(var i=1990; i<=2017; i++) {
		var opt = new Option(i);
		$('#selectStartYear').append(opt);
	}
	$('#selectStartYear').val('无');
	
	var opt = new Option("无");
	$('#selectEndYear').append(opt);
	for(var i=2000; i<=2017; i++) {
		var opt = new Option(i);
		$('#selectEndYear').append(opt);
	}
	$('#selectEndYear').val('无');
	
}

//修改框中下拉框的激活
function updateSelectActive() {
	$('#patentType-modal').selectpicker({
        width: 400
    });
	
	$('#status-modal').selectpicker({
       width: 400
	});
}
//页面中下拉框的激活
function surfaceYear() {

	$('#selectStartYear').selectpicker('refresh');
	$('#selectEndYear').selectpicker('refresh');
	$('#selectStartYear').selectpicker({
        width:100
    });
	
	$('#selectEndYear').selectpicker({
       width:100
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


//加载增加中所有下拉框的信息
function addSelectActive() {
	
	$('#patentType-add').selectpicker({
		width:300,
	});
	$('#status-add').selectpicker({
		width:300,
	});
}


//表格  - 操作 - 事件
window.actionEvents = {
		//
	    'click .lookBtn': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/patent/get",
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
	                        $('#patentName-look').val(data.information.name);
	                        $('#patentAuthor-look').val(data.information.author);
	                        $('#patentAuthorAll-look').val(data.information.authorAll);
	                        $('#patantType-look').val(data.information.patentType);
	                        $('#status-look').val(data.information.status);
	                        $('#publishTime-look').val(data.information.publishTime);
	                        $('#patentAbstracts-look').html(data.information.abstracts);
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
'click .mod': function(e, value, row, index) {
      // 从后台查询此用户，为修改输入框提前赋值
	updateSelectActive();
      $.ajax("/lims/patent/get",
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
                  $('#patentName-modal').val(data.information.name);
                  $('#patentAuthor-modal').val(data.information.author);
                  $('#patentAuthorAll-modal').val(data.information.authorAll);
                  $('#patentType-modal').val(data.information.patentType);
                  $('#status-modal').val(data.information.status);
                   $("#publishTime-modal").val(data.information.publishTime), 
                   
                   $('#patentType-modal').selectpicker('refresh');
                   $('#status-modal').selectpicker('refresh');
                  KindEditor.remove('textarea[name="content"]');
              	kedit('textarea[id="abstracts-modal"]',data.information.abstracts);
              	patentId = row.id;
                  
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
      // 获取row.id获取此行id
	  $('#submit_delete').unbind();
      $('#submit_delete').click(function() {
        $.ajax("/lims/patent/delete",
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
	$.ajax("/lims/patent/add",
	        {
	            dataType: "json", // 预期服务器返回的数据类型。
	            type: "post", //  请求方式 POST或GET
	            crossDomain:true,  // 跨域请求
	            contentType: "application/json", //  发送信息至服务器时的内容编码类型
	            // 发送到服务器的数据
	            data:JSON.stringify({
		            "param"	: {
		            	 "name" : $('#patentName-add').val(),
		                 "author"  : $('#patentAuthor-add').val(),
		                 "authorAll"  : $('#patentAuthorAll-add').val(),
		                 "patentType"   : $('#patentType-add').val(),
		                 "status"   : $('#status-add').val(),
		                 "publishTime"  :  $("#publishTime-add").val(),
		                 "processingTime": "2017-10",
		                 "abstracts"	:$('#abstracts-add').val()
		                },
		             "fileIds" : fileIds
	            }),
	            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	            // 请求成功后的回调函数。
	            success: function(data){
	                if (data.state == "0") {
	                	fileIds=[];
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
	 $.ajax("/lims/patent/update",
             {
        	   
               dataType: "json", // 预期服务器返回的数据类型。
                 type: "post", //  请求方式 POST或GET
                 crossDomain:true,  // 跨域请求
                 contentType: "application/json", //  发送信息至服务器时的内容编码类型
                 // 发送到服务器的数据
                 data:JSON.stringify({
              	 "param"	: {
              		   "id"    : patentId,
                         "name"  : $('#patentName-modal').val(),
                         "author"  : $('#patentAuthor-modal').val(),
                         "authorAll" : $('#patentAuthorAll-modal').val(),
                         "patentType"   : $('#patentType-modal').val(),
                         "publishTime" : $("#publishTime-modal").val(),
                         "processingTime" : "2017-10",
                         "status"   : $('#status-modal').val(),
                         "abstracts"  : $('#abstracts-modal').val()
         		  	},
         		  	"fileIds" : fileIds
              	   
                  
                   }),
                 async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
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
//时间日期选择器图标
function addIcon(){
	$(".icon-arrow-left").html("<span class='glyphicon glyphicon-chevron-left'>");
	$(".icon-arrow-right").html("<span class='glyphicon glyphicon-chevron-right'>");
}
