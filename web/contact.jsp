<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>

	<title>Liên hệ - Hung Nguyen Tourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/contact.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/contact.jpg">
		<div id="header-title">
			LIÊN HỆ VỚI CHÚNG TÔI<br>
					____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="contact-left-column">
		<div id="title">CÔNG TY TNHH MỘT THÀNH VIÊN DỊCH VỤ LỮ HÀNH HUNGNGUYENTOURIST</div>
		<div class="info-container">
			<div class="info-icon"><i class="fas fa-map-marker-alt"></i></div>
			<div class="info-title">ĐỊA CHỈ</div>
			<div class="info">Phòng 506 Đại học FPT, Tòa nhà Innovation, Công viên phần mềm Quang Trung, Quận 12, TP. HCM</div>
		</div>
		<div class="info-container">
			<div class="info-icon"><i class="fas fa-phone"></i></div>
			<div class="info-title">TƯ VẤN & ĐẶT DỊCH VỤ</div>
			<div class="info">
				Điện thoại: (84-28) 38 279 279<br>
				Hotline: 1900 0009
			</div>
		</div>
		<div class="info-container">
			<div class="info-icon"><i class="far fa-envelope"></i></div>
			<div class="info-title">EMAIL</div>
			<div class="info">info@hungnguyentourist.net</div>
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="contact-right-column">
		<form id="contact-form" action="ContactMainController" method="POST" onsubmit="return validateContact()">
			<input type="text" name="name" placeholder="Họ & tên">
			<input type="text" name="email" placeholder="Email (*)">
			<input type="text" name="phone" placeholder="Điện thoại">
			<input type="text" name="address" placeholder="Địa chỉ">
			​<textarea name="message" rows="10" placeholder="Nội dung (*)"></textarea>
			<input type="hidden" name="action" value="add">
			<input type="submit" value="Gửi">
		</form>
	</div>

	<script>
		function validateContact(){
			var valid = true;
			var form = document.getElementById("contact-form");
			var email = form.elements[1];
			var content = form.elements[4];
			if (!email.value.match(/^[\w\.]+@\w+(\.\w+)+$/)){
				valid = false;
				email.value = "";
				email.classList.add("formInvalid");
				email.placeholder = "Email không hợp lệ!";
			}
			if (content.value == ""){
				valid = false;
				content.value = "";
				content.classList.add("formInvalid");
				content.placeholder = "Bạn cần nhập nội dung!";
			}
			if (!valid) alert("Thông tin liên hệ chưa được gửi. Vui lòng kiểm tra lại.");
			else alert("Thông tin liên hệ đã được gửi đi. Cảm ơn sự quan tâm của quý khách!");
			return valid;
		}
	</script>

	<!-- ============================================================================================ -->

	<%@include file="common/footer.jsp" %>

</body>
</html>