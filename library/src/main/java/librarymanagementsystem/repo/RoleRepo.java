package librarymanagementsystem.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagementsystem.constant.ERole;
import librarymanagementsystem.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String>{
    Optional<Role> findByRole(ERole role);
}
