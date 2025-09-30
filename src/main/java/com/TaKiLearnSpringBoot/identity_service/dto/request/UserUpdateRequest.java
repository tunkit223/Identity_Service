package com.TaKiLearnSpringBoot.identity_service.dto.request;


import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, message = "PASSWORD_INVALID")
    String passWord;
    String firstName;
    String lastName;


    LocalDate dayOfB;
    List<String> roles;
}
