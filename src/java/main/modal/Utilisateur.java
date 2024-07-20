
package main.modal;

import java.util.Date;
import java.util.UUID;

public class Utilisateur {
    private UUID id;
    private String username;
    private String password;
    private String grade;
    private String firstName;
    private String lastName;
    private Date date;
    private String sponsor;

    public Utilisateur(String username, String password, String grade, String firstName, String lastName, Date date, String sponsor) {
        this.id=UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.grade = grade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.sponsor = sponsor;
    }

    public Utilisateur(UUID id, String username, String password, String grade, String firstName, String lastName, Date date, String sponsor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grade = grade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.sponsor = sponsor;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrade() {
        return grade;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDate() {
        return date;
    }

    public String getSponsor() {
        return sponsor;
    }       
}
