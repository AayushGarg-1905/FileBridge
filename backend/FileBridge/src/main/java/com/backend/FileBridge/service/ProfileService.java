package com.backend.FileBridge.service;

import com.backend.FileBridge.document.ProfileDocument;
import com.backend.FileBridge.dto.ProfileDTO;
import com.backend.FileBridge.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        ProfileDocument profile = convertDtoToDocument(profileDTO);
        profile = profileRepository.save(profile);

        return convertDocumentToDTO(profile);
    }

    public ProfileDTO updateProfile(ProfileDTO profileDTO){
        ProfileDocument profile = profileRepository.findByClerkId(profileDTO.getClerkId());
        if(profile!=null){
            if(profileDTO.getEmail()!=null && !profileDTO.getEmail().isEmpty()){
                profile.setEmail(profileDTO.getEmail());
            }
            if(profileDTO.getFirstName()!=null && !profileDTO.getFirstName().isEmpty()){
                profile.setFirstName(profileDTO.getFirstName());
            }
            if(profileDTO.getLastName()!=null && !profileDTO.getLastName().isEmpty()){
                profile.setLastName(profileDTO.getLastName());
            }
            if(profileDTO.getPhotoUrl()!=null && !profileDTO.getPhotoUrl().isEmpty()){
                profile.setPhotoUrl(profileDTO.getPhotoUrl());
            }
           profile =  profileRepository.save(profile);
            return convertDocumentToDTO(profile);
        }
        return null;
    }

    public void deleteProfile(String clerkId){
        ProfileDocument profile = profileRepository.findByClerkId(clerkId);
        if(profile!=null){
            profileRepository.delete(profile);
        }
    }

    public ProfileDocument getCurrentProfile(){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            throw new UsernameNotFoundException("User not authenticated");
        }
        String clerkId = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findByClerkId(clerkId);
    }

    private ProfileDocument convertDtoToDocument(ProfileDTO profileDTO){
        return ProfileDocument.builder()
                .clerkId(profileDTO.getClerkId())
                .email(profileDTO.getEmail())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .photoUrl(profileDTO.getPhotoUrl())
                .credits(profileDTO.getCredits())
                .createdAt(Instant.now())
                .build();
    }

    private ProfileDTO convertDocumentToDTO(ProfileDocument profileDocument){
        return ProfileDTO.builder()
                .id(profileDocument.getId())
                .clerkId(profileDocument.getClerkId())
                .email(profileDocument.getEmail())
                .firstName(profileDocument.getFirstName())
                .lastName(profileDocument.getLastName())
                .photoUrl(profileDocument.getPhotoUrl())
                .credits(profileDocument.getCredits())
                .createdAt(profileDocument.getCreatedAt())
                .build();
    }

}
