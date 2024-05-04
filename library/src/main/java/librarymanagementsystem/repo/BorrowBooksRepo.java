package librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import librarymanagementsystem.entity.BorrowBooks;

public interface BorrowBooksRepo extends JpaRepository<BorrowBooks, String>, JpaSpecificationExecutor<BorrowBooks> {

}
