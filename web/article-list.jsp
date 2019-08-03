<%@page import="hungnp.support.ListPack"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	List<ArticleDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
	String pageTitle = (String) request.getAttribute("pageTitle");
%>

<!DOCTYPE html>
<html>
<head>

	<title><%= pageTitle %> - Hung Nguyen Tourist</title>

	<%@include file="/common/head-include.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header-image.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/article-list.css">

</head>
<body>

	<%@include file="/common/menu.jsp" %>
	
	<!-- ============================================================================================ -->

	<div id="header">
		<img src="<%= request.getContextPath() %>/images/cover/article.jpg">
		<div id="header-title">
			<%= pageTitle.toUpperCase() %><br>
				____
		</div>
	</div>

	<!-- ============================================================================================ -->

	<div id="main-content-right-sidebar-container">
        
	<div id="main-content">
		<%
		if (itemList.size() == 0){
			%>
			<div style="color: red;">Không tìm thấy bài viết!</div>
			<%
		}
		%>
		<%
		for (ArticleDTO dto : itemList){
			%>
			<div class="article-container">
				<div class="article-date">
					<div class="article-date-day"><%= Parser.stringToDay(dto.getLastUpdate()) %></div>
					THÁNG <%= Parser.stringToMonth(dto.getLastUpdate()) %><br>
					<%= Parser.stringToYear(dto.getLastUpdate()) %>
				</div>
				<div class="article-title-container">
					<a href="<%= Parser.articleInfoToUrl(request.getContextPath(), dto.getID(), dto.getTitle()) %>"><div class="article-title"><%= dto.getTitle() %></div></a>
				</div>
				<img src="<%= dto.getCoverPhoto() %>" class="article-img">
				<div class="article-paragraph"><%= dto.getArticle() %>... <a href="<%= Parser.articleInfoToUrl(request.getContextPath(), dto.getID(), dto.getTitle()) %>"><span class="article-read-more">Xem thêm</span></a></div>
			</div>
			<%
		}
		%>
	    <%@include file="/common/page-navigation.jsp" %>
	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/article-right-column.jsp" %>
        
	</div>

	<!-- ============================================================================================ -->

	<%@include file="/common/footer.jsp" %>

</body>
</html>