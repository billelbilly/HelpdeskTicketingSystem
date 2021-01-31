package com.biginformatique.helpdesk.models;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "users")
public class User implements Serializable {
	

   

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int user_id;

    public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	@Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email")
    private String Email;
    
    @Column(name = "phone")
    private String Phone;
    
    @Column(name = "username")
    private String username;

	@Column(name = "password")
    private String password;
    
    @Column(name = "etat")
    private int Etat;
    
    @Column(name = "dateExpiration")
    private LocalDate dateExpiration;
    
    @Column(name = "lastAccessDate")
    private LocalDate lastAccessDate;
    
    @Column(name = "currentAccessDate")
    private LocalDate currentAccessDate;
    
    @OneToMany(fetch = FetchType.EAGER ,mappedBy="user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Ticket> tickets;
    
    @OneToMany(fetch = FetchType.EAGER ,mappedBy="user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Response> responses;
    
    
    // Assign To logic
//    @OneToOne(mappedBy = "userAssigned")
//    private Ticket ticketAssigned;

	public int getId() {
		return user_id;
	}

//	public Ticket getTicketAssigned() {
//		return ticketAssigned;
//	}
//
//	public void setTicketAssigned(Ticket ticketAssigned) {
//		this.ticketAssigned = ticketAssigned;
//	}

	public void setId(int id) {
		this.user_id = id;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int etat) {
		if(etat!=0) {
			this.Etat=etat;
		}else
		 this.Etat=0;
	}

    
	public int getEtat() {
		return Etat;
	}
	public void setEtat(int etat) {
		Etat = etat;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public LocalDate getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(LocalDate lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public LocalDate getCurrentAccessDate() {
		return currentAccessDate;
	}

	public void setCurrentAccessDate(LocalDate currentAccessDate) {
		this.currentAccessDate = currentAccessDate;
	}
	
	
}