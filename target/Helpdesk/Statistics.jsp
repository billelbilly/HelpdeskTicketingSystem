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
	<div class="body_scroll">
		<div class="block-header">
			<div class="row">
				<div class="col-lg-7 col-md-6 col-sm-12">
					<h2>Statistiques Générales</h2>

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
			<div class="row clearfix">
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Nombre Tiquet</strong> par type
							</h2>

						</div>
						<div class="body">
							<canvas id="pie_chart" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>nombre tiquets </strong>de chaque logiciel
							</h2>
							<select name=listLogiciel id="listLogicielChart" style='width: 200px;'>
							

							</select>
							
						</div>
						<div id="pie_by_logiciel_div" class="body">
							<canvas id="pie_by_logiciel" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Titre</strong> Titre
							</h2>
							<ul class="header-dropdown">
								<li class="dropdown"><a href="javascript:void(0);"
									class="dropdown-toggle" data-toggle="dropdown" role="button"
									aria-haspopup="true" aria-expanded="false"> <i
										class="zmdi zmdi-more"></i>
								</a>
									<ul class="dropdown-menu dropdown-menu-right">
										<li><a href="javascript:void(0);">Action</a></li>
										<li><a href="javascript:void(0);">Another action</a></li>
										<li><a href="javascript:void(0);">Something else</a></li>
									</ul></li>
								<li class="remove"><a role="button" class="boxs-close"><i
										class="zmdi zmdi-close"></i></a></li>
							</ul>
						</div>
						<div class="body">
							<canvas id="bar_chart" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Titre</strong> Titre
							</h2>
							<ul class="header-dropdown">
								<li class="dropdown"><a href="javascript:void(0);"
									class="dropdown-toggle" data-toggle="dropdown" role="button"
									aria-haspopup="true" aria-expanded="false"> <i
										class="zmdi zmdi-more"></i>
								</a>
									<ul class="dropdown-menu dropdown-menu-right">
										<li><a href="javascript:void(0);">Action</a></li>
										<li><a href="javascript:void(0);">Another action</a></li>
										<li><a href="javascript:void(0);">Something else</a></li>
									</ul></li>
								<li class="remove"><a role="button" class="boxs-close"><i
										class="zmdi zmdi-close"></i></a></li>
							</ul>
						</div>
						<div class="body">
							<canvas id="gradient_area" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Titre</strong> Titre
							</h2>
							<ul class="header-dropdown">
								<li class="dropdown"><a href="javascript:void(0);"
									class="dropdown-toggle" data-toggle="dropdown" role="button"
									aria-haspopup="true" aria-expanded="false"> <i
										class="zmdi zmdi-more"></i>
								</a>
									<ul class="dropdown-menu dropdown-menu-right">
										<li><a href="javascript:void(0);">Action</a></li>
										<li><a href="javascript:void(0);">Another action</a></li>
										<li><a href="javascript:void(0);">Something else</a></li>
									</ul></li>
								<li class="remove"><a role="button" class="boxs-close"><i
										class="zmdi zmdi-close"></i></a></li>
							</ul>
						</div>
						<div class="body">
							<canvas id="radar_chart" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Titre</strong> Titre
							</h2>
							<ul class="header-dropdown">
								<li class="dropdown"><a href="javascript:void(0);"
									class="dropdown-toggle" data-toggle="dropdown" role="button"
									aria-haspopup="true" aria-expanded="false"> <i
										class="zmdi zmdi-more"></i>
								</a>
									<ul class="dropdown-menu dropdown-menu-right">
										<li><a href="javascript:void(0);">Action</a></li>
										<li><a href="javascript:void(0);">Another action</a></li>
										<li><a href="javascript:void(0);">Something else</a></li>
									</ul></li>
								<li class="remove"><a role="button" class="boxs-close"><i
										class="zmdi zmdi-close"></i></a></li>
							</ul>
						</div>
						<div class="body">
							<canvas id="line_chart" class="chartjs_graph"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-12">
					<div class="card">
						<div class="header">
							<h2>
								<strong>Titre</strong> Titre
							</h2>
							<ul class="header-dropdown">
								<li class="dropdown"><a href="javascript:void(0);"
									class="dropdown-toggle" data-toggle="dropdown" role="button"
									aria-haspopup="true" aria-expanded="false"> <i
										class="zmdi zmdi-more"></i>
								</a>
									<ul class="dropdown-menu dropdown-menu-right">
										<li><a href="javascript:void(0);">Action</a></li>
										<li><a href="javascript:void(0);">Another action</a></li>
										<li><a href="javascript:void(0);">Something else</a></li>
									</ul></li>
								<li class="remove"><a role="button" class="boxs-close"><i
										class="zmdi zmdi-close"></i></a></li>
							</ul>
						</div>
						<div class="body">
							<canvas id="chart-area" class="chartjs_graph"></canvas>
						</div>
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
<script src="resources/js/chart.js@2.8.0"></script>
<script src="resources/js/statistics.js"></script>
<script src="resources/js/Settings.js"></script>

</body>
</html>