	var action;
	var userPermission=$("#userPermission").val();
	if (userPermission==="1") {
		action="/getCreatedTickets";
	} else if(userPermission==="2"){
		action="/getResponsesOnTickets";
	}else {
		action="/getAssign&Responses";	
	}
	
//setInterval(function(){
	

$.ajax({
		type : "GET",
		url : "/Helpdesk/TicketManagement",
		data : {
			usersession:$("#usersession").val(),
			action : action
		},

		dataType : "json",
		success : function(data) {
			//Empty Notifications List First
			$('.list-unstyled').empty();
			
			var ids_cree=data.createdTickets;
			var ids_response=data.responses;
			var ids_assign=data.assignedTickets;
			
			
			
			var html_element;
			var list_numero_cree="Numéro: ";
			var list_numero_response="Numéro: ";
			var list_numero_assign="Numéro: ";
		
			
			if (userPermission==="1") {
				//get responses notification on the tickets created by the admin
				var nbr_cree=ids_cree.length;
				//var nbr_response=ids_response.length;
				if (nbr_cree!=0) {
					$("#nbrNotif").text(nbr_cree);
					$("#set_heart").addClass("heartbit");
				}
				for (var i = 0; i < ids_cree.length; i++) {
					//$('#tickets_created').text("Numéro: "+""+ids_cree[i]+" ")
					list_numero_cree+="#"+ids_cree[i]+" ";

				}
				html_element=`<li>
								<a href="javascript:void(0);">
										<div class="icon-circle bg-green">
											<i class="zmdi zmdi-edit"></i>
										</div>
										<div class="menu-info">
											<h4>
												<b>Nouveau</b>Tiquets Créés
											</h4>
											<p>
												${list_numero_cree}
											</p>
										</div>
								</a>
								
							</li>
							<li>
								<a href="javascript:void(0);">
										<div class="icon-circle bg-grey">
											<i class="zmdi zmdi-comment-text"></i>
										</div>
										<div class="menu-info">
											<h4>
												<b>Nouvelle</b> Réponse sur les Tiquets:
											</h4>
											<p>
												${list_numero_response}
											</p>
										</div>
								</a>
							</li>`;
				
				
				$('.list-unstyled').append(html_element)
				
			} else if(userPermission==="2") {
				var nbr_response=ids_response.length;
				if (nbr_response!=0) {
					$("#nbrNotif").text(nbr_response);
					$("#set_heart").addClass("heartbit");
				}
				
				for (var i = 0; i < ids_response.length; i++) {
					//$('#tickets_created').text("Numéro: "+""+ids_cree[i]+" ")
					list_numero_response+="#"+ids_response[i]+" ";

				}
				html_element=`
							<li>
								<a href="javascript:void(0);">
										<div class="icon-circle bg-grey">
											<i class="zmdi zmdi-comment-text"></i>
										</div>
										<div class="menu-info">
											<h4>
												<b>Nouvelle</b> Réponse sur les Tiquets:
											</h4>
											<p>
												${list_numero_response}
											</p>
										</div>
								</a>
							</li>`;
				
				
				$('.list-unstyled').append(html_element)

			}else {
				//get list of responses notifications of this user's entreprise tickets or reponses 
				// of tickets assigned to this user entreprise
				var nbr_assign=ids_assign.length;
				//var nbr_response=ids_response.length;
				if (nbr_assign!=0) {
					$("#nbrNotif").text(nbr_assign);
					$("#set_heart").addClass("heartbit");
				}
				for (var i = 0; i < ids_assign.length; i++) {
					//$('#tickets_created').text("Numéro: "+""+ids_cree[i]+" ")
					list_numero_assign+="#"+ids_assign[i]+" ";

				}
				html_element=`<li>
					           <a href="javascript:void(0);">
										<div class="icon-circle bg-light-blue">
											<i class="zmdi zmdi-settings"></i>
										</div>
										<div class="menu-info">
											<h4>Tiquet Assignés</h4>
											<p>
												${list_numero_assign}
											</p>
										</div>
								</a>
							</li>
							<li>
								<a href="javascript:void(0);">
										<div class="icon-circle bg-grey">
											<i class="zmdi zmdi-comment-text"></i>
										</div>
										<div class="menu-info">
											<h4>
												<b>Nouvelle</b> Réponse sur les Tiquets:
											</h4>
											<p>
												${list_numero_response}
											</p>
										</div>
								</a>
							</li>`;
				
				
				$('.list-unstyled').append(html_element)
				
			}

		


		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Notifications Failed bro");
		},
	});

//}, 30000);

$(".dropdown-toggle").on("click",function(){
	$("#nbrNotif").text('');
	$("#set_heart").removeClass("heartbit");
});