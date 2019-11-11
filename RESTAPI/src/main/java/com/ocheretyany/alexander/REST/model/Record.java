package com.ocheretyany.alexander.REST.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "connections")
public class Record {
	
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String hostname;
    @NotBlank
    private String port;
    @NotBlank
    private String databasename;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    
    public Record(){
        super();
    }
    
    public Record(Long id, String name, String hostname, String port, String databasename, String username, String password) {
        super();
        this.id = id;
        this.name = name;
        this.hostname = hostname;
        this.port=port;
        this.databasename = databasename;
        this.username = username;
        this.password = password;
    }

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHostname() {
		return hostname;
	}

	public String getPort() {
		return port;
	}

	public String getDatabasename() {
		return databasename;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}