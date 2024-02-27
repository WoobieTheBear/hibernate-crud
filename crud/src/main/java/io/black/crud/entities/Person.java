package io.black.crud.entities;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="person")
public class Person {
    public Person(){}
    public Person(String firstName, String lastName, String email, String company){
        this.fristName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        this.created = (this.created == null) ? now : this.created;
        this.lastLogin = now;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String fristName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="company")
    private String company;

    @Column(name="created_at")
    private Timestamp created;

    @Column(name="last_login")
    private Timestamp lastLogin;

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return fristName;
    }

    public void setFirstname(String name) {
        fristName = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String name) {
        lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String name) {
        company = name;
    }

    public Timestamp getCreationstamp() {
        return created;
    }

    public void setCreationstamp(Timestamp stamp) {
        this.created = stamp;
    }

    public Timestamp getLastlogin() {
        return lastLogin;
    }

    public void setLastlogin(Timestamp stamp) {
        this.lastLogin = stamp;
    }

    public String toString(){
        /*
        Field[] fields = this.getClass().getDeclaredFields();
        String output = "{ ";
        for(Field field : fields){
            field.setAccessible(true);
            try {
                output += "\"" + field.getName() + "\": \"" + field.get(this) + "\", "; 
            } catch ( IllegalAccessException exc ){
                System.out.println( exc.getMessage() );
            }
        }
        output += " }";
        */
        return "[id: " + id + "; first_name: " + fristName + "; last_name: " + lastName + "; email: " + email + "; company: " + company + "]";
    }
}
