package project.practice.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.document.models.Document;

import java.util.List;

@Repository
public interface DocRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByUserId(Long userId);

    List<Document> findAllByUserIdAndType(Long userId, String type);
    List<Document> findAllByType(String type);

    Boolean existsByTitle(String title);
}
