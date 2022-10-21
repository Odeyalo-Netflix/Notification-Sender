package com.odeyalo.netflix.emailsenderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sent_emails")
public class SentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "recipient", nullable = false, updatable = false, length = 100)
    private String to;
    @Column(nullable = false, updatable = false, length = 5000)
    private String body;
    @Column(nullable = false, updatable = false)
    private String subject;
    @Column(nullable = false, updatable = false)
    private boolean success;
    @Column(updatable = false)
    private String exceptionReason;
}
