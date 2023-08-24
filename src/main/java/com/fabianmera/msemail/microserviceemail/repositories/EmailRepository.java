package com.fabianmera.msemail.microserviceemail.repositories;

import com.fabianmera.msemail.microserviceemail.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
}
