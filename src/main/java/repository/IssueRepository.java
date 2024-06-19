package repository;

import model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

    //получить список выдач по id читателя
    @Query("SELECT i FROM Issue i WHERE i.readerId = :id")
    List<Issue> getAllIssueByReaderId(Long id);


}
