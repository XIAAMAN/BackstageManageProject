$(function () {

  // sidebar menu dropdown toggle
  $("#dashboard-menu .dropdown-toggle").click(function (e) {
    e.preventDefault();
    var $item = $(this).parent();
    $item.toggleClass("active");
    if ($item.hasClass("active")) {
      $item.find(".submenu").slideDown("fast");
    } else {
      $item.find(".submenu").slideUp("fast");
    }
  }); 
  
  // sidebar click——侧边栏菜单项点击
  	var url=window.location.href;	  //获取当前页面的url
	var len=url.length;   //获取url的长度值
	var b=url.split("?")[1]; //获取参数内容 
	var var1=b.split("&")[0];   //获取参数的值
	var var2=b.split("&")[1]; 
	var id="#" + var1.split("=")[1];
	var parentId=var2.split("=")[1];
	var pointer = '<div id="pointer" class="pointer">' + 
	  '<div class="arrow"></div>' +
	  '<div class="arrow_border"></div>' +
	  '</div>';         // 菜单激活时改编箭头的位置
	// 先遍历所有菜单的状态，并取消其他箭头时激活时的状态
	$("#pointer").remove();
	$(".active").removeClass("active");
	if(parentId==0) {
		$(id).parent().addClass("active");
		$(id).after(pointer);
	}
	else {
		$(id).parent().parent().addClass("active");
		$(id).parent().parent().parent().addClass("active");
		$(id).parent().siblings().prepend(pointer);	
		// 改变菜单链接背景颜色
		$(id).parent().css({"background-color":"#337ab7","color":"white"});
		$(id).css("color","white");
	}
	
  
});