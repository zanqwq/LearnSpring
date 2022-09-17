package com.example.demo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.services.storageService.StorageService;

@Controller
public class FileUploadController {
  private final StorageService storageService;

  @Autowired
  public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping("/files")
  public String[] getFileList() throws IOException {
    List<String> filenameList = storageService.getPathStream().map(path -> path.getFileName().toString()).collect(Collectors.toList());
    String[] filenames = filenameList.toArray(new String[filenameList.size()]);
    return filenames;
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.getResourceByFilename(filename);
    return ResponseEntity.
      ok().
      header(
        HttpHeaders.CONTENT_DISPOSITION,
        String.format(
          "attachment; filename=\"%s\"",
          file.getFilename()
        )
      ).
      body(file);
  }

  @PostMapping("/files")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) {
    storageService.store(file);
    return "redirect:/filed";
  }

  // public ResponseEntity<?> handleStorageFileNotFound()
}
