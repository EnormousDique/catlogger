package ru.muwa.catlog.service;

import jakarta.transaction.Transactional;
import ru.muwa.catlog.model.CatLog;
import ru.muwa.catlog.repository.CatLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatLogService {

    private final CatLogRepository repo;

    public CatLogService(CatLogRepository repo) {
        this.repo = repo;
    }

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
}
