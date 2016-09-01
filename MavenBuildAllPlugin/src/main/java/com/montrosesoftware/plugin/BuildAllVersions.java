package com.montrosesoftware.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Mojo( name = "buildAll")
public class BuildAllVersions extends AbstractMojo
{
    @Parameter(property = "buildAll.projectNames")
    private List<String> projectNames;

    public void execute() throws MojoExecutionException
    {
        if(projectNames == null || projectNames.isEmpty()){
            return;
        }

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File( "pom.xml" ) );
        request.setGoals( Arrays.asList( "clean", "test" ) );

        for (String name : projectNames){
            Properties properties = new Properties();
            properties.setProperty("montrosesoftware.version", name);
            request.setProperties(properties);

            Invoker invoker = new DefaultInvoker();
            try {
                invoker.execute( request );
            } catch (MavenInvocationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}