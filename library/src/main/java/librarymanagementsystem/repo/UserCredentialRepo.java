package librarymanagementsystem.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagementsystem.entity.UserCredential;

public interface UserCredentialRepo extends JpaRepository<UserCredential, String>{

    Optional<UserCredential> findByUsername(String username);
}
