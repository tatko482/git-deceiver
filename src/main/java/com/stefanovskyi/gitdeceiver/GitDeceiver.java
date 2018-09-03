package com.stefanovskyi.gitdeceiver;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.net.URISyntaxException;

public class GitDeceiver {
    public static void main(String args[]) throws IOException, GitAPIException, URISyntaxException {
        System.out.println("Git Deceiver");

        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.createRepositoryInCurrentFolder();
    }
}
