<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.ContactDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	List<ContactDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
%>

<!DOCTYPE html>
<html>
<head>

	<title>Liên hệ - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-contact.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			LIÊN HỆ<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">

			<div class="info-group-title">LIÊN HỆ</div>

			<div id="search-add-container">
				<div id="search-form-container">
					<form action="../ContactMainController" method="POST">
						<input type="text" name="search" placeholder="Tìm kiếm..." value="<%= Parser.nullToEmpty(request.getParameter("search"))%>">
						<input type="hidden" name="action" value="search">
						<button type="submit"><i class="fas fa-search"></i></button>
					</form>
				</div>
			</div>
			
			<%
				if (itemList.size() == 0) {
			%>
			<div class="not-found">Không tìm thấy tài khoản!</div>
			<%
				} else {
			%>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Họ tên</th>
						<th>Nội dung</th>
						<th>Gửi lúc</th>
						<th>Đã xử lí</th>
						<th>Xóa</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (ContactDTO dto : itemList) {
					%>
					<tr>
						<td><a href="<%= request.getContextPath()%>/admin/contact-view?id=<%= dto.getID() %>"><%= dto.getID() %></a></td>
						<td><%= Parser.nullToEmpty(dto.getName()) %></td>
						<td><a href="<%= request.getContextPath()%>/admin/contact-view?id=<%= dto.getID() %>"><%= dto.getMessage() %></a></td>
						<td><%= dto.getSentTime() %></td>
						<td>
							<form action="../ContactMainController" method="POST">
								<input type="hidden" name="id" value="<%= dto.getID() %>">
								<input type="hidden" name="processed" value="<%= dto.isProcessed() %>">
								<%
								if (search != null){
									%>
									<input type="hidden" name="search" value="<%= search %>">
									<%
								}
								%>
								<input type="hidden" name="action" value="change-processed">
								<button type="submit"><%= dto.isProcessed() ? "<i style='color: green;' class='far fa-check-circle'></i>" : "<i style='color: red;' class='far fa-times-circle'></i>" %></button>
							</form>
						</td>
						<td>
							<form action="../ContactMainController" method="POST">
								<input type="hidden" name="id" value="<%= dto.getID()%>">
								<%
								if (search != null){
									%>
									<input type="hidden" name="search" value="<%= search %>">
									<%
								}
								%>
								<input type="hidden" name="action" value="delete">
								<input type="submit" value="Xóa" class="delete">
							</form>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			<%
				}
			%>
			
			<%@include file="/common/page-navigation.jsp" %>
			
		</div>

	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>