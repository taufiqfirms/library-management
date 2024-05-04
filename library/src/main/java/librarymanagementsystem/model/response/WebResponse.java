package librarymanagementsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private String status;
    private String message;
    private PagingResponse paging;
    private T data;
}
