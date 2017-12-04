<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>用户登录</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/loginCSS/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/loginCSS/demo.css" />
	<!--必要样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/loginCSS/component.css" />
	<!--[if IE]>
		<script src="http://libs.baidu.com/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
</head>
<body>
	<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>欢迎您</h3>
						<form action="login" method="post">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="username" class="text" id="user" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="password" id="pass" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="mb2"><button class="act-but submit" style="color: #FFFFFF">登录</button></div>
						</form>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		
		<script src="${ctx}/loginJS/TweenLite.min.js"></script>
		<script src="${ctx}/loginJS/EasePack.min.js"></script>
		<script src="${ctx}/loginJS/rAF.js"></script>
		<script src="${ctx}/loginJS/demo-1.js"></script>
</body>
</html>