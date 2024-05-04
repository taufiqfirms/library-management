package librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagementsystem.entity.Books;

public interface BooksRepo extends JpaRepository<Books, String>{

}
