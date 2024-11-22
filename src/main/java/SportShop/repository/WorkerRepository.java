package SportShop.repository;

import SportShop.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Integer> {

    @Query(value = "SELECT w.w_surname AS surname, w.w_name AS name, w.w_patronymic AS patronymic " +
            "FROM workers w " +
            "JOIN posts p ON w.w_id = p.w_id " +
            "WHERE p.salary BETWEEN :minSalary AND :maxSalary", nativeQuery = true)
    List<Map<String, Object>> findWorkersBySalaryRange(@Param("minSalary") int minSalary,
                                                       @Param("maxSalary") int maxSalary);
}
