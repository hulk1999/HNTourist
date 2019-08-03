<%@page import="hungnp.dtos.TouristDTO"%>
<%@page import="java.util.List"%>
<%@page import="hungnp.dtos.BookingDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	BookingDTO dto = (BookingDTO) request.getAttribute("dto");
	List<TouristDTO> touristList = (List) request.getAttribute("touristList");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Thông tin đặt chỗ - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-booking-view.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			THÔNG TIN ĐẶT CHỖ<br>
			 ____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">
			
			<div class="info-group-title">MÃ ĐẶT CHỖ: <%= dto.getId() %></div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Mã tour</div>
					<div class="info-content"><a href="<%= request.getContextPath()%>/admin/tour-view?code=<%= dto.getTourCode() %>"><%= dto.getTourCode() %></a></div>
				</div>
				<div class="info-container">
					<div class="info-label">Người lớn</div>
					<div class="info-content"><%= dto.getAdultNumber() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Tài khoản</div>
					<div class="info-content"><a href="<%= request.getContextPath()%>/admin/account-view?username=<%= dto.getUsername() %>"><%= Parser.nullToEmpty(dto.getUsername()) %></a></div>
				</div>
				<div class="info-container">
					<div class="info-label">Trẻ em</div>
					<div class="info-content"><%= dto.getChildNumber() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Thanh toán</div>
					<div class="info-content"><%= dto.getPaymentMethod() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Em bé</div>
					<div class="info-content"><%= dto.getBabyNumber() %></div>
				</div>
			</div>
			<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Tên liên hệ</div>
					<div class="info-content"><%= dto.getContactName() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">SĐT liên hệ</div>
					<div class="info-content"><%= dto.getContactPhone() %></div>
				</div>
			</div><div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Email liên hệ</div>
					<div class="info-content"><%= dto.getContactEmail() %></div>
				</div>
				<div class="info-container">
					<div class="info-label">Địa chỉ liên hệ</div>
					<div class="info-content"><%= dto.getContactAddress() %></div>
				</div>
			</div>
				<div class="info-line-container">
				<div class="info-container">
					<div class="info-label">Trạng thái</div>
					<div class="info-content status" style="background-color: <%= Parser.statusToColor(dto.getStatus()) %>"><%= dto.getStatus() %></div>
				</div>
				<div class="info-container">
					<div class="info-label"> </div>
					<div class="info-content"> </div>
				</div>
			</div>
			
			<%
			if (touristList.size() > 0){
				%>
				<div class="info-group-title">THÔNG TIN HÀNH KHÁCH</div>
				<%
			}
			%>
			<%
			int count = 0;
			for (TouristDTO dtoIter : touristList){
				%>
				<div class="tourist-info-group-title">Hành khách #<%= ++count %></div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Tên</div>
						<div class="info-content"><%= dtoIter.getName() %></div>
					</div>
					<div class="info-container">
						<div class="info-label">SĐT</div>
						<div class="info-content"><%= dtoIter.getPhone() %></div>
					</div>
				</div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Giới tính</div>
						<div class="info-content"><%= dtoIter.getGender() %></div>
					</div>
					<div class="info-container">
						<div class="info-label">Địa chỉ</div>
						<div class="info-content"><%= dtoIter.getAddress() %></div>
					</div>
				</div>
				<div class="info-line-container">
					<div class="info-container">
						<div class="info-label">Quốc gia</div>
						<div class="info-content"><%= dtoIter.getCountry() %></div>
					</div>
					<div class="info-container">
						<div class="info-label">Passport</div>
						<div class="info-content"><%= dtoIter.getPassport() %></div>
					</div>
				</div>
				<%
			}
			%>
		</div>

	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>