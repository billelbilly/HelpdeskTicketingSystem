package com.biginformatique.helpdesk.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biginformatique.helpdesk.dao.SettingsDao;
import com.biginformatique.helpdesk.dao.UserDao;
import com.biginformatique.helpdesk.models.Logiciel;
import com.biginformatique.helpdesk.models.MailingAttachSettings;
import com.biginformatique.helpdesk.models.Structure;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.models.Version;
import com.biginformatique.helpdesk.util.EncryptDecryptPassword;
import com.biginformatique.helpdesk.util.HibernateUtil;
import com.google.gson.Gson;

import top.jfunc.json.impl.JSONObject;

@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SettingsDao settingsDao;
	private UserDao userDao;
	private EncryptDecryptPassword encryptPassword;

	public Settings() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
		settingsDao = new SettingsDao();
		userDao = new UserDao();
		try {
			encryptPassword = new EncryptDecryptPassword();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/getInitialSettings":
			try {
				getInitialSettings(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;

		case "/getInitialSettingsList":
			try {
				getInitialSettingsList(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;

		case "/getLogicielList":
			try {
				getLogicielList(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;
			
		case "/getStructureList":
			try {
				getStructureList(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;

		case "/getVersionList":
			try {
				getVersionList(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;

		case "/getVersionListByLogiciel":
			try {
				getVersionListByLogiciel(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void getStructureList(HttpServletRequest request, HttpServletResponse response) {
		List StructureList = settingsDao.StructureListDao();
		/* Send Json Response To Client */
		String jsonStructureList = new Gson().toJson(StructureList);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonStructureList);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	private void getVersionListByLogiciel(HttpServletRequest request, HttpServletResponse response) {
		String logicielId = request.getParameter("logiciel_id");
		List listVersion = settingsDao.getVersionListByLogicielDao(logicielId);

		/* Send Json Response To Client */
		String jsonlistVersion = new Gson().toJson(listVersion);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonlistVersion);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void getVersionList(HttpServletRequest request, HttpServletResponse response) {
		List VersionList = settingsDao.getVersionListDao();
		/* Send Json Response To Client */
		String jsonVersionList = new Gson().toJson(VersionList);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonVersionList);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void getLogicielList(HttpServletRequest request, HttpServletResponse response) {
		List LogicielList = settingsDao.getLogicielListDao();
		/* Send Json Response To Client */
		String jsonLogicielList = new Gson().toJson(LogicielList);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonLogicielList);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void getInitialSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MailingAttachSettings settings = null;
		JSONObject jo = new JSONObject();
		settings = settingsDao.getInitialSettingsDao();
		jo.put("settings", settings);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	private void getInitialSettingsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List settingsList = null;
		JSONObject jo = new JSONObject();
		settingsList = settingsDao.getSettingsListDao();
		jo.put("settings", settingsList);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jo.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "/MailSettings&Attachment":
			try {
				MailAttachSettings(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/Logiciel&Version":
			try {
				LogicielVersionSettings(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/Logiciel":
			try {
				LogicielSettings(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/Version":
			try {
				VersionSettings(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/Structure":
			try {
				StructureSettings(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void StructureSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nomStructure = request.getParameter("nomStructure");
		Structure structure = new Structure();
		JSONObject jo = new JSONObject();
		structure.setNomStructure(nomStructure);
		if (settingsDao.structureDao(structure)) {

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

	private void LogicielSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nomLogiciel = request.getParameter("nomLogiciel");
		Logiciel logiciel = new Logiciel();
		JSONObject jo = new JSONObject();
		logiciel.setNomLogiciel(nomLogiciel);
		if (settingsDao.LogicielDao(logiciel)) {

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

	private void VersionSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nomVersion = request.getParameter("nomVersion");
		Version version = new Version();
		JSONObject jo = new JSONObject();
		version.setNomVersion(nomVersion);
		if (settingsDao.VersionDao(version)) {

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

	private void LogicielVersionSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String Logiciel = request.getParameter("listLogiciel");
		String Version = request.getParameter("listVersion");
		JSONObject jo = new JSONObject();

		if (settingsDao.LogicielVersionDao(Integer.parseInt(Logiciel), Integer.parseInt(Version))) {

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

	private void EditMailAttachSettings(HttpServletRequest request, HttpServletResponse response,
			MailingAttachSettings settings) throws Exception {
		String host = request.getParameter("host");
		String smtp = request.getParameter("smtp");
		String port = request.getParameter("port");
		String email = request.getParameter("email");
		String nom = request.getParameter("nom");
		String password = request.getParameter("password");
		String attchpath = request.getParameter("attchpath");
		JSONObject jo = new JSONObject();
		MailingAttachSettings mailingAttachSettings = new MailingAttachSettings();
		mailingAttachSettings.setSetting_id(settings.getSetting_id());
		mailingAttachSettings.setHost(host);
		mailingAttachSettings.setSmtp(smtp);
		mailingAttachSettings.setPort(port);
		mailingAttachSettings.setEmail(email);
		mailingAttachSettings.setNom(nom);
		if (!password.equals("")) {
			password = encryptPassword.encrypt(password);
			// Encrypt password here before save it to database
			mailingAttachSettings.setPassword(password);
		}

		mailingAttachSettings.setAttchpath(attchpath);
		if (settingsDao.EditMailAttachSettingsDao(mailingAttachSettings)) {
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

	private void MailAttachSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String host = request.getParameter("host");
		String smtp = request.getParameter("smtp");
		String port = request.getParameter("port");
		String email = request.getParameter("email");
		String nom = request.getParameter("nom");
		String password = request.getParameter("password");
		String attchpath = request.getParameter("attchpath");
		JSONObject jo = new JSONObject();
		MailingAttachSettings mailingAttachSettings = new MailingAttachSettings();
		MailingAttachSettings settings = null;
		settings = settingsDao.getInitialSettingsDao();

		if (settings != null) {
			EditMailAttachSettings(request, response, settings);

		} else {
			mailingAttachSettings.setHost(host);
			mailingAttachSettings.setSmtp(smtp);
			mailingAttachSettings.setPort(port);
			mailingAttachSettings.setEmail(email);
			mailingAttachSettings.setNom(nom);
			// Encrypt password here before save it to database
			password = encryptPassword.encrypt(password);
			mailingAttachSettings.setPassword(password);
			mailingAttachSettings.setAttchpath(attchpath);

			if (settingsDao.saveMailingAttachSettings(mailingAttachSettings)) {
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
	
	

}
