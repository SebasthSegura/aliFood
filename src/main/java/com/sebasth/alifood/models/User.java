package com.sebasth.alifood.models;

public class User {
    private int id;
    private String username;
    private String passwordHash;

    // Create a complete constructor
    public User(int id, String username, String passwordHash){
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Constructor to create a new User object without id
    public User(String username, String passwordHash) {
        this(-1, username, passwordHash); // -1 to indicate no id on bd
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Nombre de Usuario = '" + username + '\'' +
                ", Hash = '" + passwordHash + '\'' +
                '}';
    }
}
