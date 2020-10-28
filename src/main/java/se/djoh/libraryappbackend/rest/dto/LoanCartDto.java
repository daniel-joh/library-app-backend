package se.djoh.libraryappbackend.rest.dto;

import lombok.Data;
import java.util.List;

@Data
public class LoanCartDto {
    private Long userId;
    private List<Long> bookIds;

}
