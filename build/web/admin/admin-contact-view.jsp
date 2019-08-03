<%@page import="hungnp.dtos.ContactDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	ContactDTO dto = (ContactDTO) request.getAttribute("dto");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Thông tin liên hệ - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-contact-view.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			THÔNG TIN LIÊN HỆ<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">
			<div class="info-group-title">LIÊN HỆ - ID: <%= dto.getID() %></div>
			<div class="info-label">Họ tên</div>
			<div class="info-content"><%= Parser.nullToSpace(dto.getName()) %>&nbsp;</div>
			<div class="info-label">Email</div>
			<div class="info-content"><%= dto.getEmail() %></div>
			<div class="info-label">Số ĐT</div>
			<div class="info-content"><%= Parser.nullToSpace(dto.getPhone()) %></div>
			<div class="info-label">Địa chỉ</div>
			<div class="info-content"><%= Parser.nullToSpace(dto.getAddress()) %></div>
			<div class="info-label">Gửi lúc</div>
			<div class="info-content"><%= dto.getSentTime() %></div>
			<div class="info-label">Xử lí</div>
			<div class="info-content"><b><%= dto.isProcessed() ? "<inline style='color: green'>Đã xử lí</inline>" : "<inline style='color: red'>Chưa xử lí</inline>" %></b></div>
			<div class="info-label">Nội dung</div>
			<div class="info-content"><%= dto.getMessage() %></div>
		</div>

	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>