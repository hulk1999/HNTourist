<%@page import="hungnp.models.AccountDAO"%>
<%@page pageEncoding="UTF-8" %>

<style>

	#logo{
		position: fixed;
		top: 5px;
		left: 6%;
		transition: top 0.5s;
		z-index: 100;
	}

	#menu{
	 	overflow: hidden;
		position: fixed;
		padding: 15px 0px;
		width: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		transition: all 0.5s;
		z-index: 99;
	}

	#menu a{
		float: right;
		color: white;
		padding: 10px;
		font-weight: bold;
		font-size: 14px;
		transition: color 0.5s;
		text-decoration: none;
	}

	#menu a:first-of-type{
		margin-right: 6%;
	}

	#menu a:hover{
		color: rgb(255, 137, 30)!important;
	}

</style>

<!-- ========================================================= -->

<%
    String menuAccountText = "ĐĂNG NHẬP";
    String menuAccountLink = "/login";
    if (session.getAttribute("username") != null){
        AccountDAO dao = new AccountDAO();
        if (dao.getRole((String) session.getAttribute("username")).equals("admin")) menuAccountText = "QUẢN LÍ";
		else menuAccountText = "CÁ NHÂN";
		menuAccountLink = "/account/profile";
    }  
%>

<a href="<%= request.getContextPath() %>"><img id="logo" src="<%= request.getContextPath() %>/images/logo.png"></a>
<div id="menu">
    <a href="<%= request.getContextPath() %><%= menuAccountLink %>" class="menu-link"><%= menuAccountText %> &nbsp<i class="far fa-user"></i></a>
    <a href="<%= request.getContextPath() %>/contact" class="menu-link">LIÊN HỆ</a>
    <a href="<%= request.getContextPath() %>/article-list" class="menu-link">BÀI VIẾT</a>
    <a href="<%= request.getContextPath() %>/tour-article-list?show=foreign" class="menu-link">TOUR NƯỚC NGOÀI</a>
    <a href="<%= request.getContextPath() %>/tour-article-list?show=local" class="menu-link">TOUR TRONG NƯỚC</a>
    <a href="<%= request.getContextPath() %>" class="menu-link">TRANG CHỦ</a>
</div>

<!-- ========================================================= -->

<script>


	window.oldYOffset = 10; // for detecting scroll direction

	window.addEventListener("scroll", handleScroll); // check whenever scrolling

	// handle scroll up or down
	function handleScroll(){
		if (window.oldYOffset < window.pageYOffset){ // when scroll down
			document.getElementById("menu").style.backgroundColor = "black";
			document.getElementById("logo").style.top = "-75px";
			document.getElementById("menu").style.top = "-90px";
		}
		else{ // when scroll up
			if (window.pageYOffset == 0) document.getElementById("menu").style.backgroundColor = "rgba(0, 0, 0, 0.5)";
			document.getElementById("logo").style.top = "5px";
			document.getElementById("menu").style.top = "0px";
		}
		window.oldYOffset = window.pageYOffset;
	}

</script>