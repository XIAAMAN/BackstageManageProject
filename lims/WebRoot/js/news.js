//通知相关的js
var fileIds = new Array();
var addcontent;
var newsModId;
var newsModState;
$(function() {

		// 增加验证栏
			$("#newsCheck").Validform({
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
		 			
		 			newsAdd();
		 			//newsModSubmit();
		 			return false;
		 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
		 			//这里明确return false的话表单将不会提交; 
		 		}

		 	});
			
			
			$("#newsModCheck").Validform({
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

		 			newsModSubmit();
		 			
		 			
		 			
		 			return false;
		 			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
		 			//这里明确return false的话表单将不会提交; 
		 		}

		 	});
	
	
  // 加载表格
	
  loadTable();
	
	// 当页面currentPage和pageSize发生改变
//当页面currentPage和pageSize发生改变
	$('#userInfo-table').on('page-change.bs.table', function(e,number,size) {
		loading1();
		//刷新表格
		var pageSize = size.toString();
		var currentPage = number.toString();
		$.ajax("/lims/news/listDynaPageMap",
		    {
		      dataType: "json", // 预期服务器返回的数据类型。
		        type: "post", //  请求方式 POST或GET
		        crossDomain:true,  // 跨域请求
		        contentType: "application/json", //  发送信息至服务器时的内容编码类型
		        // 发送到服务器的数据
		        data:JSON.stringify({
		        
		        	"title"		:	$('#title_list').val(),
			  		"author"	:	$('#author_list').val(),
			  		"keywork"   :   $('#keyword_list').val(),
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


function newsModSubmit(){
    	
        //单击提交后修改
        $.ajax("/lims/news/update",
          {
            dataType: "json", // 预期服务器返回的数据类型。
              type: "post", //  请求方式 POST或GET
              crossDomain:true,  // 跨域请求
              contentType: "application/json", //  发送信息至服务器时的内容编码类型
              // 发送到服务器的数据
              data:JSON.stringify({
            	  
            	  "param"	: {
                "id"    : newsModId,
                "title"		:	$('#title-modal').val(),
                "photo"		:	$('#photo-modal').val(),
		  		"author"	:	$('#author-modal').val(),
		  		"keyword"	:	$('#keyword-modal').val(),
		  		"content"	:	$('#content-modal').val(),
		  		
		  		
            		},
        		  	"fileIds" : fileIds

                }),
              async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
              // 请求成功后的回调函数。
              success: function(data){
            	  
                if (data.state == "0") {
                  alert("状态 " + data.state + " " + "修改成功! " + data.information.id + " " + data.information.author);
                  $('#updateNoticeModal').modal('hide');
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





function loadTable() {
	
	loading1();
	$.ajax("/lims/news/listDynaPageMap",
			{
		dataType: "json", // 预期服务器返回的数据类型。
			type: "post", //  请求方式 POST或GET
  	crossDomain:true,  // 跨域请求
  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
  	// 发送到服务器的数据
	data:JSON.stringify({
  		
	  		"title"		:	$('#title_list').val(),
	  		"author"	:	$('#author_list').val(),
	  		"keyword"   :   $('#keyword_list').val(),
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
			  		
			  		 $('input[type="checkbox"]').bootstrapSwitch({

				      	            onColor:"success",  
				      	            offColor:"info",  
				      	            size:"small",  
				      	           
				      	        })
			  		
			   	},
			   	error: function(){
					alert("您的请求有误，请正常操作或联系管理员");
			   }
		});
	removeLoading('test');
	
}




function btnOnOff(value, row, index) {	
	var state='';
	
	if (row.seq == '1')
		state = 'checked';
    return '<button  class="switchButton switch"><div  data-animated="false"><input type="checkbox" '+ state +' /></div></button> ';
    		
}


window.onOffactionEvents={
		
		'click .switch ': function(e, value, row, index) {
			
			$.ajax("/lims/news/updateSeq",
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

function newsAdd(){
	
	var swi;
	if($('#addswitch').bootstrapSwitch('state')==true){swi=1;}
	else {swi=0;}
	
$.ajax("/lims/news/add",
	{
		dataType: "json", // 预期服务器返回的数据类型。
  			type: "post", //  请求方式 POST或GET
	  	crossDomain:true,  // 跨域请求
	  	contentType: "application/json", //  发送信息至服务器时的内容编码类型
	  	// 发送到服务器的数据
	  	
	  	data:JSON.stringify({
		  	"param"	: {
		  		"title"		:	$('#title_save').val(),
		  		"author"	:	$('#author_save').val(),
		  		"photo"		:	$('#photo_save').val(),
		  		"content"	:	$('#content_save').val(),
		  		"seq"		:	swi,  		
		  		"keyword"	:	$('#keyword_save').val()
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


function responseHandler(result){
	 //如果没有错误则返回数据，渲染表格
	 return {
	     total : result.total, //总页数,前面的key必须为"total"
	     data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
	 };
};

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
	    	$.ajax("/lims/news/get",
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
	                    	    $('#id_look').val(data.information.id);
	                    	    $('#title_look').val(data.information.title);
	                    	    
	                    	    $('#title_look').attr("title",data.information.title);
	                    	   
		                        $('#author_look').val(data.information.author);
		                        
		                       
		                        
		                        $('#keyword_look').val(data.information.keyword);
		                        
		                        $('#content_look').html(data.information.content);	
		                       // KindEditor.remove('textarea[name="content"]');
		                      //  KindEditor.remove('textarea[name="addcontent"]');
		                      //  KindEditor.remove('textarea[name="modalcontent"]');
		                        
		                      //  kedit('textarea[name="content"]',data.information.content);
		                        
		                        
		                        
		                        $('#publishTime_look').val(data.information.publishTime);
		                        //编辑框赋值
		                        

		                        
		                        //图片
		                        var ph,phpath;
		                       // phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
		                        ph=document.getElementById("photo_look");
		                       
		                        if(!data.information.photo)
		                        	{ph.innerHTML="";}
		                        else{ph.innerHTML="<img height='200px' width='150px' src='/lims/enclosure/download/news/"+data.information.photo+"'/>";
		                        }
		                        
		                     //   'D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news'+data.information.photo.fileName+'.png''
		                       
		                      
		                        
		                        
		                        //附件
		                        var inp,inv,enpath;
		                        inp=document.getElementById("enclosures_look");
		                        inv="";
		                       
		                       // enpath="D:\Tomcat\tomcat\webapps\lims\WEB-INF\\uploads\news"+data.information.enclosures[i].fileName+".*";
		                         for(var i=0; i<data.information.enclosures.length; i++){
		                            inv+="<a id="+i+" href='/lims/enclosure/download/news/"+data.information.enclosures[i].fileName+"'>"+data.information.enclosures[i].oldName+"</a>"+ "<br/>";
		                           
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
	    	$.ajax("/lims/news/get",
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
	                    	   $('#id-modal').val(data.information.id);
	                    	   newsModId=data.information.id;
	                    	   
	                    	    $('#title-modal').val(data.information.title);
	                    	
	                    	    $('#photo-modal').val(data.information.photo);
	                    	    
		                        $('#author-modal').val(data.information.author);
		                        $('#keyword-modal').val(data.information.keyword);
		                        
		                        //$('#content-modal').val(data.information.content);
		                        
		                        KindEditor.remove('textarea[name="content"]');
		                     
		                        kedit('textarea[id="content-modal"]',data.information.content);
		                        
  
		                        $('#publishTime-modal').val(data.information.publishTime);
		                        
		                        //图片
		                        var ph,phpath;
		                     //   phpath="D:\\Tomcat\\tomcat\\webapps\\lims\WEB-INF\\uploads\\news"+data.information.photo;
		                        ph=document.getElementById("photo-modal");
		                        if(data.information.photo) {
		                        	ph.innerHTML="<img height='200px' width='150px' " +
                    				"src='/lims/enclosure/download/news/"+data.information.photo+"'/>" +
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
		                        invb="";
		                       
		                       // enpath="D:\Tomcat\tomcat\webapps\lims\WEB-INF\\uploads\news"+data.information.enclosures[i].fileName+".*";
		                         for(var i=0; i<data.information.enclosures.length; i++){
		                            inv+="<a id='a"+i+"' href='/lims/enclosure/download/news/"+data.information.enclosures[i].fileName+"'>"+data.information.enclosures[i].oldName+"</a>";	
		                            invb="<button type='button' id='b"+i+"' class='btn btn-danger' " + "onclick='deletes("+data.information.enclosures[i].id+","+i+")'>"+"删除"+"</button><br/>";
		                            inv+="  "+invb+"<br>";
				                    }
		                         inp.innerHTML=inv;
		                        
           
		                        
		                     //放置于ValidForm回调函数   
		                  //  $('#noticeSubmit_update').unbind();

	                      } else {
	                        alert("新闻不存在");
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
	              $.ajax("/lims/news/delete",
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
	                        alert("已删除新闻");
	                        $('#deleteNoticeModal').modal('hide');
	                        loadTable();
	                      } else {
	                        alert("新闻不存在");
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
        	"module":"news", "name" : photoname
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
				"table" : "news"
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



function photoChangeShow(){
	 $("#photochange").uploadFile({
	      	url:"/lims/enclosure/singleSave",     //文件上传url
		    fileName:"news",               //提交到服务器的文件名
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
		            	"module":"news", "name" : data.information
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

//清除错误提示信息
function clearWrongMsg() {
	
	KindEditor.remove('textarea[name="content"]');
	kedit('textarea[id="content_save"]',"");
	$("#newsAddCheck").Validform().resetForm();	//重置表单
	//修改提示内容
	photoUpload("#photouploader");
	fileUpload("#fileuploader");
	$('.ajax-file-upload-container').html("");	//删除已经添加成功的文件
}

function fileUpload(fileuploader) {
    $(fileuploader).uploadFile({
        url:"/lims/enclosure/upload", //后台处理方法
        fileName:"news",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
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

function photoUpload(photoUpload) {
	 $(photoUpload).uploadFile({
	      	url:"/lims/enclosure/singleSave",     //文件上传url
		    fileName:"news",               //提交到服务器的文件名
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
		            	"module":"news", "name" : data.information
		    	  	}),
		            success: function(data) 
		            {
		                if(data.state===0){
		                    pd.statusbar.hide();        //删除成功后隐藏进度条等
		                    $('#photo_save').val("");  // 注册时用
		                    $('#photo_modal').val("");  // 修改时用
		                    alert("请上传新图片");
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
		        }
		    }
		});
	
}

$('#addNoticeModal').off('shown.bs.modal').on('shown.bs.modal', function (e) {
	 $(document).off('focusin.modal');//解决编辑器弹出层文本框不能输入的问题
	 });


