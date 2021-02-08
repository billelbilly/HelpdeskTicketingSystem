package com.biginformatique.helpdesk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.TicketUser;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class TicketUserDao {
	private UserDao userDao;

	public TicketUserDao() {
		userDao = new UserDao();
	}
	
	public boolean saveTicketUserDao(TicketUser ticketuser ) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user=null;
		boolean ticketAssigned=false;
		
		try {
			// start a transaction
			transaction = session.beginTransaction();	
			
			
			if (getTicketUser(ticketuser)!=null) {
				
				return ticketAssigned;
			}else {
				//Insert Ticket_User here
				session.save(ticketuser);
				//Get username From ticketuser
				user=userDao.getUserById(ticketuser.getUser_id());
				
				String updateTicket = "UPDATE Ticket set AssignedTo= :lastName WHERE ticket_id = :ticket_id";
				Query query = session.createQuery(updateTicket);
				query.setParameter("ticket_id", ticketuser.getTicket_id());
				query.setParameter("lastName", user.getLastName());
				query.executeUpdate();
				// commit transaction
				transaction.commit();
				ticketAssigned=true;
				return ticketAssigned;
				
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ticketAssigned;
	}
	
	public TicketUser getTicketUser(TicketUser ticketUserToGet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		TicketUser ticketuser = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			ticketuser = (TicketUser) session.get(TicketUser.class, ticketUserToGet.getTicket_id());

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ticketuser;
	}
	
	
	
	

}
