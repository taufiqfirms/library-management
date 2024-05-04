package librarymanagementsystem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import librarymanagementsystem.constant.EBooks;
import librarymanagementsystem.constant.EBooksStatus;
import librarymanagementsystem.entity.Books;
import librarymanagementsystem.entity.BorrowBooks;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.model.request.BorrowBooksRequest;
import librarymanagementsystem.model.request.UpdateBorrowBooksStatusRequest;
import librarymanagementsystem.model.response.BorrowBooksResponse;
import librarymanagementsystem.repo.BorrowBooksRepo;
import librarymanagementsystem.repo.UserRepo;
import librarymanagementsystem.service.BooksService;
import librarymanagementsystem.service.BorrowBooksService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BorrowBooksServiceImpl implements BorrowBooksService{

    private final BooksService booksService;
    private final UserRepo userRepo;
    private final BorrowBooksRepo borrowBooksRepo;
    
    @Override
    public BorrowBooksResponse borrowBook(BorrowBooksRequest booksRequest) {
        
        Books findBooks = booksService.getBooksById(booksRequest.getBooksId());

        if(findBooks == null){
            throw new RuntimeException("Books not found with id: " + booksRequest.getBooksId());
        }
        if(findBooks.getStatus() == EBooks.BOOKED){
            throw new RuntimeException("Books already booked");
        }

        findBooks.setStatus(EBooks.BOOKED);
        booksService.updateBooks(findBooks);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User findUser = userRepo.getUserByUserCredential_Username(username);

        BorrowBooks newBorrowBooks = BorrowBooks.builder()
        .user(findUser)
        .books(findBooks)
        .borrowDate(booksRequest.getBorrowDate())
        .status(EBooksStatus.PENDING)
        .build();

        BorrowBooks savedBorrowBooks = borrowBooksRepo.saveAndFlush(newBorrowBooks);
        return BorrowBooksResponse.builder()
        .id(savedBorrowBooks.getId())
        .user(savedBorrowBooks.getUser())
        .books(savedBorrowBooks.getBooks())
        .borrowDate(savedBorrowBooks.getBorrowDate())
        .status(savedBorrowBooks.getStatus())
        .build();
    }

    @Override
    public Page<BorrowBooks> getAllBorrowedBook(Integer page, Integer size, String userId, String booksId,
            Date borrowDate, EBooksStatus status) {
                if (page == null || page <= 0){
                    page = 1;
                }
        
                if (size == null || size <= 0) {
                    size = 10;
                }

                Pageable pageable = PageRequest.of(page-1, size);
                Specification<BorrowBooks> spec = (root, query, builder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if(userId != null){
                        predicates.add(builder.equal(root.get("user").get("id"), userId));
                    }
                    if(booksId != null){
                        predicates.add(builder.equal(root.get("books").get("id"), booksId));
                    }
                    if(borrowDate != null){
                        predicates.add(builder.equal(root.get("borrowDate"), borrowDate));
                    }if(status != null){
                        predicates.add(builder.equal(root.get("status"), status));
                    }

                    return builder.and(predicates.toArray(new Predicate[0]));
                };

            return borrowBooksRepo.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public BorrowBooksResponse updateBorrowedSstatus(String bookingId, UpdateBorrowBooksStatusRequest updateBorrowBooksStatusRequest) {
        BorrowBooks borrowBooks = borrowBooksRepo.findById(bookingId).orElseThrow(() ->
        new RuntimeException("Borrowed Books not found with id: " + bookingId));

        borrowBooks.setStatus(updateBorrowBooksStatusRequest.getStatus());
        if(updateBorrowBooksStatusRequest.getStatus() == EBooksStatus.ACCEPTED){
            borrowBooks.setStatus(EBooksStatus.ACCEPTED);
        } else if(updateBorrowBooksStatusRequest.getStatus() == EBooksStatus.REJECTED){
            borrowBooks.setStatus(EBooksStatus.REJECTED);

            Books books = borrowBooks.getBooks();
            if (books != null){
                books.setStatus(EBooks.AVAILABLE);
                booksService.updateBooks(books);
            }
        }

        borrowBooksRepo.saveAndFlush(borrowBooks);

        return BorrowBooksResponse.builder()
        .id(borrowBooks.getId())
        .user(borrowBooks.getUser())
        .books(borrowBooks.getBooks())
        .borrowDate(borrowBooks.getBorrowDate())
        .status(borrowBooks.getStatus())
        .build();
    }

}
