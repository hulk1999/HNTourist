<%@page import="hungnp.dtos.LocationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	List<LocationDTO> regionList = (List) request.getAttribute("regionList");
	List<LocationDTO> locationList = (List) request.getAttribute("locationList");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Thêm bài viết tour - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/quill/quill.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-add.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-tour-article-add.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			THÊM BÀI VIẾT TOUR<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

	<%@include file="/common/admin-left-column.jsp" %>

	<!-- ============================================================================================ -->

	<div id="main-content">
		
		<form id="add-tour-article-form" action="../TourArticleMainController" method="POST" onsubmit="getArticle(); return validateAddTourArticle();">

			<div class="info-group-title">THÊM BÀI VIẾT TOUR</div>

			<div class="input-label">Tiêu đề (*)</div>
			<input type="text" name="title">
			<div class="input-label">Link ảnh bìa (*)</div>
			<input type="text" name="cover-photo">
			<div class="input-line-container">
				<div class="input-field-container">
					<div class="input-label">Thời gian (*)</div>
					<input type="text" name="duration">
				</div>
				<div class="input-field-container">
					<div class="input-label">Phương tiện (*)</div>
					<input type="text" name="transport">
				</div>
			</div>
			<div class="input-line-container">
				<div class="input-field-container">
					<div class="input-label">Điểm xuất phát (*)</div>
					<input type="text" name="departure">
				</div>
				<div class="input-field-container">
					<div class="input-label">Điểm đến (*)</div>
					<input type="text" name="destination">
				</div>
			</div>

			<div class="input-label">Bài viết (*)</div>
			<%@include file="/common/quill.jsp" %>
			<input type="hidden" name="article">

			<div class="input-label">Địa điểm</div>
			<div id="location-list"></div>
			<select id="foreign" onchange="changeRegion();">
				<option>Trong nước</option>
				<option>Nước ngoài</option>
			</select>
			<select id="region" onchange="changeLocation();">
				<%
					for (LocationDTO dto : regionList){
						%>
							<option value="<%= dto.isForeign() ? "Nước ngoài" : "Trong nước" %>"><%= dto.getRegion() %></option>
						<%
					}
				%>
			</select>
			<select id="location">
				<%
					for (LocationDTO dto : locationList){
						%>
							<option value="<%= dto.getRegion() %>"><%= dto.getLocation() %></option>
						<%
					}
				%>
			</select>
			<button type="button" id="add-more-location" onclick="addLocation();">Thêm địa điểm</button>
			
			<div class="info-group-title" id="more-tour-title">THÊM TOUR</div>
			<div id="tour-input-section-container"></div>
			<button type="button" id="add-more-tour" onclick="showTourInput();">Thêm tour</button>

			<input type="hidden" name="action" value="add">
			<input type="submit" value="ĐĂNG">

		</form>
	</div>

	</div>

	<script src="<%= request.getContextPath() %>/js/moment.js"></script>
	<script type="text/javascript">
		 
		var locationCount = 0;
		var locationList = document.getElementById("location-list");
		var locationCombobox = document.getElementById("location");
		function addLocation(){	
			var location = locationCombobox.options[locationCombobox.selectedIndex].innerHTML;
			var locationChosenArray = document.getElementsByName("location");
			for (var i = 0; i <= locationChosenArray.length - 1; i++)
				if (location == locationChosenArray[i].value) return;
			locationList.innerHTML += `
				<div class="inputted-location-container" id="inputted-location-container-` + locationCount + `">
					<input type="hidden" name="location" class="inputted-location" value="` + location + `">
					<div class="inputted-location">` + location + `</div>
					<div class="inputted-location-remove" onclick="removeInputtedLocation(` + locationCount + `);">x</div>
				</div>
				`;
			locationCount++;
		}

		function removeInputtedLocation(index){
			var inputtedLocationContainer = document.getElementById("inputted-location-container-" + index);
			inputtedLocationContainer.parentNode.removeChild(inputtedLocationContainer);
		}

		var foreignCombobox = document.getElementById("foreign");
		var regionCombobox = document.getElementById("region");
		var regionComboboxSize = regionCombobox.childNodes.length - 1;
		changeRegion();
		function changeRegion(){
			for (var i = 0; i <= regionComboboxSize - 1; i++) regionCombobox.childNodes[i].hidden = true;
			var foreignComboboxChosenVal = foreignCombobox.options[foreignCombobox.selectedIndex].innerHTML;
			var found = false;
			for (var i = 0; i <= regionComboboxSize - 1; i++)
				if (foreignComboboxChosenVal == regionCombobox.childNodes[i].value){
					if (!found){
						found = true;
						regionCombobox.childNodes[i].selected = true;
					}
					regionCombobox.childNodes[i].hidden = false;
				}
			changeLocation();
		}

		var locationComboboxSize = locationCombobox.childNodes.length - 1;
		changeLocation();
		function changeLocation(){
			for (var i = 0; i <= locationComboboxSize - 1; i++) locationCombobox.childNodes[i].hidden = true;
			var regionComboboxChosenVal = regionCombobox.options[regionCombobox.selectedIndex].innerHTML;
			var found = false;
			for (var i = 0; i <= locationComboboxSize - 1; i++)
				if (regionComboboxChosenVal == locationCombobox.childNodes[i].value){
					if (!found){
						found = true;
						locationCombobox.childNodes[i].selected = true;
					}
					locationCombobox.childNodes[i].hidden = false;
				}
		}
		 
		var tourInputShowedCount = 0;
		var tourInputContainer = document.getElementById("tour-input-section-container");
		function showTourInput(){
			tourInputShowedCount++;
			var tourCodeContainer = document.createElement("div");
			tourCodeContainer.id = "tour-input-container-" + tourInputShowedCount;
			tourCodeContainer.innerHTML = `
				<div class="tour-input-title">
					<div class="tour-input-remove" onclick="removeTourInput(` + tourInputShowedCount + `);">x</div>
					Tour
				</div>
				<div class="input-line-container">
					<div class="input-field-container">
						<div class="input-label">Ngày khởi hành (*)</div>
						<input type="text" name="startDate">
					</div>
					<div class="input-field-container">
						<div class="input-label">Giá người lớn (*)</div>
						<input type="number" name="price" min="0">
					</div>
				</div>
				<div class="input-line-container">
					<div class="input-field-container">
						<div class="input-label">Giá trẻ em (*)</div>
						<input type="number" name="priceForChildren" min="0">
					</div>
					<div class="input-field-container">
						<div class="input-label">Giá em bé (*)</div>
						<input type="number" name="priceForBaby" min="0">
					</div>
				</div>
				<div class="input-line-container">
					<div class="input-field-container">
						<div class="input-label">Khuyến mãi</div>
						<input type="number" name="discount" min="0">
					</div>
					<div class="input-field-container">
						<div class="input-label">Tổng số chỗ (*)</div>
						<input type="number" name="totalSeats" min="0">
					</div>
				</div>
				`;
			tourInputContainer.appendChild(tourCodeContainer);
		}

		function removeTourInput(index){
			var tourInputContainer = document.getElementById("tour-input-container-" + index);
			tourInputContainer.parentNode.removeChild(tourInputContainer);
		}

		function getArticle(){
			document.getElementsByName("article")[0].value = editor.container.firstChild.innerHTML;
		}

		var articleTextArea = editor.container.firstChild;
		var articleTextAreaEmpty = articleTextArea.innerHTML;
		function validateAddTourArticle(){
			var valid = true;
			var form = document.getElementById("add-tour-article-form");
			var title = form.elements[0];
			var coverPhoto = form.elements[1];
			var duration = form.elements[2];
			var transport = form.elements[3];
			var departure = form.elements[4];
			var destination = form.elements[5];
			if (title.value.length == 0){
				valid = false;
				title.value = "";
				title.classList.add("form-invalid");
				title.placeholder = "Tiêu đề không được để trống!";
			}
			if (coverPhoto.value.length == 0){
				valid = false;
				coverPhoto.value = "";
				coverPhoto.classList.add("form-invalid");
				coverPhoto.placeholder = "Ảnh bìa không được để trống!";
			}
			if (duration.value.length == 0){
				valid = false;
				duration.value = "";
				duration.classList.add("form-invalid");
				duration.placeholder = "Thời gian không được để trống!";
			}
			if (transport.value.length == 0){
				valid = false;
				transport.value = "";
				transport.classList.add("form-invalid");
				transport.placeholder = "Phương tiện không được để trống!";
			}
			if (departure.value.length == 0){
				valid = false;
				departure.value = "";
				departure.classList.add("form-invalid");
				departure.placeholder = "Điểm xuất phát không được để trống!";
			}
			if (destination.value.length == 0){
				valid = false;
				destination.value = "";
				destination.classList.add("form-invalid");
				destination.placeholder = "Điểm đến không được để trống!";
			}
			if (articleTextArea.innerHTML == articleTextAreaEmpty){
				valid = false;
				alert("Bài viết không được để trống!");
			}
			var startDateList = document.getElementsByName("startDate");
			var priceList = document.getElementsByName("price");
			var priceForChildrenList = document.getElementsByName("priceForChildren");
			var priceForBabyList = document.getElementsByName("priceForBaby");
			var totalSeatsList = document.getElementsByName("totalSeats");
			for (var i = 0; i <= startDateList.length - 1; i++){
				if (startDateList[i].value.length == 0){
					valid = false;
					startDateList[i].value = "";
					startDateList[i].classList.add("form-invalid");
					startDateList[i].placeholder = "Ngày khởi hành không được để trống!";
				} else if (!moment(startDateList[i].value, "DD/MM/YYYY", true).isValid()){
					valid = false;
					startDateList[i].value = "";
					startDateList[i].classList.add("form-invalid");
					startDateList[i].placeholder = "Ngày khởi hành không hợp lệ!";
				}
				if (priceList[i].value.length == 0){
					valid = false;
					priceList[i].value = "";
					priceList[i].classList.add("form-invalid");
					priceList[i].placeholder = "Giá người lớn không được để trống!";
				}
				if (priceForChildrenList[i].value.length == 0){
					valid = false;
					priceForChildrenList[i].value = "";
					priceForChildrenList[i].classList.add("form-invalid");
					priceForChildrenList[i].placeholder = "Giá trẻ em không được để trống!";
				}
				if (priceForBabyList[i].value.length == 0){
					valid = false;
					priceForBabyList[i].value = "";
					priceForBabyList[i].classList.add("form-invalid");
					priceForBabyList[i].placeholder = "Giá em bé không được để trống!";
				}
				if (totalSeatsList[i].value.length == 0){
					valid = false;
					totalSeatsList[i].value = "";
					totalSeatsList[i].classList.add("form-invalid");
					totalSeatsList[i].placeholder = "Tổng số chỗ không được để trống!";
				}
			}
			return valid;
		}

	</script>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>