package com.fabianmera.msemail.microserviceemail.repositories;

import com.fabianmera.msemail.microserviceemail.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
