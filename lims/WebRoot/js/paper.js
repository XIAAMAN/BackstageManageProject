
var fileIds=new Array();
var paperId;
$(function(){
	loadTable();
	$("#paperType-add").selectpicker({
		width: "300px"
	});
	$('#selectPublishTime').selectpicker({
        width:120
    });

	$('#selectPaperType').selectpicker({
		width:120
  });
	$('#selectindexType').selectpicker({
		width:180
	});
	
	// 增加验证栏
		$("#paperAddCheck").Validform({
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
	 			paperAdd();
	 			
	 			return false;
	 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
	 			//这里明确return false的话表单将不会提交; 
	 		}, 
	 	});
		
	// 修改验证栏
			$("#paperUpdateCheck").Validform({
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
		 			paperUpdate();
		 			
		 			return false;
		 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
		 			//这里明确return false的话表单将不会提交; 
		 		}, 
		 	});
	
	// 当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/paper/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        		"name"		:	$('#papername_list').val(),
		        		"author"	:	$('#paperauthor_list').val(),
		        		
		        		"paperType":	$('#selectPaperType').val() == "无" ? "" : $('#selectPaperType').val(),
		        		"indexType":	getIndexType('#selectindexType'),
		        		"pageIndex" : currentPage,
				        "pageSize"  : pageSize    
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
	})

	
});

function loading1() {
	$('body').loading({
		loadingWidth:240,
		title:'请稍等!',
		name:'test',
		discription:'努力加载中...',
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

/*激活下拉框*/
function selectActive() {

	$("#paperType-modal").selectpicker({
		width: "400px"
	});
}

////出版时间查询
//function showPublishTime() {
//	var date=new Date();
//	var year=parseInt(date.getFullYear());
//	var opt = new Option("无");
//	$("#selectPublishTime").append(opt);
//	for(i=year-20; i<=year;i++) {
//		var opt = new Option(i);
//		$("#selectPublishTime").append(opt);
//	}
//	$("#selectPublishTime").val("无");
//}

/*加载表格*/
function loadTable(){

	paperTime();
	
	$.ajax("/lims/paper/listDynaPageMap",
		{
			dataType: "json", // 预期服务器返回的数据类型。
   			type: "post", //  请求方式 POST或GET
		  	crossDomain:true,  // 跨域请求
		  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
		  	// 发送到服务器的数据
		  	data:JSON.stringify({
			  		"name"		:	$('#papername_list').val(),
			  		"author"	:	$('#paperauthor_list').val(),
			  	
	        		"paperType":	$('#selectPaperType').val() == "无" ? "" : $('#selectPaperType').val(),
	        		"indexType":	getIndexType('#selectindexType'),
			  		"pageIndex" :   $('#userInfo-table').attr('data-page-number'),
			        "pageSize"  :   $('#userInfo-table').attr('data-page-size'),
		  	
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
	
}


//对论文索引类型多选框查询进行处理
function getIndexType(paperIndexId) {
	
	var length = $(paperIndexId).val().length;

	var temp="";
	var i=0;
	for (i=0;i<length;i++) {
		temp += $(paperIndexId).val()[i];
		if(i != (length-1)) {
			temp +=",";
		}
	}
	if ("无"==temp) {	//选中的为无则传一个空字符
		temp = "";
	}else if (temp.indexOf("无") > 0) {	//无字在开始位置
		temp = temp.replace(",无","");
	} else if (temp.indexOf("无") == 0) {	//无字在中间
		temp = temp.replace("无,","");
	}
	return temp;
}

//作者(全)
function authorFormatter(value, row, index){
	var author = row.author;
	var authorAll = row.authorAll;
	var tolAuthor;
	if(authorAll == 0 || authorAll == null){
		authorAll = " ";
		tolAuthor = author + " " + authorAll;
	}else{
		tolAuthor = author + "," + authorAll;
	}
	return tolAuthor;
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
	$("#selectYear").val(time);
	
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

/*操作按钮显示*/
function actionFormatter(value, row, index) {
    return '<button id="btn_look" type="button" class="lookBtn btn btn-primary" data-toggle="modal"  data-target="#lookPaperModal">'+
	       ' <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看' +
	       '</button>' +
    	   '<button id="btn_edit" type="button" class="paperMod btn btn-warning" data-toggle="modal"  data-target="#updatePaperModal">'+
           ' <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 修改' +
           '</button>' +
           ' <button id="btn_delete" type="button" class="paperDelete btn btn-danger" data-toggle="modal"  data-target="#deletePaperModal">' +
           ' <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span> 删除 ' +
           ' </button>'
}


/*增加数据*/
function paperAdd(){
	
	//获取多选框的数据
	var obj1=document.getElementsByName('checkbox1'); //选择所有name="'checkbox1'"的对象，返回数组 
	var checked1=''; 
	for(var i=0; i<obj1.length; i++){ 
		if(obj1[i].checked)   //取到对象数组后，我们来循环检测它是不是被选中 
			checked1+=obj1[i].value+','; //如果选中，将value添加到变量s中 
	} 
	checked1=checked1.substring(0,checked1.length-1); //删除最后一个多余逗号
	
  $.ajax("/lims/paper/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	data:JSON.stringify({
		  	"param"	: {
		  		"name"		       :	        $('#paperName-add').val(),
		  		"author"	       :            $('#paperAuthor-add').val(),
		  		"authorComm"	   :	        $('#paperAuthorComm-add').val(),
		  		"authorAll"		   :	        $('#paperAuthorAll-add').val(),
		  		"publishTime"      :            $('#paperPublishTime-add').val(),
		  		"paperType"		   :	        $('#paperType-add option:selected').val(),
		  		"indexType"		   :	        checked1,
		  		"journal"		   :            $('#paperJournal-add').val(),
		  		"vol"			   :	        $('#paperVol-add').val(),
		  		"abstracts"		   :	        $('#paperAbstracts-add').val(),
		  	},
			"fileIds" : fileIds
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		alert('添加论文成功');
	  		if (data.state == 0) {
	  			fileIds = [];
	  			$('#addPaperModal').modal('hide');
	  			$(".ajax-file-upload-container").html("");
	  			loadTable();
	  			//清空上传
	  			
	  			var checkbox3=document.getElementsByName('checkbox1');//多选框数组
	  			
	  			for(var j=0;j<checkbox3.length;j++){
	  						checkbox3[j].checked=false;
	  			}
	  		} else {
	  			alert("注册失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
}

//修改数据

function paperUpdate() {
	var obj2=document.getElementsByName('checkbox2'); //选择所有name="'checkbox1'"的对象，返回数组 
	var checked3=''; 
	for(var i=0; i<obj2.length; i++){ 
		if(obj2[i].checked)   //取到对象数组后，我们来循环检测它是不是被选中 
			checked3+=obj2[i].value+','; //如果选中，将value添加到变量s中 
	} 
	checked3=checked3.substring(0,checked3.length-1); //删除最后一个多余逗号
	
    $.ajax("/lims/paper/update",
      {
        dataType: "json", // 预期服务器返回的数据类型。
          type: "post", //  请求方式 POST或GET
          crossDomain:true,  // 跨域请求
          contentType: "application/json", //  发送信息至服务器时的内容编码类型
          // 发送到服务器的数据
          data:JSON.stringify({
            "param"  :{
            "id"            :   paperId,
             "name"		    :	$('#paperName-modal').val(),
	  		"author"	    :   $('#paperAuthor-modal').val(),
	  		"authorComm"	:	$('#paperAuthorComm-modal').val(),
	  		"authorAll"		:	$('#paperAuthorAll-modal').val(),
	  		"publishTime"   :   $('#paperPublishTime-modal').val(),
	  		"paperType"		:	$('#paperType-modal').val(),
	  		"indexType"		:	checked3,
	  		"journal"		:	$('#paperJournal-modal').val(),
	  		"vol"			:	$('#paperVol-modal').val(),
	  		"abstracts"		:	$('#paperAbstracts-modal').val(),
            },
            "fileIds" : fileIds
            }),
          async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
          // 请求成功后的回调函数。
          success: function(data){
            if (data.state == "0") {
              alert("修改成功! ");
              $(".ajax-file-upload-statusbar").empty();                         
              $('#updatePaperModal').modal('hide');
              loadTable();
              clearChecked();
            } else {
              alert("状态 " + data.state + " " + "修改失败!  错误:" + data.message);
            }
          },
          error: function(){
          alert("请求错误，请检查网络连接");
         }
    });
}

/*查看  修改  删除*/
window.actionEvents = {
		
		//查询操作
	    'click .lookBtn': function(e, value, row, index) {
	    	    
	            $.ajax("/lims/paper/get",
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
	                    	var author=data.information.author+"，"+data.information.authorAll;
	                      if (data.state == "0") {
	                        // 为模态框中的输入框提前赋值
	                        $('#paperName-look').val(data.information.name);
	                        $('#paperAuthor-look').val(author);
	                        $('#paperAuthorComm-look').val(data.information.authorComm);
	                        $('#paperPublishTime-look').val(data.information.publishTime);
	                        $('#paperType-look').val(data.information.paperType);
	                        
	                        $('#paperIndexType-look').val(data.information.indexType);
	                        $('#paperJournal-look').val(data.information.journal);
	                        $('#paperVol-look').val(data.information.vol);
	                        $('#paperAbstracts-look').html(data.information.abstracts);
	                        
	                      //附件
	                        var inp,inv;
	                        inp=document.getElementById("paperFiles-look");
	                        inv="";             
	                         for(var i=0; i<data.information.enclosures.length; i++){
	                        	 var id=data.information.enclosures[i].id;
	                        	 var newFileName=data.information.enclosures[i].fileName;
	                        	 var a="<a href='http://localhost:8080/lims/enclosure/download/paper/"+newFileName+"'"+" class='files-look"+"'"+">"+
	                        	 fileOldName(data.information.enclosures[i].oldName,data.information.enclosures[i].size)+"</a>";
	                        	 
	                            inv+=a+"<br/>";		                        	
			                    }
	                         inp.innerHTML=inv;
	                      } else {
	                        alert("论文不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
	          
	        },	
		
	

  //修改操作
  'click .paperMod': function(e, value, row, index) {
	  selectActive();
	  fileUpload("#fileuploader-model");
		$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
          // 从后台查询此用户，为修改输入框提前赋值
	       $.ajax("/lims/paper/get",
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
	                    	
	                        $('#paperName-modal').val(data.information.name);
	                        $('#paperAuthor-modal').val(data.information.author);
	                        $('#paperAuthorComm-modal').val(data.information.authorComm);
	                        $('#paperAuthorAll-modal').val(data.information.authorAll);
	                        $('#paperPublishTime-modal').val(data.information.publishTime);
	                        
	                        $('#paperType-modal').val(data.information.paperType);
	                        $("#paperType-modal").selectpicker("refresh");
	                        
	                        $('#paperJournal-modal').val(data.information.journal);
	                        $('#paperVol-modal').val(data.information.vol);
	                        
	                        //多选框赋值
	                        var indexType=data.information.indexType;
	                        var type=indexType.split(",");//数据数组
	                        var checkbox2=document.getElementsByName('checkbox2');//多选框数组
	                   	 	for(var i=0;i<type.length;i++){
	                   	 		for(var j=0;j<checkbox2.length;j++){
	                   	 			if(type[i]==checkbox2[j].value){
	                   	 				checkbox2[j].checked="checked";//值相等勾选
	                   	 			}
	                   	 		}
	                   	 	}	 	
	                   	    KindEditor.remove("textarea[name='content']");
	                        kedit('textarea[id="paperAbstracts-modal"]',data.information.abstracts);
	                        
	                      //附件
	                        var inp,inv;
	                        inp=document.getElementById("paperFiles-modal");
	                        inv="";             
	                         for(var i=0; i<data.information.enclosures.length; i++){
	                        	 var id=data.information.enclosures[i].id;
	                        	 var newFileName=data.information.enclosures[i].fileName;
	                        	 var a="<a href='http://localhost:8080/lims/enclosure/download/paper/"+newFileName+"'"+" id='a"+i+"'"+">"+
	                        	       fileOldName(data.information.enclosures[i].oldName,data.information.enclosures[i].size)+"</a>";
	                        	 var button1="<button  type='button'"+" class='btn-sm btn-danger' "+"id='button"+i+"'"+"onClick=deletes("+id+","+i+")>"+"删除"+"</button>";
	                            inv+=a+"   "+button1+ "<br/>";		                        	
			                    }
	                         inp.innerHTML=inv;
	                         paperId = row.id;       
	            
	                      } else {
	                        alert("论文不存在");
	                      }
	                    },
	                    error: function(){
	                    alert("请求错误，请检查网络连接");
	                   }
	              });
        
      },
      'click .paperDelete' : function(e, value, row, index) {
          //删除操作
          // 获取row.id获取此行id
    	  $('#paperSubmit_delete').unbind();
          $('#paperSubmit_delete').click(function() {
            $.ajax("/lims/paper/delete",
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
                      alert("已删除论文: ");
                      $('#deletePaperModal').modal('hide');
                      loadTable();
                    } else {
                      alert("此论文不存在");
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

//清空修改多选框
function clearChecked(){
	var checkbox3=document.getElementsByName('checkbox2');//多选框数组
	for(var j=0;j<checkbox3.length;j++){
				checkbox3[j].checked=false;
	}
}

//增加模块编辑框
function clearWrongMsg(){
	KindEditor.remove("textarea[name='content']");	
    kedit("textarea[id='paperAbstracts-add']","");
    $("#paperAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	$("#paperName-add").next().text("请输入标题名称");
	$("#paperAuthor-add").next().text("请输入作者名");
	$("#paperAuthorAll-add").next().text("请输入其他作者");
	$("#paperAuthorComm-add").next().text("请输入通讯作者");
	$("#paperJournal-add").next().text("请输入期刊名称");
	$("#paperVol-add").next().text("请输入卷数");
	fileUpload("#fileuploader");
	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件

}

function fileUpload(fileuploader) {
	 $(fileuploader).uploadFile({
         url:"/lims/enclosure/upload", //后台处理方法
         fileName:"paper",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
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
         statusBarWidth:550,
         dragdropWidth:550,
         //删除按钮执行的方法
         deleteCallback: function (data, pd) {
             var fileId=data.data[0].fileId;
             alert(fileId)
             $.post("${ctx}/file/paper/delete", {fileId:fileId},
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
	  		"table" : "paper"
	  	}),
	  	async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
	  	// 请求成功后的回调函数。
	  	success: function(data){
	  		if (data.state == 0) {
	  			alert("删除成功!");
	  			$("#a"+i).remove();
	  			$("#button"+i).remove();
	  		} else {
	  			alert("删除失败!  错误:" + data.message);
	  		}
	   	},
	   	error: function(){
			alert("您的请求有误，请正常操作或联系管理员");
	   }
	});
	
}

//时间日期选择器图标
function addIcon(){
	$(".icon-arrow-left").html("<span class='glyphicon glyphicon-chevron-left'>");
	$(".icon-arrow-right").html("<span class='glyphicon glyphicon-chevron-right'>");
}

//限制附件文件名字长度
function fileOldName(name,size){
	var fileName;
	var str=name.split(".");
	var n=str.length-1;
	if(name.length>15){
		return fileName=name.substring(0,15)+"...."+str[n]+"("+size+" kB"+")";
	}
	else{
		return name=name+"("+size+" kB"+")";
	}
}
