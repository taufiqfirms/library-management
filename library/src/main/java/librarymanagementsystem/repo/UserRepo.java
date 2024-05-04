package librarymanagementsystem.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import librarymanagementsystem.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

    @Query(value = "select * from user", nativeQuery = true)
    Page<User> getUser(Pageable pageable);

    @Query(value = "select * from user where id =?1", nativeQuery = true)
    Optional<User> getUserById(@Param("id") String id);

    @Modifying
    @Query(value = "delete from user where id =?1", nativeQuery = true)
    void deleteUser(String id);

    User getUserByUserCredential_Username(String username);
}
