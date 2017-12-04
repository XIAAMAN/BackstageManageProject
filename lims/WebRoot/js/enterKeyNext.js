/**
 * 回车键跳到下一个输入框
 */

$(function () {
	    $('input:text:first').focus();
	    var $inp = $('input:text');
	    $inp.bind('keydown', function (e) {           //回车切换焦点
	        var key = e.which; 
	        if (key == 13) {
	            e.preventDefault();                  //preventDefault() 方法阻止元素发生默认的行为
	            var nxtIdx = $inp.index(this) + 1;
	            $(":input:text:eq(" + nxtIdx + ")").focus();
	        }
	    });
	});