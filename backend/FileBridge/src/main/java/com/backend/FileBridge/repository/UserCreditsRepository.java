package com.backend.FileBridge.repository;

import com.backend.FileBridge.document.UserCreditsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCreditsRepository extends MongoRepository<UserCreditsDocument,String> {
UserCreditsDocument findByClerkId(String clerkId);
}

