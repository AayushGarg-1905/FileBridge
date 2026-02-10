package com.backend.FileBridge.service;

import com.backend.FileBridge.document.FileMetadataDocument;
import com.backend.FileBridge.document.ProfileDocument;
import com.backend.FileBridge.dto.FileMetadataDTO;
import com.backend.FileBridge.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final ProfileService profileService;
    private final UserCreditsService userCreditsService;
    private final FileMetadataRepository fileMetadataRepository;

    public List<FileMetadataDTO> uploadFiles(MultipartFile files[]) throws IOException {
        ProfileDocument profileDocument = profileService.getCurrentProfile();
        List<FileMetadataDocument> savedFiles = new ArrayList<>();

        if (!userCreditsService.hasEnoughCredits(files.length)) {
            throw new RuntimeException("Not enough credits to upload files");
        }
        Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileMetadataDocument fileMetadataDocument = FileMetadataDocument.builder().
                    fileLocation(targetLocation.toString())
                    .name(file.getOriginalFilename())
                    .size(file.getSize())
                    .type(file.getContentType())
                    .clerkId(profileDocument.getClerkId())
                    .isPublic(false)
                    .uploadedAt(LocalDateTime.now()).build();

            userCreditsService.consumeCredits();
            savedFiles.add(fileMetadataRepository.save(fileMetadataDocument));

        }

        return savedFiles.stream().map(fileMetadataDocument -> mapToDTO(fileMetadataDocument))
                .collect(Collectors.toList());
    }

    public List<FileMetadataDTO>getFiles(){
        ProfileDocument profileDocument = profileService.getCurrentProfile();
        List<FileMetadataDocument>list = fileMetadataRepository.findByClerkId(profileDocument.getClerkId());
        return list.stream().map(fileMetadataDocument -> mapToDTO(fileMetadataDocument))
                .collect(Collectors.toList());
    }

    public FileMetadataDTO getPublicFile(String id){
        Optional<FileMetadataDocument>fileOptional = fileMetadataRepository.findById(id);
        if(fileOptional.isEmpty() || !fileOptional.get().getIsPublic()){
            throw new RuntimeException("Unable to get the file");
        }
        FileMetadataDocument fileMetadataDocument =  fileOptional.get();
        return mapToDTO(fileMetadataDocument);
    }

    public FileMetadataDTO getDownloadableFile(String id){
       FileMetadataDocument fileMetadataDocument =  fileMetadataRepository.findById(id).orElseThrow(()->new RuntimeException("File not found"));
        return mapToDTO(fileMetadataDocument);
    }

    public void deleteFile(String id){
        try{
            ProfileDocument profileDocument = profileService.getCurrentProfile();
            FileMetadataDocument fileMetadataDocument =  fileMetadataRepository.findById(id).orElseThrow(()->new RuntimeException("File not found"));
            if(!fileMetadataDocument.getClerkId().equals(profileDocument.getClerkId())){
                throw new RuntimeException("File does not belong to current user");
            }
            Path filePath = Paths.get(fileMetadataDocument.getFileLocation());
            Files.deleteIfExists(filePath);
            fileMetadataRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Error deleting the file: "+e.getMessage());
        }
    }

    public FileMetadataDTO togglePublic(String id){
        FileMetadataDocument fileMetadataDocument = fileMetadataRepository.findById(id).orElseThrow(()->new RuntimeException("File not found"));
        fileMetadataDocument.setIsPublic(!fileMetadataDocument.getIsPublic());
        return mapToDTO(fileMetadataRepository.save(fileMetadataDocument));
    }

    private FileMetadataDTO mapToDTO(FileMetadataDocument fileMetadataDocument){
        return FileMetadataDTO.builder()
                .id(fileMetadataDocument.getId())
                .fileLocation(fileMetadataDocument.getFileLocation())
                .name(fileMetadataDocument.getName())
                .size(fileMetadataDocument.getSize())
                .type(fileMetadataDocument.getType())
                .clerkId(fileMetadataDocument.getClerkId())
                .isPublic(fileMetadataDocument.getIsPublic())
                .uploadedAt(fileMetadataDocument.getUploadedAt()).build();
    }
}
