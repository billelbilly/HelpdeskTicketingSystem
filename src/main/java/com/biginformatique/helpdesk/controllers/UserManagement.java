package com.biginformatique.helpdesk.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biginformatique.helpdesk.dao.SettingsDao;
import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.User;
import com.google.gson.Gson;

import top.jfunc.json.impl.JSONObject;

@WebServlet("/UserManagement")
public class UserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private SettingsDao settingsDao;

	public UserManagement() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
		settingsDao = new SettingsDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/getUsersEntreprise":
			getUserEntreprise(request, response);

			break;

		case "/getAllUsers":
			getUsers(request, response);
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/ValidateUser":
			try {
				validateUser(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/DeleteUser":
			try {
				deleteUser(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/EditUser":
			try {
				editUser(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/register":
			register(request, response);
			break;

		default:
			break;
		}

	}

	private void getUserEntreprise(HttpServletRequest request, HttpServletResponse response) {
		List allUserEntreprise = userDao.getUserEntrepriseDao();
		/* Send Json Response To Client */
		String jsonAllUserEntreprise = new Gson().toJson(allUserEntreprise);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonAllUserEntreprise);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	protected void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List allUsers = userDao.getAllUsers();
		JSONObject jo = new JSONObject();
		// get each ticket username Creator as json object and send
		// it to the Client Side
		jo.put("users", allUsers);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void validateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		String usernameId = request.getParameter("usernameId");
		String userType = request.getParameter("userType");
		switch (userType) {
		case "Client":
			userType = "2";
			break;
		case "Admin":
			userType = "1";
			break;
		case "User Entreprise":
			userType = "3";
			break;

		default:
			break;
		}
		if (userDao.validateUserDao(usernameId, userType)) {
			jo.put("success", "true");
			jo.put("id", usernameId);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());
		} else {
			jo.put("success", "false");
			jo.put("id", usernameId);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jo.toString());

		}

	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String typeUser = request.getParameter("userType");
		String structureId = request.getParameter("structure");
		String sdateExpiration = request.getParameter("date_expiration_compte");
		int user_email[] = new int[2];
		LocalDate dateExpiration = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (sdateExpiration != "") {
			dateExpiration = LocalDate.parse(sdateExpiration, formatter);
//			  try {
//				dateExpiration=new SimpleDateFormat("dd/MM/yyyy").parse(sdateExpiration);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  

		}

		User user = new User(Integer.parseInt(typeUser));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setUsername(username);
		user.setDateExpiration(dateExpiration);
		user.setPassword(userDao.hash(password));
		if (typeUser.equals("2") || typeUser.equals("3")) {
			user.setStructure(settingsDao.getStructureById(Integer.parseInt(structureId)));

		}

		user_email = (int[]) userDao.getUser(user);
		if (user_email[0] == 0 && user_email[1] == 0) {
			userDao.saveUser(user);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("usersaved");

		} else {
			if (user_email[0] == 1) {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("usernameexist");

			} else {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("emailexist");
			}

		}

	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		String user_id = request.getParameter("username_id");
		if (userDao.deleteUserDao(user_id)) {
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

	private void editUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("usernameId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String username = request.getParameter("username");
//		String typeUser = request.getParameter("userType");
		String password = request.getParameter("password");
		String sdateExpiration = request.getParameter("date_expiration_compte");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateExpiration = null;
		// Test here if Date Strings are not null
		if (sdateExpiration != "") {
			dateExpiration = LocalDate.parse(sdateExpiration, formatter);
//			  try {
//				dateExpiration=new SimpleDateFormat("dd/MM/yyyy").parse(sdateExpiration);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  

		}
		User user = new User();
		int user_email[] = new int[2];
//		switch (typeUser) {
//		case "1":// admin
//			 user = new User(1);
//
//			break;
//		case "2":// client
//			 user = new User(2);
//
//			break;
//		
//		case "3":// utilisateur entreprise
//			 user = new User(3);
//
//			break;
//
//		default:
//			break;
//		}
		user.setId(Integer.parseInt(userId));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setUsername(username);
		user.setDateExpiration(dateExpiration);
		if (!password.equals("")) {
			user.setPassword(userDao.hash(password));
		}
		user_email = (int[]) userDao.getUserToEdit(user);
		if (user_email[0] == 0 && user_email[1] == 0) {
			userDao.editUserDao(user);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("userupdated");

		} else {
			if (user_email[0] == 1) {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("usernameexist");

			} else {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("emailexist");
			}

		}

	}

}
