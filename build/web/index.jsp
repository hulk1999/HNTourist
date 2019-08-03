<%@page import="hungnp.dtos.ArticleDTO"%>
<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	List<List<Object>> popularTourListPack = (List) request.getAttribute("popularTourListPack");
	ArticleDTO articleDto = (ArticleDTO) request.getAttribute("articleDto");
	List<List<Object>> newTourListPack = (List) request.getAttribute("newTourListPack");
%>

<!DOCTYPE html>
<html>
<head>

	<title>Hungnguyentourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/home.css">

</head>
<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/home.jpg">
		<div id="header-title">
			<div id="page-title">HUNGNGUYENTOURIST</div>
			<div id="slogan">Thương hiệu lữ hành hàng đầu Việt Nam</div>
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="search-field">
		<form action="TourArticleMainController" method="POST">
			<input type="text" name="search" placeholder="Tìm kiếm...">
			<input type="hidden" name="fromDate" value="">
    		<input type="hidden" name="toDate" value="">
			<input type="hidden" name="foreign" value="Tất cả">
			<input type="hidden" name="action" value="search-show">
			<button type="submit"><i class="fas fa-search"></i></button>
		</form>
	</div>

	<!-- ============================================================================================ -->

	<div class="tours-section">

		<div class="section-title">TOUR NỔI BẬT</div>

		<div id="tours-container">
			<%
			for (List<Object> listIter : popularTourListPack){
				TourArticleDTO tourArticleDto = (TourArticleDTO) listIter.get(0);
				int price = (int) listIter.get(1);
				int discount = (int) listIter.get(2);
				%>
				<a href="<%= request.getContextPath() %>/tour-article?id=<%= tourArticleDto.getID() %>" class="tour-container-link">
					<div class="tour-container">
						<div class="image-container">
							<img src="<%= tourArticleDto.getCoverPhoto() %>">
							<%
							if (price >= 0){
								%>
								<div class="price">
									<div>Giá từ</div>
									<div><b><%= Parser.intToVNmeseCurrency(price) %></b></div>
									<div><%= tourArticleDto.getDuration() %></div>
								</div>
								<%
							}
							%>
							<%
							if (discount > 0){
								%>
								<div class="discount">Giảm <%= Parser.intToVNmeseCurrency(discount) %> đ</div>
								<%
							}
							%>
						</div>
						<div class="info">
							<div class="left-column">Địa điểm xuất phát</div><div class="right-column"><%= tourArticleDto.getDeparture() %></div>
							<div class="left-column">Phương tiện di chuyển</div><div class="right-column"><%= tourArticleDto.getTransport() %></div>
						</div>
						<div class="title"><%= tourArticleDto.getTitle() %></div>
					</div>
				</a>
				<%
			}
			%>
		</div>

		<a href="<%= request.getContextPath() %>/tour-article-list?show=popular" class="tours-see-more-container"><div class="tours-see-more">Xem thêm</div></a>

	</div>

	<!-- ============================================================================================ -->

	<div id="article-section">
		
		<div class="image-container">
			
			<img src="<%= articleDto.getCoverPhoto() %>">

			<div id="article">
				<a href="<%= request.getContextPath() %>/article-list"><div id="section-title">BÀI VIẾT MỚI</div></a>
				<div id="title"><a href="<%= request.getContextPath() %>/article?id=<%= articleDto.getID() %>"><%= articleDto.getTitle() %></a></div>
				<div id="content"><%= articleDto.getArticle() %>...</div>
				<div id="see-more-container"><a href="<%= request.getContextPath() %>/article?id=<%= articleDto.getID() %>"><div id="see-more">Xem thêm</div></a></div>
			</div>

		</div>

	</div>

	<!-- ============================================================================================ -->

	<div class="tours-section">

		<div class="section-title">TOUR MỚI CẬP NHẬT</div>

		<div id="tours-container">
			<%
			for (List<Object> listIter : newTourListPack){
				TourArticleDTO tourArticleDto = (TourArticleDTO) listIter.get(0);
				int price = (int) listIter.get(1);
				int discount = (int) listIter.get(2);
				%>
				<a href="<%= request.getContextPath() %>/tour-article?id=<%= tourArticleDto.getID() %>" class="tour-container-link">
					<div class="tour-container">
						<div class="image-container">
							<img src="<%= tourArticleDto.getCoverPhoto() %>">
							<%
							if (price >= 0){
								%>
								<div class="price">
									<div>Giá từ</div>
									<div><b><%= Parser.intToVNmeseCurrency(price) %></b></div>
									<div><%= tourArticleDto.getDuration() %></div>
								</div>
								<%
							}
							%>
							<%
							if (discount > 0){
								%>
								<div class="discount">Giảm <%= Parser.intToVNmeseCurrency(discount) %> đ</div>
								<%
							}
							%>
						</div>
						<div class="info">
							<div class="left-column">Địa điểm xuất phát</div><div class="right-column"><%= tourArticleDto.getDeparture() %></div>
							<div class="left-column">Phương tiện di chuyển</div><div class="right-column"><%= tourArticleDto.getTransport() %></div>
						</div>
						<div class="title"><%= tourArticleDto.getTitle() %></div>
					</div>
				</a>
				<%
			}
			%>
		</div>

		<a href="<%= request.getContextPath() %>/tour-article-list" class="tours-see-more-container"><div class="tours-see-more">Xem thêm</div></a>
		
	</div>
		
	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>