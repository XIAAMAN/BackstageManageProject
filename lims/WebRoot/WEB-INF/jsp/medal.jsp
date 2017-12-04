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
						<h4 class="panel-title">科研成果</h4>
					</div>
					<div class="panel-body">
						<!-- 查询条件, 组件为input和button -->
						<div class="queryCondition form-inline">
							<div class="input-group queryStyle">
								<span class="input-group-addon">项目名称</span> <input type="text"
									class="form-control" placeholder="请输入项目名称" id="medalName"
									>
							</div>
							
							<div class="input-group queryStyle">
						        <span class="input-group-addon">主持人</span>
						        <select name="medalTeacher" class="selectpicker form-control" id="medalTeacher" data-live-search="true" data-size="10">
								</select>
        					</div> 
							
							<div class="input-group queryStyle">
								<span class="input-group-addon">项目类型</span> 
								<select
									class="form-control selectpicker" id="medalType">
									<option selected>无</option>
									<option>教学成果奖</option>
									<option>科研成果奖</option>
								</select>
							</div>
							
							<div class="input-group queryStyle">
								<span class="input-group-addon">成绩</span> 
								<select
									class="form-control selectpicker" id="medalGrade">
									<option selected>无</option>
									<option>特等奖</option>
									<option>一等奖</option>
									<option>二等奖</option>
									<option>三等奖</option>
									<option>其他</option>
								</select>
							</div>
							
							<div class="input-group queryStyle">
								<span class="input-group-addon">级别</span> 
								<select
									class="form-control selectpicker" id="medalLevel">
									<option selected>无</option>
									<option>国家级</option>
									<option>省部级</option>
									<option>市厅级</option>
									<option>校级</option>
									<option>横向</option>
								</select>
							</div>
							<!-- 查询和增加按钮 -->
							<div class="query_add_btn ">
								<button onclick="loadTable()" id="queryBtn" type="button"
									class="btn btn-primary queryBtn form-control">
									<span class="glyphicon glyphicon-search"></span> 查询
								</button>
								
								<!-- 增加按钮 -->
					        	<button onclick="clearWrongMsg()" id="medalAddBtn" class="btn btn-default addBtn" data-toggle="modal" data-target="#addMedalModal" >
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
										<th data-align="center" data-field="name">项目名称</th>
										<th data-align="center" data-field="teacher.name">主持人</th>
										<th data-align="center" data-field="level">级别</th>
										<th data-align="center" data-field="type">类型</th>
										<th data-align="center" data-field="grade">成绩</th>
										<th data-align="center" data-field="sponsor">是否为主办方</th>
										<th data-align="center" data-field="ranking">排名</th>
										<th data-align="center" data-field="winningTime">获奖时间</th>
										
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

<!-- 增加模态框，单击增加按钮，弹出表单 -->
	<div class="modal fade" id="addMedalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         <form class="registerform" action="" id="medalAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加科研成果
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      
          	       
           			 <div >
		                <label>项目名称</label>
		                <input type="text" value="" id='medalNameAdd' class="form-control" datatype="*6-50" errormsg="请输入6-50个字符" />
		                <span class="Validform_checktip">请输入项目名称</span>
		             </div>
		             
		             <div >
		                <label>主持人</label>
		                <select id="medalTeacherIdAdd" class="selectpicker" data-live-search="true" data-size="10">
						</select>
						<span class="Validform_checktip">请选择主持人</span>   
		             </div>
		             
		              <div >
		                <label>项目类型</label>
		                <select id="medalTypeAdd" class="selectpicker" data-size="10">
		                	<option selected>教学成果奖</option>
							<option>科研成果奖</option>
						</select>
						<span class="Validform_checktip">请选择项目类型</span>   
		             </div>
		             
		             <div >
		                <label>成绩</label>
		                <select id="medalGradeAdd" class="selectpicker" data-size="10">
		                	<option selected>特等奖</option>
							<option>一等奖</option>
							<option>二等奖</option>
							<option>三等奖</option>
							<option>其他</option>
						</select>
						<span class="Validform_checktip">请选择成绩</span>   
		             </div>
		             
		            <div >
		                <label>级别</label>
		                <select id="medalLevelAdd" class="selectpicker" data-size="10">
		                	<option selected>国家级</option>
							<option>省部级</option>
							<option>市厅级</option>
							<option>校级</option>
							<option>横向</option>
						</select>
						<span class="Validform_checktip">请选择成绩</span>   
		             </div>
		
		             
		             <div >
		                <label>排名</label>
		                <input type="text" value="" id='rankingAdd' class="form-control" datatype="/^[1-9]$|^[123][0-9]$/" errormsg="请输入1或2位数字" />
		                <span class="Validform_checktip">请输入排名</span>
		             </div>
		             
		            <div class="controls input-append date form_date">
		                <label>获奖时间</label>
		                <input size="16" type="text" id="winningTimeAdd" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		                      <span class="add-on"><i class="icon-th"></i></span>    
		                <span class="Validform_checktip">请选择获奖时间</span>
		             </div> 
		             
		             <div class="isTopStyle">
		                <span class="isTopStyleSpan">是否主办方</span>
		                <span class="switch" data-animated="false"><input id="sponsorAdd" type="checkbox"/></span>
		             </div>

		             <div >
		                <label>备注</label>
						<textarea name="content" id="medalAbstractsAdd"></textarea>						
		             </div>
		             
		              <div >
					<label>图片</label> <input style="height:60px" type="hidden" id="photo_save" /><br/>
						<form id="form" method="post"  enctype="multipart/form-data" >  
						 <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >  
       					 <div id="photoUploader">上传照片</div>
   				 		</form>
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
    
    <!-- 查看模态框，单击增加按钮，弹出表单 -->
	<div class="modal fade" id="lookMedalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        科研成果信息
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body lookModal">
          	      	 

          	      	 <div >
		                <label class=" control-label" >项目名称</label>
		                <input type="text" class="form-control" id='medalNameLook'  />
		              </div> 
    
		             <div >
		                <label class=" control-label" >主持人</label>
		                <input type="text"  id='medalTeacherLook' class="form-control" />
		             </div>
             		        
		             <div >
		                <label class="control-label" >级别</label>
		                <input type="text" id='medalLevelLook' class="form-control"  />
		             </div>
		             
		              <div >
		                <label class=" control-label" >项目类型</label>
		                <input type="text" id='medalTypeLook' class="form-control"  />
		             </div>

		              <div >
                            <label  class="control-label">成绩</label>
                            <input type="text" id='medalGradeLook' class="form-control"/>
                       </div> 
                       
                       <div >
		                <label class="control-label" >排名</label>
		                <input type="text" id='medalRankingLook' class="form-control"  />
		             </div>
		             
		             <div >
		                <label class=" control-label" >获奖时间</label>
		                <input type="text" id='medalWinningTimeLook' class="form-control"  />
		             </div>
					
					<div >
                         <label   class="control-label">图片</label>
                         <div  id='medalPhotoLook' class="enclosuresShow">
                         </div>
                    </div> 
                    
                   <div>
                      <div >内容</div>
                         <div  id='medalAbstractsLook'  class="lookDescription"></div>     
                  </div>
                        
					
                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal" >关闭</button>
           		            		 	              		
           		</div>  <!-- modal-footer -->  
           		   
           		                 
            	</div><!-- /.modal-content -->
              
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->

<!-- 修改模态框，单击操作按钮，弹出表单  -->
	<div class="modal fade" id="updateMedalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px;">
        <form class="form-horizontal registerform" id="medalUpdateCheck" role="form">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改科研成果信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
                    
                   	<div>
		                <label>项目名称</label>
		                <input type="text" value="" id='medalNameUpdate' class="form-control" datatype="*6-50"/>   
		             </div>
		             
		             <div >
		                <label>主持人</label>
		                <select id="medalTeacherIdUpdate" class="selectpicker form-control" data-live-search="true" data-size="10">
						</select>  
		             </div>
		             
		              <div >
		                <label>项目类型</label>
		                <select id="medalTypeUpdate" class="selectpicker form-control" data-size="10">
		                	<option selected>教学成果奖</option>
							<option>科研成果奖</option>
						</select>  
		             </div>
		             
		             <div >
		                <label>成绩</label>
		                <select id="medalGradeUpdate" class="selectpicker form-control" data-size="10">
		                	<option selected>特等奖</option>
							<option>一等奖</option>
							<option>二等奖</option>
							<option>三等奖</option>
							<option>其他</option>
						</select> 
		             </div>
		             
		            <div >
		                <label>级别</label>
		                <select id="medalLevelUpdate" class="selectpicker form-control" data-size="10">
		                	<option selected>国家级</option>
							<option>省部级</option>
							<option>市厅级</option>
							<option>校级</option>
							<option>横向</option>
						</select>
		             </div>
					
		             
		             <div >
		                <label>排名</label>
		                <input type="text" value="" id='rankingUpdate' class="form-control" datatype="/^[1-9]$|^[123][0-9]$/"/>
		             </div>
		             
		            <div class="controls input-append date form_date">
		                <label>获奖时间</label>
		                <input size="16" type="text" id="winningTimeUpdate" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		                      <span class="add-on"><i class="icon-th"></i></span>    
		             </div>
		              
		             <div class="isTopStyle">
		                <span class="isTopStyleSpan">是否主办方</span>
		                <span class="switch" data-animated="false"><input id="sponsorUpdate" type="checkbox"/></span>
		             </div>
		             
		             <div >
		                <label>备注</label>
						<textarea name="content" id="medalAbstractsUpdate"></textarea>						
		             </div>
		             
		              <div>
                            <label>照片</label>
                            <div id="photoUploadUpdate" ></div>
                        </div>
 
                </div><!-- .modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" >关闭  
                    </button>
                    <button type="submit" id="medalSubmit_update" class="btn btn-primary">
                        提交
                    </button><span id="tip"> </span>
                </div>
            </div><!-- /.modal-content -->
             </form>
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
   

    <!-- 删除模态框（Modal） -->
	<div class="modal fade" id="deleteMedalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
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
					<button type="button" id="medalSubmit_delete" class="btn btn-danger">
						删除
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	
	
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ctx}/js/medal.js"></script>
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