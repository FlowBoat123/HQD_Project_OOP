package org.example.javafxtutorial;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private int ID;
    private String username;
    private String password;
    private LocalDateTime creationTime;
    private String bio;
    private String email;
    private String website;
    private int repoCount;
    private int followerCount;
    private int followingCount;

    // Default constructor with hardcoded values
    public User() {
        this.ID = 1010;
        this.username = "FlowBoat123";
        this.password = "123";
        this.creationTime = LocalDateTime.of(2024, 5, 11, 0, 0);
        this.bio = "LoveCraft";
        this.email = "hienflb@gmail.com";
        this.website = "love.craft";
        this.repoCount = 0;
        this.followerCount = 0;
        this.followingCount = 0;
    }

    public User(String username, String password, LocalDateTime creationTime) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
    }

    public User(String username, String password, LocalDateTime creationTime, String bio, String email, String website, int repoCount, int followerCount, int followingCount) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
        this.bio = bio;
        this.email = email;
        this.website = website;
        this.repoCount = repoCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public User(String username, String password, LocalDateTime creationTime, String bio, String email, String website) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
        this.bio = bio;
        this.email = email;
        this.website = website;
    }

    // Getters and Setters for all fields

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(int repoCount) {
        this.repoCount = repoCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
