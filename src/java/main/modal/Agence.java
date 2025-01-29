package main.modal;

import java.util.UUID;

public class Agence {

    private UUID id;
    private String name;
    private String host;
    private int port;
    private String lastupdated_at;
    private int status;

    public Agence() {
    }

    public Agence(String name, String host, int port, String lastupdated_at, int status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.host = host;
        this.port = port;
        this.lastupdated_at = lastupdated_at;
        this.status = status;
    }

    public Agence(UUID id, String name, String host, int port, String lastupdated_at, int status) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
        this.lastupdated_at = lastupdated_at;
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

    public String getLastupdated_at() {
        return lastupdated_at;
    }

    public int getStatus() {
        return status;
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

    public void setLastupdated_at(String lastupdated_at) {
        this.lastupdated_at = lastupdated_at;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
