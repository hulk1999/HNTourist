<%@page pageEncoding="UTF-8" %>

<style>

	#footer{
		height: 160px;
		background-color: black;
		position: relative;
	}

	#footer #left-column{
		position: absolute;
		top: 30px;
		left: 100px;
	}

	#footer #left-column #slogan{
		color: white;
		margin-top: 20px;
	}

	#footer #right-column{
		position: absolute;
		top: 30px;
		right: 170px;
		color: white;
	}

	#footer #right-column #title{
		font-size: 26px;
		margin-bottom: 20px;
	}

	#footer #right-column #content{
		line-height: 24px;
	}

</style>

<!-- ========================================================= -->

<div id="footer">

	<div id="left-column">
		<div id="image-container">
			<a href="<%= request.getContextPath() %>"><img src="<%= request.getContextPath() %>/images/logo.png"></a>
		</div>
		<div id="slogan">HUNGNGUYENTOURIST - Thương hiệu lữ hành hàng đầu Việt Nam</div>
	</div>

	<div id="right-column">
		<div id="title">Liên hệ</div>
		<div id="content">
			Tổng đài: 1900 0009<br>
			Email: info@hungnguyentourist.net
		</div>
	</div>

</div>