<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.AccountDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	AccountDTO dto = (AccountDTO) request.getAttribute("dto");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Thay đổi thông tin - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-common.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-change-info.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			THAY ĐỔI THÔNG TIN<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">
		
		<%
			if (dto.isAdmin()){
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
        <form id="change-info-form" action="../AccountMainController" method="POST" onsubmit="return validateChangeInfo()">
			<div class="info-group">
            <div class="info-group-title">THÔNG TIN CÁ NHÂN</div>
            <input type="text" name="full-name" placeholder="Họ tên" value="<%= Parser.nullToEmpty(dto.getFullName()) %>">
            <input type="text" name="email" placeholder="Email" value="<%= Parser.nullToEmpty(dto.getEmail()) %>">
            <input type="text" name="phone" placeholder="Số ĐT" value="<%= Parser.nullToEmpty(dto.getPhone()) %>">
            <select name="gender">
                <%
                    if (dto.getGender() == null){
                        %>
                            <option value="" disabled selected hidden>Giới tính</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                        <%
                    } else{
                        %>
                            <option value="<%= dto.getGender() %>"><%= dto.getGender() %></option>
                            <option value="<%= dto.getGender().equals("Nam") ? "Nữ" : "Nam" %>"><%= dto.getGender().equals("Nam") ? "Nữ" : "Nam" %></option>
                        <%
                    }
                %>
            </select>
            <input type="text" name="birthdate" placeholder="Ngày sinh" value="<%= Parser.nullToEmpty(dto.getBirthdate()) %>">
            </div>
            <div class="info-group">
                    <div class="info-group-title">LIÊN HỆ</div>
                    <input type="text" name="address" placeholder="Địa chỉ" value="<%= Parser.nullToEmpty(dto.getAddress()) %>">
                    <input type="text" name="title" placeholder="Chức danh" value="<%= Parser.nullToEmpty(dto.getTitle()) %>">
                    <input type="text" name="agency" placeholder="Cơ quan" value="<%= Parser.nullToEmpty(dto.getAgency()) %>">
            </div>
            <input type="hidden" name="action" value="change-info">
            <input type="submit" value="Lưu">
        </form>
        </div>
	</div>
        
    <script src="<%= request.getContextPath() %>/js/moment.js"></script>
    <script>
    	function validateChangeInfo(){
    		var valid = true;
    		var form = document.getElementById("change-info-form");
    		var email = form.elements[1];
            var phone = form.elements[2];
            var birthdate = form.elements[4];
            if (email.value.length > 0){
                if (!email.value.match(/^[\w\.]+@\w+(\.\w+)+$/)){
                    valid = false;
                    email.value = "";
                    email.classList.add("form-invalid");
                    email.placeholder = "Email không hợp lệ!";
                }
            }
            if (phone.value.length > 0){
                if (!phone.value.match(/^[0-9]+$/)){
                    valid = false;
                    phone.value = "";
                    phone.classList.add("form-invalid");
                    phone.placeholder = "Số điện thoại không hợp lệ!";
                }
            }
            if (birthdate.value.length > 0){
                if (!moment(birthdate.value, "DD/MM/YYYY", true).isValid()){
                    valid = false;
                    birthdate.value = "";
                    birthdate.classList.add("form-invalid");
                    birthdate.placeholder = "Ngày sinh không hợp lệ!";
				}
            }
    		return valid;
    	}
    </script>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>