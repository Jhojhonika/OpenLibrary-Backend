package com.example.book_management.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotBlank(message = "Author is required.")
    @Size(max = 50, message = "Author must not exceed 50 characters.")
    private String author;

    @NotBlank(message = "Publication date is required.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Publication date must be in DD-MM-YYYY format.")
    private String publicationDate;

    @NotBlank(message = "ISBN is required.")
    @Pattern(regexp = "^\\d{13}$", message = "ISBN must be exactly 13 digits.")
    private String isbn;

    @NotBlank(message = "Genre is required.")
    private String genre;

    @NotNull(message = "Rating is required.")
    @Min(1)
    @Max(5)
    private Integer rating;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Title is required.") @Size(max = 100, message = "Title must not exceed 100 characters.") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required.") @Size(max = 100, message = "Title must not exceed 100 characters.") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Author is required.") @Size(max = 50, message = "Author must not exceed 50 characters.") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author is required.") @Size(max = 50, message = "Author must not exceed 50 characters.") String author) {
        this.author = author;
    }

    public @NotBlank(message = "Publication date is required.") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Publication date must be in YYYY-MM-DD format.") String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(@NotBlank(message = "Publication date is required.") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Publication date must be in YYYY-MM-DD format.") String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public @NotBlank(message = "ISBN is required.") @Pattern(regexp = "^\\d{13}$", message = "ISBN must be exactly 13 digits.") String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotBlank(message = "ISBN is required.") @Pattern(regexp = "^\\d{13}$", message = "ISBN must be exactly 13 digits.") String isbn) {
        this.isbn = isbn;
    }

    public @NotBlank(message = "Genre is required.") String getGenre() {
        return genre;
    }

    public void setGenre(@NotBlank(message = "Genre is required.") String genre) {
        this.genre = genre;
    }

    public @NotNull(message = "Rating is required.") @Min(1) @Max(5) Integer getRating() {
        return rating;
    }

    public void setRating(@NotNull(message = "Rating is required.") @Min(1) @Max(5) Integer rating) {
        this.rating = rating;
    }


    // Getters and setters
}

