package com.gym.app.mahesh_gym.utils;

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
                        Object existingValue = field.get(existing);  // Step 4: Get existing value

                        // Step 5: If new value is null, set it to the existing value
                        if (newValue == null && existingValue != null) {
                            field.set(source, existingValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error accessing field: " + field.getName(), e);
                    }
                });
    }

}
