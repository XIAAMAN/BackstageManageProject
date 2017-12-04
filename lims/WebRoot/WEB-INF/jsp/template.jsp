<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    <link rel="stylesheet" href="css/bootstrap.css"> 
	<link rel="stylesheet" href="css/bootstrap-table.min.css">
	<link rel='stylesheet' href='css/test.css'>
    
	<!-- bootstrap -->
    <link href="css/bootstrap-rewrite.css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />

    <!-- libraries -->
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
    
    <!-- style-override -->
    <link rel="stylesheet" type="text/css" href="css/template.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>

    <!-- navbar -->
    <div class="navbar">
        <div class="navbar-inner">
            <button type="button" class="btn btn-navbar visible-phone" id="menu-toggler">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
            <!-- navbar brand -->
            <a class="brand" href="#"><i class="icon-eye-open"></i> 科研信息管理系统</a>            
        </div>
    </div>
    <!-- end navbar -->

    <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">
        	<c:forEach items="${sessionScope.menu_session}" var="parent">
        		<li>
        			<c:choose>
        				<c:when test="${!empty parent.menus}">
        				<a href="${parent.url}" class="dropdown-toggle">
	        				<i class="${parent.icon}"></i>
	        				<span>${parent.name}</span>
	        				<i class="icon-chevron-down"></i>
        				</a>
        				<ul class="submenu">
						<c:forEach items="${parent.menus}" var="son">
							
							<li>
								
								<a href="${ctx}${son.url}?id=${son.id}&parentId=${son.parentId}" id="${son.id}" >
									<div class="${son.icon }"></div>
									<span style="margin-left:5px">${son.name }</span>
								</a>
								
							</li>
						</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<a href="${ctx}${parent.url}?id=${parent.id}&parentId=${parent.parentId}" id="${parent.id}">
	        				<i class="${parent.icon}"></i>
	        				<span>${parent.name}</span>
        				</a>
					</c:otherwise>
        			</c:choose>	
				</li>
			</c:forEach>
        </ul>
    </div>
    <!-- end sidebar -->


	<!-- main container -->
    <!-- end main container -->
    <!-- scripts for this page -->
    <script src="js/jquery-latest.js"></script>
    <script src="js/theme.js"></script>