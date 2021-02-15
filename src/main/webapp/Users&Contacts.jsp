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


	<div class="container-fluid">

		<div class="row clearfix">
			<div class="col-lg-12">
				<div class="card">
					<div class="header">
						<h2>
							<strong><i class="zmdi zmdi-accounts-list-alt"></i>
								Gestion</strong> Utilisateurs
						</h2>

					</div>

					<div class="body">
						<button class="btn btn-sm btn-success pull-right"
							data-toggle="modal" data-target="#registerModal">
							<i class="fa fa-pencil"></i> Nouveau
						</button>
						<div id="usersList"></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row clearfix">
			<div class="col-lg-12">
				<div class="card">
					<div class="header">
						<h2>
							<strong><i class="zmdi zmdi-email"></i> Gestion</strong> Contacts
						</h2>


					</div>

					<div class="body">
						<div id="contactList"></div>
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
<script src="resources/js/manageUsers.js"></script>
<script src="resources/js/contacts/contacts.js"></script>
<script src="resources/js/Settings.js"></script>
</body>
</html>