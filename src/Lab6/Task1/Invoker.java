package Lab6.Task1;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Invoker {

    public static void main(String[] args) {
        try {
            MyClass myClass = new MyClass();
            Class<?> clazz = myClass.getClass();
            Method[] methods = clazz.getDeclaredMethods();

            System.out.println("=== Вызов аннотированных защищенных и приватных методов ===\n");

            for (Method method : methods) {
                if (method.isAnnotationPresent(Repeat.class)) {
                    Repeat repeat = method.getAnnotation(Repeat.class);
                    int times = repeat.times();

                    // Делаем метод доступным если он приватный
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    System.out.println("Метод: " + method.getName() +
                            ", Аннотация @Repeat(times = " + times + ")");

                    // Вызываем метод указанное количество раз
                    for (int i = 0; i < times; i++) {
                        try {
                            Object result;
                            if (method.getParameterCount() == 0) {
                                result = method.invoke(myClass);
                            } else {
                                // Простые параметры для демонстрации
                                Class<?>[] paramTypes = method.getParameterTypes();
                                Object[] params = new Object[paramTypes.length];

                                for (int j = 0; j < paramTypes.length; j++) {
                                    if (paramTypes[j] == String.class) {
                                        params[j] = "Test" + (i + 1);
                                    } else if (paramTypes[j] == int.class) {
                                        params[j] = (i + 1) * 10;
                                    }
                                }

                                result = method.invoke(myClass, params);
                            }

                            System.out.println("  Вызов " + (i + 1) + ": " + result);

                        } catch (IllegalAccessException | InvocationTargetException e) {
                            System.err.println("Ошибка при вызове метода: " + e.getMessage());
                        }
                    }
                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
