<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<meta name="description"
	content="Responsive Bootstrap 4 and web Application ui kit.">
<title>PanneauAdmin</title>


<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/select2.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/datepicker.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/tabulator_bootstrap4.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/jvectormap/jquery-jvectormap-2.0.3.min.css" />

<!-- Custom Css -->
<link rel="stylesheet" type="text/css" href="resources/assets/css/style.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/ResetPassword.css">

<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="resources/css/bootstrap-datetimepicker.min.css"> -->


<style type="text/css">
.dropdown-toggle {
	display: none;
}

.option-heading:before {
	content: "\25bc";
}

.option-heading.is-active:before {
	content: "\25b2";
}

/* Helpers */
.is-hidden {
	display: none;
}

.option-heading:hover {
	cursor: pointer;
}

#Settings .modal-lg {
	max-width: 70%;
	max-height: 70%;
}

.datepicker-container {
	z-index: 9999 !important; /* has to be larger than 1050 */
}

div.progress {
	width: 200px
}

#delete_user_modal {
	z-index: 9999 !important; /* has to be larger than 1050 */
}

#edit_user_modal {
	z-index: 9999 !important; /* has to be larger than 1050 */
}




#userListModal .modal-lg {
	max-width: 80%;
	max-height: 80%;
}

#card_success, #card_danger, #card_info {
	width: 240px;
}
</style>

</head>
<body class="theme-blush">


	<!-- Page Loader -->
	<div class="page-loader-wrapper">
		<div class="loader">
			<div class="m-t-30">
				<img class="zmdi-hc-spin" src="resources/assets/images/loader.svg"
					width="48" height="48" alt="Aero">
			</div>
			<p>Chargement...</p>
		</div>
	</div>

	<!-- Overlay For Sidebars -->
	<div class="overlay"></div>


	<!--Right Icon menu Sidebar -->
	<div class="navbar-right">
		<ul class="navbar-nav">
			<li><a href="<%=request.getContextPath()%>/Logout"
				class="mega-menu" title="Log Out"><i class="zmdi zmdi-power"></i></a></li>

			<!--  -----------------------  Admin Notifications ------------------------------------------- -->
			<li class="dropdown"><a href="javascript:void(0);"
				class="dropdown-toggle" title="Notifications" data-toggle="dropdown"
				role="button"><i class="zmdi zmdi-notifications"></i>
					<div class="notify">
<!-- 						<span class="heartbit"></span> -->
<!-- 						<span class="point">3</span> -->
						
						<span id="set_heart"></span>
						<span id="nbrNotif" class="point"></span>
					</div> </a>
				<ul class="dropdown-menu slideUp2">
					<li class="header">Notifications</li>
					<li class="body">
						<ul class="menu list-unstyled">

						</ul>
					</li>
					<li class="footer">
<!-- 					<a href="javascript:void(0);">View All -->
<!-- 							Notifications</a> -->
							
							</li>
				</ul></li>
			<!-- ------------------------------------------------------------------------------------------- -->
		</ul>
	</div>

	<!-- Left Sidebar -->
	<aside id="leftsidebar" class="sidebar">
		<div class="navbar-brand">
			<button class="btn-menu ls-toggle-btn" type="button">
				<i class="zmdi zmdi-menu"></i>
			</button>

		</div>
		<div class="menu">
			<ul class="list">
				<li>
					<div class="user-info">
						<a class="image" href="javascript:void(0)"> <!--  <img src="assets/images/profile_av.jpg" alt="User">-->
							<i class="fa fa-user-circle fa-lg"></i>
						</a>
						<div class="detail">
							<h4>Bienvenue</h4>
							<small><%=session.getAttribute("username")%></small>
							</br>
							<small><strong>Dernier Accès:</strong> <%=session.getAttribute("lastAccessDate")%></small>
						</div>
					</div>
				</li>
				<li class="active open"><a href="panneauAdmin_new.jsp"><i
						class="zmdi zmdi-home"></i><span>Tableau de Bord</span></a></li>
				<li><a href="mainTemplate_new.jsp"><i
						class="zmdi zmdi-folder-star-alt"></i><span>Tiquets</span></a></li>

				<li><a href="Users&Contacts.jsp"><i
						class="zmdi zmdi-account-box-mail"></i><span>Utilisateurs &
							Contacts</span></a></li>
				<li><a href="Statistics.jsp"><i class="zmdi zmdi-chart"></i><span>Statistiques</span></a></li>

				<li><a data-toggle="modal" data-target="#Settings"
					href="javascript:void(0)"><i class="zmdi zmdi-settings"></i><span>Paramètres</span></a></li>


			</ul>
		</div>
	</aside>