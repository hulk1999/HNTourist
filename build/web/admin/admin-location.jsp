<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.LocationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	int mainPageShow = ((ListPack) request.getAttribute("listPack")).getPageShow();
	List<LocationDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
	List<List<Integer>> appearanceCountList = (List) request.getAttribute("appearanceCountList");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Địa điểm - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/admin-location.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			ĐỊA ĐIỂM<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">

			<div class="info-group-title">ĐỊA ĐIỂM</div>

			<div id="search-add-container">
				<div id="search-form-container">
					<form action="../LocationMainController" method="POST">
						<input type="text" name="search" placeholder="Tìm kiếm..." value="<%= Parser.nullToEmpty(request.getParameter("search"))%>">
						<input type="hidden" name="action" value="search">
						<button type="submit"><i class="fas fa-search"></i></button>
					</form>
				</div>
				<a href="<%= request.getContextPath()%>/admin/location-add"><div id="add-main-button">Thêm địa điểm</div></a>
			</div>

			<%
				if (itemList.size() == 0) {
			%>
			<div class="not-found">Không tìm thấy địa điểm!</div>
			<%
				} else {
			%>
			<table>
				<thead>
					<tr>
						<th>STT</th>
						<th>Trong nước / Nước ngoài</th>
						<th>Khu vực</th>
						<th>Địa điểm / Quốc gia</th>
						<th>Số bài viết</th>
						<th>Số tour
						<th>Xóa</th>
					</tr>
				</thead>
				<tbody>
					<%
					int count = (mainPageShow - 1) * 20;
					for (LocationDTO dto : itemList) {
						%>
						<tr>
							<td><%= ++count %></td>
							<td><%= dto.isForeign() ? "Nước ngoài" : "Trong nước" %></td>
							<td><%= dto.getRegion() %></td>
							<td><%= dto.getLocation() %></td>
							<td><%= appearanceCountList.get(count - (mainPageShow - 1)*20 - 1).get(0) %></td>
							<td><%= appearanceCountList.get(count - (mainPageShow - 1)*20 - 1).get(1) %></td>
							<td>
								<form action="../LocationMainController" method="POST">
									<input type="hidden" name="location" value="<%= dto.getLocation()%>">
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