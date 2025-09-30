package com.TaKiLearnSpringBoot.identity_service.mapper;


import com.TaKiLearnSpringBoot.identity_service.dto.request.PermissionRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.PermissionResponse;
import com.TaKiLearnSpringBoot.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermisson(PermissionRequest request);
    PermissionResponse toPermissonResponse(Permission permisson);
}
