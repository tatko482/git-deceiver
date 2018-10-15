package com.stefanovskyi.gitdeceiver;

import com.stefanovskyi.gitdeceiver.util.Util;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Random;

public class GitDeceiver {

    public static void main(String args[]) {

        GitAuthData gitAuthData = new GitAuthData(args).invoke();
        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(gitAuthData.getRepositoryName());
        fakeRepository.addRemote(gitAuthData.getRepositoryName());
        LocalDateTime commitDate = Util.getStartDate(args[5]);

        Random rand = new Random();

        int amountOfDays = 333;

        for (int day = 0; day < amountOfDays; day++) {
            commitDate = commitDate.plusDays(1);

            int commitsPerDay = getCommitsAmountPerDay(commitDate, rand);

            for (int commitNumber = 0; commitNumber < commitsPerDay; commitNumber++) {

                commitDate = commitDate.plusMinutes(15);
                PersonIdent personIdent = fakeRepository.getPersonIdent(gitAuthData.getUserFullName(),
                        gitAuthData.getUserEmail(),
                        commitDate);
                fakeRepository.makeFakeCommit(personIdent);
            }
        }

        fakeRepository.pushToRemote(gitAuthData.getLogin(), gitAuthData.getPassword());
        Util.deleteRepositoryFolderIfExists(Util.getRepositoryNameFromUrl(gitAuthData.getRepositoryName()));
    }

    private static int getCommitsAmountPerDay(LocalDateTime commitDate, Random rand) {
        int maximumCommitsPerWeekDay = 6;
        int maximumCommitsPerWeekend = 15;
        int minimumCommitsPerWeekend = 8;
        int fridayMaximumCommits = 10;
        int fridayMinimumCommits = 6;

        DayOfWeek dayOfWeek = commitDate.getDayOfWeek();

        int commitsPerDay;

        if (dayOfWeek.getValue() == 7 || dayOfWeek.getValue() == 6) {
            commitsPerDay = rand.nextInt(maximumCommitsPerWeekend) + minimumCommitsPerWeekend;
        } else if (dayOfWeek.getValue() == 5) {

            commitsPerDay = rand.nextInt(fridayMaximumCommits) + fridayMinimumCommits;
        }
        else {
            commitsPerDay = rand.nextInt(maximumCommitsPerWeekDay) + 1;
        }
        return commitsPerDay;
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
