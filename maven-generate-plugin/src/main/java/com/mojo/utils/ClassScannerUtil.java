package com.mojo.utils;

import com.mojo.constant.AnnotationConstant;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
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

    private JavaProjectBuilder builder;

    private Collection<JavaClass> javaClasses;

    public ClassScannerUtil(String path) {
        builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(path));
        this.javaClasses = builder.getClasses();
    }

    /**
     * @param basePath 扫描包路径
     * @return
     * 此处不使用该方法的原因是：不采用自定义类加载器去加载类信息
     * 直接使用官方注解 @requiresDependencyResolution compile 该属性可以获取目标项目的环境类路径
     * 详细详见印象笔记文章：https://app.yinxiang.com/shard/s14/nl/18331031/9d251bd5-a8da-48d4-b878-e4e0cbce34ee
     *
     * 调用：
     * if (null == scanPackage || "".equals(scanPackage)) {
     *    scanPackage = "com";
     * }
     * // 获取提供者名称 适用于运行时
     * Set<Class<?>> needMockSet = ClassScannerUtil.scan(scanPackage.split(","));
     */
    public static final Set<Class<?>> runTimeScan(String[] basePath) {
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
     * @param basePath       扫描包路径
     * @param annotationTags 注解标签
     * @return
     */
    public static final Set<Class<?>> runTimeScan(String[] basePath, Class<? extends Annotation>... annotationTags) {
        Set<Class<?>> resClazzSet = new HashSet<>();
        List<TypeFilter> typeFilters = new LinkedList<>();
        if (ArrayUtils.isNotEmpty(annotationTags)) {
            for (Class<? extends Annotation> annotation : annotationTags) {
                typeFilters.add(new AnnotationTypeFilter(annotation, false));
            }
        }
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
     * 检查当前扫描到的类是否含有任何一个指定的注解标记
     *
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
                    log.error("过滤匹配类型时出错 {}", e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 获取编译时和运行时的 ClassLoader 路径对象
     *
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

//            ClassLoader contextClassLoader = URLClassLoader.newInstance(allClassPathElements.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());
            ClassLoader contextClassLoader = URLClassLoader.newInstance(runTimeUrls, Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (Exception exception) {
            throw new MojoFailureException("获取编译 or 运行时 ClassLoader 对象失败", exception);
        }

        return runTimeUrls;
    }

    /**
     * 初始化自定义注解值，将自定义注解添加到static final集合中
     *
     * @param annotationTags
     */
    public static void initAnnotations(String[] annotationTags) {
        Set<String> annotationSet = AnnotationConstant.ANNOTATION_SET;
        if (annotationTags.length != 0) {
            annotationSet.clear();
            log.info("使用自定义类注解, 剔除原对象初始化的值...");
            annotationSet.addAll(Arrays.asList(annotationTags));
        }
        log.info("检测类注解 {}", annotationTags.length != 0 ? Arrays.toString(annotationTags) : StringUtils.join(annotationSet, ","));
    }

    /**
     * 扫描路径
     *
     * @param path
     */
    public static void compileScan(String path) {
        //构建JavaProjectBuilder class类
        ClassScannerUtil classScannerUtil = new ClassScannerUtil(path);
        //根据annotationTags获取带有标签属性的类
        Set<JavaClass> annotationSet = classScannerUtil.getAnnotationTagsData();
        annotationSet.stream().forEach(javaClass -> {
            List<JavaMethod> methods = javaClass.getMethods();
            methods.stream().forEach(javaMethod -> {
                log.info("方法名 " + javaMethod.getName());
                log.info("入参 " + javaMethod.getParameterTypes());
                log.info("出参 " + javaMethod.getReturnType());
                log.info("自定义注解 " + javaMethod.getAnnotations());
                javaMethod.getAnnotations().stream().forEach(logApi -> {
                    log.info(String.valueOf(logApi.getProperty("code")));
                    log.info(String.valueOf(logApi.getProperty("name")));
                });
            });
        });
    }

    /**
     * 过滤类 检查类注解是否 包含annotation中的属性值
     *
     * @return
     */
    public Set<JavaClass> getAnnotationTagsData() {
        Set<JavaClass> apiMethodDocs = new HashSet<>();
        //全局变量 存放扫描哪些注解的集合
        Set<String> annotationSet = AnnotationConstant.ANNOTATION_SET;

        javaClasses.forEach(javaClass -> {
            try {
                List<JavaAnnotation> classAnnotations = javaClass.getAnnotations();
                classAnnotations.forEach(annotation -> {
                    if (annotationSet.contains(annotation.getType().getName())) {
                        apiMethodDocs.add(javaClass);
                    }
                });
            } catch (Exception e) {
                log.error("扫描提取[{}]包路径下, 类[{}]标记了注解[{}]的类出现异常", javaClass.getPackageName(), javaClass.getName(), StringUtils.join(annotationSet, ","));
                log.error("过滤匹配类型时出错 {}", e.getMessage());
            }
        });
        return apiMethodDocs;
    }

}
