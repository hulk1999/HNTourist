<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	boolean admin = (boolean) session.getAttribute("admin");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Đổi mật khẩu - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-common.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-change-password.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			ĐỔI MẬT KHẨU<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">
        
		<%
			if (admin){
				%>
					<%@include file="/common/admin-left-column.jsp" %>
				<%
			} else{
				%>
					<%@include file="/common/user-left-column.jsp" %>
				<%
			}
		%>
		
        <div id="main-content">
        <form id="change-password-form" action="../AccountMainController" method="POST" onsubmit="return validateChangePassword()">
            <%
                String passwordPlaceholder = "Mật khẩu hiện tại (*)";
                String passwordClassInvalid = "";
				String newPasswordPlaceholder = "Mật khẩu mới (*)";
                String newPasswordClassInvalid = "";
				String error = request.getParameter("error");
                if (error != null){
					if (error.equals("wrong")){
                    passwordPlaceholder = "Sai mật khẩu!";
                    passwordClassInvalid = "form-invalid";
					} else{
						newPasswordPlaceholder = "Trùng với mật khẩu cũ!";
						newPasswordClassInvalid = "form-invalid";
					}
				}
            %>
                <div class="info-group">
                    <div class="info-group-title">ĐỔI MẬT KHẨU</div>
                    <input type="password" name="current-password" placeholder="<%= passwordPlaceholder %>" class="<%= passwordClassInvalid %>">
                    <input type="password" name="new-password" placeholder="<%= newPasswordPlaceholder %>" class="<%= newPasswordClassInvalid %>">
                    <input type="password" name="confirm-password" placeholder="Nhập lại mật khẩu mới (*)">
					<input type="hidden" name="action" value="change-password">
                    <input type="submit" value="Lưu">
                </div>
                <div class="info-group"></div>
        </form>
        </div>
	</div>

	<script>
		function validateChangePassword(){

	        var form = document.getElementById("change-password-form");
	        var password = form.elements[0];
	        var newPassword = form.elements[1];
	        var newPasswordConfirmation = form.elements[2];

			password.classList.remove("form-invalid");
			newPassword.classList.remove("form-invalid");
			newPasswordConfirmation.classList.remove("form-invalid");
			password.placeholder = "Mật khẩu hiện tại (*)";
			newPassword.placeholder = "Mật khẩu mới (*)";
			newPasswordConfirmation.placeholder = "Nhập lại mật khẩu mới (*)";
			
			var valid = true;
			if (newPassword.value.length <= 7){
				valid = false;
				newPassword.classList.add("form-invalid");
				newPassword.placeholder = "Mật khẩu tối thiểu 8 ký tự!";
			}
			if (newPassword.value !== newPasswordConfirmation.value){
				valid = false;
				newPasswordConfirmation.classList.add("form-invalid");
				newPasswordConfirmation.placeholder = "Mật khẩu không khớp!";
			}
			if (!valid){
				password.value="";
				newPassword.value = "";
				newPasswordConfirmation.value = "";
			}
			
			return valid;

		}
	</script>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>