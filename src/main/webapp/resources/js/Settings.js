function showLoader() {
	document.getElementById("semiTransparentDiv").style.display = "block";

}

// Function to get list of Logiciel
function getLogicielList() {

	$.ajax({
		type : "GET",
		url : "/Helpdesk/Settings",
		data : {
			action : "/getLogicielList"
		},
		// processData: false,
		// contentType: "text",
		dataType : "json",
		success : function(data) {

			$('#listLogiciel').empty();

			for (var i = 0; i < data.length; i++) {
				$('#listLogiciel').append(
						'<option value="' + data[i][0] + '">' + data[i][1]
								+ '</option>');

			}

			// Initialize select2
			$("#listLogiciel").select2();

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Erreur Serveur Veuillez contactez votre administrateur !");
		},
	});

}



// Function to get list of Version
function getVersionList() {

	$.ajax({
		type : "GET",
		url : "/Helpdesk/Settings",
		data : {
			action : "/getVersionList"
		},
		// processData: false,
		// contentType: "text",
		dataType : "json",
		success : function(data) {

			$('#listVersion').empty();

			for (var i = 0; i < data.length; i++) {
				$('#listVersion').append(
						'<option value="' + data[i][0] + '">' + data[i][1]
								+ '</option>');

			}

			// Initialize select2
			$("#listVersion").select2();

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Erreur Serveur Veuillez contactez votre administrateur !");
		},
	});

}


/**
 * Option dropdowns. Slide toggle
 */
$(".option-heading").on(
		'click',
		function() {
			$(this).toggleClass('is-active').next(".option-content").stop()
					.slideToggle(500);
			showLoader();
			$("#host").val('');
			$("#smtp").val('');
			$("#port").val('');
			$("#email").val('');
			$("#nom").val('');
			$("#attchpath").val('');
			$("#password_smtp").val('');
			$("#password2_smtp").val('');

			// Init Settings Form Here
			$.ajax({

				type : "GET",
				url : "/Helpdesk/Settings",
				data : {
					action : "/getInitialSettings"
				},

				dataType : "json",
				success : function(data) {
					$("#semiTransparentDiv").hide();
					var settingsObj = data.settings;
					var isNotNull = false;
					for ( var prop in settingsObj) {
						if (settingsObj.hasOwnProperty(prop)) {
							isNotNull = true;
						}
					}
					if (isNotNull) {
						$("#host").val(settingsObj.host);
						$("#smtp").val(settingsObj.smtp);
						$("#port").val(settingsObj.port);
						$("#email").val(settingsObj.email);
						$("#nom").val(settingsObj.nom);
						$("#attchpath").val(settingsObj.attchpath);
						$("#password_smtp").removeAttr("required");
						
						
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#semiTransparentDiv").hide();
					alert("Erreur Serveur Contactez Votre Administrateur");
				},
			});

		});

$("#MailingSettingForm").submit(function(e) {
	e.preventDefault(); // avoid to execute the actual submit of the form to
	var form = $(this);
	var form_data = $("#MailingSettingForm").serialize();
	showLoader();
	$.ajax({

		type : "POST",
		url : form.attr("action"),
		data : form_data, // serializes the form's elements.

		dataType : "json",
		success : function(data) {

			$("#semiTransparentDiv").hide();
			if (data.success === "true") {
				$('.modal').modal('hide');
				$("body").removeClass("modal-open");
				$("div.modal-backdrop").remove();

				flash('Paramétrage Sauvegardé Avec Succès !', {

					// background color
					'bgColor' : 'green',

					// text color
					'ftColor' : 'white',

					// or 'top'
					'vPosition' : 'top',

					// or 'left'
					'hPosition' : 'right',

					// duration of animation
					'fadeIn' : 400,
					'fadeOut' : 400,

					// click to close
					'clickable' : true,

					// auto hides after a duration time
					'autohide' : true,

					// timout
					'duration' : 4000

				});

			} else {
				$('.modal').modal('hide');
				$("body").removeClass("modal-open");
				$("div.modal-backdrop").remove();

				flash('Paramétrage Echoué !', {

					// background color
					'bgColor' : 'red',

					// text color
					'ftColor' : 'white',

					// or 'top'
					'vPosition' : 'top',

					// or 'left'
					'hPosition' : 'right',

					// duration of animation
					'fadeIn' : 400,
					'fadeOut' : 400,

					// click to close
					'clickable' : true,

					// auto hides after a duration time
					'autohide' : true,

					// timout
					'duration' : 4000

				});
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Contactez Votre Administrateur");
		},
	});
});


$("#LogicielForm").submit(
		function(e) {
			e.preventDefault(); // avoid to execute the actual submit of the
								// form to
			var form = $(this);
			var form_data = $("#LogicielForm").serialize();
			showLoader();
			$.ajax({

				type : "POST",
				url : form.attr("action"),
				data : form_data, // serializes the form's elements.

				dataType : "json",
				success : function(data) {
					$("#semiTransparentDiv").hide();
					if (data.success === "true") {
						$("#semiTransparentDiv").hide();
						var isHidden = document.getElementById("alertParam")
								.hasAttribute("hidden");
						if (isHidden) {
							$("#alertParam").removeAttr("hidden");
							if ($("#alertParam").hasClass("alert alert-danger")) {
								$("#alertParam").removeClass("alert alert-danger")
							}
							$("#alertParam").addClass("alert alert-success");
							$("#alertParam").text(
									'Logiciel Ajouté Avec Succès !');
							$('#nomLogiciel').val('');
						}

					}else {
						$("#semiTransparentDiv").hide();
						var isHidden = document.getElementById("alertParam")
								.hasAttribute("hidden");
						if (isHidden) {
							$("#alertParam").removeAttr("hidden");
							if ($("#alertParam").hasClass("alert alert-success")) {
								$("#alertParam").removeClass("alert alert-success")
							}
							$("#alertParam").addClass("alert alert-danger");
							$("#alertParam").text(
									'Logiciel existe déjà !');
							$('#nomLogiciel').val('');
						}
						
					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#semiTransparentDiv").hide();
					alert("Logiciel Settings Error: " + errorThrown);
				},
			});
		});

$("#VersionForm").submit(
		function(e) {
			e.preventDefault(); // avoid to execute the actual submit of the
								// form to
			var form = $(this);
			var form_data = $("#VersionForm").serialize();
			showLoader();
			$.ajax({

				type : "POST",
				url : form.attr("action"),
				data : form_data, // serializes the form's elements.

				dataType : "json",
				success : function(data) {
					$("#semiTransparentDiv").hide();
					if (data.success === "true") {
						$("#semiTransparentDiv").hide();
						var isHidden = document.getElementById("alertParam")
								.hasAttribute("hidden");
						if (isHidden) {
							$("#alertParam").removeAttr("hidden");
							if ($("#alertParam").hasClass("alert alert-danger")) {
								$("#alertParam").removeClass("alert alert-danger")
							}
							$("#alertParam").addClass("alert alert-success");
							$("#alertParam").text(
									'Version Ajouté Avec Succès !');
							$('#nomVersion').val('');
						}

					}else {
						$("#semiTransparentDiv").hide();
						var isHidden = document.getElementById("alertParam")
								.hasAttribute("hidden");
						if (isHidden) {
							$("#alertParam").removeAttr("hidden");
							if ($("#alertParam").hasClass("alert alert-success")) {
								$("#alertParam").removeClass("alert alert-success")
							}
							$("#alertParam").addClass("alert alert-danger");
							$("#alertParam").text(
									'Version existe déjà !');
							$('#nomVersion').val('');
						}
						
					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#semiTransparentDiv").hide();
					alert("Erreur Serveur Contactez Votre Administrateur");
				},
			});
		});

$("#LogicielVersionForm").submit(function(e) {
	e.preventDefault(); // avoid to execute the actual submit of the form to
	var form = $(this);
	var form_data = $("#LogicielVersionForm").serialize();
	showLoader();
	$.ajax({

		type : "POST",
		url : form.attr("action"),
		data : form_data, // serializes the form's elements.

		dataType : "json",
		success : function(data) {
	
			$("#semiTransparentDiv").hide();
			if (data.success === "true") {
				$("#semiTransparentDiv").hide();
				var isHidden = document.getElementById("alertParam")
						.hasAttribute("hidden");
				if (isHidden) {
					$("#alertParam").removeAttr("hidden");
					if ($("#alertParam").hasClass("alert alert-danger")) {
						$("#alertParam").removeClass("alert alert-danger")
					}
					$("#alertParam").addClass("alert alert-success");
					$("#alertParam").text(
							'Succès d\'association !');
					
				}

			}else {
				$("#semiTransparentDiv").hide();
				var isHidden = document.getElementById("alertParam")
						.hasAttribute("hidden");
				if (isHidden) {
					$("#alertParam").removeAttr("hidden");
					if ($("#alertParam").hasClass("alert alert-success")) {
						$("#alertParam").removeClass("alert alert-success")
					}
					$("#alertParam").addClass("alert alert-danger");
					$("#alertParam").text(
							'Version déjà Affectée à ce Logiciel !');
					
				}
				
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Contactez Votre Administrateur");
		},
	});
});

$("#nomVersion").attr('disabled', true);
$("#listLogiciel").attr('disabled', true);
$("#listVersion").attr('disabled', true);
$("#versionSauv").attr('disabled', true);
$("#logicielVersionSauv").attr('disabled', true);

$('input[type=radio][name=inlineRadioOptions]').change(
		function() {
			if (this.value == 'logiciel') {
				var isHidden = document.getElementById("alertParam")
						.hasAttribute("hidden");
				if (!isHidden) {
					$("#alertParam").attr("hidden", true);

				}
				$("#nomVersion").attr('disabled', true);
				$("#listLogiciel").attr('disabled', true);
				$("#listVersion").attr('disabled', true);
				$("#versionSauv").attr('disabled', true);
				$("#logicielVersionSauv").attr('disabled', true);

				$("#nomLogiciel").removeAttr("disabled");
				$("#logicielSauv").removeAttr("disabled");

			} else if (this.value == 'version') {
				var isHidden = document.getElementById("alertParam")
						.hasAttribute("hidden");
				if (!isHidden) {
					$("#alertParam").attr("hidden", true);

				}
				$("#nomLogiciel").attr('disabled', true);
				$("#listLogiciel").attr('disabled', true);
				$("#listVersion").attr('disabled', true);
				$("#logicielSauv").attr('disabled', true);
				$("#logicielVersionSauv").attr('disabled', true);

				$("#nomVersion").removeAttr("disabled");
				$("#versionSauv").removeAttr("disabled");
			} else {
				var isHidden = document.getElementById("alertParam")
						.hasAttribute("hidden");
				if (!isHidden) {
					$("#alertParam").attr("hidden", true);

				}
				// Call getLogiciellist and getVersionlist functions here
				getLogicielList()
				getVersionList();
				$("#nomLogiciel").attr('disabled', true);
				$("#nomVersion").attr('disabled', true);
				$("#logicielSauv").attr('disabled', true);
				$("#versionSauv").attr('disabled', true);

				$("#listLogiciel").removeAttr("disabled");
				$("#listVersion").removeAttr("disabled");
				$("#logicielVersionSauv").removeAttr("disabled");

			}
		});

$("#nomLogiciel").on(
		'focus',
		function() {
			var isHidden = document.getElementById("alertParam").hasAttribute(
					"hidden");
			if (!isHidden) {
				$("#alertParam").attr("hidden", true);

			}
		});

$("#nomVersion").on(
		'focus',
		function() {
			var isHidden = document.getElementById("alertParam").hasAttribute(
					"hidden");
			if (!isHidden) {
				$("#alertParam").attr("hidden", true);

			}
		});

$("#listLogiciel").change(
		function() {
			var isHidden = document.getElementById("alertParam").hasAttribute(
					"hidden");
			if (!isHidden) {
				$("#alertParam").attr("hidden", true);

			}
		});

$("#listVersion").change(
		function() {
			var isHidden = document.getElementById("alertParam").hasAttribute(
					"hidden");
			if (!isHidden) {
				$("#alertParam").attr("hidden", true);

			}
		});