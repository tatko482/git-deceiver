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

    public void addRemote(String remoteURI) {
        RemoteAddCommand remoteAddCommand = this.git.remoteAdd();
        remoteAddCommand.setName("origin");

        try {
            remoteAddCommand.setUri(new URIish(remoteURI));
            remoteAddCommand.call();
        } catch (URISyntaxException | GitAPIException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void makeFakeCommit(PersonIdent commitAuthor, String uniqueId) throws IOException, GitAPIException {
        Repository repository = this.git.getRepository();
        String fileName = getFile(uniqueId, repository).getName();

        git.add().addFilepattern(fileName).call();
        git.commit().setMessage("Added " + fileName)
                .setAuthor(commitAuthor)
                .call();

        LOGGER.info("Committed file " + fileName + " to repository at " + repository.getDirectory());
    }

    public PersonIdent getPersonIdent(String userFullName, String email, LocalDateTime commitDate) {
        Date date = Date.from(commitDate.atZone(ZoneId.systemDefault()).toInstant());
        TimeZone utc = TimeZone.getTimeZone("UTC");
        return new PersonIdent(userFullName, email, date, utc);
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
