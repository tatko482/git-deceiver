package com.stefanovskyi.gitdeceiver.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {
    private final static Logger LOGGER = Logger.getLogger(Util.class.getName());

    public static void deleteRepositoryFolderIfExists(String repositoryNameFromUrl) {
        if (Files.exists(Paths.get(repositoryNameFromUrl))) {
            File folderName = new File(repositoryNameFromUrl);
            try {
                FileUtils.deleteDirectory(folderName);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    public static String getRepositoryNameFromUrl(String url) {
        return url.split("/")[4].split("\\.")[0];
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
