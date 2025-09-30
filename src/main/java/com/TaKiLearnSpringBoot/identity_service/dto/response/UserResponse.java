package com.TaKiLearnSpringBoot.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     String id;
     String userName;
     String firstName;
     String lastName;
     LocalDate dayOfB;
     Set<RoleResponse> roles;
}
