
$(document).ready(function() {

	
	function showLoader() {
		document.getElementById("semiTransparentDiv").style.display="block";	
		}
	
	 var detailsTicket = function(cell, formatterParams) {
			
			return '<button class="btn btn-primary btn-sm" data-toggle="tooltip" title="Détails du Tiquet"><i class="fa fa-info-circle fa-sm" style="color:white"></i></button>'

		};
		
var responsesTicket = function(cell, formatterParams) {
	   var ticket_id = cell.getRow().getData(0)[0].toString();	
			return `
			<button
			class="btn btn-sm btn-info">
			<i class="fa fa-comments" style="pointer-events: none;"></i> Historique</button>`;

		};
		
  var attachment = function(cell, formatterParams) {
 	 var ticket_id = cell.getRow().getData(0)[0].toString();
 	 var attachPath = cell.getRow().getData(0)[7];
 	 if (attachPath!= null) {
 		 attachPath = cell.getRow().getData(0)[7].toString();
 		 return `<form action="/Helpdesk/DownloadAttachment" method="GET">
			<input type="text" name="path" id="path-${ticket_id}"
				value="${attachPath}" hidden/>
			<button id="attachment-${ticket_id}" type="submit" class="btn btn-warning btn-sm pull-right" data-toggle="tooltip" title="Télécharger Attachement"  download><i class="fa fa-download"></i></button>
			</form>`;

		  }
			
		};
		
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
				 var str = date.getFullYear() + "-" +  String(date.getMonth() + 1).padStart(2, '0') + "-" + String(date.getDate()).padStart(2, '0') + " " +  date.getHours() + ":" +String(date.getMinutes()).padStart(2, '0');;
				break;
			}
		   
		    return str;
		}
	
	 function getTicketsTabulator() {
			
			
			var table = new Tabulator("#closedTickets", {
				height : "100%", 		
				locale:true,
			    langs:{
			        "fr-fr":{

			            "ajax":{
			                "loading":"Chargement ...", //ajax loader text
			                "error":"Erreur", //ajax error text
			            },

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

			        }
			    },
			    
				ajaxURL : "/Helpdesk/TicketManagement",
				ajaxParams:{
					usersession: $("#usersession").val(), 
				    action: "/getClosedTickets"
				    },
				ajaxContentType : "application/json; charset=utf-8",
				ajaxContentType : "json",
				ajaxResponse : function(url, params, response) {
					// url - the URL of the request
					// params - the parameters passed with the request
					// response - the JSON object returned in the body of the response.

					
					var nbr_fermer = 0;
				
					var percent_closed=0;
				
					var Total_Ticket=response.ticket.length;
					response.ticket.forEach(function(ticket) {
						
						if (ticket[3] == "fermer") {
							nbr_fermer++;

						} 

					});
		            // Dump each Ticket number to The Interface.

					$("#nbr_fermer").text(nbr_fermer);
					
					
					if (Total_Ticket!=0) {
						// Dump each Ticket percentage to The Interface.
						
						percent_closed=Math.round((nbr_fermer*100)/Total_Ticket);
					
					} 
					
					
				
					$("#percent_closed_progress").css("width", percent_closed*2);	
					$("#percent_closed").text(percent_closed+"%");
				
					

					return response.ticket; // return the tableData property of a
												// response json object
				},
				ajaxError : function(xhr, textStatus, errorThrown) {
					alert("Error Occured !" + errorThrown);
				},
				// layout:"fitDataStretch", //fit columns to width of table (optional)
				layout : "fitColumns",

				columns : [
					
					// Define Table Columns
					{
						title : "N° Ticket",
						field : "0",
						headerFilter : true,
						headerFilterPlaceholder:"Recherche",
					},
					
					// Define Table Columns
					{
						title : "Client/User",
						field : "8",
						headerFilter : true,
						headerFilterPlaceholder:"Recherche",
					},
				// Define Table Columns
				{
					title : "Objet",
					field : "1",
				// headerFilter : true
				},
				{
					formatter : detailsTicket,
					align : "center",
					title : "Détails",
					headerSort : false,
					cellClick : function(e, cell) {
						///********* Here Add All The Ticket Information That you want to be added **********///
						
						var ticket_info;
						var creationDate=getFormattedDate(cell.getRow().getData(0)[5].toString());
						var ticketCreator=cell.getRow().getData(0)[15].toString()+"."+cell.getRow().getData(0)[8].toString();
						var logiciel=cell.getRow().getData(0)[9].toString();
						var version=cell.getRow().getData(0)[10].toString();
					
						$("#Details").modal("show");
					   // Clear closing_info div from any paragraph first
						$("#ticket_info").empty();
						
							
						var closingDate=getFormattedDate(cell.getRow().getData(0)[13].toString());
						var ticketCloser=cell.getRow().getData(0)[12].toString();
						ticket_info=`<p>créé par: <strong>@${ticketCreator}</strong>, le: <strong>${creationDate}</strong></p>
						 <p>Fermé par: <strong>@${ticketCloser}</strong> le: <strong>${closingDate}</strong></p>
						 <p>Logiciel & Version: <strong>${logiciel}, ${version}</strong> </p>`;
						$("#ticket_info").html(ticket_info);
	
						$("#putDetail").text(cell.getRow().getData(0)[2].toString());
						
						///*********************************************************************************///

					}
				}

				, {
					title : "Etat",
					field : "3",
					headerFilter : true,
					headerFilterPlaceholder:"Recherche",

				}, {
					title : "Sévérité",
					field : "4",
					headerFilter : true,
					headerFilterPlaceholder:"Recherche",

				}, {
					title : "Date Création",
					field : "5",
					headerFilter : true,
					headerFilterPlaceholder:"Recherche",
					formatter : "datetime",
					formatterParams : {		
						outputFormat : "YYYY-MM-DD",
						invalidPlaceholder : "(invalid date format)",
					}
				}, {
					title : "créé Par",
					field : "6",
					visible : false,
				},
	
	 				
	 				{
	 					formatter : attachment,
	 	             	align : "center",
	 					width : 60,
	 					headerSort : false,
	 	 				},
	 	 				{
	 	 					formatter : responsesTicket,
	 	 	             	align : "center",
	 	 					headerSort : false,
	 	 					cellClick : function(e, cell) {
	 	 						
	 	 						$("#responseHistory").modal("show");
	 	 						
	 	 						var search_response=$('input.search_response').quicksearch('ul.media-list li');
	 	 						
	 	 						var ticket_id=cell.getRow().getData(0)[0].toString();
	 	 						var prenom="";
	 	 						if (cell.getRow().getData(0)[15]!=null) {
	 	 							 prenom=cell.getRow().getData(0)[15].toString();
								}
	 	 						var nom=cell.getRow().getData(0)[8].toString();
	 	 						var date=getFormattedDate(cell.getRow().getData(0)[5].toString(),"ticket");
	 	 						var details=cell.getRow().getData(0)[2].toString();
	 	 						
	 	 						
	 	 				
	 	 						var ticket_detail_header=`
	 	 						<p>
	 	 						  Tiquet <strong class="text-dark">#${ticket_id}</strong> créé par <strong class="text-dark"> @${prenom+"."+nom}</strong>
	 	 						   le: ${date}
	 	 						</p>
	 	 					    <p>${details}</p>	
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
	 	 														</span> <strong class="text-success">  @${response[3]+" "+response[4]}</strong>
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
	 	 	
	 	 						
	 	 					}
	 	 	 				},

				],
				
				placeholder : "Pas de Ticket Pour le Moment !",
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
	 getTicketsTabulator();
	

	
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
				class="zmdi zmdi-folder-star-alt"></i><span>Tiquets Fermés</span></a></li>
				`;
		$("ul.list").append(tabsToAdd);
		
		
	}else {
	tabsToAdd=`<li class="active open"><a href="mainTemplate_new.jsp">
	<i class="zmdi zmdi-home"></i><span>Tableau de Bord</span></a></li>
	<li><a href="ClosedTickets.jsp"><i
				class="zmdi zmdi-account-box-mail"></i><span>Tiquets Fermés</span></a></li>
				`;
		$("ul.list").append(tabsToAdd);
	}
	///*********************************************///

	

});