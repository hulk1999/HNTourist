<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.dtos.BookingDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	BookingDTO bookingDto = (BookingDTO) request.getAttribute("bookingDto");
	TourDTO tourDto = (TourDTO) request.getAttribute("tourDto");
	TourArticleDTO tourArticleDto = (TourArticleDTO) request.getAttribute("tourArticleDto");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Đặt chỗ - Hung Nguyen Tourist</title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/booking-3.css">

</head>
<body>

    <%@include file="/common/menu.jsp" %>
    
    <!-- ============================================================================================ -->

    <div id="header">
        <img src="<%= request.getContextPath()%>/images/cover/booking.jpg">
        <div id="header-title">
            ĐẶT CHỖ<br>
              ____
        </div>
    </div>

    <!-- ============================================================================================ -->

    <div id="steps-container">
        <div class="step"><inline class="step-number">1</inline>. CHỌN DỊCH VỤ</div>
        <div class="step"><inline class="step-number">2</inline>. NHẬP THÔNG TIN<br>HÀNH KHÁCH</div>
        <div class="step" id="current-step"><inline class="step-number">3</inline>. XÁC NHẬN</div>
        <div class="step"><inline class="step-number">4</inline>. THANH TOÁN</div>
    </div>

    <div id="announcement">Đặt chỗ của bạn đã được lưu. Vui lòng kiểm tra kỹ thông tin trước khi xác nhận đặt chỗ.</div>

    <div id="page-title">THÔNG TIN ĐẶT CHỖ</div>

    <!-- ============================================================================================ -->

    <div id="main-content">

        <div id="tour-image-container">
            <img src="<%= tourArticleDto.getCoverPhoto() %>">
        </div>
        <div id="tour-title"><%= tourArticleDto.getTitle() %></div>

        <div id="page-navigation-container">
			<a href="booking-2?code=<%= tourDto.getCode() %>" id="page-navigation-left">TRỞ VỀ</a>
			<form action="BookingMainController" method="POST">
				<input type="hidden" name="code" value="<%= tourDto.getCode() %>">
				<input type="hidden" name="action" value="save3">
                <input type="submit" value="XÁC NHẬN" id="page-navigation-right">
			</form>
        </div>

    </div>

    <!-- ============================================================================================ -->

    <div id="right-sidebar">
            
        <div id="tour-container">
            <div class="tour-info"><i class="fas fa-code"></i> Mã tour: <inline class="tour-info-content"><%= tourDto.getCode() %></inline></div>
            <div class="tour-info"><i class="fas fa-map-marker-alt"></i> Điểm đến: <inline class="tour-info-content"><%= tourArticleDto.getDestination() %></inline></div>
            <div class="tour-info"><i class="fas fa-user"></i> Tên: <inline class="tour-info-content"><%= bookingDto.getContactName() %></inline></div>
            <div class="tour-info"><i class="far fa-address-card"></i> Địa chỉ: <inline class="tour-info-content"><%= bookingDto.getContactAddress() %></inline></div>
            <div class="tour-info"><i class="fas fa-phone"></i> Số điện thoại: <inline class="tour-info-content"><%= bookingDto.getContactPhone() %></inline></div>
            <div class="tour-info"><i class="far fa-envelope"></i> Email: <inline class="tour-info-content"><%= bookingDto.getContactEmail() %></inline></div>
            <div class="tour-info"><i class="far fa-calendar"></i> Ngày đi: <inline class="tour-info-content"><%= tourDto.getStartDate()%></inline></div>
            <div class="tour-info"><i class="far fa-calendar-alt"></i> Thời gian: <inline class="tour-info-content"><%= tourArticleDto.getDuration() %></inline></div>
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá người lớn: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPrice()) %> đ X <inline class="passenger-count"><%= bookingDto.getAdultNumber() %></inline></inline></div>
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá trẻ em: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPriceForChildren()) %> đ X <inline class="passenger-count"><%= bookingDto.getChildNumber() %></inline></inline></div>
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá em bé: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPriceForBaby()) %> đ X <inline class="passenger-count"><%= bookingDto.getBabyNumber() %></inline></inline></div>
            <div class="tour-info"><i class="far fa-credit-card"></i> Khuyến mãi: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getDiscount()) %> đ X <inline class="passenger-count"><%= bookingDto.getAdultNumber() %></inline></inline></div>
			<div id="sum-cost">Tổng: <inline id="cost-number"><inline id="cost-number-output"><%= Parser.intToVNmeseCurrency(tourDto.getPrice()*bookingDto.getAdultNumber() + tourDto.getPriceForChildren()*bookingDto.getChildNumber() + tourDto.getPriceForBaby()*bookingDto.getBabyNumber() - tourDto.getDiscount()*bookingDto.getAdultNumber()) %></inline> đ</inline></div>
        </div>

        <div id="transaction-support">Hỗ trợ giao dịch <inline id="support-number">1900 0091</inline></div>

    </div>

    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

</body>

</html>