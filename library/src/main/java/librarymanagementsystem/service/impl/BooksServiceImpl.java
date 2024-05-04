package librarymanagementsystem.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import librarymanagementsystem.constant.EBooks;
import librarymanagementsystem.entity.Books;

import librarymanagementsystem.model.request.BookRequest;
import librarymanagementsystem.model.response.BooksResponse;
import librarymanagementsystem.repo.BooksRepo;
import librarymanagementsystem.service.BooksService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BooksServiceImpl implements BooksService{

    private final BooksRepo booksRepo;
    
    @Override
    public BooksResponse register(BookRequest bookRequest) {
        Books newBooks = Books.builder()
        .title(bookRequest.getTitle())
        .author(bookRequest.getAuthor())
        .status(EBooks.AVAILABLE)
        .build();

        Books savedBooks = booksRepo.saveAndFlush(newBooks);
        return BooksResponse.builder()
        .id(savedBooks.getId())
        .title(savedBooks.getTitle())
        .author(savedBooks.getAuthor())
        .status(EBooks.AVAILABLE)
        .build();
    }

    @Override
    public Books getBooksById(String id) {
         Optional<Books> optionalBooks = booksRepo.getBooksById(id);
        if (optionalBooks.isPresent()) return optionalBooks.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id : " + id + " Not Found");
    }

    @Override
    public Books updateBooks(Books books) {
        this.getBooksById(books.getId());
        return booksRepo.save(books);
    }

    @Override
    public void deleteBooksById(String id) {
        this.getBooksById(id);
        booksRepo.deleteBooks(id);
    }

    @Override
    public Page<Books> getAll(Integer page, Integer size) {
        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return booksRepo.getBooks(pageable);
    }

}