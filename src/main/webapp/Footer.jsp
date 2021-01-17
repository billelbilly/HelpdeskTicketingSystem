<!--Edit User-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="edit_user_modal">

				<div class="modal-dialog">
					<div class="modal-content">

						<div class="modal-body">

							<em><i class="fa fa-edit"
								style="font-size: 30px; color: green; width: 10%; height: 10%"></i>
								Editer User</em>
							<div class="mt-3">
								<form id="editForm"
									action="<%=request.getContextPath()%>/UserManagement"
									method="POST" autocomplete="off"
									oninput='password2.setCustomValidity(password2.value != password.value ? "Mot de passe ne correspondent pas." : "")'>

									<div class="form-group" hidden="1">
										<input type="text" name="usernameId" id="user_id" value="" />
									</div>
									<div class="form-group" hidden="1">
										<input type="text" name="action" id="action" value="/EditUser" />
									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>
										<input name="firstName" id="prenom_id" class="form-control"
											placeholder="Pr�nom" type="text" required="required">
									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>
										<input name="lastName" id="nom_id" class="form-control"
											placeholder="Nom" type="text" required="required">
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-envelope"></i>
											</span>
										</div>
										<input name="email" id="email_id" class="form-control"
											placeholder="addresse Email" type="email" required="required">
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-phone"></i>
											</span>
										</div>

										<input name="phone" id="phone_id" class="form-control"
											placeholder="T�l�phone" type="text" required="required">
									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>
										<input name="username" id="username_id" class="form-control"
											placeholder="Username" type="text" required="required">
									</div>

									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-lock"></i>
											</span>
										</div>
										<input name="password" id="password_id" class="form-control"
											placeholder="Password" type="password">
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-lock"></i>
											</span>
										</div>
										<input name="password2" id="password2_id" class="form-control"
											placeholder="Confirmer password" type="password">
									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control"
											id="date_expiration_compte_EditUser"
											name="date_expiration_compte"
											placeholder="Date Exp�ration du Compte" required>

									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">Type</span>
										</div>
										<select name=userType id="userType_id">
											<option value="1">Admin</option>
											<option value="2">Client</option>
											<option value="3">User Entreprise</option>
										</select>
									</div>
									<!-- form-group// -->
									<div class="form-group">

										<button type="submit" class="btn btn-success btn-md">
											<i class="fa fa-save"></i> Sauvgarder
										</button>
									</div>
								</form>
							</div>


						</div>

						<div class="modal-footer">


							<button class="btn btn-danger btn-md" data-dismiss="modal"
								style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>




<!--Validate User-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="validate_user_modal">

				<div class="modal-dialog">
					<div class="modal-content">

						<div class="modal-body">

							<em><i class="fa fa-check"
								style="font-size: 30px; color: green; width: 10%; height: 10%"></i>
								Validez l'utilisateur en tant que ?</em>
							<form id="validateForm"
								action="<%=request.getContextPath()%>/UserManagement"
								method="POST" autocomplete="off">

								<div class="form-group" hidden="1">
									<input type="text" name="usernameId" id="username_id" value="" />
								</div>
								<div class="form-group" hidden="1">
									<input type="text" name="action" id="action"
										value="/ValidateUser" />
								</div>
								<div class="form-group mt-3">
									<select name=userType>
										<option selected="selected">Client</option>
										<option>Admin</option>
										<option>User Entreprise</option>


									</select>
								</div>
								<button type="submit" class="btn btn-success btn-md">
									<i class="fa fa-check"></i> Valider
								</button>
							</form>

						</div>

						<div class="modal-footer">

							<button class="btn btn-danger btn-md" data-dismiss="modal"
								style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>

				</div>


			</div>

		</div>

	</div>


</div>

<!--Delete User-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="delete_user_modal">

				<div class="modal-dialog">
					<div class="modal-content">

						<div class="modal-body">

							<em><i class="fa fa-exclamation-triangle"
								style="font-size: 30px; color: red; width: 10%; height: 10%"></i>�tes
								vous s�re ?</em>
						</div>

						<div class="modal-footer">

							<form id="DeleteUserForm"
								action="<%=request.getContextPath()%>/UserManagement"
								method="POST" autocomplete="off">

								<div class="form-group" hidden="1">
									<input type="text" name="username_id" id="username_id" value="" />
								</div>
								<div class="form-group" hidden="1">
									<input type="text" name="action" id="action"
										value="/DeleteUser" />
								</div>
								<button type="submit" class="btn btn-danger btn-md">
									<i class="fa fa-trash"></i> Supprimer
								</button>
							</form>
							<button class="btn btn-success btn-md" data-dismiss="modal"
								style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>

<!--Delete Ticket-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="delete_ticket_modal">

				<div class="modal-dialog">
					<div class="modal-content">

						<div class="modal-body">

							<em><i class="fa fa-exclamation-triangle"
								style="font-size: 30px; color: red; width: 10%; height: 10%"></i>�tes
								vous s�re ?</em>

						</div>

						<div class="modal-footer">

							<form id="DeleteTicketForm"
								action="<%=request.getContextPath()%>/TicketManagement"
								method="POST" autocomplete="off">

								<div class="form-group" hidden>
									<input type="text" name="ticket_id" id="ticket_id" value="" />
								</div>
								<div class="form-group" hidden>
									<input type="text" name="action" id="action"
										value="/DeleteTicket" />
								</div>
								<button type="submit" class="btn btn-danger btn-md">
									<i class="fa fa-trash"></i> Supprimer
								</button>
							</form>
							<button id="close_model" class="btn btn-success btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>

<!--Delete Contact-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="delete_contact_modal">

				<div class="modal-dialog">
					<div class="modal-content">

						<div class="modal-body">

							<em><i class="fa fa-exclamation-triangle"
								style="font-size: 30px; color: red; width: 10%; height: 10%"></i>�tes
								vous s�re ?</em>

						</div>

						<div class="modal-footer">

							<form id="DeleteContactForm"
								action="<%=request.getContextPath()%>/Contacts" method="POST"
								autocomplete="off">

								<div class="form-group" hidden>
									<input type="text" name="contact_id" id="contact_id" value="" />
								</div>
								<div class="form-group" hidden>
									<input type="text" name="action" id="action"
										value="/DeleteContact" />
								</div>
								<button type="submit" class="btn btn-danger btn-md">
									<i class="fa fa-trash"></i> Supprimer
								</button>
							</form>
							<button id="close_model" class="btn btn-success btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>

<!--Assign Ticket-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="assign_ticket_modal">

				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>Assigner Tiquet</h5>
						</div>
						<div class="modal-body">

							<form id="AssignTicketForm"
								action="<%=request.getContextPath()%>/TicketManagement"
								method="POST" autocomplete="off">

								<div class="form-group">
									<input type="text" name="ticket_id" id="ticket_id" value=""
										hidden />
								</div>
								<div class="form-group">
									<input type="text" name="action" id="action"
										value="/AssignTicket" hidden />
								</div>
								<!-- Here put Select Field Populated with the UserEntreprise -->
								<div class="form-group">
									<select name=userEntreprise id="userEntreprise"
										style='width: 200px;'
										oninvalid="this.setCustomValidity('Affectez Des User Entreprise D\'abord !')"
										oninput="setCustomValidity('')" required>

									</select>
								</div>
								<div class="form-group">
									<!-- Here put Planification DatePickers -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control date-input"
											id="date_debut_planif" name="date_debut_planif"
											placeholder="Date D�but Planification">

									</div>

									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control date-input"
											id="date_fin_planif" name="date_fin_planif"
											placeholder="Date Fin Planification">

									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control date-input"
											id="date_debut_realise" name="date_debut_realise"
											placeholder="Date D�but R�alisation">

									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control date-input"
											id="date_fin_realise" name="date_fin_realise"
											placeholder="Date Fin R�alisation">

									</div>

								</div>
								<button type="submit" class="btn btn-info btn-md">
									<i class="fa fa-tasks"></i> Assigner
								</button>
							</form>

						</div>

						<div class="modal-footer">
							<button id="close_model" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>
						</div>
					</div>

				</div>

			</div>

		</div>

	</div>


</div>

<!--Show Ticket Details In popup-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="Details">

				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">
								<i class="fa fa-info-circle fa-lg" aria-hidden="true"></i>
								D�tails du Tiquet
							</h5>
						</div>

						<div class="modal-body">

							<div id="ticket_info"></div>

							<div class="form-group">
								<textarea class="form-control" id="putDetail" rows="10"
									style="color: #000" readonly></textarea>
							</div>


						</div>

						<div class="modal-footer">


							<button id="close_model" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Fermer
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>

<!--Show Message In popup-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="Message">

				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">
								<i class="fa fa-envelope fa-lg" aria-hidden="true"></i> Message
							</h5>
						</div>

						<div class="modal-body">

							<div class="form-group">

								<textarea class="form-control" id="putMessage" rows="10"
									style="color: #000" readonly></textarea>
							</div>



						</div>

						<div class="modal-footer">


							<button id="close_model" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Fermer
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>


<!--Show Settings In popup-->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="Settings">

				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">
								<i class="fa fa-cog fa-lg" aria-hidden="true"></i> Param�tres
							</h5>

						</div>

						<div class="modal-body">


							<!-- first param menu -->
							<div class="option-heading">Param�trage Mailing &
								Attachement</div>
							<div class="option-content is-hidden">
								<div class="col-md-8 offset-2 mt-1">

									<form id="MailingSettingForm"
										action="<%=request.getContextPath()%>/Settings" method="POST"
										oninput='password2.setCustomValidity(password2.value != password.value ? "Mot de passe ne correspondent pas." : "")'
										autocomplete="off">

										<div class="form-group" hidden="1">
											<input type="text" name="action" id="action"
												value="/MailSettings&Attachment" />
										</div>
										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i class="fa fa-at"></i>
												</span>
											</div>
											<input name="host" id="host" class="form-control"
												placeholder="Host" type="text"
												oninvalid="this.setCustomValidity('Host obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i
													class="fa fa-server"></i>
												</span>
											</div>
											<input name="smtp" id="smtp" class="form-control"
												placeholder="SMTP Server" type="text"
												oninvalid="this.setCustomValidity('SMTP Server obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i class="fa fa-plug"></i>
												</span>
											</div>
											<input name="port" id="port" class="form-control"
												placeholder="Port" type="number"
												oninvalid="this.setCustomValidity('Port obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i
													class="fa fa-envelope"></i>
												</span>
											</div>
											<input name="email" id="email" class="form-control"
												placeholder="Email" type="email"
												oninvalid="this.setCustomValidity('Email obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i class="fa fa-user"></i>
												</span>
											</div>
											<input name="nom" id="nom" class="form-control"
												placeholder="Nom" type="text"
												oninvalid="this.setCustomValidity('Nom obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i class="fa fa-lock"></i>
												</span>
											</div>
											<input name="password" id="password_smtp"
												class="form-control" placeholder="Password" type="password"
												oninvalid="this.setCustomValidity('Password obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>
										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i class="fa fa-lock"></i>
												</span>
											</div>
											<input name="password2" id="password2_smtp"
												class="form-control" placeholder="Confirmer password"
												type="password">
										</div>

										<div class="form-group input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> <i
													class="fa fa-paperclip"></i>
												</span>
											</div>
											<input name="attchpath" id="attchpath" class="form-control"
												placeholder="Path Attachement exp: C:/Attachements"
												type="text"
												oninvalid="this.setCustomValidity('Path Attachement obligatoire !')"
												oninput="setCustomValidity('')" required>
										</div>


										<!-- form-group// -->
										<div class="form-group">

											<button id="sauv" type="submit"
												class="btn btn-success btn-md">
												<i class="fa fa-save"></i> Sauvgarder
											</button>
										</div>
									</form>
								</div>

							</div>
							<br>
							<!-- second param menu -->
							<div id="usersParam" class="option-heading">Logiciels et
								Versions</div>
							<div class="option-content is-hidden">
								<!-- Alert Message -->
								<div id="alertParam" role="alert" hidden></div>

								<div class="d-flex justify-content-around mt-3">
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="inlineRadioOptions" id="logicielRadio" value="logiciel"
											checked> <label class="form-check-label"
											for="logicielRadio">Ajouter Logiciel</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="inlineRadioOptions" id="versionRadio" value="version">
										<label class="form-check-label" for="versionRadio">Ajouter
											Version</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="inlineRadioOptions" id="assignLogicielVersion"
											value="logiciel&version"> <label
											class="form-check-label" for="assignLogicielVersion">Associer
											Logiciel&Version</label>
									</div>
								</div>

								<div class="d-flex justify-content-around mt-1">


									<form id="LogicielForm"
										action="<%=request.getContextPath()%>/Settings" method="POST"
										autocomplete="off">


										<div class="form-group" hidden="1">
											<input type="text" name="action" id="action"
												value="/Logiciel" />
										</div>
										<div class="form-group">
											<input name="nomLogiciel" id="nomLogiciel"
												class="form-control" placeholder="Nom Logiciel" type="text"
												oninvalid="this.setCustomValidity('Nom Logiciel obligatoire !')"
												oninput="setCustomValidity('')" autocomplete="off" required />

										</div>
										<div class="form-group">

											<button id="logicielSauv" type="submit"
												class="btn btn-success btn-md">
												<i class="fa fa-plus-circle"></i> Ajouter
											</button>
										</div>

									</form>


									<form id="VersionForm"
										action="<%=request.getContextPath()%>/Settings" method="POST"
										autocomplete="off">

										<div class="form-group" hidden="1">
											<input type="text" name="action" id="action" value="/Version" />
										</div>
										<div class="form-group">
											<input name="nomVersion" id="nomVersion" class="form-control"
												placeholder="Nom Version" type="text"
												oninvalid="this.setCustomValidity('Nom Version obligatoire !')"
												oninput="setCustomValidity('')" autocomplete="off" required />

										</div>
										<div class="form-group">

											<button id="versionSauv" type="submit"
												class="btn btn-success btn-md">
												<i class="fa fa-plus-circle"></i> Ajouter
											</button>
										</div>

									</form>

									<form id="LogicielVersionForm"
										action="<%=request.getContextPath()%>/Settings" method="POST"
										autocomplete="off">

										<div class="form-group" hidden="1">
											<input type="text" name="action" id="action"
												value="/Logiciel&Version" />
										</div>
										<!-- Here put Select Field Populated with the UserEntreprise -->
										<div class="form-group">
											<select name=listLogiciel id="listLogiciel"
												style='width: 200px;'
												oninvalid="this.setCustomValidity('Ajoutez des Logiciel D\'abord !')"
												oninput="setCustomValidity('')" required>

											</select>
										</div>

										<!-- Here put Select Field Populated with the UserEntreprise -->
										<div class="form-group">
											<select name=listVersion id="listVersion"
												style='width: 200px;'
												oninvalid="this.setCustomValidity('Ajoutez des Version D\'abord !')"
												oninput="setCustomValidity('')" required>

											</select>
										</div>
										<div class="form-group">

											<button id="logicielVersionSauv" type="submit"
												class="btn btn-success btn-md">
												<i class="fa fa-tasks"></i> Associer
											</button>
										</div>

									</form>



								</div>

							</div>

							<br>


						</div>

						<div class="modal-footer">


							<button id="close_model" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Fermer
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>


<!-------------------------------Add new user----------------------->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="registerModal">

				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">
								<i class="fa fa-pencil fa-lg" aria-hidden="true"></i> Ajouter
								nouveau utilisateur
							</h5>

						</div>

						<div class="modal-body">
							<div id="successAlert" class="alert alert-success" role="alert"
								hidden></div>
							<div class="col-md-8 offset-2">

								<form id="registerForm"
									action="<%=request.getContextPath()%>/UserManagement"
									method="post" autocomplete="off"
									oninput='password2.setCustomValidity(password2.value != password.value ? "Mot de passe ne correspondent pas." : "")'>
									<div class="form-group" hidden="1">
										<input type="text" name="action" id="action" value="/register" />
									</div>

									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>
										<input name="firstName" id="prenomRegister_id"
											class="form-control" placeholder="Pr�nom" type="text"
											oninvalid="this.setCustomValidity('Pr�nom obligatoire !')"
											oninput="setCustomValidity('')" required>
									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>
										<input name="lastName" id="nomRegister_id"
											class="form-control" placeholder="Nom" type="text"
											oninvalid="this.setCustomValidity('Nom obligatoire !')"
											oninput="setCustomValidity('')" required>
									</div>
									<!-- form-group// -->
									<div id="emailAlert" class="alert alert-danger" role="alert"
										hidden></div>
									<div class="form-group input-group">

										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-envelope"></i>
											</span>
										</div>

										<input name="email" id="emailRegister_id" class="form-control"
											placeholder="adresse Email" type="email"
											oninvalid="this.setCustomValidity('Email obligatoire !')"
											oninput="setCustomValidity('')" required>
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-phone"></i>
											</span>
										</div>

										<input name="phone" id="phoneRegister_id" class="form-control"
											placeholder="T�l�phone" type="text"
											oninvalid="this.setCustomValidity('T�l�phone obligatoire !')"
											oninput="setCustomValidity('')" required>
									</div>
									<div id="usernameAlert" class="alert alert-danger" role="alert"
										hidden></div>
									<div class="form-group input-group">

										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-user"></i>
											</span>
										</div>

										<input name="username" id="usernameRegister_id"
											class="form-control" placeholder="Username" type="text"
											oninvalid="this.setCustomValidity('Username obligatoire !')"
											oninput="setCustomValidity('')" required>
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-lock"></i>
											</span>
										</div>
										<input name="password" id="passwordRegister_id"
											class="form-control" placeholder="Password" type="password"
											required>
									</div>
									<!-- form-group// -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i class="fa fa-lock"></i>
											</span>
										</div>
										<input name="password2" id="password2Register_id"
											class="form-control" placeholder="Confirmer password"
											type="password" required>
									</div>
									<!-- form-group// -->
									<!-- Here put Expiration DatePickers -->
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> <i
												class="fa fa-calendar" aria-hidden="true"></i>
											</span>
										</div>
										<input type="text" class="form-control"
											id="date_expiration_compte" name="date_expiration_compte"
											placeholder="Date Exp�ration du Compte" required>

									</div>
									<div class="form-group input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">Type</span>
										</div>
										<select name="userType" id="userType_id" required>
											<option value="1">Admin</option>
											<option value="2">Client</option>
											<option value="3">User Entreprise</option>
										</select>
									</div>

									<div class="form-group">
										<button type="submit" class="btn btn-success">Submit</button>
									</div>

								</form>
							</div>



						</div>

						<div class="modal-footer">


							<button id="closeAddUser" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Annuler
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>
<!-- ------------------------------------------------------------------------------------------ -->

<!------------------------------- Liste des Utilisateurs ----------------------->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<div class="modal fade" id="userListModal">

				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">

							<h5 class="modal-title">
								<i class="fa fa-badge fa-lg" aria-hidden="true"></i> Liste des
								Utilisateur
							</h5>


						</div>

						<div class="modal-body">
							<div class="mb-1">
								<button id="download-xlsx" class="btn btn-success btn-sm">T�l�charger
									XLSX</button>
								<button id="download-pdf" class="btn btn-danger btn-sm">T�l�charger
									PDF</button>
								<button class="btn btn-sm btn-success pull-right mx-1 "
									data-toggle="modal" data-target="#registerModal">
									<i class="fa fa-pencil"></i> Nouveau
								</button>
							</div>


							<div id="usersList"></div>

						</div>

						<div class="modal-footer">


							<button id="close_model" class="btn btn-danger btn-md"
								data-dismiss="modal" style="color: white">
								<i class="fa fa-times" aria-hidden="true"></i> Fermer
							</button>

						</div>

					</div>


				</div>


			</div>

		</div>

	</div>


</div>
<!-- ------------------------------------------------------------------------------------------ -->


<script src="resources/js/jquery-3.5.1.min.js"></script>
<script src="resources/js/moment.min.js"></script>
<script src="resources/js/select2.min.js" defer></script>
<script src="resources/js/tabulator.min.js"></script>
<script src="resources/js/flash.min.js"></script>
<script src="resources/js/manageTickets.js"></script>
<script src="resources/js/manageUsers.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/mainTemplate.js"></script>
<script src="resources/js/popper.js"></script>
<script src="resources/assets/bundles/libscripts.bundle.js"></script>
<script src="resources/assets/bundles/vendorscripts.bundle.js"></script>
<script src="resources/assets/bundles/jvectormap.bundle.js"></script>
<script src="resources/assets/bundles/sparkline.bundle.js"></script>
<script src="resources/assets/bundles/c3.bundle.js"></script>
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/datepicker.js"></script>
<script src="resources/js/datepickerForAdmin.js"></script>
<script src="resources/assets/bundles/mainscripts.bundle.js"></script>
<script src="resources/assets/js/pages/index.js"></script>
<script src="resources/js/Settings.js"></script>
<script src="resources/js/validateForms.js"></script>

