package librarymanagementsystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import librarymanagementsystem.entity.UserCredential;

public interface UserCredentialService extends UserDetailsService{
    UserCredential loadByUserId(String userId);
}
