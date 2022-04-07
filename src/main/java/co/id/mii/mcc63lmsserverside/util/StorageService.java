package co.id.mii.mcc63lmsserverside.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import co.id.mii.mcc63lmsserverside.exception.StorageException;
import co.id.mii.mcc63lmsserverside.exception.StorageFileNotFoundException;


public class StorageService {

  public static void store(String uploadDir, String filename, MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path uploadPath = Paths.get(uploadDir);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      Path destinationFile = uploadPath.resolve(filename).normalize().toAbsolutePath();

      if (!destinationFile.getParent().equals(uploadPath.toAbsolutePath())) {
        // This is a security check
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

  public static Path load(String location, String filename) {
    Path locationPath = Paths.get(location);
    return locationPath.resolve(filename);
  }

  public static Resource loadAsResource(String location, String filename) {
    try {
      Path file = load(location, filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

}
