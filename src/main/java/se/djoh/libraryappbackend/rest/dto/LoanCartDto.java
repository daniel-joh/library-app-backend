package se.djoh.libraryappbackend.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class LoanCartDto {
    @NotNull(message = "User id cannot be null")
    private Long userId;
    @NotEmpty(message = "List of book ids cannot be empty")
    private List<Long> bookIds;

}
