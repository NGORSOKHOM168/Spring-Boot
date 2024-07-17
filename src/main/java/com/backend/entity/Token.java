package com.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="tokens")
public class Token {
    @Id
    @GeneratedValue
    public Long id;
    @Column(unique = true)
    private String token;

    @Column(name = "revoked")
    private boolean revoked;
    
    @Column(name = "expired")
    private boolean expired;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
