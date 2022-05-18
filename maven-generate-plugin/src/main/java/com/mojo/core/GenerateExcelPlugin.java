package com.mojo.core;

import com.mojo.utils.ClassScannerUtil;
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
 * @requiresDependencyResolution compile
 */
@Mojo(name = "genExcel", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class GenerateExcelPlugin extends AbstractMojo {
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
    @Parameter(property = "scanPackage", defaultValue = "com,org")
    private String scanPackage;

    @Parameter(property = "reporter.scannerPath")
    private String annotationTags;

    @Override
    public void execute() throws MojoFailureException {
        if (!mavenProject.getModules().isEmpty()) {
            return;
        }

        ClassScannerUtil.getClassPathElements(mavenProject);
        //此处注释掉的原因是：不采用自定义类加载器去加载类信息
        //直接使用官方注解 @requiresDependencyResolution compile 该属性可以获取目标项目的环境类路径
        //详细详见印象笔记文章：https://app.yinxiang.com/shard/s14/nl/18331031/9d251bd5-a8da-48d4-b878-e4e0cbce34ee
//        if (null == scanPackage || "".equals(scanPackage)) {
//            scanPackage = "com";
//        }
        // 获取提供者名称
//        Set<Class<?>> needMockSet = ClassScanner.scan(scanPackage.split(","));

        getLog().info("扫描地址: " + mavenProject.getBasedir().getPath());
        getLog().info("输出文件路径-target目录: " + outputTargetDirectory);
        getLog().info("输出的文件名称: " + projectArtifactId);
        ClassScannerUtil.scan(mavenProject.getBasedir().getPath());
    }
}
