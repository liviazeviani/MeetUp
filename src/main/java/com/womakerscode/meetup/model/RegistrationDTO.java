package com.womakerscode.meetup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegistrationDTO {

    //Regras para os atributos

    private long id; // como o id vai ser gerado eu não preciso da anotação NotEmpty

    @NotEmpty
    private String name;

    @NotEmpty
    private LocalDate dateRegistration;

    @NotEmpty
    private String registration;
}
