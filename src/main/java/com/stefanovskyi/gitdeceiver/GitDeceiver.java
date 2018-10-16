package com.stefanovskyi.gitdeceiver;

import com.stefanovskyi.gitdeceiver.util.Util;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Random;

public class GitDeceiver {

    public static void main(String args[]) {

        GitAuthData gitAuthData = new GitAuthData(args);
        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(gitAuthData.getRepositoryName());
        fakeRepository.addRemote(gitAuthData.getRepositoryName());
        LocalDateTime commitDate = Util.getStartDate(args[5]);

        int amountOfDays = 333;

        generateCommits(gitAuthData, fakeRepository, commitDate, amountOfDays);

        fakeRepository.pushToRemote(gitAuthData.getLogin(), gitAuthData.getPassword());
        Util.deleteRepositoryFolderIfExists(Util.getRepositoryNameFromUrl(gitAuthData.getRepositoryName()));
    }

    private static void generateCommits(GitAuthData gitAuthData, FakeRepository fakeRepository,
                                        LocalDateTime commitDate, int amountOfDays) {
        Random rand = new Random();

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
}
