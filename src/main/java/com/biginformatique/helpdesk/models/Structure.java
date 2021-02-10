package com.biginformatique.helpdesk.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "structure")
public class Structure implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "structure_id")
    private int structure_id;
	
	@Column(name = "nom_structure",unique = true)
    private String nomStructure;
	
	@OneToOne(mappedBy = "structure")
    private User user;
	
	public int getStructure_id() {
		return structure_id;
	}

	public void setStructure_id(int structure_id) {
		this.structure_id = structure_id;
	}

	public String getNomStructure() {
		return nomStructure;
	}

	public void setNomStructure(String nomStructure) {
		this.nomStructure = nomStructure;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	
	

}
