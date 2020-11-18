package se.djoh.libraryappbackend.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="publishers")
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy="publisher")
    private List<BookDescription> bookDescriptions = new ArrayList<>();
}
