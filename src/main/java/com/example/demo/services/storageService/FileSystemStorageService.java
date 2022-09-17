package com.example.demo.services.storageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileSystemStorageService implements StorageService {
  private final Path rootLocation;

  @Autowired
  public FileSystemStorageService(String rootLocationStr) {
    this.rootLocation = Paths.get(rootLocationStr);
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new Error("cannot init storage", e);
    }
  }

  @Override
  public void store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new Error("Failed to store empty file");
      }
      Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
    } catch (IOException e) {
      throw new Error("failed to store file", e);
    }
  }

  @Override
  public Stream<Path> getPathStream() {
    try {
      return Files.
        walk(this.rootLocation, 1).
        filter(path -> !path.equals(this.rootLocation)).
        map(path -> this.rootLocation.relativize(path));
    } catch (IOException e) {
      throw new Error("failed to read stored files", e);
    }
  }

  @Override
  public Path getPathByFileName(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource getResourceByFilename(String filename) {
  }

  @Override
  public void deleteAllFiles() {
    try {
      FileSystemUtils.deleteRecursively(this.rootLocation);
    } catch (IOException e) {
      throw new Error("failed to delete all files");
    }
  }
}
