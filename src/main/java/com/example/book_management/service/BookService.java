package com.example.book_management.service;

import com.example.book_management.model.Book;
import com.example.book_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    // Add a new book to the repository with a unique ID
    public Book addBook(Book book) {
        // Generate a unique ID for the book if not already present
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(sequenceGeneratorService.generateSequence("book_sequence"));
        }

        // Save the book in the repository
        return bookRepository.save(book);
    }

    public List<Book> getBooksByFilters(String id, String author, String title, String genre) {
        if (id != null && !id.isEmpty()) {
            return bookRepository.findById(id).map(List::of).orElse(List.of());
        } else if (author != null && !author.isEmpty() &&
                title != null && !title.isEmpty() &&
                genre != null && !genre.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(author, title, genre);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (genre != null && !genre.isEmpty()) {
            return bookRepository.findByGenreContainingIgnoreCase(genre);
        } else {
            return bookRepository.findAll();
        }
    }





    public void deleteBookById(String id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book with ID " + id + " does not exist.");
        }
        bookRepository.deleteById(id);
    }



}

