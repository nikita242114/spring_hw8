package api;

import aspect.RecoverException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // получить книгу по id
    @RecoverException(noRecoverFor = {IllegalArgumentException.class})
    @Operation(summary = "get book by ID", description = "Поиск книги по ID книги")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    //получить список всех книг
    @Operation(summary = "get all book", description = "Поиск всех книг в системе")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // создание книги
    @Operation(summary = "create book", description = "Добавить книгу в общий список системы")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    //обновление книг

    @Operation(summary = "update book", description = "Изменение информации о книге")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBooks(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBooks(id, book));
    }

    // удаление книги
    @Operation(summary = "delete book", description = "Удаление книги из списка")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}