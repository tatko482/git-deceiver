package com.stefanovskyi.gitdeceiver.util;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class UtilTest {

    @Test
    public void whenDeleteRepositoryFolderIfExistsExecuted_thenFolderRemoved() {
        String directoryName = "directory_name";
        createDirectoryIfNotExists(directoryName);
        Util.deleteRepositoryFolderIfExists(directoryName);

        assertFalse(Files.exists(Paths.get(directoryName)));
    }

    @Test
    public void whenGetRepositoryNameFromUrlExecuted_ThenReturnRepositoryName() {
        String repositoryName = "social_mapper";
        String url = "https://github.com/AStefanovskiy/social_mapper.git";
        String repositoryNameFromUrl = Util.getRepositoryNameFromUrl(url);
        assertEquals(repositoryNameFromUrl, repositoryName);
    }

    @Test
    public void whenGetRandomStringExecuted_thanStringOfSizeReturned() {
        int length = 10;
        String randomString = Util.getRandomString(length);
        assertEquals(randomString.length(), length);
    }

    @Test
    public void whenGetRandomStringExecutedMultipleTimes_thanResultAlwaysDifferent() {
        List<String> listOfRandomStrings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listOfRandomStrings.add(Util.getRandomString(10));
        }
        Set<String> listWithoutDuplicates = new HashSet<>(listOfRandomStrings);

        assertEquals(listOfRandomStrings.size(), listWithoutDuplicates.size());
    }

    @Test
    public void whenGetStartDateExecuted_validLocalDateTimeReturned() {
        LocalDateTime startDate = Util.getStartDate("2017-11-4-5-15");
        LocalDateTime actual = LocalDateTime.of(2017, Month.of(11), 4, 5, 15);

        assertEquals(actual, startDate);
    }

    private void createDirectoryIfNotExists(String directoryName) {
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
}