package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.service.LoginUser;

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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "user_id")
    private Integer userId;

	public void setUserId(LoginUser loginUser) {
		
		
	}

    
}