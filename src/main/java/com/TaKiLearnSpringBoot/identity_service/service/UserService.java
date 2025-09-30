package com.TaKiLearnSpringBoot.identity_service.service;
import com.TaKiLearnSpringBoot.identity_service.dto.request.UserCreationRequest;


import com.TaKiLearnSpringBoot.identity_service.dto.request.UserUpdateRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.UserResponse;
import com.TaKiLearnSpringBoot.identity_service.entity.User;
import com.TaKiLearnSpringBoot.identity_service.enums.Role;
import com.TaKiLearnSpringBoot.identity_service.exception.AppException;
import com.TaKiLearnSpringBoot.identity_service.exception.ErrorCode;
import com.TaKiLearnSpringBoot.identity_service.mapper.UserMapper;
import com.TaKiLearnSpringBoot.identity_service.repository.RoleRepository;
import com.TaKiLearnSpringBoot.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;


    public UserResponse createUser(UserCreationRequest request){

        if(userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
       // user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User byUserName = userRepository.findByUserName(name).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(byUserName);
    }


    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getUser(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.userName == authentication.name || hasRole('ADMIN')")
    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user,request);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
