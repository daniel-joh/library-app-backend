package se.djoh.libraryappbackend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="year_of_birth")
    private Integer yearOfBirth;

    @Column(name="year_of_death")
    private Integer yearOfDeath;

}
