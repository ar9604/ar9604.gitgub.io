<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面1</title>

	<%--静态包base标签，css样式，jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		//页面加载后
		$(function (){

			$("#username").blur(function (){
				//1.获取用户名
				var value = this.value;
				$.getJSON("${basePath}userServlet","action=ajaxExistsUsername&username=" + value,function (data){
					if (data.existsUsername){
						$("span.errorMsg").text("用户名已存在!")
					}else {
						$("span.errorMsg").text("用户名可用!")

					}
				})
			});

			//给验证码的图片绑定单击事件
			$("#code_img").click(function (){
				//在事件相应的function函数中有一个正在相应的this对象，这个this对象是当前正在相应的dom对象
				//src属性表示验证码img标签的 图片路径。它可读，可写
				this.src="${basePath}kaptcha.jpg?id=" + new Date();
			});

			$("#sub_btn").click(function (){
				// 验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				var usernameTest = $("#username").val();
				var usernamePatt = /^\w{5,12}$/;
				if (!usernamePatt.test(usernameTest)){
					$("span.errorMsg").text("用户名不合法");
					return false;
				}

				// 验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				var passwordTest = $("#password").val();
				var passwordPatt = /^\w{5,12}$/;
				if (!passwordPatt.test(passwordTest)){
					$("span.errorMsg").text("密码不合法");
					return false;
				}

				// 验证确认密码：和密码相同var passwordTest = $("#password").val();
				var repwdTest = $("#repwd").val();
				if (repwdTest != passwordTest){
					$("span.errorMsg").text("第二次密码和第一次不一样");
					return false;/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/
				}
				$("span.errorMsg").text("");
				// 邮箱验证：xxxxx@xxx.com
				var emailTest = $("#email").val();
				var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
				if (!emailPatt.test(emailTest)){
					$("span.errorMsg").text("邮箱格式不合法");
					return false;
				}
				// 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
				var codeTest = $("#code").val();
				//去掉验证码前后空格
				var codeTest = $.trim(codeTest);

				if (codeTest == null || codeTest == ""){
					$("span.errorMsg").text("验证码不能为空！");
					return false;

				}

				$("span.errorMsg").text("");

			})

		})

	</script>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">
									${requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input name="action" type="hidden" value="register" />
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"
									value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
									value="${requestScope.email}"/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code"/>
									<img id="code_img" src="kaptcha.jpg" style="float: right; margin-right: 30px; width: 120px;height: 40px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%--静态包含页脚内容--%>
		<%@include file="/pages/common/fooster.jsp"%>
</body>
</html>