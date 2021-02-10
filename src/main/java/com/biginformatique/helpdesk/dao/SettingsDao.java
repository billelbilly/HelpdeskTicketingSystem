package com.biginformatique.helpdesk.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biginformatique.helpdesk.models.Logiciel;
import com.biginformatique.helpdesk.models.LogicielVersion;
import com.biginformatique.helpdesk.models.MailingAttachSettings;
import com.biginformatique.helpdesk.models.Structure;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.models.Version;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class SettingsDao {

	public boolean saveMailingAttachSettings(MailingAttachSettings mailingAttachSettings) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		MailingAttachSettings settings = null;
		boolean saved = false;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			// get an settings object
			settings = (MailingAttachSettings) session.createQuery("from MailingAttachSettings S").getSingleResult();

			String updateSettings = "UPDATE MailingAttachSettings S set S.host = :host,"
					+ "S.smtp= :smtp, S.port= :port, S.nom= :nom, S.password= :password, S.attchpath= :attchpath WHERE id = :setting_id";
			Query query = session.createQuery(updateSettings);
			query.setParameter("setting_id", settings.getSetting_id());
			query.setParameter("host", mailingAttachSettings.getHost());
			query.setParameter("smtp", mailingAttachSettings.getSmtp());
			query.setParameter("port", mailingAttachSettings.getPort());
			query.setParameter("nom", mailingAttachSettings.getNom());
			query.setParameter("password", mailingAttachSettings.getPassword());
			query.setParameter("attchpath", mailingAttachSettings.getAttchpath());
			query.executeUpdate();
			// commit transaction
			transaction.commit();
			saved = true;

		} catch (Exception e) {

			if (settings == null) {
				// save the settings object
				session.save(mailingAttachSettings);
				// commit transaction
				transaction.commit();
				saved = true;
			} else {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}

		} finally {
			session.close();
		}
		return saved;
	}

	public MailingAttachSettings getInitialSettingsDao() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		MailingAttachSettings settings = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			// get an settings object
			settings = (MailingAttachSettings) session.createQuery("from MailingAttachSettings S").getSingleResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
		}
		return settings;
	}

	public List getSettingsListDao() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List listSettings = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT S.setting_id, S.host, S.smtp,S.port, S.email,S.nom,S.attchpath FROM MailingAttachSettings S");
			listSettings = query.list();
			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return listSettings;
	}

	public boolean EditMailAttachSettingsDao(MailingAttachSettings mailingAttachSettings) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();
			if (mailingAttachSettings.getPassword() != null) {

				String updateUser = "UPDATE MailingAttachSettings S set S.host= :host, S.smtp= :smtp, S.port= :port, S.email= :email, S.nom = :nom, S.password= :password, S.attchpath= :attchpath WHERE setting_id = :setting_id";
				Query query = session.createQuery(updateUser);
				query.setParameter("host", mailingAttachSettings.getHost());
				query.setParameter("smtp", mailingAttachSettings.getSmtp());
				query.setParameter("port", mailingAttachSettings.getPort());
				query.setParameter("email", mailingAttachSettings.getEmail());
				query.setParameter("nom", mailingAttachSettings.getNom());
				query.setParameter("password", mailingAttachSettings.getPassword());
				query.setParameter("attchpath", mailingAttachSettings.getAttchpath());
				query.setParameter("setting_id", mailingAttachSettings.getSetting_id());

				int result = query.executeUpdate();

				if (result != 0) {
					// commit transaction
					transaction.commit();
					return true;
				}
			} else {
				String updateUser = "UPDATE MailingAttachSettings S set S.host= :host, S.smtp= :smtp, S.port= :port, S.email= :email, S.nom = :nom, S.attchpath= :attchpath WHERE setting_id = :setting_id";
				Query query = session.createQuery(updateUser);
				query.setParameter("host", mailingAttachSettings.getHost());
				query.setParameter("smtp", mailingAttachSettings.getSmtp());
				query.setParameter("port", mailingAttachSettings.getPort());
				query.setParameter("email", mailingAttachSettings.getEmail());
				query.setParameter("nom", mailingAttachSettings.getNom());
				query.setParameter("attchpath", mailingAttachSettings.getAttchpath());
				query.setParameter("setting_id", mailingAttachSettings.getSetting_id());
				int result = query.executeUpdate();

				if (result != 0) {
					// commit transaction
					transaction.commit();
					return true;
				}

			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;

	}

	public boolean LogicielVersionDao(int logiciel_id, int version_id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		LogicielVersion logicielversion = new LogicielVersion();
		logicielversion.setLogiciel_id(logiciel_id);
		logicielversion.setVersion_id(version_id);
		boolean saved = false;
		List logicielversionExists = null;
		try {
			logicielversionExists = em
					.createQuery(
							"FROM LogicielVersion LV WHERE LV.logiciel_id= :logiciel_id AND LV.version_id= :version_id")
					.setParameter("logiciel_id", logiciel_id).setParameter("version_id", version_id).setMaxResults(1)
					.getResultList();
			if (logicielversionExists.isEmpty()) {
				// Save Logiciel&Version in Logiciel_Version
				em.getTransaction().begin();
				em.persist(logicielversion);
				em.getTransaction().commit();
				saved = true;

			} else {
				saved = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return saved;

	}

	public boolean VersionDao(Version version) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		boolean saved = false;
		try {
			em.getTransaction().begin();
			em.persist(version);
			em.getTransaction().commit();
			saved = true;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
		}
		return saved;

	}

	public boolean LogicielDao(Logiciel logciel) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		boolean saved = false;
		try {
			em.getTransaction().begin();
			em.persist(logciel);
			em.getTransaction().commit();
			saved = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return saved;

	}

	public List getLogicielListDao() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List LogicielList = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			Query query = session.createQuery("SELECT L.logiciel_id, L.nomLogiciel FROM Logiciel L");
			LogicielList = query.list();

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return LogicielList;
	}

	public List getVersionListDao() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List VersionList = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			Query query = session.createQuery("SELECT V.version_id, V.nomVersion FROM Version V");
			VersionList = query.list();

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return VersionList;
	}

	public List getVersionListByLogicielDao(String logicielId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		int logicielIdInt=Integer.parseInt(logicielId);
		List listVersion = null;

		try {
			listVersion = em
					.createQuery(
							"SELECT V.version_id, V.nomVersion FROM LogicielVersion LV, Version V WHERE LV.logiciel_id= :logiciel_id AND LV.version_id=V.version_id")
					.setParameter("logiciel_id", logicielIdInt).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return listVersion;

	}

	public boolean structureDao(Structure structure) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		boolean saved = false;
		try {
			em.getTransaction().begin();
			em.persist(structure);
			em.getTransaction().commit();
			saved = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return saved;
	}

	public List StructureListDao() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List StructureList = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			Query query = session.createQuery("SELECT S.structure_id, S.nomStructure FROM Structure S");
			StructureList = query.list();

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return StructureList;
	}
	
	public Structure getStructureById(int structureId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Structure structure = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			structure = (Structure) session.get(Structure.class, structureId);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return structure;

	}

}
