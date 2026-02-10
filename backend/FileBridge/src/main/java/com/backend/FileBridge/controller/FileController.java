package com.backend.FileBridge.controller;

import com.backend.FileBridge.document.UserCreditsDocument;
import com.backend.FileBridge.dto.FileMetadataDTO;
import com.backend.FileBridge.service.FileMetadataService;
import com.backend.FileBridge.service.UserCreditsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileMetadataService fileMetadataService;
    private final UserCreditsService userCreditsService;

    @PostMapping("/upload")
    public ResponseEntity<?>uploadFiles(@RequestPart("files")MultipartFile files[]) throws IOException {
        List<FileMetadataDTO> list = fileMetadataService.uploadFiles(files);
        Map<String,Object> response = new HashMap<>();

        UserCreditsDocument userCreditsDocument = userCreditsService.getUserCredits();
        response.put("files",list);
        response.put("remainingCredits",userCreditsDocument.getCredits());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<?>getFilesForCurrentUser(){
        List<FileMetadataDTO> files = fileMetadataService.getFiles();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?>getPublicFile(@PathVariable("id")String id){
        FileMetadataDTO fileMetadataDTO = fileMetadataService.getPublicFile(id);
        return ResponseEntity.ok(fileMetadataDTO);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource>downloadFile(@PathVariable("id")String id) throws IOException{
        FileMetadataDTO fileMetadataDTO = fileMetadataService.getPublicFile(id);
        Path path = Paths.get(fileMetadataDTO.getFileLocation());
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attacjment; filename=\""+fileMetadataDTO.getName()+"\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteFile(@PathVariable("id")String id) {
        fileMetadataService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-public")
    public ResponseEntity<?>togglePublic(@PathVariable("id")String id) {
        FileMetadataDTO fileMetadataDTO = fileMetadataService.togglePublic(id);
        return ResponseEntity.ok(fileMetadataDTO);
    }
}
