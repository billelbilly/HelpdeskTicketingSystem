
//var deleteUser = function(cell, formatterParams) {
//	var id = cell.getRow().getData(0)[0].toString();
//	return '<button id='
//			+ id
//			+ ' class=" open_modal btn btn-danger btn-sm" data-toggle="modal" data-target="#delete_user_modal" data-id='
//			+ id
//			+ ' data-toggle="tooltip" title="Delete User"><i class="fa fa-trash fa-sm" style="color:white"></i></button>'
//
//};

var editUser = function(cell, formatterParams) {
	var id = cell.getRow().getData(0)[0].toString();
	return '<button id='
			+ id
			+ ' class=" open_modal btn btn-info btn-sm" data-toggle="modal" data-target="#edit_user_modal" data-id='
			+ id
			+ ' data-toggle="tooltip" title="Editer User"><i class="fa fa-edit fa-sm" style="color:white"></i></button>'

};

function showLoader() {
	document.getElementById("semiTransparentDiv").style.display = "block";
}

// ***********Function to Format the
// CreationDateTime************//
function getFormattedDate(datetoformat) {
	var date = new Date(datetoformat);
	var str = String(date.getDate()).padStart(2, '0') + "/"
			+ String((date.getMonth() + 1)).padStart(2, '0') + "/"
			+ date.getFullYear();

	return str;
}

function getUsersTabulator() {
	// create Tabulator on DOM element with id "example-table"
	var table = new Tabulator("#usersList", {
		height : "100%", // set height of table (in CSS or here), this
							// enables
		// the Virtual DOM and improves render speed
		// dramatically (can be any valid css height value)
		// data:get_data, //assign data to table
		locale:true,
	    langs:{
	        "fr-fr":{
//	            "columns":{
//	                "name":"Name", //replace the title of column name with the value "Name"
//	            },
	            "ajax":{
	                "loading":"Chargement ...", //ajax loader text
	                "error":"Erreur", //ajax error text
	            },
//	            "groups":{ //copy for the auto generated item count in group header
//	                "item":"item", //the singular  for item
//	                "items":"items", //the plural for items
//	            },
	            "pagination":{
	                "first":"Début", //text for the first page button
	                "first_title":"Premièr Page", //tooltip text for the first page button
	                "last":"Fin",
	                "last_title":"Dernièr Page",
	                "prev":"Précédent",
	                "prev_title":"Page Précédente",
	                "next":"Suivant",
	                "next_title":"Page Suivante",
	                "page_size":"Taille Page", //label for the page size select element
	            },
//	            "headerFilters":{
//	                "default":"filter column...", //default header filter placeholder text
//	                "columns":{
//	                    "name":"filter name...", //replace default header filter text for column name
//	                }
//	            }
	        }
	    },
		ajaxURL : "/Helpdesk/UserManagement",
		ajaxParams : {
			action : "/getAllUsers"
		},
		ajaxResponse : function(url, params, response) {
			// url - the URL of the request
			// params - the parameters passed with the request
			// response - the JSON object returned in the body of the response.
			


			return response.users; // return the tableData property of a response
								// json object
		},
		ajaxError : function(xhr, textStatus, errorThrown) {
			alert("Error Occured !");
		},
		// layout:"fitDataStretch", //fit columns to width of table (optional)
		layout : "fitColumns",

		columns : [
				// Define Table Columns
				{
					title : "Prénom",
					field : "1",
					headerFilter : true,
					headerFilterPlaceholder : "Recherche",
				},
				{
					title : "Nom",
					field : "2",
					headerFilter : true,
					headerFilterPlaceholder : "Recherche",
				},
				{
					title : "Email",
					field : "3",
					headerFilter : true,
					headerFilterPlaceholder : "Recherche",

				},
				{
					title : "Téléphone",
					field : "4",
					headerFilter : true,
					headerFilterPlaceholder : "Recherche",

				},
				{
					title : "Username",
					field : "5",
					headerFilter : true,
					headerFilterPlaceholder : "Recherche",
				},
				{
					title : "Date Expiration",
					field : "7",
					headerFilter : true,
					headerFilterPlaceholder:"Recherche",
					formatter : "datetime",
					formatterParams : {		
						outputFormat : "YYYY-MM-DD",
						invalidPlaceholder : "",
					}
				},
				
//				{
//					formatter : deleteUser,
//					align : "center",
//					width : 51,
//					headerSort : false,
//					cellClick : function(e, cell) {
//						var id = cell.getRow().getData(0)[0].toString();
//						$(".modal-footer #username_id").val(id);
//
//					}
//				},

				{
					formatter : editUser,
					align : "center",
					width : 51,
					headerSort : false,
					cellClick : function(e, cell) {
						$("#password_id").val('');
						$("#password2_id").val('');
						var phone_id="";
						var dateExpiration="";
						var user_id = cell.getRow().getData(0)[0].toString();
						var prenom_id = "";
						var nom_id = "";
						var email_id = cell.getRow().getData(0)[3].toString();
						if (cell.getRow().getData(0)[4]!=null) {
							phone_id = cell.getRow().getData(0)[4].toString();
						}
						if (cell.getRow().getData(0)[1]!=null) {
							prenom_id = cell.getRow().getData(0)[1].toString();
						}
						if (cell.getRow().getData(0)[2]!=null) {
							nom_id = cell.getRow().getData(0)[2].toString();
						}
						
						
						var isHidden = document.getElementById("date_expiration_compte_EditUser")
						.hasAttribute("required");
						if (isHidden) {
						$("#date_expiration_compte_EditUser").removeAttr("required");	
						$("#date_expiration_compte_EditUser").css("border", "");
					                  }
						
						if (cell.getRow().getData(0)[7]!=null) {
							
							dateExpiration = cell.getRow().getData(0)[7];
							dateExpiration=getFormattedDate(dateExpiration);
						}
						
						var username_id = cell.getRow().getData(0)[5]
								.toString();
						var userType_id = cell.getRow().getData(0)[6]
								.toString();

						if (userType_id==2) {
							$("#date_expiration_compte_Edit").show();
							$("#date_expiration_compte_EditUser").attr("required",true);
						} else {
							$("#date_expiration_compte_Edit").hide();
						}
								

						$(".modal-body #user_id").val(user_id);
						$(".modal-body #prenom_id").val(prenom_id);
						$(".modal-body #nom_id").val(nom_id);
						$(".modal-body #email_id").val(email_id);
						$(".modal-body #phone_id").val(phone_id);
						$(".modal-body #username_id").val(username_id);
						$(".modal-body #userType_id").val(userType_id);
						$(".modal-body #date_expiration_compte_EditUser").val(
								dateExpiration);

					}
				} ],
		groupBy : "6",
		groupHeader : function(value, count, data, group) {
			// value - the value all members of this group share
			// count - the number of rows in this group
			// data - an array of all the row data objects in this group
			// group - the group component for the group
			if (value == "1") {
				value = "Admins"

			} else if (value == "2") {
				value = "Client";
			} else {
				value = "User Entreprise";
			}

			return value + "<span style='color:#d00; margin-left:10px;'>("
					+ count + " Utilisateurs)</span>";
		},
		placeholder : "Pas d'utilisateur pour le moment !",
		pagination : "local",
		paginationSize : 10,
		paginationSizeSelector : [ 25, 50, 100 ],
		printAsHtml : true,
	/*
	 * printHeader: "<h1>Example Table Header<h1>", printFooter: "<h2>Example
	 * Table Footer<h2>",
	 */
	});

}
getUsersTabulator();

/*
 * 
 * //trigger download of data.xlsx file document
 * .getElementById("download-xlsx") .addEventListener("click", function () {
 * table.download("xlsx", "data.xlsx", { sheetName: "List Débit" }); });
 * 
 * //trigger download of data.pdf file document .getElementById("download-pdf")
 * .addEventListener("click", function () { table.download("pdf", "data.pdf", {
 * orientation: "portrait", //set page orientation to portrait title: "List
 * Débit", //add title to report }); });
 * 
 * //print button document .getElementById("print-table")
 * .addEventListener("click", function () { table.print(false, true); });
 */

$("#validateForm")
		.submit(
				function(e) {
					e.preventDefault(); // avoid to execute the actual submit of
										// the form to the server and use Ajax
										// instead.

					var form = $(this);
					var url = form.attr("action");
					var form_data = $("#validateForm").serialize();
					showLoader();

					$
							.ajax({
								type : "POST",
								url : url,
								data : form_data, // serializes the form's
													// elements.
								processData : false,
								// contentType: "text",
								dataType : "json",
								success : function(data) {
									$("#semiTransparentDiv").hide();

									if (data.success) {
										getUsersTabulator();

										$('.modal').modal('hide');
										$("body").removeClass("modal-open");
										$("div.modal-backdrop").remove();

										$("#" + data.id)
												.replaceWith(
														'<img src="resources/images/Check_24x24.png" alt="IMG">');

									} else {
										alert("Erreur Serveur Contactez Votre Administrateur");
									}

								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									$("#semiTransparentDiv").hide();
									alert("Erreur Serveur Contactez Votre Administrateur");
								},
							});
				});

$("#DeleteUserForm").submit(function(e) {
	e.preventDefault(); // avoid to execute the actual submit of the form.

	var form = $(this);
	var url = form.attr("action");
	var form_data = $("#DeleteUserForm").serialize();
	showLoader();

	$.ajax({
		type : "POST",
		url : url,
		data : form_data, // serializes the form's elements.
		processData : false,
		// contentType: "text",
		dataType : "json",
		success : function(data) {
			$("#semiTransparentDiv").hide();
			if (data.success) {
				getUsersTabulator();
				$('.modal').modal('hide');
				$("body").removeClass("modal-open");
				$("div.modal-backdrop").remove();

			} else {
				alert("Delete User Failed !");
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Contactez Votre Administrateur");
		},
	});
});

$("#editForm").submit(function(e) {
	e.preventDefault(); // avoid to execute the actual submit of the form.

	var form = $(this);
	var url = form.attr("action");
	var form_data = $("#editForm").serialize();
	showLoader();

	$.ajax({
		type : "POST",
		url : url,
		data : form_data, // serializes the form's elements.
		processData : false,
		// contentType: "text",
		dataType : "text",
		success : function(data) {
			$("#semiTransparentDiv").hide();
		

			if (data == "userupdated") {
				$('.modal').modal('hide');
				$("body").removeClass("modal-open");
				$("div.modal-backdrop").remove();
				getUsersTabulator();
			} else if (data == "usernameexist") {
						
				$('#username_id').on('input', function() {
					$("#username_id").css("border", "");
					var isHidden = document.getElementById("usernameAlertEditUser").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#usernameAlertEditUser").attr("hidden",true);
					}
					
				});
				$("#username_id").css("border", "1px solid red");
            	$("#usernameAlertEditUser").removeAttr("hidden");
            	$("#usernameAlertEditUser").text('Username existe déjà !');


			} else if (data == "emailexist") {
				$("#semiTransparentDiv").hide();
				$('#email_id').on('input', function() {
					$("#email_id").css("border", "");
					var isHidden = document.getElementById("emailAlertEditUser").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#emailAlertEditUser").attr("hidden",true);
					}
					
				});
				$("#email_id").css("border", "1px solid red");
            	$("#emailAlertEditUser").removeAttr("hidden");
            	$("#emailAlertEditUser").text('Email existe déjà !');
	
			} else {
				alert("Erreur Serveur Contactez Votre Administrateur")
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Contactez Votre Administrateur");
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
				var isHidden = document.getElementById("successAlert").hasAttribute("hidden");
            	if (isHidden) {
            		$("#successAlert").removeAttr("hidden");
                	$("#successAlert").text('Utilisateur Ajouté Avec Succès !');
				}
				$("#semiTransparentDiv").hide();
				$('#prenomRegister_id').val('');
				$('#nomRegister_id').val('');
				$('#emailRegister_id').val('');
				$('#phoneRegister_id').val('');
				$('#usernameRegister_id').val('');
				$('#passwordRegister_id').val('');
				$('#password2Register_id').val('');	
				getUsersTabulator();
				
			}
			else if (data=="usernameexist") {
				$("#semiTransparentDiv").hide();					
				$('#usernameRegister_id').on('input', function() {
					$("#usernameRegister_id").css("border", "");
					var isHidden = document.getElementById("usernameAlert").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#usernameAlert").attr("hidden",true);
					}
					
				});
				$("#usernameRegister_id").css("border", "1px solid red");
            	$("#usernameAlert").removeAttr("hidden");
            	$("#usernameAlert").text('Username existe déjà !');
				
			}else{
				$("#semiTransparentDiv").hide();
				$('#emailRegister_id').on('input', function() {
					$("#emailRegister_id").css("border", "");
					var isHidden = document.getElementById("emailAlert").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#emailAlert").attr("hidden",true);
					}
					
				});
				$("#emailRegister_id").css("border", "1px solid red");
            	$("#emailAlert").removeAttr("hidden");
            	$("#emailAlert").text('Email existe déjà !');

			}
			
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			$("#semiTransparentDiv").hide();
			alert("Erreur Serveur Veuillez contacter votre administrateur !");
		},
	});
});



//Show List of Users Div here
$("#gestionUtilisateur").on("click",function(){
	$("#usersListDiv").removeAttr("hidden");

})

// Hide or Show Expiration field depending on user Type
$("#date_expiration_compte_Register").hide();
$("#date_expiration_compte_Edit").hide();
$("#userTypeRegister_id").on("change",function(){
	var userType=$("#userTypeRegister_id").val();
	
	if (userType==2) {
		$("#date_expiration_compte_Register").show();
		$("#date_expiration_compte").attr("required",true);
	} else {
		$("#date_expiration_compte").val('');
		var isHidden = document.getElementById("date_expiration_compte")
		.hasAttribute("required");
		if (isHidden) {
		$("#date_expiration_compte").removeAttr("required");		
	}
		$("#date_expiration_compte_Register").hide();
	}
	
})

	


