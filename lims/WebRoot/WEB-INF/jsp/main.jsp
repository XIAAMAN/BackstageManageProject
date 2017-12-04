<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lims首页</title>
<jsp:include page="template.jsp" flush="true"/><!--动态包含-->
</head>
<body>
<!-- 注释 -->
	<div class="contents">
			<center><h1>首页</h1>	</center>
		<h3>欢迎  <font color="red">${sessionScope.user_session.userName}</font> 登陆</h3>
		<c:forEach items="${sessionScope.menu_session}" var="parent">
			<p>parent: ${parent.name}</p>
			<c:forEach items="${parent.menus}" var="son">
				<p>  son: ${son.name}</p>
			</c:forEach>
		</c:forEach>
		

	</div>
	
</body>
</html>