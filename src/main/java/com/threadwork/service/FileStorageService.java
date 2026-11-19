package com.threadwork.service;
import com.threadwork.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
/** Saves uploaded product photos to the uploads folder. The file name is always generated, never taken from the user. */
@Service
public class FileStorageService {
  private static final Map<String, String> TYPES = Map.of("image/jpeg", "jpg", "image/png", "png", "image/webp", "webp");
  private final Path dir;
  public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) throws IOException {
    this.dir = Path.of(uploadDir).toAbsolutePath().normalize();
    Files.createDirectories(dir);
  }
  public Path dir() { return dir; }
  public String save(MultipartFile file) {
    String ext = TYPES.get(file.getContentType());
    if (file.isEmpty() || ext == null) throw new ApiException(HttpStatus.BAD_REQUEST, "Upload a JPG, PNG or WebP image");
    String name = UUID.randomUUID() + "." + ext;
    try (InputStream in = file.getInputStream()) { Files.copy(in, dir.resolve(name)); }
    catch (IOException e) { throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not save the image"); }
    return "/uploads/" + name;
  }
}
