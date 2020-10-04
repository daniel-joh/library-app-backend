package se.djoh.libraryappbackend.domain;

import lombok.Data;
import javax.persistence.*;

@Embeddable
@Data
public class Address {
    @Column(name="street_address")
    private String streetAddress;

    private String city;

    @Column(name="zip_code")
    private Integer zipCode;

    private String country;
}
