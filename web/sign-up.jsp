<%@page import="hungnp.support.Parser"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>

	<title>Đăng ký - Hung Nguyen Tourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/sign-up.css">

</head>
<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="background-container">
		<img src="<%= request.getContextPath() %>/images/login/background.jpg">
	</div>

	<div id="sign-up-form-container">
		<div id="title">ĐĂNG KÝ</div>
		<form id="sign-up-form" action="AccountMainController" method="POST" onsubmit="return validateSignUp()">
			<%
				String usernamePlaceHolder = "Tên đăng nhập (*)";
				String emailValue = "";
				String classInvalid = "";
				if (request.getParameter("duplicate") != null){
					usernamePlaceHolder = "Tên đăng nhập đã tồn tại!";
					emailValue = request.getParameter("email");
					classInvalid = "form-invalid";
				}
			%>
			<input type="text" placeholder="<%= usernamePlaceHolder %>" name="username" class="<%= classInvalid %>"><br>
			<input type="text" placeholder="Email" name="email" value="<%= Parser.nullToEmpty(emailValue) %>"><br>
			<input type="password" placeholder="Mật khẩu (*)" name="password"><br>
			<input type="password" placeholder="Nhập lại mật khẩu (*)" name="password-re-entered"><br>
			<input type="hidden" name="action" value="sign-up">
			<input type="submit" value="ĐĂNG KÝ">
			<div id="login-container">
				<inline id="login-text">Đã có tài khoản?</inline>
				<a id="login-link" href="<%= request.getContextPath() %>/login.jsp">ĐĂNG NHẬP</a>
			</div>
		</form>
	</div>
	
	<script>
		function validateSignUp(){

			var form = document.getElementById("sign-up-form");
			var username = form.elements[0];
			var email = form.elements[1];
			var password = form.elements[2];
			var passwordReEntered = form.elements[3];

			password.classList.remove("form-invalid");
			passwordReEntered.classList.remove("form-invalid");
			password.placeholder = "Mật khẩu (*)";
			passwordReEntered.placeholder = "Nhập lại mật khẩu (*)";
			
			var valid = true;
			if (username.value.match(/\\* \\*/)){
				valid = false;
				username.value = "";
				username.classList.add("form-invalid");
				username.placeholder = "Tên không chứa khoảng trắng!";
			}
			if (username.value.length === 0){
				valid = false;
				username.value = "";
				username.classList.add("form-invalid");
				username.placeholder = "Tên không được để trống!";
			}
			if (username.value.length > 20){
				valid = false;
				username.value = "";
				username.classList.add("form-invalid");
				username.placeholder = "Tên không được quá 20 ký tự!";
			}
            if (email.value.length > 0){
                if (!email.value.match(/^[\w\.]+@\w+(\.\w+)+$/)){
                        valid = false;
                        email.value = "";
                        email.classList.add("form-invalid");
                        email.placeholder = "Email không hợp lệ!";
                }
            }
			if (password.value.length <= 7){
				valid = false;
				password.classList.add("form-invalid");
				password.placeholder = "Mật khẩu tối thiểu 8 ký tự!";
			}
			if (password.value !== passwordReEntered.value){
				valid = false;
				passwordReEntered.classList.add("form-invalid");
				passwordReEntered.placeholder = "Mật khẩu không khớp!";
			}
			if (!valid){
				password.value = "";
				passwordReEntered.value = "";
			}

			return valid;
		
		}
	</script>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>