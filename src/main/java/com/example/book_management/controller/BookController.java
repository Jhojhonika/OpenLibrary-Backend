

package com.example.book_management.controller;


import com.example.book_management.model.Book;
import com.example.book_management.repository.BookRepository;
import com.example.book_management.service.BookService;
import com.example.book_management.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://openlibraryofbooks.netlify.app")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;


    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;




    // Get all books or filter by id, author, title, and genre
    @GetMapping("/api/books")
    public ResponseEntity<Object> getBooksByFilters(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {
        System.out.println("Received ID: " + id); // Debugging
        try {
            List<Book> books = bookService.getBooksByFilters(id, author, title, genre);
            if (books.isEmpty()) {
                return ResponseEntity.status(404).body("No books found matching the criteria.");
            }
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching books: " + e.getMessage());
        }
    }



    // Add a new book
    @PostMapping("/api/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            // Generate a unique ID for the book if not already present
            if (book.getId() == null || book.getId().isEmpty()) {
                book.setId(sequenceGeneratorService.generateSequence("book_sequence"));
            }

            // Call the service to add the book to the repository
            bookService.addBook(book);

            // Return success response
            return ResponseEntity.ok("Book added successfully!");
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(500).body("Error saving book: " + e.getMessage());
        }
    }
    @GetMapping("/images/{genre}")
    public ResponseEntity<List<String>> getImagesByGenre(@PathVariable String genre) {
        try {
            // Define the path to the genre folder
            Path genrePath = Paths.get("src/main/resources/static/uploads/images/").resolve(genre).normalize();
            System.out.println("Checking path: " + genrePath.toString());
            List<String> imageUrls = new ArrayList<>();

            // Check if the genre directory exists
            if (Files.exists(genrePath) && Files.isDirectory(genrePath)) {
                // List all images in the genre folder
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(genrePath)) {
                    for (Path entry : stream) {
                        // Add image URL to the list
                        imageUrls.add("/api/images/" + genre + "/" + entry.getFileName().toString());
                    }
                }

                return ResponseEntity.ok(imageUrls);
            } else {
                return ResponseEntity.status(404).body(null); // No images found for the genre
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Serve image by genre and filename
    @CrossOrigin(origins = "http://openlibraryofbooks.netlify.app")
    @GetMapping("/api/books/images/{genre}/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String genre, @PathVariable String filename) {
        try {
            // Define the path to the image
            Path imagePath = Paths.get("src/main/resources/static/uploads/images/")
                    .resolve(genre)
                    .resolve(filename)
                    .normalize();

            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Determine the content type
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // Fallback type
                }

                // Return the image with appropriate headers
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            }  else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}




