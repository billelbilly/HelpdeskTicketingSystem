$(document).ready(function () {
	function showLoader() {
		document.getElementById("semiTransparentDiv").style.display="block";
			
		}
	$("#idForm").submit(function (e) {
		e.preventDefault(); // avoid to execute the actual submit of the form.

		var form = $(this);
		var url = form.attr("action");
		var form_data = $("#idForm").serialize();
		showLoader();
		
		
		$.ajax({
			type: "POST",
			url: url,
			data: form_data, // serializes the form's elements.
			processData: false,
			//contentType: "text",
			dataType: "json",
			success: function (data) {
				$("#semiTransparentDiv").hide();
				
				switch (data.user_type) {
				case "0":
					flash('Veuillez Validé votre compte contactez votre administrateur pour plus d\'information', {

						// background color
						'bgColor': 'blue',

						// text color
						'ftColor': 'white',

						// or 'top'
						'vPosition': 'top',

						// or 'left'
						'hPosition': 'right',

						// duration of animation
						'fadeIn': 400,
						'fadeOut': 400,

						// click to close
						'clickable': true,

						// auto hides after a duration time
						'autohide': true,

						// timout
						'duration': 4000

					});
					
					break;
					case "1":
						
						// keep page login in history when clicking back button
						window.location.href = "/Helpdesk/panneauAdmin_new.jsp";
						
						/*This is for Sending Hidden Parameters via URL*/
						
//						openWindowWithPost("/Helpdesk/PanneauAdmin.jsp",'','',data.username);
//						function openWindowWithPost(url, windowoption, name, params)
//						{
//						         var form = document.createElement("form");
//						         form.setAttribute("method", "post");
//						         form.setAttribute("action", url);
//						         form.setAttribute("target", name);
//						         var input = document.createElement('input');
//						         input.type = 'hidden';
//						         input.name = "username";
//						         input.value = params;
//						         form.appendChild(input);
//						         
//						         // This is for sending Multiple parameters via URL
//						        /* for (var i in params) {
//						             if (params.hasOwnProperty(i)) {
//						                 var input = document.createElement('input');
//						                 input.type = 'hidden';
//						                 input.name = i;
//						                 input.value = params[i];
//						                 form.appendChild(input);
//						             }
//						         }*/
//						         document.body.appendChild(form);
//						         window.open(url, name, windowoption);
//						         form.submit();
//						         document.body.removeChild(form);
//						 }
						// doesnt keep login in history
						//window.location.replace("/Helpdesk/PanneauAdmin.jsp");
						
						break;
					case "2":
						window.location.href = "/Helpdesk/mainTemplate.jsp";
						break;
					case "3":
						window.location.href = "/Helpdesk/mainTemplate.jsp";
						break;
					case "-1":
						flash('Nom Utilisateur incorrect', {

							// background color
							'bgColor': 'red',

							// text color
							'ftColor': 'white',

							// or 'top'
							'vPosition': 'top',

							// or 'left'
							'hPosition': 'right',

							// duration of animation
							'fadeIn': 400,
							'fadeOut': 400,

							// click to close
							'clickable': true,

							// auto hides after a duration time
							'autohide': true,

							// timout
							'duration': 4000

						});
						
						break;
						
					case "-2":
						flash('Mot de Passe incorrect', {

							// background color
							'bgColor': 'red',

							// text color
							'ftColor': 'white',

							// or 'top'
							'vPosition': 'top',

							// or 'left'
							'hPosition': 'right',

							// duration of animation
							'fadeIn': 400,
							'fadeOut': 400,

							// click to close
							'clickable': true,

							// auto hides after a duration time
							'autohide': true,

							// timout
							'duration': 4000

						});
						
						break;

				default:
					break;
				}
				


			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert(errorThrown);
			},
		});
	});

	$("#registerForm").submit(function (e) {
		e.preventDefault(); // avoid to execute the actual submit of the form.

		var form = $(this);
		var url = form.attr("action");
		var form_data = $("#registerForm").serialize();
		showLoader();

		$.ajax({
			type: "POST",
			url: url,
			data: form_data, // serializes the form's elements.
			processData: false,
			//contentType: "text",
			dataType: "text",
			success: function (data) {
				$("#semiTransparentDiv").hide();
				if (data=="usersaved") {
					flash('Enregistrement Termnié avec succès !', {

						// background color
						'bgColor': 'green',

						// text color
						'ftColor': 'white',

						// or 'top'
						'vPosition': 'top',

						// or 'left'
						'hPosition': 'right',

						// duration of animation
						'fadeIn': 400,
						'fadeOut': 400,

						// click to close
						'clickable': true,

						// auto hides after a duration time
						'autohide': true,

						// timout
						'duration': 4000

					});
					$('#prenom_id').val('');
					$('#nom_id').val('');
					$('#email_id').val('');
					$('#phone_id').val('');
					$('#username_id').val('');
					$('#password_id').val('');
					$('#password2_id').val('');
					
					
				}
				else if (data=="usernameexist") {
					$("input").change(function() {
						$("#username_id").css("border", "");
					}).trigger("change");
					$("#username_id").css("border", "1px solid red");
					flash('Utilisateur existe déjà !', {

						// background color
						'bgColor': 'red',

						// text color
						'ftColor': 'white',

						// or 'top'
						'vPosition': 'top',

						// or 'left'
						'hPosition': 'right',

						// duration of animation
						'fadeIn': 400,
						'fadeOut': 400,

						// click to close
						'clickable': true,

						// auto hides after a duration time
						'autohide': true,

						// timout
						'duration': 4000

					});
					
				}else{
					$("input").change(function() {
						$("#email_id").css("border", "");
					}).trigger("change");
					$("#email_id").css("border", "1px solid red");
					flash('Email Exist déjà !', {
						

						// background color
						'bgColor': 'red',

						// text color
						'ftColor': 'white',

						// or 'top'
						'vPosition': 'top',

						// or 'left'
						'hPosition': 'right',

						// duration of animation
						'fadeIn': 400,
						'fadeOut': 400,

						// click to close
						'clickable': true,

						// auto hides after a duration time
						'autohide': true,

						// timout
						'duration': 4000

					});

				}
				
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert(errorThrown);
			},
		});
	});
});