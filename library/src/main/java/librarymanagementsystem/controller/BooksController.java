package librarymanagementsystem.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import librarymanagementsystem.entity.Books;
import librarymanagementsystem.model.request.BookRequest;
import librarymanagementsystem.model.response.BooksResponse;
import librarymanagementsystem.model.response.PagingResponse;
import librarymanagementsystem.model.response.WebResponse;
import librarymanagementsystem.service.BooksService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    private final BooksService booksService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody BookRequest bookRequest){
        BooksResponse booksResponse = booksService.register(bookRequest);
        WebResponse<BooksResponse> response = WebResponse.<BooksResponse>builder()
        .status(HttpStatus.CREATED.getReasonPhrase())
        .message("Success added new Books")
        .data(booksResponse)
        .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getBooksById(@PathVariable String id){
        Books findBooks = booksService.getBooksById(id);
        WebResponse<Books> response = WebResponse.<Books>builder()
        .status(HttpStatus.OK.getReasonPhrase())
        .message("Success get Books by id ")
        .data(findBooks)
        .build();
    return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteBooksById(@PathVariable String id){
        booksService.deleteBooksById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success delete Books by id ")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateBooks(@RequestBody Books books){
        Books updateBooks = booksService.updateBooks(books);
        WebResponse<Books> response = WebResponse.<Books>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success update Books by id ")
                .data(updateBooks)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size){
            
            Page<Books> bookList = booksService.getAll(page, size);

            PagingResponse pagingResponse = PagingResponse.builder()
            .page(page)
            .size(size)
            .totalPages(bookList.getTotalPages())
            .totalElement(bookList.getTotalElements())
            .build();

            WebResponse<List<Books>> response = WebResponse.<List<Books>>builder()
            .status(HttpStatus.OK.getReasonPhrase())
            .message("Success get list Book")
            .paging(pagingResponse)
            .data(bookList.getContent())
            .build();
            return ResponseEntity.ok(response);
    }

}
