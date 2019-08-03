<%@page import="hungnp.dtos.BookingDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	String search = request.getParameter("search");
	List<BookingDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
%>

<!DOCTYPE html>
<html>
<head>

	<title>Đặt chỗ - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-list.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-booking.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			ĐẶT CHỖ<br>
			 ____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">

			<div class="info-group-title">ĐẶT CHỖ</div>

			<div id="search-add-container">
				<div id="search-form-container">
					<form action="../BookingMainController" method="POST">
						<input type="text" name="search" placeholder="Tìm kiếm..." value="<%= Parser.nullToEmpty(request.getParameter("search"))%>">
						<input type="hidden" name="action" value="search">
						<button type="submit"><i class="fas fa-search"></i></button>
					</form>
				</div>
			</div>
			
			<%
				if (itemList.size() == 0) {
			%>
			<div class="not-found">Không tìm thấy đặt chỗ!</div>
			<%
				} else {
			%>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Mã tour</th>
						<th>Thanh toán</th>
						<th>Họ tên</th>
						<th>Người lớn</th>
						<th>Trẻ em</th>
						<th>Em bé</th>
						<th>Trạng thái</th>
						<th>Lưu</th>
						<th>Xóa</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (BookingDTO dto : itemList) {
					%>
					<tr>
						<td><a href="<%= request.getContextPath()%>/admin/booking-view?id=<%= dto.getId() %>"><%= dto.getId() %></a></td>
						<td><a href="<%= request.getContextPath()%>/admin/tour-view?code=<%= dto.getTourCode() %>"><%= dto.getTourCode() %></a></td>
						<td><%= dto.getPaymentMethod() %></td>
						<td><%= dto.getContactName() %></td>
						<td><%= dto.getAdultNumber() %></td>
						<td><%= dto.getChildNumber() %></td>
						<td><%= dto.getBabyNumber() %></td>
						<form action="../BookingMainController" method="POST">
							<td>
								<select name="status" style="color: white; background-color: <%= Parser.statusToColor(dto.getStatus()) %>">
									<option style="background-color: orange;" value="Chưa thanh toán" <%= dto.getStatus().equals("Chưa thanh toán") ? "selected" : "" %>>Chưa thanh toán</option>
									<option style="background-color: green;" value="Đã thanh toán" <%= dto.getStatus().equals("Đã thanh toán") ? "selected" : "" %>>Đã thanh toán</option>
									<option style="background-color: red;" value="Hết hạn" <%= dto.getStatus().equals("Hết hạn") ? "selected" : "" %>>Hết hạn</option>
									<option style="background-color: gray;" value="Hủy" <%= dto.getStatus().equals("Hủy") ? "selected" : "" %>>Hủy</option>
								</select>
							</td>
							<td>
								<input type="hidden" name="id" value="<%= dto.getId() %>">
								<input type="hidden" name="tourCode" value="<%= dto.getTourCode() %>">
								<input type="hidden" name="totalSeats" value="<%= dto.getAdultNumber() + dto.getChildNumber() + dto.getBabyNumber() %>">
								<input type="hidden" name="currentStatus" value="<%= dto.getStatus() %>">
								<input type="hidden" name="action" value="change-status">
								<input type="submit" value="Lưu" class="edit">
							</td>
						</form>
						<td>
							<form action="../BookingMainController" method="POST">
								<input type="hidden" name="id" value="<%= dto.getId() %>">
								<%
								if (search != null){
									%>
									<input type="hidden" name="search" value="<%= search %>">
									<%
								}
								%>
								<input type="hidden" name="tourCode" value="<%= dto.getTourCode() %>">
								<input type="hidden" name="totalSeats" value="<%= dto.getAdultNumber() + dto.getChildNumber() + dto.getBabyNumber() %>">
								<input type="hidden" name="currentStatus" value="<%= dto.getStatus() %>">
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

	<script>
		<%
		if (request.getParameter("invalid") != null){
			%>
			alert("Không thể thay đổi trạng thái từ Hết hạn hoặc Hủy!");
			<%
		}
		%>
	</script>
			
	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>