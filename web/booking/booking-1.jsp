<%@page import="hungnp.dtos.BookingDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	BookingDTO bookingDto = (BookingDTO) request.getAttribute("bookingDto");
	TourDTO tourDto = (TourDTO) request.getAttribute("tourDto");
	TourArticleDTO tourArticleDto = (TourArticleDTO) request.getAttribute("tourArticleDto");
	int outOfSeats = request.getParameter("out-of-seats") != null ? Integer.parseInt(request.getParameter("out-of-seats")) : -1;
%>

<!DOCTYPE html>
<html>
<head>

    <title>Đặt chỗ - Hung Nguyen Tourist</title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/header-image.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/booking-1.css">

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
        <div class="step" id="current-step"><inline class="step-number">1</inline>. CHỌN DỊCH VỤ</div>
        <div class="step"><inline class="step-number">2</inline>. NHẬP THÔNG TIN<br>HÀNH KHÁCH</div>
        <div class="step"><inline class="step-number">3</inline>. XÁC NHẬN</div>
        <div class="step"><inline class="step-number">4</inline>. THANH TOÁN</div>
    </div>

    <!-- ============================================================================================ -->

    <div id="main-content">

        <form action="BookingMainController" method="POST" name="booking1" onsubmit="return validateBooking1()">

            <div class="info-group-title">SỐ LƯỢNG HÀNH KHÁCH</div>
            <div class="info-label">Người lớn (*)</div>
            <input type="number" name="adultNumber" min="1" value="<%= bookingDto != null ? bookingDto.getAdultNumber() : "1" %>" oninput="handlePassengerCountChanged()">
            <div class="info-label">Trẻ em</div>
            <input type="number" name="childNumber" min="0" value="<%= bookingDto != null ? bookingDto.getChildNumber() : "0" %>" oninput="handlePassengerCountChanged()">
            <div class="info-label">Em bé</div>
            <input type="number" name="babyNumber" min="0" value="<%= bookingDto != null ? bookingDto.getBabyNumber() : "0" %>" oninput="handlePassengerCountChanged()">

            <div class="info-group-title">PHƯƠNG THỨC THANH TOÁN</div>
            <div class="payment-method-container" onclick="handlePaymentMethodClicked(0)">
                <div class="payment-method-name">THANH TOÁN BẰNG CHUYỂN KHOẢN QUA NGÂN HÀNG <inline class="checked-icon"><i class="fas fa-check"></i></inline></div>
                <div class="payment-method-info">Quý khách chuyển tiền vào tài khoản Công ty TNHH MTV DVLH Hungnguyentourist, Ngân hàng Vietcombank TP.HCM - VCB.</div>
            </div>
            <div class="payment-method-container" onclick="handlePaymentMethodClicked(1)">
                <div class="payment-method-name">THANH TOÁN BẰNG TIỀN MẶT TẠI VĂN PHÒNG HNTOURIST <inline class="checked-icon"><i class="fas fa-check"></i></inline></div>
                <div class="payment-method-info">Quý khách đến một trong các văn phòng của Hungnguyentourist để thanh toán và nhận vé.</div>
            </div>
			<input type="hidden" name="paymentMethod" value="none">

            <label><input type="checkbox" name="agree"> Tôi đã đọc và đồng ý với điều khoản của Hungnguyentourist.</label>
            <div id="terms">
                Điều khoản này là sự thoả thuận đồng ý của quý khách khi sử dụng dịch vụ thanh toán trên trang web www.saigontourist.net của Công ty Dịch vụ Lữ hành Saigontourist (Saigontourist) và những trang web của bên thứ ba. Việc quý khách đánh dấu vào ô “Đồng ý” và nhấp chuột vào thanh “Chấp nhận” nghĩa là quý khách đồng ý tất cả các điều khoản thỏa thuận trong các trang web này.<br><br>
                <b>Giải thích từ ngữ</b><br><br>
                Điều khoản: là những điều quy định giữa Saigontourist và quý khách<br><br>
                Bên thứ ba: là những đơn vị liên kết với Saigontourist (OnePay, Vietcombank) nhằm hỗ trợ việc thanh toán qua mạng cho quý khách<br><br>
                Vé điện tử: là những thông tin và hành trình của quý khách cho chuyến đi được thể hiện trên một trang giấy mà quý khách có thể in ra được<br><br>
                <b>Về sở hữu bản quyền</b><br><br>
				Trang web www.saigontourist.net thuộc quyền sở hữu của Saigontourist và được bảo vệ theo luật bản quyền, quý khách chỉ được sử dụng trang web này với mục đích xem thông tin và đăng ký thanh toán online cho cá nhân chứ không được sử dụng cho bất cứ mục đích thương mại nào khác.<br><br>
				Việc lấy nội dung để tham khảo, làm tài liệu cho nghiên cứu phải ghi rõ ràng nguồn lấy từ nội dung trang web Saigontourist. Không được sử dụng các logo, các nhãn hiệu Saigontourist dưới mọi hình thức nếu chưa có sự đồng ý của Saigontourist bằng văn bản.<br><br>
				<b>Về thông tin khách hàng</b><br><br>
				Khi đăng ký thanh toán qua mạng, quý khách sẽ được yêu cầu cung cấp một số thông tin cá nhân và thông tin tài khoản.
				Đối với thông tin cá nhân: Những thông tin này chỉ để phục vụ cho nhu cầu xác nhận sự mua dịch vụ của quý khách và sẽ hiển thị những nội dung cần thiết trên vé điện tử. Saigontourist cũng sẽ sử dụng những thông tin liên lạc này để gửi đến quý khách những sự kiện, những tin tức khuyến mãi và những ưu đãi đặc biệt nếu quý khách đồng ý. Những thông tin này của quý khách sẽ được Saigontourist bảo mật và không tiết lộ cho bên thứ ba biết ngoại trừ sự đồng ý của quý khách hoặc là phải tiết lộ theo sự tuân thủ luật pháp quy định.<br><br>
				Đối với thông tin tài khoản: Những thông tin này sẽ được Saigontourist và bên thứ ba áp dụng những biện pháp bảo mật cao nhất do các hệ thống thanh toán nổi tiếng trên thế giới như Visa và MasterCard cung cấp nhằm đảm bảo sự an toàn tuyệt đối của thông tin tài khoản quý khách.
            </div>
			
            <div id="page-navigation-container">
				<input type="hidden" name="code" value="<%= tourDto.getCode() %>">
				<input type="hidden" name="destination" value="forward">
				<input type="hidden" name="action" value="save1">
                <button type="button" onclick="backToPreviousPage();" id="page-navigation-left">TRỞ VỀ</button>
				<input type="submit" value="TIẾP TỤC" id="page-navigation-right">
            </div>

        </form>

    </div>

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
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá người lớn: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPrice()) %> đ X <inline class="passenger-count">1</inline></inline></div>
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá trẻ em: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPriceForChildren()) %> đ X <inline class="passenger-count">0</inline></inline></div>
            <div class="tour-info"><i class="fas fa-dollar-sign"></i> Giá em bé: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getPriceForBaby()) %> đ X <inline class="passenger-count">0</inline></inline></div>
			<div class="tour-info"><i class="far fa-credit-card"></i> Khuyến mãi: <inline class="tour-info-content"><%= Parser.intToVNmeseCurrency(tourDto.getDiscount()) %> đ X <inline class="passenger-count">1</inline></inline></div>
			<div id="sum-cost">Tổng: <inline id="cost-number"><inline id="cost-number-output"></inline> đ</inline></div>
		</div>

        <div id="transaction-support">Hỗ trợ giao dịch <inline id="support-number">1900 0091</inline></div>

    </div>

    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

    <!-- ============================================================================================ -->

    <script>
             
        // for choosing number of passenger
        var passengerCountInput = document.querySelectorAll("input[type=number]");
        var passengerCountOutput = document.getElementsByClassName("passenger-count");
        var costOutput = document.getElementById("cost-number-output");
		handlePassengerCountChanged();
        function handlePassengerCountChanged() {
            for (var i = 0; i <= 2; i++){
                if (!Number.isInteger(parseInt(passengerCountInput[i].value))){
                    if (i === 0) passengerCountInput[i].value = 1;
                    else passengerCountInput[i].value = 0;
                }
                else if ((i === 0) && (passengerCountInput[i].value < 1)) passengerCountInput[i].value = 1;
                else if ((i > 0) && (passengerCountInput[i].value < 0)) passengerCountInput[i].value = 0;
                passengerCountOutput[i].innerHTML = passengerCountInput[i].value;
            }
			passengerCountOutput[3].innerHTML = passengerCountInput[0].value;
            var sum = <%= tourDto.getPrice() %>*passengerCountInput[0].value + <%= tourDto.getPriceForChildren() %>*passengerCountInput[1].value + <%= tourDto.getPriceForBaby() %>*passengerCountInput[2].value - <%= tourDto.getDiscount() %>*passengerCountInput[0].value;
            costOutput.innerHTML = toCurrency(sum.toString());
        }

        function toCurrency(num){
            var i = num.length - 3;
            while (i > 0){
                num = num.substring(0, i) + "." + num.substring(i, num.length);
                i -= 3;
            }
            return num;
        }

        // for choosing payment method
        var paymentMethod = document.getElementsByClassName("payment-method-container");
        var checkedIcon = document.getElementsByClassName("checked-icon");
        paymentMethod[0].chosen = false;
        paymentMethod[1].chosen = false;
		var paymentMethodHidden = document.getElementsByName("paymentMethod")[0];
		<%
		if (bookingDto != null){
			if (bookingDto.getPaymentMethod().equals("transfer")){
				%>
				handlePaymentMethodClicked(0); // js
				<%
			} else if (bookingDto.getPaymentMethod().equals("cash")){
				%>
				handlePaymentMethodClicked(1); // js
				<%
			}
		}
		%>
        function handlePaymentMethodClicked(i) {
            if (!paymentMethod[i].chosen){

                paymentMethod[i].chosen = true;
                paymentMethod[i].style.color = "white";
                paymentMethod[i].style.backgroundColor = "rgb(92, 184, 92)";
                checkedIcon[i].style.display = "inline";

                paymentMethod[1-i].chosen = false;
                paymentMethod[1-i].style.color = "black";
                paymentMethod[1-i].style.backgroundColor = "rgb(243, 237, 237)";
                checkedIcon[1-i].style.display = "none";

				if (i === 0) paymentMethodHidden.value = "transfer";
				else paymentMethodHidden.value = "cash";

            }
        }

        // for validating booking step 1
        function validateBooking1(){
            if ((paymentMethod[0].chosen === false) && (paymentMethod[1].chosen === false)){
                alert("Bạn phải chọn phương thức thanh toán!");
                return false;
            }
            if (document.querySelector("input[name=agree]").checked === false){
                alert("Bạn phải đồng ý với điều khoản của Hungnguyentourist để tiếp tục!");
                return false;
            }
            return true;
        }
        
		// back to tour article page
		var form = document.booking1;
		var destination = document.getElementsByName("destination")[0];
		function backToPreviousPage(){
			destination.value = "back";
			form.submit();
		}
		
		// check if number of tourists is out of range
		<%
		if (outOfSeats != -1){
			%>
			alert("Bạn đã đặt quá số chỗ còn trống! Số chỗ còn trống hiện tại là: <%= outOfSeats %>");
			<%
		}
		%>
		
    </script>

</body>

</html>