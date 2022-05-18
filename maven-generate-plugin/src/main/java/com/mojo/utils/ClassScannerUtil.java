package com.mojo.utils;

import com.mojo.utils.ClassSourceBuilderUtil;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/10 21:14
 * @Description: 核心处理类 类扫描器
 */
@Slf4j
public class ClassScannerUtil {

    private static final String RESOURCE_PATTERN = "%s/**/*.class";
    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public ClassScannerUtil(){}

    /**
     * @param basePath       扫描包路径
     * @return
     */
    public static final Set<Class<?>> scan(String[] basePath) {
        Set<Class<?>> resClazzSet = new HashSet<>();
        List<TypeFilter> typeFilters = new LinkedList<>();

        if (ArrayUtils.isNotEmpty(basePath)) {
            for (String pkg : basePath) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + String.format(RESOURCE_PATTERN, ClassUtils.convertClassNameToResourcePath(pkg));
                try {
                    Resource[] resources = resourcePatternResolver.getResources(pattern);
                    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader = readerFactory.getMetadataReader(resource);
                            String className = reader.getClassMetadata().getClassName();
                            if (ifMatchesEntityType(reader, readerFactory, typeFilters)) {
                                Class<?> curClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                                resClazzSet.add(curClass);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("扫描提取[{}]包路径下，标记了注解[{}]的类出现异常", pattern, StringUtils.join(typeFilters, ","));
                }
            }
        }
        return resClazzSet;
    }

    /**
     *
     * @param basePath 扫描包路径
     * @param annotationTags 注解标签
     * @return
     */
    public static final Set<Class<?>> scan(String[] basePath, Class<? extends Annotation>... annotationTags) {
        Set<Class<?>> resClazzSet = new HashSet<>();
        List<TypeFilter> typeFilters = new LinkedList<>();
        if (ArrayUtils.isNotEmpty(annotationTags)){
            for (Class<? extends Annotation> annotation : annotationTags) {
                typeFilters.add(new AnnotationTypeFilter(annotation, false));
            }
        }
        if (ArrayUtils.isNotEmpty(basePath)) {
            for (String pkg : basePath) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX  + String.format(RESOURCE_PATTERN, ClassUtils.convertClassNameToResourcePath(pkg));
                try {
                    Resource[] resources = resourcePatternResolver.getResources(pattern);
                    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader = readerFactory.getMetadataReader(resource);
                            String className = reader.getClassMetadata().getClassName();
                            if (ifMatchesEntityType(reader, readerFactory,typeFilters)) {
                                Class<?> curClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                                resClazzSet.add(curClass);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("扫描提取[{}]包路径下，标记了注解[{}]的类出现异常", pattern, StringUtils.join(typeFilters,","));
                }
            }
        }
        return resClazzSet;
    }

    /**
     * 检查当前扫描到的类是否含有任何一个指定的注解标记
     * @param reader
     * @param readerFactory
     * @return ture/false
     */
    private static boolean ifMatchesEntityType(MetadataReader reader, MetadataReaderFactory readerFactory, List<TypeFilter> typeFilters) {
        if (!CollectionUtils.isEmpty(typeFilters)) {
            for (TypeFilter filter : typeFilters) {
                try {
                    if (filter.match(reader, readerFactory)) {
                        return true;
                    }
                } catch (IOException e) {
                    log.error("过滤匹配类型时出错 {}",e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 获取编译时和运行时的 ClassLoader 路径对象
     * @param mavenProject
     * @return
     * @throws MojoFailureException
     */
    public static URL[] getClassPathElements(MavenProject mavenProject) throws MojoFailureException {
        URL[] runTimeUrls = null;
        try {
            List<String> compileClasspathElements = mavenProject.getCompileClasspathElements();
            List<String> runtimeClasspathElements = mavenProject.getRuntimeClasspathElements();
            //存储编译时或运行时对象 由于不重复，所以可以使用set集合存储
            Set<String> allClassPathElements = new LinkedHashSet<>(compileClasspathElements.size());
            allClassPathElements.addAll(compileClasspathElements);
            allClassPathElements.addAll(runtimeClasspathElements);
            //创建数组  用于存放classpath对象
            runTimeUrls = new URL[allClassPathElements.size()];
            int index = 0;
            for (String element : allClassPathElements) {
                runTimeUrls[index++] = new File(element).toURI().toURL();
            }

            ClassLoader contextClassLoader = URLClassLoader.newInstance(runTimeUrls, Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (Exception exception) {
            throw new MojoFailureException("Failed resolve project dependencies", exception);
        }

        return runTimeUrls;
    }

    /**
     * 获取运行时的 ClassLoader 路径对象
     * @param mavenProject MavenProject
     * @return
     * @throws MojoFailureException
     */
    public static URL[] getRuntimeClasspathElements(MavenProject mavenProject) throws MojoFailureException {
        URL[] runTimeUrls = null;
        try {
            //加载classloader
            List<String> runtimeClasspathElementList = mavenProject.getRuntimeClasspathElements();
            Set<URL> urlLinkedHashSet = new LinkedHashSet<URL>(runtimeClasspathElementList.size());

            for (String element : runtimeClasspathElementList) {
                urlLinkedHashSet.add(new File(element).toURI().toURL());
            }

            ClassLoader contextClassLoader = URLClassLoader.newInstance(urlLinkedHashSet.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (Exception exception) {
            throw new MojoFailureException("Failed resolve project dependencies", exception);
        }

        return runTimeUrls;
    }

    /**
     * 扫描路径
     * @param path
     */
    public static void scan(String path){
        ClassSourceBuilderUtil classSourceBuilderUtil = new ClassSourceBuilderUtil(path);
        Set<JavaClass> controllerData = classSourceBuilderUtil.getControllerData();
        controllerData.stream().forEach(javaClass ->{
            List<JavaMethod> methods = javaClass.getMethods();
            methods.stream().forEach(javaMethod -> {
                //方法名
                System.out.println("====" + javaMethod.getName() + "====");
                //入参
                System.out.println(javaMethod.getParameterTypes());
                //出参
                System.out.println(javaMethod.getReturnType());
                //自定义注解
                System.out.println(javaMethod.getAnnotations());
                javaMethod.getAnnotations().stream().forEach(logApi ->{
                    System.out.println(logApi.getProperty("code"));
                    System.out.println(logApi.getProperty("name"));
                });
            });
        });
    }
}
