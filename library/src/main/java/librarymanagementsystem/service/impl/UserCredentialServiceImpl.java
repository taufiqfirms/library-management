package librarymanagementsystem.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import librarymanagementsystem.entity.UserCredential;
import librarymanagementsystem.repo.UserCredentialRepo;
import librarymanagementsystem.service.UserCredentialService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepo userCredentialRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialRepo.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Load by username failed"));
    }

    @Override
    public UserCredential loadByUserId(String userId) {
        return userCredentialRepo.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Load by user id failed"));
    }

}
