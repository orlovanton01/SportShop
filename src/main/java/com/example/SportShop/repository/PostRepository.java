package com.example.SportShop.repository;

import com.example.SportShop.model.Post;
import com.example.SportShop.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT w FROM Worker w " +
            "JOIN Post p ON w.id = p.worker.id " +
            "WHERE p.pId = :pId " +
            "ORDER BY FUNCTION('random')")
    Optional<Worker> findRandomWorkerByPostId(@Param("pId") int pId);
}
