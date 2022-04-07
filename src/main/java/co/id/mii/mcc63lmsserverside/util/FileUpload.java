package co.id.mii.mcc63lmsserverside.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;
import co.id.mii.mcc63lmsserverside.exception.FileUploadException;


public class FileUpload {

  public static void store(String uploadDir, String fileName, MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new FileUploadException("Failed to store empty file.");
      }

      Path uploadPath = Paths.get(uploadDir);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      Path destinationFile = uploadPath.resolve(fileName).normalize().toAbsolutePath();

      if (!destinationFile.getParent().equals(uploadPath.toAbsolutePath())) {
        // This is a security check
        throw new FileUploadException(
            "Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

    } catch (IOException e) {
      throw new FileUploadException("Failed to store file.", e);
    }
  }
}
