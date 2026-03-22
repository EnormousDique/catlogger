package ru.muwa.catlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muwa.catlog.model.CatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CatLogRepository extends JpaRepository<CatLog,Long> {
    Page<CatLog> findAll(Pageable pageable);
    Page<CatLog> findAllByOrderByRecordedAtDesc(Pageable pageable);
}
