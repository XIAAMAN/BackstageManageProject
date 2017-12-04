<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx}/css/bootstrap.css"> 
<link rel="stylesheet" href="${ctx}/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${ctx}/css/test.css">
<link rel="stylesheet" href="${ctx}/css/demo.css">
<link rel="stylesheet" href="${ctx}/css/uploadfile.css">
<link rel="stylesheet" href="${ctx}/css/style.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-switch.css">

<link rel="stylesheet" href="${ctx}/css/animate.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">





<jsp:include page="template.jsp" flush="true"/><!--动态包含-->		

</head>
	<body>
		<!-- 页面主体 -->
	<div class="body row contents">
		<!--空一个位置-->
		
			<!-- 查询面板 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						公告数据
					</h3>
				</div>
				<div class="panel-body">
					<!-- 查询条件, 组件为input和button -->
					<div class="qeuryCondition form-inline">
						<div class="input-group queryStyle">
					        <span class="input-group-addon">标题</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="title_list">
    					</div>
    					
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">作者名</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="author_list">
        				</div>       		
        				  
        				<!-- 查询按钮 -->
        				<button onclick="loadTable()" id="queryBtn" type="button" class="btn btn-primary queryBtn">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<!-- 增加按钮 -->
			        	<button  id="noticeAddBtn" class="btn btn-default addBtn" data-toggle="modal" data-target="#addNoticeModal" onclick="clearWrongMsg()">
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
					           	data-page-list="[10,15,20, 50,100]"
					           	data-response-handler="responseHandler"
				           
				           >
				        <thead>
				           	<tr>
				           		<th data-align="center" data-field="id">编号</th>
				               	<th data-align="center" data-field="title">标题</th>
				                <th data-align="center" data-field="author">作者名</th>
				                <th data-align="center" data-field="publishTime">发布时间</th>
				                <th data-align="center" data-field="count">点击次数</th>
				               
				                <th data-align="center" data-field="seq" 
				                	data-formatter="btnOnOff"
				                	
				                	data-events="onOffactionEvents">是否置顶</th>
				               
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
	<div class="modal fade" id="addNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:800px">
         <form class="registerform" action="" id="noticeAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加公告
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      
          	       
           			 <div class="twoColsInput">
		                <label>标题</label>
		                <input type="text" value="" id='titleNotice-add' class="form-control" datatype="*6-20" placeholder="请输入6-20个字符" />		                
		             </div>
             
		             <div class="twoColsInput">
		                <label>作者名</label>
		                <input type="text" value="" id='authorNotice-add' class="form-control" datatype="*3-20" placeholder="请输入3-20个字符" />		                
		             </div>
		             </br>
					
					<!-- 文本编辑框 -->
		             <div >
		                <textarea id="contentNotice-add" name="content" ></textarea>
		             </div>	             
		             <div class="isTopStyle">
		                <span class="isTopStyleSpan">是否置顶</span>
		                <span class="switch" data-animated="false"><input id="seqNotice-add" type="checkbox"/></span>
		             </div>
		             
		          <form id="newsform" method="post" action="" enctype="multipart/form-data" >    
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <div id="fileuploader" >上传附件</div>
				    </form>

                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
           		 	<button type="submit" id="noticeSubmit_add" class="btn btn-primary">添加</button>                		 	               		
           		</div>  <!-- modal-footer -->    
           		                   
            	</div><!-- /.modal-content -->
            	
            </form>   
           
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
	
	
	<!-- 修改模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="updateNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="width:800px">
	<form class="registerform" action="" id="noticeUpdateCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改公告信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body smallUpdate">

                    	<!-- 标题modal -->
                           <div class="twoColsInput">
                           	   <label>标题</label>
                               <input type="text" class="form-control" id="title-modal" name="title-modal" value="" datatype="*6-20" placeholder="请输入6-20个字符">
                           </div>
                        
                        
						
						<!-- 作者modal -->
						
                        
                           <div class="twoColsInput">
                           		<label>作者名</label>
                               	<input type="text" class="form-control" name="author-modal" value="" id="author-modal" datatype="*2-20" placeholder="请输入2-20个字符">
                           </div>
                        
						
						<!--内容 modal -->
                        <div >
                        	<div>内容</div>
                            <textarea id="content-modal" name="contentUpdate" ></textarea>
                        </div>
                        
                      	<!--已有文件 -->
                       <div>
                       		<div class="updateFileLabelStyle">附件</div>
                            <div id="noticeUpdateFile">
                            </div>
                        </div>
                        
					<form id="updateNoticeform" method="post"  enctype="multipart/form-data" >    
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <div id="updatefileuploader">上传附件</div>
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
    
    <!-- 查看模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="lookNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="width:600px">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        查看公告信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body lookModal">

                    	<!-- 标题modal -->
                           <div>
                           	   <label>标题</label>
                               <input type="text" class="form-control" id="title-look" value="">
                           </div>
                           
						<!-- 作者modal -->
                           <div>
                           		<label>作者名</label>
                               	<input type="text" class="form-control" value="" id="author-look" >
                           </div>
                        
						
						<!--内容 modal -->
                        <div >
		                <label>内容</label>
						<div id="description-look" class="lookDescription"></div>						
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
	<div class="modal fade" id="deleteNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #d9534f; color: white;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						删除公告 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此公告？
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
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ctx}/js/notice.js"></script>
<script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>
<script type="text/javascript" src="${ctx}/js/loading.js"></script>
</body>
</html>
