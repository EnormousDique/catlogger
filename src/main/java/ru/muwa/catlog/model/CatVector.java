package ru.muwa.catlog.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "cat_record_vectors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CatVector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "cat_log_id", nullable = false)
    private Long catLogId;

    @Column(columnDefinition = "vector(768)", nullable = false)
    private float[] embedding;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name= "created_at")
    private LocalDateTime createdAt;

}
