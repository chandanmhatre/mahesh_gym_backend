package com.gym.app.mahesh_gym.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileStorageUtil {

    /**
     * Decodes a Base64 string and stores it as a file in the given directory.
     *
     * @param base64String The Base64 encoded file content
     * @throws IOException If any I/O error occurs
     */
    public static void saveBase64ToFile(String customerId, String base64String, String fileName) throws IOException {
        byte[] fileBytes;
        // 1️⃣ Remove Base64 header if present (MIME type prefix)
        StringBuilder base64StringBuilder = new StringBuilder(base64String.replaceAll("^data:[^;]+;base64,", "").trim());

        // 2️⃣ Ensure Base64 string has valid padding
        while (base64StringBuilder.length() % 4 != 0) {
            base64StringBuilder.append("=");
        }
        base64String = base64StringBuilder.toString();

        try {
            // convert to byte
            fileBytes = Base64.getDecoder().decode(base64String);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid Base64 input: " + e.getMessage());
        }
        String folderPath = "D:/springboot/s3/customer/" + customerId;
        String filePath = folderPath + "/" + fileName;
        Path folder = Paths.get(folderPath);

        try {
            // Create the folder
            Files.createDirectories(folder);
            System.out.println("Folder created: " + folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Write byte array to file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
        }

        System.out.println("File saved successfully at: " + filePath);
    }
}

