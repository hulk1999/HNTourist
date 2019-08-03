<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.AccountDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	List<AccountDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
%>

<!DOCTYPE html>
<html>
<head>

	<title>Tài khoản - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-account.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			TÀI KHOẢN<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">

			<div class="info-group-title">TÀI KHOẢN</div>

			<div id="search-add-container">
				<div id="search-form-container">
					<form action="../AccountMainController" method="POST">
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
						<th>Tên tài khoản</th>
						<th>Họ tên</th>
						<th>Giới tính</th>
						<th>Admin</th>
						<th>Ngày tạo</th>
						<th>Xóa</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (AccountDTO dto : itemList) {
					%>
					<tr>
						<td><a href="<%= request.getContextPath()%>/admin/account-view?username=<%= dto.getUsername()%>"><%= dto.getUsername()%></a></td>
						<td><%= Parser.nullToEmpty(dto.getFullName()) %></td>
						<td><%= Parser.nullToEmpty(dto.getGender()) %></td>
						<td <%= dto.isAdmin() ? "class='admin'" : "" %>>
							<%= dto.isAdmin() ? "Admin" : "Khách hàng" %>
							<form id="change-role-form" action="../AccountMainController" method="POST">
								<input type="hidden" name="username" value="<%= dto.getUsername()%>">
								<input type="hidden" name="admin" value="<%= dto.isAdmin() %>">
								<%
								if (search != null){
									%>
									<input type="hidden" name="search" value="<%= search %>">
									<%
								}
								%>
								<input type="hidden" name="action" value="change-role">
								<button type="submit"><i class="fas fa-exchange-alt"></i></button>
							</form>
						</td>
						<td><%= dto.getCreated() %></td>
						<td>
							<form action="../AccountMainController" method="POST">
								<input type="hidden" name="username" value="<%= dto.getUsername()%>">
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