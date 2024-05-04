package librarymanagementsystem.model.request;

import librarymanagementsystem.constant.EBooksStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBorrowBooksStatusRequest {
    private EBooksStatus status;
}
