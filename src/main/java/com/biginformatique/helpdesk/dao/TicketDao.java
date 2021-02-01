package com.biginformatique.helpdesk.dao;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class TicketDao {

	public TicketDao() {

	}

	public void saveTicket(Ticket ticket) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(ticket);
			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// This is for Tickets in Template
	public List getTicketsByUserDao(User user) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List allTickets = null;
		List currentUserTickets = null;
		List assignedTickets = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			if (user.getEtat() == 2) {
				// if User is a Client
				Query query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user, T.Attachment, U.username, L.nomLogiciel, V.nomVersion,T.AssignedTo,T.ClosedBy,T.closedDateTime,T.assignedDateTime  FROM Ticket T, User U, Logiciel L, Version V WHERE T.user= :user and T.user=U.user_id AND L.logiciel_id=T.Logiciel AND T.Version=V.version_id ORDER BY T.createDateTime DESC")
						.setParameter("user", user);
				allTickets = query.list();
				// commit transaction
				transaction.commit();

			} else if (user.getEtat() == 1) {
				// if the User is Admin
				Query query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user,T.Attachment, U.username, L.nomLogiciel, V.nomVersion,T.AssignedTo,T.ClosedBy,T.closedDateTime,T.assignedDateTime  FROM Ticket T, User U, Logiciel L, Version V WHERE T.user=U.user_id AND L.logiciel_id=T.Logiciel AND T.Version=V.version_id ORDER BY T.createDateTime DESC");
				allTickets = query.list();
				// commit transaction
				transaction.commit();
			} else {
				// if User is UserEntreprise
				Query query = session.createQuery(
						"SELECT  T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user,T.Attachment, U.username, L.nomLogiciel, V.nomVersion,T.AssignedTo,T.ClosedBy,T.closedDateTime,T.assignedDateTime  FROM Ticket T, TicketUser TU, User U, Logiciel L, Version V"
								+ " WHERE (T.ticket_id= TU.ticket_id AND T.AssignedTo= :username AND T.user=U.user_id AND L.logiciel_id=T.Logiciel AND T.Version=V.version_id) ORDER BY T.createDateTime DESC");
				query.setParameter("username", user.getUsername());
				assignedTickets = query.list();

				query = session.createQuery(
						"SELECT  T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user,T.Attachment, U.username, L.nomLogiciel, V.nomVersion,T.AssignedTo,T.ClosedBy,T.closedDateTime,T.assignedDateTime  FROM Ticket T, User U, Logiciel L, Version V"
								+ " WHERE T.user= :user and T.user=U.user_id AND L.logiciel_id=T.Logiciel AND T.Version=V.version_id ORDER BY T.createDateTime DESC");
				query.setParameter("user", user);
				currentUserTickets = query.list();

				// commit transaction
				transaction.commit();

				allTickets = (List) Stream.concat(assignedTickets.stream(), currentUserTickets.stream())
						.collect(Collectors.toList());

			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return allTickets;

	}

	// This is for tickets in Tabulator
	public List getAllTicketsDao() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List allTickets = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime,T.AssignedTo,T.ClosedBy,U.username,T.closedDateTime,T.assignedDateTime, L.nomLogiciel, V.nomVersion  FROM Ticket T, User U, Logiciel L, Version V WHERE T.user=U.user_id AND L.logiciel_id=T.Logiciel AND T.Version=V.version_id ORDER BY T.createDateTime DESC");
			allTickets = query.list();
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

		return allTickets;

	}

	public Ticket getTicketById(String ticketId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Ticket ticket = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			ticket = (Ticket) session.get(Ticket.class, Integer.parseInt(ticketId));

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ticket;
	}

	public boolean updateTicketDao(Ticket ticket) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		Ticket ticketToUpdate = new Ticket();
		ticketToUpdate = em.find(Ticket.class, ticket.getTicket_id());

		try {
			// start a transaction
			em.getTransaction().begin();
			if (ticket.getAttachment() != null) {

				ticketToUpdate.setObjet(ticket.getObjet());
				ticketToUpdate.setDetails(ticket.getDetails());
				ticketToUpdate.setEtat(ticket.getEtat());
				ticketToUpdate.setSeverity(ticket.getSeverity());
				ticketToUpdate.setUser(ticket.getUser());
				ticketToUpdate.setClosedBy(ticket.getClosedBy());
				ticketToUpdate.setAttachment(ticket.getAttachment());
				ticketToUpdate = em.merge(ticketToUpdate);

			} else {
				ticketToUpdate.setObjet(ticket.getObjet());
				ticketToUpdate.setDetails(ticket.getDetails());
				ticketToUpdate.setEtat(ticket.getEtat());
				ticketToUpdate.setSeverity(ticket.getSeverity());
				ticketToUpdate.setUser(ticket.getUser());
				ticketToUpdate.setClosedBy(ticket.getClosedBy());
				ticketToUpdate = em.merge(ticketToUpdate);

			}

			if (ticketToUpdate != null) {
				// commit transaction
				em.getTransaction().commit();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return false;

	}

	public List filtreTicketDao(String filtre, User user) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List filtreTickets = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			if (user.getEtat() == 1) {
				Query query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user, T.Attachment, U.username FROM Ticket T, User U WHERE T.Etat= :filtre and T.user=U.user_id ORDER BY T.createDateTime DESC");
				query.setParameter("filtre", filtre);
				filtreTickets = query.list();
				// commit transaction
				transaction.commit();

			} else if (user.getEtat() == 2) {
				Query query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user, T.Attachment, U.username FROM Ticket T, User U WHERE T.Etat= :filtre and T.user= :user and T.user=U.user_id ORDER BY T.createDateTime DESC");
				query.setParameter("filtre", filtre);
				query.setParameter("user", user);
				filtreTickets = query.list();
				// commit transaction
				transaction.commit();

			} else {

				Query query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user, T.Attachment, U.username FROM Ticket T, TicketUser TU, User U"
								+ " WHERE (T.Etat= :filtre AND T.ticket_id= TU.ticket_id AND T.AssignedTo= :username AND T.user=U.user_id ) ORDER BY T.createDateTime DESC");
				query.setParameter("username", user.getUsername());
				query.setParameter("filtre", filtre);
				List assignedTickets = query.list();

				query = session.createQuery(
						"SELECT T.id, T.Objet, T.Details, T.Etat,T.Severity, T.createDateTime, T.user, T.Attachment, U.username FROM Ticket T, User U"
								+ " WHERE T.Etat= :filtre AND T.user= :user AND T.user=U.user_id ORDER BY T.createDateTime DESC");
				query.setParameter("user", user);
				query.setParameter("filtre", filtre);
				List currentUserTickets = query.list();

				// commit transaction
				transaction.commit();

				filtreTickets = (List) Stream.concat(assignedTickets.stream(), currentUserTickets.stream())
						.collect(Collectors.toList());

			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return filtreTickets;
	}

	public boolean deleteTicketDao(String ticket_id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		boolean ticketRemoved = false;
		try {
			em.getTransaction().begin();
			Ticket ticketToDelete = em.find(Ticket.class, Integer.parseInt(ticket_id));

			String deleteAssociatedResponses = "DELETE FROM responses WHERE ticket_id = :ticketId";
			Query query = (Query) em.createNativeQuery(deleteAssociatedResponses);
			query.setParameter("ticketId", ticketToDelete.getId());
			query.executeUpdate();

			String deleteAssigned = "DELETE FROM ticket_user WHERE ticket_id = :ticketId";
			query = (Query) em.createNativeQuery(deleteAssigned);
			query.setParameter("ticketId", ticketToDelete.getId());
			query.executeUpdate();

			String deletePlanif = "DELETE FROM planification WHERE ticket_id = :ticketId";
			query = (Query) em.createNativeQuery(deletePlanif);
			query.setParameter("ticketId", ticketToDelete.getId());
			query.executeUpdate();

			if (ticketToDelete.getAttachment() != null) {
				try {
					File f = new File(ticketToDelete.getAttachment());
					f.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			em.remove(ticketToDelete);
			// commit EntityManager Transaction
			em.getTransaction().commit();
			ticketRemoved = true;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
		}
		return ticketRemoved;
	}

	public void updateTicketStatus(Ticket ticket) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		Ticket ticketToUpdate = new Ticket();
		ticketToUpdate = ticket;

		try {
			em.getTransaction().begin();
			ticketToUpdate.setEtat("assigné");
			ticketToUpdate = em.merge(ticketToUpdate);

			if (ticketToUpdate != null) {
				// commit transaction
				em.getTransaction().commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public List getCreatedTicketsDao(User user) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List createdTickets = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT T.id FROM Ticket T WHERE T.createDateTime > :lastAccessDate OR T.createDateTime > :currentAccessDate").
					setParameter("lastAccessDate", user.getLastAccessDate()).
					setParameter("currentAccessDate", user.getCurrentAccessDate());
			createdTickets = query.list();
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

		return createdTickets;
		

	}

	public List getAssignResponsesDao(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List assignedTickets = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT T.id FROM Ticket T WHERE (T.assignedDateTime > :lastAccessDate AND T.AssignedTo= :username) OR (T.assignedDateTime > :currentAccessDate AND T.AssignedTo= :username)").
					setParameter("lastAccessDate", user.getLastAccessDate()).
					setParameter("currentAccessDate", user.getCurrentAccessDate()).
					setParameter("username", user.getUsername());
			assignedTickets = query.list();
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

		return assignedTickets;
	}

	public List getResponsesOnTicketsDao(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List responses = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT T.id FROM Ticket T, Response R WHERE"
					+ " (R.createDateTime > :lastAccessDate OR R.createDateTime > :currentAccessDate) AND (T.user= :user)").
					setParameter("lastAccessDate", user.getLastAccessDate()).
					setParameter("currentAccessDate", user.getCurrentAccessDate()).
					setParameter("user", user);
			responses = query.list();
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

		return responses;
	}

}
