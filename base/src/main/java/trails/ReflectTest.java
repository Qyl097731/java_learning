package trails;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description
 * @date:2023/1/22 21:41
 * @author: qyl
 */
public class ReflectTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = Hello.class;
        Hello instance = (Hello) clazz.newInstance ();

        Method[] methods = clazz.getDeclaredMethods ();
        for (Method method : methods) {
            System.out.println (method.getName ());
        }

        Method publicMethod = clazz.getDeclaredMethod ("publicMethod", String.class);
        publicMethod.invoke (instance,"good boy");

        Field[] fields = clazz.getDeclaredFields ();
        for (Field field : fields) {
            field.setAccessible (true);
            field.set (instance,"good");
        }

        Method privateMethod = clazz.getDeclaredMethod ("privateMethod");
        privateMethod.setAccessible (true);
        privateMethod.invoke (instance);
    }
    static class Hello{
        static {
            System.out.println ("hello");
        }

        private String value;

        public Hello() {
            value = "hello";
        }

        public void publicMethod(String s) {
            System.out.println("I love " + s);
        }

        private void privateMethod() {
            System.out.println("value is " + value);
        }
    }
}
