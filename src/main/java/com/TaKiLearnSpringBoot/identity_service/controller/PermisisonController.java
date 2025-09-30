package com.TaKiLearnSpringBoot.identity_service.controller;

import com.TaKiLearnSpringBoot.identity_service.dto.ApiResponse;
import com.TaKiLearnSpringBoot.identity_service.dto.request.PermissionRequest;
import com.TaKiLearnSpringBoot.identity_service.dto.response.PermissionResponse;
import com.TaKiLearnSpringBoot.identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisisons")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermisisonController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> delete(@PathVariable String permissionId){
        permissionService.delete(permissionId);
        return ApiResponse.<Void>builder()
                .message("Permission has been deleted")
                .build();
    }
}
