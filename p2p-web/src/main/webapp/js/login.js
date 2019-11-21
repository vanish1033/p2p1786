var referrer = "";//登录后返回页面
referrer = document.referrer;
if (!referrer) {
	try {
		if (window.opener) {                
			// IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性              
			referrer = window.opener.location.href;
		}  
	} catch (e) {
	}
}

//按键盘Enter键即可登录
$(document).keyup(function(event){
	if(event.keyCode == 13){
		Login();
	}
});
//输入域失去焦点，将红色提示信息清除
$(document).ready(function() {
	$("#phone").blur(function() {  
		$("#showId").html("");
	});
	$("#loginPassword").blur(function() {  
		$("#showId").html("");
	});
	//loadStat();
});

//校验验证码
function checkCaptcha() {
    //获取用户输入的验证码
    var captcha = $.trim($("#captcha").val());
    var flag = false;

    if ("" == captcha){
        $("#showId").html("请输入验证码");
    }else{
        $.ajax({
            url:"loan/checkCaptcha",
            type:"post",
            data:"captcha=" + captcha,
            async:false,
            dataType:"json",
            success:function (jsonObject) {
                if (jsonObject.errorMessage == "OK") {
                    $("#showId").html("");
                    flag = true;
                }else {
                    $("#showId").html(jsonObject.errorMessage);
                    flag = false;
                }
            },
            error:function () {
                $("#showId").html("系统繁忙，请稍后重试...");
                flag = false;
            }
        });
    }

    if (!flag){
        return false;
    }

    return true;

}

//用户登录
function Login() {
	var phone = $("#phone").val();
	var loginPassword = $("#loginPassword").val();
	var captcha = $("#captcha").val();
	// if (phone == "" || phone == "请输入11位手机号码") {
	// 	$("#showId").html("请输入11位手机号码");
	// 	return false;
	// }
	// //判断手机号码合法性正则表达式
	// if (!/^1[1-9]\d{9}$/.test(phone)) {
	// 	$("#showId").html("请输入正确的手机号");
	// 	return false;
	// }
	if (loginPassword == "" || loginPassword == "请输入登录密码") {
		$("#showId").html("请输入登录密码");
		return false;
	}
	if (loginPassword.length<6 || loginPassword.length>16) {
		$("#showId").html("登录密码为6到16位");
		return false;
	}
	if (captcha=="" && $("#showCaptcha").css('display') == "block"){
		$("#showId").html("请输入图形验证码");
		return false;
	}

    $.ajax({
		url:"loan/login",
		type:"post",
		dataType:"json",
		data:{"phone":phone,"password":$.md5(loginPassword)},
		success:function (jsonObject) {
			if (jsonObject.errorMessage == "OK"){
				//登录成功之后，从哪来回哪去
				window.location.href = referrer;
			}else{
                $("#showId").html("用户名或密码错误");
			}
        },
		error:function () {
            $("#showId").html("系统错误，请稍后重试...");
        }
	});
	
}
function checkLoginPassword() {
    //获取登录密码
    var loginPassword = $.trim($("#loginPassword").val());

    if ("" == loginPassword) {
        $("#showId").html("请输入登录密码");
        return false;
    } else {
        $("#showId").html("");
    }
    return true;
}

function checkPhone() {
    // //获取用户的手机号码
    // var phone = $.trim($("#phone").val());
    //
    // if ("" == phone) {
    //     $("#showId").html("请输入手机号码");
    //     return false;
    // } else if(!/^1[1-9]\d{9}$/.test(phone)) {
    //     $("#showId").html("请输入正确的手机号码");
    //     return false;
    // } else {
    //     $("#showId").html("");
    // }

    return true;
}