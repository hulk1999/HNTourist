<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	List<List<Object>> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
%>

<!DOCTYPE html>
<html>
<head>

	<title>Tour - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-tour.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			TOUR<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">

			<div class="info-group-title">MÃ TOUR</div>

			<div id="search-add-container">
				<div id="search-form-container">
					<form action="../TourMainController" method="POST">
						<input type="text" name="search" placeholder="Tìm kiếm..." value="<%= Parser.nullToEmpty(request.getParameter("search"))%>">
						<input type="hidden" name="action" value="search">
						<button type="submit"><i class="fas fa-search"></i></button>
					</form>
				</div>
			</div>
			
			<%
			if (itemList.size() == 0) {
				%>
				<div class="not-found">Không tìm thấy tour!</div>
				<%
			} else {
			%>
			<table>
				<thead>
					<tr>
						<th>Code</th>
						<th>Ngày khởi hành</th>
						<th>Điểm xuất phát</th>
						<th>Điểm đến</th>
						<th>Giá</th>
						<th>Tổng số chỗ</th>
						<th>Đã đặt</th>
						<th>Bài viết tour</th>
						<th>Sửa</th>
						<th>Xóa</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (List<Object> tmpObj : itemList) {
						TourDTO dtoIterator = (TourDTO) tmpObj.get(0);
					%>
					<tr>
						<td><a href="<%= request.getContextPath()%>/admin/tour-view?code=<%= dtoIterator.getCode() %>"><%= dtoIterator.getCode()%></a></td>
						<td><%= dtoIterator.getStartDate() %></td>
						<td><%= (String) tmpObj.get(1) %></td>
						<td><%= (String) tmpObj.get(2) %></td>
						<td><%= Parser.intToVNmeseCurrency(dtoIterator.getPrice()) %></td>
						<td><%= dtoIterator.getTotalSeats() %></td>
						<td><%= dtoIterator.getSeatsBooked() %></td>
						<td><a href="<%= request.getContextPath()%>/tour-article?id=<%= dtoIterator.getTourArticleID() %>"><%= dtoIterator.getTourArticleID() %></a></td>
						<%
						if (search == null){
							%>
							<td><a class="edit" href="<%= request.getContextPath()%>/admin/tour-edit?code=<%= dtoIterator.getCode() %>">Sửa</a></td>
							<%
						} else{
							%>
							<td><a class="edit" href="<%= request.getContextPath()%>/admin/tour-edit?code=<%= dtoIterator.getCode() %>&search=<%= search %>">Sửa</a></td>
							<%
						}
						%>
						<td>
							<form action="../TourMainController" method="POST">
								<input type="hidden" name="code" value="<%= dtoIterator.getCode() %>">
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