package com.biginformatique.helpdesk.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.biginformatique.helpdesk.models.Contact;
import com.biginformatique.helpdesk.models.Logiciel;
import com.biginformatique.helpdesk.models.LogicielVersion;
import com.biginformatique.helpdesk.models.MailingAttachSettings;
import com.biginformatique.helpdesk.models.Planification;
import com.biginformatique.helpdesk.models.Response;
import com.biginformatique.helpdesk.models.Structure;
import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.TicketUser;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.models.Version;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASS;

	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				DB_URL=System.getenv("DB_URL");
				DB_USER=System.getenv("DB_USER");
				DB_PASS=System.getenv("DB_PASS");

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
				settings.put(Environment.URL,DB_URL);
				settings.put(Environment.USER, DB_USER);
				settings.put(Environment.PASS, DB_PASS);
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Ticket.class);
				configuration.addAnnotatedClass(Response.class);
				configuration.addAnnotatedClass(TicketUser.class);
				configuration.addAnnotatedClass(Planification.class);
				configuration.addAnnotatedClass(Contact.class);
				configuration.addAnnotatedClass(MailingAttachSettings.class);
				configuration.addAnnotatedClass(Logiciel.class);
				configuration.addAnnotatedClass(Version.class);
				configuration.addAnnotatedClass(LogicielVersion.class);
				configuration.addAnnotatedClass(Structure.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

}
