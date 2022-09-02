package chapter6.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author qyl
 * @program RunTests.java
 * @createTime 2022-09-02 13:56
 */
public class RunTests {
    public static void main(String[] args) throws Exception {
        runSample3();
    }

    public static void runSample() throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        Class<?> clazz = Class.forName("chapter6.annotations.Sample");
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    System.out.println("m + failed :" + exc);
                } catch (Exception e) {
                    System.out.println("Invalid @Test :" + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }

    public static void runSample2() throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        Class<?> clazz = Class.forName(Sample2.class.getName());
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                int oldPassed = passed;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n",m);
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    Class<? extends Exception>[] excTypes = m.getAnnotation(ExceptionTest.class).value();
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if(passed == oldPassed){
                        System.out.printf("Test %s failed: %s %n",m,exc);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid @Exception :" + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }

    public static void runSample3() throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        Class<?> clazz = Class.forName(Sample2.class.getName());
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionRTest.class)) {
                tests++;
                int oldPassed = passed;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n",m);
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    ExceptionRTest[] excTypes = m.getAnnotation(ExceptionTestContainer.class).value();
                    for (ExceptionRTest excType : excTypes) {
                        if (excType.value().isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if(passed == oldPassed){
                        System.out.printf("Test %s failed: %s %n",m,exc);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid @Exception :" + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }
}
