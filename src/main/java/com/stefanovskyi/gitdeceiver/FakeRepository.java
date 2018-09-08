package com.stefanovskyi.gitdeceiver;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class FakeRepository {
    private final static Logger LOGGER = Logger.getLogger(FakeRepository.class);

    private Git git;

    public void pushToRemote(String username, String password) throws GitAPIException {
        PushCommand pushCommand = this.git.push();
        UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(username, password);
        pushCommand.setCredentialsProvider(user);
        pushCommand.call();
    }

    public void addRemote(String remoteURI) throws URISyntaxException, GitAPIException {
        RemoteAddCommand remoteAddCommand = this.git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(new URIish(remoteURI));
        remoteAddCommand.call();
    }

    public void makeFakeCommit(String userFullName, String email, String uniqueId) throws IOException, GitAPIException {
        Repository repository = this.git.getRepository();

        File myFile = getFile(uniqueId, repository);
        String fileName = myFile.getName();
        git.add().addFilepattern(fileName).call();

        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.SEPTEMBER, 4, 5, 15);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        TimeZone utc = TimeZone.getTimeZone("UTC");
        PersonIdent firstAuthor = new PersonIdent(userFullName, email, date, utc);

        git.commit().setMessage("Added " + fileName)
                .setAuthor(firstAuthor)
                .call();

        LOGGER.info("Committed file " + myFile + " to repository at " + repository.getDirectory());
    }

    public void cloneRepository(String repositoryName) {
        try {
            this.git = Git.cloneRepository().setURI(repositoryName).call();
        } catch (GitAPIException e) {
            LOGGER.info(e.getMessage());
        }
    }

    private File getFile(String uniqueId, Repository repository) throws IOException {
        String safeUniqueId = uniqueId.replace("\\.", "_").replace(":", "_");
        String parent = repository.getDirectory().getParent();
        String child = "testfile_" + safeUniqueId;

        File myFile = new File(parent, child);
        if (!myFile.createNewFile()) {
            throw new IOException("Could not create file " + myFile);
        }
        return myFile;
    }
}
