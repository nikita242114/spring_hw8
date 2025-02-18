package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
@Schema(name = "Выдачи")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Идентификатор выдачи")
    private Long id;
    @Schema(name = "Идентификатор книги")
    private Long bookId;
    @Schema(name = "Идентификатор читателя")
    private Long readerId;

    /**
     * Дата выдачи
     */
    private  LocalDateTime issuedAt;
    /**
     * Дата возврата
     */

    private LocalDateTime timeReturn = null;

    public Issue() {
    }

    public Issue(Long bookId, Long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
//    this.issuedAt = issuedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getTimeReturn() {
        return timeReturn;
    }

}