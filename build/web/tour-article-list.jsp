<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	List<TourArticleDTO> itemList = ((ListPack) request.getAttribute("listPack")).getItemList();
	List<List<Object>> tourDateListPack = (List) request.getAttribute("tourDateListPack");
	String pageTitle = (String) request.getAttribute("pageTitle");
	String search = request.getParameter("search");
	String fromDate = request.getParameter("fromDate");
	String toDate = request.getParameter("toDate");
	String foreign = request.getParameter("foreign");
%>

<!DOCTYPE html>
<html>
<head>

    <title><%= pageTitle %> - Hung Nguyen Tourist</title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/tour-article-list.css">

</head>
<body>

    <%@include file="/common/menu.jsp" %>
    
    <!-- ============================================================================================ -->

    <div id="header">
        <img src="<%= request.getContextPath() %>/images/cover/tour-article.jpg">
        <div id="header-title-container">
        	<div id="header-title">
				<%= pageTitle.toUpperCase() %><br>
						____
			</div>
        </div>
    </div>

    <!-- ============================================================================================ -->

    <div id="search-bar">
		<form action="TourArticleMainController" method="POST" onsubmit="return validateSearch();">
			<input type="text" name="search" placeholder="Tìm tour..." value="<%= Parser.nullToEmpty(search) %>">
    		<input type="text" name="fromDate" placeholder="Từ ngày (dd/mm/yyyy)" value="<%= Parser.nullToEmpty(fromDate) %>">
    		<input type="text" name="toDate" placeholder="Đến ngày (dd/mm/yyyy)" value="<%= Parser.nullToEmpty(toDate) %>">
    		<select name="foreign">
    			<option value="Tất cả">Tất cả</option>
				<option value="Trong nước" <%= foreign == null ? "" : foreign.equals("Trong nước") ? "selected" : "" %>>Trong nước</option>
				<option value="Ngoài nước" <%= foreign == null ? "" : foreign.equals("Ngoài nước") ? "selected" : "" %>>Ngoài nước</option>
			</select>
			<input type="hidden" name="action" value="search-show">
			<input type="submit" value="TÌM KIẾM">
       	</form>
    </div>

	<script src="<%= request.getContextPath() %>/js/moment.js"></script>
	<script>
		var fromDate = document.getElementsByName("fromDate")[0];
		var toDate = document.getElementsByName("toDate")[0];
		function validateSearch(){
			var valid = true;
			if (fromDate.value.length > 0)
				if (!moment(fromDate.value, "DD/MM/YYYY", true).isValid()){
					valid = false;
					fromDate.value = "";
				}
			if (toDate.value.length > 0)
				if (!moment(toDate.value, "DD/MM/YYYY", true).isValid()){
					valid = false;
					toDate.value = "";
				}
			if (!valid){
				alert("Ngày không hợp lệ!");
			}
			return valid;
		}
		
	</script>
			
    <!-- ============================================================================================ -->

    <div id="tours-list-container">
		<%
		if (itemList.size() == 0){
			%>
			<div style="color: red;">Không tìm thấy tour!</div>
			<%
		}
		%>
		<%
		int counter = 0;
		for (TourArticleDTO dto : itemList){
			int tourArticleMinPrice = (int) tourDateListPack.get(counter).get(0);
			int tourArticleMaxDiscount = (int) tourDateListPack.get(counter).get(1);
			List<String> tourDateList = (List<String>) tourDateListPack.get(counter).get(2);
			counter++;
			%>
			<div class="tour-container">
				<div class="tour-image-container">
					<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><img src="<%= dto.getCoverPhoto() %>"></a>
				</div>
				<div class="tour-middle-column">
					<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="tour-title"><%= dto.getTitle() %></div></a>
					<div class="tour-destinations"><%= dto.getDeparture() %> | <%= dto.getDestination() %></div>
					<div class="tour-duration">Thời gian: <%= dto.getDuration() %></div>
					<div class="tour-transport">Phương tiện: <%= dto.getTransport() %></div>
				</div>
				<%
				if (tourArticleMinPrice > -1){
					%>
					<div class="tour-right-column">
						<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="tour-price">Giá từ <inline class="tour-price-number"><%= Parser.intToVNmeseCurrency(tourArticleMinPrice) %></inline></div></a>
						<%
						if (tourArticleMaxDiscount > 0){
							%>
							<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="discount">Giảm <b><%= Parser.intToVNmeseCurrency(tourArticleMaxDiscount) %></b></div></a>
							<%
						}
						%>
						<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="tour-date first"><i class="far fa-calendar-alt"></i> <%= tourDateList.get(0) %></div></a>
						<%
						if (tourDateList.size() >= 2){
							%>
							<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="tour-date"><i class="far fa-calendar-alt"></i> <%= tourDateList.get(1) %></div></a>
							<%
						}
						%>
						<%
						if (tourDateList.size() == 3){
							%>
							<a href="<%= request.getContextPath() %>/tour-article?id=<%= dto.getID() %>"><div class="tour-more-date">Xem thêm ngày</div></a>
							<%
						}
						%>
					</div>
					<%
				}
				%>
				
			</div>
			<%
		}
		%>
    </div>

    <!-- ============================================================================================ -->

    <%@include file="/common/page-navigation.jsp" %>

    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

</body>
</html>