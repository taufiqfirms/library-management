package librarymanagementsystem.model.response;

import java.util.Date;

import librarymanagementsystem.constant.EBooksStatus;
import librarymanagementsystem.entity.Books;
import librarymanagementsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBooksResponse {
    private String id;
    private User user;
    private Books books;
    private Date borrowDate;
    private EBooksStatus status;
}
