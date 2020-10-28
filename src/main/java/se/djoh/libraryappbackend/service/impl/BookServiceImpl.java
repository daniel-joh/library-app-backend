package se.djoh.libraryappbackend.service.impl;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.djoh.libraryappbackend.domain.*;
import se.djoh.libraryappbackend.repository.BookRepository;
import se.djoh.libraryappbackend.service.BookService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookDescription> searchByCriteria(String titleString, String authorString, String isbnString, String genreString) {
        if (titleString.isEmpty() && authorString.isEmpty() && isbnString.isEmpty() && genreString.isEmpty()) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookDescription> cq = cb.createQuery(BookDescription.class);
        Root<BookDescription> book = cq.from(BookDescription.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(titleString)) {
            predicates.add(cb.like(cb.upper(book.get("title")), "%" + titleString.toUpperCase() + "%"));
        }

        if (StringUtils.isNotEmpty(isbnString)) {
            predicates.add(cb.equal(book.get("isbn"), isbnString));
        }

        Join<BookDescription, Genre> genre = book.join(BookDescription_.genre);
        if (StringUtils.isNotEmpty(genreString)) {
            predicates.add(cb.equal(cb.upper(genre.get("name")), genreString.toUpperCase()));
        }

        Join<BookDescription, Author> author = book.join(BookDescription_.author);
        if (StringUtils.isNotEmpty(authorString)) {
            Expression<String> exp = cb.concat(cb.upper(author.get("firstName")), " ");
            exp = cb.concat(exp, cb.upper(author.get("lastName")));
            predicates.add(cb.like(exp, "%" + authorString.toUpperCase() + "%"));
        }

        Predicate[] predArray = new Predicate[predicates.size()];
        cq.select(book).where(predicates.toArray(predArray));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Book getBookById(Long id) {
        Book ret = null;
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            ret = book.get();
        }
        return ret;
    }


}
