<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE HTML>
<html>
  <head>
     <meta charset="utf-8"> 
     <meta name="viewport" content="width=device-width, initial-scale=1.0">      
     
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

     


	 
	 
     <link href="${ctx}/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
     
     <jsp:include page="template.jsp" flush="true"/><!--动态包含-->
  </head>
  
  <body>
  
   <!-- 中间表格表格 -->
		<div class="contents">
			<!-- 查询面板 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						论文
					</h3>
				</div>
				<div class="panel-body">
					<!-- 查询条件, 组件为input和button -->
					<div class="qeuryCondition form-inline">
						<div class="input-group queryStyle">
					        <span class="input-group-addon">标题</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="papername_list">
    					</div>
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">作者</span>
					        <input type="text" class="form-control" placeholder="关键字查询" id="paperauthor_list">
        				</div>
        				
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">索引类型</span>
					        <select name="selectTeacher" class="selectpicker form-control" id="selectindexType" data-size="10" multiple="multiple">
					        	 <option selected>无</option>
								 <option>SCI</option>
			                	 <option>EI</option>
			                	 <option>CSCI</option>
			                	 <option>ISTP</option>
			                	 <option>CPCI</option>
							</select>
        				</div>
        				
        				<div class="input-group  queryStyle">
					        <span class="input-group-addon">论文类型</span>
					        <select name="selectTeacher" class="selectpicker form-control" id="selectPaperType" data-size="10">
					        	 <option selected>无</option>
								 <option>JA</option>
			                	 <option>CA</option>
			                	 <option>JG</option>
							</select>
        				</div>		
						<!-- <div class="input-group  queryStyle">
					        <span class="input-group-addon">发表时间</span>
					        <select sytle="" name="selectStartYear" class="selectpicker form-control" id="selectPublishTime" data-size="10"></select>
    					</div>	 -->
        				

        				<!-- 查询按钮 -->
        				<button onclick="loadTable()" id="queryBtn" type="button" class="btn btn-primary queryBtn">
			          		<span class="glyphicon glyphicon-search"></span> 查询
			        	</button>
			        	<button id="addBtn" class="btn btn-default addBtn" data-toggle="modal" onclick="clearWrongMsg()" data-target="#addPaperModal">
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
				               	<th data-align="center" data-field="name">标题</th>
				                <th data-align="center" data-field="author" data-formatter="authorFormatter">作者(全)</th>
				                <!--<th data-align="center" data-field="authorAll">其他作者</th> -->
				                <th data-align="center" data-field="authorComm">通讯作者</th>
				                <th data-align="center" data-field="publishTime">发表时间</th>
				                <th data-align="center" data-field="paperType">论文类型</th>
				                <th data-align="center" data-field="indexType">索引类型</th>
				                <th data-align="center" data-field="journal">期刊名称</th>
				                <th data-align="center" data-field="vol">卷数</th>
				             <!--  <th data-align="center" data-field="abstracts">摘要</th> -->
				                
				                <th data-align="center" data-field="operation" 
						        	data-formatter="actionFormatter" data-width="25%"
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
	<div class="modal fade" id="addPaperModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
         <form class="registerform" action="" id="paperAddCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        增加论文
                    </h4>
               </div><!-- .modal-header -->
               
               <div class="modal-body addModal">
          	      
          	       
           			 <div >
		                <label>标题</label>
		                <input type="text" value="" id='paperName-add' class="form-control" datatype="*3-50" errormsg="请输入3-50个字符" />
		                <span class="Validform_checktip">请输入标题名称</span>
		             </div>
             
		             <div >
		                <label>作者名</label>
		                <input type="text" value="" id='paperAuthor-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入作者名</span>
		             </div>
		             
		             <div >
		                <label>其他作者</label>
		                <input type="text" value="" id='paperAuthorAll-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入其他作者</span>
		             </div>
		             
		             <div >
		                <label>通讯作者</label>
		                <input type="text" value="" id='paperAuthorComm-add' class="form-control" datatype="*2-20" errormsg="请输入2-20个字符" />
		                <span class="Validform_checktip">请输入通讯作者</span>
		             </div> 
		              
		              <div class="controls input-append date form_date">
		                <label>发表时间</label>
		                <input size="16" type="text" id="paperPublishTime-add" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <span class="add-on"><i class="icon-th"></i></span>
					    
		                <span class="Validform_checktip">请选择发表时间</span>
		             </div> 
		             
        			 <div>
		                <label >论文类型</label>
		                <select id="paperType-add" class="selectpicker form-control" data-size="10" style="width:300px">
		               	 	<option value="JA">JA</option>
		                	<option value="CA">CA</option>
		                	<option value="JG">JG</option>
						</select>
		                <span class="Validform_checktip">请选择论文类型</span>
		             </div>
		             
		            
		              <div class="paperUpdatecheckBox">
		              	<label>索引类型</label>
							<input type="checkbox" name="checkbox1" value="SCI" class="paperType-checkbox"/>SCI
							<input type="checkbox" name="checkbox1" value="EI"  class="paperType-checkbox"/>EI
							<input type="checkbox" name="checkbox1" value="CSCI" class="paperType-checkbox"/> CSCI
							<input type="checkbox" name="checkbox1" value="ISTP" class="paperType-checkbox"/> ISTP
							<input type="checkbox" name="checkbox1" value="CPCI" class="paperType-checkbox"/> CPCI		
						<span class="Validform_checktip">请选择索引类型</span>			
					 </div>
		             
		             <div >
		                <label>期刊名称</label>
		                <input type="text" value="" id='paperJournal-add' class="form-control" datatype="*3-20" errormsg="最大20个字符" />
		                <span class="Validform_checktip">请输入期刊名称</span>
		             </div>
		             
		             <div >
		                <label>卷数</label>
		                <input type="text" value="" id='paperVol-add' class="form-control" datatype="*1-20" errormsg="最多20个字符" />
		                <span class="Validform_checktip">请输入卷数</span>
		             </div>
		             
					
		             <div >
		                <label>论文摘要</label>
						<textarea name="content" id="paperAbstracts-add"></textarea>					
		             </div>
		             
		              <form id="newsform" method="post"  enctype="multipart/form-data" >   
		              <div> 
		                <label>附件</label>
				        <input type="hidden" name="columnId" value="${columnId }">
				        <input type="hidden" name="state" id="state" >
				        <div id="fileuploader">上传附件</div>
				        </div>
				      </form>
		             

                </div>    <!-- modal-body -->    
                   
                <div class="modal-footer">                		 	
       		 		<button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
           		 	<button type="submit" id="paperSubmit_add"  class="btn btn_sub btn-primary">添加</button>                		 	               		
           		</div>  <!-- modal-footer -->    
           		                   
            	</div><!-- /.modal-content -->
            </form>   
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
    
    <!-- 查看模态框，单击操作按钮，弹出表单 -->
	<div class="modal fade" id="lookPaperModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #428bca; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
           				 查看论文信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body lookModal">
                    <form class="form-horizontal" role="form">
                    
                    	<!-- 标题modal -->
                        <div>
                           <label>标题</label>
                           <input type="text" class="form-control" id="paperName-look" name="paperName-look" value=""
                                  placeholder="请输入论文名称">                           
                        </div>
                        
						<!-- 作者 modal -->
                        <div>
                            <label>作者</label>                            
                            <input type="text" class="form-control" name="paperAuthor-look" value="" id="paperAuthor-look"
                                   placeholder="请输入作者名">                        
                        </div>
						
						<!-- 通讯作者 modal -->
						<div>
                            <label>通讯作者</label>                           
                            <input type="text" class="form-control" name="paperAuthorComm-look" value="" id="paperAuthorComm-look"
                                   placeholder="请输入通讯作者">                          
                        </div>
						
						<!-- 发表时间 modal -->
						<div>
                            <label>发表时间</label>
                            <input type="text" class="form-control" name="paperPublishTime-look" value="" id="paperPublishTime-look"
                                   placeholder="请输入发表时间">                          
                        </div>
                        
						<!-- 论文类型 modal -->
                        <div>
                            <label>论文类型</label>      
                            <input  type="text" class="form-control"  name="paperType-look" value="" id="paperType-look"
                                   placeholder="请输入论文类型">                           
                        </div>
                        
                        <!--索引类型 modal -->
                        <div>
                           <label>索引类型</label>                           
                           <input type="text" class="form-control" name="paperIndexType-look" value="" id="paperIndexType-look"
                                  placeholder="请输入索引类型">          
                        </div>
                        
                        <!--期刊名称  modal -->
                        <div>
                            <label>期刊名称</label>                          
                            <input type="text" class="form-control" name="paperJournal-look" value="" id="paperJournal-look"
                                   placeholder="请输入期刊名称">                           
                        </div>
                        
                        <!--卷数 modal -->
                        <div>
                            <label>卷数</label>                        
                            <input type="text" class="form-control" name="paperVol-look" value="" id="paperVol-look"
                                   placeholder="请输入卷数">                            
                        </div>
                       
                        <!-- 摘要modal -->
                        <div>
		                	<label>摘要</label>
		                	<div id="paperAbstracts-look" class="lookDescription" ></div>
		                </div> 
		                
		                <!--附件 modal -->
                        <div>
                            <label>附件</label>
                            <div id="paperFiles-look"></div>                            
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
	<div class="modal fade" id="updatePaperModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" 
	aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:600px">
        <form class="registerform" action="" id="paperUpdateCheck">
            <div class="modal-content">
                <div class="modal-header" style="background-color:  #F0AD4E; color: white;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                       	 修改论文信息
                    </h4>
                </div><!-- .modal-header -->
                <div class="modal-body updateModal">
                    <form class="form-horizontal" role="form">
  						   
                    	<!-- 标题modal -->
                        <div>
                            <label>标题</label>                          
                            <input type="text" class="form-control" id="paperName-modal" name="paperName-modal" value="" datatype="*3-50">                          
                        </div>
                        
						
						<!-- 作者modal -->
						<div>
                            <label>作者名</label>
                            <input type="text" class="form-control" name="paperAuthor-modal" value="" id="paperAuthor-modal" datatype="*2-20">                            
                        </div>
						
						<!--通讯作者 modal -->
                        <div>
                            <label>通讯作者</label>                            
                            <input type="text" class="form-control" name="paperAuthorComm-modal" value="" id="paperAuthorComm-modal" datatype="*2-20">                           
                        </div> 
                        
                        <!--其他作者 modal -->
                        <div>
                            <label>其他作者</label>                            
                            <input type="text" class="form-control" name="paperAuthorAll-modal" value="" id="paperAuthorAll-modal" datatype="*2-20">                            
                        </div> 
                        
                        <div>			                
			                <div class="input-append date form_date">
			                	<label>发表时间</label>
				                <input size="16" type="text" value="2017-10-15" id="paperPublishTime-modal" class="form-control" data-date=""   data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		                        <span class="add-on"><i class="icon-remove"></i></span>
							    <span class="add-on"><i class="icon-th"></i></span>
			                </div>
		                </div> 
                        
                        <div>
		                	<label>论文类型</label>		                
			                <select id="paperType-modal" class="selectpicker form-control" >
			               	 	<option value="JA">JA</option>
			                	<option value="CA">CA</option>
			                	<option value="JG">JG</option>
							</select>						
		             </div>
                        
                        <div class="paperUpdatecheckBox">
		              		<label>索引类型</label>
							<input type="checkbox" name="checkbox2" value="SCI" class="paperType-checkbox"/>SCI
							<input type="checkbox" name="checkbox2" value="EI"  class="paperType-checkbox"/>EI
							<input type="checkbox" name="checkbox2" value="CSCI" class="paperType-checkbox"/> CSCI
							<input type="checkbox" name="checkbox2" value="ISTP" class="paperType-checkbox"/> ISTP
							<input type="checkbox" name="checkbox2" value="CPCI" class="paperType-checkbox"/> CPCI			
					    </div>
					 
                        <!--期刊名称  modal -->
                        <div>
                            <label>期刊名称</label>
                            <input type="text" class="form-control" name="paperJournal-modal" value="" id="paperJournal-modal" datatype="*3-20">
                        </div>
                        
                        <!--卷数 modal -->
                        <div>
                            <label>卷数</label>                            
                            <input type="text" class="form-control" name="paperVol-modal" value="" id="paperVol-modal" datatype="*1-20"/>                           
                        </div>
                       
                        <!-- 摘要modal -->
                        <div>
	                	    <div>摘要</div>          	
	               	        <textarea id="paperAbstracts-modal" name="content"></textarea>                 		                       	 
		                </div>  
		               
		               <!--附件 modal -->
                        <div>
                            <div>附件</div>
                            <div id="paperFiles-modal">
                            </div>
                        </div>
                        
                        <form id="newsform" method="post"  enctype="multipart/form-data" >    
					        <input type="hidden" name="columnId" value="${columnId }">
					        <input type="hidden" name="state" id="state" >
					        <div id="fileuploader-model">上传附件</div>
				      	</form>
                                     
                    </form>
                </div><!-- .modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick=clearChecked()>关闭  </button>
                    <button type="submit" id="papersubmit_update" class="btn btn-primary">提交</button>
                    <span id="tip"> </span>
                </div>
            </div><!-- /.modal-content -->
            </form>
        </div><!-- /.modal-dialog -->
    </div><!--.modal-->
    
     <!-- 删除模态框（Modal） -->
	<div class="modal fade" id="deletePaperModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #d9534f; color: white;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						删除论文 <span class="glyphicon glyphicon-exclamation-sign"></span>
					</h4>
				</div>
				<div class="modal-body">
					是否确认删除此论文？
				</div>
				<div class="modal-footer">
					<button type="button" id="paperSubmit_delete" class="btn btn-danger">
						删除
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div>
	</div><!-- /.modal -->
    
    
  <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
  <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-select.js"></script>
  <script type="text/javascript" src="${ctx}/js/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="${ctx}/js/bootstrap-table-zh-CN.js"></script>
  <script type="text/javascript" src="${ctx}/js/jquery.uploadfile.js"></script>

  <script type="text/javascript" src="${ctx}/js/paper.js"></script>
  <script type="text/javascript" src="${ctx}/js/enterKeyNext.js"></script>
  
  <script type="text/javascript" src="${ctx}/js/Validform_v5.3.2.js"></script>
  <script type="text/javascript" src="${ctx}/kindEditor/kindeditor.js"></script>
  <script type="text/javascript" src="${ctx}/kindEditor/lang/zh_CN.js"></script>
  <script type="text/javascript" src="${ctx}/js/loading.js"></script>
 
  
  <script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
  <script type="text/javascript" src="${ctx}/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
  </body>
</html>
