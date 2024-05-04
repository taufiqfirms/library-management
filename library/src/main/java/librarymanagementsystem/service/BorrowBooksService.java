package librarymanagementsystem.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import librarymanagementsystem.constant.EBooksStatus;
import librarymanagementsystem.entity.BorrowBooks;
import librarymanagementsystem.model.request.BorrowBooksRequest;
import librarymanagementsystem.model.request.UpdateBorrowBooksStatusRequest;
import librarymanagementsystem.model.response.BorrowBooksResponse;

public interface BorrowBooksService {
    BorrowBooksResponse borrowBook(BorrowBooksRequest booksRequest);

    Page<BorrowBooks> getAllBorrowedBook(Integer page, Integer size, String userId, String booksId, Date borrowDate, EBooksStatus status);

    BorrowBooksResponse updateBorrowedSstatus(String bookingId, UpdateBorrowBooksStatusRequest updateBorrowBooksStatusRequest);
}
