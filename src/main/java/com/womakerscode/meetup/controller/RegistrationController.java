package com.womakerscode.meetup.controller;


import com.womakerscode.meetup.model.RegistrationDTO;
import com.womakerscode.meetup.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {

    private RegistrationService registrationService;
    private ModelMapper modelMapper;


}
