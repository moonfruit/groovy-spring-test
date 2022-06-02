package com.github.moonfruit.test;

import groovy.lang.GroovyClassLoader;
import groovy.transform.CompileStatic;
import groovy.typecheckers.RegexChecker;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.util.Collections.singletonMap;

@Component
public class GroovyTest implements ApplicationRunner, BeanClassLoaderAware {

    private static final Logger log = LoggerFactory.getLogger(GroovyTest.class);

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("classLoader = {}", classLoader);

        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(new ASTTransformationCustomizer(
                singletonMap("extensions", RegexChecker.class.getName()),
                CompileStatic.class, classLoader));

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(classLoader, configuration);
        Class<?> aClass = groovyClassLoader.parseClass("1+1");
        log.info("class = {}", aClass);
    }
}
