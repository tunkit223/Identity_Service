package com.TaKiLearnSpringBoot.identity_service.controller;

import com.TaKiLearnSpringBoot.identity_service.dto.request.UserCreationRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.UserResponse;
import com.TaKiLearnSpringBoot.identity_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse response;
    private LocalDate dob;

    @BeforeEach
    void initDate(){
        dob = LocalDate.of(2005,3,22);

        request = UserCreationRequest.builder()
                .userName("Kiet")
                .firstName("Truong")
                .lastName("Kiet")
                .passWord("123456")
                .dayOfB(dob)
                .build();

        response = UserResponse.builder()
                .id("bc1d97ce-a4e7-490f-b36c-6dc222afe661")
                .userName("Kiet")
                .firstName("Truong")
                .lastName("Kiet")
                .dayOfB(dob)
                .build();
    }

    @Test
    //Test create User in Controller
    void createUser_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                        .thenReturn(response);

        //WHEN (THEN)
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                        .value("bc1d97ce-a4e7-490f-b36c-6dc222afe661")
                );

        //Then
    }


    @Test
        //Test create User in Controller
    void createUser_userNameInvalid_fail() throws Exception {
        //GIVEN
        request.setUserName("T");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);


        //WHEN (THEN)
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Username must be at least 3 characters")
                );

        //Then
    }
}
