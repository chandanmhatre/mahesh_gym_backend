package com.gym.app.mahesh_gym.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityUtils {

    private EntityUtils() {
        // Private constructor to prevent instantiation
    }

    public static void mergeNonNullProperties(Object source, Object existing) {
        // Step 1: Get all declared fields of the object's class
        Arrays.stream(source.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);  // Step 2: Enable access to private fields

                    try {
                        Object newValue = field.get(source);  // Step 3: Get new value
                        Field targetField = getField(existing.getClass(), field.getName()); // Get matching field in existing

                        if (targetField != null) {
                            targetField.setAccessible(true);
//                            Object existingValue = targetField.get(existing);

                            // Step 4: Only update if new value is non-null and of the same type
                            if (newValue != null) {
                                targetField.set(existing, newValue);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error accessing field: " + field.getName(), e);
                    }
                });
    }

    // Helper method to get field from target class (handles cases where target is a superclass)
    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return clazz.getSuperclass() != null ? getField(clazz.getSuperclass(), fieldName) : null;
        }
    }
}
