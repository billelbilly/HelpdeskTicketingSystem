package com.biginformatique.helpdesk.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.biginformatique.helpdesk.dao.SettingsDao;
import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.MailingAttachSettings;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.EmailUtility;
import com.biginformatique.helpdesk.util.EncryptDecryptPassword;
import com.google.gson.Gson;

import top.jfunc.json.impl.JSONObject;

@WebServlet("/login")
public class Login extends HttpServlet {
	private UserDao userDao;
	private SettingsDao settingsDao;
	private String host;
	private String smtp;
	private String port;
	private String email;
	private String name;
	private String pass;
	private EncryptDecryptPassword decryptPassword;

	public void init() {
		userDao = new UserDao();
		settingsDao = new SettingsDao();
		try {
			decryptPassword = new EncryptDecryptPassword();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			authenticate(request, response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		User user = null;
		LocalDateTime currentAccessDate = null;
		LocalDateTime lastAccessDate = null;
		MailingAttachSettings settings = null;
		int usertype = userDao.validate(username, password);
		user = userDao.getUserByUsername(username);
		JSONObject jo = new JSONObject();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		String recipient = user.getEmail();
		String subject = "Expiration du support";
		String content = "Cher client,<br><br>Nous vous informons que votre support à été expiré, nous vous recommendons "
				+ "de le renouveler.";
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

		switch (usertype) {

		case 0:
			// Send Email to inform the Client to renew his Licence

			try {
				EmailUtility.sendEmail(smtp, port, email, name, pass, recipient, subject, content);
			} catch (Exception e) {

				e.printStackTrace();
			}

			jo.put("username", username);
			jo.put("user_type", "0");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

			break;

		case 1:
			// Redirect to Admin Page
			session.setAttribute("username", username);
			session.setAttribute("userPermission", user.getEtat());
			lastAccessDate = user.getLastAccessDate();
			if (lastAccessDate == null) {
				lastAccessDate = LocalDateTime.now();
			}
			session.setAttribute("lastAccessDate", lastAccessDate.format(myFormatObj));
			// Update Current Access date here
			currentAccessDate = LocalDateTime.now();
			userDao.updateLastAccessOrCurrentAccessDate(user, currentAccessDate, "current");
			jo.put("username", username);
			jo.put("user_type", "1");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

			break;

		case 2:
			// Redirect to Client Page
			session.setAttribute("username", username);
			session.setAttribute("userPermission", user.getEtat());
			lastAccessDate = user.getLastAccessDate();
			if (lastAccessDate == null) {
				lastAccessDate = LocalDateTime.now();
			}
			session.setAttribute("lastAccessDate", lastAccessDate.format(myFormatObj));
			// Update Current Access date here
			currentAccessDate = LocalDateTime.now();
			userDao.updateLastAccessOrCurrentAccessDate(user, currentAccessDate, "current");
			jo.put("username", username);
			jo.put("user_type", "2");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

			break;

		case 3:
			// Redirect to UserEntreprise Page
			session.setAttribute("username", username);
			session.setAttribute("userPermission", user.getEtat());
			lastAccessDate = user.getLastAccessDate();
			if (lastAccessDate == null) {
				lastAccessDate = LocalDateTime.now();
			}
			session.setAttribute("lastAccessDate", lastAccessDate.format(myFormatObj));
			// Update Current Access date here
			currentAccessDate = LocalDateTime.now();
			userDao.updateLastAccessOrCurrentAccessDate(user, currentAccessDate, "current");
			jo.put("username", username);
			jo.put("user_type", "3");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

			break;

		case -1:
			// inexisted user
			jo.put("username", username);
			jo.put("user_type", "-1");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
			break;

		case -2:
			// mdp false
			jo.put("username", username);
			jo.put("user_type", "-2");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
			break;

		default:
			break;
		}

	}

}
