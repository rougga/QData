
package main.modal;

import java.util.UUID;

public class Agence {
    private UUID id;
    private String name;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private int status;

    public Agence() {
    }

    public Agence(String name, String host, int port, String database, String username, String password,int status) {
        this.id=UUID.randomUUID();
        this.name = name;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    
    public Agence(UUID id, String name, String host, int port, String database, String username, String password,int status) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.status = status;
    }
    

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
 }
