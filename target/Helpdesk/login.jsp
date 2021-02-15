<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%-- <c:set var="contextPath" value="${pageContext.request.contextPath}" /> --%>

<html>
<head>
<title>Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="resources/css/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="resources/css/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="resources/css/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="resources/css/util.css">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/ResetPassword.css">
<!--===============================================================================================-->
</head>
<body>

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-pic js-tilt" data-tilt>
					<img src="resources/images/logo.jpg" alt="IMG">
				</div>

				<form id="idForm" action="<%=request.getContextPath()%>/login"
					class="login100-form validate-form" method="post" autocomplete="off">
					<span class="login100-form-title"> Login Utilisateur </span>

					<div class="wrap-input100 validate-input"
						data-validate="username Obligatoire">
						<input class="input100" type="text" name="username"
							placeholder="Username"> <span class="focus-input100"></span>
						<span class="symbol-input100"> <i class="fa fa-user"
							aria-hidden="true"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="Password Obligatoire">
						<input class="input100" type="password" name="password"
							placeholder="Password"> <span class="focus-input100"></span>
						<span class="symbol-input100"> <i class="fa fa-lock"
							aria-hidden="true"></i>
						</span>
					</div>

					<div class="container-login100-form-btn">
						<button type="submit" class="login100-form-btn">Login</button>
					</div>

					<div class="text-center p-t-12">
						<span class="txt1"> Récupération/Changement </span> <a class="txt2" href="/Helpdesk/ResetPassword.jsp">
							Password? </a>
					</div>

					<div class="text-center p-t-136">
<!-- 						<a class="txt2" href="/Helpdesk/register.jsp"> Créer un Compte -->
<!-- 							<i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i> -->
<!-- 						</a> -->
					</div>
				</form>
			</div>
		</div>
		<div id="semiTransparentDiv"> </div>
	</div>




	<!--===============================================================================================-->
	<script src="resources/js/jquery-3.5.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="resources/js/popper.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="resources/js/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="resources/js/tilt.jquery.min.js"></script>
	<script>
		$('.js-tilt').tilt({
			scale : 1.1
		})
	</script>
	<!--===============================================================================================-->
	<script src="resources/js/main.js"></script>
	<script src="resources/js/flash.min.js"></script>
	<script src="resources/js/validateForms.js"></script>
</body>
</html>