package se.djoh.libraryappbackend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="genres")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
