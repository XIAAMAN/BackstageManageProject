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
	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" type="text/css">
	<link rel="stylesheet" href="${ctx}/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${ctx}/kindEditor/default/default.css" type="text/css">
		
	<link rel="stylesheet" href="${ctx}/css/animate.css">
	<link rel="stylesheet" href="${ctx}/css/loading.css">

	<link href="${ctx}/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	
	<jsp:include page="template.jsp" flush="true"/><!--动态包含-->
</head>

<body>
<!-- 页面主体 -->
<div class="body row contents">
	
		<!-- 查询面板 -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">专利信息</h3>
			</div>
			<div class="panel-body">
				<!-- 查询条件, 组件为input和button -->
				<div class="qeuryCondition form-inline">
					<div class="input-group queryStyle">
				        <span class="input-group-addon">标题</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="name_list">
					</div>
					
					<div class="input-group  queryStyle">
				        <span class="input-group-addon">作者</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="author_list&authorAll_list">
    				</div>
    				
    				
    				<div class="input-group queryStyle">
				        <span class="input-group-addon">专利类型</span>
				        <select name="selectTeacher" class="selectpicker form-control" id="selectPatentType" data-size="10">
				        	<option selected>无</option>
							<option>发明专利</option>
			                <option>外观专利</option>
			                <option>实用新型</option>
						</select>
    				</div>
    				
    				<div class="input-group queryStyle">
				        <span class="input-group-addon">状态</span>
				        <select name="selectTeacher" class="selectpicker form-control" id="selectStatus" data-size="10">
				        	 <option selected>无</option>
							 <option>已受理</option>
		                	<option>已授权</option>
						</select>
    				</div>   
    				
    				<div class="input-group queryStyle">
				        <span class="input-group-addon">起止日期</span>
				        <select sytle="" name="selectStartYear" class="selectpicker form-control" id="selectStartYear" data-size="10"></select>
						<span class="input-group-addon">-</span>
						<select sytle="" name="selectEndYear" class="selectpicker form-control" id="selectEndYear" data-size="10"></select>
    				</div>
    				
    				
    				<!-- 查询按钮 -->
    				<button id="queryBtn" type="button" class="btn btn-primary queryBtn" onclick="loadTable()">
		          		<span class="glyphicon glyphicon-search"></span> 查询
		        	</button>
		        	<button onclick="clearWrongMsg()" id="addBtn" type="button" class="btn btn-default addBtn" data-toggle="modal" data-target="#addUserModal">
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
				           		
				               	<th data-align="center" data-field="name">标题</th>
				                <th data-align="center" data-field="author&authorAll" data-formatter="authorFormatter" >作者(全)</th>
				                <!-- <th data-align="center" data-field="authorAll">其他作者</th> -->
				                <th data-align="center" data-field="publishTime">发表时间</th>
				                <th data-align="center" data-field="patentType">专利类型</th>
				                <th data-align="center" data-field="status">状态</th>
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
     <form class="registerform" action="" id="patentAddCheck">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #428bca; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel"> 增加专利</h4>
           </div><!-- .modal-header -->
           
           <div class="modal-body addModal">
       			 <div >
	                <label>标题</label>
	                <input type="text" value="" id='patentName-add' class="form-control" datatype="*6-20" errormsg="请输入6-20个字符" />
	                <span class="Validform_checktip">请输入专利标题</span>
	             </div>

 				<div >
	                <label>作者</label>
	                <input type="text" value="" id='patentAuthor-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
	                <span class="Validform_checktip">请输入作者</span>
	            </div>

	            <div >
	                <label>其他作者</label>
	                <input type="text" value="" id='patentAuthorAll-add' class="form-control" />
	                <span class="Validform_checktip">请输入其他作者</span>
	            </div>

	            <div >
	                <label>专利类型</label>
	                <select id="patentType-add" class="selectpicker" data-size="10" >
		                <option>发明专利</option>
		                <option>外观专利</option>
		                <option>实用新型</option>
					</select>
	                <span class="Validform_checktip">请选择专利类型</span>
	             </div>

				<div class="controls input-append date form_date">
	                <label>发表时间</label>
	                <input size="16" type="text" id="publishTime-add" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                       <span class="add-on"><i class="icon-th"></i></span>    
	                <span class="Validform_checktip">请选择发表时间</span>
	             </div> 
   
	            <div >
	                <label>状态</label>
	                <select id="status-add" class="selectpicker" data-size="10" >
		                <option>已受理</option>
		                <option>已授权</option>
					</select>
	                <span class="Validform_checktip">请选择状态</span>
	             </div>
	            
	             
	             <div >
	                <label>摘要</label>
					<textarea name="content" id="abstracts-add"></textarea>						
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
<div class="modal fade" id="lookUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width:600px">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #428bca; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">查看专利信息</h4>
            </div><!-- .modal-header -->
            <div class="modal-body lookModal">
                <form class="form-horizontal" role="form">
                
                	<!-- 专利标题modal -->
                    <div>
                        <label>标题</label>
                            <input type="text" class="form-control" id="patentName-look" name="patentName-look">
                    </div>

                    <!-- 作者modal -->
                    <div>
                        <label>作者</label>
                            <input type="text" class="form-control" id="patentAuthor-look" name="patentAuthor-look">
                    </div>
                    
                    <!-- 其他作者modal -->
                    <div>
                        <label>其他作者</label>
                            <input type="text" class="form-control" id="patentAuthorAll-look" name="patentAuthorAll-look">
                    </div>
					
					<!-- 专利类型 modal -->
					<div>
                        <label>专利类型</label>
                            <input type="text" class="form-control" name="patantType-look" id="patantType-look">
                    </div>
					
					<!--状态 modal -->
                    <div>
                        <label>状态</label>
                            <input type="text" class="form-control" name="status-look" id="status-look">
                    </div>
                    
					<div>
                        <label>出版时间</label>
                            <input type="text" class="form-control" name="publishTime-look" id="publishTime-look">
                    </div>
                    
					<!-- 摘要 modal -->
                    <div>
	                	<label>摘要</label>
	                	<div id="patentAbstracts-look" class="lookDescription" ></div>
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
     <form class="registerform" action="" id="patentUpdateCheck">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    修改专利信息
                </h4>
            </div><!-- .modal-header -->
            <div class="modal-body updateModal">
                <form class="form-horizontal" role="form">
                
                	<!-- 专利标题modal -->
                    <div>
                        <label>标题</label>
                        
                            <input type="text" class="form-control" id="patentName-modal" datatype="*6-20" name="patentname-modal" value="">
                        
                    </div>

                    <!-- 作者modal -->
                    <div>
                        <label>作者</label>
                       
                            <input type="text" class="form-control" id="patentAuthor-modal" datatype="*2-20" name="patentAuthor-modal" >
                      
                    </div>

                    <!-- 其他作者modal -->
                    <div>
                        <label>其他作者</label>
                        
                            <input type="text" class="form-control" id="patentAuthorAll-modal" datatype="*2-20" name="patentAuthorAll-modal">
                       
                    </div>
              
                  <div>
                      <label>专利类型</label>
                   
                      <select class="form-control"  id="patentType-modal" class="selectpicker" data-size="10" >
                      	  
			              <option>发明专利</option>
			              <option>外观专利</option>
			              <option>实用新型</option>
						</select>
                      
                  </div>
                  
                  <div>
                      <label>状态</label>
                    
                      <select class="form-control"  id="status-modal" class="selectpicker" data-size="10" >
                       
			            <option>已受理</option>
		                <option>已授权</option>
						</select>
                      
                  </div>
	             
	             <div class="controls input-append date form_date">
	                <label>发表时间</label>
	                <input size="16" type="text" id="publishTime-modal" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                       <span class="add-on"><i class="icon-th"></i></span>    
	                
	             </div> 
	             
	             
	             <div >
	                <label>摘要</label>
					<textarea name="content" id="abstracts-modal" ></textarea>
										
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
					删除专利 <span class="glyphicon glyphicon-exclamation-sign"></span>
				</h4>
			</div>
			<div class="modal-body">
				是否确认删除此专利？
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
	<script type="text/javascript" src="${ctx}/js/patent.js"></script>
	<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
	<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
	<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/loading.js"></script>
	
	<script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</body>
</html>
