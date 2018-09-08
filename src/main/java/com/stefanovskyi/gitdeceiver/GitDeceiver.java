package com.stefanovskyi.gitdeceiver;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class GitDeceiver {
    public static void main(String args[]) throws IOException, GitAPIException, URISyntaxException {
        String repositoryName = "";
        String userFullName = "";
        String userEmail = "";
        String login = "";
        String password = "";

        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(repositoryName);
        fakeRepository.addRemote(repositoryName);

        for (int i = 0; i < 10; i++) {
            String date = LocalDateTime.now().toString();
            fakeRepository.makeFakeCommit(userFullName, userEmail, date);
        }

        fakeRepository.pushToRemote(login, password);
    }
}
