package com.odeyalo.netflix.emailsenderservice.repository;

import com.odeyalo.netflix.emailsenderservice.entity.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {

}
