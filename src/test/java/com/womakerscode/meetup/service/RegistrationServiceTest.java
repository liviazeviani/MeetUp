package com.womakerscode.meetup.service;

import com.womakerscode.meetup.model.entity.Registration;
import com.womakerscode.meetup.repository.RegistrationRepository;
import com.womakerscode.meetup.service.impl.RegistrationServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class RegistrationServiceTest {

    RegistrationService registrationService;

    @MockBean
    RegistrationRepository registrationRepository;

    @BeforeEach
    public void setUp(){
        this.registrationService = new RegistrationServiceImpl(registrationRepository) {
        }

    }

    @Test
    @DisplayName("Should save a registration")
    public  void saveStudent(){

        //cenário
        Registration registration = createValidRegistration();

        //execução
        Mockito.when(registrationRepository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(registrationRepository.save(registration)).thenReturn(createValidRegistration());

        Registration savedRegistration = registrationService.save(registration);

    }


}
