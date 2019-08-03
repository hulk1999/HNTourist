<%@page pageEncoding="UTF-8" %>

<style>
    
    #admin-left-column{
		width: 20%;
		margin: 50px 2% 50px 4%;
		float: left;
    }

    .menu-choice{
		padding: 15px 0px 15px 20px;
		border-top: 1px solid rgb(200, 200, 200);
		color: rgb(60, 60, 60);
		background-color: rgb(240, 240, 240);
    }

    #admin-left-column #form-logout-container{
		border-top: 1px solid rgb(200, 200, 200);
		border-bottom: 1px solid rgb(200, 200, 200);
		background-color: rgb(240, 240, 240);
    }

    .menu-choice:hover, .menu-choice-2:hover, #admin-left-column #form-logout-container:hover{
		background-color: white;
    }

    .showing-menu-container{
		cursor: pointer;
		max-height: 50px;
		overflow: hidden;
		transition: max-height 0.5s;
    }

    .menu-choice-2{
		padding: 12px 0px 12px 40px;
		border-top: 1px solid rgb(220, 220, 220);
		color: rgb(60, 60, 60);
		font-size: 14px;
		background-color: rgb(245, 245, 245);
    }

	#admin-left-column button{
		width: 100%;
		text-align: left;
		font-size: 16px;
		padding: 15px 0px 15px 20px;
		margin: 0px;
		border: none;
		color: rgb(60, 60, 60);
		background-color: rgba(0, 0, 0, 0);
		cursor: pointer;
	}
	
    i{
        width: 20px;
    }
    
</style>

<div id="admin-left-column">
	<div class="showing-menu-container">
		<div class="menu-choice"><i class="fas fa-user"></i> Cá nhân</div>
		<a href="<%= request.getContextPath() %>/account/profile"><div class="menu-choice-2"><i class="far fa-address-book"></i> Hồ sơ</div></a>
		<a href="<%= request.getContextPath() %>/account/booking"><div class="menu-choice-2"><i class="fas fa-shopping-cart"></i> Đặt chỗ của tôi</div></a>
		<a href="<%= request.getContextPath() %>/account/change-info"><div class="menu-choice-2"><i class="fas fa-info-circle"></i> Thay đổi thông tin</div></a>
		<a href="<%= request.getContextPath() %>/account/change-password"><div class="menu-choice-2"><i class="fas fa-cog"></i> Đổi mật khẩu</div></a>
	</div>
	<a href="<%= request.getContextPath() %>/admin/booking"><div class="menu-choice"><i class="fas fa-bookmark"></i> Đặt chỗ</div></a>
	<div class="showing-menu-container">
		<div class="menu-choice"><i class="fas fa-plane"></i> Tour</div>
		<a href="<%= request.getContextPath() %>/admin/tour-article"><div class="menu-choice-2"><i class="far fa-newspaper"></i> Bài viết</div></a>
		<a href="<%= request.getContextPath() %>/admin/tour"><div class="menu-choice-2"><i class="fas fa-code"></i> Mã tour</div></a>
	</div>
	<a href="<%= request.getContextPath() %>/admin/article"><div class="menu-choice"><i class="far fa-newspaper"></i> Bài viết</div></a>
	<a href="<%= request.getContextPath() %>/admin/contact"><div class="menu-choice"><i class="far fa-envelope"></i> Liên hệ</div></a>
	<a href="<%= request.getContextPath() %>/admin/account"><div class="menu-choice"><i class="fas fa-users"></i> Tài khoản</div></a>
	<a href="<%= request.getContextPath() %>/admin/location"><div class="menu-choice"><i class="fas fa-map-marker-alt"></i> Địa điểm</div></a>
	<form action="../AccountLogoutController" method="POST">
		<div id="form-logout-container">
			<button type="submit"><i class="fas fa-arrow-left"></i> Đăng xuất</button>
		</div>
	</form>
</div>

<script>
	var showingMenuContainer = document.getElementsByClassName("showing-menu-container");
	for (var i = 0; i <= 1; i++) showingMenuContainer[i].isShowing = false;
	showingMenuContainer[0].addEventListener("click", function(){
		if (!showingMenuContainer[0].isShowing)	showingMenuContainer[0].style.maxHeight = "220px";
		else showingMenuContainer[0].style.maxHeight = "50px";
		showingMenuContainer[0].isShowing = !showingMenuContainer[0].isShowing;
	});
	showingMenuContainer[1].addEventListener("click", function(){
		if (!showingMenuContainer[1].isShowing)	showingMenuContainer[1].style.maxHeight = "140px";
		else showingMenuContainer[1].style.maxHeight = "50px";
		showingMenuContainer[1].isShowing = !showingMenuContainer[1].isShowing;
	});
</script>