package com.TaKiLearnSpringBoot.identity_service.dto.request;


import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String userName;

    @Size(min = 6, message = "PASSWORD_INVALID")
    String passWord;
    String firstName;
    String lastName;

    LocalDate dayOfB;
}
