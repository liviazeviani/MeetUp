package com.womakerscode.meetup.service;

import com.womakerscode.meetup.exception.BusinessException;
import com.womakerscode.meetup.model.entity.Registration;
import com.womakerscode.meetup.repository.RegistrationRepository;
import com.womakerscode.meetup.service.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class RegistrationServiceTest {

    RegistrationService registrationService;

    @MockBean
    RegistrationRepository registrationRepository;

    @BeforeEach
    public void setUp(){
        this.registrationService = new RegistrationServiceImpl(registrationRepository); {
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

        // assert
        assertThat(savedRegistration.getId()).isEqualTo(101);
        assertThat(savedRegistration.getName()).isEqualTo("Lívia Pavini Zeviani");
        assertThat(savedRegistration.getDateOfRegistration()).isEqualTo("05/04/2022");
        assertThat(savedRegistration.getRegistration()).isEqualTo("001");

    }

//    @Test
//    @DisplayName("Should get an Registration by Id")
//    public void getByRegistrationIdTest() {
//
//        // cenario
//        Integer id = 11;
//        Registration registration = createValidRegistration();
//        registration.setId(id);
//        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.of(registration));
//
//
//        // execucao
//        Optional<Registration> foundRegistration = registrationService.getRegistrationById(id);
//
//        assertThat(foundRegistration.isPresent()).isTrue();
//        assertThat(foundRegistration.get().getId()).isEqualTo(id);
//        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
//        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
//        assertThat(foundRegistration.get().getRegistration()).isEqualTo(registration.getRegistration());
//
//    }
//
//    @Test
//    @DisplayName("Should delete an registration")
//    public void deleteRegistrationTest() {
//
//        Registration registration = Registration.builder().id(11).build();
//
//        assertDoesNotThrow(() -> registrationService.delete(registration));
//
//        Mockito.verify(registrationRepository, Mockito.times(1)).delete(registration);
//    }

    private Registration createValidRegistration() {
        return Registration.builder()
                .id(101)
                .name("Lívia Pavini Zeviani")
                .dateOfRegistration("05/04/2022")
                .registration("001")
                .build();
    }

    @Test
    @DisplayName("Should throw business exception when" +
            "try to save new registration w/ registration duplicated")
    public void shouldNotSavedRegistrationDuplicated(){

        Registration registration = createValidRegistration();
        Mockito.when(registrationRepository.existsByRegistration(Mockito.any())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable( () -> registrationService.save(registration));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Registration was alredy created");

        Mockito.verify(registrationRepository, Mockito.never()).save(registration);
    }

}




