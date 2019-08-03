<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>

		<title>Đăng nhập - Hung Nguyen Tourist</title>

		<%@include file="/common/head-include.jsp" %>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/login.css">

	</head>
	<body>

		<%@include file="/common/menu.jsp" %>

		<!-- ============================================================================================ -->

		<div id="background-container">
			<img src="<%= request.getContextPath()%>/images/login/background.jpg">
		</div>

		<div id="login-form-container">
			<div id="title">ĐĂNG NHẬP</div>
			<form id="login-form" action="AccountMainController" method="POST" onsubmit="checkSaveAccount();">
				<%
					String usernamePlaceHolder = "Tên đăng nhập";
					String classInvalid = "";
					if (request.getParameter("wrong") != null) {
						usernamePlaceHolder = "Sai tên đăng nhập hoặc mật khẩu!";
						classInvalid = "form-invalid";
					}
				%>
				<input type="text" placeholder="<%= usernamePlaceHolder%>" name="username" class="<%= classInvalid%>"><br>
				<input type="password" placeholder="Mật khẩu" name="password"><br>
				<label id="remember-password-container">
					<input type="checkbox" name="remember">
					<inline id="remember-password">Ghi nhớ</inline>
				</label>
				<input type="hidden" name="action" value="login">
				<input type="submit" value="ĐĂNG NHẬP">
				<div id="sign-up-container">
					<inline id="sign-up-text">Chưa có tài khoản?</inline>
					<a id="sign-up-link" href="<%= request.getContextPath()%>/sign-up">ĐĂNG KÝ</a>
				</div>
			</form>
		</div>

		<script>
			
			var username = document.getElementsByName("username")[0];
			var password = document.getElementsByName("password")[0];
			var remember = document.getElementsByName("remember")[0];
			
			if (localStorage.getItem("username") !== null){
				username.value = localStorage.getItem("username");
				password.value = localStorage.getItem("password");
				if (localStorage.getItem("username") !== "") remember.checked = true;
			}
			
			function checkSaveAccount(){
				if (remember.checked){
					localStorage.setItem("username", username.value);
					localStorage.setItem("password", password.value);
				} else{
					localStorage.setItem("username", "");
					localStorage.setItem("password", "");
				}
			}
			
		</script>
				
		<!-- ============================================================================================ -->

		<%@include file="/common/footer.jsp" %>

	</body>
</html>