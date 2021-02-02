function showLoader() {
document.getElementById("semiTransparentDiv").style.display="block";	
}

$("#resetForm").submit(function(e) {
		e.preventDefault(); // avoid to execute the actual submit of the form to
		var form = $(this);
		var form_data = $("#resetForm").serialize();
		showLoader();
		$.ajax({
			
			type: "POST",
			url: form.attr("action"),
			data: form_data, // serializes the form's elements.

			dataType: "json",
			success: function (data) {
				$("#semiTransparentDiv").hide();
				if (data.success==="true") {
					
					$("#toReplace").hide();
					$("#loginPage").removeAttr("hidden")
					
					flash('Lien de Récupération a été envoyé Vérifiez votre Email !', {

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
					
				}else if (data.success==="false"){
					$("#semiTransparentDiv").hide();
					$("#emailInput").css("border", "1px solid red");
					
					flash('Email inexistant !', {

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
				}else {
					$("#semiTransparentDiv").hide();
					flash('Erreur Serveur SMTP Vérifiez votre paramétrage !', {

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
				flash('Erreur Serveur SMTP Vérifiez votre paramétrage !', {

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
			},
		});
	});

$("#updatePasswordForm").submit(function(e) {
	e.preventDefault(); // avoid to execute the actual submit of the form to
	var form = $(this);
	var form_data = $("#updatePasswordForm").serialize();
	showLoader();

	$.ajax({
		
		type: "POST",
		url: form.attr("action"),
		data: form_data, // serializes the form's elements.

		dataType: "json",
		success: function (data) {
			$("#semiTransparentDiv").hide();
			
		
			if (data.success==="true") {
				
				$("#toReplace").hide();
				$("#loginPage").removeAttr("hidden")
				
				flash('Mot de Passe Changé avec Succès !', {

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
				
			}else {
				console.log("Password Change Failed !");
			}

		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Contactez Votre Administrateur");
		},
	});
});


