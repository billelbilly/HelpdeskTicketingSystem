package com.biginformatique.helpdesk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.biginformatique.helpdesk.models.Response;
import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class ResponseDao {

	public ResponseDao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean saveResponse(Response response) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			session.save(response);
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

	public List getResponseByTicket(Ticket ticket) {
		
		//Think here how to join with the user Table to get the User who Responded
		// and before that save the user session with Ticket Response Submit Form
		
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			List responses = null;

			try {

				// start a transaction
				transaction = session.beginTransaction();
			
				Query query = session.createQuery(
						"SELECT R.id, R.Response, R.createDateTime,U.firstName, U.lastName FROM Response R, User U "
						+ "WHERE R.ticket = :ticket AND R.user=U.user_id ORDER BY R.createDateTime DESC ")
						.setParameter("ticket", ticket);
				
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
