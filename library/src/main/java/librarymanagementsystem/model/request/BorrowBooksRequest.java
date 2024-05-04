package librarymanagementsystem.model.request;

import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import librarymanagementsystem.constant.EBooksStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBooksRequest {

    @NotEmpty
    private String booksId;

    @NotNull
    @Future
    private Date borrowDate;

    @NotNull
    private EBooksStatus status;
}
