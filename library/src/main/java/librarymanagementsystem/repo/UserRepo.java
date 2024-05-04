package librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagementsystem.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

}
