
$(document).ready(function() {

	
	function showLoader() {
		document.getElementById("semiTransparentDiv").style.display="block";	
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
				$('#listLogiciel').append(
						'<option>--Selectionnez--</option>');

				for (var i = 0; i < data.length; i++) {
					$('#listLogiciel').append(
							'<option value="' + data[i][0] + '">' + data[i][1]
									+ '</option>');

				}

				// Initialize select2
				//$("#listLogiciel").select2();

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("Erreur Serveur Veuillez contactez votre administrateur !");
			},
		});

	}
	getLogicielList();




	
	function Filevalidation() {
		
		var fi = document.getElementById('file'); 
        // Check if any file is selected. 
        if (fi.files.length > 0) { 
            for (var i = 0; i <= fi.files.length - 1; i++) { 
  
                var fsize = fi.files.item(i).size; 
                var file = Math.round((fsize / 1024)); 
                // The size of the file. 
                if (file >= 10240) { 
                	$("#bigFile").show();
                	$("#bigFile").removeAttr("hidden");
                	$("#bigFile").text('Fichier très grand, SVP Importez un fichier de taille inférieure à 10MB!');
                	$('#saveTicket').attr('disabled', 'disabled');
             
                }else {
                	var isHidden = document.getElementById("bigFile").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#bigFile").attr("hidden");
                		$("#bigFile").hide();
                		$('#saveTicket').removeAttr('disabled');
					}
                	
				} 
             
            } 
        } 
		
	}
	
	function FilevalidationUpdate() {
		
		var fi = document.getElementById('fileUpdate'); 
        // Check if any file is selected. 
        if (fi.files.length > 0) { 
            for (var i = 0; i <= fi.files.length - 1; i++) { 
  
                var fsize = fi.files.item(i).size; 
                var file = Math.round((fsize / 1024)); 
                // The size of the file. 
                if (file >= 10240) { 
                	$("#bigFileUpdate").show();
                	$("#bigFileUpdate").removeAttr("hidden");
                	$("#bigFileUpdate").text('Fichier très grand, SVP Importez un fichier de taille inférieure à 10MB!');
                	$('#saveTicketUpdate').attr('disabled', 'disabled');
             
                }else {
                	var isHidden = document.getElementById("bigFileUpdate").hasAttribute("hidden");
                	if (!isHidden) {
                		$("#bigFileUpdate").attr("hidden");
                		$("#bigFileUpdate").hide();
                		$('#saveTicketUpdate').removeAttr('disabled');
					}
                	
				} 
             
            } 
        } 
		
	}
	
	$("#file").on('change',function() {
		Filevalidation();
		var isHidden = document.getElementById("FileExist").hasAttribute("hidden");
	
    	if (!isHidden) {
    		$("#FileExist").attr("hidden",true);
    		$("#FileExist").hide();	
		}
    	
    
	});
	
	$("#fileUpdate").on('change',function() {
		FilevalidationUpdate();
		var isHidden = document.getElementById("FileExistUpdate").hasAttribute("hidden");
		if (!isHidden) {
    		$("#FileExistUpdate").attr("hidden",true);
    		$("#FileExistUpdate").hide();	
		}
	});

	
	var qs=$('input.search').quicksearch('ul.list-group li');
	var search_response=$('input.search_response').quicksearch('ul.media-list li');
	
	
	
	
	function getTickets(data) {

		$("#nbr_ticket_open").text("0 créés");
		$("#nbr_ticket_assigned").text("0 Assignés");
		//$("#nbr_ticket_closed").text("0 Fermés");
		
		///This is to clear up Ticket list And Close Creation Modal
		$(".list-group").empty();
		//$(".pagination").empty();
		$('.modal').modal('hide');
		$("body").removeClass("modal-open");
		$("div.modal-backdrop").remove();
		//***********************************
		var objTicket=data.ticket;
		var isNotNull=false;
		for(var prop in objTicket) {
		    if(objTicket.hasOwnProperty(prop)) {
		    	isNotNull=true;
		    }
		  }

		if (isNotNull) {
			var nbr_ticket_open=0;
			//var nbr_ticket_closed=0;
			var nbr_ticket_assign=0;
		
			data.ticket.forEach(function(ticket) {

				
				// ***********Function to Format the
				// CreationDateTime************//
				function getFormattedDate(datetoformat, opt) {
					var date = new Date(datetoformat);
					switch (opt) {
					case "ticket":
						 var str = date.getFullYear() + "-" + String(date.getMonth() + 1).padStart(2, '0') + "-" + String(date.getDate()).padStart(2, '0') + " " +  String(date.getHours()).padStart(2, '0') + ":" + String(date.getMinutes()).padStart(2, '0');
						break;
					case "resp":
						 var str = date.getFullYear() + "-" + String(date.getMonth() + 1).padStart(2, '0') + "-" + String(date.getDate()).padStart(2, '0') + " " +  String(date.getHours()).padStart(2, '0') + ":" + String(date.getMinutes()).padStart(2, '0');
						break;

					default:
						 var str = String(date.getDate()).padStart(2, '0') + "/" + String((date.getMonth() + 1)).padStart(2, '0') + "/" + date.getFullYear();
						break;
					}
				   
				    return str;
				}
			
				var date=getFormattedDate(ticket[5],"ticket");
				// ****************************************************************//
				

				var urlFile=$("#urlFile").val();
				

				var item=`<li id="backgroundTicket-${ticket[0]}" class="list-group-item list-group-item-success">
									<div class="media">
										<i class="fa fa-file-o pull-left"></i>
										<div class="media-body">
											<strong class="text-dark">${ticket[1]}</strong> <span class="${ticket[0]}" >${ticket[4]}</span><span
												class="number pull-right"># ${ticket[0]}</span>
											<p class="info">
												créé par: <strong class="text-dark"> @${ticket[15]+"."+ticket[8]}</strong> le: ${date} <i
													class="fa fa-comments"></i> <a id="responseBtn-${ticket[0]}"
													class="btn  btn-info btn-sm" href="#" role="button" data-toggle="modal"	data-target="#issue">
													Réponses</a>
												<button id="responseHistory-${ticket[0]}" class="btn btn-sm btn-info " data-toggle="modal" data-target="#responseHistory" hidden><i class="fa fa-comments" style="pointer-events: none;"></i> Historique</button>
											</p>
										</div>
									</div>
									<button id="validate_ticket" class="btn btn-sm btn-success" hidden>Valider</button>
									<button id="edit-${ticket[0]}" class="btn btn-sm btn-info " data-toggle="modal" data-target="#updateIssue" hidden><i class="fa fa-edit" style="pointer-events: none;"></i>Edit</button>
									<form action="/Helpdesk/DownloadAttachment" method="GET">
									<input type="text" name="path" id="path-${ticket[0]}"
														value="${ticket[7]}" hidden/>
									<button id="attachment-${ticket[0]}" type="submit" class="btn btn-warning btn-sm pull-right" data-toggle="tooltip" title="Télécharger Attachement" hidden download><i class="fa fa-download"></i></button>
									</form>
									<!--<a id="attachment-${ticket[0]}" href="${urlFile}" class="btn btn-warning btn-sm pull-right" data-toggle="tooltip" title="Télécharger Attachement" hidden download><i class="fa fa-download"></i></a>-->
									<button id="planif-${ticket[0]}" class="btn btn-sm btn-info pull-right mx-1 " data-toggle="tooltip" title="Planifier Tiquet" hidden><i class="fa fa-calendar" style="pointer-events: none;"></i></button>
								</li>`;
				
				$('.list-group').append(item);
				
				//get user permission here if UserEntreprise/Admin show Planification Button
			
			    if ($("#userPermission").val()==3 || $("#userPermission").val()==1) {
			    	$("#listPlanifBtn").removeAttr("hidden");
//			    	if (ticket[3]!=="fermer") {
//			    		$("#planif-"+ticket[0]+"").removeAttr("hidden");
//			    				
//					}			    		    	
			    	
				}

			    
			    if ($("#userPermission").val()==1 || $("#userPermission").val()==2 || $("#usersession").val()===ticket[16]) {

			    	$("#edit-"+ticket[0]+"").removeAttr("hidden");
				}
			    
	
				switch (ticket[4]) {
				case "Critique":
					
					$("."+ticket[0]+"").addClass("badge badge-pill badge-danger");
					
					break;
                case "Moyen":
                
                	$("."+ticket[0]+"").addClass("badge badge-pill badge-warning");
					
					break;
                case "Normale":
                	
                	$("."+ticket[0]+"").addClass("badge badge-pill badge-success");
					
					break;

				default:
					break;
				}
				
				if(ticket[3]=="créé") {
					nbr_ticket_open++;
					$("#backgroundTicket-"+ticket[0]+"").addClass("list-group-item-success");
				}
//				else if(ticket[3]=="fermer") {
//					nbr_ticket_closed++;
//					$("#backgroundTicket-"+ticket[0]+"").addClass("list-group-item-danger");
//					$("#responseBtn-"+ticket[0]+"").hide();
//		    		$("#responseHistory-"+ticket[0]+"").removeAttr("hidden");
//					
//				}
				else {
					nbr_ticket_assign++;
					$("#backgroundTicket-"+ticket[0]+"").addClass("list-group-item-info");
					
				}
				if (ticket[7]!=null) {
					$("#attachment-"+ticket[0]+"").removeAttr("hidden");
					
				}
				
		
				// ****** When Clicking on Ticket Reponses This Happens
				// ***************//
				
				$("#responseBtn-"+ticket[0]+"").click(function (e) {
					var search_response=$('input.search_response').quicksearch('ul.media-list li');
					
					var ticket_id=$((e.target)).attr('id');
					ticket_id=""+ticket_id+"";
					ticket_id = ticket_id.replace(/[^0-9]/g,'');					
			
					var ticket_detail_header=`
					<p>
					  Tiquet <strong class="text-dark">#${ticket[0]}</strong> créé par <strong class="text-dark"> @${ticket[15]+"."+ticket[8]}</strong>
					   le: ${date}
					</p>
				    <p>${ticket[2]}</p>	
					`;
					$( "#ticket_detail_header p" ).remove();
					$(".modal .media-list li").remove();
					$('.response_list div').remove();
					$('.ticket_list div').remove();
					$('#ticket_detail_header').append(ticket_detail_header);
					$(".modal #ticket_id").val(ticket_id);
					$(".modal #responseDetail").val('');
					
						    
					
					showLoader();
					
					
					// ************ Get Responses By Ticket 
					// Id****************//
					
					$.ajax({
						type: "GET",
						url: "/Helpdesk/ResponseManagement",
						data: { 
						    ticket_id: ticket_id,
						    action: "/getResponses"
						  },
						dataType: "json",
						
						success: function (data) {
							$("#semiTransparentDiv").hide();
					
							var objResponse=data.responses;
							var userResponseSession;
							
							var isNotNull=false;
							for(var prop in objResponse) {
							    if(objResponse.hasOwnProperty(prop)) {
							    	isNotNull=true;
							    }
							  }
							
							if (isNotNull) {
				
								
								objResponse.forEach(function(response) {
								    
								    var date_creation_response=getFormattedDate(response[2],"resp");
								  
									var ticket_response=`
									
									<li class="media">
										   <div id="review"></div>
												<div class="media-body">
												
													<span class="text-muted pull-right"> <small
														class="text-muted">Réponse le: ${date_creation_response}</small>
													</span> <strong class="text-success">  @${response[3]+" "+response[4]}</strong>
													<p>
														${response[1]}
														
													</p>
												</div>
									
								     </li>`;
									
									$( ".media-list" ).append(ticket_response);
									
								});
								search_response.cache();
							}else {

								$('.response_list').append('<div class="alert alert-warning" role="alert">Pas de Réponses pour le moment !</div>');
							}

						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
							$("#semiTransparentDiv").hide();
							alert("Erreur Serveur Contactez Votre Administrateur");
						},
					});
					// *****************************************************************//
				
					
					

				});
				// *************************************************************//
				
				
				// ****** When Clicking on Ticket History This Happens
				// ***************//
				
				$("#responseHistory-"+ticket[0]+"").click(function (e) {
					var search_response=$('input.search_response').quicksearch('ul.media-list li');
					
					var ticket_id=$((e.target)).attr('id');
					ticket_id=""+ticket_id+"";
					ticket_id = ticket_id.replace(/[^0-9]/g,'');					
			
					var ticket_detail_header=`
					<p>
					  Tiquet <strong class="text-dark">#${ticket[0]}</strong> créé par <strong class="text-dark"> @${ticket[15]+"."+ticket[8]}</strong>
					   le: ${date}
					</p>
				    <p>${ticket[2]}</p>	
					`;
					$( "#ticket_detail_header_history p" ).remove();
					$(".modal #historyList li").remove();
					$('.response_list div').remove();
					$('.ticket_list div').remove();
					$('#ticket_detail_header_history').append(ticket_detail_header);
				
					
					showLoader();
					
					
					// ************ Get Responses By Ticket 
					// Id****************//
					
					$.ajax({
						type: "GET",
						url: "/Helpdesk/ResponseManagement",
						data: { 
						    ticket_id: ticket_id,
						    action: "/getResponses"
						  },
						dataType: "json",
						
						success: function (data) {
							$("#semiTransparentDiv").hide();
					
							var objResponse=data.responses;
							var userResponseSession;
							
							var isNotNull=false;
							for(var prop in objResponse) {
							    if(objResponse.hasOwnProperty(prop)) {
							    	isNotNull=true;
							    }
							  }
							
							if (isNotNull) {
				
								
								objResponse.forEach(function(response) {
								    
								    var date_creation_response=getFormattedDate(response[2],"resp");
								  
									var ticket_response=`
									
									<li class="media">
										   <div id="review"></div>
												<div class="media-body">
												
													<span class="text-muted pull-right"> <small
														class="text-muted">Réponse le: ${date_creation_response}</small>
													</span> <strong class="text-success">  @${response[3]}</strong>
													<p>
														${response[1]}
														
													</p>
												</div>
									
								     </li>`;
									
									$( "#historyList" ).append(ticket_response);
									
								});
								search_response.cache();
							}else {

								$('.response_list').append('<div class="alert alert-warning" role="alert">Pas de Réponses pour le moment !</div>');
							}

						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
							$("#semiTransparentDiv").hide();
							alert("Erreur Serveur Contactez Votre Administrateur");
						},
					});
					// *****************************************************************//
				
					
					

				});
				// *************************************************************//
				
				
			// ************* Click on Edit Ticket******************////
				$("#edit-"+ticket[0]+"").click(function (e) {
					
					
					var ticket_id=$((e.target)).attr('id');
					ticket_id=""+ticket_id+"";
					ticket_id = ticket_id.replace(/[^0-9]/g,'');
					$(".modal #ticket_id").val(ticket_id);
					$(".modal #objet").val(ticket[1]);
					$(".modal #detail").val(ticket[2]);
					$(".modal #severity").val(ticket[4]);
					
					if (ticket[3]=="assigné") {
						$(".modal #etat_ticket").remove("option");
						$(".modal #etat_ticket").html("<option>fermer</otion><option>assigné</option>");
						$(".modal #etat_ticket").val(ticket[3]);
					} else {
						$(".modal #etat_ticket").remove("option");
						$(".modal #etat_ticket").html("<option>créé</otion><option>fermer</option>");
						$(".modal #etat_ticket").val(ticket[3]);

					}
					
					
					
				});
									
			// ******************************************************//
				
		//******************** PLANIFICATION ***************************//		
				$("#planif-"+ticket[0]+"").click(function (e) {
				
					
					$("#PlanifModal").modal("show");
				
					///****** Call DatePicker
					$('#PlanifModal').on('shown.bs.modal', function(e) {
						$('.date-input').datepicker({ 
							ClearBtn: true,
							format: 'dd/mm/yyyy',
							});
					});
					
					
					
					///*** Get Ticket Id
					var ticket_id=$((e.target)).attr('id');
					ticket_id=""+ticket_id+"";
					ticket_id = ticket_id.replace(/[^0-9]/g,'');
					$(".modal #ticket_id").val(ticket_id);	
					$("#date_debut_planif").val('');
				    $("#date_fin_planif").val('');
				    $("#date_debut_realise").val('');
				    $("#date_fin_realise").val('');
				    $("#observation").val('');
				    $('#date_debut_planif').attr('placeholder', 'Date Début Planification');
				    $('#date_fin_planif').attr('placeholder', 'Date Fin Planification');
				    $('#date_debut_realise').attr('placeholder', 'Date Début Réalisation');
				    $('#date_fin_realise').attr('placeholder', 'Date Fin Réalisation');
				    $("#date_debut_planif").css("border", "");
					$("#date_fin_planif").css("border", "");
					$("#date_debut_realise").css("border", "");
					$("#date_fin_realise").css("border", "");
				    
				    showLoader();
					//Initialize The Form
					
				      $.ajax({
							
								type: "GET",
								url: "/Helpdesk/TicketManagement",
								data: { 
								    ticket_id: ticket_id,   
								    action: "/getPlanifByTicket"
								  },
					
								dataType: "json",
								success: function (data) {
									$("#semiTransparentDiv").hide();
									var objPlanif=data.ticketPlanif;
									var isNotNull=false;
									for(var prop in objPlanif) {
									    if(objPlanif.hasOwnProperty(prop)) {
									    	isNotNull=true;
									    }
									  }
									if (isNotNull) {
										//Init Planif Form Here
										var dateDebutPlanif;
										var dateFinPlanif;
										var dateDebutRealise;
										var dateFinRealise;
										$.each(objPlanif, function(key, value) {
											
											dateDebutPlanif=getFormattedDate(value.dateDebutPlanif,"");
											dateFinPlanif=getFormattedDate(value.dateFinPlanif,"");
											dateDebutRealise=value.dateDebutRealise;
											dateFinRealise=value.dateFinRealise;
										    $("#date_debut_planif").val(dateDebutPlanif);
										    $("#date_fin_planif").val(dateFinPlanif);
										    if (dateDebutRealise!=undefined && dateFinRealise!=undefined) {
										    	    $("#date_debut_realise").val(getFormattedDate(value.dateDebutRealise,""));
												    $("#date_fin_realise").val(getFormattedDate(value.dateFinRealise,""));
											}
										   
										    $("#observation").val(value.observation);
										    
										});
									

										
									} else{
										console.log("No planification for this Ticket !");
									}
								},
								error: function (XMLHttpRequest, textStatus, errorThrown) {
									$("#semiTransparentDiv").hide();
									alert("Erreur Serveur Contactez Votre Administrateur");
								},
						});
						
					
				
				});
	
			//********************************************************************************************//	
		

			});
			qs.cache();
			
			
	        /////////////************* Pagination Tickets List Here *************//////////////
	 	    
          function paginationFunc(){
        	    //$(".pagination").empty();
				var numberOfItems = $(".list-group .list-group-item").length;
				var limitPerPage=5;
				/// Hide all The Other <li> element but the very first 5 
				$(".list-group .list-group-item:gt("+(limitPerPage-1)+")").hide();
				/// Here we calculate the number of pages needed
				var totalPages= Math.ceil(numberOfItems/limitPerPage);
				$('.pagination').twbsPagination({
			        totalPages: totalPages,
       		        visiblePages: 3,
			        first:'Début',
			        prev:'<span aria-hidden="true">&laquo;</span>',
			        next:'<span aria-hidden="true">&raquo;</span>',
			        last:'Fin',
			        onPageClick: function (event, page) {
						
						var currentPage=page; 
						$(".list-group .list-group-item").hide();
						
						var grandTotal=limitPerPage*currentPage;
						for (var i = grandTotal-limitPerPage; i < grandTotal; i++) {
							$(".list-group .list-group-item:eq("+i+")").show();
							
						}
					
				
			        }
			    });		
						
          }	
           paginationFunc();
 	
			//////////**********************End Pagination***********************///////////
			
			// Set number ticket here
			$("#nbr_ticket_open").text(nbr_ticket_open+" créés");
			//$("#nbr_ticket_closed").text(nbr_ticket_closed+" Fermés");
			$("#nbr_ticket_assigned").text(nbr_ticket_assign+" Assignés");
	

		} else {

			$('.ticket_list').append('<div class="alert alert-warning" role="alert">Pas de Tiquets pour le moment !</div>');
		}
		
	}
	

	function RefreshPage() {
		$('.ticket_list div').hide();
		showLoader();
		
		   ///*********************     Get Tickets     ***************///
		$.ajax({
			type : "GET",
			url : "/Helpdesk/TicketManagement",
			data: { 
			    usersession: $("#usersession").val(), 
			    action: "/getTickets"
			  },
			dataType : "json",
			success : function (data) {
				$("#semiTransparentDiv").hide();
				getTickets(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert("Erreur Serveur Contactez Votre Administrateur");
			},
		});
		///**************************************************************///
	
	}
	RefreshPage();
	

	
	///******** Check UserPermission ***************///

	if ($("#userPermission").val()==1) {
		tabsToAdd=`<li class="active open"><a href="panneauAdmin_new.jsp"><i
		class="zmdi zmdi-home"></i><span>Tableau de Bord</span></a></li>		
		<li><a href="Users&Contacts.jsp"><i
				class="zmdi zmdi-account-box-mail"></i><span>Utilisateurs &
					Contacts</span></a></li>
		<li><a href="Statistics.jsp"><i
				class="zmdi zmdi-chart"></i><span>Statistiques</span></a></li>
				<li><a href="ClosedTickets.jsp"><i
				class="zmdi zmdi zmdi-folder-star-alt"></i><span>Tiquets Fermés</span></a></li>
				`;
		$("ul.list").append(tabsToAdd);
		
		
	}else {
	tabsToAdd=`<li class="active open"><a href="mainTemplate_new.jsp">
	<i class="zmdi zmdi-home"></i><span>Tableau de Bord</span></a></li>
	<li><a href="ClosedTickets.jsp"><i
				class="zmdi zmdi-folder-star-alt"></i><span>Tiquets Fermés</span></a></li>
				`;
		$("ul.list").append(tabsToAdd);
	}
	///*********************************************///
	
	// /******** Filtre Tickets ***************///
	$('#filtreTicket').change(function() {
		$(".list-group").empty();
		//$(".pagination li").remove();
		showLoader();
		
      $.ajax({
			
				type: "GET",
				url: "/Helpdesk/TicketManagement",
				data: { 
				    filtre: $("#filtreTicket").val(),   
				    usersession: $("#usersession").val(),
				    action: "/filtreTickets"
				  },
	
				dataType: "json",
				success: function (data) {
					$("#semiTransparentDiv").hide();
					
					var objTicket=data.ticket;
					var isNotNull=false;
					for(var prop in objTicket) {
					    if(objTicket.hasOwnProperty(prop)) {
					    	isNotNull=true;
					    }
					  }
					if (isNotNull) {
						$('.ticket_list').hide();
						getTickets(data);
						
					} 
					
					else {
						$('.ticket_list').append('<div class="alert alert-warning" role="alert">Pas de Tiquets pour le moment !</div>');
						
					}
								
	
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					$("#semiTransparentDiv").hide();
					alert("Erreur Serveur Contactez Votre Administrateur");
				},
		});
		
	});
	///*********************************************///
	
	

	
// ****************** Submit New Ticket *******************************//
	$("#ticketForm").submit(function(e) {
		e.preventDefault(); // avoid to execute the actual submit of the form to
		// the server and use Ajax instead.

		var form = $(this);
		var url = form.attr("action");
		var formData = new FormData($(this)[0]);
		showLoader();

		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : url,
			data : formData, // serializes the form's elements.
			processData : false,
			contentType: false,
			
			dataType : "json",
			success : function(data) {
				$("#semiTransparentDiv").hide();
				if (data.success==="true") {
					
					RefreshPage();
					
					flash('Tiquet Créé Avec Succès', {

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

				} else {
					$("#semiTransparentDiv").hide();
					$("#FileExist").show();
                	$("#FileExist").removeAttr("hidden");
                	$("#FileExist").text('Fichier existe déjà!');
                	
                	
				}

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert("Erreur Serveur Contactez Votre Administrateur");
			},
		});
	});
	
	// ***************************************************************************//
	// *************************** Submit Response ***************************//
	$("#responseForm").submit(function(e) {
		e.preventDefault(); // avoid to execute the actual submit of the form to
		var form = $(this);
		var form_data = $("#responseForm").serialize();
		showLoader();
		$.ajax({
			
			type: "POST",
			url: form.attr("action"),
			data: form_data, // serializes the form's elements.

			dataType: "json",
			success: function (data) {
				$("#semiTransparentDiv").hide();
					
				if (data.success==="true") {
					RefreshPage();
				}else {
					alert("Erreur Serveur Contactez Votre Administrateur");
				}

			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert("Erreur Serveur Contactez Votre Administrateur");
			},
		});
	});
	// *********************************************************************//
	// *************************** Update Ticket ***************************//
	$("#updateTicketForm").submit(function(e) {
		e.preventDefault(); // avoid to execute the actual submit of the form to
		
		var form = $(this);
		var url = form.attr("action");
		var formData = new FormData($(this)[0]);
		showLoader();

		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : url,
			data : formData, // serializes the form's elements.
			processData : false,
			contentType: false,

			dataType: "json",
			success: function (data) {
				
				$("#semiTransparentDiv").hide();
				if (data.success==="true") {
					
					RefreshPage();
				}else {
					
					$("#semiTransparentDiv").hide();
					$("#FileExistUpdate").show();
                	$("#FileExistUpdate").removeAttr("hidden");
                	$("#FileExistUpdate").text('Fichier existe déjà!');
				}

			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert("Erreur Serveur Contactez Votre Administrateur");
			},
		});
	});
	// *********************************************************************//
	
	// ****************** Planify Ticket *******************************//
	$("#planifForm").submit(function(e) {
		e.preventDefault(); 
		
		var form = $(this);
		var url = form.attr("action");
		var form_data = $("#planifForm").serialize();
		//Validate Planification Dates here
		
		showLoader();

		$.ajax({
			type : "POST",
			url : url,
			data : form_data, // serializes the form's elements.
			processData : false,
		
			
			dataType : "json",
			success : function(data) {
				
				$("#semiTransparentDiv").hide();
				if (data.success==="true") {
					
					$("#ticket_id").val('');
					$("#action").val('');
					$("#date_debut_planif").val('');
					$("#date_fin_planif").val('');
					$("#date_debut_realise").val('');
					$("#date_fin_realise").val('');
					$("#observation").val('');
					
					
					//***** Close Modal *****************///
					$('.modal').modal('hide');
					$("body").removeClass("modal-open");
					$("div.modal-backdrop").remove();
					//***********************************
					
					flash('Planification Terminée Avec Succès', {

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

				} else {
					$("#ticket_id").val('');
					$("#action").val('');
					$("#date_debut_planif").val('');
					$("#date_fin_planif").val('');
					$("#date_debut_realise").val('');
					$("#date_fin_realise").val('');
					$("#observation").val('');
					//***** Close Modal *****************///
					$('.modal').modal('hide');
					$("body").removeClass("modal-open");
					$("div.modal-backdrop").remove();
					//***********************************
					flash('Tiquet Déja Planifier Voir Liste Planification !', {

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
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#semiTransparentDiv").hide();
				alert("Erreur Serveur Contactez Votre Administrateur");
			},
		});
	});
	
	// ***************************************************************************//
	
	//////**************************** Planification List ******************************//////
	$("#listPlanifBtn").click(function(){
		//create Tabulator on DOM element with id "example-table"
		var table = new Tabulator("#listPlanif", {
			height : "100%", // set height of table (in CSS or here), this enables
								// the Virtual DOM and improves render speed
								// dramatically (can be any valid css height value)
			//data:get_data, //assign data to table
			locale:true,
		    langs:{
		        "fr-fr":{
//		            "columns":{
//		                "name":"Name", //replace the title of column name with the value "Name"
//		            },
		            "ajax":{
		                "loading":"Chargement ...", //ajax loader text
		                "error":"Erreur", //ajax error text
		            },
//		            "groups":{ //copy for the auto generated item count in group header
//		                "item":"item", //the singular  for item
//		                "items":"items", //the plural for items
//		            },
		            "pagination":{
		                "first":"Premier", //text for the first page button
		                "first_title":"Premièr Page", //tooltip text for the first page button
		                "last":"Dernier",
		                "last_title":"Dernièr Page",
		                "prev":"Précédent",
		                "prev_title":"Page Précédente",
		                "next":"Suivant",
		                "next_title":"Page Suivante",
		            },
//		            "headerFilters":{
//		                "default":"filter column...", //default header filter placeholder text
//		                "columns":{
//		                    "name":"filter name...", //replace default header filter text for column name
//		                }
//		            }
		        }
		    },
			ajaxURL : "/Helpdesk/TicketManagement",
			ajaxParams:{action:"/getPlanifications",usersession: $("#usersession").val(),},
			ajaxResponse:function(url, params, response){
		        //url - the URL of the request
		        //params - the parameters passed with the request
		        //response - the JSON object returned in the body of the response.
				
				

		        return response.planifList; //return the tableData property of a response json object
		    },
		  
			ajaxError : function(xhr, textStatus, errorThrown) {
				alert("Error Occured !");
			},
			// layout:"fitDataStretch", //fit columns to width of table (optional)
			layout : "fitColumns",

			columns : [
			// Define Table Columns
				
			{
				title: "Tiquet",
				field:"0" ,
				headerFilter : true,
				headerFilterPlaceholder:"Recherche"
				//mutator:ticketId,
			
			},
				{
				title: "Client",
				field:"1" ,
				headerFilter : true,
				headerFilterPlaceholder:"Recherche"
				//mutator:ticketId,
			
			},
			{
				title : "D.D.Planif",
				field : "2",
				formatter : "datetime",
				formatterParams : {		
					outputFormat : "YYYY-MM-DD",
					invalidPlaceholder : "(PAS DE DATE)",
				},
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",
			}, {
				title : "D.F.Planif",
				field : "3",
				formatter : "datetime",
				formatterParams : {		
					outputFormat : "YYYY-MM-DD",
					invalidPlaceholder : "(PAS DE DATE)",
				},
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",
			}, {
				title : "D.D.Realise",
				field : "4",
				formatter : "datetime",
				formatterParams : {		
					outputFormat : "YYYY-MM-DD",
					invalidPlaceholder : "(PAS DE DATE)",
				},
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",

			}, {
				title : "D.F.Realise",
				field : "5",
				formatter : "datetime",
				formatterParams : {		
					outputFormat : "YYYY-MM-DD",
					invalidPlaceholder : "(PAS DE DATE)",
				},
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",

			},
			
			{
				title: "Etat",
				field:"6" ,
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",
				
			
			},
			{
				title : "Observation",
				field : "7",
				headerFilter : true,
				headerFilterPlaceholder:"Recherche",
			},
			
			
		  ],
			//groupBy : "6",
//			groupHeader:function(value, count, data, group){
//			    //value - the value all members of this group share
//			    //count - the number of rows in this group
//			    //data - an array of all the row data objects in this group
//			    //group - the group component for the group
//				if (value=="1") {
//					value="Admins"
//					
//				}else if(value=="0") {
//					value="Utilisateurs Non Validés"
//				}else if(value=="2"){
//					value="Client";	
//				}else{
//					value="User Entreprise";	
//				}
	//
//			    return value + "<span style='color:#d00; margin-left:10px;'>(" + count + " Users)</span>";
//			},
			placeholder : "Pas de planification pour le moment !",
			pagination : "local",
			paginationSize : 10,
			paginationSizeSelector : [25, 50, 100],
			printAsHtml : true,
			
		/*
		 * printHeader: "<h1>Example Table Header<h1>", printFooter: "<h2>Example
		 * Table Footer<h2>",
		 */
		});
		$("#download-xlsx").click(function() {
			table.download("xlsx", "ListePlanification.xlsx", { sheetName: "Liste Planification" });
		});
		
		$("#download-pdf").click(function() {
			 table.download("pdf", "ListePlanification.pdf", {
			        orientation:"portrait", //set page orientation to portrait
			        title:"ListePlanification", //add title to report
			    });
		})
		});
    
	
	////***********************************************************************************////
	

	// initialize Rating
	$("#review").rating();
	
	//Clear Search and back to first page 
	$("#search").keydown(function(){
			$(".last a").click();
			$(".first a").click();
		
	});
	
	$("#listLogiciel").on("change",function(){
		   var logiciel_id=$("#listLogiciel").val();
		  if (logiciel_id!=="--Selectionnez--") {
				$.ajax({
					type : "GET",
					url : "/Helpdesk/Settings",
					data : {
						logiciel_id:logiciel_id,
						action : "/getVersionListByLogiciel"
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
						//$("#listVersion").select2();

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("Erreur Serveur Veuillez contactez votre administrateur !");
					},
				});
			
		}else {
			$("#listVersion").empty();
			}

		
	});
	

	/**
	   * Option dropdowns. Slide toggle
	   */
	  $(".option-heading").on('click', function() {
	    $(this).toggleClass('is-active').next(".option-content").stop().slideToggle(500);
	  });
	  
	
	

});