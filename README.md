# groovy-spring-test

I found that RegexChecker is not working when loading form Spring Boot and Java 8. This repository has some codes to reveal it.

Code:
```java
ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

CompilerConfiguration configuration = new CompilerConfiguration();
configuration.addCompilationCustomizers(new ASTTransformationCustomizer(
        singletonMap("extensions", RegexChecker.class.getName()),
        CompileStatic.class, classLoader));

GroovyClassLoader groovyClassLoader = new GroovyClassLoader(classLoader, configuration);
Class<?> aClass = groovyClassLoader.parseClass("1+1");
```

Above code when running in spring boot will preduce a java.lang.IllegalAccessError:
<details>
  <summary>Click to expand!</summary>

```
java.lang.IllegalAccessError: tried to access method groovy.typecheckers.RegexChecker$1.checkPatternMethod(Lorg/codehaus/groovy/ast/expr/MethodCall;Lorg/codehaus/groovy/ast/ClassNode;)V from class groovy.typecheckers.RegexChecker$1$checkPatternMethod
	at org.codehaus.groovy.control.CompilationUnit$IPrimaryClassNodeOperation.doPhaseOperation(CompilationUnit.java:971)
	at org.codehaus.groovy.control.CompilationUnit.processPhaseOperations(CompilationUnit.java:692)
	at org.codehaus.groovy.control.CompilationUnit.compile(CompilationUnit.java:666)
	at groovy.lang.GroovyClassLoader.doParseClass(GroovyClassLoader.java:373)
	at groovy.lang.GroovyClassLoader.lambda$parseClass$2(GroovyClassLoader.java:316)
	at org.codehaus.groovy.runtime.memoize.StampedCommonCache.compute(StampedCommonCache.java:163)
	at org.codehaus.groovy.runtime.memoize.StampedCommonCache.getAndPut(StampedCommonCache.java:154)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:314)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:298)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:258)
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:274)
	at com.github.moonfruit.test.SimpleMain.run(SimpleMain.java:38)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:771)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:755)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1306)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1295)
	at com.github.moonfruit.test.SpringMain.main(SpringMain.java:10)
Caused by: java.lang.IllegalAccessError: tried to access method groovy.typecheckers.RegexChecker$1.checkPatternMethod(Lorg/codehaus/groovy/ast/expr/MethodCall;Lorg/codehaus/groovy/ast/ClassNode;)V from class groovy.typecheckers.RegexChecker$1$checkPatternMethod
	at groovy.typecheckers.RegexChecker$1$checkPatternMethod.callCurrent(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallCurrent(CallSiteArray.java:49)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:171)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:194)
	at groovy.typecheckers.RegexChecker$1.visitStaticMethodCallExpression(RegexChecker.groovy:157)
	at org.codehaus.groovy.ast.expr.StaticMethodCallExpression.visit(StaticMethodCallExpression.java:44)
	at org.codehaus.groovy.ast.CodeVisitorSupport.visitExpressionStatement(CodeVisitorSupport.java:117)
	at org.codehaus.groovy.ast.ClassCodeVisitorSupport.visitExpressionStatement(ClassCodeVisitorSupport.java:204)
	at org.codehaus.groovy.ast.stmt.ExpressionStatement.visit(ExpressionStatement.java:41)
	at org.codehaus.groovy.ast.stmt.ExpressionStatement$visit.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:45)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:125)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:139)
	at groovy.typecheckers.RegexChecker$_run_closure1.doCall(RegexChecker.groovy:183)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:343)
	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:328)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:279)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1009)
	at groovy.lang.Closure.call(Closure.java:418)
	at org.codehaus.groovy.transform.stc.AbstractTypeCheckingExtension.safeCall(AbstractTypeCheckingExtension.java:183)
	at org.codehaus.groovy.transform.stc.GroovyTypeCheckingExtensionSupport.beforeVisitMethod(GroovyTypeCheckingExtensionSupport.java:320)
	at org.codehaus.groovy.transform.stc.DefaultTypeCheckingExtension.beforeVisitMethod(DefaultTypeCheckingExtension.java:139)
	at org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor.visitMethod(StaticTypeCheckingVisitor.java:2552)
	at org.codehaus.groovy.transform.sc.StaticCompilationVisitor.visitConstructorOrMethod(StaticCompilationVisitor.java:236)
	at org.codehaus.groovy.transform.sc.StaticCompilationVisitor.visitMethod(StaticCompilationVisitor.java:251)
	at org.codehaus.groovy.ast.ClassNode.visitMethods(ClassNode.java:1131)
	at org.codehaus.groovy.ast.ClassNode.visitContents(ClassNode.java:1124)
	at org.codehaus.groovy.ast.ClassCodeVisitorSupport.visitClass(ClassCodeVisitorSupport.java:52)
	at org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor.visitClass(StaticTypeCheckingVisitor.java:439)
	at org.codehaus.groovy.transform.sc.StaticCompilationVisitor.visitClass(StaticCompilationVisitor.java:197)
	at org.codehaus.groovy.transform.sc.StaticCompileTransformation.visit(StaticCompileTransformation.java:68)
	at org.codehaus.groovy.control.customizers.ASTTransformationCustomizer.call(ASTTransformationCustomizer.groovy:298)
	at org.codehaus.groovy.control.CompilationUnit$IPrimaryClassNodeOperation.doPhaseOperation(CompilationUnit.java:937)
	... 17 more

1 error

	at org.codehaus.groovy.control.ErrorCollector.failIfErrors(ErrorCollector.java:292) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.control.ErrorCollector.addException(ErrorCollector.java:140) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.control.CompilationUnit$IPrimaryClassNodeOperation.doPhaseOperation(CompilationUnit.java:971) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.control.CompilationUnit.processPhaseOperations(CompilationUnit.java:692) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.control.CompilationUnit.compile(CompilationUnit.java:666) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.doParseClass(GroovyClassLoader.java:373) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.lambda$parseClass$2(GroovyClassLoader.java:316) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.runtime.memoize.StampedCommonCache.compute(StampedCommonCache.java:163) ~[groovy-4.0.2.jar:4.0.2]
	at org.codehaus.groovy.runtime.memoize.StampedCommonCache.getAndPut(StampedCommonCache.java:154) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:314) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:298) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:258) ~[groovy-4.0.2.jar:4.0.2]
	at groovy.lang.GroovyClassLoader.parseClass(GroovyClassLoader.java:274) ~[groovy-4.0.2.jar:4.0.2]
	at com.github.moonfruit.test.SimpleMain.run(SimpleMain.java:38) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:771) [spring-boot-2.7.0.jar:2.7.0]
	... 5 common frames omitted
```

</details>

Load from spring boot:
```console
./mvnw spring-boot:run
```

Simple run the same codes:
```console
./mvnw compile exec:java
```
