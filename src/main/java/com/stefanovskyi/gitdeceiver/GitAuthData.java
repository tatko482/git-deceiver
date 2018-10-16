package com.stefanovskyi.gitdeceiver;

public class GitAuthData {
    private String repositoryName;
    private String userFullName;
    private String userEmail;
    private String login;
    private String password;

    public GitAuthData(String... args) {
        repositoryName = args[0];
        userFullName = args[1];
        userEmail = args[2];
        login = args[3];
        password = args[4];
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
