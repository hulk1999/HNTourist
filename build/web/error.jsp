<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>

	<title>Lỗi - Hungnguyentourist</title>

	<%@include file="common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="css/header-image.css">
	
	<style>
		#main-content{
			padding: 0px 40px;
			margin: 69px 0px;
		}
	</style>

</head>
<body>

	<%@include file="common/menu.jsp" %>
	
	<div id="header">
		<img src="images/contact/cover.jpg">
		<div id="header-title">
			TRANG LỖI<br>
			  ____
		</div>
	</div>

	<div id="main-content">
		<h2><%= request.getParameter("error") %></h2>
	</div>

	<%@include file="common/footer.jsp" %>

</body>
</html>