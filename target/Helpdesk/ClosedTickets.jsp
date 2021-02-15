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
<title>Tiquets Fermés</title>


<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/tabulator_bootstrap4.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/select2.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/datepicker.css">

<link rel="stylesheet" type="text/css"
	href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/jvectormap/jquery-jvectormap-2.0.3.min.css" />

<!-- Custom Css -->
<link rel="stylesheet" type="text/css"
	href="resources/assets/css/style.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/ResetPassword.css">





<style type="text/css">

/* Response Css*/
.comment-wrapper .card-body {
	max-height: 650px;
	overflow: auto;
	border: solid #0275d8 3px;
}

.comment-wrapper .media-list .media img {
	width: 64px;
	height: 64px;
	border: 2px solid #e5e7e8;
}

.comment-wrapper .media-list .media {
	border-bottom: 1px dashed #efefef;
	margin-bottom: 25px;
}

#logout {
	padding: 10px;
}

#logout:hover {
	background: red;
}

#back:hover {
	background: #d3d3d3;
}

.datepicker-container {
	z-index: 1050 !important; /* has to be larger than 1050 */
}

#planifList .modal-lg {
	max-width: 80%;
	max-height: 80%;
}

#newIssue .modal-md {
	max-width: 40%;
	max-height: 40%;
}

#newIssueOfTableView .modal-md {
	max-width: 40%;
	max-height: 40%;
}

div.responseScroll {
	/*background-color: lightblue;*/
	max-height: 200px;
	overflow: auto;
}

div#ticket_detail_header {
	/*background-color: lightblue;*/
	max-width: 80%;
	overflow: auto;
}

div#ticket_detail_headerOfTableView {
	/*background-color: lightblue;*/
	max-width: 80%;
	overflow: auto;
}

.btn-simple {
	display: none;
}

.option-heading:before {
	content: "\25bc";
}

.option-heading:hover {
	cursor: pointer;
}

.option-heading.is-active:before {
	content: "\25b2";
}

/* Helpers */
.is-hidden {
	display: none;
}

input[type=radio]:hover {
	cursor: pointer;
}
</style>

</head>
<body class="theme-blush">
	<%
		response.setHeader("cache-control", "no-cache,no-store,must-revalidate");
		if (session.getAttribute("username") == null) {

			response.sendRedirect("index.jsp");
		}
	%>
	<!-- Send user Session To Server -->
	<input type="text" name="usersession" id="usersession"
		value="<%=session.getAttribute("username")%>" hidden />
	<input type="text" name="userPermission" id="userPermission"
		value="<%=session.getAttribute("userPermission")%>" hidden />



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

			<!--  -----------------------  Client and UserEntreprise Notifications ------------------------------------------- -->
			<li class="dropdown"><a href="javascript:void(0);"
				class="dropdown-toggle" title="Notifications" data-toggle="dropdown"
				role="button"><i class="zmdi zmdi-notifications"></i>
					<div class="notify">
						<!-- 						<span class="heartbit"></span> -->
						<!-- 						<span class="point">3</span> -->

						<span id="set_heart"></span> <span id="nbrNotif" class="point"></span>
					</div> </a>
				<ul class="dropdown-menu slideUp2">
					<li class="header">Notifications</li>
					<li class="body">
						<ul class="menu list-unstyled">

						</ul>
					</li>
					<li class="footer">
						<!-- 					<a href="javascript:void(0);">View All --> <!-- 							Notifications</a> -->

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
							<small><%=session.getAttribute("username")%></small> </br> <small><strong>Dernier
									Accès:</strong> <%=session.getAttribute("lastAccessDate")%></small>
						</div>
					</div>
				</li>



			</ul>
		</div>
	</aside>

	<!-- Main Content -->

	<section class="content">


		<div class="container-fluid">
		
			<div class="row clearfix">
				<div class="col-lg-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong><i class="zmdi zmdi-accounts-list-alt"></i>
									Tiquets</strong> Fermés
							</h2>

						</div>

						<div class="body">

							<div id="closedTickets"></div>
						</div>
					</div>
				</div>
			</div>


		</div>

		<div id="semiTransparentDiv"></div>
	</section>

	<!-- Responses History -->
	<div class="modal fade" id="responseHistory" tabindex="-1"
		role="dialog" aria-labelledby="issue" aria-hidden="true">
		<div class="modal-wrapper">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header bg-primary">
						<!-- <button type="button" class="close" data-dismiss="modal" -->
						<!-- aria-hidden="true">×</button> -->
						<h4 class="modal-title text-dark">
							<i class="fa fa-info-circle"></i> Historique des Réponses
						</h4>
					</div>

					<div class="modal-body">

						<!-- Ticket Details -->
						<div class="row">

							<div id="ticket_detail_header_history" class="col-md-10"></div>
						</div>
						<!-- End Ticket Details -->
						<!-- Ticket response comments -->
						<hr>

						<div class="row bootstrap snippets bootdeys">
							<div class="col-md-12">
								<div class="comment-wrapper">
									<div class="card border-primary">
										<div class="card-header bg-primary">
											<span class="text-white">Réponses</span>
											<div class="pull-right">
												<input class="search_response" type="text" name="search"
													placeholder="Recherche" autofocus="autofocus">

											</div>

										</div>
										<div class="card-body">

											<div class="response_list_history mt-5"></div>

											<div class="responseScroll">
												<ul id="historyList" class="media-list">


												</ul>
											</div>

										</div>
									</div>
								</div>

							</div>
						</div>

						<!-- End Ticket response comments -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">
							<i class="fa fa-times"></i> Fermer
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!-- Responses History -->


	<jsp:include page="Footer.jsp"></jsp:include>
	<script src="resources/js/popper.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/jquery.quicksearch.js"></script>
	<script src="resources/js/xlsx.full.min.js"></script>
	<script src="resources/js/jspdf.min.js"></script>
	<script src="resources/js/jspdf.plugin.autotable.js"></script>
	<script src="resources/js/rating.js"></script>
	<script src="resources/js/closedTickets.js"></script>


</body>
</html>