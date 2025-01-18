package com.example.book_management.repository;
import com.example.book_management.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenreContainingIgnoreCase(String genre);

    List<Book> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(
            String author, String title, String genre);



Page<Book> findAll(Pageable pageable);


}

