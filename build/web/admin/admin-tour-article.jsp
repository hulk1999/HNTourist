<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	List<TourArticleDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
%>

<!DOCTYPE html>
<html>
<head>

	<title>Bài viết tour - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-tour-article.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			BÀI VIẾT TOUR<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

	<%@include file="/common/admin-left-column.jsp" %>

	<!-- ============================================================================================ -->

	<div id="main-content">
		
		<div class="info-group-title">BÀI VIẾT TOUR</div>

		<div id="search-add-container">
			<div id="search-form-container">
				<form action="../TourArticleMainController" method="POST">
					<input type="text" name="search" placeholder="Tìm kiếm..." value="<%= Parser.nullToEmpty(request.getParameter("search"))%>">
					<input type="hidden" name="action" value="search">
					<button type="submit"><i class="fas fa-search"></i></button>
				</form>
			</div>
			<a href="<%= request.getContextPath() %>/admin/tour-article-add"><div id="add-main-button">Thêm bài viết</div></a>
		</div>

		<%
			if (itemList.size() == 0) {
		%>
		<div class="not-found">Không tìm thấy bài viết!</div>
		<%
			} else {
		%>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Tiêu đề</th>
					<th>Thời gian</th>
					<th>Điểm xuất phát</th>
					<th>Điểm đến</th>
					<th>Lượt xem</th>
					<th>Sửa</th>
					<th>Xóa</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (TourArticleDTO dto : itemList) {
				%>
				<tr>
					<td><%= dto.getID() %></td>
					<td><a href="<%= request.getContextPath()%>/tour-article?id=<%= dto.getID() %>"><%= dto.getTitle() %></a></td>
					<td><%= dto.getDuration() %></td>
					<td><%= dto.getDeparture() %></td>
					<td><%= dto.getDestination() %></td>
					<td><%= dto.getViews() %></td>
					<%
					if (search == null){
						%>
						<td><a class="edit" href="<%= request.getContextPath()%>/admin/tour-article-edit?id=<%= dto.getID() %>">Sửa</a></td>
						<%
					} else{
						%>
						<td><a class="edit" href="<%= request.getContextPath()%>/admin/tour-article-edit?id=<%= dto.getID() %>&search=<%= search %>">Sửa</a></td>
						<%
					}
					%>
					<td>
						<form action="../TourArticleMainController" method="POST">
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