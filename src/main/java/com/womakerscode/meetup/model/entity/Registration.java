package com.womakerscode.meetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Registration {

    //Dados da pessoa que vai se registrar na aplicação

    @Id //persistir o id, elemento único da tabela
    @Column(name = "registration_id") //o nome da tabela, como ela vai estar no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id gera um valor por identidade
    private long id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "date_of_registration")
    private LocalDate dateRegistration;

    @Column
    private String registration;
}
