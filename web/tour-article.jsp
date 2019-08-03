<%@page import="hungnp.support.Parser"%>
<%@page import="hungnp.dtos.TourDTO"%>
<%@page import="java.util.List"%>
<%@page import="hungnp.dtos.TourArticleDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
	TourArticleDTO dto = (TourArticleDTO) request.getAttribute("dto");
	List<String> locationList = (List) request.getAttribute("locationList");
	List<TourDTO> tourList = (List) request.getAttribute("tourList");
%>

<!DOCTYPE html>
<html>
<head>

    <title><%= dto.getTitle() %></title>

    <%@include file="/common/head-include.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/tour-article.css">

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
		
        <div id="tour-header-info">
            <div class="column">
                <div class="info">THỜI GIAN: <inline class="info-content"><%= dto.getDuration() %></inline></div>
                <div class="info">ĐIỂM XUẤT PHÁT: <inline class="info-content"><%= dto.getDeparture() %></inline></div>
            </div>
            <div class="column">
                <div class="info">PHƯƠNG TIỆN: <inline class="info-content"><%= dto.getTransport() %></inline></div>
                <div class="info">ĐIỂM ĐẾN: <inline class="info-content"><%= dto.getDestination() %></inline></div>
            </div>
        </div>

        <table id="prices-list">
            <thead>
                <tr>
                    <th>KHỞI HÀNH</th>
                    <th>MÃ TOUR</th>
                    <th>GIÁ</th>
                    <th>GIÁ TRẺ EM</th>
                    <th>GIÁ EM BÉ</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
				<%
				for (TourDTO dtoIter : tourList){
					%>
					<tr>
						<td><%= dtoIter.getStartDate() %></td>
						<td><%= dtoIter.getCode() %></td>
						<td class="number"><%= Parser.intToVNmeseCurrency(dtoIter.getPrice()) %></td>
						<td class="number"><%= Parser.intToVNmeseCurrency(dtoIter.getPriceForChildren()) %></td>
						<td class="number"><%= Parser.intToVNmeseCurrency(dtoIter.getPriceForBaby()) %></td>
						<td>
							<a href="<%= request.getContextPath()%>/booking-1?code=<%= dtoIter.getCode() %>">
								<div class="buy-online-container">
									<%
									if (dtoIter.getDiscount() > 0){
										%>
										<div class="discount">Giảm <%= Parser.intToVNmeseCurrency(dtoIter.getDiscount()) %></div>
										<%
									}
									%>
									<div class="buy-online <%= dtoIter.getDiscount() > 0 ? "contains-discount" : "" %>">MUA ONLINE</div>
								</div>
							</a>
						</td>
					</tr>
					<%
				}
				%>
            </tbody>
        </table>

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
						<a href="<%= request.getContextPath()%>/tour-article-list?show=<%= location %>">
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

    <div id="right-sidebar">
            
        <div class="right-sidebar-section">
            <div class="right-sidebar-section-title">Hỗ trợ khách hàng</div>
            <div class="right-sidebar-section-content">
                <div class="info"><i class="fas fa-phone"></i> Hotline: 1900 0009</div>
                <div class="info"><i class="fas fa-envelope"></i> Email: info@hungnguyentourist.net</div>
            </div>
        </div>

        <div class="right-sidebar-section">
            <div class="right-sidebar-section-title">Vì sao nên mua tour online?</div>
            <div class="right-sidebar-section-content">
                <div class="info"><i class="fas fa-star"></i> An toàn - Bảo mật</div>
                <div class="info"><i class="fas fa-star"></i> Tiện lợi, tiết kiệm thời gian</div>
                <div class="info"><i class="fas fa-star"></i> Không tính phí giao dịch</div>
                <div class="info"><i class="fas fa-star"></i> Giao dịch bảo đảm</div>
                <div class="info"><i class="fas fa-star"></i> Nhận thêm ưu đãi</div>
            </div>
        </div>

        <div class="right-sidebar-section">
            <div class="right-sidebar-section-title">Thương hiệu uy tín</div>
            <div class="right-sidebar-section-content">
                <div class="info"><i class="fas fa-star"></i> Thành lập từ năm 1975</div>
                <div class="info"><i class="fas fa-star"></i> Thương hiệu lữ hành hàng đầu</div>
                <div class="info"><i class="fas fa-star"></i> Thương hiệu quốc gia</div>
            </div>
        </div>

    </div>
	
	</div>
		
    <!-- ============================================================================================ -->

    <%@include file="/common/footer.jsp" %>

</body>
</html>