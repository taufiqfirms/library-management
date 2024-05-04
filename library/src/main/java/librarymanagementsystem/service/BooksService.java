package librarymanagementsystem.service;

import org.springframework.data.domain.Page;

import librarymanagementsystem.entity.Books;
import librarymanagementsystem.model.request.BookRequest;
import librarymanagementsystem.model.response.BooksResponse;

public interface BooksService {

    BooksResponse register(BookRequest bookRequest);
    Page<Books> getAllBooks(Integer page, Integer size);
    Books getBooksById(String id);
    Books updateBooks(Books books);
    void deleteBooksById(String id);

}
