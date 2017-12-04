<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css" type="text/css"> 
	 <link rel="stylesheet" href="${ctx}/css/bootstrap-table.min.css" type="text/css">
	 <link rel='stylesheet' href='${ctx}/css/test.css' type="text/css">
	 <link rel="stylesheet" href="${ctx}/css/bootstrap-select.css" type="text/css">
	 <link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" >
	 <link rel="stylesheet" href="${ctx}/css/demo.css" type="text/css">
	 <link rel="stylesheet" href="${ctx}/css/uploadfile.css">
	 <link rel="stylesheet" href="${ctx}/css/loading.css">
	 <link rel="stylesheet" href="${ctx}/kindEditor/default/default.css" type="text/css">
	 <link rel="stylesheet" href="${ctx}/css/animate.css">
	
     <link href="${ctx}/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
     
     <jsp:include page="template.jsp" flush="true"/><!--动态包含-->
</head>

<body>
<!-- 页面主体 -->
<div class="body row contents">
	
		<!-- 查询面板 -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">学生信息</h3>
			</div>
			<div class="panel-body">
				<!-- 查询条件, 组件为input和button -->
				<div class="qeuryCondition form-inline">
					<div class="input-group  queryStyle">
				        <span class="input-group-addon">学生姓名</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="mdStudent_list">
    				</div>
    				
					<div class="input-group queryStyle">
				        <span class="input-group-addon">专业名称</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="major_list">
					</div>
										    				
    				<div class="input-group queryStyle">
				        <span class="input-group-addon">研究方向</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="researchInterest_list">
    				</div>
    						
    				<!-- 查询按钮 -->
    				<button id="queryBtn"  onclick="loadTable()" type="button" class="btn btn-primary queryBtn" >
		          		<span class="glyphicon glyphicon-search"></span> 查询
		        	</button>
		        	<button id="addBtn" onclick="clearWrongMsg()"  type="button" class="btn btn-default addBtn" data-toggle="modal" data-target="#addUserModal" >
				        <span class="glyphicon glyphicon-plus"></span> 增加
				    </button>
			</div><br/>

				<!-- 显示表格信息 -->
				<div class="info">
					<table id="userInfo-table" data-toggle="table"
			          data-click-to-select="true"
					        data-pagination="true"
				         	data-striped="true"					       
				           	data-side-pagination="server"
				           	data-page-number="1"
				           	data-page-size="10" 
				           	data-page-list="[10,15,20, 50, 100]"
				           	data-response-handler="responseHandler">
				        <thead>
				           	<tr>
								
								<th data-align="center" data-field="photo" data-formatter="photoFormatter">个人照片</th>
								<th data-align="center" data-field="name">学生姓名</th>
								 <th data-align="center" data-field="major">专业名称</th>
								 <th data-align="center" data-field="college">所属学院</th>
								 <th data-align="center" data-field="researchInterest">研究方向</th>
								 <th data-align="center" data-field="email">邮箱</th>
								 <th data-align="center" data-field="type">类别</th>
								 
				                
				                <th data-align="center" data-field="operation" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
				            </tr>
				        </thead>
					</table>
				</div>
		</div>
</div>

<!-- 增加模态框，单击增加按钮，弹出表单 -->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width:600px">
     <form class="registerform" action="" id="mdStudentAddCheck">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #428bca; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel"> 增加学生</h4>
           </div><!-- .modal-header -->
           
           <div class="modal-body addModal" >
       			 <div >
	                <label>学生姓名</label>
	                <input type="text" value="" id='mdStudentName-add' class="form-control" datatype="*2-10" errormsg="请输入2-10个字符" />
	                <span class="Validform_checktip">请输入学生姓名</span>
	             </div>
	             
   				 <!-- <div>
   				 	<label>个人照片</label> 
					<form id="form" method="post"  enctype="multipart/form-data" >    
       				<div id="photouploader">上传照片</div>
       				<input type="text" id="photo-add" /></form>
   				 </div> -->
   				    				    				 				
				<div >
	                <label>专业</label>
	                <input type="text" name="major-add" value="" id='major-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
	                <span class="Validform_checktip">请输入专业名称</span>
	            </div>

	            <div >
	                <label>所属学院</label>
	                <input type="text" name="college-add" value="" id='college-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
	                <span class="Validform_checktip">请输入所属学院</span>
	            </div>

	            <div style="margin-bottom: 10px">
	                <label>类别</label>
	                <input type="radio" value="硕士" id='mdStudentType-add'  name="mdStudentType-add"  checked/>硕士
	                <input type="radio" value="博士" id='mdStudentType-add'  name="mdStudentType-add" />博士
	                <span class="Validform_checktip">请选择类别</span>
	            </div>

	            <div >
	                <label>研究方向</label>
	                <input type="text" value="" id='researchInterest-add' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
	                <span class="Validform_checktip">请输入研究方向</span>
	           	</div>
	           	
	           	<div >
	                <label>邮箱</label>
	                <input type="text" name="email-add" value="" id='email-add' class="form-control" datatype="/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/" errormsg="请输入正确邮箱地址" />
	                <span class="Validform_checktip">请输入邮箱地址</span>
	           	</div>
	            
	             <div >
	                <label>个人简介</label>
					<textarea name="content" id="description-add"></textarea>						
	             </div>
	             
	             <div >
	             <label>个人照片  	: </label> <input type="hidden" id="photo-add" />
   				 <form id="newsform" method="post"  enctype="multipart/form-data" >    
       					 <div id="photouploader">上传照片</div>
   				 </form>
						
   				 </div>
            </div>    <!-- modal-body -->    
               
            <div class="modal-footer ">                		 	
   		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
       		 	<button type="submit" id="submit_add" class="btn btn-primary" >添加</button>                		 	               		
       		</div>  <!-- modal-footer -->    
       		                   
        	</div><!-- /.modal-content -->
        </form>   
    </div><!-- /.modal-dialog -->
</div><!--.modal-->

<!-- 查看模态框，单击操作按钮，弹出表单 -->
<div class="modal fade" id="lookUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width:600px">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #428bca; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">查看学生信息</h4>
            </div><!-- .modal-header -->
            <div class="modal-body  lookModal">
                <form class="form-horizontal" role="form">
                
                	<!-- 学生姓名 modal -->
                    <div>
                        <label>学生姓名</label>
                                 <input type="text" class="form-control" id="mdStudentname-look" name="mdStudentname-look" value="" >
                    </div>
                    
                    <!-- 个人照片 modal 
                    <div>
                            <label>个人照片</label>
                            <div class="col-sm-9" id='photo-look'>
                            </div>
                  </div>-->
					 
                        
                    <!-- 专业名称 modal -->
                    <div>
                        <label >专业名称</label>
                            <input type="text" class="form-control" id="major-look" name="major-look" value="" >
                   </div>
                    
                    <!-- 类别 modal -->
					<div>
                        <label>类别</label>
                            <input type="text" class="form-control" name="mdStudentType-look" value="" id="mdStudentType-look">
					</div>
					
					<!-- 所属学院 modal -->
                    <div>
                        <label>所属学院</label>
                            <input type="text" class="form-control" id="college-look" name="college-look" value="" >
                    </div>
					
					<!--研究方向 modal -->
                    <div>
                        <label>研究方向</label>
                            <input type="text" class="form-control" name="researchInterest-look" value="" id="researchInterest-look">
                    </div>
					
					<!--邮箱 modal -->
                    <div>
                        <label>邮箱</label>
                            <input type="text" class="form-control" name="email-look" value="" id="email-look">
                    </div>
                    
                    
		              <div >
                            <label   class="control-label">图片</label>
                            <div  id='photo_look' class="enclosuresShow">
                               
                            </div>
                       </div> 
                    
                    
                    
					<!-- 个人简介 modal -->
                    <div>
                        <label> 个人简介</label>
                        <div id="description-look"  class="lookDescription"></div>
                    </div>
                    
                </form>
            </div><!-- .modal-body -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!--.modal-->


<!-- 修改模态框，单击操作按钮，弹出表单 -->
<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width:600px">
    <form class="registerform" action="" id="mdStudentUpdateCheck">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="mdStudentUpdateCheck">
                    修改学生信息
                </h4>
            </div><!-- .modal-header -->
            <div class="modal-body updateModal">
               
                	<!-- 学生姓名 modal -->
                    <div>
                        <label>学生姓名</label>
                        
                            <input type="text" class="form-control" id="mdStudentname-modal" datatype="*2-20"  name="mdStudentname-modal" value="">
                        
                    </div>

                    <!-- 专业名称 modal -->
                    <div>
                        <label>专业名称</label>
                       
                            <input type="text" class="form-control" id="major-modal" datatype="*2-20" name="major-modal" value="">
                        
                    </div>
                        
                    <!-- 所属学院 modal -->
                    <div>
                        <label>所属学院</label>
                       
                            <input type="text" class="form-control" id="college-modal" datatype="*2-20" name="college-modal" value="">
                        
                    </div>
                    
              		<!-- 类别 modal -->
                     <div>
                      <label>类别</label>
                        <input type="radio" id="mdStudentType-modal" name="mdStudentType-modal" value="硕士"/>硕士
                      	<input type="radio" id="mdStudentType-modal" name="mdStudentType-modal"  value="博士"/>博士
                     </div>
                     
                   <!-- 研究方向 modal -->
                    <div>
                        <label>研究方向</label>
                        
                            <input type="text" class="form-control" id="researchInterest-modal" datatype="*2-20" name="researchInterest-modal" value="">
                       
                    </div>
                    
                    <!-- 邮箱 modal -->
                    <div>
                        <label>邮箱</label>
                        
                            <input type="text" class="form-control" id="email-modal" datatype="*2-20" name="email-modal" value="">
                       
                    </div>
                    
	             <!-- 个人简介modal -->
	             <div>
	                <label>个人简介</label>
					<textarea name="content" id="description-modal" ></textarea>	
	             </div>
	             
	             <!-- 个人照片 modal -->
                        <div>
                            <label for="photo-modal" >个人照片</label>
                            <div id="photo-modal" ></div>
                        </div>
                    
                
            </div><!-- .modal-body -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" >关闭  
                </button>
                <button type="submit" id="submit_update" class="btn btn-primary">
                    提交
                </button><span id="tip"> </span>
            </div>
        </div><!-- /.modal-content -->
        </form>
    </div><!-- /.modal-dialog -->
</div><!--.modal-->


<!-- 删除模态框（Modal） -->
<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #d9534f; color: white;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					删除学生 <span class="glyphicon glyphicon-exclamation-sign"></span>
				</h4>
			</div>
			<div class="modal-body">
				是否确认删除此学生？
			</div>
			<div class="modal-footer">
				<button type="button" id="submit_delete" class="btn btn-danger">
					删除
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>

</div>	
	
	<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap-select.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/mdStudent.js"></script>
	<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
	<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>

	<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
	<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/bootstrap-switch.js"></script>
	<script type="text/javascript" src="/lims/js/loading.js"></script>
</body>

</html>
