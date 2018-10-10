package com.stefanovskyi.gitdeceiver;

import com.stefanovskyi.gitdeceiver.util.Util;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;
import java.time.Month;

public class GitDeceiver {

    public static void main(String args[]) {

        GitAuthData gitAuthData = new GitAuthData(args).invoke();
        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(gitAuthData.getRepositoryName());
        fakeRepository.addRemote(gitAuthData.getRepositoryName());
        LocalDateTime commitDate = getStartDate();

        for (int i = 0; i < 10; i++) {
            String date = LocalDateTime.now().toString();

            commitDate = commitDate.plusDays(1);
            PersonIdent personIdent = fakeRepository.getPersonIdent(gitAuthData.getUserFullName(),
                                                                    gitAuthData.getUserEmail(),
                                                                    commitDate);
            fakeRepository.makeFakeCommit(personIdent, date);
        }

        fakeRepository.pushToRemote(gitAuthData.getLogin(), gitAuthData.getPassword());
        Util.deleteRepositoryFolderIfExists(Util.getRepositoryNameFromUrl(gitAuthData.getRepositoryName()));
    }

    private static LocalDateTime getStartDate() {
        return LocalDateTime.of(2017, Month.SEPTEMBER, 4, 5, 15);
    }

    private static class GitAuthData {
        private String[] args;
        private String repositoryName;
        private String userFullName;
        private String userEmail;
        private String login;
        private String password;

        public GitAuthData(String... args) {
            this.args = args;
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

        public GitAuthData invoke() {
            repositoryName = args[0];
            userFullName = args[1];
            userEmail = args[2];
            login = args[3];
            password = args[4];
            return this;
        }
    }
}
