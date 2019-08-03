<%@page import="java.util.List"%>
<%@page import="hungnp.dtos.ArticleDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	ArticleDTO dto = (ArticleDTO) request.getAttribute("dto");
	List<String> locationList = (List) request.getAttribute("locationList");
%>

<!DOCTYPE html>
<html>
<head>

    <title><%= dto.getTitle() %> - Hung Nguyen Tourist</title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/article.css">

</head>

<body>

    <%@include file="/common/menu.jsp" %>
    
    <!-- ============================================================================================ -->

    <div id="header">
        <img src="<%= dto.getCoverPhoto() %>">
        <div id="header-title"><%= dto.getTitle() %></div>
    </div>

    <!-- ============================================================================================ -->

	<div id="ultimate-container">
	
    <div id="main-content">
        <div id="article-container">
			<%
			if (locationList.size() > 0){
				%>
				<div id="article-location">
					ĐỊA ĐIỂM: 
					<%
                    int count = 0;
					for (String location : locationList){
						%>
						<a href="<%= request.getContextPath()%>/article-list?show=<%= location %>">
							<div class="location">
								<%
								if (count > 0){
									%>
									|
									<%
								}
								%>
								<%= location %>
							</div>
						</a>
						<%
                        count++;
					}
					%>
				</div>
				<%
			}
			%>
			<%= dto.getArticle() %>
            <div id="article-date"><i class="far fa-clock"></i> <%= Parser.stringToVNmeseDateString(dto.getLastUpdate()) %></div>
        </div>
    </div>

    <!-- ============================================================================================ -->

    <%@include file="/common/article-right-column.jsp" %>
    
	</div>
			
    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

</body>
</html>