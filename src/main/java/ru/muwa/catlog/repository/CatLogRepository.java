package ru.muwa.catlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muwa.catlog.model.CatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface CatLogRepository extends JpaRepository<CatLog,Long> {
    Page<CatLog> findAll(Pageable pageable);
    Page<CatLog> findAllByOrderByRecordedAtDesc(Pageable pageable);

    List<CatLog> findAllByRecordedAtAfterOrderByRecordedAtAsc(LocalDateTime recordedAt);
}
