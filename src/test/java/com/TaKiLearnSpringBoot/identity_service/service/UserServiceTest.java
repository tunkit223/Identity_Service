package com.TaKiLearnSpringBoot.identity_service.service;

import com.TaKiLearnSpringBoot.identity_service.dto.request.UserCreationRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.UserResponse;
import com.TaKiLearnSpringBoot.identity_service.entity.User;
import com.TaKiLearnSpringBoot.identity_service.exception.AppException;
import com.TaKiLearnSpringBoot.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initDate() {
        dob = LocalDate.of(2005, 3, 22);

        request = UserCreationRequest.builder()
                .userName("admin")
                .firstName("Truong")
                .lastName("Kiet")
                .passWord("123456")
                .dayOfB(dob)
                .build();

        response = UserResponse.builder()
                .id("bc1d97ce-a4e7-490f-b36c-6dc222afe661")
                .userName("admin")
                .firstName("Truong")
                .lastName("Kiet")
                .dayOfB(dob)
                .build();

        user = User.builder()
                .id("bc1d97ce-a4e7-490f-b36c-6dc222afe661")
                .userName("admin")
                .firstName("Truong")
                .lastName("Kiet")
                .dayOfB(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        //GIVEN
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("bc1d97ce-a4e7-490f-b36c-6dc222afe661");
    }

    @Test
    void createUser_userExisted_fail() {
        //GIVEN
        when(userRepository.existsByUserName(anyString())).thenReturn(true);

        //WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
}
