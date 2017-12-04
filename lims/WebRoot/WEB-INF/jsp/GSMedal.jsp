<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx}/css/bootstrap.css"> 
<link rel="stylesheet" href="${ctx}/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/test.css">
<link rel="stylesheet" href="${ctx}/css/demo.css">
<link rel="stylesheet" href="${ctx}/css/uploadfile.css">
<link rel="stylesheet" href="${ctx}/css/style.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-switch.css">

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
					<h3 class="panel-title">
						指导学生成果
					</h3>
				</div>
				<div class="panel-body">
					<!-- 查询条件, 组件为input和button -->
					<div class="qeuryCondition form-inline">
						<div class="input-group queryStyle">
					        <span class="input-group-addon">项目名称</span>
					        <input type="text" class="form-control" placeholder="标题查询" id="name_list">
    					</div>
    			
    					
    						<div class="input-group queryStyle">
						        <span class="input-group-addon">主持人</span>
						        <select name="teacher_list" class="selectpicker form-control" id="teacher_list" data-live-search="true" data-size="10">
								</select>
        					</div> 

    					<div class="input-group queryStyle">
								<span class="input-group-addon">等级</span> 
								<select
									class="form-control selectpicker" id="level_list">
									<option selected>无</option>
									<option>国家级</option>
									<option>省部级</option>
									<option>市厅级</option>
									<option>校级</option>
									<option>横向</option>
								</select>
							</div>
    					
    					<div class="input-group queryStyle">
					        <span class="input-group-addon">成绩</span>

					        <select
									class="form-control selectpicker" id="grade_list">
									<option selected>无</option>
									<option>特等奖</option>
									<option>一等奖</option>
									<option>二等奖</option>
									<option>三等奖</option>
									<option>其他</option>
								</select>
    					</div>

        						
        				  
        				<!-- 查询按钮 -->
        				<button onclick="loadTable()" id="queryBtn" type="button" class="btn btn-primary queryBtn">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<!-- 增加按钮 -->
			        	<button onclick="clearWrongMsg()" id="noticeAddBtn" class="btn btn-default addBtn" data-toggle="modal" data-target="#addNoticeModal" >
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
				           		
				               	<th data-align="center" data-field="name">项目名称</th>
				               	<th data-align="center" data-field="teacher.name">主持人</th>
				                <th data-align="center" data-field="grade">成绩</th>
				                <th data-align="center" data-field="level">项目等级</th>
				                <th data-align="center" data-field="winningTime">获奖时间</th>
				              
				               <!-- <th data-align="center" data-field="enclosures.length">文件数量</th> --> 
				              
				               
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
	
	<!-- 查看模态框，单击增加按钮，弹出表单 -->
	<div class="modal fade" id="lookNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         <form class="registerform" action="" id="noticeAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        指导学生成果信息
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body lookModal">

          	      	 <div >
		                <label class=" control-label" >项目名称</label>
		                <input type="text" class="form-control" value="" id='name_look' />
		              </div> 

		             <div >
		                <label class=" control-label" >成绩</label>
		                <input type="text" value=""  data-toggle="tooltip" title=""  id='grade_look' class="form-control" />
		             </div>
   
		             <div >
		                <label class=" control-label" >等级</label>
		                <input type="text" value=""  id='level_look' class="form-control"  />
		            
		             </div>
		              <div >
		                <label class=" control-label" >主持人</label>
		                <input type="text" value=""  id='teacherName_look' class="form-control"  />
		              
		             </div>

		               <div >
		                <label class=" control-label" >全部学生</label>
		                <input type="text" value=""  id='studentAll_look' class="form-control"  />
		              
		             </div>
		                  
		            <div >
		                <label class=" control-label" >获奖时间</label>
		                <input type="text" value=""  id='winningTime_look' class="form-control"  />
		              
		             </div>

                   <div  >
                        
		              <label   class=" control-label" >附件</label>
		               <div id='enclosures_look'  class="enclosuresShow"></div>
   				   </div>
		          

                     <div>
                        <div >内容</div>
                        <div  id='abstracts_look'  class="lookDescription"></div>
                    </div>
   
                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal" >关闭</button>
           		            		 	              		
           		</div>  <!-- modal-footer -->  
           		   
           		                 
            	</div><!-- /.modal-content -->
               </form>
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
     
	
	
	
	
	
	
	<!-- 增加模态框，单击增加按钮，弹出表单 -->
	<div class="modal fade" id="addNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px;">
         <form class="registerform" action="" id="GSMedalAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加指导学生成果信息
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      	
          	       
           			 <div >
		                <label>项目名称</label>
		                <input type="text" value="" id='name_save' class="form-control" datatype="*6-50" errormsg="请输入6-50个字符"/>
		               <span class="Validform_checktip">请输入项目名称</span>
		             </div>
		             

		             
		             <div >
						       <label>主持人</label>
						        <select name="teacher_list" class="selectpicker form-control" id="teacher_save" data-live-search="true" data-size="10">
								</select>
								<span class="Validform_checktip">请选择主持人</span>
        			</div> 
        			

        			
        			
		             
        			
        			<div >
								<label>级别</label> 
								<select class="form-control selectpicker" id="level_save">
									<option>国家级</option>
									<option>省部级</option>
									<option>市厅级</option>
									<option>校级</option>
									<option>横向</option>
								</select>
								<span class="Validform_checktip">请选择项目级别</span>
							</div>
    					
    					<div >
					        <label  >成绩</label>

					        <select
									class="form-control selectpicker" id="grade_save">
									<option>特等奖</option>
									<option>一等奖</option>
									<option>二等奖</option>
									<option>三等奖</option>
									<option>其他</option>
								</select>
								<span class="Validform_checktip">请选择成绩</span>
    					</div>
		             
           
		            
	             
		             <div  >
		                <label>学生</label>
		                <input type="text" value="" id='studentAll_save' class="form-control" datatype="*2-10" errormsg="请输入2-10个字符"/>
		                <span class="Validform_checktip">请输入学生姓名</span>
		             </div>
		             
		           <div class="controls input-append date form_date">
		                <label>获奖时间</label>
		                <input size="16" type="text" id="winningTime_save" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		                      <span class="add-on"><i class="icon-th"></i></span>    
		                <span class="Validform_checktip">请选择获奖时间</span>
		           </div>

		
	                <div >
						<textarea id='abstracts_save' name="content" ></textarea>												
		            </div>
		             
		             
	
					
					

				 <form id="newsform" method="post"  enctype="multipart/form-data" >    
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <label>附件</label>
				        <div id="fileuploader">上传附件</div>
				    </form>	

                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
           		 	<button type="submit"  id="noticeSubmit_add" class="btn btn_sub btn-primary">添加</button>                		 	               		
           		</div>  <!-- modal-footer -->    
           		                   
            	</div><!-- /.modal-content -->
            </form>   
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
    
    
    <!-- 修改模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="updateNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px;">
        <form class="form-horizontal registerform" id="GSMedalModCheck" role="form">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改指导学生成果信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
                    
                   
                    
                   	 <div >
		                <label>项目名称</label>
		                <input type="text" value="" id='name_modal' class="form-control" datatype="*6-50"/>
		             </div>
		             

		             
		             <div>
						        <label>主持人</label>
						        <select name="teacher_list" class="selectpicker form-control" id="teacher_modal" data-live-search="true" data-size="10">
								</select>
        			</div> 
        			
        			<div >
								<label>级别</label> 
								<select
									class="form-control selectpicker" id="level_modal">
									<option>国家级</option>
									<option>省部级</option>
									<option>市厅级</option>
									<option>校级</option>
									<option>横向</option>
								</select>
							</div>
    					
    					<div >
					        <label>成绩</label>

					        <select
									class="form-control selectpicker" id="grade_modal">
									<option>特等奖</option>
									<option>一等奖</option>
									<option>二等奖</option>
									<option>三等奖</option>
									<option>其他</option>
								</select>
    					</div>
           
	
		             

	             
		             <div  >
		                <label>学生</label>
		                <input type="text" value="" id='studentAll_modal' class="form-control" datatype="*2-10" />
		             </div>
		             
		           <div class="controls input-append date form_date">
		                <label>获奖时间</label>
		                <input size="16" type="text" id="winningTime_modal" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		                <span class="add-on"><i class="icon-th"></i></span>    
		           </div>

		
	                <div >
						<textarea id='abstracts_modal' name="content" ></textarea>												
		            </div>

                        
                        <!-- 附件modal -->
						<div >
                            <div class="updateFileLabelStyle">附件</div>

                            <div  id="enclosures_modal">
 
                            </div>
   
                        </div>
                          <form id="newsform" method="post"  enctype="multipart/form-data" >    
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <div id="fileuploadermod">上传附件</div>
				      </form>
 
                </div><!-- .modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" >关闭  
                    </button>
                    <button type="submit" id="noticeSubmit_update" class="btn btn-primary">
                        提交
                    </button><span id="tip"> </span>
                </div>
            </div><!-- /.modal-content -->
             </form>
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
    
    
	
	

    <!-- 删除模态框（Modal） -->
	<div class="modal fade" id="deleteNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #d9534f; color: white;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						删除指导学生成果信息 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此指导学生成果信息？
				</div>
				<div class="modal-footer">
					<button type="button" id="noticeSubmit_delete" class="btn btn-danger">
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
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ctx}/js/GSMedal.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>	
	
<script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</body>
</html>
