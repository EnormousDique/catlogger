package ru.muwa.catlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.muwa.catlog.model.CatVector;

import java.util.List;

public interface VectorRepository extends JpaRepository<CatVector,Long>{

    boolean existsByCatLogId(long catLogId);

    boolean deleteByCatLogId(long catLogId);

    /*
    @Query(value = """
              SELECT * FROM cat_record_vectors\s
              ORDER BY embedding <=> cast(:embedding as vector)
              LIMIT :limit
            """, nativeQuery = true)
    List<CatVector> findNearest(float[] embedding, int limit);

     */

    /*
    @Query(value = """
    SELECT * FROM cat_record_vectors
    ORDER BY embedding <=> CAST(:embedding AS vector)
    LIMIT :limit
    """, nativeQuery = true)
    List<CatVector> findNearest(@Param("embedding") String embedding, int limit);

     */

    /*
    @Query(value = """
    SELECT * FROM cat_record_vectors
    ORDER BY embedding <=> array_to_vector(cast(:embedding as float[]))
    LIMIT :limit
    """, nativeQuery = true)
    List<CatVector> findNearest(@Param("embedding") float[] embedding, int limit);


     */

    /*
    @Query(value = """
    SELECT * FROM cat_record_vectors
    ORDER BY embedding <=> array_to_vector(cast(:embedding as real[]))
    LIMIT :limit
    """, nativeQuery = true)
    List<CatVector> findNearest(@Param("embedding") float[] embedding, @Param("limit") int limit);


     */
    /*
    @Query(value = """
    SELECT * FROM cat_record_vectors
    ORDER BY embedding <=> cast(:embedding as vector)
    LIMIT :limit
    """, nativeQuery = true)
    List<CatVector> findNearest(@Param("embedding") String embedding, @Param("limit") int limit);


     */
    @Query(value = """
    SELECT id, cat_log_id, content, created_at, 
           embedding::real[] AS embedding 
    FROM cat_record_vectors
    ORDER BY embedding <=> cast(:embedding as vector)
    LIMIT :limit
    """, nativeQuery = true)
    List<CatVector> findNearest(@Param("embedding") String embedding, @Param("limit") int limit);
}

