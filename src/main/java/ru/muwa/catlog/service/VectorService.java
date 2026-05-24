package ru.muwa.catlog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import ru.muwa.catlog.model.CatLog;
import ru.muwa.catlog.model.CatVector;
import ru.muwa.catlog.repository.VectorRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VectorService {

    private final VectorRepository repo;
    private final EmbeddingModel embeddingModel;

    public void saveAsVector(CatLog l) {

        // Человеко-читаемое представление записи
        var s = l.toString();
        log.info("Получаем вектор для записи:");
        log.info(s);
        repo.save
                 (CatVector.builder()
                .createdAt(LocalDateTime.now())
                .content(s)
                .embedding(embed(s))
                .catLogId(l.getId())
                .build());
        log.info("успешно");
    }

    public float[] embed(String s){
        return embeddingModel.embed(s);
    }



}
