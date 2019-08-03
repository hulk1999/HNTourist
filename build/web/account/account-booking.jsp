<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.dtos.BookingDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	boolean isAdmin = (boolean) session.getAttribute("admin");
	List<BookingDTO> bookingList = (List) request.getAttribute("bookingList");
	List<TourDTO> tourList = (List) request.getAttribute("tourList");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Đặt chỗ của tôi - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-common.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/account-booking.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			ĐẶT CHỖ CỦA TÔI<br>
			     ____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">
		
		<%
			if (isAdmin){
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
			
			<div class="info-group-title">ĐẶT CHỖ CỦA TÔI</div>
			
			<%
			if (bookingList.size() == 0){
				%>
				<div style="color: red;">Bạn chưa có đặt chỗ nào!</div>
				<%
			} else{
				%>
				<table>
					<thead>
						<tr>
							<th>Mã đặt chỗ</th>
							<th>Mã tour</th>
							<th>Ngày hết hạn</th>
							<th>Tổng số chỗ</th>
							<th>Tổng tiền</th>
							<th>Trạng thái</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (int i = 0; i <= bookingList.size() - 1; i++){
							BookingDTO bookingDto = bookingList.get(i);
							TourDTO tourDto = tourList.get(i);
							%>
							<tr>
								<td><a href="<%= request.getContextPath() %>/account/booking-view?id=<%= bookingDto.getId() %>"><%= bookingDto.getId() %></a></td>
								<td><a href="<%= request.getContextPath() %>/tour-article?id=<%= tourDto.getTourArticleID() %>"><%= tourDto.getCode() %></a></td>
								<td><%= Parser.subtractDays(tourDto.getStartDate(), 10) %></td>
								<td><%= bookingDto.getAdultNumber() + bookingDto.getChildNumber() + bookingDto.getBabyNumber() %></td>
								<td><%= Parser.intToVNmeseCurrency(bookingDto.getAdultNumber()*tourDto.getPrice() + bookingDto.getChildNumber()*tourDto.getPriceForChildren() + bookingDto.getBabyNumber()*tourDto.getPriceForBaby() - bookingDto.getAdultNumber()*tourDto.getDiscount()) %> đ</td>
								<td style="background-color: <%= Parser.statusToColor(bookingDto.getStatus()) %>;"><%= bookingDto.getStatus() %></td>
							</tr>
							<%
						}
						%>
					</tbody>
				</table>
				<%
			}
			%>
			
		</div>
		
	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>