package com.example.mailService.email.controller;

import com.example.mailService.email.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email-metadata")
@PreAuthorize("hasRole('ROLE_USER')")
public class EmailMetadataController {

    // get-list

    // get

    // put

    // delete
}
