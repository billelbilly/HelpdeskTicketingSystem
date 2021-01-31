package com.biginformatique.helpdesk.controllers;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.User;


@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private UserDao userDao;
	
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	userDao = new UserDao();
    	
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user=userDao.getUserByUsername((String) session.getAttribute("username"));
		session.removeAttribute("username");
		session.invalidate();
		//Update Last Access Date Here
		LocalDate lastAccessDate = LocalDate.now();
		userDao.updateLastAccessOrCurrentAccessDate(user, lastAccessDate, "last");
		response.sendRedirect("index.jsp");
	
	}

	

}
