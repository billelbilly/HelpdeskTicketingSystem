<jsp:include page="Header.jsp"></jsp:include>
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


<!-- Main Content -->

<section class="content">
	<div class="block-header">
		<div class="row">
			<div class="col-lg-7 col-md-6 col-sm-12">
				<h2>Tableau de Bord</h2>

				<button class="btn btn-primary btn-icon mobile_menu" type="button">
					<i class="zmdi zmdi-sort-amount-desc"></i>
				</button>
			</div>
			<div class="col-lg-5 col-md-6 col-sm-12">
				<button
					class="btn btn-primary btn-icon float-right right_icon_toggle_btn"
					type="button">
					<i class="zmdi zmdi-arrow-right"></i>
				</button>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row clearfix justify-content-center">
			<div class="col-lg-3 col-md-4 col-sm-12">
				<div class="card">
					<div id="card_success" class="body bg-success">
						<h6>Tiquets Crées</h6>
						<h2 id="nbr_open"></h2>
						<small id="percent_open"></small>
						<div class="progress">
							<div id="percent_open_progress" class="progress-bar l-amber"
								role="progressbar" aria-valuenow="0" aria-valuemin="0"
								aria-valuemax="100"></div>
						</div>
					</div>
				</div>
			</div>
<!-- 			<div class="col-lg-3 col-md-4 col-sm-12"> -->
<!-- 				<div class="card"> -->
<!-- 					<div id="card_danger" class="body bg-danger"> -->
<!-- 						<h6>Tiquets Fermés</h6> -->
<!-- 						<h2 id="nbr_fermer"></h2> -->

<!-- 						<small id="percent_closed"></small> -->
<!-- 						<div class="progress"> -->
<!-- 							<div id="percent_closed_progress" class="progress-bar l-amber" -->
<!-- 								role="progressbar" aria-valuenow="45" aria-valuemin="0" -->
<!-- 								aria-valuemax="100"></div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="col-lg-3 col-md-4 col-sm-12">
				<div class="card">
					<div id="card_info" class="body bg-info">
						<h6>Tiquets Assignés</h6>
						<h2 id="nbr_assign"></h2>

						<small id="percent_assigned"></small>
						<div class="progress">
							<div id="percent_assigned_progress" class="progress-bar l-amber"
								role="progressbar" aria-valuenow="45" aria-valuemin="0"
								aria-valuemax="100"></div>
						</div>
					</div>
				</div>
			</div>

		</div>


		<div class="row clearfix">
			<div class="col-lg-12">
				<div class="card">
					<div class="header">
						<h2>
							<strong><i class="zmdi zmdi-folder-star-alt"></i>
								Gestion</strong> Tiquets
						</h2>
					</div>
					<div class="body">
						<div id="ticketList"></div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="semiTransparentDiv"></div>
</section>


<jsp:include page="Footer.jsp"></jsp:include>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/manageTickets.js"></script>
<script src="resources/js/Settings.js"></script>
</body>
</html>