package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @SequenceGenerator(name = "LIBRARY_ID_GENERATOR", sequenceName = "LIBRARY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIBRARY_ID_GENERATOR")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Log> logs;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Log> getLibraries() {
		return this.logs;
	}

	

    
}