package com.backend.FileBridge.controller;

import com.backend.FileBridge.dto.ProfileDTO;
import com.backend.FileBridge.service.ProfileService;
import com.backend.FileBridge.service.UserCreditsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class ClerkWebhookController {

    @Value("${clerk.webhook.secret}")
    private String webhookSecret;

    private  final ProfileService profileService;
    private final UserCreditsService userCreditsService;

    @PostMapping("/clerk")
    public ResponseEntity<?>handleClerkWebHook(@RequestHeader("svix-id")String svixId,
                                               @RequestHeader("svix-timestamp")String svixTimestamp,
                                               @RequestHeader("svix-signature")String svixSignature,
                                               @RequestBody String payload){
        try{
            boolean isValid = verifyWebhookSignature(svixId,svixTimestamp,svixTimestamp);
            if(!isValid){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid webhook signature");
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(payload);
            String eventType = rootNode.path("type").asText();

            switch (eventType){
                case "user.created":
                    handleUserCreated(rootNode.path("data"));
                    break;
                case "user.updated":
                    handleUserUpdated(rootNode.path("data"));
                    break;
                case "user.deleted":
                    handleUserDeleted(rootNode.path("data"));
                    break;
            }
            return ResponseEntity.ok().build();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }

    private boolean verifyWebhookSignature(String svixId,String svixTimestamp, String svixSignature){
        return true;
    }

    private void handleUserCreated(JsonNode data){
        String clerkId = data.path("id").asText();
        String firstName = data.path("first_name").asText();
        String lastName = data.path("last_name").asText();
        String photoUrl = data.path("image_url").asText();

        String email = "";
        JsonNode emailAddressesNode = data.path("email_addresses");
        if(emailAddressesNode.isArray() && emailAddressesNode.size()>0){
            email = emailAddressesNode.get(0).path("email_address").asText();
        }

        ProfileDTO profileDTO = ProfileDTO.builder().clerkId(clerkId)
                .firstName(firstName)
                .lastName(lastName)
                .photoUrl(photoUrl)
                .email(email)
                .build();

        profileService.createProfile(profileDTO);
        userCreditsService.createInitialCredits(clerkId);
    }
    private void handleUserUpdated(JsonNode data){
        String clerkId = data.path("id").asText();
        String firstName = data.path("first_name").asText();
        String lastName = data.path("last_name").asText();
        String photoUrl = data.path("image_url").asText();

        String email = "";
        JsonNode emailAddressesNode = data.path("email_addresses");
        if(emailAddressesNode.isArray() && emailAddressesNode.size()>0){
            email = emailAddressesNode.get(0).path("email_address").asText();
        }

        ProfileDTO profileDTO = ProfileDTO.builder().clerkId(clerkId)
                .firstName(firstName)
                .lastName(lastName)
                .photoUrl(photoUrl)
                .email(email)
                .build();

        profileService.updateProfile(profileDTO);
    }

    private void handleUserDeleted(JsonNode data){
        String clerkId = data.path("id").asText();
        profileService.deleteProfile(clerkId);
    }
}
