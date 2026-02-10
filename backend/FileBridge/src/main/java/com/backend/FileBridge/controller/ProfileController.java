package com.backend.FileBridge.controller;

import com.backend.FileBridge.dto.ProfileDTO;
import com.backend.FileBridge.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<?>registerProfile(@RequestBody ProfileDTO profileDTO){
        ProfileDTO savedProfile = profileService.createProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @PostMapping("/update")
    public ResponseEntity<?>updateProfile(@RequestBody ProfileDTO profileDTO){
        ProfileDTO updatedProfile = profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedProfile);
    }
}
