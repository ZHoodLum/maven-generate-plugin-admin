package com.mojo.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 18:28
 * @Description:测试自定义Mojo 方式一：@Mojo
 */
@Mojo(name = "helloWorld",defaultPhase = LifecyclePhase.VALIDATE)
public class HelloWorldMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("========= HelloWorldMojo ==========");
    }
}
