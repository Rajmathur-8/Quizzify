package com.raj.quiz_app_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(length = 500)
    private String bio;

    private String avatarUrl;

    private int quizzesAttempted = 0;

    @Enumerated(EnumType.STRING)
    private Role role = Role.PLAYER;

    private int xp = 0;
    private boolean enabled = false;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
