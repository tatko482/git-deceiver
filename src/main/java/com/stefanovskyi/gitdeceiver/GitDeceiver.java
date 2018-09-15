package com.stefanovskyi.gitdeceiver;

import com.stefanovskyi.gitdeceiver.util.Util;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;

public class GitDeceiver {
    public static void main(String args[]) {
        String repositoryName = "";
        String userFullName = "";
        String userEmail = "";
        String login = "";
        String password = "";

        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(repositoryName);
        fakeRepository.addRemote(repositoryName);
        LocalDateTime commitDate = LocalDateTime.of(2017, Month.SEPTEMBER, 4, 5, 15);

        for (int i = 0; i < 10; i++) {
            String date = LocalDateTime.now().toString();
            commitDate = commitDate.plusDays(1);
            PersonIdent personIdent = fakeRepository.getPersonIdent(userFullName, userEmail, commitDate);
            fakeRepository.makeFakeCommit(personIdent, date);
        }

        fakeRepository.pushToRemote(login, password);
        Util.deleteRepositoryFolderIfExists(Util.getRepositoryNameFromUrl(repositoryName));
    }
}
