package com.github.moonfruit.test;

import groovy.lang.GroovyClassLoader;
import groovy.transform.CompileStatic;
import groovy.typecheckers.RegexChecker;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Collections.singletonMap;

@Component
public class SimpleMain implements CommandLineRunner, BeanClassLoaderAware {

    private static final Logger log = LoggerFactory.getLogger(SimpleMain.class);

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void run(String... args) {
        log.info("classLoader = {}", classLoader);

        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(new ASTTransformationCustomizer(
                singletonMap("extensions", RegexChecker.class.getName()),
                CompileStatic.class, classLoader));

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(classLoader, configuration);
        Class<?> aClass = groovyClassLoader.parseClass("1+1");
        log.info("class = {}", aClass);
    }

    public static void main(String[] args) {
        new SimpleMain().run(args);
    }
}
