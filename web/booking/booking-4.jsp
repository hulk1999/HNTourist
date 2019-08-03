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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/booking-4.css">

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
        <div class="step"><inline class="step-number">3</inline>. XÁC NHẬN</div>
        <div class="step" id="current-step"><inline class="step-number">4</inline>. THANH TOÁN</div>
    </div>

    <div id="announcement">
		Đặt chỗ của bạn đã được xác nhận. Vui lòng làm theo hướng dẫn bên dưới để hoàn tất thanh toán.<br><br>
		Mã đặt chỗ của bạn là: #<%= request.getParameter("id") %>
	</div>

    <div id="page-title">THÔNG TIN THANH TOÁN</div>

    <!-- ============================================================================================ -->

    <div id="main-content-right-sidebar-container">

    <div id="main-content">

		<%
		if (bookingDto.getPaymentMethod().equals("transfer")){
			%>
			<div id="transfer">

				<b>Bạn đã chọn phương thức chuyển khoản qua ngân hàng</b><br><br>
				
				<b>Lưu ý khi chuyển khoản:</b><br>
				Trước khi thanh toán qua ngân hàng, quý khách vui lòng liên hệ nhân viên tư vấn để xác nhận số chỗ.<br><br>

				<b>Khi chuyển khoản, quý khách vui lòng nhập nội dung chuyển khoản là:</b><br>
				MDH [mãđơnhàng], [Họ tên], [Nội dung thanh toán]<br>
				VD: MDH 0000001, Nguyễn Văn A, Mua tour trên web<br>
				Ðể việc thanh toán được chính xác. Xin cảm ơn quý khách!<br><br>

				<b>Thông tin tài khoản Công ty TNHH MTV DVLH Saigontourist tại ngân hàng Vietcombank TP.HCM - VCB:</b><br>
				Tài khoản VND: 007.100.001075.3<br>
				Tài khoản USD: 007.137.008721.3<br>
				Tài khoản EUR: 007.114.060551.8<br>
				SWIFT BFTVVNVX007

			</div>
			<%
		} else{
			%>
			<div id="direct">

				Quý khách vui lòng đến một trong các văn phòng của Hungnguyentourist để thanh toán và nhận vé.<br><br>

				<div id="left-column">
					<div id="title">Hệ thống lữ hành Hungnguyentourist</div>
					<div class="province-item" id="active-province-item" onclick="handleProvinceItemClicked(0)">HNtourist TP. HCM</div>
					<div class="province-item" onclick="handleProvinceItemClicked(1)">HNtourist Hà Nội</div>
					<div class="province-item" onclick="handleProvinceItemClicked(2)">HNtourist Đà Nẵng</div>
					<div class="province-item" onclick="handleProvinceItemClicked(3)">HNtourist Nha Trang</div>
					<div class="province-item" onclick="handleProvinceItemClicked(4)">HHNtourist Tiền Giang</div>
				</div>

				<div id="main-column">
					<div class="province-show-container" id="active-province-show">
						<div class="province-show">Hungnguyentourist TP. HCM</div>
						<b>Trụ sở chính:</b><br>
						45 Lê Thánh Tôn, Quận 1. Điện thoại: (028) 38279279<br><br>
						<b>Quận 1:</b><br>
						102 Nguyễn Huệ. Điện thoại: (028) 35208208<br><br>
						<b>Quận 5:</b><br>
						01 Nguyễn Chí Thanh. Điện thoại: (028) 38303029<br>
						607 Nguyễn Trãi. Điện thoại: (028) 38578686<br><br>        
						<b>Quận Tân Bình:</b><br>
						19 Hoàng Việt. Điện thoại: (028) 38110439<br><br>            
						<b>Quận 11:</b><br>
						261 Lê Đại Hành. Điện thoại: (028) 39621925<br><br>      
						<b>Quận Gò Vấp:</b><br>
						109 Nguyễn Oanh. Điện thoại: (028) 38942939
					</div>
					<div class="province-show-container">
						<div class="province-show">Hungnguyentourist Hà Nội</div>
						<b>Quận Hoàn Kiếm:</b><br>
						55B Phan Chu Trinh. Điện thoại: (024) 38250923<br><br>
						<b>Quận Tây Hồ:</b><br>
						Tầng 11 - 249A Thụy Khuê. Điện thoại: (024) 38250925<br><br>
						<b>Quận Ba Đình:</b><br>
						31K Láng Hạ. Điện thoại: (024) 3232 3559
					</div>
					<div class="province-show-container">
						<div class="province-show">Hungnguyentourist Đà Nẵng</div>
						357 Phan Châu Trinh. Điện thoại: (0236) 389 7229<br><br>
						43 Lê Duẩn. Điện thoại: (0236) 3664884
					</div>
					<div class="province-show-container">
						<div class="province-show">Hungnguyentourist Nha  Trang</div>
						28 Yersin - Phường Vạn Thắng , Tp. Nha Trang, Khánh Hòa
					</div>
					<div class="province-show-container">
						<div class="province-show">Hungnguyentourist Tiền Giang</div>
						12B9 Nguyễn Trãi, Tp. Mỹ Tho. Điện thoại: (0273) 39566887
					</div>
				</div>

			</div>
			<%
		}
		%>
        
    </div>

	<script>
		var provinceItems = document.getElementsByClassName("province-item");
		var provincesShow = document.getElementsByClassName("province-show-container");
		var lastSelectedIndex = 0;
		function handleProvinceItemClicked(index){
			provinceItems[lastSelectedIndex].removeAttribute("id");
			provinceItems[index].setAttribute("id", "active-province-item");
			provincesShow[lastSelectedIndex].removeAttribute("id");
			provincesShow[index].setAttribute("id", "active-province-show");
			lastSelectedIndex = index;
		}
	</script>
		
    <!-- ============================================================================================ -->

    <div id="right-sidebar">
            
        <div id="tour-container">
            <div id="tour-image-container">
                <img src="<%= tourArticleDto.getCoverPhoto() %>">
            </div>
            <div id="tour-title"><%= tourArticleDto.getTitle() %></div>
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

    </div>

	<script>
		history.pushState(null, null, location.href);
		window.onpopstate = function () {
			history.go(1);
		};
	</script>
		
    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

</body>

</html>