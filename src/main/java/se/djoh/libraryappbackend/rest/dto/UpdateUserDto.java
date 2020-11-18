package se.djoh.libraryappbackend.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private Long id;

    @NotBlank(message = "User name is mandatory")
    private String username;

    private String password;

    @Email(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Ssn is mandatory")
    private String ssn;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Street address is mandatory")
    private String streetAddress;

    @NotBlank(message = "City is mandatory")
    private String city;

    @Digits(integer = 5, fraction = 0, message = "Zip code must be 5 digits only")
    @NotNull(message = "Zip code is mandatory")
    private Integer zipCode;

    @NotBlank(message = "Country is mandatory")
    private String country;

    private String userRole;

}
