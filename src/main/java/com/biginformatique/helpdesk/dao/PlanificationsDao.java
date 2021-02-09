package com.biginformatique.helpdesk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biginformatique.helpdesk.models.Planification;
import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class PlanificationsDao {

	public PlanificationsDao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean planifyTicketDao(Planification planifObj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			session.save(planifObj);
			// commit transaction
			transaction.commit();
			return true;
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

	public List getPlanificationsDao(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List listPlanifications = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			if (user.getEtat() == 1) {
				Query query = session.createQuery(
						"SELECT T.ticket_id, U.username, P.dateDebutPlanif, P.dateFinPlanif, P.dateDebutRealise, P.dateFinRealise,T.Etat,P.Observation FROM Planification P, Ticket T, User U "
						+ "WHERE P.ticket=T.ticket_id AND T.user=U.user_id");
				listPlanifications = query.list();
				// commit transaction
				transaction.commit();
				
			} else {
				Query query = session.createQuery(
						"SELECT T.ticket_id, U.username, P.dateDebutPlanif, P.dateFinPlanif, P.dateDebutRealise, P.dateFinRealise,T.Etat,P.Observation FROM Planification P, Ticket T, User U, TicketUser TU "
						+ "WHERE P.ticket=T.ticket_id AND T.ticket_id= TU.ticket_id AND T.AssignedTo= :firstNamelastName AND T.user=U.user_id")
						.setParameter("firstNamelastName", user.getFirstName()+"."+user.getLastName());
				listPlanifications = query.list();
				// commit transaction
				transaction.commit();

			}
			
			

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return listPlanifications;
	}
	
	public List getPlanificationByTicketIdDao(Ticket ticket) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List ticketPlanif = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			Query query= (Query) session.createQuery("from Planification P where P.ticket = :ticket")
					.setParameter("ticket", ticket);
			ticketPlanif = query.getResultList();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ticketPlanif;
	}
	
	public boolean UpdatePlanif(Planification planifObj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();
			

				String updateUser = "UPDATE Planification P set P.dateDebutPlanif= :dateDebutPlanif, P.dateFinPlanif= :dateFinPlanif, P.dateDebutRealise= :dateDebutRealise, P.dateFinRealise= :dateFinRealise, P.Observation = :Observation WHERE P.ticket = :ticket";
				Query query = session.createQuery(updateUser);
				query.setParameter("dateDebutPlanif", planifObj.getDateDebutPlanif());
				query.setParameter("dateFinPlanif", planifObj.getDateFinPlanif());
				query.setParameter("dateDebutRealise", planifObj.getDateDebutRealise());
				query.setParameter("dateFinRealise", planifObj.getDateFinRealise());
				query.setParameter("Observation", planifObj.getObservation());
				query.setParameter("ticket", planifObj.getTicket());

				int result = query.executeUpdate();

				if (result != 0) {
					// commit transaction
					transaction.commit();
					return true;
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

}
