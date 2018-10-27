package com.stefanovskyi.gitdeceiver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GitAuthDataTest {

    private GitAuthData gitAuthData;

    private String repository = "https://github.com/username/newrepo2.git";
    private String fullName = "User FullName";
    private String email = "user@email.com";
    private String username = "username";
    private String password = "password";

    @Before
    public void init() {
        gitAuthData = new GitAuthData(repository, fullName, email, username, password);
    }

    @Test
    public void getRepositoryName() {
        assertEquals(repository, gitAuthData.getRepositoryName());
    }

    @Test
    public void getUserFullName() {
        assertEquals(fullName, gitAuthData.getUserFullName());
    }

    @Test
    public void getUserEmail() {
        assertEquals(email, gitAuthData.getUserEmail());
    }

    @Test
    public void getLogin() {
        assertEquals(username, gitAuthData.getLogin());
    }

    @Test
    public void getPassword() {
        assertEquals(password, gitAuthData.getPassword());
    }
}