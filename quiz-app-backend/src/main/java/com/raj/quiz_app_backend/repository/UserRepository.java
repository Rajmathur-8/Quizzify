package com.raj.quiz_app_backend.repository;

import com.raj.quiz_app_backend.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    long countByEnabledTrue();

    // 🧠 Registration stats: users registered per month (for dashboard chart)
    @Query("SELECT FUNCTION('MONTH', u.createdAt) AS month, COUNT(u) AS count " +
            "FROM User u GROUP BY FUNCTION('MONTH', u.createdAt) ORDER BY month")
    List<Object[]> getUserRegistrationStats();

    // Optional: registrations by date range (for graphs)
    @Query("SELECT FUNCTION('DATE', u.createdAt), COUNT(u) FROM User u " +
            "WHERE u.createdAt BETWEEN :start AND :end GROUP BY FUNCTION('DATE', u.createdAt)")
    List<Object[]> getUserRegistrationStatsBetween(@Param("start") Date start, @Param("end") Date end);
}
