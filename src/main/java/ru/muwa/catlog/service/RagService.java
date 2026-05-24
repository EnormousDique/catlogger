package ru.muwa.catlog.service;


import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.muwa.catlog.model.CatLog;
import ru.muwa.catlog.model.CatVector;
import ru.muwa.catlog.repository.CatLogRepository;
import ru.muwa.catlog.repository.VectorRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorRepository vectorRepository;
    private final ChatClient chatClient;
    private final CatLogRepository catLogRepository;
    private final VectorService vectorService;

    @Value("classpath:prompts/cat-rules.st")
    private Resource catPromptTemplate;

    @Transactional(readOnly = true)
    public String getAdvice() {

        System.out.println("Получаем историю за последние сутки");
        LocalDateTime last24hrs = LocalDateTime.now().minusHours(24);
        List<CatLog> recent = getRecentCatLogsList(last24hrs);

        String chronologicalHistory = recent.stream()
                .map(CatLog::toString)
                .collect(Collectors.joining());

        if (chronologicalHistory.isEmpty()) {
            chronologicalHistory = "За последние 24 часа записей нет.";
        }

        // TODO: вынести волшебные значения
        /*
        float[] queryEmbedding = vectorService.embed(chronologicalHistory);
        System.out.println("получены занные за 24 часа");
        List<CatVector> nearest = vectorRepository.findNearest(Arrays.toString(queryEmbedding),10);
        System.out.println("получены похожие данные из общей истории");
        String vectorContext = nearest.stream()
                .map(CatVector::getContent)
                .collect(Collectors.joining("\n---\n"));
/*
        if (vectorContext.isEmpty()) {
            vectorContext = "Похожих клинических случаев не найдено.";
        }

 */
        System.out.println("Сформирован контекст, отправляем запрос ИИ");
        String finalChronologicalHistory = chronologicalHistory;
        String finalVectorContext = "Временно недоступен."; //vectorContext;
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(catPromptTemplate)
                        .params(Map.of(
                                "chronological_history", finalChronologicalHistory,
                                "vector_context", finalVectorContext
                        ))
                )
                .call()
                .content();

    }

    private List<CatLog> getRecentCatLogsList(LocalDateTime beginDateTime){
        return catLogRepository.findAllByRecordedAtAfterOrderByRecordedAtAsc(beginDateTime);
    }

}
