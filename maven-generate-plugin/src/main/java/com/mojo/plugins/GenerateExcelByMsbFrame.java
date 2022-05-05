package com.mojo.plugins;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 19:22
 * @Description:
 */
@Mojo(name = "genExcel",defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class GenerateExcelByMsbFrame extends AbstractMojo {
    /**
     * Location of the file
     * target目录
     */
    @Parameter(defaultValue="${project.build.directory}", required = true)
    private File outputTargetDirectory;

    /**
     * 输出的文件名称
     */
    @Parameter(defaultValue = "${project.artifactId}", required = true)
    private String projectArtifactId;

    /**
     * 工程路径
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject mavenProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!mavenProject.getModules().isEmpty()){
            return;
        }
        try {
//            List runtimeClasspathElements = project.getRuntimeClasspathElements();
//            URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
//            for (int i = 0; i < runtimeClasspathElements.size(); i++) {
//                String element = (String) runtimeClasspathElements.get(i);
//                runtimeUrls[i] = new File(element).toURI().toURL();
//            }
//            URLClassLoader newLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());

            //加载classloader
            List<String> runtimeClasspathElementList = mavenProject.getRuntimeClasspathElements();
            Set<URL> urlLinkedHashSet = new LinkedHashSet<URL>(runtimeClasspathElementList.size());

            for (String element : runtimeClasspathElementList) {
                urlLinkedHashSet.add(new File(element).toURI().toURL());
            }
            ClassLoader contextClassLoader = URLClassLoader.newInstance(urlLinkedHashSet.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (DependencyResolutionRequiredException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        getLog().info("扫描地址: " + mavenProject.getBasedir().getPath());
        getLog().info("扫描地址2: " + outputTargetDirectory);
        getLog().info("扫描地址3: " + projectArtifactId);
    }

    private URL[] getRuntimeClasspathElements() throws MojoFailureException {
        URL[] runtimeUrls = null;
        try {
            List<String> compileClasspathElements = mavenProject.getCompileClasspathElements();
            List<String> runtimeClasspathElements = mavenProject.getRuntimeClasspathElements();
            Set<String> allClassPathElements = new LinkedHashSet<>(compileClasspathElements.size());
            allClassPathElements.addAll(compileClasspathElements);
            allClassPathElements.addAll(runtimeClasspathElements);
            runtimeUrls = new URL[allClassPathElements.size()];
            int index=0;
            for (String s: allClassPathElements ) {
                runtimeUrls[index++] = new File(s).toURI().toURL();
            }
        } catch (Exception exception) {
            throw new MojoFailureException("Failed resolve project dependencies", exception);
        }

        return runtimeUrls;
    }
}
