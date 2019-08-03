<%@page import="hungnp.dtos.TouristDTO"%>
<%@page import="java.util.List"%>
<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.dtos.BookingDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	BookingDTO bookingDto = (BookingDTO) request.getAttribute("bookingDto");
	List<TouristDTO> touristList = (List) request.getAttribute("touristList");
	TourDTO tourDto = (TourDTO) request.getAttribute("tourDto");
	TourArticleDTO tourArticleDto = (TourArticleDTO) request.getAttribute("tourArticleDto");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Đặt chỗ - Hung Nguyen Tourist</title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/booking-2.css">

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
        <div class="step" id="current-step"><inline class="step-number">2</inline>. NHẬP THÔNG TIN<br>HÀNH KHÁCH</div>
        <div class="step"><inline class="step-number">3</inline>. XÁC NHẬN</div>
        <div class="step"><inline class="step-number">4</inline>. THANH TOÁN</div>
    </div>

    <!-- ============================================================================================ -->

    <div id="main-content">

        <form action="BookingMainController" method="POST" name="booking2" onsubmit="return validateBooking2()">

            <div class="info-group-title">THÔNG TIN LIÊN HỆ</div>
            <div class="info-line-container">
            	<div class="info-container">
            		<div class="info-label">Tên (*)</div>
					<input type="text" name="contactName" value="<%= Parser.nullToEmpty(bookingDto.getContactName()) %>">
            	</div>
            	<div class="info-container">
            		<div class="info-label">Số điện thoại (*)</div>
            		<input type="text" name="contactPhone" value="<%= Parser.nullToEmpty(bookingDto.getContactPhone()) %>">
            	</div>
            </div>
            <div class="info-line-container">
            	<div class="info-container">
            		<div class="info-label">Email (*)</div>
            		<input type="text" name="contactEmail" value="<%= Parser.nullToEmpty(bookingDto.getContactEmail()) %>">
            	</div>
            	<div class="info-container">
            		<div class="info-label">Địa chỉ (*)</div>
            		<input type="text" name="contactAddress" value="<%= Parser.nullToEmpty(bookingDto.getContactAddress()) %>">
            	</div>
            </div>

			<%
			int totalNum = bookingDto.getAdultNumber() + bookingDto.getChildNumber() + bookingDto.getBabyNumber();
			for (int i = 0; i <= totalNum - 1; i++){
				%>
				<div class="info-group-title">THÔNG TIN HÀNH KHÁCH #<%= i + 1 %></div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Tên</div>
						<input type="text" name="name" value="<%= i <= touristList.size() - 1 ? touristList.get(i).getName() : "" %>">
					</div>
					<div class="info-container">
						<div class="info-label">Số điện thoại</div>
						<input type="text" name="phone" value="<%= i <= touristList.size() - 1 ? touristList.get(i).getPhone() : "" %>">
					</div>
				</div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Địa chỉ</div>
						<input type="text" name="address" value="<%= i <= touristList.size() - 1 ? touristList.get(i).getAddress() : "" %>">
					</div>
					<div class="info-container">
						<div class="info-label">Giới tính</div>
						<select name="gender">
							<%
							if ((i > touristList.size() - 1) || touristList.get(i).getGender() == null){
								%>
								<option value="" selected hidden>Giới tính</option>
								<option value="Nam">Nam</option>
								<option value="Nữ">Nữ</option>
								<%
							} else{
								%>
								<option value="<%= touristList.get(i).getGender() %>"><%= touristList.get(i).getGender() %></option>
								<option value="<%= touristList.get(i).getGender().equals("Nam") ? "Nữ" : "Nam" %>"><%= touristList.get(i).getGender().equals("Nam") ? "Nữ" : "Nam" %></option>
								<%
							}
							%>
						</select>
					</div>
				</div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Quốc gia</div>
						<input type="text" name="country" value="<%= i <= touristList.size() - 1 ? touristList.get(i).getCountry() : "" %>">
					</div>
					<div class="info-container">
						<div class="info-label">Passport</div>
						<input type="text" name="passport" value="<%= i <= touristList.size() - 1 ? touristList.get(i).getPassport() : "" %>">
					</div>
				</div>
				<%
			}
			%>

            <div id="page-navigation-container">
				<input type="hidden" name="code" value="<%= tourDto.getCode() %>">
				<input type="hidden" name="destination" value="forward">
				<input type="hidden" name="action" value="save2">
                <button type="button" onclick="backToPreviousPage();" id="page-navigation-left">TRỞ VỀ</button>
                <input type="submit" value="TIẾP TỤC" id="page-navigation-right">
            </div>

        </form>

    </div>

    <script>
		
    	var inputFields = document.querySelectorAll("input[type=text]");
		function validateBooking2(){
		    if ((inputFields[0].value === "") || (inputFields[3].value === "")){
		        alert("Bạn phải điền đầy đủ thông tin liên hệ!");
		        return false;
		    }
		    if (!inputFields[2].value.match(/^[\w\.]+@\w+(\.\w+)+$/)){
		    	alert("Email không hợp lệ!");
		        return false;
		    }
		    if (!inputFields[1].value.match(/^0[0-9]{8,10}$/)){
		    	alert("Số điện thoại không hợp lệ!");
		        return false;
		    }
		    return true;
		}
		
		// back to booking1 page
		var form = document.booking2;
		var destination = document.getElementsByName("destination")[0];
		function backToPreviousPage(){
			destination.value = "back";
			form.submit();
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