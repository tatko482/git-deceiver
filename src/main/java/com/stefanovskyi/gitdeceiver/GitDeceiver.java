package com.stefanovskyi.gitdeceiver;

import com.stefanovskyi.gitdeceiver.util.Util;

import java.time.LocalDateTime;

public class GitDeceiver {

    public static void main(String args[]) {

        GitAuthData gitAuthData = new GitAuthData(args);
        FakeRepository fakeRepository = new FakeRepository();

        fakeRepository.cloneRepository(gitAuthData.getRepositoryName());
        fakeRepository.addRemote(gitAuthData.getRepositoryName());
        LocalDateTime commitDate = Util.getStartDate(args[5]);
        int amountOfDays = Integer.parseInt(args[6]);

        fakeRepository.generateCommits(gitAuthData, fakeRepository, commitDate, amountOfDays);

        fakeRepository.pushToRemote(gitAuthData.getLogin(), gitAuthData.getPassword());
        Util.deleteRepositoryFolderIfExists(Util.getRepositoryNameFromUrl(gitAuthData.getRepositoryName()));
    }
}
