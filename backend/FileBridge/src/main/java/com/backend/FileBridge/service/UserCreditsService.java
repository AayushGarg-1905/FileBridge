package com.backend.FileBridge.service;

import com.backend.FileBridge.document.UserCreditsDocument;
import com.backend.FileBridge.repository.UserCreditsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreditsService {

    private final UserCreditsRepository userCreditsRepository;
    private final ProfileService profileService;

    public UserCreditsDocument createInitialCredits(String clerkId) {
        UserCreditsDocument userCreditsDocument = UserCreditsDocument.builder().
                clerkId(clerkId)
                .credits(5)
                .plan("BASIC").build();

       return userCreditsRepository.save(userCreditsDocument);
    }

    public UserCreditsDocument getUserCredits(String clerkId){
        return userCreditsRepository.findByClerkId(clerkId);
    }
    public UserCreditsDocument getUserCredits(){
        String clerkId = profileService.getCurrentProfile().getClerkId();
        return getUserCredits(clerkId);
    }

    public Boolean hasEnoughCredits(int requiredCredits){
        UserCreditsDocument userCreditsDocument = getUserCredits();
        return userCreditsDocument.getCredits()>=requiredCredits;
    }

    public UserCreditsDocument consumeCredits(){
        UserCreditsDocument userCreditsDocument = getUserCredits();
        if(userCreditsDocument.getCredits()<=0){
            return null;
        }
        userCreditsDocument.setCredits(userCreditsDocument.getCredits()-1);
        return userCreditsRepository.save(userCreditsDocument);
    }

    public UserCreditsDocument addCredits(String clerkId, Integer creditsToAdd,String plan){
      UserCreditsDocument userCreditsDocument =  userCreditsRepository.findByClerkId(clerkId);
      if(userCreditsDocument==null){
          userCreditsDocument = createInitialCredits(clerkId);
      }
      userCreditsDocument.setCredits(userCreditsDocument.getCredits()+creditsToAdd);
      userCreditsDocument.setPlan(plan);
      return userCreditsRepository.save(userCreditsDocument);


    }
}
