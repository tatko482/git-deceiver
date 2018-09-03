package com.stefanovskyi.gitdeceiver;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class FakeRepository {
    private String repositoryName = "TestGitRepository";

    public void createRepositoryInCurrentFolder() throws IOException, GitAPIException, URISyntaxException {

        File localPath = File.createTempFile(repositoryName, "");
        if (!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }
        Git git = Git.init().setDirectory(localPath).call();

        System.out.println("Having repository: " + git.getRepository().getDirectory());

        Repository repository = git.getRepository();

        // create the file
        String parent = repository.getDirectory().getParent();
        File myFile = new File(parent, "testfile");
        System.out.println(parent);
        if (!myFile.createNewFile()) {
            throw new IOException("Could not create file " + myFile);
        }

        git.add().addFilepattern("testfile")
                 .call();

        // and then commit the changes
        git.commit().setMessage("Added testfile")
                    .call();

        System.out.println("Committed file " + myFile + " to repository at " + repository.getDirectory());

        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(new URIish("https://github.com/url.git"));
        // you can add more settings here if needed
        remoteAddCommand.call();

        // push to remote:
        PushCommand pushCommand = git.push();
        pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("username", "password"));
        // you can add more settings here if needed
        pushCommand.call();


//        FileUtils.deleteDirectory(localPath);
    }
}
