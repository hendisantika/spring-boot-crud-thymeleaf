package id.my.hendisantika.thymeleaf.repository;

import id.my.hendisantika.thymeleaf.entity.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-crud-thymeleaf
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/2/24
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {
    Page<Tutorial> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("UPDATE Tutorial t SET t.published = :published WHERE t.id = :id")
    @Modifying
    void updatePublishedStatus(Integer id, boolean published);
}
