package com.mojo.core;

import com.mojo.utils.ClassScannerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 19:22
 * @Description:
 */
@Mojo(name = "classScanner", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
@Slf4j
public class ClassScannerPlugin extends AbstractMojo {
    /**
     * Location of the file
     * target目录
     */
    @Parameter(defaultValue = "${project.build.directory}", required = true)
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

    /**
     * 传入参数--class扫描路径 多个使用逗号隔开
     */
//    @Parameter(property = "scanPackage", defaultValue = "com,org")
//    private String scanPackage;

    //存放扫描的注解--作用于类上的注解   比如自定义注解、spring注解
    @Parameter(property = "reporter.scannerPath")
    private String[] annotationTags;

    @Override
    public void execute() throws MojoFailureException {
        if (!mavenProject.getModules().isEmpty()) {
            return;
        }

        ClassScannerUtil.getClassPathElements(mavenProject);
        getLog().info("扫描地址2: " + mavenProject.getBasedir().getPath());
        getLog().info("输出文件路径-target目录: " + outputTargetDirectory);
        getLog().info("输出的文件名称: " + projectArtifactId);

        //扫描自定义注解  或 注解 spring 注解
        ClassScannerUtil.initAnnotations(annotationTags);
        //解析class类，获取每个方法上的参数 并存入excel中
        ClassScannerUtil.compileScan(mavenProject.getBasedir().getPath());
    }
}
