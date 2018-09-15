package com.stefanovskyi.gitdeceiver;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.stefanovskyi.gitdeceiver.util.Util.deleteRepositoryFolderIfExists;
import static com.stefanovskyi.gitdeceiver.util.Util.getRepositoryNameFromUrl;
import static junit.framework.TestCase.assertTrue;

public class FakeRepositoryTest {

    private FakeRepository fakeRepository = new FakeRepository();

    @Test
    public void cloneRepository() {
        String url = "https://github.com/AStefanovskiy/social_mapper.git";
        String repositoryNameFromUrl = getRepositoryNameFromUrl(url);

        deleteRepositoryFolderIfExists(repositoryNameFromUrl);

        fakeRepository.cloneRepository(url);
        assertTrue(Files.exists(Paths.get(repositoryNameFromUrl)));

        deleteRepositoryFolderIfExists(repositoryNameFromUrl);
    }
}
