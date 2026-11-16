package com.prizz.models;

/**
 * [MODELS] Abstract Base User Entity.
 * Purpose: Represents any user in the system with shared attributes and methods.
 * Syllabus: Core OOP (Abstraction, Encapsulation), Wrapper classes (Double, Integer).
 */
public abstract class User {
    private Integer userId;
    private String name;
    private String email;
    private String role;
    private Double walletBalance;

    public User(Integer userId, String name, String email, String role, Double walletBalance) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.walletBalance = (walletBalance != null) ? walletBalance : 0.0;
    }

    // Abstract method to demonstrate Polymorphism
    public abstract String getProfileSummary();

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(Double walletBalance) { this.walletBalance = walletBalance; }

    @Override
    public String toString() {
        return String.format("User[ID=%d, Name='%s', Role='%s', Balance=$%.2f]", 
                userId, name, role, walletBalance);
    }
}

