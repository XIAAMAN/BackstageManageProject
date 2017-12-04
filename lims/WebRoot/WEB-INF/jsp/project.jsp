<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx}/css/bootstrap.css" type="text/css"> 
<link rel="stylesheet" href="${ctx}/css/bootstrap-table.min.css" type="text/css">
<link rel='stylesheet' href='${ctx}/css/test.css' type="text/css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/demo.css" type="text/css">
<link rel="stylesheet" href="${ctx}/kindEditor/default/default.css" type="text/css">

<link rel="stylesheet" href="${ctx}/css/animate.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

	
<jsp:include page="template.jsp" flush="true"/><!--动态包含-->		

</head>
	<body>
		<!-- 页面主体 -->
	<div class="body row contents">
		
		<!-- 中间表格表格 -->
		
			<!-- 查询面板 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						项目数据
					</h3>
				</div>
				<div class="panel-body">
					<!-- 查询条件, 组件为input和button -->
					<div class="qeuryCondition form-inline">
						<div class="input-group queryStyle">
					        <span class="input-group-addon">项目名称</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="name_list">
    					</div>
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">级别</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="level_list">
        				</div>
        				
        				<div class="input-group queryStyle">
					        <span class="input-group-addon">主持人</span>
					        <select name="selectTeacher" class="selectpicker form-control" id="selectTeacher" data-live-search="true" data-size="10">
							</select>
        				</div>      		
        				
        				<div class="input-group queryStyle">
					        <span class="input-group-addon">起止日期</span>
					        <select name="selectStartYear" class="selectpicker form-control" id="selectStartYear" data-size="10"></select>
							<span class="input-group-addon">-</span>
							<select name="selectEndYear" class="selectpicker form-control" id="selectEndYear" data-size="10"></select>
        				</div>
        				
        				<!-- 查询按钮 -->
        				<button onclick="loadTable()" id="queryBtn" type="button" class="btn btn-primary queryBtn">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<button id="addBtn" class="btn btn-default addBtn" data-toggle="modal" data-target="#addUserModal" onclick=showSelectedAdd()>
					        <span class="glyphicon glyphicon-plus"></span> 增加
					    </button>
					</div><br>
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
					           	data-response-handler="responseHandler"
				           
				           >
				        <thead>
				           	<tr>
				               	<th data-align="center" data-field="code">项目编号</th>
				                <th data-align="center" data-field="name">项目名称</th>
				                <th data-align="center" data-field="teacher.name">主持人</th>
				                <th data-align="center" data-field="member">项目成员</th>
				                <th data-align="center" data-field="level">级别</th>
				                <th data-align="center" data-field="fund">经费(万元)</th>
				                <th data-align="center" data-field="source">来源</th>
				                
				                <th data-align="center" data-field="startEndDate" 
				                	data-formatter="dateFormatter">起止时间</th>
				           
				                
				                <th data-align="center" data-field="operation" 
						        	data-formatter="actionFormatter"
						       		data-events="actionEvents">操作
						        </th>
				            
				            </tr>
				        </thead>
    					</table>
					</div>
				</div>
			</div>
		
		
	</div>
	
	<!-- 增加模态框，单击增加按钮，弹出表单 -->
	<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         <form class="registerform" action="" id="projectAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加用户
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      
          	       
           			 <div >
		                <label>项目名称</label>
		                <input type="text" value="" id='projectName-add' class="form-control" datatype="*6-50" errormsg="请输入6-50个字符" />
		                <span class="Validform_checktip">请输入项目名称</span>
		             </div>
		             
		             <div >
		                <label>主持人</label>
		                <select id="teacherId-add" class="selectpicker" data-live-search="true" data-size="10">
						</select>
						<span class="Validform_checktip">请选择主持人</span>
		               
		             </div>
		             
		             <div >
		                <label>项目成员</label>
		                <input type="text" value="" id='member-add' class="form-control" datatype="*2-30" errormsg="请输入2-30个字符" />
		                <span class="Validform_checktip">请输入项目成员</span>
		             </div>
		             
		             <div >
		                <label>项目编号</label>
		                <input type="text" value="" id='code-add' class="form-control" datatype="*6-20" errormsg="请输入6-20个字符" />
		                <span class="Validform_checktip">请输入您的项目编号</span>
		             </div>
		             
		             <div >
		                <label>经费(万元)</label>
		                <input type="text" value="" id='fund-add' class="form-control" datatype="/^[0-9]+([.][0-9]+){0,1}$/" errormsg="必须为数字，可以有小数" />
		                <span class="Validform_checktip">请输入项目经费</span>
		             </div>
		             
		             <div >
		                <label>来源</label>
		                <input type="text" value="" id='source-add' class="form-control" datatype="*" errormsg="请输入正确的项目来源" />
		                <span class="Validform_checktip">请输入项目来源</span>
		             </div>
		             
		             <div >
		                <label>级别</label>
		                <select id="level-add" class="selectpicker" data-size="10" >
			                <option>国家级</option>
			                <option>省部级</option>
			                <option>市厅级</option>
			                <option>校级</option>
			                <option>横向</option>
						</select>
		                <span class="Validform_checktip">请选择项目级别</span>
		             </div>
		             
		             <div >	
		                <label>开始时间</label>
		                <select id="startYear-add" class="selectpicker" data-size="10" ></select>
		                <span>-</span>		                
		                <select id="startMonth-add" class="selectpicker" data-size="10" ></select>
		                <span class="Validform_checktip">请选择项目开始日期</span>
		             </div>
		             
		             <div >
		                <label>终止时间</label>
		                <select id="endYear-add" class="selectpicker" data-size="10" ></select>
		                <span>-</span>		                
		                <select id="endMonth-add" class="selectpicker" data-size="10" ></select>
		                <span class="Validform_checktip">请选择项目终止日期</span>
		             </div>
		             
		             <div >
		                <label>项目简介</label>
						<textarea name="content" ></textarea>						
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
            查看项目信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body lookModal">
                    <form class="form-horizontal" role="form">
                    
                    	<!-- 项目名称modal -->
                        <div>
                            <label for="projectName-look" class="control-label">项目名称</label>
                            <input type="text" class="form-control" id="projectName-look" name="projectName-look" value="">                        
                        </div>
                        
						<!-- 教师Id modal -->
                        <div>
                            <label for="teacherId-look" class="control-label">主持人</label>                          
                            <input type="text" class="form-control" name="teacherId-look" value="" id="teacherId-look">                           
                        </div>
						
						<!-- 成员 modal -->
						<div>
                            <label for="member-look" class="control-label">项目成员</label>                          
                            <input type="text" class="form-control" name="member-look" value="" id="member-look"> 
                        </div>
						
						<!--项目编号 modal -->
                        <div>
                            <label for="code-look" class="control-label">项目编号</label>
                            <input type="text" class="form-control" name="code-look" value="" id="code-look">
					    </div>
						
						<!-- 经费 modal -->
                        <div>
                            <label for="fund-look" class="control-label">经费</label>
                            <input  type="text" class="form-control"  name="fund-look" value="" id="fund-look">       
                        </div>
                        
                        <!--来源 modal -->
                        <div>
                            <label for="source-look" class="control-label">来源</label>                            
                            <input type="text" class="form-control" name="source-look" value="" id="source-look">             
                        </div>
                        
                        <!--等级  modal -->
                        <div>
                            <label for="level-look" class="control-label">级别</label>
                            <input type="text" class="form-control" name="level-look" value="" id="level-look">
                        </div>
                        
                        <!--起始时间 modal -->
                        <div>
                            <label for="startDate-look" class="control-label">起始时间</label>
                            <input type="text" class="form-control" name="startDate-look" value="" id="startDate-look">                          
                        </div>
                        
                        <!-- 终止时间modal -->
                        <div>
                            <label for="endDate-look" class="control-label">终止时间</label>   
                            <input type="text" class="form-control" name="endDate-look" value="" id="endDate-look">                            
                        </div>
                        
                        <!-- 项目简介modal -->
                        <div>
                           <div>项目简介</div>                             
                           <div id="description-look" class="lookDescription"></div>     
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
	<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
        <form class="registerform" action="" id="projectUpdateCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改用户信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
                    <form class="form-horizontal" role="form">
                    
                    	<!-- 项目名称modal -->
                        <div>
                            <label for="projectName-modal" class="control-label">项目名称</label>
                            <input type="text" class="form-control" id="projectName-modal" name="nickname-modal" value="" datatype="*6-50">                        
                        </div>
                        
						<!-- 教师Id modal -->
                        <div >
                            <label>主持人</label>
							<select id="teacherId-modal" class="selectpicker" data-live-search="true" data-size="10">
							</select>                            
                        </div>
						
						<!-- 成员 modal -->
						<div>
                            <label>项目成员</label>
                            <input type="text" class="form-control" name="member-modal" value="" id="member-modal" datatype="*2-30">   
                        </div>
						
						<!--项目编号 modal -->
                        <div>
                            <label for="code-modal" class="control-label">项目编号</label>
                            <input type="text" class="form-control" name="code-modal" value="" id="code-modal" datatype="*6-20">                         
                        </div>
						
						<!-- 经费 modal -->
                        <div>
                            <label for="fund-modal" class="control-label">经费(万元)</label>                      
                            <input type="text" class="form-control"  name="fund-modal" value="" id="fund-modal" datatype="/^[0-9]+([.][0-9]+){0,1}$/">   
                        </div>
                        
                        <!--来源 modal -->
                        <div>
                            <label for="source-modal" class="control-label">来源</label>                          
                            <input type="text" class="form-control" name="source-modal" value="" id="source-modal" datatype="*2-20">                          
                        </div>
                        
                        <!--等级  modal -->
                        <div>
                            <label for="level-modal" class="control-label">级别</label>                      
                              
                            <select id="level-modal" class="selectpicker" data-size="10">
	                            <option>国家级</option>
				                <option>省部级</option>
				                <option>市厅级</option>
				                <option>校级</option>
				                <option>横向</option>
							</select>                        
                        </div>
                        
                        <!--起始时间 modal -->
                        <div>
                            <label for="startDate-modal" class="control-label">起始时间</label>                          
                            <input type="text" class="form-control" name="startDate-modal" value="" id="startDate-modal">                         
                        </div>
                        
                        <!-- 终止时间modal -->
                        <div>
                            <label for="endDate-modal" class="control-label">终止时间</label>                          
                            <input type="text" class="form-control" name="endDate-modal" value="" id="endDate-modal">                           
                        </div>
                        
                        <!-- 项目简介modal -->
                        <div>
                            <label for="description-modal" class="control-label">项目简介</label>      
                            <textarea name="content" id="description-modal"></textarea>	            
                        </div>
                        
                    </form>
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
						删除项目 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此项目？
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
	<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/js/project.js"></script>
<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>
</body>
</html>
