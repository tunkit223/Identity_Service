package com.TaKiLearnSpringBoot.identity_service.service;


import com.TaKiLearnSpringBoot.identity_service.dto.request.RoleRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.RoleResponse;
import com.TaKiLearnSpringBoot.identity_service.mapper.RoleMapper;
import com.TaKiLearnSpringBoot.identity_service.repository.PermissionRepository;
import com.TaKiLearnSpringBoot.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleResult;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleService {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permissisons = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissisons));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
