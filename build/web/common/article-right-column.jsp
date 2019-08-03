<%@page import="hungnp.support.Parser"%>
<%@page import="java.util.List"%>
<%@page import="hungnp.dtos.ArticleDTO"%>
<%@page pageEncoding="UTF-8" %>

<style>
    
    #right-sidebar{
        width: 23%;
        float: right;
        margin: 50px 4% 0 0;
        color: rgb(60, 60, 60);
    }

    .right-sidebar-section{
        background-color: white;
        border: 1px solid rgb(220, 220, 220);
        margin-bottom: 30px;
    }

    .right-sidebar-section-title{
        border-bottom: 1px solid rgb(220, 220, 220);
        padding: 15px;
        color: white;
        background-color: rgb(255, 137, 30);
    }
	
	.right-sidebar-section-title a{
        color: white;
    }

    .right-sidebar-section-content{
        padding: 20px 15px;
    }

    .section-post{
        height: 70px;
        margin-bottom: 20px;
    }

    .right-sidebar-section-content a:last-of-type .section-post{
        margin-bottom: 0;
    }

    .section-post-img-container{
        float: left;
        margin-right: 10px;
        width: 70px;
        height: 70px;
        overflow: hidden;
    }

    .section-post-img{
        height: 100%;
    }

    .section-post-title{
		height: 53px;
        color: rgb(60, 60, 60);
        font-size: 14px;
        transition: color 0.5s;
    }

    .section-post-title:hover{
        color: rgb(255, 137, 30);
    }

    .section-post-date{
        color: rgb(140, 140, 140);
        font-size: 14px;
    }

    .section-destination{
        margin-bottom: 10px;
        color: rgb(60, 60, 60);
        transition: color 0.5s;
    }

    .section-destination:hover{
        color: rgb(255, 137, 30);
    }

    .right-sidebar-section-content a:last-of-type .section-destination{
        margin-bottom: 0;
    }
    
</style>

<%
	List<Object> rightSidebarPack = (List) request.getAttribute("rightSidebarPack");
	List<ArticleDTO> popularArticleList = (List) rightSidebarPack.get(0);
	List<ArticleDTO> newArticleList = (List) rightSidebarPack.get(1);
	List<List<Object>> popularLocationList = (List) rightSidebarPack.get(2);
%>

<div id="right-sidebar">

	<div class="right-sidebar-section">
		<div class="right-sidebar-section-title"><a href="<%= request.getContextPath()%>/article-list?show=popular">BÀI VIẾT NỔI BẬT</a></div>
		<div class="right-sidebar-section-content">
			<%
			for (ArticleDTO popularDto : popularArticleList){
				%>
				<a href="<%= request.getContextPath()%>/article?id=<%= popularDto.getID() %>"><div class="section-post">
					<div class="section-post-img-container"><img src="<%= popularDto.getCoverPhoto() %>" class="section-post-img"></div>
					<div class="section-post-title"><%= popularDto.getTitle() %></div>
					<div class="section-post-date"><i class="far fa-clock"></i> <%= Parser.stringToVNmeseDateString(popularDto.getLastUpdate()) %></div>
				</div></a>
				<%
			}
			%>
		</div>
	</div>

	<div class="right-sidebar-section">
		<div class="right-sidebar-section-title"><a href="<%= request.getContextPath()%>/article-list">BÀI VIẾT MỚI</a></div>
		<div class="right-sidebar-section-content">
			<%
			for (ArticleDTO newDto : newArticleList){
				%>
				<a href="<%= request.getContextPath()%>/article?id=<%= newDto.getID() %>"><div class="section-post">
					<div class="section-post-img-container"><img src="<%= newDto.getCoverPhoto() %>" class="section-post-img"></div>
					<div class="section-post-title"><%= newDto.getTitle() %></div>
					<div class="section-post-date"><i class="far fa-clock"></i> <%= Parser.stringToVNmeseDateString(newDto.getLastUpdate()) %></div>
				</div></a>
				<%
			}
			%>
		</div>
	</div>

	<div id="section-destinations" class="right-sidebar-section">
		<div class="right-sidebar-section-title">ĐỊA ĐIỂM NỔI BẬT</div>
		<div class="right-sidebar-section-content">
			<%
			for (List location : popularLocationList){
				%>
					<a href="<%= request.getContextPath()%>/article-list?show=<%= (String) location.get(0) %>">
						<div class="section-destination">
							&#8226;
							<%= (String) location.get(0) %>
							(<%= (int) location.get(1) %>)
						</div>
					</a>
				<%
			}
			%>
		</div>
	</div>

</div>