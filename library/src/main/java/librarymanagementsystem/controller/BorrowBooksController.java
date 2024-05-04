package librarymanagementsystem.controller;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import librarymanagementsystem.constant.EBooksStatus;
import librarymanagementsystem.entity.BorrowBooks;
import librarymanagementsystem.model.request.BorrowBooksRequest;
import librarymanagementsystem.model.request.UpdateBorrowBooksStatusRequest;
import librarymanagementsystem.model.response.BorrowBooksResponse;
import librarymanagementsystem.model.response.PagingResponse;
import librarymanagementsystem.model.response.WebResponse;
import librarymanagementsystem.service.BorrowBooksService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowBooksController {

    private final BorrowBooksService borrowBooksService;

    @PostMapping("/borrowed")
    public ResponseEntity<?> borrowBooks(@RequestBody BorrowBooksRequest borrowBooksRequest){
        BorrowBooksResponse borrowResponse = borrowBooksService.borrowBook(borrowBooksRequest);

        WebResponse<BorrowBooksResponse>  response = WebResponse.<BorrowBooksResponse>builder()
        .status(HttpStatus.CREATED.getReasonPhrase())
        .message("Success borrowing Books")
        .data(borrowResponse)
        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBorrowedBooks(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(required = false) String userId,
        @RequestParam(required = false) String booksId,
        @RequestParam(required = false) Date borrowDate,
        @RequestParam(required = false) EBooksStatus status
    ){
        Page<BorrowBooks> borrow = borrowBooksService.getAllBorrowedBook(page, size, userId, booksId, borrowDate, status);

        PagingResponse pagingResponse = PagingResponse.builder()
        .page(page)
        .size(size)
        .totalPages(borrow.getTotalPages())
        .totalElement(borrow.getTotalElements())
        .build();

        WebResponse<List<BorrowBooks>> response = WebResponse.<List<BorrowBooks>>builder()
        .status(HttpStatus.OK.getReasonPhrase())
        .message("Success get borrowed Books")
        .paging(pagingResponse)
        .data(borrow.getContent())
        .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/status/{bookingId}")
    public ResponseEntity<?> updateBorrowingStatus(
        @PathVariable("bookingId") String bookingId,
        @RequestBody UpdateBorrowBooksStatusRequest updateBorrowBooksStatusRequest
    ){

    BorrowBooksResponse borrow = borrowBooksService.updateBorrowedSstatus(bookingId, updateBorrowBooksStatusRequest);

    return ResponseEntity.ok(borrow);

    }
    
}
