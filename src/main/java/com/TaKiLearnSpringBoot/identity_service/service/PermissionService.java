package com.TaKiLearnSpringBoot.identity_service.service;


import com.TaKiLearnSpringBoot.identity_service.dto.request.PermissionRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.PermissionResponse;
import com.TaKiLearnSpringBoot.identity_service.entity.Permission;
import com.TaKiLearnSpringBoot.identity_service.mapper.PermissionMapper;
import com.TaKiLearnSpringBoot.identity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermisson((request));
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissonResponse(permission);
    }


    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissonResponse).toList();
    }


    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
