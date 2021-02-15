package com.biginformatique.helpdesk.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import com.biginformatique.helpdesk.models.Ticket;
import com.biginformatique.helpdesk.models.User;
import com.biginformatique.helpdesk.util.HibernateUtil;

public class UserDao {

	public void saveUser(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();
			// save the user object
			session.save(user);
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
	}

	public int validate(String userName, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;

		try {
			// start a transaction
			transaction = session.beginTransaction();
			// get user object
			user = (User) session.createQuery("FROM User U WHERE U.username = :userName")
					.setParameter("userName", userName).uniqueResult();

			if (user != null && verifyHash(password, user.getPassword())) {
				LocalDate dateExpiration = user.getDateExpiration();
				if (dateExpiration != null) {
					// Date currentdate = new Date(System.currentTimeMillis());

					LocalDate currentDate = LocalDate.now();
					int resultComparaison = currentDate.compareTo(dateExpiration);
					if (resultComparaison >= 0) {
						// Expired Date
						return 0;
					} else {
						return user.getEtat();
					}
				} else {
					return user.getEtat();
				}

			} else if (user == null) {
				// user not found
				return -1;
			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		// wrong password
		return -2;
	}

	public List getAllUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List allUsers = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			Query query = session.createQuery(
					"SELECT DISTINCT  U.id, U.firstName, U.lastName, U.Email,U.Phone, U.username,U.Etat,U.dateExpiration,CASE "
					+ "WHEN U.structure IS NULL THEN '' ELSE S.nomStructure END AS nomStructure FROM User U, Structure S "
					+ "WHERE U.structure=S.structure_id OR  U.structure IS NULL");
			allUsers = query.list();

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

		return allUsers;

	}

	public int[] getUser(User usersent) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		int[] array = new int[2];

		try {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			String selectUser = "FROM User U WHERE U.username = :username OR U.Email= :email";
			Query getUserquery = session.createQuery(selectUser);
			getUserquery.setParameter("username", usersent.getUsername());
			getUserquery.setParameter("email", usersent.getEmail());
			List results = getUserquery.getResultList();

			if (!results.isEmpty()) {
				user = (User) results.get(0);
				if (user != null && user.getUsername().equals(usersent.getUsername())) {
					array[0] = 1;
					array[1] = 0;

				} else if (user != null) {
					array[0] = 0;
					array[1] = 1;

				}

			}

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

		return array;

	}

	public int[] getUserToEdit(User usersent) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		int[] array = new int[2];

		try {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			String selectUser = "FROM User U WHERE  U.id != :user_to_edit_id AND (U.username = :username OR U.Email= :email)";
			Query getUserquery = session.createQuery(selectUser);
			getUserquery.setParameter("user_to_edit_id", usersent.getId());
			getUserquery.setParameter("username", usersent.getUsername());
			getUserquery.setParameter("email", usersent.getEmail());
			List results = getUserquery.getResultList();

			if (!results.isEmpty()) {
				user = (User) results.get(0);
				if (user != null && user.getUsername().equals(usersent.getUsername())) {
					array[0] = 1;
					array[1] = 0;

				} else if (user != null && user.getEmail().equals(usersent.getEmail())) {
					array[0] = 0;
					array[1] = 1;

				}

			}

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

		return array;

	}

	public boolean validateUserDao(String usernameId, String userType) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			String updateUser = "UPDATE User set Etat = :etat " + "WHERE id = :user_id";
			Query query = session.createQuery(updateUser);
			query.setParameter("etat", Integer.parseInt(userType));
			query.setParameter("user_id", Integer.parseInt(usernameId));
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

	public boolean deleteUserDao(String user_id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		boolean userRemoved = false;
		try {
			em.getTransaction().begin();
			User userToDelete = em.find(User.class, Integer.parseInt(user_id));

			String deleteAssociatedResponses = "DELETE FROM responses WHERE user_id = :userId";
			Query query = (Query) em.createNativeQuery(deleteAssociatedResponses);
			query.setParameter("userId", userToDelete.getId());
			query.executeUpdate();

			String deleteAssociatedTickets = "DELETE FROM tickets WHERE user_id = :userId";
			query = (Query) em.createNativeQuery(deleteAssociatedTickets);
			query.setParameter("userId", userToDelete.getId());
			query.executeUpdate();

			em.remove(userToDelete);
			// commit EntityManager Transaction
			em.getTransaction().commit();
			userRemoved = true;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
		}
		return userRemoved;

	}

	public boolean editUserDao(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();
			if (user.getPassword() != null) {

				String updateUser = "UPDATE User U set U.firstName= :firstname, U.lastName= :lastname, U.Email= :email, U.Phone= :phone, U.password = :password, U.username= :username, U.dateExpiration= :dateExpiration, U.structure= :structure WHERE id = :user_id";
				Query query = session.createQuery(updateUser);
				query.setParameter("user_id", user.getId());
				query.setParameter("firstname", user.getFirstName());
				query.setParameter("lastname", user.getLastName());
				query.setParameter("email", user.getEmail());
				query.setParameter("phone", user.getPhone());
				query.setParameter("username", user.getUsername());
				query.setParameter("password", user.getPassword());
				query.setParameter("dateExpiration", user.getDateExpiration());
				query.setParameter("structure", user.getStructure());
				int result = query.executeUpdate();

				if (result != 0) {
					// commit transaction
					transaction.commit();
					return true;
				}
			} else {
				String updateUser = "UPDATE User U set U.firstName= :firstname, U.lastName= :lastname, U.Email= :email, U.Phone= :phone,  U.username= :username, U.dateExpiration= :dateExpiration, U.structure= :structure WHERE U.id = :user_id";
				Query query = session.createQuery(updateUser);
				query.setParameter("user_id", user.getId());
				query.setParameter("firstname", user.getFirstName());
				query.setParameter("lastname", user.getLastName());
				query.setParameter("email", user.getEmail());
				query.setParameter("phone", user.getPhone());
				query.setParameter("username", user.getUsername());
				query.setParameter("dateExpiration", user.getDateExpiration());
				query.setParameter("structure", user.getStructure());
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

	public User getUserByUsername(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			user = (User) session.createQuery("from User u where u.username = :username")
					.setParameter("username", username).getSingleResult();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;

	}

	public User getUserByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			user = (User) session.createQuery("from User u where u.Email = :email").setParameter("email", email)
					.getSingleResult();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;

	}

	public User getUserById(int userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			user = (User) session.get(User.class, userId);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;

	}

	public String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean verifyHash(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

	public List getUserEntrepriseDao() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List getUserEntreprise = null;

		try {

			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			Query query = session.createQuery(
					"SELECT U.id, U.firstName, U.lastName, U.Email,U.Phone, U.username,U.Etat FROM User U WHERE U.Etat=3");
			getUserEntreprise = query.list();

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

		return getUserEntreprise;

	}

	public boolean updatePassword(User user, String hash) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			// start a transaction
			transaction = session.beginTransaction();

			String updateUser = "UPDATE User set password = :password " + "WHERE id = :user_id";
			Query query = session.createQuery(updateUser);
			query.setParameter("password", hash);
			query.setParameter("user_id", user.getUser_id());
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

	public void updateLastAccessOrCurrentAccessDate(User user, LocalDateTime currentAccessDate, String op) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		User userToUpdate = new User();
		userToUpdate = user;

		try {
			em.getTransaction().begin();
			switch (op) {
			case "last":
				userToUpdate.setLastAccessDate(currentAccessDate);

				break;
			case "current":
				userToUpdate.setCurrentAccessDate(currentAccessDate);

				break;

			default:
				break;
			}

			userToUpdate = em.merge(userToUpdate);

			if (userToUpdate != null) {
				// commit transaction
				em.getTransaction().commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

}
