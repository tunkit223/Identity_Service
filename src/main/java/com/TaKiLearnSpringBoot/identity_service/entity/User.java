package com.TaKiLearnSpringBoot.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     String id;
     String userName;
     String passWord;
     String firstName;
     String lastName;
     LocalDate dayOfB;

     @ManyToMany
     Set<Role> roles;

}
