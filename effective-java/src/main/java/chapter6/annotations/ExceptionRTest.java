package chapter6.annotations;

import java.lang.annotation.*;

/**
 * @author qyl
 * @program ExceptionRTest.java
 * @Description @Repeatable测试
 * @createTime 2022-09-02 14:32
 */
@Repeatable(ExceptionTestContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionRTest {
    Class<? extends Exception> value();
}

/**
 * @author nsec
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTestContainer{
    ExceptionRTest[] value();
}
