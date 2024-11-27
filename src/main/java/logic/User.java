package logic;

import java.util.Date;

/**
 * This class represents a user in the system.
 */
public class User {
    private int ID;
    private String username;
    private String password;
    private Date creationTime;
    private String bio;
    private String email;
    private String website;
    private String details;
    private String avatar;
    private int repoCount;
    private int followerCount;
    private int followingCount;

    /**
     * Constructor to initialize a User object with all fields.
     *
     * @param ID            The user's ID.
     * @param username      The user's username.
     * @param password      The user's password.
     * @param creationTime  The user's creation time.
     * @param bio           The user's bio.
     * @param email         The user's email.
     * @param website       The user's website.
     * @param repoCount     The user's repository count.
     * @param followerCount The user's follower count.
     * @param followingCount The user's following count.
     */
    public User(int ID, String username, String password, Date creationTime, String bio, String email, String website, int repoCount, int followerCount, int followingCount) {
        this.ID = ID;
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

    /**
     * Default constructor to initialize a User object with default values.
     */
    public User() {
        this.ID = 1010;
        this.username = "FlowBoat123";
        this.password = "123";
        this.bio = "LoveCraft";
        this.email = "hienflb@gmail.com";
        this.website = "love.craft";
        this.repoCount = 0;
        this.followerCount = 0;
        this.followingCount = 0;
    }

    /**
     * Constructor to initialize a User object with username, password, and creation time.
     *
     * @param username     The user's username.
     * @param password     The user's password.
     * @param creationTime The user's creation time.
     */
    public User(String username, String password, Date creationTime) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
    }

    /**
     * Constructor to initialize a User object with username, password, creation time, bio, email, website, repo count, follower count, and following count.
     *
     * @param username      The user's username.
     * @param password      The user's password.
     * @param creationTime  The user's creation time.
     * @param bio           The user's bio.
     * @param email         The user's email.
     * @param website       The user's website.
     * @param repoCount     The user's repository count.
     * @param followerCount The user's follower count.
     * @param followingCount The user's following count.
     */
    public User(String username, String password, Date creationTime, String bio, String email, String website, int repoCount, int followerCount, int followingCount) {
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

    /**
     * Constructor to initialize a User object with username, password, creation time, bio, email, and website.
     *
     * @param username     The user's username.
     * @param password     The user's password.
     * @param creationTime The user's creation time.
     * @param bio          The user's bio.
     * @param email        The user's email.
     * @param website      The user's website.
     */
    public User(String username, String password, Date creationTime, String bio, String email, String website) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
        this.bio = bio;
        this.email = email;
        this.website = website;
    }

    /**
     * Constructor to initialize a User object with ID, username, password, creation time, bio, email, website, details, and avatar.
     *
     * @param ID            The user's ID.
     * @param username      The user's username.
     * @param password      The user's password.
     * @param creationTime  The user's creation time.
     * @param bio           The user's bio.
     * @param email         The user's email.
     * @param website       The user's website.
     * @param details       The user's details.
     * @param avatar        The user's avatar.
     */
    public User(int ID, String username, String password, Date creationTime, String bio, String email, String website, String details, String avatar) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
        this.bio = bio;
        this.email = email;
        this.website = website;
        this.details = details;
        this.avatar = avatar;
    }

    // Getters and Setters for all fields
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
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

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", creationTime=" + creationTime +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", details='" + details + '\'' +
                ", avatar='" + avatar + '\'' +
                ", repoCount=" + repoCount +
                ", followerCount=" + followerCount +
                ", followingCount=" + followingCount +
                '}';
    }
}