<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap.css"> 
<link rel="stylesheet" href="css/bootstrap-table.min.css">
<link rel='stylesheet' href='css/test.css'>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link href="css/demo.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/animate.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<jsp:include page="template.jsp" flush="true"/><!--动态包含-->

</head>
	<body>
		<!-- 页面主体 -->
	<div class="body row contents">
		
			<!-- 查询面板 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						用户数据
					</h3>
				</div>
				<div class="panel-body">
					<!-- 查询条件, 组件为input和button -->
					<div class="qeuryCondition form-inline">
						<div class="input-group queryStyle">
					        <span class="input-group-addon">账户</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="userNameList">
    					</div>
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">用户姓名</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="nickNameList">
        				</div>
        				
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">联系电话</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="phoneList">
        				</div>
        				
        				<!-- 查询按钮 -->
        				<button onclick="loadTable()" id="queryBtn" type="button" class="btn btn-primary queryBtn">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<button onclick="clearWrongMsg()" id="addBtn" class="btn btn-default addBtn" data-toggle="modal" data-target="#addUserModal">
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
				               	<th data-align="center" data-field="userName">账户</th>
				                <th data-align="center" data-field="nickName">用户姓名</th>
				                <th data-align="center" data-field="instituteName">研究所名称</th>
				                <th data-align="center" data-field="phone">联系电话</th>
				                <th data-align="center" data-field="email">电子邮件</th>
				                <th data-align="center" data-field="lastLogin">最近登录时间</th>
		       
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
         <form class="registerform" action="" id="adminAddCheck">
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
		                <label>账户</label>
		                <input type="text" value="" id='userName-add' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符" />
		                <span class="Validform_checktip">请输入您的账户</span>
		             </div>
		             
		             <div >
		                <label>用户姓名</label>
		                <input type="text" value="" id='nickName-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入您的姓名</span>
		             </div>
		             
		             <div >
		                <label>研究所名称</label>
		                <input type="text" value="" id='instituteName-add' class="form-control" />
		                <span class="Validform_checktip">请输入研究所名称</span>
		             </div>
		             
		             <div >
		                <label>密码</label>
		                <input type="password" value="" id='password-add' class="form-control" datatype="*6-20" errormsg="请输入6-20个字符" />
		                <span class="Validform_checktip">请输入您的密码</span>
		             </div>
		             
		             <div >
		                <label>移动电话</label>
		                <input type="text" value="" id='phone-add' class="form-control" datatype="m" errormsg="请输入有效的手机号码" />
		                <span class="Validform_checktip">请输入您的手机号码</span>
		             </div>
		             
		             <div >
		                <label>邮箱</label>
		                <input type="text" value="" id='email-add' class="form-control" datatype="e" errormsg="请输入有效的邮箱地址" />
		                <span class="Validform_checktip">请输入您的邮箱</span>
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
        <form class="registerform" action="" id="adminUpdateCheck">
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
                 
                    	<!-- 账户modal -->
                        <div>
                            <label>账户</label>
                                <input type="text" class="form-control" id="userName-modal" name="userName-modal" value="" datatype="*3-20">
                        </div>
                        
						<!-- 用户姓名 -->
                        <div>
                            <label>用户姓名</label>
                                <input type="text" class="form-control" id="nickName-modal" name="nickName-modal" value="" datatype="*2-20">
                        </div>
						
						<!--  研究所编号modal -->
						<div>
                            <label>研究所名称</label>                   
                                <input type="text" class="form-control" name="instituteName-modal" value="" id="instituteName-modal">
                        </div>
						
						<!--移动电话 modal -->
                        <div>
                            <label>移动电话</label>
                                <input type="text" class="form-control" name="phone-modal" value="" id="phone-modal" datatype="m">
                        </div>
						
						<!-- 邮箱modal -->
                        <div>
                            <label>邮箱</label>
                                <input type="text" class="form-control"  name=""email-modal"" value="" id="email-modal" datatype="e">
                        </div>                       
                       
                </div><!-- .modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
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
						删除次用户 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此用户？
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
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>
</body>
</html>
