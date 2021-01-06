package com.biginformatique.helpdesk.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.User;
import com.google.gson.Gson;

import top.jfunc.json.impl.JSONObject;

@WebServlet("/login")
public class Login extends HttpServlet {
	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
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
		User user=null;
		int usertype=userDao.validate(username, password);
		user=userDao.getUserByUsername(username);
		JSONObject jo = new JSONObject();

		switch (usertype) {

		case 0:

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
