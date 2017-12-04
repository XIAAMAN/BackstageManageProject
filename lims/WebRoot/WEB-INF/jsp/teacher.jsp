<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>teacher information and operation page</title>
<link rel="stylesheet" href="${ctx}/css/bootstrap.css" type="text/css"> 
<link rel="stylesheet" href="${ctx}/css/bootstrap-table.min.css" type="text/css">
<link rel='stylesheet' href='${ctx}/css/test.css' type="text/css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/demo.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/uploadfile.css">
<link rel="stylesheet" href="${ctx}/kindEditor/default/default.css" type="text/css">

<link rel="stylesheet" href="${ctx}/css/animate.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<jsp:include page="template.jsp" flush="true"/><!--动态包含-->
</head>
<body>
	<div class="contents">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">教师信息</h4>
					</div>
					<div class="panel-body">
						<!-- 查询条件, 组件为input和button -->
						<div class="queryCondition form-inline">
							<div class="input-group queryStyle">
								<span class="input-group-addon">姓名</span> <input type="text"
									class="form-control" placeholder="请输入教师姓名" id="Name"
									style="width: 200px">
							</div>
							<div class="input-group queryStyle">
								<span class="input-group-addon">职称</span> <select
									class="form-control selectpicker"S id="Title">
									<option selected>无</option>
									<option>教授</option>
									<option>副教授</option>
									<!-- 职称 -->
									<option>讲师</option>
									<option>助教</option>
								</select>
							</div>
							<div class="input-group queryStyle">
								<span class="input-group-addon">专业</span> <input type="text"
									class="form-control" placeholder="请输入专业名称" id="Major">
							</div>
							<div class="input-group queryStyle">
								<span class="input-group-addon">研究方向</span> <input type="text"
									class="form-control" placeholder="请输入研究方向"
									id="ResearchInterest">
							</div>
							<div class="input-group queryStyle">
								<span class="input-group-addon">硕博导</span> 
								<select
									class="form-control selectpicker" id="Director">
									<option selected>无</option>
									<option>硕导</option>
									<option>博导</option>
								</select>
							</div>
							<!-- 查询和增加按钮 -->
							<div class="query_add_btn ">
								<button onclick="loadTable()" id="queryBtn" type="button"
									class="btn btn-primary queryBtn form-control">
									<span class="glyphicon glyphicon-search"></span> 查询
								</button>
								<button onclick="clearWrongMsg()" id="addBtn" type="button" class="btn btn-default addBtn form-control"
									data-toggle="modal" data-target="#addUserModal" >
									<span class="glyphicon glyphicon-plus"></span> 增加
								</button>
							</div>
						</div>
						<br>
						<!-- 显示表格信息 -->
						<div class="info">
							<table id="userInfo-table" data-toggle="table"
								data-show-columns="false" data-show-refresh="false"
								data-click-to-select="true" data-pagination="true"
								data-striped="true" data-show-toggle="false"
								data-side-pagination="server" data-pagination="true"
								data-page-number="1" data-page-size="10"
								data-page-list="[10,20, 50, 100]"
								data-response-handler="responseHandler">
								<thead>
									<tr>
										<th data-align="center" data-field="id" data-visible="false">ID</th>
										<th data-align="center" data-field="name">教师姓名</th>
										<th data-align="center" data-field="major">专业名称</th>
										<th data-align="center" data-field="title">职称</th>
										<th data-align="center" data-field="position">职务</th>
										<th data-align="center" data-field="director">硕博导</th>
										<th data-align="center" data-field="email">电子邮箱</th>
										<th data-align="center" data-field="researchInterest">研究方向</th>
										
										<th data-align="center" data-field="operation"
											data-formatter="actionFormatter" data-events="actionEvents">操作
										</th>
									</tr>
								</thead>
							</table>

						</div>
					</div>
				</div>
			</div>


<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         <form class="registerform" action="" id="teacherAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加教师
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      
          	       
           			 <div >
		                <label>教师姓名</label>
		                <input type="text" value="" id='addName' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入教师姓名</span>
		             </div>
		             
		             <div >
		                <label>专业名称</label>
		                <input type="text" value="" id='addMajor' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入专业名称</span>
		             </div>
		             
		             <div >
		                <label>所属学院</label>
		                <input type="text" value="" id='addCollege' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入所属学院</span>
		             </div>
		             
		             <div >
		                <label>毕业院校</label>
		                <input type="text" value="" id='addGraduateCollege' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入毕业院校</span>
		             </div>
		             
		             <div >
		                <label>研究方向</label>
		                <input type="text" value="" id='addResearchInterest' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入研究方向</span>
		             </div>
		             
		             <div >
		                <label>职务</label>
		                <input type="text" value="" id='addPosition' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入您的职务</span>
		             </div>
		             
		             <div >
		                <label>职称</label>
		                <select id="addTitle" class="selectpicker" data-size="10" >
			                <option selected>教授</option>
							<option>副教授</option>
							<option>讲师</option>
							<option>助教</option>
						</select>
		                <span class="Validform_checktip">请选择职称</span>
		             </div>
		             
		             <div >	
		                <label>硕博导</label>
		                <select id="addDirector" class="selectpicker" data-size="10" >
		                	<option selected>硕导</option>
							<option>博导</option>
							<option>无</option>
		                </select>
		                <span class="Validform_checktip">请选择硕博导</span>
		             </div>
		             
		              <div >
		                <label>电子邮箱</label>
		                <input type="text" value="" id='addEmail' class="form-control" datatype="e" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入您的电子邮箱</span>
		             </div>
		             
		             <div>
		             	<label>图片</label>
		             	<input type="hidden" id="addPhoto" />
		             </div>
		              <form id="newsform" method="post" action="" enctype="multipart/form-data" > 
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <div id="fileuploader" >上传图片</div>
				    </form>
		             
		             <div >
		                <label>个人简介</label>
						<textarea name="content" id="addDescription"></textarea>						
		             </div>
         		 
                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
           		 	<button type="submit" id="submit_add" class="btn btn-primary">添加</button>                		 	               		
           		</div>  <!-- modal-footer -->    
           		                   
            	</div><!-- /.modal-content -->
            </form>   
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->


<!-- 修改模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
        <form class="registerform" action="" id="teacherUpdateCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修教师信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
              
                    
                    	<!-- 项目名称modal -->
                     <div >
		                <label>教师姓名</label>
		                <input type="text" value="" id='modifyName' class="form-control" datatype="*2-20" />		               
		             </div>
		             
		             <div >
		                <label>专业名称</label>
		                <input type="text" value="" id='modifyMajor' class="form-control" datatype="*3-20"/>
		             </div>
		             
		             <div >
		                <label>所属学院</label>
		                <input type="text" value="" id='modifyCollege' class="form-control" datatype="*3-20"/>
		             </div>
		             
		             <div >
		                <label>毕业院校</label>
		                <input type="text" value="" id='modifyGraduateCollege' class="form-control" datatype="*3-20" />
		             </div>
		             
		             <div >
		                <label>研究方向</label>
		                <input type="text" value="" id='modifyResearchInterest' class="form-control" datatype="*3-20"/>
		             </div>
		             
		             <div >
		                <label>职务</label>
		                <input type="text" value="" id='modifyPosition' class="form-control" datatype="*2-20"/>
		             </div>
		             
		             <div >
		                <label>职称</label>
		                <select id="modifyTitle" class="selectpicker" data-size="10" >
			                <option selected>教授</option>
							<option>副教授</option>
							<option>讲师</option>
							<option>助教</option>
						</select>
		             </div>
		             
		             <div >	
		                <label>硕博导</label>
		                <select id="modifyDirector" class="selectpicker" data-size="10" >
		                	<option selected>硕导</option>
							<option>博导</option>
							<option>无</option>
		                </select>
		             </div>
		             
		              <div >
		                <label>电子邮箱</label>
		                <input type="text" value="" id='modifyEmail' class="form-control" datatype="e"/>
		             </div>
		             
		              <!-- 图片modal -->
                        <div>
                            <div class="updateFileLabelStyle">图片</div>
                            <div id="photo-modal">
                               
                            </div>
                        </div>
		             
		             
    
		             <div >
		                <label>个人简介</label>
						<textarea name="content" id="modifyDescription"></textarea>						
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
	
	
	<!-- 查看模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="lookUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        查看教师信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body lookModal">
              
                    
                    	<!-- 教师姓名modal -->
                     <div >
		                <label>教师姓名</label>
		                <input type="text" value="" id='lookName' class="form-control" class="form-control"/>		               
		             </div>
		             
		             <div >
		                <label>专业名称</label>
		                <input type="text" value="" id='lookMajor' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >
		                <label>所属学院</label>
		                <input type="text" value="" id='lookCollege' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >
		                <label>毕业院校</label>
		                <input type="text" value="" id='lookGraduateCollege' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >
		                <label>研究方向</label>
		                <input type="text" value="" id='lookResearchInterest' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >
		                <label>职务</label>
		                <input type="text" value="" id='lookPosition' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >
		                <label>职称</label>
		                <input type="text" value="" id='lookTitle' class="form-control" class="form-control"/>
		             </div>
		             
		             <div >	
		                <label>硕博导</label>
		                <input type="text" value="" id='lookDirector' class="form-control" class="form-control"/>
		             </div>
		             
		              <div >
		                <label>电子邮箱</label>
		                <input type="text" value="" id='lookEmail' class="form-control" datatype="e"/>
		             </div>
		             
		              <div >
                            <label   class="control-label">图片</label>
                            <div  id='photo_look' class="enclosuresShow">
                               
                            </div>
                       </div> 
		             
    
		             <div >
		                <label>个人简介</label>
						<div id="lookDescription" class="lookDescription"></div>						
		             </div>
                        
                    
                </div><!-- .modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" >关闭  
                    </button>       
                </div>
            </div><!-- /.modal-content -->
  
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
	

	<!-- 删除模态框（Modal） -->
	<div class="modal fade" id="deleteUserModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #d9534f; color: white;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						删除教师 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">是否确认删除此教师信息？</div>
				<div class="modal-footer">
					<button type="button" id="submit_delete" class="btn btn-danger">
						删除</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/js/teacher.js"></script>
<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>
</body>
</html>