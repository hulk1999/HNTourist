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

	<title>Thêm bài viết - Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/quill/quill.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-common-add.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin-article-add.css">

</head>

<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/manage.jpg">
		<div id="header-title">
			THÊM BÀI VIẾT<br>
			____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="left-column-main-content-container">

	<%@include file="/common/admin-left-column.jsp" %>

	<!-- ============================================================================================ -->

	<div id="main-content">
		
		<form id="add-article-form" action="../ArticleMainController" method="POST" onsubmit="getArticle(); return validateAddArticle();">

			<div class="info-group-title">THÊM BÀI VIẾT</div>

			<div class="input-label">Tiêu đề (*)</div>
			<input type="text" name="title">
			<div class="input-label">Link ảnh bìa (*)</div>
			<input type="text" name="cover-photo">
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

	        <input type="hidden" name="action" value="add">
			<input type="submit" value="ĐĂNG">

		</form>

	</div>

	</div>

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

		function getArticle(){
			document.getElementsByName("article")[0].value = editor.container.firstChild.innerHTML;
		}

		var articleTextArea = editor.container.firstChild;
		var articleTextAreaEmpty = articleTextArea.innerHTML;
		function validateAddArticle(){
			var valid = true;
			var form = document.getElementById("add-article-form");
			var title = form.elements[0];
			var coverPhoto = form.elements[1];
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
			if (articleTextArea.innerHTML == articleTextAreaEmpty){
				valid = false;
				alert("Bài viết không được để trống!");
			}
			return valid;
		}

	</script>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>