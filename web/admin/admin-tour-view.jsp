<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	TourDTO dto = (TourDTO) request.getAttribute("dto");
	TourArticleDTO tourArticleDto = (TourArticleDTO) request.getAttribute("tourArticleDto");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Thông tin tour - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-tour-view.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			THÔNG TIN TOUR<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">
			
			<div class="info-group-title">MÃ TOUR: <%= dto.getCode() %></div>
			
			<div class="info-label">Bài viết</div>
			<div class="info-content"><a href="<%= request.getContextPath()%>/tour-article?id=<%= dto.getTourArticleID() %>"><%= tourArticleDto.getTitle() %></a></div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Mã tour</div>
					<div class="info-content"><%= dto.getCode() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Khởi hành</div>
					<div class="info-content"><%= dto.getStartDate() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Thời gian</div>
					<div class="info-content"><%= tourArticleDto.getDuration() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Điểm xuất phát</div>
					<div class="info-content"><%= tourArticleDto.getDeparture() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Phương tiện</div>
					<div class="info-content"><%= tourArticleDto.getTransport() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Điểm đến</div>
					<div class="info-content"><%= tourArticleDto.getDestination() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Giá người lớn</div>
					<div class="info-content"><%= Parser.intToVNmeseCurrency(dto.getPrice()) %> đ</div>
				</div>
				<div class="info-container">
					<div class="info-label">Giá em bé</div>
					<div class="info-content"><%= Parser.intToVNmeseCurrency(dto.getPriceForBaby()) %> đ</div>
				</div>
			</div><div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Giá trẻ em</div>
					<div class="info-content"><%= Parser.intToVNmeseCurrency(dto.getPriceForChildren()) %> đ</div>
				</div>
				<div class="info-container">
					<div class="info-label">Khuyến mãi</div>
					<div class="info-content"><%= Parser.intToVNmeseCurrency(dto.getDiscount()) %> đ</div>
				</div>
			</div>
				<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Tổng số chỗ</div>
					<div class="info-content"><%= dto.getTotalSeats() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Đã đặt</div>
					<div class="info-content"><%= dto.getSeatsBooked() %></div>
				</div>
			</div>

		</div>

	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>