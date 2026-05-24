package ru.muwa.catlog.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import ru.muwa.catlog.model.CatLog;
import ru.muwa.catlog.repository.CatLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatLogService {

    private final CatLogRepository repo;
    private final VectorService vector;

    @Transactional
    public CatLog save(CatLog log) {
        return repo.save(log);
    }

    public Optional<CatLog> findById(Long id) {
        return repo.findById(id);
    }

    public Page<CatLog> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
    public Page<CatLog> findAllByOrderByRecordedAtDesc(Pageable pageable) {
        return repo.findAllByOrderByRecordedAtDesc(pageable);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }


    public void migrate() {
        log.info("Начат процесс миграции");
        repo.findAll().forEach
                (vector::saveAsVector);
        log.info("Завершен процесс миграции");
    }
}
