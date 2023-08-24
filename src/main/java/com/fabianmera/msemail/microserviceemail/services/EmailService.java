package com.fabianmera.msemail.microserviceemail.services;

import com.fabianmera.msemail.microserviceemail.enums.StatusEmail;
import com.fabianmera.msemail.microserviceemail.models.EmailModel;
import com.fabianmera.msemail.microserviceemail.repositories.EmailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    Logger logger = LogManager.getLogger(EmailService.class);
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.info("Email enviado correctamente a :{}", emailModel.getEmailTo());
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            logger.error("Error al enviar el email a :{} Error:{}", emailModel.getEmailTo(), e.getMessage());
        } finally {
            return emailRepository.save(emailModel);
        }
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}
