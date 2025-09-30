package com.TaKiLearnSpringBoot.identity_service.configuration;

import com.TaKiLearnSpringBoot.identity_service.entity.User;
import com.TaKiLearnSpringBoot.identity_service.enums.Role;
import com.TaKiLearnSpringBoot.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
           if( userRepository.findByUserName("admin").isEmpty()){
               var roles = new HashSet<String>();
               roles.add(Role.ADMIN.name());

               User user = User.builder()
                       .userName("admin")
                       .passWord(passwordEncoder.encode("admin"))
                       //.roles(roles)
               .build();

               userRepository.save(user);
               log.warn("default admin has been created with default password: amin, please check");
           }
        };
    }
}
