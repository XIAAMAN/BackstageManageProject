/**
 * 
 */
var fileIds = new Array();
var projectId;
$(function() {
	//初始化表格
	//initTable();
  // 加载表格
  loadTable();
  selectDateYear();
  showTeacherUpdateId();
  showTeacherId();	//搜索栏中的下拉框
  //页面教师下拉框
  $('#selectTeacher').selectpicker({
		width: 180
  });  
  $('#selectStartYear').selectpicker({      
		width:100
  });	
	$('#selectEndYear').selectpicker({
      width:100
	});
  
//增加中结束时间发生改变时
	 $('#endYear-add').on('change', function() {
	 var selectText = $(this).find('option:selected').text();
	 if(selectText == "无") {		
		 $("#endMonth-add").val("无");
	 }
	 else {
		 $('#endMonth-add').val('12月');
	 }
		
		$('#endMonth-add').selectpicker('refresh');
	});
	
	//增加验证
	$("#projectAddCheck").Validform({
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
	$("#projectUpdateCheck").Validform({
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
 			projectUpdate(); 
 			return false;
 		}
 	});
	
	//对增加信息进行验证
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
		var temp=getTeacherId();	//得到id
		temp=temp.toString();		//将id转换成字符串
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/project/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        	"name"		:	$('#name_list').val(),
			  		"level"		:	$('#level_list').val(),
			  		"teacherId"	:	temp,
			  		"startDate_s" : getSelectStartYear(),
			  		"startDate_e" : getSelectEndYear(),
		          "pageIndex": currentPage,
		          "pageSize": pageSize
		          }),
		        async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		        // 请求成功后的回调函数。
		        success: function(data){
		          $("#userInfo-table").bootstrapTable('load', data);
		          
		          $('table').find('tr').each(function(){
			  			var str=$(this).children('td').eq(1).text();
			  			var nstr="<label class='titleLabel' title="+str+" data-toggle='tooltip' >"+str+"</label>";
			  			$(this).children('td').eq(1).text("");
			  			$(this).children('td').eq(1).append(nstr);
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
	var temp=getTeacherId();	//得到id
	temp=temp.toString();		//将id转换成字符串
	$.ajax("/lims/project/listDynaPageMap",
			{
				dataType: "json", // 预期服务器返回的数据类型。
	   			type: "post", //  请求方式 POST或GET
			  	crossDomain:true,  // 跨域请求
			  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
			  	// 发送到服务器的数据
			  	data:JSON.stringify({
			  		"name"		:	$('#name_list').val(),
			  		"level"		:	$('#level_list').val(),
			  		"teacherId"	:	temp,
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
			  			var str=$(this).children('td').eq(1).text();
			  			var nstr="<label class='titleLabel' title="+str+" data-toggle='tooltip' >"+str+"</label>";
			  			$(this).children('td').eq(1).text("");
			  			$(this).children('td').eq(1).append(nstr);
			  		})
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	removeLoading('test');
}

//得到下拉框中开始年份
function getSelectStartYear() {
	var year = $('#selectStartYear').val();
	if (year == '无'){
		return "";
	}
	else {
		return year;
	}
}

//得到下拉框中结束年份
function getSelectEndYear() {
	var year = $('#selectEndYear').val();
	if (year == '无'){
		return "";
	}
	else {
		return year;
	}
}

function getTeacherId(){	//得到搜索栏下拉框中选中的主持人id
	var num=0;
	var index=0;
	var teacherString=$('#selectTeacher option:selected').val();
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

function showTeacherId(){		//搜索栏中的下拉框
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
					$('#selectTeacher').append(opt);
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#selectTeacher').append(opt);
					}
					$('#selectTeacher').val('');
			  		
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

//起止日期
function dateFormatter(value, row, index) {
	var endDate = row.endDate;
	if (endDate==null) {
		endDate='';
	}else{
		endDate=endDate.substring(0,7);
	}
	var startDate=row.startDate.substring(0,7);
	
	var sed = startDate+" ~ "+endDate;
    return sed
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

//增加中教师的id
function addTeacherId(){
	var num=0;
	var index=0;
	var teacherString=$('#teacherId-add option:selected').val();
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
						$('#teacherId-add.selectpicker').append(opt);
					}
					
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}

//增加中开始日期
function startDateAdd() {
	for(var i=1990;i<=2017;i++){
		var year = i.toString() +'年';
		var opt = new Option(year);
		$('#startYear-add.selectpicker').append(opt);
	}
	
	for (var j=1;j<=12;j++) {
		if (j < 10){
			var month = '0' + j.toString() + '月';
		}
		else {
			var month = j.toString() + '月';
		}
		var opt = new Option(month);
		$('#startMonth-add.selectpicker').append(opt);
	}
	$('#startYear-add.selectpicker').val('2017年');
	$('#startMonth-add.selectpicker').val('01月');
}

//增加中结束日期
function endDateAdd() {
	var opt = new Option("无");
	$('#endYear-add.selectpicker').append(opt);
	for(var i=2017;i<=2030;i++){
		var year = i.toString() +'年';
		var opt = new Option(year);
		$('#endYear-add.selectpicker').append(opt);
	}
	var opt = new Option("无");
	$('#endMonth-add.selectpicker').append(opt);
	for (var j=1;j<=12;j++) {
		if (j < 10){
			var month = '0' + j.toString() + '月';
		}
		else {
			var month = j.toString() + '月';
		}
		var opt = new Option(month);
		$('#endMonth-add.selectpicker').append(opt);
	}
	$('#endYear-add.selectpicker').val('2017年');
	$('#endMonth-add.selectpicker').val('12月');
}

//加载增加中所有下拉框的信息
function showSelectedAdd() {
	
	startDateAdd();
	endDateAdd();
	showTeacheraddId();
	
	$('#startYear-add').selectpicker({
		width:142,
	});
	$('#startMonth-add').selectpicker({
		width:142,
	});
	$('#endYear-add').selectpicker({
		width:142,
	});
	$('#endMonth-add').selectpicker({
		width:142,
	});
	$('#level-add').selectpicker({
		width:300,
	});
	$('#teacherId-add').selectpicker({
		width:300,
	});
	clearWrongMsg();
}

//得到正确格式的开始日期
function getStartDate() {
	var startYear = $('#startYear-add').val();
	var startMonth = $('#startMonth-add').val();
	startYear = startYear.slice(0,4);
	startMonth = startMonth.slice(0,2);
	return startYear + "-" + startMonth +"-01";
}

//得到正确格式的终止日期
function getEndDate() {
	var endYear = $('#endYear-add').val();
	var endMonth = $('#endMonth-add').val();
	if(endYear == "无") {
		return "";
	}
	else {
		endYear = endYear.slice(0,4);
		endMonth = endMonth.slice(0,2);
		return endYear + "-" + endMonth +"-01";
	}
}

//清除错误提示信息
function clearWrongMsg() {
	KindEditor.remove('textarea[name="content"]'); 
	kedit('textarea[name="content"]',"");
	$("#projectAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#projectName-add").next().text("请输入项目名称");
	$("#member-add").next().text("请输入项目成员");
	$("#code-add").next().text("请输入您的项目编号");
	$("#fund-add").next().text("请输入项目经费");
	$("#source-add").next().text("请输入项目来源");
	
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
//增加方法
function addFunction(){
	var temp = addTeacherId();
	temp=temp.toString();
	$.ajax("/lims/project/add",
	        {
	            dataType: "json", // 预期服务器返回的数据类型。
	            type: "post", //  请求方式 POST或GET
	            crossDomain:true,  // 跨域请求
	            contentType: "application/json", //  发送信息至服务器时的内容编码类型
	            // 发送到服务器的数据
	            data:JSON.stringify({
	            	
	            	"param"	: {
	            		"code" : $('#code-add').val(),
		                 "name"  : $('#projectName-add').val(),
		                 "teacher"	:	{"id":temp},
		                 "member"  : $('#member-add').val(),
		                 "fund"   : $('#fund-add').val(),
		                 "source"   : $('#source-add').val(),
		                 "level"  : $('#level-add').val(),
		                 "startDate" : getStartDate(),
		                 "endDate"   : getEndDate(),
		                 "description"   : editor.html()
				  	},
				  	"fileIds" : fileIds
	            	
	            	
	                }),
	            async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
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
	    })
}

function projectUpdate() {
	var temp=updateTeacherId();
   	temp=temp.toString();   
       //单击提交后修改
       $.ajax("/lims/project/update",
         {
    	   
           dataType: "json", // 预期服务器返回的数据类型。
             type: "post", //  请求方式 POST或GET
             crossDomain:true,  // 跨域请求
             contentType: "application/json", //  发送信息至服务器时的内容编码类型
             // 发送到服务器的数据
             data:JSON.stringify({
          	   
          	   "param"	: {
          		   "id"    : projectId,
                     "name"  : $('#projectName-modal').val(),
                     "teacher"	:  {"id":temp},
                     "member"  : $('#member-modal').val(),
                     "code" : $('#code-modal').val(),
                     "fund"   : $('#fund-modal').val(),
                     "source"   : $('#source-modal').val(),
                     "level"  : $('#level-modal').val(),
                     "startDate" : $('#startDate-modal').val(),
                     "endDate"   : $('#endDate-modal').val(),
                     "description"   : $('#description-modal').val()
 			  	},
 			  	"fileIds" : fileIds
          	   
               
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

//得到更新下拉框中选定的主持人id
function updateTeacherId(){	
	var num=0;
	var index=0;
	var teacherString=$('#teacherId-modal option:selected').val();
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
			  	async: false, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
			  	// 请求成功后的回调函数。
			  	success: function(data){
			  	
			  		for (var i=0; i<data.length; i++){
						teacher = data[i].name +"(" + data[i].college + ")";
						var opt = new Option(teacher);
						$('#teacherId-modal.selectpicker').append(opt);
					}
							
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
}


//修改下拉框调用
function showSelected() {		
		
//		$('#teacherId-modal').data('selectpicker', null);
//		$('.bootstrap-select').css("display","none");
		$('#teacherId-modal').selectpicker({
			width:400,	
		});	
		$('#level-modal').selectpicker({
			width:400,	
		});	
}

//表格  - 操作 - 事件
window.actionEvents = {
			
		//
	    'click .lookBtn': function(e, value, row, index) {
	    	
	            // 从后台查询此用户，为修改输入框提前赋值
	            $.ajax("/lims/project/get",
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
	                        $('#projectName-look').val(data.information.name);
	                        $('#teacherId-look').val(data.information.teacher.id);
	                        $('#member-look').val(data.information.member);
	                        $('#code-look').val(data.information.code);
	                        $('#fund-look').val(data.information.fund);
	                        $('#source-look').val(data.information.source);
	                        $('#level-look').val(data.information.level);
	                        $('#startDate-look').val(data.information.startDate);
	                        $('#endDate-look').val(data.information.endDate);
	                        

//	                        var html = data.information.description;
	                        $('#description-look').html(data.information.description);
	                      
	                        
	                     
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
	 
	  showSelected();
        // 从后台查询此用户，为修改输入框提前赋值
        $.ajax("/lims/project/get",
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
                    $('#projectName-modal').val(data.information.name);
//                    showSelected(data.information.teacher.name +"(" + data.information.teacher.college + ")");
                    $('#teacherId-modal').val(data.information.teacher.name +"(" + data.information.teacher.college + ")");
                    $('#teacherId-modal').selectpicker('refresh');
                    $('#member-modal').val(data.information.member);
                    $('#code-modal').val(data.information.code);
                    $('#fund-modal').val(data.information.fund);
                    $('#source-modal').val(data.information.source);
                    $('#level-modal').val(data.information.level);
                    $('#level-modal').selectpicker('refresh');
                    $('#startDate-modal').val(data.information.startDate);
                    $('#endDate-modal').val(data.information.endDate);
                    projectId = row.id;
                    
                    KindEditor.remove("textarea[name='content']");
					kedit('textarea[id="description-modal"]',data.information.description);
               
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
          $.ajax("/lims/project/delete",
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
//function:responseHandler请求数据成功后，渲染表格前的方法
//请求成功方法
function responseHandler(result){
 //如果没有错误则返回数据，渲染表格
 return {
     total : result.total, //总页数,前面的key必须为"total"
     data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
 };
};
