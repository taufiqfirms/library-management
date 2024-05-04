package librarymanagementsystem.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import librarymanagementsystem.entity.Books;

public interface BooksRepo extends JpaRepository<Books, String>{

    @Query(value = "select * from books", nativeQuery = true)
    Page<Books> getBooks(Pageable pageable);

    @Query(value = "select * from books where id =?1", nativeQuery = true)
    Optional<Books> getBooksById(@Param("id") String id);

    @Modifying
    @Query(value = "delete from books where id =?1", nativeQuery = true)
    void deleteBooks(String id);

}
