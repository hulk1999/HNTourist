<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	TourDTO dto = (TourDTO) request.getAttribute("dto");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Chỉnh sửa tour - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-tour-edit.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>

	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
		<div id="header-title">
			CHỈNH SỬA TOUR<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

		<%@include file="/common/admin-left-column.jsp" %>

		<!-- ============================================================================================ -->

		<div id="main-content">
			
			<form action="../TourMainController" method="POST">
				
				<div id="tour-input-container">
					<div class="tour-input-title">MÃ TOUR: <%= dto.getCode() %></div>
					<input type="hidden" name="code" value="<%= dto.getCode() %>">
					<div class="input-line-container">
						<div class="input-field-container">
							<div class="input-label">Ngày khởi hành (*)</div>
							<input type="text" name="startDate" value="<%= dto.getStartDate() %>" readonly>
						</div>
						<div class="input-field-container">
							<div class="input-label">Giá người lớn (*)</div>
							<input type="number" name="price" value="<%= dto.getPrice()%>" min="0">
						</div>
					</div>
					<div class="input-line-container">
						<div class="input-field-container">
							<div class="input-label">Giá trẻ em (*)</div>
							<input type="number" name="priceForChildren" value="<%= dto.getPriceForChildren()%>" min="0">
						</div>
						<div class="input-field-container">
							<div class="input-label">Giá em bé (*)</div>
							<input type="number" name="priceForBaby" value="<%= dto.getPriceForBaby()%>" min="0">
						</div>
					</div>
					<div class="input-line-container">
						<div class="input-field-container">
							<div class="input-label">Khuyến mãi</div>
							<input type="number" name="discount" value="<%= dto.getDiscount()%>" min="0">
						</div>
						<div class="input-field-container">
							<div class="input-label">Tổng số chỗ (*)</div>
							<input type="number" name="totalSeats" value="<%= dto.getTotalSeats() %>" min="0">
						</div>
					</div>
				</div>

				<input type="hidden" name="action" value="edit">
				<input type="submit" value="SỬA">
					
			</form>
				
		</div>

	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>