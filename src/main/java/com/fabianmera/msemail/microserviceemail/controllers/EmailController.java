package com.fabianmera.msemail.microserviceemail.controllers;

import com.fabianmera.msemail.microserviceemail.dtos.EmailDTO;
import com.fabianmera.msemail.microserviceemail.models.EmailModel;
import com.fabianmera.msemail.microserviceemail.services.EmailService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    //Logger para mostrar los mensajes en la consola.
    Logger logger = LogManager.getLogger(EmailService.class);

    //Metodo para enviar un email.
    @PostMapping("/emails")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {
        return new ResponseEntity<>(emailService.sendEmail(getEmailModel(emailDTO)), HttpStatus.CREATED);
    }

    //Metodo para obtener todos los emails.
    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmail(@PageableDefault(page = 0, size = 5, sort = "emailId") Pageable pageable) {
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/emails/{id}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value = "emailId") UUID emailId) {
        Optional<EmailModel> emailModelOptional = emailService.findById(emailId);
        if (!emailModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email no encontrado");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(emailModelOptional.get());
        }
    }


    //Metodo getEmailModel es un metodo privado que recibe un EmailDTO y retorna un EmailModel.
    private EmailModel getEmailModel(EmailDTO emailDTO) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);
        return emailModel;
    }
}
