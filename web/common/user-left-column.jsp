<%@page pageEncoding="UTF-8" %>

<style>
    
    #user-left-column{
		width: 20%;
		margin: 50px 2% 50px 4%;
		float: left;
    }

    #user-left-column .menu-choice{
		padding: 15px 0px 15px 20px;
		border-top: 1px solid rgb(200, 200, 200);
		color: rgb(60, 60, 60);
		background-color: rgb(240, 240, 240);
    }
	
	#user-left-column #form-logout-container{
		border-top: 1px solid rgb(200, 200, 200);
		border-bottom: 1px solid rgb(200, 200, 200);
		background-color: rgb(240, 240, 240);
    }
	
	#user-left-column .menu-choice:hover, #user-left-column #form-logout-container:hover{
		background-color: white;
    }
	
	#user-left-column button{
		width: 100%;
		text-align: left;
		font-size: 17px;
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

<div id="user-left-column">
	<a href="<%= request.getContextPath() %>/account/profile"><div class="menu-choice"><i class="fas fa-user"></i> Hồ sơ</div></a>
	<a href="<%= request.getContextPath() %>/account/booking"><div class="menu-choice"><i class="fas fa-shopping-cart"></i> Đặt chỗ</div></a>
	<a href="<%= request.getContextPath() %>/account/change-info"><div class="menu-choice"><i class="fas fa-info-circle"></i> Thay đổi thông tin</div></a>
	<a href="<%= request.getContextPath() %>/account/change-password"><div class="menu-choice"><i class="fas fa-cog"></i> Đổi mật khẩu</div></a>
	<form action="../AccountLogoutController" method="POST">
		<div id="form-logout-container">
			<button type="submit"><i class="fas fa-arrow-left"></i> Đăng xuất</button>
		</div>
	</form>
</div>