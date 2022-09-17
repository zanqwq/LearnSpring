package com.example.demo.services.storageService;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  void init();
  void store(MultipartFile file);
  Stream<Path> getPathStream();
  Path getPathByFileName(String filename);
  Resource getResourceByFilename(String filename);
  void deleteAllFiles();
}
