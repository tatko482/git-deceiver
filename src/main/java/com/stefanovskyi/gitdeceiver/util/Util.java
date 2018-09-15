package com.stefanovskyi.gitdeceiver.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.lib.Repository;

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

    public static File getFile(String uniqueId, Repository repository) {
        String safeUniqueId = removeRestrictedSigns(uniqueId);
        String parent = repository.getDirectory().getParent();
        String child = "testfile_" + safeUniqueId;

        File myFile = new File(parent, child);
        try {
            if (!myFile.createNewFile()) {
                try {
                    throw new IOException("Could not create file " + myFile);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return myFile;
    }

    private static String removeRestrictedSigns(String uniqueId) {
        return uniqueId.replace("\\.", "_").replace(":", "_");
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
