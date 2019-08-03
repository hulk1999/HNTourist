<%@page import="hungnp.dtos.LocationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	List<LocationDTO> regionList = (List) request.getAttribute("regionList");
%>


<!DOCTYPE html>
<html>
	<head>

		<title>Thêm địa điểm - Hungnguyentourist</title>

		<%@include file="/common/head-include.jsp" %>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/header-image.css">
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/admin-common-add.css">
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/admin-location-add.css">

	</head>
	<body>

		<%@include file="/common/menu.jsp" %>

		<!-- ============================================================================================ -->

		<div id="header">
			<img src="<%= request.getContextPath()%>/images/cover/manage.jpg">
			<div id="header-title">
				THÊM ĐỊA ĐIỂM<br>
				____
			</div>
		</div>

		<!-- ============================================================================================ -->

		<div id="left-column-main-content-container">

			<%@include file="/common/admin-left-column.jsp" %>

			<!-- ============================================================================================ -->

			<div id="main-content">

				<form id="add-location-form" action="../LocationMainController" method="POST" onsubmit="return validateAddLocation()">

					<div class="info-group-title">THÊM ĐỊA ĐIỂM</div>

					<div class="input-field-container">
						<div class="input-label">Trong nước / Nước ngoài (*)</div>
						<select name="foreign" onchange="changeRegionList();">
							<option>Trong nước</option>
							<option>Nước ngoài</option>
						</select>
					</div>

					<div class="input-field-container">
						<div class="input-label">Khu vực (*)</div>
						<input type="text" name="region" list="region-list-native" autocomplete="off">
						<datalist id="region-list-native">
							<%
							for (LocationDTO dto : regionList) {
								if (!dto.isForeign()) {
							%>
								<option value="<%= dto.getRegion()%>">
							<%
								}
							}
							%>
						</datalist>
						<datalist id="region-list-foreign">
							<%
							for (LocationDTO dto : regionList) {
								if (dto.isForeign()) {
							%>
								<option value="<%= dto.getRegion()%>">
							<%
								}
							}
							%>
						</datalist>
					</div>

					<%
					String locationPlaceholder = "";
					String locationClassInvalid = "";
					String error = request.getParameter("duplicate");
					if (error != null) {
						locationPlaceholder = "Trùng địa điểm cũ!";
						locationClassInvalid = "form-invalid";
					}
					%>

					<div class="input-field-container">
						<div class="input-label">Địa điểm / Quốc gia (*)</div>
						<input type="text" name="location" placeholder="<%= locationPlaceholder%>" class="<%= locationClassInvalid%>">
					</div>

					<input type="hidden" name="action" value="add">
					<input type="submit" value="THÊM">

				</form>
			</div>

		</div>

		<script type="text/javascript">

			var foreign = false;
			var regionInput = document.getElementsByName("region")[0];
			var listNative = document.getElementById("region-list-native");
			var listForeign = document.getElementById("region-list-foreign");
			function changeRegionList() {
				foreign = !foreign;
				regionInput.value = "";
				if (foreign) regionInput.setAttribute("list", "region-list-foreign");
				else regionInput.setAttribute("list", "region-list-native");
			}

			function validateAddLocation(){
				var valid = true;
	    		var form = document.getElementById("add-location-form");
	    		var region = form.elements[1];
	            var location = form.elements[2];
	            if (region.value.length == 0){
	            	valid = false;
                    region.value = "";
                    region.classList.add("form-invalid");
                    region.placeholder = "Khu vực không được để trống!";
	            }
	            if (location.value.length == 0){
	            	valid = false;
                    location.value = "";
                    location.classList.add("form-invalid");
                    location.placeholder = "Địa điểm không được để trống!";
	            }
	            return valid;
			}

		</script>

		<!-- ============================================================================================ -->

		<%@include file="/common/footer.jsp" %>

	</body>
</html>