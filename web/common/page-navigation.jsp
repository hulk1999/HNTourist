<%@page import="hungnp.support.ListPack"%>
<%@page pageEncoding="UTF-8" %>

<style>
    
    #page-navigation-container{
		text-align: center;
		margin-top: 30px;
    }

    #page-navigation-container .page-number{
		display: inline-block;
		padding: 5px 25px;
		margin: 2px;
		color: white;
		background-color: rgb(60, 60, 60);
    }

    #page-navigation-container #active-page{
		background-color: rgb(255, 137, 30);
    }
    
</style>

<%
	ListPack navigationListPack = (ListPack) request.getAttribute("listPack");
	int pageCount = navigationListPack.getPageCount();
	int pageShow = navigationListPack.getPageShow();
	String url = (String) request.getAttribute("urlForPageNavigation");
%>

<%
	if (pageCount > 1){
		%>
			<div id="page-navigation-container">
				<%
					for (int i = 1; i <= pageCount; i++){
						if (i == pageShow){
							%>
							<a href="<%= url + i %>"><div class="page-number" id="active-page"><%= i %></div></a>
							<%
						} else{
							%>
							<a href="<%= url + i %>"><div class="page-number"><%= i %></div></a>
							<%
						}
					}
				%>
			</div>
		<%
	}
%>
