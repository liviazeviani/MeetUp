package com.womakerscode.meetup.service;

import com.womakerscode.meetup.exception.BusinessException;
import com.womakerscode.meetup.model.entity.Registration;
import com.womakerscode.meetup.repository.RegistrationRepository;
import com.womakerscode.meetup.service.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
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

    @Test
    @DisplayName("Should get an Registration by Id")
    public void getByRegistrationIdTest() {

        // cenario
        Integer id = 11;
        Registration registration = createValidRegistration();
        registration.setId(id);
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.of(registration));


        // execucao
        Optional<Registration> foundRegistration = registrationService.getRegistrationById(id);

        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
        assertThat(foundRegistration.get().getRegistration()).isEqualTo(registration.getRegistration());

    }

    @Test
    @DisplayName("Should delete an registration")
    public void deleteRegistrationTest() {

        Registration registration = Registration.builder().id(11).build();

        assertDoesNotThrow(() -> registrationService.delete(registration));

        Mockito.verify(registrationRepository, Mockito.times(1)).delete(registration);
    }

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
                .hasMessage("Registration already created");

        Mockito.verify(registrationRepository, Mockito.never()).save(registration);
    }

    @Test
    @DisplayName("Should get and Registration by ID")
    public void getByRegistrationTest(){
        //cenário
        Integer id = 11;
        Registration registration = createValidRegistration();
        registration.setId(id);
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.of(registration));

        //execução
        Optional<Registration> foundRegistration = registrationService.getRegistrationById(id);

        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());

    }

    @Test
    @DisplayName("Should update an registration")
    public void updateRegistration() {

        // cenario
        Integer id = 11;
        Registration updatingRegistration = Registration.builder().id(11).build();


        // execucao
        Registration updatedRegistration = createValidRegistration();
        updatedRegistration.setId(id);

        Mockito.when(registrationRepository.save(updatingRegistration)).thenReturn(updatedRegistration);
        Registration registration = registrationService.update(updatingRegistration);

        // assert
        assertThat(registration.getId()).isEqualTo(updatedRegistration.getId());
        assertThat(registration.getName()).isEqualTo(updatedRegistration.getName());
        assertThat(registration.getDateOfRegistration()).isEqualTo(updatedRegistration.getDateOfRegistration());
        assertThat(registration.getRegistration()).isEqualTo(updatedRegistration.getRegistration());

    }

    @Test
    @DisplayName("Should filter registrations must by properties")
    public void findRegistrationTest() {

        // cenario
        Registration registration = createValidRegistration();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<Registration> listRegistrations = Arrays.asList(registration);
        Page<Registration> page = new PageImpl<Registration>(Arrays.asList(registration),
                PageRequest.of(0,10), 1);

        // execucao
        Mockito.when(registrationRepository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<Registration> result = registrationService.find(registration, pageRequest);

        // assercao
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listRegistrations);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

}




