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
	<link rel="stylesheet" href="${ctx}/css/bootstrap-switch.css">
	<link rel="stylesheet" href="${ctx}/kindEditor/default/default.css" type="text/css">
		
	<link rel="stylesheet" href="${ctx}/css/animate.css">
	<link rel="stylesheet" href="${ctx}/css/loading.css">

	<link href="${ctx}/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<jsp:include page="template.jsp" flush="true"/><!--动态包含-->
</head>
<body>
	<div class="contents">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">教材信息</h3>
					</div>
					<div class="panel-body">
						<!-- 查询条件, 组件为input和button -->
						<div class="queryCondition form-inline">
							<div class="input-group queryStyle">
				        <span class="input-group-addon">教材名称</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="name_list">
					</div>
					
					<div class="input-group  queryStyle">
				        <span class="input-group-addon">出版社</span>
				        <input type="text" class="form-control" placeholder="关键字查询" id="publisher_list">
    				</div>
    				    				 
    				<div class="input-group queryStyle">
				        <span class="input-group-addon">主持人</span>
				        <select name="medalTeacher" class="selectpicker form-control" id="teacher_list" data-live-search="true" data-size="10">
						</select>
      				</div> 
				
    				<!-- 查询按钮 -->
    				<div class="query_add_btn ">
	    				<button id="queryBtn" type="button" class="btn btn-primary queryBtn" onclick="loadTable()">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<button onclick="clearWrongMsg()" id="addBtn" type="button" class="btn btn-default addBtn" data-toggle="modal" data-target="#addUserModal">
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
										<th data-align="center" data-field="name">教材名称</th>
						               	<th data-align="center" data-field="teacher.name">主持人</th>
						                <th data-align="center" data-field="publisher">出版社</th>
						               
						                <th data-align="center" data-field="publishTime">出版日期</th> 
						                <th data-align="center" data-field="ranking">排名</th>
						                <th data-align="center" data-field="totalWords">总字数</th>
						                <th data-align="center" data-field="referenceWords">参编字数</th>
						               
						                <th data-align="center" data-field="operation" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
									</tr>
								</thead>
							</table>

						</div>
					</div>
				</div>
			</div>

<!-- 增加模态框，单击增加按钮，弹出表单 -->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width:600px">
     <form class="registerform" action="" id="bookAddCheck">
        <div class="modal-content">
            <div class="modal-header" style="background-color:  #428bca; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel"> 增加教材</h4>
           </div><!-- .modal-header -->
           
           <div class="modal-body addModal">
       			 <div >
	                <label>教材名称</label>
	                <input type="text" value="" id='name-add' class="form-control" datatype="*1-20" errormsg="请输入1-20个字符" />
	                <span class="Validform_checktip">请输入教材名称</span>
	             </div>

 				<div >
	                <label>主持人</label>
	                <select id="teacher-add" class="selectpicker" data-live-search="true" data-size="10">
					</select>
					<span class="Validform_checktip">请选择主持人</span>   
	            </div>

	            <div >
	                <label>出版社名称</label>
	                <input type="text" value="" id='publisher-add' class="form-control" datatype="*3-20" errormsg="请输入3-20个字符"/>
	                <span class="Validform_checktip">请输入出版社名称</span>
	            </div>
	            
				<div class="controls input-append date form_date">
	                <label>出版时间</label>
	                <input size="16" type="text" id="publishTime-add" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                       <span class="add-on"><i class="icon-th"></i></span>    
	                <span class="Validform_checktip">请输入出版时间</span>
	            </div> 
	            
	            <div style="margin-bottom: 10px">
	                <label>主办方</label>
	                <input type="radio" value="1" id='sponsor-add'  name="sponsor-add"  checked/>是
	                <input type="radio" value="0" id='sponsor-add'  name="sponsor-add" />否
	                <span class="Validform_checktip">请选择主办方类别</span>
	            </div>	   
	                     
	            <div >
	                <label>排名</label>
	                <input type="text" value="" id='ranking-add' class="form-control" datatype="n1-2" errormsg="请输入1-2位数字"/>
	                <span class="Validform_checktip">请输入排名</span>
	            </div>
	            
	            <div >
	                <label>总字数</label>
	                <input type="text" value="" id='totalWords-add' class="form-control" datatype="n2-10" errormsg="请输入2-10位数字" />
	                <span class="Validform_checktip">请输入总字数</span>
	            </div>
	            	            	            
	            <div >
	                <label>参编字数</label>
	                <input type="text" value="" id='referenceWords-add' class="form-control" datatype="n2-10" errormsg="请输入2-10位数字"/>
	                <span class="Validform_checktip">请输入参编字数</span>
	            </div>
	             
	            <div >
	                <label>备注</label>
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
                <h4 class="modal-title" id="myModalLabel">查看教材信息</h4>
            </div><!-- .modal-header -->
            <div class="modal-body lookModal">
                <form class="form-horizontal" role="form">
                                	
                    <div>
                        <label>教材名称</label>
                            <input type="text" class="form-control" id="name-look" name="name-look">
                    </div>
                    
                    <div>
                        <label>教师ID</label>
                            <input type="text" class="form-control" id="teacherID-look" name="teacherID-look">
                    </div>
                                        
                    <div>
                        <label>出版社名称</label>
                            <input type="text" class="form-control" id="publisher-look" name="publisher-look">
                    </div>
					
					<div>
                        <label>出版时间</label>
                            <input type="text" class="form-control" name="publishTime-look" id="publishTime-look">
                    </div>
                    
                    <div>
                        <label>主办方</label>
                            <input type="text" class="form-control" name="sponsor-look" value="" id="sponsor-look">
					</div>
										
					<div>
                        <label>排名</label>
                            <input type="text" class="form-control" name="ranking-look" id="ranking-look">
                    </div>
										
                    <div>
                        <label>总字数</label>
                            <input type="text" class="form-control" name="totalWords-look" id="totalWords-look">
                    </div>
                    
                    <div>
                        <label>参编字数</label>
                            <input type="text" class="form-control" name="referenceWords-look" id="referenceWords-look">
                    </div>
					       
                    <div>
	                	<label>备注</label>
	                	<div id="abstracts-look" class="lookDescription" ></div>
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


<!-- 修改模态框，单击操作按钮，弹出表单  -->
<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px;">
        <form class="form-horizontal registerform" id="bookUpdateCheck" role="form">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                          修改教材信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
                 <div>
                        <label>教材名称</label>
                            <input type="text" class="form-control" id="name-modal" datatype="*1-20" name="name-modal" value="">
                        
                    </div>
                    
                    <div >
		                <label>主持人</label>
		                <select id="teacher-modal" class="selectpicker" data-live-search="true" data-size="10">
						</select>  
		            </div>

                    <div>
                        <label>出版社名称</label>
                            <input type="text" class="form-control" id="publisher-modal" datatype="*3-20" name="publisher-modal">
                    </div>
              
              		<div class="controls input-append date form_date">
		                <label>出版时间</label>
		                <input size="16" type="text" id="publishTime-modal" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
	                    <span class="add-on"><i class="icon-th"></i></span>    
	             	</div> 
	             
                  <div>
                  	<label>主办方</label>
                    	<input type="radio" id="sponsor-modal" name="sponsor-modal" value="1"/>是
                      	<input type="radio" id="sponsor-modal" name="sponsor-modal" value="0"/>否
                    </div>
	             
	             <div>
                 	<label>排名</label>
                     	<input type="text" class="form-control" id="ranking-modal"  name="ranking-modal" datatype="n1-2">
                    </div>
	             
	             <div>
                    <label>总字数</label>
                        <input type="text" class="form-control" id="totalWords-modal" name="totalWords-modal" datatype="n2-10">
                    </div>
                    
                <div>
                     <label>参编字数</label>
                         <input type="text" class="form-control" id="referenceWords-modal" name="referenceWords-modal" datatype="n2-10">
                 </div>
	             
	             <div >
	                <label>备注</label>
					<textarea name="content" id="abstracts-modal" ></textarea>
										
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
						删除科研成果 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此科研成果？
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
<script type="text/javascript" src="${ctx}/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ctx}/js/book.js"></script>
<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>

<script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</body>
</html>