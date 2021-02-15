package com.biginformatique.helpdesk.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.biginformatique.helpdesk.dao.PlanificationsDao;
import com.biginformatique.helpdesk.dao.SettingsDao;
import com.biginformatique.helpdesk.dao.TicketDao;
import com.biginformatique.helpdesk.dao.TicketUserDao;
import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.MailingAttachSettings;
import com.biginformatique.helpdesk.models.Planification;
import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.TicketUser;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.EmailUtility;
import com.biginformatique.helpdesk.util.EncryptDecryptPassword;

import top.jfunc.json.impl.JSONObject;

@WebServlet("/TicketManagement")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class TicketManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TicketDao ticketDao;
	private TicketUserDao ticketuserDao;
	private UserDao userDao;
	private PlanificationsDao planifDao;
	private SettingsDao settingsDao;
	private String host;
	private String smtp;
	private String port;
	private String email;
	private String name;
	private String pass;
	private EncryptDecryptPassword decryptPassword;

	public TicketManagement() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
		ticketDao = new TicketDao();
		ticketuserDao = new TicketUserDao();
		userDao = new UserDao();
		planifDao = new PlanificationsDao();
		settingsDao = new SettingsDao();
		try {
			decryptPassword = new EncryptDecryptPassword();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/getAllTickets":
			try {
				getAllTickets(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "/getTickets":
			try {
				getTicketsByUser(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
			
		case "/getClosedTickets":
			try {
				getClosedTickets(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
			
		case "/filtreTickets":
			try {
				filtreTicket(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/getPlanifications":
			try {
				getPlanifications(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/getPlanifByTicket":
			try {
				getPlanificationByTicket(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/getCreatedTickets":
			try {
				getCreatedTickets(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/getResponsesOnTickets":
			try {
				getResponsesOnTickets(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/getAssign&Responses":
			try {
				getAssignResponses(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}

	private void getClosedTickets(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		String username = request.getParameter("usersession");
		user = userDao.getUserByUsername(username);
		List allTickets = ticketDao.getClosedTicketsDao(user);
		JSONObject jo = new JSONObject();
		jo.put("ticket", allTickets);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());
		
	}

	private void getAssignResponses(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("usersession");
		User user = userDao.getUserByUsername(username);
		List assignedTickets = ticketDao.getAssignResponsesDao(user);
		List assignedTicketsResponses = ticketDao.getResponsesOfUserEntrepriseDao(user);

		JSONObject jo = new JSONObject();
		// get each ticket username Creator as json object and send
		// it to the Client Side
		jo.put("assignedTickets", assignedTickets);
		jo.put("assignedTicketsResponses", assignedTicketsResponses);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void getResponsesOnTickets(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("usersession");
		User user = userDao.getUserByUsername(username);
		List responses = ticketDao.getResponsesOnTicketsDao(user);

		JSONObject jo = new JSONObject();
		// get each ticket username Creator as json object and send
		// it to the Client Side
		jo.put("responses", responses);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void getCreatedTickets(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("usersession");
		User user = userDao.getUserByUsername(username);
		List createdTickets = ticketDao.getCreatedTicketsDao(user);

		JSONObject jo = new JSONObject();
		// get each ticket username Creator as json object and send
		// it to the Client Side
		jo.put("createdTickets", createdTickets);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void getPlanificationByTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ticketId = request.getParameter("ticket_id");
		Ticket ticket = null;
		JSONObject jo = new JSONObject();
		ticket = ticketDao.getTicketById(ticketId);
		List ticketPlanif = planifDao.getPlanificationByTicketIdDao(ticket);
		jo.put("ticketPlanif", ticketPlanif);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void getPlanifications(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("usersession");
		User user = userDao.getUserByUsername(username);
		List planifications = planifDao.getPlanificationsDao(user);

		JSONObject jo = new JSONObject();
		jo.put("planifList", planifications);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/CreateTicket":
			saveTicket(request, response);
			break;

		case "/DeleteTicket":
			try {
				deleteTicket(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "/UpdateTicket":
			try {
				updateTicket(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/AssignTicket":
			try {
				saveTicketUser(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "/PlanifyTicket":
			try {
				planifyTicket(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			break;
		}
	}

	private void planifyTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sdateDebutPlanif = request.getParameter("date_debut_planif");
		String sdateFinPlanif = request.getParameter("date_fin_planif");
		String sdateDebutRealise = request.getParameter("date_debut_realise");
		String sdateFinRealise = request.getParameter("date_fin_realise");
		String Observation = request.getParameter("observation");
		String ticketId = request.getParameter("ticket_id");
		LocalDate dateDebutPlanif = null;
		LocalDate dateFinPlanif = null;
		LocalDate dateDebutRealise = null;
		LocalDate dateFinRealise = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Test here if Date Strings are not null

		dateDebutPlanif = LocalDate.parse(sdateDebutPlanif, formatter);
		dateFinPlanif = LocalDate.parse(sdateFinPlanif, formatter);
		if (!sdateDebutRealise.equals("") && !sdateFinRealise.equals("")) {
			dateDebutRealise = LocalDate.parse(sdateDebutRealise, formatter);
			dateFinRealise = LocalDate.parse(sdateFinRealise, formatter);
		}

		JSONObject jo = new JSONObject();
		Planification planifObj = new Planification();
		Ticket ticket = null;
		ticket = ticketDao.getTicketById(ticketId);
		List currentTicketPlanif = planifDao.getPlanificationByTicketIdDao(ticket);

		planifObj.setDateDebutPlanif(dateDebutPlanif);
		planifObj.setDateFinPlanif(dateFinPlanif);
		planifObj.setDateDebutRealise(dateDebutRealise);
		planifObj.setDateFinRealise(dateFinRealise);
		planifObj.setObservation(Observation);
		planifObj.setTicket(ticket);

		// If currentTicketPlanif not null Update Planification else Create
		// Planification
		if (!currentTicketPlanif.isEmpty()) {

			if (planifDao.UpdatePlanif(planifObj)) {

				jo.put("success", "true");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
			} else {

				jo.put("success", "false");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
			}

		} else {

			if (planifDao.planifyTicketDao(planifObj)) {

				jo.put("success", "true");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
			} else {

				jo.put("success", "false");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
			}

		}

	}

	private void planifyTicketByAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sdateDebutPlanif = request.getParameter("date_debut_planif");
		String sdateFinPlanif = request.getParameter("date_fin_planif");
		String sdateDebutRealise = request.getParameter("date_debut_realise");
		String sdateFinRealise = request.getParameter("date_fin_realise");
		String ticketId = request.getParameter("ticket_id");
		LocalDate dateDebutPlanif = null;
		LocalDate dateFinPlanif = null;
		LocalDate dateDebutRealise = null;
		LocalDate dateFinRealise = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// Test here if Date Strings are not null
		dateDebutPlanif = LocalDate.parse(sdateDebutPlanif, formatter);
		dateFinPlanif = LocalDate.parse(sdateFinPlanif, formatter);
		if (!sdateDebutRealise.equals("") && !sdateFinRealise.equals("")) {
			dateDebutRealise = LocalDate.parse(sdateDebutRealise, formatter);
			dateFinRealise = LocalDate.parse(sdateFinRealise, formatter);
		}

		Planification planifObj = new Planification();
		Ticket ticket = null;
		ticket = ticketDao.getTicketById(ticketId);
		planifObj.setDateDebutPlanif(dateDebutPlanif);
		planifObj.setDateFinPlanif(dateFinPlanif);
		planifObj.setDateDebutRealise(dateDebutRealise);
		planifObj.setDateFinRealise(dateFinRealise);
		planifObj.setTicket(ticket);

		planifDao.planifyTicketDao(planifObj);

	}

	private void getAllTickets(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List allTickets = ticketDao.getAllTicketsDao();

		JSONObject jo = new JSONObject();
		// get each ticket username Creator as json object and send
		// it to the Client Side
		jo.put("tickets", allTickets);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		String ticket_id = request.getParameter("ticket_id");
		if (ticketDao.deleteTicketDao(ticket_id)) {
			jo.put("success", "true");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
		} else {
			jo.put("success", "false");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

		}

	}

	private void saveTicketUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userEntreprise");
		String ticketId = request.getParameter("ticket_id");
		String sdateDebutPlanif = request.getParameter("date_debut_planif");
		String sdateFinPlanif = request.getParameter("date_fin_planif");
		if (!sdateDebutPlanif.equals("") && !sdateFinPlanif.equals("")) {
			planifyTicketByAdmin(request, response);
		}
		TicketUser ticketuser = new TicketUser();
		ticketuser.setUser_id(Integer.parseInt(userId));
		ticketuser.setTicket_id(Integer.parseInt(ticketId));
		JSONObject jo = new JSONObject();
		MailingAttachSettings settings = null;
		User user = userDao.getUserById(Integer.parseInt(userId));
		String recipient = user.getEmail();
		String subject = "Nouveau Tiquet Assigné";
		// Add more info to the content such Ticket number software in question ...
		String content = "Bonjour,<br><br>Un nouveau Tiquet vous a été assigné. Merci d'en jeter un oeil";
		content += "<br><br>Cordialement.";

		// reads SMTP server setting from DB
		settings = settingsDao.getInitialSettingsDao();
		host = settings.getHost();
		smtp = settings.getSmtp();
		port = settings.getPort();
		email = settings.getEmail();
		name = settings.getNom();
		pass = settings.getPassword();
		pass = decryptPassword.decrypt(pass);

		if (ticketuserDao.saveTicketUserDao(ticketuser)) {

			// Change Ticket Status to Assigned
			Ticket ticket = ticketDao.getTicketById(ticketId);
			try {
				ticketDao.updateTicketStatus(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}

			jo.put("success", "true");
			jo.put("id", ticketId);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
			// Send Email Notification here
			try {
				EmailUtility.sendEmail(smtp, port, email, name, pass, recipient, subject, content);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			jo.put("success", "false");
			jo.put("id", ticketId);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
		}

	}

	private void saveTicket(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String Objet = request.getParameter("subject");
		String Severity = request.getParameter("severity");
		String listLogiciel = request.getParameter("listLogiciel");
		String listVersion = request.getParameter("listVersion");
		String Details = request.getParameter("detail");
		Part part = request.getPart("attachment");
		User user = null;
		MailingAttachSettings mailingAttachSettings = null;

		Ticket ticket = new Ticket("créé");
		ticket.setObjet(Objet);
		ticket.setSeverity(Severity);
		ticket.setDetails(Details);
		ticket.setLogiciel(listLogiciel);
		ticket.setVersion(listVersion);

		// get the File uploaded name
		String fileName = part.getSubmittedFileName();

		// creates the save directory if it does not exists
		// getServletContext().getInitParameter("file-upload")
		mailingAttachSettings = settingsDao.getInitialSettingsDao();
		File fileSaveDir = new File(mailingAttachSettings.getAttchpath());
		File file = new File(fileSaveDir, fileName);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		if (!fileName.equals("")) {
			// Save File to Upload Directory
//			part.write(fileSaveDir + File.separator + fileName);
			String FilePathToDownload = fileSaveDir + File.separator + fileName;
			File FileExist = new File(FilePathToDownload);
			boolean exists = FileExist.exists();
			
			if (!exists) {
				try (InputStream input = part.getInputStream()) {
					Files.copy(input, file.toPath());
				}
				ticket.setAttachment(FilePathToDownload);
				String userToSearch = session.getAttribute("username").toString();
				user = userDao.getUserByUsername(userToSearch);
				ticket.setUser(user);
				ticketDao.saveTicket(ticket);
				JSONObject jo = new JSONObject();
				jo.put("success", "true");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
				
			} else {
				
				JSONObject jo = new JSONObject();
				jo.put("success", "false");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());

			}
	
			

		}else {
			
			String userToSearch = session.getAttribute("username").toString();
			user = userDao.getUserByUsername(userToSearch);
			ticket.setUser(user);
			ticketDao.saveTicket(ticket);
			JSONObject jo = new JSONObject();
			jo.put("success", "true");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
			
		}

		

	}

	private void getTicketsByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		String username = request.getParameter("usersession");
		user = userDao.getUserByUsername(username);
		List allTickets = ticketDao.getTicketsByUserDao(user);
		JSONObject jo = new JSONObject();
		jo.put("ticket", allTickets);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void filtreTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filtre = request.getParameter("filtre");
		User user = null;
		String username = request.getParameter("usersession");
		user = userDao.getUserByUsername(username);
		JSONObject jo = new JSONObject();
		List filtreTickets = null;
		if (!filtre.equals("...")) {
			filtreTickets = ticketDao.filtreTicketDao(filtre, user);

			jo.put("ticket", filtreTickets);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

		} else {
			filtreTickets = ticketDao.getTicketsByUserDao(user);
			jo.put("ticket", filtreTickets);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

		}

	}

	private void updateTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userSession = request.getParameter("usersession");
		String Objet = request.getParameter("subject");
		String Severity = request.getParameter("severity");
		String etatTicket = request.getParameter("etat_ticket");
		String Details = request.getParameter("detail");
		String ticketId = request.getParameter("ticket_id");
		Part part = request.getPart("attachment");
		User user = null;
		User userCloser = null;
		MailingAttachSettings mailingAttachSettings = null;
		MailingAttachSettings settings = null;
		// reads SMTP server setting from DB
		settings = settingsDao.getInitialSettingsDao();
		host = settings.getHost();
		smtp = settings.getSmtp();
		port = settings.getPort();
		email = settings.getEmail();
		name = settings.getNom();
		pass = settings.getPassword();
		pass = decryptPassword.decrypt(pass);

		Ticket ticket = new Ticket(etatTicket);
		ticket.setTicket_id(Integer.parseInt(ticketId));
		ticket.setObjet(Objet);
		ticket.setSeverity(Severity);
		ticket.setDetails(Details);
		// get the File uploaded name
		String fileName = part.getSubmittedFileName();

		// creates the save directory if it does not exists
		mailingAttachSettings = settingsDao.getInitialSettingsDao();
		File fileSaveDir = new File(mailingAttachSettings.getAttchpath());
		File file = new File(fileSaveDir, fileName);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		if (!fileName.equals("")) {
			// Save File to Upload Directory
//			part.write(fileSaveDir + File.separator + fileName);
			String FilePathToDownload = fileSaveDir + File.separator + fileName;
			File FileExist = new File(FilePathToDownload);
			boolean exists = FileExist.exists();
			
			if (!exists) {
				
				try (InputStream input = part.getInputStream()) {
					Files.copy(input, file.toPath());
				}
				ticket.setAttachment(FilePathToDownload);
				// Get The User Who Created The Ticket here
				Ticket ticketCreator = ticketDao.getTicketById(ticketId);
				// if etatTicket is fermer get the user who closed the ticket and add it to the
				// ticket object
				user = userDao.getUserById(ticketCreator.getUser().getUser_id());
				userCloser = userDao.getUserByUsername(userSession);
				ticket.setUser(user);
				if (etatTicket.equals("fermer")) {
					ticket.setClosedBy(userCloser.getFirstName() + "." + userCloser.getLastName());
					String recipient= ticketCreator.getUser().getEmail();
					String subject = "Tiquet Fermé";
					// Add more info to the content such Ticket number software in question ...
					String content = "Bonjour,<br><br>Votre Tiquet Numéro: #" + ticketId + " a été fermé.";
					content += "<br><br>Cordialement.";
					// Send Email Notification here
					try {
						EmailUtility.sendEmail(smtp, port, email, name, pass, recipient, subject, content);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}

				ticketDao.updateTicketDao(ticket);
				JSONObject jo = new JSONObject();
				jo.put("success", "true");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
				
				
			} else {
				JSONObject jo = new JSONObject();
				jo.put("success", "false");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());

			}

			

		}else {
			// Get The User Who Created The Ticket here
			Ticket ticketCreator = ticketDao.getTicketById(ticketId);
			// if etatTicket is fermer get the user who closed the ticket and add it to the
			// ticket object
			user = userDao.getUserById(ticketCreator.getUser().getUser_id());
			userCloser = userDao.getUserByUsername(userSession);
			ticket.setUser(user);
			if (etatTicket.equals("fermer")) {
				ticket.setClosedBy(userCloser.getFirstName() + "." + userCloser.getLastName());
				String recipient= ticketCreator.getUser().getEmail();
				String subject = "Tiquet Fermé";
				// Add more info to the content such Ticket number software in question ...
				String content = "Bonjour,<br><br>Votre Tiquet Numéro: #" + ticketId + " a été fermé.";
				content += "<br><br>Cordialement.";
				// Send Email Notification here
				try {
					EmailUtility.sendEmail(smtp, port, email, name, pass, recipient, subject, content);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			ticketDao.updateTicketDao(ticket);
			JSONObject jo = new JSONObject();
			jo.put("success", "true");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
			
		}


	}

}
