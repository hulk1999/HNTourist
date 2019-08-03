<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.AccountDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	AccountDTO dto = (AccountDTO) request.getAttribute("dto");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Hồ sơ - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-common.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-profile.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			HỒ SƠ<br>
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
			<div class="info-group">
				<div class="info-group-title">THÔNG TIN CÁ NHÂN</div>
				<div class="info-label">Họ tên</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getFullName()) %></div>
				<div class="info-label">Email</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getEmail()) %></div>
				<div class="info-label">Số ĐT</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getPhone()) %></div>
				<div class="info-label">Giới tính</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getGender()) %></div>
				<div class="info-label">Ngày sinh</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getBirthdate()) %></div>
			</div>
			<div class="info-group">
				<div class="info-group-title">LIÊN HỆ</div>
				<div class="info-label">Địa chỉ </div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getAddress()) %></div>
				<div class="info-label">Chức Danh </div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getTitle()) %></div>
				<div class="info-label">Cơ quan</div>
				<div class="info-content"><%= Parser.nullToSpace(dto.getAgency()) %></div>
			</div>
		</div>
		
	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>